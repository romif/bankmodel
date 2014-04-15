package com.epam.cdp.controller;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;

import com.epam.cdp.bankmodel.model.Account;
import com.epam.cdp.bankmodel.service.AccountServiceLocal;

/**
 * The Class AccountExchangeController.
 * @author Roman_Konovalov
 */
@Named
@ViewAccessScoped
@DeclareRoles({ "admin" })
public class AccountTopupController implements Serializable {

	/**
	 * Default serial version id.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Person id.
	 */
	@NotNull
	private Long personId;

	/**
	 * Account id.
	 */
	@NotNull
	private Long accountId;

	/**
	 * Accounts.
	 */
	private List<Account> accounts = Collections.emptyList();

	/**
	 * Amount.
	 */
	private Long amount;

	/**
	 * Account Service.
	 */
	@Inject
	private AccountServiceLocal accountService;

	/**
	 * @return the personId
	 */
	public Long getPersonId() {
		return personId;
	}

	/**
	 * @param personId the personId to set
	 */
	public void setPersonId(final Long personId) {
		this.personId = personId;
	}

	/**
	 * @return the accountId
	 */
	public Long getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(final Long accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the accounts
	 */
	public List<Account> getAccounts() {
		return accounts;
	}

	/**
	 * @param accounts
	 *            the accounts to set
	 */
	public void setAccounts(final List<Account> accounts) {
		this.accounts = accounts;
	}

	/**
	 * @return the amount
	 */
	public Long getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(final Long amount) {
		this.amount = amount;
	}

	/**
	 * Person Changed Listener.
	 */
	public void personChangedListener() {
		accounts = accountService.getAccountsForPerson(accountService.getPersonById(personId));
	}

	/**
	 * Withdraw Account.
	 * @return actions page
	 */
	public String topup() {
		accountService.topUp(accountId, amount);
		return "/pages/actions?faces-redirect=true";
	}
}
