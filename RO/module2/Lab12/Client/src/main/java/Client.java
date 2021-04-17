
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
	private Socket sock = null;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private static final String split = "#";
	
	Client(String ip, int port) throws IOException{
		// establish connection
		sock = new Socket(ip,port);
		// get input/output streams
		in = new BufferedReader(
		new InputStreamReader(sock.getInputStream( )));
		out = new PrintWriter(sock.getOutputStream(), true);
	}
	
	public Country countryFindById(Long id) {
		var query = "CountryFindById"+split+id.toString();
		out.println(query);
		String response = "";
		try {
			response = in.readLine();
			return new Country(id,response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public City cityFindByName(String name) {
		var query = "CityFindByName"+split+name;
		out.println(query);
		String response = "";
		try {
			response = in.readLine();
			String[] fields = response.split(split);
			var id = Long.parseLong(fields[0]);
			var countryid = Long.parseLong(fields[1]);
			var population = Long.parseLong(fields[3]);
			return new City(id,countryid,name,population);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Country countryFindByName(String name) {
		var query = "CountryFindByName"+split+name;
		out.println(query);
		try {
			var response = Long.parseLong(in.readLine());
			return new Country(response,name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean cityUpdate(City city) {
		var query = "CityUpdate"+split+city.getId().toString()+
				"#"+city.getCountryid().toString()+"#"+city.getName()
				+"#"+city.getPopulation().toString();
		out.println(query);
		try {
			var response = in.readLine();
			if ("true".equals(response))
				return true;
			else
				return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean countryUpdate(Country country) {
		var query = "CountryUpdate"+split+country.getId().toString()+
				"#"+country.getName();
		out.println(query);
		try {
			var response = in.readLine();
			if ("true".equals(response))
				return true;
			else
				return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean cityInsert(City city) {
		var query = "CityInsert"+
				"#"+city.getCountryid().toString()+"#"+city.getName()
				+"#"+city.getPopulation().toString();
		out.println(query);
		try {
			var response = in.readLine();
			if ("true".equals(response))
				return true;
			else
				return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean countryInsert(Country country) {
		var query = "CountryInsert"+
				"#"+country.getName();
		out.println(query);
		try {
			var response = in.readLine();
			if ("true".equals(response))
				return true;
			else
				return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean countryDelete(Country country) {
		var query = "CountryDelete"+split+country.getId().toString();
		out.println(query);
		try {
			var response = in.readLine();
			if ("true".equals(response))
				return true;
			else
				return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean cityDelete(City city) {
		var query = "CityDelete"+split+city.getId().toString();
		out.println(query);
		try {
			var response = in.readLine();
			if ("true".equals(response))
				return true;
			else
				return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Country> countryAll(){
		var query = "CountryAll";
		out.println(query);
		var list = new ArrayList<Country>();
		try {
			var response = in.readLine();
			String[] fields = response.split(split);
			for(int i=0;i<fields.length; i+=2) {
				var id = Long.parseLong(fields[i]);
				var name = fields[i+1];
				list.add(new Country(id,name));
			}
			return list;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<City> cityAll(){
		var query = "CityAll";
		out.println(query);
		var list = new ArrayList<City>();
		try {
			var response = in.readLine();
			String[] fields = response.split(split);
			for(int i=0;i<fields.length; i+=4) {
				var id = Long.parseLong(fields[i]);
				var countryid = Long.parseLong(fields[i+1]);
				var name = fields[i+2];
				var population = Long.parseLong(fields[i+3]);
				list.add(new City(id,countryid,name,population));
			}
			return list;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<City> cityFindByCountryid(Long idc){
		var query = "CityFindByCountryid"+split+idc.toString();
		out.println(query);
		var list = new ArrayList<City>();
		try {
			var response = in.readLine();
			String[] fields = response.split(split);
			for(int i=0;i<fields.length; i+=4) {
				var id = Long.parseLong(fields[i]);
				var countryid = Long.parseLong(fields[i+1]);
				var name = fields[i+2];
				var population = Long.parseLong(fields[i+3]);
				list.add(new City(id,countryid,name,population));
			}
			return list;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void disconnect(){
		try {
			sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		//(new Client()).test("localhost",12345);
	}
}
