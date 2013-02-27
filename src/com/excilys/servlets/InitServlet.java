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
	
	public final int PAGE_SIZE = 10;
	
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

		if(request.getParameter("page") == null || request.getParameter("page").equals(""))
			request.setAttribute("page", 0);
		else
			request.setAttribute("page", Integer.parseInt(request.getParameter("page")));
		int page = (Integer)request.getAttribute("page");
		
		List<Computer> listc = null;
		
		if (request.getParameter("sort") != null) {
			request.setAttribute("page", 1);
			page = (Integer)request.getAttribute("page");
			
			int sort = Integer.parseInt(request.getParameter("sort"));
			
			if (request.getParameter("search") == null) {
				listc = cd.list(page*PAGE_SIZE, PAGE_SIZE, sort);
				request.setAttribute("nbComputer", cd.getNumberComputers());
			} else {
				listc = cd.list(page*PAGE_SIZE, PAGE_SIZE, request.getParameter("search"), sort);
				request.setAttribute("nbComputer", cd.getNumberComputers(request.getParameter("search")));
				request.setAttribute("search", request.getParameter("search"));
			}

			request.setAttribute("sort", sort*(-1));
		} else if (request.getParameter("search") == null) {
			listc = cd.list(page*PAGE_SIZE, PAGE_SIZE);
			request.setAttribute("nbComputer", cd.getNumberComputers());
		} else {
			listc = cd.list(page*PAGE_SIZE, PAGE_SIZE, request.getParameter("search"));
			request.setAttribute("nbComputer", cd.getNumberComputers(request.getParameter("search")));
			request.setAttribute("search", request.getParameter("search"));
		}

		request.setAttribute("computers", listc);
		
		if (request.getParameter("message") != null)
			request.setAttribute("message", Integer.parseInt(request.getParameter("message")));
		
		if (request.getParameter("nameMess") != null)
			request.setAttribute("nameMess", request.getParameter("nameMess"));
		
		request.setAttribute("PAGE_SIZE", PAGE_SIZE);
		
		this.getServletContext().getRequestDispatcher("/dashboard.jsp").forward(request, response);
	}

}