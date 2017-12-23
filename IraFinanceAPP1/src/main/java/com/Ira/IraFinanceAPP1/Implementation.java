package com.Ira.IraFinanceAPP1;

import java.sql.*;


import org.apache.log4j.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



		
public class Implementation{			
/*1------------------------OK----- FOR REGISTER NEW USER------------------------------*/
	
	 
	  final static Logger logger=Logger.getLogger(Implementation.class);
	 
	  
		public String create(RegisterUser r2) {
			
			
			logger.info("User Address is  "+r2.getAddress());
			
			
			DatabaseConnection db=new DatabaseConnection();
			Connection con=db.getConnection();
			
			int flag=0,flag1=0;
			
			JSONObject jo = new JSONObject();
			
			
		 
			 /*------Checking Mobile or Gst already exist-------*/
			String login= "select mobilenumber,gstnumber from registration";
			
			try
			{
				Statement st=con.createStatement();
				ResultSet rs=st.executeQuery(login);
		    
		    		while(rs.next())
		    			if(rs.getString(1).equals(r2.getMobilenumber()))
		    				flag=1;
		    					if(flag==1)
		    					{
		    						jo.put("uname", "null");
		    						jo.put("check", "mexist");
		    						return jo.toString();
		    					}
		    						
		    
		    	Statement st1=con.createStatement();
		    	ResultSet rs1=st1.executeQuery(login);
		    
		    		while(rs1.next())
		    			if(rs1.getString(2).equals(r2.getGstnumber()))
		    				flag=2;
		    					if(flag==2)
		    					{
		    						jo.put("uname", "null");
		    						jo.put("check", "gexist");
		    						return jo.toString();
		    					}
		    						
		    
			} 
			catch(Exception e)
				{	flag=3;
					System.out.println(e);
				    
				}
				if(flag==3)
					return "error1";
			
		    	
		    	
               /*-----------Insert Data into Registration Table-------------------*/
		    
		    	
		    String reg="insert into registration(pswd,shopname,"
						+ "address,mobilenumber,emailid,gstnumber,acctlocked,"
						+ "subStartdate,subEnddate)  values(?,?,?,?,?,?,?,?,?)";
			
		    try 
		    {
		    	
		    	PreparedStatement st2=con.prepareStatement(reg);
			
		    	
		    	st2.setString(1, r2.getPassword());
		    	st2.setString(2, r2.getShopname());
		    	st2.setString(3, r2.getAddress());
			
		    	st2.setString(   4,  r2.getMobilenumber());
		    	st2.setString(5, r2.getEmailid());
		    	
		    	st2.setString(6, r2.getGstnumber());
		    	st2.setString(7, r2.getAcctlocked());
		    	
		    	st2.setString(8, r2.getSubstartdate());
		    	st2.setString(9, r2.getSubenddate());
			
		    	st2.executeUpdate();
		
			    flag=4;
			}
			catch(Exception e)
				{
					flag=5;
					System.out.println(e);
				}
		    	if(flag==5)
		    			return "error2";
			
			/*----------Inserting Data into Logincontrol---------*/
		    	
		    	int id=0;
		    	if(flag==4)
		    		{	/*--Getting Subid from registration as username------------*/
		    			 String mo=r2.getMobilenumber();
		    			 
		    		     String regis="select subid from registration where mobilenumber="+mo;
		    		     
		    		     try
		    		     {
		    		    	 Statement st=con.createStatement();
		    		    	 ResultSet rs=st.executeQuery(regis);
		    		    	 
		    		    	 rs.next();
		    		    	 id=rs.getInt(1);
		    		    	 
		    		    	 jo.put("uname", id);
		    		    	 jo.put("check", "success");
		    		     }
		    		     catch(Exception e)
		    		     {   flag=6;
		    		    	 System.out.println(e);
		    		     }
		    		     	if(flag==6)
		    		     		return "error3";
		    		     
		    		     	
		    			String log="insert into logincontrol(username,pswd,acctlocked,forcechgpwd,access,forcelogin)"
							+ "values(?,?,?,?,?,?)";
		    			
		    			try
		    			{
		    				PreparedStatement st1=con.prepareStatement(log);
		    				
		    				st1.setInt(1, id);
		    				st1.setString(2, r2.getPassword());
		    				st1.setString(3, r2.getAcctlocked());
		    				st1.setString(4, "false");
		    				st1.setInt(5, 15);
		    				st1.setString(6, "true");
		    				
		    				st1.executeUpdate();
		    				
		    				flag1=8;
		    			}
		    			catch(Exception e)
		    			{	flag=7;
		    				System.out.println(e);
		    			}
		    				if(flag==7)
		    					return "error4";
		    		}
		    	else 
		    		return "notregister";
			
		    	
		    	
		    	if(flag==4 && flag1==8)
		    		return jo.toString();
		    	System.out.println(id);
		    	return "error5";
		
	
		}
		
		
 
 
}