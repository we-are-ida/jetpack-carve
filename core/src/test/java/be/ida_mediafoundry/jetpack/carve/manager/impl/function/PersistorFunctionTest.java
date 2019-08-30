package be.ida_mediafoundry.jetpack.carve.manager.impl.function;

import be.ida_mediafoundry.jetpack.carve.manager.impl.function.persistor.MainModel;
import be.ida_mediafoundry.jetpack.carve.manager.impl.function.persistor.SubModel;
import be.ida_mediafoundry.jetpack.carve.manager.validator.exception.ValidationException;
import io.wcm.testing.mock.aem.junit.AemContext;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PersistorFunctionTest {

    @Rule
    public final AemContext context = new AemContext();

    @Test
    public void applyTest() throws ValidationException {
        SubModel sub1 = new SubModel();
        sub1.setName("een");
        SubModel sub2 = new SubModel();
        sub2.setName("twee");

        MainModel model = new MainModel();
        model.setId("1");
        model.getStringList().add("a");
        model.getStringList().add("b");
        model.getStringList().add("c");
        model.setBoolField(false);
        model.setDoubleField(345.5d);
        model.setLongField(54554L);
        model.setIntField(333);
        model.getModelsList().add(sub1);
        model.getModelsList().add(sub2);

        new PersistorFunction(model).apply(context.resourceResolver());

        Resource persistedResource = context.resourceResolver().getResource("/content/data/1");
        assertNotNull(persistedResource);

        ValueMap props = persistedResource.getValueMap();
        assertEquals("1", props.get("id"));
        assertEquals(3, props.get("stringList", new String[] {}).length); // FIX !! @named if
        assertEquals(false, props.get("boolField"));
        assertEquals(345.5d, props.get("doubleField"));
        assertEquals(333, props.get("intField"));
        assertEquals(54554L, props.get("longField"));

        Resource containerResouce = persistedResource.getChild("modelsList"); // FIX !! @named if
        assertNotNull(containerResouce);

        assertNotNull(containerResouce.getChild("een"));
        assertNotNull(containerResouce.getChild("twee"));
    }
}
