package com.rainbow.crm.saleslead.model;

import java.util.Date;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.division.model.Division;
import com.techtrade.rads.framework.model.graphdata.BarChartData;

public class SalesLeadAnalyzer  extends CRMModelObject{ 
	
	Division division;
	
	Date fromDate;
	Date toDate;
	
	BarChartData leadsBarData;

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public BarChartData getLeadsBarData() {
		return leadsBarData;
	}

	public void setLeadsBarData(BarChartData leadsBarData) {
		this.leadsBarData = leadsBarData;
	}
	
	
	

}
