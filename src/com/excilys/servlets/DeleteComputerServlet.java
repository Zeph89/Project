package com.excilys.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.dao.ComputerDAO;
import com.excilys.dao.ComputerDaoImpl;
import com.excilys.dao.DAOFactory;

@WebServlet("/DeleteComputerServlet")
public class DeleteComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeleteComputerServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = (Integer.parseInt(request.getParameter("id")));

		DAOFactory daoFactory = DAOFactory.getInstance();
		ComputerDAO cd = new ComputerDaoImpl(daoFactory);
		
		request.setAttribute("message", 3);
		request.setAttribute("nameMess", cd.findById(id).getName());
		
		cd.delete(id);

		this.getServletContext().getRequestDispatcher("/InitServlet").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}