var AdminVaccination=(function(){
	"use strict";
	
	// This is to load goat vet's vaccination list 
	var loadVetDataTable=function(){

		if(!!CMSAdminCons.dataTable["vaccination"].showFlg){
			CMSAdminCons.dataTable["vaccination"].dtFn._fnAjaxUpdate();
			CMSAdminCons.dataTable["vaccination"].dtFn.fnSetColumnVis( 0, false );			
		}else{	
			var	reqData=jQuery.extend({}, CMSAdminCommon.getInitReqData()),
				urlObj=CMSAdminCons.urls_info["vaccinationDetails"],
				req={};  			
			reqData["reqData"]=req;				
			$.fn.dataTableExt.sErrMode = 'throw';
			CMSAdminCons.dataTable["vaccination"].dtFn=$('#vaccinationDataTable').dataTable({
			  "ajax": {
			    "url": urlObj.url,
			    "type": urlObj.type,
			    //"data":reqData,
			    "dataType": 'json',
			    processData: false,
			    beforeSend:function(jqXHR,settings ){
			    	settings.data = JSON.stringify(reqData);
                },
				"contentType":'application/json',
			    "dataSrc": function (dataJson) {	 			    	
			    	if(CMSCom.exceptionHandler.isLoginSessionExpire(dataJson)&&dataJson.status==="1"){
			    		var jsonRes=JSON.parse(dataJson.resData);				     
				    	for (var key in jsonRes){
				    		var eachObj=jsonRes[key];
				    		jsonRes[key][0] = eachObj.vaccinationid;
				    		jsonRes[key][1] = eachObj.vaccinationname;
				    		jsonRes[key][2] = eachObj.vaccinationtype;
				    		jsonRes[key][3] = eachObj.vaccinationdose+" "+ eachObj.vaccinationunit;
				    		jsonRes[key][4] = CMSCom.textFn.limitTooltip(eachObj.vaccinationdetails,15);
				    		var statusHtml="";
				    		if(eachObj.vaccinationstatus===1){
				    			statusHtml="<i class='fa fa-circle fa-active'></i>"+"<span style='display:none;'>"+1+"</span";
				    		}else{
				    			statusHtml="<i class='fa fa-circle fa-inactive'></i>"+"<span style='display:none;'>"+0+"</span";
				    		}	
				    		jsonRes[key][5] = statusHtml;				    		
				    	} 
						setTimeout(function(){
							$( "#vaccinationDataTable" ).wrapAll( "<div class='table-responsive'></div>" );			
						},100)
					    return jsonRes;			   
				    }else{
				    	return [];
				    }	
			    }
			  },
			  destroy: true,
			  "aaSorting": [[ 0, "desc" ]],
			  'processing': false,
			  'serverSide': false,
			  'bLengthChange': false, 
			  'fnInitComplete' :function(){
				  adjustScreen.contentHeight();
			  },
			  'fnDrawCallback' : function() {
				  adjustScreen.contentHeight();
			  }		  
			});
			 var oSettings = CMSAdminCons.dataTable["vaccination"].dtFn.fnSettings();
		     oSettings._iDisplayLength = 10; 			     
		     CMSAdminCons.dataTable["vaccination"].dtFn.fnSetColumnVis( 0, false );
		    // CMSAdminCons.dataTable["vaccination"].showFlg=true
		}	
	};
	
	// this fn is to invoke after vaccination page load
	var showVaccinationDetlsPageCallBackFn=function(){
		$("#cms-vaccination-details").removeClass("hidden");	
		$("#breadcrumb-subpage").html("Health Care Details");
		loadVetDataTable();
		adjustScreen.contentHeight();
	};
	
	// this is to load vaccination detls page 
	var showVaccinationDetlsPage=function(){
		var loadStatus= $("#cms-vaccination-details").data("load-status");
		if(!!loadStatus){
			showVaccinationDetlsPageCallBackFn();
		}else{
			$("#cms-vaccination-details").load("partials/vaccination-list.html", function(response, status, xhr){
	            if(status === "success"){
	            	$("#cms-vaccination-details").data("load-status",true);
	            	showVaccinationDetlsPageCallBackFn();
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}       
	};
	
	return {
		showVaccinationDetlsPage:showVaccinationDetlsPage
	}
})();