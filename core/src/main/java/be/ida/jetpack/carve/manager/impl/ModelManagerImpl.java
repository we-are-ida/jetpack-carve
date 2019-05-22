package be.ida.jetpack.carve.manager.impl;

import be.ida.jetpack.carve.manager.ModelManager;
import be.ida.jetpack.carve.manager.constants.PersistenceConstants;
import be.ida.jetpack.carve.manager.exception.ModelManagerException;
import be.ida.jetpack.carve.manager.exception.PersistorException;
import be.ida.jetpack.carve.manager.exception.RetrieverException;
import be.ida.jetpack.carve.manager.impl.function.PersistorFunction;
import be.ida.jetpack.carve.manager.impl.function.RetrieverFunction;
import be.ida.jetpack.carve.manager.serializer.exception.SerializerException;
import be.ida.jetpack.carve.manager.validator.exception.ValidationException;
import be.ida.jetpack.carve.resource.ResolverClosure;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

@Component(scope = ServiceScope.PROTOTYPE,
        service = ModelManager.class,
        property = {
                Constants.SERVICE_DESCRIPTION + ":String=Provides a persistence manager for Sling Models.",
                Constants.SERVICE_VENDOR + ":String=" + PersistenceConstants.SERVICE_VENDOR,
        })
public class ModelManagerImpl implements ModelManager {
    public static final String MSG_CAN_NOT_SERIALIZE_MODEL = "Can not serialize model.";
    private static final String MSG_CAN_NOT_RETRIEVE_MODEL = "Unable to retrieve the model.";

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public void persist(Object model) throws ModelManagerException {
        try {
            PersistorFunction persistorFunction = new PersistorFunction(model);
            ResolverClosure<Object> persistor = new ResolverClosure<>(resourceResolverFactory, persistorFunction);
            persistor.execute();
        } catch (PersistorException | SerializerException | LoginException | ValidationException e) {
            throw new ModelManagerException(MSG_CAN_NOT_SERIALIZE_MODEL, e);
        }
    }

    @Override
    public <T> T retrieve(Class<T> clazz, String id) throws ModelManagerException {
        T result = null;

        try {
            RetrieverFunction<T> retrieverFunction = new RetrieverFunction<>(clazz, id);
            ResolverClosure<T> retriever = new ResolverClosure<>(resourceResolverFactory, retrieverFunction);
            result = retriever.execute();
        } catch (RetrieverException | LoginException e) {
            throw new ModelManagerException(MSG_CAN_NOT_RETRIEVE_MODEL, e);
        }

        return result;
    }
}
