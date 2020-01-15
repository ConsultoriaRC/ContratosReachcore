package com.rc.controlador;
import com.rc.model.crm.callCRM;
import java.io.IOException;
import java.util.ArrayList;
import com.rc.model.Contrato;

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

public class srvlt_contratos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session=request.getSession();  
         //session.setAttribute("name","test");  
    	if(session != null) {
    		ArrayList<Contrato> c = callCRM.getContract(session.getAttribute("accountId").toString());
    		//request.getSession().setAttribute("contratos", c);
    		request.setAttribute("contratos", c);
    		request.getRequestDispatcher("contratos.jsp").forward(request, response);
    	}
    	else {
    		request.getRequestDispatcher("index.jsp").forward(request, response);
    	}
    	/*response.setContentType("text/plain");
    	response.getWriter().write("Cuenta:"+ m.get("acc_name") + "\n");
	      response.getWriter().write("Razón Social:"+ m.get("rs") + "\n");
	      response.getWriter().write("RFC: " + m.get("rfc"));*/
	      //response.sendRedirect("invoices.jsp");
	}
    
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session=request.getSession();  
         //session.setAttribute("name","test");  
    	System.out.println("llega contratos");
    	if(session != null) {
    		
    		String activar = request.getParameter("activar");
    		System.out.println("activar--> " + activar);
    		if(activar!= null && activar.contentEquals("true")){
    			System.out.println("Entra activar");
    			String status = callCRM.updateContract(request.getParameter("contractId").toString());
    			System.out.println("status-->" + status);
    			ArrayList<Contrato> c = callCRM.getContract(session.getAttribute("accountId").toString());
        		request.setAttribute("contratos", c);
        		request.getRequestDispatcher("contratos.jsp").forward(request, response);
    		}	
    		else {
    			System.out.println("Entra contratos");
        		ArrayList<Contrato> c = callCRM.getContract(session.getAttribute("accountId").toString());
        		request.setAttribute("contratos", c);
        		//System.out.println("set attribute contrato" + c.size());
    			request.getRequestDispatcher("contratos.jsp").forward(request, response);
    		}
    	}
    	else {
    		request.getRequestDispatcher("index.jsp").forward(request, response);
    	}
    	/*response.setContentType("text/plain");
    	response.getWriter().write("Cuenta:"+ m.get("acc_name") + "\n");
	      response.getWriter().write("Razón Social:"+ m.get("rs") + "\n");
	      response.getWriter().write("RFC: " + m.get("rfc"));*/
	      //response.sendRedirect("invoices.jsp");
	}
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
      }

}