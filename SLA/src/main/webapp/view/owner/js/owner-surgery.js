
var OwnerSurgery=(function(){
	
	var loadSurgeryDataTable = function(){

		if(!!CMSOwnerCons.dataTable["surgeryDtls"].showFlg){
			CMSOwnerCons.dataTable["surgeryDtls"].dtFn._fnAjaxUpdate();
			CMSOwnerCons.dataTable["surgeryDtls"].dtFn.fnSetColumnVis( 0, false );			
		}else{	
			var	reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData()),
				urlObj=CMSOwnerCons.urls_info["getSurgeryIllnessData"],
				req={};     
			
			reqData["reqData"]=req;				
			$.fn.dataTableExt.sErrMode = 'throw';
			CMSOwnerCons.dataTable["surgeryDtls"].dtFn=$('#surgeryDataTable').dataTable({
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
				    		jsonRes[key][0] = eachObj.surgillid;
				    		jsonRes[key][1] = eachObj.cattle.cattleeartagid;
				    		jsonRes[key][2] = eachObj.cattle.farm.farmname;
				    		jsonRes[key][3] = CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(eachObj.surgillprocdt));
				    		jsonRes[key][4] = eachObj.vet.objUserEntity.username;
			
		    		
				    		/*var actionHtml="<a href='javascript:void(0);' class='vet-editLink' id ='surgery-edit' data-cattle-id='"+eachObj.cattle.cattleid+"' data-process-dt='"+CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(eachObj.surgillprocdt))+"' ><i class='fa fa-pencil-square-o'></i> Edit </a> ";
				    		jsonRes[key][4] =actionHtml; */
				    		
				    	} 
						setTimeout(function(){
							$( "#surgeryDataTable" ).wrapAll( "<div class='table-responsive'></div>" );
							$(".commonScroll").mCustomScrollbar({theme:"inset-2-dark"});
						},100)
					    return jsonRes;			   
				    }	
			    }
			  },
			  destroy: true,
			  "aaSorting": [[ 0, "desc" ]],
			  "aoColumnDefs": [
			       { 'bSortable': false, 'aTargets': [0] }
			   ],
			  'processing': false,
			  'serverSide': false,
			  'bLengthChange': false, 
			  'fnInitComplete' :function(){	
				 // after initial loading completes event
			  }
			});
			 var oSettings = CMSOwnerCons.dataTable["surgeryDtls"].dtFn.fnSettings();
		     oSettings._iDisplayLength = 10; 			     
		     CMSOwnerCons.dataTable["surgeryDtls"].dtFn.fnSetColumnVis( 0, false );
		}	
	};
	var showSurgeryDtlsPageCallBackFn = function(){
		$("#cms-surgery-details").removeClass("hidden");	
		$("#breadcrumb-subpage").html("Surgery Details");
		loadSurgeryDataTable();
		adjustScreen.contentHeight();
	};
	var showSurgeryDetlsPage = function(){

		var loadStatus= $("#cms-surgery-details").data("load-status");
		if(!!loadStatus){
			showSurgeryDtlsPageCallBackFn();
		}else{
			$("#cms-surgery-details").load("partials/owner-surgery-list.html", function(response, status, xhr){
	            if(status === "success"){
	            	$("#cms-surgery-details").data("load-status",true);
	            	showSurgeryDtlsPageCallBackFn();
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}       
	};
	
	return {
		showSurgeryDetlsPage:showSurgeryDetlsPage
	}
})();

