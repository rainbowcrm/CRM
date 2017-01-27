package com.rainbow.framework.setup.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.config.model.ConfigLine;
import com.rainbow.crm.database.ConnectionCreater;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.framework.setup.model.Metadata;

public class MetadataSQL {

	public static List<Metadata> getMetadata() {
		List<Metadata> metaDataList = new ArrayList<Metadata>(); 
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = ConnectionCreater.getConnection();
			String sql = "Select * from METADATA ";
			statement = connection.prepareStatement(sql);
			rs = statement.executeQuery();
			while (rs.next()) {
				String  entity = rs.getString("ENTITY");
				String className =  rs.getString("CLASS_NAME");
				FiniteValue metaDataType =new FiniteValue (rs.getString("METADATA_TYPE"));
				boolean isDivSpecific = rs.getBoolean("IS_DIVISION_SPEC");
				Metadata metaData  = new Metadata(entity,className,metaDataType,isDivSpecific);
				metaDataList.add(metaData);
			}
		} catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return metaDataList;
	}
}
