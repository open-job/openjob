package io.openjob.server.repository.dto;

import io.openjob.server.repository.dto.json.TemplateExtraDTO;
import lombok.Data;

import java.util.List;

/**
 * @author inhere
 * @since 1.0.0
 */
@Data
public class NotifyTemplateDTO {

    /**
     * PK
     */
    private Integer id;

    /**
     * Template name. eg: Wechat, DingTalk, Wecom, Feishu
     */
    private String name;

    /**
     * notify type. 1 webhook 2 email 3 sms
     */
    private Integer type;

    /**
     * Level. 1 notice 2 warning 3 error
     */
    private String level;

    /**
     * notify events list. JSON: [task_fail, task_suc, task_cancel, task_skip]
     */
    private List<String> events;

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
     * Template contents
     */
    private String content;

    /**
     * Extra info. eg: third platform token
     */
    private TemplateExtraDTO extra;

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

