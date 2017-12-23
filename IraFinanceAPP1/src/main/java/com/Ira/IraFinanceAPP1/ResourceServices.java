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
	
	

/*1------------OK----- FOR REGISTER NEW USER----------------------------- */
	
	@GET
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
   
	public String createUser(@QueryParam("pass") String pswd,
							@QueryParam("shop") String shopname,@QueryParam("add") String address,
							@QueryParam("mnum") String mobilenumber,@QueryParam("email") String emailid,
							@QueryParam("gstn") String gstnumber,@QueryParam("accl") String acctlocked,
			 				@QueryParam("sdate") String substartdate,@QueryParam("edate") String subenddate) 
    {
		
    	
    	RegisterUser userdetails=new RegisterUser();
    	
    	userdetails.setPassword(pswd);
    	userdetails.setShopname(shopname);
    	userdetails.setAddress(address);
    	userdetails.setMobilenumber(mobilenumber);
    	userdetails.setEmailid(emailid);
    	userdetails.setGstnumber(gstnumber);
    	userdetails.setAcctlocked(acctlocked);
    	userdetails.setSubstartdate(substartdate);
    	userdetails.setSubenddate(subenddate);
    	
    	BusinessLogic bl=new BusinessLogic();   	
    	
    		return bl.create(userdetails);
    
	}
    
}   
