package com.rc.model;

public class Concepto {
	private String producto;
	private String pricelist;
	private String quantity;
	private String subtotal;
	private String discount;
	private String tax;
	private String total;
	
	Concepto(){
		
	}
	public Concepto(String producto, String pricelist, String quantity, String subtotal, String discount, String tax, String total) {
		this.setProducto(producto);
		this.setPricelist(pricelist);
		this.setQuantity(quantity);
		this.setSubtotal(subtotal);
		this.setDiscount(discount);
		this.setTax(tax);
		this.setTotal(total);
	}
	public String getProducto() {
		return producto;
	}
	public String getPricelist() {
		return pricelist;
	}
	public String getQuantity() {
		return quantity;
		
	}
	public String getSubtotal() {
		return subtotal;
	}
	public String getDiscount() {
		return discount;
	}
	public String getTax() {
		return tax;
	}
	public String getTotal() {
		return total;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public void setPricelist(String pricelist) {
		this.pricelist = pricelist;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public void setTotal(String total) {
		this.total = total;
	}
}
