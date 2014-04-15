package com.epam.cdp.exceptionhandler;

import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import com.epam.cdp.bankmodel.exception.InsufficientMoneyException;

/**
 * The Class CustomExceptionHandler.
 * @author Roman_Konovalov
 *
 */
public class CustomExceptionHandler extends ExceptionHandlerWrapper {

	/**
	 * wrapped.
	 */
	private final ExceptionHandler wrapped;

	/**
	 * @param wrapped wrapped
	 */
	public CustomExceptionHandler(final ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return this.wrapped;
	}

	@Override
	public void handle() throws FacesException {

		final Iterator<ExceptionQueuedEvent> iterator = getUnhandledExceptionQueuedEvents().iterator();
		while (iterator.hasNext()) {
			ExceptionQueuedEvent event = (ExceptionQueuedEvent) iterator.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

			// get the exception from context
			Throwable t = context.getException();
			while (t.getCause() != null) {
				t = t.getCause();
			}

			final FacesContext facesContext = FacesContext.getCurrentInstance();
			try {
				NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
				if (t instanceof InsufficientMoneyException) {
					navigationHandler.handleNavigation(facesContext, null,
							"/pages/insufficientMoney.xhtml?faces-redirect=true");
				} else {
					navigationHandler.handleNavigation(facesContext, null, "/pages/exception.xhtml");
				}
			} finally {
				iterator.remove();
			}
		}
		// parent hanle
		getWrapped().handle();
	}

}
