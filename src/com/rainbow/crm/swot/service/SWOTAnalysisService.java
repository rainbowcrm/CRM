package com.rainbow.crm.swot.service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.swot.sqls.SWOTAnalysisSQLs;
import com.techtrade.rads.framework.model.graphdata.BarChartData;
import com.techtrade.rads.framework.model.graphdata.BarData;
import com.techtrade.rads.framework.model.graphdata.BarChartData.Range;
import com.techtrade.rads.framework.utils.Utils;

public class SWOTAnalysisService implements ISWOTAnalysisService {

	@Override
	public BarChartData getStrengths(Division division, Date fromDate,
			Date toDate, CRMContext context) {
	
		AtomicInteger maxValue = new AtomicInteger(0);
		Map<String,Integer> strengthLeads =SWOTAnalysisSQLs.getSalesLeadReasonSplitups(division.getId(), context.getLoggedinCompany(),
				Utils.getSQLDate(fromDate), Utils.getSQLDate(toDate),CRMConstants.FACTOR_TYPE.INTERNAL, 
				CRMConstants.BUSINESS_ORIENTATION.POSITIVE);
		
		Map<String,Integer> strengthfeedBackss =SWOTAnalysisSQLs.getFeedBackReasonSplitups(division.getId(), context.getLoggedinCompany(),
				Utils.getSQLDate(fromDate), Utils.getSQLDate(toDate),CRMConstants.FACTOR_TYPE.INTERNAL, 
				CRMConstants.BUSINESS_ORIENTATION.POSITIVE);
		strengthLeads.putAll(strengthfeedBackss);
		
		BarChartData barChartData = new BarChartData();
		barChartData.setTitle("Strengths");
		barChartData.setSubTitle(" ");
		
		strengthLeads.entrySet().forEach(entry  -> { ;
			BarData tagetBarData = new BarData();
			BarChartData.Division targetDivis = barChartData.new Division();
			tagetBarData.setText(entry.getKey());
			tagetBarData.setLegend(entry.getKey());
			tagetBarData.setValue(entry.getValue());
			tagetBarData.setColor("Green");
			targetDivis.addBarData(tagetBarData);
			barChartData.addDivision(targetDivis);
			if(entry.getValue() > maxValue.get()) {
				maxValue.set(entry.getValue());
			}
		}  );
		
		BarChartData.Range range =  barChartData.new  Range();
		range.setyMax(maxValue.get());
		range.setyMin(0);
		range.setxMin(0);
		range.setxMax(100);
		barChartData.setRange(range);
		
		return barChartData;
	}

	@Override
	public BarChartData getWeaknesses(Division division, Date fromDate,
			Date toDate, CRMContext context) {
		AtomicInteger maxValue = new AtomicInteger(0);
		Map<String,Integer> strengthLeads =SWOTAnalysisSQLs.getSalesLeadReasonSplitups(division.getId(), context.getLoggedinCompany(),
				Utils.getSQLDate(fromDate), Utils.getSQLDate(toDate),CRMConstants.FACTOR_TYPE.INTERNAL, 
				CRMConstants.BUSINESS_ORIENTATION.NEGATIVE);
		
		Map<String,Integer> strengthfeedBackss =SWOTAnalysisSQLs.getFeedBackReasonSplitups(division.getId(), context.getLoggedinCompany(),
				Utils.getSQLDate(fromDate), Utils.getSQLDate(toDate),CRMConstants.FACTOR_TYPE.INTERNAL, 
				CRMConstants.BUSINESS_ORIENTATION.NEGATIVE);
		strengthLeads.putAll(strengthfeedBackss);
		
		BarChartData barChartData = new BarChartData();
		barChartData.setTitle("Weakness");
		barChartData.setSubTitle(" ");
		
		strengthLeads.entrySet().forEach(entry  -> { ;
			BarData tagetBarData = new BarData();
			BarChartData.Division targetDivis = barChartData.new Division();
			tagetBarData.setText(entry.getKey());
			tagetBarData.setLegend(entry.getKey());
			tagetBarData.setValue(entry.getValue());
			tagetBarData.setColor("Black");
			targetDivis.addBarData(tagetBarData);
			barChartData.addDivision(targetDivis);
			if(entry.getValue() > maxValue.get()) {
				maxValue.set(entry.getValue());
			}
		}  );
		
		BarChartData.Range range =  barChartData.new  Range();
		range.setyMax(maxValue.get());
		range.setyMin(0);
		range.setxMin(0);
		range.setxMax(100);
		barChartData.setRange(range);
		
		return barChartData;
	}

	@Override
	public BarChartData getOpportunities(Division division, Date fromDate,
			Date toDate, CRMContext context) {
		AtomicInteger maxValue = new AtomicInteger(0);
		Map<String,Integer> strengthLeads =SWOTAnalysisSQLs.getSalesLeadReasonSplitups(division.getId(), context.getLoggedinCompany(),
				Utils.getSQLDate(fromDate), Utils.getSQLDate(toDate),CRMConstants.FACTOR_TYPE.EXTERNAL, 
				CRMConstants.BUSINESS_ORIENTATION.POSITIVE);
		
		Map<String,Integer> strengthfeedBackss =SWOTAnalysisSQLs.getFeedBackReasonSplitups(division.getId(), context.getLoggedinCompany(),
				Utils.getSQLDate(fromDate), Utils.getSQLDate(toDate),CRMConstants.FACTOR_TYPE.EXTERNAL, 
				CRMConstants.BUSINESS_ORIENTATION.POSITIVE);
		strengthLeads.putAll(strengthfeedBackss);
		
		BarChartData barChartData = new BarChartData();
		barChartData.setTitle("Opportunities");
		barChartData.setSubTitle(" ");
		
		strengthLeads.entrySet().forEach(entry  -> { ;
			BarData tagetBarData = new BarData();
			BarChartData.Division targetDivis = barChartData.new Division();
			tagetBarData.setText(entry.getKey());
			tagetBarData.setLegend(entry.getKey());
			tagetBarData.setValue(entry.getValue());
			tagetBarData.setColor("Blue");
			targetDivis.addBarData(tagetBarData);
			barChartData.addDivision(targetDivis);
			if(entry.getValue() > maxValue.get()) {
				maxValue.set(entry.getValue());
			}
		}  );
		
		BarChartData.Range range =  barChartData.new  Range();
		range.setyMax(maxValue.get());
		range.setyMin(0);
		range.setxMin(0);
		range.setxMax(100);
		barChartData.setRange(range);
		
		return barChartData;
	}

	@Override
	public BarChartData getThreats(Division division, Date fromDate,
			Date toDate, CRMContext context) {
		AtomicInteger maxValue = new AtomicInteger(0);
		Map<String,Integer> strengthLeads =SWOTAnalysisSQLs.getSalesLeadReasonSplitups(division.getId(), context.getLoggedinCompany(),
				Utils.getSQLDate(fromDate), Utils.getSQLDate(toDate),CRMConstants.FACTOR_TYPE.EXTERNAL, 
				CRMConstants.BUSINESS_ORIENTATION.NEGATIVE);
		
		Map<String,Integer> strengthfeedBackss =SWOTAnalysisSQLs.getFeedBackReasonSplitups(division.getId(), context.getLoggedinCompany(),
				Utils.getSQLDate(fromDate), Utils.getSQLDate(toDate),CRMConstants.FACTOR_TYPE.EXTERNAL, 
				CRMConstants.BUSINESS_ORIENTATION.NEGATIVE);
		strengthLeads.putAll(strengthfeedBackss);
		
		BarChartData barChartData = new BarChartData();
		barChartData.setTitle("Threats");
		barChartData.setSubTitle(" ");
		
		strengthLeads.entrySet().forEach(entry  -> { ;
			BarData tagetBarData = new BarData();
			BarChartData.Division targetDivis = barChartData.new Division();
			tagetBarData.setText(entry.getKey());
			tagetBarData.setLegend(entry.getKey());
			tagetBarData.setValue(entry.getValue());
			tagetBarData.setColor("Red");
			targetDivis.addBarData(tagetBarData);
			barChartData.addDivision(targetDivis);
			if(entry.getValue() > maxValue.get()) {
				maxValue.set(entry.getValue());
			}
		}  );
		
		BarChartData.Range range =  barChartData.new  Range();
		range.setyMax(maxValue.get());
		range.setyMin(0);
		range.setxMin(0);
		range.setxMax(100);
		barChartData.setRange(range);
		
		return barChartData;
	}
	
	

}
