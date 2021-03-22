package de.hsb.app.ifm.controller;

import java.util.Iterator;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.annotations.NamedQuery;
import de.hsb.app.ifm.model.Benutzer;

@Table(name = "Benutzer")
@NamedQuery(name = "checkBenutzer", query = "Select b from Benutzer b")
@RequestScoped
public class LoginHandler {

	@PersistenceContext(name = "ifm-persistence-unit")
	private EntityManager em;
	private boolean loggedIn = false;

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Transactional
	public String login(DataModel<Benutzer> benutzer) {
		if (benutzer != null) {
			for (Iterator<Benutzer> it = benutzer.iterator(); it.hasNext();) {
				Benutzer nutzer = it.next();
				if (username.equals(nutzer.getUsername()) && password.equals(nutzer.getPassword())) {
					loggedIn = true;
					FacesContext fc = FacesContext.getCurrentInstance();
					HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
					session.setAttribute("id", session.getId());
				}
			}
		}
		if (loggedIn) {
			return "index";
		} else {
			return "login";
		}
	}

	public String logout() {
		System.out.println("Hallo von Logout");
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		if (session != null) {
			session.invalidate();
		} else {
			System.out.println("Fehler beim Logout");
		}
		return "index";
	}

	public void checkLoggedIn(boolean checkLogin) {
		System.out.println("checkLogin");
		System.out.println(loggedIn);
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
		if (checkLogin) {
			if (session == null || session.getAttribute("id") == null) {
				nav.performNavigation("index");
			}
		} else {
			if (session != null && session.getAttribute("id") != null) {
				if (session.getAttribute("id").equals(session.getId())) {
					nav.performNavigation("index");
				}
			}

		}
	}

	public boolean checkLogin() {
		System.out.println("checkLogin");
		System.out.println(loggedIn);
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
		if (session != null && session.getAttribute("id") != null) {
			if (session.getAttribute("id").equals(session.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}