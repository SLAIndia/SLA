
var  CMSVetCons={
	  urls_info:{	
			register:{
				url:contextPath+"../vet/registerVet",
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
			checkusername:{
				url:contextPath+"../checkusername",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"
			},
			farmDetails:{
				url:contextPath+ "../../farm/farmDetails",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"
			},
			getCattleDetailsByFarm:{
				url:contextPath+"../../cattle/getCattleDetailsByFarm",
				contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			    dataType: "",
				type:"POST"
			},
		   getCattleDetailsOfAssignedFarm:{
				url:contextPath+"../../cattle/getCattleDetailsOfAssignedFarm",
				contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			    dataType: "json",
				type:"POST"
			},
			vaccinationDetails:{
				url:contextPath+"../../vaccination/vaccinationDetails",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"
			},
			saveVaccination:{
				url:contextPath+"../../vaccination/saveVaccination",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"
			},
			deleteVaccination:{
				url:contextPath+"../../vaccination/deleteVaccination",
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
			getVetdetails:{
				url:contextPath+"../../admin/getUserDetails",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"
			},
			editVetdetails:{
				url:contextPath+"../../vet/editVet",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"
				
			},
			vetchangePassword:{
				url:contextPath+"../../admin/changePassword",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"
			},						// ************ Health care details 
			getFarmDetailsByUser:{
				url:contextPath+"../../farm/getFarmDetailsByUser",
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
			healthcareTypeDetailsById:{
				url:contextPath+"../../healthcare/healthcareTypeDetailsById",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"
			},
			saveHealthCare:{
				url:contextPath+"../../healthcare/saveHealthCare",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"
			},
			getSurgeryMaster :{
				url:contextPath+"../../surgery/getSurgeryMaster",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"
			},
			getSurgeryIllness :{//getSurgeryIllness
				url:contextPath+"../../surgery/getSurgeryIllness",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"
			},
			saveSurgeryIllness :{
				url:contextPath+"../../surgery/saveSurgeryIllness",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"
			},
			saveSurgeryType:{
				url:contextPath+"../../surgeryType/saveSurgeryType",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"
				
			},
			getSurgeryList : {
				url:contextPath+"../../surgeryType/getSurgeryList",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"
			},
			checkSurgeryName:{
				url:contextPath+"../../surgeryType/checkSurgeryName",
				contentType:'application/json',
			    dataType: "json",
				type:"POST"
				
			},
			getSurgeryIllnessMaster:{
				url:contextPath+"../../surgery/getSurgeryIllnessMaster",
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
			checkEmail:{
				url:contextPath+"../checkEmail",
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
			vaccination:{
				dtFn:null,
				showFlg:false,
			},
			breeding:{
				dtFn:null,
				showFlg:false,
			},
			surgery:{
				dtFn:null,
				showFlg:false
			},
			hcvaccine:{
				dtFn:null,
				showFlg:false,
			},
			hcdeworming:{
				dtFn:null,
				showFlg:false,
			},
			surgeryDtls :{
				dtFn:null,
				showFlg:false,
			},
			surgeryTypeDtls:{
				dtFn:null,
				showFlg:false
				
			}
		},surgeryTreatment:[],
		 surgeryEdit:false
			
	};