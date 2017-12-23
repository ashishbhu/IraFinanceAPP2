package com.Ira.IraFinanceAPP1;

public class BusinessLogic {
	
	
	
	/*1------------OK----- FOR REGISTER NEW USER----------------------------- */
	
	public String create(RegisterUser userdetails)
	{
		
		if(userdetails.getPassword()==null)
			return "password can't be null";
		
		if((userdetails.getPassword()).length()>=40)
			return "password length must be less than or equal 40 character";
		
		
		Implementation imp=new Implementation();
		
		return imp.create(userdetails);
		
	}
	
	
	
	

}
