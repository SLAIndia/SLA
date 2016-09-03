var VetCattle=(function(){
	"use strict";
	
	var loadCattleDataTable=function(){
		
		if(!!CMSVetCons.dataTable["cattle"].showFlg){
			CMSVetCons.dataTable["cattle"].dtFn._fnAjaxUpdate();
			CMSVetCons.dataTable["cattle"].dtFn.fnSetColumnVis( 0, false );	
		}else{	
			var reqData=reqData=jQuery.extend({}, CMSVetCommon.getInitReqData()),
			urlObj=CMSVetCons.urls_info["getCattleDetailsOfAssignedFarm"];
			reqData["farmid"]=-1;			
			reqData["reqData"]=JSON.stringify({});
			
			$.fn.dataTableExt.sErrMode = 'throw';
			CMSVetCons.dataTable["cattle"].dtFn=$('#cattleDataTable').dataTable({
			  "ajax": {
			    "url": urlObj.url,
			    "type": urlObj.type,
			    "data":reqData,
			    "dataSrc": function ( dataJson ) {				    
			     	if(CMSCom.exceptionHandler.isLoginSessionExpire(dataJson)){
			     		if(dataJson.status==="1"){	
			     			var jsonRes=JSON.parse(dataJson.resData),
					     	cattleDtls=jsonRes;				     	
					     	for (var key in cattleDtls){
					    		var eachObj=cattleDtls[key];				  
					    		cattleDtls[key][0] = eachObj.cattle_id;
					    		//var viewLink="<a href='javascript:void(0)' class='view-cattle-details' data-cattle-id='"+eachObj.cattle_id+"'>"+eachObj.cattle_ear_tag_id+"</a>";
					    		cattleDtls[key][1] = eachObj.cattle_ear_tag_id;
					    		cattleDtls[key][2] = eachObj.cattle_farm_name;
					    		cattleDtls[key][3] = eachObj.cattle_gender==="M"?"Male":"Female";				    
					    		cattleDtls[key][4] = eachObj.cattle_category==="A"?"Adult":"Kid";
					    		var statusHtml="";
					    		if(eachObj.cattle_status===true){
					    			statusHtml="<i class='fa fa-circle fa-active'></i>"+"<span style='display:none;'>"+1+"</span";
					    		}else{
					    			statusHtml="<i class='fa fa-circle fa-inactive'></i>"+"<span style='display:none;'>"+0+"</span";
					    		}				    		
					    		cattleDtls[key][5] = statusHtml;	
					    	//	var actionHtml="<a href='javascript:void(0);' class='edit-link-cattle-dtls' data-cattle-id='"+eachObj.cattle_id+"'><i class='fa fa-pencil-square-o'></i> Edit</a> ";
					    	//	cattleDtls[key][6] = actionHtml;
					    	} 

							
						    return cattleDtls;
			     		}else{			     	
			     			return []
			     		}
			     	}
			    }			    
			  },
			  destroy: true,
			  "aaSorting": [[ 0, "desc" ]],	
			 // "aoColumnDefs": [{'bSortable': false, 'aTargets': [6]}],		   
			  'processing': false,
			  'serverSide': false,
			  'bLengthChange': false, 
			  'fnInitComplete' :function(){
					setTimeout(function(){
						$( "#cattleDataTable" ).wrapAll( "<div class='table-responsive'></div>" );							
					},100)
				 adjustScreen.contentHeight();
			   },
			   'fnDrawCallback' : function() {
				  adjustScreen.contentHeight();
				}				  
			});
			var oSettings = CMSVetCons.dataTable["cattle"].dtFn.fnSettings();
		    oSettings._iDisplayLength = 10;			     
		    CMSVetCons.dataTable["cattle"].dtFn.fnSetColumnVis( 0, false );
		}			
	};
	var showCattleDetlsPageCallBackFn=function(){
		$("#cms-cattle-list").removeClass("hidden");					
		$("#breadcrumb-subpage").html("Goat Details");
		loadCattleDataTable();
		adjustScreen.contentHeight();
	};
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