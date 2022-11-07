package io.openjob.server.repository.dto;

import lombok.Data;
import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 21:34:57
 * @since 1.0.0
 */
@Data
public class AdminMenuDTO {

    /**
     * PK
     */
    private Integer id;

    /**
     * Parent ID
     */
    private Integer pid;

    /**
     * Type. 1=menu 2=perm
     */
    private Integer type;

    /**
     * Menu name
     */
    private String name;

    /**
     * Route path or API path
     */
    private String path;

    /**
     * Extra meta data. JSON object: {icon:xx,title:some.name}
     */
    private Extra extra;

    /**
     * Hidden status. 1=yes 2=no
     */
    private Integer hidden;

    /**
     * Sort value
     */
    private Integer sort;

    /**
     * Delete status. 1=yes 2=no
     */
    private Integer deleted;

    /**
     * Delete time
     */
    private Integer deleteTime;

    /**
     * Update time
     */
    private Integer updateTime;

    /**
     * Create time
     */
    private Integer createTime;
}

