package com.rainbow.crm.common;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Locale;
import java.util.Map;

import com.rainbow.crm.user.model.User;
import com.techtrade.rads.framework.context.IRadsContext;

public class CRMContext implements IRadsContext,Serializable{
	String user;
	boolean authenticated ;
	String authenticationToken;
	int loggedinCompany;
	Timestamp logginTime;
	Timestamp logoffTime;
	String loggedinCompanyCode;
	Locale locale = Locale.US;
	User loggedInUser; 
	
	boolean fetchDeletedToo;
	boolean backgroundProcess; 
	boolean guestLogin;

	
	@Override
	public String getUser() {
		return user;
	}

	@Override
	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public Map getProperties() {
		return null;
	}

	@Override
	public void setProperties(Map properties) {
		
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuth) {
		authenticated = isAuth;
	}

	public String getAuthenticationToken() {
		return authenticationToken;
	}

	public void setAuthenticationToken(String authenticationToken) {
		this.authenticationToken = authenticationToken;
	}

	public int getLoggedinCompany() {
		return loggedinCompany;
	}

	public void setLoggedinCompany(int loggedinCompany) {
		this.loggedinCompany = loggedinCompany;
	}

	public Timestamp getLogginTime() {
		return logginTime;
	}

	public void setLogginTime(Timestamp logginTime) {
		this.logginTime = logginTime;
	}

	public Timestamp getLogoffTime() {
		return logoffTime;
	}

	public void setLogoffTime(Timestamp logoffTime) {
		this.logoffTime = logoffTime;
	}

	public String getLoggedinCompanyCode() {
		return loggedinCompanyCode;
	}

	public void setLoggedinCompanyCode(String loggedinCompanyCode) {
		this.loggedinCompanyCode = loggedinCompanyCode;
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

	@Override
	public void setLocale(Locale locale) {
		this.locale=locale;
		
	}

	public User getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(User loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public boolean isFetchDeletedToo() {
		return fetchDeletedToo;
	}

	public void setFetchDeletedToo(boolean fetchDeletedToo) {
		this.fetchDeletedToo = fetchDeletedToo;
	}

	public boolean isBackgroundProcess() {
		return backgroundProcess;
	}

	public void setBackgroundProcess(boolean backgroundProcess) {
		this.backgroundProcess = backgroundProcess;
	}

	public boolean isGuestLogin() {
		return guestLogin;
	}

	public void setGuestLogin(boolean guestLogin) {
		this.guestLogin = guestLogin;
	}
	
	
	
	
	
	
	

}
