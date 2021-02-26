package de.hsb.app.ifm.model;

import java.io.Serializable;
import java.util.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;


import de.hsb.app.ifm.model.Benutzer;


@NamedQuery(name="SelectBenutzer", query="Select b from Benutzer b")
@Entity
public class Benutzer implements Serializable {
	
	
		/**
	 * 
	 */
	private static final long serialVersionUID = 6497311467353500639L;
		@Id
		@GeneratedValue
		private UUID id;
		private String username;
		private String password;
		
		
		public Benutzer(String username, String password) {
			super();
			this.username = username;
			this.password = password;
		
		}
		
		public UUID getId() {
			return id;
		}

		public void setId(UUID id) {
			this.id = id;
		}

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

		public Benutzer() {
			
		}
}
