package io.openjob.common.dto;

import io.openjob.common.constant.HttpMethodEnum;
import io.openjob.common.constant.MediaTypeEnum;
import io.openjob.common.constant.RequestTypeEnum;
import io.openjob.common.constant.ResponseModeEnum;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.7
 */
public class HttpProcessorDTO {

    /**
     * Http url
     */
    private String url;

    /**
     * Http method
     *
     * @see HttpMethodEnum#getType()
     */
    private String method;

    /**
     * Timeout(ms)
     */
    private Long timeout;

    /**
     * Content Type
     *
     * @see MediaTypeEnum#getType()
     */
    private String contentType;

    /**
     * Body
     */
    private String body;

    /**
     * Cookie
     */
    private String cookie;

    /**
     * Response mode
     *
     * @see ResponseModeEnum#getMode()
     */
    private String responseMode;

    /**
     * JSON => key
     */
    private String key;

    /**
     * JSON => value or string
     */
    private String value;

    /**
     * Request type
     *
     * @see RequestTypeEnum#getType()
     */
    private String requestType;
}
