var CMSAdminCommon=(function(){
	"use strict";

	return {
		getInitReqData:function(){			
			var reqData={};		
			if(CMSCom.isUserSessionExists("admin")){
				reqData["u_id"]=CMSStorage.getStorage("admin_u_id");
				reqData["signature"]=CMSStorage.getStorage("admin-signature");
				return reqData;
			}else{
				var msg={session:0,msg:"Session Expired, Please Login again"};
				   CMSMessage.showCommonMsg(msg);			
				return null;
			}			
		},
		getAdminUserName:function(){
			return CMSStorage.getStorage("admin-username");
		}
		
	}
	
})(); 


(function(window) {
	   "use strict";
	
	
	var CMSAdminService=(function(){
		
		var getCMSId=function(){
			return CMSStorage.getStorage("admin-signature");
		};
		
		var btnOnOffHandler=function(){
			$(".btn-on-off").removeClass("on");
		    $(this).addClass("on");
		};
		
		var showPage=function(pageUrl,id){
			
			if(CMSCom.isUserSessionExists("admin")){
				if(!!pageUrl){// For directing to inner Pages			
			
				
					$("#cmsAdminDashboardWrapper").addClass("hidden");
					$(".subPageContent").addClass("hidden");
					$("#cmsAdminRightContent").removeClass("hidden");
					$("#cmsAdminLeftMenu").removeClass("hidden");
					
					if("farm-details"===pageUrl){		// Farm Details  -------------- 					
						AdminFarm.showFarmDetlsPage();					
					}else if("add-farm"===pageUrl){			
						
						AdminFarm.showFarmAddOrUpdatePage("Add Farm",id);
						
					}else if(pageUrl.indexOf("edit-farm")!==-1){		
						var farmid=pageUrl.split("=")[1];
						AdminFarm.showFarmAddOrUpdatePage("Edit Farm",farmid);			
						pageUrl="add-farm";
						
					}else if("user-details"===pageUrl){		// User Details  -------------- 
						
						AdminOwner.showOwnerDetlsPage();
						
					}else if("add-owner"===pageUrl){	
						
						AdminOwner.showOwnerAddOrUpdatePage("Add Owner",id);
				
					}else if(pageUrl.indexOf("edit-owner")!==-1){			
						var ownerid=pageUrl.split("=")[1];
						AdminOwner.showOwnerAddOrUpdatePage("Edit Owner",ownerid);		
						pageUrl="add-owner";
						
					}else if("vet-details"===pageUrl){					
						AdminVet.showVetDetlsPage();
						
					}else if("assign-farm"===pageUrl){		
						AdminVet.showVetAssignFarmPage("Assign Farm",null);
					}else if(pageUrl.indexOf("edit-vet-farm")!==-1){			
						var vetid=pageUrl.split("=")[1];
						AdminVet.showVetAssignFarmPage("Reassign Farm",vetid);	
						pageUrl="vet-details";	
					}else if("cattle-details"===pageUrl){	// Cattle Details  --------------						
						AdminCattle.showCattleDetlsPage();		
						
					}else if("health-care"===pageUrl){						
						AdminHealthcare.showHealthcareDetlsPage();
						
					}else if("vaccination-list"===pageUrl){		
						AdminVaccination.showVaccinationDetlsPage();					
	
					}else if("breeding"===pageUrl){					
						AdminBreading.showBreedingDetlsPage();			
				
					}else if("milk-production"===pageUrl){	
						MilkProduction.showMilkProductionDetlsPage();	
						$("#breadcrumb-subpage").html("Milk Production");
					}else{
						$("#cms-coming-soon").removeClass("hidden");	
					}
					
					changeLeftMenu(pageUrl);
					
					var env=CMSCom.deviceFn.findBootstrapEnv();
					
					if(env==="xs"||env==="sm"){ // scroll top for mobile 
						$('html, body').animate({						
				            scrollTop: $("#cmsAdminRightContent").offset().top
				        }, 'slow');
					}

				
				}else{	// For directing to dashboard Page						
					
					$("#cmsAdminDashboardWrapper").removeClass("hidden");
					$("#cmsAdminRightContent").addClass("hidden");
					$(".subPageContent").addClass("hidden");
					$("#cmsAdminLeftMenu").addClass("hidden");				
				}	
			}
		};

		var adminLogout=function(){
			var urlObj=CMSAdminCons.urls_info["logout"],
        	reqData=jQuery.extend({}, CMSAdminCommon.getInitReqData());
			reqData["reqData"]={};		
			reqData=JSON.stringify(reqData);
			CMSCom.ajaxService.getJsonData(urlObj,reqData).done(function(jsonResData) {	
				if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
					if(jsonResData.status==="1"){
						CMSStorage.removeStorage("admin-signature");
						CMSStorage.removeStorage("admin_u_id");
						CMSStorage.removeStorage("admin-username");
						CMSStorage.removeStorage("user_role_id");
						window.location.href="../login.html";							
					}		
				}
			}).fail(CMSCom.exceptionHandler.ajaxFailure);	
			
		};

		var collapseLeftMenu=function(pageUrl){
			$(".cms-adm-side-menu .cms-main-nav").removeClass("active");		
			$(".cms-adm-side-menu .cms-main-nav").find("i.fa").removeClass("fa-minus-square");
			$(".cms-adm-side-menu .cms-main-nav").find("i.fa").addClass("fa-plus-square");
			$(".cms-sub-nav").removeClass("in");	
			$(".cms-sub-nav li").removeClass("link-active");
		};
		
		var changeLeftMenu=function(pageUrl){		
			var subMenuEl=$(".cms-sub-nav").find("a[href='#"+pageUrl+"']"),
				mainPageUrl=subMenuEl.parent().parent().prop("id"),
				mainMenuEl=$(".cms-main-nav[data-target='#"+mainPageUrl+"']");	
			
				collapseLeftMenu();					
				mainMenuEl.find("i.fa").removeClass("fa-minus-square");	
				mainMenuEl.find("i.fa").addClass("fa-minus-square");
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
			CMSStorage.clearLocalStoragePrefix("DataTables_");
		};
		
		var subNavHandler=function(e){
			e.stopPropagation();
			var _this=$(this),			
			subMenu=$(this).find("a").prop("href");
			
			subMenu=!!subMenu?subMenu.substr(1).toLowerCase():"";
			//showPage(subMenu);	
			//window.location.hash = subMenu;		
			CMSStorage.clearLocalStoragePrefix("DataTables_");
		};
		var showInnerPage=function(){
			window.location.hash = "farm-details";
		};
		
		return {
			showPage:showPage,
			mainNavHandler:mainNavHandler,
			subNavHandler:subNavHandler,
			collapseLeftMenu:collapseLeftMenu,
			adminLogout:adminLogout,
			getCMSId:getCMSId,			
			btnOnOffHandler:btnOnOffHandler,			 
			showInnerPage:showInnerPage
		}
	})();

	var _root, CMSAdmin = {	
		boostrapComponentsFn : function(){
			$('.cms-adm-side-menu').on('shown.bs.collapse', function (e) {					
				$(".cms-adm-side-menu .cms-sub-nav").not($(e.target)).removeClass("in");
				//console.log("shown");
			});
			$('.cms-adm-side-menu').on('hidden.bs.collapse', function () {
				//console.log("hidden");
			});	
		},	
		checkUserSessionExist:function(){			
			var cms_id=CMSAdminService.getCMSId();
			return !!cms_id;
		},			
		updateURLHash:function(){
			var pageUrlHash=$(this).data("page-url");
			window.location.hash = pageUrlHash.toLowerCase();	
			CMSAdminService.collapseLeftMenu();
			CMSStorage.clearLocalStoragePrefix("DataTables_"); 	// clearing Datatable local storage history
		},
		windowResizeAction:function(){	
			setTimeout(function(){
				adjustScreen.contentHeight();
			},250);		
		},
		changeHashURLAction:function(){		
	
			var pageHash=window.location.hash;
			if(!!pageHash){			
				pageHash=pageHash.substr(1);
				pageHash=pageHash.toLowerCase();
			}else{
				pageHash="";
			}	
			CMSAdminService.showPage(pageHash);				
		},
		indexPageSetUp:function(){
			$("#adminUserName").html("Welcome "+CMSAdminCommon.getAdminUserName());
			
		},
		el : {
			body:$("body"),
			window:$(window),
			cmsAdminDashboardIconsLi:$("#cmsAdminDashboardIcons li"),	
			cmsmainnav:$(".cms-adm-side-menu .cms-main-nav"),
			cmssubnav:$(".cms-adm-side-menu .cms-sub-nav li"),
			cmsAdminLogout:$("#cmsAdminLogout"),
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
			cmsownereditLink:".owner-editLink",
			cmsaddowner:$("#cms-add-owner"),
			cmssaveOwnerform:"#cms-saveOwner-form",
			cmssaveOwnerBtn:"#cms-saveOwnerBtn",
			cmsResetOwnerBtn:"#cms-resetOwnerBtn",
			cmsOwnefarmcode:"#farmcode",
			cmsOwnerPassword:"#password",
			addFarmSelectToList:"#addFarmSelectToList",
			removeFarmSelectToList:"#removeFarmSelectToList",		
			cmsuserName:"#username",
				
			/* Vet  Module */ 	
				
			cmsvetAssignFarmform:"#cms-vetAssignFarm-form",	
			cmsvetAssignfarmcode:"#vetassignfarmcode",
			cmsvetAssignEdit:$("#cms-vet-assign-edit"),
			addVetAssignToList:"#addVetAssignToList",
			removeVetAssignToList:"#removeVetAssignToList",
			cmsvetRestAssignFarmform:"#cms-resetVetAssignFarmBtn",
			vetApproveLink:".vet-approveLink",
			vetEditAssignFarmLink:".vet-editAssignFarmLink",
			assignvetfarmdelbtn:"#assignvetfarmdelbtn",
		},
		setEvents : function() {
			_root.el.window.on('hashchange', _root.changeHashURLAction);
			_root.el.window.on('resize', _root.windowResizeAction);
			_root.el.cmsAdminDashboardIconsLi.off("click").on("click",_root.updateURLHash);		
			_root.el.cmsmainnav.off("click").on("click",CMSAdminService.mainNavHandler);
			_root.el.cmssubnav.off("click").on("click",CMSAdminService.subNavHandler);
			_root.el.cmsAdminLogout.off("click").on("click",CMSAdminService.adminLogout);
			_root.el.body.off("click").on("click",_root.el.btnOnOff,CMSAdminService.btnOnOffHandler);			
					
			// Farm module ------------------------------
			
			_root.el.body.on("click",_root.el.farmEditLink,AdminFarm.editLinkHandler);
			//_root.el.body.on("click",_root.el.farmDeleteLink,AdminFarm.deleteLinkHandler);	
			//_root.el.deletebtnYes.off("click").on("click",AdminFarm.deletebtnYesHandler);	  
			_root.el.cmsaddfarm.on("submit",_root.el.cmssaveFarmform,AdminFarm.saveFarm);			
			_root.el.cmsaddfarm.off("click").on("click",_root.el.cmssResetSaveFarmBtn,AdminFarm.resetSaveFarm);		
			
			/* Owner Module //Author : AB*/
			_root.el.cmsaddowner.on("submit",_root.el.cmssaveOwnerform,AdminOwner.saveOwner);	
			_root.el.cmsaddowner.on("click",_root.el.cmsResetOwnerBtn,AdminOwner.resetOwnerSaveFarm);	
			_root.el.body.on("keyup",_root.el.cmsOwnefarmcode,AdminOwner.showListfarm);	
			_root.el.body.on("click",_root.el.addFarmSelectToList,AdminOwner.addFarmSelectToListHandler);
			_root.el.body.on("click",_root.el.removeFarmSelectToList,AdminOwner.removeFarmSelectHandler);
			_root.el.body.on("click",_root.el.cmssaveOwnerBtn,AdminOwner.saveOwnerBtnHandler);
			_root.el.body.on("blur",_root.el.cmsuserName,AdminOwner.checkUserName);			
			_root.el.body.on("click",_root.el.cmsownereditLink,AdminOwner.editLinkHandler);
			
			/* Vet  Module */ 
			_root.el.cmsvetAssignEdit.on("submit",_root.el.cmsvetAssignFarmform,AdminVet.saveVetAssignFarm);
			_root.el.cmsvetAssignEdit.on("click",_root.el.cmsvetRestAssignFarmform,AdminVet.resetVetAssignFarm);
			_root.el.body.on("keyup",_root.el.cmsvetAssignfarmcode,AdminVet.showListfarm);	
			_root.el.body.on("click",_root.el.addVetAssignToList,AdminVet.addFarmSelectToListHandler);
			_root.el.body.on("click",_root.el.removeVetAssignToList,AdminVet.removeFarmSelectHandler);
			_root.el.body.on("click",_root.el.vetApproveLink,AdminVet.vetApproveLinkHandler);
			_root.el.body.on("click",_root.el.vetEditAssignFarmLink,AdminVet.vetEditAssignFarmLinkHandler);
			_root.el.body.on("click",_root.el.assignvetfarmdelbtn,AdminVet.assignvetfarmdelbtnHandler);
			
			_root.boostrapComponentsFn(); 
			$(".cm-navbar-btn").click(function(){
				CMSAdminService.showInnerPage();
			});
			// Stop right mouse Click 
			_root.el.body.on('contextmenu','a', function(e){ e.preventDefault(); e.stopPropagation();});		 
				
		},
		load : function() {
			_root.setEvents();
			_root.indexPageSetUp();
			adjustScreen.contentHeight();
			CMSStorage.clearLocalStoragePrefix("DataTables_"); 	// clearing Datatable local storage history
			window.location.hash =""; // removing initially hash for initial		
		},
		init : function() {
			CMSCom.init();
			_root = this;
			if(CMSCom.isUserSessionExists("admin")){
				_root.load();	
				CMSGl_Data.userLogin="admin";
			}		
		}
	};

	document.addEventListener('DOMContentLoaded', CMSAdmin.init.bind(CMSAdmin));
	
	window.publicModule = {
			showPage:CMSAdminService.showPage	
	}

})(this);