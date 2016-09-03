var MilkReport = (function(){
	"use strict";
	var loadPage = function(){
		
			var loadStatus= $("#view-milk-report").data("load-status");
			if(!!loadStatus){
				viewmilkReportCallBackFn();
			}else{
				$("#view-milk-report").load("partials/owner-milk-report.html", function(response, status, xhr){
		            if(status === "success"){
		            	$("#view-milk-report").data("load-status",true);
		            	viewmilkReportCallBackFn();
		            }else{
		            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
		            }
		        }).error(CMSCom.exceptionHandler.ajaxFailure);
			}  
	};
	
	
	var viewmilkReportCallBackFn = function(){
		$("#view-milk-report").removeClass("hidden");	
		$("#breadcrumb-subpage").html("Reports");
		resetFields();
		loadDate();
		loadFarm();
		$("#mr-Farm").chosen({allow_single_deselect: true});
		$("#mrDoeId").chosen({allow_single_deselect: true});
		adjustScreen.contentHeight();
	};
	
	var loadDate = function(){
		
		//This functions are used to deal with Date 
		
		$("#fromMilkproddt").off("click").on("click",function() {			 
	        $(this).datepicker().datepicker("show");
	 });
		
		$("#toMilkproddt").off("click").on("click",function() {			 
	        $(this).datepicker().datepicker("show");
	 });
		
		
		$("#fromMilkproddt").datepicker({ format: "dd-mm-yyyy",endDate: new Date()	         
	     }).on('changeDate', function(e){
	    	    $(this).datepicker('hide');
	     }).prop('readonly','readonly');
		
		$("#toMilkproddt").datepicker({ format: "dd-mm-yyyy",endDate: new Date()	         
	     }).on('changeDate', function(e){
	    	    $(this).datepicker('hide');
	     }).prop('readonly','readonly');
		
	};
		
	
	var loadFarm = function(){
	//Function for loading Farm in the dropdown goes here
	
	var urlObj=CMSOwnerCons.urls_info["getFarmDetailsByUser"],
 	reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData());		
	reqData["reqData"]={};
	reqData=JSON.stringify(reqData);
	
	// This is to fetch farm details for select a farm select box
	CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {
		if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
			if(jsonResData.status==="1"){
				//displayCattleAddUpdatePage(jsonResData,farmid);
				var resData=JSON.parse(jsonResData.resData),
				farmDtls=resData["farm_dtls"],	
				selectOptHtml="<option value=''></option>";	
				for(var i in farmDtls){	
					var farmObj=farmDtls[i];
						selectOptHtml+="<option value='"+farmObj["farm_id"]+"'>"+farmObj["farm_name"]+"</option>";		
				}
				$("#mr-Farm").html(selectOptHtml);
				$("#mr-Farm").trigger("chosen:updated");
			}else{
				CMSMessage.showSubPageMsg({"status":0,"msg":"Farm not assigned"});
			}
			
		}	
	}).fail(CMSCom.exceptionHandler.ajaxFailure);
	
	};
	
	var getDoeDetailsByFarm=function(){
		var farmid=$(this).val()*1;
		var urlObj=CMSOwnerCons.urls_info["getCattleDetailsByFarm"],
	 	reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData());		
		reqData["reqData"]=JSON.stringify({});
		reqData["farmid"]=farmid;		
		if(!!farmid&&farmid!==0){
			CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {
				if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
					//This functions load the drop down with response data
					displayDoeDetails(jsonResData);				
				}	
			}).fail(CMSCom.exceptionHandler.ajaxFailure);	
		}else{
			$("#mr-Farm").val("");
			$("#mrDoeId").val("");
			$("#mrDoeId").html("<option value=''></option>");
			$("#mrDoeId").trigger("chosen:updated");
			$("#mr-goBtn").prop("disabled",true);
			
			var saveMsg="Please select a farm "; 										
			CMSMessage.showSubPageMsg({"status":0,"msg":saveMsg});
			
		}	
	};
	
	var displayDoeDetails = function(response){
		$("#mr-goBtn").prop("disabled",false);
		console.log(" displayDoeDetails ")
		var details = JSON.parse(response);
		if(details.status==="1"){
			
			var resData=JSON.parse(details.resData),
			cattleDtls=resData["cattle_dtls"],	
			selectDoeOptHtml="<option value=''></option>";
			var noDoe=true;
			for(var i in cattleDtls){	
				var cattleObj=cattleDtls[i];
			
					if(cattleObj.cattlegender==="F"){
						selectDoeOptHtml+="<option value='"+cattleObj["cattleid"]+"'>"+cattleObj["cattleeartagid"]+"</option>";	
						noDoe=false;
					}
			
			}
			
			if(noDoe){
				resetFields();
				var saveMsg="There is no Doe in the farm selected "; 										
				CMSMessage.showSubPageMsg({"status":0,"msg":saveMsg});	
			}
			$("#mrDoeId").html(selectDoeOptHtml);
			$("#mrDoeId").trigger("chosen:updated");
		}else{
			
			
		}
		
	};
	
	var showReports = function(e){
		try{
			
			if($("#milk-report-form")[0].checkValidity() && mrSaveBtnHandler()){	
				
				var reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData()),
				reportdetails={};
				reportdetails["fromDt"]=$("#fromMilkproddt").val();
				reportdetails["toDt"]=$("#toMilkproddt").val();
				reportdetails["farmId"]=$("#mr-Farm").val();
				reportdetails["cattleId"]=$("#mrDoeId").val() * 1;
				var url="report.html?fromdt="+reportdetails["fromDt"]+"&toDt="+reportdetails["toDt"]+"&farmId="+reportdetails["farmId"]+"&cattleId="+reportdetails["cattleId"]+"&type=milk-production";				
				var newtab = window.open(url, '_blank'); 
				newtab.focus();
				
				
			}
			
			
			
		}catch (er) {
			// TODO: handle exception
			console.log(Error,er);
		}
		e.preventDefault();			
	};
	
		var mrSaveBtnHandler=function(event){
			var flag = true,
			farmId=$("#mr-Farm").val(),
			fromDt=$("#fromMilkproddt").val(),
			fromDtObj = $("#fromMilkproddt").data("datepicker").getDate(),
			toDt = $("#toMilkproddt").val(),
			toDtObj = $("#toMilkproddt").data("datepicker").getDate();
			
			var dateDiff=CMSCom.dateFn.daysBetween(fromDtObj,toDtObj);	
			
			if(!farmId||farmId===""){
				event.stopPropagation();
				CMSMessage.showSubPageMsg({"status":0,"msg":"Select a Farm"});
				flag = false;
			}else if(!fromDt||fromDt===""){
			event.stopPropagation();
			CMSMessage.showSubPageMsg({"status":0,"msg":"Enter From Date"});
			flag = false;
			}else if(!toDt||toDt===""){
			event.stopPropagation();
			CMSMessage.showSubPageMsg({"status":0,"msg":"Enter To Date"});
			flag = false;
			}else if(dateDiff<0){
			event.stopPropagation();
			CMSMessage.showSubPageMsg({"status":0,"msg":" \"From Date of lactation\" should be less than \"To Date of lactation \" "});
			flag = false;
		}
		return flag;
	 };
	
	var resetFields = function(){
		$("#fromMilkproddt").val("");
		$("#toMilkproddt").val("");
		$("#mrDoeId").val("");
		$("#mrDoeId").html("");
		$("#mr-Farm").val("");
		$("#mr-goBtn").prop("disabled",true);
		$("#mr-Farm").trigger("chosen:updated");
		$("#mrDoeId").trigger("chosen:updated");
	};
	
	
	
	
	return {
		loadPage:loadPage,
		getDoeDetailsByFarm:getDoeDetailsByFarm,
		showReports:showReports,
		mrSaveBtnHandler:mrSaveBtnHandler,
		resetFields:resetFields
		
	}
	
})();