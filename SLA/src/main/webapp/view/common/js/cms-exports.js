
var CMSExports = (function() {

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
	var exportToCsv = function(dataTypeTitles,dataTypeKeys,exportData,fileName) {
		var ua = window.navigator.userAgent,
            msie = ua.indexOf("MSIE "),
            link = document.createElement('a'),
            newLine=msie>0?"\n":"%0A",    
            dataString = dataTypeTitles.join(',') + newLine,
            dataTypeKeysLen = dataTypeKeys.length;        
        
        for (key in exportData) {
            var eachData = exportData[key],eachRows = "";                    

            for (var i = 0; i < dataTypeKeysLen; i++) {
                eachRows += eachData[dataTypeKeys[i]] + ",";
            }
            eachRows = eachRows.substring(0, eachRows.lastIndexOf(","));
            dataString += eachRows +  newLine;
        }        
       
        if (msie > 0){
             downloadFileForIE("text/csv;charset=utf-8;",dataString, fileName);             
        }else{
             downloadFile(link,'data:attachment/csv,' + dataString, fileName);
        }	      
	};
	var exportToExcel = function(dataTypeTitles,dataTypeKeys,exportData,fileName) {
		
	}
    var exportTo = function(cellId,type) {
        var dataType = GLOBAL_Data.exportInfo.dataType[0],
                exportFileSubName = GLOBAL_Data.exportInfo.dataType[1],
                dataTypeTitles = GLOBAL_Data.exportInfo[dataType].title,
                dataTypeKeys = GLOBAL_Data.exportInfo[dataType].keys,                
                fileName = GLOBAL_Data.exportInfo[dataType].fileName + exportFileSubName+cellId + ".csv";

        if(type==="csv"){
            exportToCsv(dataTypeTitles,dataTypeKeys,GLOBAL_Data.exportInfo.data,fileName);
        }
    };

    return {
        exportTo: exportTo
    };

})();


