package be.ida_mediafoundry.jetpack.carve.manager.serializer;

import org.apache.sling.api.resource.Resource;

public interface Serializer {
    void serialize(Resource resource, Object object);

    void serializeMultiValue(Resource resource, Object object);
}
