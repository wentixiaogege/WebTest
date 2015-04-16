package com.itu.util;


import java.sql.DriverManager;
import java.util.Properties;

import com.mysql.jdbc.Connection;
public class JDBCUtil {
	  // The JDBC Connector Class.
	  private static final String dbClassName = "com.mysql.jdbc.Driver";
	  
	  private static final String CONNECTION = "jdbc:mysql://127.0.0.1:3306/emsdb";
	  public static Connection getJDBCConnection() {
		  try {
		  	Class.forName(dbClassName);

		    // Properties for user and password. Here the user and password are both 'paulr'
		    Properties p = new Properties();
		    p.put("user","root");
		    p.put("password","355itu11");

		    // Now try to connect
		    return  (Connection) DriverManager.getConnection(CONNECTION,p);
		  }catch (Throwable ex) {
			  System.err.println("Initial SessionFactory creation failed." + ex);
	            throw new ExceptionInInitializerError(ex);
		  }
		     
	}
}
