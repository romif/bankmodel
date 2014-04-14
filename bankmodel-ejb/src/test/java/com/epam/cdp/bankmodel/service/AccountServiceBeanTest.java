package com.epam.cdp.bankmodel.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;

import com.epam.cdp.bankmodel.exception.InsufficientMoneyException;
import com.epam.cdp.bankmodel.exception.InvalidInputParametersException;
import com.epam.cdp.bankmodel.exception.NoSuchObjectException;
import com.epam.cdp.bankmodel.model.Account;
import com.epam.cdp.bankmodel.model.Person;

/**
 * The Class AccountServiceBeanTest.
 * @author Roman_Konovalov
 */
@UsingDataSet("bank.yml")
public class AccountServiceBeanTest extends AbstractArquillianDbUnitTest {

	/**
	 * Service.
	 */
	@Inject
	private AccountServiceLocal service;

	/**
	 * testCreateAccount.
	 */
	@Test
	public void testCreateAccount() {
		Person person = getPerson();
		Account account = getAccount();
		service.createPerson(person, TEST_STRING);
		account.setPerson(person);
		service.createAccount(account);
		assertNotNull(service.getAccountById(account.getId()));
	}

	/**
	 * testCreateAccountNullValue.
	 */
	@Test
	public void testCreateAccountNullValue() {
		thrown.expectCause(new CauseMatcher(InvalidInputParametersException.class));
		service.createAccount(null);
	}

	/**
	 * testCreatePerson.
	 */
	@Test
	public void testCreatePerson() {
		Person person = getPerson();
		service.createPerson(person, TEST_STRING);
		assertNotNull(service.getPersonById(person.getId()));
	}

	/**
	 * testExchange.
	 */
	@Test
	public void testExchange() {
		service.exchange(1L, 2L, 10L);
		Account from = service.getAccountById(1L);
		Account to = service.getAccountById(2L);
		assertEquals(new Long(TEST_LONG - 10L), from.getAmount());
		Long expected = TEST_LONG + Math.round(10L * from.getCurrency().getRate() / to.getCurrency().getRate());
		assertEquals(expected, service.getAccountById(2L).getAmount());
	}

	/**
	 * testExchangeNegative.
	 */
	@Test
	public void testExchangeNegative() {
		thrown.expectCause(new CauseMatcher(InsufficientMoneyException.class));
		service.exchange(1L, 2L, new Long(TEST_LONG * 2));
		assertEquals(TEST_LONG, service.getAccountById(1L).getAmount());
		assertEquals(TEST_LONG, service.getAccountById(2L).getAmount());
	}

	/**
	 * testGetAccountById.
	 */
	@Test
	public void testGetAccountById() {
		Account account = service.getAccountById(1L);
		assertNotNull(account);
		testEntity(account, new String[] {"id"});
	}

	/**
	 * testGetAccountByIdNegative.
	 */
	@Test
	public void testGetAccountByIdNegative() {
		thrown.expectCause(new CauseMatcher(NoSuchObjectException.class));
		service.getAccountById(3L);
	}

	/**
	 * testGetAccountsForPerson.
	 */
	@Test
	public void testGetAccountsForPerson() {
		Person person = getPerson();
		person.setId(2L);
		List<Account> accounts = service.getAccountsForPerson(person);
		assertEquals(2, accounts.size());
	}

	/**
	 * testGetAllPersons.
	 */
	@Test
	public void testGetAllPersons() {
		List<Person> persons = service.getAllPersons();
		assertEquals(2, persons.size());
	}

	/**
	 * testGetPersonById.
	 */
	@Test
	public void testGetPersonById() {
		Person person = service.getPersonById(2L);
		assertNotNull(person);
		testEntity(person, new String[] {"id"});
		List<Account> accounts = person.getAccounts();
		assertEquals(2, accounts.size());
		for (Account account : accounts) {
			testEntity(account, new String[] {"id"});
		}
	}

	/**
	 * testGetPersonByIdNegative.
	 */
	@Test
	public void testGetPersonByIdNegative() {
		thrown.expectCause(new CauseMatcher(NoSuchObjectException.class));
		service.getPersonById(3L);
	}

	/**
	 * testGetPersonByName.
	 */
	@Test
	public void testGetPersonByName() {
		Person person = service.getPersonByName(TEST_STRING);
		assertNotNull(person);
		testEntity(person, new String[] {"id"});
	}

	/**
	 * testGetPersonByNameNegativ.
	 */
	@Test
	public void testGetPersonByNameNegative() {
		thrown.expectCause(new CauseMatcher(NoSuchObjectException.class));
		service.getPersonByName(FAIL_TEST_STRING);
	}

	/**
	 * testTopUp.
	 */
	@Test
	public void testTopUp() {
		service.topUp(2L, TEST_LONG);
		assertEquals(new Long(2 * TEST_LONG), service.getAccountById(2L).getAmount());
	}

	/**
	 * testWithdraw.
	 */
	@Test
	public void testWithdraw() {
		service.withdraw(2L, 10L);
		assertEquals(new Long(TEST_LONG - 10L), service.getAccountById(2L).getAmount());
	}

	/**
	 * testWithdrawNegative.
	 */
	@Test
	public void testWithdrawNegative() {
		thrown.expectCause(new CauseMatcher(InsufficientMoneyException.class));
		service.withdraw(2L, 2 * TEST_LONG);
		assertEquals(TEST_LONG, service.getAccountById(2L).getAmount());
	}

	/**
	 * Gets Person.
	 * @return Person
	 */
	private Person getPerson() {
		Person person = getFilledInstance(Person.class);
		person.setId(null);
		person.setEmail(TEST_EMAIL_STRING);
		return person;
	}

	/**
	 * Gets Account.
	 * @return Account
	 */
	private Account getAccount() {
		Account account = getFilledInstance(Account.class);
		account.setId(null);
		return account;
	}

}
