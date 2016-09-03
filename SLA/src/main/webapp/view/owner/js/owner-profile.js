var OwnerProfile=(function(){
	
	var fetchProfileDetails=function(){
		var urlObj=CMSOwnerCons.urls_info["getOwnerdetails"],
		reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData());	
	
		reqData["reqData"]={"id":CMSOwnerCom.getUserId(),"role_name":"OWNER"};
		reqData=JSON.stringify(reqData);
		CMSCom.ajaxService.getJsonData(urlObj,reqData).done(function(jsonResData) {
			if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){	
					
						var resData=JSON.parse(jsonResData.resData),
						userdtls=resData[0].objUserEntity;;
						
						$("#userfname").val(userdtls["userfname"]);
						$("#userlname").val(userdtls["userlname"]);
						$("#useremail").val(userdtls["useremail"]);
						$("#userphone").val(userdtls["userphone"]);
						$("#userlocation").val(userdtls["userlocation"]);
						$("#useraddress").val(userdtls["useraddress"]);
						$("#id").val(userdtls["id"]);
						$("#ownerid").val(resData[0].id);
						$("#username").val(userdtls["username"]);
						$("#password").val(userdtls["password"]);
						$("#isactive").val(userdtls["isactive"]);
						$("#uniquesynckey").val(userdtls["uniquesynckey"]);
						
						$("#Owner-profile-createddt").val( CMSCom.dateFn.cusDateAsDD_MM_YYYY_TS(new Date(userdtls["createddt"])));
			}	
		}).fail(CMSCom.exceptionHandler.ajaxFailure);		
	
	};
	
	var viewownerProfileCallBackFn=function(){
		$("#view-user-profile").removeClass("hidden");	
		$("#breadcrumb-subpage").html("Profile");
		fetchProfileDetails();
		viewUserProfile();		
		adjustScreen.contentHeight();
	};
	
	var loadPage=function(subPageTitle,editId){
		var loadStatus= $("#view-user-profile").data("load-status");
		if(!!loadStatus){
			viewownerProfileCallBackFn(subPageTitle,editId);
		}else{
			$("#view-user-profile").load("partials/owner-profile-view-edit.html", function(response, status, xhr){
	            if(status === "success"){
	            	$("#view-user-profile").data("load-status",true);
	            	viewownerProfileCallBackFn(subPageTitle,editId);
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}  
	};
	
	var editUserProfile = function(){
	
		$("#cms-viewOwner-form .form-dt").removeClass("disableInputStyle");
		$("#cms-viewOwner-form .disablestar").show();
		$("#cmsViewUserProfile").html("Edit Profile");
		$("#cms-EditProfileBtn").hide();
		
		$("#cms-updateProfileBtn").removeClass("hidden");
		$("#cms-resetProfileBtn").removeClass("hidden");
		$("#cms-cancelProfileBtn").removeClass("hidden");
		
		$("#cms-viewOwner-form .form-dt").prop("readonly",false);
		
		
	};
	
	var viewUserProfile = function(){
		$("#cms-viewOwner-form .form-dt").addClass("disableInputStyle");
		$("#cms-viewOwner-form .disablestar").hide();
		$("#cmsViewUserProfile").html("Profile Details");
		$("#cms-EditProfileBtn").show();
		
		$("#cms-updateProfileBtn").addClass("hidden");
		$("#cms-resetProfileBtn").addClass("hidden");
		$("#cms-cancelProfileBtn").addClass("hidden");
		
		$("#cms-viewOwner-form .form-dt").prop("readonly",true);
		
		
	}
	
	var UpdateUserProfile= function(e){
		
		try{
			
		
		if($("#cms-viewOwner-form")[0].checkValidity()){	
		
			var urlObj=CMSOwnerCons.urls_info["editUserdetails"],
			editOwnerdtls={},
	 		reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData()),
	 		useridEl=$("#id"),
	 		ownerId=$("#ownerid"),
	 		saveMsgEl=$("#saveMsg"),
	 		msg="";
		
			$("#cms-viewOwner-form .form-dt").each(function(){
				editOwnerdtls[$(this).prop("name")]=$(this).val();
			});
			
			$("#cms-viewOwner-form .form-dt-num").each(function(){
				editOwnerdtls[$(this).prop("name")]=$(this).val();
			});
			if(!!checkEmail()){
				reqData["reqData"]=editOwnerdtls;			
				reqData=JSON.stringify(reqData);
		
				CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {				
					if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
						if(jsonResData.status==="1"){
							CMSMessage.showSubPageMsg({"status":1,"msg":"Profile updated"});
							viewUserProfile();
						
						}
						else{
						CMSMessage.showSubPageMsg(jsonResData);
					}	
				}
			}).fail(CMSCom.exceptionHandler.ajaxFailure);	
			}
		}
		}catch(er){}
		e.preventDefault();
		
		
	};
	
	var cancelProfile = function(){
		fetchProfileDetails();
		viewUserProfile();
		
	}
	var resetUserProfile = function(){

		fetchProfileDetails();
		
		
	};
	
	 var checkEmail = function(){
		 var result=false;
			try{
				    var checkString  = $("#useremail").val();
				    var reqData = {};
				    
				    reqData["email"] = checkString;
				    reqData["u_id"] = CMSOwnerCom.getUserId();
				    reqData=JSON.stringify(reqData);	 
				    if(!!checkString){
				     	var urlObj=CMSOwnerCons.urls_info["checkEmailEdit"];
				     	CMSCom.ajaxService.getJsonDataAsyncFalse(urlObj,reqData).done(function(jsonResData) {	
				     		if(jsonResData.status === -1){
									CMSMessage.showSubPageMsg(jsonResData);
									$("#useremail").focus();
								
							}else{
								result= true;
							}	
					}).fail(CMSCom.exceptionHandler.ajaxFailure);
				    }
			}  catch (er) {
				// TODO: handle exception
			}
			return result;
		  };
	
	
	return{
		viewownerProfileCallBackFn:viewownerProfileCallBackFn,
		loadPage:loadPage,
		editUserProfile:editUserProfile,
		UpdateUserProfile:UpdateUserProfile,
		viewUserProfile:viewUserProfile,
		resetUserProfile:resetUserProfile,
		cancelProfile:cancelProfile,
		checkEmail:checkEmail
		
		
		}
	
})();