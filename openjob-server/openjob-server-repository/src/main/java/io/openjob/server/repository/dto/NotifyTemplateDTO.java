package io.openjob.server.repository.dto;

import java.util.List;

import lombok.Data;

/**
 * @author inhere
 * @date 2022-11-07 21:33:21
 * @since 1.0.0
 */
@Data
public class NotifyTemplateDTO {

    /**
     * PK
     */
    private Integer id;

    /**
     * Tempalte name
     */
    private String name;

    /**
     * Tempalte contents
     */
    private String content;

    /**
     * Level. 1 notice 2 warning 3 error
     */
    private String level;

    /**
     * related contact ids. JSON [12, 34]
     */
    private List<Long> contactIds;

    /**
     * related group ids. JSON [12, 34]
     */
    private List<Long> groupIds;

    /**
     * Webhook URL
     */
    private String webhook;

    /**
     * Third info. JSON: Dingding, wecom, feishu
     */
    private String thirdInfo;

    /**
     * Creator user ID
     */
    private Integer userId;

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

