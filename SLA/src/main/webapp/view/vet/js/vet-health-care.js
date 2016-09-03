var VetHealthCare=(function(){
	
	var setHealthDateCalendar=function(elId,futureDate){
		 $(elId).datepicker({ 
			 format: "dd-mm-yyyy",
			 endDate:  futureDate,
			 forceParse: false 	         
	     }).on('changeDate', function(e){
	    	    $(this).datepicker('hide');
	     }).prop('readonly','readonly');

		 $(elId).off("click").on("click",function() {

		        $(elId).datepicker().datepicker("show");
		 });
		 $(elId+"-calen").off("click").on("click",function() {

		        $(elId).datepicker().datepicker("show");
		 });
	};
	
	var updateBreedingDateCalendar=function(formId,elId,futureDate){
     	$(formId+" "+elId).datepicker({ format: "dd-mm-yyyy",endDate:futureDate }); 
     	$(formId+" "+elId).datepicker('update');
	};
	
	var displayVetDtls=function(jsonResData){		
		
		if(jsonResData.status==="1"){
			var vetDtlsArr=JSON.parse(jsonResData.resData),	
				selectVetOptHtml="<option value=''></option>";					
			$("#hcvetid").html("");		
			$("#hcdewormvetid").html("");	
			for(var i in vetDtlsArr){	
				var vetDtlsObj=vetDtlsArr[i];
				if(vetDtlsObj.vetStatus*1===1){
					selectVetOptHtml+="<option value='"+vetDtlsObj["vetid"]+"' >"+vetDtlsObj.objUserEntity["username"]+"</option>";	
				}									
			}			
			$("#hcvetid").html(selectVetOptHtml);								
			$("#hcvetid").trigger("chosen:updated");	
			$("#hcdewormvetid").html(selectVetOptHtml);								
			$("#hcdewormvetid").trigger("chosen:updated");	
		}		
	};
	
	// This to fetch vaccine details for select box in add vaccine  details 
	var fetchVetDtls=function(){
		var urlObj=CMSVetCons.urls_info["getVetdetails"],
	 		reqData=jQuery.extend({}, CMSVetCommon.getInitReqData()),
	 		queryDtls={id:0,role_name: "VET"};					
		reqData["reqData"]=queryDtls;
		reqData=JSON.stringify(reqData);	
		CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {
			if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){											
				displayVetDtls(jsonResData);																
			}	
		}).fail(CMSCom.exceptionHandler.ajaxFailure);	

	};
	
	var displayVaccineDtls=function(jsonResData){		
		
		if(jsonResData.status==="1"){
			var	vaccineDtlsArr=JSON.parse(jsonResData.resData),		
				selectCattleOptHtml="<option value=''></option>";		
			
			$("#hcvaccnName").html("");
			
			for(var i in vaccineDtlsArr){	
				var vaccineDtlsObj=vaccineDtlsArr[i];
				if(vaccineDtlsObj["vaccinationstatus"]*1===1){
					selectCattleOptHtml+="<option value='"+vaccineDtlsObj["vaccinationid"]+"' data-dose='"+vaccineDtlsObj["vaccinationdose"]+"' data-unit='"+vaccineDtlsObj["vaccinationunit"]+"'>"+vaccineDtlsObj["vaccinationname"]+"</option>";
				}									
			}
			
			$("#hcvaccnName").html(selectCattleOptHtml);								
			$("#hcvaccnName").trigger("chosen:updated");						
		}		
	};
	
	// This to fetch vaccine details for select box in add vaccine  details 
	var fetchVaccineDtls=function(){
		var urlObj=CMSVetCons.urls_info["vaccinationDetails"],
	 		reqData=jQuery.extend({}, CMSVetCommon.getInitReqData()),
	 		queryDtls={};		
			
		reqData["reqData"]=queryDtls;

		reqData=JSON.stringify(reqData);	
		CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {
			if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){											
				displayVaccineDtls(jsonResData);																
			}	
		}).fail(CMSCom.exceptionHandler.ajaxFailure);	

	};

	
	var displayHealthCareAddOrUpdatePage=function(jsonResData){
		
		var resData=JSON.parse(jsonResData.resData),
			farmDtls=resData["farm_dtls"],		
			selectOptHtml="<option value=''></option>";		
	
		for(var i in farmDtls){	
			var farmObj=farmDtls[i];			
			if(!!farmObj.farm_status){
				selectOptHtml+="<option value='"+farmObj["farm_id"]+"'>"+farmObj["farm_name"]+"</option>";	
			}					
		}		
		$("#healthcareselectFarm").html(selectOptHtml);
		$("#healthcareselectFarm").trigger("chosen:updated");	
		
		 $('#healthcareselectDate').datepicker({ format: "dd-mm-yyyy",endDate: new Date(),forceParse: false 	         
	     }).on('changeDate', function(e){
	    	    $(this).datepicker('hide');
	    	    clearhealthcareselectDateOnChange();	    	    	    
	     }).prop('readonly','readonly');
		 
		 $("#healthcareselectDate").off("click").on("click",function() {			 
		        $(this).datepicker().datepicker("show");
		 });
		 $("#healthcareselectDate-calen").off("click").on("click",function() {			 
			 $("#healthcareselectDate").datepicker().datepicker("show");
		 });
	};
	var clearhealthcareselectDateOnChange=function(){
		if(!!CMSVetCons.dataTable["hcdeworming"].dtFn){
			CMSVetCons.dataTable["hcdeworming"].dtFn.fnClearTable();
		}
		if(!!CMSVetCons.dataTable["hcvaccine"].dtFn){
			CMSVetCons.dataTable["hcvaccine"].dtFn.fnClearTable();
		}
		
		
		$("#healthcareselectFarm").val(0);
		$("#healthcareselectType").val("vaccination");
		$("#healthcareselectCattle").val(0);
		$("#healthcareselectFarm").trigger("chosen:updated");				
		$("#healthcareselectType").trigger("chosen:updated");
		$("#healthcareselectCattle").trigger("chosen:updated");
		$("#vaccinationDetailsHolder").removeClass("hidden");
		$("#dewormingDetailsHolder").addClass("hidden");
	};
	
	
	var loadHealthCareAddOrUpdatePage=function(){
		
		var urlObj=CMSVetCons.urls_info["getFarmDetailsByUser"],
		 	reqData=jQuery.extend({}, CMSVetCommon.getInitReqData());		
			reqData["reqData"]={};
			reqData=JSON.stringify(reqData);
		
		// This is to fetch farm details for select a farm select box
		CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {
			if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
				if(jsonResData.status==="1"){
					displayHealthCareAddOrUpdatePage(jsonResData);
				}else{		    	  
					CMSMessage.showSubPageMsg({"status":0,"msg":"Farm not assigned, so unable to add goat health care "});
				}
				
			}	
		}).fail(CMSCom.exceptionHandler.ajaxFailure);	
	};
	
	var resetHealthCareAddOrUpdatePage=function(){
		$("#healthcareselectDate").val("");
		$("#healthcareselectFarm").val(0);
		$("#healthcareselectType").val("vaccination");
		$("#healthcareselectCattle").val(0);
		$("#healthcareselectFarm").trigger("chosen:updated");				
		$("#healthcareselectType").trigger("chosen:updated");
		$("#healthcareselectCattle").trigger("chosen:updated");
		
		if(!!CMSVetCons.dataTable["hcdeworming"].dtFn){
			CMSVetCons.dataTable["hcdeworming"].dtFn.fnClearTable();
		}
		if(!!CMSVetCons.dataTable["hcvaccine"].dtFn){
			CMSVetCons.dataTable["hcvaccine"].dtFn.fnClearTable();
		}
		$("#vaccinationDetailsHolder").removeClass("hidden");
		$("#dewormingDetailsHolder").addClass("hidden");
		$('#healthcareselectDate').prop('readonly','readonly');
		
		
	};
	
	var initshowHealthCareAddOrUpdatePage=function(){
		$("#healthcareselectFarm").chosen({allow_single_deselect: true});			
		$("#healthcareselectType").chosen({allow_single_deselect: true});
		$("#healthcareselectCattle").chosen({allow_single_deselect: true});
		loadHCVaccineDataTable([]);
		loadHCDewormingDataTable([]);
		
	};
	var initshowHealthCareAddVaccinDeWormePageModal=function(){		
		// initalize vaccination  modal 
		
		$("#healthcareselectRoutineAdmin").chosen({allow_single_deselect: true});
		$("#hcvaccnName").chosen({allow_single_deselect: true});
		$("#hcvetid").chosen({allow_single_deselect: true});
		$("#hcdewormvetid").chosen({allow_single_deselect: true});
		//vaccination modal
		setHealthDateCalendar("#hcvaccnDateVaccination",new Date());
		setHealthDateCalendar("#hcvaccnNextVaccination",null);
		//DeWorming modal
		setHealthDateCalendar("#hcdewormDateVaccination",new Date());
		setHealthDateCalendar("#hcdewormNextVaccination",null);
		//fetchVaccineDtls();
		//fetchVetDtls();
		
		
	};
	
	var showHealthCareAddOrUpdatePageCallBackFn=function(subPageTitle){

		$("#cms-vet-health-care-holder").removeClass("hidden");	
		$("#breadcrumb-subpage").html("Health Care Details");
		$("#cmsHealthCareDtlsTitle").html(subPageTitle);
		resetHealthCareAddOrUpdatePage();
		loadHealthCareAddOrUpdatePage();
		adjustScreen.contentHeight();
		fetchVaccineDtls();
		fetchVetDtls();
	};
	
	
	var showHealthCareAddOrUpdatePage = function(subPageTitle){
		var loadStatus= $("#cms-vet-health-care-holder").data("load-status");
		if(!!loadStatus){
			showHealthCareAddOrUpdatePageCallBackFn(subPageTitle);
		}else{
			
			$("#cms-vet-health-care-holder").load("partials/vet-health-care.html", function(response, status, xhr){
	            if(status === "success"){
	
	            	$("#cms-vet-health-care-holder").data("load-status",true);
	            	showHealthCareAddOrUpdatePageCallBackFn(subPageTitle);	   
	            	initshowHealthCareAddOrUpdatePage();
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
			
			// load vaccination and deworming modal 
			
			$("#cms-vet-healthcare-holder-modal").load("partials/vet-health-care-vaccin-deworm.html", function(response, status, xhr){
	            if(status === "success"){
	            	$("#cms-vet-healthcare-holder-modal").data("load-status",true);
	            	//showHealthCareAddOrUpdatePageCallBackFn(subPageTitle);	
	            	initshowHealthCareAddVaccinDeWormePageModal();
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}  
	};

	var addHealthCareTypeDtlsBtnHandler=function(){
		var healthcareselectType=$("#cms-vet-health-care-holder #healthcareselectType").val(),			
			dateOfservice=$("#healthcareselectDate").val(),
			farmid=$("#healthcareselectFarm").val()*1,
			cattleid=$("#healthcareselectCattle").val()*1;

		if(!!cattleid&&cattleid!==0){	
	
			if("vaccination"===healthcareselectType){			
				$("#hcvaccn-vaccid").val(0);
				hcVaccinResetHandler();			
				$("#cms-vet-healthcare-vaccine-modal .cmsModalTitle").html("Add Vaccination ");
				$("#cms-vet-healthcare-vaccine-modal").modal("show");
			}else if("deworming"===healthcareselectType){	
				$("#hcdeworming-vaccid").val(0);
				hcdewormingResetHandler();
				$("#cms-vet-healthcare-deworm-modal .cmsModalTitle").html("Add Routine Deworming ");
				$("#cms-vet-healthcare-deworm-modal").modal("show");
			}		
		}else{
			
			var errmsg="Please Select";
			
			if(!dateOfservice||dateOfservice.trim().length===0){
				errmsg+=" Date of Service";
			}else if(!farmid||farmid===0){
				errmsg+=" Farm";
			}else if(!cattleid||cattleid===0){
				errmsg+=" Cattle";
			}
			
			CMSMessage.showSubPageMsg({"status":0,"msg":errmsg});
		}
	
	};
	
	// for change event select healthcare 
	var healthcareselectTypeHandler=function(){
		var healthcareselectType=$("#cms-vet-health-care-holder #healthcareselectType").val();
		$("#vaccinationDetailsHolder").addClass("hidden");
		$("#dewormingDetailsHolder").addClass("hidden");
		if("vaccination"===healthcareselectType){			
			$("#vaccinationDetailsHolder").removeClass("hidden");
		}else if("deworming"===healthcareselectType){	
			$("#dewormingDetailsHolder").removeClass("hidden");		
		}
		  adjustScreen.contentHeight();
	};
	
	var displayCattleDetailsByFarm=function(cattleDtls){
		
		cattleDtls=JSON.parse(cattleDtls);
		
		if(cattleDtls.status==="1"){

			var cattleid=$("#cattleid").val(),
				resData=JSON.parse(cattleDtls.resData),
				cattleDtls=resData["cattle_dtls"],	
				selectCattleOptHtml="<option value=''></option>",
				healthcareselectDt	=$("#healthcareselectDate").val(),
				healthcareselectDtObj = $("#healthcareselectDate").data("datepicker").getDate(),
				noCattle=true;
			
			$("#healthcareselectCattle").html("");
			
			for(var i in cattleDtls){	
				var cattleObj=cattleDtls[i];	
				if(!!cattleObj["cattlestatus"]){
					var cattledobObj=new Date(cattleObj.cattledob),
					healthDtDiff=CMSCom.dateFn.daysBetween(healthcareselectDtObj,cattledobObj);
			
					if(healthDtDiff<0){
						selectCattleOptHtml+="<option value='"+cattleObj["cattleid"]+"'>"+cattleObj["cattleeartagid"]+"</option>";
						noCattle=false;
					}
						
				}								
			}
			if(noCattle){	
				var saveMsg="No active Goat with Date of Birth on or before \""+healthcareselectDt+"\""; 										
				CMSMessage.showSubPageMsg({"status":0,"msg":saveMsg});	
			}
			$("#healthcareselectCattle").html(selectCattleOptHtml);								
			$("#healthcareselectCattle").trigger("chosen:updated");						
		}		
	};
	
	var getCattleDetailsByFarm=function(farmid){
		var urlObj=CMSVetCons.urls_info["getCattleDetailsByFarm"],
	 	reqData=jQuery.extend({}, CMSVetCommon.getInitReqData());		
		reqData["reqData"]=JSON.stringify({});
		reqData["farmid"]=farmid;
		
		$("#healthcareselectCattle").html("");
		$("#healthcareselectCattle").val(0);
		$("#healthcareselectCattle").trigger("chosen:updated");
		
		if(!!farmid&&farmid!==0){
		
			CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {
				if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
					displayCattleDetailsByFarm(jsonResData);				
				}	
			}).fail(CMSCom.exceptionHandler.ajaxFailure);	
		}else{		
			$("#healthcareselectCattle").trigger("chosen:updated");
		}	
	};
	
	var healthcareselectFarmHandler=function(e){
		var _this=$(this);
			farmid=$(this).val()*1,
			healthcareselectDateEl=$("#healthcareselectDate"),
			healthcareselectDate=healthcareselectDateEl.val();
	
		if(!!healthcareselectDate&&healthcareselectDate.trim().length!==0){
			$("#ipt-er-healthcareselectFarm").hide();
			getCattleDetailsByFarm(farmid);	
		}else{
			e.stopPropagation();
			$("#healthcareselectFarm").val(0);
			$("#healthcareselectFarm").trigger("chosen:updated");				
			CMSMessage.showSubPageMsg({"status":0,"msg":"Please select Date first"});
			$(".mCSB_container").css({"top":0});
		}	
		
	};

	// This to populate Healthcare vaccine details
	var loadHCVaccineDataTable=function(HCVaccineArr){
		
		if(!!CMSVetCons.dataTable["hcvaccine"].showFlg){
			CMSVetCons.dataTable["hcvaccine"].dtFn.fnClearTable();	
			CMSVetCons.dataTable["hcvaccine"].dtFn.fnAddData(HCVaccineArr);	
			CMSVetCons.dataTable["hcvaccine"].dtFn.fnSetColumnVis( 0, false );			
		}else{	
		
			$.fn.dataTableExt.sErrMode = 'throw';
			CMSVetCons.dataTable["hcvaccine"].dtFn=$('#HCVaccineDataTable').dataTable({
				"bDestroy": true,
			    "aaData": HCVaccineArr,
			        "aoColumns": [{
			        "mDataProp": "vaccid"
				    }, {
				        "mDataProp": "objVaccinationEntity.vaccinationname"    	
			    }, {
			        "mDataProp": "objVaccinationEntity.vaccinationtype"   
			    }, {
			        "mDataProp": "vaccdose",
			        "mRender": function ( thisData, type, rowData ,i ) {
			        	return thisData+" "+rowData.objVaccinationEntity.vaccinationunit;
			        }
			    }, {
			        "mDataProp": "vaccid", // this is for vaccnextvaccdt
			        "mRender": function ( data, type, rowData ,i ) {			        	
			        	return  !!rowData.vaccvaccinationdt?CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(rowData.vaccvaccinationdt)):"";
			        }	
			    }, {
			        "mDataProp": "vaccid", // this is for vaccnextvaccdt
			        "mRender": function ( data, type, rowData ,i ) {			        	
			        	return  !!rowData.vaccnextvaccdt?CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(rowData.vaccnextvaccdt)):"";
			        }
			    }, {
			        "mDataProp": "vaccid",
			        "mRender": function ( data, type, rowData ,i ) {
			        	var actionHtml="<a href='javascript:void(0);' class='edit-hc-sub-dtls' data-type='vaccine' data-hc-vaccine-id='"+rowData.vaccid+"'><i class='fa fa-pencil-square-o'></i> Edit</a> ";
			        	return actionHtml;
			        }
			    }],
			  'fnInitComplete' :function(){
					setTimeout(function(){
						$( "#HCVaccineDataTable" ).wrapAll( "<div class='table-responsive'></div>" );							
					},100)
				 adjustScreen.contentHeight();
			   },
			   'fnDrawCallback' : function() {
				  adjustScreen.contentHeight();
				}	
			});
			 var oSettings = CMSVetCons.dataTable["hcvaccine"].dtFn.fnSettings();
		     oSettings._iDisplayLength = 5; 			     
		     CMSVetCons.dataTable["hcvaccine"].dtFn.fnSetColumnVis( 0, false );
		     CMSVetCons.dataTable["hcvaccine"].showFlg=true;
		 }	
		adjustScreen.mCustomScrollbar_FixDataTablePagin("HCVaccineDataTable");
	};
	
	// This to populate Healthcare Deworming details
	var loadHCDewormingDataTable=function(HCDewormingArr){
		
		if(!!CMSVetCons.dataTable["hcdeworming"].showFlg){
			CMSVetCons.dataTable["hcdeworming"].dtFn.fnClearTable();	
			CMSVetCons.dataTable["hcdeworming"].dtFn.fnAddData(HCDewormingArr);	
			CMSVetCons.dataTable["hcdeworming"].dtFn.fnSetColumnVis( 0, false );			
		}else{	
	
			$.fn.dataTableExt.sErrMode = 'throw';
			CMSVetCons.dataTable["hcdeworming"].dtFn=$('#HCDewormingDataTable').dataTable({
				"bDestroy": true,
			    "aaData": HCDewormingArr,
			        "aoColumns": [{
			        "mDataProp": "vaccid"
				    }, {
				        "mDataProp": "objHealthCareEntity.servicedate"    	
				    }, {
				        "mDataProp": "vaccid", // this is for vaccnextvaccdt
				        "mRender": function ( data, type, rowData ,i ) {			        	
				        	return  !!rowData.vaccvaccinationdt?CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(rowData.vaccvaccinationdt)):"";
				        }				
				    }, {
				        "mDataProp": "vaccid", // this is for vaccnextvaccdt
				        "mRender": function ( data, type, rowData ,i ) {			        	
				        	return  !!rowData.vaccnextvaccdt?CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(rowData.vaccnextvaccdt)):"";
				        }
				    }, {
			        "mDataProp": "vaccid",
			        "mRender": function ( data, type, rowData ,i ) {
			        	var actionHtml="<a href='javascript:void(0);'  class='edit-hc-sub-dtls' data-type='deworming' data-hc-vaccine-id='"+rowData.vaccid+"'><i class='fa fa-pencil-square-o'></i> Edit</a> ";
			        	return actionHtml;
			        }
			    }],
				  'fnInitComplete' :function(){
						setTimeout(function(){
							$( "#HCDewormingDataTable" ).wrapAll( "<div class='table-responsive'></div>" );							
						},100)
					 adjustScreen.contentHeight();
				   },
				   'fnDrawCallback' : function() {
					  adjustScreen.contentHeight();
					}	

			});
			 var oSettings = CMSVetCons.dataTable["hcdeworming"].dtFn.fnSettings();
		     oSettings._iDisplayLength = 5; 			     
		     CMSVetCons.dataTable["hcdeworming"].dtFn.fnSetColumnVis( 0, false );
		    // CMSVetCons.dataTable["hcdeworming"].showFlg=true;
		     adjustScreen.mCustomScrollbar_FixDataTablePagin("HCDewormingDataTable");
		 }	
	};
	
	
	var displayCattleHealthcareDtls=function(jsonResData){
		
		if(jsonResData.status*1===1){
			var cattleHCdtls =JSON.parse(jsonResData.resData),
			hcdetailMasterData=cattleHCdtls.masterData,
			hcdetailData=cattleHCdtls.detailData,
			HCVaccineArr=[],
			HCDewormingArr=[];
			
			$("#healthcareid").val(hcdetailMasterData[0].healthcareid)
			for(var i in hcdetailData){
				var hcdetailDataObj=hcdetailData[i];
				if(hcdetailDataObj.vacchctype===0){				
					HCVaccineArr.push(hcdetailDataObj);
				}else{				
					HCDewormingArr.push(hcdetailDataObj);
				}
			}

			if(HCVaccineArr.length!==0){
				loadHCVaccineDataTable(HCVaccineArr);
			}
			if(HCDewormingArr.length!==0){
				loadHCDewormingDataTable(HCDewormingArr);
			}
			
		}else{		
			$("#healthcareid").val(0);
			loadHCVaccineDataTable([]);
			loadHCDewormingDataTable([]);
		}
		
	};
	
	// This to fetch cattle health care details 
	var fetchHealthCareDtls=function(){
		var healthcareselectDateEl=$("#healthcareselectDate"),
		healthcareselectDate=healthcareselectDateEl.val(),
		farmid=$("#healthcareselectFarm").val(),
		cattleid=$("#healthcareselectCattle").val()*1;
		
		var urlObj=CMSVetCons.urls_info["healthcareDetails"],
	 		reqData=jQuery.extend({}, CMSVetCommon.getInitReqData()),
	 		queryDtls={};		
			
		queryDtls["objCattleEntity"]={"cattleid":cattleid};	
		queryDtls["servicedate"]=CMSCom.dateFn.cusDateServerFormat(healthcareselectDate) ;		
		reqData["reqData"]=queryDtls;
		
		if(!!cattleid&&cattleid!==0){
			reqData=JSON.stringify(reqData);	
			CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {
				if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){											
					displayCattleHealthcareDtls(jsonResData);																
				}	
			}).fail(CMSCom.exceptionHandler.ajaxFailure);	
		}else{		
			loadHCVaccineDataTable([]);
			loadHCDewormingDataTable([]);
		}			

	};
	
	var healthcareselectCattleHandler=function(e){
		var _this=$(this);
			cattleid=$(this).val()*1,
			healthcareselectDateEl=$("#healthcareselectDate"),
			healthcareselectDate=healthcareselectDateEl.val();	
			
		if(!!healthcareselectDate&&healthcareselectDate.trim().length!==0){
			$("#ipt-er-healthcareselectFarm").hide();
			fetchHealthCareDtls();	
		}else{
			e.stopPropagation();
			$("#healthcareselectCattle").val(0);
			$("#healthcareselectCattle").trigger("chosen:updated");			
			
			CMSMessage.showSubPageMsg({"status":0,"msg":"Please select Date first"});

			$(".mCSB_container").css({"top":0});
		}	
		
	};
	var hcvaccnNameHandler=function(e){
		var _this=$(this);
		hcvaccnName=_this.val()*1,
		hccvaccDose=$('#hcvaccnName option[value="' + hcvaccnName + '"]').data("dose"),
		hccvaccUnit=$('#hcvaccnName option[value="' + hcvaccnName + '"]').data("unit");

		if(!!hcvaccnName&&hcvaccnName!==0){
			$("#hcvaccnVaccineUnit").val(hccvaccUnit);
			$("#hcvaccnVaccineDose").val(hccvaccDose);
		}else{
			$("#hcvaccnVaccineUnit").val("");
			$("#hcvaccnVaccineDose").val(0);
		}
	};
	
	var validateHCVaccinationDtls=function(){
		
		var healthcareselectRoutineAdmin=$("#healthcareselectRoutineAdmin").val(),
			healthcareDate=$("#healthcareselectDate").val(),
			healthcareDateObj = $("#healthcareselectDate").data("datepicker").getDate(),		
			hcVaccnDateVaccn=$("#hcvaccnDateVaccination").val(),
			vaccnDateAsObj = $("#hcvaccnDateVaccination").data("datepicker").getDate(),
			hcVaccnNextVaccn=$("#hcvaccnNextVaccination").val(),
			nextvaccnDateAsObj = $("#hcvaccnNextVaccination").data("datepicker").getDate(),
			hcvaccnvetid=$("#hcvetid").val()*1,
			hcvaccnName=$("#hcvaccnName").val()*1,
			hcvaccnVaccineDose=$("#hcvaccnVaccineDose").val(),
			success=true,
			msg="",
			healthDateDiff=1,
			vaccnDateDiff=1,
			msgObj;
		
		if(!!hcVaccnDateVaccn){
			healthDateDiff=CMSCom.dateFn.daysBetween(healthcareDateObj,vaccnDateAsObj);		
		}
		
		if(!!hcVaccnNextVaccn){
			vaccnDateDiff=CMSCom.dateFn.daysBetween(vaccnDateAsObj,nextvaccnDateAsObj);		
		}
	
		if(!healthcareselectRoutineAdmin||healthcareselectRoutineAdmin===0){			
			msg="Please Select Routine of Administration";
			success=false;
		}else if(!hcvaccnvetid||hcvaccnvetid===0){			
			msg="Please select Vet/Paravet";
			success=false;
		}else if(!hcVaccnDateVaccn){			
			msg="Please select Date of Vaccination ";
			success=false;
		}else if(healthDateDiff<0){		
			msg="\"Date of Vaccination\" should be greater than or equal to \"Date of Service \"";
			success=false;	
		}else if(vaccnDateDiff<=0){
			msg="\"Date of Next Vaccination\" should be greater than \"Date of Vaccination \"";
			success=false;
		}else if(!hcvaccnName||hcvaccnName===0){			
			msg="Please select Vaccination ";
			success=false;
		}else if(!hcvaccnVaccineDose&&hcvaccnVaccineDose!==0){			
			msg="Please Enter Dose ";
			success=false;
		}
		if(!success){
			msgObj={"success":0,"msg":msg};
			CMSMessage.showCommonModalMsg(msgObj,"#cms-vet-healthcare-vaccine-modal");
		}

		return success;		
	};
	
	var saveHCVaccinationDtls=function(e){
	   try{
			if($("#cms-vet-hc-vaccination-form")[0].checkValidity()&&validateHCVaccinationDtls()){					
	
				var urlObj=CMSVetCons.urls_info["saveHealthCare"],
			 		reqData=jQuery.extend({}, CMSVetCommon.getInitReqData()),	
			 		hcvaccinationDtsl={},
			 		healthcareid=$("#healthcareid").val()*1,
			 		hcvaccnvaccid=$("#hcvaccn-vaccid").val()*1,
			 		hcvaccnCreateddt=$("#hcvaccn-createddt").val();			
			 		
				$("#cms-vet-hc-vaccination-form .form-dt").each(function(){
					hcvaccinationDtsl[$(this).prop("name")]=$(this).val();
				});
				$("#cms-vet-hc-vaccination-form .form-dt-date").each(function(){
					var inval=$(this).val();
					if(!!inval){
						hcvaccinationDtsl[$(this).prop("name")]=CMSCom.dateFn.cusDateServerFormat(inval);
					}				
				});
				$("#cms-vet-hc-vaccination-form .form-dt-refer").each(function(){					
					var _this=$(this),refer=_this.data("refer"),field=_this.data("field"),val =_this.val(),obj={};						
					obj[field]=val;					
					if(!!val){
						hcvaccinationDtsl[refer]=obj;
					}
				});
				
				$("#cms-saveHealthCareDtls-form .form-dt-refer").each(function(){					
					var _this=$(this),refer=_this.data("refer"),field=_this.data("field"),	val =_this.val(),obj={};						
					obj[field]=val;					
					if(!!val){
						hcvaccinationDtsl[refer]=obj;
					}
				});
				$("#cms-saveHealthCareDtls-form .form-dt").each(function(){
					hcvaccinationDtsl[$(this).prop("name")]=$(this).val();
				});
				$("#cms-saveHealthCareDtls-form .form-dt-date").each(function(){
					var inval=$(this).val();
					if(!!inval){
						hcvaccinationDtsl[$(this).prop("name")]=CMSCom.dateFn.cusDateServerFormat(inval);
					}				
				});
				if(!hcvaccnCreateddt){
					delete hcvaccinationDtsl["createddt"];
				}
				
				hcvaccinationDtsl["vetsign"]="";
				hcvaccinationDtsl["medicationgiven"]="";				
				hcvaccinationDtsl["vacchctype"]="0";
				

				if(!!healthcareid&&healthcareid!==0){
					hcvaccinationDtsl["healthcareid"]=healthcareid;		
				}
				if(!!hcvaccnvaccid&&hcvaccnvaccid!==0){
					hcvaccinationDtsl["vaccid"]=hcvaccnvaccid;		
				}
	
				reqData["reqData"]=hcvaccinationDtsl;
				reqData=JSON.stringify(reqData);
		
				CMSCom.ajaxService.sentMultipartFormData(urlObj,reqData).done(function(jsonResData) {	
					if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
						var resData=JSON.parse(jsonResData),
							msgObj=null;					
						if(resData.status*1===1){	
							var saveMsg=!!hcvaccnvaccid&&hcvaccnvaccid*1!==0?"Healthcare Vaccination successfully updated " :"Healthcare Vaccination successfully saved ";

							$("#cms-vet-healthcare-vaccine-modal").modal("hide");
							CMSMessage.showSubPageMsg({"status":1,"msg":saveMsg});
							fetchHealthCareDtls();
						}else{
							msgObj={"success":0,"msg":resData.msg};
							CMSMessage.showCommonModalMsg(msgObj,"#cms-vet-healthcare-vaccine-modal");
						}								
					}		
					
				}).fail(CMSCom.exceptionHandler.ajaxFailure);
				
			}
		
		}catch(er){
			console.log("e ",er)
		}
		e.preventDefault();
		
	};
	
	var validateHCDewormingDtls=function(){
		var healthcareDate=$("#healthcareselectDate").val(),
			healthcareDateObj = $("#healthcareselectDate").data("datepicker").getDate(),	
			hcdewormDateVaccn=$("#hcdewormDateVaccination").val(),
			dewormDateAsObj = $("#hcdewormDateVaccination").data("datepicker").getDate(),
			hcdewormNextVaccn=$("#hcdewormNextVaccination").val(),
			nextdewormDateAsObj = $("#hcdewormNextVaccination").data("datepicker").getDate(),
			hcdewormvetid=$("#hcdewormvetid").val()*1,
			success=true,
			msg="",
			deWormDateDiff=1,
			healthDateDiff=1,
			msgObj;
	
		if(!!hcdewormDateVaccn){
			healthDateDiff=CMSCom.dateFn.daysBetween(healthcareDateObj,dewormDateAsObj);		
		}
		
		if(!!hcdewormNextVaccn){
			deWormDateDiff=CMSCom.dateFn.daysBetween(dewormDateAsObj,nextdewormDateAsObj);		
		}
		
		if(!hcdewormDateVaccn){			
			msg="Please select Date of Routine Deworming";
			success=false;
		}else if(healthDateDiff<0){		
			msg="\"Date of Routine Deworming\" should be greater than or equal to \"Date of Service \"";
			success=false;		
		}else if(deWormDateDiff<=0){
			msg="\"Date of Next Routine Deworming\" should be greater than \"Date of Routine Deworming\"";
			success=false;
		}else if(!hcdewormvetid||hcdewormvetid==0){			
			msg="Please select Vet/Paravet";
			success=false;
		}
		if(!success){
			msgObj={"success":0,"msg":msg};
			CMSMessage.showCommonModalMsg(msgObj,"#cms-vet-healthcare-deworm-modal");
		}

		return success;		
	};
	
	var saveHCDewormingDtls=function(e){
		   try{
				if($("#cms-routine-deworming-form")[0].checkValidity()&&validateHCDewormingDtls()){					
		
					var urlObj=CMSVetCons.urls_info["saveHealthCare"],
				 	reqData=jQuery.extend({}, CMSVetCommon.getInitReqData()),
			 		healthcareid=$("#healthcareid").val()*1,
					hcdewormingVaccid=$("#hcdeworming-vaccid").val()*1,
					hcvaccinationDtsl={},
					hcdewormingCreateddt=$("#hcdeworming-createddt").val();	

					$("#cms-routine-deworming-form .form-dt").each(function(){
						hcvaccinationDtsl[$(this).prop("name")]=$(this).val();
					});

					$("#cms-routine-deworming-form .form-dt-refer").each(function(){					
						var _this=$(this),refer=_this.data("refer"),field=_this.data("field"),val =_this.val(),obj={};						
						obj[field]=val;					
						if(!!val){
							hcvaccinationDtsl[refer]=obj;
						}
					});
					$("#cms-routine-deworming-form .form-dt-date").each(function(){
						var inval=$(this).val();
						if(!!inval){
							hcvaccinationDtsl[$(this).prop("name")]=CMSCom.dateFn.cusDateServerFormat(inval);
						}				
					});
					$("#cms-saveHealthCareDtls-form .form-dt-refer").each(function(){					
						var _this=$(this),refer=_this.data("refer"),field=_this.data("field"),	val =_this.val(),obj={};						
						obj[field]=val;					
						if(!!val){
							hcvaccinationDtsl[refer]=obj;
						}
					});
					$("#cms-saveHealthCareDtls-form .form-dt").each(function(){
						hcvaccinationDtsl[$(this).prop("name")]=$(this).val();
					});
					$("#cms-saveHealthCareDtls-form .form-dt-date").each(function(){
						var inval=$(this).val();
						if(!!inval){
							hcvaccinationDtsl[$(this).prop("name")]=CMSCom.dateFn.cusDateServerFormat(inval);
						}				
					});
		
					if(!hcdewormingCreateddt){
						delete hcvaccinationDtsl["createddt"];
					}
					hcvaccinationDtsl["vaccroutine"]="";
					hcvaccinationDtsl["vaccdose"]=0;
					hcvaccinationDtsl["vaccinationunit"]="";
					hcvaccinationDtsl["objVaccinationEntity"]=null;					
					hcvaccinationDtsl["vetsign"]="";
					hcvaccinationDtsl["medicationgiven"]="";			
					hcvaccinationDtsl["vacchctype"]="1";

					if(!!healthcareid&&healthcareid!==0){
						hcvaccinationDtsl["healthcareid"]=healthcareid;		
					}
					if(!!hcdewormingVaccid&&hcdewormingVaccid!==0){
						hcvaccinationDtsl["vaccid"]=hcdewormingVaccid;		
					}
					
					reqData["reqData"]=hcvaccinationDtsl;
					reqData=JSON.stringify(reqData);

					
					CMSCom.ajaxService.sentMultipartFormData(urlObj,reqData).done(function(jsonResData) {	
						if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
							var resData=JSON.parse(jsonResData),
								msgObj=null;					
							if(resData.status*1===1){	
								var saveMsg=!!hcdewormingVaccid&&hcdewormingVaccid*1!==0?"Healthcare Deworming successfully updated " :"Healthcare Deworming successfully saved "; 
								
								$("#cms-vet-healthcare-deworm-modal").modal("hide");
								CMSMessage.showSubPageMsg({"status":1,"msg":saveMsg});
								fetchHealthCareDtls();
							}else{
								msgObj={"success":0,"msg":resData.msg};
								CMSMessage.showCommonModalMsg(msgObj,"#cms-vet-healthcare-deworm-modal");
							}							
						}		
						
					}).fail(CMSCom.exceptionHandler.ajaxFailure);
					
				}
			
			}catch(er){
				//console.log("e ",er)
			}
			e.preventDefault();
			
		};
	
		
	var populateEditHCVaccineModal=function(jsonResData){
		if(jsonResData.status*1===1){	
			$("#cms-vet-healthcare-vaccine-modal .cmsModalTitle").html("Edit Vaccination ");
			var vaccineDtlsArr=JSON.parse(jsonResData.resData),
			vaccineDtlsObj=vaccineDtlsArr[0],
			vaccvaccinationdt=vaccineDtlsObj.vaccvaccinationdt,
			vaccnextvaccdt=vaccineDtlsObj.vaccnextvaccdt;
			vaccvaccinationdt=!!vaccvaccinationdt?vaccvaccinationdt.replace("00:00:00",""):"";
			vaccnextvaccdt=!!vaccnextvaccdt?vaccnextvaccdt.replace("00:00:00",""):"";
						
			$("#healthcareselectRoutineAdmin").val(vaccineDtlsObj.vaccroutine);			
			$("#hcvetid").val(vaccineDtlsObj.vacchcvetid);			
			$("#hcvaccnDateVaccination").val(vaccvaccinationdt.trim());			
			$("#hcvaccnNextVaccination").val(vaccnextvaccdt.trim());
			
			$("#hcvaccnName").val(vaccineDtlsObj.objVaccinationEntity.vaccinationid);			
			$("#hcvaccnVaccineDose").val(vaccineDtlsObj.vaccdose);
			$("#hcvaccnVaccineUnit").val(vaccineDtlsObj.objVaccinationEntity.vaccinationunit);
			
			$("#vacchctypecomments").val(vaccineDtlsObj.vacchctypecomments);
			$("#hcvaccn-vaccid").val(vaccineDtlsObj.vaccid);
			$("#hcvaccn-createddt").val(vaccineDtlsObj.createddt);
			
			$("#hcvetid").trigger("chosen:updated");	
			$("#hcvaccnName").trigger("chosen:updated");	
			$("#healthcareselectRoutineAdmin").trigger("chosen:updated");
			
			$("#cms-vet-healthcare-vaccine-modal").modal("show");
			
			updateBreedingDateCalendar("#cms-vet-healthcare-vaccine-modal","#hcvaccnDateVaccination",new Date());
			updateBreedingDateCalendar("#cms-vet-healthcare-vaccine-modal","#hcvaccnNextVaccination",null);
		}
	};
	
	var populateEditHCDewormingModal=function(jsonResData){
		if(jsonResData.status*1===1){
			$("#cms-vet-healthcare-deworm-modal .cmsModalTitle").html("Edit Routine Deworming ");
			var dewormingDtlsArr=JSON.parse(jsonResData.resData),
			dewormingDtlsObj=dewormingDtlsArr[0],
			vaccvaccinationdt=dewormingDtlsObj.vaccvaccinationdt,
			vaccnextvaccdt=dewormingDtlsObj.vaccnextvaccdt;
			vaccvaccinationdt=!!vaccvaccinationdt?vaccvaccinationdt.replace("00:00:00",""):"";
			vaccnextvaccdt=!!vaccvaccinationdt?vaccnextvaccdt.replace("00:00:00",""):"";
			
			$("#hcdewormDateVaccination").val(vaccvaccinationdt);
			$("#hcdewormNextVaccination").val(vaccnextvaccdt);
			$("#hcdewormvetid").val(dewormingDtlsObj.vacchcvetid);
			$("#hcdewormvetid").trigger("chosen:updated");	
			$("#hcdewormtypecomments").val(dewormingDtlsObj.vacchctypecomments);
			$("#hcdeworming-vaccid").val(dewormingDtlsObj.vaccid);
			
			$("#cms-vet-healthcare-deworm-modal").modal("show");
			updateBreedingDateCalendar("#cms-vet-healthcare-deworm-modal","#hcdewormDateVaccination",new Date());
			updateBreedingDateCalendar("#cms-vet-healthcare-deworm-modal","#hcdewormNextVaccination",null);
		}
	};
	var loadHCSubdtlsModal=function(vaccineid,type){
		var urlObj=CMSVetCons.urls_info["healthcareTypeDetailsById"],
 		reqData=jQuery.extend({}, CMSVetCommon.getInitReqData()),
 		queryDtls={};		

		queryDtls["vaccid"]=vaccineid;		
		reqData["reqData"]=queryDtls;

		reqData=JSON.stringify(reqData);	
		CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {
			if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){	
				if("vaccine"===type){
					populateEditHCVaccineModal(jsonResData);
				}else{
					populateEditHCDewormingModal(jsonResData);
				}															
			}	
		}).fail(CMSCom.exceptionHandler.ajaxFailure);	
	};
	
	var editHCsubdtlsHandler=function(){		
		var _this=$(this),
			type=_this.data("type"),
			vaccineid=_this.data("hc-vaccine-id");
			loadHCSubdtlsModal(vaccineid,type);
	};
	
	
	var  hcVaccinSubmitHandler=function(e){
		
	}	

	var  hcVaccinResetHandler=function(e){
		
		var vaccineid=$("#hcvaccn-vaccid").val()*1;		
		$("#cms-vet-hc-vaccination-form .form-dt").each(function(){
			$(this).val("");
		});
		$("#cms-vet-hc-vaccination-form .form-dt-date").each(function(){
			$(this).val("");			
		});
		$("#hcvetid").val(0);	
		$("#hcvaccnName").val(0);
		$("#healthcareselectRoutineAdmin").val(0);
		$("#hcvetid").trigger("chosen:updated");	
		$("#hcvaccnName").trigger("chosen:updated");	
		$("#healthcareselectRoutineAdmin").trigger("chosen:updated");
		
		if(!!vaccineid&&vaccineid!==0){
			loadHCSubdtlsModal(vaccineid,"vaccine");
		}	
	}	
	var  hcVaccinCancelHandler=function(e){
		$("#cms-vet-healthcare-vaccine-modal").modal("hide");
	}	
	
	
	
	var hcdewormingSubmitHandler=function(e){
		
	};	
	
	
	var  hcdewormingResetHandler=function(e){
		var vaccineid=$("#hcdeworming-vaccid").val()*1;
		$("#cms-routine-deworming-form .form-dt").each(function(){
			$(this).val("");
		});
		$("#cms-routine-deworming-form .form-dt-date").each(function(){
			$(this).val("");			
		});
		$("#hcdewormvetid").val(0);
		$("#hcdewormvetid").trigger("chosen:updated");	
		if(!!vaccineid&&vaccineid!==0){
			loadHCSubdtlsModal(vaccineid,"deworming");
		}		
	}	
	var  hcdewormingCanceleHandler=function(e){
		$("#cms-vet-healthcare-deworm-modal").modal("hide");
	}	
		
	
	return {
		showHealthCareAddOrUpdatePage:showHealthCareAddOrUpdatePage,
		addHealthCareTypeDtlsBtnHandler:addHealthCareTypeDtlsBtnHandler,
		healthcareselectFarmHandler:healthcareselectFarmHandler,
		healthcareselectCattleHandler:healthcareselectCattleHandler,
		hcvaccnNameHandler:hcvaccnNameHandler,
		saveHCVaccinationDtls:saveHCVaccinationDtls,
		saveHCDewormingDtls:saveHCDewormingDtls,
		healthcareselectTypeHandler:healthcareselectTypeHandler,
		editHCsubdtlsHandler:editHCsubdtlsHandler,
		hcVaccinSubmitHandler:hcVaccinSubmitHandler,
		hcVaccinResetHandler:hcVaccinResetHandler,
		hcVaccinCancelHandler:hcVaccinCancelHandler,
		
		hcdewormingSubmitHandler:hcdewormingSubmitHandler,
		hcdewormingResetHandler:hcdewormingResetHandler,
		hcdewormingCanceleHandler:hcdewormingCanceleHandler,
		
	};
})();