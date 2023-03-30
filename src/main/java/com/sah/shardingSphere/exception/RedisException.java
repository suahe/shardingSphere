package com.sah.shardingSphere.exception;

/**
 * @author suahe
 * @date 2022/6/16
 * @ApiNote
 */
public class RedisException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RedisException(Exception e) {
        super(e);
    }

    public RedisException(String message) {
        super(message);
    }

    public RedisException(String message, Object... args) {
        super(String.format(message, args));
    }

    public RedisException(Throwable cause, String msg) {
        super(msg, cause);
    }
}
