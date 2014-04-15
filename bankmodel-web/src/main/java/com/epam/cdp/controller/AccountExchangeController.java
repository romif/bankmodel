package com.epam.cdp.controller;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import com.epam.cdp.bankmodel.service.AccountServiceLocal;

/**
 * The Class AccountExchangeController.
 * @author Roman_Konovalov
 */
@Model
public class AccountExchangeController {

	/**
	 * FromAccount.
	 */
	private Long fromAccount;

	/**
	 * ToAccount.
	 */
	private Long toAccount;

	/**
	 * Amount.
	 */
	private Long amount;

	/**
	 * AccountService.
	 */
	@Inject
	private AccountServiceLocal accountService;

	/**
	 * @return the fromAccount
	 */
	public Long getFromAccount() {
		return fromAccount;
	}

	/**
	 * @param fromAccount
	 *            the fromAccount to set
	 */
	public void setFromAccount(final Long fromAccount) {
		this.fromAccount = fromAccount;
	}

	/**
	 * @return the toAccount
	 */
	public Long getToAccount() {
		return toAccount;
	}

	/**
	 * @param toAccount
	 *            the toAccount to set
	 */
	public void setToAccount(final Long toAccount) {
		this.toAccount = toAccount;
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
	 * Exchange money.
	 * @return actions page
	 */
	public String exchange() {
		accountService.exchange(fromAccount, toAccount, amount);
		return "actions?faces-redirect=true";
	}

}
