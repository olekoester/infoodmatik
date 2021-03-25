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
			em.persist(new Rezept("Müsli ", "200ml Milch \n 1x Müsli \n Früchte nach Wahl",
					" 1.Schritt Geben Sie das Müsli in eine Schüssel  \n 2.Schritt Milch hinzufügen \n 3.Schritt Anschließend die Früchte schneiden und hinzugeben \n 4.Schritt Genießen ",
					"Müsli, Vegetarisch, Milch, Bio, lowCarb"));
			em.persist(new Rezept("Pudding", "1x Puddingmischung \n 1x Milch \n 1x Topf",
					"1.Schritt Milch kochen \n 2. Schritt Puddingmischung dazu geben \n 3.Schritt Essen",
					"Milch, Pudding, Vegetarisch, yay"));
			em.persist(new Rezept("Hacksteaks", "500g Hackfleisch \n 1 Brötchen vom Vortag/ 1 Toast (in Milch aufweichen) \n 1x Knoblauchzehe \n 1x Ei \n etwas TK Petersilie \n 1x Zwiebel \n 2 TL Tomatenmark \n Salz und Pfeffer \n Feta-Käse \n Chili \n Cayennepfeffer",
					"Zwiebeln in Butter glasig braten. Pfanne ausstellen und Knoblauch dazu geben. Wenn Zwiebeln und\r\n"
					+ "Knoblauch abgekühlt sind, zur Hackmasse geben. Brötchen oder Toast in Milch einweichen. Vor\r\n"
					+ "dem Mischen ausdrücken und auseinander zupfen. ca. 150g pro Steak.",
					"Fleisch"));
			em.persist(new Rezept("Metaxasoße", "ca 80g Tomatenmark \n 2 mittelgroße Zwiebeln, klein gehackt \n 200ml Gemüse- oder Rinderbrühe \n  250ml passierte Tomaten \n 200g süße Sahne \n 200g Crème Fraiche oder Schmand \n"
					+ "1 Knoblauchzehe, klein gehackt \n  ca. 1TL Salz \n ca. 1TL Pfeffer aus der Mühle \n ca. 1TL Paprika rosenscharf \n ca. 4cl Metaxa \n etwas Pflanzenöl"
					, "1.Schritt: Tomatenmark mit Öl im Topf anbraten \n 2.Schritt: Zwiebeln dazu geben und weichkochen \n 3.Schritt: Brühe hinzufügen \n 4.Schritt: Soße reduzieren \n 5.Schritt: Sahne und Creme Fraiche hinzugeben, aufkochen \n 6.Schritt: Soße reduzieren \n 7.Schritt: Knoblauch hinzugeben \n 8.Schritt: Salz, Pfeffer und Paprika hinzugügen \n 9.Schritt: Metaxa hinzufügen",
					"Griechisch, Soße"));
			em.persist(new Rezept("Bandnudeln mit frischem Spinat und Lachs", "600 g Bandnudeln \n 500g Blattspinat \n 1 Zwiebel \n 2 Zehen Knoblauch \n 1TL Gemüsebrühe \n 125 ml Wasser \n 1 Pkt. Lachs geräucherter \n 1 Becher \n 1 TL Speisestärke \n 2 EL Rapsöl \n 1 Prise Salz Pfeffer und Muskat", 
					"Die Nudeln nach Gebrauchsanweisung kochen - Achtung, frische Nudeln brauchen nur 2 - 3 Minuten (also aufs Timing achten)! \n Die Zwiebel in Ringe schneiden, in eine Pfanne mit hohem Rand und Deckel mit dem Öl geben und bei kleiner bis mittlerer Hitze glasig dünsten (nicht braten!). Gemüsebrühe mit Wasser mischen und dazu gießen (alternativ geht auch Weißwein statt Brühe). Den Knoblauch schälen, in möglichst kleine Stückchen schneiden und in die Pfanne geben. Nun den Spinat dazugeben. Evtl. geht das nur nach und nach, er fällt aber schnell in sich zusammen, so dass nachgelegt werden kann, falls die Pfanne nicht groß genug ist. Den Räucherlachs in Stücke schneiden und dazugeben, sobald der Spinat komplett in sich zusammen gefallen ist. Alternativ zum Räucherlachs geht auch frischer Lachs, der auf die gleiche Weise einfach gewürfelt und noch roh dazugegeben werden kann. \n Etwas Flüssigkeit abnehmen und in einer Tasse mit der Stärke mischen, bis sie sich löst. Dieses Gemisch wieder in die Pfanne geben, ebenso den Becher Cremefine bzw. Schmand. \n Mit Pfeffer, Salz und (am besten frisch hinein geriebener) Muskatnuss würzen, die Nudel abgießen und untermischen und servieren. \n","Fisch"));
			em.persist(new Rezept("Hamburger mit Kartoffelwedges", "500g Hackfleisch \n 4x Burgerbrötchen \n 1kg Kartoffeln \n 1x Tomate \n etwas Salat \n 1x Gurke \n Ketchup", 
					"1.Schritt: Die Kartoffeln waschen und in Scheiben schneiden \n 2.Schritt: Olivenöl über die Kartoffeln streichen und mit Salz, Pfeffer und Oregano würzen \n 3.Schritt: Die Kartoffeln 40 Minuten bei 180°C backen \n 4.Schritt: Das Hackfleisch zu Patties formen. \n 5.Schritt: In der Zwischenzeit die Burgerpatties braten \n 6.Schritt: Den Burger nach Wahl belegen und mit den Kartoffeln genießen","Amerikanisch, Fleisch, Hauptgericht"));
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
					new Rezept("", "", "HUCH da ist was schief gelaufen \n bittekehre zum Rezept zurück ", "")));
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
