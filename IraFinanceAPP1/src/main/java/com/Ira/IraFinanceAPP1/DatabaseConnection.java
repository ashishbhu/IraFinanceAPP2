package com.Ira.IraFinanceAPP1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;



public class DatabaseConnection {
	
	final static Logger logger=Logger.getLogger(Implementation.class);
	public Connection getConnection()
	{
		
		Connection con=null;
	
	
	 
	
			String url="jdbc:mysql://localhost:3306/irafinance?useUnicode=true&characterEncoding=UTF-8";
			String username="root";
			String password="ashish";
	
				try {
					Class.forName("com.mysql.jdbc.Driver"); 
						con=DriverManager.getConnection(url,username,password);
    				}
					catch(ClassNotFoundException e)
						{
							logger.error("JDBC Driver Not Found");
						System.out.println("JDBC Driver Not Found");
						}
					catch(SQLException e)
				    {
						logger.error("error: adress, user, password?");
						System.out.println("error: adress, user, password?");
				    }
				    catch(NullPointerException e)
				    {
				    	logger.error("Null Pointer Exception: Error In Url");
				    }

			return con;
	}
}	