package com.rc.controlador;

import com.rc.model.crm.callCRM;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.rc.model.Factura;

/**
 * Servlet implementation class Srvlt_facturas
 */
public class Srvlt_facturas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Srvlt_facturas() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(); 
		System.out.println("Entra a srlvt fact");
		if(session != null) {
			String cId = request.getParameter("contractId");
			
			System.out.println("Contract ID: " + cId);
			System.out.println("Contract Nm: " + request.getParameter("contractNm"));
			if (cId != null) {
				ArrayList<Factura> c = callCRM.getContractInvoices(cId);
				//request.setAttribute("contractNm", request.getParameter("contractNm"));
				request.setAttribute("invoices", c);
				request.getRequestDispatcher("invoices.jsp").forward(request, response);
			}
			
		}
		else {
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
	}

}
