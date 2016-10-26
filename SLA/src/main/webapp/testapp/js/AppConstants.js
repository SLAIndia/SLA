var AppConstants = (function(){	
	let BASE_URL = "http://localhost:8080/SLA/";
	let consts =  {	    
	    URL :{	    	 
	    	LOGIN: BASE_URL+"login",
	    	PROFILE_DETAILS:BASE_URL+"profile/details/",
	    	CONSUMER_DETAILS:BASE_URL+"consumer/check",
	    	PROVIDER_DETAILS:BASE_URL+"provider/check",
	    },
	    
	};
	return {
		get: function(key) { 
			return consts [key];
		}
	};
})();
