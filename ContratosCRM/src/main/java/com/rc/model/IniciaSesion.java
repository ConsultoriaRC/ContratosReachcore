package com.rc.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class IniciaSesion {

	public IniciaSesion() {
		
	}
	public static Map<String,String> iniciarSesion(String correo, String passw) throws IOException {
		Map<String,String> detail = new HashMap<String, String>();
		HttpURLConnection conn = null;
		InputStream inputStream;
		try {
			String surl = "http://104.197.10.133/AdminUsers/usuario/?correo="+correo +"&passw="+passw;
			// Create connection
			URL url = new URL(surl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			else{
				inputStream = conn.getInputStream();
				String s = IOUtils.toString(inputStream, "utf-8"); 
				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode jn = objectMapper.readTree(s);
				System.out.println(jn);
				//detail.put("count",jn.findValue("count").asText());
				detail.put("type", jn.get("status").asText());
				detail.put("accountId", jn.get("cuenta").asText());
				detail.put("userId", jn.get("usuario").asText());
				detail.put("vendor", jn.get("vendor").asText());
			}
			// Close Connection
			conn.disconnect();
			inputStream.close();
		} 
		catch (Exception e) {
			if(conn.getResponseCode() == 204) {
				detail.put("type","error");
				detail.put("value","Code: 204, Mensaje: No estás registrado aún.");
				
			}
			else {
				detail.put("type","error");
				detail.put("value", conn.getResponseCode() + " " + conn.getResponseMessage());
			}
			if (conn != null) {
				conn.disconnect();
			}
			System.out.println(e.getMessage());
	  }
		return detail;
	}
	public static Map<String,String> registrarse(String correo, String passw) throws IOException {
		Map<String,String> detail = new HashMap<String, String>();
		HttpURLConnection conn = null;
		InputStream inputStream;
		try {
			
			String surl = "http://104.197.10.133/AdminUsers/registro?correo="+correo +"&passw="+passw;
			// Create connection
			URL url = new URL(surl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("PUT");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			else {
				inputStream = conn.getInputStream();
				String s = IOUtils.toString(inputStream, "utf-8"); 
				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode jn = objectMapper.readTree(s);
				System.out.println(jn);
				//detail.put("count",jn.findValue("count").asText());
				detail.put("type",jn.get("status").asText());
				//detail.put("account", jn.get("cuenta").asText());
				//detail.put("vendor", jn.get("vendor").asText());
				detail.put("value", jn.get("value").asText());
			}
			 // Close Connection
			
			conn.disconnect();
			inputStream.close();
		} 
		catch (Exception e) {
			if(conn.getResponseCode() == 204) {
				detail.put("type","error");
				detail.put("value","No estás registrado aún.");
				
			}
			else {
				detail.put("type","error");
				detail.put("value", conn.getResponseCode() + " " + conn.getResponseMessage());
			}
			if (conn != null) {
				conn.disconnect();
			}
			//System.out.println(e.getMessage());
	  }
		return detail;
	}
}
