/**
 * 
 */
package com.epam.cdp.bankmodel.service;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.epam.cdp.bankmodel.model.Person;

/**
 * The Class SecurityServiceBean.
 * @author Roman_Konovalov
 *
 */
@Stateless
@LocalBean
public class SecurityServiceBean implements SecurityServiceLocal {

	/**
	 * Context.
	 */
	@Resource
	private SessionContext context;

	/**
	 * AccountService.
	 */
	@Inject
	private AccountServiceBean accountService;

	@Override
	public Person getCurrentUser() {
		String currentUserName = context.getCallerPrincipal().getName();
		return accountService.getPersonByName(currentUserName);
	}

}
