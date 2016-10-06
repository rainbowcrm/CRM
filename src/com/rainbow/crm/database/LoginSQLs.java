package com.rainbow.crm.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.logger.Logwriter;

public class LoginSQLs {

	
	public static void deleteSessionforOtherUsers (String session,String user) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql = " DELETE FROM LOGIN_RECORDS WHERE SESSION_ID = ? AND USER_ID <> ?" ;
			statement = connection.prepareStatement(sql) ;
			statement.setString(1,session);
			statement.setString(2,user);
			statement.executeUpdate() ;
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
	}
	
	public static void registerFirstLogin(String user, String token, String session){
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		try {
			deleteSessionforOtherUsers(session,user);
			connection  = ConnectionCreater.getConnection() ;
			String sql = " INSERT INTO LOGIN_RECORDS (TOKEN_ID,USER_ID,SESSION_ID,LOGGED_IN_TIME) VALUES (?,?,?,?)" ;
			statement = connection.prepareStatement(sql) ;
			statement.setString(1,session);
			statement.setString(2,user);
			statement.setString(3,session);
			statement.setTimestamp(4,new Timestamp(new java.util.Date().getTime()));
			statement.executeUpdate() ;
			Logwriter.INSTANCE.debug("First Login Reg for user" + user);
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
	}
	

	
	
	public static void updateLogin(String user, String token, String session){
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		try {
			connection  = ConnectionCreater.getConnection() ;
			deleteSessionforOtherUsers(session,user);
			String sql = " UPDATE LOGIN_RECORDS SET TOKEN_ID = ? ,  SESSION_ID = ? , LOGGED_IN_TIME = ? , "  + 
			" LOGGED_OFF_TIME = null, EXPIRED_TIME = null  WHERE USER_ID = ? " ;
			statement = connection.prepareStatement(sql) ;
			statement.setString(1,session);
			statement.setString(2,session);
			statement.setTimestamp(3,new Timestamp(new java.util.Date().getTime()));
			statement.setString(4,user);
			statement.executeUpdate() ;
			Logwriter.INSTANCE.debug("Subsequent Login Reg for user" + user);
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
	}
	
	
	public static void registerLogin (CRMContext context) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql =   "SELECT * FROM LOGIN_RECORDS where USER_ID = ?  " ;
			statement = connection.prepareStatement(sql);
			statement.setString(1, context.getUser());
			rs = statement.executeQuery() ;
			if (rs.next()) {
				updateLogin(context.getUser(), context.getAuthenticationToken(), context.getAuthenticationToken());
			} else {
				registerFirstLogin(context.getUser(), context.getAuthenticationToken(), context.getAuthenticationToken());
			}
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		
	}
	
	public static CRMContext loggedInUser(String sessionId) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql =   "SELECT TOKEN_ID,LOGGED_IN_TIME,USERS.USER_ID,COMPANIES.ID,COMPANIES.COMPANY_CODE FROM LOGIN_RECORDS,USERS, COMPANIES  where LOGIN_RECORDS.USER_ID = USERS.USER_ID  " +
			" AND USERS.COMPANY_ID = COMPANIES.ID  AND " +  
			" SESSION_ID = ? AND  (EXPIRED_TIME is null && LOGGED_OFF_TIME is null) " ;
			statement = connection.prepareStatement(sql);
			statement.setString(1, sessionId);
			rs = statement.executeQuery() ;
			if (rs.next()) {
				CRMContext context = new CRMContext();
				context.setAuthenticated(true);
				context.setAuthenticationToken(rs.getString("TOKEN_ID"));
				context.setLogginTime(rs.getTimestamp("LOGGED_IN_TIME"));
				context.setUser(rs.getString("USER_ID"));
				context.setLoggedinCompany(rs.getInt("COMPANIES.ID"));
				context.setLoggedinCompanyCode(rs.getString("COMPANIES.COMPANY_CODE"));
				return context;
			}else
				return null; 
			
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return null;
	}
	
}
