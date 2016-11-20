
function isEven(n) {
  n = Number(n);
  return n === 0 || !!(n && !(n%2));
}


function addRow(ctrl,oddRowStyle,evenRowStyle) {
	console.log('ctrl=' + ctrl ) ;
	var row = ctrl.parentElement.parentElement
	console.log('row=' + row ) ;
	var tabl = row.parentElement ;
	console.log('tabl=' + tabl ) ;
	var rowCount = tabl.rows.length;
	console.log('evenRowStyle=' + evenRowStyle  + ":oddRowStyle="  + oddRowStyle ) ;
	var newrow = tabl.insertRow();
	if (isEven(rowCount))
		newrow.className  = oddRowStyle  ;
	else
		newrow.className  =  evenRowStyle ;
	newrow.innerHTML = row.innerHTML;
	if (typeof loadAjaxServices == 'function' )
		loadAjaxServices();
}

function addRowofTable(tabl) {
	console.log('tabl=' + tabl ) ;
	var newrow = tabl.insertRow();
	//newrow.innerHTML = row.innerHTML;
	if (typeof loadAjaxServices == 'function' )
		loadAjaxServices();
}

function deleteRow(ctrl) {
	console.log('ctrl=' + ctrl ) ;
	var row = ctrl.parentElement.parentElement
	console.log('row=' + row ) ;
	var tabl = row.parentElement ;
	console.log('tabl=' + tabl ) ;
	var i = row.rowIndex - 1;
	console.log("rowindex=" + i);
    tabl.deleteRow(i);
	
}


function getCurrentObjectIndex(currentCtrl)  {
	var objName = currentCtrl.name;
	var elemCount = document.getElementsByName(objName).length;
	console.log("getCurrentObjectIndex:elemCount= " + elemCount  + ":objName="  + objName);
	for (var i = 0 ; i < elemCount ; i ++ ) {
		if (document.getElementsByName(objName)[i]  == 	currentCtrl)  {
			return i ;
		}
	}
	return  0 ;
}

var clickedCellIndex = -1;
function showLookupDialog(id,curControl) {
	var index  = getCurrentObjectIndex(curControl);  
	console.log('index=' + index); 
	clickedCellIndex= index;
	var dialog = document.getElementById(id);  
	if(!dialog.showModal)
	{
		dialogPolyfill.registerDialog(dialog);
	}
	dialog.showModal();
	document.getElementById('idFRM' +id).contentDocument.clickedCellIndex = index;
	
 }
function fireAjaxRequest (service, requestCtrls, responseCtrls, currentCtrl) {
	var requestStr = appURL + "rdscontroller?ajxService=" + service;
	var index  = getCurrentObjectIndex(currentCtrl);
	console.log(requestCtrls + "index" + index) ;
	//console.log(responseCtrls) ;
    var  property ;
	for (property in  requestCtrls) {
		    var ctrl =  requestCtrls[property];
		    var elem = document.getElementsByName(ctrl)[index];
		    var value = elem.value; 
			requestStr = requestStr + "&" + property +"=" + value  ;
	}
	var reqObject = new XMLHttpRequest();
	reqObject.open("GET",requestStr,false);
	reqObject.send();
	console.log("Resp" + reqObject.responseText);
	for (property in responseCtrls) {
				var ctrl =  responseCtrls[property];
				var elem = document.getElementsByName(ctrl)[index];
				var reqCtrl = requestCtrls[property] ;
				//console.log ('reqCtrl ' + reqCtrl + 'prop' + ctrl );
				if (typeof(reqCtrl) != "undefined")
					elem.value = '';
	}
	var jsonResponse =  JSON.parse(reqObject.responseText) ;
	for (property in responseCtrls) {
		var ctrl =  responseCtrls[property];
		var elem = document.getElementsByName(ctrl)[index];
		
		//console.log('elem = ' + elem  + 'ctrl =' + ctrl + 'prop =' + property) ;
		//console.log('elem type= ' + (jsonResponse[property] instanceof Array)) ;
		if (typeof(elem) == "undefined") continue ;
		elem.value = '';
//		console.log(elem.nodeName);
		if (elem.type =='select-one'  && (jsonResponse[property] instanceof Array) ){
			elem.options.length = 0;
			var propArray =jsonResponse[property] ;
			for (var i in propArray) {
				  var selectElem = propArray[i]; 
				  var text = selectElem.text;
				  var value = selectElem.value;
				  console.log (text + ":" + value + ":" + i);
				  var option = document.createElement("option");
				  option.text = text;
				  option.value=value;
				  elem.add(option);
			}
			
		}else {
		//	console.log('property=' + property) ;
			var valprop = jsonResponse[property]  ;
			//console.log('valprop=' + valprop) ;
			if(valprop != null && valprop !=  "undefined") {
				elem.value = valprop;
				if(elem.nodeName == 'SPAN' )
					elem.innerHTML= valprop;
			}
			
		}
	}
	
}

function dummy() {

alert('h') ;
}
function registerEvent(ctrl, service,requestCtrls, responseCtrls) {
	var ct = document.getElementsByName(ctrl).length;
	console.log("ct=" + ct);
	for (var i = 0 ; i < ct ; i ++ ) {
		var elem = document.getElementsByName(ctrl)[i];
		console.log("i=" + i +  "Addding event listener"  + elem.name); 
		elem.addEventListener("blur", function(){  fireAjaxRequest (service, requestCtrls, responseCtrls , this) });
	}
}

function pushError(divCtrlId,newMsg) {
	var divCtrl = document.getElementById(divCtrlId);
	var existMsg = divCtrl.innerHTML ;
	divCtrl.innerHTML =   newMsg   + existMsg ;
}

function addError(divCtrlId, errorclass, errorMsg) {
	
		var divCtrl = document.getElementById(divCtrlId);
		var existMsg = divCtrl.innerHTML ;
		var newMsg = "<span class ='" + errorclass + "'>"  + errorMsg +"</span><br>";
		divCtrl.innerHTML =   newMsg   + existMsg ;
		
}

function isNumberKey(evt)
{
   var charCode = (evt.which) ? evt.which : evt.keyCode;
   if (charCode != 46 && charCode > 31 
     && (charCode < 48 || charCode > 57))
      return false;

   return true;
}