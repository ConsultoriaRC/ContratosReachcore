package com.rc.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Factura implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String id;
	private String moneda = "";
	private String usoCFDI = "";
	private String referenciaPago = "";
	private String fechaFacturaci_n = "";
	private String m_todoPago = "";
	private String formaPago;
	private String year;
	private ArrayList<Concepto> conceptos;
	private String validTill;
	private String stage;
	private String contractName;
	private String contractId;
	private String subTotal;
	private String tax;
	private String grandTotal;
	
	Factura(){
		
	}
	public Factura(String name, String id, String moneda, String usoCFDI, String referenciaPago, String FechaFacturaci_n, String m_todoPago, String formaPago, String year, ArrayList<Concepto> conceptos, String validTill, String stage, String contractName, String contractId, String subTotal, String tax, String grandTotal) {
		this.name = name;
		this.id = id;
		this.setMoneda(moneda);
		this.setUsoCFDI(usoCFDI);
		this.setReferenciaPago(referenciaPago);
		this.setFechaFacturaci_n(FechaFacturaci_n);
		this.setM_todoPago(m_todoPago);
		this.setFormaPago(formaPago);
		this.setYear(year);
		this.conceptos = conceptos;
		this.validTill = validTill;
		this.stage = stage;
		this.contractName = contractName;
		this.contractId = contractId;
		this.subTotal = subTotal;
		this.tax = tax;
		this.grandTotal = grandTotal;
	}
	public String getName() {
		return name;
	}
	public String getId() {
		return id;
	}
	public String getMoneda() {
		return moneda;
	}
	public String getUsoCFDI() {
		return usoCFDI;
	}
	public String getReferenciaPago() {
		return referenciaPago;
		
	}
	public String getFechaFacturaci_n() {
		return fechaFacturaci_n;
	}
	public String getM_todoPago() {
		return m_todoPago;
	}
	public String getFormaPago() {
		return formaPago;
	}
	public String getYear() {
		return year;
	}
	public ArrayList<Concepto> getConceptos() {
		return conceptos;
	}
	public String getValidTill() {
		return validTill;
	}
	public String getStage() {
		return stage;
	}
	public String getContractName() {
		return contractName;
	}
	public String getContractId() {
		return contractId;
	}
	public String getSubTotal() {
		return subTotal;
	}
	public String getTax() {
		return tax;
	}
	public String getGrandTotal() {
		return grandTotal;
	}
	
	//setter
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	public void setUsoCFDI(String usoCFDI) {
		this.usoCFDI = usoCFDI;
	}
	public void setReferenciaPago(String referenciaPago) {
		this.referenciaPago = referenciaPago;
	}
	public void setFechaFacturaci_n(String fechaFacturaci_n) {
		this.fechaFacturaci_n = fechaFacturaci_n;
	}
	public void setM_todoPago(String m_todoPago) {
		this.m_todoPago = m_todoPago;
	}
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public void setYear(ArrayList<Concepto> conceptos) {
		this.conceptos = conceptos;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public void setGrandTotal(String grandTotal) {
		this.grandTotal = grandTotal;
	}
}
