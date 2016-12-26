package com.rainbow.crm.config.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.config.model.ConfigLine;
import com.rainbow.crm.config.model.ConfigSet;
import com.rainbow.crm.database.ConnectionCreater;
import com.rainbow.crm.logger.Logwriter;

public class ConfigSQL {

	public static ConfigSet getConfigSet( int company){
		ConfigSet set = new ConfigSet();
		set.setCompany(company);
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql =   "Select * from CONFIG_GROUPS " ;
			statement = connection.prepareStatement(sql);
			rs = statement.executeQuery() ;
			while (rs.next()) {
				String groupName = rs.getString(1);
				List<ConfigLine> configLines =  getConfigLinesforGroup(company, groupName);
				set.getConfigMap().put(groupName, configLines);
			}
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return set;
	}
	
	private static List<ConfigLine> getConfigLinesforGroup(int company,String groupName) {
		List<ConfigLine> list = new ArrayList<ConfigLine>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql =   "Select BC.CODE,BC.CONFIG_DESCRIPTION,BC.CONFIG_GROUP,BC.VALUE_TYPE,BC.VALUE_GENERATOR,BC.DEFAULT_VALUE,CC.VALUE " + 
			"  from BASE_CONFIGURATION BC LEFT JOIN CC ON  BC.CODE= CC.CODE AND CC.COMPANY_ID =? AND BC.CONFIG_GROUP= ? " ;
			statement = connection.prepareStatement(sql);
			statement.setInt(1, company);
			statement.setString(2, groupName);
			rs = statement.executeQuery() ;
			while (rs.next()) {
				ConfigLine configLine = new ConfigLine();
				configLine.setCompany(company);
				configLine.setConfigCode(rs.getString(1));
				configLine.setDescription(rs.getString(2));
				configLine.setGroup(rs.getString(3));
				configLine.setValueType(new FiniteValue(rs.getString(4)));
				configLine.setValueGenerator(rs.getString(5));
				configLine.setDefaultValue(rs.getString(6));
				configLine.setValue(rs.getString(7));
				list.add(configLine);
			}
			
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return list;
	}
}
