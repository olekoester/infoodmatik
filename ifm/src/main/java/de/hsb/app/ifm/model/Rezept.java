package de.hsb.app.ifm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.enterprise.context.SessionScoped;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

@NamedQuery(name="SelectRezept", query="Select r from Rezept r")
@NamedQuery(name="SelectOneRezept", query=" Select r from Rezept r where r.rid=: rid")
@NamedQuery(name="SucheRezept", query=" Select r from Rezept r where charindex(UPPER(:name), UPPER(r.name))> 0")
@NamedQuery(name="FindOwnRecipes", query="Select r from Rezept r where r.benutzer.id= :benutzer_id")
@Entity

@SessionScoped
public class Rezept implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String beschreibung;
	private String zutaten;
	private String tags;
	private ArrayList<UUID> positiv = new ArrayList<UUID>() ;
	
	
	@Id
	@GeneratedValue
	private UUID rid;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name =  "benutzer_id")
	private Benutzer benutzer;
	
	public Benutzer getBenutzer() {
		return benutzer;
	}

	public void setBenutzer(Benutzer benutzer) {
		this.benutzer = benutzer;
	}

	public Rezept() {

	}

	public Rezept(String name, String zutaten, String beschreibung,  String tags) {
		super();
		this.name=name;
		this.zutaten=zutaten;
		this.beschreibung=beschreibung;
		this.tags=tags;


	}

	public String getZutaten() {
		
		if(zutaten!=null) {

			String tmpZutaten=zutaten.replaceAll("\n", "<br/>");
			return tmpZutaten;
		}
		return zutaten;
	}

	public void setZutaten(String zutaten) {
		this.zutaten = zutaten;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBeschreibung() {

		if(beschreibung!=null) {

			String tmpBeschreibung=beschreibung.replaceAll("\n", "<br/>");
			return tmpBeschreibung;

		}
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
	public UUID getRid() {
		return rid;
	}

	public void setRid(UUID rid) {
		this.rid = rid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ArrayList<UUID> getPositiv() {
		return positiv;
	}

	public void setPositiv(UUID nutzer) {
		positiv.add(nutzer);
	}
}
