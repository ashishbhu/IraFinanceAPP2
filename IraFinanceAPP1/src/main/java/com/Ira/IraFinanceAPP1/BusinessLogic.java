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
			return "You must enter password";
		if((userdetails.getPassword()).length()>40)
			return "password length can't be greater than 40 character";
		
		if(userdetails.getShopname().isEmpty())
			return "You must enter shopname";
		if(userdetails.getShopname().length()>100)
			return "Shopname length can't be greater than 100 character";
		
		if(userdetails.getAddress().isEmpty())
			return "You must enter address";
		if(userdetails.getAddress().length()>200)
			return "Address length can't be greater than 200 character";
		
		if(userdetails.getMobilenumber().isEmpty())
			return "You must Enter Mobile Number";
		if(userdetails.getMobilenumber().length()<10 || userdetails.getMobilenumber().length()>10)
			return "mobile number length must be 10 digits";
		
		if(userdetails.getEmailid().isEmpty())
			return "You must enter EmailId";
		if(userdetails.getEmailid().length()>40)
			return "EmailId length can't be greater than 40 character";
		
		if(userdetails.getGstnumber().isEmpty())
			return "You must enter GST Number";
		if(userdetails.getGstnumber().length()>15)
			return "GST Number can't be more than 15 character";
		
		if(userdetails.getAcctlocked().isEmpty())
			return "You must Enter AcctLocked";
		
		if(userdetails.getSubstartdate().isEmpty())
			return "You must Enter Subscription Start Date";
		
		if(userdetails.getSubenddate().isEmpty())
			return "You must Enter Subscription End Date";
		
		
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
			return "You must enter username";
		if(uname.length()>10)
			return "User name can't be greater than 10 character";
		
		 if(pass.isEmpty())
			 return "You must enter password";
		 if(pass.length()>40)
			return "Password can't be greater than 40 character"; 
		 
		 
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
			return "Username can't be greater than 10 character";
		
		if(pass.isEmpty())
			return "You must enter password";
		if(pass.length()>40)
			return "password can't be greater than 40 character";
		
		logger.info("In temp password service: you are trying to set temp password for user name: "+" "+user);
		
		Implementation imp=new Implementation();
		return imp.getTempPassword(user,pass);
		
	}
	
	
/*6.----------ok-----------------------RESET PASSWORD----------------------------------------------------*/	
	
	public String resetPassword(String resetpass)
	{
		
	    String user=null;
		String pass=null;
		
		try
		{
			JSONObject obj =new JSONObject(resetpass);
			user=obj.getString("user");
			pass=obj.getString("pass");
		}
		catch(JSONException e)
		{
			
			logger.error("In reset Password Service: error in recieving JSON object");
		}
		
		
		if(user.isEmpty())
			return "You must enter username";
		if(user.length()>10)
			return "Username can't be greater than 10 character";
		
		if(pass.isEmpty())
			return "You must enter password";
		if(pass.length()>40)
			return "password can't be greater than 40 character";
		
		logger.info("In reset password service: You are trying to reset password with username:"+" "+user);
		
		
		Implementation imp=new Implementation();
		return imp.getResetPassword(user,pass);
	}

	
/*7.----------ok---------------------SUB USER REGISTRATION--------------------------------------------*/	
	

	public String createSubUser(String subuser)
	{
		
		RegisterSubUser subuserdetail=new RegisterSubUser();
			
			try
			{
				JSONObject obj=new JSONObject(subuser);
				
				subuserdetail.setMainuser(obj.getString("user"));
				subuserdetail.setSubuser(obj.getString("cuname"));
				subuserdetail.setPass(obj.getString("cpass"));
				subuserdetail.setAccess(obj.getInt("access"));
				subuserdetail.setSubstsrtdate(obj.getString("sdate"));
				subuserdetail.setSubenddate(obj.getString("edate"));
			}
			catch(JSONException e)
			{
				logger.error("In sub user registration service: error in JSON object");
			}
			catch(Exception e)
			{
				logger.error("In sub user registration service: error in JSON object at the time of receiving sub user details");
			}
		    
			if(subuserdetail.getMainuser().isEmpty())
				return "You must enter parent user name";
			if(subuserdetail.getMainuser().length()>10)
				return "Parent user name cant't be greater than 10 character";
			
			if(subuserdetail.getSubuser().isEmpty())
				return "You must enter sub user name";
			if(subuserdetail.getSubuser().length()>16)
				return "sub user name can't be greater than 16 character";
			
			if(subuserdetail.getPass().isEmpty())
				return "You must enter password";
			if(subuserdetail.getPass().length()>40)
				return "Password can't be greater than 40 character";
			
			if(Integer.toString(subuserdetail.getAccess()).isEmpty())
				return "You must enter access";
			if(Integer.toString(subuserdetail.getAccess()).length()>5)
				return "access length can't be greater than 5 digits";
			
			if(subuserdetail.getSubstsrtdate().isEmpty())
				return "You must enter start date";
			
			if(subuserdetail.getSubenddate().isEmpty())
				return "You must enter end date";
			
			
			
			logger.info("In sub user registration service:  You are trying to create sub user with these details: "+"main user: "+" "+
			subuserdetail.getMainuser()+" "+"sub user id: "+" "+subuserdetail.getSubuser()+" "+"access: "+
					subuserdetail.getAccess()+" "+"start date: "+subuserdetail.getSubstsrtdate()+" "+
			"end date: "+subuserdetail.getSubenddate());
		
		
			Implementation imp=new Implementation();
			return imp.createSubUser(subuserdetail);
	}
	
	
	
/*8.-------------------------OK----------GET ALL SUB USER NAME BY MAIN USER NAME------------------------------*/
	
	
	public String getAllSubUser(String username)
	{
		
		if(username.isEmpty())
			return "You must enter parent user name";
		if(username.length()>10)
			return "user name can't be greater than 10 character";
		
		
		
		logger.info("In all sub user name service: You are trying to access all sub user name using parent user name :"+username);
		
		
		Implementation imp=new Implementation();
		return imp.getAllSubUser(username);
		
	}

	
/*9.-------ok----------------------------GET SUB USER ACCESS by SUB USER NAME--------------------------------------------*/	
	
	public String getSubUserAccess(String username)
	{
		
		if(username.isEmpty())
			return "You must enter sub user name";
		if(username.length()>16)
			return "sub user name can't be greater than 16 character";
		
		logger.info("In get sub user access service: you are trying to see sub user access by sub user name "+username);
		
		
		Implementation imp=new Implementation();
		return imp.getSubUserAccess(username);
		
	}

	
/*10------------------OK-----------FOR EDIT SUB USER ACCESS----------------------------------------------------*/	
	
	public String editSubUserAccess(String editsubuser)
	{
		
		String subuser=null;
		String pass=null;
		int access=0;
			
		    try
		    {
			JSONObject obj= new JSONObject(editsubuser);/*---parsing json object--*/
			
			subuser=obj.getString("user");
			pass=obj.getString("pass");
			access=obj.getInt("access");
		    }
		    catch(JSONException e)
		    {
		    	logger.error("In edit sub user access service: error in json object at the time of parsing json object");
		    }
			catch(Exception e)
		    {
				logger.error("In edit sub user access service: error at the time of parsing json object");
		    }
		
		
		   if(subuser.isEmpty())
			   return "You must enter sub user name";
		   if(subuser.length()>16)
			   return "sub user name can't be greater than 16 character";
		   
		   if(pass.isEmpty())
			   return "You must enter password";
		   if(pass.length()>40)
			   return "sub user password can't be greater than 40 character";
		   
		   if(Integer.toString(access).isEmpty())
			   return "You must enter access";
		   if(Integer.toString(access).length()>5)
			   return "access length can't be greater than 5 digit";
		   
		   logger.info("In edit sub user access service :You are trying to edit sub user access with details: "+subuser+", "
				   +"access "+access);
		    
		
		   Implementation imp=new Implementation();
			return imp.editSubUserAccess(subuser,pass,access);
	}

}















