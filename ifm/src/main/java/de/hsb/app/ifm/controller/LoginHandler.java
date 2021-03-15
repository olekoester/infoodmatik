package de.hsb.app.ifm.controller;



import java.lang.module.Configuration;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.List;

import javax.enterprise.context.RequestScoped;
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


@Table(name="Benutzer")
@NamedQuery(name="checkBenutzer",query="Select b from Benutzer b")
@RequestScoped
public class LoginHandler {
	
	@PersistenceContext(name="ifm-persistence-unit")
	private EntityManager em;
	
	
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
	public boolean login(String username, String password) {
	//	Query checkuser = em.createQuery("Select username, password from Benutzer");
	//	String dbUsername, dbPassword;
		boolean login = false;
		
		List <?> list =  em.createNativeQuery("Select b from Benutzer b where b.username="+ username +" and b.password="+ password).
				setParameter("username", username).
				setParameter("password", password).
				getResultList();
				//	Query query = em.createNativeQuery("Select b from Benutzer b where b.username="+ username +" and b.password="+ password);
		
	
		
	//	List<?> list = query.getResultList();
		
		
		
		if (list.size() == 1) {
			login = true;
		} else if (list.size() == 0) {
			login = false;
		} 
		
	//	return (boolean) query.getSingleResult();
		System.out.println(1);
		System.out.println(list); 
		System.out.println(login);
		return login;
		
	}



	public String logout() {
		return "login";
	}

	public String checkLoggedIn() {
		return "";
	}


}















//list.forEach( -> Sysytem.out.println(.name));
// Für den LoginHandler bzw. login brauche ich definitiv em / createNamedQuery oder createQuery / getSingleResult und unter umständen Resultlist