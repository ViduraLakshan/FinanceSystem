package com.financeSystem.registration;


public class User{
  
    private int id;
    private String userName;
    private String email;
   
    
	public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getemail() {
        return email;
    }
    public void setemail(String email) {
        this.email = email;
    }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}