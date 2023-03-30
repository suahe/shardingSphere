package com.sah.shardingSphere.exception;

/**
 * @author suahe
 * @date 2022/6/16
 * @ApiNote
 */
public class LoginAuthException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LoginAuthException(Exception e) {
        super(e);
    }

    public LoginAuthException(String message) {
        super(message);
    }

    public LoginAuthException(String message, Object... args) {
        super(String.format(message, args));
    }

    public LoginAuthException(Throwable cause, String msg) {
        super(msg, cause);
    }
}
