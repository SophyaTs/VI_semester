package com.app.controller;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.app.data.CityDAO;
import com.app.data.City;

@Path("/city")
public class CityController {
	@GET
	@Path("/read")
	@Produces(MediaType.APPLICATION_JSON)
	public List<City> getCityById(){
		return CityDAO.findAll();
	}
		    
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public City addCity(City city){
		CityDAO.insert(city);
		return city;
	}
		 
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	//@Consumes(MediaType.APPLICATION_JSON)
	public City updateCity(City city){ 
		CityDAO.update(city);
		return city;
	}
		 
	@DELETE
	//@Consumes(MediaType.APPLICATION_JSON)
	public void deleteCity(City city){
		CityDAO.delete(city);
	}
}
