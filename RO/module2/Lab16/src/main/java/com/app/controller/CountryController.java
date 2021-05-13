package com.app.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.app.data.*;

@Path("/country")
public class CountryController {
		 
	@GET
	@Path("/read")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Country> getCountryById(){
		return CountryDAO.findAll();
	}
		    
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Country addCountry(@FormParam("name") String name){
		Country country = new Country();
		country.setName(name);
		CountryDAO.insert(country);
		return country;
	}
		 
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	//@Consumes(MediaType.APPLICATION_JSON)
	public Country updateCountry(Country country){
		//var country = new Country(id,name);  
		CountryDAO.update(country);
		return country;
	}
		 
	@DELETE
	//@Consumes(MediaType.APPLICATION_JSON)
	public void deleteCountry(Country country){
		Long id = country.getId();
		List<City> list = CityDAO.findByCountryId(id);
		for(City c: list) {
			CityDAO.delete(c);
		}
		CountryDAO.delete(country);
	}
}