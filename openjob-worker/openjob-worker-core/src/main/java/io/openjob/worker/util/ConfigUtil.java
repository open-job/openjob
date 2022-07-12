package io.openjob.worker.util;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class ConfigUtil {

    /**
     * Private constructor.
     */
    private ConfigUtil() {

    }

    /**
     * Classpath.
     */
    private static final String CLASSPATH_PREFIX = "classpath:";

    /**
     * Default charset.
     */
    private static final Charset DEFAULT_CHARSET = Charset.forName(StandardCharsets.UTF_8.name());

    /**
     * Load properties.
     *
     * @param filename filename
     * @return Properties
     */
    public static Properties loadProperties(String filename) {
        // Empty filename.
        if (StringUtils.isBlank(filename)) {
            return null;
        }

        // Absolute file.
        if (isAbsoluteFile(filename)) {
            return loadPropertiesFromAbsoluteFile(filename);
        }

        // Classpath file.
        if (filename.startsWith(CLASSPATH_PREFIX)) {
            return loadPropertiesFromClasspathFile(filename);
        }

        // Relative file.
        return loadPropertiesFromRelativeFile(filename);
    }

    /**
     * Whether is absolute file.
     *
     * @param path path
     * @return Boolean
     */
    private static Boolean isAbsoluteFile(String path) {
        File[] files = File.listRoots();
        for (File file : files) {
            if (path.startsWith(file.getPath())) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * Load relative file.
     *
     * @param fileName fileName
     * @return Properties
     */
    private static Properties loadPropertiesFromRelativeFile(String fileName) {
        String userDir = System.getProperty("user.dir");
        String realFilePath = appendSeparator(userDir) + fileName;
        return loadPropertiesFromAbsoluteFile(realFilePath);
    }

    /**
     * Append file separator.
     *
     * @param dir dir
     * @return String
     */
    public static String appendSeparator(String dir) {
        if (!dir.endsWith(File.separator)) {
            dir += File.separator;
        }
        return dir;
    }

    /**
     * Load relative file.
     *
     * @param fileName fileName
     * @return Properties
     */
    private static Properties loadPropertiesFromAbsoluteFile(String fileName) {
        Properties properties;
        try {

            File file = new File(fileName);
            if (!file.exists()) {
                return null;
            }

            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), DEFAULT_CHARSET))) {
                properties = new Properties();
                properties.load(bufferedReader);
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    /**
     * Load classpath file.
     *
     * @param fileName fileName
     * @return Properties
     */
    private static Properties loadPropertiesFromClasspathFile(String fileName) {
        fileName = fileName.substring(CLASSPATH_PREFIX.length()).trim();

        List<URL> list;
        try {
            Enumeration<URL> urls = getClassLoader().getResources(fileName);
            list = new ArrayList<>();
            while (urls.hasMoreElements()) {
                list.add(urls.nextElement());
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        if (list.isEmpty()) {
            return null;
        }

        Properties properties = new Properties();
        for (URL url : list) {
            try (BufferedReader bufferedReader =
                         new BufferedReader(new InputStreamReader(url.openStream(), DEFAULT_CHARSET))) {
                Properties p = new Properties();
                p.load(bufferedReader);
                properties.putAll(p);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return properties;
    }

    /**
     * Get class loader.
     *
     * @return ClassLoader
     */
    private static ClassLoader getClassLoader() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = ConfigUtil.class.getClassLoader();
        }
        return classLoader;
    }
}
