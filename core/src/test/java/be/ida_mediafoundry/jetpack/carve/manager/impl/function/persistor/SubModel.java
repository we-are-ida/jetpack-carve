package be.ida_mediafoundry.jetpack.carve.manager.impl.function.persistor;

import be.ida_mediafoundry.jetpack.carve.annotations.CarveId;
import be.ida_mediafoundry.jetpack.carve.annotations.CarveModel;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;
import javax.inject.Named;

@CarveModel
@Model(adaptables = Resource.class)
public class SubModel {

    @Inject
    @CarveId
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
