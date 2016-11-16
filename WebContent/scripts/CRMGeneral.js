function deleteFromList() {
	var selObjs = document.getElementsByName("idChkSel");
	var oneSelected = false;
	for (var i=0; i< selObjs.length;i++){
	      if(selObjs[i].checked){
	           oneSelected =  true;
	           break;
	      }
	}
	if (oneSelected == false) {
		alert('Selected atleast one recrord to delete') ;
		return false;
	}
	var conf = confirm('Are you sure to delete the selected record(s)');
	if (conf == true )
		 return true;
	else return false;
	
}

function validateforCreate() {
	return true;
}



function acknowledgeAlert(){
	document.frmList.submitAction.value ="acknowledge" ;
	document.frmList.submit();
}