package com.Ira.IraFinanceAPP1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;



import java.io.File;
import java.util.*;  
import javax.mail.*;  
import javax.mail.internet.*;  
import javax.activation.*; 

import org.apache.log4j.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;





		
public class Implementation{			
/*1------------------------OK----- FOR REGISTER NEW USER------------------------------*/
	
	 
	  final static Logger logger=Logger.getLogger(Implementation.class);
private static final String Syatem = null;
	 
	  
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
		    			   String log="insert into logincontrol(username,pswd,acctlocked,forcechgpwd,access,forcelogin, parentid)"
							 + "values(?,?,?,?,?,?,?)";
		    			
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
		    				st2.setInt(7, id);
		    				
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
	 
				String mail=null;
				String reg="select subid, emailid from registration";
					/*-----------Checking user exist in registration tbale or not----------------*/
					try
					{
							st1=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
							rs1=st1.executeQuery(reg);
				
							while(rs1.next())
							{
								if(user.equals(rs1.getString(1)))
								{
									temp=1;
									mail=rs1.getString(2);
									//System.out.println(mail);
									//break;
						
								}
							}
							if(temp==0) /* if user not exist in registration table */
							{
								

								jo.put("parentuser", "null");
								jo.put("check", "uincorrect" );
								jo.put("accl",  "null");
								jo.put("forcep",  "null");
								jo.put("access","null");
								//jo.put("akj", "arg1");
								jo.put("email","null");
								return jo.toString(); 
								
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
	 		
										if(user.equals(rs.getString(1)))
											{     
												flag=2;
											//System.out.println("h"); 
	 		
												if(password.equals(rs.getString(2)))
												
													{
														flag=4;
					
														jo.put("parentuser", rs.getString(1));
														jo.put("check","success" );
														jo.put("accl",rs.getString(3));
														jo.put("forcep",rs.getString(4));
														jo.put("access", rs.getInt(5));
														jo.put("email",mail);
			 		  
														//System.out.println(jo.toString());
														return jo.toString(); /*---if user and pass exist in logincontrol----*/
					
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
											//jo.put("akj", "arg1");
											jo.put("email","null");
											return jo.toString();    /*---if user name incorrect------*/
										}
			 
									if(flag==2)
										{
											jo.put("parentuser", "null");
											jo.put("check", "pincorrect" );
											jo.put("accl",  "null");
											jo.put("forcep",  "null");
											jo.put("access","null");
											jo.put("email","null");
			  
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
					Statement st3=null;
					try
					{
						st3=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					    rs3=st3.executeQuery(subu);
					    
					    if(rs3.next()==false)
					    {
					    	
					    }
					    else
					    {
						rs3.next();
						id=rs3.getInt(1); 
					    }
					    /*-selecting sub user parent id----*/
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
					/*finally
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
					*/
					String submail="select  emailid from registration where subid="+id;
					int flag1=0;
					String submailid=null;
					ResultSet rs2=null;
					try       /*----checking user name and password of subuser is exist or  not-----*/
					{
						st1=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						rs2=st1.executeQuery(login);
						
						ResultSet rs7=st1.executeQuery(submail);
						rs7.next();
						submailid=rs7.getString(1);
						
						while(rs2.next())
						{
							if(user.equals(rs2.getString(1)))
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
											jo.put("email",submailid);
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
							jo.put("email","null");
							return jo.toString();   /*---if subuser id is incorrect----*/
						}
						
						if(flag1==1)
						{
							jo.put("parentuser", "null");
							jo.put("check", "pincorrect" );
							jo.put("accl",  "null");
							jo.put("forcep",  "null");
							jo.put("access","null");
							jo.put("email","null");
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
			
			JSONObject jo=new JSONObject();
			try
			{
			jo.put("check", "success");
			}
			catch(Exception e)
			{
				logger.error("In Temp Password Service: error: "+e);
			}
			
			
			
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
				{
					jo.put("check", "unotexist");
					return jo.toString();
				}
					
			
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
			
					return jo.toString();
			}	
	
/*6.----------ok-----------------------RESET PASSWORD----------------------------------------------------*/	
	
	
	public String getResetPassword(String user,String pass)
	{
		
		DatabaseConnection db=new DatabaseConnection();
		Connection con=db.getConnection();
	
		Statement st=null;
		ResultSet rs=null;
		
		int flag=0;
		
		JSONObject jo=new JSONObject();
		try
		{
		jo.put("check", "reset");
		}
		catch(Exception e)
		{
			logger.error("In Reset Password Service: error: "+e);
		}
		
			
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
										//System.out.println("main");
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
											
											return jo.toString();
	    	      
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
					
					}
					catch(Exception e)
					{
						logger.error("In reset password service: error  : "+e+ " at the time of closing statement or resultset or connection");
					}
				}
						
				
				int temp=0;		
						
			if(flag==0)
			{
				String sub="select  childUserName from subuser";
				
				try
				{
					Statement st4=con.createStatement();
					ResultSet rs4=st4.executeQuery(sub);
				
					while(rs4.next())
					{
						//System.out.println("sub");
						if(rs4.getString(1).equals(user))
						{
							//System.out.println("sub1");
							String subu="update subuser set childPassword=? , forcelogin=? where  childUserName=?";
							String log="update logincontrol set pswd=? ,forcechgpwd=?, forcelogin=? where username=?";
					
					
							PreparedStatement ps1 = con.prepareStatement(subu);
							PreparedStatement fcp1 = con.prepareStatement(log);

							ps1.setString(1, pass);
							ps1.setString(2, "false");
							ps1.setString(3, user);
						
							fcp1.setString(1, pass);
							fcp1.setString(2, "false");
							fcp1.setString(3, "false");
							fcp1.setString(4, user);

							ps1.executeUpdate();
							fcp1.executeUpdate();
						
							temp=1;
							flag=1;
						
						
						}
					}
				
				}
				catch(Exception e)
				{
					logger.error("In Reset Password Service: error: "+e);
				}
			
				if(flag==0 || temp==0)
				{
					try
					{
					jo.put("check", "unoexist");
					return jo.toString();
					}
					catch(Exception e)
					{
						logger.error("In Reset Password Service: error: "+e);
					}
				}
			}			
				
				
			return jo.toString();	
				
			
						
						
						
		
	}
	

	





/*7.----------ok------------------------SUB USER REGISTRATION----------------------------------------------------*/		
	
	public String createSubUser(RegisterSubUser subuser )
		{ 
		
			DatabaseConnection db=new DatabaseConnection();
			Connection con=db.getConnection();
			
			
			JSONObject jo=new JSONObject();
			try
			{
				jo.put("check", "success");
			}catch(Exception e)
			{
				logger.error("In Sub User Registration Service: error: "+e);
			}
			
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
				{
					jo.put("check","pnotexist");
					return jo.toString();
				}
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
							{
								jo.put("check","subexist");
								return jo.toString();
							}
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
				
						return jo.toString();
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
		
		/*		String main="select subId from registration where subid="+username;
				Statement st=null;
				ResultSet rs=null;
				try
					{
			
						 st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						 rs=st.executeQuery(main);
						 
						 if(rs.next()==false)
						 {
							 return "munotexist";
						 }
							 
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
		
		
		*/
		
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
					    	//ja.put();
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
						 
						 else
						 {
							if(rs.getString(1).equals(username))/*--when subuser exist--*/
								{   
								
									flag=1;
									//break;
								}
						 }
						
						if(flag==1)
							{
								while(rs1.next())
									if(rs1.getString(1).equals(username))
									{ 
										
										if(rs1.getString(4).equals("false"))/*--when accl is not lock--*/
											{   flag=2;
	    		
												jo.put("check","suexist");
												jo.put("accl", "false");
												jo.put("access", rs1.getString(5));
													
												//return jo.toString(); /*--when sub user exist and accl not lock--*/
											}
							}	}
	     	
	     	
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
			
			
			JSONObject jo=new JSONObject();
			
			try
			{
				jo.put("check", "success");
			}
			catch(Exception e)
			{
				logger.error("In Edit Sub User Access Service: error: "+e);
			}
			
			
			//System.out.println(pass+access);
					try  /*---checking sub user exist or not---*/
					{
						String sub="select childUserName from subuser where  childUserName="+newsubuser;
						 st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						
						 rs=st.executeQuery(sub);
						  
						if(rs.next()==false)
						{
							jo.put("check", "uincorrect");
							return jo.toString();
						}
							
							 
					
				
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
			
				return jo.toString();
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
				//System.out.println(e);
				logger.error("In Synch item service :error  : "+e+ "  at the time parsing data in item table");
				flag=1;
			}
			catch(SQLException e)
			{
				//System.out.println(e);
				logger.error("In Synch item service :error  : "+e+ "  at the time of inserting data");
				flag=1;
			}
			catch(Exception e)
			{
				//System.out.println(e);
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
		
		
		public String getAllItem(String id,String date)
		{
			

			DatabaseConnection db=new DatabaseConnection();
			Connection con=db.getConnection();
			
			int flag=0;
			
			
			//String dat="select  startdate from itemmain where startDate<'"+date+"' order by startdate desc limit 1";
			
			
			Statement st1=null;
			ResultSet rs1=null;
			String datestring="'"+date+"'";      /*selecting current date */
			
			String itemdetail="select *from itemmain where enddate>="+datestring +"and subid="+id;
			
			//JSONObject objmain=new JSONObject();
			//JSONArray jamain=new JSONArray();
			
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
						
					if(rs.next()==false)
					{
						//jamain.put(jo);
						//objmain.put("check", "fail");
						//objmain.put("item", jamain);
						//return objmain.toString();
						
						jo.put("check", "fail");
						return jo.toString();
					}
					int i=0;
					rs.previous();
					while(rs.next())
							{
								System.out.println(++i);
								flag=1;
								//objmain.put("check", "success");
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
				
				
								String itemid=rs.getString(3);
								String item="'"+itemid+"'";
								int version=Integer.parseInt(rs.getString(12))-1;
								//System.out.println(version);
								
								String olditem="select *from itemmain where itemId="+item+"and version="+version +" and subid="+id;
								
								Statement st2=con.createStatement();
								ResultSet rs2=st2.executeQuery(olditem);
								
								if(rs2.next()==true)
								{
									System.out.println("old_version");
									ja.put(rs2.getInt(2));
									ja1.put(rs2.getString(3));
									ja2.put(rs2.getString(4));
									ja3.put(rs2.getString(5));
									ja4.put(rs2.getString(6));
									ja5.put(rs2.getString(7));
									ja6.put(rs2.getString(8));
									ja7.put(rs2.getString(9));
									ja8.put(rs2.getString(10));
									ja9.put(rs2.getString(11));
									ja10.put(rs2.getString(12));
								}
								
							}
								
								//jamain.put(jo);
								//objmain.put("check", "success");
								//objmain.put("item", jamain);
								jo.put("check", "success");
							

						if(flag==0)
						{
							//jamain.put(jo);
							//objmain.put("check", "fail");
							//objmain.put("item", jamain);
							jo.put("check", "fail");
							return jo.toString();

						}
						
						
			
			}
			catch(JSONException e)
			{
				logger.error("In Item Detail Service: error  : "+e+ "  at the time of fetching active item");
				System.out.println(e);
			}
			catch(SQLException e)
			{
				logger.error("In Item Detail Service: error  : "+e+ "  at the time of fetching active item");
				System.out.println(e);
			}
			catch(Exception e)
			{
				
				logger.error("In Item Detail Service: error  : "+e+ " at the time of fetching active item");
				System.out.println(e);
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
					System.out.println(e);
				}
			}
			
			
			
			return  jo.toString();
		}

		
/*13.----------ok--------------Login DETAIL BY USER NAME------------------------------------------------------------------*/		

		public String getLoginDetail(String username)
		{
			
			
			DatabaseConnection db=new DatabaseConnection();
			Connection con=db.getConnection();
			
			
			
			
			JSONObject jo=new JSONObject();
			
			
			String name="'"+username+"'";
			
			
			
			String log="select *from logincontrol";
			
			Statement st=null;
			ResultSet rs=null;					/*fetching detail from login control*/
		try
			{
				
				 
			
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
				
				
				
				
				JSONObject obj=new JSONObject(username);
			    JSONArray objja=obj.getJSONArray("username");
			    
			    for(int i=0; i<objja.length(); i++)
			    {
			    	System.out.println(objja.getString(i));
			    
			    
				
				
					
					
					 st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					 rs=st.executeQuery(log); 
					
					 int temp=0;
					 while(rs.next())
					 {
						
						 temp=0;
						 if(rs.getString(1).equals(objja.getString(i)))
						 
						 {
							
								 ja.put(rs.getString(1));
								 ja1.put(rs.getString(2));
								 ja2.put(rs.getString(3));
								 ja3.put(rs.getString(4));
								 ja4.put(rs.getString(5));
								 ja5.put(rs.getString(6));
								 ja6.put(rs.getString(7));
						   
							temp=1;
							break;
						
							 
						 }
						 
						 
					 }
					 
					 if(temp==0)
					 {
						 ja.put("null");
						 ja1.put("null");
						 ja2.put("null");
						 ja3.put("null");
						 ja4.put("null");
						 ja5.put("null");
						 ja6.put("null");
						 
					 }
					 
					 
				
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
		public String getReportHDR(String parent,String date1,String date2)
		{
			
			DatabaseConnection db=new DatabaseConnection();
			Connection con=db.getConnection();
			
			DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
			java.util.Date dat1=null;
			java.util.Date dat2=null;
			
			int checkfilegeneration=0,sendstatus=0;
			
			String tomail=null;
			
			JSONObject jo=new JSONObject();
			
				try 
				{
					dat1 =  formatter.parse(date1);
					dat2 = formatter.parse(date2);
				} 
				catch (ParseException e)
				{
					logger.error("In Details of report Service: error: "+e);
				//e.printStackTrace();
				}
				
			
			
				int isdate=0;
			
			
				String invoicehdr="select  userid,invoice_dt,customer_email from invoice_hdr";
			
					Statement st=null;
					ResultSet rs=null;
					try
					{
						st = con.createStatement();
						rs = st.executeQuery(invoicehdr);
					}
					catch (SQLException e) 
					{
						logger.error("In Details of report Service: error: "+e);
						//e.printStackTrace();
					}
					
					System.out.println(invoicehdr);
				
					//int less=dat1.compareTo(rs.getDate(2));
					//int greater=dat1.compareTo(rs.getDate(2));
					//int equa=dat1.compareTo(rs.getDate(2));
		 			
					
					try {
							if(rs.next()==false)
							{
								try
								{
									jo.put("check", "notexist");
								} 
								catch (JSONException e)
								{
					
									logger.error("In Details of report Service: error: "+e);
									//e.printStackTrace();
								}
								return jo.toString();
						}
						//rs.previous();
							int count=0;
						while(rs.next())
						{
							
							
							if(rs.getString(1).equals(parent) &&( (dat2.compareTo(rs.getDate(2)))>0 && (dat1.compareTo(rs.getDate(2)))<0 ||(date1.equals(rs.getString(2)) || date2.equals(rs.getString(2)))))
							{
								tomail=rs.getString(3);
								System.out.println(rs.getInt(1));
								isdate=1;
								count++;
								break;
							}
							
						}
						System.out.println(count);
					} catch (SQLException e) {
						logger.error("In Details of report Service: error: "+e);
						//e.printStackTrace();
					}
				
					
				
					
					if(isdate==0)        /* if date not exist */
						{
							try
								{
								jo.put("check", "notexist");
								} 
								catch (JSONException e)
							{
						
									logger.error("In Details of report Service: error: "+e);
									//e.printStackTrace();
							}
							return jo.toString();
						}
					else     /* if date exist then generate excel file */
					{
						
						
						try
						{
						Statement statement = con.createStatement();
						
							System.out.println(parent);
								FileOutputStream fileOut;
								fileOut = new FileOutputStream("C:\\Excel_File\\reportof"+parent+".xls");
								
								HSSFWorkbook workbook = new HSSFWorkbook();
								HSSFSheet worksheet = workbook.createSheet("Sheet 0");
				        
								Row row1 = worksheet.createRow((short)0);
								row1.createCell(0).setCellValue("userId");
								row1.createCell(1).setCellValue("invoiceId");
								row1.createCell(2).setCellValue("invoiceDate");
								row1.createCell(3).setCellValue("customerName");
								row1.createCell(4).setCellValue("customerGst");
								row1.createCell(5).setCellValue("customerMobile");
								row1.createCell(6).setCellValue("paidFlag");
								row1.createCell(7).setCellValue("totalDis_Amount");
								row1.createCell(8).setCellValue("paidVia");
								row1.createCell(9).setCellValue("paymentRef");
								row1.createCell(10).setCellValue("total_Inv_Amt");
								row1.createCell(11).setCellValue("cgst_amt");
								row1.createCell(12).setCellValue("sgst_amt");
								row1.createCell(13).setCellValue("igst_amt");
								row1.createCell(14).setCellValue("customer_email");
								row1.createCell(15).setCellValue("total_gst");
								row1.createCell(16).setCellValue("parent_id");
								
								Row row2 ;
				        
								ResultSet rs3 = statement.executeQuery("SELECT  *FROM invoice_hdr");
								//int count=0;
								int a=1;
								while(rs3.next())
								{
									if(rs3.getString(2).equals(parent) &&( (dat2.compareTo(rs3.getDate(4)))>0 && (dat1.compareTo(rs3.getDate(4)))<0 ||(date1.equals(rs3.getString(4)) || date2.equals(rs3.getString(4)))))
									{	
										//count++;
										 //System.out.println(parent);
										//int a = rs3.getRow();
										checkfilegeneration=1;
										row2 = worksheet.createRow((short)a);
										row2.createCell(0).setCellValue(rs3.getString(2));
										row2.createCell(1).setCellValue(rs3.getString(3));
										row2.createCell(2).setCellValue(rs3.getString(4));
										row2.createCell(3).setCellValue(rs3.getString(5));
										row2.createCell(4).setCellValue(rs3.getString(6));
										row2.createCell(5).setCellValue(rs3.getString(7));
										row2.createCell(6).setCellValue(rs3.getString(8));
										row2.createCell(7).setCellValue(rs3.getString(9));
										row2.createCell(8).setCellValue(rs3.getString(10));
										row2.createCell(9).setCellValue(rs3.getString(11));
										row2.createCell(10).setCellValue(rs3.getString(12));
										row2.createCell(11).setCellValue(rs3.getString(13));
										row2.createCell(12).setCellValue(rs3.getString(14));
										row2.createCell(13).setCellValue(rs3.getString(15));
										row2.createCell(14).setCellValue(rs3.getString(16));
										row2.createCell(15).setCellValue(rs3.getString(17));
										row2.createCell(16).setCellValue(rs3.getString(18));
										
										++a;
									}
								}
				      // System.out.println(count);
								workbook.write(fileOut);
								fileOut.flush();
								fileOut.close();
								rs3.close();
								statement.close();
								//con.close();
								//System.out.println("Export Success");
							}
							catch(SQLException ex)
							{
								checkfilegeneration=2;
								//System.out.println(ex);
								logger.error("In Details of report Service: error: "+ex);
							}
							catch(IOException ioe)
							{
								checkfilegeneration=2;
								//System.out.println(ioe);
								logger.error("In Details of report Service: error: "+ioe);
							}
					
						}	
						
					
					
					if(checkfilegeneration==1) /* sending mail if file generated */
					{
						 final String username = "support@iratechnologies.com";
							final String password = "India@123";
							String tosend=tomail;
							// setting gmail smtp properties
							Properties props = new Properties();
							props.put("mail.smtp.auth", "true");
							props.put("mail.smtp.starttls.enable", "true");
							props.put("mail.smtp.host", "smtp.gmail.com");
							props.put("mail.smtp.port", "587");

							// check the authentication
							Session session = Session.getInstance(props, new javax.mail.Authenticator() {
								protected PasswordAuthentication getPasswordAuthentication() {
									return new PasswordAuthentication(username, password);
								}
							});

							try {

								Message message = new MimeMessage(session);
								message.setFrom(new InternetAddress("support@iratechnologies.com"));

								// recipients email address
								message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(tosend));

								// add the Subject of email
								message.setSubject("Support Ira Technologies");

								Multipart multipart = new MimeMultipart();

								// add the body message
								BodyPart bodyPart = new MimeBodyPart();
								bodyPart.setText("This is the Invoice Requested Report for a Period. Thank You");
								multipart.addBodyPart(bodyPart);

								// attach the file
								MimeBodyPart mimeBodyPart = new MimeBodyPart();
								try
								{
								mimeBodyPart.attachFile(new File("C:\\Excel_File\\reportof"+parent+".xls"));
								}
								catch(Exception e)
								{
									sendstatus=1;
									//System.out.println(e);
									logger.error("In Details of report Service: error: "+e);
								}
								multipart.addBodyPart(mimeBodyPart);

								message.setContent(multipart);

								Transport.send(message);

								//System.out.println("Email Sent Successfully");

							} catch (MessagingException e)
							{
								sendstatus=1;
								//e.printStackTrace();
								logger.error("In Details of report Service: error: "+e);

							}
					
						
					}
					else
					{
						try {
							jo.put("check", "fail");
							return jo.toString();
						} 
						catch (JSONException e)
						{
							logger.error("In Details of report Service: error: "+e);
							//e.printStackTrace();
						}
					}
					
					
					
					if(sendstatus==1)
					{

						try {
							jo.put("check", "fail");
							return jo.toString();
						} 
						catch (JSONException e)
						{
							logger.error("In Details of report Service: error: "+e);
							//e.printStackTrace();
						}
					}
					
					
					/* deleting file which is created */
					try
			        {
			            Files.deleteIfExists(Paths.get("C:\\Excel_File\\reportof"+parent+".xls"));
			        }
			        catch(NoSuchFileException e)
			        {
			            //System.out.println("No such file/directory exists");
			            logger.error("In Details of report Service: error: "+e);
			        }
			        catch(DirectoryNotEmptyException e)
			        {
			            //System.out.println("Directory is not empty.");
			            logger.error("In Details of report Service: error: "+e);
			        }
			        catch(IOException e)
			        {
			            //System.out.println("Invalid permissions.");
			            logger.error("In Details of report Service: error: "+e);
			        }
					
					
					
					/* after send successfully mail */
				
					try {
						jo.put("check", "success");
						} 
					catch (JSONException e)
					{
						logger.error("In Details of report Service: error: "+e);
						//e.printStackTrace();
					}
					return jo.toString();
			
		}

		
/*15.--------ok------------DETAILS OF REPORT By INVOICE_ID----------------------------------------------------------------*/	
		
		public String getInvoiceDetail(String invoiceid)
		{
			DatabaseConnection db=new DatabaseConnection();
			Connection con=db.getConnection();
			String inid="'"+invoiceid+"'";
	        // String ch="idnotexist";
			JSONObject jomain=new JSONObject();
			
			JSONArray jamain=new JSONArray();
	         
			JSONObject jo=new JSONObject();
			try
			{
				//jomain.put("check", "idnotexist");
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
			
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
			JSONArray ja15=new JSONArray();
			JSONArray ja16=new JSONArray();
			
			
			jo.put("username", ja);										
			jo.put("invoiceId", ja1);
			jo.put("invoiceDate", ja2);
			
			jo.put("customerName", ja3);
			jo.put("customerGst", ja4);
			jo.put("customerMobile", ja5);
			jo.put("paidFlag", ja6);
			jo.put("totalDiscount", ja7);
			jo.put("paidVia", ja8);
			jo.put("paymentRef", ja9);
			jo.put("totalInvoiceAmount", ja10);
			jo.put("cGstAmount", ja11);
			jo.put("sGstAmount", ja12);
			jo.put("iGstAmount" , ja13);
			jo.put("customerEmail", ja14);
			jo.put("gstAmount", ja15);
			jo.put("parentUser", ja16);
			
			
			JSONArray j=new JSONArray();     /*  for invoice_line   */
			JSONArray j1=new JSONArray();
			JSONArray j2=new JSONArray();
			JSONArray j3=new JSONArray();
			JSONArray j4=new JSONArray();
			JSONArray j5=new JSONArray();
			JSONArray j6=new JSONArray();
			JSONArray j7=new JSONArray();
			JSONArray j8=new JSONArray();
			JSONArray j9=new JSONArray();
			JSONArray j10=new JSONArray();
			JSONArray j11=new JSONArray();
			JSONArray j12=new JSONArray();
			JSONArray j13=new JSONArray();
			JSONArray j14=new JSONArray();
			JSONArray j15=new JSONArray();
			
			JSONObject o=new JSONObject();
			JSONArray jaline=new JSONArray();
			o.put("username", j);
			o.put("invoiceId", j1);
			o.put("invoiceDate", j2);
			o.put("itemSequence", j3);
			o.put("itemId", j4);
			o.put("itemQuantity", j5);
			o.put("itemRate", j6);
			o.put("itemDiscount", j7);
			o.put("itemCgst", j8);
			o.put("itemSgst", j9);
			o.put("itemIgst", j10);
			o.put("itemVersion", j11);
			o.put("parentUser", j12);
			o.put("itemGst", j13);
			o.put("itemName", j14);
			o.put("netAmount", j15);
			
			
			
			
			
					/*fetching invoice_hdr detail by invoice id*/
			
			String hdr="select *from invoice_hdr where  invoice_id="+inid;
			 st=con.createStatement();
			 rs=st.executeQuery(hdr);
			
			if(rs.next()==false)
			{
				jamain.put(jo);
		        jomain.put("invoice_hdr", jamain);
				
			}
			rs.previous();
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
					ja12.put(rs.getString(14));
					ja13.put(rs.getString(15));
					ja14.put(rs.getString(16));
					ja15.put(rs.getString(17));
					ja16.put(rs.getString(18));
					
					//ch="idexist";
					jamain.put(jo);
				    jomain.put("invoice_hdr", jamain);
			 	}
			 
			 
			   /* fetching data from invoice_line table */
			
			 String line="select *from invoice_line where  invoice_id="+inid;
			 Statement st1=con.createStatement();
			 ResultSet rs1=st1.executeQuery(line);
			 
			 if(rs1.next()==false)
			 {
				    jaline.put(o);
				   	jomain.put("invoice_line", jaline);
				   	jomain.put("check", "fail");
			 }
			 
			 rs1.previous();
			 while(rs1.next())
			 {
				 
				    j.put(rs1.getString(2));
				 	j1.put(rs1.getString(3));
				 	j2.put(rs1.getString(4));
				 	j3.put(rs1.getString(5));
				 	j4.put(rs1.getString(6));
				 	j5.put(rs1.getString(7));
				 	j6.put(rs1.getString(8));
				 	j7.put(rs1.getString(9));
				 	j8.put(rs1.getString(10));
				 	j9.put(rs1.getString(11));
				 	j10.put(rs1.getString(12));
				 	j11.put(rs1.getString(13));
					j12.put(rs1.getString(14));
					j13.put(rs1.getString(15));
					j14.put(rs1.getString(16));
					j15.put(rs1.getString(17));
					
				   	jaline.put(o);
				   	jomain.put("invoice_line", jaline);
				   	jomain.put("check", "success");
				 
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
			
			return jomain.toString();
			
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
			
			
			public String setInvoice_hdr(String item)
			{
				
				DatabaseConnection db=new DatabaseConnection();
				Connection con=db.getConnection();
				
				int flag=0, inserthdr=0, insertline=0,fail=0;
				//System.out.println(item);
				JSONObject jo=new JSONObject();
				try
				{
					jo.put("check", "success");
				}catch(Exception e)
				{
					logger.error("In Synch Invoice Service: error: "+e);
				}
				
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
					logger.error("In Synch Invoice Service: error  : "+e+ " in creating ststement or resultset at the time of fetching max id invoice_hdr and invoice_line");
				}
				catch(Exception e)
				{
					logger.error("In Synch Invoice Service: error : "+e+ "  at the time of fetching max id from invoice_hdr and invoice_line");
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
					 JSONObject objmain=new JSONObject(item);
					 JSONArray  arrmain=objmain.getJSONArray("invoice");
					 
					 JSONObject objhdr=arrmain.getJSONObject(0);//  getJSONObject(arrmain.length()-1);
					 JSONObject objline=arrmain.getJSONObject(1);
					
					 
					
					 							/* Parsing for invoice_hdr table */
					 JSONArray arr =   objhdr.getJSONArray("username"); 
					 JSONArray arr1 =  objhdr.getJSONArray("invoiceId");
					 JSONArray arr2 =  objhdr.getJSONArray("invoiceDate");
					 JSONArray arr3 =  objhdr.getJSONArray("customerName");
					 JSONArray arr4 =  objhdr.getJSONArray("customerGst");
					 JSONArray arr5 =  objhdr.getJSONArray("customerMobile");
					 JSONArray arr6 =  objhdr.getJSONArray("paidFlag");
					 JSONArray arr7 =  objhdr.getJSONArray("totalDiscount");
					 JSONArray arr8 =  objhdr.getJSONArray("paidVia");
					 JSONArray arr9 =  objhdr.getJSONArray("paymentRef");
					 JSONArray arr10 = objhdr.getJSONArray("totalInvoiceAmount");
					 JSONArray arr11 = objhdr.getJSONArray("cGstAmount");
					 JSONArray arr12 = objhdr.getJSONArray("sGstAmount");
					 JSONArray arr13 = objhdr.getJSONArray("iGstAmount");
					 JSONArray arr14=  objhdr.getJSONArray("customerEmail");
					 JSONArray arr15=  objhdr.getJSONArray("gstAmount");
					 JSONArray arr16 = objhdr.getJSONArray("parentUser");
					 
					 							/* parsing for invoice_line table */
					 JSONArray ar=objline.getJSONArray("username");
					 JSONArray ar1=objline.getJSONArray("invoiceId");
					 JSONArray ar2=objline.getJSONArray("invoiceDate");
					 JSONArray ar3=objline.getJSONArray("itemSequence");
					 JSONArray ar4=objline.getJSONArray("itemId");
					 JSONArray ar5=objline.getJSONArray("itemQuantity");
					 JSONArray ar6=objline.getJSONArray("itemRate");
					 JSONArray ar7=objline.getJSONArray("itemDiscount");
					 JSONArray ar8=objline.getJSONArray("itemCgst");
					 JSONArray ar9=objline.getJSONArray("itemSgst");
					 JSONArray ar10=objline.getJSONArray("itemIgst");
					 JSONArray ar11=objline.getJSONArray("itemVersion");
					 JSONArray ar12=objline.getJSONArray("parentUser");
					 JSONArray ar13=objline.getJSONArray("itemGst");
					 JSONArray ar14=objline.getJSONArray("itemName");
					 JSONArray ar15=objline.getJSONArray("netAmount");
					 
					 
					 
					
					 //System.out.println("length of username arr"+arr.length());
					 
					 String hdr="insert into invoice_hdr(userid,invoice_id, invoice_dt,customer_name,"
					 		+ "customer_gst, customer_mob,paid_flag,total_disc_amt, paid_via, payment_Ref, "
					 		+ "total_inv_amt, cgst_amt,sgst_amt,igst_amt,customer_email,total_gst,parent_id ) "
					 		+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					 
					 for(int i=0; i<arr.length(); i++)
					 {
						
						 
						 String hd="select invoice_id from invoice_hdr";
						 
						    st1=con.createStatement();
							rs1=st1.executeQuery(hd);
							
							int insert=0;
							while(rs1.next())
							{
								if(rs1.getString(1).equals(arr1.getString(i)))
								{
									insert=1;
								}
							}
						 
						 
						 //System.out.print(arr.getInt(i));
						
							if(insert==0)
							{
								PreparedStatement ps = con.prepareStatement(hdr);
				 			
								ps.setString(1, arr.getString(i));
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
								ps.setDouble(13, Double.parseDouble(arr12.getString(i)));
								ps.setDouble(14, Double.parseDouble(arr13.getString(i)));
								ps.setString(15, arr14.getString(i));
								ps.setString(16, arr15.getString(i));
								ps.setString(17, arr16.getString(i));
						
						 
								ps.executeUpdate();
							}
						 
					 }
					 
					 
					 
					
					 
					 String line="insert into invoice_line(userid,invoice_id, invoice_dt,invoice_item_seq,"
					 		+ " item_id,item_qty,item_rate,total_dis_amt,cgst_amt, sgst_amt, igst_amt,"
					 		+ "item_version,parent_id,item_gst,item_name,item_net_amount)"
					 		+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					 
					 Statement st2=null;
					 ResultSet rs2=null;
					 for(int i=0; i<ar.length() ; i++)
					 {
						 
						 
						 String li="select invoice_id from invoice_line";
						 
						    st2=con.createStatement();
							rs2=st2.executeQuery(li);
							
							int insert1=0;
							while(rs2.next())
							{
								if(rs2.getString(1).equals(ar1.getString(i)))
								{
									insert1=1;
								}
							}
						 
						 
						 if(insert1==0)
						 {
							
						 PreparedStatement ps1 = con.prepareStatement(line);
						 
						 ps1.setString(1, ar.getString(i));
						 ps1.setString(2, ar1.getString(i));
						 ps1.setString(3, ar2.getString(i));
						 ps1.setString(4, ar3.getString(i));
						 ps1.setString(5, ar4.getString(i));
						 ps1.setString(6, ar5.getString(i));
						 ps1.setString(7, ar6.getString(i));
						 ps1.setString(8, ar7.getString(i));
						 ps1.setString(9, ar8.getString(i));
						 ps1.setString(10, ar9.getString(i));
						 ps1.setString(11, ar10.getString(i));
						 ps1.setString(12, ar11.getString(i));
						 ps1.setString(13, ar12.getString(i));
						 ps1.setString(14,ar13.getString(i));
						 ps1.setString(15,ar14.getString(i));
						 ps1.setString(16,ar15.getString(i));
						 
						 ps1.executeUpdate();
						 }
						 
					 }
					 
					 
				 }
				 catch(JSONException e)
				 {
					 fail=1;
					 logger.error("In Synch Invoice Service: error  : "+e+ " in json object");
					 System.out.println(e);
				 }
				 catch(SQLException e)
				 {
					 fail=1;
					 logger.error("In Synch Invoice Service: error : "+e+ "  in preparestatement at the time of synch invoice");
				 }
				 catch(Exception e)
				 {
					 
					 fail=1;
					 logger.error("In Synch Invoice Service: error  : "+e+ " at the time of synch invoice");
				 }
				 
				 
				
				 
				 if(fail==1)     /*deleting data if failed*/
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
					 
					
				 }
				 
				 if(fail==1)
				 {
					 try
					 {
						 jo.put("check", "fail");           /* If insertion fail*/
						 return jo.toString();
					 }
					 catch(Exception e)
					 {
						 logger.error("In Synch Invoce Service: error: "+e); 
					 }
				 }
				 
				 return jo.toString();      /*if insertion success */
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



















