function getReplies()
{
	
	var selectedTopic = document.topicfrm.lstAllTopics.value ;
	console.log('selectedTopic = '  +selectedTopic) ;
	document.topicfrm.hdnreplyRead.value=0;
	document.getElementById('txtdiscussion').innerHTML ='';
	var jsonVar= {
			"selectedTopic":selectedTopic,
			"repliesRead":0,
	}
	populateReplies(jsonVar);
	
	

}

function populateReplies(jsonVar )
{
	var requestStr = appURL + "rdscontroller?ajxService=replyTopic";
	var reqObject = new XMLHttpRequest();   // new HttpRequest instance 
	reqObject.open("POST", requestStr,false);
	reqObject.setRequestHeader("Content-Type", "application/json");
	reqObject.send(JSON.stringify(jsonVar));
	console.log("Resp" + reqObject.responseText);
	var totalResponse =  JSON.parse(reqObject.responseText) ;
	var propArray =  totalResponse['updatedReplies'] ;
	var ct = 0;
	for (var i in propArray) {
	var jsonResponse = propArray[i];
	var reply= jsonResponse['Reply'];
	var repliedBy= jsonResponse['RepliedBy'];
	varRepliedByUser  = repliedBy['UserId'];
	document.getElementById('txtdiscussion').innerHTML = document.getElementById('txtdiscussion').innerHTML + 
	    "<B>" + varRepliedByUser + "</B>" +":" + reply + "\n";
		ct ++ ;
	}
	document.topicfrm.hdnreplyRead.value=ct;

}

function postReply()
{
	var requestStr = appURL + "rdscontroller?ajxService=replyTopic";
	var selectedTopic = document.topicfrm.lstAllTopics.value ;
	console.log('selectedTopic = '  +selectedTopic) ;
	var reply = document.topicfrm.txtnewReply.value;
	var repliesRead= document.topicfrm.hdnreplyRead.value;

	var jsonVar= {
			"selectedTopic":selectedTopic,
			"reply":reply,
			"repliesRead":repliesRead
	}
	
	populateReplies(jsonVar);

}
