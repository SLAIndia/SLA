var GoatReport = (function(){
	
	"use strict";
	var loadPage = function(){
		try{
		
			var loadStatus= $("#view-goat-report").data("load-status");
			if(!!loadStatus){
				viewgoatReportCallBackFn();
			}else{
				$("#view-goat-report").load("partials/owner-goat-report.html", function(response, status, xhr){
		            if(status === "success"){
		            	$("#view-goat-report").data("load-status",true);
		            	viewgoatReportCallBackFn();
		            }else{
		            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
		            }
		        }).error(CMSCom.exceptionHandler.ajaxFailure);
			}
		}catch (er) {
			// TODO: handle exception
			console.log(Error,er);
		}
	};
	
	var viewgoatReportCallBackFn = function(){
		mrResetBtnHandler();
		$("#view-goat-report").removeClass("hidden");	
		$("#breadcrumb-subpage").html("Reports");
		//resetFields();
		loadDate();
		loadFarm();
		$("#gr-Farm").chosen({allow_single_deselect: true});
		adjustScreen.contentHeight();
			
	};
	
	var loadDate = function(){
			
			//This functions are used to deal with Date 
			
			$("#fromGoatreportdt").off("click").on("click",function() {			 
		        $(this).datepicker().datepicker("show");
		 });
			
			$("#toGoatreportdt").off("click").on("click",function() {			 
		        $(this).datepicker().datepicker("show");
		 });
			
			
		$("#fromGoatreportdt").datepicker({ format: "dd-mm-yyyy",endDate: new Date()	         
		     }).on('changeDate', function(e){
		    	    $(this).datepicker('hide');
		     }).prop('readonly','readonly');
			
			$("#toGoatreportdt").datepicker({ format: "dd-mm-yyyy",endDate: new Date()	         
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
					var resData=JSON.parse(jsonResData.resData),
					farmDtls=resData["farm_dtls"],	
					selectOptHtml="<option value=''></option>";	
					for(var i in farmDtls){	
						var farmObj=farmDtls[i];
							selectOptHtml+="<option value='"+farmObj["farm_id"]+"'>"+farmObj["farm_name"]+"</option>";		
					}
					$("#gr-Farm").html(selectOptHtml);
					$("#gr-Farm").trigger("chosen:updated");
				}else{
					CMSMessage.showSubPageMsg({"status":0,"msg":"Farm not assigned"});
				}
				
			}	
		}).fail(CMSCom.exceptionHandler.ajaxFailure);
		
		};
		
		
		var mrSaveBtnHandler=function(){
			var flag = true,
			 farmId=$("#gr-Farm").val(),
			 fromDt=$("#fromGoatreportdt").val(),
			 fromDtObj = $("#fromGoatreportdt").data("datepicker").getDate(),
			 toDt = $("#toGoatreportdt").val(),
			 toDtObj = $("#toGoatreportdt").data("datepicker").getDate();
			

			var dateDiff=CMSCom.dateFn.daysBetween(fromDtObj,toDtObj);	
			
			if(!fromDt||fromDt===""){
		
			CMSMessage.showSubPageMsg({"status":0,"msg":"Enter From Date"});
			flag = false;

		
		}else if(!toDt||toDt===""){
		
			CMSMessage.showSubPageMsg({"status":0,"msg":"Enter To Date"});
			flag = false;			

		}else if(dateDiff<0){
		
			CMSMessage.showSubPageMsg({"status":0,"msg":" \"From D.O.B\" should be less than \"To D.O.B \" "});
			flag = false;
		
		}
		return flag;
	 };
	 
	 
	 var showReports = function(e){
			try{
				
				if($("#goat-report-form")[0].checkValidity() && mrSaveBtnHandler()){	
					
					var reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData()),
					reportdetails={};
					reportdetails["fromDt"]=$("#fromGoatreportdt").val();
					reportdetails["toDt"]=$("#toGoatreportdt").val();
					reportdetails["farmId"]=!!($("#gr-Farm").val() * 1)?($("#gr-Farm").val() * 1):0;
				

					
					var url="report.html?fromdt="+reportdetails["fromDt"]+"&toDt="+reportdetails["toDt"]+"&farmId="+reportdetails["farmId"]+"&type=goat-report";				
					var newtab = window.open(url, '_blank'); 
					newtab.focus();
					
					
				}
				
				
				
			}catch (er) {
				// TODO: handle exception
				console.log(Error,er);
			}
			e.preventDefault();			
		};
		
		var mrResetBtnHandler = function(){
			$("#fromGoatreportdt").val("");
			$("#toGoatreportdt").val("");
			$("#gr-Farm").val("");
			//$("#gr-Farm").html("");
			$("#gr-Farm").trigger("chosen:updated");
			
		};
	
	return {
		loadPage:loadPage,
		showReports:showReports,
		mrResetBtnHandler:mrResetBtnHandler
		
	}
		
	
	
	
	
})();