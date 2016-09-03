var OwnerHealthcare=(function(){
	"use strict";
	var loadHealthcareDataTable=function(){  
		
		if(!!CMSOwnerCons.dataTable["healthcare"].showFlg){
			CMSOwnerCons.dataTable["healthcare"].dtFn._fnAjaxUpdate();
			CMSOwnerCons.dataTable["healthcare"].dtFn.fnSetColumnVis( 0, false );	
		}else{				
			var	reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData()),
				urlObj=CMSOwnerCons.urls_info["getHealthCareList"],
				req="";   	
			reqData["reqData"]=req;				
			$.fn.dataTableExt.sErrMode = 'throw';			
			CMSOwnerCons.dataTable["healthcare"].dtFn=$('#healthcareDataTable').dataTable({
			  "ajax": {
			    "url": urlObj.url,
			    "type":"POST",
			    processData: false,
			    beforeSend:function(jqXHR,settings ){
			    	settings.data = JSON.stringify(reqData);
                },
            	"contentType":'application/json',
			    "dataSrc": function ( dataJson ) {	
			     	if(CMSCom.exceptionHandler.isLoginSessionExpire(dataJson)){
			     		if(dataJson.status==="1"){			     		
					     	var jsonRes=JSON.parse(dataJson.resData);		
					     	
					     	for (var key in jsonRes){
					    		var eachObj=jsonRes[key],	
					    			hc_service_dt=CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(eachObj.healthcare_service_dt)),
					    		//cattleid,healthcareselectDate
					    			viewLink="<a href='#view-health-care?cattleId="+eachObj.cattle_id+"&hc-date="+hc_service_dt+"' >"+eachObj.cattle_ear_tag_id+"</a>";

					    		jsonRes[key][0] = eachObj.healthcare_service_dt;					    	
					    		jsonRes[key][1] = viewLink;
					    		jsonRes[key][2] = CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(eachObj.cattle_dob));
					    		jsonRes[key][3] = hc_service_dt;		    
					    		jsonRes[key][4] =eachObj.farm_code;	
					    		jsonRes[key][5] =eachObj.vet_name;	
					    		//jsonRes[key][5] =!!eachObj.breeding_aborted_dt? CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(eachObj.breeding_aborted_dt)):"";
					    		
					    		var actionHtml="<a href='javascript:void(0);' class='edit-link-breeding-dtls' data-breeding-id='"+eachObj.breeding_id+"'><i class='fa fa-pencil-square-o'></i> Edit</a> ";
					    	//	jsonRes[key][6] = actionHtml;
					    	}
							
						    return jsonRes;
			     		}else{
			     			return [];
			     		}
				
			     	}
			    }			    
			  },
			  destroy: true,
			  "aaSorting": [[ 0, "desc" ]],	
			  //"aoColumnDefs": [{'bSortable': false, 'aTargets': [6]}],		   
			  'processing': false,
			  'serverSide': false,
			  'bLengthChange': false, 
			  'fnInitComplete' :function(){	
					setTimeout(function(){
						$("#healthcareDataTable" ).wrapAll( "<div class='table-responsive'></div>" );
						adjustScreen.contentHeight();
					},100);				
			  },
			  'fnDrawCallback' : function() {
				  adjustScreen.contentHeight();
			  }
			});
			var oSettings = CMSOwnerCons.dataTable["healthcare"].dtFn.fnSettings();
		    oSettings._iDisplayLength = 10;			     
		    CMSOwnerCons.dataTable["healthcare"].dtFn.fnSetColumnVis( 0, false );
		}			
	};

	var showHealthcareDetlsPageCallBackFn=function(){
		 $("#cms-healthcare-details").removeClass("hidden");					
		$("#breadcrumb-subpage").html("Health Care");
		loadHealthcareDataTable();
		adjustScreen.contentHeight();
		
	};
	var showHealthcareDetlsPage=function(){
		var loadStatus= $("#cms-healthcare-details").data("load-status");
		if(!!loadStatus){
			showHealthcareDetlsPageCallBackFn();
		}else{
			 $("#cms-healthcare-details").load("partials/owner-health-care-list.html", function(response, status, xhr){
	            if(status === "success"){
	            	 $("#cms-healthcare-details").data("load-status",true);
	            	showHealthcareDetlsPageCallBackFn();
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}  
	};
	
	// This to populate Healthcare vaccine details
	var loadHCVaccineDataTable=function(HCVaccineArr){
		
		if(!!CMSOwnerCons.dataTable["hcvaccine"].showFlg){
			CMSOwnerCons.dataTable["hcvaccine"].dtFn.fnClearTable();	
			CMSOwnerCons.dataTable["hcvaccine"].dtFn.fnAddData(HCVaccineArr);	
			CMSOwnerCons.dataTable["hcvaccine"].dtFn.fnSetColumnVis( 0, false );			
		}else{	
		
			$.fn.dataTableExt.sErrMode = 'throw';
			CMSOwnerCons.dataTable["hcvaccine"].dtFn=$('#HCVaccineDataTable').dataTable({
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
			 var oSettings = CMSOwnerCons.dataTable["hcvaccine"].dtFn.fnSettings();
		     oSettings._iDisplayLength = 5; 			     
		     CMSOwnerCons.dataTable["hcvaccine"].dtFn.fnSetColumnVis( 0, false );
		     CMSOwnerCons.dataTable["hcvaccine"].showFlg=true;
		 }	
		adjustScreen.mCustomScrollbar_FixDataTablePagin("HCVaccineDataTable");
	};
	
	// This to populate Healthcare Deworming details
	var loadHCDewormingDataTable=function(HCDewormingArr){
		
		if(!!CMSOwnerCons.dataTable["hcdeworming"].showFlg){
			CMSOwnerCons.dataTable["hcdeworming"].dtFn.fnClearTable();	
			CMSOwnerCons.dataTable["hcdeworming"].dtFn.fnAddData(HCDewormingArr);	
			CMSOwnerCons.dataTable["hcdeworming"].dtFn.fnSetColumnVis( 0, false );			
		}else{	
	
			$.fn.dataTableExt.sErrMode = 'throw';
			CMSOwnerCons.dataTable["hcdeworming"].dtFn=$('#HCDewormingDataTable').dataTable({
				"bDestroy": true,
			    "aaData": HCDewormingArr,
			        "aoColumns": [{
			        "mDataProp": "vaccid"
				    }, {
				        "mDataProp": "objHealthCareEntity.servicedate",
				        "mRender": function ( data, type, rowData ,i ) {	
				        
				        	return  !!rowData.objHealthCareEntity.servicedate?CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(rowData.objHealthCareEntity.servicedate)):"";
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
			 var oSettings = CMSOwnerCons.dataTable["hcdeworming"].dtFn.fnSettings();
		     oSettings._iDisplayLength = 5; 			     
		     CMSOwnerCons.dataTable["hcdeworming"].dtFn.fnSetColumnVis( 0, false );
		    // CMSOwnerCons.dataTable["hcdeworming"].showFlg=true;
		     adjustScreen.mCustomScrollbar_FixDataTablePagin("HCDewormingDataTable");
		 }	
	};
	
	var displayHCMasterDtls=function(hcdetailMasterObj){
		
		$("#view-hc-id").html(hcdetailMasterObj.healthcareid);
		$("#view-hc-date").html(CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(hcdetailMasterObj.servicedate)));
		$("#view-hc-ear-tag-id").html(hcdetailMasterObj.objCattleEntity.cattleeartagid);
		$("#view-hc-farm-name").html(hcdetailMasterObj.objCattleEntity.farm.farmname);
		//$("#view-hc-farm-name").html(hcdetailMasterObj.objCattleEntity.farm.farmname);
		
	};
	
	var displayCattleHealthcareDtls=function(jsonResData){
	
		if(jsonResData.status*1===1){
			var cattleHCdtls =JSON.parse(jsonResData.resData),
			hcdetailMasterData=cattleHCdtls.masterData,
			hcdetailData=cattleHCdtls.detailData,
			HCVaccineArr=[],
			HCDewormingArr=[];
	
			displayHCMasterDtls(hcdetailMasterData[0]);
			
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
			}else{
				loadHCDewormingDataTable([]);
			}
			if(HCDewormingArr.length!==0){
				loadHCDewormingDataTable(HCDewormingArr);
			}else{
				loadHCDewormingDataTable([]);
			}
			
		}else{		
			$("#healthcareid").val(0);
			loadHCVaccineDataTable([]);
			loadHCDewormingDataTable([]);
		}
		
	};
	
	var fetchHealthcareDetls=function(cattleid,healthcareselectDate){
		
		var urlObj=CMSOwnerCons.urls_info["healthcareDetails"],
 		reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData()),
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
	var showViewHCDetlsPageCallBackFn=function(cattleid,healthcareselectDate){
		$("#cms-view-healthcare-details").removeClass("hidden");					
		$("#breadcrumb-subpage").html("Health Care");	
		fetchHealthcareDetls(cattleid,healthcareselectDate);
		adjustScreen.contentHeight();
		
	};
	var showViewHCDetlsPage=function(cattleid,healthcareselectDate){
		var loadStatus= $("#cms-view-healthcare-details").data("load-status");
		if(!!loadStatus){
			showViewHCDetlsPageCallBackFn(cattleid,healthcareselectDate);
		}else{
			$("#cms-view-healthcare-details").load("partials/owner-health-care-view.html", function(response, status, xhr){
	            if(status === "success"){
	            	$("#cms-view-healthcare-details").data("load-status",true);
	            	showViewHCDetlsPageCallBackFn(cattleid,healthcareselectDate);
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}  
	};
	
	return {
		showHealthcareDetlsPage:showHealthcareDetlsPage,
		showViewHCDetlsPage:showViewHCDetlsPage
	}
	
})();



