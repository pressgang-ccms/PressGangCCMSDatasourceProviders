package org.jboss.pressgang.ccms.provider;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.pressgang.ccms.provider.exception.BadRequestException;
import org.jboss.pressgang.ccms.provider.exception.InternalServerErrorException;
import org.jboss.pressgang.ccms.provider.exception.NotFoundException;
import org.jboss.pressgang.ccms.provider.exception.ProviderException;
import org.jboss.pressgang.ccms.provider.exception.UnauthorisedException;
import org.jboss.pressgang.ccms.provider.exception.UpgradeException;
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
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.ClientResponseFailure;

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
            // If the method isn't a getter or returns void then skip it
            if (!method.getName().startsWith("get") || method.getReturnType().equals(Void.TYPE)) continue;

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

    protected String getExpansionString(final Collection<String> expansionNames) throws IOException {
        final ExpandDataTrunk expand = new ExpandDataTrunk();
        final List<ExpandDataTrunk> expandDataTrunks = new ArrayList<ExpandDataTrunk>();
        for (final String expansionName : expansionNames) {
            expandDataTrunks.add(new ExpandDataTrunk(new ExpandDataDetails(expansionName)));
        }
        expand.setBranches(expandDataTrunks);
        return mapper.writeValueAsString(expand);
    }

    protected String getExpansionString(final Map<String, List<String>> expansionNames) throws IOException {
        final ExpandDataTrunk expand = new ExpandDataTrunk();
        final List<ExpandDataTrunk> expandDataTrunks = new ArrayList<ExpandDataTrunk>();
        for (final Map.Entry<String, List<String>> expansionName : expansionNames.entrySet()) {
            final ExpandDataTrunk expandData = new ExpandDataTrunk(new ExpandDataDetails(expansionName.getKey()));

            // Add the sub expansions
            if (expansionName.getValue() != null) {
                final List<ExpandDataTrunk> subExpandDataTrunks = new ArrayList<ExpandDataTrunk>();
                for (final String subExpansionName : expansionName.getValue()) {
                    subExpandDataTrunks.add(new ExpandDataTrunk(new ExpandDataDetails(subExpansionName)));
                }
                expandData.setBranches(subExpandDataTrunks);
            }

            expandDataTrunks.add(expandData);
        }
        expand.setBranches(expandDataTrunks);
        return mapper.writeValueAsString(expand);
    }

    protected RuntimeException handleException(final Exception e) {
        if (e instanceof ClientResponseFailure) {
            final ClientResponseFailure crf = ((ClientResponseFailure) e);
            final ClientResponse response = crf.getResponse();
            final int status = response.getStatus();
            switch (status) {
                case HttpURLConnection.HTTP_BAD_REQUEST: return new BadRequestException(e);
                case HttpURLConnection.HTTP_INTERNAL_ERROR: return new InternalServerErrorException(e);
                case HttpURLConnection.HTTP_UNAUTHORIZED: return new UnauthorisedException(e);
                case 426: return new UpgradeException(e);
                case HttpURLConnection.HTTP_NOT_FOUND: return new NotFoundException(e);
                default: return crf;
            }
        } else if (e instanceof ProviderException) {
            return (ProviderException) e;
        } else if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        } else {
            return new RuntimeException(e);
        }
    }
}
