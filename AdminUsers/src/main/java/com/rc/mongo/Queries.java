package com.rc.mongo;

import com.mongodb.client.*;
import com.mongodb.client.result.UpdateResult;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
//import com.mongodb.BasicDBObject;
//import com.mongodb.DBObject;
//import com.mongodb.MongoClient;
//import com.mongodb.MongoClientURI;
import com.mongodb.MongoClient;


public class Queries {
	//String urlDB = "mongodb+srv://kmonge:<password>@cluster0-z32i5.gcp.mongodb.net/test?retryWrites=true&w=majority";
	
	public static Map<String, String> validaUsr(String correo, String passw) {
		Map<String, String> response = new HashMap<String, String>();
		//String uri = "mongodb+srv://kmonge:1q2w3e4r@cluster0-z32i5.gcp.mongodb.net/test?retryWrites=true&w=majority";
		String uri = "mongodb+srv://consultoria:1q2w3e4r@rccrm-gx0n0.gcp.mongodb.net/test?retryWrites=true&w=majority";
		com.mongodb.client.MongoClient mongoClient = MongoClients.create(uri);;
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		MongoDatabase database = mongoClient.getDatabase("IntegrationRCCRM").withCodecRegistry(pojoCodecRegistry);
		//System.out.println(mongoClient.);
		try {
			
			MongoCollection<Document> collection = database.getCollection("Usuarios");
			//Bson filter = null;
			//filter = eq("_correo", correo);
			Bson filter = new Document("_correo",correo);
			Document myDoc = collection.find(filter).first();
			if(myDoc != null) {
				if(myDoc.get("passw").equals(passw)) {
					MongoCollection<Document> intentos = database.getCollection("Intentos");
					filter = null;
					filter = new Document("_correo",correo);
					Document doc = intentos.find(filter).first();
					int i = Integer.parseInt(doc.get("intentos").toString());
					if(i>3) {
						//String datetime[] = doc.get("datetime").toString().split("T");
						//String datetoday[] = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")).split("T");
						response.put("type", "locked");
						response.put("value", "Rebasaste el número de intento, el usuario será desbloqueado automáticamente en 4 horas.");			
					}
					else {
						response.put("type", "success");
						response.put("accountId", (String) myDoc.get("accountId"));
						response.put("vendor", (String) myDoc.get("vendorName"));
						response.put("correo", (String) myDoc.get("_correo"));
						response.put("usuarioId", (String) myDoc.get("userId"));
						MongoCollection<Document> logger = database.getCollection("logger");
						Document log = new Document("clase", "Queries").append("success", "{'correo':'"+correo+"','cuenta':'"+myDoc.get("accountId")+"','usuarioId':'"+myDoc.get("userId")+"'}").append("Message", "nuevo inicio de sesión.").append("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")).toString());
						logger.insertOne(log);
					}
				}
				else{
					MongoCollection<Document> intentos = database.getCollection("Intentos");
					filter = null;
					filter = new Document("_correo",correo);
					Document doc = intentos.find(filter).first();
					int i = Integer.parseInt(doc.get("intentos").toString());
					if(i<3) {
						++i;
						filter = null;
						filter = eq("_correo", correo);
						Bson query = combine(set("datetime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")).toString()),set("intentos", ""+i));
						intentos.updateOne(filter, query);
						response.put("type", "error");
						response.put("value", "Usuario o contraseña incorrecto.");
					}
					else {
						//Document intento = new Document("datetime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))).append("_correo", correo).append("intentos", ""+i);
						//intentos.insertOne(intento);
						response.put("type", "locked");
						response.put("value", "Rebasaste el número de intento, el usuario será desbloqueado automáticamente en 4 horas.");
					}
				}
			}
			else {
				response.put("type", "error");
				response.put("value", "Usuario no registrado.");
			}
		}
		catch(Exception e) {
			try {
				response.put("type", "error");
				response.put("value", e.getMessage());
				MongoCollection<Document> logger = database.getCollection("logger").withCodecRegistry(pojoCodecRegistry);
				Document doc = new Document("clase", "Queries").append("StackTrace", e.getStackTrace()[0].toString()).append("errMessage", e.getMessage()).append("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
				logger.insertOne(doc);
				int i = e.getStackTrace().length;
				for(int n=0; n<i; n++) {
					System.out.println(e.getStackTrace()[n]);
				}
			}
			catch(Exception ex) {
				response.put("type", "error");
				response.put("value", "Error en el servicio.");
				//response.put("value", ex.getMessage());
				//System.out.println(ex.getStackTrace().toString());
			}
		}
		mongoClient.close();
		return response;
	}
	public static Map<String,String> registrarUsr(String correo, String cuenta, String usuarioId, String vendor) {
		Map<String,String> response = new HashMap<String, String>();
		//String uri = "mongodb+srv://kmonge:1q2w3e4r@cluster0-z32i5.gcp.mongodb.net/test?retryWrites=true&w=majority";
		String uri = "mongodb+srv://consultoria:1q2w3e4r@rccrm-gx0n0.gcp.mongodb.net/test?retryWrites=true&w=majority";
		com.mongodb.client.MongoClient mongoClient = MongoClients.create(uri);;
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		MongoDatabase database = mongoClient.getDatabase("IntegrationRCCRM").withCodecRegistry(pojoCodecRegistry);
		
		try {
			MongoCollection<Document> collection = database.getCollection("Usuarios");
			Bson filter = new Document("_correo",correo);
			Document myDoc = collection.find(filter).first();
			if(myDoc == null) {
				filter = null;
				filter = new Document("userId",usuarioId);
				myDoc = collection.find(filter).first();
				if(myDoc != null) {
					filter = null;
					filter = new Document("userId", usuarioId);
					myDoc = collection.findOneAndDelete(filter);
					MongoCollection<Document> logger = database.getCollection("logger");
					Document log = new Document("clase", "Queries").append("success", "{'correo':'"+myDoc.get("_correo").toString()+"','usuarioId':'"+usuarioId+"'}").append("Message", "Usuario asociado al corrreo fue eliminado.").append("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
					logger.insertOne(log);
					
					Document doc = new Document("_correo", correo).append("accountId", cuenta).append("userId", usuarioId).append("passw", "").append("vendorName", vendor);
					collection.insertOne(doc);
					response.put("type", "success");
					response.put("value", "Registro de usuario generada.");
					log = new Document("clase", "Queries").append("success", "{'correo':'"+correo+"','cuenta':'"+cuenta+"','usuarioId':'"+usuarioId+"'}").append("Message", "nuevo usuario agregado.").append("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
					logger.insertOne(log);
					MongoCollection<Document> intentos = database.getCollection("Intentos");
					filter = null;
					filter = new Document("userId", usuarioId);
					myDoc = intentos.findOneAndDelete(filter);
					Document intento = new Document("_correo", correo).append("userId",usuarioId).append("intentos", "0").append("datetime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
					intentos.insertOne(intento);
						//Enviar invitación
					AdminUsers.EnviaInvitacion(correo);
					
				}
				else {
					Document doc = new Document("_correo", correo).append("accountId", cuenta).append("userId", usuarioId).append("passw", "").append("vendorName", vendor);
					collection.insertOne(doc);
					response.put("type", "success");
					response.put("value", "Registro de usuario generada.");
					MongoCollection<Document> logger = database.getCollection("logger");
					Document log = new Document("clase", "Queries").append("success", "{'correo':'"+correo+"','cuenta':'"+cuenta+"','usuarioId':'"+usuarioId+"'}").append("Message", "nuevo usuario agregado.").append("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
					logger.insertOne(log);
					MongoCollection<Document> intentos = database.getCollection("Intentos");
					Document intento = new Document("_correo", correo).append("intentos", "0").append("datetime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
					intentos.insertOne(intento);
					//Enviar invitación
					AdminUsers.EnviaInvitacion(correo);
				}
			}
			else {
				response.put("type", "error");
				response.put("value", "Correo registrado previamente.");
			}
		}
		catch(Exception e) {
			try {
				e.printStackTrace();
			System.out.println("\n\n"+ e.getMessage()+"\n\n");
				response.put("type", "error");
				response.put("value", e.getMessage());
				MongoCollection<Document> logger = database.getCollection("logger");
				Document doc = new Document("clase", "Queries").append("StackTrace", e.getStackTrace()[0].toString()).append("errMessage", e.getMessage()).append("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
				logger.insertOne(doc);
			}
			catch(Exception ex) {
				response.put("type", "error");
				//response.put("value", "Error en el servicio.");
				ex.printStackTrace();
				response.put("value", ex.getMessage() + " \n");
			}
			return response;
		}
		mongoClient.close();
		return response;
	}
	public static Map<String,String> generaPassw(String correo, String passw, boolean actualizar) {
		/*
		 * 
		 */
		Map<String,String> response = new HashMap<String,String>();
		//String uri = "mongodb+srv://kmonge:1q2w3e4r@cluster0-z32i5.gcp.mongodb.net/test?retryWrites=true&w=majority";
		String uri = "mongodb+srv://consultoria:1q2w3e4r@rccrm-gx0n0.gcp.mongodb.net/test?retryWrites=true&w=majority";
		com.mongodb.client.MongoClient mongoClient = MongoClients.create(uri);
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		MongoDatabase database = mongoClient.getDatabase("IntegrationRCCRM").withCodecRegistry(pojoCodecRegistry);
	
		try {		
			MongoCollection<Document> collection = database.getCollection("Usuarios");
			Bson filter = new Document("_correo",correo);
			Document myDoc = collection.find(filter).first();
			if(myDoc != null && !actualizar) {
				//Bson filter = new Document("_correo", correo); 
				//Bson newValue = new Document("passw", passw);
				//System.out.println("OK");
				//Document filter = new Document("_correo", correo);
				//Document update = new Document("cuenta", passw);
				//System.out.println("Passw:"+myDoc.get("passw")+"/");
				if(myDoc.get("passw").toString().isEmpty()) {
					filter = null;
					Bson query = null;
					filter = eq("_correo", correo);
					query = combine(set("passw", passw));
					UpdateResult result = collection.updateOne(filter, query);
					//System.out.println("OK 2");
					if(result.getModifiedCount() != 0) {
						response.put("type", "success");
						response.put("value", "Registro finalizado.");
						MongoCollection<Document> logger = database.getCollection("logger");
						Document log = new Document("clase", "Queries").append("success", "{'correo':'"+correo+"'}").append("Message", "Contraseña Generada.").append("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
						logger.insertOne(log);
					}
					else {
						response.put("type", "error");
						response.put("value", "Ocurrió un problema al generar contraseña.");
						MongoCollection<Document> logger = database.getCollection("logger");
						Document log = new Document("clase", "Queries").append("error", "{'numActualizados':'"+result.getModifiedCount()+"}").append("Message", "Contraseña no generada.").append("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
						logger.insertOne(log);
					}
				}
				else {
					response.put("type", "error");
					response.put("value", "Usuario previamente registrado.");
				}
			}
			else {
				if(myDoc != null && actualizar) {
					filter = null;
					Bson query = null;
					filter = eq("_correo", correo);
					query = combine(set("passw", passw));
					
					UpdateResult result = collection.updateOne(filter, query);
					//System.out.println("OK 2");
					if(result.getModifiedCount() != 0) {
						response.put("type", "success");
						response.put("value", "Contraseña actualizada.");
						MongoCollection<Document> logger = database.getCollection("logger");
						Document log = new Document("clase", "Queries").append("success", "{'correo':'"+correo+"'}").append("Message", "Contraseña actualizada.").append("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
						logger.insertOne(log);
					
					}
					else {
						response.put("type", "error");
						response.put("value", "Contraseña no actualizada.");
					}
				}
				else {
					response.put("type", "error");
					response.put("value", "Aún no cuentas con invitación, Contacta a tu Representante Comercial.");
				}
			}
		}
		catch(Exception e) {
			//FileWriter fw = null;
			//PrintWriter pw = null;
			try {
				MongoCollection<Document> logger = database.getCollection("logger");
				Document doc = new Document("clase", Queries.class.toString()).append("StackTrace", e.getStackTrace()[e.getStackTrace().length - 3].toString()).append("errMessage", e.getMessage()).append("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
				logger.insertOne(doc);
				response.put("type", "error");
				response.put("value", e.getMessage());
				//File f = new File("C:\\Users\\kmonge\\REACHCORE\\CONSULTORÍA\\Testing\\Proyecto Portal Clientes\\ContratosCRM.log");
				//fw = new FileWriter("C:\\Users\\kmonge\\REACHCORE\\CONSULTORÍA\\Testing\\Proyecto Portal Clientes\\ContratosCRM.log");
				//pw = new PrintWriter(fw);
				//int c = e.getStackTrace().length;
				//pw.println("|Servicio: AdminUsers|Clase: Queries|timestamp: "+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))+"|");
				//for(int i = 0; i<c; i++) {
					//pw.println(e.getStackTrace()[i]);
				//}
				//pw.println("--------------------------------------------------------------------------------------------");
				//e.printStackTrace();
				
			}
			catch(Exception ex) {
				response.put("type", "error");
				response.put("value", "Error en el servicio.");
			}
			/*finally {
				try {
		           // Nuevamente aprovechamos el finally para 
		           // asegurarnos que se cierra el fichero.
		           if (null != fw)
		        	   fw.close();
		           } 
		           catch (Exception e2) {
		              e2.printStackTrace();
		           }
			}*/
		}
		mongoClient.close();
		return response;
	}
	
	
	public static void main(String[] args) {
		//System.out.println(registrarUsr("kerenm@gmaila.com","21469990","214699900","Admin ZOHO").toString());
		System.out.println(generaPassw("kmongegmail.com","1",false).toString());
		//System.out.println(validaUsr("kerenm@gmaila.com","1q2w3e4r"));
	}
}
