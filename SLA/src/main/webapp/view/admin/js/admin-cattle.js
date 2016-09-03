var AdminCattle=(function(){
	"use strict";
	
	// This is to load goat cattle list 
	var loadCattleDataTable=function(){
		
		if(!!CMSAdminCons.dataTable["cattle"].showFlg){
			CMSAdminCons.dataTable["cattle"].dtFn._fnAjaxUpdate();
			CMSAdminCons.dataTable["cattle"].dtFn.fnSetColumnVis( 0, false );	
		}else{			
			var reqData=reqData=jQuery.extend({}, CMSAdminCommon.getInitReqData()),
			urlObj=CMSAdminCons.urls_info["getCattleDetailsByFarm"];
			reqData["farmid"]=-1;			
			reqData["reqData"]=JSON.stringify({});	
			$.fn.dataTableExt.sErrMode = 'throw';	
			CMSAdminCons.dataTable["cattle"].dtFn=$('#cattleDataTable').dataTable({
			  "ajax": {
			    "url": urlObj.url,
			    "type": urlObj.type,
			    "data":reqData,
			    "dataSrc": function ( dataJson ) {	
			    	console.log(" dataSrc ")
			     	if(CMSCom.exceptionHandler.isLoginSessionExpire(dataJson)){
			     		if(dataJson.status==="1"){			
					     	var jsonRes=JSON.parse(dataJson.resData),
					     	cattleDtls=jsonRes["cattle_dtls"];				     	
					     	for (var key in cattleDtls){
					    		var eachObj=cattleDtls[key];				  
					    		cattleDtls[key][0] = eachObj.cattleeartagid;
					    		//cattleDtls[key][1] = eachObj.farm.farmname;
					    		cattleDtls[key][1] =  CMSCom.textFn.limitTooltip(eachObj.farm.farmname,15);
					    		cattleDtls[key][2] = eachObj.cattlegender==="M"?"Male":"Female";				    
					    		cattleDtls[key][3] = eachObj.cattlecategory==="A"?"Adult":"Kid";
					    		var statusHtml="";
					    		if(eachObj.cattlestatus===true){
					    			statusHtml="<i class='fa fa-circle fa-active'></i>"+"<span style='display:none;'>"+1+"</span";
					    		}else{
					    			statusHtml="<i class='fa fa-circle fa-inactive'></i>"+"<span style='display:none;'>"+0+"</span";
					    		}				    		
					    		cattleDtls[key][4] = statusHtml;	
					    	} 
							setTimeout(function(){
								$("#cattleDataTable" ).wrapAll( "<div class='table-responsive'></div>" );									
							},100);
							
						    return cattleDtls;
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
			var oSettings = CMSAdminCons.dataTable["cattle"].dtFn.fnSettings();
		    oSettings._iDisplayLength = 10;			     
		 //   CMSAdminCons.dataTable["cattle"].dtFn.fnSetColumnVis( 0, false );
		   // CMSAdminCons.dataTable["cattle"].showFlg=true;
		}			
	};
	
	// this fn is to invoke after cattle page load
	var showCattleDetlsPageCallBackFn=function(){
		$("#cms-cattle-list").removeClass("hidden");					
		$("#breadcrumb-subpage").html("Goat Details");
		loadCattleDataTable();
		adjustScreen.contentHeight();
		
	};	
	// this is to load cattle detls page 
	var showCattleDetlsPage=function(){
		var loadStatus= $("#cms-cattle-list").data("load-status");
		if(!!loadStatus){
			showCattleDetlsPageCallBackFn();
		}else{
	        $("#cms-cattle-list").load("partials/cattle-list.html", function(response, status, xhr){
	            if(status === "success"){
	            	$("#cms-cattle-list").data("load-status",true);
	            	showCattleDetlsPageCallBackFn();
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}  
	};
	
	return {
		showCattleDetlsPage:showCattleDetlsPage
	};	

})();