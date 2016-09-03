var AdminVet=(function(){
	"use strict";	
	
	var loadVetDataTable=function(){

		if(!!CMSAdminCons.dataTable["vet"].showFlg){
			CMSAdminCons.dataTable["vet"].dtFn._fnAjaxUpdate();
			CMSAdminCons.dataTable["vet"].dtFn.fnSetColumnVis( 0, false );			
		}else{	
			var	reqData=jQuery.extend({}, CMSAdminCommon.getInitReqData()),
				urlObj=CMSAdminCons.urls_info["getUserDetails"],
				req={"id": 0,"role_name" :"VET"};     
			
			reqData["reqData"]=req;				
			$.fn.dataTableExt.sErrMode = 'throw';
			CMSAdminCons.dataTable["vet"].dtFn=$('#vetDataTable').dataTable({
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
				    		var eachObj=jsonRes[key].objUserEntity,
				    			vetStatus=jsonRes[key].vetStatus;
				    		jsonRes[key][0] = eachObj.id;
				    		jsonRes[key][1] = !!eachObj.userfname?eachObj.userfname:"";					    			    	
				    		jsonRes[key][2] =  !!eachObj.username?eachObj.username:"";
				    		jsonRes[key][3] =  !!eachObj.useremail?eachObj.useremail:"";
				    		var statusHtml="",
				    			 approveActHtml="";

				    		if(vetStatus*1===1){ // should be change to status
				    			statusHtml="<i class='fa fa-circle fa-active'></i>"+"<span style='display:none;'>"+1+"</span";
				    			approveActHtml="<a href='javascript:void(0);' class='vet-approveLink padding-R10' data-vet-id='"+eachObj.id+"' data-change-status='"+0+"'><i class='fa fa-ban'></i> Unapprove </a> ";
				    		}else{
				    			statusHtml="<i class='fa fa-circle fa-inactive'></i>"+"<span style='display:none;'>"+0+"</span";
				    			approveActHtml="<a href='javascript:void(0);' class='vet-approveLink padding-R10' data-vet-id='"+eachObj.id+"'  data-change-status='"+1+"'><i class='fa  fa-check-square'></i> Approve </a> ";
				    		}	
				    		jsonRes[key][4] = statusHtml;				    		
				    		var actionHtml=approveActHtml;
				    		if(vetStatus*1===1){
				    			actionHtml+="<a href='javascript:void(0);' class='vet-editAssignFarmLink' data-vet-id='"+eachObj.id+"'><i class='fa fa-pencil-square-o'></i> Assign/Reassign Farm</a> ";
				    		}
				    	
				    		jsonRes[key][5] =actionHtml; 
				    		
				    	} 
						setTimeout(function(){
							$( "#vetDataTable" ).wrapAll( "<div class='table-responsive'></div>" );						
						},100)
					    return jsonRes;			   
				    }	
			    }
			  },
			  destroy: true,
			  "bStateSave": true,
			  "aaSorting": [[ 0, "desc" ]],
			  "aoColumnDefs": [
			       { 'bSortable': false, 'aTargets': [5 ] }
			   ],
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
			 var oSettings = CMSAdminCons.dataTable["vet"].dtFn.fnSettings();
		     oSettings._iDisplayLength = 10; 			     
		     CMSAdminCons.dataTable["vet"].dtFn.fnSetColumnVis( 0, false );
		}	
	};
	var showVetDetlsPageCallBackFn=function(){
		$("#cms-vet-details").removeClass("hidden");	
		$("#breadcrumb-subpage").html("User Details");
		loadVetDataTable();
		adjustScreen.contentHeight();
	};
	var showVetDetlsPage=function(){

		var loadStatus= $("#cms-vet-details").data("load-status");
		if(!!loadStatus){
			showVetDetlsPageCallBackFn();
		}else{
	        $("#cms-vet-details").load("partials/vet-list.html", function(response, status, xhr){
	            if(status === "success"){
	            	$("#cms-vet-details").data("load-status",true);
	            	showVetDetlsPageCallBackFn();
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}       
	};
	
	var vetObjects = [],
		vetMap = {};
	var setSelectVetTypeAHead=function(){
		
		var constructMap =function (data, consMap) {
		    var consObjects = [];
		    $.each(data, function(i, object) {
		    	var seltext=object.user_fname;
		    	seltext+=!!object.user_lname?" "+object.user_lname :"";
		    	seltext+=!!object.user_name?" ("+object.user_name+" )" :""; // username required		    	
		    	consMap[seltext] = object;
		        consObjects.push(seltext);
		    });
		    return consObjects;
		};
		
		var urlObj=CMSAdminCons.urls_info["getVetDetailsByName"];
		
		$('#selectvet').typeahead({
			  hint: true,
			  highlight: true,
			  minLength: 1
			  },{			
			  source: function (query, pr) {
		        	var vetKey={},
		        	reqData=jQuery.extend({}, CMSAdminCommon.getInitReqData());
		        	vetKey["vetfnamelname"]=query;	
					reqData["reqData"]=vetKey;			
					reqData=JSON.stringify(reqData);
					var matches = [];
					CMSCom.ajaxService.getJsonDataAsyncFalse(urlObj,reqData).done(function(jsonResData) {				
						if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
							if(jsonResData.status==="1"){
								$('#selectedVetId').val("");
								var resData=JSON.parse(jsonResData.resData);						
								vetObjects = [];
								vetMap = {};						
								vetObjects = constructMap(resData, vetMap);            
								pr(vetObjects);								
							}		
						}
					}).fail(CMSCom.exceptionHandler.ajaxFailure);	
			  },
			  updater:function (item) {

			        //item = selected item
			        //do your stuff.

			        //dont forget to return the item to reflect them into input
			        return item;
			    }
			});	
		$('#selectvet').on('typeahead:selected', function(evt, item) {
		     $('#selectedVetId').val(vetMap[item].vet_id);
			 $("#ipt-er-selectvet").hide();
			 $("#assignvetfarmdelbtn").show();
				$("#selectvet").prop('disabled', true);	
		});

	};
	
	var showListfarm = function(){
		
		var urlObj=CMSAdminCons.urls_info["getFarmsByCodeAndName"],
			farmlist={},
			reqData=jQuery.extend({}, CMSAdminCommon.getInitReqData()),
			seachKey=$(this).val(),
			msg="";
			
		if(!!seachKey&&seachKey.trim().length!==0){				
		
			farmlist["farmcode"]=seachKey;	
			reqData["reqData"]=farmlist;			
			reqData=JSON.stringify(reqData);
			
			var farmSelectOptHtml="";
			
			CMSCom.ajaxService.getJsonData(urlObj,reqData).done(function(jsonResData) {				
				if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
					if(jsonResData.status==="1"){
						var farmArr=JSON.parse(jsonResData.resData);
						for(var i in farmArr){
							var farm=farmArr[i];	
							if(!(farm["farm_id"] in CMSAdminCons.vetAssignFarms)){
								farmSelectOptHtml+="<option value='"+farm["farm_id"]+"'>"+farm["farm_code"]+" - "+farm["farm_name"]+"</option>";
							}						
						}	
						$("#vetfarmListSelect").html(farmSelectOptHtml);	
					}else{
						$("#vetfarmListSelect").html("");
					}		
				}
			}).fail(CMSCom.exceptionHandler.ajaxFailure);
		}else{
			$("#vetfarmListSelect").html("");
		}
	};
	
	var addFarmSelectToListHandler=function(e){
		
		var optionHtml="";
		var selected=[];
		$('#vetfarmListSelect :selected').each(function(i){ 		
			var _this=$(this),
				val=_this.val(),
				text=_this.text();
			 selected[val]=text;	
			 optionHtml+="<option value='"+val+"' selected>"+text+"</option>";	
			 CMSAdminCons.vetAssignFarms[val]=text;
			_this.remove();
		});
		$("#ipt-er-vetassignfarmid").hide();
		$("#vetassignFarmList").append(optionHtml);
		
	};

	var removeFarmSelectHandler=function(e){

		var optionHtml="";
		$('#vetassignFarmList :selected').each(function(i){ 		
			var _this=$(this),
				val=_this.val(),
				text=_this.text();
			delete CMSAdminCons.vetAssignFarms[val];
			 optionHtml+="<option value='"+val+"'>"+text+"</option>";
			_this.remove();
		});
		$("#vetfarmListSelect").append(optionHtml);
	
	};
	
	var populateVetAssignDtls=function(jsonResData){
		
		var jsonRes=JSON.parse(jsonResData.resData),   	
    	 	vetid=jsonRes[0].vetid,
    		resData=jsonRes[0].objUserEntity,
    		
    		
    		farmIds= "";
		
		if(!!jsonRes[1]&&!!jsonRes[1].farmIds){
			farmIds=jsonRes[1].farmIds;
		}
		var optionHtml="";
		if(!!farmIds){
			for(var key in farmIds){
				var farmText=farmIds[key][2]+" - "+farmIds[key][1],
					farmId=farmIds[key][0];
				
				 CMSAdminCons.vetAssignFarms[farmId]=farmText;
				 optionHtml+="<option value='"+farmId+"' selected> "+farmText+" </option>";	
			}	 		
			 $("#vetassignFarmList").append(optionHtml);
		}else{
			$("#breadcrumb-subpage").html("Assign Farm");
			$("#cmsVetAssignTitle").html("Assign Farm");
		}
			
		var vetTextField= !!resData.userfname?resData.userfname:"";
		vetTextField+= !!resData.userlname?" "+resData.userlname:"";
		vetTextField+= !!resData.username?" ("+resData.username+")":"";
		$("#selectvet").val(vetTextField);
		$('#selectedVetId').val(vetid);
		$('#assignvetuserId').val(resData.id);
		$("#uniqueSynckey").val(jsonRes[0].uniquesynckey);
		
	};
	
	var getVetDtsForReassignFarm=function(vetId){
		
			var urlObj=CMSAdminCons.urls_info["getUserDetails"],
		 	reqData=jQuery.extend({}, CMSAdminCommon.getInitReqData());		
			reqData["reqData"]={"role_name":"VET","id":vetId};
			reqData=JSON.stringify(reqData);
		
			CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {
				if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
					if(jsonResData.status==="1"){						
						populateVetAssignDtls(jsonResData);
					}else{
						CMSMessage.showSubPageMsg(jsonResData);
					}		
				}
			}).fail(CMSCom.exceptionHandler.ajaxFailure);	
		
	};
	
	var showVetAssignFarmPageCallBackFn=function(subPageTitle,editId){
		$("#cms-vet-assign-edit").removeClass("hidden");
		$("#breadcrumb-subpage").html(subPageTitle);
		$("#cmsVetAssignTitle").html(subPageTitle);
		adjustScreen.contentHeight();
		$("#selectedVetId").val(0);
		$("#assignvetuserId").val(0);
		resetVetAssignFarm();
		if(!!editId&&editId!==0){
			$("#selectvet").prop('disabled', true);	
			getVetDtsForReassignFarm(editId);
		}else{
			$("#selectvet").prop('disabled', false);
		}
	};
	var showVetAssignFarmPage=function(subPageTitle,editId){
		var loadStatus= $("#cms-vet-assign-edit").data("load-status");
		if(!!loadStatus){
			showVetAssignFarmPageCallBackFn(subPageTitle,editId);
		}else{
			$("#cms-vet-assign-edit").load("partials/vet-assign-edit.html", function(response, status, xhr){
	            if(status === "success"){
	            	$("#cms-vet-assign-edit").data("load-status",true);
	            	showVetAssignFarmPageCallBackFn(subPageTitle,editId);
	            	setSelectVetTypeAHead();
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}   
	};
	
	
	var saveVetAssignFarm=function(e){
		
		var urlObj=CMSAdminCons.urls_info["saveVetFarm"],
			reqData=jQuery.extend({}, CMSAdminCommon.getInitReqData()),
			selectedVetId=$("#selectedVetId").val(),
			farmIds=[],
			saveVetAssignDts={}; 		
 		
		if(!selectedVetId||selectedVetId*1===0){
			$("#ipt-er-selectvet").show();
		}else if ($.isEmptyObject(CMSAdminCons.vetAssignFarms)){
			$("#ipt-er-vetassignfarmid").show();
		}else {
			for(var key in CMSAdminCons.vetAssignFarms){
				farmIds.push(key);
			}
			saveVetAssignDts["vetid"]=selectedVetId;	
			saveVetAssignDts["farmIds"]=farmIds;
			saveVetAssignDts["uniquesynckey"]=$("#uniqueSynckey").val();
			
			reqData["reqData"]=saveVetAssignDts;			
			reqData=JSON.stringify(reqData);
			
			CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {				
				if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
					if(jsonResData.status==="1"){
						
						var msg="Farms assigned to vet successfully";						
						window.location.hash = "vet-details";			
						resetVetAssignFarm();
						CMSMessage.showSubPageMsg({"status":1,"msg":msg});
					}else{
						CMSMessage.showSubPageMsg(jsonResData);
					}					
				}					
			}).fail(CMSCom.exceptionHandler.ajaxFailure);			
		}
		e.preventDefault();		
	};
	var resetVetAssignFarm=function(e){
		CMSAdminCons.vetAssignFarms={};
		var userId=$("#assignvetuserId").val();
		
		$("#selectedVetId").val("");
		$("#selectvet").val("");
		$("#vetassignFarmList").html("");
		$("#vetfarmListSelect").html("");
		$("#vetassignfarmcode").val("");
	
		$(".cms-ipt-error").hide();	
		$("#assignvetfarmdelbtn").hide();
		$("#selectvet").prop('disabled', false);

		if(!!userId&&userId*1!==0){
			getVetDtsForReassignFarm(userId);
		}
	};
	
	var vetApproveLinkHandler=function(){
		var vetid=$(this).data("vet-id"),
			vetChangeStatus	=$(this).data("change-status"),
			urlObj=CMSAdminCons.urls_info["approveVet"],
			reqData=jQuery.extend({}, CMSAdminCommon.getInitReqData());		
		reqData["reqData"]={"id":vetid,"vetStatus":vetChangeStatus*1};
		reqData=JSON.stringify(reqData);

		CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {
			if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
				if(jsonResData.status==="1"){
					var msg="";
					if(vetChangeStatus===1){
						msg="Vet approved successfully";
					}else{
						var msg="Vet unapproved successfully";
					}
					
					CMSMessage.showSubPageMsg({"status":1,"msg":msg});
					loadVetDataTable();
				}else{
					CMSMessage.showSubPageMsg(jsonResData);
				}		
			}
		}).fail(CMSCom.exceptionHandler.ajaxFailure);	

	};
	
	var vetEditAssignFarmLinkHandler=function(){
		var _this=$(this),
			vetId=_this.data("vet-id");
		window.location.hash = "edit-vet-farm="+vetId;	

	};
	var assignvetfarmdelbtnHandler=function(){
		
		$("#selectvet").val("");
		$('#selectedVetId').val(0);
		$("#selectvet").prop('disabled', false);
		$("#assignvetfarmdelbtn").hide();
		
	};
	return {
		showVetDetlsPage:showVetDetlsPage,
		showVetAssignFarmPage:showVetAssignFarmPage,
		saveVetAssignFarm:saveVetAssignFarm,
		showListfarm:showListfarm,
		addFarmSelectToListHandler:addFarmSelectToListHandler,
		removeFarmSelectHandler:removeFarmSelectHandler,
		resetVetAssignFarm:resetVetAssignFarm,
		vetApproveLinkHandler:vetApproveLinkHandler,
		vetEditAssignFarmLinkHandler:vetEditAssignFarmLinkHandler,
		assignvetfarmdelbtnHandler:assignvetfarmdelbtnHandler
	}
})();