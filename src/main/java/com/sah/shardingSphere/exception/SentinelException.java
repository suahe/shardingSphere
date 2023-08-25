package com.sah.shardingSphere.exception;

public class SentinelException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SentinelException(String message){
		super(message);
	}

	public SentinelException(Throwable cause)
	{
		super(cause);
	}

	public SentinelException(String message, Throwable cause)
	{
		super(message,cause);
	}
}
