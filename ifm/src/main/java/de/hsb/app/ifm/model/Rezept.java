package de.hsb.app.ifm.model;

import java.io.Serializable;
import java.util.UUID;

import javax.enterprise.context.SessionScoped;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@NamedQuery(name="SelectRezept", query="Select r from Rezept r")
@NamedQuery(name="SelectOneRezept", query=" Select r from Rezept r where r.rid=: rid")
@NamedQuery(name="SelectRezept2", query=" Select r from Rezept r where r.name= 'Pudding'")
//@NamedQuery(name="SucheRezept")
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
	@Id
	@GeneratedValue
	private UUID rid;
	public Rezept() {
		
	}
	
	public Rezept(String name, String zutaten, String beschreibung,  String tags) {
		super();
		this.name=name;
		this.beschreibung=beschreibung;
		this.tags=tags;
		this.zutaten=zutaten;
		
	}

	public String getZutaten() {
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
}
