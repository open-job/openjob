package io.openjob.server.repository.dto;

import lombok.Data;

/**
 * @author riki
 * @since 1.0.0
 */
@Data
public class WorkerListReqDTO {
    /**
     * List page
     */
    private Integer page;

    /**
     * List size. default 10
     */
    private Integer size;


    /**
     * Appid
     */
    private Long appId;

    /**
     * NamespaceId
     */
    private Long namespaceId;

    /**
     * App name
     */
    private String appName;


}
