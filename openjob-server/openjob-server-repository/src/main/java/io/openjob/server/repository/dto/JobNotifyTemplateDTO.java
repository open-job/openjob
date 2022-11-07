package io.openjob.server.repository.dto;

import lombok.Data;

/**
 * @author inhere
 * @date 2022-11-07 13:44:56
 * @since 1.0.0
 */
@Data
public class JobNotifyTemplateDTO {

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
    private Contact_ids contactIds;

    /**
     * related group ids. JSON [12, 34]
     */
    private Group_ids groupIds;

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

