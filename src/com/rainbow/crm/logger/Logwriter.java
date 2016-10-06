package com.rainbow.crm.logger;

import javax.servlet.ServletContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Logwriter {

	
	private void Logger(){
		
	}
	Logger logger  = LogManager.getLogger() ;
	
	public static Logwriter INSTANCE = new Logwriter(); ;
	
	public void instantiate(ServletContext ctx) {
			debug("initializing loggin features");
	}
	
	public void debug(String message) {
		System.out.println(message);
		logger.debug(message);
	}
	
	public void error(Exception ex) {
		ex.printStackTrace();
		logger.error(ex.getMessage());
	}

	
}
