var CMSVetCommon=(function(){
	"use strict";
	var getUserId=function(){
		return CMSStorage.getStorage("vet_u_id");
	};
	return {
		getInitReqData:function(){			
			var reqData={};		
			if(CMSCom.isUserSessionExists("vet")){
				reqData["u_id"]=CMSStorage.getStorage("vet_u_id");
				reqData["signature"]=CMSStorage.getStorage("vet-signature");
				return reqData;
			}else{
				var msg={session:0,msg:"Session Expired, Please Login again"};
				   CMSMessage.showCommonMsg(msg);			
				return null;
			}

		},
		getUserId:getUserId,
		getVetUserName:function(){
			return CMSStorage.getStorage("vet-username");
		}
		
	}
	
})();

(function(window) {
	   "use strict";
	
	
	var CMSVetService=(function(){
		
		var getCMSId=function(){
			return CMSStorage.getStorage("vet-signature");
		};
		
		var btnOnOffHandler=function(){
			$(".btn-on-off").removeClass("on");
		    $(this).addClass("on");
		};
		
		var showPage=function(pageUrl,id){
			if(CMSCom.isUserSessionExists("vet")){
				if(!!pageUrl){// For directing to inner Pages			
					
					$("#cmsVetDashboardWrapper").addClass("hidden");
					$(".subPageContent").addClass("hidden");
					$("#cmsVetInnerContent").removeClass("hidden");
			
					
					if("farm-details"===pageUrl){		// Farm Details  -------------- 					
						VetFarm.showFarmDetlsPage();
						$("#breadcrumb-subpage").html("Farm Details");
					}else if("cattle-details"===pageUrl){	// Cattle Details  --------------	
						
						VetCattle.showCattleDetlsPage();
						$("#breadcrumb-subpage").html("Cattle Details");
					}else if("health-care"===pageUrl){ // Health Care Details ---------
						
						VetHealthCare.showHealthCareAddOrUpdatePage("Health Care");
						
					}else if("vaccination-list"===pageUrl){		
			
						$("#breadcrumb-subpage").html("Vaccination");
						VetVaccination.showVaccinationDetlsPage();
					}else if("add-vaccination"===pageUrl){		
			
						$("#breadcrumb-subpage").html("Vaccination");
						VetVaccination.showVaccinationAddOrUpdatePage("Add Vaccination",null);
						
					}else if(pageUrl.indexOf("edit-vaccination")!==-1){			
						
						var vacId=pageUrl.split("=")[1];
						VetVaccination.showVaccinationAddOrUpdatePage("Edit Vaccination",vacId);	
						pageUrl="add-vaccination";	
						
					}else if("surgery-illness-details"===pageUrl){	
						//$("#cms-coming-soon").removeClass("hidden");			
						$("#breadcrumb-subpage").html("Surgery - Illness Details");
						CMSVetCons.surgeryEdit = false;
						VetSurgery.showSurgeryDetlsPage();
					}else if("add-surgery-illness-details"===pageUrl){	
						$("#breadcrumb-subpage").html("Surgery - Illness Details");
						VetSurgery.showSurgeryAddOrUpdatePage("Add Surgery/Illness Details",null,null);
					}else if("add-surgery-type"===pageUrl){	
						$("#breadcrumb-subpage").html("Add Surgery Type");
						SurgeryType.loadPage(null);
					}
					else if("surgery-type-list"===pageUrl){	
						$("#breadcrumb-subpage").html("Surgery List");
						SurgeryType.surgeryTypeList();
					}else if(pageUrl.indexOf("edit-surgery-type")!==-1){
						$("#breadcrumb-subpage").html("Edit Surgery Type");
						var surgId=pageUrl.split("=")[1];
						SurgeryType.loadPage(surgId);
						pageUrl="add-surgery-type";
					}
					else if(pageUrl.indexOf("edit-surgery-illness")!==-1){			
						var params =pageUrl.split("=")[1];
						var cattleId = params.split("&")[0];
						var procDt = params.split("&")[1];
						VetSurgery.showSurgeryAddOrUpdatePage("Edit Surgery/Illness Details",cattleId,procDt);	
						pageUrl="add-surgery-illness-details";	
						
					}else if("breeding"===pageUrl){					
						VetBreading.showBreedingDetlsPage();
						
					}else if("profile"===pageUrl){		
						VetProfile.loadvetProfile();
					
						
					}else if("change-password" ===pageUrl){
						VetChangePassword.loadPage();
						
					}else{
						$("#cms-coming-soon").removeClass("hidden");	
					}
					changeLeftMenu(pageUrl);
					
					
				}else{	// For directing to dashboard Page						
					
					$("#cmsVetDashboardWrapper").removeClass("hidden");
					$(".subPageContent").removeClass("hidden");
					$("#cmsVetInnerContent").addClass("hidden");
							
				}	
			}
		};

		var vetLogout=function(){
			var urlObj=CMSVetCons.urls_info["logout"],
        	reqData=jQuery.extend({}, CMSVetCommon.getInitReqData());
			reqData["reqData"]={};		
			reqData=JSON.stringify(reqData);
			CMSCom.ajaxService.getJsonData(urlObj,reqData).done(function(jsonResData) {	
				if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
					if(jsonResData.status==="1"){
						CMSStorage.removeStorage("vet-signature");
						CMSStorage.removeStorage("vet_u_id");
						CMSStorage.removeStorage("vet-username");
						CMSStorage.removeStorage("user_role_id");
						CMSStorage.removeStorage("userObj");
						window.location.href="../login.html";							
					}		
				}
			}).fail(CMSCom.exceptionHandler.ajaxFailure);	

		};
		
		
		
	
		
		var collapseLeftMenu=function(pageUrl){
			$(".cms-vet-side-menu .cms-main-nav").removeClass("active");		
			//$(".cms-adm-side-menu .cms-main-nav").find("i.fa").removeClass("fa-minus-square");
			//$(".cms-adm-side-menu .cms-main-nav").find("i.fa").addClass("fa-plus-square");
			$(".cms-sub-nav").removeClass("in");	
			$(".cms-sub-nav li").removeClass("link-active");
		};
		
		var changeLeftMenu=function(pageUrl){		
			var subMenuEl=$(".cms-sub-nav").find("a[href='#"+pageUrl+"']"),
				mainPageUrl=subMenuEl.parent().parent().prop("id"),
				mainMenuEl=$(".cms-main-nav[data-target='#"+mainPageUrl+"']");	
			
				collapseLeftMenu();					

				mainMenuEl.addClass("active");
				subMenuEl.parent().parent().addClass("in");
				subMenuEl.parent().addClass("link-active");			
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
		
		var mainNavHandler=function(e){
			e.stopPropagation();
			CMSStorage.clearLocalStoragePrefix("DataTables_"); // clearing Datatable local storage history
			var _this=$(this),			
			isNotCollapsed=_this.find("i.fa").hasClass("fa-minus-square"),
			subMenu=_this.data("target");
			
			if(!isNotCollapsed){
				subMenu=!!subMenu?subMenu.substr(1).toLowerCase():"";				
				if(getHashURL()===subMenu){				
					showPage(subMenu);
				}else{	
					window.location.hash = subMenu;	
				}
					
			}else{
				collapseLeftMenu();
			}	
		
		};
		
		var subNavHandler=function(e){
			e.stopPropagation();
			CMSStorage.clearLocalStoragePrefix("DataTables_");
			var _this=$(this),			
			subMenu=$(this).find("a").prop("href").split("#")[1];			
			//subMenu=!!subMenu?subMenu.substr(1).toLowerCase():"";	

			if(getHashURL()===subMenu){				
				showPage(subMenu);
			}else{	
				//window.location.hash = subMenu;	
			}

		};
		
		return {
			showPage:showPage,
			mainNavHandler:mainNavHandler,
			subNavHandler:subNavHandler,
			collapseLeftMenu:collapseLeftMenu,
			vetLogout:vetLogout,
			getCMSId:getCMSId,
	
			btnOnOffHandler:btnOnOffHandler,			 
		
		}
	})();
	
	var _root, CMSVet = {	
		boostrapComponentsFn : function(){
			$('.cms-vet-side-menu').on('shown.bs.collapse', function (e) {					
				$(".cms-vet-side-menu .cms-sub-nav").not($(e.target)).removeClass("in");
				$(".cms-vet-side-menu .cms-main-nav").not($(e.target)).removeClass("active");
				
				//console.log("shown");
			});
			$('.cms-vet-side-menu').on('hidden.bs.collapse', function () {
				//console.log("hidden");
			});	
		},	
		checkUserSessionExist:function(){			
			var cms_id=CMSVetService.getCMSId();
			return !!cms_id;
		},			
		updateURLHash:function(){
			var pageUrlHash=$(this).data("page-url");
			window.location.hash = pageUrlHash.toLowerCase();	
			CMSVetService.collapseLeftMenu();
			CMSStorage.clearLocalStoragePrefix("DataTables_"); // clearing Datatable local storage history
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
			CMSVetService.showPage(pageHash);				
		},
		indexPageSetUp:function(){
			$("#vetUserName").html("Welcome "+CMSVetCommon.getVetUserName());
			
		},
		el : {
			body:$("body"),
			window:$(window),
			cmsVetDashboardIcons:$("#cmsVetDashboardIcons li"),	
			cmsmainnav:$(".cms-vet-side-menu .cms-main-nav"),
			cmssubnav:$(".cms-vet-side-menu .cms-sub-nav li"),
			cmsvetLogout:$("#cmsvetLogout"),
			btnOnOff:".btn-on-off",
			
			// Farm module ------------------------------
			farmEditLink:".farm-editLink", 
			farmDeleteLink:".farm-deleteLink",
			deletebtnYes:$("#deletebtnYes"),
			cmssaveFarmBtn:$(".cms-saveFarmBtn"),
			cmsaddfarm:$("#cms-add-farm"),
			cmssaveFarmform:"#cms-saveFarm-form",
			cmssResetSaveFarmBtn:"#cms-resetFarmBtn",
			// Owner Module 
			cmsaddowner:$("#cms-add-owner"),
			cmssaveOwnerform:"#cms-saveOwner-form",
			cmssaveOwnerBtn:"#cms-saveOwnerBtn",
			cmsResetOwnerBtn:"#cms-resetOwnerBtn",
			cmsOwnefarmcode:"#farmcode",
			cmsOwnerPassword:"#password",
			addFarmSelectToList:"#addFarmSelectToList",
			removeFarmSelectToList:"#removeFarmSelectToList" ,
			// vet Vaccination
			cmssaveVaccinationform:"#cms-saveVaccination-form",
			saveVaccinationBtn:"#cms-saveVaccinationBtn" ,
			resetVaccinationBtn:"#cms-resetVaccinationBtn" ,
			editVet:"#vet-edit",
			deleteVet:"#vet-delete",
			//Viw and Edit Veet Profile // AB
			vetprofileHolder:$("#cms-vet-profile-holder"),
			vetprofileForm:"#vet-Profile-form",
			vetprofileEditBtn:"#vet-EditProfileBtn",
			vetprofileResetBtn:"#vet-resetProfileBtn",
			vetprofileCancelBtn:"#vet-cancelProfileBtn",
			vetEmail:"#useremail",
			//Change Password
			
			vetChangepasswordHolder:$("#cms-vet-change-password-holder"),
			vetPasswordform:"#vet-change-password-form",
			vetResetpassword:"#vet-resetPasswordBtn",
				
				
			//Surgery Details
				vetSurgeryHolder:$("#cms-add-update-surgery"),
				addTreatment:"#addTreatment",
				farmselectid:"#farmselectid",
				consultant:".consultant",
				surgRequired:"#surgRequired",
				cattleSelectid:"#cattleSelectid",
				saveSurgery:"#cms-saveSurgey-form",
				resetSurgery:"#cms-resetSurgeryBtn",
				surgeryDetailsHolder:$("#gms-surgery-details"),
				editSurgery:"#surgery-edit",
				surgillprocdt:"#surgillprocdt",
				surgillprocdtCalen:"#surgillprocdt-calen",
				
			// Treatment Details
				treatmentHolder :$("#treatment-add-modal"),
				treatmntSave :"#treatmntSave",
				treatmntReset :"#treatmntReset",
				treatmntCancel :"#treatmntCancel",
				treatmentDataTableHolder:$("#treatmentDataTableHolder"),
				editTreatment:"#treatment-edit",
				
				surgeryTypeHolder:$("#cms-surgery-type-holder"),
				surgeryTypeForm:$("#saveSurgeryType-form"),
				surgeryTypesubmit:"#saveSurgryTypeBtn",
				surgeryTypereset:"#resetSurgryTypeBtn",
				
				surgeryTypeListHolder:$("#cms-surgery-type-list-holder"),
				surgeryListEdit:"#surgery-type-edit",
			
			
				
				// Healthcare 
				cmsvethealthcareholder:$("#cms-vet-health-care-holder"),
				addHealthCareTypeDtlsBtn:"#addHealthCareTypeDtlsBtn",
				healthcareselectFarm:"#healthcareselectFarm",
				healthcareselectCattle:"#healthcareselectCattle",
				hcvaccnName:"#hcvaccnName",
				cmsvethcvaccinationform:"#cms-vet-hc-vaccination-form",
				cmsroutinedewormingform:"#cms-routine-deworming-form",
				cmsvetHealthcareHolderModal:$("#cms-vet-healthcare-holder-modal"),		
				healthcareselectType:"#healthcareselectType",
				editHCsubdtls:".edit-hc-sub-dtls",
				hcVaccinSubmit:"#hcVaccinSubmit",
				hcVaccinReset:"#hcVaccinReset",
				hcVaccinCancel:"#hcVaccinCancel",				
				hcdewormingSubmit:"#hcdewormingSubmit",
				hcdewormingReset:"#hcdewormingReset",
				hcdewormingCancel:"#hcdewormingCancel",
			
			

		},
		setEvents : function() {
			_root.el.window.on('hashchange', _root.changeHashURLAction);
			_root.el.window.on('resize', _root.windowResizeAction);
			_root.el.cmsVetDashboardIcons.off("click").on("click",_root.updateURLHash);		
			_root.el.cmsmainnav.off("click").on("click",CMSVetService.mainNavHandler);
			_root.el.cmssubnav.off("click").on("click",CMSVetService.subNavHandler);
			_root.el.cmsvetLogout.off("click").on("click",CMSVetService.vetLogout);
			_root.el.body.on("click",_root.el.btnOnOff,CMSVetService.btnOnOffHandler);			
			
			//Vet Vaccination 
			_root.el.body.on("submit",_root.el.cmssaveVaccinationform,VetVaccination.saveVaccination);	
			_root.el.body.on("click",_root.el.resetVaccinationBtn,VetVaccination.resetVaccination);
			_root.el.body.on("click",_root.el.editVet,VetVaccination.editVet);
			_root.el.body.on("click",_root.el.deleteVet,VetVaccination.deleteVet);
			
			//Vet List and Edit Profile
			_root.el.vetprofileHolder.on("click",_root.el.vetprofileEditBtn,VetProfile.editVetprofile);
			_root.el.vetprofileHolder.on("click",_root.el.vetprofileResetBtn,VetProfile.resetVetprofile);
			_root.el.vetprofileHolder.on("click",_root.el.vetprofileCancelBtn,VetProfile.cancelVetprofile);
			_root.el.vetprofileHolder.on("submit",_root.el.vetprofileForm,VetProfile.updateVetprofile);
			//_root.el.vetprofileHolder.on("blur",_root.el.vetEmail,VetProfile.checkEmail);
			
			//Change Password
			_root.el.vetChangepasswordHolder.on("submit",_root.el.vetPasswordform,VetChangePassword.updatepassword);
			_root.el.vetChangepasswordHolder.on("click",_root.el.vetResetpassword,VetChangePassword.resetField);
			
			
			//Surgery
			_root.el.vetSurgeryHolder.on("click",_root.el.addTreatment,VetSurgery.showTreatmentmodal);
			_root.el.vetSurgeryHolder.on("change",_root.el.farmselectid,VetSurgery.loadCattleDtls);
			_root.el.vetSurgeryHolder.on("click",_root.el.consultant,VetSurgery.assignVet);
			_root.el.vetSurgeryHolder.on("click",_root.el.surgRequired,VetSurgery.showSurgeryMaster);
			_root.el.vetSurgeryHolder.on("change",_root.el.cattleSelectid,VetSurgery.loadSurgeryDtls);
			_root.el.vetSurgeryHolder.on("submit",_root.el.saveSurgery,VetSurgery.saveSurgery);
			_root.el.vetSurgeryHolder.on("click",_root.el.resetSurgery,VetSurgery.resetSurgeryPage);
			_root.el.body.on("click",_root.el.editSurgery,VetSurgery.editSurgery);
			_root.el.body.on("click",_root.el.editTreatment,VetSurgery.editTreatment);
			_root.el.vetSurgeryHolder.on("change",_root.el.surgillprocdt,VetSurgery.fetchEditData);
			_root.el.vetSurgeryHolder.on("change",_root.el.surgillprocdtCalen,VetSurgery.fetchEditData);
			
			//Surgery-Treatment
			_root.el.treatmentHolder.on("click",_root.el.treatmntSave,VetSurgery.addTreatmentDtls);
			_root.el.treatmentHolder.on("click",_root.el.treatmntReset,VetSurgery.resetTreatmentDtls);
			_root.el.treatmentHolder.on("click",_root.el.treatmntCancel,VetSurgery.cancelTreatmentDtls);
			
			//Surgery Type
			_root.el.surgeryTypeHolder.on("submit",_root.el.surgeryTypeForm,SurgeryType.saveDetails);
			_root.el.surgeryTypeHolder.on("click",_root.el.surgeryTypereset,SurgeryType.resetFields);			
			_root.el.surgeryTypeListHolder.on("click",_root.el.surgeryListEdit,SurgeryType.editSurgeryType);
			
			
			// Healthcare 			
			_root.el.cmsvethealthcareholder.on("click",_root.el.addHealthCareTypeDtlsBtn,VetHealthCare.addHealthCareTypeDtlsBtnHandler);
			_root.el.cmsvethealthcareholder.on("change",_root.el.healthcareselectFarm,VetHealthCare.healthcareselectFarmHandler);
			_root.el.cmsvethealthcareholder.on("change",_root.el.healthcareselectCattle,VetHealthCare.healthcareselectCattleHandler);
			_root.el.cmsvethealthcareholder.on("change",_root.el.healthcareselectType,VetHealthCare.healthcareselectTypeHandler);
			_root.el.cmsvetHealthcareHolderModal.on("change",_root.el.hcvaccnName,VetHealthCare.hcvaccnNameHandler);
			_root.el.cmsvetHealthcareHolderModal.on("submit",_root.el.cmsvethcvaccinationform,VetHealthCare.saveHCVaccinationDtls);
			_root.el.cmsvetHealthcareHolderModal.on("submit",_root.el.cmsroutinedewormingform,VetHealthCare.saveHCDewormingDtls);			
			_root.el.body.on("click",_root.el.editHCsubdtls,VetHealthCare.editHCsubdtlsHandler);
			
			_root.el.cmsvetHealthcareHolderModal.on("click",_root.el.hcVaccinSubmit,VetHealthCare.hcVaccinSubmitHandler);
			_root.el.cmsvetHealthcareHolderModal.on("click",_root.el.hcVaccinReset,VetHealthCare.hcVaccinResetHandler);
			_root.el.cmsvetHealthcareHolderModal.on("click",_root.el.hcVaccinCancel,VetHealthCare.hcVaccinCancelHandler);
			
			_root.el.cmsvetHealthcareHolderModal.on("click",_root.el.hcdewormingSubmit,VetHealthCare.hcdewormingSubmitHandler);
			_root.el.cmsvetHealthcareHolderModal.on("click",_root.el.hcdewormingReset,VetHealthCare.hcdewormingResetHandler);
			_root.el.cmsvetHealthcareHolderModal.on("click",_root.el.hcdewormingCancel,VetHealthCare.hcdewormingCanceleHandler);
			

			_root.boostrapComponentsFn(); 
		},
		load : function() {
			CMSCom.init();
			_root.setEvents();
			_root.indexPageSetUp();
			adjustScreen.contentHeight();
			CMSStorage.clearLocalStoragePrefix("DataTables_"); // clearing Datatable local storage history
			window.location.hash =""; // removing initially hash for initial		
		},
		init : function() {
			_root = this;
			if(CMSCom.isUserSessionExists("vet")){
				_root.load();	
				CMSGl_Data.userLogin="vet";
			}				
		}
	};

	document.addEventListener('DOMContentLoaded', CMSVet.init.bind(CMSVet));
	
	window.publicModule = {
			showPage:CMSVetService.showPage	
	}

})(this);

	
	
	
	
	
