package be.ida_mediafoundry.jetpack.carve.manager.impl;

import be.ida_mediafoundry.jetpack.carve.manager.ModelManager;
import be.ida_mediafoundry.jetpack.carve.manager.constants.PersistenceConstants;
import be.ida_mediafoundry.jetpack.carve.manager.exception.ModelManagerException;
import be.ida_mediafoundry.jetpack.carve.models.*;
import be.ida_mediafoundry.jetpack.carve.util.ModelsUtil;
import io.wcm.testing.mock.aem.junit.AemContext;
import org.apache.sling.api.resource.Resource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

public class ModelManagerImplTest {

    private static final String PRIMITIVES_MODEL_ID = "123456789";
    private static final String REFERENCES_MODEL_ID = "134543243";

    @Rule
    public final AemContext context = new AemContext();
    private ModelManager modelManager;

    private static void assertPrimitivesModel(PrimitivesModel primitivesModel) {
        assertThat(primitivesModel).isNotNull();
        assertThat(primitivesModel.getId()).isEqualTo(PRIMITIVES_MODEL_ID);
        assertThat(primitivesModel.getName()).isEqualTo(ModelsUtil.NAME);
        assertThat(primitivesModel.getNumberInteger()).isEqualTo(ModelsUtil.NUMBER_INTEGER);
        assertThat(primitivesModel.getNumberFloat()).isEqualTo(ModelsUtil.NUMBER_FLOAT);
        assertThat(primitivesModel.getNumberLong()).isEqualTo(ModelsUtil.NUMBER_LONG);
        assertThat(primitivesModel.getNumberDouble()).isEqualTo(ModelsUtil.NUMBER_DOUBLE);
        assertThat(primitivesModel.getNumberInt()).isEqualTo(ModelsUtil.NUMBER_INT);
        assertThat(primitivesModel.getNumberFlt()).isEqualTo(ModelsUtil.NUMBER_FLT);
        assertThat(primitivesModel.getNumberLng()).isEqualTo(ModelsUtil.NUMBER_LNG);
        assertThat(primitivesModel.getNumberDbl()).isEqualTo(ModelsUtil.NUMBER_DBL);
        assertThat(primitivesModel.getDate()).isEqualTo(ModelsUtil.DATE);
    }

    @Before
    public void setUp() {
        context.registerInjectActivateService(new ModelManagerImpl());
        context.addModelsForClasses(PrimitivesModel.class, ReferencesModel.class, LocationModel.class,
                DatePathPolicyBasedModel.class, BucketPathPolicyBasedModel.class);
    }

    @Test
    public void persistPrimitive() throws ModelManagerException {
        modelManager = context.getService(ModelManager.class);
        modelManager.persist(ModelsUtil.getPrimitivesModel(PRIMITIVES_MODEL_ID));

        Resource primitivesModelResource = context.resourceResolver().getResource(String.format("%s/%s/%s", PersistenceConstants.PERSISTENCE_ROOT, "primitives-model", PRIMITIVES_MODEL_ID));
        assertThat(primitivesModelResource).isNotNull();

        PrimitivesModel primitivesModel = primitivesModelResource.adaptTo(PrimitivesModel.class);
        assertPrimitivesModel(primitivesModel);
    }

    @Test
    public void persistReferences() throws ModelManagerException {
        modelManager = context.getService(ModelManager.class);
        modelManager.persist(ModelsUtil.getReferencesModel(REFERENCES_MODEL_ID, PRIMITIVES_MODEL_ID));

        Resource referencesModelResource = context.resourceResolver().getResource(String.format("%s/%s/%s", PersistenceConstants.PERSISTENCE_ROOT, "references-model", REFERENCES_MODEL_ID));
        assertThat(referencesModelResource).isNotNull();

        ReferencesModel referencesModel = referencesModelResource.adaptTo(ReferencesModel.class);
        assertThat(referencesModel).isNotNull();
        assertThat(referencesModel.getId()).isEqualTo(REFERENCES_MODEL_ID);
        assertPrimitivesModel(referencesModel.getPrimitivesModel());
    }

    @Test
    public void persistLocation() throws ModelManagerException {
        modelManager = context.getService(ModelManager.class);
        modelManager.persist(ModelsUtil.getLocationModel("123"));

        Resource locationModelResource = context.resourceResolver().getResource(String.format("%s/%s", "/apps/test", "123"));
        assertThat(locationModelResource).isNotNull();

        LocationModel locationModel = locationModelResource.adaptTo(LocationModel.class);
        assertThat(locationModel).isNotNull();
        assertThat(locationModel.getId()).isEqualTo("123");
        assertThat(locationModel.getCalendar()).isEqualTo(ModelsUtil.CALENDAR);
    }

    @Test
    public void persistDatePathPolicy() throws ModelManagerException {
        modelManager = context.getService(ModelManager.class);
        modelManager.persist(ModelsUtil.getDatePathPolicyBasedModel("456"));

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd/HH/mm");
        String formatDateTime = now.format(formatter);

        Resource resource = context.resourceResolver().getResource(String.format("%s/%s/%s/%s", PersistenceConstants.PERSISTENCE_ROOT, "date-path-policy-based-model", formatDateTime, "456"));
        assertThat(resource).isNotNull();

        DatePathPolicyBasedModel model = resource.adaptTo(DatePathPolicyBasedModel.class);
        assertThat(model).isNotNull();
        assertThat(model.getId()).isEqualTo("456");
    }

    @Test
    public void persistBucketPathPolicy() throws ModelManagerException {
        modelManager = context.getService(ModelManager.class);
        modelManager.persist(ModelsUtil.getBucketPathPolicyBasedModel("456"));

        //md5 for 456 = 250cf8b51c773f3f8dc8b4be867a9a02
        String path = "250/cf8";

        Resource resource = context.resourceResolver().getResource(String.format("%s/%s/%s/%s", PersistenceConstants.PERSISTENCE_ROOT, "bucket-path-policy-based-model", path, "456"));
        assertThat(resource).isNotNull();

        BucketPathPolicyBasedModel model = resource.adaptTo(BucketPathPolicyBasedModel.class);
        assertThat(model).isNotNull();
        assertThat(model.getId()).isEqualTo("456");
    }

    @Test
    public void retrievePrimitivesModel() throws ModelManagerException {
        modelManager = context.getService(ModelManager.class);
        modelManager.persist(ModelsUtil.getPrimitivesModel(PRIMITIVES_MODEL_ID));

        PrimitivesModel primitivesModel = modelManager.retrieve(PrimitivesModel.class, PRIMITIVES_MODEL_ID);
        assertPrimitivesModel(primitivesModel);
    }

    @Test
    public void retrieveLocationsModel() throws ModelManagerException {
        modelManager = context.getService(ModelManager.class);
        modelManager.persist(ModelsUtil.getLocationModel("123"));

        LocationModel locationModel = modelManager.retrieve(LocationModel.class, "123");
        assertThat(locationModel).isNotNull();
        assertThat(locationModel.getId()).isEqualTo("123");
        assertThat(locationModel.getCalendar()).isEqualTo(ModelsUtil.CALENDAR);
    }

    @Test
    public void retrieveBucketPathPolicyBasedModel() throws ModelManagerException {
        modelManager = context.getService(ModelManager.class);
        modelManager.persist(ModelsUtil.getBucketPathPolicyBasedModel("456"));

        BucketPathPolicyBasedModel model = modelManager.retrieve(BucketPathPolicyBasedModel.class, "456");
        assertThat(model).isNotNull();
        assertThat(model.getId()).isEqualTo("456");
    }
}
