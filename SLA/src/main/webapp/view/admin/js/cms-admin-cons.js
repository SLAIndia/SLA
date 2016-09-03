"use strict";
var CMSAdminCons={ 
	urls_info:{	
		farmDetails:{
			url:contextPath+ "../../farm/farmDetails",
			contentType:'application/json',
		    dataType: "json",
			type:"POST"
		},
		saveFarm:{
			url:contextPath+"../../farm/saveFarm",
			contentType:'application/json',
		    dataType: "json",
			type:"POST"
		},
		getFarm:{
			url:contextPath+"../../farm/farmData",
			contentType:'application/json',
		    dataType: "json",
			type:"POST"
		},
		checkUserExist:{
			url:contextPath+"../../checkusername",
			contentType:'application/json',
		    dataType: "json",
			type:"POST"
			
		},
		deleteFarm:{
			url:contextPath+"../../farm/deleteFarm",
			contentType:'application/json',
		    dataType: "json",
			type:"POST"
		},
		getCattleDetailsByFarm:{
			url:contextPath+"../../cattle/getCattleDetailsByFarm",
			contentType:'application/json',
		    dataType: "json",
			type:"POST"
		},
		saveOwner:{
			url:contextPath+"../../admin/saveUser",
			contentType:'application/json',
		    dataType: "json",
			type:"POST"
		},
		getFarmsByCodeAndName:{
			url:contextPath+"../../farm/getFarmsByCodeAndName",
			contentType:'application/json',
		    dataType: "json",
			type:"POST"
		},
		getUserDetails:{
			url:contextPath+"../../admin/getUserDetails",
			contentType:'application/json',
		    dataType: "json",
			type:"POST"
		},
		vaccinationDetails:{
			url:contextPath+"../../vaccination/vaccinationDetails",
			contentType:'application/json',
		    dataType: "json",
			type:"POST"
		},
		getVetDetailsByName:{
			url:contextPath+"../../vet/getVetDetailsByName",
			contentType:'application/json',
		    dataType: "json",
			type:"POST"
		},
		saveVetFarm:{
			url:contextPath+"../../vet/saveVetFarm",
			contentType:'application/json',
		    dataType: "json",
			type:"POST"
		},
		approveVet:{
			url:contextPath+"../../vet/approveVet",
			contentType:'application/json',
		    dataType: "json",
			type:"POST"
		},
		breedingDetails:{
			url:contextPath+"../../breeding/breedingDetails",
			contentType:'application/json',
		    dataType: "json",
			type:"POST"
			
		},
		getHealthCareList:{
			url:contextPath+"../../healthcare/getHealthCareList",
			contentType:'application/json',
		    dataType: "json",
			type:"POST"
			
		},
		milkProductionDetails:{
			url:contextPath+"../../milkproduction/getAllMilkProduction",
			contentType:'application/json',
		    dataType: "json",
			type:"POST"
			
		},
		logout:{
			url:contextPath+"../../logout",
			contentType:'application/json',
		    dataType: "json",
			type:"POST"
		},
		checkEmailEdit:{
			url:contextPath+"../../checkEmail",
			contentType:'application/json',
		    dataType: "json",
			type:"POST"
		},
		
	},
	dataTable:{ /** CMS Data table contants */
		farm:{
			dtFn:null,
			showFlg:false,
		},
		cattle:{
			dtFn:null,
			showFlg:false,
		},
		owner:{
			dtFn:null,
			showFlg:false,
		},
		vet:{
			dtFn:null,
			showFlg:false,
		},
		vaccination:{
			dtFn:null,
			showFlg:false,
		},
		breeding:{
			dtFn:null,
			showFlg:false,
		},
		milkproduction:{
			dtFn:null,
			showFlg:false,
		},
		healthcare:{
			dtFn:null,
			showFlg:false,
		}
	
	},
	ownerAssignFarms:{},
	vetAssignFarms:{}
};
