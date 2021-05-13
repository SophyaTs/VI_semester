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

import com.data.City;
import com.data.CityDAO;
import com.data.Country;
import com.data.CountryDAO;

/**
 * Servlet implementation class CityCRUDServlet
 */
//@WebServlet("/CityCRUDServlet")
public class CityCRUDServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CityCRUDServlet() {
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
		String action = url.substring(5);
        try {
            switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "insert":
                insertCity(request, response);
                break;
            case "delete":
                deleteCity(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "update":
                updateCity(request, response);
                break;
            case "list":
                listCity(request, response);
                break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
	}

	private void listCity(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<City> listCity = CityDAO.findAll();
        request.setAttribute("listCity", listCity);
        RequestDispatcher dispatcher = request.getRequestDispatcher("CityList.jsp");
        dispatcher.forward(request, response);
    }
 
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("CityForm.jsp");
        dispatcher.forward(request, response);
    }
 
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        City existingCity = CityDAO.findById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("CityForm.jsp");
        request.setAttribute("city", existingCity);
        dispatcher.forward(request, response);
 
    }
 
    private void insertCity(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String name = request.getParameter("name");
        Long countryid = Long.parseLong(request.getParameter("countryid"));
        Long population = Long.parseLong(request.getParameter("population"));
 
        City newCity = new City();
        newCity.setName(name);
        newCity.setCountryid(countryid);
        newCity.setPopulation(population);
        CityDAO.insert(newCity);
        response.sendRedirect("citylist");
    }
 
    private void updateCity(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        Long countryid = Long.parseLong(request.getParameter("countryid"));
        Long population = Long.parseLong(request.getParameter("population"));
 
        City city = new City(id, countryid,name,population);
        CityDAO.update(city);
        response.sendRedirect("citylist");
    }
 
    private void deleteCity(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        City city = new City();
        city.setId(id);
        CityDAO.delete(city);
        response.sendRedirect("citylist");
 
    }

}
