package com.rainbow.crm.dashboard.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.rainbow.crm.database.ConnectionCreater;
import com.rainbow.crm.logger.Logwriter;

public class DashBoardSQLs {

	public static Double getPeriodTotalSale(String associate,  Date startDate, Date endDate )
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = ConnectionCreater.getConnection();
			String sql =  " SELECT SUM(SALES_LINES.LINE_TOTAL) FROM SALES , SALES_LINES WHERE SALES.ID= SALES_LINES.SALES_ID AND  " + 
			 " SALES.SALES_DATE > ? AND  SALES.SALES_DATE <= ?  AND " + 
			" SALES_LINES.USER_ID= ? AND  SALES_LINES.IS_VOIDED = FALSE AND SALES.IS_VOIDED= FALSE  ";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setString(3, associate);
			rs = statement.executeQuery();
			while (rs.next()) {
				Double amount = rs.getDouble(1);
				return amount; 
			}
			
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return 0d ;
	}
	
	public static Double getSaleAllMade(int divison,  Date startDate, Date endDate )
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = ConnectionCreater.getConnection();
			String sql =  " SELECT SUM(SALES_LINES.LINE_TOTAL) FROM SALES , SALES_LINES WHERE SALES.ID= SALES_LINES.SALES_ID AND  " + 
			 " SALES.SALES_DATE > ? AND  SALES.SALES_DATE <= ?  AND SALES.DIVISION_ID = ?  " + 
			"  AND  SALES_LINES.IS_VOIDED = FALSE AND SALES.IS_VOIDED= FALSE  ";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, divison);
			rs = statement.executeQuery();
			while (rs.next()) {
				Double amount = rs.getDouble(1);
				return amount; 
			}
			
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return 0d ;
	}
	
	public static Map<String, Double> getSaleMadeByAssociate(int divison,  Date startDate, Date endDate )
	{
		Map<String, Double> ans = new HashMap<String, Double>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = ConnectionCreater.getConnection();
			String sql =  " SELECT SUM(SALES_LINES.LINE_TOTAL),USER_ID FROM SALES , SALES_LINES WHERE SALES.ID= SALES_LINES.SALES_ID AND  " + 
			 " SALES.SALES_DATE > ? AND  SALES.SALES_DATE <= ?  AND SALES.DIVISION_ID = ?  " + 
			"  AND  SALES_LINES.IS_VOIDED = FALSE AND SALES.IS_VOIDED= FALSE  GROUP BY USER_ID";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, divison);
			rs = statement.executeQuery();
			while (rs.next()) {
				Double amount = rs.getDouble(1);
				String user = rs.getString(2);
				ans.put(user, amount);
			}
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return ans;
	}
	
	public static Map<String, Double> getSaleMadeByTerritory(int divison,  Date startDate, Date endDate )
	{
		Map<String, Double> ans = new HashMap<String, Double>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = ConnectionCreater.getConnection();
			String sql =  " SELECT SUM(SALES.NET_AMOUNT),TERRITORIES.TERRITORY FROM SALES , TERRITORIES WHERE TERRITORIES.ID= SALES.TERRITORY_ID AND  " + 
			 " SALES.SALES_DATE > ? AND  SALES.SALES_DATE <= ?  AND SALES.DIVISION_ID = ?  " + 
			"  AND  SALES.IS_VOIDED= FALSE  GROUP BY TERRITORIES.TERRITORY";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, divison);
			rs = statement.executeQuery();
			while (rs.next()) {
				Double amount = rs.getDouble(1);
				String user = rs.getString(2);
				ans.put(user, amount);
			}
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return ans;
	}
	
	public static Map<String, Double> getItemWiseSale(int division, String associate,  Date startDate, Date endDate )
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Map<String, Double> ans = new HashMap<String, Double> ();
		try {
			connection = ConnectionCreater.getConnection();
			String sql =  " SELECT SUM(SALES_LINES.LINE_TOTAL),ITEMS.ITEM_NAME FROM SALES , SALES_LINES  ,  SKUS, ITEMS WHERE SALES.ID= SALES_LINES.SALES_ID AND  " + 
			 " SALES_LINES.SKU_ID = SKUS.ID AND SKUS.ITEM_ID = ITEMS.ID AND  SALES.SALES_DATE >= ? AND  SALES.SALES_DATE <= ? AND SALES.DIVISION_ID = ? AND " + 
			" SALES_LINES.USER_ID= ? AND  SALES_LINES.IS_VOIDED = FALSE AND SALES.IS_VOIDED= FALSE GROUP BY ITEMS.ITEM_NAME ";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, division);
			statement.setString(4, associate);
			rs = statement.executeQuery();
			while (rs.next()) {
				String itemName = rs.getString(2);
				Double amount = rs.getDouble(1);
				ans.put(itemName, amount);
			}
			
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return ans ;
		
	}
	
	
	public static Map<String, Double> getProductWiseSale(int division,  Date startDate, Date endDate )
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Map<String, Double> ans = new HashMap<String, Double> ();
		try {
			connection = ConnectionCreater.getConnection();
			String sql =  " SELECT SUM(SALES_LINES.LINE_TOTAL),PRODUCTS.PRODUCT_NAME FROM SALES , SALES_LINES  ,  SKUS, ITEMS,PRODUCTS WHERE SALES.ID= SALES_LINES.SALES_ID AND  " + 
			 " SALES_LINES.SKU_ID = SKUS.ID AND SKUS.ITEM_ID = ITEMS.ID AND ITEMS.PRODUCT_ID=PRODUCTS.ID AND SALES.SALES_DATE >= ? AND  SALES.SALES_DATE <= ? AND SALES.DIVISION_ID = ? AND " + 
			" SALES_LINES.IS_VOIDED = FALSE AND SALES.IS_VOIDED= FALSE GROUP BY PRODUCTS.PRODUCT_NAME ";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, division);
			rs = statement.executeQuery();
			while (rs.next()) {
				String productName = rs.getString(2);
				Double amount = rs.getDouble(1);
				ans.put(productName, amount);
			}
			
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return ans ;
		
	}
	
	public static Map<String, Double> getCategoryWiseSale(int division,  Date startDate, Date endDate )
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Map<String, Double> ans = new HashMap<String, Double> ();
		try {
			connection = ConnectionCreater.getConnection();
			String sql =  " SELECT SUM(SALES_LINES.LINE_TOTAL),CATEGORIES.CATEGORY_NAME FROM SALES , SALES_LINES  ,  SKUS, ITEMS,PRODUCTS, CATEGORIES WHERE SALES.ID= SALES_LINES.SALES_ID AND  " + 
			 " SALES_LINES.SKU_ID = SKUS.ID AND SKUS.ITEM_ID = ITEMS.ID AND ITEMS.PRODUCT_ID=PRODUCTS.ID AND PRODUCTS.CATEGORY_ID = CATEGORIES.ID AND SALES.SALES_DATE >= ? AND  SALES.SALES_DATE <= ? AND SALES.DIVISION_ID = ? AND " + 
			" SALES_LINES.IS_VOIDED = FALSE AND SALES.IS_VOIDED= FALSE GROUP BY CATEGORIES.CATEGORY_NAME ";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, division);
			rs = statement.executeQuery();
			while (rs.next()) {
				String productName = rs.getString(2);
				Double amount = rs.getDouble(1);
				ans.put(productName, amount);
			}
			
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return ans ;
		
	}
	
	public static Map<String, Double> getBrandWiseSale(int division,  Date startDate, Date endDate )
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Map<String, Double> ans = new HashMap<String, Double> ();
		try {
			connection = ConnectionCreater.getConnection();
			String sql =  " SELECT SUM(SALES_LINES.LINE_TOTAL),BRANDS.BRAND_NAME FROM SALES , SALES_LINES  ,  SKUS, ITEMS,BRANDS WHERE SALES.ID= SALES_LINES.SALES_ID AND  " + 
			 " SALES_LINES.SKU_ID = SKUS.ID AND SKUS.ITEM_ID = ITEMS.ID AND ITEMS.BRAND_ID=BRANDS.ID AND SALES.SALES_DATE >= ? AND  SALES.SALES_DATE <= ? AND SALES.DIVISION_ID = ? AND " + 
			" SALES_LINES.IS_VOIDED = FALSE AND SALES.IS_VOIDED= FALSE GROUP BY BRANDS.BRAND_NAME ";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, division);
			rs = statement.executeQuery();
			while (rs.next()) {
				String productName = rs.getString(2);
				Double amount = rs.getDouble(1);
				ans.put(productName, amount);
			}
			
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return ans ;
		
	}
	
	public static Map<String, Double> getItemWiseSale(int division,  Date startDate, Date endDate )
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Map<String, Double> ans = new HashMap<String, Double> ();
		try {
			connection = ConnectionCreater.getConnection();
			String sql =  " SELECT SUM(SALES_LINES.LINE_TOTAL),ITEMS.ITEM_NAME FROM SALES , SALES_LINES  ,  SKUS, ITEMS WHERE SALES.ID= SALES_LINES.SALES_ID AND  " + 
			 " SALES_LINES.SKU_ID = SKUS.ID AND SKUS.ITEM_ID = ITEMS.ID AND  SALES.SALES_DATE >= ? AND  SALES.SALES_DATE <= ? AND SALES.DIVISION_ID = ? AND " + 
			" SALES_LINES.IS_VOIDED = FALSE AND SALES.IS_VOIDED= FALSE GROUP BY ITEMS.ITEM_NAME ";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, division);
			rs = statement.executeQuery();
			while (rs.next()) {
				String productName = rs.getString(2);
				Double amount = rs.getDouble(1);
				ans.put(productName, amount);
			}
			
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return ans ;
		
	}

}
