var AdminBreading=(function(){
	"use strict";
	
	// This is to load breeding list 
	var loadBreedingDataTable=function(){
		
		if(!!CMSAdminCons.dataTable["breeding"].showFlg){
			CMSAdminCons.dataTable["breeding"].dtFn._fnAjaxUpdate();
			CMSAdminCons.dataTable["breeding"].dtFn.fnSetColumnVis( 0, false );	
		}else{				
			var	reqData=jQuery.extend({}, CMSAdminCommon.getInitReqData()),
				urlObj=CMSAdminCons.urls_info["breedingDetails"],
				req=JSON.stringify({"breedingid": 0});   	
			reqData["reqData"]=req;				
			$.fn.dataTableExt.sErrMode = 'throw';			
			CMSAdminCons.dataTable["breeding"].dtFn=$('#breedingDataTable').dataTable({
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
					    		jsonRes[key][0] = eachObj.breeding_id;					    	
					    		jsonRes[key][1] = !!eachObj.buck_ear_tag?eachObj.buck_ear_tag:"";
					    		jsonRes[key][2] = !!eachObj.doe_ear_tag?eachObj.doe_ear_tag:"";
					    		jsonRes[key][3] = CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(eachObj.breeding_date));				    
					    		jsonRes[key][4] = eachObj.breeding_kids_no;			    		
					    		jsonRes[key][5] =!!eachObj.breeding_aborted_dt? CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(eachObj.breeding_aborted_dt)):"";					    		

					    	} 
							setTimeout(function(){
								$("#breedingDataTable" ).wrapAll( "<div class='table-responsive'></div>" );			
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
			var oSettings = CMSAdminCons.dataTable["breeding"].dtFn.fnSettings();
		    oSettings._iDisplayLength = 10;			     
		    CMSAdminCons.dataTable["breeding"].dtFn.fnSetColumnVis( 0, false );
		  //  CMSAdminCons.dataTable["breeding"].showFlg=true;
		}			
	};	
	
	// this fn is to invoke after breeding page load
	var showBreadingDetlsPageCallBackFn=function(){
		$("#cms-breading-details").removeClass("hidden");					
		$("#breadcrumb-subpage").html("Breeding Details");
		loadBreedingDataTable();
		adjustScreen.contentHeight();		
	};
	
	// this is to load breeding detls page 
	var showBreedingDetlsPage=function(){
		var loadStatus= $("#cms-breading-details").data("load-status");
		if(!!loadStatus){
			showBreadingDetlsPageCallBackFn();
		}else{
			$("#cms-breading-details").load("partials/breeding-list.html", function(response, status, xhr){
	            if(status === "success"){
	            	$("#cms-breading-details").data("load-status",true);
	            	showBreadingDetlsPageCallBackFn();
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}  
	};
	
	return {
		showBreedingDetlsPage:showBreedingDetlsPage
	}
	
})();


