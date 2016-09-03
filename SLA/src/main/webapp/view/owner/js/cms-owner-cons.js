"use strict";
var  CMSOwnerCons={
	  urls_info:{	
		   getCattleDetailsOfAssignedFarm:{
				url:contextPath+"../../cattle/getCattleDetailsOfAssignedFarm",
				contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			    dataType: "json",
				type:"POST"
			},
			getFarmDetailsByUser:{
				url:contextPath+"../../farm/getFarmDetailsByUser",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"
			},
			getallFarmDetailsByUser:{
				url:contextPath+"../../farm/getallFarmDetailsByUser",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"
			},
			saveCattle:{
				url:contextPath+"../../cattle/saveCattle",					   
				type:"POST"
			},
			saveCattleNoImg:{
				url:contextPath+"../../cattle/saveCattleNoImg",					   
				type:"POST"
			},
			getCattleDetailsByFarm:{
				url:contextPath+"../../cattle/getCattleDetailsByFarm",
				contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			    dataType: "",/*	*/
				type:"POST"
			},
			getCattleDetails:{
				url:contextPath+"../../cattle/getCattleDetails",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"
			},
			getParentByFarmBirthdt:{
				url:contextPath+"../../cattle/getParentByFarmBirthdt",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"
			},
			getOwnerdetails:{
				url:contextPath+"../../admin/getUserDetails",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"
				
			},
			editUserdetails:{
				
					url:contextPath+"../../admin/editUser",
					contentType:'application/json',
				    dataType: "json",
					type:"POST"
				
			},
			saveMilk:{
				url:contextPath+"../../milkproduction/saveMilkProduction",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"
				
			},
			editMilk:{
				url:contextPath+"../../milkproduction/milkproductionDtls",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"
				
			},  			// ********************* Breeding
			saveBreeding:{
				url:contextPath+"../../breeding/saveBreeding",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"
				
			},
			getCattleDetailsOfAssignedFarmAndBreedDt:{
				url:contextPath+"../../cattle/getCattleDetailsOfAssignedFarmAndBreedDt",					   
				type:"POST"
			},
			breedingDetails:{
				url:contextPath+"../../breeding/breedingDetails",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"
				
			},
			getBreedingDetails:{
				url:contextPath+"../../breeding/getBreedingDetails",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"
				
			},
			breedKidDetails:{
				url:contextPath+"../../breeding/breedKidDetails",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"
			},
			saveBreedKid:{
				url:contextPath+"../../breeding/saveBreedKid",
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
			healthcareDetails:{
				url:contextPath+"../../healthcare/healthcareDetails",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"
			},
			changePassword:{
				
				url:contextPath+"../../admin/changePassword",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"
				
			},
			getSurgeryIllnessData:{
				url:contextPath+"../../surgery/getSurgeryIllnessData",
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
			getMilkProductionReport:{
				url:contextPath+"../../report/getMilkProdDetails",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"
				
			},

			getGoatReport:{
				url:contextPath+"../../report/getCattleDetails",
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
			milkProductionDetails:{
				url:contextPath+"../../milkproduction/getAllMilkProductionByOwner",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"				
			},
			getmilkProductionDetialsbyUserId:{
				url:contextPath+"../../milkproduction/getMilkProductionById",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"		
				
			},
			exportFunction:{
				
				url:contextPath+"../../report/exportFuncionality",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"	
				
			}
			
		},
		cattleImageData:[],
		cattlePrimaryImg:{},
		cattleSavedImages:[],
		cattleDeletedImages:[],
		cattleImageDim:{min:[250,250],max:[4096,4096] }, // [width , height]
		dataTable:{
			farm:{
				dtFn:null,
				showFlg:false,
			},
			cattle:{
				dtFn:null,
				showFlg:false,
			},
			breeding:{
				dtFn:null,
				showFlg:false,
			},
			breedingKids:{
				dtFn:null,
				showFlg:false,
			},
			healthcare:{
				dtFn:null,
				showFlg:false,
			},
			surgeryDtls:{
				dtFn:null,
				showFlg:false,
			},
			mkreport:{
				dtFn:null,
				showFlg:false,
			},
			goatreport:{
				dtFn:null,
				showFlg:false,
			},
			milkproduction:{
				dtFn:null,
				showFlg:false,
			},
			hcvaccine:{
				dtFn:null,
				showFlg:false,
			},
			hcdeworming:{
				dtFn:null,
				showFlg:false,
			}
		}			
};


var CMSOwnerCom=(function(){
	var getInitReqData=function(){		
		var reqData={};		
		if(CMSCom.isUserSessionExists("owner")){
			reqData["u_id"]=CMSStorage.getStorage("owner_u_id");
			reqData["signature"]=CMSStorage.getStorage("owner-signature");
			return reqData;
		}else{
			var msg={session:0,msg:"Session Expired, Please Login again"};
			   CMSMessage.showCommonMsg(msg);
		
			return null;
		}	
	
	};
	var getUserId=function(){
		return CMSStorage.getStorage("owner_u_id");
	};
	var getCMSSignature=function(){
		return CMSStorage.getStorage("owner-signature");
	};
	
	return {
		getInitReqData:getInitReqData,
		getUserId:getUserId,
		getCMSSignature:getCMSSignature,
		getOwnerUserName:function(){
			return CMSStorage.getStorage("owner-username");
		}
	}
})();  //CMSOwnerCom.getCMSSignature()