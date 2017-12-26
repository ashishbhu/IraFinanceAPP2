package com.Ira.IraFinanceAPP1;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

@Path("register")
public class ResourceServices {
	
	

/*1------------OK--------------------- FOR REGISTER NEW USER---------------------------------------- */
	
	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createUser(String customerdetail) 
    {
		
    	
    	BusinessLogic bl=new BusinessLogic();   	
    	
    		return bl.create(customerdetail);
    
	}
	
	
/*2---------------ok---------------FOR LOGIN USER--------------------------------------------------------*/	
	
	@POST
	@Path("login")
	@Consumes(MediaType.APPLICATION_JSON)
	public String loginUser(String logindetail)
	{
		
		BusinessLogic bl=new BusinessLogic();  
		return bl.getLoginUser(logindetail);
		
		
	}
	
	
/*3.------------ok---------------------FORGET USER NAME-------------------------------------------------------*/
	
	 
	   @GET
	   @Path("forgetuser")
	   @Produces(MediaType.APPLICATION_JSON)
	   @Consumes(MediaType.APPLICATION_JSON)
	   public String forgetUser(@QueryParam("mobile") String mobile)
	   {
		   BusinessLogic bl=new BusinessLogic();  
		   return bl.getForgetUser(mobile);
	   }
	
/*4.-----------ok-----------------------FORGET PASSWORD--------------------------------------------------------*/	
		
	   @GET
	   @Path("forgetpassword")
	   @Consumes(MediaType.APPLICATION_JSON)
	   @Produces(MediaType.APPLICATION_JSON)
	   public String forgetPassword(@QueryParam("user") String username)
	   {
		   BusinessLogic bl=new BusinessLogic();
		   return bl.getForgetPassword(username);
	
	   }
	   
	   
	   
/*5.---------ok------------------------TEMP PASSWORD--------------------------------------------------------*/
	   
	   @POST
	   @Path("temppass")
	   @Consumes(MediaType.APPLICATION_JSON)
	   public String tempPassword(String temppass)
	   {
		   BusinessLogic bl=new BusinessLogic();
		   return bl.getTempPassword(temppass);
	   }	   
	   
	   
    
}   
