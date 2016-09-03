var MilkProduction = (function(){

   var addDetails=function(milkprodid){
		var loadStatus= $("#add-milk-data").data("load-status");
		if(!!loadStatus){
			milkProductionCallBackFn(milkprodid);
		}else{
			$("#add-milk-data").load("partials/owner-milk-production.html", function(response, status, xhr){
	            if(status === "success"){
	            	$("#add-milk-data").data("load-status",true);
	            	  milkProductionCallBackFn(milkprodid);
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}  
	};
	
	var milkProductionCallBackFn=function(milkprodid){
		
		$("#milkprodId").val("");
		if(!!milkprodid){
			$("#add-milk-data").removeClass("hidden");	
			$("#breadcrumb-subpage").html("Edit Milk Production");
			$("#cmsMilkProduction").html("Edit Milk Production");
			
			fetchMilkProductionDetails(milkprodid,"edit");	
			$("#mp-addFarm").chosen({allow_single_deselect: true});
			$("#mkDoeId").chosen({allow_single_deselect: true});
			
			adjustScreen.contentHeight();		
			
		}else{		
			$("#add-milk-data").removeClass("hidden");	
			$("#breadcrumb-subpage").html("Milk Production");
			$("#cmsMilkProduction").html("Add Milk Production");
			mpResetfield();
			fetch_mp_Details();
			$("#mp-addFarm").chosen({allow_single_deselect: true});
			$("#mkDoeId").chosen({allow_single_deselect: true});
			adjustScreen.contentHeight();
		}		
	};
	
	var fetchMilkProductionDetails = function(milkprodid,callType){
		
		var urlObj=CMSOwnerCons.urls_info["getmilkProductionDetialsbyUserId"],
		reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData());	
		
		reqData["reqData"]={"milkprodid":milkprodid*1};
		reqData=JSON.stringify(reqData);
		
		CMSCom.ajaxService.getJsonData(urlObj,reqData).done(function(jsonResData) {
			if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){	
				if(jsonResData.status*1===1){	
					var resData=JSON.parse(jsonResData.resData),
					milkPrdltsObj=resData[0];
					
					// Used to load the table in read only view of milk production list - owner//AB
					if("view"===callType){
						
						$("#view-mp-milkprodid").html(milkPrdltsObj.milkprodid);
						$("#view-mp-farmname").html(milkPrdltsObj.objdoe.farm.farmname);
						$("#view-mp-doeId").html(milkPrdltsObj.objdoe.cattleeartagid);
						$("#view-mp-milkproddt").html(CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(milkPrdltsObj.milkproddt)));
						$("#view-mp-milkprodqty").html(milkPrdltsObj.milkprodqty);
						$("#view-mp-milkprodcomments").html(milkPrdltsObj.milkprodcomments);
						$("#view-mp-edit-link").prop("href","#edit-milk-production="+milkPrdltsObj.milkprodid);
						
					}else if("edit"===callType){
					
					$("#milkproddt").val(CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(milkPrdltsObj.milkproddt)));
			     	$("#milkproddt").datepicker({ format: "dd-mm-yyyy",endDate: new Date()}); 
			     	$("#milkproddt").datepicker('update');
			     	
					$("#mp-createddt").val(CMSCom.dateFn.cusDateAsDD_MM_YYYY_TS(new Date(milkPrdltsObj.createddt)));
					$("#milkprodId").val(milkPrdltsObj.milkprodid);
					$("#milkprodqty").val(milkPrdltsObj.milkprodqty);
					$("#milkprodcomments").val(milkPrdltsObj.milkprodcomments);	
					
					fetch_mp_Details(milkPrdltsObj.objdoe.farm.farmid);
					
					getDoeDetailsByFarm(milkPrdltsObj.objdoe.farm.farmid,milkPrdltsObj.objdoe.cattleid);
				}
										
			}else{
					
				}
				
			}	
		}).fail(CMSCom.exceptionHandler.ajaxFailure);
	};
	
	var fetch_mp_Details=function(farmid){
		
		var urlObj=CMSOwnerCons.urls_info["getFarmDetailsByUser"],
	 	reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData());		
		reqData["reqData"]={};
		reqData=JSON.stringify(reqData);
		
		// This is to fetch farm details for select a farm select box
		CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {
			if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
				if(jsonResData.status==="1"){
					//displayCattleAddUpdatePage(jsonResData,farmid);
					var resData=JSON.parse(jsonResData.resData),
					farmDtls=resData["farm_dtls"],	
					selectOptHtml="<option value=''></option>";	
					for(var i in farmDtls){	
						var farmObj=farmDtls[i];
							selectOptHtml+="<option value='"+farmObj["farm_id"]+"'>"+farmObj["farm_name"]+"</option>";		
					}
					$("#mp-addFarm").html(selectOptHtml);
					$("#mp-addFarm").trigger("chosen:updated");
					$("#milkproddt").datepicker({ format: "dd-mm-yyyy",endDate: new Date()	         
				     }).on('changeDate', function(e){
				    	    $(this).datepicker('hide');
				    	    $("#mp-addFarm").val("");
				    	    $("#mkDoeId").val("");
				    	    $("#doeParent").addClass("hidden");
				    	    $("#milkprodqty").val("");
				    	    $("#milkprodcomments").val("");
				    	    $("#mp-addFarm").trigger("chosen:updated");
				     }).prop('readonly','readonly');
					 $("#milkproddt").off("click").on("click",function() {			 
					        $(this).datepicker().datepicker("show");
					 });
					
					if(!!farmid){
						
						$("#mp-addFarm").val(farmid);
						$("#mp-addFarm").trigger("chosen:updated");
					}					
				}else{
					CMSMessage.showSubPageMsg({"status":0,"msg":"Farm not assigned, so unable to add cattle "});
				}				
			}	
		}).fail(CMSCom.exceptionHandler.ajaxFailure);		
	};
	
	var mpshowDoedetails = function(e){
		var milkproddtEl=$("#milkproddt"),
		milkproddt=milkproddtEl.val();
		if(!milkproddt||milkproddt===""){
			e.stopPropagation();
			$.webshims.validityAlert.showFor( milkproddtEl,"Please fill out this field." );
			CMSMessage.showSubPageMsg({"status":0,"msg":" Please select date first "});
			$("#mp-addFarm").val("");
			$("#mp-addFarm").trigger("chosen:updated");
		}else{
			var farmid=$(this).val()*1;
			getDoeDetailsByFarm(farmid,null);// farm id , doeid=null
		}
	};
	
	var getDoeDetailsByFarm=function(farmid,doeId){
		var urlObj=CMSOwnerCons.urls_info["getCattleDetailsByFarm"],
	 	reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData());		
		reqData["reqData"]=JSON.stringify({});
		reqData["farmid"]=farmid;		
		$("#doeParent").removeClass("hidden");
		$("#mkDoeId").html("");
		if(!!farmid&&farmid!==0){
			CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {
				if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
					//This functions load the drop down with response data
					displayDoeDetails(jsonResData,doeId);				
				}	
			}).fail(CMSCom.exceptionHandler.ajaxFailure);	
		}else{		
			$("#doeParent").addClass("hidden");
		}	
	};
	
	var displayDoeDetails = function(response,doeId){
		$("#mp-saveBtn").prop("disabled",true);
		
		var details = JSON.parse(response),
			milkproddt	=$("#milkproddt").val(),
			milkproddtObj = $("#milkproddt").data("datepicker").getDate();
		if(details.status==="1"){
			
			resData=JSON.parse(details.resData),
			cattleDtls=resData["cattle_dtls"],	
			selectDoeOptHtml="<option value=''></option>";
			var noFemaleCattle=true;
			for(var i in cattleDtls){	
				var cattleObj=cattleDtls[i];
			
					if((cattleObj.cattlegender==="F"&&cattleObj.cattlecategory==="A")&&!!cattleObj.cattlestatus){
						var cattledobObj=new Date(cattleObj.cattledob),
							prodDtDiff=CMSCom.dateFn.daysBetween(milkproddtObj,cattledobObj);
				
						if(prodDtDiff<0){
							selectDoeOptHtml+="<option value='"+cattleObj["cattleid"]+"'>"+cattleObj["cattleeartagid"]+"</option>";	
							$("#mp-saveBtn").prop("disabled",false);
							noFemaleCattle=false;
						}					
					}			
			}
			
			if(noFemaleCattle){
				$("#doeParent").addClass("hidden");
				var saveMsg="In the selected farm, no active adult Doe with Date of Birth less than \""+milkproddt+"\""; 										
				CMSMessage.showSubPageMsg({"status":0,"msg":saveMsg});	
			}
			$("#mkDoeId").html(selectDoeOptHtml);
			$("#mkDoeId").trigger("chosen:updated");
		
			if(!!doeId){
				$("#mkDoeId").val(doeId);
				$("#mkDoeId").trigger("chosen:updated");
			}
		}else{
			$("#doeParent").addClass("hidden");
			var saveMsg="There is no Doe in the farm selected "; 
			CMSMessage.showSubPageMsg({"status":0,"msg":saveMsg});
		}
		
	};
	
	var mpSavedetails = function(e){
		try{
			
			if($("#milk-production-form")[0].checkValidity()){	
				
				var milkproddt = $("#milkproddt").val(),
					mpaddFarm=$("#mp-addFarm").val(),
					mkDoeId=$("#mkDoeId").val(),
					milkprodqty=$("#milkprodqty").val(),
					milkprodcomments=$("#milkprodcomments").val();
				
				if(!milkproddt || milkproddt===""){
					$.webshims.validityAlert.showFor($("#milkproddt") ,"Please fill out this field." );
				}else if(!mpaddFarm || mpaddFarm===""){
					CMSMessage.showSubPageMsg({"status":0,"msg":"Select a Farm"});
				}else if(!mkDoeId || mkDoeId===""){
					CMSMessage.showSubPageMsg({"status":0,"msg":"Select a Doe"});
				}else if(!milkprodqty || milkprodqty===""){
					CMSMessage.showSubPageMsg({"status":0,"msg":"Enter Milk Quantity"});
					$("#milkprodqty").focus();
				}
				else{
				var reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData()),
					mpdetails={};
				
				$("#milk-production-form .form-dt").each(function(){
					mpdetails[$(this).prop("name")]=$(this).val();
				});
				$("#milk-production-form .form-dt-refer").each(function(){					
					var _this=$(this),
						refer=_this.data("refer"),field=_this.data("field"),				
						val =_this.val(),referObj={};
						
					referObj[field]=val;
					
					if(!!val){
						mpdetails[refer]=referObj;
					}
				});
				mpdetails["milkproddt"]=CMSCom.dateFn.cusDateServerFormat(mpdetails["milkproddt"]);
	
				urlObj=CMSOwnerCons.urls_info["saveMilk"];
				
				reqData["reqData"]=mpdetails;
				reqData=JSON.stringify(reqData);
				CMSCom.ajaxService.sentMultipartFormData(urlObj,reqData).done(function(jsonResData) {	
					if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
						var resData=JSON.parse(jsonResData);
					
						if(resData.status*1===1){	
							var saveMsg="Details successfully saved "; 										
							CMSMessage.showSubPageMsg({"status":1,"msg":saveMsg});
							mpResetfield();
							window.location.hash="milk-production-list";
						}else{
							CMSMessage.showSubPageMsg(jsonResData);
						}
					}		
					
				}).fail(CMSCom.exceptionHandler.ajaxFailure);
			}
			}
			
		}catch (e) {
			console.log("e ",e);
		}
		e.preventDefault();			
	};
	
	var mpEditedetails = function(e){
		
		try{
		var reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData()),
		mpEditdetails={};
		
		mpDate = $("#milkproddt").val();
		mpDoeId=$("#mkDoeId").val();
		
		mpEditdetails["milkproddt"] =  CMSCom.dateFn.cusDateServerFormat(mpDate);
		mpEditdetails["objdoe"] ={"cattleid":mpDoeId};
		
		reqData["reqData"]=mpEditdetails;
		urlObj=CMSOwnerCons.urls_info["editMilk"];
		reqData=JSON.stringify(reqData);
		
		CMSCom.ajaxService.sentMultipartFormData(urlObj,reqData).done(function(jsonResData) {	
			if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
		
				jsonResData=JSON.parse(jsonResData);	
				var resData=JSON.parse(jsonResData.resData);
				if(jsonResData.status*1===1){	
					
					$("#mp-createddt").val(resData[0].createddt);
					$("#milkprodqty").val(resData[0].milkprodqty);
					$("#milkprodcomments").val(resData[0].milkprodcomments);
					
				}else{
					CMSMessage.showSubPageMsg(jsonResData);
				}
			}	
			
			}).fail(CMSCom.exceptionHandler.ajaxFailure);
		}catch (e) {
		// TODO: handle exception
		console.log("e",e)
	}
	e.preventDefault();			
			
	};
	
	var mpResetfield = function(e){
		var milkprodId=$("#milkprodId").val()*1;
		
		$("#milk-production-form .form-dt").each(function(){
			$(this).val("");
		});
		$("#milk-production-form .form-dt-refer").each(function(){
			$(this).val("");
		});
		$("#mp-addFarm").val("");
		$("#mp-addFarm").trigger("chosen:updated");
		$("#doeParent").addClass("hidden");
		if(!!milkprodId){
			fetchMilkProductionDetails(milkprodId);	
		}
		
	};
	
	 var mpSaveBtnHandler=function(e){
			var milkproddtEl=$("#milkproddt"),
			milkproddt=milkproddtEl.val();
		if(!milkproddt||milkproddt===""){
			e.stopPropagation();
			CMSMessage.showSubPageMsg({"status":0,"msg":"Select Date Of Lactation"});
			$.webshims.validityAlert.showFor( milkproddtEl,"Please fill out this field." );
		}
	 };
	 
	 //To load Read only page of milk production list - owner
	 var showReadOnlyPage = function(milkprodid){
			
			var loadStatus= $("#view-milkList-holder").data("load-status");
			if(!!loadStatus){
				showReadOnlyPageCallBackFn(milkprodid);
			}else{
				$("#view-milkList-holder").load("partials/owner-milk-production-view.html", function(response, status, xhr){
		            if(status === "success"){
		            	$("#view-milkList-holder").data("load-status",true);
		            	showReadOnlyPageCallBackFn(milkprodid);
		            }else{
		            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
		            }
		        }).error(CMSCom.exceptionHandler.ajaxFailure);
			}  
		};
		
		var showReadOnlyPageCallBackFn = function(milkprodid){
			
			$("#view-milkList-holder").removeClass("hidden");	
			$("#breadcrumb-subpage").html("Milk Production");
			adjustScreen.contentHeight();
			fetchMilkProductionDetails(milkprodid,"view");	
			
			
		};
	
	return{
		addDetails:addDetails,
		milkProductionCallBackFn:milkProductionCallBackFn,
		mpshowDoedetails:mpshowDoedetails,
		mpSavedetails:mpSavedetails,
		mpEditedetails:mpEditedetails,
		mpResetfield:mpResetfield,
		mpSaveBtnHandler:mpSaveBtnHandler,
		showReadOnlyPage:showReadOnlyPage
	};

})();

var MilkProductionList = (function(){
	
	var loadPage = function(){
			
			var loadStatus= $("#owner-milkList-holder").data("load-status");
			if(!!loadStatus){
				showMilkProductionListPageCallBackFn();
			}else{
				$("#owner-milkList-holder").load("partials/owner-milk-production-list.html", function(response, status, xhr){
		            if(status === "success"){
		            	$("#owner-milkList-holder").data("load-status",true);
		            	showMilkProductionListPageCallBackFn();
		            }else{
		            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
		            }
		        }).error(CMSCom.exceptionHandler.ajaxFailure);
			}  
			
		};
		
		var showMilkProductionListPageCallBackFn = function(){
			$("#owner-milkList-holder").removeClass("hidden");					
			$("#breadcrumb-subpage").html("View Milk Production Details");
			loadMilkProductionDataTable();
			adjustScreen.contentHeight();
			
		};
		
		
		
	var loadMilkProductionDataTable=function(){
				
				if(!!CMSOwnerCons.dataTable["milkproduction"].showFlg){
					CMSOwnerCons.dataTable["milkproduction"].dtFn._fnAjaxUpdate();
					CMSOwnerCons.dataTable["milkproduction"].dtFn.fnSetColumnVis( 0, false );	
				}else{				
					var	reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData()),
						urlObj=CMSOwnerCons.urls_info["milkProductionDetails"],
					req="";   	
					reqData["reqData"]=req;	
					$.fn.dataTableExt.sErrMode = 'throw';			
					CMSOwnerCons.dataTable["milkproduction"].dtFn=$('#milkproductionDataTable').dataTable({
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
							     		dataArr=[],
							     		incr=0;
	
							     	for (var key in jsonRes){
							    		var eachObj=jsonRes[key],
							    		dataObj={};
							         	
							    		if(eachObj.objdoe.cattlegender==="F"){
							    			
							    		var viewLink="<a href='#view-milk-production="+eachObj.milkprodid+"' >"+eachObj.objdoe.cattleeartagid+"</a>",
								        actionHtml="<a href='#edit-milk-production="+eachObj.milkprodid+"' ><i class='fa fa-pencil-square-o'></i> Edit</a> ";
							    			
							         	dataObj["0"] = eachObj.milkprodid; 
							    		dataObj["1"] = CMSCom.textFn.limitTooltip(eachObj.objdoe.farm.farmname,10);
							         	dataObj["2"] = viewLink;						  
							    		dataObj["3"] = !!eachObj.milkproddt? CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(eachObj.milkproddt)):"";
							    		dataObj["4"] = eachObj.milkprodqty;								    		
							    		dataObj["5"] =actionHtml;
							    		dataArr[incr]=dataObj;
							    		incr++;
							    	
							    		}
							    	} 
								
								    return dataArr;
					     		}else{
					     			return [];
					     		}
					     	}
					    }			    
					  },
					  destroy: true,
					  "aaSorting": [[ 0, "desc" ]],	
					  "aoColumnDefs": [{'bSortable': false, 'aTargets': [5]}],		   
					  'processing': false,
					  'serverSide': false,
					  'bLengthChange': false, 
					  'fnInitComplete' :function(){
							setTimeout(function(){
								$("#milkproductionDataTable" ).wrapAll( "<div class='table-responsive'></div>" );	
								  adjustScreen.contentHeight();
							},100);
					  },
					  'fnDrawCallback' : function() {
						  adjustScreen.contentHeight();
					  }
					});
					var oSettings = CMSOwnerCons.dataTable["milkproduction"].dtFn.fnSettings();
				    oSettings._iDisplayLength = 10;			     
				    CMSOwnerCons.dataTable["milkproduction"].dtFn.fnSetColumnVis( 0, false );
				//    CMSOwnerCons.dataTable["milkproduction"].showFlg=true;
				}			
			};
			
			
				
			
			
		return {
			
			loadPage:loadPage
			
		};
})();