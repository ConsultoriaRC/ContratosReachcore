package com.rc.controlador;

import com.rc.model.crm.callCRM;
import java.io.IOException;
import java.util.Map;

import com.rc.model.Cuenta;
import com.rc.model.IniciaSesion;

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

public class srvlt_menu extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String c = request.getParameter("correo");
    	String p = request.getParameter("passw");
    	if(c != null & p != null & session.getAttribute("usuario") == null) {
    		Map<String, String> r = IniciaSesion.iniciarSesion(c, p);
    		System.out.println("r -> " + r);
    		if(r != null) {
    			String Type = r.get("type");
    			if(Type.equals("success")) {
    				Cuenta account = callCRM.getAccount(r.get("accountId").toString(), r.get("userId").toString());
    				session.setAttribute("account", account);
    				session.setAttribute("accountId", r.get("accountId"));
    				session.setAttribute("correo", c);
    				//request.setAttribute("account", account);
    				request.getRequestDispatcher("menu.jsp").forward(request, response);
    	           
    			}
    			else {	
    				request.setAttribute("message", r.get("value"));
    				request.setAttribute("type", r.get("type"));
    				request.getRequestDispatcher("index.jsp").forward(request, response);
    			}
    		}
    		else {	
    			
    			request.getRequestDispatcher("index.jsp").forward(request, response);
    		}
    	}
    	else {
    		String correo = request.getParameter("correo");
    		String passw = request.getParameter("passw1");
    		if(correo != null & passw != null) {
    			Map<String, String> rs = IniciaSesion.registrarse(correo, passw);
    			System.out.println( rs.get("value"));
    			if(rs != null && rs.get("type").equals("success")) {
    				request.setAttribute("message", rs.get("value"));
    				request.setAttribute("type", rs.get("type"));
    				request.getRequestDispatcher("index.jsp").forward(request, response);
    			}
    			else {
    				request.setAttribute("message", rs.get("value"));
    				request.setAttribute("type", rs.get("type"));
    				request.getRequestDispatcher("registro.jsp").forward(request, response);
    			}
    		}
    		else {
    			String correor = request.getParameter("correorec");
        		if(correor != null) {
        			//Map<String, String> rs = IniciaSesion.registrarse(correo, passw);
        			
        			request.getRequestDispatcher("ConfirmacionRecuperarPassw.jsp").forward(request, response);
        		}
        		else {
        			request.getRequestDispatcher("index.jsp").forward(request, response);
        		}
    		}
		}
    }
	
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	//HttpSession session=request.getSession();  
        //session.setAttribute("name","test");  
    	
		//request.getSession().setAttribute("contratos", c);
    	
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