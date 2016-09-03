var OwnerReports=(function(){
	
	var excelData={};
	
	var loadMilkProduction=function(frmDt,toDt,farmId,cattleid){
		
		if(!!CMSOwnerCons.dataTable["mkreport"].showFlg){
			CMSOwnerCons.dataTable["mkreport"].dtFn._fnAjaxUpdate();
			CMSOwnerCons.dataTable["mkreport"].dtFn.fnSetColumnVis( 0, false );	
			setTimeout(function(){
				$("#milkProdReport" ).wrapAll( "<div class='table-responsive'></div>" );	
			},100);
		}else{	
			var reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData()),
				urlObj=CMSOwnerCons.urls_info["getMilkProductionReport"],
				query={};
			
			query["fromDt"]=CMSCom.dateFn.cusDateServerFormat(frmDt);
			query["toDt"]=CMSCom.dateFn.cusDateServerFormat(toDt);
			query["farmId"]=farmId*1;
			query["cattleId"]=cattleid*1;			
			reqData["reqData"]=query;			

			$.fn.dataTableExt.sErrMode = 'throw';
			CMSOwnerCons.dataTable["mkreport"].dtFn=$('#milkProdReport').dataTable({
			  "ajax": {
			    "url": urlObj.url,
			    "type": urlObj.type,
			    "dataType": 'json',
			    processData: false,
			    beforeSend:function(jqXHR,settings ){
			    	settings.data = JSON.stringify(reqData);
                },
            	"contentType":'application/json',
			    "dataSrc": function ( dataJson ) {		
			    	if(CMSCom.exceptionHandler.isLoginSessionExpire(dataJson)&&dataJson.status*1===1){	    	
			       		
				     	var jsonRes=JSON.parse(dataJson.resData),				     	
				     		 excelRow="";
				     	for (var key in jsonRes){
				    		var eachObj=jsonRes[key];
				    		
				    		jsonRes[key][0] = eachObj.milkprodid;
				    		jsonRes[key][1] = !!eachObj.milkproddt?CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(eachObj.milkproddt)):"";	
				    		jsonRes[key][2] = !!eachObj.objdoe.cattleeartagid?eachObj.objdoe.cattleeartagid:"";
				    		jsonRes[key][3] = !!eachObj.objdoe.cattledob?CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(eachObj.objdoe.cattledob)):"";
				    		jsonRes[key][4] = !!eachObj.objdoe.cattlelocation?eachObj.objdoe.cattlelocation:"";		
				    		jsonRes[key][5] = !!eachObj.objdoe.farm.farmname? CMSCom.textFn.limitTooltip(eachObj.objdoe.farm.farmname ,15):"";	
				    		jsonRes[key][6] = !!eachObj.objdoe.farm.farmcode?eachObj.objdoe.farm.farmcode:"";	
				    		jsonRes[key][7] = eachObj.milkprodqty;      		
				    		
				    		excelRow+="<tr>";
				    		excelRow+="<td align='center'>"+jsonRes[key][1]+"</td>";
				    		excelRow+="<td align='center'>"+jsonRes[key][2]+"</td>";
				    		excelRow+="<td align='center'>"+(!!jsonRes[key][3]?jsonRes[key][3]:" -- ")+"</td>";
				    		excelRow+="<td align='center'>"+(!!jsonRes[key][4]?jsonRes[key][4]:" --")+"</td>";
				    		excelRow+="<td align='center'>"+(!!jsonRes[key][5]?jsonRes[key][5]:" --")+"</td>";
				    		excelRow+="<td align='center'>"+(!!jsonRes[key][6]?jsonRes[key][6]:" -- ")+"</td>";
				    		excelRow+="<td align='center'>"+(!!jsonRes[key][7]?jsonRes[key][7]:" -- ")+"</td>";				    		
				    		excelRow+="</tr>";
				    				    		
				    	} 
				     	
				     	var title="<tr><th colspan='7'>Milk Production Report Details</th></tr>"
				     	var tableHeading=" <thead>"+title+"<tr><th>Date of Lactation   </th>";				            
				     	tableHeading+="  <th>Doe Ear tag</th><th> Date of Birth</th><th>Location</th>";  				              
				     	tableHeading+="<th>Farm</th>    <th>Farm Code</th><th>Milk Quantity </th> </tr></thead>";
				     	
				     	var logo="<div></div>";				     	
				     	excelData="<table border='1' style='border-collapse:collapse; border:1px solid #ddd' cellspacing='5' cellpadding='5'>"+tableHeading+"<tbody>"+excelRow+"</tbody></table>";

						setTimeout(function(){
							$("#milkProdReport" ).wrapAll( "<div class='table-responsive'></div>" );	

							/*if(CMSCom.deviceFn.isMobile.any()){
								$("#owner-rpt-print").hide();
								$("#owner-rpt-export").hide();
							}else{
								$("#owner-rpt-print").show();
								$("#owner-rpt-export").show();
							}*/
							
							$("#owner-rpt-print").show();
							$("#owner-rpt-export").show();
						},100);
					    return jsonRes;		
			    	}else{
			    		return [];
			    	}
			    }			    
			  },
			  destroy: true,
			  "aaSorting": [[ 1, "desc" ]],			
			  'processing': false,
			  'serverSide': false,
			//  'bLengthChange': false, 
			  'fnInitComplete' :function(){
					setTimeout(function(){
						$("#milkProdReport" ).wrapAll( "<div class='table-responsive'></div>" );	
						},100);
			  },
			  "fnRowCallback": function(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
			        /* push this row of data to currData array*/
			       // console.log(aData);
				
			    }
			});
			var oSettings = CMSOwnerCons.dataTable["mkreport"].dtFn.fnSettings();
		     oSettings._iDisplayLength = 10;			     
		     CMSOwnerCons.dataTable["mkreport"].dtFn.fnSetColumnVis( 0, false );
		}			
	};
	
	var loadGoatReport=function(frmDt,toDt,farmId){
		
		if(!!CMSOwnerCons.dataTable["goatreport"].showFlg){
			CMSOwnerCons.dataTable["goatreport"].dtFn._fnAjaxUpdate();
			CMSOwnerCons.dataTable["goatreport"].dtFn.fnSetColumnVis( 0, false );	
		}else{	
			var reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData()),
				urlObj=CMSOwnerCons.urls_info["getGoatReport"],
				query={};
			
			query["fromDt"]=CMSCom.dateFn.cusDateServerFormat(frmDt);
			query["toDt"]=CMSCom.dateFn.cusDateServerFormat(toDt);
			query["farmId"]=farmId*1;
			
			reqData["reqData"]=query;			
			$.fn.dataTableExt.sErrMode = 'throw';
			CMSOwnerCons.dataTable["goatreport"].dtFn=$('#goatProdReport').dataTable({
			  "ajax": {
			    "url": urlObj.url,
			    "type": urlObj.type,
			    "dataType": 'json',
			    processData: false,
			    beforeSend:function(jqXHR,settings ){
			    	settings.data = JSON.stringify(reqData);
                },
            	"contentType":'application/json',
			    "dataSrc": function ( dataJson ) {		
			    	if(CMSCom.exceptionHandler.isLoginSessionExpire(dataJson)&&dataJson.status*1===1){
			    			    			
				     	var jsonRes=JSON.parse(dataJson.resData),				 
				     		excelRow="";
				     	
				     	for (var key in jsonRes){
				    		var eachObj=jsonRes[key];
				    	
				    		jsonRes[key][0] = eachObj.farm.farmid;
				    		jsonRes[key][1] = eachObj.farm.farmcode;
				    		jsonRes[key][2] =  eachObj.cattleeartagid;//!!(eachObj.cattleeartagid)?eachObj.cattleeartagid:""
				    		jsonRes[key][3] =  !!(eachObj.cattledob)?CMSCom.dateFn.cusDateAsDD_MM_YYYY(new Date(eachObj.cattledob)):"";
				    		jsonRes[key][4] =  !!(eachObj.buckcattle)?eachObj.buckcattle.cattleeartagid:"";
				    		jsonRes[key][5] =  !!(eachObj.doecattle)?eachObj.doecattle.cattleeartagid:"";
				    		jsonRes[key][6] =  !!(eachObj.cattlestatus)?"Active":"Inactive";
				    		
				    		excelRow+="<tr>";
				    		excelRow+="<td align='center'>"+jsonRes[key][1]+"</td>";
				    		excelRow+="<td align='center'>"+jsonRes[key][2]+"</td>";
				    		excelRow+="<td align='center'>"+(!!jsonRes[key][3]?jsonRes[key][3]:" -- ")+"</td>";
				    		excelRow+="<td align='center'>"+(!!jsonRes[key][4]?jsonRes[key][4]:" --")+"</td>";
				    		excelRow+="<td align='center'>"+(!!jsonRes[key][5]?jsonRes[key][5]:" --")+"</td>";
				    		excelRow+="<td align='center'>"+jsonRes[key][6]+"</td>";
				    		excelRow+="</tr>";
				    				    		
				    	} 
				     	
				     	var title="<tr><th colspan='6'>Goat Report Details</th></tr>"
				    	var tableHeading="<thead>"+title+"<tr><th>Farm Code   </th><th>Ear Tag</th>";
            	     	tableHeading+="<th>Date of Birth</th>  ";
				     	tableHeading+="<th>Buck Ear Tag</th>";
				     	tableHeading+="<th>Doe Ear Tag</th>";
				     	tableHeading+="  <th>Status</th></tr> </thead>";
				     	
				     	excelData="<table border='1' style='border-collapse:collapse; border:1px solid #ddd' cellspacing='5' cellpadding='5'>"+tableHeading+"<tbody>"+excelRow+"</tbody></table>";
				
						setTimeout(function(){
							$("#goatProdReport" ).wrapAll( "<div class='table-responsive'></div>" );
						
							/*if(CMSCom.deviceFn.isMobile.any()){
								$("#owner-rpt-print").hide();
								$("#owner-rpt-export").hide();
							}else{
								$("#owner-rpt-print").show();
								$("#owner-rpt-export").show();
							}*/
							$("#owner-rpt-print").show();
							$("#owner-rpt-export").show();
					
						},100);
					    return jsonRes;		
			    	}else{
			    		return [];
			    	}
			    }			    
			  },
			  destroy: true,
			  "aaSorting": [[ 1, "desc" ]],			
			  'processing': false,
			  'serverSide': false,
			//  'bLengthChange': false, 
			  'fnInitComplete' :function(){
					setTimeout(function(){
						$("#goatProdReport" ).wrapAll( "<div class='table-responsive'></div>" );	
						},100);
			  },
			  "fnRowCallback": function(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
			        /* push this row of data to currData array*/
			       // console.log(aData);

			    }
			});
			var oSettings = CMSOwnerCons.dataTable["goatreport"].dtFn.fnSettings();
		     oSettings._iDisplayLength = 10;			     
		     CMSOwnerCons.dataTable["goatreport"].dtFn.fnSetColumnVis( 0, false );
		}			
	};
	
	
	var printReport=function(){
		
		var newWin= window.open("");
		   newWin.document.write(excelData);
		   newWin.document.close();
		   newWin.focus();
		   newWin.print();
		   newWin.close();

	};
	 var downloadFile = function(link,dataString, fileName) {       
	        link.href = dataString;
	        link.target = '_blank';
	        link.download = fileName;
	        document.body.appendChild(link);
	        link.click();
	    };

		
		var downloadFileForIE = function(type,dataString, fileName) {
		    var blob = new Blob([dataString], {
		            "type": type
		    });
		    navigator.msSaveBlob(blob, fileName);
		};
		
		
	var exportReport=function(){
		


        var uri = 'data:application/vnd.ms-excel;base64,';
        var template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body>{table}</body></html>'; 
        var base64 = function(s) {
            return window.btoa(unescape(encodeURIComponent(s)))
        };

        var format = function(s, c) {
            return s.replace(/{(\w+)}/g, function(m, p) {
                return c[p];
            })
        };



        var ctx = {
            worksheet : 'Worksheet',
            table : excelData
        }

		if(CMSCom.deviceFn.isIE()){
			 downloadFileForIE("text/csv;charset=utf-8;",excelData, "export.xls");
		}else{
			 var link = document.createElement("a");
		     link.download = "export.xls";
		     link.href = uri + base64(format(template, ctx));
		     link.target = '_blank';
		     document.body.appendChild(link);
		     link.click();
		}
       
	};	
	
	var downloadExcel=function(url){
		
		 var link = document.createElement("a");
	     //link.download = "export.xls";
	     link.href = "../../"+url;
	     link.target = '_blank';
	     document.body.appendChild(link);
	     link.click();
	};
	
	var exportFunction = function(){
		
		var type=CMSCom.URL.queryParam('type');
		fromdt=CMSCom.URL.queryParam('fromdt'),
		toDt=CMSCom.URL.queryParam('toDt'),
		farmId=CMSCom.URL.queryParam('farmId');	
		cattleId=CMSCom.URL.queryParam('cattleId');	
		
		var urlObj=CMSOwnerCons.urls_info["exportFunction"],
	 	reqData=jQuery.extend({}, CMSOwnerCom.getInitReqData());
		
		query={};
		
		query["type"]=type;
		query["fromDt"]=CMSCom.dateFn.cusDateServerFormat(fromdt);
		query["toDt"]=CMSCom.dateFn.cusDateServerFormat(toDt);
		query["farmId"]=farmId*1;
		
		reqData["reqData"]=query;	
		
		reqData=JSON.stringify(reqData);
		
		CMSCom.ajaxService.sentJsonData(urlObj,reqData).done(function(jsonResData) {
			if(CMSCom.exceptionHandler.isLoginSessionExpire(jsonResData)){
				if(jsonResData.status==="1"){
					var resData=JSON.parse(jsonResData.resData);
					downloadExcel("uploads/"+resData);
				}else{
					
				}
				
			}	
		}).fail(CMSCom.exceptionHandler.ajaxFailure);
	};
	
	
	// This is owner report event handlers 
	var eventHandler=function(){		
		$("#owner-rpt-print").off("click").on("click",printReport);
		//Disabling this temporarily
		//$("#owner-rpt-export").off("click").on("click",exportReport);
		//Trying with new method using java
		$("#owner-rpt-export").off("click").on("click",exportFunction);
	};
		
	// This is to load owner reports	
	var show=function(){ 			
		var type=CMSCom.URL.queryParam('type');
		
		  $(".reportTable").addClass("hidden");
			$("#owner-rpt-print").hide();
			$("#owner-rpt-export").hide();
		   if("goat-report"===type){
			   
			  $("#report-title").html("Goat Report Details");
			  $("#goatProdReport").removeClass("hidden");
			  
			  var fromdt=CMSCom.URL.queryParam('fromdt'),
				toDt=CMSCom.URL.queryParam('toDt'),
				farmId=CMSCom.URL.queryParam('farmId');			
				loadGoatReport(fromdt,toDt,farmId);
			  
			  
		  } else if("milk-production"===type){
			  $("#report-title").html("Milk Production Report Details");
			  $("#milkProdReport").removeClass("hidden");
			    
			var fromdt=CMSCom.URL.queryParam('fromdt'),
			toDt=CMSCom.URL.queryParam('toDt'),
			farmId=CMSCom.URL.queryParam('farmId'),
			cattleId=CMSCom.URL.queryParam('cattleId');			
			loadMilkProduction(fromdt,toDt,farmId,cattleId);
		}
		
		eventHandler();
	};

	return {
		show:show		
	};	
	
})();



