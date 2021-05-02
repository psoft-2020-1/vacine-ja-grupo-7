package com.ufcg.psoft.vacinaja.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Administrador {
	
	@Id
	private String login;
	
	public Administrador() {}
	
	public Administrador(String login) {
		this.login = login;
	}
	
	public String getLogin() {
		return this.login;
	}
	
	

}
