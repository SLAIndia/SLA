var VetBreading=(function(){
	"use strict";
	var loadBreedingDataTable=function(){
		
		if(!!CMSVetCons.dataTable["breeding"].showFlg){
			CMSVetCons.dataTable["breeding"].dtFn._fnAjaxUpdate();
			CMSVetCons.dataTable["breeding"].dtFn.fnSetColumnVis( 0, false );	
		}else{				
			var	reqData=jQuery.extend({}, CMSVetCommon.getInitReqData()),
				urlObj=CMSVetCons.urls_info["breedingDetails"],
				req=JSON.stringify({"breedingid": 0});   	
			reqData["reqData"]=req;				
			$.fn.dataTableExt.sErrMode = 'throw';			
			CMSVetCons.dataTable["breeding"].dtFn=$('#breedingDataTable').dataTable({
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
					    		var actionHtml="<a href='javascript:void(0);' class='edit-link-breeding-dtls' data-breeding-id='"+eachObj.breeding_id+"'><i class='fa fa-pencil-square-o'></i> Edit</a> ";
					    	//	jsonRes[key][6] = actionHtml;
					    	} 
							
						    return jsonRes;
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
					setTimeout(function(){
						$( "#breedingDataTable" ).wrapAll( "<div class='table-responsive'></div>" );							
					},100)
				 adjustScreen.contentHeight();
			   },
			   'fnDrawCallback' : function() {
				  adjustScreen.contentHeight();
				}				  
			});
			var oSettings = CMSVetCons.dataTable["breeding"].dtFn.fnSettings();
		    oSettings._iDisplayLength = 10;			     
		    CMSVetCons.dataTable["breeding"].dtFn.fnSetColumnVis( 0, false );
		}			
	};
	
	
	
	var showBreadingDetlsPageCallBackFn=function(){
		$("#cms-vet-breeding-holder").removeClass("hidden");					
		$("#breadcrumb-subpage").html("Breeding Details");
		loadBreedingDataTable();
		adjustScreen.contentHeight();
		
	};
	var showBreedingDetlsPage=function(){
		var loadStatus= $("#cms-vet-breeding-holder").data("load-status");
		if(!!loadStatus){
			showBreadingDetlsPageCallBackFn();
		}else{
			$("#cms-vet-breeding-holder").load("partials/breeding-list.html", function(response, status, xhr){
	            if(status === "success"){
	            	$("#cms-vet-breeding-holder").data("load-status",true);
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




