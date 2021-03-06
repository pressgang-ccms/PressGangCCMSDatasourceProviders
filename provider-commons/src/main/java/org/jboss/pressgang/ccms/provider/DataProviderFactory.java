/*
  Copyright 2011-2014 Red Hat, Inc

  This file is part of PressGang CCMS.

  PressGang CCMS is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  PressGang CCMS is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with PressGang CCMS.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.jboss.pressgang.ccms.provider;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.LinkedList;
import java.util.List;

import org.jboss.pressgang.ccms.provider.listener.ProviderListener;

/**
 *
 */
public abstract class DataProviderFactory {
    private List<ProviderListener> listeners = new LinkedList<ProviderListener>();

    private static ClassLoader getContextClassLoader() {
        return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
            public ClassLoader run() {
                ClassLoader cl = null;
                try {
                    cl = Thread.currentThread().getContextClassLoader();
                } catch (SecurityException ex) {
                }
                return cl;
            }
        });
    }

    /**
     * Initialise the Data Provider Factory with the required data.
     *
     * @param args The arguments required to create the Data Provider Factory.
     */
    @SuppressWarnings("unchecked")
    public static DataProviderFactory create(final Object... args) {
        // Find the implementation for the DataProviderFactory.
        final Class<? extends DataProviderFactory> dataProviderClass = findDataProviderImpl();

        // Find the constructor that matches the arguments passed.
        final DataProviderFactory factory;
        try {
            Constructor<? extends DataProviderFactory> dataProviderConstructor = null;
            final Constructor[] constructors = dataProviderClass.getDeclaredConstructors();
            for (final Constructor constructor : constructors) {
                final Class<?>[] params = constructor.getParameterTypes();
                boolean matches = false;

                // Ensure that all the argument match
                if (args.length == params.length) {
                    matches = true;
                    for (int i = 0; i < params.length; i++) {
                        if (!params[i].isAssignableFrom(args[i].getClass())) {
                            matches = false;
                        }
                    }
                }

                // If the constructor matches then break the loop, as we've found the matching constructor
                if (matches) {
                    dataProviderConstructor = constructor;
                    break;
                }
            }

            // Make sure a constructor was found
            if (dataProviderConstructor != null) {
                factory = dataProviderConstructor.newInstance(args);
            } else {
                factory = null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return factory;
    }

    @SuppressWarnings("unchecked")
    private static Class<? extends DataProviderFactory> findDataProviderImpl() {
        // Get the class loader to be used to load the factory.
        final ClassLoader classLoader = getContextClassLoader();

        final String serviceId = "META-INF/services/" + DataProviderFactory.class.getName();
        // try to find services in CLASSPATH
        try {
            InputStream is;
            if (classLoader == null) {
                is = ClassLoader.getSystemResourceAsStream(serviceId);
            } else {
                is = classLoader.getResourceAsStream(serviceId);
            }

            if (is != null) {
                // Read the class name from the services meta-inf
                final BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                final String factoryClassName = rd.readLine();
                rd.close();

                // Find the class for the specified class name
                if (factoryClassName != null && !"".equals(factoryClassName)) {
                    try {
                        return (Class<? extends DataProviderFactory>) findClass(factoryClassName, classLoader);
                    } catch (ClassNotFoundException e) {
                        final URL url = classLoader.getResource(serviceId);
                        throw new ClassNotFoundException("Could not find from factory file " + url, e);
                    }
                } else {
                    final URL url = classLoader.getResource(serviceId);
                    throw new ClassNotFoundException("Could not find from factory file " + url);
                }
            } else {
                final URL url = classLoader.getResource(serviceId);
                throw new ClassNotFoundException("Could not find from factory file " + url);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Creates an instance of the specified class using the specified <code>ClassLoader</code> object.
     *
     * @throws ClassNotFoundException if the given class could not be found or could not be instantiated
     */
    private static Class<?> findClass(String className, ClassLoader classLoader) throws ClassNotFoundException {
        try {
            Class<?> spiClass;
            if (classLoader == null) {
                spiClass = Class.forName(className);
            } else {
                try {
                    spiClass = Class.forName(className, false, classLoader);
                } catch (ClassNotFoundException ex) {
                    spiClass = Class.forName(className);
                }
            }
            return spiClass;
        } catch (ClassNotFoundException x) {
            throw x;
        } catch (Exception x) {
            throw new ClassNotFoundException("Factory " + className + " could not be instantiated: " + x, x);
        }
    }

    protected DataProviderFactory() {
    }

    /**
     * Get a Data Provider instance associated with the Application for the specified class.
     *
     * @param clazz The class of the Data Provider to get an instance of.
     * @param <T>   A DataProvider interface or implementation.
     * @return The created data provider instance if one was able to be found.
     */
    public <T> T getProvider(final Class<T> clazz) {
        return loadProvider(clazz);
    }

    /**
     * Load a provider for a specified class.
     *
     * @param clazz The class of the Data Provider to load.
     * @param <T>   A DataProvider interface or implementation.
     * @return The created data provider instance if one was able to be found.
     */
    protected abstract <T> T loadProvider(final Class<T> clazz);

    public abstract boolean isTransactionsSupported();

    public abstract void rollback();

    public abstract boolean isNotificationsSupported();

    public void registerListener(ProviderListener listener) {
        if (isNotificationsSupported()) {
            listeners.add(listener);
        } else {
            throw new UnsupportedOperationException("Notification events aren't supported");
        }
    }

    protected List<ProviderListener> getListeners() {
        return listeners;
    }
}
