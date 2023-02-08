package io.openjob.server.admin.constant;

/**
 * @author inhere
 */
public class AdminConstant {
    /**
     * login expire time, default is 30min
     */
    public static final Integer LOGIN_EXPIRE_TIME = 1800;

    /**
     * user login session key name in headers
     * "session: session_key"
     */
    public static final String HEADER_SESSION_KEY = "session";

    /**
     * user api token header name in headers
     * "token: user_token"
     */
    public static final String HEADER_TOKEN_KEY = "token";

    /**
     * Request uid key.
     */
    public static final String REQUEST_UID_KEY = "uid";


    /**
     * mark is permission data
     */
    public static final Integer MENU_TYPE_PERM = 2;

    /**
     * mark is admin menu data
     */
    public static final Integer MENU_TYPE_MENU = 1;
}
