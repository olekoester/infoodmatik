package de.hsb.app.ifm.controller;

import java.lang.module.Configuration;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.h2.engine.Session;
import org.hibernate.SessionFactory;
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

//	private static Logger log = Logger.getLogger(LoginHandler.class);
	private String username;
	private String password;
	private ResultSet rs;

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
		// Query checkuser = em.createQuery("Select username, password from Benutzer");
		// String dbUsername, dbPassword;
		if(benutzer != null) {
			for(Iterator <Benutzer> it = benutzer.iterator(); it.hasNext();) {
				Benutzer nutzer = it.next();
				if(username.equals(nutzer.getUsername()) && password.equals(nutzer.getPassword())) {
					loggedIn = true;
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
		loggedIn = false;
		return "index";
	}

	public String checkLoggedIn() {
		System.out.println("Hallo von checkLogin");
		if(loggedIn) {
			System.out.println("Hallo von checkLogin: eingeloggt");
			return "registrieren";
		}
		return "index";
	}

}

//list.forEach( -> Sysytem.out.println(.name));
// Für den LoginHandler bzw. login brauche ich definitiv em / createNamedQuery oder createQuery / getSingleResult und unter umständen Resultlist