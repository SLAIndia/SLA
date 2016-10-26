var appEventHandlers = (function() {
	let _root;
	let timeInterval;
	let initTime = 120;
	let expirayTime = function(){
		
		let time = initTime;
		if(timeInterval){
			clearInterval(timeInterval);
		}
		  
		timeInterval = window.setInterval(function(){
			if(time>-1){
				$("#expirationTime").html(time--);
			}
			
		}, 1000);
	};
	let ajaxErrorhandle =function(error){
		$("#responseCont").html(JSON.stringify(error));
	}; 
	
	let loginService = {
		loginSuccess:function(res){
			let userData = res.data;
			console.log("data",userData);
	    	 console.log("Token",res.jqXHR.getResponseHeader("Token"));
	    	 $("#loginUserTitle").html(userData.data.pEmail)
	    	 $("#loginUserName").html(userData.data.fname +" "+ userData.data.lname);
	    	 $("#loginUserRoles").html(userData.data.user.role.rolename);
	    	 
	    	 let token = res.jqXHR.getResponseHeader("Token")
	    	 $("#userLoginToken").html(token);
	    	 
	    	 localStorage.setItem('token', token);
	    	 expirayTime();
	    	 handlers.closeLoginModal();
		}
	};	
	
	let handlers = {			
	    closeLoginModal:function(){
		   _root.el.loginModal.hide();
	    },
		windowOnClick : function(event) {
			let	modal = _root.el.loginModal;
			if (event.target.id == modal.prop("id")) {
				handlers.closeLoginModal();
			}
		},
		userLoginOnSubmit : function(event) {
			event.preventDefault();
			event.stopPropagation();
			let	username = _root.el.uname.val(), 
				password = _root.el.password.val(),
				data = {
					"username":username,		 
					"password":password
				};					
			let requestObj = {
				url : AppConstants.get("URL")["LOGIN"],
				type : 'POST',
				contentType:'application/x-www-form-urlencoded',
				data:data,
				proccessData:false
			};			
			AjaxService.invoke(requestObj).then(function(res){	
				localStorage.setItem('username', username);
				loginService.loginSuccess(res);
			}).catch(ajaxErrorhandle);
		},
		showUserDetails : function(){		
			
			let userName = localStorage.getItem('username');
			let requestObj = {
				url : AppConstants.get("URL")["PROFILE_DETAILS"]+userName,
				secure : true
			};			
			AjaxService.invoke(requestObj).then(function(res){			
				console.log("res ",res);
				$("#responseCont").html(JSON.stringify(res));
		    }).catch(ajaxErrorhandle);
		},
		showConsumerDetails : function(){		
			let requestObj = {
				url : AppConstants.get("URL")["CONSUMER_DETAILS"],
				secure : true
			};			
			AjaxService.invoke(requestObj).then(function(res){			
				console.log("res ",res);
				$("#responseCont").html(JSON.stringify(res));
			}).catch(ajaxErrorhandle);
		},
		showProviderDetails : function(){		
			let requestObj = {
				url : AppConstants.get("URL")["PROVIDER_DETAILS"],
				secure : true
			};			
			AjaxService.invoke(requestObj).then(function(res){			
				console.log("res ",res);
				$("#responseCont").html(JSON.stringify(res));
		    }).catch(ajaxErrorhandle);
		}
	};
	return {
		get : function(root){
			_root = root;
			return handlers;
		}
	};
})();

