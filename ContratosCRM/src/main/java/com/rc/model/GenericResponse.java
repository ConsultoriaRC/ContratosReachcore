package com.rc.model;

import java.io.Serializable;
import java.util.List;

public class GenericResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type;
	private String message;
	private String status;
	
	GenericResponse(){
		
	}
	public GenericResponse(String type, String message, String status) {
		this.type = type;
		this.message = message;
		this.status = status;
	}
	
	public String getType() {
		return type;
	}
	public String getMessage() {
		return message;
	}
	
	public String getStatus() {
		return status;
	}
	
	
	//setters
	public void setType(String type) {
		this.type = type;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
}
