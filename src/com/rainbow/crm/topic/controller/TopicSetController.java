package com.rainbow.crm.topic.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMGeneralController;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.topic.model.Topic;
import com.rainbow.crm.topic.model.TopicLine;
import com.rainbow.crm.topic.model.TopicSet;
import com.rainbow.crm.topic.service.ITopicService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class TopicSetController extends CRMGeneralController{

	@Override
	public PageResult submit(ModelObject object) {
		return new PageResult();
	}

	@Override
	public PageResult submit(ModelObject object, String actionParam) {
		TopicSet topicSet = (TopicSet) object;
		ITopicService service  =  getService() ;
		if  ("Refresh".equalsIgnoreCase(actionParam)) {
			List<Topic> topics = service.getOpenTopics((CRMContext)getContext());
			topicSet.setOpenTopics(topics);
		} else if ("fetchLatest".equalsIgnoreCase(actionParam)) {
			int topicId = topicSet.getCurrentTopic() ;
			if (topicId > 0 ) {
				int readReply = topicSet.getReadReply();
				Topic topic = new Topic();
				topic.setId(topicId);
				List<TopicLine> updatedReplies=  service.getUpdatedReplies(topic, readReply, (CRMContext)getContext());
				topicSet.setNewReplies(updatedReplies);
			}
		}
		return super.submit(object, actionParam);
	}

	
	public Map <String, String > getAllOpenTopics() {
		ITopicService service  =  getService() ;
		List<Topic> topics = service.getOpenTopics((CRMContext)getContext());
		Map<String, String> ans = new LinkedHashMap<String,String> ();
		
		return ans;
	}
	public ITopicService getService() {
		ITopicService serv = (ITopicService) SpringObjectFactory.INSTANCE.getInstance("ITopicService");
		return serv;
	}

}
