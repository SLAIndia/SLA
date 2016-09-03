var VetFarm=(function(){
	"use strict";
	
	// This is to load farm list 
	var loadFarmDataTable=function(){

		if(!!CMSVetCons.dataTable["farm"].showFlg){
			CMSVetCons.dataTable["farm"].dtFn._fnAjaxUpdate();
			CMSVetCons.dataTable["farm"].dtFn.fnSetColumnVis( 0, false );			
		}else{	
			var	reqData=jQuery.extend({}, CMSVetCommon.getInitReqData()),
				urlObj=CMSVetCons.urls_info["getallFarmDetailsByUser"];
			
			reqData["reqData"]={u_id:CMSVetCommon.getUserId()};			
			$.fn.dataTableExt.sErrMode = 'throw';
			CMSVetCons.dataTable["farm"].dtFn=$('#farmDataTable').dataTable({
			  "ajax": {
			    "url": urlObj.url,
			    "type": urlObj.type,
			    "dataType": 'json',
			    processData: false,
			    beforeSend:function(jqXHR,settings ){
			    	settings.data = JSON.stringify(reqData);
                },
            	"contentType":'application/json',
			    "dataSrc": function (dataJson) {			  
			    	if(CMSCom.exceptionHandler.isLoginSessionExpire(dataJson)){			    	
			    		if(dataJson.status==="1"){	    			
				    		var jsonRes=JSON.parse(dataJson.resData),
			    			farmDtls=jsonRes["farm_dtls"];
					    	for (var key in farmDtls){
					    		var eachObj=farmDtls[key];					    
					    		farmDtls[key][0] = eachObj.farm_id;
					    		farmDtls[key][1] = eachObj.farm_code;
					    		farmDtls[key][2] = eachObj.farm_name;				    	
					    		farmDtls[key][3] = eachObj.farm_location;
					    		var statusHtml="";
					    		if(eachObj.farm_status===true){
					    			statusHtml="<i class='fa fa-circle fa-active'></i>"+"<span style='display:none;'>"+1+"</span";
					    		}else{
					    			statusHtml="<i class='fa fa-circle fa-inactive'></i>"+"<span style='display:none;'>"+0+"</span";
					    		}	
					    		farmDtls[key][4] = statusHtml;	    		
					    	} 

						    return farmDtls;		
			    		}else{			    			
			    			return [];
			    		}			    		
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
					setTimeout(function(){
						$( "#farmDataTable" ).wrapAll( "<div class='table-responsive'></div>" );							
					},100)
				 adjustScreen.contentHeight();
			   },
			   'fnDrawCallback' : function() {
				  adjustScreen.contentHeight();
				}				  
			});
			 var oSettings = CMSVetCons.dataTable["farm"].dtFn.fnSettings();
		     oSettings._iDisplayLength = 10; 			     
		     CMSVetCons.dataTable["farm"].dtFn.fnSetColumnVis( 0, false );
		}	
	};
	
	// this fn is to invoke after farm listing page  load
	var showFarmDetlsPageCallBackFn=function(){
		$("#cms-farm-list").removeClass("hidden");	
		$("#breadcrumb-subpage").html("Farm Details");
		loadFarmDataTable();
		adjustScreen.contentHeight();
	};
	
	// this is to load farm listing page 
	var showFarmDetlsPage=function(){
		var loadStatus= $("#cms-farm-list").data("load-status");
		if(!!loadStatus){
			showFarmDetlsPageCallBackFn();
		}else{
	        $("#cms-farm-list").load("partials/farm-list.html", function(response, status, xhr){
	            if(status === "success"){
	            	$("#cms-farm-list").data("load-status",true);
	            	showFarmDetlsPageCallBackFn();
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}       
	};

	return {
		showFarmDetlsPage:showFarmDetlsPage		
	};	

})();