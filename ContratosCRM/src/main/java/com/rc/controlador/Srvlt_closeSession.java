package com.rc.controlador;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * Servlet implementation class SimpleServlet
 */

public class Srvlt_closeSession extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		 PrintWriter out=response.getWriter();  
		
		File f = new File("src\\main\\webapp\\tmp\\"+ session.getAttribute("accountId"));
		if(f.isDirectory())
			f.delete();
		session.invalidate();
    	out.print("Has cerrado sesión.");
    	request.getRequestDispatcher("index.jsp").forward(request, response);
    			
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