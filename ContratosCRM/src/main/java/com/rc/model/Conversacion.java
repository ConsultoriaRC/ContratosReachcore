package com.rc.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Conversacion implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String fechaCreaci_n;
	private String modifiedTime;
	private String id;
	private Contrato contrato;
	private Factura factura;
	private String activo;
	private ArrayList<Comentario> comentarios;
	private ArrayList<Adjunto> adjuntos;
	private Cuenta account;
	private Contacto contact;
	private String ownerNm;
	private String ownerId;
	
	Conversacion() {
		
	}
	public Conversacion(String name, String fechaCreaci_n, String modifiedTime, String id, Cuenta account, Contrato contrato, Factura factura, Contacto contact, String activo, ArrayList<Comentario> comentarios, ArrayList<Adjunto> adjuntos, String ownerNm, String ownerId) {
		this.setName(name);
		this.setFechaCreaci_n(fechaCreaci_n);
		this.modifiedTime = modifiedTime;
		this.setId(id);
		this.setContrato(contrato);
		this.setFactura(factura);
		this.setActivo(activo);
		this.setAdjuntos(adjuntos);
		this.comentarios = comentarios;
		this.account = account;
		this.contrato = contrato;
		this.contact = contact;
		this.ownerNm = ownerNm;
		this.ownerId = ownerId;
	}
	public String getName() {
		return name;
	}
	public String getModifiedTime() {
		return modifiedTime;
	}
	public String getFechaCreaci_n() {
		return fechaCreaci_n;
	}
	public String getId() {
		return id;
	}
	public Contrato getContrato() {
		return contrato;
	}
	public Factura getFactura() {
		return factura;
	}
	public String getActivo() {
		return activo;
	}
	public ArrayList<Comentario> getComentarios() {
		return comentarios;
	}
	public ArrayList<Adjunto> getAdjuntos() {
		return adjuntos;
	}
	public Cuenta getAccount() {
		return account;
	}
	public Contacto getContacto() {
		return contact;
	}
	public String getOwnerNm() {
		return ownerNm;
	}
	public String getOwnerId() {
		return ownerId;
	}
	
	//setters
	public void setName(String name) {
		this.name = name;
	}
	public void setFechaCreaci_n(String fechaCreaci_n) {
		this.fechaCreaci_n = fechaCreaci_n;
	}
	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
	public void setFactura(Factura factura) {
		this.factura = factura;
	}
	public void setActivo(String activo) {
		this.activo = activo;
	}
	public void setComentarios(ArrayList<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	public void setAdjuntos(ArrayList<Adjunto> adjuntos) {
		this.adjuntos = adjuntos;
	}
	public void setAccount(Cuenta account) {
		this.account = account;
	}
	public void setContacto(Contacto contact) {
		this.contact = contact;
	}
	public void setOwnerNm(String ownerNm) {
		this.ownerNm = ownerNm;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
}
