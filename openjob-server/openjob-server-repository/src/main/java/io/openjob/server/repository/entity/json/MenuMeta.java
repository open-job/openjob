package io.openjob.server.repository.entity.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author inhere
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuMeta implements Serializable {
    private String icon;

    private String title;

    /**
     * view component path
     */
    private String component;
}
