package com.epam.cdp.bankmodel.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.cdp.bankmodel.enums.Currency;
import com.epam.cdp.bankmodel.exception.InsufficientMoneyException;
import com.epam.cdp.bankmodel.exception.NoSuchObjectException;
import com.epam.cdp.bankmodel.interceptors.CheckingInputParametersInterceptor;
import com.epam.cdp.bankmodel.model.Account;
import com.epam.cdp.bankmodel.model.Person;

/**
 * The Class AccountServiceBean.
 * @author Roman_Konovalov
 *
 */
@Stateless
@LocalBean
@Interceptors(CheckingInputParametersInterceptor.class)
public class AccountServiceBean implements AccountServiceLocal {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(AccountServiceBean.class);

	/**
	 * Entity Manager.
	 */
	@PersistenceContext
	private EntityManager em;

	/**
	 * Default constructor.
	 */
	public AccountServiceBean() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createAccount(final Account account) {
		LOG.debug("Creating new account");

		em.persist(account);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createPerson(final Person person, final String password) {
		LOG.debug("Createing new person");

		Query query = em.createNativeQuery("INSERT INTO users(name, pass) VALUES (?, ?)")
				.setParameter(1, person.getName()).setParameter(2, password);
		query.executeUpdate();
		query = em.createNativeQuery("INSERT INTO user_roles(user_name, role_name) VALUES (?, 'USER');").setParameter(
				1, person.getName());
		query.executeUpdate();
		em.persist(person);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void exchange(final Long fromAccountId, final Long toAccountId, final Long amount) {
		LOG.debug("Exchanging money form account with id {} to account with id {}", fromAccountId, toAccountId);

		final Currency senderCurrency = getAccountById(fromAccountId).getCurrency();
		final Currency recipientCurrency = getAccountById(toAccountId).getCurrency();
		withdraw(fromAccountId, amount);
		topUp(toAccountId, Math.round(amount * senderCurrency.getRate() / recipientCurrency.getRate()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Account getAccountById(final Long id) {
		Account account = em.find(Account.class, id);
		if (account == null) {
			throw new NoSuchObjectException("Account with id: " + id + "doesn't exist");
		}
		return account;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Account> getAccountsForPerson(final Person person) {
		final TypedQuery<Account> query = em.createQuery("from Account where person = :person", Account.class)
				.setParameter("person", person);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Person> getAllPersons() {
		final TypedQuery<Person> query = em.createQuery("from Person order by name", Person.class);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Person getPersonById(final Long id) {
		Person person = em.find(Person.class, id);
		if (person == null) {
			throw new NoSuchObjectException("Person with id: " + id + "doesn't exist");
		}
		return person;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Person getPersonByName(final String name) {
		final TypedQuery<Person> query = em.createQuery("from Person where name = :name", Person.class).setParameter(
				"name", name);
		Person person;
		try {
			person = query.getSingleResult();
		} catch (NoResultException e) {
			throw new NoSuchObjectException("Person with name: " + name + "doesn't exist");
		}
		return person;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void topUp(final Long accountId, final Long amountTopUp) {
		final Account account = getAccountById(accountId);
		final Long accountAmmount = account.getAmount();
		account.setAmount(accountAmmount + amountTopUp);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void withdraw(final Long accountId, final Long amountWithdraw) {
		final Account account = getAccountById(accountId);
		final Long accountAmmount = account.getAmount();
		if (amountWithdraw > accountAmmount) {
			throw new InsufficientMoneyException();
		}
		account.setAmount(accountAmmount - amountWithdraw);
	}

}
