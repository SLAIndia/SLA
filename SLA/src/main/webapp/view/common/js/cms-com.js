

var CMSGl_Data={  // Ran Admin Global Data
	fieldvals : {
		/*probe_name:{
			valueMissing:"Please Enter Probe Name"
		},*/
		com:{
			valueMissing:"This field is required",
			onlyNos:"Only numbers are required",
			invalidInput:"This is invalid",
			pattern:{
				password:"Password must be between 6 and 25 character!"
			} 
		},
		userdetail_email:{
			pattern:"Invalid Email!"
		},
		cattlelatt:{
			pattern:"Enter valid latitude "	
		},
		cattlelong:{
			pattern:"Enter valid longitude "		
		},
		probe_mgt_address:{
			pattern:"Invalid MGT Address (ex: 192.168.20.110:8080)"
		},
		ip:{
			pattern:"Invalid IP address"
		}	
	},
	flag:{
		probeFormValTabs:true
	},
	userLogin:null
};


var CMSMessage=(function(){
	
	var commonMsgEl=$("#cmscommonMsg"),
		commonMsgModalEl=".modalFormMsg-alert",
		subPageMsgEl=$("#subPageMsg");
	
	var showCommonMsg=function(msgData){		
		if(msgData.success===1){
			commonMsgEl.addClass("alert-success");
			commonMsgEl.removeClass("alert-danger");
		}else{
			commonMsgEl.removeClass("alert-success");
			commonMsgEl.addClass("alert-danger");
		}
	
		commonMsgEl.html(msgData.msg);
		commonMsgEl.fadeIn(2000);
		commonMsgEl.stop(true,false).fadeTo(1000,1);
		setTimeout(function() {
			commonMsgEl.fadeOut(3000);
		});		
	};
	
	var showCommonModalMsg=function(msgData,modalEl){	
		var msgEl=$(modalEl).find(commonMsgModalEl);
	
		if(msgData.success===1){
			msgEl.addClass("alert-success");
			msgEl.removeClass("alert-danger");

		}else{
			msgEl.removeClass("alert-success");
			msgEl.addClass("alert-danger");
		}
	
		msgEl.find(".modalFormMsg").html(msgData.msg);
		msgEl.fadeIn(2000);
		msgEl.stop(true,false).fadeTo(1000,1);
		setTimeout(function() {
			msgEl.fadeOut(3000);
		});		
	};
	
	var showSubPageMsg=function(msgData){	
		if(msgData.status*1===1){
			subPageMsgEl.addClass("formmsg-success");
			subPageMsgEl.removeClass("formmsg-error");
		}else{
			subPageMsgEl.removeClass("formmsg-success");
			subPageMsgEl.addClass("formmsg-error");
		}
		subPageMsgEl.html(msgData.msg);
		subPageMsgEl.fadeIn(1000);
		adjustScreen.contentHeight();
	   
		setTimeout(function() {
			subPageMsgEl.fadeOut(4000,function(){
				 adjustScreen.contentHeight();
			});
		});
		if(CMSCom.deviceFn.isMobile.any()){
			$('html, body').animate({
				 // scrollTop: $("#owner-inner-page .subPageContent").not(".hidden").offset().top
	            scrollTop: subPageMsgEl.offset().top
	        }, 'slow');
		}
	};
	
	var showSubPageMsgNoHide=function(msgData){	
		if(msgData.status*1===1){
			subPageMsgEl.addClass("formmsg-success");
			subPageMsgEl.removeClass("formmsg-error");
		}else{
			subPageMsgEl.removeClass("formmsg-success");
			subPageMsgEl.addClass("formmsg-error");
		}
		subPageMsgEl.html(msgData.msg);
		subPageMsgEl.fadeIn(1000);
	   

	};
	
	var showCommonLoader=function(){
		$(".overlay").fadeIn();
		$(".common-loader").fadeIn();
	};
	
	var hideCommonLoader=function(){
		$(".overlay").fadeOut();
		$(".common-loader").fadeOut();	
	};
	
	
	return {
		showCommonMsg:showCommonMsg,
		showCommonModalMsg:showCommonModalMsg,
		showSubPageMsg:showSubPageMsg,
		showSubPageMsgNoHide:showSubPageMsgNoHide,
		showCommonLoader:showCommonLoader,
		hideCommonLoader:hideCommonLoader
	};
	
})();



var CMSCom=(function(){
	
	var ajaxService={				
		getJsonData:function(urlInfo,parm){				
			data=!!parm?parm:"";		
		    return $.ajax({
		        url: urlInfo.url,	
		        data:data,
		        contentType: urlInfo.contentType,
		        dataType: urlInfo.dataType,
		        type: urlInfo.type,
		        cache:false
		    });  	
		},	
		sentJsonData:function(urlInfo,data){			
		    return $.ajax({
		        url: urlInfo.url,
		        data: data,
		        contentType: urlInfo.contentType,
		        dataType: urlInfo.dataType,
		        type: urlInfo.type,
		        cache:false
		    });   	    
		},
		sentMultipartFormData:function(urlInfo,data){			
		    return $.ajax({
		        url: urlInfo.url,
		        data: data,
		        type: urlInfo.type,
			    mimeType:"multipart/form-data",
			    contentType: false,
			    cache: false,
			    processData:false,
		    });   	    
		},
		getJsonDataAsyncFalse:function(urlInfo,parm){				
			data=!!parm?parm:"";		
		    return $.ajax({
		        url: urlInfo.url,	
		        data:data,
		        contentType: urlInfo.contentType,
		        dataType: urlInfo.dataType,
		        type: urlInfo.type,
		        async:false,
		        cache:false
		    });  	
		},	
		loadPage:function(pageInfo,callBkFn,callBkFnParam){
			var loadStatus= pageInfo.el.data("load-status");
			if(!!loadStatus){
				callBkFn(callBkFnParam);
			}else{
				pageInfo.el.load(pageInfo.url, function(response, status, xhr){
		            if(status === "success"){
		            	pageInfo.el.data("load-status",true);
		            	callBkFn(callBkFnParam);
		            }else{
		            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
		            }
		        }).error(CMSCom.exceptionHandler.ajaxFailure);
			}  
		}  
	};
	
	
	
	var deviceFn={
		isIE : function  () {
		    var myNav = navigator.userAgent.toLowerCase(),
		    ieIELower=(myNav.indexOf('msie') != -1) ? parseInt(myNav.split('msie')[1]) : false,
		    isIETrident=(/Trident\/7\.0/.test(navigator.userAgent));	
		    return (isIETrident||ieIELower);
		},
		findBootstrapEnv:function () {
		    var envs = ['xs', 'sm', 'md', 'lg'];

		    $el = $('<div>');
		    $el.appendTo($('body'));

		    for (var i = envs.length - 1; i >= 0; i--) {
		        var env = envs[i];

		        $el.addClass('hidden-'+env);
		        if ($el.is(':hidden')) {
		            $el.remove();
		            return env
		        }
		    };
		},
		isMobile : {      		
		    Android: function() {
		        return navigator.userAgent.match(/Android/i);
		    },
		    BlackBerry: function() {
		        return navigator.userAgent.match(/BlackBerry/i);
		    },
		    iOS: function() {
		        return navigator.userAgent.match(/iPhone|iPad|iPod/i);
		    },
		    Opera: function() {
		        return navigator.userAgent.match(/Opera Mini/i);
		    },
		    Windows: function() {
		        return navigator.userAgent.match(/IEMobile/i);
		    },
		    any: function() {
		        return (deviceFn.isMobile.Android() || deviceFn.isMobile.BlackBerry() || deviceFn.isMobile.iOS() || deviceFn.isMobile.Opera() || deviceFn.isMobile.Windows());
		    }
		}
	}; 
	
	var dateFn={
		format_two_digits:function  (n) {
		      return n < 10 ? '0' + n : n;
		},
		formattedDate:function (date) {
	        var d = new Date(date || Date.now()),
	        month = '' + (d.getMonth() + 1),
	        day = '' + d.getDate(),
	        year = d.getFullYear();

	        if (month.length < 2) month = '0' + month;
	        if (day.length < 2) day = '0' + day;

	        return [day, month, year].join('/');
	    },time_format: function (d) {
	    	var _this=this;
	        var hours = _this.format_two_digits(d.getHours()),
	        minutes = _this.format_two_digits(d.getMinutes()),
	        seconds = _this.format_two_digits(d.getSeconds());
	        return hours + ":" + minutes + ":" + seconds;
	    },time_format_HH_MM: function (d) {
	    	var _this=this;
	        var hours = _this.format_two_digits(d.getHours()),
	        minutes = _this.format_two_digits(d.getMinutes());
	        return hours + ":" + minutes;
	    },
	    cusDateAsDD_MM_YYYY: function(date) {  
	        var d = date,
	        month = '' + (d.getMonth() + 1),
	        day = '' + d.getDate(),
	        year = d.getFullYear();

	        if (month.length < 2) month = '0' + month;
	        if (day.length < 2) day = '0' + day;

	        return [day, month, year].join('-');
	    },
	    cusDateAsDD_MM_YYYY_TS: function(date) {  
	        var d = date,
	        month = '' + (d.getMonth() + 1),
	        day = '' + d.getDate(),
	        year = d.getFullYear(),
	        seconds = d.getSeconds(),
	        minutes = d.getMinutes(),
	        hour = d.getHours();
	       
	        if (month.length < 2) month = '0' + month;
	        if (day.length < 2) day = '0' + day;
	        
	        if ((seconds+"").length < 2) seconds = '0' + seconds;
	        
	        if ((minutes+"").length < 2) minutes = '0' + minutes;
	        
	        if ((hour+"").length < 2) hour = '0' + hour;
	        
	        
	        return [day, month, year].join('-')+" "+hour+":"+minutes+":"+seconds;
	    },
	    current_Time: function() {
	        return new Date().getTime();
	    },
	    daysBetween : function( date1, date2 ) { 
	    	  //Get 1 day in milliseconds
	    	  var one_day=1000*60*60*24;

	    	  // Convert both dates to milliseconds
	    	  var date1_ms = date1.getTime();
	    	  var date2_ms = date2.getTime();

	    	  // Calculate the difference in milliseconds
	    	  var difference_ms = date2_ms - date1_ms;
	    	    
	    	  // Convert back to days and return
	    	  return Math.round(difference_ms/one_day); 
	    },
	    cusDateServerFormat: function(dateStr) {  
	        return dateStr+" 00:00:00";
	    }
	};	
	
	var exceptionHandler={
		ajaxFailure: function(jqXHR,error, errorThrown) {
            var message;
            var statusErrorMap = {
                400 : "Server understood the request but request content was invalid.",
                401 : "Unauthorised access.",
                403 : "Forbidden resouce can't be accessed",
                404:  "The requested resource is not available",
                500 : "Internal Server Error.",
                503 : "Service Unavailable"
            };           
            if (jqXHR.status) {            
                message =statusErrorMap[jqXHR.status];            
            }        
            if(!message){
              //  message="Something went wrong";
            	  message="Internal Server Error.";
            }
            if(!!CMSGl_Data.userLogin){
            	if(!CMSCom.isUserSessionExists(CMSGl_Data.userLogin)){ // if user session expires 
            		message="Session Expired, Please Login again";
            		logoutRedirect();
            	}
            }

            var msg={session:0,msg:message};
            CMSMessage.showCommonMsg(msg);
		},
		isLoginSessionExpire:function(data){
			var jsonResData=null,
				result=true;
		    try {
		    	jsonResData=typeof data ==='object' ? data : JSON.parse(data);	
		    	if("status" in jsonResData && (jsonResData["status"]==="9"||jsonResData["status"]==="8")){
				   logoutRedirect();				   
				   result=false
		    	}
		    }catch(e){
				result=false;
		    }		    
			return result;
		}
	};	
	var logoutRedirect=function(){
		var msg={session:0,msg:"Session Expired, Please Login again"};
		   CMSMessage.showCommonMsg(msg);
		   setTimeout(function(){
			   window.location.href="../login.html";
		   },3000);
	};
	

	var isUserSessionExists=function(utype){		
		var result=true;		
		if(CMSStorage.updateStorageExpiryTime(utype+"_u_id")){
			CMSStorage.updateStorageExpiryTime(utype+"-signature")
			CMSStorage.updateStorageExpiryTime(utype+"-username")
			CMSStorage.updateStorageExpiryTime(utype+"_role_id")
		}else{
			result =false;
			logoutRedirect();
		}
		return result;
	};
	
	
	var textFn={
		limit:function(txt,lmtLen){
			var result=null;    		
    		if(!!txt&&txt.length  > lmtLen){    
    			result= txt.substring(0,lmtLen)+"...";    			
    		}else{
    			result = txt;    			
    		}
    		return result;
		},
		limitTooltip:function(txt,lmtLen){
			var result=null;    		
    		if(!!txt&&txt.length  > lmtLen){    
    			result= txt.substring(0,lmtLen)+"...";  
    			result="<div class='lmt-tooltip' data-toggle='tooltip' data-placement='left' title='"+txt+"'>"+result+"</dv>";
    		}else{
    			result = txt;    			
    		}
    		return result;
		}	
	};
	
	var formErrIncr=0;
	var inValid ={
		isJsonString:function(str) {
		    try {
		        JSON.parse(str);
		    } catch (e) {
		        return false;
		    }
		    return true;
		},
		isNumberKey:function (e){			
			 //return false if not 0-9
		    if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
		       return false;
		    }else{
		        //limit length but allow backspace so that you can still delete the numbers.
		        if( $(this).val().length >= parseInt($(this).attr('maxlength')) && (e.which != 8 && e.which != 0)){
		            return false;
		        }
		    }
		},
		isFloatKey:function (e){
			var index=$(this).val().indexOf('.');

			 //return false if not 0-9
			if((e.which==110||e.which==190||e.which==46)&&(index===-1)){
				return true;
			}else if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
		       return false;
		    }else{
		        //limit length but allow backspace so that you can still delete the numbers.
		        if( $(this).val().length >= parseInt($(this).attr('maxlength')) && (e.which != 8 && e.which != 0)){
		            return false;
		        }
		    }
		},
		isPostiveFloatKey:function (e){
			var index=$(this).val().indexOf('.');

			 //return false if not 0-9
			if((e.which==110||e.which==190||e.which==46)&&(index===-1)){
				return true;
			}else if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
		       return false;
		    }else{
		        //limit length but allow backspace so that you can still delete the numbers.
		        if( $(this).val().length >= parseInt($(this).attr('maxlength')) && (e.which != 8 && e.which != 0)){
		            return false;
		        }
		    }
		},
		noWhiteSpace:function(e){
			if(e.which==32){
				return false;
			}
		},
		errMsg:function(e) {// ### please  change for safri  in js/lib/polyfiller-shims/shims/combos/15.js
	
			var _this=this,
				errmsg="",
				inputName=_this.name;
	
		    if (_this.validity.valueMissing) {	       
		    	
		    	errmsg=(inputName in CMSGl_Data.fieldvals)?
		    			CMSGl_Data.fieldvals[inputName]["valueMissing"]:
		    			CMSGl_Data.fieldvals.com["valueMissing"];
		    	
		    } else if (_this.validity.typeMismatch){		    	
		    	errmsg=CMSGl_Data.fieldvals.com["invalidInput"];
		    } else if (_this.validity.patternMismatch){	
		    	errmsg=(inputName in CMSGl_Data.fieldvals)?CMSGl_Data.fieldvals[inputName]["pattern"]:CMSGl_Data.com.invalidInput;	
		    } else{
		    	
		    }

		    _this.setCustomValidity(errmsg);
		    
		    return true;
		}
	};
	var validation={
		isImg:function(fileName){
			var fileExtension = ['jpeg', 'jpg', 'png', 'gif', 'bmp'];
			return !!($.inArray(fileName.split('.').pop().toLowerCase(), fileExtension) > -1)
		},
		isNumber:function(n){
			 return !isNaN(parseFloat(n)) && isFinite(n);
		},
		isDecimal:function (txt) { 
			var decimal=  /^[-+]?[0-9]+\.[0-9]+$/; 
			return txt.match(decimal);
		} 
	};
	
	var URL={
		queryParam:function gup( name, url ) {
			  if (!url) url = location.href
			  name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
			  var regexS = "[\\?&]"+name+"=([^&#]*)";
			  var regex = new RegExp( regexS );
			  var results = regex.exec( url );
			  return results == null ? null : results[1];
		}
	};
	
	var inputEventinit =function(){		
		//$(".nu-only").on('keyup keypress blur change',inValid.isNumberKey);
		//$("form").on('keyup keypress blur change',".nu-only",inValid.isNumberKey);
		//$(".nu-only").bind("cut copy paste",function(e) {e.preventDefault();});
		$("body").on('keyup keypress blur change',".no-white",inValid.noWhiteSpace);
		$("body").on('cut copy paste',".no-white",function(e) {e.preventDefault();});
		$("body").on('keyup keypress blur change',".nu-only",inValid.isNumberKey);
		$("body").on('cut copy paste',".nu-only",function(e) {e.preventDefault();});
		
		$("body").on('keyup keypress blur change',".float-only",inValid.isFloatKey);
		$("body").on('cut copy paste',".float-only",function(e) {e.preventDefault();});
		$("body").on('keyup keypress blur change',".pos-float-only",inValid.isPostiveFloatKey);
		$("body").on('cut copy paste',".pos-float-only",function(e) {e.preventDefault();});
		//$(".no-white").bind("cut copy paste",function(e) {e.preventDefault();});
		
		  $('[data-toggle="tooltip"]').tooltip();  
		//lmt-tooltip
		/*  $('body').tooltip({
			    selector: '.lmt-tooltip'
			});*/
	};
	
	var init=function(){
		inputEventinit();
	};
	

	return {
		ajaxService:ajaxService,
		validation:validation,
		deviceFn:deviceFn,
		dateFn:dateFn,
		exceptionHandler:exceptionHandler,
		logoutRedirect:logoutRedirect,
		textFn:textFn,		
		inValid:inValid,
		isUserSessionExists:isUserSessionExists,
		URL:URL,
		init:init
	};
	
})();




