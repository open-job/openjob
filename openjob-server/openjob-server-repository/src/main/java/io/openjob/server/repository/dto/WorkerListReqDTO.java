package io.openjob.server.repository.dto;

import lombok.Data;

/**
 * @author riki
 * @date 2022-12-26
 */
@Data
public class WorkerListReqDTO {
    /**
     * List page
     */
    private Integer page = 1;

    /**
     * List size. default 10
     */
    private Integer size = 10;


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
