package org.jboss.pressgang.ccms.provider;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.pressgang.ccms.rest.RESTManager;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.collections.base.RESTBaseUpdateCollectionV1;
import org.jboss.pressgang.ccms.rest.v1.entities.base.RESTBaseEntityV1;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataDetails;
import org.jboss.pressgang.ccms.rest.v1.expansion.ExpandDataTrunk;
import org.jboss.pressgang.ccms.rest.v1.jaxrsinterfaces.RESTInterfaceV1;
import org.jboss.pressgang.ccms.utils.RESTCollectionCache;
import org.jboss.pressgang.ccms.utils.RESTEntityCache;
import org.jboss.pressgang.ccms.wrapper.RESTWrapperFactory;

public abstract class RESTDataProvider extends DataProvider {
    private static ObjectMapper mapper = new ObjectMapper();

    private final RESTManager restManager;

    protected RESTDataProvider(final RESTManager restManager, final RESTWrapperFactory wrapperFactory) {
        super(wrapperFactory);
        this.restManager = restManager;
    }

    @Override
    protected RESTWrapperFactory getWrapperFactory() {
        return (RESTWrapperFactory) super.getWrapperFactory();
    }

    protected RESTManager getRESTManager() {
        return restManager;
    }

    protected RESTInterfaceV1 getRESTClient() {
        return getRESTManager().getRESTClient();
    }

    protected RESTEntityCache getRESTEntityCache() {
        return getRESTManager().getRESTEntityCache();
    }

    protected RESTCollectionCache getRESTCollectionCache() {
        return getRESTManager().getRESTCollectionCache();
    }

    /**
     * Cleans the entity and it's collections to remove any data that doesn't need to be sent to the server.
     *
     * @param entity The entity to be cleaned.
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    protected void cleanEntityForSave(final RESTBaseEntityV1<?, ?, ?> entity) throws InvocationTargetException, IllegalAccessException {
        if (entity == null) return;

        for (final Method method : entity.getClass().getMethods()) {
            // If the method isn't a getter or isn't accessible then skip it.
            if (!method.getName().startsWith("get") || !method.isAccessible()) continue;

            // An entity might have
            if (isCollectionClass(method.getReturnType())) {
                cleanCollectionForSave((RESTBaseCollectionV1<?, ?, ?>) method.invoke(entity));
            } else if (isEntityClass(method.getReturnType())) {
                cleanEntityForSave((RESTBaseEntityV1<?, ?, ?>) method.invoke(entity));
            }
        }
    }

    /**
     * Checks if a class is or extends the REST Base Collection class.
     *
     * @param clazz The class to be checked.
     * @return True if the class is or extends
     */
    private boolean isCollectionClass(Class<?> clazz) {
        if (clazz == RESTBaseUpdateCollectionV1.class || clazz == RESTBaseCollectionV1.class) {
            return true;
        } else if (clazz.getSuperclass() != null) {
            return isCollectionClass(clazz.getSuperclass());
        } else {
            return false;
        }
    }

    /**
     * Checks if a class is or extends the REST Base Entity class.
     *
     * @param clazz The class to be checked.
     * @return True if the class is or extends
     */
    private boolean isEntityClass(Class<?> clazz) {
        if (clazz == RESTBaseEntityV1.class) {
            return true;
        } else if (clazz.getSuperclass() != null) {
            return isEntityClass(clazz.getSuperclass());
        } else {
            return false;
        }
    }

    /**
     * Cleans a collection and it's entities to remove any items in the collection that don't need to be sent to the server.
     *
     * @param collection The collection to be cleaned.
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    protected void cleanCollectionForSave(final RESTBaseCollectionV1<?, ?, ?> collection) throws InvocationTargetException,
            IllegalAccessException {
        if (collection == null) return;

        collection.removeInvalidChangeItemRequests();

        for (final RESTBaseEntityV1<?, ?, ?> item : collection.returnItems()) {
            cleanEntityForSave(item);
        }
    }

    protected String getExpansionString(String expansionName) throws IOException {
        return getExpansionString(expansionName, null);
    }

    protected String getExpansionString(String expansionName, String subExpansionName) throws IOException {
        final ExpandDataTrunk expand = new ExpandDataTrunk();
        final ExpandDataTrunk expandData = new ExpandDataTrunk(new ExpandDataDetails(expansionName));
        if (subExpansionName != null) {
            final ExpandDataTrunk expandSubData = new ExpandDataTrunk(new ExpandDataDetails(subExpansionName));
            expandData.setBranches(Arrays.asList(expandSubData));
        }
        expand.setBranches(Arrays.asList(expandData));
        return mapper.writeValueAsString(expand);
    }
}