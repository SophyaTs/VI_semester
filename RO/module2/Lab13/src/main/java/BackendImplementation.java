
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class BackendImplementation extends UnicastRemoteObject implements Backend {

	protected BackendImplementation() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Country countryFindById(Long id) throws RemoteException {
		return CountryDAO.findById(id);
	}

	@Override
	public City cityFindByName(String name) throws RemoteException {
		return CityDAO.findByName(name);
	}

	@Override
	public Country countryFindByName(String name) throws RemoteException {
		return CountryDAO.findByName(name);
	}

	@Override
	public boolean cityUpdate(City city) throws RemoteException {
		return CityDAO.update(city);
	}

	@Override
	public boolean countryUpdate(Country country) throws RemoteException {
		return CountryDAO.update(country);
	}

	@Override
	public boolean cityInsert(City city) throws RemoteException {
		return CityDAO.insert(city);
	}

	@Override
	public boolean countryInsert(Country country) throws RemoteException {
		return CountryDAO.insert(country);
	}

	@Override
	public boolean countryDelete(Country country) throws RemoteException {
		return CountryDAO.delete(country);
	}

	@Override
	public boolean cityDelete(City city) throws RemoteException {
		return CityDAO.delete(city);
	}

	@Override
	public List<Country> countryAll() throws RemoteException {
		return CountryDAO.findAll();
	}

	@Override
	public List<City> cityAll() throws RemoteException {
		return CityDAO.findAll();
	}

	@Override
	public List<City> cityFindByCountryid(Long idc) throws RemoteException {
		return CityDAO.findByCountryId(idc);
	}

	
	public static void main(String[] args) throws RemoteException, MalformedURLException {
			var bck = new BackendImplementation();
			Registry registry = LocateRegistry.createRegistry(123);
			registry.rebind("Lab13", bck);
			System.out.println("Server started!");
	}
}
