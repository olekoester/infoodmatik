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
	private Benutzer merkeBenutzer = new Benutzer ();
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
		em.persist(new Benutzer("Hugo", "1234"));
		benutzer = new ListDataModel<> ();
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
	
	@Transactional
	public String speichern() {
		System.out.println(adminEingabe);
		boolean available = true;
		String username = merkeBenutzer.getUsername();
		String password = merkeBenutzer.getPassword();
		System.out.println(username + "		" + password);
		System.out.println(benutzer);
		if(benutzer != null) {
			for(Iterator <Benutzer> it = benutzer.iterator(); it.hasNext();) {
				Benutzer nutzer = it.next();
				if(username.equals(nutzer.getUsername())) {
					available = false;
				}
			}
		}
		if(!(adminEingabe.equals(""))) {
			if(adminEingabe.equals(adminPasswort)) {
				merkeBenutzer.setRolle(Rolle.ADMIN);
			}else {
				available = false;
			}
		}else {
			merkeBenutzer.setRolle(Rolle.NUTZER);
		}
		System.out.println(merkeBenutzer.getRolle());
		System.out.println(available);
		if (available) {
			merkeBenutzer = em.merge(merkeBenutzer);
			em.persist(merkeBenutzer);
			benutzer.setWrappedData(em.createNamedQuery("SelectBenutzer").getResultList());
			return "index";
		} else {
			return "registrieren";
		}
	}
	
	
	public String neu(Benutzer b) {
		System.out.println("Ich bin die Methode neu() und wurde gerade aufgerufen");
		merkeBenutzer = new Benutzer();
		return "registrieren";
	}
		
}
