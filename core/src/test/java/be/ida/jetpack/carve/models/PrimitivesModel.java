package be.ida.jetpack.carve.models;

import be.ida.jetpack.carve.annotations.CarveId;
import be.ida.jetpack.carve.annotations.CarveModel;
import be.ida.jetpack.carve.manager.pathpolicy.providers.SimplePathPolicyProvider;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;

@CarveModel(pathPolicyProvider = SimplePathPolicyProvider.class)
@Model(adaptables = Resource.class)
public class PrimitivesModel {
    @CarveId
    @Inject
    @Named("test-id")
    private String id;
    @Inject
    private String name;
    @Inject
    private Integer numberInteger;
    @Inject
    private Long numberLong;
    @Inject
    private Double numberDouble;
    @Inject
    private Float numberFloat;
    @Inject
    private int numberInt;
    @Inject
    private long numberLng;
    @Inject
    private float numberFlt;
    @Inject
    private double numberDbl;
    @Inject
    private Date date;

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

    public Integer getNumberInteger() {
        return numberInteger;
    }

    public void setNumberInteger(Integer numberInteger) {
        this.numberInteger = numberInteger;
    }

    public Long getNumberLong() {
        return numberLong;
    }

    public void setNumberLong(Long numberLong) {
        this.numberLong = numberLong;
    }

    public Float getNumberFloat() {
        return numberFloat;
    }

    public void setNumberFloat(Float numberFloat) {
        this.numberFloat = numberFloat;
    }

    public Double getNumberDouble() {
        return numberDouble;
    }

    public void setNumberDouble(Double numberDouble) {
        this.numberDouble = numberDouble;
    }

    public int getNumberInt() {
        return numberInt;
    }

    public void setNumberInt(int numberInt) {
        this.numberInt = numberInt;
    }

    public long getNumberLng() {
        return numberLng;
    }

    public void setNumberLng(long numberLng) {
        this.numberLng = numberLng;
    }

    public float getNumberFlt() {
        return numberFlt;
    }

    public void setNumberFlt(float numberFlt) {
        this.numberFlt = numberFlt;
    }

    public double getNumberDbl() {
        return numberDbl;
    }

    public void setNumberDbl(double numberDbl) {
        this.numberDbl = numberDbl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
