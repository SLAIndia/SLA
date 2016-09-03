
var VetVaccination=(function(){
	
	var loadVetDataTable=function(){

		if(!!CMSVetCons.dataTable["vaccination"].showFlg){
			CMSVetCons.dataTable["vaccination"].dtFn._fnAjaxUpdate();
			CMSVetCons.dataTable["vaccination"].dtFn.fnSetColumnVis( 0, false );			
		}else{	
			var	reqData=jQuery.extend({}, CMSVetCommon.getInitReqData()),
				urlObj=CMSVetCons.urls_info["vaccinationDetails"],
				req={};     
			
			reqData["reqData"]=req;				
			$.fn.dataTableExt.sErrMode = 'throw';
			CMSVetCons.dataTable["vaccination"].dtFn=$('#vaccinationDataTable').dataTable({
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
				    		jsonRes[key][0] = eachObj.vaccinationid;
				    		jsonRes[key][1] = eachObj.vaccinationname;
				    		jsonRes[key][2] = eachObj.vaccinationtype;
				    		jsonRes[key][3] = eachObj.vaccinationdose + " "+eachObj.vaccinationunit;
				    		/*jsonRes[key][4] = eachObj.vaccinationexpdt;	*/			    	
			
		    		
				    		var actionHtml="<a href='javascript:void(0);' class='vet-editLink' id ='vet-edit' data-vet-id='"+eachObj.vaccinationid+"'><i class='fa fa-pencil-square-o'></i> Edit </a> ";
				    		actionHtml+="<a href='javascript:void(0);' class='vet-editLink' id ='vet-delete' data-vet-id='"+eachObj.vaccinationid+"'><i class='fa fa-trash'></i> Delete</a> ";
				    		jsonRes[key][4] =actionHtml; 
				    		
				    	} 
						setTimeout(function(){
							$( "#vaccinationDataTable" ).wrapAll( "<div class='table-responsive'></div>" );
							$(".commonScroll").mCustomScrollbar({theme:"inset-2-dark"});
							
						},100)
					    return jsonRes;			   
				    }	
			    }
			  },
			  destroy: true,
			  "bStateSave": true,
			  "aaSorting": [[ 0, "desc" ]],
			  "aoColumnDefs": [
			       { 'bSortable': false, 'aTargets': [4] }
			   ],
			  'processing': false,
			  'serverSide': false,
			  'bLengthChange': false, 
			  'fnInitComplete' :function(){	
				 // after initial loading completes event
			  },
			  fnDrawCallback : function() {
								
			  }			  
			});
			 var oSettings = CMSVetCons.dataTable["vaccination"].dtFn.fnSettings();
		     oSettings._iDisplayLength = 10; 			     
		     CMSVetCons.dataTable["vaccination"].dtFn.fnSetColumnVis( 0, false );
		}	
	};
	var showVaccinationDetlsPageCallBackFn=function(){
		$("#cms-vaccination-details").removeClass("hidden");	
		$("#breadcrumb-subpage").html("Health Care Details");
		loadVetDataTable();
		adjustScreen.contentHeight();
	};
	var showVaccinationDetlsPage=function(){

		var loadStatus= $("#cms-vaccination-details").data("load-status");
		if(!!loadStatus){
			showVaccinationDetlsPageCallBackFn();
		}else{
			$("#cms-vaccination-details").load("partials/vaccination-list.html", function(response, status, xhr){
	            if(status === "success"){
	            	$("#cms-vaccination-details").data("load-status",true);
	            	showVaccinationDetlsPageCallBackFn();
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}       
	};
	
	var displayAddUpdatePage=function(){
		
			
		var	selectOptHtml="<option value=''></option>";		
		var vacType = [{"id":"Anti-Biotic","type":"Anti-Biotic"}];
		for(var i in vacType){	
			var typeObj=vacType[i];
				selectOptHtml+="<option value='"+typeObj["id"]+"'>"+typeObj["type"]+"</option>";		
		}
		
		$("#vaccinationtype").html(selectOptHtml);
		$("#vaccinationtype").chosen({allow_single_deselect: true});
		
		var vacUnit = [{"id":"mg","type":"mg"},{"id":"ml","type":"ml"}];
		selectOptHtml="<option value=''></option>";	
		for(var i in vacUnit){	
			var typeObj=vacUnit[i];
				selectOptHtml+="<option value='"+typeObj["id"]+"'>"+typeObj["type"]+"</option>";		
		}
		$("#vaccinationunit").html(selectOptHtml);
		$("#vaccinationunit").chosen({allow_single_deselect: true});
		
		 /*$('#vaccinationexpdt').datepicker({ format: "dd-mm-yyyy",startDate: new Date()	         
	     }).on('changeDate', function(e){
	    	    $(this).datepicker('hide');
	     }).prop('readonly','readonly');
		 $("#vaccinationexpdt").off("click").on("click",function() {			 
		        $(this).datepicker().datepicker("show");
		 });*/
		 setTimeout(function(){
			 //$("#vaccinationtype").val(0)
			 $("#vaccinationtype").trigger("chosen:updated");
		 });
	};
	
	var populateVaccineEdit = function(jsonResData,vacId){
		var vaccinDtls =JSON.parse(jsonResData.resData);
		var vaccinObj = vaccinDtls[0];
		displayAddUpdatePage();
		$("#vaccinationid").val(vacId);
		$("#cms-saveVaccination-form .form-dt").each(function(){
			$(this).val(vaccinObj[$(this).prop("name")])
		});
		$("#cms-saveVaccination-form .form-dt-num").each(function(){
			$(this).val(vaccinObj[$(this).prop("name")])
		});
		$("#cms-saveVaccination-form .form-dt-date").each(function(){
			$(this).val(vaccinObj[$(this).prop("name")]);
		});
		$("#vaccinationtype").trigger("chosen:updated");
		$("#vaccinationunit").trigger("chosen:updated");
		
	};
	
	var fetchVaccinationDetais = function(vacId){
		var urlObj=CMSVetCons.urls_info["vaccinationDetails"],
		reqData=jQuery.extend({}, CMSVetCommon.getInitReqData());	
	
		reqData["reqData"]={"vaccinationid":vacId};
		reqData=JSON.stringify(reqData);
		CMSCom.ajaxService.getJsonData(urlObj,reqData).done(function(jsonResData) {
			if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){	
				if(jsonResData.status*1===1){		
						populateVaccineEdit(jsonResData,vacId);
				}else{
					// no 
				}
			}	
		}).fail(CMSCom.exceptionHandler.ajaxFailure);	
	};
	var deleteVaccinationDetais = function(vacId){
		var urlObj=CMSVetCons.urls_info["deleteVaccination"],
		reqData=jQuery.extend({}, CMSVetCommon.getInitReqData()),
		msg = "";	
	
		reqData["reqData"]={"vaccinationid":vacId};
		reqData=JSON.stringify(reqData);
		CMSCom.ajaxService.getJsonData(urlObj,reqData).done(function(jsonResData) {
			if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){	
				if(jsonResData.status*1===1){		
					loadVetDataTable();
					$(".commonScroll").mCustomScrollbar({theme:"inset-2-dark"});
					msg="Vaccination successfully Deleted  ";
					window.location.hash = "vaccination-list";			
					CMSMessage.showSubPageMsg({"status":1,"msg":msg});
				}else{
					// no 
				}
			}	
		}).fail(CMSCom.exceptionHandler.ajaxFailure);	
	};
	
	var showVaccinationPageCallBackFn=function(subPageTitle,editId){
		$("#cms-add-update-vaccination").removeClass("hidden");	
		$("#breadcrumb-subpage").html("Health Care Details");
		$("#cmsVaccinationTitle").html(subPageTitle);
		if(!!editId){			
			fetchVaccinationDetais(editId);
		}else{
			displayAddUpdatePage();	
			$("#vaccinationid").val("0");
			resetVaccination();
		}
		adjustScreen.contentHeight();
	};
	
	
	var showVaccinationAddOrUpdatePage = function(subPageTitle,editId){
		var loadStatus= $("#cms-add-update-vaccination").data("load-status");
		if(!!loadStatus){
			showVaccinationPageCallBackFn(subPageTitle,editId);
		}else{
			$("#cms-add-update-vaccination").load("partials/vaccination-add-update.html", function(response, status, xhr){
	            if(status === "success"){
	            	$("#cms-add-update-vaccination").data("load-status",true);
	            	showVaccinationPageCallBackFn(subPageTitle,editId);
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}  
	};
	var saveVaccination=function(e){
		try{
		if($("#cms-saveVaccination-form")[0].checkValidity()){		
			var urlObj=CMSVetCons.urls_info["saveVaccination"],
				saveDtls={},
		 		reqData=jQuery.extend({}, CMSVetCommon.getInitReqData()),
		 		vaccinationidEl = $("#vaccinationid"),
		 		vaccinationid = vaccinationidEl.val()
		 		msg = "";
			$("#cms-saveVaccination-form .form-dt").each(function(){
				saveDtls[$(this).prop("name")]=$(this).val().trim();
			});
			$("#cms-saveVaccination-form .form-dt-num").each(function(){
				saveDtls[$(this).prop("name")]=$(this).val()*1;
			});		
			$("#cms-saveVaccination-form .form-dt-date").each(function(){
				if(!!$(this).val() && $(this).val() !=0){
					saveDtls[$(this).prop("name")]=$(this).val();
				}
			});	
			var type = $("#vaccinationtype").val(),
			 unit = $("#vaccinationunit").val();
			if (!type || type.trim().length===0){
				$("#ipt-er-vaccinationtype small").html("Select a Vaccination Type");
				$("#ipt-er-vaccinationtype").show();
			}
			else if (!unit || unit.trim().length===0){
				$("#ipt-er-vaccinationunit small").html("Select a Unit");
				$("#ipt-er-vaccinationunit").show();
			}else{
				if(vaccinationid > 0){
					saveDtls["vaccinationid"] = vaccinationid;
				}
				reqData["reqData"]=saveDtls;			
				reqData=JSON.stringify(reqData);
				CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {				
					if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
						if(jsonResData.status==="1"){
							
							var resData=JSON.parse(jsonResData.resData);							
							
							msg=vaccinationid*1===0?"Vaccination successfully saved ":"Vaccination successfully updated  ";
							window.location.hash = "vaccination-list";			
							$("#vaccinationid").val("0");
							$("#vac-createddt").val("0");
							resetVaccination();
							CMSMessage.showSubPageMsg({"status":1,"msg":msg});
						}else if(jsonResData.status==="13"){
							msg= "Same Vaccination Details already Exist" ;
							CMSMessage.showSubPageMsg({"status":0,"msg":msg});
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
	var resetVaccination = function(){
		$("#ipt-er-vaccinationtype").hide();
		$("#ipt-er-vaccinationunit").hide();
		$("#cms-saveVaccination-form .form-dt").each(function(){
			$(this).val("");
		});
		$("#cms-saveVaccination-form .form-dt-num").each(function(){
			$(this).val("");
		});
		 $("#vaccinationtype").val(0)
		 $("#vaccinationtype").trigger("chosen:updated");
		 $("#vaccinationunit").val(0)
		 $("#vaccinationunit").trigger("chosen:updated");
		 var vacId =  $("#vaccinationid").val()*1;
		 if(vacId > 0){
			 showVaccinationAddOrUpdatePage('Edit Vaccination',vacId);
		 }else{
			 $("#vaccinationid").val("0");
		 }
		
	};
	
	var editVet = function(){
		var vacId=$(this).data("vet-id");
		window.location.hash = "edit-vaccination="+vacId;	
	};
	var deleteVet = function(){
		var vacId=$(this).data("vet-id");
		deleteVaccinationDetais(vacId);
	};
	
	return {
		showVaccinationDetlsPage:showVaccinationDetlsPage,
		showVaccinationAddOrUpdatePage:showVaccinationAddOrUpdatePage,
		saveVaccination:saveVaccination,
		resetVaccination:resetVaccination,
		editVet:editVet,
		deleteVet:deleteVet
	}
})();

