package com.excilys.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.beans.Computer;
import com.excilys.service.CompanyService;
import com.excilys.service.CompanyServiceImpl;
import com.excilys.service.ComputerService;
import com.excilys.service.ComputerServiceImpl;

@WebServlet("/InfoComputerServlet")
public class InfoComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public InfoComputerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	
	private void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = (Integer.parseInt(request.getParameter("id")));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		ComputerService cd = new ComputerServiceImpl();
		CompanyService cy = new CompanyServiceImpl();

		Computer c = cd.findById(id);
		request.setAttribute("id", c.getId());
		request.setAttribute("name", c.getName());
		
		if (c.getIntroducedDate() != null)
			request.setAttribute("introducedDate", dateFormat.format(c.getIntroducedDate()));
		else
			request.setAttribute("introducedDate", "");
		
		if (c.getDiscontinuedDate() != null)
			request.setAttribute("discontinuedDate", dateFormat.format(c.getDiscontinuedDate()));
		else
			request.setAttribute("discontinuedDate", "");
		
		request.setAttribute("companyId", c.getCompany().getId());
		request.setAttribute("companies", cy.list());
		
		this.getServletContext().getRequestDispatcher("/updateComputer.jsp").forward(request, response);
	}

}
