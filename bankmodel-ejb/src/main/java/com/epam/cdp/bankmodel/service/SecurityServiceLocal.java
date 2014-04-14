package com.epam.cdp.bankmodel.service;

import javax.ejb.Local;

import com.epam.cdp.bankmodel.model.Person;

/**
 * The Interface SecurityServiceLocal.
 * @author Roman_Konovalov
 */
@Local
public interface SecurityServiceLocal {

	/**
	 * Gets Current User.
	 * @return Current User
	 */
	Person getCurrentUser();

}
