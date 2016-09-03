var AdminOwner=(function(){
	
	var loadOwnerDataTable=function(){

		if(!!CMSAdminCons.dataTable["owner"].showFlg){
			CMSAdminCons.dataTable["owner"].dtFn._fnAjaxUpdate();
			CMSAdminCons.dataTable["owner"].dtFn.fnSetColumnVis( 0, false );			
		}else{	
			var	reqData=jQuery.extend({}, CMSAdminCommon.getInitReqData()),
				urlObj=CMSAdminCons.urls_info["getUserDetails"],
				req={"id": 0,"role_name" :"OWNER"};     
			
			reqData["reqData"]=req;				
			$.fn.dataTableExt.sErrMode = 'throw';
			CMSAdminCons.dataTable["owner"].dtFn=$('#ownerDataTable').dataTable({
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
			    	if(CMSCom.exceptionHandler.isLoginSessionExpire(dataJson)){
			    		if(dataJson.status==="1"){
			    			var jsonRes=JSON.parse(dataJson.resData);		
					    	for (var key in jsonRes){
					    		var eachObj=jsonRes[key].objUserEntity;					
					    		jsonRes[key][0] = eachObj.id;
					    		jsonRes[key][1] = !!eachObj.username?eachObj.username:"";		
					    		jsonRes[key][2] = !!eachObj.userfname?CMSCom.textFn.limitTooltip(eachObj.userfname,15):"";				    		    	
					    		jsonRes[key][3] = !!eachObj.userlocation? CMSCom.textFn.limitTooltip(eachObj.userlocation,15):"";
					    		var statusHtml="";
					    		if(eachObj.isactive===true){ 
					    			statusHtml="<i class='fa fa-circle fa-active'></i>"+"<span style='display:none;'>"+1+"</span";
					    		}else{
					    			statusHtml="<i class='fa fa-circle fa-inactive'></i>"+"<span style='display:none;'>"+0+"</span";
					    		}	
					    		jsonRes[key][4] = statusHtml;				    		
					    		var actionHtml="<a href='javascript:void(0);' class='owner-editLink' data-owner-id='"+eachObj.id+"'><i class='fa fa-pencil-square-o'></i> Edit </a> ";
					    		jsonRes[key][5] =actionHtml; 				    		
					    	} 
							setTimeout(function(){
								$( "#ownerDataTable" ).wrapAll( "<div class='table-responsive'></div>" );
							
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
			 var oSettings = CMSAdminCons.dataTable["owner"].dtFn.fnSettings();
		     oSettings._iDisplayLength = 10; 			     
		     CMSAdminCons.dataTable["owner"].dtFn.fnSetColumnVis( 0, false );
		    // CMSAdminCons.dataTable["owner"].showFlg=true;
		}	
	};
	var showOwnerDetlsPageCallBackFn=function(){
		$("#cms-owner-details").removeClass("hidden");	
		$("#breadcrumb-subpage").html("User Details");
		loadOwnerDataTable();
		adjustScreen.contentHeight();
	};
	var showOwnerDetlsPage=function(){

		var loadStatus= $("#cms-owner-details").data("load-status");
		if(!!loadStatus){
			showOwnerDetlsPageCallBackFn();
		}else{
			$("#cms-owner-details").load("partials/owner-list.html", function(response, status, xhr){
	            if(status === "success"){
	            	$("#cms-owner-details").data("load-status",true);
	            	showOwnerDetlsPageCallBackFn();
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}       
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
							if(!(farm["farm_id"] in CMSAdminCons.ownerAssignFarms)){
								farmSelectOptHtml+="<option value='"+farm["farm_id"]+"'>"+farm["farm_code"]+" - "+farm["farm_name"]+"</option>";
							}						
						}	
						$("#farmListSelect").html(farmSelectOptHtml);	
					}else{
						$("#farmListSelect").html("");
					}		
				}
			}).fail(CMSCom.exceptionHandler.ajaxFailure);
		}else{
			$("#farmListSelect").html("");
		}
	};
	
	var resetOwnerSaveFarm=function(e){
	
		var owneruserid=$("#owneruserid").val()*1;
		$("#cms-saveOwner-form .form-dt").each(function(){
			$(this).val("")
		});
		$("#cms-saveOwner-form .form-dt-onOff").each(function(){
			$(this).find(".btn-on-off").removeClass("on");
			$(this).find(".btn-on-off[data-val='true']").addClass("on");				
		});
		$("#farmListSelect").html("");
		$("#addedFarmList").html("");
		$("#farmcode").val("");
		$("#repassword").val("");
		$(".cms-ipt-error").hide();
		$("#owneruserid").val(0);
		$("#ownerid").val(0);
		CMSAdminCons.ownerAssignFarms={};
		if(!!owneruserid&&owneruserid!==0){
			getOwnerDtsForEdit(owneruserid);
		}
		$(".mCSB_container").css({"top":0});	
	};
	
	var saveOwner=function(e){		
		
		try{	

		if(($("#cms-saveOwner-form")[0].checkValidity()) && checkPasswordMatch() && checkEmail()){		
			
			var urlObj=CMSAdminCons.urls_info["saveOwner"],
				saveOwnerDts={},
		 		reqData=jQuery.extend({}, CMSAdminCommon.getInitReqData()),
		 		owneridEl=$("#ownerid"),
		 		owneruseridEl=$("#owneruserid"),
		 		saveMsgEl=$("#saveMsg"),
		 		msg="";
		 		ownerid=owneridEl.val()*1,
		 		owneruserid=owneruseridEl.val()*1,
		 		ownerCreateddt=$("#ownerCreateddt").val();
			
			$("#cms-saveOwner-form .form-dt").each(function(){
				saveOwnerDts[$(this).prop("name")]=$(this).val();
			});
			$("#cms-saveOwner-form .form-dt-onOff").each(function(){
				saveOwnerDts[$(this).data("input-name")]=$(this).find(".btn-on-off.on").data("val")+"";
			});			
			
			if ($.isEmptyObject(CMSAdminCons.ownerAssignFarms)){
				$("#ipt-er-assignfarmid small").html("Assign Farm");
				$("#ipt-er-assignfarmid").show();
			}else{
				var farmIds=[];
				for(var key in CMSAdminCons.ownerAssignFarms){
					farmIds.push(key);
				}
				if(!!ownerid&&ownerid!==0){
					saveOwnerDts["ownerid"]=ownerid;
				}
				if(!!owneruserid&&owneruserid!==0){
					saveOwnerDts["id"]=owneruserid;
				}	
				if(!ownerCreateddt){
					delete saveOwnerDts["createddt"];
				}
				
				saveOwnerDts["farmIds"]=farmIds;			
				saveOwnerDts["role_name"]="owner";			
				reqData["reqData"]=saveOwnerDts;			
				reqData=JSON.stringify(reqData);
				
				CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {				
					if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
						if(jsonResData.status==="1"){
							
							var resData=JSON.parse(jsonResData.resData);							
							
							msg=ownerid===0?"Owner successfully saved ":"Owner successfully updated  ";
							window.location.hash = "user-details";			
							owneridEl.val(0);
							owneruseridEl.val(0);
							resetOwnerSaveFarm();
							CMSMessage.showSubPageMsg({"status":1,"msg":msg});
						}else{
							CMSMessage.showSubPageMsg(jsonResData);
						}					
					}					
				}).fail(CMSCom.exceptionHandler.ajaxFailure);	 
			}
		}
		}catch(er){console.log("er ",er)}
		e.preventDefault();
		
	};
	
	var populateOwnerDtls=function(jsonResData){	
		if(!!jsonResData){
			if("resData" in jsonResData ){
				var resData=JSON.parse(jsonResData.resData),
					userdtls=resData[0].objUserEntity,
					farmIds= "";
			
				if(!!resData[1]&&!!resData[1].farmIds){
					farmIds=resData[1].farmIds;
				}

				$("#cms-saveOwner-form .form-dt").each(function(){				
					$(this).val(userdtls[$(this).prop("name")]);
				});
				$("#cms-saveOwner-form .form-dt-onOff").find(".btn-on-off").removeClass("on");
				$("#cms-saveOwner-form .form-dt-onOff").each(function(){				
					$(this).find(".btn-on-off[data-val='"+userdtls[$(this).data("input-name")]+"']").addClass("on");
				});
				$("#ownerid").val(resData[0].id);
				$("#owneruserid").val(userdtls.id);
				var optionHtml="";
				if(!!farmIds){
					for(var key in farmIds){
						var farmText=farmIds[key][2]+" - "+farmIds[key][1],
							farmId=farmIds[key][0];
						 CMSAdminCons.ownerAssignFarms[farmId]=farmText;
						 optionHtml+="<option value='"+farmId+"' selected> "+farmText+" </option>";	
					}	 
			
					 $("#addedFarmList").append(optionHtml);
				}
				  adjustScreen.contentHeight();
			}				
		}				
	};
	
	var getOwnerDtsForEdit=function(ownerId){
		var urlObj=CMSAdminCons.urls_info["getUserDetails"],
	 	reqData=jQuery.extend({}, CMSAdminCommon.getInitReqData());		
		reqData["reqData"]={"role_name":"OWNER","id":ownerId};
		reqData=JSON.stringify(reqData);
	
		CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {
			if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
				if(jsonResData.status==="1"){
					populateOwnerDtls(jsonResData);
				}else{
					CMSMessage.showSubPageMsg(jsonResData);
				}		
			}
		}).fail(CMSCom.exceptionHandler.ajaxFailure);	
	};
	

	var showOwnerAddOrUpdatePageCallBackFn=function(subPageTitle,editId){
		$("#cms-add-owner").removeClass("hidden");
		$("#breadcrumb-subpage").html("User Details");
		$("#cmsOwnerSaveTitle").html(subPageTitle);
		adjustScreen.contentHeight();
		$("#owneruserid").val(0);
		$("#ownerid").val(0);
		resetOwnerSaveFarm();
		if(!!editId&&editId!==0){
			//$("#breadcrumb-subpage").html("Edit Owner");
			getOwnerDtsForEdit(editId);
			
			$("#username").prop('disabled', true);
			
			$("#password").prop("disabled", true); 
			$("#repassword").prop("disabled", true); 
			$("#passwordHolder").hide();
		}else{
			var passpordPattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}";
			
			$("#username").prop('disabled', false);			
			$("#password").prop("disabled", false); 
			$("#repassword").prop("disabled", false); 
			$("#passwordHolder").show();
		}
		  adjustScreen.contentHeight();
		
	};
	var showOwnerAddOrUpdatePage=function(subPageTitle,editId){
		var loadStatus= $("#cms-add-owner").data("load-status");
		if(!!loadStatus){
			showOwnerAddOrUpdatePageCallBackFn(subPageTitle,editId);
		}else{
			$("#cms-add-owner").load("partials/owner-add-update.html", function(response, status, xhr){
	            if(status === "success"){
	            	$("#cms-add-owner").data("load-status",true);
	            	showOwnerAddOrUpdatePageCallBackFn(subPageTitle,editId);
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}   
	};
	
	
	var checkPasswordMatch = function() {
			
	    var result=true;
	    	password = $("#password").val(),	    
	    	confirmPassword = $("#repassword").val(),
	    	owneridEl=$("#ownerid"),
	 		ownerid=owneridEl.val()*1;
		    	
		    if(!ownerid||ownerid===0){
		    	
			    if(!!password || !!confirmPassword || password.trim().lenght!==0 || confirmPassword.trim().lenght!==0){
			    	if (password != confirmPassword){
			    		CMSMessage.showSubPageMsg({"status":0,"msg":"Password does not match"});
			    		result=false;
			    		
			    	}
			    }
		    }
		    
		    return result;
		};
		
	
	var checkUserName = function(e){
		e.stopPropagation()
		var uname=$(this).val();
		if(!!uname&&uname.trim().length!==0){
			var urlObj = CMSAdminCons.urls_info["checkUserExist"],
			userdetails={};
			userdetails["username"]=uname;
			userdetails=JSON.stringify(userdetails);
			
			CMSCom.ajaxService.sentJsonData(urlObj,userdetails).done(function(jsonResData) {
				if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){			
					if(jsonResData.status ===-1){		
						$.webshims.validityAlert.showFor( $("#username"),jsonResData.msg ); 
					}
				}
			
			}).fail(CMSCom.exceptionHandler.ajaxFailure);	
		}
		
	}
	
	var addFarmSelectToListHandler=function(e){
	
		var optionHtml="";
		var selected=[];
		$('#farmListSelect :selected').each(function(i){ 		
			var _this=$(this),
				val=_this.val(),
				text=_this.text();
			 selected[val]=text;	
			 optionHtml+="<option value='"+val+"' selected>"+text+"</option>";	
			 CMSAdminCons.ownerAssignFarms[val]=text;
			_this.remove();
		});
		$(".cms-ipt-error").hide();
		$("#addedFarmList").append(optionHtml);
		
	};
	var removeFarmSelectHandler=function(e){

		var optionHtml="";
		$('#addedFarmList :selected').each(function(i){ 		
			var _this=$(this),
				val=_this.val(),
				text=_this.text();
			delete CMSAdminCons.ownerAssignFarms[val];
			 optionHtml+="<option value='"+val+"'>"+text+"</option>";
			_this.remove();
		});
		$("#farmListSelect").append(optionHtml);
	
	};

	var saveOwnerBtnHandler=function(e){		
		$(".mCSB_container").css({"top":0});			
	};

	var editLinkHandler=function(){
		var ownerId=$(this).data("owner-id");
		window.location.hash = "edit-owner="+ownerId;	
		//window.publicModule.showPage("edit-owner",ownerId);			
	}; 
	 var checkEmail = function(){
			try{
				    var checkString  = $("#owneremail").val();
				    var reqData = {},
				    result=false;
				    var userId = $("#owneruserid").val();
				    reqData["email"] = checkString;
				    reqData["u_id"] = userId;
				    reqData=JSON.stringify(reqData);	 
				    if(!!checkString){
				     	var urlObj=CMSAdminCons.urls_info["checkEmailEdit"];
				     	CMSCom.ajaxService.getJsonDataAsyncFalse(urlObj,reqData).done(function(jsonResData) {	
				     		if(jsonResData.status === -1){
				     			CMSMessage.showSubPageMsg(jsonResData);
								$("#owneremail").focus();
							}else{
								result=true;
							}		
					}).fail(CMSCom.exceptionHandler.ajaxFailure);
				    }
				return result;
			}  catch (er) {
			}
		  };
	return {
		showOwnerDetlsPage:showOwnerDetlsPage,
		showOwnerAddOrUpdatePage:showOwnerAddOrUpdatePage,
		saveOwner:saveOwner,
		resetOwnerSaveFarm:resetOwnerSaveFarm,
		showListfarm:showListfarm,
		addFarmSelectToListHandler:addFarmSelectToListHandler,
		removeFarmSelectHandler:removeFarmSelectHandler,
		saveOwnerBtnHandler:saveOwnerBtnHandler,
		checkUserName:checkUserName,
		editLinkHandler:editLinkHandler
	}
	
})();

