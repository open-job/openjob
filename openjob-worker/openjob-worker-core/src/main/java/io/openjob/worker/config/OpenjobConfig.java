package io.openjob.worker.config;

import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
public class OpenjobConfig {

    /**
     * Properties
     */
    private static final Map<String, String> PROPS = Maps.newConcurrentMap();

    static {
        try {
            init();
            load();
        } catch (Throwable e) {
            log.error("Failed to initialize openjob config", e);
        }
    }

    /**
     * Init default config.
     */
    public static void init() {

    }

    /**
     * Load properties.
     */
    public static void load() {
        Properties properties = ConfigLoader.getProperties();
        for (Object key : properties.keySet()) {
            setConfig((String) key, (String) properties.get(key));
        }
    }

    /**
     * Set config
     *
     * @param key   key
     * @param value value
     */
    public static void setConfig(String key, String value) {
        Assert.notNull(key, "key cannot be null");
        Assert.notNull(key, "value cannot be null");

        PROPS.put(key, value);
    }

    /**
     * Set default config
     *
     * @param key   key
     * @param value value
     */
    public static void setDefaultConfig(String key, String value) {
        Assert.notNull(key, "key cannot be null");
        Assert.notNull(key, "value cannot be null");

        if (Objects.isNull(PROPS.get(key))) {
            PROPS.put(key, value);
        }
    }

    /**
     * Get config by String.
     *
     * @param key key
     * @return String
     */
    public static String getString(String key) {
        Assert.notNull(key, "key cannot be null");
        return PROPS.get(key);
    }

    /**
     * Get config by String.
     *
     * @param key key
     * @return String
     */
    public static String getString(String key, String def) {
        Assert.notNull(key, "key cannot be null");

        String value = PROPS.get(key);
        if (Objects.isNull(value)) {
            return def;
        }
        return value;
    }

    /**
     * Get config by integer.
     *
     * @param key key
     * @return Integer
     */
    public static Integer getInteger(String key) {
        Assert.notNull(key, "key cannot be null");
        return Integer.valueOf(PROPS.get(key));
    }

    /**
     * Get config by integer.
     *
     * @param key key
     * @return Integer
     */
    public static Integer getInteger(String key, Integer def) {
        Assert.notNull(key, "key cannot be null");

        String value = PROPS.get(key);
        if (Objects.isNull(value)) {
            return def;
        }
        return Integer.valueOf(value);
    }

    /**
     * Get config by Long.
     *
     * @param key key
     * @return Long
     */
    public static Long getLong(String key) {
        Assert.notNull(key, "key cannot be null");
        return Long.valueOf(PROPS.get(key));
    }

    /**
     * Get config by Long.
     *
     * @param key key
     * @return Long
     */
    public static Long getLong(String key, Long def) {
        Assert.notNull(key, "key cannot be null");

        String value = PROPS.get(key);
        if (Objects.isNull(value)) {
            return def;
        }
        return Long.valueOf(value);
    }

    /**
     * Get config by boolean.
     *
     * @param key key
     * @return Boolean
     */
    public static Boolean getBoolean(String key) {
        Assert.notNull(key, "key cannot be null");
        return Boolean.valueOf(PROPS.get(key));
    }

    /**
     * Get config by boolean.
     *
     * @param key key
     * @return Boolean
     */
    public static Boolean getBoolean(String key, Boolean def) {
        Assert.notNull(key, "key cannot be null");

        String value = PROPS.get(key);
        if (Objects.isNull(value)) {
            return def;
        }
        return Boolean.valueOf(value);
    }

    /**
     * Get config.
     *
     * @param key key
     * @return String
     */
    public static String getConfig(String key) {
        Assert.notNull(key, "key cannot be null");
        return PROPS.get(key);
    }

    /**
     * Remove config.
     *
     * @param key key
     * @return String
     */
    public static String removeConfig(String key) {
        Assert.notNull(key, "key cannot be null");
        return PROPS.remove(key);
    }
}
