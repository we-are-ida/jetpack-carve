package be.ida_mediafoundry.jetpack.carve.serializer.impl;

import be.ida_mediafoundry.jetpack.carve.manager.serializer.Serializer;
import be.ida_mediafoundry.jetpack.carve.manager.serializer.impl.SimpleSerializerImpl;
import be.ida_mediafoundry.jetpack.carve.manager.validator.exception.ValidationException;
import io.wcm.testing.mock.aem.junit.AemContext;
import org.apache.sling.api.resource.Resource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SimpleSerializerImplTest {

    private static final String RES_PATH = "/test";

    @Rule
    public final AemContext context = new AemContext();

    private Resource testResource;

    @Before
    public void setUp() {
        testResource = context.create().resource(RES_PATH);
    }

    @Test
    public void testSerialize() throws NoSuchFieldException, ValidationException {
        TestClass model = new TestClass();
        model.field1 = "abcd";

        Serializer serializer = new SimpleSerializerImpl(TestClass.class.getField("field1"));
        serializer.serialize(testResource, model);

        assertTrue(context.resourceResolver().getResource(RES_PATH).getValueMap().containsKey("field1"));
        assertEquals("abcd", context.resourceResolver().getResource(RES_PATH).getValueMap().get("field1"));
    }

    @Test
    public void testSerializeMultiValue() throws NoSuchFieldException, ValidationException {
        TestClass model = new TestClass();
        model.field2 = new ArrayList<>();
        model.field2.add("a");
        model.field2.add("b");

        Serializer serializer = new SimpleSerializerImpl(TestClass.class.getField("field2"));
        serializer.serialize(testResource, model);

        assertTrue(context.resourceResolver().getResource(RES_PATH).getValueMap().containsKey("field2"));
        assertEquals(2, ((List<String>)context.resourceResolver().getResource(RES_PATH).getValueMap().get("field2")).size());
    }

    public class TestClass {
        public String field1;
        public List<String> field2;
    }
}
