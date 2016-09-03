var OwnerCattle=(function(){
	"use strict";

	var loadCattleDataTable=function(){
		
		if(!!CMSOwnerCons.dataTable["cattle"].showFlg){
			CMSOwnerCons.dataTable["cattle"].dtFn._fnAjaxUpdate();
			CMSOwnerCons.dataTable["cattle"].dtFn.fnSetColumnVis( 0, false );	
		}else{	
			var reqData=reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData()),
			urlObj=CMSOwnerCons.urls_info["getCattleDetailsOfAssignedFarm"];
			reqData["farmid"]=-1;			
			reqData["reqData"]=JSON.stringify({});	
			
			$.fn.dataTableExt.sErrMode = 'throw';
			CMSOwnerCons.dataTable["cattle"].dtFn=$('#cattleDataTable').dataTable({
			  "ajax": {
			    "url": urlObj.url,
			    "type":urlObj.type,
			    "data":reqData,
			    "dataSrc": function ( dataJson ) {	
			     	if(CMSCom.exceptionHandler.isLoginSessionExpire(dataJson)){
			     		if(dataJson.status==="1"){			          		
					     	var jsonRes=JSON.parse(dataJson.resData),
					     	cattleDtls=jsonRes;				     	
					     	for (var key in cattleDtls){
					    		var eachObj=cattleDtls[key];				  
					    		cattleDtls[key][0] = eachObj.cattle_id;
					    		var viewLink="<a href='javascript:void(0)' class='view-cattle-details' data-cattle-id='"+eachObj.cattle_id+"'>"+eachObj.cattle_ear_tag_id;+"</a>";
					    		cattleDtls[key][1] = viewLink;
					    		cattleDtls[key][2] = CMSCom.textFn.limitTooltip(eachObj.cattle_farm_name ,15);
					    		cattleDtls[key][3] = CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(eachObj.cattle_dob));					    		cattleDtls[key][4] = eachObj.cattle_gender==="M"?"Male":"Female";				    
					    		cattleDtls[key][5] = eachObj.cattle_category==="A"?"Adult":"Kid";
					    		var statusHtml="";
					    		if(eachObj.cattle_status===true){
					    			statusHtml="<i class='fa fa-circle fa-active'></i>"+"<span style='display:none;'>"+1+"</span";
					    		}else{
					    			statusHtml="<i class='fa fa-circle fa-inactive'></i>"+"<span style='display:none;'>"+0+"</span";
					    		}				    		
					    		cattleDtls[key][6] = statusHtml;	
					    		var actionHtml="<a href='javascript:void(0);' class='edit-link-cattle-dtls' data-cattle-id='"+eachObj.cattle_id+"'><i class='fa fa-pencil-square-o'></i> Edit</a> ";
					    		cattleDtls[key][7] = actionHtml;
					    	} 							
						    return cattleDtls;
			     		}else{
			     			return []
			     		}
			     	}
			    }			    
			  },
			  destroy: true,
			  "bStateSave": true,
			  "aaSorting": [[ 0, "desc" ]],	
			  "aoColumnDefs": [{'bSortable': false, 'aTargets': [7]}],		   
			  'processing': false,
			  'serverSide': false,
			  'bLengthChange': false, 
			  'fnInitComplete' :function(){
					setTimeout(function(){
						$("#cattleDataTable" ).wrapAll( "<div class='table-responsive'></div>" );	
						 adjustScreen.contentHeight();
					},100);				 
			  },
			  'fnDrawCallback' : function() {
				  adjustScreen.contentHeight();
			  }
			});
			var oSettings = CMSOwnerCons.dataTable["cattle"].dtFn.fnSettings();
		    oSettings._iDisplayLength = 10;			     
		    CMSOwnerCons.dataTable["cattle"].dtFn.fnSetColumnVis( 0, false );
		}			
	};
	var showCattleDetlsPageCallBackFn=function(){
		$("#cattle-dtTableHolder").removeClass("hidden");					
		$("#breadcrumb-subpage").html("Goat Details");
		loadCattleDataTable();
		adjustScreen.contentHeight();
		
	};
	var showCattleDetlsPage=function(){
		var loadStatus= $("#cattle-dtTableHolder").data("load-status");
		if(!!loadStatus){
			showCattleDetlsPageCallBackFn();
		}else{
	        $("#cattle-dtTableHolder").load("partials/owner-cattle.html", function(response, status, xhr){
	            if(status === "success"){
	            	$("#cattle-dtTableHolder").data("load-status",true);
	            	showCattleDetlsPageCallBackFn();
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}  
	};

	
	var displayCattleAddUpdatePage=function(jsonResData,farmid,doefarmid,buckfarmid){
		
		var resData=JSON.parse(jsonResData.resData),
			farmDtls=resData["farm_dtls"],		
			selectOptHtml="<option value=''></option>";	
		if(CMSCom.deviceFn.isMobile.any()){
			selectOptHtml="";
		}
		
		for(var i in farmDtls){	
			var farmObj=farmDtls[i];
				selectOptHtml+="<option value='"+farmObj["farm_id"]+"'>"+farmObj["farm_name"]+"</option>";		
		}

		
		$(".addOrUpdateCattleFarm").html(selectOptHtml);
		$(".addOrUpdateCattleFarm").trigger("chosen:updated");

		$("#cattleDoefarmSelect").val(doefarmid);
		$("#cattleDoefarmSelect").trigger("chosen:updated");		

		$("#cattleBuckfarmSelect").val(buckfarmid);
		$("#cattleBuckfarmSelect").trigger("chosen:updated");
		
		if(CMSCom.deviceFn.isMobile.any()){
			$(".addOrUpdateCattleFarm").each(function(){
				var placeholder=$(this).data("placeholder"),
				placeholderOptHtml=" <option value='0' disabled selected>"+placeholder+"</option>",
				optionHtml=$(this).html();
				
				console.log(" placeholder "+placeholder);
				$(this).html(placeholderOptHtml+optionHtml);
				
			});
		}
		
		 setTimeout(function(){
			 $("#addCattleSelectFarm").val(farmid)
			 $("#addCattleSelectFarm").trigger("chosen:updated");
		 });
	};
	
	var loadCattleAddUpdatePage=function(farmid,doefarmid,buckfarmid){		
		
		var urlObj=CMSOwnerCons.urls_info["getFarmDetailsByUser"],
	 	reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData());		
		reqData["reqData"]={};
		reqData=JSON.stringify(reqData);
		
		// This is to fetch farm details for select a farm select box
		CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {
			if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
				if(jsonResData.status==="1"){
					displayCattleAddUpdatePage(jsonResData,farmid,doefarmid,buckfarmid);
				}else{
					CMSMessage.showSubPageMsg({"status":0,"msg":"Farm not assigned, so unable to add cattle "});
				}
				
			}	
		}).fail(CMSCom.exceptionHandler.ajaxFailure);		
	};
	var initCattleAddOrUpdatePage=function(){
		
		$(".addOrUpdateCattleFarm").chosen({allow_single_deselect: true});
		$("#cattleDoefarmSelect-option").chosen({allow_single_deselect: true});
		$("#cattleBuckfarmSelect-option").chosen({allow_single_deselect: true});
		
		 $('#cattledob').datepicker({ format: "dd-mm-yyyy",endDate: new Date(),forceParse: false         
	     }).on('changeDate', function(e){
	    	    $(this).datepicker('hide');
	    	    var doefarmid=$("#cattleDoefarmSelect").val(),
	    	    	buckfarmid=$("#cattleBuckfarmSelect").val(),
	    	    	cattledobVal= $(this).val(),
	    	    	cattleid=$("#cattleid").val()*1;
	    	    if(!!doefarmid&&doefarmid!==0){
	    	    	var cattleDoeId=$("#cattleDoefarmSelect-option").val()*1;
	    	    	getDoeOrBuckDetailsByFarmEdit(doefarmid,cattleDoeId,cattleid,cattledobVal,"cattleDoefarmSelect");
	    	    }
	    	    if(!!buckfarmid&&buckfarmid!==0){	    	    	
	    	    	var	cattleBuckId=$("#cattleBuckfarmSelect-option").val()*1;    	  
	    	    	getDoeOrBuckDetailsByFarmEdit(buckfarmid,cattleBuckId,cattleid,cattledobVal,"cattleBuckfarmSelect");
	    	    }	    	  
	    	    
	     }).prop('readonly','readonly');
		 $("#cattledob").off("click").on("click",function() {			 
		        $(this).datepicker().datepicker("show");
		 });
		 $("#cattledob").off("blur").on("blur",function(e) {			 
		    console.log("Blur ");
		 });
		 $("#cattledob-calen").off("click").on("click",function() {			 
			 $("#cattledob").datepicker().datepicker("show");
		 });
		 
	}
	var showCattleAddOrUpdatePageCallBackFn=function(subPageTitle,editId){
		$("#add-cattle-details").removeClass("hidden");
		$("#breadcrumb-subpage").html("Goat Details");
		$("#cmsCattleSaveTitle").html(subPageTitle);		
	
		if(!!editId){	
			$("#addCattleSelectFarm").prop('disabled', true);
			$("#cattleEarTagReadonly").removeClass("hidden");
			fetchCattleDetails(editId,"edit");				
		}else{
			$("#addCattleSelectFarm").prop('disabled', false);
			$("#cattleEarTagReadonly").addClass("hidden");
			loadCattleAddUpdatePage(0,0,0);// 0= goat farm ,  0 = buck farmid , 0 = doe farmid	
		}
		$("#cattleid").val(0);		
		OwnerCattle.resetCattleSaveFarm();		
		adjustScreen.contentHeight();

	};
	var showCattleAddOrUpdatePage=function(subPageTitle,editId){
		var loadStatus= $("#add-cattle-details").data("load-status");
		if(!!loadStatus){		
			showCattleAddOrUpdatePageCallBackFn(subPageTitle,editId);
		}else{
			$("#add-cattle-details").load("partials/owner-cattle-add-update.html", function(response, status, xhr){
	            if(status === "success"){
	            	$("#add-cattle-details").data("load-status",true);	            
	            	showCattleAddOrUpdatePageCallBackFn(subPageTitle,editId);
	            	initCattleAddOrUpdatePage();
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}  
	};
	
	
	//------------------- Cattle Image Upload fns starts ----
	var getCattleImgHtml=function(index,imgName,imageSrc,saveStatus,isprimaryImgCl){		
		var cattleImgHtml="<div class='max-width thumbnail-wrapper "+isprimaryImgCl+"' data-index='"+index+"' data-cattle-img-file='"+imgName+"' >";
		cattleImgHtml+="<div class='delete-button remove-cattle-Img' data-save-status='"+saveStatus+"'><a class='btn-delete'><i class='fa fa-times-circle'></i></a></div>"	
		cattleImgHtml+=" <img src='"+imageSrc+"' class='uploadImgs' data-img-st='true'/></div>";  
		return cattleImgHtml;
	};
	
	
	var processImage=function(data,index){			
		var reader = new FileReader(),
	 	image  = new Image(),
	 	imgType=data.type.toLowerCase(),
	 	imgName=data.name;
		
		reader.onload = function (_file) {			
			image.src    = _file.target.result;			
	        image.onload = function() {		        	

	        	var imgHeight=this.height,
        		    imgWidth=this.width,		        	
	        		imageSrc=image.src;			

		        if((CMSOwnerCons.cattleImageDim.min[0]<=imgWidth&&
		 	        	   CMSOwnerCons.cattleImageDim.max[0]>=imgWidth)&&
		 	        	   (CMSOwnerCons.cattleImageDim.min[1]<=imgHeight&&
		 	        	   CMSOwnerCons.cattleImageDim.max[1]>=imgHeight)){		        	
		        	 
					$("#uploadCattleImgHolder").append(getCattleImgHtml(index,imgName,imageSrc,false,"")); // saved status
		        	
		        	CMSOwnerCons.cattleImageData[index]=data;	
		        	$("#cattleImageFile-erMsg").hide();
		        	adjustScreen.contentHeight();
	        	}else{
	        		var errorMgs="Image : min  "+CMSOwnerCons.cattleImageDim.min[0] +" * "+CMSOwnerCons.cattleImageDim.min[1];	        						
	        		errorMgs+= " max "+CMSOwnerCons.cattleImageDim.max[0] +" * "+CMSOwnerCons.cattleImageDim.max[1];
	        		
	        		$("#cattleImageFile-erMsg small").html(errorMgs);
					$("#cattleImageFile-erMsg").show();
	        	}
	        		     		
	        };
	        image.onerror= function() { 
	        	$("#cattleImageFile-erMsg small").html("* Invalid image ");
				$("#cattleImageFile-erMsg").show();
	        	
	        };	       
		};
		reader.readAsDataURL(data);
		
	};
	
	var cattleImageFileUploadHandler=function(e){			
		var data=e.originalEvent.target.files[0];
		
		if(!!data){
			var imgName=data.name;	

			if(CMSCom.validation.isImg(imgName)){			
				var imageDataLen=!!CMSOwnerCons.cattleImageData?CMSOwnerCons.cattleImageData.length:0,
					savedImagesLen=!!CMSOwnerCons.cattleSavedImages?CMSOwnerCons.cattleSavedImages.length:0,	
					len=imageDataLen+	savedImagesLen,
						iSize = (data.size / 1024),
						iSizeMB = (Math.round((iSize / 1024) * 100) / 100);
				if(iSizeMB<=4){
					if(len<100000){						
						var index=imageDataLen;					
						processImage(data,index);			
					}else{
						$(this).val("");
						return false;
					}	
				}else{
					$("#cattleImageFile-erMsg small").html("* Image size should be less than 4 MB ");
					$("#cattleImageFile-erMsg").show();
				}
							
			}else{			
				$("#cattleImageFile-erMsg small").html("* Invalid File ");
				$("#cattleImageFile-erMsg").show();
			}
			$(this).val("");
		}		
	};
	
	
	var selectCattlePrimaryImage=function(e){
		//e.stopPropagation();
		/*var _this=$(this),
		cattleImgFileIndex=_this.data("index"),
		cattleImgFile=_this.data("cattle-img-file"),
		targetCls=$(event.target).prop('class');
		CMSOwnerCons.cattlePrimaryImg={"index":cattleImgFileIndex,"name":cattleImgFile};
		$("#uploadCattleImgHolder .thumbnail-wrapper").removeClass("img-active");
		_this.addClass("img-active");      */  	
	};
	var removeCattleImageHandler=function(e){
		e.stopPropagation(e);
		var _this=$(this),
			cattleImgFile=_this.parent().data("cattle-img-file"),
			cattleImgFileIndex=_this.parent().data("index"),
			savedStatus=_this.data("save-status");

		if(_this.parent().hasClass("img-active")){			
			if(CMSOwnerCons.cattlePrimaryImg.index===cattleImgFileIndex){
				CMSOwnerCons.cattlePrimaryImg={};
			} 
		}
		
		if(!!savedStatus){			
			if(!!CMSOwnerCons.cattleSavedImages){				
				var cattleImgFileIndex=CMSOwnerCons.cattleSavedImages.indexOf(cattleImgFile);
				CMSOwnerCons.cattleSavedImages.splice(cattleImgFileIndex,1);
				CMSOwnerCons.cattleDeletedImages.push(cattleImgFile);
			}	
		}else{
			if(!!CMSOwnerCons.cattleImageData){
				CMSOwnerCons.cattleImageData.splice(cattleImgFileIndex,1);
			}
		}		

		_this.parent().remove();
	};
	
	//------------------- Cattle Image Upload fns ends ----
	var populateCattleDetailsEdit=function(jsonResData){
		
		var cattleDtls=JSON.parse(jsonResData.resData),
		cattleDtlsObj=cattleDtls.cattleDetails,
		cattleImgsArr=cattleDtls.cattleImageDetails;		
		
		var farmid=cattleDtlsObj.farm.farmid,
			cattleid=cattleDtlsObj.cattleid,
			cattledob= CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(cattleDtlsObj["cattledob"])),
			doeid=null,buckid=null,
			doefarmid=0,
		    buckfarmid=0;
		
		if("buckcattle" in cattleDtlsObj){
			buckid=cattleDtlsObj.buckcattle.cattleid;
			buckfarmid=cattleDtlsObj.buckcattle.farm.farmid;
		}
		if("doecattle" in cattleDtlsObj){
			doeid=cattleDtlsObj.doecattle.cattleid;
			doefarmid=cattleDtlsObj.doecattle.farm.farmid;
		}
		$("#cms-saveCattle-form .form-dt").each(function(){
			$(this).val(cattleDtlsObj[$(this).prop("name")])
		});
		$("#cms-saveCattle-form .form-dt-num").each(function(){
			$(this).val(cattleDtlsObj[$(this).prop("name")])
		});
		$("#cms-saveCattle-form .form-dt-radio").each(function(){
			var selectdValue=cattleDtlsObj[$(this).prop("name")];	
			selectdValue=selectdValue.trim();
			$('#cms-saveCattle-form  input[name="' + $(this).prop("name")+ '"][value="' + selectdValue + '"]').prop('checked', true);
		});
		$("#cms-saveCattle-form .form-dt-onOff").find(".btn-on-off").removeClass("on");
		$("#cms-saveCattle-form .form-dt-onOff").each(function(){				
			$(this).find(".btn-on-off[data-val='"+cattleDtlsObj[$(this).data("input-name")]+"']").addClass("on");
		});	

		$("#cattleEarTag").val(cattleDtlsObj.cattleeartagid);	
		$("#cattledob").val(cattledob);		
	
	
		var uploadImgHtml="";
		for(var key in cattleImgsArr){
			var eachObj=cattleImgsArr[key],
			imageSrc="../../cattle/getImage?fileUrl="+eachObj.cattleimageurl,
			isprimaryImgCl="";
			CMSOwnerCons.cattleSavedImages.push(eachObj.cattleimageurl);
			if(!!eachObj.cattleimageisprimary){
				isprimaryImgCl="img-active";
			}
			
			uploadImgHtml+=getCattleImgHtml(-key,eachObj.cattleimageurl,imageSrc,true,isprimaryImgCl);
		}
		$("#uploadCattleImgHolder").html(uploadImgHtml);
		adjustScreen.contentHeight();
		
		// loading edit page cattle farm details 
		loadCattleAddUpdatePage(farmid,doefarmid,buckfarmid);
	  	getDoeOrBuckDetailsByFarmEdit(doefarmid,doeid,cattleid,cattledob,"cattleDoefarmSelect");   	  
	   	getDoeOrBuckDetailsByFarmEdit(buckfarmid,buckid,cattleid,cattledob,"cattleBuckfarmSelect");

	};
	
	var showCattleDetails=function(jsonResData){
		var cattleDtls=JSON.parse(jsonResData.resData),
		cattleDtlsObj=cattleDtls.cattleDetails,
		cattleImgsArr=cattleDtls.cattleImageDetails;			
	
		$(".cattleDtlsViewColn").each(function(){
			var val=cattleDtlsObj[$(this).data("view-key")];
			val=!!val?val:"";
			$(this).html(val)
		});
		
		
		var gender=cattleDtlsObj.cattlegender==="M"?"Male":"Female",
			category=cattleDtlsObj.cattlecategory==="K"?"Kid":"Adult",
			cattlestatus=cattleDtlsObj.cattlestatus===true?"Active":"InActive",
			cattleFarmName=cattleDtlsObj.farm.farmname;
		
		$("#view-cattlegender").html(gender);
		$("#view-cattlecategory").html(category);
		$("#view-cattlestatus").html(cattlestatus);
		$("#view-farmfarmname").html(cattleFarmName);
		$("#cattleEditLinkID").data("cattle-id",cattleDtlsObj.cattleid);
	
		$("#cattleOverViewImg").html("");
		$("#cattleViewThumbHolder").html("");
		var overViewHtml="",
			cattleViewThumbHolder="<div class='item active'>",
			thumbflag="active",
			flag="active",
			cattleImgsLen=cattleImgsArr.length;

		if(!!cattleImgsArr&&cattleImgsLen!==0){	
			for(var key in cattleImgsArr){			
				var eachObj=cattleImgsArr[key];					
				overViewHtml+="<div class='item "+flag+"'><img src='../../cattle/getImage?fileUrl="+eachObj.cattleimageurl+"'></div>";
				cattleViewThumbHolder+="<div data-target='#carousel' data-slide-to='"+key+"' class='thumb'><img src='../../cattle/getImage?fileUrl="+eachObj.cattleimageurl+"'></div>";
				if((key*1+1)%4==0){	
					if((key*1+1)===cattleImgsLen){
						cattleViewThumbHolder=cattleViewThumbHolder+"</div>";
					}else{
						cattleViewThumbHolder=cattleViewThumbHolder+"</div><div class='item'>";
					}					
				}
				flag="";
			}
			$(".imagePrevBtn").show();
			$(".imageNextBtn").show();
			
		}else{	
			$(".imagePrevBtn").hide();
			$(".imageNextBtn").hide();
			overViewHtml+="<div class='item "+flag+"'><img src='../common/images/noImage.png'></div>";
		}
		
		
		$("#cattleOverViewImg").html(overViewHtml);
		$("#cattleViewThumbHolder").html(cattleViewThumbHolder);
		adjustScreen.contentHeight();
	};
	
	var fetchCattleDetails=function(cattleId,callType){
		var urlObj=CMSOwnerCons.urls_info["getCattleDetails"],
		reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData());	
	
		reqData["reqData"]={"cattleid":cattleId};
		reqData=JSON.stringify(reqData);

		CMSCom.ajaxService.getJsonData(urlObj,reqData).done(function(jsonResData) {
			if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){	
				if(jsonResData.status*1===1){		
					if("view"===callType){
						$(".subPageContent").addClass("hidden");
						$("#view-cattle-details").removeClass("hidden");
						showCattleDetails(jsonResData);
					}else{
						populateCattleDetailsEdit(jsonResData);
					}					
				}else{
					// not avaible 
				}
				
			}	
		}).fail(CMSCom.exceptionHandler.ajaxFailure);		
	
	};
	var showCattleViewPage=function(cattleId){

		var loadStatus= $("#view-cattle-details").data("load-status");
		if(!!loadStatus){
			fetchCattleDetails(cattleId,"view");
		}else{
			$("#view-cattle-details").load("partials/owner-cattle-view.html", function(response, status, xhr){
	            if(status === "success"){
	            	$("#view-cattle-details").data("load-status",true);
	            	fetchCattleDetails(cattleId,"view");
	            }else{
	            	CMSCom.exceptionHandler.ajaxFailure(statusTxt, xhr);
	            }
	        }).error(CMSCom.exceptionHandler.ajaxFailure);
		}  
	};
	
	var viewCattleDetails=function(){
		var _this=$(this),
			cattleId=_this.data("cattle-id");
		
		window.location.hash="view-cattle="+cattleId;
	};
	
	var editLinkCattleDtlsHandler=function(){

		var _this=$(this),
			cattleId=_this.data("cattle-id");		
		window.location.hash="edit-cattle="+cattleId;

	};

	
	var displayDoeOrBuckDetailsSelectOption=function(cattleParentDtls,cattleParentId,id){		

		var parentSelectOptionEl=$("#"+id+"-option");
		if(cattleParentDtls.status==="1"){
			var	cattleid=$("#cattleid").val(),
				resData=JSON.parse(cattleParentDtls.resData),
				cattleDtls=resData["cattle_dtls"],	
				selectOptHtml="<option value=''></option>";				
				
			parentSelectOptionEl.html("");

			var genderOpt=id==="cattleBuckfarmSelect"?"M":"F";
			
			
			if(CMSCom.deviceFn.isMobile.any()){				
				var placeholder=parentSelectOptionEl.data("placeholder");
				selectOptHtml=" <option value='0' disabled selected>"+placeholder+"</option>";
				
			}
			
			for(var i in cattleDtls){	
				var cattleObj=cattleDtls[i];
				if(cattleid*1!==cattleObj["cattleid"]*1){						
					if(!!cattleObj.cattle_status&&cattleObj.cattle_gender ===genderOpt){
						selectOptHtml+="<option value='"+cattleObj["cattle_id"]+"'>"+cattleObj["cattle_ear_tag_id"]+"</option>";					
					}
				}
			}

			

			parentSelectOptionEl.html(selectOptHtml);
			parentSelectOptionEl.trigger("chosen:updated");
			
			setTimeout(function(){	
				if(!!cattleParentId){
					parentSelectOptionEl.val(cattleParentId);
				}
				parentSelectOptionEl.trigger("chosen:updated");
			});			 
		}else{		
			parentSelectOptionEl.html("<option value=''></option>");		
			parentSelectOptionEl.val(0);	
			parentSelectOptionEl.trigger("chosen:updated");
		}		
	};

	var getDoeOrBuckDetailsByFarmEdit=function(farmid,cattleParentId,cattleid,cattledob,id){
		var urlObj=CMSOwnerCons.urls_info["getParentByFarmBirthdt"],
		 	reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData()),
		 	queryDtls={};
		
		if(!!cattledob&&cattledob.trim().length!==0){
			
			queryDtls["farm"]={"farmid":farmid};	
			queryDtls["cattledob"]=CMSCom.dateFn.cusDateServerFormat(cattledob);	
			queryDtls["cattleid"]=cattleid;				
			reqData["reqData"]=queryDtls;	
			
			
			if(!!farmid&&farmid!==0){
				reqData=JSON.stringify(reqData);	
				CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {
					if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){											
						displayDoeOrBuckDetailsSelectOption(jsonResData,cattleParentId,id);																
					}	
				}).fail(CMSCom.exceptionHandler.ajaxFailure);	
			}else{		
				var parentSelectOptionEl=$("#"+id+"-option");
				parentSelectOptionEl.html("<option value=''></option>");
				parentSelectOptionEl.trigger("chosen:updated");
			}			
		}				
	};
	
	
	var addOrUpdateCattleFarmHandler=function(e){

		var _this=$(this),	
			farmid=_this.val()*1,
			id=_this.prop("id"),
		 	cattledobEl=$("#cattledob"),
			cattledob=cattledobEl.val();
		
		if(id==="cattleBuckfarmSelect"||id==="cattleDoefarmSelect"){
			if(!!cattledob&&cattledob.trim().length!==0){
				getDoeOrBuckDetailsByFarmEdit(farmid,null,0,cattledob,id);
			}else{
				e.stopPropagation();
				_this.val(0);
				_this.trigger("chosen:updated");				
				CMSMessage.showSubPageMsg({"status":0,"msg":"Please select Date of Birth first"});
				$(".mCSB_container").css({"top":0});
			}
		}
		$("#ipt-er-cattlefarmid").hide();
	};
	
	var validateCattle=function(){
		
		var result=true,
			cattlelatt=$("#cms-saveCattle-form #cattlelatt").val(),
			cattlelong=$("#cms-saveCattle-form #cattlelong").val();
		
		$(".cms-ipt-error").hide();
		
		if(!!cattlelatt&&!CMSCom.validation.isDecimal(cattlelatt)){
			$("#ipt-er-cattlelatt small").html("* Only Decimal values ");
			$("#ipt-er-cattlelatt").show();
			$("#cms-saveCattle-form #cattlelatt").focus();
			result=false;
		}else if(!!cattlelong&&!CMSCom.validation.isDecimal(cattlelong)){
			$("#ipt-er-cattlelong small").html("* Only Decimal values ");
			$("#ipt-er-cattlelong").show();
			$("#cms-saveCattle-form #cattlelong").focus();
			result=false;
		}
		
		return result;			
	};
	
	
	var saveCattleBtnHandler=function(e){		
			
		var cattledobEl=$("#cattledob"),
			cattledob=cattledobEl.val(),
			cattlefarmid=$("#addCattleSelectFarm").val()*1;

		
		
		if(!cattledob||cattledob===""){
			$(".mCSB_container").css({"top":0});	
			e.stopPropagation();			
			$.webshims.validityAlert.showFor( cattledobEl,"Please fill out this field." );
		}else if(!cattlefarmid||cattlefarmid*1===0){
			$("#ipt-er-cattlefarmid").show();
			$(".mCSB_container").css({"top":0});
			$.webshims.validityAlert.hide();
			e.stopPropagation();
		}else{
			$(".mCSB_container").css({"top":0});	
		}
		
	};
	var saveCattle=function(e){
		try{
			if($("#cms-saveCattle-form")[0].checkValidity()&&validateCattle()){		
				var formData= new FormData(), 	
					saveFarmDts={},
					reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData()),
					cattlefarmid=$("#addCattleSelectFarm").val(),
					cattledob=$("#cattledob").val(),
					cattleid=$("#cattleid").val();
			
				$("#cms-saveCattle-form .form-dt").each(function(){
					reqData[$(this).prop("name")]=$(this).val();
				});
				$("#cms-saveCattle-form .form-dt-num").each(function(){
					reqData[$(this).prop("name")]=$(this).val()*1;
				});
				
				$("#cms-saveCattle-form .form-dt-refer").each(function(){					
					var _this=$(this),
						refer=_this.data("refer"),field=_this.data("field"),				
						val =_this.val(),farm={};
						
					farm[field]=val;
					
					if(!!val){
						reqData[refer]=farm;
					}
				});
				
				$("#cms-saveCattle-form .form-dt-radio:checked").each(function(){
					reqData[$(this).prop("name")]=$(this).val();
				});
				$("#cms-saveCattle-form .form-dt-onOff").each(function(){
					reqData[$(this).data("input-name")]=$(this).find(".btn-on-off.on").data("val");
				});
				
				reqData["cattledob"]=CMSCom.dateFn.cusDateServerFormat(reqData["cattledob"]);
				reqData["cattletype"]=1;
				reqData=JSON.stringify(reqData);		
				
				formData.append("cattlePrimaryImg",CMSOwnerCons.cattlePrimaryImg.name);
				formData.append("reqData",reqData);				
				formData.append("u_id",CMSOwnerCom.getUserId());
				formData.append("signature",CMSOwnerCom.getCMSSignature());
				
				var len=CMSOwnerCons.cattleImageData.length,
				cattleSavedImageslen=CMSOwnerCons.cattleSavedImages.length,
				cattleDeletedImageslen=CMSOwnerCons.cattleDeletedImages.length,
			 	urlObj=urlObj=CMSOwnerCons.urls_info["saveCattle"];
		
			
				if(!!CMSOwnerCons.cattleImageData&&len!==0){
					for(var key in CMSOwnerCons.cattleImageData){						
						formData.append("fileData",CMSOwnerCons.cattleImageData[key]);
					}					
				}
				
				if(!!CMSOwnerCons.cattleSavedImages&&cattleSavedImageslen!==0){
					for(var key in CMSOwnerCons.cattleSavedImages){						
						formData.append("updateImages",CMSOwnerCons.cattleSavedImages[key]);
					}	
				}	
				console.log(" CMSOwnerCons.cattleDeletedImages ",CMSOwnerCons.cattleDeletedImages);
				if(!!CMSOwnerCons.cattleDeletedImages&&cattleDeletedImageslen!==0){
					for(var key in CMSOwnerCons.cattleDeletedImages){						
						formData.append("deletedImages",CMSOwnerCons.cattleDeletedImages[key]);
					}	
				}

			
				if(!cattlefarmid||cattlefarmid*1===0){
					//$.webshims.validityAlert.showFor( $("#addCattleSelectFarm_chosen"),"Select Farm" ); 
					$("#ipt-er-cattlefarmid").show();
				}else if(!cattledob||cattledob===""){	
					// cattle dob not selected 
				}else{

					$("#ipt-er-cattlefarmid").hide();				
					CMSMessage.showCommonLoader();
					CMSCom.ajaxService.sentMultipartFormData(urlObj,formData).done(function(jsonResData) {	
						if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
							var resData=JSON.parse(jsonResData),
								saveMsg;
							if(resData.status*1===1){	
								saveMsg=!!cattleid&&cattleid*1!==0?"Goat successfully updated " :"Goat successfully saved "; 										
								$("#saveMsg").html(saveMsg);	
								OwnerCattle.resetCattleSaveFarm();
								window.location.hash="cattle-details";
								CMSOwnerCons.cattleImageData=[];
								CMSOwnerCons.cattleSavedImages=[];
								CMSOwnerCons.cattleDeletedImages=[];
							}else{
								saveMsg=resData.msg;
							}
							CMSMessage.showSubPageMsg({"status":1,"msg":saveMsg});	
							CMSMessage.hideCommonLoader();
						}		
						
					}).fail(CMSCom.exceptionHandler.ajaxFailure);
				}
			}else{
				//alert()
			}
		}catch(e){
			//console.log(" e ",e);
		}
		e.preventDefault();			
	};
	
	var resetCattleSaveFarm=function(){
		var cattleId=$("#cattleid").val();
		CMSOwnerCons.cattleImageData=[];
		CMSOwnerCons.cattlePrimaryImg={};
		CMSOwnerCons.cattleSavedImages=[];
		CMSOwnerCons.cattleDeletedImages=[];
		$("#cms-saveCattle-form .form-dt").each(function(){
			$(this).val("");
		});
		$("#cms-saveCattle-form .form-dt-num").each(function(){
			$(this).val("");
		});
		$("#cms-saveCattle-form .form-dt-onOff").each(function(){
			$(this).find(".btn-on-off").removeClass("on");
			$(this).find(".btn-on-off[data-val='true']").addClass("on");

		});
		
		// clear radio values and checking first radio button
		$("#cms-saveCattle-form .form-dt-radio:checked").prop('checked', false);
		$("#cms-saveCattle-form .form-dt-radio-holder").each(function(){
			$(this).find(".form-dt-radio:eq(0)").prop('checked', true);
		});
		$("#ipt-er-cattlefarmid").hide();			
		$("#addCattleSelectFarm").val(0);
		$("#addCattleSelectFarm").trigger("chosen:updated");
		$(".addOrUpdateCattleFarm").val(0);
		$(".addOrUpdateCattleFarm").trigger("chosen:updated");
		$("#cattleDoefarmSelect-option").html("<option value=''></option>");
		$("#cattleBuckfarmSelect-option").html("<option value=''></option>");
		$("#cattleDoefarmSelect-option").val(0);	
		$("#cattleDoefarmSelect-option").trigger("chosen:updated");
		$("#cattleBuckfarmSelect-option").val(0);
		$("#cattleBuckfarmSelect-option").trigger("chosen:updated");
		
		$("#uploadCattleImgHolder").html("");
		$("#cattleImageFile-erMsg").hide();
	
		if(!!cattleId&&cattleId*1!==0){
			showCattleAddOrUpdatePage("Edit Goat",cattleId);
		}
	};

	
	return {
		showCattleDetlsPage:showCattleDetlsPage,
		showCattleViewPage:showCattleViewPage,
		showCattleAddOrUpdatePage:showCattleAddOrUpdatePage,
		loadCattleDataTable:loadCattleDataTable,		
		editLinkCattleDtlsHandler:editLinkCattleDtlsHandler,
		cattleImageFileUploadHandler:cattleImageFileUploadHandler,
		removeCattleImageHandler:removeCattleImageHandler,
		viewCattleDetails:viewCattleDetails,
		resetCattleSaveFarm:resetCattleSaveFarm,
		addOrUpdateCattleFarmHandler:addOrUpdateCattleFarmHandler,
		saveCattle:saveCattle,
		saveCattleBtnHandler:saveCattleBtnHandler,
		selectCattlePrimaryImage:selectCattlePrimaryImage
	};	
	
})();