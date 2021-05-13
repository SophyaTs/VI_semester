package com.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.data.*;

/**
 * Servlet implementation class CountryCRUDServlet
 */
//@WebServlet("/CountryCRUDServlet")
public class CountryCRUDServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CountryCRUDServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		var url = request.getServletPath();		
		String action = url.substring(8);
        try {
            switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "insert":
                insertCountry(request, response);
                break;
            case "delete":
                deleteCountry(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "update":
                updateCountry(request, response);
                break;
            case "list":
                listCountries(request, response);
                break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
	}

	private void listCountries(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Country> listCountry = CountryDAO.findAll();
        request.setAttribute("listCountry", listCountry);
        RequestDispatcher dispatcher = request.getRequestDispatcher("CountryList.jsp");
        dispatcher.forward(request, response);
    }
 
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("CountryForm.jsp");
        dispatcher.forward(request, response);
    }
 
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        Country existingCountry = CountryDAO.findById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("CountryForm.jsp");
        request.setAttribute("country", existingCountry);
        dispatcher.forward(request, response);
 
    }
 
    private void insertCountry(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String name = request.getParameter("name");
 
        Country newCountry = new Country();
        newCountry.setName(name);
        CountryDAO.insert(newCountry);
        response.sendRedirect("countrylist");
    }
 
    private void updateCountry(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
 
        Country country = new Country(id, name);
        CountryDAO.update(country);
        response.sendRedirect("countrylist");
    }
 
    private void deleteCountry(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        var list = CityDAO.findByCountryId(id);
		for(City c: list) {
			CityDAO.delete(c);
		}
        Country country = new Country();
        country.setId(id);
        CountryDAO.delete(country);
        response.sendRedirect("countrylist");
 
    }
}
