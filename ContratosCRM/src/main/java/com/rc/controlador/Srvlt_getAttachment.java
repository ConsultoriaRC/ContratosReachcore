package com.rc.controlador;
import com.rc.model.crm.callCRM;

import java.io.IOException;
import java.io.PrintWriter;
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


public class Srvlt_getAttachment extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	 @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	request.setCharacterEncoding("utf-8");
    	response.setContentType("text/html;charset=utf-8");
        if(session != null) {
    		String fileName = request.getParameter("converId");
    		String idAttach = request.getParameter("attachId");
    		System.out.println("id conver:" + fileName);
			System.out.println("id attach:" + idAttach);
			//System.out.println("correo: " + session.getAttribute("correo").toString());
			
			if(fileName != null && idAttach!=null) {
				Cuenta cuenta = (Cuenta) session.getAttribute("account");
				Attachments.getAttachment(cuenta.getId(), "", idAttach, fileName);
				
				PrintWriter out = response.getWriter();
		        out.print("{\"status\":1}");
				//request.getRequestDispatcher("conversaciones.jsp").forward(request, response);
			}
			else {
				PrintWriter out = response.getWriter();
		        out.print("{\"status\":0}");
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
    		System.out.println("		*DOGet*");
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