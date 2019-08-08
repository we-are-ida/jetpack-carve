package be.ida_mediafoundry.jetpack.carve.util;

import be.ida_mediafoundry.jetpack.carve.models.*;
import java.util.Calendar;
import java.util.Date;

public class ModelsUtil {
    public final static String NAME = "JetPack";
    public final static Integer NUMBER_INTEGER = new Integer(12345);
    public final static Long NUMBER_LONG = new Long(12345l);
    public final static Float NUMBER_FLOAT = new Float(12345.33f);
    public final static Double NUMBER_DOUBLE = new Double(12345.33);
    public final static int NUMBER_INT = 12345;
    public final static long NUMBER_LNG = 12345l;
    public final static float NUMBER_FLT = 12345.33f;
    public final static double NUMBER_DBL = 12345.33;
    public final static Date DATE = new Date(0L);
    public final static Calendar CALENDAR = Calendar.getInstance();

    public static PrimitivesModel getPrimitivesModel(String id) {
        PrimitivesModel primitivesModel = new PrimitivesModel();
        primitivesModel.setId(id);
        primitivesModel.setName(NAME);
        primitivesModel.setNumberInteger(NUMBER_INTEGER);
        primitivesModel.setNumberFloat(NUMBER_FLOAT);
        primitivesModel.setNumberLong(NUMBER_LONG);
        primitivesModel.setNumberDouble(NUMBER_DOUBLE);
        primitivesModel.setNumberInt(NUMBER_INT);
        primitivesModel.setNumberFlt(NUMBER_FLT);
        primitivesModel.setNumberLng(NUMBER_LNG);
        primitivesModel.setNumberDbl(NUMBER_DBL);
        primitivesModel.setDate(DATE);
        return primitivesModel;
    }

    public static ReferencesModel getReferencesModel(String id, String childId) {
        ReferencesModel referencesModel = new ReferencesModel();
        referencesModel.setId(id);
        referencesModel.setPrimitivesModel(getPrimitivesModel(childId));

        return referencesModel;
    }

    public static LocationModel getLocationModel(String id) {
        LocationModel locationModel = new LocationModel();
        locationModel.setId(id);
        locationModel.setName(NAME);
        locationModel.setCalendar(CALENDAR);
        return locationModel;
    }

    public static DatePathPolicyBasedModel getDatePathPolicyBasedModel(String id) {
        DatePathPolicyBasedModel model = new DatePathPolicyBasedModel();
        model.setId(id);
        model.setName(NAME);
        return model;
    }

    public static BucketPathPolicyBasedModel getBucketPathPolicyBasedModel(String id) {
        BucketPathPolicyBasedModel model = new BucketPathPolicyBasedModel();
        model.setId(id);
        model.setName(NAME);
        return model;
    }
}
