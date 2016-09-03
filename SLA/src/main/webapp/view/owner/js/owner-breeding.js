var OwnerBreading=(function(){
	"use strict";
	
	// This is to load and display breeding table
	var loadBreedingDataTable=function(){
		
		if(!!CMSOwnerCons.dataTable["breeding"].showFlg){
			CMSOwnerCons.dataTable["breeding"].dtFn._fnAjaxUpdate();
			CMSOwnerCons.dataTable["breeding"].dtFn.fnSetColumnVis( 0, false );	
		}else{				
			var	reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData()),
				urlObj=CMSOwnerCons.urls_info["breedingDetails"],
				req=JSON.stringify({"breedingid": 0});   	
			reqData["reqData"]=req;				
			$.fn.dataTableExt.sErrMode = 'throw';			
			CMSOwnerCons.dataTable["breeding"].dtFn=$('#breedingDataTable').dataTable({
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
					    		buckEarTagLink="",
					    		doeEarTagLink="",
					    		viewBuckEarTagLink="<a href='#view-breeding="+eachObj.breeding_id+"' >"+eachObj.buck_ear_tag+"</a>",
					    		viewDoeEarTagLink="<a href='#view-breeding="+eachObj.breeding_id+"' >"+eachObj.doe_ear_tag+"</a>";
					    	
					    		jsonRes[key][0] = eachObj.breeding_id;					    	
					    		jsonRes[key][1] =  viewDoeEarTagLink;
					    		jsonRes[key][2] =viewBuckEarTagLink ;
					    		jsonRes[key][3] = CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(eachObj.breeding_date));				    
					    		jsonRes[key][4] = eachObj.breeding_kids_no;			    		
					    		jsonRes[key][5] =!!eachObj.breeding_aborted_dt? CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(eachObj.breeding_aborted_dt)):"";	
					    		var actionHtml="<a href='javascript:void(0);' class='edit-link-breeding-dtls' data-breeding-id='"+eachObj.breeding_id+"'><i class='fa fa-pencil-square-o'></i> Edit</a> ";
					    		jsonRes[key][6] = actionHtml;
					    	} 

						    return jsonRes;
			     		}else{		
			     			return [];
			     		}
			     	}
			    }			    
			  },
			  destroy: true,
			  "bStateSave": true,
			  "aaSorting": [[ 0, "desc" ]],	
			  "aoColumnDefs": [{'bSortable': false, 'aTargets': [6]}],		   
			  'processing': false,
			  'serverSide': false,
			  'bLengthChange': false, 
			  'fnInitComplete' :function(){	
					setTimeout(function(){
						$("#breedingDataTable" ).wrapAll( "<div class='table-responsive'></div>" );	
						adjustScreen.contentHeight();	
					},100);					
			  },
			  'fnDrawCallback' : function() {
				  adjustScreen.contentHeight();
			  }
			});
			var oSettings = CMSOwnerCons.dataTable["breeding"].dtFn.fnSettings();
		    oSettings._iDisplayLength = 10;			     
		    CMSOwnerCons.dataTable["breeding"].dtFn.fnSetColumnVis( 0, false );
		}			
	};
	
	// This is to load and display breeding kids table 
	var loadBreedingKidsDataTable=function(breedingid,appEl,isView){
		
		if(!!CMSOwnerCons.dataTable["breedingKids"].showFlg){
			
			var	reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData()),
			urlObj=CMSOwnerCons.urls_info["breedKidDetails"],
			req=JSON.stringify({"breedingid": breedingid,"breedkidid":0});   	
			reqData["reqData"]=req;	
			oSettings.ajax.beforeSend=function(jqXHR,settings ){
		    	settings.data = JSON.stringify(reqData);
	        };

			CMSOwnerCons.dataTable["breedingKids"].dtFn._fnAjaxUpdate();
			CMSOwnerCons.dataTable["breedingKids"].dtFn.fnSetColumnVis( 0, false );	
			
		}else{				
			var	reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData()),
				urlObj=CMSOwnerCons.urls_info["breedKidDetails"],
				req=JSON.stringify({"breedingid": breedingid,"breedkidid":0});   	
			reqData["reqData"]=req;				
			$.fn.dataTableExt.sErrMode = 'throw';			
			CMSOwnerCons.dataTable["breedingKids"].dtFn=$(appEl).dataTable({
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
					     	var jsonRes=JSON.parse(dataJson.resData),	
				     		noOfKids=jsonRes.length,
				     		leastCattleDoB=0;
					     	$("#breedingkidsno").val(noOfKids);
					     	
					     	for (var key in jsonRes){
					    		var eachObj=jsonRes[key],
					    		   goatDoB=new Date(eachObj.cattle_dob);
					    		
					    		console.log(" eachObj ",eachObj.cattle_farm_status);
					    		jsonRes[key][0] = eachObj.cattle_id;					    	
					    		jsonRes[key][1] = eachObj.cattle_ear_tag_id;
					    		jsonRes[key][2] = eachObj.breed_kids_weight;
					    		jsonRes[key][3] =eachObj.cattle_gender;				    
					    		jsonRes[key][4] = !!eachObj.cattle_dob? CMSCom.dateFn.cusDateAsDD_MM_YYYY(goatDoB):"";
					    		jsonRes[key][5] = !!eachObj.breed_kids_weaning_dt? CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(eachObj.breed_kids_weaning_dt)):"";		
		
					    		if(!isView){
					    			if(!!eachObj.cattle_farm_status){
					    				var actionHtml="<a href='javascript:void(0);' class='edit-link-breedingKids-dtls' data-breeding-id='"+eachObj.breeding_id+"' data-breed-kid-id='"+eachObj.breed_kid_id+"'><i class='fa fa-pencil-square-o'></i> Edit</a> ";
						    			jsonRes[key][6] = actionHtml;
					    			}else{
					    				jsonRes[key][6] = "";
					    			}
					    			
					    		}
					    		try{
					    			if(leastCattleDoB===0){
					    				leastCattleDoB=goatDoB;
					    			}else{					    			
						    			if(goatDoB.getTime()<leastCattleDoB.getTime()){
						    				leastCattleDoB=goatDoB;
							    		}
					    			}		
					    		}catch(dobErr){};
					    		
					    	} 		
					     	$("#breedingCattleleastDob").val(leastCattleDoB);				 
						    return jsonRes;
			     		}else{
			     			$("#breedingkidsno").val(0);
			     			return [];
			     		}
			     	}
			    }			    
			  },
			  destroy: true,
			  "aaSorting": [[ 0, "desc" ]],	
			//  "aoColumnDefs": [{'bSortable': false, 'aTargets': [6]}],		   
			  'processing': false,
			  'serverSide': false,
			  'bLengthChange': false, 
			  'fnInitComplete' :function(){	
					setTimeout(function(){
						$(appEl ).wrapAll( "<div class='table-responsive'></div>" );	
						adjustScreen.contentHeight();
					    if(!isView){// This is to remove sorting for action column 
					    	$(appEl).find("th:eq(5)").removeClass("sorting");
					    }
						   
					},100);					
			  },
			  'fnDrawCallback' : function() {
				  adjustScreen.contentHeight();
			  }			  
			});
			var oSettings = CMSOwnerCons.dataTable["breedingKids"].dtFn.fnSettings();
		    oSettings._iDisplayLength = 5;	
		    if(!isView){// This is to remove sorting for action column 
			    oSettings.aoColumns[6].bSortable=false;
			    oSettings.aoColumns[6].sSortingClass="";			 
		    }

		    CMSOwnerCons.dataTable["breedingKids"].dtFn.fnSetColumnVis( 0, false );
		   // adjustScreen.mCustomScrollbar_FixDataTablePagin(appEl);
		}			
	};
	
	var showBreadingDetlsPageCallBackFn=function(){
		$("#breading-details").removeClass("hidden");					
		$("#breadcrumb-subpage").html("Breeding Details");
		loadBreedingDataTable();
		adjustScreen.contentHeight();		
	};
	
	var showBreedingDetlsPage=function(){
		var loadStatus= $("#breading-details").data("load-status");
		if(!!loadStatus){
			showBreadingDetlsPageCallBackFn();
		}else{
			$("#breading-details").load("partials/owner-breeding.html", function(response, status, xhr){
	            if(status === "success"){
	            	$("#breading-details").data("load-status",true);
	            	showBreadingDetlsPageCallBackFn();
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}  
	};	
	
	var displayDoeBuckDetailsSelectOption=function(cattleListDtls,doeid,buckid,breedingDt){	

			var cattleid=$("#cattleid").val(),
				selectDoeOptHtml="<option value=''></option>",
				selectBuckOptHtml="<option value=''></option>",
				errorMsg="",
				noAdultBuckCattle=true,
				noAdultDoeCattle=true;
			
			$("#cattleParent").removeClass("hidden");			
			$("#breedingdoeid").html("");
			$("#breedingbuckid").html("");
			
			for(var i in cattleListDtls){	
				var cattleObj=cattleListDtls[i];			
				if(cattleObj.cattle_status===true&&cattleObj.cattle_category==="A"){					
					if(cattleObj.cattle_gender==="M"){
						noAdultBuckCattle=false;
						selectBuckOptHtml+="<option value='"+cattleObj["cattle_id"]+"'>"+cattleObj["cattle_ear_tag_id"]+"</option>";					
					}else{
						noAdultDoeCattle=false;
						selectDoeOptHtml+="<option value='"+cattleObj["cattle_id"]+"'>"+cattleObj["cattle_ear_tag_id"]+"</option>";
					}				
				}					
			}
			if(noAdultBuckCattle&&noAdultDoeCattle){
				errorMsg="Doe and Buck";
			}else if (noAdultBuckCattle){
				errorMsg="Buck";
			}else if (noAdultDoeCattle){
				errorMsg="Doe";
			}
			
			if(!!errorMsg){
				CMSMessage.showSubPageMsg({"status":0,"msg":"No active Adult "+errorMsg+" with Date of Birth less than \""+breedingDt+"\""});
			}
			$("#breedingdoeid").html(selectDoeOptHtml);
			$("#breedingbuckid").html(selectBuckOptHtml);
		
			/*var disStatus=$("#breedingdoeid").data("dis-status");
			if(!!disStatus){			
				$("#breedingdoeid").data("dis-status",true);				
			}else{					
				$("#breedingdoeid").data("dis-status",true);
				setTimeout(function(){	
					$("#breedingdoeid").chosen({allow_single_deselect: true});
					$("#breedingbuckid").chosen({allow_single_deselect: true});
				});
			}*/
			setTimeout(function(){	
				if(!!doeid){
					$("#breedingdoeid").val(doeid);
				}
				if(!!buckid){
					$("#breedingbuckid").val(buckid);
				}
				$("#breedingdoeid").trigger("chosen:updated");
				$("#breedingbuckid").trigger("chosen:updated");
			});			 	
	};
	
	var fetchDoeBuckDetailsByBreedingDt=function(breedingDt,doeid,buckid){
	
		var reqData=reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData()),
		urlObj=CMSOwnerCons.urls_info["getCattleDetailsOfAssignedFarmAndBreedDt"];
		reqData["breedingdt"]=breedingDt;			
		reqData["reqData"]=JSON.stringify({});	
		
		CMSCom.ajaxService.getJsonData(urlObj,reqData).done(function(jsonResData) {
			if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){	
			
				jsonResData=JSON.parse(jsonResData);
				
				if(jsonResData.status==="1"){					
					var resData=JSON.parse(jsonResData.resData);	
		
					displayDoeBuckDetailsSelectOption(resData,doeid,buckid,breedingDt);
				}else{
					CMSMessage.showSubPageMsg({"status":0,"msg":"No active Adult Doe and Buck with Date of Birth less than \""+breedingDt+"\""});
					$("#breedingdoeid").html("<option value=''></option>");
					$("#breedingbuckid").html("<option value=''></option>");
					$("#breedingdoeid").trigger("chosen:updated");
					$("#breedingbuckid").trigger("chosen:updated");
				}									
			}	
		}).fail(CMSCom.exceptionHandler.ajaxFailure);	
		
	};
	
	var setBreedingDateCalendar=function(elId,futureDt){
		 $(elId).datepicker({ format: "dd-mm-yyyy",endDate: futureDt,forceParse: false 	         
	     })
	     .on('changeDate', function(ev){
	    
    	    $(this).datepicker('hide');
    	    
    	    if(elId==="#breedingdate"){
    	    	var breedingdate=$("#breedingdate").val(),
    	    		doeid=$("#breedingdoeid").val(),
    	    		buckid=$("#breedingbuckid").val(),
    	    		breedingCattleleastDob=$("#breedingCattleleastDob").val();

    	    	if(breedingdate!==""){	    	    		
    	    		if(validateBreedingDtWrtKidsDOB()){
	    	    		fetchDoeBuckDetailsByBreedingDt(breedingdate,doeid,buckid);	
	    	    		populateBreedingIfExists();
	    	    	}	    	    		
    	    	}	    	    		    	
    	    }    		
	    	
	     }).prop('readonly','readonly');

		 $(elId).off("click").on("click",function() {
		        $(elId).datepicker().datepicker("show");
		 });
		 $(elId+"-calen").off("click").on("click",function() {
		        $(elId).datepicker().datepicker("show");
		 });
	};
	var updateBreedingDateCalendar=function(formId,elId,futureDt){
	
     	$(formId+" "+elId).datepicker({ format: "dd-mm-yyyy",endDate: futureDt}); 
     	$(formId+" "+elId).datepicker('update');
	};

	
	var loadBreedingDtlsAddUpdatePage=function(){		
		try{

			 setBreedingDateCalendar('#breedingdate',new Date());
			 setBreedingDateCalendar('#breedingaborted_dt',new Date());
		
			 setBreedingDateCalendar('#breedinglactstartdt',new Date());
			 setBreedingDateCalendar('#breedinglactenddt',null);			 
				adjustScreen.contentHeight();
		}catch(e){
			//console.log(" e ---> ",e);
		}		
	};
	
	var populateBreedingDtls=function(breedingDtls){
		
     	var breedingDtlsArr=JSON.parse(breedingDtls.resData),
     		breedingDtlsObj=breedingDtlsArr[0],
     		breedingid=breedingDtlsObj.breeding_id,
     	 	breeding_lact_start_dt=!!breedingDtlsObj.breeding_lact_start_dt?CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(breedingDtlsObj.breeding_lact_start_dt)):"",
     		breeding_lact_end_dt=	!!breedingDtlsObj.breeding_lact_end_dt?CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(breedingDtlsObj.breeding_lact_end_dt)):"",
     		breeding_date=!!breedingDtlsObj.breeding_date?CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(breedingDtlsObj.breeding_date)):"",
     		breeding_aborted_dt=!!breedingDtlsObj.breeding_aborted_dt?CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(breedingDtlsObj.breeding_aborted_dt)):"";
		
     	$("#cms-saveBreedingDtls-form #breedinglactstartdt").val(breeding_lact_start_dt);
     	$("#cms-saveBreedingDtls-form #breedinglactenddt").val(breeding_lact_end_dt);
     	$("#cms-saveBreedingDtls-form #breedingdate").val(breeding_date);
     	$("#cms-saveBreedingDtls-form #breedingaborted_dt").val(breeding_aborted_dt);     	
     	$("#cms-saveBreedingDtls-form #breedingabortedno").val(breedingDtlsObj.breeding_aborted_no);
     	$("#cms-saveBreedingDtls-form #breedingkidsno").val(breedingDtlsObj.breeding_kids_no);
     	$("#cms-saveBreedingDtls-form #breedingid").val(breedingDtlsObj.breeding_id);
     	$("#cms-saveBreedingDtls-form #breed-uniquesynckey").val(breedingDtlsObj.unique_sync_key);
     	$("#cms-saveBreedingDtls-form #breedingcreateddt").val(CMSCom.dateFn.cusDateAsDD_MM_YYYY_TS(new Date(breedingDtlsObj.createddt)));
     	     	       
     	updateBreedingDateCalendar("#cms-saveBreedingDtls-form","#breedingdate",new Date());
     	updateBreedingDateCalendar("#cms-saveBreedingDtls-form","#breedinglactstartdt",new Date());
     	updateBreedingDateCalendar("#cms-saveBreedingDtls-form","#breedinglactenddt",null);
     	updateBreedingDateCalendar("#cms-saveBreedingDtls-form","#breedingaborted_dt",new Date());

     	loadBreedingDtlsAddUpdatePage();
     	fetchDoeBuckDetailsByBreedingDt(breeding_date,breedingDtlsObj.breeding_doe_id,breedingDtlsObj.breeding_buck_id); 
     	
		loadBreedingKidsDataTable(breedingDtlsObj.breeding_id*1,"#breedingKidsDataTable",false);  // false - this is for edit page
		$('#breedingKidsDataTableHolder').removeClass("hidden");
	};
	
	var populateViewBreedingDtls=function(breedingDtls){
		
     	var breedingDtlsArr=JSON.parse(breedingDtls.resData),
     		breedingDtlsObj=breedingDtlsArr[0],
     		breedingid=breedingDtlsObj.breeding_id,
     	 	breeding_lact_start_dt=!!breedingDtlsObj.breeding_lact_start_dt?CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(breedingDtlsObj.breeding_lact_start_dt)):"",
     		breeding_lact_end_dt=	!!breedingDtlsObj.breeding_lact_end_dt?CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(breedingDtlsObj.breeding_lact_end_dt)):"",
     		breeding_date=!!breedingDtlsObj.breeding_date?CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(breedingDtlsObj.breeding_date)):"",
     		breeding_aborted_dt=!!breedingDtlsObj.breeding_aborted_dt?CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(breedingDtlsObj.breeding_aborted_dt)):"";
     		
     	//$("#view-breeding-id").html(breedingid);
     	$("#view-breeding-date").html(breeding_date);
     	$("#view-breeding-doe").html(breedingDtlsObj.doe_ear_tag);
     	$("#view-breeding-buck").html(breedingDtlsObj.buck_ear_tag);
     	$("#view-breeding-StartofLactation").html(breeding_lact_start_dt);
     	$("#view-breeding-EndofLactation").html(breeding_lact_end_dt);     	
     	$("#view-breeding-DateofAbortion").html(breeding_aborted_dt);
     	$("#view-breeding-NoofAbortedKids").html(breedingDtlsObj.breeding_aborted_no);
     	$("#view-breeding-NoofKids").html(breedingDtlsObj.breeding_kids_no);     	
     	$("#view-breeding-editlink").prop("href","#edit-breeding="+breedingid);
    	
	};
	
	var  fetchBreedingDtls=function(breedingid,page){
		var urlObj=CMSOwnerCons.urls_info["breedingDetails"],
	 	reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData()),		
		req=JSON.stringify({"breedingid":breedingid}); 
		reqData["reqData"]=req;		
		reqData=JSON.stringify(reqData);
	
		CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {
			if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
				if(jsonResData.status==="1"){
					if("addOREdit"===page){
						populateBreedingDtls(jsonResData);
					}else{
						populateViewBreedingDtls(jsonResData);
					}				
					
				}else{
					CMSMessage.showSubPageMsg(jsonResData);
				}		
			}
		}).fail(CMSCom.exceptionHandler.ajaxFailure);	
	};
	
	
	var displayBreedingKidAddUpdateModal=function(jsonResData){
		
		var resData=JSON.parse(jsonResData.resData),
			farmDtls=resData["farm_dtls"],		
			selectOptHtml="<option value=''></option>";		
		
		for(var i in farmDtls){	
			var farmObj=farmDtls[i];
				selectOptHtml+="<option value='"+farmObj["farm_id"]+"'>"+farmObj["farm_name"]+"</option>";		
		}
		
		$("#breedingKidCattleSelectFarm").html(selectOptHtml);
		$("#breedingKidCattleSelectFarm").chosen({allow_single_deselect: true});	  
		
		 setBreedingDateCalendar('#breed-cattledob',new Date() );
		 setBreedingDateCalendar('#breedkidsweaningdt', null );	
		 setBreedingDateCalendar('#breedkidsnurlactdt',new Date());		

	};
	
	var loadBreedingKidAddUpdateModal=function(){		
		
		var urlObj=CMSOwnerCons.urls_info["getFarmDetailsByUser"],
	 	reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData());		
		reqData["reqData"]={};
		reqData=JSON.stringify(reqData);
		
		// This is to fetch farm details for select a farm select box
		CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {
			if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
				if(jsonResData.status==="1"){
					displayBreedingKidAddUpdateModal(jsonResData);
				}else{
					CMSMessage.showSubPageMsg({"status":0,"msg":"Farm not assigned, so unable to add cattle "});
				}
				
			}	
		}).fail(CMSCom.exceptionHandler.ajaxFailure);		
	};
	
	var initBreedingAddOrUpdatePage=function(){
		$("#breedingdoeid").html("<option value=''></option>");
		$("#breedingbuckid").html("<option value=''></option>");
		$("#breedingdoeid").chosen({allow_single_deselect: true});
		$("#breedingbuckid").chosen({allow_single_deselect: true});			
		
		adjustScreen.mCustomScrollbar_FixDataTablePagin("breedingKidsDataTable");
	};
	
	var showBreedingAddOrUpdatePageCallBackFn=function(subPageTitle,editId){
		$("#add-breading").removeClass("hidden");
		$("#breadcrumb-subpage").html("Breeding Details");
		$("#cmsBreedingDetailsSaveTitle").html(subPageTitle);		
	
		if(!!editId){			
			fetchBreedingDtls(editId,"addOREdit");		
			$("#cms-breedingaddKidsBtn").removeClass("hidden");
		

		}else{			
			loadBreedingDtlsAddUpdatePage();	
			$("#cms-breedingaddKidsBtn").addClass("hidden");			
			$('#breedingKidsDataTableHolder').addClass("hidden");
		}
		$("#breedingid").val(0);		
		resetBreedingDtlsHandler();		
		adjustScreen.contentHeight();

	};
	var showBreedingAddOrUpdatePage=function(subPageTitle,editId){
		var loadStatus= $("#add-breading").data("load-status");
		if(!!loadStatus){		
			showBreedingAddOrUpdatePageCallBackFn(subPageTitle,editId);
		}else{
			$("#add-breading").load("partials/owner-breeding-add-update.html", function(response, status, xhr){
	            if(status === "success"){
	            	$("#add-breading").data("load-status",true);	            
	            	showBreedingAddOrUpdatePageCallBackFn(subPageTitle,editId);
	            	initBreedingAddOrUpdatePage();
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
			
			// Loading Add Kid Modal 
			$("#breeding-add-kid-modal").load("partials/owner-breeding-add-kid.html", function(response, status, xhr){
	            if(status === "success"){
	            	loadBreedingKidAddUpdateModal();
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}  
	};
	
	var validateBreedingDtWrtKidsDOB=function(){
		var success=true,
			breedingdate=$("#breedingdate").val(),
  	    	breedingCattleleastDob=$("#breedingCattleleastDob").val();

    	if(breedingdate!==""){
    		var breedingdateObj=$("#breedingdate").data("datepicker").getDate();
    		if(breedingCattleleastDob*1!==0){
    	    	breedingCattleleastDob=new Date(breedingCattleleastDob);    	    		
	    		if(!(breedingdateObj.getTime()<breedingCattleleastDob.getTime())){	    	    			
	    			success=false;
    	    		CMSMessage.showSubPageMsg({"status":0,"msg":"Date of Breeding should be less than kids' Date of Birth "});
    	    	}
    	    }    		
    	}	    	    		    	

		return success;
	};
	
	var validateBreedingDtls=function(e){
		
		var	breedingdoeid=$("#breedingdoeid").val()*1,
			breedingbuckid=$("#breedingbuckid").val()*1,
			breedingdate=$("#breedingdate").val(),
			breedingdateObj = $("#breedingdate").data("datepicker").getDate(),
			
			breedinglactstartdt=$("#breedinglactstartdt").val(),
			breedinglactstartdtObj = $("#breedinglactstartdt").data("datepicker").getDate(),
			
			breedinglactenddt=$("#breedinglactenddt").val(),
			breedinglactenddtObj = $("#breedinglactenddt").data("datepicker").getDate(),
			
			breedingaborteddt=$("#breedingaborted_dt").val(),
			breedingaborteddtObj = $("#breedingaborted_dt").data("datepicker").getDate(),
			success=true,
			msg="",
			breedingDateDiff=1,
			breedingDatelactweanDiff=1,
			breedingabortDiff=1,
			breedinglactstartEndDiff=1,
			breedinglactDiff=1;
			
		
		if(!!breedingaborteddt){
			breedingabortDiff=CMSCom.dateFn.daysBetween(breedingdateObj,breedingaborteddtObj);		
		}
		
		if(!!breedingdate){
			if(!!breedinglactstartdt){
				breedinglactDiff=CMSCom.dateFn.daysBetween(breedingdateObj,breedinglactstartdtObj);
			}
			if(!!breedinglactstartdt&&!!breedinglactenddt){
				breedinglactstartEndDiff=CMSCom.dateFn.daysBetween(breedinglactstartdtObj,breedinglactenddtObj);		
			}
		}
		
		 $(".cms-ipt-error").hide();
		 if(!breedingdate){			
			$("#ipt-er-breedingdate").show();
			success=false;
			adjustScreen.mCustomScrollbarTop();	
		 }else if(!breedingdoeid||breedingdoeid===0){
			$("#ipt-er-breedingdoeid").show();
			success=false;
			adjustScreen.mCustomScrollbarTop();
		}else if(!breedingbuckid||breedingbuckid===0){
			$("#ipt-er-breedingbuckid").show();
			success=false;
			adjustScreen.mCustomScrollbarTop();

		}else if(!breedinglactstartdt&&!!breedinglactenddt){		
			$("#ipt-er-breedinglactstartdt").show();
			success=false;	
			adjustScreen.mCustomScrollbarTop();	
		}else if(breedinglactDiff<=0){
			msg="\"Start of Lactation\" should be greater than \"Date of Breed \"";
			success=false;		
		}else if(breedinglactstartEndDiff<=0){
			msg="\"End of Lactation\" should be greater than \"Start of Lactation \"";
			success=false;	
		}else if(breedingabortDiff<=0){
			msg="\"Date of Abortion\" should be greater than \"Date of Breed \"";
			success=false;
		}
		if(!success){		
			CMSMessage.showSubPageMsg({"status":0,"msg":msg});
		}else{
			success=validateBreedingDtWrtKidsDOB();
		}
		
		return success;
	};		
	
	// This is to save and update breeding dts
	var saveBreedingDtls=function(e){
		try{
			if($("#cms-saveBreedingDtls-form")[0].checkValidity()&&validateBreedingDtls()){		
				
			var urlObj=CMSOwnerCons.urls_info["saveBreeding"],
				saveBreedingDts={},
				reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData()),
				cattlefarmid=$("#addCattleSelectFarm").val(),
				cattledob=$("#cattledob").val(),
				breedingid=$("#breedingid").val()*1,
				breedingcreateddt=$("#breedingcreateddt").val();
		
			$("#cms-saveBreedingDtls-form .form-dt").each(function(){
				saveBreedingDts[$(this).prop("name")]=$(this).val();
			});
			$("#cms-saveBreedingDtls-form .form-dt-date").each(function(){
				var inval=$(this).val();
				if(!!inval){
					saveBreedingDts[$(this).prop("name")]=CMSCom.dateFn.cusDateServerFormat(inval);
				}				
			});
			$("#cms-saveBreedingDtls-form .form-dt-num").each(function(){
				saveBreedingDts[$(this).prop("name")]=$(this).val()*1;
			});
			
			if(!!breedingid){
				saveBreedingDts["breedingid"]=breedingid;
			}
			if(!breedingcreateddt){
				delete saveBreedingDts["createddt"];
			}		
			
			saveBreedingDts["breedingvillage"]="";	
			reqData["reqData"]=saveBreedingDts;			
			reqData=JSON.stringify(reqData);
			
				CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {				
					if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
						if(jsonResData.status==="1"){
							var resData=JSON.parse(jsonResData.resData);	
							var msg=breedingid===0?"Breeding details successfully saved ":"Breeding details successfully updated  ";
				
							window.location.hash = "breeding-details";					
							
							CMSMessage.showSubPageMsg({"status":1,"msg":msg});
						}else{
							CMSMessage.showSubPageMsg(jsonResData);
						}					
					}					
				}).fail(CMSCom.exceptionHandler.ajaxFailure);	 
			
			}
		}catch(er){
			console.log(" er ",er);
		}
		e.preventDefault();		
	};
	
	
	var resetBreedingDtlsHandler=function(e){
		var breedingid=$("#breedingid").val()*1;
		$("#cms-saveBreedingDtls-form .form-dt").each(function(){
			$(this).val("");
		});
		$("#cms-saveBreedingDtls-form .form-dt-num").each(function(){
			$(this).val("");
		});
		$("#cms-saveBreedingDtls-form .form-dt-date").each(function(){
			$(this).val("");			
		});

		$("#breedingdoeid").val(0);
		$("#breedingbuckid").val(0);
		$("#breedingdoeid").html("<option value=''></option>");
		$("#breedingbuckid").html("<option value=''></option>");
		$("#breedingdoeid").trigger("chosen:updated");
		$("#breedingbuckid").trigger("chosen:updated");
		$(".cms-ipt-error").hide();
		$("#breedingkidsno").val(0);
		$("#breedingCattleleastDob").val(0);
		if(!!breedingid&&breedingid*1!==0){
			showBreedingAddOrUpdatePage("Edit Breeding Details",breedingid);
		}		
	};
	
	var breedingaddKidsHandlder=function(){		
		//$("#breedingid").val(0);
		var breedingdoeid=$("#breedingdoeid").val()*1,
			breedingbuckid=$("#breedingbuckid").val()*1;
		
		if(!breedingdoeid||breedingdoeid===0){
			CMSMessage.showSubPageMsg({"status":0,"msg":"Select Ear Tag of Doe"});
		}else if(!breedingbuckid||breedingbuckid===0){
			CMSMessage.showSubPageMsg({"status":0,"msg":"Select Ear Tag of Buck"});
		}else{
			$("#breedingKidCattleSelectFarm").prop('disabled', false);
			$("#cms-savebreedAddKidDtls-form #breeding-kidid").val(0);
			
			resetbreedAddKidDtls();
			$("#breedAddKidModal .cmsModalTitle").html("Add Kid");
			$("#breedAddKidModal").modal("show");	
		}		
	};

	 
	var editlinkBreedingdtlsHandlder=function(){
		var _this=$(this),
		breedingid=_this.data("breeding-id");
		window.location.hash="edit-breeding="+breedingid;
	};
	
	var resetbreedAddKidDtls=function(){
		
		var breedingid=$("#breedingid").val(),
			breedkidid=$("#cms-savebreedAddKidDtls-form #breeding-kidid").val();
		
		$("#cms-savebreedAddKidDtls-form .form-dt").each(function(){
			$(this).val("");
		});
		$("#cms-savebreedAddKidDtls-form .form-dt-num").each(function(){
			$(this).val("");
		});
		$("#cms-savebreedAddKidDtls-form .form-dt-date").each(function(){
			$(this).val("");			
		});

		$("#breedingKidCattleSelectFarm").val(0);
		$("#breedingKidCattleSelectFarm").trigger("chosen:updated");
		
		// clear radio values and checking first radio button
		$("#cms-savebreedAddKidDtls-form .form-dt-radio:checked").prop('checked', false);
		$("#cms-savebreedAddKidDtls-form .form-dt-radio-holder").each(function(){
			$(this).find(".form-dt-radio:eq(0)").prop('checked', true);
		});
		$("#cms-savebreedAddKidDtls-form #breeding-cattlecode").val("");
		$("#cms-savebreedAddKidDtls-form #breeding-cattlestatus").val(true);
		$("#cms-savebreedAddKidDtls-form #breeding-cattlecategory").val("K");
		$("#cms-savebreedAddKidDtls-form #breeding-cattleid").val(0);
		
		
		if(!!breedkidid&&breedkidid*1!==0){
			fetchBreedingKidDtls(breedingid,breedkidid);
		}	
	};
	
	
	var validatebreedAddKidDtls=function(e){
		var	breedingdate=$("#breedingdate").val(),
			breedingdateObj = $("#breedingdate").data("datepicker").getDate(),
			breedcattledob=$("#breed-cattledob").val(),
			breedcattledobObj = $("#breed-cattledob").data("datepicker").getDate(),
			breedkidsnurlactdt=$("#breedkidsnurlactdt").val(),
			breedkidsnurlactdtObj = $("#breedkidsnurlactdt").data("datepicker").getDate(),
			breedkidsweaningdt=$("#breedkidsweaningdt").val(),
			breedkidsweaningdtObj = $("#breedkidsweaningdt").data("datepicker").getDate(),
			kidFarmId=$("#breedingKidCattleSelectFarm").val()*1,
			breedcattlelocation=$("#breed-cattlelocation").val(),
			breedcattlelandmark=$("#breed-cattlelandmark").val(),
			breedcattlelatt=$("#breed-cattlelatt").val(),
			breedcattlelong=$("#breed-cattlelong").val(),
			success=true,
			msg="",
			breedingDateDiff=1,
			breedkidsDiff=1,
			breedingDatelactweanDiff=1,
			msgObj;
		
		if(!!breedcattledob){
			breedingDateDiff=CMSCom.dateFn.daysBetween(breedingdateObj,breedcattledobObj);	
		}
		if(!!breedcattledobObj&&!!breedkidsnurlactdtObj){
			breedkidsDiff=CMSCom.dateFn.daysBetween(breedcattledobObj,breedkidsnurlactdtObj);	
		}
		if(!!breedkidsnurlactdt&&!!breedkidsweaningdt){
			breedingDatelactweanDiff=CMSCom.dateFn.daysBetween(breedkidsnurlactdtObj,breedkidsweaningdtObj);	
		}
		
		if(!breedcattledob){			
			msg="Please Select Date of Birth ";
			success=false;
		}else if(breedingDateDiff<=0){
			msg="\"Kids' Date of Birth\" should be greater than \"Date of Breeding \"";
			success=false;	
			
		}else if(!kidFarmId||kidFarmId===0){			
			msg="Please Select Farm";
			success=false;	
		}else if(!breedkidsnurlactdt&&!!breedkidsweaningdt){		
			msg="Please Select Nursing Lactation Date ";	
			success=false;	
		}else if(breedkidsDiff<0){
			msg="\"Nursing Lactation Date\" should be greater than or equal to \"Kids' Date of Birth\"";
			success=false;		
		}else if(breedingDatelactweanDiff<=0){
			msg="\"Weaning Date\" should be greater than \"Nursing Lactation Date \"";
			success=false;	
		}else  if(!!breedcattlelatt&&!CMSCom.validation.isDecimal(breedcattlelatt)){		
			msg=" Latitude should be decimal ";
			success=false;
		}else if(!!breedcattlelong&&!CMSCom.validation.isDecimal(breedcattlelong)){			
			msg=" Longitude  should be decimal  ";
			success=false;
		}
	
		if(!success){
			msgObj={"success":0,"msg":msg};
			CMSMessage.showCommonModalMsg(msgObj,"#breedAddKidModal");
		}
		return success;
	};
	var savebreedAddKidDtls=function(e){
		try{
			if($("#cms-savebreedAddKidDtls-form")[0].checkValidity()&&validatebreedAddKidDtls()){		
				
			var urlObj=CMSOwnerCons.urls_info["saveBreedKid"],
				saveBreedingKidsDts={},
				reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData()),
				cattlefarmid=$("#addCattleSelectFarm").val(),
				cattledob=$("#cattledob").val(),
				breedingid=$("#breedingid").val()*1,
				breedingCattleid=$("#breeding-cattleid").val()*1,
				breedingKidid=$("#breeding-kidid").val()*1,
				breedingKidcreateddt=$("#breeding-Kidcreateddt").val();
		
			$("#cms-savebreedAddKidDtls-form .form-dt").each(function(){
				saveBreedingKidsDts[$(this).prop("name")]=$(this).val();
			});
			$("#cms-savebreedAddKidDtls-form .form-dt-num").each(function(){
				saveBreedingKidsDts[$(this).prop("name")]=$(this).val()*1;
			});
			$("#cms-savebreedAddKidDtls-form .form-dt-date").each(function(){
				var inval=$(this).val();
				if(!!inval){
					saveBreedingKidsDts[$(this).prop("name")]=CMSCom.dateFn.cusDateServerFormat(inval);
				}				
			});
			$("#cms-savebreedAddKidDtls-form .form-dt-refer").each(function(){					
				var _this=$(this),
					refer=_this.data("refer"),field=_this.data("field"),				
					val =_this.val(),farm={};
					
				farm[field]=val;
				
				if(!!val){
					saveBreedingKidsDts[refer]=farm;
				}
			});
			
			$("#cms-savebreedAddKidDtls-form .form-dt-radio:checked").each(function(){
				saveBreedingKidsDts[$(this).prop("name")]=$(this).val();
			});
			
			// Breeading details 
			$("#cms-saveBreedingDtls-form .form-dt").each(function(){
				saveBreedingKidsDts[$(this).prop("name")]=$(this).val();
			});
			$("#cms-saveBreedingDtls-form .form-dt-num").each(function(){
				saveBreedingKidsDts[$(this).prop("name")]=$(this).val()*1;
			});
			

			if(!!breedingKidid&&breedingKidid!==0){
				saveBreedingKidsDts["id"]=breedingKidid;
			}
			if(!!breedingCattleid&&breedingCattleid!==0){
				saveBreedingKidsDts["cattleid"]=breedingCattleid;
			}else{
				saveBreedingKidsDts["cattleid"]=0;
			} 
			if(!breedingKidcreateddt){
				delete saveBreedingKidsDts["createddt"];
			}	
			saveBreedingKidsDts["objBreedingEntity"]={"breedingid":breedingid};
			saveBreedingKidsDts["doecattle"]={"cattleid":saveBreedingKidsDts["breedingdoeid"]}; // this is due to switched for DB
			saveBreedingKidsDts["buckcattle"]={"cattleid":saveBreedingKidsDts["breedingbuckid"]}; // this is due to switched for DB
			
			reqData["reqData"]=saveBreedingKidsDts;			
			reqData=JSON.stringify(reqData);
			
				CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {				
					if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
						var msgObj;
						if(jsonResData.status==="1"){
							$("#breedAddKidModal").modal("hide");
							var resData=JSON.parse(jsonResData.resData),	
								msg;
							if(breedingCattleid===0){
								msg="Breeding Kids details successfully saved ";
								var noOfKids=$("#breedingkidsno").val();
								$("#breedingkidsno").val(noOfKids*1+1);		
							}else{
								msg="Breeding  Kids details successfully updated  ";
							}
					
							//loadBreedingKidsDataTable(breedingid);
							loadBreedingKidsDataTable(breedingid,"#breedingKidsDataTable");// false - this is for edit page
						
							CMSMessage.showSubPageMsg({"status":1,"msg":msg});
						}else{
							msgObj={"success":0,"msg":jsonResData.msg};
							CMSMessage.showCommonModalMsg(msgObj,"#breedAddKidModal");
							
						}							
					}					
				}).fail(CMSCom.exceptionHandler.ajaxFailure);	/* */
			
			}
		}catch(er){
			console.log(" er ",er);
		}
		e.preventDefault();	
		
		
	};
	
	var populateBreedKidsDtls=function(breedingKidsDtls){
		
	  	var breedingKidsDtlsArr=JSON.parse(breedingKidsDtls.resData),
	  	breedingKidsDtlsObj=breedingKidsDtlsArr[0],
 		breedingid=breedingKidsDtlsObj.breeding_id,
 	 	breedingCattledob=!!breedingKidsDtlsObj.cattle_dob?CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(breedingKidsDtlsObj.cattle_dob)):"",
 	 	breedkidsnurlactdt=!!breedingKidsDtlsObj.breed_kids_nur_lact_dt?CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(breedingKidsDtlsObj.breed_kids_nur_lact_dt)):"",
 	 	breedkidsweaningdt=!!breedingKidsDtlsObj.breed_kids_weaning_dt?CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(breedingKidsDtlsObj.breed_kids_weaning_dt)):"";

	 	$("#cms-savebreedAddKidDtls-form #breedkidsnurlactdt").val(breedkidsnurlactdt);
	 	$("#cms-savebreedAddKidDtls-form #breedkidsweaningdt").val(breedkidsweaningdt);
	 	$("#cms-savebreedAddKidDtls-form #breed-cattledob").val(breedingCattledob);
	 	
	 	updateBreedingDateCalendar("#cms-savebreedAddKidDtls-form","#breedkidsnurlactdt",new Date());
	 	updateBreedingDateCalendar("#cms-savebreedAddKidDtls-form","#breedkidsweaningdt",null);
	 	updateBreedingDateCalendar("#cms-savebreedAddKidDtls-form","#breed-cattledob",new Date());
	 	
	 	$("#cms-savebreedAddKidDtls-form #breedkidsweight").val(breedingKidsDtlsObj.breed_kids_weight);	 	
	 	$("#cms-savebreedAddKidDtls-form #breed-cattlelocation").val(breedingKidsDtlsObj.cattle_location);
	 	$("#cms-savebreedAddKidDtls-form #breed-cattlelandmark").val(breedingKidsDtlsObj.cattle_land_mark);
	 	$("#cms-savebreedAddKidDtls-form #breed-cattlelatt").val(breedingKidsDtlsObj.cattle_latt);
	 	$("#cms-savebreedAddKidDtls-form #breed-cattlelong").val(breedingKidsDtlsObj.cattle_long);	 	
	 	$("#cms-savebreedAddKidDtls-form #breeding-cattlecode").val(breedingKidsDtlsObj.cattlecode);
		$("#cms-savebreedAddKidDtls-form #breeding-cattlestatus").val(breedingKidsDtlsObj.cattle_status);
		$("#cms-savebreedAddKidDtls-form #breeding-cattlecategory").val(breedingKidsDtlsObj.cattle_category);
		$("#cms-savebreedAddKidDtls-form #breeding-cattleid").val(breedingKidsDtlsObj.cattle_id);
		$("#cms-savebreedAddKidDtls-form #breeding-kidid").val(breedingKidsDtlsObj.breed_kid_id);		
		$("#breedingKidCattleSelectFarm").val(breedingKidsDtlsObj.cattle_farm_id);		
	   	$("#cms-savebreedAddKidDtls-form #breeding-Kidcreateddt").val(CMSCom.dateFn.cusDateAsDD_MM_YYYY_TS(new Date(breedingKidsDtlsObj.cattle_created_dt)));	   	
		$("#breedingKidCattleSelectFarm").trigger("chosen:updated");
		$("#cms-savebreedAddKidDtls-form .form-dt-radio-holder input[value="+breedingKidsDtlsObj.cattle_gender+"]").prop("checked",true);
		
		$("#breedAddKidModal .cmsModalTitle").html("Edit Kid");
	 	$("#breedAddKidModal").modal("show");
	};
	
	
	// This is to fetch breed kids dtls 
	var  fetchBreedingKidDtls=function(breedingid,breedkidid){
		var urlObj=CMSOwnerCons.urls_info["breedKidDetails"],
	 	reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData()),		
	 	req=JSON.stringify({"breedingid": breedingid,"breedkidid":breedkidid});   
		reqData["reqData"]=req;		
		reqData=JSON.stringify(reqData);
	
		CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {
			if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
				if(jsonResData.status==="1"){
					populateBreedKidsDtls(jsonResData);					
				}else{
					CMSMessage.showSubPageMsg(jsonResData);
				}		
			}
		}).fail(CMSCom.exceptionHandler.ajaxFailure);	
	};
	
	// This is edit link handler in breed kids datatable
	var editlinkBreedingKidsdtlsHandlder=function(){
		var _this=$(this),
		breedingid=_this.data("breeding-id"),
		breedkidid=_this.data("breed-kid-id"),
		breedingdoeid=$("#breedingdoeid").val()*1,
		breedingbuckid=$("#breedingbuckid").val()*1;
		
		if(!breedingdoeid||breedingdoeid===0){
			CMSMessage.showSubPageMsg({"status":0,"msg":"Select Ear Tag of Doe"});
		}else if(!breedingbuckid||breedingbuckid===0){
			CMSMessage.showSubPageMsg({"status":0,"msg":"Select Ear Tag of Buck"});
		}else{
			$("#breedingKidCattleSelectFarm").prop('disabled', true);
			fetchBreedingKidDtls(breedingid,breedkidid);	
		}
	};
	

	var cancelbreedAddKidDtls=function(){
		$("#breedAddKidModal").modal("hide");
	};
	
	var showBreedingViewPageCallBackFn=function(subPageTitle,editId){
		$("#view-breading-details").removeClass("hidden");
		$("#breadcrumb-subpage").html("Breeding Details");
	
	
/*		if(!!editId){			
			fetchBreedingDtls(editId);		
			$("#cms-breedingaddKidsBtn").removeClass("hidden");
			loadBreedingKidsDataTable(editId*1);
			$('#breedingKidsDataTableHolder').removeClass("hidden");
		}else{			
			loadBreedingDtlsAddUpdatePage();	
			$("#cms-breedingaddKidsBtn").addClass("hidden");			
			$('#breedingKidsDataTableHolder').addClass("hidden");
		}
		$("#breedingid").val(0);		
		resetBreedingDtlsHandler();	*/	
		fetchBreedingDtls(editId,"view");	
		loadBreedingKidsDataTable(editId*1,"#view-breedingKidsDataTable",true); // false - this is for view page
		adjustScreen.contentHeight();

	};
	var showBreedingViewPage=function(subPageTitle,breedingId){
		var loadStatus= $("#view-breading-details").data("load-status");
		if(!!loadStatus){		
			showBreedingViewPageCallBackFn(subPageTitle,breedingId);
		}else{
			$("#view-breading-details").load("partials/owner-breeding-view.html", function(response, status, xhr){
	            if(status === "success"){
	            	$("#view-breading-details").data("load-status",true);	            
	            	showBreedingViewPageCallBackFn(subPageTitle,breedingId);	            	
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}  
	};
	
	var  fetchBreedingWrtdtBuckDoe=function(breedingdate,breedingbuckid,breedingdoeid){
		var urlObj=CMSOwnerCons.urls_info["getBreedingDetails"], 
	 	reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData()),		
		req=JSON.stringify({"breedingdate":CMSCom.dateFn.cusDateServerFormat(breedingdate),"breedingbuckid":breedingbuckid*1,"breedingdoeid":breedingdoeid*1}); 
		reqData["reqData"]=req;		
		reqData=JSON.stringify(reqData);
	
		CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {
			if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
				console.log(" jsonResData ",jsonResData);
				if(jsonResData.status==="1"){	
					var doeEarTag=$("#breedingdoeid option:selected").text(),
						buckEarTag=$("#breedingbuckid option:selected").text();
					CMSMessage.showSubPageMsg({"status":1,"msg":"Breeding details already exists for selected "+doeEarTag+" and "+buckEarTag+" on "+breedingdate});
					populateBreedingDtls(jsonResData);
					$("#cmsBreedingDetailsSaveTitle").html("Edit Breeding");
					$("#cms-breedingaddKidsBtn").removeClass("hidden");
				}	
			}
		}).fail(CMSCom.exceptionHandler.ajaxFailure);	
	};
	
	var populateBreedingIfExists=function(){
		var  breedingdate=$("#breedingdate").val(),
			 breedingbuckid=$("#breedingbuckid").val(),
		     breedingdoeid=$("#breedingdoeid").val();
		if(!!breedingdate){
			if(!!breedingbuckid&&breedingbuckid){
				fetchBreedingWrtdtBuckDoe(breedingdate,breedingbuckid,breedingdoeid);
			}	
		}	
	};
	
	var breedingDoeOnChangeHandler=function(){	
		populateBreedingIfExists();
	};
	var breedingBuckOnChangeHandler=function(){	
		populateBreedingIfExists();
	};
	
	return {
		showBreedingDetlsPage:showBreedingDetlsPage,
		showBreedingAddOrUpdatePage:showBreedingAddOrUpdatePage,
		saveBreedingDtls:saveBreedingDtls,
		resetBreedingDtlsHandler:resetBreedingDtlsHandler,
		breedingaddKidsHandlder:breedingaddKidsHandlder,
		editlinkBreedingdtlsHandlder:editlinkBreedingdtlsHandlder,
		savebreedAddKidDtls:savebreedAddKidDtls,
		editlinkBreedingKidsdtlsHandlder:editlinkBreedingKidsdtlsHandlder,
		resetbreedAddKidDtls:resetbreedAddKidDtls,
		cancelbreedAddKidDtls:cancelbreedAddKidDtls,
		showBreedingViewPage:showBreedingViewPage,
		breedingDoeOnChangeHandler:breedingDoeOnChangeHandler,
		breedingBuckOnChangeHandler:breedingBuckOnChangeHandler,
	}
	
})();


