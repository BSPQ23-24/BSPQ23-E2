package com.deusto.app.server.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BikeServerManager implements ActionListener, Runnable {

	protected static final Logger logger = LogManager.getLogger();
	
	public BikeServerManager(String hostname, String port) {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		String hostname = args[0];
		String port = args[1];

		logger.info("Ejemplo de log");
		new BikeServerManager(hostname, port);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}