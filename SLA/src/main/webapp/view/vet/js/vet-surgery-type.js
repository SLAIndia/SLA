var SurgeryType = (function(){
	
	var loadSurgeryTypeDataTable=function(){

		if(!!CMSVetCons.dataTable["surgeryTypeDtls"].showFlg){
			CMSVetCons.dataTable["surgeryTypeDtls"].dtFn._fnAjaxUpdate();
			CMSVetCons.dataTable["surgeryTypeDtls"].dtFn.fnSetColumnVis( 0, false );			
		}else{	
			var	reqData=jQuery.extend({}, CMSVetCommon.getInitReqData()),
				urlObj=CMSVetCons.urls_info["getSurgeryList"],
				req={};    
				req["surgeryid"]=0;
				reqData["reqData"]=req;	

			$.fn.dataTableExt.sErrMode = 'throw';
			CMSVetCons.dataTable["surgeryTypeDtls"].dtFn=$('#surgeryTypeDataTable').dataTable({
			  "ajax": {
			    "url": urlObj.url,
			    "type": urlObj.type,
			    //"data":reqData,
			    "dataType": 'json',
			    processData: false,
			    beforeSend:function(jqXHR,settings ){			    
			    	settings.data = JSON.stringify(reqData);
                },
				"contentType":'application/json',
			    "dataSrc": function (dataJson) {	
			    	if(CMSCom.exceptionHandler.isLoginSessionExpire(dataJson)&&dataJson.status==="1"){
			    		var jsonRes=JSON.parse(dataJson.resData);				     
				    	for (var key in jsonRes){
				    		var eachObj=jsonRes[key];
				    		
				    		jsonRes[key][0] = eachObj.surgeryid;
				    		jsonRes[key][1] = eachObj.surgeryname;
				    		var statusHtml="",
				    		actionHtml="";
				    		var status = eachObj.surgerystatus;
			    		if(status*1===1){ // should be change to status
			    			statusHtml="<i class='fa fa-circle fa-active'></i>"+"<span style='display:none;'>"+1+"</span";
			    		}else{
			    			statusHtml="<i class='fa fa-circle fa-inactive'></i>"+"<span style='display:none;'>"+0+"</span";
			    		}	
			    		jsonRes[key][2] = statusHtml;				    		
			    	
				    	var actionHtml="<a href='javascript:void(0);' class='vet-editLink' id ='surgery-type-edit' data-surgery-typeid='"+eachObj.surgeryid+"'><i class='fa fa-pencil-square-o'></i> Edit </a> ";
				    	jsonRes[key][3] = actionHtml; 
				    		
				    	} 

					    return jsonRes;			   
				    }else{
				    	return [];
				    }	
			    }
			  },
			  destroy: true,
			  "aaSorting": [[ 0, "desc" ]],
			  "aoColumnDefs": [
			       { 'bSortable': false, 'aTargets': [3] }
			   ],
			  'processing': false,
			  'serverSide': false,
			  'bLengthChange': false, 
			  'fnInitComplete' :function(){
					setTimeout(function(){
						$( "#surgeryTypeDataTable" ).wrapAll( "<div class='table-responsive'></div>" );							
					},100)
				 adjustScreen.contentHeight();
			   },
			   'fnDrawCallback' : function() {
				  adjustScreen.contentHeight();
				}			  
			});
			 var oSettings = CMSVetCons.dataTable["surgeryTypeDtls"].dtFn.fnSettings();
		     oSettings._iDisplayLength = 10; 			     
		     CMSVetCons.dataTable["surgeryTypeDtls"].dtFn.fnSetColumnVis( 0, false );
		}	
	};
	var surgeryTypeListCallBackFn = function(){
		//resetFields();
		
		$("#cms-surgery-type-list-holder").removeClass("hidden");	
		$("#breadcrumb-subpage").html("Surgery/Illness Details");
		loadSurgeryTypeDataTable();
		adjustScreen.contentHeight();
		//listProfiledetails();
		//viewUserProfile();
		
	};
	
	var surgeryTypeList = function(){
			var loadStatus= $("#cms-surgery-type-list-holder").data("load-status");
			if(!!loadStatus){
				surgeryTypeListCallBackFn();
			}else{
				$("#cms-surgery-type-list-holder").load("partials/surgery-type-list.html", function(response, status, xhr){
		            if(status === "success"){
		            	$("#cms-surgery-type-list-holder").data("load-status",true);
		            	surgeryTypeListCallBackFn();
		            }else{
		            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
		            }
		        }).error(CMSCom.exceptionHandler.ajaxFailure);
			}  
			
		
	};
	
	
	var loadPage = function(surgId){
		try{
	
		var loadStatus= $("#cms-surgery-type-holder").data("load-status");
		if(!!loadStatus){
			surgeryTypeCallBackFn(surgId);
		}else{
			$("#cms-surgery-type-holder").load("partials/surgery-type.html", function(response, status, xhr){
	            if(status === "success"){
	            	$("#cms-surgery-type-holder").data("load-status",true);
	            	surgeryTypeCallBackFn(surgId);
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}  
		}catch (er) {
			// TODO: handle exception
			console.log(error,er);
		}
		
	};
	
	var surgeryTypeCallBackFn = function(surgId){
		
		
		$("#surgeryid").val(0);
		resetFields();
		$("#breadcrumb-subpage").html("Surgery/Illness Details");
		$("#cms-surgery-type-holder").removeClass("hidden");	
		if(!!surgId){
			fetchSurgeryTypeEdit(surgId);		
			
			$("#cmsSurgeryTypeTitle").html("Edit Surgery Type");
			$("#surgeryname").prop('disabled',true);
		}else{
			$("#surgeryname").prop('disabled',false);
			$("#cmsSurgeryTypeTitle").html("Add Surgery Type");
		}
		 adjustScreen.contentHeight();
		//listProfiledetails();
		//viewUserProfile();
		
	};
	
	var saveDetails = function(e){
		try{
			
		var surgeryid=$("#saveSurgeryType-form #surgeryid").val()*1;
		
		if($("#saveSurgeryType-form")[0].checkValidity() && checkSurgeryName(surgeryid)){	
			var urlObj=CMSVetCons.urls_info["saveSurgeryType"],
			surgeryTypedtls={},
			reqData=jQuery.extend({}, CMSVetCommon.getInitReqData()),
	 		msg="";
			
			$("#saveSurgeryType-form .form-dt").each(function(){
				surgeryTypedtls[$(this).prop("name")]=$(this).val();
			});
			$("#saveSurgeryType-form .form-dt-num").each(function(){
				if($(this).val()*1 > 0){
					surgeryTypedtls[$(this).prop("name")]=$(this).val()*1;
				}
				
			});
			$("#saveSurgeryType-form .form-dt-onOff").each(function(){
				surgeryTypedtls[$(this).data("input-name")]=$(this).find(".btn-on-off.on").data("val")*1;
			});
			
			reqData["reqData"]=surgeryTypedtls;			
			reqData=JSON.stringify(reqData);
			
			CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {				
				if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
					if(jsonResData.status*1===1){
						$("#surgeryid").val("0")
						CMSMessage.showSubPageMsg({"status":1,"msg":"Details saved successfully"});
						resetFields();
						window.location.hash = "surgery-type-list";
					}
					else{
					}	
				}
			}).fail(CMSCom.exceptionHandler.ajaxFailure);	
			
			
		}
	}catch (er) {
		// TODO: handle exception
		console.log(error,er);
	}
	e.preventDefault();
		
	};
	
	var checkSurgeryName = function(surgeryid){
		
			
			var urlObj=CMSVetCons.urls_info["checkSurgeryName"],
			flag = true,
			reqData=jQuery.extend({}, CMSVetCommon.getInitReqData()),
			surgeryTypedtls={};
	
			if(!surgeryid||surgeryid===0){
				var surgeryname =$("#surgeryname").val();
			surgeryTypedtls["surgeryname"]=!!surgeryname?surgeryname.trim():"";
			reqData["reqData"] = surgeryTypedtls;
			
				reqData=JSON.stringify(reqData);
				
				CMSCom.ajaxService.getJsonDataAsyncFalse(urlObj,reqData).done(function(jsonResData) {				
					if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
						if(jsonResData.status*1===1){
							CMSMessage.showSubPageMsg({"status":0,"msg":"Surgery name already exist"});
							flag =  false;
							
						}
						else{
						}	
					}
				}).fail(CMSCom.exceptionHandler.ajaxFailure);	
			}
			return  flag;
			
		
	}
	
	var resetFields = function(){
		
		var surgId = $("#surgeryid").val()*1;
	
		$("#saveSurgeryType-form .form-dt").each(function(){
			$(this).val("");
		});
		
		$("#saveSurgeryType-form .form-dt-date").each(function(){
			$(this).val("");
		
		});
		$("#saveSurgeryType-form .form-dt-onOff").each(function(){
			$(this).find(".btn-on-off").removeClass("on");
			$(this).find(".btn-on-off[data-val='1']").addClass("on");

		});
		
		if(!!surgId && surgId >0){
			fetchSurgeryTypeEdit(surgId);
		}else{
			$("#saveSurgeryType-form .form-dt-num").each(function(){
				$(this).val("0");
			});
		}
		
		
	};
	var editSurgeryType =function(){
		
		var surId = $(this).data('surgery-typeid');
		window.location.hash = "edit-surgery-type="+surId;
		//fetchSurgeryTypeEdit();
	};
	
	var fetchSurgeryTypeEdit = function(surgId){
	
		var urlObj=CMSVetCons.urls_info["getSurgeryList"],
		reqData=jQuery.extend({}, CMSVetCommon.getInitReqData()),
		surgeryTypedtls={};
		surgeryTypedtls["surgeryid"]= surgId;
		reqData["reqData"] = surgeryTypedtls;
		
		reqData=JSON.stringify(reqData);
		
		CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {				
			if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
				if(jsonResData.status*1===1){
					displaySurgeryType(jsonResData);
				}
				else{
				}	
			}
		}).fail(CMSCom.exceptionHandler.ajaxFailure);	
		
		
	};
	
	var displaySurgeryType = function(jsonResData){
		var resData =JSON.parse(jsonResData.resData);
		resData = resData[0];
		console.log("resData is ",resData)
		$("#saveSurgeryType-form .form-dt-date").each(function(){
				$(this).val(resData[$(this).prop("name")])
			
		});
		$("#saveSurgeryType-form .form-dt-num").each(function(){
			$(this).val(resData[$(this).prop("name")])
		});
		$("#saveSurgeryType-form .form-dt").each(function(){
			$(this).val(resData[$(this).prop("name")]);
		});

		$("#saveSurgeryType-form .form-dt-onOff").find(".btn-on-off").removeClass("on");
		$("#saveSurgeryType-form .form-dt-onOff").each(function(){				
			$(this).find(".btn-on-off[data-val='"+resData[$(this).data("input-name")]+"']").addClass("on");
		});	
		$("#surgeryname").prop('disable',true);
	};

	return{
		surgeryTypeList:surgeryTypeList,
		loadPage:loadPage,
		saveDetails:saveDetails,
		resetFields:resetFields,
		editSurgeryType:editSurgeryType
		
	};


	
	
})();