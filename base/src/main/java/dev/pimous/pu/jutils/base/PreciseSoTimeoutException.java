package dev.pimous.pu.jutils.base;

import java.io.Serial;
import java.net.SocketTimeoutException;

/**
 * @author Xibitol
 * @since 1.0.0
 */
public class PreciseSoTimeoutException extends SocketTimeoutException{

	@Serial
	private static final long serialVersionUID = -2424066774686565249L;

	public final int delay;
	public final int expectedBytes;

	@SuppressWarnings("this-escape")
	public PreciseSoTimeoutException(
		final int delay, final int expectedBytes,
		final SocketTimeoutException cause
	){
		super();

		this.delay = delay;
		this.expectedBytes = expectedBytes;
		this.bytesTransferred = cause.bytesTransferred;

		addSuppressed(cause); // this-escape
	}
}
