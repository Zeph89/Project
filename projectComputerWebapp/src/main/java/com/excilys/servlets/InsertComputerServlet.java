package com.excilys.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.beans.Computer;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

@WebServlet("/InsertComputerServlet")
public class InsertComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Autowired
	private ComputerService cd;
	
	@Autowired
	private CompanyService cy;
	
    public InsertComputerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String introducedDate = request.getParameter("introduced");
		String discontinuedDate = request.getParameter("discontinued");
		String companyS = request.getParameter("company");
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		boolean error = false;
		
		Date introduced = null;
		Date discontinued = null;
		if (introducedDate.equals("") == false) {
			try {
				introduced = dateFormat.parse(introducedDate);
				request.setAttribute("introducedE", 0);
			} catch (ParseException e) {
				request.setAttribute("introducedE", 1);
				error = true;
			}
		}
		
		if (discontinuedDate.equals("") == false) {
			try {
				discontinued = dateFormat.parse(discontinuedDate);	
				request.setAttribute("discontinuedE", 0);
			} catch (ParseException e) {
				request.setAttribute("discontinuedE", 1);
				error = true;
			}
		}
		
		int companyId = 0;
		if (companyS.equals("") == false)
			companyId = (Integer.parseInt(request.getParameter("company")));
		
		if (!error) {
			Computer c = new Computer();
			c.setName(name);
			c.setIntroducedDate(introduced);
			c.setDiscontinuedDate(discontinued);
			
			if (companyId != 0)
				c.setCompany(cy.findById(companyId));
			else
				c.setCompany(null);
		
			cd.create(c);
			
			request.setAttribute("message", 1);
			request.setAttribute("nameMess", c.getName());

			this.getServletContext().getRequestDispatcher("/InitServlet").forward(request, response);
		} else {
			request.setAttribute("name", name);
			request.setAttribute("introducedDate", introducedDate);
			request.setAttribute("discontinuedDate", discontinuedDate);
			request.setAttribute("companyId", companyId);
			
			request.setAttribute("companies", cy.list());
			this.getServletContext().getRequestDispatcher("/insertComputer.jsp").forward(request, response);
		}
	}
}
