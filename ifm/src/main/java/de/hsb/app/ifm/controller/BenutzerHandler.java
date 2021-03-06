package de.hsb.app.ifm.controller;

import java.io.Serializable;
import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

import de.hsb.app.ifm.model.Benutzer;
import de.hsb.app.ifm.model.Benutzer.Rolle;

@Named
@SessionScoped
public class BenutzerHandler implements Serializable {

	private static final long serialVersionUID = 1L;
	private static boolean test = true;
	@PersistenceContext(name = "ifm-persistence-unit")
	private EntityManager em;

	@Resource
	private UserTransaction utx;

	public DataModel<Benutzer> getBenutzer() {
		return benutzer;
	}

	private String adminEingabe;
	private String adminPasswort = "admin";

	public String getAdminEingabe() {
		return adminEingabe;
	}

	public void setAdminEingabe(String adminEingabe) {
		this.adminEingabe = adminEingabe;
	}

	private DataModel<Benutzer> benutzer;
	private Benutzer merkeBenutzer = new Benutzer();

	public Benutzer getMerkeBenutzer() {
		return merkeBenutzer;
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public UserTransaction getUtx() {
		return utx;
	}

	public void setUtx(UserTransaction utx) {
		this.utx = utx;
	}

	public void setMerkeBenutzer(Benutzer merkeBenutzer) {
		this.merkeBenutzer = merkeBenutzer;
	}

	public void setBenutzer(DataModel<Benutzer> benutzer) {
		this.benutzer = benutzer;
	}

	@PostConstruct
	public void init() {
		try {
			utx.begin();
		} catch (NotSupportedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SystemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (test) {
			Benutzer tmp = new Benutzer("Hugo", "1234");
			tmp.setRolle(Rolle.ADMIN);
			em.persist(tmp);
			test = false;
		}
		benutzer = new ListDataModel<>();
		benutzer.setWrappedData(em.createNamedQuery("SelectBenutzer").getResultList());

		try {
			utx.commit();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicMixedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/*
	 * Diese Methode wird bei Registrierung aufgerufen. 
	 * Dabei pr�fen wir zun�chst, ob der gew�hlte Name bereits vergeben ist.
	 * Anschlie�end �berpr�fen wir das Adminpasswort, falls dort etwas eingetragen wurde.
	 * Falls alles stimmt werden die Daten gespeichert und man kommt auf die Indexseite.
	 * Sollte das Adminpasswort falsch sein oder der Nutzername bereits vergben, bleibt man auf registrieren.xhtnml.
	 * 
	 * */
	@Transactional
	public String speichern() {
		boolean available = true;
		String username = merkeBenutzer.getUsername();
		if (benutzer != null) {
			for (Iterator<Benutzer> it = benutzer.iterator(); it.hasNext();) {
				Benutzer nutzer = it.next();
				if (username.equals(nutzer.getUsername())) {
					available = false;
				}
			}
		}
		if (!(adminEingabe.equals(""))) {
			if (adminEingabe.equals(adminPasswort)) {
				merkeBenutzer.setRolle(Rolle.ADMIN);
			} else {
				available = false;
			}
		} else {
			merkeBenutzer.setRolle(Rolle.NUTZER);
		}
		if (available) {
			merkeBenutzer = em.merge(merkeBenutzer);
			em.persist(merkeBenutzer);
			benutzer.setWrappedData(em.createNamedQuery("SelectBenutzer").getResultList());
			return "login";
		} else {
			return "registrieren";
		}
	}

	public String neu(Benutzer b) {
		merkeBenutzer = new Benutzer();
		return "registrieren";
	}

}
