package de.hsb.app.ifm.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.New;
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
			em.persist(new Rezept("M�sli ", "200ml Milch \n 1x M�sli \n Fr�chte nach Wahl",
					" 1.Schritt Geben Sie das M�sli in eine Sch�ssel  \n 2.Schritt Milch hinzuf�gen \n 3.Schritt Anschlie�end die Fr�chte schneiden und hinzugeben \n 4.Schritt Genie�en ",
					"M�sli, Vegetarisch, Milch, Bio, lowCarb"));
			em.persist(new Rezept("Pudding", "1x Puddingmischung \n 1x Milch \n 1x Topf",
					"1.Schritt Milch kochen \n 2. Schritt Puddingmischung dazu geben \n 3.Schritt Essen",
					"Milch, Pudding, Vegetarisch, yay"));
			em.persist(new Rezept("Hacksteaks", "500g Hackfleisch \n 1 Br�tchen vom Vortag/ 1 Toast (in Milch aufweichen) \n 1x Knoblauchzehe \n 1x Ei \n etwas TK Petersilie \n 1x Zwiebel \n 2 TL Tomatenmark \n Salz und Pfeffer \n Feta-K�se \n Chili \n Cayennepfeffer",
					"Zwiebeln in Butter glasig braten. Pfanne ausstellen und Knoblauch dazu geben. Wenn Zwiebeln und\r\n"
					+ "Knoblauch abgek�hlt sind, zur Hackmasse geben. Br�tchen oder Toast in Milch einweichen. Vor\r\n"
					+ "dem Mischen ausdr�cken und auseinander zupfen. ca. 150g pro Steak.",
					"Fleisch"));
			em.persist(new Rezept("Metaxaso�e", "ca 80g Tomatenmark \n 2 mittelgro�e Zwiebeln, klein gehackt \n 200ml Gem�se- oder Rinderbr�he \n  250ml passierte Tomaten \n 200g s��e Sahne \n 200g Cr�me Fraiche oder Schmand \n"
					+ "1 Knoblauchzehe, klein gehackt \n  ca. 1TL Salz \n ca. 1TL Pfeffer aus der M�hle \n ca. 1TL Paprika rosenscharf \n ca. 4cl Metaxa \n etwas Pflanzen�l"
					, "1.Schritt: Tomatenmark mit �l im Topf anbraten \n 2.Schritt: Zwiebeln dazu geben und weichkochen \n 3.Schritt: Br�he hinzuf�gen \n 4.Schritt: So�e reduzieren \n 5.Schritt: Sahne und Creme Fraiche hinzugeben, aufkochen \n 6.Schritt: So�e reduzieren \n 7.Schritt: Knoblauch hinzugeben \n 8.Schritt: Salz, Pfeffer und Paprika hinzug�gen \n 9.Schritt: Metaxa hinzuf�gen",
					"Griechisch, So�e"));
			em.persist(new Rezept("Bandnudeln mit frischem Spinat und Lachs", "600 g Bandnudeln \n 500g Blattspinat \n 1 Zwiebel \n 2 Zehen Knoblauch \n 1TL Gem�sebr�he \n 125 ml Wasser \n 1 Pkt. Lachs ger�ucherter \n 1 Becher \n 1 TL Speisest�rke \n 2 EL Raps�l \n 1 Prise Salz Pfeffer und Muskat", 
					"Die Nudeln nach Gebrauchsanweisung kochen - Achtung, frische Nudeln brauchen nur 2 - 3 Minuten (also aufs Timing achten)! \n Die Zwiebel in Ringe schneiden, in eine Pfanne mit hohem Rand und Deckel mit dem �l geben und bei kleiner bis mittlerer Hitze glasig d�nsten (nicht braten!). Gem�sebr�he mit Wasser mischen und dazu gie�en (alternativ geht auch Wei�wein statt Br�he). Den Knoblauch sch�len, in m�glichst kleine St�ckchen schneiden und in die Pfanne geben. Nun den Spinat dazugeben. Evtl. geht das nur nach und nach, er f�llt aber schnell in sich zusammen, so dass nachgelegt werden kann, falls die Pfanne nicht gro� genug ist. Den R�ucherlachs in St�cke schneiden und dazugeben, sobald der Spinat komplett in sich zusammen gefallen ist. Alternativ zum R�ucherlachs geht auch frischer Lachs, der auf die gleiche Weise einfach gew�rfelt und noch roh dazugegeben werden kann. \n Etwas Fl�ssigkeit abnehmen und in einer Tasse mit der St�rke mischen, bis sie sich l�st. Dieses Gemisch wieder in die Pfanne geben, ebenso den Becher Cremefine bzw. Schmand. \n Mit Pfeffer, Salz und (am besten frisch hinein geriebener) Muskatnuss w�rzen, die Nudel abgie�en und untermischen und servieren. \n","Fisch"));
			em.persist(new Rezept("Hamburger mit Kartoffelwedges", "500g Hackfleisch \n 4x Burgerbr�tchen \n 1kg Kartoffeln \n 1x Tomate \n etwas Salat \n 1x Gurke \n Ketchup", 
					"1.Schritt: Die Kartoffeln waschen und in Scheiben schneiden \n 2.Schritt: Oliven�l �ber die Kartoffeln streichen und mit Salz, Pfeffer und Oregano w�rzen \n 3.Schritt: Die Kartoffeln 40 Minuten bei 180�C backen \n 4.Schritt: Das Hackfleisch zu Patties formen. \n 5.Schritt: In der Zwischenzeit die Burgerpatties braten \n 6.Schritt: Den Burger nach Wahl belegen und mit den Kartoffeln genie�en","Amerikanisch, Fleisch, Hauptgericht"));
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
