package com.rc.controlador;
import com.rc.model.crm.callCRM;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.rc.model.Conversacion;
import com.rc.model.Comentario;
import com.rc.model.Contacto;
import com.rc.model.Contrato;
import com.rc.model.Cuenta;
import com.rc.model.crm.Attachments;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


//import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SimpleServlet
 */


public class srvlt_conversaciones extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	 @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	request.setCharacterEncoding("utf-8");
    	response.setContentType("text/html;charset=utf-8");
        if(session != null) {
    		
    		System.out.println("id conver:" + request.getParameter("converId"));
			System.out.println("Comment:" + request.getParameter("newcoment"));
			System.out.println("correo: " + session.getAttribute("correo").toString());
			
    		if(request.getParameter("newcoment") != null) {
    			String comment = request.getParameter("newcoment");
    			//Cuenta c = (Cuenta) session.getAttribute("account");
    			System.out.println("		*IF newcoment*");
    			//ArrayList<Comentario> comentarios = new ArrayList<Comentario>();
    			Comentario comentario = new Comentario("Cliente", session.getAttribute("correo").toString(), comment.trim(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
    			String converId = request.getParameter("converId");
    			Map<String, String> result = callCRM.setComentarios(comentario, converId);
    			String status = result.get("status");
    			
				if(status != null && status.equals("success")) {
					Object fileNamePath = session.getAttribute("fullPath");
					System.out.println("		*FULLPATH*" + fileNamePath);
					//ArrayList<Adjunto> adjuntos = new ArrayList<Adjunto>();
					if(fileNamePath != null) {
					Attachments.uploadAttachment(converId, "", fileNamePath.toString());
						session.removeAttribute("fullPath");
						
					}
				}
    			ArrayList<Conversacion> conver = callCRM.getConversations(session.getAttribute("accountId").toString());
    			request.setAttribute("conver", conver);
    			request.getRequestDispatcher("conversaciones.jsp").forward(request, response);
    		}
    		else {
    			String snewconver = request.getParameter("newconver");
    			System.out.println("		*ELSE newcoment*");
    			if(snewconver != null) {
    				Map<String, String> motivos = new HashMap<String, String>();
    				//motivos.put("cotizacion", "Corrección de cotización");
    				//motivos.put("factura", "Corrección de factura");
    				//motivos.put("consumo", "Reporte de consumo");
    				//motivos.put("adicionales", "Cotización de servicios adicionales");
    				motivos.put("cotizacion", "CorrCoti");
    				motivos.put("factura", "CorrFac");
    				motivos.put("consumo", "RepCons");
    				motivos.put("adicionales", "Coti");
    				motivos.put("ficha", "FichaPago");
    				

    				
    				System.out.println("		*IF newconver* >" + request.getParameter("contractId") +"/");
    				if(request.getParameter("contractId") != null) {
    					System.out.println("*1*:" + request.getParameter("contractNm"));
    					String contratoId = request.getParameter("contractId");
    					String contratoNm = request.getParameter("contractNm");
    					ArrayList<Comentario> comentarios = new ArrayList<Comentario>();
    					
    					//ArrayList<Adjunto> adjuntos = new ArrayList<Adjunto>();
    					String dateTime =  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    					System.out.println("Comentario --->" + request.getParameter("newconver"));
    					comentarios.add(new Comentario("Cliente", session.getAttribute("correo").toString(), request.getParameter("newconver").trim(),dateTime));
    					//adjuntos.add(new Adjunto());
    					String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    					Cuenta cuenta = (Cuenta) session.getAttribute("account");
    					Contacto contacto = cuenta.getContacto();
    					Contrato contrato = new Contrato(contratoNm, "", contratoId,null,"","", null, "", "", "", "", "", "");
    					String name = cuenta.getName() + " (" + motivos.get(request.getParameter("motivo")) + ") " + date;
    					Map<String, String> result = callCRM.setNewConversacion(new Conversacion(name, dateTime, "", "", cuenta, contrato, null, contacto, "true", comentarios, null, cuenta.getOwnerNm(), cuenta.getOwnerId()));
    					String status = result.get("status");
    					if(status.equals("success")) {
    						Object fileNamePath = session.getAttribute("fullPath");
    						System.out.println("		*FULLPATH*" + fileNamePath);
    						if(fileNamePath != null) {
    							//ArrayList<Adjunto> adjuntos = new ArrayList<Adjunto>();
    							Attachments.uploadAttachment(result.get("id"), "", fileNamePath.toString());
    						}
    					}
    					
    					//ArrayList<Conversacion> conver = callCRM.getConversations(session.getAttribute("accountId").toString());
    	    			//request.setAttribute("conver", conver);
    	    			request.getRequestDispatcher("menu.jsp").forward(request, response);
    				}
    				else {
    					System.out.println("		*ELSE newconver*");
    					if(request.getParameter("adjunto") != null) {
    						
    						System.out.println(request.getParameter("adjunto"));
    						
    					}
    					else {
    						System.out.println(request.getParameter("adjunto"));
    						//Conversacion conver = new Conversacion(request.getParameter("motivo") + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"), null, null, null, null, null, "true", );
    					}
    				}
    				//ArrayList<Conversacion> c = callCRM.getConversations(session.getAttribute("accountId").toString());
        			//request.setAttribute("conver", c);
    				//request.getRequestDispatcher("conversaciones.jsp").forward(request, response);
    			}
    			else {
    				
    			}
    		}
    	}
    	else {
    		request.getRequestDispatcher("index.jsp").forward(request, response);
    	}

    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();  
         //session.setAttribute("name","test");
    	if(session != null) {
    		System.out.println("*DOGet*");
    			ArrayList<Conversacion> c = callCRM.getConversations(session.getAttribute("accountId").toString());
    			//	request.getSession().setAttribute("contratos", c);
    			request.setAttribute("conver", c);
    			request.getRequestDispatcher("conversaciones.jsp").forward(request, response);
    	}
    	else {
    		request.getRequestDispatcher("index.jsp").forward(request, response);
    	}
	}

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

}