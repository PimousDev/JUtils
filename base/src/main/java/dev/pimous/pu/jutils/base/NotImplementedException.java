package dev.pimous.pu.jutils.base;

import java.io.Serial;

public class NotImplementedException extends InternalException{

	@Serial
	private static final long serialVersionUID = -2957044987975805404L;

	public NotImplementedException(String message, Throwable cause){
		super(message, cause);
	}
	public NotImplementedException(String message){
		super(message);
	}
	public NotImplementedException(){
		super();
	}
}
