package be.ida.jetpack.carve.manager.serializer;

import org.apache.sling.api.resource.Resource;

public interface Serializer {
    void serialize(Resource resource, Object object);
}
