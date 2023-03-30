package com.sah.shardingSphere.exception;

public class LockException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public LockException(String message){
		super(message);
	}

	public LockException(Throwable cause)
	{
		super(cause);
	}

	public LockException(String message, Throwable cause)
	{
		super(message,cause);
	}
}
