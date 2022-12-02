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
     */
    public static final String LOGIN_HEADER_KEY = "session-key";

    /**
     * mark is permission data
     */
    public static final Integer MENU_TYPE_PERM = 1;

    /**
     * mark is admin menu data
     */
    public static final Integer MENU_TYPE_MENU = 2;
}
