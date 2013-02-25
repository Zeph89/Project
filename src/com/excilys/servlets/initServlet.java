package com.excilys.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.beans.Company;
import com.excilys.beans.Computer;
import com.excilys.dao.CompanyDAO;
import com.excilys.dao.CompanyDaoImpl;
import com.excilys.dao.ComputerDAO;
import com.excilys.dao.ComputerDaoImpl;
import com.excilys.dao.DAOFactory;


@WebServlet("/initServlet")
public class initServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public initServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DAOFactory daoFactory = DAOFactory.getInstance();
		ComputerDAO cd = new ComputerDaoImpl(daoFactory);

		List<Computer> listc = cd.list();
		request.setAttribute("computers", listc);
		
		System.out.println(listc);
		this.getServletContext().getRequestDispatcher("/dashboard.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
