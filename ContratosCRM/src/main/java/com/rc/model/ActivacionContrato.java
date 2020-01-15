package com.rc.model;

import java.io.Serializable;
import java.util.List;

public class ActivacionContrato implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;
	private String message;
	private boolean voboCliente;
	private boolean voboComercial;
	private boolean activado;
	
	ActivacionContrato(){
		
	}
	public ActivacionContrato(String status, String message, boolean voboCliente, boolean voboComercial, boolean activado) {
		this.status = status;
		this.message = message;
		this.voboCliente = voboCliente;
		this.voboComercial = voboComercial;
		this.activado = activado;
	}
	public String getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	public boolean getVoboCliente() {
		return voboCliente;
	}
	public boolean getVoboComercial() {
		return voboComercial;
	}
	public boolean getActivado() {
		return activado;
	}
	
	//setters
	public void setStatus(String status) {
		this.status = status;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setgetVoboCliente(boolean voboCliente) {
		this.voboCliente = voboCliente;
	}
	public void setVoboComercial(boolean voboComercial) {
		this.voboComercial = voboComercial;
	}
	public void setActivado(boolean activado) {
		this.activado = activado;
	}
}
