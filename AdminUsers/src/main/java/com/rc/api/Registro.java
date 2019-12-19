package com.rc.api;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.rc.mongo.Queries;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Registro {
	
	@GET
	@Path("/usuario")
	@Produces("application/json")
	public String getRegistro(@QueryParam("correo") String correo, 
            @QueryParam("passw") String passw) {
		Map<String, String> result = Queries.validaUsr(correo, passw);
		if( result.get("type").equals("error")) {
		String pattern = 
	      "{\"status\":\"%s\", \"valor\":\"%s\"}";
		
			return String.format(pattern,  result.get("type"), result.get("value"));
		}
		else {
			String pattern = 
				      "{\"status\":\"%s\", \"cuenta\":\"%s\", \"usuario\":\"%s\", \"correo\":\"%s\", \"vendor\": \"%s\"}";
			return String.format(pattern,  result.get("type"), result.get("accountId"), result.get("usuarioId"), result.get("correo"), result.get("vendor")); 
		}
	}
	 
	@POST
	@Path("/registro")
	@Produces("application/json")
	public String create(@QueryParam("correo") String correo, 
	                        @QueryParam("cuenta") String cuenta, 
	                        @QueryParam("usuarioId")   String usuarioId, 
	                        @QueryParam("vendor")   String vendor) {
		
		Map<String, String> result = Queries.registrarUsr(correo, cuenta, usuarioId, vendor);
		String pattern = 
				      "{ \"status\":\"%s\", \"mensaje\":\"%s\"}";
		   return String.format(pattern,  result.get("type"), result.get("value"));  
	   
	}
	@PUT
	@Path("/registro")
	@Produces("application/json")
	public String update(@QueryParam("correo") String correo, 
	                        @QueryParam("passw") String passw) {
		
		Map<String, String> result = Queries.generaPassw(correo, passw, false);
		String pattern =  "{ \"status\":\"%s\", \"value\":\"%s\"}";
		 return String.format(pattern,  result.get("type"), result.get("value"));  
	   
	}
}