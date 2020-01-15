package com.rc.model;

public class Comentario {
	private String interlocutor;
	private String email;
	private String comment;
	private String fecha;
	
	
	Comentario(){
		
	}
	public Comentario(String interlocutor, String email, String comment, String fecha) {
		this.setInterlocutor(interlocutor);
		this.setEmail(email);
		this.setComment(comment);
		this.setFecha(fecha);
	}
	public String getInterlocutor() {
		return interlocutor;
	}
	public String getEmail() {
		return email;
	}
	public String getComment() {
		return comment;
		
	}
	public String getFecha() {
		return fecha;
	}
	
	public void setInterlocutor(String interlocutor) {
		this.interlocutor = interlocutor;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
}
