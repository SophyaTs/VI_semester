

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Map;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public class UserInterface extends JFrame{
	private static Client client = null;
	private static Country cCnt = null;
	private static City cCt = null;
	
	private static boolean editMode = false;
	private static boolean countryMode = true;
	
	private static JButton btnAddCountry = new JButton("Add Country");
	private static JButton btnAddCity= new JButton("Add City");
	private static JButton btnEdit= new JButton("Edit Data");
	private static JButton btnCancel= new JButton("Cancel");
	private static JButton btnSave= new JButton("Save");
	private static JButton btnDelete= new JButton("Delete");
	
	private static Box menuPanel = Box.createVerticalBox();
	private static Box actionPanel = Box.createVerticalBox();
	private static Box comboPanel = Box.createVerticalBox();
	private static Box cityPanel = Box.createVerticalBox();
	private static Box countryPanel = Box.createVerticalBox();
	
	private static JComboBox comboCountry = new JComboBox();
	private static JComboBox comboCity = new JComboBox();
	
	private static JTextField tfCountryName = new JTextField(30);
	private static JTextField tfCityName = new JTextField(30);
	private static JTextField tfCityCountryName = new JTextField(30);
	private static JTextField tfCityPopulation = new JTextField(30);
	
	private static JFrame frame;
	
	UserInterface(){
		super("World Map");
		frame = this;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	
		this.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent event) {
		    	frame.dispose();
		    	client.disconnect();
		        System.exit(0);
		    }
		});
		Box box = Box.createVerticalBox();

		// Menu
		menuPanel.add(btnAddCountry);
		btnAddCountry.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				editMode = false;
				countryMode = true;
				menuPanel.setVisible(false);
				comboPanel.setVisible(false);
				countryPanel.setVisible(true);
				cityPanel.setVisible(false);
				actionPanel.setVisible(true);
				//setContentPane(box);
				pack();
			}
		});
		menuPanel.add(btnAddCity);
		btnAddCity.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				editMode = false;
				countryMode = false;
				menuPanel.setVisible(false);
				comboPanel.setVisible(false);
				countryPanel.setVisible(false);
				cityPanel.setVisible(true);
				actionPanel.setVisible(true);
				//setContentPane(box);
				pack();
			}
		});
		menuPanel.add(btnEdit);
		btnEdit.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				editMode = true;
				menuPanel.setVisible(false);
				comboPanel.setVisible(true);
				countryPanel.setVisible(false);
				cityPanel.setVisible(false);
				actionPanel.setVisible(true);
				//setContentPane(box);
				pack();
			}
		});
		
		// ComboBoxes
		comboPanel.add(new JLabel("Country:"));
		comboPanel.add(comboCountry);
		comboCountry.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        String name = (String)comboCountry.getSelectedItem();
		        cCnt =client.countryFindByName((String) comboCountry.getSelectedItem());
		        countryMode = true;
		        countryPanel.setVisible(true);
				cityPanel.setVisible(false);
				fillCountryFields();
				//setContentPane(box);
				pack();
		    }
		});
		comboPanel.add(new JLabel("City:"));
		comboPanel.add(comboCity);
		comboCity.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        String name = (String)comboCity.getSelectedItem();
		        cCt = client.cityFindByName((String)comboCity.getSelectedItem());
		        countryMode = false;
		        countryPanel.setVisible(false);
				cityPanel.setVisible(true);
				fillCityFields();
				//setContentPane(box);
				pack();
		    }
		});
		fillComboBoxes();
		comboPanel.setVisible(false);
		
		// City Fields
		cityPanel.add(new JLabel("Name:"));
		cityPanel.add(tfCityName);
		cityPanel.add(new JLabel("Country Name:"));
		cityPanel.add(tfCityCountryName);
		cityPanel.add(new JLabel("Population:"));
		cityPanel.add(tfCityPopulation);
		cityPanel.setVisible(false);
		
		// Country Fields
		countryPanel.add(new JLabel("Name:"));
		countryPanel.add(tfCountryName);
		countryPanel.setVisible(false);
		
		// Action Bar		
		actionPanel.add(btnSave);
		btnSave.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				save();
			}
		});
		actionPanel.add(btnDelete);
		btnDelete.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				delete();
			}
		});
		actionPanel.add(btnCancel);
		btnCancel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				clearFields();
				menuPanel.setVisible(true);
				comboPanel.setVisible(false);
				countryPanel.setVisible(false);
				cityPanel.setVisible(false);
				actionPanel.setVisible(false);
				//setContentPane(box);
				pack();
			}
		});
		actionPanel.setVisible(false);
		
		clearFields();
		box.add(menuPanel);
		box.add(comboPanel);
		box.add(countryPanel);
		box.add(cityPanel);	
		box.add(actionPanel);
		setContentPane(box);
		pack();
	}
	
	private static void save() {
		if(editMode) {
			if(countryMode) {
				cCnt.setName(tfCountryName.getText());
				if (!client.countryUpdate(cCnt)) {
					JOptionPane.showMessageDialog(null, "Error: something went wrong!");
				}
			} else {
				cCt.setName(tfCityName.getText());
				Country cnt = client.countryFindByName(tfCityCountryName.getText());
				if(cnt == null) {
					JOptionPane.showMessageDialog(null, "Error: no such country!");
					return;
				}
				cCt.setCountryid(cnt.getId());
				cCt.setPopulation(Long.parseLong(tfCityPopulation.getText()));
				if (!client.cityUpdate(cCt)) {
					JOptionPane.showMessageDialog(null, "Error: something went wrong!");
				}
			}
		} else {
			if (countryMode) {
				var country = new Country();
				country.setName(tfCountryName.getText());				
				if(client.countryInsert(country)) {
					comboCountry.addItem(country.getName());
				}
			} else {
				var city = new City();
				city.setName(tfCityName.getText());
				Country cnt = client.countryFindByName(tfCityCountryName.getText());
				if(cnt == null) {
					JOptionPane.showMessageDialog(null, "Error: no such country!");
					return;
				}
				city.setCountryid(cnt.getId());
				city.setPopulation(Long.parseLong(tfCityPopulation.getText()));
				if(client.cityInsert(city)) {
					comboCity.addItem(city.getName());
				}
			}
		}
	}
	
	private static void delete() {
		if(editMode) {
			if(countryMode) {				
				var list = client.cityFindByCountryid(cCnt.getId());
				for(City c: list) {
					comboCity.removeItem(c.getName());
					client.cityDelete(c);
				}
				client.countryDelete(cCnt);
				comboCountry.removeItem(cCnt.getName());	
				
			} else {
				client.cityDelete(cCt);
				comboCity.removeItem(cCt.getName());
			}
		}
	}
	
	private void fillComboBoxes() {
		comboCountry.removeAllItems();
		comboCity.removeAllItems();
		var countries = client.countryAll();
		var cities = client.cityAll();
		for(Country c: countries) {
			comboCountry.addItem(c.getName());
		}
		for(City c: cities) {
			comboCity.addItem(c.getName());
		}
	}
	
	private static void clearFields() {
		tfCountryName.setText("");
		tfCityName.setText("");
		tfCityCountryName.setText("");
		tfCityPopulation.setText("");
		cCnt = null;
		cCt = null;
	}
	
	private static void fillCountryFields() {
		if (cCnt == null)
			return;
		tfCountryName.setText(cCnt.getName());
	}
	private static void fillCityFields() {
		if(cCt == null)
			return;
		Country cnt = client.countryFindById(cCt.getCountryid());
		tfCityName.setText(cCt.getName());
		tfCityCountryName.setText(cnt.getName());
		tfCityPopulation.setText(String.valueOf(cCt.getPopulation()));
	}
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		client = new Client("localhost",12345);
		JFrame myWindow = new UserInterface();		
		myWindow.setVisible(true);
	}
}
