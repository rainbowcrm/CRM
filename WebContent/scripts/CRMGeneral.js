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

function showPrintDialog(id,docType) {
	var dialog = document.getElementById(id);  
	document.getElementById('printFRM').contentWindow.document.getElementById('txtid').value = document.getElementById('hdnUserID').value;
	document.getElementById('printFRM').contentWindow.document.getElementById('docType').value=docType;
	if(!dialog.showModal)
	{
		dialogPolyfill.registerDialog(dialog);
	}
	dialog.showModal();
	document.getElementById('printFRM').contentWindow.document.getElementById('cust').submit();
	
 }


function acknowledgeAlert(){
	document.frmList.submitAction.value ="acknowledge" ;
	document.frmList.submit();
}

function closeCustomerCreateDialog(dialogId) {
	window.parent.document.getElementById("txtPhone").value = window.document.getElementById("txtPhone").value;
	window.parent.document.getElementById("txtEmail").value = window.document.getElementById("txtemail").value;
	window.parent.document.getElementById("txtCustomerName").value = window.document.getElementById("txtName").value + " " + window.document.getElementById("txtLName").value;
	window.parent.document.getElementById(dialogId).close();
}