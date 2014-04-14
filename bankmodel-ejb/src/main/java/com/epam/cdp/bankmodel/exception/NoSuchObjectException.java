package com.epam.cdp.bankmodel.exception;

/**
 * The Class NoSuchObjectException.
 * @author Roman_Konovalov
 *
 */
public class NoSuchObjectException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new no such object exception.
	 *
	 * @param message the message
	 */
	public NoSuchObjectException(final String message) {
		super(message);
	}

	/**
	 * Instantiates a new no such object exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public NoSuchObjectException(final String message, final Throwable cause) {
		super(message, cause);
	}

}