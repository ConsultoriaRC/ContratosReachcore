package com.rc.model.crm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
//import org.apache.http.entity.mime.MultipartEntity;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.logger.LocalLogger;
import com.rc.model.Adjunto;


public class Attachments {
	
	public static String getAttachment(String accountId, String tkn, String attachmentId, String fileName) {
		String fileLocation = "";
		HttpsURLConnection connection = null;
		InputStream inputStream;
		try {
			
			String surl = "https://www.zohoapis.com/crm/v2/Attachments/"+attachmentId;
			// Create connection
			URL url = new URL(surl);
			connection = (HttpsURLConnection) url.openConnection();
			// Prepare a POST request Action
			connection.setRequestMethod("GET");
			if(tkn != "")
				connection.setRequestProperty("Authorization","Zoho-oauthtoken "+ tkn);
			else
				connection.setRequestProperty("Authorization","Zoho-oauthtoken "+ refreshTkn());
			
			//System.out.println(tkn);
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			// Create a SSL SocketFactory
			SSLSocketFactory sslSocketFactory = getFactorySimple();
			connection.setSSLSocketFactory(sslSocketFactory);
			System.out.println("HTTP Response Code " + connection.getResponseCode());
			System.out.println("HTTP Response Message "+ connection.getResponseMessage());
			inputStream = connection.getInputStream();
			//get Properties
			Properties properties = new Properties();
			properties.load(Attachments.class.getResourceAsStream("/pom.properties"));
			String artifactId = properties.getProperty("artifactId");
			//fileLocation = properties.getProperty("repositoryPath") +"/"+ accountId;
			fileLocation = "/"+ artifactId +"/tmp/"+ accountId;
			//se crea la carpeta
			File location = new File(fileLocation);
			location.mkdirs();
			location = null;
			fileLocation = fileLocation + "/" + fileName;
			//se crea el archivo
			location = new File(fileLocation);
			if(!location.isFile()) {
				//
				FileOutputStream out = new FileOutputStream(fileLocation);
				 //InputStream is = (InputStream)response.getEntity();
				 int len = 0;
				 byte[] buffer = new byte[20480];
				 while((len = inputStream.read(buffer)) != -1) {
					 out.write(buffer, 0, len);
				 }
				 out.flush();
			     out.close();
			}
			fileLocation = location.getAbsolutePath();
	        //System.out.println("Server size: " + baos.size());
			//System.out.println("location: " + location.getAbsolutePath());
			LocalLogger.writeIntoLog(location.getAbsolutePath(), Attachments.class.toString());
			//System.out.println("Escribe en Log ");
			// Close Connection
			
			connection.disconnect();
			inputStream.close();
		
	  } 
	  catch (Exception e) {
		  if (connection != null) {
				connection.disconnect();
			}
		  System.out.println(e.getMessage());
		  return fileLocation;
	  }
		
		
		 return fileLocation;
	}
	
	public static String uploadAttachment(String converId, String tkn, String fileNamePath) {
		
		String surl = "https://www.zohoapis.com/crm/v2/Conversaciones/"+converId + "/Attachments";
		
		try {
			if(tkn != "")
				ClientMultipartFormPost.UploadFile(surl, tkn, fileNamePath);
			else
				ClientMultipartFormPost.UploadFile(surl, refreshTkn(), fileNamePath);
		File f = new File(fileNamePath);
		f.delete();
	  } 
	  catch (Exception e) {
		  
		  System.out.println(e.getMessage());
		  return "Error";
	  }
		return "success";
	}
	
	public static ArrayList<Adjunto> getAttachmentRelatedToConver(String ConverId, String tkn) {
		HttpsURLConnection connection = null;
		InputStream inputStream;
		ArrayList<Adjunto> arrAdjuntos = new ArrayList<Adjunto>();
		try {
			
			String surl = "https://www.zohoapis.com/crm/v2/Conversaciones/"+ConverId + "/Attachments";
			// Create connection
			URL url = new URL(surl);
			connection = (HttpsURLConnection) url.openConnection();
			// Prepare a POST request Action
			connection.setRequestMethod("GET");
			if(tkn != "")
				connection.setRequestProperty("Authorization","Zoho-oauthtoken "+ tkn);
			else
				connection.setRequestProperty("Authorization","Zoho-oauthtoken "+ refreshTkn());
			
			//System.out.println(tkn);
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			// Create a SSL SocketFactory
			SSLSocketFactory sslSocketFactory = getFactorySimple();
			connection.setSSLSocketFactory(sslSocketFactory);
			System.out.println("HTTP Response Code " + connection.getResponseCode());
			System.out.println("HTTP Response Message "+ connection.getResponseMessage());
			inputStream = connection.getInputStream();
			String s = IOUtils.toString(inputStream, "utf-8"); 
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jn = objectMapper.readTree(s);
			JsonNode adjuntos = jn.findValue("data");
			Iterator<JsonNode> iterator = adjuntos.iterator();
			while(iterator.hasNext()) {
				JsonNode adjunto = iterator.next();
				arrAdjuntos.add(new Adjunto(adjunto.get("File_Name").asText(), adjunto.get("Size").asText(), adjunto.get("id").asText(), "", adjunto.get("Created_Time").asText()));
			}
			connection.disconnect();
			inputStream.close();
		
	  } 
	  catch (Exception e) {
		  if (connection != null) {
				connection.disconnect();
			}
		  System.out.println(e.getMessage());
		  return arrAdjuntos;
	  }
		 return arrAdjuntos;
	}
	
	private static String refreshTkn() {
		String tkn = "";
		HttpsURLConnection connection = null;
		InputStream inputStream;
		try {
			String surl = "https://accounts.zoho.com/oauth/v2/token?refresh_token=1000.b2bf1aab1e41147acd10fc93dc27d7dc.a0f60b1f1d7881194bed2375e2401154&client_id=1000.O71SMM0A3XNZ12580XU31SF84OV49H&client_secret=979724bb63d03989a30b69c5aa60b0a65f8c042657&grant_type=refresh_token";
			// Create connection
			//logger.info("Try to connect to the URL " + httpURL + " ...");
			URL url = new URL(surl);
			connection = (HttpsURLConnection) url.openConnection();
			// Prepare a POST request Action
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			// Create a SSL SocketFactory
			SSLSocketFactory sslSocketFactory = getFactorySimple();
			connection.setSSLSocketFactory(sslSocketFactory);
			System.out.println("HTTP Response Code " + connection.getResponseCode());
			System.out.println("HTTP Response Message "+ connection.getResponseMessage());
			inputStream = connection.getInputStream();
			
			//String s = IOUtils.toString(inputStream, "utf-8"); 
			HashMap<String,Object> json = new ObjectMapper().readValue(inputStream, HashMap.class);
			 
			tkn = (String) json.get("access_token");
			
			// Close Connection
			connection.disconnect();
			inputStream.close();
	  } 
	  catch (Exception e) {
		  if (connection != null) {
				connection.disconnect();
			}
		  System.out.println(e.getMessage());
	  }
	 
		return tkn;
	}
	
	private static SSLSocketFactory getFactorySimple() throws Exception {
		SSLContext context = SSLContext.getInstance("TLS");
		context.init(null, null, null);
		return context.getSocketFactory();
	}
	
	public static void main(String[] args) {
		 //System.out.println(getAttachment("2146999000014643367", "", "2146999000023519008", "COMPLEMENTO DE PAGO.pdf"));
		 System.out.println(getAttachmentRelatedToConver("2146999000024794011", "").get(0).getFileName());
		 
		 //System.out.println(uploadAttachment( "2146999000023383002", "","C:\\Users\\kmonge\\Downloads\\CFDI- nuevo Certificado.pdf"));
		
	}
}
