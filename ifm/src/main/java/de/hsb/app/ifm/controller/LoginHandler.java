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
	
	/*
	 * Überprüft beim Login, ob die Daten korrekt sind.
	 * @param DataModel<Benutzer> benutzer In dem DataModel sind alle Benutzer gespeichert. Daraus vergleichen wir die Zugangsadten. 
	 * @return String Gibt die Seite an, auf die man nach dem Login umgeleitet wird.
	 * Man wird auf die Index umgeleitet, wenn die Daten korrekt sind. 
	 * Falls man ungültige Daten, eingegeben hat bleibt man auf der Loginseite.
	 * */
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
					session.setAttribute("username", username);
					session.setAttribute("userID", nutzer.getId());
				}
			}
		}
		if (loggedIn) {
			return "index";
		} else {
			return "login";
		}
	}
	
	/*
	 * Zersört die aktuelle Session.
	 * @return String Gibt die Seite an auf, die man nach dem Logout umgeleitet wird. 
	 * Bei uns ist dies die Inedxseite.
	 * */
	public String logout() {
		try {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			if (session != null) {
				session.invalidate();
			} else {
				System.out.println("Fehler beim Logout");
			}
		}catch (Exception e) {
			System.out.println("Bereits ausgeloggt");
		}
		return "index?faces-redirect=true";
	}

	/*Die Methode überprüft ob ein Nutzer eingeloggt ist. 
	 *@param checkLogin Der Parameter gibt an, ob die Seite erreichbar sein soll, wenn man eingeloggt oder nicht eingeloggt ist.
	 *Wenn checkLogin true ist, ist die Seite nur erreichbar, wenn man eingeloggt. 
	 *Wenn checkLogin false ist, ist die Seite nur erreichbar, wenn man nicht eingeloggt ist. 
	 *
	 *Falls man die Seite nicht erreichen soll, wird man auf die Index umgeleitet. 
	 * */
	public void checkLoggedIn(boolean checkLogin) {
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

	/*
	 * Prüft, ob man eingeloggt ist. 
	 * @return boolean Die Methode liefert einen boolean zurück, der true ist, wenn man eingeloggt ist. 
	 * Der Boolean ist false, wenn man nicht eingeloggt ist.
	 * 
	 * */
	public boolean checkLogin() {
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