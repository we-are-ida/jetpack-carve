package be.ida_mediafoundry.jetpack.carve.manager.pathpolicy.providers;

import java.util.function.Function;

public interface PathPolicyProvider extends Function<String, String> {

    String SEPERATOR = "/";

    //TODO: add configuration to indicate if a provider is enabled for retrieving and/or persisting

}
