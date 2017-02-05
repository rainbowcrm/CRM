package com.rainbow.framework.query.model;

import java.util.ArrayList;
import java.util.List;

public class QueryReport {
	
	List<QueryRecord>  records;

	public List<QueryRecord> getRecords() {
		return records;
	}

	public void setRecords(List<QueryRecord> records) {
		this.records = records;
	}
	
	public void addRecord(QueryRecord record) {
		if(records == null)
			records = new ArrayList<> ();
		records.add(record);
	}
	

}
