package com.miniproject.dboperation;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConnection {
	
	public Connection getConnection() {
		Connection connection = null;
		FileInputStream fis = null;
		String filePath = "D:\\MiniProject2\\src\\com\\miniproject\\dboperation\\Database Variables.properties";
		try {
			fis = new FileInputStream(filePath);
			Properties properties= new Properties(); 
			properties.load(fis);
			Class.forName(properties.getProperty("driver"));
			connection = DriverManager.getConnection(properties.getProperty("databaselink")+
													properties.getProperty("databasename")+
													"?autoReconnect=true&useSSL=false",
													properties.getProperty("username"),
													properties.getProperty("password"));
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;

	}

}
