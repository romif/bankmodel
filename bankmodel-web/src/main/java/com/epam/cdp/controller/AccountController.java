package com.epam.cdp.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.epam.cdp.bankmodel.model.Account;
import com.epam.cdp.bankmodel.service.AccountServiceLocal;
import com.epam.cdp.bankmodel.service.SecurityServiceBean;

/**
 * The Class AccountController.
 * @author Roman_Konovalov
 */
@Model
public class AccountController {

	/**
	 * New Account.
	 */
	private Account newAccount;

	/**
	 * Account Service.
	 */
	@Inject
	private AccountServiceLocal accountService;

	/**
	 * Security Service.
	 */
	@Inject
	private SecurityServiceBean securityService;

	/**
	 * Init new Account.
	 */
	@PostConstruct
	public void init() {
		newAccount = new Account();
	}

	/**
	 * Gets new Account.
	 * @return newAccount
	 */
	@Named
	@Produces
	public Account getNewAccount() {
		return newAccount;
	}

	/**
	 * Register new Account.
	 * @return actions page
	 */
	public String register() {
		newAccount.setPerson(securityService.getCurrentUser());
		accountService.createAccount(newAccount);
		return "actions?faces-redirect=true";
	}

}
