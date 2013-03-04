package com.excilys.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.service.ComputerService;

@WebServlet("/DeleteComputerServlet")
public class DeleteComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ComputerService cd;
       
    public DeleteComputerServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = (Integer.parseInt(request.getParameter("id")));

		request.setAttribute("message", 3);
		request.setAttribute("nameMess", cd.findById(id).getName());
		
		cd.delete(id);

		this.getServletContext().getRequestDispatcher("/InitServlet").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}
