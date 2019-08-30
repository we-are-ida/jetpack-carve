package be.ida_mediafoundry.jetpack.carve.manager.impl.function.persistor;

import be.ida_mediafoundry.jetpack.carve.annotations.CarveId;
import be.ida_mediafoundry.jetpack.carve.annotations.CarveModel;
import be.ida_mediafoundry.jetpack.carve.manager.pathpolicy.providers.SimplePathPolicyProvider;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@CarveModel(pathPolicyProvider = SimplePathPolicyProvider.class, location = "/content/data")
@Model(adaptables = Resource.class)
public class MainModel {

    @CarveId
    @Inject
    private String id;

    @Inject
    private Integer intField;

    @Inject
    private Double doubleField;

    @Inject
    private Boolean boolField;

    @Inject
    private Long longField;

    @Inject
    @Named("strings")
    private List<String> stringList = new ArrayList<>();

    @Inject
    @Named("models")
    private List<SubModel> modelsList = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIntField() {
        return intField;
    }

    public void setIntField(Integer intField) {
        this.intField = intField;
    }

    public Double getDoubleField() {
        return doubleField;
    }

    public void setDoubleField(Double doubleField) {
        this.doubleField = doubleField;
    }

    public Boolean getBoolField() {
        return boolField;
    }

    public void setBoolField(Boolean boolField) {
        this.boolField = boolField;
    }

    public Long getLongField() {
        return longField;
    }

    public void setLongField(Long longField) {
        this.longField = longField;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    public List<SubModel> getModelsList() {
        return modelsList;
    }

    public void setModelsList(List<SubModel> modelsList) {
        this.modelsList = modelsList;
    }
}
