package com.excilys.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.beans.Computer;
import com.excilys.dao.CompanyDAO;
import com.excilys.dao.CompanyDaoImpl;
import com.excilys.dao.ComputerDAO;
import com.excilys.dao.ComputerDaoImpl;
import com.excilys.dao.DAOFactory;

@WebServlet("/UpdateComputerServlet")
public class UpdateComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateComputerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = (Integer.parseInt(request.getParameter("id")));
		String name = request.getParameter("name");
		String introducedDate = request.getParameter("introduced");
		String discontinuedDate = request.getParameter("discontinued");
		String companyS = request.getParameter("company");
		
		DAOFactory daoFactory = DAOFactory.getInstance();
		ComputerDAO cd = new ComputerDaoImpl(daoFactory);
		Computer c = cd.findById(id);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		boolean error = false;
		
		if (introducedDate.equals("") == false) {
			try {
				dateFormat.parse(introducedDate);
				request.setAttribute("introducedE", 0);
			} catch (ParseException e) {
				request.setAttribute("introducedE", 1);
				error = true;
			}
		}
		
		if (discontinuedDate.equals("") == false) {
			try {
				dateFormat.parse(discontinuedDate);	
				request.setAttribute("discontinuedE", 0);
			} catch (ParseException e) {
				request.setAttribute("discontinuedE", 1);
				error = true;
			}
		}
		
		if (!error) {
			int companyId = -1;
			if (companyS.equals("") == false)
				companyId = (Integer.parseInt(request.getParameter("company")));
			
			cd.update(c, name, introducedDate, discontinuedDate, companyId);
			
			request.setAttribute("message", 2);
			request.setAttribute("nameMess", c.getName());
	
			this.getServletContext().getRequestDispatcher("/InitServlet").forward(request, response);
		} else {
			request.setAttribute("id", c.getId());
			request.setAttribute("name", name);
			request.setAttribute("introducedDate", introducedDate);
			request.setAttribute("discontinuedDate", discontinuedDate);
			request.setAttribute("companyId", c.getCompany().getId());
			
			CompanyDAO cy = new CompanyDaoImpl(daoFactory);
			request.setAttribute("companies", cy.list());
			this.getServletContext().getRequestDispatcher("/updateComputer.jsp").forward(request, response);
		}
	}
}