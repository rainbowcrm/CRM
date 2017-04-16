package com.rainbow.crm.saleslead.model;

import java.util.ArrayList;
import java.util.List;

import com.rainbow.crm.document.model.Document;
import com.rainbow.crm.followup.model.Followup;

public class SalesLeadExtended extends SalesLead {

	List<Followup> followups;
	List<Document > documents;
	
	public List<Followup> getFollowups() {
		return followups;
	}
	public void setFollowups(List<Followup> followups) {
		this.followups = followups;
	}
	public List<Document> getDocuments() {
		return documents;
	}
	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
	
	public void addFollowup(Followup followup) {
		if (followups == null)
			followups = new ArrayList<Followup> ();
		followups.add(followup);
	}
	
	public void addDocument(Document document) {
		if (documents == null)
			documents = new ArrayList<Document> ();
		documents.add(document);
	}

	
}
