package com.rc.model.crm;


import java.util.List;
import java.util.Map;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.SSLContext;

import org.apache.commons.io.IOUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.model.*;

public class callCRM {
	private static String tkn = "";
	private static int time = 0;
	private static String refreshTkn() {
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
			//System.out.println("HTTP Response Code " + connection.getResponseCode());
			//System.out.println("HTTP Response Message "+ connection.getResponseMessage());
			inputStream = connection.getInputStream();
			
			//HashMap<String,Object> json = new ObjectMapper().readValue(inputStream, HashMap.class);
			String s = IOUtils.toString(inputStream, "utf-8"); 
			ObjectMapper objectMapper = new ObjectMapper();
			//System.out.println(objectMapper.toString());
			JsonNode json = objectMapper.readTree(s);
			tkn = json.get("access_token").asText(); 
			
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
	
	public static Cuenta getAccount(String account, String contactId) {
		Cuenta detail = null;
		
		HttpsURLConnection connection = null;
		InputStream inputStream;
		try {
			
			String surl = "https://www.zohoapis.com/crm/v2/Accounts/"+account;
			// Create connection
			URL url = new URL(surl);
			connection = (HttpsURLConnection) url.openConnection();
			// Prepare a POST request Action
			connection.setRequestMethod("GET");
			if(tkn != "")
				connection.setRequestProperty("Authorization","Zoho-oauthtoken "+ tkn);
			else {
				tkn = refreshTkn();
				connection.setRequestProperty("Authorization","Zoho-oauthtoken "+ tkn);
			}
			System.out.println(tkn);
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			// Create a SSL SocketFactory
			SSLSocketFactory sslSocketFactory = getFactorySimple();
			connection.setSSLSocketFactory(sslSocketFactory);
			//System.out.println("HTTP Response Code " + connection.getResponseCode());
			//System.out.println("HTTP Response Message "+ connection.getResponseMessage());
			inputStream = connection.getInputStream();
			
			String s = IOUtils.toString(inputStream, "utf-8"); 
			ObjectMapper objectMapper = new ObjectMapper();
			//System.out.println(objectMapper.toString());
			JsonNode jn = objectMapper.readTree(s);
			//System.out.println(jn);
			JsonNode data = jn.findValue("data");
			String d = data.toString();
			d = d.substring(1, d.length()-1);
			//System.out.println(d);
			data = objectMapper.readTree(d);
			jn = data.findValue("Owner");
			detail = new Cuenta(data.get("Account_Name").asText(), account, data.get("Raz_n_social").asText(), data.get("RFC").asText(), getContacto(contactId), jn.get("name").asText(), jn.get("id").asText()); 
			//System.out.println(detail.getName());
			  
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

		return detail;
	}
	
	public static Contacto getContacto(String contactId) {
		Contacto detail = null;
		
		HttpsURLConnection connection = null;
		InputStream inputStream;
		try {
			
			String surl = "https://www.zohoapis.com/crm/v2/Contacts/"+contactId;
			// Create connection
			URL url = new URL(surl);
			connection = (HttpsURLConnection) url.openConnection();
			// Prepare a POST request Action
			connection.setRequestMethod("GET");
			if(tkn != "")
				connection.setRequestProperty("Authorization","Zoho-oauthtoken "+ tkn);
			else
				connection.setRequestProperty("Authorization","Zoho-oauthtoken "+ refreshTkn());
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			// Create a SSL SocketFactory
			SSLSocketFactory sslSocketFactory = getFactorySimple();
			connection.setSSLSocketFactory(sslSocketFactory);
			//System.out.println("HTTP Response Code " + connection.getResponseCode());
			//System.out.println("HTTP Response Message "+ connection.getResponseMessage());
			inputStream = connection.getInputStream();
			
			String s = IOUtils.toString(inputStream, "utf-8"); 
			ObjectMapper objectMapper = new ObjectMapper();
			//System.out.println(objectMapper.toString());
			JsonNode jn = objectMapper.readTree(s);
			//System.out.println(jn);
			JsonNode data = jn.findValue("data");
			String d = data.toString();
			d = d.substring(1, d.length()-1);
			//System.out.println(d);
			data = objectMapper.readTree(d);
			detail = new Contacto(data.get("Full_Name").asText(), data.get("id").asText(), data.get("Email").asText()); 
			//System.out.println(detail.get("rs"));
			  
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
	 
		
		return detail;
	}
	
	public static ArrayList<Contrato> getContract(String account) {
		ArrayList<Contrato> detail = new ArrayList<Contrato>();
		
		HttpsURLConnection connection = null;
		InputStream inputStream;
		try {
			
			String surl = "https://www.zohoapis.com/crm/v2/Accounts/"+account + "/Deals";
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
			//System.out.println("HTTP Response Message "+ connection.getResponseMessage());
			inputStream = connection.getInputStream();
			
			String s = IOUtils.toString(inputStream, "utf-8"); 
			ObjectMapper objectMapper = new ObjectMapper();
			//System.out.println(objectMapper.toString());
			JsonNode jn = objectMapper.readTree(s);
			//System.out.println(jn);
			
			JsonNode deals = jn.findValue("data");
			Iterator<JsonNode> iterator = deals.iterator();
			while(iterator.hasNext()) {
				JsonNode deal = iterator.next();
				//System.out.println(deals.toString());
				//JsonNode deal = objectMapper.readTree(deals.toString());
				//System.out.println("Deal > " +  deal.toString());
				String stage = deal.get("Stage").asText();
				String ancla = deal.get("Cuenta_Ancla1").asText();
				//System.out.println(stage);
				//int year = Calendar.YEAR;
				
				if((stage.equals("Vigente") || stage.equals("Negociación") || stage.equals("Vencido NO renovado")) && (ancla.equals("SLB") || ancla.equals("WTF"))) {
					
					//System.out.println("IF --");
					String finicio = deal.get("Fecha_de_inicio").asText();
					//int finit = Integer.parseInt(finicio.substring(0, 4));
					//System.out.println(finit);
					//System.out.println("Deal > " +  deal.toString());
					
					JsonNode servicios = deal.get("Servicios_PAC");
					List<String> serviciosPAC = new ArrayList<String>();
					int i = servicios.size();
					//System.out.println("int: " + i);
					
					for(int n = 0; n<i; n++) {
						serviciosPAC.add(servicios.get(n).asText());
					}
					boolean voboComercial = deal.get("VoBo_Comercial").asBoolean();
					boolean voboCliente = deal.get("VoBo_Cliente").asBoolean();
					ActivacionContrato activacion;
					if(stage.equals("Negociación") && (!voboComercial && !voboCliente)) {
						
							activacion = new ActivacionContrato("success", "!Aprueba tu Contrato!", voboCliente, voboComercial, false);
							
					}
					else {
						if(stage.equals("Negociación") && (!voboComercial && voboCliente)) {
							activacion = new ActivacionContrato("success", "Contrato Aprobado, en espera de aprobaciï¿½n del comercial", voboCliente, voboComercial, true);
						}
						else {
							activacion = null;
						}
					}
					
					//System.out.println("serviciosPAC: " + serviciosPAC.toString());
					String contractNm = deal.get("Deal_Name").asText().replace("\"", "");
					String total = deal.get("Total_T").asText();
					int totalLength = total.length();
					if(totalLength > 3) {
						if(totalLength>=4 && totalLength<=6) {
							total = total.substring(0, totalLength-3) + "," + total.substring(totalLength - 3, totalLength);
						}
						if(totalLength>6 && totalLength<=9) {
							String s1 =  "," + total.substring(totalLength - 3, totalLength);
							String s2 = "," + total.substring(totalLength - 6, totalLength - 3);
							total = total.substring(0, totalLength-6) + s2 + s1;
						}
					}
					if(!total.contains("."))
						total = total +".00";
						
					detail.add(new Contrato(contractNm, finicio, deal.get("id").asText(), serviciosPAC, stage, deal.get("Duraci_n_del_contrato").asText(), activacion, total, deal.get("Fecha_de_finalizaci_n").asText(), deal.get("Plazo_de_pago").asText(), deal.get("T_rminos_de_pago_en_d_as").asText(), deal.get("Pago_del_servicio").asText(), deal.get("$currency_symbol").asText()));
				}
			 }
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
	 
		return detail;
	}
	
	public static String updateContract(String deal) {
		String detail = "";
		
		
		HttpsURLConnection connection = null;
		InputStream inputStream;
		try {
			
			String surl = "https://www.zohoapis.com/crm/v2/deals";
			// Create connection
			URL url = new URL(surl);
			connection = (HttpsURLConnection) url.openConnection();
			// Prepare a POST request Action
			connection.setRequestMethod("PUT");
			if(tkn != "")
				connection.setRequestProperty("Authorization","Zoho-oauthtoken "+ tkn);
			else
				connection.setRequestProperty("Authorization","Zoho-oauthtoken "+ refreshTkn());
			//System.out.println(tkn);
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			
			String input = "";
			
			input = "{\"data\": [" +
						 		"{" +
						 			"\"id\": \"" +deal +"\"," +
						 			"\"VoBo_Cliente\": true" +
						 		"}" +	
						 	"]," +
						 	"\"trigger\": [" +
						     "\"approval\"" +
						   "]" +
						 "}";
			
			System.out.println("Input " + input);
			OutputStream os = connection.getOutputStream();
	        os.write(input.getBytes());
	        os.flush();
			
			// Create a SSL SocketFactory
			SSLSocketFactory sslSocketFactory = getFactorySimple();
			connection.setSSLSocketFactory(sslSocketFactory);
			//System.out.println("HTTP Response Code " + connection.getResponseCode());
			//System.out.println("HTTP Response Message "+ connection.getResponseMessage());
			inputStream = connection.getInputStream();
			
			String s = IOUtils.toString(inputStream, "utf-8"); 
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jn = objectMapper.readTree(s);
			System.out.println(jn);
			JsonNode deals = jn.findValue("data");
			detail = deals.get("code").asText();
			
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
	 
		return detail;
	}
	
	public static ArrayList<Conversacion> getConversations(String account) {
		ArrayList<Conversacion> detail = new ArrayList<Conversacion>();
		
		HttpsURLConnection connection = null;
		InputStream inputStream;
		try {
			
			String surl = "https://www.zohoapis.com/crm/v2/Accounts/"+ account +"/Conversaciones";
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
			//System.out.println("HTTP Response Code " + connection.getResponseCode());
			//System.out.println("HTTP Response Message "+ connection.getResponseMessage());
			inputStream = connection.getInputStream();
			/*if(connection.getResponseCode() == 204) {
				 throw new Exception("204");
				//detail.put("code","204");
				//detail.put("mssg","No tienes conversaciones aï¿½n.");
			}*/
			String s = IOUtils.toString(inputStream, "utf-8"); 
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jn = objectMapper.readTree(s);
			//System.out.println(jn);
			//detail.put("count",jn.findValue("count").asText());
			JsonNode conversaciones = jn.findValue("data");
			Iterator<JsonNode> iterator = conversaciones.iterator();
			while(iterator.hasNext()) {
				JsonNode conversacion = iterator.next();
				//System.out.println("Conversación-> "+ conversacion.toString());
				
				ArrayList<Adjunto> adjuntosBean = new ArrayList<Adjunto>();
				//JsonNode adjuntos = conversacion.findValue("Adjuntos");
				//System.out.println("Adjuntos-> "+ adjuntos.toString());
				adjuntosBean = Attachments.getAttachmentRelatedToConver(conversacion.get("id").asText(), tkn);
				//Iterator<Adjunto> iter = adjuntosBean.listIterator();
				int size = adjuntosBean.size();
				for(int i = 0; i<size; i++) {
					Adjunto adjunto = adjuntosBean.get(i);
					//System.out.println("Adjunto-> "+ adjunto.toString());
					String location = Attachments.getAttachment(account, tkn, adjunto.getId(), adjunto.getFileName());
					adjuntosBean.get(i).setServerLocation(location);
					System.out.println("location: " + location);
					
				}
				//System.out.println("adjuntosBean size: " + adjuntosBean.size());
				//conversacion.get("Contrato").asText(), conversacion.get("Factura").asText()
				detail.add(new Conversacion(conversacion.get("Name").asText(), conversacion.get("Fecha_de_creaci_n").asText(), conversacion.get("Modified_Time").asText(), conversacion.get("id").asText(),null,null,null,null, conversacion.get("Activo").asText(), getComentarios(conversacion.get("id").asText()), adjuntosBean, "", "")); 
				
			}
			// Close Connection
			
			connection.disconnect();
			inputStream.close();
	  } 
	  catch (Exception e) {
		  if (connection != null) {
				connection.disconnect();
			}
		 //if(connection.getResponseCode() != 204) {
		 //detail.put("code","204");
		 //detail.put("mssg","No tienes conversaciones aï¿½n.");
		  System.out.println(e.getMessage());
	  }
	 
		//System.out.println("detail size-> "+detail.size());
		System.out.println("Final de getconver->");
		return detail;
	}
	
	public static ArrayList<Factura> getContractInvoices(String contractId) {
		ArrayList<Factura> detail = new ArrayList<Factura>();
		HttpsURLConnection connection = null;
		InputStream inputStream;
		try {
			
			String surl = "https://www.zohoapis.com/crm/v2/deals/"+contractId + "/Quotes";
			// Create connection
			URL url = new URL(surl);
			connection = (HttpsURLConnection) url.openConnection();
			// Prepare a POST request Action
			connection.setRequestMethod("GET");
			String tkn = refreshTkn();
			connection.setRequestProperty("Authorization","Zoho-oauthtoken "+ tkn);
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
			//System.out.println(jn);
			
			JsonNode facturas = jn.findValue("data");
			Iterator<JsonNode> iterator = facturas.iterator();
			while(iterator.hasNext()) {
				JsonNode factura = iterator.next();
				//System.out.println(factura.toString());
				
				ArrayList<Concepto> conceptos = new ArrayList<Concepto>();
				JsonNode productDetails = factura.findValue("Product_Details");
				Iterator<JsonNode> iter = productDetails.iterator();
				while(iter.hasNext()) {
					productDetails = iter.next();
					JsonNode productDetail = objectMapper.readTree(productDetails.toString());
					JsonNode product = productDetail.findValue("product");
					
					String listPrice = productDetail.get("list_price").asText();
					int priceLength = listPrice.length();
					
					if(priceLength > 3) {
						if(priceLength>=4 && priceLength<=6) {
							listPrice = listPrice.substring(0, priceLength-3) + "," + listPrice.substring(priceLength - 3, priceLength);
						}
						if(priceLength>6 && priceLength<=9) {
							String s1 =  "," + listPrice.substring(priceLength - 3, priceLength);
							String s2 = "," + listPrice.substring(priceLength - 6, priceLength - 3);
							listPrice = listPrice.substring(0, priceLength-6) + s2 + s1;
						}
					}
					if(!listPrice.contains("."))
						listPrice = listPrice +".00";

					String net_total = productDetail.get("net_total").asText();
					int netTotalLength = net_total.length();
					
					if(netTotalLength > 3) {
						if(netTotalLength>=4 && netTotalLength<=6) {
							net_total = net_total.substring(0, netTotalLength-3) + "," + net_total.substring(netTotalLength - 3, netTotalLength);
						}
						if(netTotalLength>6 && netTotalLength<=9) {
							String s1 =  "," + net_total.substring(netTotalLength - 3, netTotalLength);
							String s2 = "," + net_total.substring(netTotalLength - 6, netTotalLength - 3);
							net_total = net_total.substring(0, netTotalLength-6) + s2 + s1;
						}
					}
					if(!net_total.contains("."))
						net_total = net_total +".00";
					
					String discount = productDetail.get("Discount").asText();
					int discountLength = discount.length();
					
					if(discountLength > 3) {
						if(discountLength>=4 && discountLength<=6) {
							discount = discount.substring(0, discountLength-3) + "," + discount.substring(discountLength - 3, discountLength);
						}
						if(discountLength>6 && discountLength<=9) {
							String s1 =  "," + discount.substring(discountLength - 3, discountLength);
							String s2 = "," + discount.substring(discountLength - 6, discountLength - 3);
							discount = discount.substring(0, discountLength - 6) + s2 + s1;
						}
					}
					if(!discount.contains("."))
						discount = discount +".00";
					
					String tax = productDetail.get("Tax").asText();
					int taxLength = tax.length();
					
					if(taxLength > 3) {
						if(taxLength>=4 && taxLength<=6) {
							tax = tax.substring(0, taxLength - 3) + "," + tax.substring(taxLength - 3, taxLength);
						}
						if(taxLength>6 && taxLength<=9) {
							String s1 =  "," + tax.substring(taxLength - 3, taxLength);
							String s2 = "," + tax.substring(taxLength - 6, taxLength - 3);
							tax = tax.substring(0, taxLength - 6) + s2 + s1;
						}
					}
					if(!tax.contains("."))
						tax = tax +".00";
					
					String total = productDetail.get("total").asText();
					int totalLength = total.length();
					
					if(totalLength > 3) {
						if(totalLength>=4 && totalLength<=6) {
							total = total.substring(0, totalLength - 3) + "," + total.substring(totalLength - 3, totalLength);
						}
						if(totalLength>6 && totalLength<=9) {
							String s1 =  "," + total.substring(totalLength - 3, totalLength);
							String s2 = "," + total.substring(totalLength - 6, totalLength - 3);
							total = total.substring(0, totalLength - 6) + s2 + s1;
						}
					}
					if(!total.contains("."))
						total = total +".00";
					
					conceptos.add(new Concepto(product.get("name").toString(), listPrice, productDetail.get("quantity").asText(), net_total, discount, tax, total));
				}
				
				String sub_total = factura.get("Sub_Total").asText();
				int stLength = sub_total.length();
				
				if(stLength > 3) {
					if(stLength>=4 && stLength<=6) {
						sub_total = sub_total.substring(0, stLength - 3) + "," + sub_total.substring(stLength - 3, stLength);
					}
					if(stLength>=6 && stLength<=9) {
						String s1 =  "," + sub_total.substring(stLength - 3, stLength);
						String s2 = "," + sub_total.substring(stLength - 6, stLength - 3);
						sub_total = sub_total.substring(0, stLength - 6) + s2 + s1;
					}
				}
				if(!sub_total.contains("."))
					sub_total = sub_total +".00";
				
				String tax = factura.get("Tax").asText();
				int taxLength = tax.length();
				
				if(taxLength > 3) {
					if(taxLength>=4 && taxLength<=6) {
						tax = tax.substring(0, taxLength - 3) + "," + tax.substring(taxLength - 3, taxLength);
					}
					if(taxLength>=6 && taxLength<=9) {
						String s1 =  "," + tax.substring(taxLength - 3, taxLength);
						String s2 = "," + tax.substring(taxLength - 6, taxLength - 3);
						tax = tax.substring(0, taxLength - 6) + s2 + s1;
					}
				}
				if(!tax.contains("."))
					tax = tax +".00";
				
				String gtotal = factura.get("Grand_Total").asText();
				int gtLength = gtotal.length();
				
				if(gtLength > 3) {
					if(gtLength>=4 && gtLength<=6) {
						gtotal = gtotal.substring(0, gtLength - 3) + "," + gtotal.substring(gtLength - 3, gtLength);
					}
					if(gtLength>=6 && gtLength<=9) {
						String s1 =  "," + gtotal.substring(gtLength - 3, gtLength);
						String s2 = "," + gtotal.substring(gtLength - 6, gtLength - 3);
						gtotal = gtotal.substring(0, gtLength - 6) + s2 + s1;
					}
				}
				if(!gtotal.contains("."))
					gtotal = gtotal +".00";
				
				
				JsonNode deal = factura.findValue("Deal_Name");
				//System.out.println(deal.toString());
				detail.add(new Factura(factura.get("Subject").asText(), factura.get("id").asText(),factura.get("Moneda").asText(), factura.get("Uso_de_CFDI").asText(), factura.get("Referencia_de_pago").asText(), factura.get("Fecha_de_facturaci_n").asText(), factura.get("M_todo_de_pago1").asText(), factura.get("Forma_de_pago").asText(), factura.get("A_o_del_servicio").asText(), conceptos, factura.get("Valid_Till").asText(), factura.get("Quote_Stage").asText(), deal.get("name").asText(), deal.get("id").asText(),  sub_total, tax, gtotal)); 
			
			 //detail.put("IVA",data.get("IVA").toString());
			 //detail.put("Monto_consultor_a",data.get("Monto_consultor_a").toString());
			}
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
	 
		return detail;
	}
	
	public static ArrayList<Comentario> getComentarios(String conversacionId) {
		ArrayList<Comentario> detail = new ArrayList<Comentario>();
		
		HttpsURLConnection connection = null;
		InputStream inputStream;
		try {
			
			String surl = "https://www.zohoapis.com/crm/v2/Conversaciones/"+conversacionId;
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
			//System.out.println("HTTP Response Code " + connection.getResponseCode());
			//System.out.println("HTTP Response Message "+ connection.getResponseMessage());
			inputStream = connection.getInputStream();
			
			String s = IOUtils.toString(inputStream, "utf-8"); 
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jn = objectMapper.readTree(s);
			JsonNode data = jn.findValue("Comentarios");
			Iterator<JsonNode> iterator = data.iterator();
			while(iterator.hasNext()) {
				JsonNode comentario = iterator.next();
				//System.out.println(comentario.toString());
				detail.add(new Comentario(comentario.get("Nombre").asText(), comentario.get("Email").asText(), comentario.get("Comentario").asText(), comentario.get("Fecha").asText())); 
			}
			  
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
	 
		//System.out.println(detail.size());
		return detail;
	}
	
	public static Map<String,String> setComentarios(Comentario comentario, String conversacionId) {
		Map<String, String> detail = new HashMap<String, String>();
		
		HttpsURLConnection connection = null;
		InputStream inputStream;
		try {
			ArrayList<Comentario> old = getComentarios(conversacionId);
			System.out.println("Núm. comentarios old: " + old.size());
			String surl = "https://www.zohoapis.com/crm/v2/Conversaciones/"+conversacionId;
			// Create connection
			URL url = new URL(surl);
			connection = (HttpsURLConnection) url.openConnection();
			// Prepare a POST request Action
			connection.setRequestMethod("PUT");
			if(tkn != "")
				connection.setRequestProperty("Authorization","Zoho-oauthtoken "+ tkn);
			else
				connection.setRequestProperty("Authorization","Zoho-oauthtoken "+ refreshTkn());
			connection.setRequestProperty("Content-Type","application/json;charset=utf-8");
			//System.out.println(tkn);
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			// Create a SSL SocketFactory
			String input = "";
			if(old != null & old.size()>0) {
				int s = old.size();
				input = "{" + 
					"\"data\":" + 
						"[{" + 
						"\"Comentarios\": ["; 
				for(int i=0; i<s; i++) {
					input = input + "{" + 
							"\"Nombre\": \" "+ old.get(i).getInterlocutor()+"\"," + 
							"\"Email\": \""+ old.get(i).getEmail()+"\"," + 
							"\"Comentario\": \""+ old.get(i).getComment()+"\"," +
							"\"Fecha\": \""+ old.get(i).getFecha()+"\"" +
							"},";
				}
				
					input = input + "{" + 
					"\"Nombre\": \" "+ comentario.getInterlocutor()+"\"," + 
					"\"Email\": \" "+ comentario.getEmail()+"\"," + 
					"\"Comentario\": \""+ comentario.getComment()+"\"," +
					"\"Fecha\": \""+ comentario.getFecha()+"\"" +
					"}]," + 
					"\"Activo\": true" + "," +
					"\"ltimo_comentario\": \""+ comentario.getComment()+"\"" +
					"}]" +
					"}";
			}
			else {
				input = "{" + 
						"\"data\":" + 
							"[{" + 
							"\"Comentarios\": [" + 
						"{" + 
						"\"Nombre\": \" "+ comentario.getInterlocutor()+"\"," + 
						"\"Email\": \" "+ comentario.getEmail()+"\"," + 
						"\"Comentario\": \""+ comentario.getComment()+"\"," +
						"\"Fecha\": \""+ comentario.getFecha()+"\"" +
						"}]," + 
						"\"Activo\": true" + "," +
						"\"ltimo_comentario\":\""+comentario.getComment()+"\"" +
						"}]" + 
						"}";
				
			}
			System.out.println("Input " + input);
			OutputStream os = connection.getOutputStream();
	        os.write(input.getBytes());
	        os.flush();
	        SSLSocketFactory sslSocketFactory = getFactorySimple();
			connection.setSSLSocketFactory(sslSocketFactory);
			//System.out.println("HTTP Response Code " + connection.getResponseCode());
			//System.out.println("HTTP Response Message "+ connection.getResponseMessage());
			
			if (connection.getResponseCode() != HttpsURLConnection.HTTP_OK) {
	            throw new RuntimeException("Failed : HTTP error code : "
	                    + connection.getResponseCode());
	        }
			inputStream = connection.getInputStream();
			
			String s = IOUtils.toString(inputStream, "utf-8"); 
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jn = objectMapper.readTree(s);
			//System.out.println(jn.toString());
			JsonNode data = jn.findValue("data");
			String d = data.toString();
			d = d.substring(1, d.length()-1);
			//System.out.println(d);
			data = objectMapper.readTree(d);
			//System.out.println(data.toString());
			detail.put("code", data.get("code").asText());
			detail.put("message", data.get("message").asText());
			detail.put("status", data.get("status").asText());
			  
			// Close Connection
			
			connection.disconnect();
			inputStream.close();
		
	  }
	  catch (Exception e) {
		  if (connection != null) {
				connection.disconnect();
			}
		  detail.put("code", "error");
		  detail.put("message", e.getMessage());
		  detail.put("status", "error");
		  
		  //System.out.println(e.getMessage());
		  return detail;
	  }
	 
		//System.out.println("size: "+detail.size());
		return detail;
	}
	public static Map<String,String> setNewConversacion(Conversacion conver) {
		Map<String, String> detail = new HashMap<String, String>();
		
		HttpsURLConnection connection = null;
		InputStream inputStream;
		try {
			String surl = "https://www.zohoapis.com/crm/v2/Conversaciones";
			// Create connection
			URL url = new URL(surl);
			connection = (HttpsURLConnection) url.openConnection();
			// Prepare a POST request Action
			connection.setRequestMethod("POST");
			if(tkn != "")
				connection.setRequestProperty("Authorization","Zoho-oauthtoken "+ tkn);
			else
				connection.setRequestProperty("Authorization","Zoho-oauthtoken "+ refreshTkn());
			
			connection.setRequestProperty("Content-Type","application/json;charset=utf-8");
			//System.out.println(tkn);
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			
			String input = "";
			if(conver.getFactura() != null) {
			input = "{" + 
					"\"data\":" + 
						"[{" + 
						"\"Comentarios\": [" + 
					"{" + 
					"\"Nombre\": \" "+ conver.getComentarios().get(0).getInterlocutor()+"\"," + 
					"\"Email\": \" "+ conver.getComentarios().get(0).getEmail()+"\"," + 
					"\"Comentario\": \""+ conver.getComentarios().get(0).getComment()+"\"," +
					"\"Fecha\": \""+ conver.getComentarios().get(0).getFecha()+"\"" +
					"}]," + 
					"\"Name\": \""+ conver.getName() + "\"," +
					"\"Fecha_de_creaci_n\": \""+ conver.getFechaCreaci_n() + "\"," +
					"\"Activo\": true," + 
					"\"ltimo_comentario\":\""+conver.getComentarios().get(0).getComment()+ "\"," +
					"\"Cuenta\": \"{"+
						"\"name\":" + conver.getAccount().getName()+"\"," +
						"\"id\":" + conver.getAccount().getId()+"\"" +
						"},"+
					"\"Contacto\": \"{"+
						"\"name\":" + conver.getContacto().getName()+"\"," +
						"\"id\":" + conver.getContacto().getId()+"\"" +
						"},"+
						"\"Factura\": \"{"+
						"\"name\":" + conver.getFactura().getName()+"\"," +
						"\"id\":" + conver.getFactura().getId()+"\"" +
						"}"+
						 "\"Owner\": {" +
			                "\"name\": \""+conver.getOwnerNm() +"\"," +
			                "\"id\": \"" +conver.getOwnerId() + "\""+
			            "}"+
					"}" + 
					"]}";
			}
			else {
				if(conver.getContrato() != null) {
					input = "{" + 
							"\"data\":" + 
								"[{" + 
								"\"Comentarios\": [" + 
							"{" + 
							"\"Nombre\": \" "+ conver.getComentarios().get(0).getInterlocutor()+"\"," + 
							"\"Email\": \" "+ conver.getComentarios().get(0).getEmail()+"\"," + 
							"\"Comentario\": \""+ conver.getComentarios().get(0).getComment()+"\"," +
							"\"Fecha\": \""+ conver.getComentarios().get(0).getFecha()+"\"" +
							"}]," + 
							"\"Name\": \""+ conver.getName() + "\"," +
							"\"Fecha_de_creaci_n\": \""+ conver.getFechaCreaci_n() + "\"," +
							"\"Activo\": true," +
							"\"ltimo_comentario\":\""+conver.getComentarios().get(0).getComment()+ "\"," +
							"\"Cuenta\": {"+
								"\"name\":\"" + conver.getAccount().getName()+"\"," +
								"\"id\": \"" + conver.getAccount().getId()+"\"" +
								"},"+
							"\"Contacto\": {"+
								"\"name\": \"" + conver.getContacto().getName()+"\"," +
								"\"id\": \"" + conver.getContacto().getId()+"\"" +
								"},"+
								"\"Contrato\": {"+
								"\"name\":\"" + conver.getContrato().getName()+"\"," +
								"\"id\": \"" + conver.getContrato().getId()+"\"" +
								"}"+
							"}" + 
							"]}";
				}
				else {
					input = "{" + 
							"\"data\":" + 
								"[{" + 
								"\"Comentarios\": [" + 
							"{" + 
							"\"Nombre\": \""+ conver.getComentarios().get(0).getInterlocutor()+"\"," + 
							"\"Email\": \""+ conver.getComentarios().get(0).getEmail()+"\"," + 
							"\"Comentario\": \""+ conver.getComentarios().get(0).getComment()+"\"," +
							"\"Fecha\": \""+ conver.getComentarios().get(0).getFecha()+"\"" +
							"}]," + 
							"\"Name\": \""+ conver.getName() + "\"," +
							"\"Fecha_de_creaci_n\": \""+ conver.getFechaCreaci_n() + "\"," +
							"\"Activo\": true," +
							"\"ltimo_comentario\":\""+conver.getComentarios().get(0).getComment()+ "\"," +
							"\"Cuenta\": \"{"+
								"\"name\":" + conver.getAccount().getName()+"\"," +
								"\"id\":" + conver.getAccount().getId()+"\"" +
								"},"+
							"\"Contacto\": \"{"+
								"\"name\":" + conver.getContacto().getName()+"\"," +
								"\"id\":" + conver.getContacto().getId()+"\"" +
								"}"+
								
							"}" + 
							"]}";
				}
				
			}
			System.out.println("Input " + input);
			OutputStream os = connection.getOutputStream();
	        os.write(input.getBytes());
	        os.flush();
	        SSLSocketFactory sslSocketFactory = getFactorySimple();
			connection.setSSLSocketFactory(sslSocketFactory);
			//System.out.println("HTTP Response Code " + connection.getResponseCode());
			//System.out.println("HTTP Response Message "+ connection.getResponseMessage());
			
			if (connection.getResponseCode() != HttpsURLConnection.HTTP_OK && connection.getResponseCode() != HttpsURLConnection.HTTP_CREATED) {
	            throw new RuntimeException("Failed : HTTP error code : "
	                    + connection.getResponseCode());
	        }
			inputStream = connection.getInputStream();
			
			String s = IOUtils.toString(inputStream, "utf-8"); 
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jn = objectMapper.readTree(s);
			System.out.println(jn.toString());
			JsonNode data = jn.findValue("data");
			String d = data.toString();
			d = d.substring(1, d.length()-1);
			//System.out.println(d);
			data = objectMapper.readTree(d);
			//System.out.println(data.toString());
			detail.put("code", data.get("code").asText());
			detail.put("message", data.get("message").asText());
			detail.put("status", data.get("status").asText());
			if(data.get("code").asText().equals("SUCCESS")) {
				JsonNode details = data.findValue("details");
				detail.put("id", details.get("id").asText());
			}
			  
			// Close Connection
			
			connection.disconnect();
			inputStream.close();
		
	  }
	  catch (Exception e) {
		  if (connection != null) {
				connection.disconnect();
			}
		  System.out.println(e.getMessage());
		  detail.put("code", "error");
		  detail.put("message", e.getMessage());
		  detail.put("status", "error");
	  }
	 
		//System.out.println("size: "+detail.size());
		return detail;
	}
	
	/*public static Map<String, String> ActivarContrato(String contractId) {
		Map<String, String> detail = new HashMap<String, String>();
		
		HttpsURLConnection connection = null;
		InputStream inputStream;
		try {
			
			String surl = "https://www.zohoapis.com/crm/v2/Deals/"+contractId;
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
			inputStream = connection.getInputStream();
			
			String s = IOUtils.toString(inputStream, "utf-8"); 
			ObjectMapper objectMapper = new ObjectMapper();
			//System.out.println(objectMapper.toString());
			JsonNode jn = objectMapper.readTree(s);
			//System.out.println(jn);
			
			JsonNode deal = jn.findValue("data");
			detail.put("type", "success");
			String voboComercial = deal.get("VoBo_Comercial").asText();
			//String voboComercial = deal.get("VoBo_Comercial").asText();
			if(voboComercial == "true") {
				detail.put("message", "Contrato Activado.");
				detail.put("activado", "true");
			}
			else {
				detail.put("message", "No fue posible activar tu contrato, por favor revisalo con tu agente comercial sReachcore.");
				detail.put("activado", "false");
			}
					
			 
			// Close Connection
			
			connection.disconnect();
			inputStream.close();
	  } 
	  catch (Exception e) {
		  if (connection != null) {
				connection.disconnect();
		  }
		  detail.put("type", "error");
		  detail.put("message", e.getMessage());
		  detail.put("activado", "false");
		  	System.out.println(e.getMessage());
	  }
	 
		return detail;
	}*/
	
	private static SSLSocketFactory getFactorySimple() throws Exception {
		SSLContext context = SSLContext.getInstance("TLS");
		context.init(null, null, null);
		return context.getSocketFactory();
	}
	
	public static void main(String[] args) {
		/*Cuenta account = callCRM.getAccount("2146999000014643367", "2146999000014661338");
		System.out.println("Nombre: " + account.getName());
		System.out.println(account.getId());
		System.out.println(account.getRaz_nSocial());
		System.out.println(account.getRfc());
		System.out.println(account.getOwnerNm());
		*/
		
		ArrayList<Contrato> m = callCRM.getContract("2146999000014643367");
		System.out.println(m.toString());
		 int i = m.size();
			for(int n=0; n<i; n++) {
				Contrato contrato = m.get(n);
				
				System.out.println("Nombre: " + contrato.getName());
				System.out.println(contrato.getFinicio());
				System.out.println(contrato.getDuracion());
				System.out.println(contrato.getId());
				System.out.println(contrato.getStatus());
				System.out.println(contrato.getTerminosPago());
				System.out.println(contrato.getPagoServicio());
				System.out.println(contrato.getMoneda());
				int a = contrato.getServicio().size();
				for(int b=0; b<a; b++) {
				System.out.println(contrato.getServicio().get(b));
				}
				System.out.println(contrato.getValor());
			}
		//ArrayList<Conversacion> detail = new ArrayList<Conversacion>();
		//detail.add(new Conversacion("name", "fechaCreaci_n", "modified_Time", "id", "contratoId", "facturaId", "activo", null));
		//System.out.println(detail.size());
		//ArrayList<Adjunto> adjuntosBean = new ArrayList<Adjunto>();
		//adjuntosBean.add(new Adjunto("extn", "isPreviewAvailable", "downloadUrl", "entityId", "fileSize", "fileName","fileId", "attachmentId"));
		//System.out.println("adjuntosBean size: " + adjuntosBean.size());
		
		/*ArrayList<Conversacion> c = callCRM.getConversations("2146999000014643367");
		//System.out.println(c.toString());
		 int i = c.size();
			for(int n=0; n<i; n++) {
				Conversacion conver = c.get(n);
				
				System.out.println("Nombre: " + conver.getName());
				System.out.println(conver.getActivo());
				System.out.println(conver.getContrato());
				System.out.println(conver.getFactura());
				System.out.println(conver.getFechaCreaci_n());
				System.out.println(conver.getId());
				System.out.println(conver.getModifiedTime());
				System.out.println(conver.getName());
				
				int a = conver.getAdjuntos().size();
				for(int b=0; b<a; b++) {
					Adjunto adj = conver.getAdjuntos().get(b);
					System.out.println(adj.getAttachmentId());
					System.out.println(adj.getDownloadUrl());
					System.out.println(adj.getEntityId());
					System.out.println(adj.getExtn());
					System.out.println(adj.getFileId());
					System.out.println(adj.getFileName());
					System.out.println(adj.getFileSize());
					System.out.println(adj.getIsPreviewAvailable());
				}
				int e = conver.getComentarios().size();
				for(int d=0; d<e; d++) {
					Comentario coments = conver.getComentarios().get(d);
					System.out.println(coments.getInterlocutor());
					System.out.println(coments.getEmail());
					System.out.println(coments.getComment());
					System.out.println(coments.getFecha());
					
				}
			}*/
		/*ArrayList<Comentario> c = callCRM.getComentarios("2146999000022411918");
		System.out.println(c.toString());
		 int i = c.size();
			for(int n=0; n<i; n++) {
				Comentario coment = c.get(n);
				
				System.out.println("Nombre: " + coment.getInterlocutor());
				System.out.println(coment.getEmail());
				System.out.println(coment.getComment());
				System.out.println(coment.getFecha());
			}*/
		/*Map<String, String> c = callCRM.setComentarios(new Comentario("Paco","kmonge@reach.com","Hey!","2019-09-09T12:49:00-06:00"),"2146999000026746001");
		System.out.println(c.toString());
		 System.out.println("Nombre: " + c.get("code"));
		System.out.println(c.get("message"));
		System.out.println(c.get("status"));*/
		
	}
		
}
