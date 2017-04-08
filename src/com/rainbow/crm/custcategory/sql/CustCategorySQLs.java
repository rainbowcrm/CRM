package com.rainbow.crm.custcategory.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.rainbow.crm.database.ConnectionCreater;
import com.rainbow.crm.logger.Logwriter;

public class CustCategorySQLs {
	
	public static Map<String,String> getAllCriteriaFields (String entity) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		Map<String,String> ans = new LinkedHashMap<String, String>();
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql =   " SELECT * FROM CUST_CATEGORY_COMPONENTS WHERE ENTITY = ? ORDER BY JOIN_HQL_CLAUSE  " ;
			statement = connection.prepareStatement(sql);
			statement.setString(1, entity);
			rs = statement.executeQuery() ;
			while (rs.next()) {
				String displayField = rs.getString("KEY_FIELD");
				String hqlField = rs.getString("HQL_KEY_FIELD");
				ans.put(hqlField, displayField);
			}
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return ans;
	}

}
