package mod2lab1;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class DOMParser {
	public static class SimpleErrorHandler implements ErrorHandler {
			public void warning(SAXParseException e) throws SAXException {
				System.out.println("Line " +e.getLineNumber() + ":");
				System.out.println(e.getMessage());
			}
			public void error(SAXParseException e) throws SAXException {
				System.out.println("Line " +e.getLineNumber() + ":");
				System.out.println(e.getMessage());
			}
			public void fatalError(SAXParseException e) throws SAXException {
				System.out.println("Line " +e.getLineNumber() + ":");
				System.out.println(e.getMessage());
			}
		}
	
	public static WorldMap parse(String path) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		builder.setErrorHandler(new SimpleErrorHandler());
		Document doc = builder.parse(new File(path));
		doc.getDocumentElement().normalize();
		
		var map = new WorldMap();
		NodeList nodes = doc.getElementsByTagName("country");
		
		for(int i = 0; i < nodes.getLength(); ++i) {
			Element n = (Element)nodes.item(i);
			var country = new Country();
			country.setId(n.getAttribute("id"));
			country.setName(n.getAttribute("name"));
			map.addCountry(country);
			
		}
		
		nodes = doc.getElementsByTagName("city");
		for(int j =0; j < nodes.getLength(); ++j) {
			Element e = (Element) nodes.item(j);
			var city = new City();
			city.setId(e.getAttribute("id"));
			city.setCountryId(e.getAttribute("countryId"));
			city.setName(e.getAttribute("name"));
			city.setPopulation(Integer.parseInt(e.getAttribute("population")));
			map.addCity(city);
		}
		
		return map;
	}
	
	public static void write(WorldMap map, String path) throws ParserConfigurationException, TransformerException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		var doc = builder.newDocument();
		Element root = doc.createElement("map");
		doc.appendChild(root);
		
		var countries = map.getCountries();
		for(Map.Entry<String, Country> entry : countries.entrySet()) {
			Element ctr = doc.createElement("country");
			ctr.setAttribute("id", entry.getValue().getId());
			ctr.setAttribute("name", entry.getValue().getName());
			root.appendChild(ctr);
			
			for(City city: entry.getValue().getCities()) {
				Element ct = doc.createElement("city");
				ct.setAttribute("id", city.getId());
				ct.setAttribute("countryId", city.getCountryId());
				ct.setAttribute("name", city.getName());
				ct.setAttribute("population", String.valueOf(city.getPopulation()));
				ctr.appendChild(ct);
			}
		}
		Source domSource = new DOMSource(doc);
		Result fileResult = new StreamResult(new File(path));
		TransformerFactory tfactory = TransformerFactory.newInstance();
		Transformer transformer = tfactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"map.dtd");
		transformer.transform(domSource, fileResult);
	}
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		var map = parse("src/main/java/mod2lab1/map.xml");
		var city = new City("id6","x2","Geneva",198000);
		//map.addCity(city);
		write(map,"src/main/java/mod2lab1/map.xml");
	}
}
