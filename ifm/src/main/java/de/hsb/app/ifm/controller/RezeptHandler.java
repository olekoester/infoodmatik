package de.hsb.app.ifm.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

import de.hsb.app.ifm.model.Benutzer;
import de.hsb.app.ifm.model.Benutzer.Rolle;
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
	private static boolean firstExecute = true;
	private Rezept merkeRezept = new Rezept();

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
		if (firstExecute) {
			em.persist(new Rezept("M�slii ", "1x Milch \n 1x M�sli",
					" 1.Schritt F�gen Sie das M�sli  \n 2.Schritt Milch hinzuf�gen \n 3.Schritt Anschlie�end alles in eine Sch�ssel geben \n 4.Schritt Guten Appetit ",
					"M�sli, Vegetarisch, Milch, Bio, Vegan, lowCarb"));
			em.persist(new Rezept("Pudding", "1x Puddingmischung \n 1x Milch \n 1x Topf",
					"1.Schritt Milch kochen \n 2. Schritt Puddingmischung dazu geben \n 3.Schritt Essen",
					"Milch, Pudding, Vegetarisch, yay"));
			em.persist(new Rezept("Kr�melEistee", "1x Wasser \n 1x Eisteekr�mel \n 1x Wasserkocher",
					"1.Schritt Wasser in Wasserkocher kochen \n 2. Schritt Kr�meltee in Tasse \n 3.Schritt Kochendes Wasser in Tasse auf Kr�meltee",
					"Wasser, Tee, Kr�mel, lowCarb"));
			em.persist(new Rezept("Toast", "1x Wei�brot \n 1x Toaster",
					"1.Schritt Wei�brot aus Verpackung \n 2. Schritt Wei�brot in Toaster \n 3.Schritt warten \n 4.Schritt Toast aus Toaster \n 5.Schritt Essen",
					"Toast, Wei�brot, Vegetarisch, Geister, Bio"));
			em.persist(new Rezept("Nix", "1x Nix", "1.Schritt Du machst eh nix du Faues St�ck >:C",
					"Vegan, nix, also wirklich, da ist nix, also Langsam werde ich Wild, HALLO DU BRAUCHST HIER NICH GUCKEN, SAMMA WAS IST MIT DIR?,"));
			em.persist(new Rezept("Bandnudeln mit frischem Spinat und Lachs", "600 g Bandnudeln \n 500g Blattspinat \n 1 Zwiebel \n 2 Zehen Knoblauch \n 1TL Gem�sebr�he \n 125 ml Wasser \n 1 Pkt. Lachs ger�ucherter \n 1 Becher \n 1 TL Speisest�rke \n 2 EL Raps�l \n 1 Prise Salz Pfeffer und Muskat", 
					"Die Nudeln nach Gebrauchsanweisung kochen - Achtung, frische Nudeln brauchen nur 2 - 3 Minuten (also aufs Timing achten)! \n Die Zwiebel in Ringe schneiden, in eine Pfanne mit hohem Rand und Deckel mit dem �l geben und bei kleiner bis mittlerer Hitze glasig d�nsten (nicht braten!). Gem�sebr�he mit Wasser mischen und dazu gie�en (alternativ geht auch Wei�wein statt Br�he). Den Knoblauch sch�len, in m�glichst kleine St�ckchen schneiden und in die Pfanne geben. Nun den Spinat dazugeben. Evtl. geht das nur nach und nach, er f�llt aber schnell in sich zusammen, so dass nachgelegt werden kann, falls die Pfanne nicht gro� genug ist. Den R�ucherlachs in St�cke schneiden und dazugeben, sobald der Spinat komplett in sich zusammen gefallen ist. Alternativ zum R�ucherlachs geht auch frischer Lachs, der auf die gleiche Weise einfach gew�rfelt und noch roh dazugegeben werden kann. \n Etwas Fl�ssigkeit abnehmen und in einer Tasse mit der St�rke mischen, bis sie sich l�st. Dieses Gemisch wieder in die Pfanne geben, ebenso den Becher Cremefine bzw. Schmand. \n Mit Pfeffer, Salz und (am besten frisch hinein geriebener) Muskatnuss w�rzen, die Nudel abgie�en und untermischen und servieren. \n","Fisch"));
			firstExecute = false;
		}
		rezept = new ListDataModel<>();
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
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		String username = (String) session.getAttribute("username");
		Query query = em.createNamedQuery("SearchByName");
		query.setParameter("username", username);
		Benutzer nutzer = (Benutzer) query.getSingleResult();
		merkeRezept.setBenutzer(nutzer);
		merkeRezept = em.merge(merkeRezept);
		em.persist(merkeRezept);
		rezept.setWrappedData(em.createNamedQuery("SelectRezept").getResultList());
		return "index";
	}

	public void neu() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		if (session.getAttribute("rid") != null) {
			String rid = (String) session.getAttribute("rid");
			UUID id = UUID.fromString(rid);
			merkeRezept = em.find(Rezept.class, id);
			session.removeAttribute("rid");
		} else {
			merkeRezept = new Rezept();
		}
	}

	@Transactional
	public List<Rezept> getOneRezept(String rid) {
		try {
			UUID rid2 = UUID.fromString(rid);
			Query query = em.createNamedQuery("SelectOneRezept");
			query.setParameter("rid", rid2);
			return query.getResultList();
		} catch (Exception e) {
			List<Rezept> fehler = new ArrayList<Rezept>(Arrays.asList(
					new Rezept("", "", "HUCH da ist was schief gelaufen \n bittekehre zum Rezept zur�ck ", "")));
			return fehler;
		}
	}

	public List<Rezept> sucheRezept(String SuchAnfrage,boolean tags) {
		Query query;
		System.out.println(tags+"    "+SuchAnfrage);
		if(tags) {
			query = em.createNamedQuery("SucheTags");
			query.setParameter("tags", SuchAnfrage);
		}else {
			query = em.createNamedQuery("SucheRezept");
			query.setParameter("name", SuchAnfrage);
		}
		
		
		List<Rezept> erg = query.getResultList();
		if (erg.isEmpty()) {
			List<Rezept> fehler = new ArrayList<Rezept>(
					Arrays.asList(new Rezept("Die Suche ergab leider keinen Treffer", "", "", "")));
			return fehler;
		}
		return erg;

	}
	@Transactional
	public String like(String rid,boolean liked) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		
		UUID nutzerID = (UUID) session.getAttribute("userID");
		UUID rid2 = UUID.fromString(rid);
		
		merkeRezept = em.find(Rezept.class, rid2);
		merkeRezept.setLike(nutzerID,liked);
		merkeRezept = em.merge(merkeRezept);

		em.persist(merkeRezept);
		rezept.setWrappedData(em.createNamedQuery("SelectRezept").getResultList());
		
		return "rezepte?rid="+rid;
	}

	public List<Rezept> findeEigeneRezepte() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		String username = (String) session.getAttribute("username");
		Query query = em.createNamedQuery("SearchByName");
		query.setParameter("username", username);
		Benutzer nutzer = (Benutzer) query.getSingleResult();
		Rolle rolle = nutzer.getRolle();
		if (rolle == Rolle.NUTZER) {
			UUID id = nutzer.getId();
			query = em.createNamedQuery("FindOwnRecipes");
			query.setParameter("benutzer_id", id);
			return query.getResultList();
		} else {
			query = em.createNamedQuery("SelectRezept");
			return query.getResultList();
		}
	}

	@Transactional
	public String delete(UUID id) {
		merkeRezept = em.find(Rezept.class, id);
		merkeRezept = em.merge(merkeRezept);
		em.remove(merkeRezept);
		rezept.setWrappedData(em.createNamedQuery("SelectRezept").getResultList());
		return "eigenerezepte";
	}

	@Transactional
	public String edit(UUID id) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("rid", id.toString());
		return "rezepterstellen";
	}

	public String backToIndex() {
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
