(function(window) {
	"use strict";
    var _root, app = {    		
        el : {
		    body:$("body"),
			window:$(window),
			// ************ login ************ 
			loginModal:$("#loginModal"),
			userLoginForm:$("#userLoginForm"),
			uname:$("#uname"),
			password:$("#password"),
			userDetailsBtn:$("#userDetailsBtn"),
			consumerDetailsBtn:$("#consumerDetailsBtn"),
			providerDetailsBtn:$("#providerDetailsBtn")
			
			
		},
		setEvents : function() {
			_root.el.window.off("click").on("click",_root.handler.windowOnClick);		

			// ************ login ************ 
			_root.el.userLoginForm.off("submit").on("submit",_root.handler.userLoginOnSubmit);
			_root.el.userDetailsBtn.off("click").on("click",_root.handler.showUserDetails);
			_root.el.consumerDetailsBtn.off("click").on("click",_root.handler.showConsumerDetails);
			_root.el.providerDetailsBtn.off("click").on("click",_root.handler.showProviderDetails);
					
						
		},
		load : function() {
			_root.handler = appEventHandlers.get(_root);
		    _root.setEvents();		
		},
		init : function() {
			_root = this;	
			_root.load();
		}
	}; 
	document.addEventListener('DOMContentLoaded', app.init.bind(app));
})(this);