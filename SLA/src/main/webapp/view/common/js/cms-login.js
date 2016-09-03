var CMSLoginCom=(function(){
	var getInitReqData=function(){		
		var reqData={};		
		reqData["u_id"]=sessionStorage.getItem("u_id");
		reqData["signature"]=sessionStorage.getItem("signature");	
		return reqData;
	};
	var getUserId=function(){
		return sessionStorage.getItem("u_id");
	};
	var getCMSSignature=function(){
		return sessionStorage.getItem("signature");
	};
	
	return {
		getInitReqData:getInitReqData,
		getUserId:getUserId,
		getCMSSignature:getCMSSignature,
		
	}
})();




(function() {
	
	var loginCons={
			urls_info:{	
				userlogin:{
					url:contextPath+"../login",
					contentType:'application/json',
				    dataType: "json",
					type:"POST"
				},
				forgotPassword:{
					url:contextPath+"../admin/forgotPassword",
					contentType:'application/json',
				    dataType: "json",
					type:"POST"
				},
				resetPassword:{
					url:contextPath+"../admin/resetPassword",
					contentType:'application/json',
				    dataType: "json",
					type:"POST"
					
				}
				
			}
	};
	

	
	var CMSLoginService=(function(){
		
		 
		var validEmailChecker = function(e){
			
		};
		
		
		var forgotPasswordHandler=function(e){
			try{
				
			if(($("#forgotPasswordForm")[0].checkValidity())){
				$(".preLoader").removeClass("hidden");
				var email = $("#forgotPassword").val();
				var urlobj = loginCons.urls_info["forgotPassword"];
				reqData={};
				reqData["useremail"] = email;
				reqData=JSON.stringify(reqData);
				console.log("Request",reqData);
				
				CMSCom.ajaxService.sentJsonData(urlobj,reqData).done(function(jsonResData) {				
					if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
						var msgObj;
						if(jsonResData.status==="1"){
							
							
							msgObj={"success":1,"msg":"Email send"};
							
						}
						else if (jsonResData.status==="14"){
							msgObj={"success":0,"msg":"Please Use this feature from your Desktop application"};
						
						}	
						else{
							msgObj={"success":0,"msg":"No User with this email id"};
							
						}
						CMSMessage.showCommonModalMsg(msgObj,"#ForgotModal");
						$(".preLoader").addClass("hidden");
					}
				}).fail(CMSCom.exceptionHandler.ajaxFailure);	
				
				 
			}
			}catch (e) {
				console.log(Error,e);
			}
			e.preventDefault();
		};
		$('#ForgotModal').on('hidden.bs.modal', function (e) {
			$("#forgotPassword").val("");
		})
		
		var checkPasswordMatch = function() {
			
		    var result=true;
		    	password = $("#resetPasswordText").val(),	    
		    	confirmPassword = $("#cnfrmPasswordText").val();
		    
		    if(!!password || !!confirmPassword || password.trim().lenght!==0 || confirmPassword.trim().lenght!==0){
		    	if (password != confirmPassword){
		    		CMSMessage.showSubPageMsg({"status":0,"msg":"Password does not match"});
		    		result=false;
		    		
		    	}
		    }
		    
		    return result;
		};
		
		
		
		var loginResponseHandler=function(jsonResData){
			
			if(!!jsonResData){
				var status =jsonResData.status;
				
				
				if(status==="5"){
					var resData=JSON.parse(jsonResData.resData),
					signature=jsonResData.signature;
					sessionStorage.setItem("signature", signature);
					sessionStorage.setItem("u_id", resData.user_id);
					$("#resetPassword").modal("show");
					
					
				}else	if(status==="0"){
					$("#cms-userlogin-error").html(""+jsonResData.msg);
					$("#cms-userlogin-error").parent().removeClass('hidden');
				}
				else{
					var resData=JSON.parse(jsonResData.resData),
						signature=jsonResData.signature,
						redirectPage="login.html";	
					
					if(resData["user_role_id"]===1){
						CMSStorage.setStorage("admin_u_id", resData.user_id);
						CMSStorage.setStorage("admin-signature", signature);
						CMSStorage.setStorage("admin-username", resData.user_name);
						CMSStorage.setStorage("admin_role_id", resData.user_role_id);						

						redirectPage="admin/index.html";
					}else if(resData["user_role_id"]===2){
						CMSStorage.setStorage("owner-signature", signature);
						CMSStorage.setStorage("owner_u_id", resData.user_id);
						CMSStorage.setStorage("owner-username", resData.user_name);
						CMSStorage.setStorage("user_role_id", resData.user_role_id);
						redirectPage="owner/index.html";
					}else if(resData["user_role_id"]===3){
						CMSStorage.setStorage("vet-signature", signature);
						CMSStorage.setStorage("vet_u_id", resData.user_id);
						CMSStorage.setStorage("vet-username", resData.user_name);
						CMSStorage.setStorage("user_role_id", resData.user_role_id);
						redirectPage="vet/index.html";
					}
				 	window.location.href=redirectPage;					
				}				
			}			
		};
		
		var resetPassword = function(e){
			try{
				
				if(($("#resetPasswordForm")[0].checkValidity()) && checkPasswordMatch() ){
					
					var password = $("#resetPasswordText").val();
					var urlobj = loginCons.urls_info["resetPassword"],
					reqData=jQuery.extend({}, CMSLoginCom.getInitReqData());
					details={};
					details["newpassword"]=password;
					reqData["reqData"]=details;
					reqData=JSON.stringify(reqData);
					
					CMSCom.ajaxService.sentJsonData(urlobj,reqData).done(function(jsonResData) {				
							var msgObj;
							if(jsonResData.status==="1"){
								
								msgObj={"success":1,"msg":"Password Successfully changed"};
								//$("#resetPassword").modal("hide");
								
							}
							else{
								msgObj={"success":0,"msg":"No user with this mail ID"};
							
							}	
							CMSMessage.showCommonModalMsg(msgObj,"#resetPassword");
						
					}).fail(CMSCom.exceptionHandler.ajaxFailure);	
					
					
					 
				}else{
					msgObj={"success":0,"msg":"Password's are not matching"};
					CMSMessage.showCommonModalMsg(msgObj,"#resetPassword");
				}
				
			}catch (er) {
				// TODO: handle exception
				console.log(Error,er);
				e.preventDefault();
			}
			e.preventDefault();
		};
		var userLoginHandler=function(e){		
			
			if($("#cms-userloginform")[0].checkValidity()){				
				var userLoginDts={};			
				userLoginDts["username"]=$("#cms-userlogin-username").val(),
				userLoginDts["password"]=$("#cms-userlogin-password").val();			
				userLoginDts=JSON.stringify(userLoginDts);		
				var urlObj=loginCons.urls_info["userlogin"];
				
				CMSCom.ajaxService.sentJsonData(urlObj,userLoginDts).done(function(jsonResData) {
					loginResponseHandler(jsonResData);	 	
				}).fail(CMSCom.exceptionHandler.ajaxFailure);	 
			}
			e.preventDefault();
		};
		
		return {
			userLoginHandler:userLoginHandler,
			forgotPasswordHandler:forgotPasswordHandler,
			resetPassword:resetPassword
		}
	})();
	
	var _root, CMSLogin = {			
			windowResizeAction:function(){				
				adjustScreen.contentHeight();
			},
		el : {
			body:"body",
			window:$(window),
			cmsuserloginbtn:$("#cms-userloginbtn"),
			cmsuserloginform:$("#cms-userloginform"),
			forgotPasswordBtn:"#forgotPassword",
			forgotPasswordForm:$("#forgotPasswordForm"),
			resetPasswordForm:$("#resetPasswordForm")
		},
		setEvents : function() {
			_root.el.window.on('resize', _root.windowResizeAction);	
			_root.el.cmsuserloginform.submit(CMSLoginService.userLoginHandler);
			_root.el.forgotPasswordForm.on("submit",_root.el.forgotPasswordForm,CMSLoginService.forgotPasswordHandler);
			_root.el.resetPasswordForm.on("submit",CMSLoginService.resetPassword);
			//_root.el.resetPasswordForm.on("keyup",_root.el.forgotPasswordForm,CMSLoginService.forgotPasswordHandler);
		},
		load : function() {
			CMSCom.init();
			_root.setEvents();
			adjustScreen.contentHeight();
			webshim.setOptions('forms', {
				lazyCustomMessages: true,
				replaceValidationUI: true,
				customDatalist: true
			});
			webshim.polyfill('forms');
			// load the forms polyfill
		},
		init : function() {
			_root = this;
			_root.load();
			CMSCom.init();
		}
	};

document.addEventListener('DOMContentLoaded', CMSLogin.init.bind(CMSLogin));

})();