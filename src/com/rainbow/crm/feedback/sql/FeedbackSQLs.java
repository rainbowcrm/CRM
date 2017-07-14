package com.rainbow.crm.feedback.sql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.rainbow.crm.database.ConnectionCreater;
import com.rainbow.crm.logger.Logwriter;

public class FeedbackSQLs {

	
	public static Map<String, Integer> getFeedBackReason(  Date startDate, Date endDate, int company, int division, 
			int minRating, int MaxRating, String FeedBackOn )
	{
		Map<String, Integer> ans = new HashMap<String, Integer>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			String divisionPart = (division==-1)?"":" AND FEEDBACKS.DIVISION_ID = ? "; 
			connection = ConnectionCreater.getConnection();
			String sql =  " SELECT COUNT(FEEDBACK_LINES.ID),REASON_CODES.REASON FROM FEEDBACKS,FEEDBACK_LINES,REASON_CODES WHERE   " +
			" FEEDBACKS.ID= FEEDBACK_LINES.FEEDBACK_ID AND FEEDBACK_LINES.REASON_CODE_ID = REASON_CODES.ID AND   " +
			 " FEEDBACKS.FDBACK_DATE >= ? AND  FEEDBACKS.FDBACK_DATE <= ?  AND FEEDBACKS.COMPANY_ID =?   " +
			 " AND FEEDBACK_LINES.RATING >= ? AND FEEDBACK_LINES.RATING <= ?  AND FEEDBACK_LINES.FEEDBACK_ON= ?  "  +
			 divisionPart + 
			"  AND FEEDBACKS.IS_DELETED = FALSE AND FEEDBACK_LINES.IS_DELETED=FALSE  GROUP BY REASON_CODES.REASON ";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, company);
			statement.setInt(4, minRating);
			statement.setInt(5, MaxRating);
			statement.setString(6, FeedBackOn);
			if(division != -1 ) 
				statement.setInt(7, division);
			rs = statement.executeQuery();
			while (rs.next()) {
				Integer amount = rs.getInt(1);
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
}
