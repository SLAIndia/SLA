$(document).ready(function() {

	var isRegistered = false;
		
	  var checkUsername = function(e){
		try{
			
			    var checkString  = $("#username").val();
			    var reqData = {},
			    result=true;
			    
			    reqData["username"] = $("#username").val();
			    reqData=JSON.stringify(reqData);	 
			    if(checkString){
			     	var urlObj=CMSVetCons.urls_info["checkusername"];
			     	CMSCom.ajaxService.getJsonDataAsyncFalse(urlObj,reqData).done(function(jsonResData) {	
			     		
			     		if(jsonResData.status === -1){
							$("#failed_msg").html('Username already exists.');
							$("#failed_msg").removeClass('hidden');
							
							result=false;
						}else{
							result=true;
							$("#failed_msg").addClass('hidden');
						}		
				}).fail(CMSCom.exceptionHandler.ajaxFailure);
			    }
			return result;
		}  catch (e) {
			// TODO: handle exception
		}
	  };

		$('#RegisterModal').on('hidden.bs.modal', function () {
			
        if(isRegistered){
        	isRegistered=false;
        	$('#Approval').modal('show');        	
        }
       });
	
		
		$("#username").keyup(function (e) {
		   
			checkUsername();
		});
	 
	  $("#isaccepted").click(function() {
		  $("#register").prop("disabled", !this.checked);
	  });	
	  
	  //To clear the fields on load 
	  $('#RegisterModal').on('shown.bs.modal', function() {
		  resetForm();
		});

	  var passwordValidation = function() {
		  var result=true,
		    	password = $("#password").val(),	    
		    	confirmPassword = $("#cpassword").val();
		    
		    if(!!password || !!confirmPassword || password.trim().lenght!==0 || confirmPassword.trim().lenght!==0){
		    	if (password != confirmPassword){
		    		$("#failed_msg").html('Password mismatch');
		    		$("#failed_msg").removeClass("hidden");
		    		result=false;
		    		
		    	}
		    }
		    return result;
		    
		};


	  	
	  	var resetForm = function(){
	  		$("#firstname").val("");
	  		$("#lastname").val("");
	  		$("#username").val("");
			$("#password").val("");
			$("#cpassword").val("");
			$("#speciality").val("");
			$("#location").val("");
			$("#phone").val("");
			$("#email").val("");
			$("#address").val("");
			$('#isaccepted').prop('checked', false);		
			$("#register").prop("disabled", true);
			$('#vet').prop('checked', true);
			$('#paravet').prop('checked', false);
			$("#failed_msg").addClass('hidden');
	  	};
		$("#form-newacc").submit(function(e){
		
			if(($("#form-newacc")[0].checkValidity()) && passwordValidation() && checkUsername() && checkEmail()){
			
			   	var urlObj=CMSVetCons.urls_info["register"];
			   	var reqData = {};		
			   	reqData["username"] = $("#username").val();
			   	reqData["password"] =  $("#password").val();
				reqData["confirmpass"] = $("#cpassword").val();
				reqData["isactive"] = true;
				reqData["role_name"] = "vet";
				reqData["userfname"] =  $("#firstname").val();
				reqData["userlname"] =  $("#lastname").val();
				reqData["vettype"] = $(".regular-radio:checked").val();
				reqData["useraddress"] = $("#address").val();
				reqData["userlocation"] =  $("#location").val();
				reqData["useremail"] =  $("#email").val();
				reqData["userphone"] =  $("#phone").val();
				reqData["vetspeciality"] =  $("#speciality").val();
				reqData["vetStatus"] = 0;		
				reqData=JSON.stringify(reqData);	
				CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {			
					if(jsonResData.status === 1){
						isRegistered = true;
						$("#failed_msg").addClass('hidden');
						$('#RegisterModal').modal('hide');
						resetForm();
						
					}else{
						$("#failed_msg").html('Failed to save :' + jsonResData.message);
						$("#failed_msg").removeClass('hidden');
					}		
				}).fail(CMSCom.exceptionHandler.ajaxFailure);
			}
			e.preventDefault();
	});
		
	 var checkEmail = function(){
			try{
				    var checkString  = $("#email").val();
				    var reqData = {},
				    result=false;
				    
				    reqData["email"] = checkString;
				    reqData=JSON.stringify(reqData);	 
				    if(!!checkString){
				     	var urlObj=CMSVetCons.urls_info["checkEmail"];
				     	CMSCom.ajaxService.getJsonDataAsyncFalse(urlObj,reqData).done(function(jsonResData) {	
				     		console.log(jsonResData);
				     		if(jsonResData.status === -1){
								$("#failed_msg").html('Email alreay exists.');
								$("#failed_msg").removeClass('hidden');
								$("#email").val("")
							}else{
								result=true;
								$("#failed_msg").addClass('hidden');
							}		
					}).fail(CMSCom.exceptionHandler.ajaxFailure);
				    }
				return result;
			}  catch (er) {
				// TODO: handle exception
			}
		  };
		
});