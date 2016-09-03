var OwnerFarm=(function(){
	
	var loadFarmDataTable=function(){
		
		if(!!CMSOwnerCons.dataTable["farm"].showFlg){
			CMSOwnerCons.dataTable["farm"].dtFn._fnAjaxUpdate();
			CMSOwnerCons.dataTable["farm"].dtFn.fnSetColumnVis( 0, false );	
		}else{	
			var reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData()),
			urlObj=CMSOwnerCons.urls_info["getallFarmDetailsByUser"];
			
			reqData["reqData"]={u_id:CMSOwnerCom.getUserId()};			

			$.fn.dataTableExt.sErrMode = 'throw';
			CMSOwnerCons.dataTable["farm"].dtFn=$('#farmDataTable').dataTable({
			  "ajax": {
			    "url": urlObj.url,
			    "type": urlObj.type,
			    "dataType": 'json',
			    processData: false,
			    beforeSend:function(jqXHR,settings ){
			    	settings.data = JSON.stringify(reqData);
                },
            	"contentType":'application/json',
			    "dataSrc": function ( dataJson ) {		
			    	if(CMSCom.exceptionHandler.isLoginSessionExpire(dataJson)){
				     	var jsonRes=JSON.parse(dataJson.resData),
				     	farmDtls=jsonRes["farm_dtls"];
				     	for (var key in farmDtls){
				    		var eachObj=farmDtls[key];
				    	
				    		farmDtls[key][0] = eachObj.farm_id;
				    		farmDtls[key][1] = eachObj.farm_code;
				    		farmDtls[key][2] = CMSCom.textFn.limitTooltip(eachObj.farm_name ,15);				    
				    		farmDtls[key][3] = CMSCom.textFn.limitTooltip(eachObj.farm_location ,15);		
				    		var statusHtml="";
				    		if(eachObj.farm_status===true){
				    			statusHtml="<i class='fa fa-circle fa-active'></i>"+"<span style='display:none;'>"+1+"</span";
				    		}else{
				    			statusHtml="<i class='fa fa-circle fa-inactive'></i>"+"<span style='display:none;'>"+0+"</span";
				    		}				    		
				    		farmDtls[key][4] = statusHtml;			    		
				    	} 

					    return farmDtls;		
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
						$("#farmDataTable" ).wrapAll( "<div class='table-responsive'></div>" );	
						adjustScreen.contentHeight();
					},100);
			  },
			  'fnDrawCallback' : function() {
				  adjustScreen.contentHeight();
			  }
			});
			var oSettings = CMSOwnerCons.dataTable["farm"].dtFn.fnSettings();
		     oSettings._iDisplayLength = 10;			     
		     CMSOwnerCons.dataTable["farm"].dtFn.fnSetColumnVis( 0, false );
		}			
	};
	
	var showFarmDetlsPageCallBackFn=function(){
		$("#farms-dtTableHolder").removeClass("hidden");					
		$("#breadcrumb-subpage").html("Farm Details");
		loadFarmDataTable();
		adjustScreen.contentHeight();
	
	};
	var showFarmDetlsPage=function(){

		var loadStatus= $("#farms-dtTableHolder").data("load-status");
		if(!!loadStatus){
			showFarmDetlsPageCallBackFn();
		}else{
	        $("#farms-dtTableHolder").load("partials/owner-farm.html", function(response, status, xhr){
	            if(status === "success"){
	            	$("#farms-dtTableHolder").data("load-status",true);
	            	showFarmDetlsPageCallBackFn();
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}  
	}
	
	return {
		loadFarmDataTable:loadFarmDataTable,
		showFarmDetlsPage:showFarmDetlsPage,
	};	
	
})();