package com.Ira.IraFinanceAPP1;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class BusinessLogic {
	
	
	final static Logger logger=Logger.getLogger(BusinessLogic.class);
/*1---------------------------OK----- FOR REGISTER NEW USER---------------------------------------------------------------- */
	
	public String create(String customerdetail)
	{
		
		RegisterUser userdetails=new RegisterUser();
		
		  try
		  {
			JSONObject obj=new JSONObject(customerdetail);
			
			userdetails.setPassword(obj.getString("pass"));
			userdetails.setShopname(obj.getString("shopname"));
			userdetails.setAddress(obj.getString("address"));
			userdetails.setMobilenumber(obj.getString("mobilenumber"));
			userdetails.setEmailid(obj.getString("emailid"));
			userdetails.setGstnumber(obj.getString("gstnumber"));
			userdetails.setAcctlocked(obj.getString("acctlocked"));
			userdetails.setSubstartdate(obj.getString("substartdate"));
			userdetails.setSubenddate(obj.getString("subenddate"));
			
			
			logger.info("User Details: Shopname: "+userdetails.getShopname()+", "+"Address: "+userdetails.getAddress()+ 
					 ", " + "MobileNo: "+userdetails.getMobilenumber()+ ", "+ "EmailId: "+userdetails.getEmailid()+
					 ", "+ "GstNo: "+userdetails.getGstnumber()+", "+  "AcctLocked: "+userdetails.getAcctlocked()+
					 ", "+ "StartDate: "+userdetails.getSubstartdate()+ ", "+ "EndDate: "+userdetails.getSubenddate());
			
			
		  }
		  catch(JSONException e)
		  {
			  logger.error("Error in Receiving User Details");
		  }
		
		
		
		if(userdetails.getPassword().isEmpty())
			return "Must enter password";
		if((userdetails.getPassword()).length()>40)
			return "password length must be less than or equal 40 character";
		
		if(userdetails.getShopname().isEmpty())
			return "Must enter shopname";
		if(userdetails.getShopname().length()>100)
			return "Shopname length must be les than or equal to 100 character";
		
		if(userdetails.getAddress().isEmpty())
			return "Address can't be null";
		if(userdetails.getAddress().length()>200)
			return "Address length must be less than or equal to 200 character";
		
		if(userdetails.getMobilenumber().isEmpty())
			return "Must Enter Mobile Number";
		if(userdetails.getMobilenumber().length()<10 || userdetails.getMobilenumber().length()>10)
			return "mobile number length must be 10 digits";
		
		if(userdetails.getEmailid().isEmpty())
			return "Must enter EmailId";
		if(userdetails.getEmailid().length()>40)
			return "EmailId length must be less than or equal to 40 character";
		
		if(userdetails.getGstnumber().isEmpty())
			return "Must enter GST Number";
		if(userdetails.getGstnumber().length()>15)
			return "GST Number can't be more than 15 character";
		
		if(userdetails.getAcctlocked().isEmpty())
			return "Must Enter AcctLocked";
		
		if(userdetails.getSubstartdate().isEmpty())
			return "Must Enter Subscription Start Date";
		
		if(userdetails.getSubenddate().isEmpty())
			return "Must Enter Subscription End Date";
		
		
		Implementation imp=new Implementation();
		
		return imp.create(userdetails);
		
	}
	
	
/*2-------------------------------------------FOR LOGIN USER----------------------------------------------------------------*/	

	
	public String getLoginUser(String logindetail)
	{
		String uname=null;
		String pass=null;
		
		try
		{
		 JSONObject obj=new JSONObject(logindetail);
		uname= obj.getString("uname");
		pass= obj.getString("pass");
		
		logger.info("trying to login with user name: "+uname);
		if(uname.isEmpty())
			return "Must enter username";
		if(uname.length()>10)
			return "User name can not be greater than 10 character";
		
		 if(pass.isEmpty())
			 return "Must enter password";
		 if(pass.length()>40)
			return "Password can not be greater than 40 character"; 
		 
		 
		}
		catch(JSONException e)
		{
			logger.error("error in JSON object in login block");
		}
		catch(Exception e)
		{
			logger.error("error in login block");
		}
		
		logger.info("In login service: trying to login with username is: "+" "+uname);
		
        Implementation imp=new Implementation();
		
		return imp.loginUser(uname,pass);
		
	}
	
/*3.------------ok---------------------FORGET USER NAME-------------------------------------------------------*/
	
	
	public String getForgetUser(String mobile)
	{
		if(mobile.isEmpty())
			return "You must enter mobile number";
		if(mobile.length()<10 || mobile.length()>10)
			return "Enter 10 digit mobile number";
		
		
		logger.info("In forget user service: Trying to access forget password using mobilenumber: "+" "+mobile);
		 
		Implementation imp=new Implementation();
		 
		 return imp.getForgetUser(mobile);
		
	}
	
/*4.-----------ok-----------------------FORGET PASSWORD--------------------------------------------------------*/	

	public String getForgetPassword(String username)
	{
		
		if(username.isEmpty())
			return "You must enter username";
		if(username.length()>10)
			return "user name length can't be greater than 10 character";
		
	
		logger.info("In forget password service: trying to access password using username is:"+" "+username);
		
		Implementation imp=new Implementation();
		 return imp.getForgetPassword(username);
	}
	
/*5.---------ok------------------------TEMP PASSWORD--------------------------------------------------------*/	
	
	
	public String getTempPassword(String temppass)
	{
		String user=null;
		String pass=null;
		try
		{
			JSONObject obj=new JSONObject(temppass);
			user=obj.getString("user");
			pass=obj.getString("pass");
			
			
		}
		catch(JSONException e)
		{
			logger.error("In temp password service: error in JSON object");
		}
		
		if(user.isEmpty())
			return "You must enter username";
		if(user.length()>10)
			return "Username not greater than 10 character";
		
		if(pass.isEmpty())
			return "You must enter password";
		if(pass.length()>40)
			return "password not greater than 40 character";
		
		logger.info("In temp password service: you are trying to set temp password for user name: "+" "+user);
		
		Implementation imp=new Implementation();
		return imp.getTempPassword(user,pass);
		
	}
	
	
	
	
	
	

}
