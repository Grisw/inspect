package pers.sdulxt.inspect.model;

public interface Constant {

    /**
     * Debug mode.
     */
    boolean DEBUG = true;

    /**
     * Token expire time (In seconds).
     */
    int TOKEN_EXPIRE = 30 * 60;

    /**
     * Default password for a new user.
     */
    String DEFAULT_PASSWORD = "123456";
}
