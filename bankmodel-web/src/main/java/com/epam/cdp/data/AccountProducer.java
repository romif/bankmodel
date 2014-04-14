package com.epam.cdp.data;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.epam.cdp.bankmodel.enums.Currency;
import com.epam.cdp.bankmodel.model.Account;
import com.epam.cdp.bankmodel.service.AccountServiceBean;
import com.epam.cdp.bankmodel.service.SecurityServiceBean;

/**
 * The Class AccountProducer.
 * @author Roman_Konovalov
 */
@RequestScoped
public class AccountProducer {

	/**
	 * Account Service.
	 */
	@Inject
	private AccountServiceBean accountService;

	/**
	 * Security Service.
	 */
	@Inject
	private SecurityServiceBean securityService;

	/**
	 * Gets Currencies.
	 * @return Currency array
	 */
	@Named
	@Produces
	public Currency[] getCurrencies() {
		return Currency.values();
	}

	/**
	 * Gets Accounts.
	 * @return Account list
	 */
	@Named
	@Produces
	public List<Account> getAccounts() {
		return accountService.getAccountsForPerson(securityService.getCurrentUser());
	}

}
