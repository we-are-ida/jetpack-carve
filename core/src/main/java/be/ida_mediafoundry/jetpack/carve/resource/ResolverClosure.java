package be.ida_mediafoundry.jetpack.carve.resource;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

public class ResolverClosure<T> {
    private static final String SERVICE_ACCOUNT_IDENTIFIER = "jetpack-carve-service";

    private Function<ResourceResolver, T> function;
    private ResourceResolverFactory resourceResolverFactory;

    public ResolverClosure(ResourceResolverFactory resourceResolverFactory, Function<ResourceResolver, T> function) {
        this.function = function;
        this.resourceResolverFactory = resourceResolverFactory;
    }

    public T execute() throws LoginException {
        try (final ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(getAuthenticationInfo())) {
            return function.apply(resourceResolver);
        }
    }

    private static Map<String, Object> getAuthenticationInfo(){
        return Collections.singletonMap(
                ResourceResolverFactory.SUBSERVICE,
                (Object) SERVICE_ACCOUNT_IDENTIFIER);
    }
}
