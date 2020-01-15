package com.rc.model;

import java.io.Serializable;
import java.util.List;

public class Contrato implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String finicio;
	private String id;
	private List<String> servicios;
	private String status;
	private String duracion;
	private ActivacionContrato activacion;
	private String valor;
	private String ffin;
	private String plazoPago;
	private String terminosPago;
	private String pagoServicio;
	private String moneda;
	
	Contrato(){
		
	}
	public Contrato(String name, String finicio, String id, List<String> servicios, String status, String duracion, ActivacionContrato activacion, String valor, String ffin, String plazoPago, String terminosPago, String pagoServicio, String moneda) {
		this.setName(name);
		this.setFinicio(finicio);
		this.setId(id);
		this.setServicios(servicios);
		this.status = status;
		this.setDuracion(duracion);
		this.activacion = activacion;
		this.valor = valor;
		this.ffin = ffin;
		this.plazoPago = plazoPago;
		this.terminosPago = terminosPago;
		this.pagoServicio = pagoServicio;
		this.moneda = moneda;
	}
	public String getName() {
		return name;
	}
	public String getFinicio() {
		return finicio;
	}
	public String getId() {
		return id;
		
	}
	public List<String> getServicio() {
		return servicios;
	}
	public String getStatus() {
		return status;
	}
	public String getDuracion() {
		return duracion;
	}
	public ActivacionContrato getActivacionContrato() {
		return activacion;
	}
	public String getValor() {
		return valor;
	}
	public String getFfin() {
		return ffin;
	}
	public String getPlazoPago() {
		return plazoPago;
	}
	public String getTerminosPago() {
		return terminosPago;
	}
	public String getPagoServicio() {
		return pagoServicio;
	}
	public String getMoneda() {
		return moneda;
	}
	
	//setters
	public void setName(String name) {
		this.name = name;
	}
	public void setFinicio(String finicio) {
		this.finicio = finicio;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setServicios(List<String> servicios) {
		this.servicios = servicios;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}
	public void setActivacionContrato(ActivacionContrato activacion) {
		this.activacion = activacion;
	}
	public void setPlazoPago(String plazoPago) {
		this.plazoPago = plazoPago;
	}
	public void setTerminosPago(String terminosPago) {
		this.terminosPago = terminosPago;
	}
	public void setPagoServicio(String pagoServicio) {
		this.pagoServicio = pagoServicio;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public void setFfin(String ffin) {
		this.ffin = ffin;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
}
