package com.epam.cdp.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.hibernate.validator.constraints.NotEmpty;

import com.epam.cdp.bankmodel.model.Person;
import com.epam.cdp.bankmodel.service.AccountServiceLocal;

/**
 * The Class PersonController.
 * @author Roman_Konovalov
 *
 */
@Model
public class PersonController {

	/**
	 * newPerson.
	 */
	private Person newPerson;

	/**
	 * password.
	 */
	@NotEmpty
	private String password;

	/**
	 * accountService.
	 */
	@Inject
	private AccountServiceLocal accountService;

	/**
	 * Init new Person.
	 */
	@PostConstruct
	public void init() {
		newPerson = new Person();
	}

	/**
	 * @return the newPerson
	 */
	public Person getNewPerson() {
		return newPerson;
	}

	/**
	 * @param newPerson
	 *            the newPerson to set
	 */
	public void setNewPerson(final Person newPerson) {
		this.newPerson = newPerson;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * Register new Person.
	 * @return actions page
	 */
	public String register() {
		accountService.createPerson(newPerson, password);
		return "/pages/actions?faces-redirect=true";
	}

}
