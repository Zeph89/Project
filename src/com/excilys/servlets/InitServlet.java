package com.excilys.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.beans.Computer;
import com.excilys.dao.ComputerDAO;
import com.excilys.dao.ComputerDaoImpl;
import com.excilys.dao.DAOFactory;

@WebServlet("/InitServlet")
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public InitServlet() {
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
		DAOFactory daoFactory = DAOFactory.getInstance();
		ComputerDAO cd = new ComputerDaoImpl(daoFactory);

		List<Computer> listc = null;
		if (request.getParameter("search") == null)
			listc = cd.list();
		else
			listc = cd.list(request.getParameter("search"));

		request.setAttribute("computers", listc);
		
		if (request.getParameter("message") != null) {
			request.setAttribute("message", Integer.parseInt(request.getParameter("message")));
			System.out.println(request.getParameter("message"));
		}
		
		if (request.getParameter("nameMess") != null)
			request.setAttribute("nameMess", request.getParameter("nameMess"));
		
		this.getServletContext().getRequestDispatcher("/dashboard.jsp").forward(request, response);
	}

}
