var VetProfile = (function(){
	
	var loadvetProfile = function(){
			
			var loadStatus= $("#cms-vet-profile-holder").data("load-status");
			if(!!loadStatus){
				vetProfileProfileCallBackFn();
			}else{
				$("#cms-vet-profile-holder").load("partials/vet-profile-view-edit.html", function(response, status, xhr){
		            if(status === "success"){
		            	$("#cms-vet-profile-holder").data("load-status",true);
		            	vetProfileProfileCallBackFn();
		            }else{
		            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
		            }
		        }).error(CMSCom.exceptionHandler.ajaxFailure);
			}  
			
			
		}
	
	
	var vetProfileProfileCallBackFn = function(){
			
			$("#cms-vet-profile-holder").removeClass("hidden");	
			$("#breadcrumb-subpage").html("Profile");
			listProfiledetails();
			viewUserProfile();
			adjustScreen.contentHeight();
	};
	
	var listProfiledetails = function(){
		
		var urlObj=CMSVetCons.urls_info["getVetdetails"],
		reqData=jQuery.extend({}, CMSVetCommon.getInitReqData());	
	
		reqData["reqData"]={"id":CMSVetCommon.getUserId(),"role_name":"VET"};
		reqData=JSON.stringify(reqData);
		CMSCom.ajaxService.getJsonData(urlObj,reqData).done(function(jsonResData) {
			if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){	
					
						var resData=JSON.parse(jsonResData.resData),
						userdtls=resData[0].objUserEntity;
						$("#vetid").val(resData[0]["vetid"]);
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
						$("#vet-uniquesynckey").val(userdtls["uniquesynckey"]);
						
						$("#Owner-vet-createddt").val( userdtls["createddt"]);
						
						
			}	
		}).fail(CMSCom.exceptionHandler.ajaxFailure);		
		
		
	};
	
	var viewUserProfile = function(){
		$("#vet-Profile-form .form-dt").addClass("disableInputStyle");
		$("#vet-Profile-form .disablestar").hide();
		$("#titleVetProfile").html("Profile Details");
		$("#breadcrumb-subpage").html("Profile");
		$("#vet-EditProfileBtn").show();
		
		$("#vet-updateProfileBtn").addClass("hidden");
		$("#vet-resetProfileBtn").addClass("hidden");
		$("#vet-cancelProfileBtn").addClass("hidden");
		
		$("#vet-Profile-form .form-dt").prop("readonly",true);
		
		
	}
	
	
	var editVetprofile = function(){
		
		$("#vet-Profile-form .form-dt").removeClass("disableInputStyle");
		$("#vet-Profile-form .disablestar").show();
		$("#titleVetProfile").html("Edit Profile");
		$("#breadcrumb-subpage").html("Profile");
		$("#vet-EditProfileBtn").hide();
		
		$("#vet-updateProfileBtn").removeClass("hidden");
		$("#vet-resetProfileBtn").removeClass("hidden");
		$("#vet-cancelProfileBtn").removeClass("hidden");
		
		$("#vet-Profile-form .form-dt").prop("readonly",false);
		
	}
	
	var updateVetprofile = function(e){
		try{
		if($("#vet-Profile-form")[0].checkValidity()){	
			var urlObj=CMSVetCons.urls_info["editVetdetails"],
			editVetdetails={},
	 		reqData=jQuery.extend({}, CMSVetCommon.getInitReqData()),
	 		msg="";
			
			$("#vet-Profile-form .form-dt").each(function(){
				editVetdetails[$(this).prop("name")]=$(this).val();
			});
			
			$("#vet-Profile-form .form-dt-num").each(function(){
				editVetdetails[$(this).prop("name")]=$(this).val()*1;
			});
			
			reqData["reqData"]=editVetdetails;			
			reqData=JSON.stringify(reqData);
			if(!!checkEmail()){
				CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {				
					if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
						if(jsonResData.status*1===1){
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
	}catch (e) {}
		
	e.preventDefault();
		
	};
	
	var resetVetprofile = function(){
		listProfiledetails();
		
	};
	
	var cancelVetprofile = function(){
		resetVetprofile();
		viewUserProfile();
		
	};
	
	 var checkEmail = function(){
		 var result=false;
			try{
				    var checkString  = $("#useremail").val();
				    var reqData = {};
				    
				    reqData["email"] = checkString;
				    reqData["u_id"] = CMSVetCommon.getUserId();
				    reqData=JSON.stringify(reqData);	 
				    if(!!checkString){
				     	var urlObj=CMSVetCons.urls_info["checkEmailEdit"];
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
	
	
	
	return {
		vetProfileProfileCallBackFn:vetProfileProfileCallBackFn,
		loadvetProfile:loadvetProfile,
		editVetprofile:editVetprofile,
		resetVetprofile:resetVetprofile,
		cancelVetprofile:cancelVetprofile,
		updateVetprofile:updateVetprofile,
		checkEmail:checkEmail
	};
	
})();