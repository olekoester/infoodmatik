package de.hsb.app.ifm.controller;

import java.io.Serializable;

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


import de.hsb.app.ifm.model.Rezept;

@Named
@SessionScoped

public class RezeptHandler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@PersistenceContext(name = "ifm-persistence-unit")
	private EntityManager em;
	@Resource
	private UserTransaction utx;

	private DataModel<Rezept> rezept;
	private Rezept merkeRezept = new Rezept ();

	@PostConstruct
	public void init() {
		try {
			utx.begin();
		} catch (NotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		em.persist(new Rezept("Müslii", "1x Milch 1x Müsli 1.Schritt Fügen Sie das Müsli 2.Schritt Milch hinzufügen 3.Schritt Anschließend alles in eine Schüssel geben 4.Schritt Guten Appetit ", "Müsli, Vegetarisch, Milch, Bio, Vegan, lowCarb"));
		System.out.println("hallo von em persist");
		rezept = new ListDataModel<> ();
		rezept.setWrappedData(em.createNamedQuery("SelectRezept").getResultList());
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
		merkeRezept = em.merge(merkeRezept);
		em.persist(merkeRezept);
		rezept.setWrappedData(em.createNamedQuery("SelectRezept").getResultList());
		return "index";
	}
	
	public String neu(Rezept r) {
		System.out.println("Methode neu() von RezeptHandler");
		merkeRezept = new Rezept();
		return "index";
		
	}
	
	
	public DataModel<Rezept> getRezept() {
		return rezept;
	}
	public void setRezept(DataModel<Rezept> rezept) {
		this.rezept = rezept;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Rezept getMerkeRezept() {
		return merkeRezept;
	}
	public void setMerkeRezept(Rezept merkeRezept) {
		this.merkeRezept = merkeRezept;
	}

	
}



