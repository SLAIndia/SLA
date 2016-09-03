var VetChangePassword = (function(){
	
	var updatepassword=function(e){
		
		try{
			
		
		if(checkPasswordMatch()){
			
			var urlObj=CMSVetCons.urls_info["vetchangePassword"],
			chngvetPasswrdDtls={},
	 		reqData=jQuery.extend({}, CMSVetCommon.getInitReqData());
	 	
			chngvetPasswrdDtls["password"]=$("#vetOldpassword").val();
			chngvetPasswrdDtls["newpassword"]=$("#vetNewpassword").val();
	 		
	 		reqData["reqData"]=chngvetPasswrdDtls;			
			reqData=JSON.stringify(reqData);
			
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
		
		var loadStatus= $("#cms-vet-change-password-holder").data("load-status");
		if(!!loadStatus){
			callBackfunction();
		}else{
			$("#cms-vet-change-password-holder").load("partials/vet-change-password.html", function(response, status, xhr){
	            if(status === "success"){
	            	$("#cms-vet-change-password-holder").data("load-status",true);
	            	callBackfunction();
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}  
		
	};
	
	var callBackfunction = function(){
		$("#cms-vet-change-password-holder").removeClass("hidden");	
		
		$("#breadcrumb-subpage").html("Profile");
		
		
	}
	
   var checkPasswordMatch =function() {
		
	    var result=true;
	    	password = $("#vetNewpassword").val(),	    
	    	confirmPassword = $("#vetCnfrmpassword").val();
	    
	    if(!!password || !!confirmPassword || password.trim().lenght!==0 || confirmPassword.trim().lenght!==0){
	    	if (password != confirmPassword){
	    		CMSMessage.showSubPageMsg({"status":0,"msg":"Password does not match"});
	    		result=false;
	    		
	    	}
	    }
	    
	    return result;
	};
	
	function resetField(){
		$("#vetOldpassword").val("");
		$("#vetNewpassword").val("");
		$("#vetCnfrmpassword").val("");
		
		
	}
	
	return {
		updatepassword:updatepassword,
		callBackfunction:callBackfunction,
		checkPasswordMatch:checkPasswordMatch,
		loadPage:loadPage,
		resetField:resetField
	}
	
})();