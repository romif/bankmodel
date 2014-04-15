package com.epam.cdp.bankmodel.interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.cdp.bankmodel.exception.InvalidInputParametersException;

/**
 * The Class CheckingInputParametersInterceptor.
 * @author Roman_Konovalov
 */
public class CheckingInputParametersInterceptor {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(CheckingInputParametersInterceptor.class);

	/**
	 * Default constructor.
	 */
	public CheckingInputParametersInterceptor() {
	}

	/**
	 * AroundInvoke.
	 * @param ic
	 *            InvocationContext
	 * @return Object
	 * @throws Exception
	 *             Exception
	 */
	@AroundInvoke
	public Object aroundInvoke(final InvocationContext ic) throws Exception {
		for (Object parameter : ic.getParameters()) {
			if (parameter == null) {
				LOG.debug("Can't invoke method {}, because input parameters are not defined..", ic.getMethod()
						.getName());
				throw new InvalidInputParametersException("Can't invoke method " + ic.getMethod().getName()
						+ ", because input parameters are not defined..");
			}
		}
		return ic.proceed();
	}

}
