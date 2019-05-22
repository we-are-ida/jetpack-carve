package be.ida.jetpack.carve.manager.impl.function;

import be.ida.jetpack.carve.annotations.CarveModel;
import be.ida.jetpack.carve.manager.exception.ModelManagerException;
import be.ida.jetpack.carve.manager.util.ModelManagerUtil;
import org.apache.commons.lang.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.function.Function;

abstract class ManagerFunction {
    static Function<String, String> getPathPolicy(Class clazz) throws ModelManagerException {
        Annotation[] annotations = clazz.getDeclaredAnnotations();

        return Arrays.stream(annotations)
              .filter(annotation -> annotation instanceof CarveModel)
              .findFirst()
              .map(annotation -> ((CarveModel) annotation).pathPolicyProvider())
              .map(pathPolicyProviderClass -> {
                  try {
                      return pathPolicyProviderClass.newInstance();
                  } catch (IllegalAccessException | InstantiationException e) {
                      return null;
                  }
              })
              .orElseThrow(() -> new ModelManagerException("Could not get PathPolicy"));
    }

    static String getLocation(Class clazz) {
        Annotation[] annotations = clazz.getDeclaredAnnotations();

        return Arrays.stream(annotations)
                     .filter(annotation -> annotation instanceof CarveModel)
                     .findFirst()
                     .map(annotation -> ((CarveModel) annotation).location())
                     .map(location -> StringUtils.isBlank(location) ? null : location)
                     .orElse(ModelManagerUtil.getCollectionPath(clazz));
    }
}
