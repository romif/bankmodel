package com.epam.cdp.exceptionhandler;

import javax.faces.context.ExceptionHandlerFactory;

/**
 * @author Roman_Konovalov
 */
public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory {

	/**
	 * parent.
	 */
	private final ExceptionHandlerFactory parent;

	/**
	 * @param parent parent
	 */
	public CustomExceptionHandlerFactory(final ExceptionHandlerFactory parent) {
		this.parent = parent;
	}

	@Override
	public CustomExceptionHandler getExceptionHandler() {
		return new CustomExceptionHandler(this.parent.getExceptionHandler());
	}

}