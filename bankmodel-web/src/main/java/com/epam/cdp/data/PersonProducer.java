package com.epam.cdp.data;

import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.epam.cdp.bankmodel.model.Person;
import com.epam.cdp.bankmodel.service.AccountServiceBean;

/**
 * The Class PersonProducer.
 * @author Roman_Konovalov
 *
 */
@RequestScoped
@DeclareRoles({ "admin" })
public class PersonProducer {

	/**
	 * AccountService.
	 */
	@Inject
	private AccountServiceBean accountService;

	/**
	 * Gets All Persons.
	 * @return Person list
	 */
	@Named
	@Produces
	public List<Person> getAllPersons() {
		return accountService.getAllPersons();
	}

}
