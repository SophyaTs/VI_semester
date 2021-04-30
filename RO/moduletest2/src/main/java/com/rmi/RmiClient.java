package com.rmi;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.cli.CommandLineInterface;

public class RmiClient {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, NotBoundException {
		String url = "//localhost:123/Module2";
		var server = (RmiServer) Naming.lookup(url);
		System.out.println("RMI object found");
		var cli = new CommandLineInterface(server);
		cli.interact_blin_gejidgjididi(args);
	}
}
