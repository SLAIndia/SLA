var MilkProduction = (function(){
	
	var showMilkProductionDetlsPage = function(){
		
		var loadStatus= $("#cms-milk-production-holder").data("load-status");
		if(!!loadStatus){
			showMilkProductionPageCallBackFn();
		}else{
			$("#cms-milk-production-holder").load("partials/milk-production-list.html", function(response, status, xhr){
	            if(status === "success"){
	            	$("#cms-milk-production-holder").data("load-status",true);
	            	showMilkProductionPageCallBackFn();
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}  
		
	};
	
	var showMilkProductionPageCallBackFn = function(){
		
		$("#cms-milk-production-holder").removeClass("hidden");					
		$("#breadcrumb-subpage").html("Milk Production Details");
		loadMilkProductionDataTable();
		adjustScreen.contentHeight();
		
	}
	
	var loadMilkProductionDataTable=function(){
			
			if(!!CMSAdminCons.dataTable["milkproduction"].showFlg){
				CMSAdminCons.dataTable["milkproduction"].dtFn._fnAjaxUpdate();
				CMSAdminCons.dataTable["milkproduction"].dtFn.fnSetColumnVis( 0, false );	
			}else{				
				var	reqData=jQuery.extend({}, CMSAdminCommon.getInitReqData()),
					urlObj=CMSAdminCons.urls_info["milkProductionDetails"],
				req="";   	
				reqData["reqData"]=req;	
				$.fn.dataTableExt.sErrMode = 'throw';			
				CMSAdminCons.dataTable["milkproduction"].dtFn=$('#milkproductionDataTable').dataTable({
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
						     	var jsonRes=JSON.parse(dataJson.resData),
						     		dataArr=[],
						     		incr=0;

						     	for (var key in jsonRes){
						    		var eachObj=jsonRes[key],
						    		dataObj={};
						         	
						    		if(eachObj.objdoe.cattlegender==="F"){
						    			
						         	dataObj["0"] = eachObj.milkprodid; 
						    		dataObj["1"] = CMSCom.textFn.limitTooltip(eachObj.objdoe.farm.farmname,15);
						         	dataObj["2"] = eachObj.objdoe.cattleeartagid;						  
						    		dataObj["3"] = !!eachObj.milkproddt? CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(eachObj.milkproddt)):"";
						    		dataObj["4"] = eachObj.milkprodqty;							   
						    		dataObj["5"] =CMSCom.textFn.limitTooltip(eachObj.milkprodcomments,15);
						    		dataArr[incr]=dataObj;
						    		incr++;
						    	
						    		}
						    	} 
						     
								setTimeout(function(){
									$("#milkproductionDataTable" ).wrapAll( "<div class='table-responsive'></div>" );			
								},100);
							
							    return dataArr;
				     		}else{
				     			return [];
				     		}
				     	}
				    }			    
				  },
				  destroy: true,
				  "aaSorting": [[ 0, "desc" ]],	
				  //"aoColumnDefs": [{'bSortable': false, 'aTargets': [6]}],		   
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
				var oSettings = CMSAdminCons.dataTable["milkproduction"].dtFn.fnSettings();
			    oSettings._iDisplayLength = 10;			     
			    CMSAdminCons.dataTable["milkproduction"].dtFn.fnSetColumnVis( 0, false );
			//    CMSAdminCons.dataTable["milkproduction"].showFlg=true;
			}			
		};
	
	
	return{
		showMilkProductionDetlsPage:showMilkProductionDetlsPage
	};
	
})();