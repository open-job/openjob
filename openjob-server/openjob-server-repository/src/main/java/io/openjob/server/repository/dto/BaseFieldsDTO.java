package io.openjob.server.repository.dto;

import lombok.Data;

/**
 * @author inhere <in.798@qq.com>
 */
@Data
abstract public class BaseFieldsDTO {

    /**
     * Delete status. 1=yes 2=no
     */
    private Integer deleted;

    /**
     * Delete time
     */
    private Long deleteTime;

    /**
     * Update time
     */
    private Long updateTime;

    /**
     * Create time
     */
    private Long createTime;
}
