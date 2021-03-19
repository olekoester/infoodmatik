package de.hsb.app.ifm.controller;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
	private List<Rezept> aktuellesRezept;
	
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
		em.persist(new Rezept("Pudding", "1x Puddingmischung 1x Milch 1x Topf 1.Schritt Milch kochen 2. Schritt Puddingmischung dazu geben 3.Schritt Essen", "Milch, Pudding, Vegetarisch, yay" ));	
		em.persist(new Rezept("KrümelEistee", "1x Wasser 1x Eisteekrümel 1x Wasserkocher 1.Schritt Wasser in Wasserkocher kochen 2. Schritt Krümeltee in Tasse 3.Schritt Kochendes Wasser in Tasse auf Krümeltee", "Wasser, Tee, Krümel, lowCarb" ));	
		em.persist(new Rezept("Toast", "1x Weißbrot 1x Toaster 1.Schritt Weißbrot aus Verpackung 2. Schritt Weißbrot in Toaster 3.Schritt warten 4.Schritt Toast aus Toaster 5.Schritt Essen", "Toast, Weißbrot, Vegetarisch, Geister, Bio" ));
		em.persist(new Rezept("Nix", "1x Nix 1.Schritt Du machst eh nix du Faues Stück >:C", "Vegan, nix, also wirklich, da ist nix, also Langsam werde ich Wild, HALLO DU BRAUCHST HIER NICH GUCKEN, SAMMA WAS IST MIT DIR?," ));	
		
		//System.out.println(rezept.getRowData());
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
	
	@Transactional
	public List<Rezept> getOneRezept (String rid) {
		System.out.println(rid);
		UUID rid2=UUID.fromString(rid) ;
		Query query= em.createNamedQuery("SelectOneRezept");
		query.setParameter("rid", rid2);
		return query.getResultList();
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



