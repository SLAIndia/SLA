var OwnerChangepassword = (function(){
	
	var updatepassword=function(e){
	
		try{
			
		
		if(checkPasswordMatch()){
			
			var urlObj=CMSOwnerCons.urls_info["changePassword"],
			chngPasswrdDtls={},
	 		reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData());
	 	
	 		chngPasswrdDtls["password"]=$("#ownerOldpassword").val();
	 		chngPasswrdDtls["newpassword"]=$("#ownerNewpassword").val();
	 		
	 		reqData["reqData"]=chngPasswrdDtls;			
			reqData=JSON.stringify(reqData);
			console.log(" UPDATE -------------")
			CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {				
				if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
					if(jsonResData.status==="1"){
						CMSMessage.showSubPageMsg({"status":1,"msg":"Successfully changed"});
						resetField();
						
					}
					else{
						CMSMessage.showSubPageMsg({"status":0,"msg":"Old Password entered is incorrect"});
						resetField();
					}	
				}
			}).fail(CMSCom.exceptionHandler.ajaxFailure);	
			
		}
	}catch (e) {
		console.log(e)
	}
	e.preventDefault();
	};
	
	
	
	var loadPage = function(){
		
		var loadStatus= $("#owner-change-password").data("load-status");
		if(!!loadStatus){
			callBackfunction();
		}else{
			$("#owner-change-password").load("partials/owner-change-password.html", function(response, status, xhr){
	            if(status === "success"){
	            	$("#owner-change-password").data("load-status",true);
	            	callBackfunction();
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}  
		
	};
	
	var callBackfunction = function(){
		$("#owner-change-password").removeClass("hidden");	
		$("#breadcrumb-subpage").html("Change Password");
		
		adjustScreen.contentHeight();
	}
	
	var checkPasswordMatch = function() {
		
	    var result=true;
	    	password = $("#ownerNewpassword").val(),	    
	    	confirmPassword = $("#ownerCnfrmpassword").val();
	    
	    if(!!password || !!confirmPassword || password.trim().lenght!==0 || confirmPassword.trim().lenght!==0){
	    	if (password != confirmPassword){
	    		CMSMessage.showSubPageMsg({"status":0,"msg":"Password does not match"});
	    		result=false;
	    	}
	    }
	    
	    return result;
	};
	
	function resetField(){
		$("#ownerOldpassword").val("");
		$("#ownerNewpassword").val("");
		$("#ownerCnfrmpassword").val("");
		
		
	}
	
	return{
		loadPage:loadPage,
		callBackfunction:callBackfunction,
		checkPasswordMatch:checkPasswordMatch,
		updatepassword:updatepassword,
		resetField:resetField
	};
	
})();