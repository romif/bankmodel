package com.epam.cdp.controller;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import com.epam.cdp.bankmodel.service.AccountServiceLocal;

/**
 * The Class AccountWithdrawController.
 * @author Roman_Konovalov
 *
 */
@Model
public class AccountWithdrawController {

	/**
	 * Account.
	 */
	private Long account;

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
	 * @return the account
	 */
	public Long getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(final Long account) {
		this.account = account;
	}

	/**
	 * @return the amount
	 */
	public Long getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(final Long amount) {
		this.amount = amount;
	}

	/**
	 * Withdraw Account.
	 * @return actions page
	 */
	public String withdraw() {
		accountService.withdraw(account, amount);
		return "/pages/actions?faces-redirect=truee";
	}

}
