package com.rainbow.crm.login.model;

import com.techtrade.rads.framework.model.abstracts.ModelObject;

public class Login extends ModelObject{

	String username;
	String password;
	String authToken ;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	
	
	
}
