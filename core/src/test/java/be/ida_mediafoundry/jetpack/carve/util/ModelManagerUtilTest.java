package be.ida_mediafoundry.jetpack.carve.util;

import be.ida_mediafoundry.jetpack.carve.manager.util.ModelManagerUtil;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ModelManagerUtilTest {

    @Test
    public void canStoreInMultiValuePropertyTest() throws NoSuchFieldException {
        assertFalse(ModelManagerUtil.canStoreInMultiValueProperty(TestClass.class.getField("one")));
        assertFalse(ModelManagerUtil.canStoreInMultiValueProperty(TestClass.class.getField("two")));
        assertTrue(ModelManagerUtil.canStoreInMultiValueProperty(TestClass.class.getField("three")));
    }

    class TestClass {
        public String one;
        public String[] two;
        public List<String> three;
    }
}
