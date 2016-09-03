


(function(window) {
	"use strict";
	var CMSOwnerService=(function(){
		
		var btnOnOffHandler=function(){
			 $(".btn-on-off").removeClass("on");
		    $(this).addClass("on");
		};
		
		var adminLogout=function(){
			
			var urlObj=CMSOwnerCons.urls_info["logout"],
        	reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData());
			reqData["reqData"]={};		
			reqData=JSON.stringify(reqData);
			CMSCom.ajaxService.getJsonData(urlObj,reqData).done(function(jsonResData) {	
				if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
					if(jsonResData.status==="1"){
						CMSStorage.removeStorage("owner-signature");
						CMSStorage.removeStorage("owner_u_id");
						CMSStorage.removeStorage("owner-username");
						CMSStorage.removeStorage("user_role_id");
						window.location.href="../login.html";							
					}		
				}
			}).fail(CMSCom.exceptionHandler.ajaxFailure);	
		};

		var showPage=function(pageUrl,id){			
			if(CMSCom.isUserSessionExists("owner")){
				if(pageUrl===""){
					$("#owner-dashboard").removeClass("hidden");
					$("#owner-inner-page").addClass("hidden");
				}else{
					$("#owner-dashboard").addClass("hidden");
					$("#owner-inner-page").removeClass("hidden");
					$(".subPageContent").addClass("hidden");
					
					if(pageUrl==="farm-details"){ // Farm Details  ********** 
						
						OwnerFarm.showFarmDetlsPage();	
						
					}else if(pageUrl==="cattle-details"){ // Cattle Details  ********** 						
	
						OwnerCattle.showCattleDetlsPage();
						
					}else if(pageUrl==="add-cattle"){ // Add Cattle Details  ********** 
						
						OwnerCattle.showCattleAddOrUpdatePage("Add Goat",null);		
						
					}else if(pageUrl.indexOf("edit-cattle")!==-1){			// Edit Cattle Details  ********** 
						var cattleid=pageUrl.split("=")[1];
						OwnerCattle.showCattleAddOrUpdatePage("Edit Goat",cattleid);			
						pageUrl="add-cattle";	
						
					}else if(pageUrl.indexOf("view-cattle")!==-1){			// Edit Cattle Details  ********** 
						var cattleid=pageUrl.split("=")[1];
						OwnerCattle.showCattleViewPage(cattleid);			
						pageUrl="cattle-details";	
						
					}else if(pageUrl==="health-care"){
						OwnerHealthcare.showHealthcareDetlsPage();	
						
					}else if(pageUrl.indexOf("view-health-care")!==-1){	
						var cattleId=CMSCom.URL.queryParam('cattleId'),
							hcDt=CMSCom.URL.queryParam('hc-date');			
						OwnerHealthcare.showViewHCDetlsPage(cattleId,hcDt);	
						pageUrl="health-care";	
					}else if(pageUrl==="breeding-details"){				// Edit Breeding Details  ********** 
						OwnerBreading.showBreedingDetlsPage();
						$("#breadcrumb-subpage").html("Breeding details");
						
					}else if(pageUrl==="add-breeding"){
						OwnerBreading.showBreedingAddOrUpdatePage("Add Breeding",null);
						$("#breadcrumb-subpage").html("Breeding details");
						
					}else if(pageUrl.indexOf("edit-breeding")!==-1){			
						var breedingid=pageUrl.split("=")[1];
						OwnerBreading.showBreedingAddOrUpdatePage("Edit Breeding",breedingid);
						$("#breadcrumb-subpage").html("Breeding details");			
						pageUrl="add-breeding";		
						
					}else if(pageUrl.indexOf("view-breeding")!==-1){			
						var breedingid=pageUrl.split("=")[1];
						OwnerBreading.showBreedingViewPage("View Breeding Details",breedingid);
						$("#breadcrumb-subpage").html("Breeding details");			
						pageUrl="breeding-details";			
						
					}else if(pageUrl==="profile"){ // View and Edit User Profile
						OwnerProfile.loadPage();
						$("#breadcrumb-subpage").html("Profile");
						
					}else if(pageUrl==="change-password"){
						OwnerChangepassword.loadPage();
						$("#breadcrumb-subpage").html("Change Password");
						
					}else if(pageUrl==="milk-production"){
						MilkProduction.addDetails(id);
						$("#breadcrumb-subpage").html("Milk Production");
						
					}else if(pageUrl==="milk-production-list"){
						MilkProductionList.loadPage();
						$("#breadcrumb-subpage").html("Milk Production");
						
					}else if(pageUrl.indexOf("edit-milk-production")!==-1){			
						var milkprodid=pageUrl.split("=")[1];
						MilkProduction.addDetails(milkprodid);
						pageUrl="milk-production";	
						
					}else if(pageUrl.indexOf("view-milk-production")!==-1){
						var milkprodid=pageUrl.split("=")[1];
						$("#breadcrumb-subpage").html("View Milk Production ");
						MilkProduction.showReadOnlyPage(milkprodid);
						pageUrl="milk-production-list";	
						
					}
					else if(pageUrl==="goat-reports"){
						GoatReport.loadPage();
						$("#breadcrumb-subpage").html("Reports");
					}else if(pageUrl==="milk-reports"){
						MilkReport.loadPage();
						$("#breadcrumb-subpage").html("Reports");
					}else if(pageUrl==="heath-reports"){
						$("#cms-coming-soon").removeClass("hidden");
						$("#breadcrumb-subpage").html("Reports");
					}else if("surgery-list"===pageUrl){	
						$("#breadcrumb-subpage").html("Surgery/Illness Details");
						OwnerSurgery.showSurgeryDetlsPage();
					}
					
	
					// For highlighting left menu
					var subMenuEl=$(".owner-sub-nav").find("li[data-sub-page='"+pageUrl+"']");
	
					
					if(!subMenuEl.parent().hasClass("in")){
						$(".owner-main-nav").removeClass("active");
						$(".owner-sub-nav").removeClass("in");						
						subMenuEl.parent().addClass("in");					
					}	
					$(".owner-sub-nav li").removeClass("link-active");
					subMenuEl.addClass("link-active");
					$(".owner-main-nav[data-target='#"+pageUrl+"']").addClass("active");
					
					var env=CMSCom.deviceFn.findBootstrapEnv();
					if(env==="xs"||env==="sm"){ // scroll top for mobile 
						$('html, body').animate({			
				            scrollTop: $("#owner-inner-page .blurred-bg").offset().top
				        }, 'slow');
					}
					
				}
			}
			adjustScreen.footer();
		};
		
		var getHashURL=function(){				
			var pageHash=window.location.hash;
			if(!!pageHash){			
				pageHash=pageHash.substr(1);
				pageHash=pageHash.toLowerCase();
			}else{
				pageHash="";
			}	
			return 	pageHash;
		};
		
		var  mainNavHandler=function(e){	
			e.stopPropagation();
			var _this=$(this),			
			pageUrl=_this.data("page-url"),
			subMenu=_this.data("target"),
			targetCls=$(e).prop('class');	
	
			CMSStorage.clearLocalStoragePrefix("DataTables_"); // clearing Datatable local storage history
			
			var subPageUrl=$(subMenu+" li:eq(0)").data("sub-page");		
			subMenu=!!subMenu?subMenu.substr(1).toLowerCase():"";	
		
			if(getHashURL()===subMenu){				
				showPage(subMenu);
			}else{	
				window.location.hash = subMenu;	
			}			
		};
		
		var  subNavHandler=function(e){	
			var _this=$(this),			
			subPageUrl=_this.data("sub-page");	
			subPageUrl=!!subPageUrl?subPageUrl:"";
			$(".owner-sub-nav li").removeClass("link-active");
			$(this).addClass("link-active");
			CMSStorage.clearLocalStoragePrefix("DataTables_");
			if(getHashURL()===subPageUrl){				
				showPage(subPageUrl);
			}else{	
				window.location.hash=subPageUrl;	
			}			
		};	
		
		var ownerDashboardIconsHandler=function(){			
			CMSStorage.clearLocalStoragePrefix("DataTables_"); // clearing Datatable local storage history
		};
		
		return {
			showPage:showPage,		
			mainNavHandler:mainNavHandler,
			subNavHandler:subNavHandler,
			adminLogout:adminLogout,
			btnOnOffHandler:btnOnOffHandler,
			ownerDashboardIconsHandler:ownerDashboardIconsHandler
		}
	})();
	
	var _root, CMSOwner = {	
		loadwebShim:function(){
			
			webshim.setOptions('forms', {
				lazyCustomMessages: true,
				replaceValidationUI: true,
				customDatalist: true
			});
			webshim.polyfill('forms');
			// load the forms polyfill
//			webshims.validityMessages.en = {
//				    valueMissing: {
//				        defaultMessage: 'Please fill out this field. {%title}'
//				    },
//				    patternMismatch:{
//				    	 defaultMessage: 'Please fill out this field. invalid'
//				    }
//			};
		},	
		boostrapComponentsFn : function(){
			$('#menu-content').on('shown.bs.collapse', function (e) {			
				$(".owner-main-nav").not($(e.target)).removeClass("active");
				$(".owner-sub-nav").not($(e.target)).removeClass("in");	
				$(e.target).addClass("active");
				
			});
			$('#menu-content').on('hidden.bs.collapse', function () {
			});	
		},	
		checkUserSessionExist:function(){			
			var cms_id=CMSOwnerCom.getCMSSignature();
			return !!cms_id;
		},			
		updateURLHash:function(){
			var pageUrlHash=$(this).data("page-url");
			window.location.hash = pageUrlHash.toLowerCase();	
		},
		windowResizeAction:function(){				
			adjustScreen.contentHeight();
		},
		changeHashURLAction:function(){		
			
			var pageHash=window.location.hash;
			if(!!pageHash){			
				pageHash=pageHash.substr(1);
				pageHash=pageHash.toLowerCase();
			}else{
				pageHash="";
			}	
			CMSOwnerService.showPage(pageHash);	
			
		},
		indexPageSetUp:function(){
			$("#ownerUserName").html("Welcome "+CMSOwnerCom.getOwnerUserName());
			
		},
		el : {
			body:$("body"),
			window:$(window),	
			cmsOwnerLogout:$("#cmsOwnerLogout"),
			btnOnOff:".btn-on-off",
			ownerMainnav:$(".owner-side-menu .owner-main-nav"),
			ownerSubnav:$(".owner-side-menu .owner-sub-nav li"),	
			ownerDashboardIcons:$("#ownerDashboardIcons"),
			
			// Cattle El
			addCattleDetailsHolder:$("#add-cattle-details"),
			cattleImageFile:"#cattleImageFile",
			uploadCattleImgHolder:$("#uploadCattleImgHolder"),
			thumbnailWrapper:".thumbnail-wrapper",
			removeCattleImg:".remove-cattle-Img",
			addCattleSelectFarm:"#addCattleSelectFarm",	
			addOrUpdateCattleFarm:".addOrUpdateCattleFarm",
			cmsSaveCattleForm_form_dt :"#cms-saveCattle-form .form-dt",
			cmsSaveCattleForm:"#cms-saveCattle-form",
			cmsSaveCattleBtn:"#cms-saveCattleBtn",
			cmsResetFarmBtn:"#cms-resetFarmBtn",
			viewCattleDetails:".view-cattle-details",
			editLinkCattleDtls:".edit-link-cattle-dtls",
			//For Editing user profile
			editProfileHolder:$("#view-user-profile"), // Section or div to hold the content 
			editOwnerForm:"#cms-viewOwner-form",
			cmsEditUserBtn:"#cms-EditProfileBtn",
			cmsUpdateUserBtn:"#cms-updateProfileBtn",
			cmsCancelUserBtn:"#cms-cancelProfileBtn",
			cmsResetUsrBtn:"#cms-resetProfileBtn",
			userEmail:"#useremail",
			//Milk Production  //Author AB
			mpForm:"#milk-production-form",
			mpHolder:$("#add-milk-data"),
			mpFarmDrpdwn:"#mp-addFarm",
			mpDoeDrpdwn:"#mkDoeId",
			mpSaveBtn:"#mp-saveBtn",
			mpResetBtn:"#mp-resetBtn",
			
			//Milk production list
			
			mpListHolder:$("#owner-milkList-holder"),
			editMilkProductionDtls:".edit-milk-production-dtls",
			
			
			
			//breading
			addbreadingHolder:$("#add-breading"),
			cmsSaveBreedingDtlsForm:"#cms-saveBreedingDtls-form",
			cmsResetBreedingDtlsBtn:"#cms-resetBreedingDtlsBtn",
			cmsBreedingaddKidsBtn:"#cms-breedingaddKidsBtn",
			editlinkBreedingdtls:".edit-link-breeding-dtls",
			breedingbuckid:"#breedingbuckid",
			breedingdoeid:"#breedingdoeid",
			
			//breading Kid
			breedingAddkidmodal:$("#breeding-add-kid-modal"),
			cmssavebreedAddKidDtlsForm:"#cms-savebreedAddKidDtls-form",
			editlinkBreedingKidsdtls:".edit-link-breedingKids-dtls",
			breedingaddKidReset:"#breedingaddKidReset",
			breedingaddKidCancel:"#breedingaddKidCancel",
			
			//Change Password Owner // AB
			chngPasswordHolder:$("#owner-change-password"),
			chngPasswordForm:"#cms-change-password-form",
			chngPasswordBtn:"#cms-changePasswordBtn",
			chngPasswordResetButton:"#cms-resetPasswordBtn",
				
			//Reports // AB
				
			milkReportHolder:$("#view-milk-report"),
			milkReportForm:"#milk-report-form",
			mrFarmDrpdwn:"#mr-Farm",
			mrSaveBtn:"#mr-goBtn",
			mrResetBtn:"#mr-resetBtn",
				
			goatReportHolder:$("#view-goat-report"),
			goatReportForm:"#goat-report-form",
			grFarmDrpdwn:"#gr-Farm",
			grSaveBtn:"#gr-goBtn",
			grResetBtn:"#gr-resetBtn"
			
		},
		setEvents : function() {
	
			if(CMSCom.deviceFn.isIE()){				
				window.addEventListener("hashchange", _root.changeHashURLAction);
			}else{
				_root.el.window.on('hashchange', _root.changeHashURLAction);
			}
			_root.el.window.on('resize', _root.windowResizeAction);					
			_root.el.cmsOwnerLogout.off("click").on("click",CMSOwnerService.adminLogout);
			_root.el.body.off("click").on("click",_root.el.btnOnOff,CMSOwnerService.btnOnOffHandler);				
			_root.el.ownerMainnav.off("click").on("click",CMSOwnerService.mainNavHandler);
			_root.el.ownerSubnav.off("click").on("click",CMSOwnerService.subNavHandler);
			_root.el.ownerDashboardIcons.off("click").on("click",CMSOwnerService.ownerDashboardIconsHandler);
			
			// Cattle Events
			_root.el.addCattleDetailsHolder.on("invalid",_root.el.cmsSaveCattleForm_form_dt,CMSCom.inValid.errMsg);
			_root.el.addCattleDetailsHolder.on("input",_root.el.cmsSaveCattleForm_form_dt,CMSCom.inValid.errMsg);
			
			_root.el.addCattleDetailsHolder.on("change",_root.el.cattleImageFile,OwnerCattle.cattleImageFileUploadHandler);
			_root.el.addCattleDetailsHolder.on("click",_root.el.thumbnailWrapper,OwnerCattle.selectCattlePrimaryImage);
			_root.el.addCattleDetailsHolder.on("click",_root.el.removeCattleImg,OwnerCattle.removeCattleImageHandler);	
			
			_root.el.addCattleDetailsHolder.on("change",_root.el.addOrUpdateCattleFarm,OwnerCattle.addOrUpdateCattleFarmHandler);
			
			_root.el.addCattleDetailsHolder.on("click",_root.el.cmsSaveCattleBtn,OwnerCattle.saveCattleBtnHandler);			
			_root.el.addCattleDetailsHolder.on("submit",_root.el.cmsSaveCattleForm,OwnerCattle.saveCattle);			
			_root.el.addCattleDetailsHolder.on("click",_root.el.cmsResetFarmBtn,OwnerCattle.resetCattleSaveFarm);	
			_root.el.body.on("click",_root.el.viewCattleDetails,OwnerCattle.viewCattleDetails);		
			_root.el.body.on("click",_root.el.editLinkCattleDtls,OwnerCattle.editLinkCattleDtlsHandler);
			
			//List and Edit User Profile // AB
			_root.el.editProfileHolder.on("click",_root.el.cmsEditUserBtn,OwnerProfile.editUserProfile);
			//_root.el.editProfileHolder.on("click",_root.el.cmsUpdateUserBtn,OwnerProfile.viewUserProfile);
			_root.el.editProfileHolder.on("click",_root.el.cmsCancelUserBtn,OwnerProfile.cancelProfile);
			_root.el.editProfileHolder.on("click",_root.el.cmsResetUsrBtn,OwnerProfile.resetUserProfile);
			_root.el.editProfileHolder.on("submit",_root.el.editOwnerForm,OwnerProfile.UpdateUserProfile);
			//_root.el.editProfileHolder.on("blur",_root.el.userEmail,OwnerProfile.checkEmail);
			
			//mp-Milk Production//AB
			_root.el.mpHolder.on("change",_root.el.mpFarmDrpdwn,MilkProduction.mpshowDoedetails); // To load Doe on change of farm
			_root.el.mpHolder.on("change",_root.el.mpDoeDrpdwn,MilkProduction.mpEditedetails); //Loads data into fields (If data exists)
			_root.el.mpHolder.on("submit",_root.el.mpForm,MilkProduction.mpSavedetails);// to SAVE milk production details
			_root.el.mpHolder.on("click",_root.el.mpResetBtn,MilkProduction.mpResetfield);// To clear all input fields
			_root.el.mpHolder.on("click",_root.el.mpSaveBtn,MilkProduction.mpSaveBtnHandler);// For validation of calender
			
			//_root.el.mpListHolder.on("click",_root.el.editMilkProductionDtls,MilkProductionList.editMilkProductionHandler);
			
			
			
			
			
			//Change Password
			_root.el.chngPasswordHolder.on("submit",_root.el.chngPasswordForm,OwnerChangepassword.updatepassword);
			_root.el.chngPasswordHolder.on("click",_root.el.chngPasswordResetButton,OwnerChangepassword.resetField);
			
			
			
			
			//breeding
			_root.el.addbreadingHolder.on("submit",_root.el.cmsSaveBreedingDtlsForm,OwnerBreading.saveBreedingDtls);
			_root.el.addbreadingHolder.on("click",_root.el.cmsBreedingaddKidsBtn,OwnerBreading.breedingaddKidsHandlder);
			_root.el.addbreadingHolder.on("click",_root.el.cmsResetBreedingDtlsBtn,OwnerBreading.resetBreedingDtlsHandler);
			_root.el.body.on("click",_root.el.editlinkBreedingdtls,OwnerBreading.editlinkBreedingdtlsHandlder);
			_root.el.addbreadingHolder.on("change",_root.el.breedingbuckid,OwnerBreading.breedingBuckOnChangeHandler); // To load Doe on change of farm
			_root.el.addbreadingHolder.on("change",_root.el.breedingdoeid,OwnerBreading.breedingDoeOnChangeHandler); 
			

			
			//breading kids
			_root.el.breedingAddkidmodal.on("submit",_root.el.cmssavebreedAddKidDtlsForm,OwnerBreading.savebreedAddKidDtls);
			_root.el.body.on("click",_root.el.editlinkBreedingKidsdtls,OwnerBreading.editlinkBreedingKidsdtlsHandlder);
			_root.el.breedingAddkidmodal.on("click",_root.el.breedingaddKidReset,OwnerBreading.resetbreedAddKidDtls);
			_root.el.breedingAddkidmodal.on("click",_root.el.breedingaddKidCancel,OwnerBreading.cancelbreedAddKidDtls);
			
			
			//reports
			_root.el.milkReportHolder.on("change",_root.el.mrFarmDrpdwn,MilkReport.getDoeDetailsByFarm); // To load Doe on change of farm
			_root.el.milkReportHolder.on("submit",_root.el.milkReportForm,MilkReport.showReports);
			_root.el.milkReportHolder.on("click",_root.el.mrSaveBtn,MilkReport.mrSaveBtnHandler);
			_root.el.milkReportHolder.on("click",_root.el.mrResetBtn,MilkReport.resetFields);
			
			
			
			
			_root.el.goatReportHolder.on("submit",_root.el.goatReportForm,GoatReport.showReports);
			_root.el.goatReportHolder.on("click",_root.el.grSaveBtn,GoatReport.mrSaveBtnHandler);
			_root.el.goatReportHolder.on("click",_root.el.grResetBtn,GoatReport.mrResetBtnHandler);
			
			
			
			
			_root.boostrapComponentsFn();
			
			// Stop right mouse Click 
			_root.el.body.on('contextmenu','a', function(e){ e.preventDefault(); e.stopPropagation();});		
		},
		load : function() {
			CMSCom.init();
			_root.setEvents();
			_root.indexPageSetUp();
			adjustScreen.contentHeight();			
			CMSStorage.clearLocalStoragePrefix("DataTables_"); 	// clearing Datatable local storage history
			window.location.hash =""; 							// removing initially hash for initial 			
			_root.loadwebShim();
		},
		init : function() {
			_root = this;			
			if(CMSCom.isUserSessionExists("owner")){
				_root.load();	
				CMSGl_Data.userLogin="owner";
			}	
		}
	};

	document.addEventListener('DOMContentLoaded', CMSOwner.init.bind(CMSOwner));
	window.publicModule = {
			showPage:CMSOwnerService.showPage	
	}
	
})(this);