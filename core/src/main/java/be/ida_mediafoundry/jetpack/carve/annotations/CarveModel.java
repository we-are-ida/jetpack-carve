package be.ida_mediafoundry.jetpack.carve.annotations;

import be.ida_mediafoundry.jetpack.carve.manager.pathpolicy.providers.BucketPathPolicyProvider;
import be.ida_mediafoundry.jetpack.carve.manager.pathpolicy.providers.PathPolicyProvider;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CarveModel {
    Class<? extends PathPolicyProvider> pathPolicyProvider() default BucketPathPolicyProvider.class;
    String location() default StringUtils.EMPTY;
}
