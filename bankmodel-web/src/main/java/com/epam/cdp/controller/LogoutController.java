package com.epam.cdp.controller;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * The Class LogoutController.
 * @author Roman_Konovalov
 */
@Model
public class LogoutController {

	/**
	 * Logout user.
	 * @return actions page
	 */
	public String logout() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return "actions?faces-redirect=true";
	}

}
