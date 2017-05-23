package com.rainbow.crm.login.model;

import com.techtrade.rads.framework.model.abstracts.ModelObject;

public class Login extends ModelObject{

	String username;
	String password;
	String authToken ;
	boolean isMobileLogin;
	String mobileNotificationId;
	boolean isLoggedOut;
	
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
	public boolean isMobileLogin() {
		return isMobileLogin;
	}
	public void setMobileLogin(boolean isMobileLogin) {
		this.isMobileLogin = isMobileLogin;
	}
	public String getMobileNotificationId() {
		return mobileNotificationId;
	}
	public void setMobileNotificationId(String mobileNotificationId) {
		this.mobileNotificationId = mobileNotificationId;
	}
	public boolean isLoggedOut() {
		return isLoggedOut;
	}
	public void setLoggedOut(boolean isLoggedOut) {
		this.isLoggedOut = isLoggedOut;
	}
	
	
	
	
	
	
}
