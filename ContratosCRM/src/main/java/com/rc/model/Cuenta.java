package com.rc.model;

import java.io.Serializable;

public class Cuenta implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String id;
	private String raz_nSocial;
	private String rfc;
	private Contacto contacto;
	private String ownerNm;
	private String ownerId;
	
	public Cuenta(String name, String id, String raz_nSocial, String rfc, Contacto contacto, String ownerNm, String ownerId) {
		this.setName(name);
		this.id = id;
		this.setRaz_nSocial(raz_nSocial);
		this.setRfc(rfc);
		this.contacto = contacto;
		this.ownerNm = ownerNm;
		this.ownerId = ownerId;
	}
	public String getName() {
		return name;
	}
	public String getId() {
		return id;
	}
	public String getRaz_nSocial() {
		return raz_nSocial;
	}
	public String getRfc() {
		return rfc;
	}
	public Contacto getContacto() {
		return contacto;
	}
	public String getOwnerNm() {
		return ownerNm;
	}
	public String getOwnerId() {
		return ownerId;
	}
	
	//
	public void setName(String name) {
		this.name = name;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setRaz_nSocial(String raz_nSocial) {
		this.raz_nSocial = raz_nSocial;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}
	public void setOwnerNm(String ownerNm) {
		this.ownerNm = ownerNm;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
}
