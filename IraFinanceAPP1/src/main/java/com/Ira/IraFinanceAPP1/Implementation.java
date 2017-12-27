package com.Ira.IraFinanceAPP1;

import java.sql.*;


import org.apache.log4j.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



		
public class Implementation{			
/*1------------------------OK----- FOR REGISTER NEW USER------------------------------*/
	
	 
	  final static Logger logger=Logger.getLogger(Implementation.class);
	 
	  
		public String create(RegisterUser userdetails) {
			
			
			DatabaseConnection db=new DatabaseConnection();
			Connection con=db.getConnection();
			
			
			
			int flag=0,flag1=0;
			
			
			
			
		 
			 /*------Checking Mobile or Gst already exist-------*/
			String login= "select mobilenumber,gstnumber from registration";
			ResultSet rs=null;
			Statement st=null;
			try
			{
				JSONObject jo = new JSONObject();
				
				st=con.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			    rs=st.executeQuery(login);
			
			
				int inserting=0;
				
				
					if(rs.next()==false)
					{
						
					}
					else
					{
						
						rs.previous();
						
						while(rs.next())
							if(rs.getString(1).equals(userdetails.getMobilenumber()))
								flag=1;
		    						if(flag==1)
		    						{
		    							jo.put("uname", "null");
		    							jo.put("check", "mexist");
		    							
		    							logger.error("mobile number exist");
		    							return jo.toString();
		    							
		    						}
		    						
		    
		    						
		    				
		    			while(rs.previous())
		    				if(rs.getString(2).equals(userdetails.getGstnumber()))
		    						flag=2;
		    							if(flag==2)
		    								{
		    									jo.put("uname", "null");
		    									jo.put("check", "gexist");
		    									
		    									logger.error("gst number exist");
		    									return jo.toString();
		    								}
		    						
		    
					   }
			
			}
			catch(SQLException e)
			{
				logger.error("error in create statement");
			}
			catch(JSONException e)
			{
				logger.error("error in JSON object");
				
			}
			catch(Exception e)
			{
				logger.error("error in register user for checking mobile end gst existing status");
			}
			finally
			{
				try
				{
				rs.close();
				st.close();
				}
				catch(SQLException e)
				{
					logger.error("error at the time of closing resultset and statement in register user for checking mobile end gst existing status");
				}
			}
			
			
		    	
			
               /*-----------Insert Data into Registration Table-------------------*/
		    
		    	
		    String reg="insert into registration(pswd,shopname,"
						+ "address,mobilenumber,emailid,gstnumber,acctlocked,"
						+ "subStartdate,subEnddate)  values(?,?,?,?,?,?,?,?,?)";
			
		    PreparedStatement st1=null;
		    try
		    {
		    	
		    	st1=con.prepareStatement(reg);
			
		    	
		    	st1.setString(1, userdetails.getPassword());
		    	st1.setString(2, userdetails.getShopname());
		    	st1.setString(3, userdetails.getAddress());
			
		    	st1.setString(4, userdetails.getMobilenumber());
		    	st1.setString(5, userdetails.getEmailid());
		    	
		    	st1.setString(6, userdetails.getGstnumber());
		    	st1.setString(7, userdetails.getAcctlocked());
		    	
		    	st1.setString(8, userdetails.getSubstartdate());
		    	st1.setString(9, userdetails.getSubenddate());
			
		    	st1.executeUpdate();
		    	flag=4;
		    }
		    catch(SQLException e)
		    {
		    	logger.error("error in PreparedStatement at the time of inserting user details in registration table");
		    }
		    catch(Exception e)
		    {
		    	logger.error("error at the time of inserting user details in registration table");
		    }
		    finally
		    {
		    	try
		    	{
		    	st1.close();
		    	}
		    	catch(SQLException e)
		    	{
		    		logger.error("error in closing PreparedStatement at the time of inserting user details in registration table");
		    	}
		    }
			    
			
			
			
			/*----------Inserting Data into Logincontrol---------*/
		    	
		    	int id=0;
		    	if(flag==4)
		    		{	/*--Getting Subid from registration as username------------*/
		    		
		    			JSONObject jo=new JSONObject();
		    			String mo=userdetails.getMobilenumber();
		    			 ResultSet rs1=null;
		    			
		    		     String regis="select subid from registration where mobilenumber="+mo;
		    		     
		    		    
		    		    	try
		    		    	{
		    		    	
		    		    	st=con.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		    		    	
		    		    	rs1=st.executeQuery(regis);
		    		    	 
		    		    	 rs1.next();
		    		    	 id=rs1.getInt(1);
		    		    	 
		    		    	 jo.put("uname", id);
		    		    	 jo.put("check", "success");
		    		    
		    		    	}catch(SQLException e)
		    		    	{
		    		    		logger.error("error in resultset at the time of getting sub id from registration table as username");
		    		    		
		    		    	}
		    		    	catch(JSONException e)
		    		    	{
		    		    		logger.error("error in json object at the time of getting sub id from registration table");
		    		    	}
		    		    	catch(Exception e)
		    		    	{
		    		    		logger.error("error in Getting Subid from registration as username");
		    		    	}
		    		    	finally
		    		    	{
		    		    		try
		    		    		{
		    		    		rs1.close();
		    		    		}
		    		    		catch(SQLException e)
		    		    		{
		    		    			logger.error("error in closing resultset at the time of getting sub id from registration table");
		    		    		}
		    		    	}
		    		    	
		    		    	
		    		    	
		    		     	/*----------------Insert into logincontrol----------------*/
		    			   String log="insert into logincontrol(username,pswd,acctlocked,forcechgpwd,access,forcelogin)"
							 + "values(?,?,?,?,?,?)";
		    			
		    			   PreparedStatement st2=null;
		    			   try
		    			   {
		    				   
		    			  
		    				st2=con.prepareStatement(log);
		    				
		    				st2.setInt(1, id);
		    				st2.setString(2, userdetails.getPassword());
		    				st2.setString(3, userdetails.getAcctlocked());
		    				st2.setString(4, "false");
		    				st2.setInt(5, 15);
		    				st2.setString(6, "true");
		    				
		    				st2.executeUpdate();
		    				
		    				flag1=8;
		    			   }
		    			   catch(SQLException e)
		    			   {
		    				   logger.error("error in prepareStatement at the time of inserting data in logincontrol table");
		    			   }
		    			   catch(Exception e)
		    			   {
		    				   logger.error("error at the time of inserting data in logincontrol table");
		    			   }
		    			   finally
		    			   {
		    				   try
		    				   {
		    				   st2.close();
		    				   con.close();
		    				   }
		    				   catch(SQLException e)
		    				   {
		    					   logger.error("error in closing prepareStatement at the time of inserting data in logincontrol table");
		    				   }
		    			   }
		    	
		    	
		    	     if(flag==4 && flag1==8)
		    	     {
		    	    	 logger.info("user :" +" "+id+" "+"successfully register");
		    		     return jo.toString();
		    	     }
		    	          
	
		       }
		    	
		    	JSONObject jo1= new JSONObject();
		    	try
		    	{
		    	jo1.put("uname", "null");
		    	jo1.put("check", "null");
		    	}
		    	catch(Exception e)
		    	{
		    		logger.error("In register service : some error occured");
		    	}
		    	finally
		    	{   try
		    		{
		    			con.close();
		    		}
		    		catch(Exception e)
		    		{
		    			logger.error("In register service: error at the time of clossing connection");
		    		}
		    	}
		    	return jo1.toString(); 
		    	
		}
			
 
		
/*2-------------------------------------------FOR LOGIN USER----------------------------------------------------------------*/		
		

		public String loginUser(String username,String password)
		{
			DatabaseConnection db=new DatabaseConnection();
			Connection con=db.getConnection();
			
				int flag=0,temp=0;
			//System.out.println("2.Login");
	 
				JSONObject jo=new JSONObject();
	 
				
				Statement st1=null;
				ResultSet rs1=null;
				String log="select *from logincontrol";
	 
				
				String reg="select subid from registration";
					/*-----------Checking user exist in registration tbale or not----------------*/
					try
					{
							st1=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
							rs1=st1.executeQuery(reg);
				
							while(rs1.next())
							{
								if(username.equals(rs1.getString(1)))
								{
									temp=1;
						
								}
							}
				
					}
					catch(SQLException e)
					{
						logger.error("In Login Service: error in creating statement or resultset at the time of checking user exist in logincontrol or not");
					}
					catch(Exception e)
					{
						logger.error("In login service: error at the time of checking user exist in logincontrol or not");
					}
					finally
					{
						try
						{
						rs1.close();
						}catch(Exception e)
						{
							logger.error("In login service: error in closing resultset at the time of checking user exist in logincontrol or not");
						}
					}
				
				
				if(temp==1)  /*---if user is main user and exist in registration table-------*/
				{
				    ResultSet rs=null;
					try
						{
							st1=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
							 rs=st1.executeQuery(log);
	 	
								while(rs.next())
									{   //flag=1;
	 		
										if(username.equals(rs.getString(1)))
											{     
												flag=2;
											//System.out.println("h"); 
	 		
												if(password.equals(rs.getString(2)))
												
													{
														flag=4;
					
														jo.put("parentuser", "null");
														jo.put("check","success" );
														jo.put("accl",rs.getString(3));
														jo.put("forcep",rs.getString(4));
														jo.put("access", rs.getInt(5));
			 		  
														return jo.toString();     /*---if user and pass exist in logincontrol----*/
					
													}
	 				
											}
									}
	 	
									if(flag==0)
										{
											jo.put("parentuser", "null");
											jo.put("check", "uincorrect" );
											jo.put("accl",  "null");
											jo.put("forcep",  "null");
											jo.put("access","null");
			  
											return jo.toString();    /*---if user name incorrect------*/
										}
			 
									if(flag==2)
										{
											jo.put("parentuser", "null");
											jo.put("check", "pincorrect" );
											jo.put("accl",  "null");
											jo.put("forcep",  "null");
											jo.put("access","null");
			  
											return jo.toString();  /*---------if password incorrect--------*/
										}
	 	
						}
						catch(SQLException e)
							{
								flag=3;
								logger.error("In Login service: error in result set at the time of checking after user is exist");
							}
	 
							catch(Exception e)
							{
								logger.error("In Login service: error at the time of checking after user is exist");
								
							}
						finally
						{
							try
							{
								rs.close();
							}
							catch(Exception e)
							{
								logger.error("In login service: error in closing resultset at the time of checking after user is exist");
							}
						}
					}
				
				
				else    /*-----for checking user is sub user or not-----*/
				{
					
					String login="select *from logincontrol";
					
					//System.out.println(username);
					String s1="'"+username+"'";
					String subu="select subid from subuser where childUserName="+s1;
					
					int id=0;
					ResultSet rs3=null;
					try
					{
						st1=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					    rs3=st1.executeQuery(subu);
						rs3.next();
						id=rs3.getInt(1);            /*-selecting sub user parent id----*/
						//System.out.println(id);
					
					}
					catch(SQLException e)
					{
						logger.error("In login service: error in resultset at the time selecting sub user name");
					}
					catch(Exception e)
					{
						logger.error("In login service: error at the time selecting sub user name");
					}
					finally
					{
						try
						{
							rs3.close();
						}
						catch(Exception e)
						{
							logger.error("In login service: error in closing resultset at the time of selecting sub user name");
						}
					}
					
					
					int flag1=0;
					ResultSet rs2=null;
					try       /*----checking user name and password of subuser is exist or  not-----*/
					{
						st1=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						rs2=st1.executeQuery(login);
						
						while(rs2.next())
						{
							if(username.equals(rs2.getString(1)))
							{
										flag1=1;
									if(password.equals(rs2.getString(2)))
										{
											flag1=2;
										
											jo.put("parentuser", id);
											jo.put("check","success" );
											jo.put("accl",rs2.getString(3));
											jo.put("forcep",rs2.getString(4));
											jo.put("access", rs2.getInt(5));
										
											return jo.toString();   /*---if subuser name and password exist--*/
											
	
										}

							}
								
						}
						
						if(flag1==0)
						{
							jo.put("parentuser", "null");
							jo.put("check", "uincorrect" );
							jo.put("accl",  "null");
							jo.put("forcep",  "null");
							jo.put("access","null");
  
							return jo.toString();   /*---if subuser id is incorrect----*/
						}
						
						if(flag1==1)
						{
							jo.put("parentuser", "null");
							jo.put("check", "pincorrect" );
							jo.put("accl",  "null");
							jo.put("forcep",  "null");
							jo.put("access","null");
  
							return jo.toString();   /*----if subuser password incorrect---*/
						}
						
						
					}
					catch(SQLException e)
					{
						logger.error("In login service: error in resultset at the time of checking sub user name and password in logincontrol");
					}
					catch(Exception e)
					{
						logger.error("In login service: error at the time of checking sub user name and password in logincontrol");
					}
					finally
					{
						try
						{
							rs2.close();
							con.close();
						}
						catch(Exception e)
						{
							logger.error("In login service:error in closing resultset and connection at the time of checking sub user name and password in logincontrol");
						}
					}
				}
	  
						return jo.toString();
	 
		}
 
 
		
		
/*3.-----------ok-----------------FORGET USER NAME--------------------------------------------------------------*/
		 
		public String getForgetUser(String mobilenumber)
			{
				DatabaseConnection db=new DatabaseConnection();
				Connection con=db.getConnection();
			
				Statement st=null;
				ResultSet rs=null;
				int flag=0;
				String mo="select subid,mobilenumber,emailid,acctlocked from registration";
		
				JSONObject jo=new JSONObject();
		
					try
						{
							st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						    rs=st.executeQuery(mo);
	     
								while(rs.next())
									{
										if(rs.getString(2).equals(mobilenumber))  /*---checking mobile number exist or not--*/
											{
												if(rs.getString(4).equals("false")) /*----checking account lock or not---*/
													{
														jo.put("check", "myes");
														jo.put("uname", rs.getString(1));
														jo.put("mnum", rs.getString(2));
														jo.put("email", rs.getString(3));
	    			 
														return jo.toString();   /*---mobile number exist and account not lock---*/
													}
												else
													{
														jo.put("check", "ulock");
														jo.put("uname", "null");
														jo.put("mnum", "null");
														jo.put("email", "null");
	 	    		
														return jo.toString(); /*---if account is lock--*/
													}
											}
	    	
									}
	    
						}
						catch(SQLException e)
						{
							logger.error("In forget user service: error in creating connection or resultset at the time of checking mobile number and account lock existance");
							
						}
						catch (Exception e) 
							{
								logger.error("In forget user service: error  at the time of checking mobile number and account lock existance");
							
							}
						finally
						{
							try
							{
								rs.close();
								con.close();
								
							}catch(Exception e)
							{
								logger.error("In forget service: error at the time of colsing resultset or connection");
							}
						}
		
							
				try
					{
						jo.put("check", "mno");
						jo.put("uname", "null");
						jo.put("mnum", "null");
						jo.put("email", "null");
	     
	     
					}
					catch(Exception e)
						{
							logger.error("In forget user service: error when mobile number not exist");
						}
	
						
		
				return jo.toString();
						
			}
		
		
		
/*4.-----------ok-----------------------FORGET PASSWORD--------------------------------------------------------*/		
		

		 
	public String getForgetPassword(String username)
			{
			
				DatabaseConnection db=new DatabaseConnection();
				Connection con=db.getConnection();
				
				Statement st=null;
				ResultSet rs=null;
				String un="select subid,mobilenumber,emailid,acctlocked from registration";
		
				JSONObject jo=new JSONObject();
		
					try
						{
							 st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
							 rs=st.executeQuery(un);
	     
								while(rs.next())
									{
										if(rs.getString(1).equals(username))/*--for checking username existance--*/
											{
												if(rs.getString(4).equals("false")) /*--for checking account locked status--*/
													{
														jo.put("ckeck", "uyes");
														jo.put("accl", rs.getString(4));
														jo.put("mnum", rs.getString(2));
														jo.put("email", rs.getString(3));
	    			  
	    			  
														return jo.toString(); /*-username exist and account not lock---*/
													}
	    		 
													jo.put("ckeck", "uyes");
													jo.put("accl", "true");
													jo.put("mnum", "null");
													jo.put("email", "null");
    			  
													return jo.toString(); /*if account is lock--*/
	    		 
											}
	    	  
									}
						}
						catch(SQLException e)
						{
							logger.error("In forget password service: error in database connection or in resultset at the time of checking user and account existance");
							
						}
						catch(Exception e)
							{
								
							logger.error("In forget password service: error at the time of checking user and account existance");
							}
						finally
						{
							try
							{
							st.close();
							rs.close();
							con.close();
							}
							catch(Exception e)
							{
								logger.error("In forget password service: error at the time of closing resultset and connection");
							}
							
						}
	 	
			try
				{
						jo.put("ckeck", "uno");
						jo.put("accl", "null");
						jo.put("mnum", "null");
						jo.put("email", "null");
	 	
				}
				
			catch(Exception e)
						{
							logger.error("In forget password service: error when user name not exist");
						}
				return jo.toString();
	 
			}



/*5.---------ok------------------------TEMP PASSWORD--------------------------------------------------------*/	
		
	public String getTempPassword(String user,String pass)
		{
			DatabaseConnection db=new DatabaseConnection();
			Connection con=db.getConnection();
		
			Statement st=null;
			ResultSet rs=null;
	
			int flag=0,temp=0;
			
			String s="'"+user+"'";
			String rg="update registration set pswd=? where subid=?";
		
			String lc="update logincontrol set pswd=? ,forcechgpwd=? where username="+s;
		
		
			/*-------------------CHECKING USER EXIST OR NOT---------------------------*/
			String main="select subid from registration";
		
				try
					{
						 st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						 rs=st.executeQuery(main);
			
						while(rs.next())
						{
							if(user.equals(rs.getString(1)))/*checking user exist or not in registration table-*/
							{
								temp=1;
							}
						}
				if(temp==0)
					return "unotexist";
			
					}
				catch(SQLException e)
				{
					logger.error("In temp password service: error in creating statement or resultset at the time of checking user exist in register table or not");
				}
				catch(Exception e)
				{
					logger.error("In temp password service: error at the time of checking user exist in register table or not");
				}
				finally
				{
					try
					{
					rs.close();
					st.close();
					}
					catch(Exception e)
					{
						logger.error("In temp password service: error in clossing statement and resultset at the time checking user exist in register table or not");
					}
				}
				
			
			
			
				PreparedStatement ps=null;
				PreparedStatement fcp=null;
				try
					{
		
		
						//Statement st3=con.createStatement();
						 ps = con.prepareStatement(rg);
						 fcp = con.prepareStatement(lc);
	
						ps.setString(1, pass);
						ps.setString(2, user);
	
						fcp.setString(1, pass);
						fcp.setString(2, "true");
						//fcp.setString(3, username);
	
						ps.executeUpdate();
						fcp.executeUpdate();
      
					}
					catch(SQLException e)
					{
						logger.error("In temp Password service: error in prepare statement at the time of set user name and password in registration and logincontrol table");
					}
					catch(Exception e)
					{
						logger.error("In temp Password service: error at the time of set user name and password in registration and logincontrol table");
					}
				    finally
				    {
				    	try
				    	{
				    	ps.close();
				    	fcp.close();
				    	con.close();
				    	}
				    	catch(Exception e)
				    	{
				    		logger.error("In temp password service: error in closing preparestatement and connection at the time of set user name and passwors");
				    	}
				    }
			
					return "success";
			}	
	
/*6.----------ok-----------------------RESET PASSWORD----------------------------------------------------*/	
	
	
	public String getResetPassword(String user,String pass)
	{
		
		DatabaseConnection db=new DatabaseConnection();
		Connection con=db.getConnection();
	
		Statement st=null;
		ResultSet rs=null;
		
		int flag=0;
		
			String rege="select subid from registration";
		
				try
					{
						 st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						 rs=st.executeQuery(rege);
						
						/*-------CHECKING FOR MAIN USER-----*/ 
							while(rs.next())
							{
								if(rs.getString(1).equals(user))/*--Checking user name exist in registration table or not---*/
									{
										 flag=1;
										String rg="update registration set pswd=? where subid=?";
										String lc="update logincontrol set pswd=? ,forcechgpwd=? where username=?";
	    		
										PreparedStatement ps=null;
										PreparedStatement fcp=null;
										try
											{
	    			
	    			
											
											 ps = con.prepareStatement(rg);
										     fcp = con.prepareStatement(lc);
	    		
											ps.setString(1, pass);
											ps.setString(2, user);
	    		
											fcp.setString(1, pass);
											fcp.setString(2, "false");
											fcp.setString(3, user);
	    		
											ps.executeUpdate();
											fcp.executeUpdate();
	    	      
											}
										catch(SQLException e)
										{
											logger.error("In reset password service: error in prepare statement at the time of checking user exist in register table");
										}
										catch(Exception e)
											{
												logger.error("In reset password service: error  at the time of checking user exist in register table");
												
											}
										finally
										{
										    ps.close();
										    fcp.close();
										}
										
	    		
									}
							}
							
							if(flag==0)
								return "unoexist";
							
					}

			    	/*--------Main Exception-------*/
				catch(SQLException e)
				{
					logger.error("In reset password service: error in create statement or result set");
				}
				catch(Exception e)
						{ 
							logger.error("In reset password service: error");
						}
				finally
				{
					try
					{
					st.close();
					rs.close();
					con.close();
					}
					catch(Exception e)
					{
						logger.error("In reset password service: error at the time of closing statement or resultset or connection");
					}
				}
						return "reset";
				
		
	}
	

	
/*7.----------ok------------------------SUB USER REGISTRATION----------------------------------------------------*/		
	
	public String createSubUser(RegisterSubUser subuser )
		{ 
		
			DatabaseConnection db=new DatabaseConnection();
			Connection con=db.getConnection();
			
			
			 
			int subid=0 ,flag=0;

			
			
			String mu="select  *from registration where subid="+subuser.getMainuser();
			Statement st=null;
			ResultSet rs=null;
			
			try  /*----checking parent user exist or not---*/
			{
				st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				rs=st.executeQuery(mu);
				
				
				//System.out.println(rs.getString(1));
				if(rs.next()==false)
					return "pnotexist";
			}
			catch(SQLException e)
			{
				logger.error("In sub user register service :error in creating statement and resultset at the time of checking parent user existance");
			}
			catch(Exception e)
			{
				logger.error("In sub user register service :error at the time of checking parent user existance");
			}
			finally 
			{
				try
				{
				st.close();
				rs.close();
				}
				catch(Exception e)
				{
					logger.error("In sub user register service :error in closing statement and resultset at the time of checking parent user existance");
				}
				
			}
			

			
			
			String control="select  childUserName from subuser";  /*-checking sub user already exist or not--*/
				try
					{
						 st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						 rs=st.executeQuery(control);
						
						while(rs.next())
							if(rs.getString(1).equals(subuser.getSubuser()))
								return "subexist";
					}
					catch(SQLException e)
					{
					
						logger.error("In sub user Register service :error in create statement or resultset at the time of checking sub user existance");
					}
					catch(Exception e)
					{   
						logger.error("In sub user Register service :error at the time of checking sub user existance");
					}
					finally
					{
						try
						{
						rs.close();
						st.close();
						}
						catch(Exception e)
						{
							
							logger.error("In sub user register service :error in closing statement and resultset at the time of checking sub user existance");
						}
					}

				
			
				
				  /*----------inserting details in sub user table and login control----*/
				String su="insert into subuser values(?,?,?,?,?,?,?,?)";

				String log="insert into logincontrol values(?,?,?,?,?,?,?)";
				
				PreparedStatement ps=null;
				PreparedStatement ps1=null;
				
				try {
						ps=con.prepareStatement(su);
						ps1=con.prepareStatement(log);

						ps.setString(1, subuser.getMainuser());
						ps.setString(2, subuser.getSubuser());
						ps.setString(3, subuser.getPass());
						ps.setString(4, "false");
						ps.setString(5, subuser.getSubstsrtdate());
						ps.setString(6, subuser.getSubenddate());
						ps.setString(7, "true");
						ps.setInt(8, subuser.getAccess());

						ps1.setString(1, subuser.getSubuser());
						ps1.setString(2, subuser.getPass());
						ps1.setString(3, "false");
						ps1.setString(4, "true");
						ps1.setInt(5, subuser.getAccess());
						ps1.setString(6, "true");
						ps1.setString(7, subuser.getMainuser());
		
						ps.executeUpdate(); 
						ps1.executeUpdate();
					}
					catch(SQLException e)
					{
						logger.error("In sub user register service :error in preparestatement at the time of inserting details in sub user and logincontrol table");
					}
					catch(Exception e)
					{   logger.error("In sub user register service :error at the time of inserting details in sub user and logincontrol talbe");
					}
					finally
					{
						try
						{
						ps.close();
						ps1.close();
						con.close();
						}
						catch(Exception e)
						{
							logger.error("In sub user register service :error in closing preparestatment and connection after sub user registration");
						}
					}
				
						return "success";
		}		


/*8.-------------------------OK----------GET ALL SUB USER NAME BY MAIN USER NAME-----------------------------*/

		public String getAllSubUser(String username)
		{
	
			DatabaseConnection db=new DatabaseConnection();
			Connection con=db.getConnection();
			
			
		
			JSONObject jo = new JSONObject();
			JSONArray ja = new JSONArray();
		
				try {
					jo.put("subusername", ja);
					}
				catch (JSONException e1) 
				{
					logger.error("In all sub user services: error in JSON object");
				}
		
		
		      /*-------checking existance of main user in register table--*/
		
				String main="select subId from registration where subid="+username;
				Statement st=null;
				ResultSet rs=null;
				try
					{
			
						 st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						 rs=st.executeQuery(main);
						 
						 if(rs.next()==false)
							 return "munotexist";
					}
				catch(SQLException e)
				{
					logger.error("In all sub user name service: error in create statement or result at the time of checking parent user exist or not");
				}
				catch(Exception e)
					{
					logger.error("In all sub user name service: error at the time of checking parent user exist or not");
						
					}
				finally
				{
					try
					{
					rs.close();
					st.close();
					}
					catch(Exception e)
					{
						logger.error("In all sub user name service: error in closing result set or create statement at the time of checking parent user exist or not");
					}
				}
		
		
		
		
		      /*----Fetching all user name from subuser table using main user subid--*/
		
				String sub = "select subId,childUserName from subuser where subId="+username;
				Statement st1=null;	
				ResultSet rs1=null;
				try
					{
			
						st1=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					    rs1=st1.executeQuery(sub);
					    
					    if(rs1.next()==false)
					    {
					    	ja.put("null");
					    	return jo.toString();
					    	
					    }
					    else
					    {
					    	rs1.previous();
					    	while(rs1.next())
								{
								
									if(username.equals(rs1.getString(1))) 
										{
	    		
	    		
											ja.put(rs1.getString(2));
	        
	            
										}
	    	
								}
	    	
					    }
					}
				catch(SQLException e)
				{
					logger.error("In all sub user services: error in create statement or result set at the time of fetching all sub user");
				}
				catch(Exception e)
					{
						
					logger.error("In all sub user services: error at the time of fetching all sub user");
						
					}
				finally
				{
					try
					{
					rs1.close();
					st1.close();
					con.close();
					}
					catch(Exception e)
					{
						logger.error("In all sub user services: error in closing resultset or statement or connection at the time of fetching all sub user");
					}
				}
				

					return jo.toString();
		
		
		
	}

/*9.-------ok----------------------------GET SUB USER ACCESS by SUB USER NAME--------------------------------------------*/	


		public String getSubUserAccess(String username)
		{
			
			
			DatabaseConnection db=new DatabaseConnection();
			Connection con=db.getConnection();
			
			String subuser="'"+username+"'";
			int flag=0;
			String suser="select childUserName from subuser where childUserName="+subuser;;
			String slogin="select *from logincontrol";
		 
		 
			JSONObject jo=new JSONObject();
			
			Statement st=null;
			Statement st1=null;
			ResultSet rs=null;
			ResultSet rs1=null;
				try
					{
						 st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					 
						 rs=st.executeQuery(suser);
	     
						 st1=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						 rs1=st1.executeQuery(slogin);
						
						 if(rs.next()==false)/*--checking sub user exist or not--*/
						 {
							 	jo.put("check","sunotexist");
								jo.put("accl", "null");
								jo.put("access","null");
					 
					
								return jo.toString();
							 
						 }
						 
						 //rs.previous();
						while(rs.next())
							if(rs.getString(1).equals(username))/*--when subuser exist--*/
								{    
									flag=1;
									break;
								}
						
						if(flag==1)
							{
								while(rs1.next())
									if(rs1.getString(1).equals(username))
										if(rs1.getString(3).equals("false"))/*--when accl is not lock--*/
											{   flag=2;
	    		
												jo.put("check","suexist");
												jo.put("accl", "false");
												jo.put("access", rs1.getString(5));
	    			 
												//return jo.toString(); /*--when sub user exist and accl not lock--*/
											}
							}
	     	
	     	
					}
				catch(SQLException e)
				{
					logger.error("In get sub user access service: error in create statement or resultset at the time checking of sub user detail");
				}
				catch(Exception e)
				{   
					logger.error("In get sub user access service: error at the time checking of sub user detail");
				}
				finally
				{
					
					try
					{
					rs.close();
					rs1.close();
					st.close();
					st1.close();
					con.close();
					}
					catch(Exception e)
					{
						logger.error("In get sub user access service: error in closing  statement or resultset or connection at the time checking of sub user detail");
					}
				}
		 
				
				if(flag==1)
					{
						try
						{
						jo.put("check","suexist");
						jo.put("accl", "true");
						jo.put("access","null"); 
			 
		 
						return jo.toString();/*--when sub user exist but accl is lock--*/
						}
						catch(Exception e)
						{
							logger.error("In get sub user access service: error in JSON object when sub user exist but accl lock");		
						}
					}	
				
				return jo.toString(); /*--when sub user exist and accl not lock--*/
	
		}

		
/*10------------------OK-----------FOR EDIT SUB USER ACCESS----------------------------------------------------*/		
		
	
		public String editSubUserAccess(String subuser,String pass,int access)
		{
			DatabaseConnection db=new DatabaseConnection();
			Connection con=db.getConnection();
			
			int flag=0,temp=0;
			String newsubuser="'"+subuser+"'";
			Statement st=null;
			ResultSet rs=null;
			
				
			
			//System.out.println(pass+access);
					try  /*---checking sub user exist or not---*/
					{
						String sub="select childUserName from subuser where  childUserName="+newsubuser;
						 st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						
						 rs=st.executeQuery(sub);
						  
						if(rs.next()==false)
							 return "uincorrect";
							 
					
				
					}
					catch(SQLException e)
					{
						logger.error("In edit sub user access service: error in create statement or result set at the time of checking sub user existance");
					}
					catch(Exception e)
					{
						logger.error("In edit sub user access service: error at the time of checking sub user existance");
					
					}
					finally
					{
						try
						{
						rs.close();
						//st.close();
						}
						catch(Exception e)
						{
							logger.error("In edit sub user access service: error at the time of closing create statement or result set");
						}
						
					}
				
					/*--edit sub user access--*/
					
				String sub1="update subuser set childPassword=?, access=? where childUserName=?";
				String login="update logincontrol set pswd=?,access=? where userName=?";	
			
				PreparedStatement ps =null;
				PreparedStatement ps1 =null;
				try
				{
				     ps = con.prepareStatement(sub1);
					 ps1 = con.prepareStatement(login);
			
					ps.setString(1, pass);
					ps.setInt(2, access);
					ps.setString(3, subuser);
			
					ps1.setString(1, pass);
					ps1.setInt(2, access);
					ps1.setString(3, subuser);
			
					ps.executeUpdate();
					ps1.executeUpdate();
				}
				catch(SQLException e)
				{
					logger.error("In edit sub user access service: error in prepare statement at the time of edit sub user access");
				}
				catch(Exception e)
				{
					logger.error("In edit sub user access service: error at the time of edit sub user access");
				}
				finally
				{
					
					try
					{
						ps.close();
						ps1.close();
						con.close();
					}
					catch(Exception e)
					{
						logger.error("In edit sub user access service: error at the time of closing preparestatement or connection");
					}
					
				}
			
				return "success";
		}		
		

}


















