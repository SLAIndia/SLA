
var VetSurgery=(function(){
	
	var fixSurgeryDataTableHeight=function(){
		var treatmentDataTableH=$("#treatmentDataTable_wrapper").height();
		$("#treatmentDataTableHolder").height(treatmentDataTableH+50);
		$("#treatmentDataTableHolder .mCustomScrollbar").height(treatmentDataTableH+25);
	}; 
	
	var loadSurgeryDataTable = function(){

		if(!!CMSVetCons.dataTable["surgeryDtls"].showFlg){
			CMSVetCons.dataTable["surgeryDtls"].dtFn._fnAjaxUpdate();
			CMSVetCons.dataTable["surgeryDtls"].dtFn.fnSetColumnVis( 0, false );			
		}else{	
			var	reqData=jQuery.extend({}, CMSVetCommon.getInitReqData()),
				urlObj=CMSVetCons.urls_info["getSurgeryIllnessMaster"],
				req={};     
			
			reqData["reqData"]=req;				
			$.fn.dataTableExt.sErrMode = 'throw';
			CMSVetCons.dataTable["surgeryDtls"].dtFn=$('#surgeryDataTable').dataTable({
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
				    		jsonRes[key][0] = eachObj.surgillid;
				    		jsonRes[key][1] = eachObj.cattle.cattleeartagid;
				    		jsonRes[key][2] = CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(eachObj.surgillprocdt));
				    		jsonRes[key][3] = eachObj.vet.objUserEntity.username;
				    		/*jsonRes[key][4] = eachObj.vaccinationexpdt;	*/			    	
			
		    		
				    		var actionHtml="<a href='javascript:void(0);' class='vet-editLink' id ='surgery-edit' data-cattle-id='"+eachObj.cattle.cattleid+"' data-process-dt='"+CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(eachObj.surgillprocdt))+"' ><i class='fa fa-pencil-square-o'></i> Edit </a> ";
				    		/*actionHtml+="<a href='javascript:void(0);' class='vet-editLink' id ='surgery-delete' data-surg-id='"+eachObj.surgillid+"'><i class='fa fa-trash'></i> Delete</a> ";*/
				    		jsonRes[key][4] =actionHtml; 
				    		
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
			       { 'bSortable': false, 'aTargets': [4] }
			   ],
			  'processing': false,
			  'serverSide': false,
			  'bLengthChange': false, 
			  'fnInitComplete' :function(){
					setTimeout(function(){
						$( "#surgeryDataTable" ).wrapAll( "<div class='table-responsive'></div>" );							
					},100)
				 adjustScreen.contentHeight();
			   },
			   'fnDrawCallback' : function() {
				  adjustScreen.contentHeight();
				}			  
			});
			 var oSettings = CMSVetCons.dataTable["surgeryDtls"].dtFn.fnSettings();
		     oSettings._iDisplayLength = 10; 			     
		     CMSVetCons.dataTable["surgeryDtls"].dtFn.fnSetColumnVis( 0, false );
		}	
	};
	var showSurgeryDtlsPageCallBackFn = function(){
		$("#cms-surgery-details").removeClass("hidden");	
		$("#breadcrumb-subpage").html("Surgery/Illness Details");
		loadSurgeryDataTable();
		adjustScreen.contentHeight();
	};
	var showSurgeryDetlsPage = function(){

		var loadStatus= $("#cms-surgery-details").data("load-status");
		if(!!loadStatus){
			showSurgeryDtlsPageCallBackFn();
		}else{
			$("#cms-surgery-details").load("partials/surgery-list.html", function(response, status, xhr){
	            if(status === "success"){
	            	$("#cms-surgery-details").data("load-status",true);
	            	showSurgeryDtlsPageCallBackFn();
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}       
	};
	
	var displayTreatmentPage = function(){
		var vacUnit = [{"id":"mg","type":"mg"},{"id":"ml","type":"ml"}];
		selectOptHtml="<option value=''></option>";	
		for(var i in vacUnit){	
			var typeObj=vacUnit[i];
				selectOptHtml+="<option value='"+typeObj["id"]+"'>"+typeObj["type"]+"</option>";		
		}
		$("#surgilldetdoseunit").html(selectOptHtml);
		//$("#surgilldetdoseunit").chosen({allow_single_deselect: true});
		
	};
	
	var loadAddUpdatePage = function(cattleSelected,processDt){		
		
		var urlObj=CMSVetCons.urls_info["getallFarmDetailsByUser"],
		reqData=jQuery.extend({}, CMSVetCommon.getInitReqData());
		reqData["reqData"]={};
		reqData=JSON.stringify(reqData);
		
		// This is to fetch farm details assigned to the user
		CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {
			if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
				if(jsonResData.status==="1"){
					displayAddUpdatePage(jsonResData,cattleSelected,processDt)
				}else{
					CMSMessage.showSubPageMsg({"status":0,"msg":"Farm not assigned"});
				}
				
			}	
		}).fail(CMSCom.exceptionHandler.ajaxFailure);
		
	};
	
	var loadVetUser = function(){
		
		var	reqData=jQuery.extend({}, CMSVetCommon.getInitReqData()),
		urlObj=CMSVetCons.urls_info["getVetdetails"],
		req={"id": 0,"role_name" :"VET"};     
		reqData["reqData"]=req;	
		reqData=JSON.stringify(reqData);
		
		// This is to fetch vet details 
		CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {
			if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
				if(jsonResData.status==="1"){
					loadVetDetails(jsonResData)
				}else{
					CMSMessage.showSubPageMsg({"status":0,"msg":"No vet "});
				}
				
			}	
		}).fail(CMSCom.exceptionHandler.ajaxFailure);
		
	};
	
	var loadVetDetails = function(jsonResData){
		var resData=JSON.parse(jsonResData.resData),	
		selectOptHtml="<option value=''></option>";			
		
		for(var i in resData){	
			var vetObj=resData[i];
			var userId = CMSVetCommon.getUserId();
			var uId =  vetObj.objUserEntity.id;
			if((userId != uId) &&(vetObj['vetStatus'] === 1) && (!!vetObj.objUserEntity['isactive'])){
				selectOptHtml+="<option value='"+vetObj["vetid"]+"'>"+vetObj.objUserEntity["username"]+"</option>";		
			}
		}
		$("#selectvetid").html(selectOptHtml);
		$("#selectvetid").chosen({allow_single_deselect: true});
		$("#vetDiv").addClass('hidden');
		
	}
	var loadSurgeryMasterData = function(cattleSelected,processDt){
		
		var	reqData=jQuery.extend({}, CMSVetCommon.getInitReqData()),
		urlObj=CMSVetCons.urls_info["getSurgeryMaster"];
		reqData["reqData"]={};	
		reqData=JSON.stringify(reqData);
		
		// This is to fetch surgery master details 
		CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {
			if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
				if(jsonResData.status==="1"){
					loadSurgeryCombo(jsonResData);
				}else{
					$("#addsurgerytype").html("");
					$("#addsurgerytype").trigger("chosen:updated");
					CMSMessage.showSubPageMsg({"status":0,"msg":" Type of Surgery not Available "});
				}
				 if(!!cattleSelected){	
						loadSurgeryDtlsforSelected(cattleSelected,processDt);
				}
			}	
		}).fail(CMSCom.exceptionHandler.ajaxFailure);
		
		
		
	};
	var loadSurgeryCombo = function(jsonResData){
		var resData=JSON.parse(jsonResData.resData),
		selectOptHtml="<option value=''></option>";		
		
		for(var i in resData){	
			var masterObj=resData[i];
				selectOptHtml+="<option value='"+masterObj["surgery_id"]+"'>"+masterObj["surgery_name"]+"</option>";		
		}
		
		$("#addsurgerytype").html(selectOptHtml);
		$("#addsurgerytype").chosen({allow_single_deselect: true});	
		$("#addsurgerytype").trigger("chosen:updated");
		$("#surgerytypeDiv").addClass('hidden');
	};
	var displayAddUpdatePage = function(jsonResData,cattleSelected,processDt){
		 
		
		
		var resData=JSON.parse(jsonResData.resData),
		farmDtls=resData["farm_dtls"],		
		selectOptHtml="<option value=''></option>";		
		
		for(var i in farmDtls){	
			var farmObj=farmDtls[i];
			if(!!farmObj["farm_status"]){
				selectOptHtml+="<option value='"+farmObj["farm_id"]+"'>"+farmObj["farm_name"]+"</option>";		
			}
		}
		
		$("#farmselectid").html(selectOptHtml);
		$("#farmselectid").chosen({allow_single_deselect: true});	
		
		 $('#surgillprocdt').datepicker({ format: "dd-mm-yyyy",endDate: new Date(),forceParse: false	         
	     }).on('changeDate', function(e){
	    	    $(this).datepicker('hide');
	     }).prop('readonly','readonly');
		 $("#surgillprocdt").off("click").on("click",function() {			 
		        $(this).datepicker().datepicker("show");
		 });
		 $("#surgillprocdt-calen").off("click").on("click",function() {			 
			 $("#surgillprocdt").datepicker().datepicker("show");
		 });
		 $("#cattleSelectid").html("");
		 $("#cattleSelectid").trigger("chosen:updated");
		 $("#cattleSelectid").chosen({allow_single_deselect: true});
		 $("#surgilldetdoseunit").chosen({allow_single_deselect: true});
		 loadVetUser();
		 loadSurgeryMasterData(cattleSelected,processDt);
		 
		 adjustScreen.contentHeight();
	};
	
	var showsurgeryPageCallBackFn = function(subPageTitle,cattleSelected,processDt){
		$("#cms-add-update-surgery").removeClass("hidden");	
		$("#breadcrumb-subpage").html(subPageTitle);
		$("#cmsSurgeryTitle").html(subPageTitle);
		if(!cattleSelected || cattleSelected == 0){
			$("#surgillid").val("0");
		}
		resetSurgeryPage();
		loadAddUpdatePage(cattleSelected,processDt);
		adjustScreen.contentHeight();
	};
	
	
	var showSurgeryAddOrUpdatePage = function(subPageTitle,cattleId,procDt){
		var loadStatus= $("#cms-add-update-surgery").data("load-status");
		if(!!loadStatus){
			showsurgeryPageCallBackFn(subPageTitle,cattleId,procDt);
		}else{
			$("#cms-add-update-surgery").load("partials/surgery-add-update.html", function(response, status, xhr){
	            if(status === "success"){
	            	CMSVetCons.surgeryTreatment = [];
	            	$("#cms-add-update-surgery").data("load-status",true);
	            	showsurgeryPageCallBackFn(subPageTitle,cattleId,procDt);
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
			
			// Loading Treatment Modal 
			$("#treatment-add-modal").load("partials/surgery-treatment-details.html", function(response, status, xhr){
	            if(status === "success"){
	            	displayTreatmentPage();
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}  
	};
	var saveSurgery = function(e){
		try{
			$("#ip-error-cattleid").hide();
			$("#ip-error-vetid").hide();
			$("#ip-error-surgType").hide();
			$("#ip-error-temp").hide();
			$("#ip-error-weight").hide();
		if($("#cms-saveSurgey-form")[0].checkValidity()){	
			var urlObj=CMSVetCons.urls_info["saveSurgeryIllness"],
				saveDtls={},
		 		reqData=jQuery.extend({}, CMSVetCommon.getInitReqData()),
		 		surgillid = $("#surgillid").val()*1
		 		msg = "";
			$("#cms-saveSurgey-form .form-dt").each(function(){
				saveDtls[$(this).prop("name")]=$(this).val();
			});
			$("#cms-saveSurgey-form .form-dt-num").each(function(){
				saveDtls[$(this).prop("name")]=$(this).val()*1;
			});	
			if(CMSVetCons.surgeryTreatment.length >0){
				saveDtls['listDetails'] = CMSVetCons.surgeryTreatment;
			}
			
			var cattleSelected = $("#cattleSelectid").val()*1;
			saveDtls['cattle'] = {"cattleid":cattleSelected};
			var consultant = $("input:radio[name='consultant']:checked").val()*1,
			   vetid = $("#selectvetid").val()*1,
			   surgeryType = $("#addsurgerytype").val()*1,
			   surgeryCreateddt=$("#surgery-createddt").val(),
			   procdt = $("#surgillprocdt").val(),
			   weight = $("#surgillweight").val()*1,
			   temp = $("#surgilltemp").val()*1;
			
			if($("#surgRequired").is(':checked')){
				saveDtls['surgillsurgeryid'] = surgeryType;
			}
			if(consultant === 0){
				saveDtls['vet'] = {"vetid":0};
			}else{
				vetid = $("#selectvetid").val()*1;
				saveDtls['vet'] = {"vetid":vetid};
			}
			if(!procdt){
				var saveMsg="Please Select a process Date"; 										
				CMSMessage.showSubPageMsg({"status":0,"msg":saveMsg});
			}else if(!weight){
				CMSMessage.showSubPageMsg({"status":0,"msg":"Enter weight of goat "});
				$("#surgillweight").focus();
			}else if(!temp){
				CMSMessage.showSubPageMsg({"status":0,"msg":"Enter temperature of goat"});
				$("#surgilltemp").focus();
			}else if(!cattleSelected || cattleSelected === 0){
				CMSMessage.showSubPageMsg({"status":0,"msg":"Select a goat"});
				$("#cattleSelectid").focus();
			}else if(consultant===1 && (!vetid || vetid === 0)){
				CMSMessage.showSubPageMsg({"status":0,"msg":"Select a Vet"});
				$("#selectvetid").focus();
			}else if($("#surgRequired").is(':checked') && (!surgeryType || surgeryType=== 0)){
				CMSMessage.showSubPageMsg({"status":0,"msg":"Select Surgery Type"});
				$("#addsurgerytype").focus();
			}else{
				if(surgillid > 0){
					saveDtls["surgillid"] = surgillid;
				}
				saveDtls["surgillprocdt"] =CMSCom.dateFn.cusDateServerFormat(saveDtls["surgillprocdt"] );
				if(!!surgeryCreateddt&&surgeryCreateddt*1!==0){
					saveDtls["createddt"]=surgeryCreateddt; 
				}
				reqData["reqData"]=saveDtls;			
				reqData=JSON.stringify(reqData);
			//	console.log("in save ",reqData);
				CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {				
					if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
						if(jsonResData.status==="1"){
							
							var resData=JSON.parse(jsonResData.resData);							
							
							msg=surgillid*1===0?" Surgery/Illness successfully saved ":"Surgery/Illness successfully updated  ";
							window.location.hash = "surgery-illness-details";	
							$("#tmpProcDt").val("0");
							$("#tmpCattleId").val("0");
							$("#surgillid").val("0");
							$("#surgery-createddt").val("0");
							CMSVetCons.surgeryEdit = false;
							
							resetSurgeryPage();
							CMSMessage.showSubPageMsg({"status":1,"msg":msg});
						}else{
						
							CMSMessage.showSubPageMsg(jsonResData);
						}	
						
					}					
				}).fail(CMSCom.exceptionHandler.ajaxFailure);	
			}
		}
		}catch(er){
			console.log("e ",er)
		}
		e.preventDefault();
		
	};
	var resetSurgeryPage = function(){
		$("#cms-saveSurgey-form .form-dt").each(function(){
			$(this).val("");
		});
		$("#cms-saveSurgey-form .form-dt-num").each(function(){
			$(this).val("");
		});
		$("#cms-saveSurgey-form .form-dt-refer").each(function(){
			$(this).val(0);
			$(this).trigger("chosen:updated");
		});
		$("#cms-saveSurgey-form .form-dt-chk").each(function(){
			$(this).prop('checked',false);
		});
		/*$("#surgerytypeDiv").addClass('hidden');
		//$(".consultant").trigger('click');
		$("#vetDiv").addClass('hidden');*/
		
		CMSVetCons.surgeryTreatment = [];
		$("#treatmentDataTableHolder").addClass('hidden');
		$("#addsurgerytype").trigger("chosen:updated");
		$("input[name=consultant][value=" + 0 + "]").prop('checked', true);
		if($("#surgillid").val()*1 >0 && (!!CMSVetCons.surgeryEdit)){
			var cattleSelected = $("#tmpCattleId").val()*1;
			var processDt = $("#tmpProcDt").val();
			loadSurgeryDtlsforSelected(cattleSelected,processDt);
		}else{
			//$("#addsurgerytype").html("");
			$("#addsurgerytype").trigger("chosen:updated");
			$("#tmpProcDt").val("0");
			$("#tmpCattleId").val("0");
			$("#surgillid").val("0");
			if(!$("#surgRequired").is(':checked')){
				$("#surgerytypeDiv").addClass('hidden');
			}
			var consultant = $("input:radio[name='consultant']:checked").val()*1;
			if(consultant === 0){
				$("#vetDiv").addClass('hidden');
			}
		}
	};
	
	var resetTreatmentDtls = function(){
		$("#cms-saveTreatmentDtls-form .form-dt").each(function(){
			$(this).val("");
		});
		$("#cms-saveTreatmentDtls-form .form-dt-num").each(function(){
			$(this).val("");
		});
		$("#surgilldetdoseunit").val(0);
		$("#surgilldetdoseunit").trigger("chosen:updated");
		var index = $("#index").val()*1;
		if(index !== 1000){
			showTreatmentObj(index);
		}else{
			$("#index").val("1000");
		}
	};
	var showTreatmentmodal = function(){
		$("#index").val("1000");
		resetTreatmentDtls();
		$("#myModalLabel").html("Add Treatment Details");
		$("#surgeryTreatmentModal").modal("show");
		
	};
	var loadCattleDtls = function(){
		var farmId = $(this).val();
		if(!!farmId){
			getGotDetailsByFarm(farmId,null);
		}else{
			$("#cattleSelectid").html("");
			$("#cattleSelectid").trigger("chosen:updated");
		}
	}
	
	var getGotDetailsByFarm = function(farmid,cattleId){
		var urlObj=CMSVetCons.urls_info["getCattleDetailsByFarm"],
	 	reqData=jQuery.extend({}, CMSVetCommon.getInitReqData());		
		reqData["reqData"]=JSON.stringify({});
		reqData["farmid"]=farmid;		
		if(!!farmid&&farmid!==0){
			CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {
				if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
					displayGotDetails(jsonResData,cattleId);				
				}	
			}).fail(CMSCom.exceptionHandler.ajaxFailure);	
		}	
	};
	
	var displayGotDetails = function(response,cattleId){
		
		var details = JSON.parse(response),
		surgillprocdt	=$("#surgillprocdt").val(),
		surgillprocdtObj = $("#surgillprocdt").data("datepicker").getDate();
		selectOptHtml="<option value=''></option>";	

		if(details.status==="1"){
			
			resData=JSON.parse(details.resData),
			cattleDtls=resData["cattle_dtls"];
			var nocattle=true;
			for(var i in cattleDtls){	
				var cattleObj=cattleDtls[i];
					if(cattleObj["cattleid"] >0 && (!!cattleObj["cattlestatus"])){
						
						var cattledobObj=new Date(cattleObj.cattledob),
						surgillprocDiff=CMSCom.dateFn.daysBetween(surgillprocdtObj,cattledobObj);
						if(surgillprocDiff<0){
							selectOptHtml+="<option value='"+cattleObj["cattleid"]+"'>"+cattleObj["cattleeartagid"]+"</option>";	
							nocattle=false;
						}
						
					}else{
					//	var saveMsg="There is no Cattle in the farm selected "; 										
					//	CMSMessage.showSubPageMsg({"status":0,"msg":saveMsg});	
					}
			}
			
			if(nocattle){
				var saveMsg="No active Goat with Date of Birth less than \""+surgillprocdt+"\""; 										
				CMSMessage.showSubPageMsg({"status":0,"msg":saveMsg});	
			}
			
		}
		$("#cattleSelectid").html(selectOptHtml);
		if(!!cattleId){
			$("#cattleSelectid").val(cattleId);
		}
		$("#cattleSelectid").chosen({allow_single_deselect: true});
		$("#cattleSelectid").trigger("chosen:updated");
		
	};
	var assignVet = function(){
		if(($(this).val()*1 )===1){
			$("#vetDiv").removeClass('hidden');
		}else{
			$("#vetDiv").addClass('hidden');
		}
	};
	
	var showSurgeryMaster = function(){
		if($(this).is(':checked')){
			$("#surgerytypeDiv").removeClass('hidden');
		}else{
			$("#surgerytypeDiv").addClass('hidden');
		}
	};
	var showTreatmentTable = function(){
		
		$("#treatmentDataTableHolder").removeClass('hidden');
		if(!!CMSVetCons.dataTable["surgery"].showFlg){
			console.log("update ---",CMSVetCons.dataTable["surgery"].dtFn);
			CMSVetCons.dataTable["surgery"].dtFn.fnFilter('');
			CMSVetCons.dataTable["surgery"].dtFn.fnClearTable();	
			CMSVetCons.dataTable["surgery"].dtFn.fnDrawCallback=function(){	
				setTimeout(function(){
					fixSurgeryDataTableHeight();
				},1000);
		};
			if(CMSVetCons.surgeryTreatment.length>0){
				CMSVetCons.dataTable["surgery"].dtFn.fnAddData(CMSVetCons.surgeryTreatment);	
				//CMSVetCons.dataTable["surgery"].dtFn.fnSetColumnVis( 0, false );
			}
		}else{	

			CMSVetCons.dataTable["surgery"].dtFn=$('#treatmentDataTable').dataTable({
				"bDestroy": true,
			   "aaData": CMSVetCons.surgeryTreatment,
			       "aoColumns": [{
			       "mDataProp": "surgilldetname"
			   }, {
			       "mDataProp": "surgilldetdose"
			   }, {
			       "mDataProp": "surgilldetdoseunit"
			   }, {
			       "mDataProp": "surgilldettreat"
			   }, {
			       "mDataProp": "surgilldetname",
			       "mRender": function (val,type, full,i) {
			    	 
			       	var actionHtml="<a href='javascript:void(0);' class='vet-editLink' id ='treatment-edit' data-treatment-id='"+i.row+"'><i class='fa fa-pencil-square-o'></i> Edit </a> ";
		    		/*actionHtml+="<a href='javascript:void(0);' class='vet-editLink' id ='treatment-delete' data-treatment-id='"+i.row+"'><i class='fa fa-trash'></i> Delete</a> ";*/
			       	return actionHtml;
			       }
			   }],
			   "fnInitComplete" :function(){	
			    	setTimeout(function(){
						$( "#treatmentDataTable" ).wrapAll( "<div class='table-responsive'></div>" );	
						  adjustScreen.contentHeight();
					},100)
				  },
				  fnDrawCallback : function() {
			
						setTimeout(function(){
							fixSurgeryDataTableHeight();
							  adjustScreen.contentHeight();
						},1000);
				  }
			});
			var oSettings = CMSVetCons.dataTable["surgery"].dtFn.fnSettings();
			//console.log(" dtFn ",CMSVetCons.dataTable["surgery"].dtFn);
			//console.log(" oSettings ",oSettings);
		    oSettings._iDisplayLength = 5; 			    
		   // CMSVetCons.dataTable["surgery"].dtFn.fnSetColumnVis( 0, false );
		    CMSVetCons.dataTable["surgery"].showFlg=true;

		}
		
		adjustScreen.mCustomScrollbar_FixDataTablePagin("treatmentDataTable");
		//fixSurgeryDataTableHeight();
		    
	};


	var addTreatmentDtls = function(e) {
		var treatmentObj = {};
		try {
			if ($("#cms-saveTreatmentDtls-form")[0].checkValidity()) {
				var unit = $("#surgilldetdoseunit").val(),
				 dose = $("#surgilldetdose").val()*1,
				 name = $("#surgilldetname").val(),
				  msgObj ="";
				if(!name || name.trim().length ===0){
				    msgObj={"success":0,"msg":"Enter Name"};
					CMSMessage.showCommonModalMsg(msgObj,"#surgeryTreatmentModal");
					$("#surgilldetname").focus();
				}else if(!dose){
					msgObj={"success":0,"msg":"Enter Dose"};
					CMSMessage.showCommonModalMsg(msgObj,"#surgeryTreatmentModal");
					$("#surgilldetdose").focus();
				}else if(!unit || unit.trim().length===0){
					msgObj={"success":0,"msg":"Select Unit"};
					CMSMessage.showCommonModalMsg(msgObj,"#surgeryTreatmentModal");
					$("#surgilldetdoseunit").focus();
				}else{
				$("#cms-saveTreatmentDtls-form .form-dt").each(function() {
					treatmentObj[$(this).prop("name")] = $(this).val();
				});
				$("#cms-saveTreatmentDtls-form .form-dt-num").each(function() {
					treatmentObj[$(this).prop("name")] = $(this).val() * 1;
				});
				// surgilldetid
				if($("#index").val()*1 !== 1000){
					var index = $("#index").val()*1 ;
					CMSVetCons.surgeryTreatment[index] = treatmentObj;
				}else{
					CMSVetCons.surgeryTreatment.push(treatmentObj);
				}
				
				showTreatmentTable();
				$("#index").val("1000");
				resetTreatmentDtls();
				$("#surgeryTreatmentModal").modal("hide");
			}
				
			}
		} catch (er) {
			console.log("e ", er)
		}
		e.preventDefault();
	};
	var cancelTreatmentDtls = function(){
		resetTreatmentDtls();
		$("#surgeryTreatmentModal").modal("hide");
	};
	var fetchEditData = function(){
		var cattleSelected = $("#cattleSelectid").val()*1;
		if(cattleSelected > 0){
			var processDt = $("#surgillprocdt").val();
			if(!!processDt && (!CMSVetCons.surgeryEdit)){
				loadSurgeryDtlsforSelected(cattleSelected,processDt);
			}
		}
		var farmId = $("#farmselectid").val()*1;
		getGotDetailsByFarm(farmId,cattleSelected);
	};
	var loadSurgeryDtls = function(){
		
		if(!CMSVetCons.surgeryEdit){
			var cattleSelected = $("#cattleSelectid").val()*1;
			if(!!cattleSelected && cattleSelected!==0){
				var processDt = $("#surgillprocdt").val();
				if(!!processDt){
					loadSurgeryDtlsforSelected(cattleSelected,processDt);
				}else{
					/*$("#cattleSelectid").val(0);
					$("#cattleSelectid").trigger("chosen:updated");*/
					var saveMsg="Please Select a process Date"; 										
					CMSMessage.showSubPageMsg({"status":0,"msg":saveMsg});	
				}
			}
			
		}
		
	};
	
	var loadSurgeryDtlsforSelected = function(cattleSelected,processDt){
		var urlObj=CMSVetCons.urls_info["getSurgeryIllness"],
	 	reqData=jQuery.extend({}, CMSVetCommon.getInitReqData());		
		
		var reqObj = {};
		reqObj['cattle'] = {"cattleid":cattleSelected};
		reqObj["surgillprocdt"] = CMSCom.dateFn.cusDateServerFormat(processDt);
		reqData["reqData"]=reqObj;			
		reqData=JSON.stringify(reqData);

			CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {
				if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
					if(jsonResData.status==="1"){
						displaySurgeryDetails(jsonResData);				
					}else{
						resetPage();
					}
				}	
			}).fail(CMSCom.exceptionHandler.ajaxFailure);	
		
	};
	
	var resetPage = function(){
		$("#cms-saveSurgey-form .form-dt-num").each(function(){
			$(this).val("");
		});
		$("#surgillsymp").val("");
		$("#surgillassess").val("");
		if(!CMSVetCons.surgeryEdit){
			$("#tmpProcDt").val("0");
			$("#tmpCattleId").val("0");
			$("#surgillid").val("0");
			$("#index").val("1000");
		}
		CMSVetCons.surgeryTreatment = [];
		$("#treatmentDataTableHolder").addClass('hidden');
		//showTreatmentTable();
	}
	
	var displaySurgeryDetails = function(jsonResData){
		var resData =JSON.parse(jsonResData.resData);
		var surgeryIllness = resData.surgeryIllness[0];
		if(!!resData.surgeryTreatment){
			CMSVetCons.surgeryTreatment = resData.surgeryTreatment;
		}
			for (var i = 0; i < CMSVetCons.surgeryTreatment.length; i++) {
				var obj = CMSVetCons.surgeryTreatment[i];
				delete obj.surgIllness;
				delete obj.surgilldetid;
			}
		
		$("#cms-saveSurgey-form .form-dt").each(function(){
			if($(this).prop("name") === "surgillprocdt"){
				 $(this).val(CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(surgeryIllness[$(this).prop("name")])));
			}else{
				$(this).val(surgeryIllness[$(this).prop("name")])
			}
			//
		});
		$("#cms-saveSurgey-form .form-dt-num").each(function(){
			$(this).val(surgeryIllness[$(this).prop("name")])
		});
		
		
		$("#surgillid").val(surgeryIllness.surgillid);
		var farmid = surgeryIllness.cattle.farm.farmid;
		$("#farmselectid").val(farmid);
		$("#farmselectid").trigger("chosen:updated");
		var cattleid = surgeryIllness.cattle.cattleid;
		getGotDetailsByFarm(farmid,cattleid);
		var uId = surgeryIllness.vet.objUserEntity.id;
		var vetId = surgeryIllness.vet.vetid;
		selectConsultant(uId,vetId);
		var surgId =surgeryIllness.surgillsurgeryid;
		if(!!surgId){
			$("#surgRequired").prop('checked',true);
			$("#surgerytypeDiv").removeClass('hidden');
			$("#addsurgerytype").val(surgId);
			$("#addsurgerytype").trigger("chosen:updated");
			
		}else{
			$("#surgRequired").prop('checked',false);
		}
		$("#tmpProcDt").val($("#surgillprocdt").val());
		$("#tmpCattleId").val(cattleid);
		
		if (!!CMSVetCons.surgeryTreatment && CMSVetCons.surgeryTreatment.length > 0) {
			showTreatmentTable();
		}
		$("#surgery-createddt").val(CMSCom.dateFn.cusDateAsDD_MM_YYYY_TS(new Date(surgeryIllness["createddt"])));
	};
	var selectConsultant = function(uId,vetId){
		var userId = CMSVetCommon.getUserId();
		if(uId == userId){
			$("input[name=consultant][value=" + 0 + "]").prop('checked', true);
		//	$("#vetDiv").addClass('hidden');
		}else{
			$("input[name=consultant][value=" + 1 + "]").prop('checked', true);
			$("#vetDiv").removeClass('hidden');
			$("#selectvetid").val(vetId);
			$("#selectvetid").trigger("chosen:updated");
			console.log("has Classs",$("#vetDiv").hasClass('hidden'));
		}
	};
	var editSurgery =function(){
		var cattleId=$(this).data("cattle-id");
		var procDt = $(this).data("process-dt");
		CMSVetCons.surgeryEdit = true;
		window.location.hash = "edit-surgery-illness="+cattleId+"&"+procDt;
	};
	var editTreatment = function(){
		$("#myModalLabel").html("Edit Treatment Details");
		var index = $(this).data("treatment-id");
		$("#index").val(index);
		showTreatmentObj(index);
	};
	
	var showTreatmentObj = function(index){
		var treatmentObj = {};
		treatmentObj = CMSVetCons.surgeryTreatment[index] ;
		$("#cms-saveTreatmentDtls-form .form-dt").each(function(){
			$(this).val(treatmentObj[$(this).prop("name")]);
		});
		$("#cms-saveTreatmentDtls-form .form-dt-num").each(function(){
			$(this).val(treatmentObj[$(this).prop("name")]);
		});
		
		 $("#surgilldetdoseunit").trigger("chosen:updated");
		$("#surgeryTreatmentModal").modal("show");
	};
	return {
		showSurgeryDetlsPage:showSurgeryDetlsPage,
		showSurgeryAddOrUpdatePage:showSurgeryAddOrUpdatePage,
		showTreatmentmodal:showTreatmentmodal,
		loadCattleDtls:loadCattleDtls,
		assignVet:assignVet,
		showSurgeryMaster:showSurgeryMaster,
		addTreatmentDtls:addTreatmentDtls,
		resetTreatmentDtls:resetTreatmentDtls,
		cancelTreatmentDtls:cancelTreatmentDtls,
		loadSurgeryDtls:loadSurgeryDtls,
		saveSurgery:saveSurgery,
		resetSurgeryPage:resetSurgeryPage,
		editSurgery:editSurgery,
		editTreatment:editTreatment,
		fetchEditData:fetchEditData
	}
})();

