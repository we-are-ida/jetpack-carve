package be.ida.jetpack.carve.manager.pathpolicy.providers;

public class SimplePathPolicyProvider implements PathPolicyProvider {

    @Override
    public String apply(String id) {
        return id;
    }
}
