package com.epam.cdp.bankmodel.service;

import java.util.List;

import javax.ejb.Local;

import com.epam.cdp.bankmodel.model.Account;
import com.epam.cdp.bankmodel.model.Person;

/**
 * The Interface AccountServiceLocal.
 * @author Roman_Konovalov
 */
@Local
public interface AccountServiceLocal {

	/**
	 * Creates Account.
	 * @param account
	 *            Account
	 */
	void createAccount(Account account);

	/**
	 * Creates Person.
	 * @param person
	 *            Person
	 * @param password
	 *            password
	 */
	void createPerson(Person person, String password);

	/**
	 * Exchanges money.
	 * @param fromAccountId
	 *            from Account Id
	 * @param toAccountId
	 *            to Account Id
	 * @param amount
	 *            amount
	 */
	void exchange(Long fromAccountId, Long toAccountId, Long amount);

	/**
	 * Gets Account By Id.
	 * @param id
	 *            account Id
	 * @return Account
	 */
	Account getAccountById(Long id);

	/**
	 * Gets Accounts.
	 * @param person
	 *            person
	 * @return Account list
	 */
	List<Account> getAccountsForPerson(Person person);

	/**
	 * Gets All Persons.
	 * @return Person list
	 */
	List<Person> getAllPersons();

	/**
	 * Gets Person by id.
	 * @param id
	 *            id
	 * @return Person
	 */
	Person getPersonById(Long id);

	/**
	 * Gets Person by name.
	 * @param name
	 *            name
	 * @return Person
	 */
	Person getPersonByName(String name);

	/**
	 * TopUp account.
	 * @param accountId
	 *            account Id
	 * @param amountTopUp
	 *            amount to topUp
	 */
	void topUp(Long accountId, Long amountTopUp);

	/**
	 * Withdraw account.
	 * @param accountId
	 *            account Id
	 * @param amountWithdraw
	 *            amount to withdraw
	 */
	void withdraw(Long accountId, Long amountWithdraw);

}
