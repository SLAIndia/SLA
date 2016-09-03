var AdminHealthcare=(function(){
	"use strict";
	
	// This is to load goat healthcare list 
	var loadHealthcareDataTable=function(){  
		
		if(!!CMSAdminCons.dataTable["healthcare"].showFlg){
			CMSAdminCons.dataTable["healthcare"].dtFn._fnAjaxUpdate();
			CMSAdminCons.dataTable["healthcare"].dtFn.fnSetColumnVis( 0, false );	
		}else{				
			var	reqData=jQuery.extend({}, CMSAdminCommon.getInitReqData()),
				urlObj=CMSAdminCons.urls_info["getHealthCareList"],
				req="";   	
			reqData["reqData"]=req;				
			$.fn.dataTableExt.sErrMode = 'throw';			
			CMSAdminCons.dataTable["healthcare"].dtFn=$('#healthcareDataTable').dataTable({
			  "ajax": {
			    "url": urlObj.url,
			    "type":"POST",
			    processData: false,
			    beforeSend:function(jqXHR,settings ){
			    	settings.data = JSON.stringify(reqData);
                },
            	"contentType":'application/json',
			    "dataSrc": function ( dataJson ) {	
			     	if(CMSCom.exceptionHandler.isLoginSessionExpire(dataJson)){
			     		if(dataJson.status==="1"){			     		
					     	var jsonRes=JSON.parse(dataJson.resData);					     	
					     	for (var key in jsonRes){
					    		var eachObj=jsonRes[key];			
					    		jsonRes[key][0] = eachObj.healthcareid;					    	
					    		jsonRes[key][1] = eachObj.objCattleEntity.cattleeartagid;
					    		jsonRes[key][2] = CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(eachObj.objCattleEntity.cattledob));
					    		jsonRes[key][3] = CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(eachObj.servicedate));		    
					    		jsonRes[key][4] =eachObj.objCattleEntity.farm.farmcode;		
					    	} 
							setTimeout(function(){
								$("#healthcareDataTable" ).wrapAll( "<div class='table-responsive'></div>" );			
							},100);
							
						    return jsonRes;
			     		}else{
			     			return [];
			     		}
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
			var oSettings = CMSAdminCons.dataTable["healthcare"].dtFn.fnSettings();
		    oSettings._iDisplayLength = 10;			     
		    CMSAdminCons.dataTable["healthcare"].dtFn.fnSetColumnVis( 0, false );
		    CMSAdminCons.dataTable["healthcare"].showFlg=true;
		}			
	};
	// this fn is to invoke after healthcare page load
	var showHealthcareDetlsPageCallBackFn=function(){
		 $("#cms-healthcare-details").removeClass("hidden");					
		$("#breadcrumb-subpage").html("Health Care Details");
		loadHealthcareDataTable();
		adjustScreen.contentHeight();
		
	};
	// this is to load healthcare detls page 
	var showHealthcareDetlsPage=function(){
		var loadStatus= $("#cms-healthcare-details").data("load-status");
		if(!!loadStatus){
			showHealthcareDetlsPageCallBackFn();
		}else{
			 $("#cms-healthcare-details").load("partials/health-care-list.html", function(response, status, xhr){
	            if(status === "success"){
	            	 $("#cms-healthcare-details").data("load-status",true);
	            	showHealthcareDetlsPageCallBackFn();
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}  
	};
	
	return {
		showHealthcareDetlsPage:showHealthcareDetlsPage
	}
	
})();


