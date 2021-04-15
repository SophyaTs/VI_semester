package mod2lab1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WorldMap {
	private Map<String,Country> countries = new HashMap<>();
	private Map<String,String> countryNames = new HashMap<>();
	private Map<String,City> cities = new HashMap<>();
	private Map<String,String> cityNames = new HashMap<>();
	
	public void generateId(Country c) {
		var id = countries.size();	
		String idStr = "x"+id;
		while(countries.get(idStr) != null) {
			id++;
			idStr = "x"+id;			
		}
		c.setId(idStr);
	}
	public void generateId(City c) {
		var id = cities.size();	
		String idStr = "x"+id;
		while(cities.get(idStr) != null) {
			id++;
			idStr = "x"+id;			
		}
		c.setId(idStr);
	}
	
	public void addCountry(Country c) {
		var changed = false;
		if(countries.get(c.getId())!=null) {
			changed = true;
			generateId(c);
		}
		if (changed) {
			for(City ct: c.getCities())
				ct.setCountryId(c.getId());
		}
		countries.put(c.getId(), c);
		countryNames.put(c.getName(), c.getId());
	}
	
	public void removeCountry(Country c) {
		countries.remove(c.getId());
		countryNames.remove(c.getName());
		for(City ct: c.getCities())
			cities.remove(ct.getId());
	}
	
	public void addCity(City c) {
		if(cities.get(c.getId())!=null) {
			generateId(c);
		}
		cities.put(c.getId(),c);
		countries.get(c.getCountryId()).getCities().add(c);
		cityNames.put(c.getName(), c.getId());
	}
	
	public void removeCity(City c) {
		cities.remove(c.getId());
		cityNames.remove(c.getName());
		countries.get(c.getCountryId()).getCities().remove(c);
	}
	
	public void transferCity(City city, Country country) {
		var old = countries.get(city.getCountryId());
		if (old != null)
			old.getCities().remove(city);
		city.setCountryId(country.getId());
		country.getCities().add(city);
	}
	
	public void rename(Country c, String name) {
		countryNames.remove(c.getName());
		c.setName(name);
		countryNames.put(c.getName(),c.getId());
	}
	public void rename(City c, String name) {
		cityNames.remove(c.getName());
		c.setName(name);
		cityNames.put(c.getName(),c.getId());
	}

	
	public Country getCountry(String name) {
		var id = countryNames.get(name);
		if(id != null)
			return countries.get(id);
		return null;
	}
	public City getCity(String name) {
		var id = cityNames.get(name);
		if(id != null)
			return cities.get(id);
		return null;
	}
}
