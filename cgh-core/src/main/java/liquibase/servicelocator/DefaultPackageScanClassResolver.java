package liquibase.servicelocator;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import liquibase.logging.Logger;
import liquibase.logging.core.DefaultLogger;

/**
 * Default implement of {@link PackageScanClassResolver}
 */
public class DefaultPackageScanClassResolver implements PackageScanClassResolver {

    private static Map<String, Set<String>> classesByJarUrl = new HashMap<String, Set<String>>();

    protected final transient Logger log = new DefaultLogger();
    private Set<ClassLoader> classLoaders;
    private Set<PackageScanFilter> scanFilters;

    private Set<Class<?>> cachedClasses = new HashSet<Class<?>>();

    public DefaultPackageScanClassResolver() {
        cacheClasses();
    }

    private void cacheClasses() {
        try {
            cachedClasses.clear();
            List<String> classNames = readResourceAsStrings("/uk/co/techblue/cgh/liquibase/liquibase-services.txt");
            Set<ClassLoader> set = getClassLoaders();
            for (String className : classNames) {
                for (ClassLoader classLoader : set) {
                    try {
                        Class<?> type = classLoader.loadClass(className);
                        cachedClasses.add(type);
                        break;
                    } catch (NoClassDefFoundError e) {
                        log.debug("Cannot find the class definition in classloader: " + classLoader + ". Reason: " + e, e);
                    } catch (Throwable e) {
                        log.severe("Cannot load class in classloader: " + classLoader + ".  Reason: " + e, e);
                    }
                }
            }
        } catch (IOException ex) {
            log.severe("", ex);
        }
    }

    protected List<String> readResourceAsStrings(String resourceName) throws IOException {
        String s = readResourceAsString(resourceName);
        return Arrays.asList(s.split("\\r?\\n"));
    }

    protected String readResourceAsString(String resourceName) throws IOException {
        StringBuilder sb = new StringBuilder(1024);
        BufferedInputStream inStream = new BufferedInputStream(getClass().getResourceAsStream(resourceName));
        byte[] chars = new byte[1024];
        int bytesRead = 0;
        try {
            while ((bytesRead = inStream.read(chars)) > -1) {
                // System.out.println(bytesRead);
                sb.append(new String(chars, 0, bytesRead));
            }
        } finally {
            inStream.close();
        }
        return sb.toString();
    }

    public void addClassLoader(ClassLoader classLoader) {
        try {
            getClassLoaders().add(classLoader);
        } catch (UnsupportedOperationException ex) {
            // Ignore this exception as the PackageScanClassResolver
            // don't want use any other classloader
        }
    }

    public void addFilter(PackageScanFilter filter) {
        if (scanFilters == null) {
            scanFilters = new LinkedHashSet<PackageScanFilter>();
        }
        scanFilters.add(filter);
    }

    public void removeFilter(PackageScanFilter filter) {
        if (scanFilters != null) {
            scanFilters.remove(filter);
        }
    }

    public Set<ClassLoader> getClassLoaders() {
        if (classLoaders == null) {
            classLoaders = new HashSet<ClassLoader>();
            ClassLoader ccl = Thread.currentThread().getContextClassLoader();
            if (ccl != null) {
                log.debug("The thread context class loader: " + ccl + "  is used to load the class");
                classLoaders.add(ccl);
            }
            classLoaders.add(DefaultPackageScanClassResolver.class.getClassLoader());
        }
        return classLoaders;
    }

    public void setClassLoaders(Set<ClassLoader> classLoaders) {
        this.classLoaders = classLoaders;
    }

    @SuppressWarnings("unchecked")
    public Set<Class<?>> findImplementations(Class<?> parent, String... packageNames) {
        if (packageNames == null) {
            return Collections.EMPTY_SET;
        }

        log.debug("Searching for implementations of " + parent.getName() + " in packages: " + Arrays.asList(packageNames));

        PackageScanFilter test = getCompositeFilter(new AssignableToPackageScanFilter(parent));
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
        for (String pkg : packageNames) {
            find(test, pkg, classes);
        }

        log.debug("Found: " + classes);

        return classes;
    }

    @SuppressWarnings("unchecked")
    public Set<Class<?>> findByFilter(PackageScanFilter filter, String... packageNames) {
        if (packageNames == null) {
            return Collections.EMPTY_SET;
        }

        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
        for (String pkg : packageNames) {
            find(filter, pkg, classes);
        }

        log.debug("Found: " + classes);

        return classes;
    }

    protected void find(PackageScanFilter test, String packageName, Set<Class<?>> classes) {
        packageName = packageName.replace('.', '/');

        Set<ClassLoader> set = getClassLoaders();

        for (ClassLoader classLoader : set) {
            find(test, packageName, classLoader, classes);
        }
    }

    protected void find(PackageScanFilter test, String packageName, ClassLoader loader, Set<Class<?>> classes) {
        log.debug("Searching for: " + test + " in package: " + packageName + " using classloader: "
                + loader.getClass().getName());

        packageName = packageName.replaceAll("/", ".");

        for (Class<?> aClass : cachedClasses) {
            if (aClass.isInterface()) {
                continue;
            }
            if (!aClass.getName().startsWith(packageName)) {
                continue;
            }
            if (test.matches(aClass)) {
                classes.add(aClass);
            }
        }
    }

    // We can override this method to support the custom ResourceLocator

    protected URL customResourceLocator(URL url) throws IOException {
        // Do nothing here
        return url;
    }

    /**
     * Strategy to get the resources by the given classloader.
     * <p/>
     * Notice that in WebSphere platforms there is a {@link WebSpherePackageScanClassResolver} to take care of WebSphere's
     * odditiy of resource loading.
     * 
     * @param loader the classloader
     * @param packageName the packagename for the package to load
     * @return URL's for the given package
     * @throws IOException is thrown by the classloader
     */
    protected Enumeration<URL> getResources(ClassLoader loader, String packageName) throws IOException {
        log.debug("Getting resource URL for package: " + packageName + " with classloader: " + loader);

        // If the URL is a jar, the URLClassloader.getResources() seems to require a trailing slash. The
        // trailing slash is harmless for other URLs
        if (!packageName.endsWith("/")) {
            packageName = packageName + "/";
        }
        return loader.getResources(packageName);
    }

    private PackageScanFilter getCompositeFilter(PackageScanFilter filter) {
        if (scanFilters != null) {
            CompositePackageScanFilter composite = new CompositePackageScanFilter(scanFilters);
            composite.addFilter(filter);
            return composite;
        }
        return filter;
    }

    /**
     * Finds matches in a physical directory on a filesystem. Examines all files within a directory - if the File object is not
     * a directory, and ends with <i>.class</i> the file is loaded and tested to see if it is acceptable according to the Test.
     * Operates recursively to find classes within a folder structure matching the package structure.
     * 
     * @param test a Test used to filter the classes that are discovered
     * @param parent the package name up to this directory in the package hierarchy. E.g. if /classes is in the classpath and we
     *        wish to examine files in /classes/org/apache then the values of <i>parent</i> would be <i>org/apache</i>
     * @param location a File object representing a directory
     */
    @SuppressWarnings("unused")
    private void loadImplementationsInDirectory(PackageScanFilter test, String parent, File location, Set<Class<?>> classes) {
        File[] files = location.listFiles();
        StringBuilder builder = null;

        for (File file : files) {
            builder = new StringBuilder(100);
            String name = file.getName();
            if (name != null) {
                name = name.trim();
                builder.append(parent).append("/").append(name);
                String packageOrClass = parent == null ? name : builder.toString();

                if (file.isDirectory()) {
                    loadImplementationsInDirectory(test, packageOrClass, file, classes);
                } else if (name.endsWith(".class")) {
                    addIfMatching(test, packageOrClass, classes);
                }
            }
        }
    }

    /**
     * Finds matching classes within a jar files that contains a folder structure matching the package structure. If the File is
     * not a JarFile or does not exist a warning will be logged, but no error will be raised.
     * 
     * @param test a Test used to filter the classes that are discovered
     * @param parent the parent package under which classes must be in order to be considered
     * @param stream the inputstream of the jar file to be examined for classes
     * @param urlPath the url of the jar file to be examined for classes
     */
    protected void loadImplementationsInJar(PackageScanFilter test, String parent, InputStream stream, String urlPath,
            Set<Class<?>> classes) {
        JarInputStream jarStream = null;
        try {

            if (!classesByJarUrl.containsKey(urlPath)) {
                Set<String> names = new HashSet<String>();

                if (stream instanceof JarInputStream) {
                    jarStream = (JarInputStream) stream;
                } else {
                    jarStream = new JarInputStream(stream);
                }

                JarEntry entry;
                while ((entry = jarStream.getNextJarEntry()) != null) {
                    String name = entry.getName();
                    if (name != null) {
                        name = name.trim();
                        if (!entry.isDirectory() && name.endsWith(".class")) {
                            names.add(name);
                        }
                    }
                }

                classesByJarUrl.put(urlPath, names);
            }

            for (String name : classesByJarUrl.get(urlPath)) {
                if (name.startsWith(parent)) {
                    addIfMatching(test, name, classes);
                }
            }
        } catch (IOException ioe) {
            log.warning("Cannot search jar file '" + urlPath + "' for classes matching criteria: " + test
                    + " due to an IOException: " + ioe.getMessage(), ioe);
        } finally {
            try {
                if (jarStream != null) {
                    jarStream.close();
                }
            } catch (IOException ignore) {
            }
        }
    }

    /**
     * Add the class designated by the fully qualified class name provided to the set of resolved classes if and only if it is
     * approved by the Test supplied.
     * 
     * @param test the test used to determine if the class matches
     * @param fqn the fully qualified name of a class
     */
    protected void addIfMatching(PackageScanFilter test, String fqn, Set<Class<?>> classes) {
        try {
            String externalName = fqn.substring(0, fqn.indexOf('.')).replace('/', '.');
            Set<ClassLoader> set = getClassLoaders();
            boolean found = false;
            for (ClassLoader classLoader : set) {
                log.debug("Testing that class " + externalName + " matches criteria [" + test + "] using classloader:"
                        + classLoader);
                try {
                    Class<?> type = classLoader.loadClass(externalName);
                    log.debug("Loaded the class: " + type + " in classloader: " + classLoader);
                    if (test.matches(type)) {
                        log.debug("Found class: " + type + " which matches the filter in classloader: " + classLoader);
                        classes.add(type);
                    }
                    found = true;
                    break;
                } catch (ClassNotFoundException e) {
                    log.debug("Cannot find class '" + fqn + "' in classloader: " + classLoader + ". Reason: " + e, e);
                } catch (NoClassDefFoundError e) {
                    log.debug("Cannot find the class definition '" + fqn + "' in classloader: " + classLoader + ". Reason: "
                            + e, e);
                } catch (Throwable e) {
                    log.severe("Cannot load class '" + fqn + "' in classloader: " + classLoader + ".  Reason: " + e, e);
                }
            }
            if (!found) {
                // use debug to avoid being noisy in logs
                log.debug("Cannot find class '" + fqn + "' in any classloaders: " + set);
            }
        } catch (Exception e) {
            log.warning(
                    "Cannot examine class '" + fqn + "' due to a " + e.getClass().getName() + " with message: "
                            + e.getMessage(), e);
        }
    }

}