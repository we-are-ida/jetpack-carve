package be.ida-mediafoundry.jetpack.carve.models;

import be.ida-mediafoundry.jetpack.carve.annotations.CarveId;
import be.ida-mediafoundry.jetpack.carve.annotations.CarveModel;
import be.ida-mediafoundry.jetpack.carve.manager.pathpolicy.providers.SimplePathPolicyProvider;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import javax.inject.Inject;
import javax.inject.Named;

@CarveModel(pathPolicyProvider = SimplePathPolicyProvider.class)
@Model(adaptables = Resource.class)
public class ReferencesModel {
    @CarveId
    @Inject
    @Named("test-id")
    private String id;

    @Inject
    @ChildResource(name = "primitives")
    private PrimitivesModel primitivesModel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PrimitivesModel getPrimitivesModel() {
        return primitivesModel;
    }

    public void setPrimitivesModel(PrimitivesModel primitivesModel) {
        this.primitivesModel = primitivesModel;
    }
}
