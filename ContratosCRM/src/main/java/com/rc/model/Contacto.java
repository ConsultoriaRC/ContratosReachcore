package com.rc.model;

import java.io.Serializable;

public class Contacto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String id;
	private String email;
	
	public Contacto() {
		
	}
	
	public Contacto(String name, String id, String email) {
		this.setName(name);
		this.id = id;
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public String getId() {
		return id;
	}
	public String getEmail() {
		return email;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
