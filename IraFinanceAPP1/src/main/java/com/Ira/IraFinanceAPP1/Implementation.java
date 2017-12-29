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
				logger.error("In register service: error : "+e+ ",  in create statement");
			}
			catch(JSONException e)
			{
				logger.error("In register service:  error  : "+e+ ", in JSON object");
				
			}
			catch(Exception e)
			{
				logger.error("In register service:  error  : "+e+ ",  in register user for checking mobile end gst existing status");
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
					logger.error("In register service: error : "+e+ " at the time of closing resultset and statement in register user for checking mobile end gst existing status");
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
		    	logger.error("In register service: error  : "+e+ ",  at the time of inserting user details in registration table");
		    }
		    catch(Exception e)
		    {
		    	logger.error("In register Service :error : "+e+ ",  at the time of inserting user details in registration table");
		    }
		    finally
		    {
		    	try
		    	{
		    	st1.close();
		    	}
		    	catch(SQLException e)
		    	{
		    		logger.error("In register Service: error  : "+e+ ",  at the time of inserting user details in registration table");
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
		    		    		logger.error("In register Service: error  : "+e+ ",  at the time of getting sub id from registration table as username");
		    		    		
		    		    	}
		    		    	catch(JSONException e)
		    		    	{
		    		    		logger.error("In register service: error  : "+e+ ",  at the time of getting sub id from registration table");
		    		    	}
		    		    	catch(Exception e)
		    		    	{
		    		    		logger.error("In register service error  : "+e);
		    		    	}
		    		    	finally
		    		    	{
		    		    		try
		    		    		{
		    		    		rs1.close();
		    		    		}
		    		    		catch(SQLException e)
		    		    		{
		    		    			logger.error("In register service: error  : "+e+ ",  at the time of getting sub id from registration table");
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
		    				   logger.error("In register service: error : "+e+ ",  at the time of inserting data in logincontrol table");
		    			   }
		    			   catch(Exception e)
		    			   {
		    				   logger.error("In register service :error  : "+e+ ", at the time of inserting data in logincontrol table");
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
		    					   logger.error("In register service: error:  : "+e+ ",  at the time of inserting data in logincontrol table");
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
		    		logger.error("In register service :  error  : "+e);
		    	}
		    	finally
		    	{   try
		    		{
		    			con.close();
		    		}
		    		catch(Exception e)
		    		{
		    			logger.error("In register service: error : "+e+ ",  at the time of clossing connection");
		    		}
		    	}
		    	return jo1.toString(); 
		    	
		}
			
 
		
/*2-------------------------------------------FOR LOGIN USER----------------------------------------------------------------*/		
		

		public String loginUser(String user,String password)
		{
			DatabaseConnection db=new DatabaseConnection();
			Connection con=db.getConnection();
			
			String username="'"+user+"'";
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
						logger.error("In Login Service: error  : "+e+ ",  at the time of checking user exist in logincontrol or not");
					}
					catch(Exception e)
					{
						logger.error("In login service: error  : "+e+ ", at the time of checking user exist in logincontrol or not");
					}
					finally
					{
						try
						{
						rs1.close();
						}catch(Exception e)
						{
							logger.error("In login service: error  : "+e+ ", at the time of checking user exist in logincontrol or not");
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
								logger.error("In Login service: error  : "+e+ ",  at the time of checking after user is exist");
							}
	 
							catch(Exception e)
							{
								logger.error("In Login service: error  : "+e+ ", at the time of checking after user is exist");
								
							}
						finally
						{
							try
							{
								rs.close();
							}
							catch(Exception e)
							{
								logger.error("In login service: error  : "+e+ ",  at the time of checking after user is exist");
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
						logger.error("In login service: error  : "+e+ ",  at the time selecting sub user name");
					}
					catch(Exception e)
					{
						logger.error("In login service: error  : "+e+ ", at the time selecting sub user name");
					}
					finally
					{
						try
						{
							rs3.close();
						}
						catch(Exception e)
						{
							logger.error("In login service: error  : "+e+ ",  at the time of selecting sub user name");
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
						logger.error("In login service: error  : "+e+ ",  at the time of checking sub user name and password in logincontrol");
					}
					catch(Exception e)
					{
						logger.error("In login service: error : "+e+ ", at the time of checking sub user name and password in logincontrol");
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
							logger.error("In login service:error : "+e+ ",  at the time of checking sub user name and password in logincontrol");
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
							logger.error("In forget user service: error  : "+e+ ",  at the time of checking mobile number and account lock existance");
							
						}
						catch (Exception e) 
							{
								logger.error("In forget user service: error  : "+e+ ",  at the time of checking mobile number and account lock existance");
							
							}
						finally
						{
							try
							{
								rs.close();
								con.close();
								
							}catch(Exception e)
							{
								logger.error("In forget service: error  : "+e+ ", at the time of colsing resultset or connection");
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
							logger.error("In forget user service: error : "+e+ ",  when mobile number not exist");
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
							logger.error("In forget password service: error  : "+e+ ",  at the time of checking user and account existance");
							
						}
						catch(Exception e)
							{
								
							logger.error("In forget password service: error  : "+e+ ", at the time of checking user and account existance");
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
								logger.error("In forget password service: error  : "+e+ ", at the time of closing resultset and connection");
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
							logger.error("In forget password service: error  : "+e+ ", when user name not exist");
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
					logger.error("In temp password service: error  : "+e+ "  at the time of checking user exist in register table or not");
				}
				catch(Exception e)
				{
					logger.error("In temp password service: error  : "+e+ " at the time of checking user exist in register table or not");
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
						logger.error("In temp password service: error  : "+e+ "  at the time checking user exist in register table or not");
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
						logger.error("In temp Password service: error  : "+e+ "  at the time of set user name and password in registration and logincontrol table");
					}
					catch(Exception e)
					{
						logger.error("In temp Password service: error  : "+e+ " at the time of set user name and password in registration and logincontrol table");
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
				    		logger.error("In temp password service: error  : "+e+ "  at the time of set user name and passwors");
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
											logger.error("In reset password service: error  : "+e+ "  at the time of checking user exist in register table");
										}
										catch(Exception e)
											{
												logger.error("In reset password service: error  : "+e+ "  at the time of checking user exist in register table");
												
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
					logger.error("In reset password service: error  : "+e+ " in create statement or result set");
				}
				catch(Exception e)
						{ 
							logger.error("In reset password service: error : "+e  );
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
						logger.error("In reset password service: error  : "+e+ " at the time of closing statement or resultset or connection");
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
				logger.error("In sub user register service :error  : "+e+ "  at the time of checking parent user existance");
			}
			catch(Exception e)
			{
				logger.error("In sub user register service :error  : "+e+ " at the time of checking parent user existance");
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
					logger.error("In sub user register service :error  : "+e+ "  at the time of checking parent user existance");
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
					
						logger.error("In sub user Register service :error  : "+e+ "  at the time of checking sub user existance");
					}
					catch(Exception e)
					{   
						logger.error("In sub user Register service :error  : "+e+ " at the time of checking sub user existance");
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
							
							logger.error("In sub user register service :error  : "+e+ "  at the time of checking sub user existance");
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
						logger.error("In sub user register service :error  : "+e+ "  at the time of inserting details in sub user and logincontrol table");
					}
					catch(Exception e)
					{   logger.error("In sub user register service :error : "+e+ "  at the time of inserting details in sub user and logincontrol talbe");
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
							logger.error("In sub user register service :error : "+e+ " after sub user registration");
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
				catch (JSONException e) 
				{
					logger.error("In all sub user services: error : " +e+  "  in JSON object");
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
					logger.error("In all sub user name service: error  : "+e+ "  at the time of checking parent user exist or not");
				}
				catch(Exception e)
					{
					logger.error("In all sub user name service: error  : "+e+ " at the time of checking parent user exist or not");
						
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
						logger.error("In all sub user name service: error  : "+e+ "  at the time of checking parent user exist or not");
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
					logger.error("In all sub user services: error  : "+e+ "  at the time of fetching all sub user");
				}
				catch(Exception e)
					{
						
					logger.error("In all sub user services: error at  : "+e+ " the time of fetching all sub user");
						
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
						logger.error("In all sub user services: error  : "+e+ "  at the time of fetching all sub user");
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
					logger.error("In get sub user access service: error  : "+e+ "  at the time checking of sub user detail");
				}
				catch(Exception e)
				{   
					logger.error("In get sub user access service: error  : "+e+ " at the time checking of sub user detail");
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
						logger.error("In get sub user access service: error  : "+e+ "  at the time checking of sub user detail");
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
							logger.error("In get sub user access service: error  : "+e+ " in JSON object when sub user exist but accl lock");		
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
						logger.error("In edit sub user access service: error  : "+e+ "  at the time of checking sub user existance");
					}
					catch(Exception e)
					{
						logger.error("In edit sub user access service: error  : "+e+ " at the time of checking sub user existance");
					
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
							logger.error("In edit sub user access service: error  : "+e+ " at the time of closing create statement or result set");
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
					logger.error("In edit sub user access service: error  : "+e+ "  at the time of edit sub user access");
				}
				catch(Exception e)
				{
					logger.error("In edit sub user access service: error : "+e+ "  at the time of edit sub user access");
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
						logger.error("In edit sub user access service: error : "+e+ "  at the time of closing preparestatement or connection");
					}
					
				}
			
				return "success";
		}		
		

		
/*11.------------ok----------------------Synch Item------------------------------------------------------------------*/			
		
		public String addItem(String str)
		{
			
			DatabaseConnection db=new DatabaseConnection();
			Connection con=db.getConnection();
					
			int flag=0;
			int temp=0;
			int count=0,addnew=0;
			//String str="{\"subid\":[1000],\"itemid\":[AF1],\"itemname\":[APPLE],\"itemprice\":[20],\"measurement\":[per kg],\"itemcategory\":[Fruit],\"gstcategory\":[igst],\"startdate\":[2017-12-16],\"enddate\":[2018-12-16],\"count\":[0],\"version\":[1]}";
			
			
			
			
			
			
			
			/*--------------------selecting last item in item table--------------*/
			
			String key="select max(id) from itemmain";
			
				Statement st=null;
				ResultSet rs=null;
				
				try
				{
					st=con.createStatement();
					rs=st.executeQuery(key);
					rs.next();
					temp=rs.getInt(1);
					System.out.println(temp);
			
				}
				catch(SQLException e)
				{
					logger.error("In Synch item service :error  : "+e+ "  at the time of selecting last item in item table");
				}
				catch(Exception e)
				{
					logger.error("In Synch item service :error  : "+e+ " at the time of selecting last item in item table");
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
						logger.error("In Synch item service :error  : "+e+ "  at the time of selecting last item in item table");
						
					}
				}
			
			
			
			/*------------------------inserting data into itemmain-------------------*/
			
			
			
			try
			{
				//System.out.println(str);
				JSONObject obj=new JSONObject(str);
				
				
				JSONArray arr = obj.getJSONArray("subid");
				JSONArray arr1 = obj.getJSONArray("itemid");
				JSONArray arr2= obj.getJSONArray("itemname");
				JSONArray arr3 = obj.getJSONArray("itemprice");
				JSONArray arr4 = obj.getJSONArray("measurement");
				JSONArray arr5 = obj.getJSONArray("itemcategory");
				JSONArray arr6 = obj.getJSONArray("gstcategory");
				JSONArray arr7 = obj.getJSONArray("startdate");
				JSONArray arr8 = obj.getJSONArray("enddate");
				JSONArray arr9 = obj.getJSONArray("count");
				JSONArray arr10 = obj.getJSONArray("version");
				
			
			    String itemtable="select itemid,version,enddate from itemmain";
			
			   
			    String s="insert into itemmain (subId,itemid,itemname,itemprice,measurement,itemcategory,gstcategory,startdate,enddate,count,version) values(?,?,?,?,?,?,?,?,?,?,?)";
				
				
			    
			    
			    for(int i=0; i<arr.length() && i<arr1.length() && i<arr2.length() && i<arr3.length() && i<arr4.length() && i<arr5.length() && i<arr6.length() && i<arr7.length() && i<arr8.length() &&i<arr9.length() &&i<arr10.length()  ; i++)
				{
					int iditem=0,itemversion=0,itemenddate=0;
					
					 
					PreparedStatement	ps = con.prepareStatement(s);
					Statement 	 st1=con.createStatement();
					 ResultSet	 rs1=st1.executeQuery(itemtable);
					
					while(rs1.next())
						{
							if(rs1.getString(1).equals(arr1.getString(i)))
							{	
								
								iditem=1;
							
								if(rs1.getString(2).equals(arr10.getString(i)))
										itemversion=1;
								if(rs1.getString(3).equals(arr8.getString(i)))
											itemenddate=1;
							
							
									if(iditem==1 && itemversion==1 && itemenddate==1)
									{
										//System.out.println("id= ver= date=");
										break;
									}
							
								
									if(iditem==1 && itemversion==1 && itemenddate!=1)
									{
										
									
								
										String dd=arr8.getString(i);
										String idd=arr1.getString(i);
										String ve=arr10.getString(i);
										String dd1="'"+dd+"'";
										String idd1="'"+idd+"'";
										String ver="'"+ve+"'";
									
									String up="update itemmain set enddate="+dd1 +"where itemid="+idd1 + "and version="+ver;
								
									 PreparedStatement ps2 = con.prepareStatement(up);
								
								
									ps2.executeUpdate();
								    
									count=0;
									
									break;
								}
							
								if(iditem==1 && itemversion!=1)
									{
									
									count=1;
									
									
									}
							
							}
					
				}
					
					
						if(count==1 || iditem==0 )
						{
							
							
							ps.setInt(1,arr.getInt(i));
							ps.setString(2, arr1.getString(i));
							ps.setString(3, arr2.getString(i));
							ps.setString(4, arr3.getString(i));
							ps.setString(5, arr4.getString(i));
							ps.setString(6, arr5.getString(i));
							ps.setString(7, arr6.getString(i));
							ps.setString(8, arr7.getString(i));
							ps.setString(9, arr8.getString(i));
							ps.setString(10, arr9.getString(i));
							ps.setString(11, arr10.getString(i));
					
					
							ps.executeUpdate();
						}
				
				}
			}
			catch(JSONException e)
			{
				System.out.println(e);
				logger.error("In Synch item service :error  : "+e+ "  at the time parsing data in item table");
			}
			catch(SQLException e)
			{
				System.out.println(e);
				logger.error("In Synch item service :error  : "+e+ "  at the time of inserting data");
			}
			catch(Exception e)
			{
				System.out.println(e);
				logger.error("In Synch item service :error  : "+e+ "  at the time of inserting data in item table");
				flag=1;	
				
			}
			
				
			
		
				
			
			/*-----------------------delete data if failed----------------*/
			PreparedStatement ps3=null;
			if(flag==1)
			{
				String del="delete from itemmain where id>"+temp;
				
				try
				{
				 ps3 = con.prepareStatement(del);
				
				ps3.executeUpdate();
				}
				catch(SQLException e)
				{
					logger.error("In Synch item service :error  : "+e+ "  at the time of deleting data if synch failed");
				}
				catch(Exception e)
	 			{
					logger.error("In Synch item service :error  : "+e+ " at the time of deleting data if synch failed");
				}
				finally
				{
					try
					{
					ps3.close();
					con.close();
					}
					catch(Exception e)
					{
						logger.error("In Synch item service :error  : "+e+ "  at the time of deleting data if synch failed");
					}
				}
			}
			
			
			/*------------------Sending Responce---------------*/
			
			JSONObject jo=new JSONObject();
			try
			{
			
			
				if(flag==1)
				{
					jo.put("check", "fail");
					return jo.toString();
				}
				else
				{
					jo.put("check", "success");
				}
			
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
			
			return	jo.toString();
		
			
		}
		
		
		
/*12.--------ok------------------GET ITEM DETAIL BY SUB ID WHICH ITEM IS ACTIVE------------------------------*/		
		
		
		public String getAllItem(String id)
		{
			

			DatabaseConnection db=new DatabaseConnection();
			Connection con=db.getConnection();
			
			int flag=0;
			
			String dat="select subdate(curdate(),1) from dual";
			
			
			Statement st1=null;
			ResultSet rs1=null;
			String datestring=null;      /*selecting current date */
			try
			{
				 st1=con.createStatement();
				 rs1=st1.executeQuery(dat); 
				rs1.next();
				String dat1=rs1.getString(1);
				datestring="'"+dat1+"'";
				
				
			}
			catch(SQLException e)
			{
				logger.error("In Item Detail Service: error  : "+e+ "  at the time of selection current date");
			}
			catch(Exception e)
			{
				logger.error("In Item Detail Service: error  : "+e+ " at the time of selection current date");
			}
			finally
			{
				try
				{
					
				st1.close();
				rs1.close();
				}
				catch(Exception e)
				{
					logger.error("In Item Detail Service: error  : "+e+ "  at the time of selection current date");
				}
			}
			
			
			String itemdetail="select *from itemmain where enddate>="+datestring +"and subid="+id;
			
			JSONObject jo=new JSONObject();
			Statement st=null;
			ResultSet rs=null;
			try										/*Fetching active item in item table*/
			{
					 st=con.createStatement();
					 rs=st.executeQuery(itemdetail); 
			
					
					JSONArray ja=new JSONArray();
					JSONArray ja1=new JSONArray();
					JSONArray ja2=new JSONArray();
					JSONArray ja3=new JSONArray();
					JSONArray ja4=new JSONArray();
					JSONArray ja5=new JSONArray();
					JSONArray ja6=new JSONArray();
					JSONArray ja7=new JSONArray();
					JSONArray ja8=new JSONArray();
					JSONArray ja9=new JSONArray();
					JSONArray ja10=new JSONArray();
			
					jo.put("subid", ja);
					jo.put("itemid", ja1);
					jo.put("itemname", ja2);
					jo.put("itemprice", ja3);
					jo.put("measurement", ja4);
					jo.put("itemcategory", ja5);
					jo.put("gstcategory", ja6);
					jo.put("startdate", ja7);
					jo.put("enddate", ja8);
					jo.put("count", ja9);
					jo.put("version", ja10);
						
						while(rs.next())
							{
				
								ja.put(rs.getInt(2));
								ja1.put(rs.getString(3));
								ja2.put(rs.getString(4));
								ja3.put(rs.getString(5));
								ja4.put(rs.getString(6));
								ja5.put(rs.getString(7));
								ja6.put(rs.getString(8));
								ja7.put(rs.getString(9));
								ja8.put(rs.getString(10));
								ja9.put(rs.getString(11));
								ja10.put(rs.getString(12));
				
				
				
							}
							
			
			}
			catch(JSONException e)
			{
				logger.error("In Item Detail Service: error  : "+e+ "  at the time of fetching active item");
			}
			catch(SQLException e)
			{
				logger.error("In Item Detail Service: error  : "+e+ "  at the time of fetching active item");
			}
			catch(Exception e)
			{
				
				logger.error("In Item Detail Service: error  : "+e+ " at the time of fetching active item");
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
					logger.error("In Item Detail Service: error  : "+e+ "  at the time of fetching active item");
				}
			}
			
			return  jo.toString();
		}

		
/*13.----------ok--------------Login DETAIL BY USER NAME------------------------------------------------------------------*/		

		public String getLoginDetail(String name)
		{
			
			
			DatabaseConnection db=new DatabaseConnection();
			Connection con=db.getConnection();
			
			String uname="'"+name+"'";
			
			JSONObject jo=null;
			
			String log="select *from logincontrol where username="+uname;
			
			Statement st=null;
			ResultSet rs=null;					/*fetching detail from login control*/
			try
			{
				
				 jo=new JSONObject();
				
				JSONArray ja=new JSONArray();
				JSONArray ja1=new JSONArray();
				JSONArray ja2=new JSONArray();
				JSONArray ja3=new JSONArray();
				JSONArray ja4=new JSONArray();
				JSONArray ja5=new JSONArray();
				JSONArray ja6=new JSONArray();
				
				jo.put("username", ja);
				jo.put("password", ja1);
				jo.put("accl", ja2);
				jo.put("fchg", ja3);
				jo.put("access", ja4);
				jo.put("forcelogin", ja5);
				jo.put("parentid", ja6);
				
				
					
					
					 st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					 rs=st.executeQuery(log); 
					
					if(rs.next()==false)
					{
						ja.put("null");
						ja1.put("null");
						ja2.put("null");
						ja3.put("null");
						ja4.put("null");
						ja5.put("null");
						ja6.put("null");
						
						return jo.toString();
						
					}
					else
						{
							
							
							ja.put(rs.getString(1));
							ja1.put(rs.getString(2));
							ja2.put(rs.getString(3));
							ja3.put(rs.getString(4));
							ja4.put(rs.getString(5));
							ja5.put(rs.getString(6));
							ja6.put(rs.getString(7));
						   
							
						
						}
				
				
			}
			catch(JSONException e)
			{
				logger.error("In Login Detail Service: error  : "+e+ "  at the time of fetching detail");
			}
			catch(SQLException e)
			{
				logger.error("In Login Detail Service: error  : "+e+ "  at the time of fetching detail");
			}
			catch(Exception e)
			{
				logger.error("In Login Detail Service: error  : "+e+ " at the time of fetching detail");
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
					logger.error("In Login Detail Service: error  : "+e+ "  at the time of fetching detail");
				}
			}
			
			
			return jo.toString();
		}
		
		
/*14.------ok------------------DETAILS OF REPORT by Date-----------------------------------------------------------------*/		
		public String getReportHDR(String date1,String date2)
		{
			
			DatabaseConnection db=new DatabaseConnection();
			Connection con=db.getConnection();
			
			String dat1="'"+date1+"'";
			String dat2="'"+date2+"'";
			
			
			
			JSONObject jo=new JSONObject();
			
			
			Statement st=null;
			ResultSet rs=null;
				try
				{
					JSONArray ja=new JSONArray();
					JSONArray ja1=new JSONArray();
					JSONArray ja2=new JSONArray();
					JSONArray ja3=new JSONArray();
					JSONArray ja4=new JSONArray();
					JSONArray ja5=new JSONArray();
					JSONArray ja6=new JSONArray();
					JSONArray ja7=new JSONArray();
					JSONArray ja8=new JSONArray();
					JSONArray ja9=new JSONArray();
					JSONArray ja10=new JSONArray();
					JSONArray ja11=new JSONArray();
					JSONArray ja12=new JSONArray();
					JSONArray ja13=new JSONArray();
					JSONArray ja14=new JSONArray();
			
					jo.put("userid", ja);
					jo.put("invoice_id", ja1);
					jo.put("invoice_dt", ja2);
					jo.put("invoice_desc", ja3);
					jo.put("customer_name", ja4);
					jo.put("customer_gst", ja5);
					jo.put("customer_mob", ja6);
					jo.put("paid_flag", ja7);
					jo.put("total_disc_amt", ja8);
					jo.put("paid_via", ja9);
					jo.put("payment_Ref", ja10);
					jo.put("total_inv_amt", ja11);
					jo.put("cgst_amt", ja12);
					jo.put("sgst_amt", ja13);
					jo.put("igst_amt" , ja14);
			
			
					/*fetching data from invoice_hdr*/
					
					String hdr="select *from invoice_hdr where  invoice_dt between "+dat1+" and "+dat2;
					st=con.createStatement();
					rs=st.executeQuery(hdr);
			
			
					while(rs.next())
					{
				
				
						ja.put(rs.getString(2));
						ja1.put(rs.getString(3));
						ja2.put(rs.getString(4));
						ja3.put(rs.getString(5));
						ja4.put(rs.getString(6));
						ja5.put(rs.getString(7));
						ja6.put(rs.getString(8));
						ja7.put(rs.getString(9));
						ja8.put(rs.getString(10));
						ja9.put(rs.getString(11));
						ja10.put(rs.getString(12));
						ja11.put(rs.getString(13));
						ja12.put(rs.getDouble(14));
						ja13.put(rs.getDouble(15));
						ja14.put(rs.getDouble(16));
					
					}
			
			
			
				}
				catch(JSONException e)
				{
					logger.error("In Detail of Report Service by Date: error  : "+e+ " in Json object ");
				}
				catch(SQLException e)
				{
					logger.error("In Detail of Report Service by Date: error  : "+e+ "  at the time of fetching detail from invoice_hdr within date");
				}
				catch(Exception e)
				{
					logger.error("In Detail of Report Service by Date: error  : "+e+ " at the time of fetching detail from invoice_hdr within date");
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
						logger.error("In Detail of Report Service by Date: error  : "+e+ " in closing  statement or resultset or connection at the time of fetching detail from invoice_hdr within date");
						
					}
					
				}
		
			
				return jo.toString();
			
		}

		
/*15.--------ok------------DETAILS OF REPORT By INVOICE_ID----------------------------------------------------------------*/	
		
		public String getInvoiceDetail(String invoiceid)
		{
			DatabaseConnection db=new DatabaseConnection();
			Connection con=db.getConnection();
			
	         
			
			JSONObject jo=new JSONObject();
			
			Statement st=null;
			ResultSet rs=null;
			
			try
			{
			JSONArray ja=new JSONArray();
			JSONArray ja1=new JSONArray();
			JSONArray ja2=new JSONArray();
			JSONArray ja3=new JSONArray();
			JSONArray ja4=new JSONArray();
			JSONArray ja5=new JSONArray();
			JSONArray ja6=new JSONArray();
			JSONArray ja7=new JSONArray();
			JSONArray ja8=new JSONArray();
			JSONArray ja9=new JSONArray();
			JSONArray ja10=new JSONArray();
			JSONArray ja11=new JSONArray();
			JSONArray ja12=new JSONArray();
			JSONArray ja13=new JSONArray();
			JSONArray ja14=new JSONArray();
			
			jo.put("userid", ja);
			jo.put("invoice_id", ja1);
			jo.put("invoice_dt", ja2);
			jo.put("invoice_desc", ja3);
			jo.put("customer_name", ja4);
			jo.put("customer_gst", ja5);
			jo.put("customer_mob", ja6);
			jo.put("paid_flag", ja7);
			jo.put("total_disc_amt", ja8);
			jo.put("paid_via", ja9);
			jo.put("payment_Ref", ja10);
			jo.put("total_inv_amt", ja11);
			jo.put("cgst_amt", ja12);
			jo.put("sgst_amt", ja13);
			jo.put("igst_amt" , ja14);
			
					/*fetching invoice_hdr detail by invoice id*/
			
			String hdr="select *from invoice_hdr where  invoice_id="+invoiceid;
			 st=con.createStatement();
			 rs=st.executeQuery(hdr);
			
			
			 while(rs.next())
			 	{
				 	ja.put(rs.getString(2));
				 	ja1.put(rs.getInt(3));
				 	ja2.put(rs.getString(4));
				 	ja3.put(rs.getString(5));
				 	ja4.put(rs.getString(6));
				 	ja5.put(rs.getString(7));
				 	ja6.put(rs.getString(8));
				 	ja7.put(rs.getString(9));
				 	ja8.put(rs.getDouble(10));
				 	ja9.put(rs.getString(11));
				 	ja10.put(rs.getString(12));
				 	ja11.put(rs.getDouble(13));
					ja12.put(rs.getDouble(14));
					ja13.put(rs.getDouble(15));
					ja14.put(rs.getDouble(16));
				
			 	}
			 
			}
			catch(JSONException e)
			{
				logger.error("In Detail of Invoice id Service by Invoice id: error  : "+e+ " in json object");
			}
			catch(SQLException e)
			{
				logger.error("In Detail of Invoice id Service by Invoice id: error : "+e+ "  in creating statement or resultset at the time of fetching invoice_hdr detail by invoice id");
			}
			catch(Exception e)
			{
				logger.error("In Detail of Invoice id Service by Invoice id: error  : "+e+ " at the time of fetching invoice_hdr detail by invoice id");
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
					logger.error("In Detail of Invoice id Service by Invoice id: error : "+e+ "  in closing statement or resultset or connection at the time of fetching invoice_hdr detail by invoice id");
				}
			}
			
			return jo.toString();
			
		}
		
/*16.--------ok-----------------GETING CUSTOMER DETAIL from Invoice_hdr BY MOBILE NUMBER------------------------------*/
		
		
		
		public String getCustomerDetails(String mobile)
		{

			DatabaseConnection db=new DatabaseConnection();
			Connection con=db.getConnection();
			
			
			
			JSONObject jo=new JSONObject();
			
			Statement st=null;
			ResultSet rs=null;
			try
			{
			
				JSONArray ja1=new JSONArray();
				JSONArray ja2=new JSONArray();
				JSONArray ja3=new JSONArray();
				
				jo.put("customermobile", ja1);
				jo.put("name", ja2);
				jo.put("gst", ja3);
				
					/* Fetching invoice_hdr detail by mobile number*/
				
				String invoicehdr="select  customer_name,customer_mob,  customer_gst from invoice_hdr where  customer_mob="+mobile;	
				 st=con.createStatement();
				 rs=st.executeQuery(invoicehdr);
				if(rs.next()==false)
				{
					ja1.put("notexist");
					ja2.put("null");
					ja3.put("null");
					
					return jo.toString();
					
				}
					rs.previous();
				while(rs.next())
				{
				
					ja1.put(rs.getString(2));
					ja2.put(rs.getString(1));
					ja3.put(rs.getString(3));
				}
				
				
			}
			catch(JSONException e)
			{
				logger.error("In Geting customer invoice_hdr detail service by mobile: error  : "+e+ " in json object at the time of fetching invoice_hdr detail by mobile number");
			}
			catch(SQLException e)
			{
				logger.error("In Geting customer invoice_hdr detail service by mobile: error : "+e+ "  in creating statement or resultset at the time of fetching invoice_hdr detail by mobile number");
			}
			catch(Exception e)
			{
				logger.error("In Geting customer invoice_hdr detail service by mobile: error  : "+e+ " at the time of fetching invoice_hdr detail by mobile number");
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
					logger.error("In Geting customer invoice_hdr service by mobile: error  : "+e+ " in closing statement or resultset or connection at the time of fetching invoice_hdr detail by mobile number");
				}
			}
			
			return jo.toString();
		}
		


		
/*17.-------------------------SYNCH INVOICE-----------------------------------------------------------------------------*/	
			
			
			public String setInvoice_hdr_Line(String item)
			{
				
				DatabaseConnection db=new DatabaseConnection();
				Connection con=db.getConnection();
				
				int flag=0, inserthdr=0, insertline=0;
				
				
				
				String maxidhdr="select max(id) from invoice_hdr";    /*fetching max id from both table*/
				String maxidline="select max(id) from invoice_line";
				
				Statement st=null;
				Statement st1=null;
				ResultSet rs=null;
				ResultSet rs1=null;
				
				int hdrid=0;
				int lineid=0;
				
				
				try
				{
						st=con.createStatement();
						rs=st.executeQuery(maxidhdr);
						
						
						
						if(rs.next()==false)
						{
							inserthdr=1;
							
						}
						else
						{
							
							hdrid=rs.getInt(1);
							
						}
						
						
						st1=con.createStatement();
						rs1=st1.executeQuery(maxidline);
						
						if(rs1.next()==false)
						{
							insertline=1;
							
						}
						else
						{
							lineid=rs1.getInt(1);
							
						}
						
				}
				catch(SQLException e)
				{
					logger.error("In Synch Invoice Service: error  : "+e+ " in creating ststement or resultset at the time of fetching max id from both table");
				}
				catch(Exception e)
				{
					logger.error("In Synch Invoice Service: error : "+e+ "  at the time of fetching max id from both table");
				}
				finally
				{
					try
					{
					st.close();
					st1.close();
					rs.close();
					rs1.close();
					}
					catch(Exception e)
					{
						logger.error("In Synch Invoice Service: error  : "+e+ " in closing statement or resultset at the time of fetching max id from both table");
					}
				}
				
				
				 
				 try
				 {
					 JSONObject obj=new JSONObject(item);
					
					 JSONArray arr = obj.getJSONArray("userid");
					 JSONArray arr1 = obj.getJSONArray("invoice_id");
					 JSONArray arr2 = obj.getJSONArray("invoice_dt");
					 JSONArray arr3 = obj.getJSONArray("invoice_desc");
					 JSONArray arr4 = obj.getJSONArray("customer_name");
					 JSONArray arr5 = obj.getJSONArray("customer_gst");
					 JSONArray arr6 = obj.getJSONArray("customer_mob");
					 JSONArray arr7 = obj.getJSONArray("paid_flag");
					 JSONArray arr8 = obj.getJSONArray("total_disc_amt");
					 JSONArray arr9 = obj.getJSONArray("paid_via");
					 JSONArray arr10 = obj.getJSONArray("payment_Ref");
					 JSONArray arr11 = obj.getJSONArray("total_inv_amt");
					 JSONArray arr12 = obj.getJSONArray("cgst_amt");
					 JSONArray arr13 = obj.getJSONArray("sgst_amt");
					 JSONArray arr14 = obj.getJSONArray("igst_amt");
					 JSONArray arr15 = obj.getJSONArray("invoice_item_seq");
					 JSONArray arr16 = obj.getJSONArray("item_id");
					 JSONArray arr17 = obj.getJSONArray("item_uom");
					 JSONArray arr18 = obj.getJSONArray("item_qty");
					 JSONArray arr19 = obj.getJSONArray("item_rate");
					 JSONArray arr20 = obj.getJSONArray("item_dis_rt");
					 JSONArray arr21 = obj.getJSONArray("total_dis_on_item");
					 
					 
					
				
					 
					 String hdr="insert into invoice_hdr(userid,invoice_id, invoice_dt, invoice_desc, customer_name,"
					 		+ "customer_gst, customer_mob,paid_flag,total_disc_amt, paid_via, payment_Ref, "
					 		+ "total_inv_amt, cgst_amt,sgst_amt,igst_amt ) "
					 		+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					 
					 for(int i=0; i<arr.length() && i<arr2.length() && i<arr3.length() && i<arr4.length() && i<arr5.length() && i<arr6.length() && i<arr7.length() && i<arr8.length() && i<arr9.length() && i<arr10.length() && i<arr11.length() && i<arr12.length() && i<arr13.length() && i<arr14.length(); i++)
					 {
						
						 //System.out.print(arr.getInt(i));
						 
						 PreparedStatement ps = con.prepareStatement(hdr);
							
						 ps.setInt(1, arr.getInt(i));
						 ps.setString(2,arr1.getString(i));
						 ps.setString(3, arr2.getString(i));
						 ps.setString(4, arr3.getString(i));
						 ps.setString(5, arr4.getString(i));
						 ps.setString(6, arr5.getString(i));
						 ps.setString(7, arr6.getString(i));
						 ps.setString(8, arr7.getString(i));
						 ps.setString(9, arr8.getString(i));
						 ps.setString(10, arr9.getString(i));
						 ps.setString(11, arr10.getString(i));
						 ps.setString(12, arr11.getString(i));
						 ps.setDouble(13, arr12.getDouble(i));
						 ps.setDouble(14, arr13.getDouble(i));
						 ps.setDouble(15, arr14.getDouble(i));
						 
						 ps.executeUpdate();
						 
					 }
					 
					 
					 
					
					 
					 String line="insert into invoice_line(userid,invoice_id, invoice_dt, invoice_item_seq,"
					 		+ " item_id,invoice_desc, item_uom, item_qty,item_rate, item_dis_rt,total_dis_on_item,"
					 		+ " total_dis_amt,cgst_amt, sgst_amt, igst_amt) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					 
					 
					 for(int i=0; i<arr.length() && i<arr1.length() && i<arr2.length() && i<arr15.length() && i<arr16.length() && i<arr3.length() && i<arr17.length() && i<arr18.length() && i<arr19.length() && i<arr20.length() && i<arr21.length() && i<arr8.length() && i<arr12.length() && i<arr13.length() && i<arr14.length(); i++)
					 {
						 
						 
						 PreparedStatement ps1 = con.prepareStatement(line);
						 
						 ps1.setString(1, arr.getString(i));
						 ps1.setString(2, arr1.getString(i));
						 ps1.setString(3, arr2.getString(i));
						 ps1.setString(4, arr15.getString(i));
						 ps1.setString(5, arr16.getString(i));
						 ps1.setString(6, arr3.getString(i));
						 ps1.setString(7, arr17.getString(i));
						 ps1.setString(8, arr18.getString(i));
						 ps1.setString(9, arr19.getString(i));
						 ps1.setString(10, arr20.getString(i));
						 ps1.setString(11, arr21.getString(i));
						 ps1.setString(12, arr8.getString(i));
						 ps1.setString(13, arr12.getString(i));
						 ps1.setString(14, arr13.getString(i));
						 ps1.setString(15, arr14.getString(i));
						 
						 
						 ps1.executeUpdate();
						 
					 }
				
					 
				 }
				 catch(JSONException e)
				 {
					 flag=1;
					 logger.error("In Synch Invoice Service: error  : "+e+ " in json object");
				 }
				 catch(SQLException e)
				 {
					 flag=1;
					 logger.error("In Synch Invoice Service: error : "+e+ "  in preparestatement at the time of synch invoice");
				 }
				 catch(Exception e)
				 {
					 
					 flag=1;
					 logger.error("In Synch Invoice Service: error  : "+e+ " at the time of synch invoice");
				 }
				 
				 
				
				 
				 if(flag==1)     /*deleting data if failed*/
				 {
					 
					 String delhdr="delete from invoice_hdr where id>"+hdrid;
					 
					try
					{
						PreparedStatement ps2 = con.prepareStatement(delhdr);
						
						ps2.executeUpdate();
					}
					catch(SQLException e)
					{
						logger.error("In Synch Invoice Service: error  : "+e+ " in preparestatement at the time of deleting data if failed");
					}
					catch(Exception e)
					{
						logger.error("In Synch Invoice Service: error : "+e+ "  at the time of deleting data if failed");
					}
					 
					 
					 
					 
					 
					 String delline="delete from invoice_line where id>"+lineid;
					 
					 try
					 {
					    PreparedStatement ps3 = con.prepareStatement(delline);
						
						ps3.executeUpdate();
					 }
					 catch(SQLException e)
					 {
						 logger.error("In Synch Invoice Service: error : "+e+ "  in preparestatement at the time of deleting data if failed"); 
					 }
					 catch(Exception e)
					 {
						 logger.error("In Synch Invoice Service: error  : "+e+ "  at the time of deleting data if failed");
					 }
					 return "fail";
					 
				 }
				
				
				return "success";
			}


/*----------------------------------------GST DETAILS--------------------------------------------------------*/			

    /*18.-------------------------SET GST DETAILS-------------------------------------------------------*/
			
		 public void  setGstDetails() {
				 
				   
				   Gst g1=new Gst("C1","5 % IGST",5,"2017-07-01","9999-12-31",5,0,0);
				   	getGST(g1);
				   	
				   	Gst g2=new Gst("C2","5 % CGST & SGST",5,"2017-07-01","9999-12-31",0,2.5,2.5);
				   	getGST(g2);		   
				   	
				   	Gst g3=new Gst("C3","12 % IGST",12,"2017-07-01","9999-12-31",12,0,0);
				   	getGST(g3);		   
				   	
				   	Gst g4=new Gst("C4","12 % CGST & SGST",12,"2017-07-01","9999-12-31",0,6,6);
				   	getGST(g4);		   
				   	
				   	Gst g5=new Gst("C5","18 % IGST",18,"2017-07-01","9999-12-31",18,0,0);
				   	getGST(g5);		   
				   	
				   	Gst g6=new Gst("C6","18 % CGST & SGST",18,"2017-07-01","9999-12-31",0,9,9);
				   	getGST(g6);	
				   	
				   	Gst g7=new Gst("C7","28 % IGST",28,"2017-07-01","9999-12-31",28,0,0);
				   	getGST(g7);
				   	
				   	Gst g8=new Gst("C8","28 % CGST & SGST",28,"2017-07-01","9999-12-31",0,14,14);
				   	getGST(g8);		   
				   
			   }
			   public void  getGST(Gst g) {
				   
				   
				   DatabaseConnection db=new DatabaseConnection();
					Connection con=db.getConnection();
					
				   String gstSQL="insert into gsttable(gstid,gstdesc,gstper,gststartdate,gstenddate,igst,cgst,sgst)  values(?,?,?,?,?,?,?,?)";
				   try {
					   PreparedStatement st=con.prepareStatement(gstSQL);
					   st.setString(1,g.getGstid());
					   st.setString(2, g.getGstdesc());
					   st.setDouble(3, g.getGstper());
					   st.setString(4, g.getGststartdate());
					   st.setString(5, g.getGstenddate());
					   st.setDouble(6, g.getIgst());
					   st.setDouble(7, g.getCgst());
					   st.setDouble(8, g.getSgst());
					
					   st.executeUpdate();
				   		}
				   		catch(SQLException e)
				   		{
				   			logger.error("In Set Gst Detail Service: error  : "+e+ " in preparestatement");
				   		}
				   
				   		catch(Exception e) 
				   		{
				   			logger.error("In Set Gst Detail Service: error : "+e+ " ");
				   		}
				   		
			   }

			   
			   
/*19.-------------------------Show GST Details---------------------------------------------*/
			   
			   public String getGstDetails() {
				   
				   DatabaseConnection db=new DatabaseConnection();
					Connection con=db.getConnection();
				   
				   String getGst="select  gstid,gstdesc,gstper,igst,cgst,sgst  from gsttable";
				   
				   JSONObject jo=new JSONObject();
				   JSONArray ja=new JSONArray();
				   JSONArray ja1=new JSONArray();
				   JSONArray ja2=new JSONArray();
				   JSONArray ja3=new JSONArray();
				   JSONArray ja4=new JSONArray();
				   JSONArray ja5=new JSONArray();
				   
				   try {
				   jo.put("gstid", ja);
				   jo.put("gstdesc", ja1);
				   jo.put("gstper", ja2);
				   jo.put("igst", ja3);
				   jo.put("cgst", ja4);
				   jo.put("sgst", ja5);
				   }
				   catch(JSONException e)
				   {
					   logger.error("In Get GST Details: error : "+e+ "  in JSON object");
				   }
				   
				   
				   try {
					
					   Statement st=con.createStatement();
					   ResultSet rs=st.executeQuery(getGst);
					 
					   while(rs.next()) {
						  
					   
						       ja.put(rs.getString(1));
						       ja1.put(rs.getString(2));
						       ja2.put(rs.getDouble(3));
						       ja3.put(rs.getDouble(4));
						       ja4.put(rs.getDouble(5));
						       ja5.put(rs.getDouble(6));
						       //ja.put(rs.getString(2));
						 
						  
						  // g1.add(g);
						  
						   
					   
					   }
					   
				   }
				   catch(SQLException e)
				   {
					   logger.info("In Show GST service: error : "+e+ " in create statement or resultset at the time of fetching gst details");
				   }
				   catch(Exception e)
				   {
					   logger.info("In Show GST service: error : "+e+ "  at the time of fetching gst details");
				   }
				  
				   return jo.toString();
			   }
			   
			   
/*20.-----------------Insert New GST-----------------------------------------------------------*/
			   
	   
	public String insertGST(Gst gi)
	{
		
		DatabaseConnection db=new DatabaseConnection();
		Connection con=db.getConnection();
		
		
	int	flag=0;		   
		String sql="insert into gsttable(gstid,gstdesc,gstper,gststartdate,gstenddate,igst,cgst,sgst) values"
				   		+ "(?,?,?,?,?,?,?,?)";
				  
		
			JSONObject jo=new JSONObject();	   
			try {
					  
				PreparedStatement st=con.prepareStatement(sql);
					  
				st.setString(1, gi.getGstid());  
				st.setString(2,gi.getGstdesc());
				st.setDouble(3, gi.getGstper());
				st.setString(4, gi.getGststartdate());
				st.setString(5, gi.getGstenddate());
			    st.setDouble(6, gi.getIgst());
				st.setDouble(7, gi.getCgst());
				st.setDouble(8, gi.getSgst());
				st.executeUpdate();
				 
			   }
				catch(SQLException e)
				{
					logger.error("In GST Insert service: error : "+e+" in preparestatement at the time of insert gst");
				
					flag=1;
				}
				catch(Exception e) 
				{
					logger.error("In GST Insert service: error: "+e+" at the time of insert gst");
					flag=1;
					  
					
				 }
				   
				   if(flag==1)
				   {
					   try
					   {
					   jo.put("check", "failed");
					   }
					   catch(Exception e)
					   {
						   logger.error("In GST insert service: error"+e);
					   }
					   return jo.toString();
				   }
				   
				  try
				  {
				   jo.put("check", "success");
				  }
				  catch(Exception e)
				  {
					  logger.error("In Gst insert service: error"+e);
					  
				  }
				   
				   return jo.toString();
			   }
	
	
/*21.---------------------GST Update Service-------------------------------------------*/	
	
	
	public String updateGST(Gst gi)
	{
		
		DatabaseConnection db=new DatabaseConnection();
		Connection con=db.getConnection();
		
		
	int	flag=0;		   
	String sql="update gsttable set gstdesc=?,gstper=?,gststartdate=?,gstenddate=?,igst=?,cgst=?,sgst=? "
				+ "where gstid=?";
		
			JSONObject jo=new JSONObject();	
			
			String id="'"+gi.getGstid()+"'";
			String gsttab="select gstid from gsttable where gstid="+id;
				
			try
			{
				Statement st1=con.createStatement();
				ResultSet rs=st1.executeQuery(gsttab);
				if(rs.next()==false)
				{
					try
					{
					jo.put("check", "gstid_not_found");
					return jo.toString();
					}
					catch(Exception e)
					{
						logger.error("In gst update service: error: "+e);
					}
				}
			}
			catch(SQLException e)
			{
				logger.error("In Gst Update Service: error: "+e);
			}
			catch(Exception e)
			{
				logger.error("In gst update service: error: "+e);
			}
				
				
			
			
			try {
					  
				PreparedStatement st=con.prepareStatement(sql);
					  
				 
				st.setString(1,gi.getGstdesc());
				st.setDouble(2, gi.getGstper());
				st.setString(3, gi.getGststartdate());
				st.setString(4, gi.getGstenddate());
			    st.setDouble(5, gi.getIgst());
				st.setDouble(6, gi.getCgst());
				st.setDouble(7, gi.getSgst());
				st.setString(8, gi.getGstid()); 
				st.executeUpdate();
				 
			   }
				catch(SQLException e)
				{
					logger.error("In GST update service: error "+e);
				
					flag=1;
				}
				catch(Exception e) 
				{
					logger.error("In GST update service: error "+e);
					flag=1;
					 
					
				 }
				   
				   if(flag==1)
				   {
					   try
					   {
					   jo.put("check", "failed");
					   }
					   catch(Exception e)
					   {
						   logger.error("In GST insert service: error"+e);
					   }
					   return jo.toString();
				   }
				   
				  try
				  {
				   jo.put("check", "success");
				  }
				  catch(Exception e)
				  {
					  logger.error("In Gst insert service: error"+e);
					  
				  }
				   
				   return jo.toString();
			   }
			   

	
	

}



















