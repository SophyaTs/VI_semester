import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private ServerSocket server = null;
	private Socket sock = null;
	private PrintWriter out = null;
	private BufferedReader in = null;

	public void start(int port) throws IOException {
		server = new ServerSocket(port);
		while (true) {
			sock = server.accept();
			in = new BufferedReader(new InputStreamReader(sock.getInputStream( )));
			out = new PrintWriter(sock.getOutputStream(), true);
			while (processQuery());
		}
	}
	
	private boolean processQuery() {
		int result = 0;
		String response = "";
		try {
			String query = in.readLine();
			if (query==null) 
				return false;
			
			String[] fields = query.split("#");
			if (fields.length == 0){
				return true;
			} else {
				var action = fields[0];
				Country country;
				City city;	
				
				System.out.println(action);
				
				switch(action) {
				case "CountryFindById":
					var id = Long.parseLong(fields[1]);
					country = CountryDAO.findById(id);
					response = country.getName();
					System.out.println(response);
					out.println(response);
					break;
				case "CityFindByCountryid":
					id = Long.parseLong(fields[1]);
					var list = CityDAO.findByCountryId(id);
					var str = new StringBuilder();
					for(City c: list) {
						str.append(c.getId());
						str.append("#");
						str.append(c.getCountryid());
						str.append("#");
						str.append(c.getName());
						str.append("#");
						str.append(c.getPopulation());
						str.append("#");
					}
					response = str.toString();
					System.out.println(response);
					out.println(response);
					break;
				case "CityFindByName":
					var name = fields[1];
					city = CityDAO.findByName(name);
					response = city.getId().toString()+"#"+city.getCountryid().toString()+"#"+city.getName()+"#"+city.getPopulation().toString();
					System.out.println(response);
					out.println(response);
					break;
				case "CountryFindByName":
					name = fields[1];
					country = CountryDAO.findByName(name);
					response = country.getId().toString();
					System.out.println(response);
					out.println(response);
					break;
				case "CityUpdate":
					id = Long.parseLong(fields[1]);
					var countryid = Long.parseLong(fields[2]); 
					name = fields[3];
					var population = Long.parseLong(fields[4]); 
					city = new City(id,countryid,name,population);
					if(CityDAO.update(city))
						response = "true";
					else
						response = "false";
					System.out.println(response);
					out.println(response);
					break;
				case "CountryUpdate": 
					id = Long.parseLong(fields[1]);
					name = fields[2];
					country = new Country(id,name);
					if (CountryDAO.update(country))
						response = "true";
					else
						response = "false";
					System.out.println(response);
					out.println(response);
					break;
				case "CityInsert":
					countryid = Long.parseLong(fields[1]); 
					name = fields[2];
					population = Long.parseLong(fields[3]); 
					city = new City((long) 0,countryid,name,population);
					if(CityDAO.insert(city))	
						response = "true";
					else
						response = "false";
					System.out.println(response);
					out.println(response);
					break;
				case "CountryInsert":
					name = fields[1];
					country = new Country();
					country.setName(name);
					
					System.out.println(name);
					
					if(CountryDAO.insert(country))
						response = "true";
					else
						response = "false";
					System.out.println(response);
					out.println(response);
					break;
				case "CityDelete":
					id = Long.parseLong(fields[1]);
					city = new City();
					city.setId(id);
					if(CityDAO.delete(city))
						response = "true";
					else
						response = "false";
					System.out.println(response);
					out.println(response);
					break;
				case "CountryDelete":
					id = Long.parseLong(fields[1]);
					country = new Country();
					country.setId(id);
					if(CountryDAO.delete(country))
						response = "true";
					else
						response = "false";
					System.out.println(response);
					out.println(response);
					break;
				case "CityAll":
					var list1 = CityDAO.findAll();
					str = new StringBuilder();
					for(City c: list1) {
						str.append(c.getId());
						str.append("#");
						str.append(c.getCountryid());
						str.append("#");
						str.append(c.getName());
						str.append("#");
						str.append(c.getPopulation());
						str.append("#");
					}
					response = str.toString();
					System.out.println(response);
					out.println(response);
					break;
				case "CountryAll":
					var list2 = CountryDAO.findAll();
					str = new StringBuilder();
					for(Country c: list2) {
						str.append(c.getId());
						str.append("#");
						str.append(c.getName());
						str.append("#");
					}
					response = str.toString();
					System.out.println(response);
					out.println(response);
					break;
				}
			}
			
			return true;
		}
		catch(IOException e){
			return false;
		}
	}
	public static void main(String[] args) {
		try {
			Server srv = new Server();
			srv.start(12345);
		} catch(IOException e) {
			System.out.println("Возникла ошибка");
		}
	}
}
