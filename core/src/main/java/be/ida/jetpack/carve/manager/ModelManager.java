package be.ida.jetpack.carve.manager;

import be.ida.jetpack.carve.manager.exception.ModelManagerException;

public interface ModelManager {
    <T> T retrieve(Class<T> clazz, String id) throws ModelManagerException;

    void persist(Object object) throws ModelManagerException;
}
