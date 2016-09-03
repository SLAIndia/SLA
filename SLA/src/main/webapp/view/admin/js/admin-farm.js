var AdminFarm=(function(){
	"use strict";	
	// This is to load farm list 
	var loadFarmDataTable=function(){

		if(!!CMSAdminCons.dataTable["farm"].showFlg){
			CMSAdminCons.dataTable["farm"].dtFn._fnAjaxUpdate();
			CMSAdminCons.dataTable["farm"].dtFn.fnSetColumnVis( 0, false );			
		}else{	
			var	reqData=jQuery.extend({}, CMSAdminCommon.getInitReqData()),
				urlObj=CMSAdminCons.urls_info["farmDetails"];
			
			reqData["reqData"]=JSON.stringify({});			
			$.fn.dataTableExt.sErrMode = 'throw';
			CMSAdminCons.dataTable["farm"].dtFn=$('#farmDataTable').dataTable({
			  "ajax": {
			    "url": urlObj.url,
			    "type": urlObj.type,
			    "data":reqData,
			    "dataSrc": function (dataJson) {	 			    	
			    	if(CMSCom.exceptionHandler.isLoginSessionExpire(dataJson)){
			    		if(dataJson.status==="1"){
				    		var jsonRes=JSON.parse(dataJson.resData);				     
					    	for (var key in jsonRes){
					    		var eachObj=jsonRes[key];
					    		jsonRes[key][0] = eachObj.farm_id;
					    		jsonRes[key][1] = eachObj.farm_code;
					    			
					    		jsonRes[key][2] =  CMSCom.textFn.limitTooltip(eachObj.farm_name,15);
					    		jsonRes[key][3] = CMSCom.textFn.limitTooltip(eachObj.farm_location,15);
					    		var statusHtml="";
					    		if(eachObj.farm_status===true){
					    			statusHtml="<i class='fa fa-circle fa-active'></i>"+"<span style='display:none;'>"+1+"</span";
					    		}else{
					    			statusHtml="<i class='fa fa-circle fa-inactive'></i>"+"<span style='display:none;'>"+0+"</span";
					    		}	
					    		jsonRes[key][4] = statusHtml;				    		
					    		var actionHtml="<a href='javascript:void(0);' class='farm-editLink' data-farm-id='"+eachObj.farm_id+"'><i class='fa fa-pencil-square-o'></i> Edit</a> ";				    		
					    		jsonRes[key][5] =actionHtml; 
					    		
					    	} 
							setTimeout(function(){
								$( "#farmDataTable" ).wrapAll( "<div class='table-responsive'></div>" );
								
							},100)
						    return jsonRes;	
			    		}else{
			    			return [];
			    		}
				    }	
			    }
			  },
			  destroy: true,
			  "bStateSave": true,
			  "aaSorting": [[ 0, "desc" ]],
			  "aoColumnDefs": [
			       { 'bSortable': false, 'aTargets': [ 5 ] }
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
			 var oSettings = CMSAdminCons.dataTable["farm"].dtFn.fnSettings();
		     oSettings._iDisplayLength = 10; 			     
		     CMSAdminCons.dataTable["farm"].dtFn.fnSetColumnVis( 0, false );
		   //  CMSAdminCons.dataTable["farm"].showFlg=true;
		}	
	};
	
	// this fn is to invoke after farm page load
	var showFarmDetlsPageCallBackFn=function(){
		$("#cms-farm-list").removeClass("hidden");	
		$("#breadcrumb-subpage").html("Farm Details");
		AdminFarm.loadFarmDataTable();
		adjustScreen.contentHeight();
	};
	
	// this is to load farm detls page 
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

	// this fn is to invoke after farm save or update page  load
	var showFarmAddOrUpdatePageCallBackFn=function(subPageTitle,editId){
		$("#cms-add-farm").removeClass("hidden");
		$("#breadcrumb-subpage").html("Farm Details");
		$("#cmsfarmSaveTitle").html(subPageTitle);
		$("#farmid").val(0);
		resetSaveFarm();
		if(!!editId){
			getfarmDtsForEdit(editId);
		}		
		adjustScreen.contentHeight();
	};
	
	// this is to load farm save or update page 
	var showFarmAddOrUpdatePage=function(subPageTitle,editId){
		var loadStatus= $("#cms-add-farm").data("load-status");
		if(!!loadStatus){
			showFarmAddOrUpdatePageCallBackFn(subPageTitle,editId);
		}else{
			$("#cms-add-farm").load("partials/farm-add-update.html", function(response, status, xhr){
	            if(status === "success"){
	            	$("#cms-add-farm").data("load-status",true);
	            	showFarmAddOrUpdatePageCallBackFn(subPageTitle,editId);
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}   
	};
	
	// This is to save  farm save or update form
	var saveFarm=function(e){

		if($("#cms-saveFarm-form")[0].checkValidity()){		
			
			var urlObj=CMSAdminCons.urls_info["saveFarm"],
				saveFarmDts={},
		 		reqData=jQuery.extend({}, CMSAdminCommon.getInitReqData()),
		 		farmidEl=$("#farmid"),
		 		farmCreateddt=$("#farm-createddt").val(),
		 		saveMsgEl=$("#saveMsg"),
		 		msg="",
		 		farmid=farmidEl.val()*1;
			
			$("#cms-saveFarm-form .form-dt").each(function(){
				saveFarmDts[$(this).prop("name")]=$(this).val();
			});
			$("#cms-saveFarm-form .form-dt-onOff").each(function(){
				saveFarmDts[$(this).data("input-name")]=$(this).find(".btn-on-off.on").data("val")+"";
			});	
	
			if(!farmCreateddt){
				delete saveFarmDts["createddt"];
			}

			saveFarmDts["farmid"]=farmid;	
			reqData["reqData"]=saveFarmDts;			
			reqData=JSON.stringify(reqData);
			
			CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {				
				if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
					if(jsonResData.status==="1"){
						var resData=JSON.parse(jsonResData.resData);	
						
						farmidEl.val(0);
						resetSaveFarm();						
						msg=farmid===0?"Farm successfully saved ":"Farm successfully updated  ";					
						window.location.hash = "farm-details";								
						
						CMSMessage.showSubPageMsg({"status":1,"msg":msg});
					}else{
						CMSMessage.showSubPageMsg(jsonResData);
					}					
				}					
			}).fail(CMSCom.exceptionHandler.ajaxFailure);	 
		}
		e.preventDefault();
		
	};
	
	// This is to reset farm save or update form
	var resetSaveFarm=function(){		
		var farmid=$("#farmid").val()*1;
		$("#cms-saveFarm-form .form-dt").each(function(){
			$(this).val("");	
		});		
		$("#cms-saveFarm-form .form-dt-onOff").each(function(){
			$(this).find(".btn-on-off").removeClass("on");
			$(this).find(".btn-on-off[data-val='true']").addClass("on");				
		});
		if(!!farmid&&farmid!==0){	// For farm edit mode 
			getfarmDtsForEdit(farmid);
		}		
	};	
	
	// This is to populate farm dtls for edit
	var populateFarmDtls=function(jsonResData){	
		if(!!jsonResData){
			if("resData" in jsonResData ){
				var formData=JSON.parse(jsonResData.resData);
				
				$("#cms-saveFarm-form .form-dt").each(function(){				
					$(this).val(formData[$(this).prop("name")]);
				});
				$("#cms-saveFarm-form .form-dt-onOff").find(".btn-on-off").removeClass("on");
				$("#cms-saveFarm-form .form-dt-onOff").each(function(){				
					$(this).find(".btn-on-off[data-val='"+formData[$(this).data("input-name")]+"']").addClass("on");
				});					
			}				
		}				
	};	
	
	// This is to fetch farm details for edit
	var getfarmDtsForEdit=function(farmId){
		var urlObj=CMSAdminCons.urls_info["getFarm"],
	 	reqData=jQuery.extend({}, CMSAdminCommon.getInitReqData());		
		reqData["reqData"]={"farmid":farmId};
		reqData=JSON.stringify(reqData);
	
		CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {
			if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
				if(jsonResData.status==="1"){
					populateFarmDtls(jsonResData);
				}else{
					CMSMessage.showSubPageMsg(jsonResData);
				}		
			}
		}).fail(CMSCom.exceptionHandler.ajaxFailure);	
	};
	
	// This is to handler for farm edit
	var editLinkHandler=function(){
		var farmId=$(this).data("farm-id");
		window.location.hash = "edit-farm="+farmId;			
	}; 
	
	return {
		showFarmDetlsPage:showFarmDetlsPage,
		showFarmAddOrUpdatePage:showFarmAddOrUpdatePage,
		saveFarm:saveFarm,
		resetSaveFarm:resetSaveFarm,
		editLinkHandler:editLinkHandler,
		loadFarmDataTable:loadFarmDataTable
		
	};	

})();