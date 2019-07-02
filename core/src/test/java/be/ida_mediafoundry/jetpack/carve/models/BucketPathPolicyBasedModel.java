package be.ida_mediafoundry.jetpack.carve.models;

import be.ida_mediafoundry.jetpack.carve.annotations.CarveId;
import be.ida_mediafoundry.jetpack.carve.annotations.CarveModel;
import be.ida_mediafoundry.jetpack.carve.manager.pathpolicy.providers.BucketPathPolicyProvider;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;
import javax.inject.Named;

@CarveModel(pathPolicyProvider = BucketPathPolicyProvider.class)
@Model(adaptables = Resource.class)
public class BucketPathPolicyBasedModel {
    @CarveId
    @Inject
    @Named("test-id")
    private String id;
    @Inject
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
