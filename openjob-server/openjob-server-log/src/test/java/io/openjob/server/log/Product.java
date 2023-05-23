package io.openjob.server.log;

import lombok.Data;

import java.util.Map;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.1
 */
@Data
public class Product {
    private String id;
    private String name;
    private Map<String, String> content;
}
