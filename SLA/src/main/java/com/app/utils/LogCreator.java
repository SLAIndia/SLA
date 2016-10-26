package com.app.utils;
/**
 * @author Jinesh George
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LogCreator {
	private static final Logger logger = Logger.getLogger(LogCreator.class);

	public static boolean FileCreater(String id, String user, String operation,
			String message, String status, String logData) {
		DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
		Date time = Common.getCurrentGMTTimestamp();
		String time11 = timeFormat.format(time);
		String res = "";
		if (status.equals("0"))
			res = "Failed";
		else
			res = "Success";
		boolean flag = false;
		String htmlData = "";
		htmlData += id + "##" + user + "##" + operation + "##" + message + "##"
				+ status + "##" + time11 + "##" + res + "##" + logData;
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		String dt11 = dateFormat.format(date);
		String path = "";
		Properties prop = new Properties();
		try {
			// load a properties file
			prop.load(EmailUser.class.getClassLoader().getResourceAsStream(
					"config.properties"));
			path = prop.getProperty("IFYNDER_LOG_PATH");
		} catch (IOException ex) {
			logger.info("Error  while geting email password " + ex.getMessage());
		}
		File Folder = new File(path);
		if (!Folder.exists())
			Folder.mkdir();
		File file = new File(path + dt11 + ".txt");
		BufferedWriter bw = null;
		if (file.exists()) {
			BufferedReader br = null;

			try {
				htmlData = "";
				bw = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(file, true), "UTF-8"));
				bw.newLine();
				htmlData += id + "##" + user + "##" + operation + "##"
						+ message + "##" + status + "##" + time11 + "##" + res
						+ "##" + logData;
				bw.write(htmlData);
				flag = true;

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (br != null)
						br.close();
					bw.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}

		try {

			if (!file.exists()) {
				flag = file.createNewFile();
				bw = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(file), "UTF-8"));
				bw.write(htmlData);

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return flag;
	}
	
	
	/*private String generateLogTable(HttpServletRequest request, HttpSession session, String filterStr) throws ApplicationException {
        String jsonObject = "";
        String jsonObjectCore = "";
        session.setAttribute("logList",DivisionManager.getInstance().loadAllDivisions());
        String languageCode = new DWRHandler().getLanguageCode(session);
        try {
                
                String path=PropReader.getInstance().getProperty("SENK_LOG_PATH");
                File folder = new File(path+"\\WEB-INF\\Log");
                String files;
                ArrayList<LinkedHashMap<String, String>> fileList = new ArrayList<LinkedHashMap<String, String>>();
                LinkedHashMap<String,String> fileMap = null;
                if(folder.exists()){
                File[] listOfFiles = folder.listFiles();
                for (int i = 0; i < listOfFiles.length; i++)
                  {
                
                   if (listOfFiles[i].isFile())
                   {
                   fileMap = new LinkedHashMap<String, String>();
                   files = listOfFiles[i].getName();
                   fileMap.put("filename", files);
                   fileMap.put("filepath", listOfFiles[i].getPath());
                   fileList.add(fileMap);
                  
                   }
                  }
                }
                
                if (null != session.getAttribute("logList")) {
                        jsonObject = "{\"clearLbl\":\""+ChangeLanguage.getLabel("Clear", languageCode)+"\"," +
                                        "\"previous\":\""+ChangeLanguage.getLabel("previouslogs", languageCode)+"\"," +
                                              "\"logFile\":\""+ChangeLanguage.getLabel("logrecords", languageCode)+"\"," +
                                              "\"filterStr\":\""+filterStr+"\"," +
                                              " \"values\": [" ;
                        for (int i = 0; i < fileList.size(); i++) {
                                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                                map = fileList.get(i);
                                if("".equals(jsonObjectCore)) {
                                        jsonObjectCore += " {\"logHeading\": \""+map.get("filename")+"\",\"logData\": \""+map.get("filename")+"\",\"logFileName\": \""+map.get("filename")+""+"\"} ";
                                } else {
                                        jsonObjectCore += ", {\"logHeading\": \""+map.get("filename")+"\",\"logData\": \""+map.get("filename")+"\",\"logFileName\": \""+map.get("filename")+""+"\"} ";
                                }
                                
                        }
                        jsonObject += jsonObjectCore+ "]}";
                }
        } catch (Exception e) {
                e.printStackTrace();
        }
        return jsonObject;                
}

private String generateLogData(HttpServletRequest request,String fileName, HttpSession session, String filterStr) throws ApplicationException {
        String jsonObject = "";
        String jsonObjectCore = "";
        session.setAttribute("logList",DivisionManager.getInstance().loadAllDivisions());
        String languageCode = new DWRHandler().getLanguageCode(session);
        String USER_ROLE_ID = request.getSession().getAttribute("USER_ROLE_ID") != null ? request.getSession()
                        .getAttribute("USER_ROLE_ID").toString() : "";
        try {
                if(fileName.equals(""))
                {
                        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        Date date = new Date();
                        fileName = dateFormat.format(date)+".txt";
                }
                String path=PropReader.getInstance().getProperty("SENK_LOG_PATH");
                File folder = new File(path+"\\WEB-INF\\Log");
                String files;
                ArrayList<LinkedHashMap<String, String>> fileList = new ArrayList<LinkedHashMap<String, String>>();
                LinkedHashMap<String,String> fileMap = null;
                if(folder.exists()){
                File file1 = new File(path+"\\WEB-INF\\Log\\"+fileName);
        
                   if (file1.isFile() && file1.exists())
                   {BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file1),"UTF-8"));
                   try {
                                String str;
                            while ((str = in.readLine()) != null)
                            {String logData = "";
                                    String[] head = str.split("##");
                                    if(!USER_ROLE_ID.equals("2")){
                                    if(request.getSession().getAttribute("AUTHORISED_USER_NAME").toString().equals(head[0])){
                                    fileMap = new LinkedHashMap<String, String>();
                                        fileMap.put("user", head[0]);
                                        fileMap.put("operation", ChangeLanguage.getLabel(head[1],languageCode));
                                        fileMap.put("newworknumber", head[2]);
                                        fileMap.put("time", head[3]);
                                        fileMap.put("result", head[4]);
                                        fileMap.put("id", head[5].trim());
                                        if(!head[5].equals("deleted")){
                                        if(head[1].equals("edited"))
                                                logData+=ChangeLanguage.getLabel("edited",languageCode)+" "+ChangeLanguage.getLabel("WorkNumber",languageCode)+" - "+head[11];
                                        else if(head[1].equals("copiedandcreated"))
                                                logData+=ChangeLanguage.getLabel("copiedfrom",languageCode)+" "+ChangeLanguage.getLabel("WorkNumber",languageCode)+" - "+head[11];
                                        else if(head[1].equals("deleted"))
                                                logData+=ChangeLanguage.getLabel("deleted",languageCode)+" "+ChangeLanguage.getLabel("WorkNumber",languageCode)+" - "+head[11];
                                        else if(head[1].equals("created"))
                                                logData+=ChangeLanguage.getLabel("created",languageCode)+" "+ChangeLanguage.getLabel("WorkNumber",languageCode)+" - "+head[2];
                                        
                                        logData += ", "+ChangeLanguage.getLabel("NameofWork",languageCode)+" - "
                                        +head[6]+", "+ChangeLanguage.getLabel("FiscalYearofWork",languageCode)+" - "
                                        +head[7]+", "+ChangeLanguage.getLabel("TypeofWork",languageCode)+" - "
                                        +head[8]+", "+ChangeLanguage.getLabel("WorkStatus",languageCode)+" - "
                                        +head[9]+", "+ChangeLanguage.getLabel("Division", languageCode)+" - "+head[10];
                                        
                                        }
                                        fileMap.put("logData", logData);
                                        fileList.add(fileMap);
                                    }
                                    }
                                    else if(USER_ROLE_ID.equals("2"))
                                    {
                                                fileMap = new LinkedHashMap<String, String>();
                                                        fileMap.put("user", head[0]);
                                                        fileMap.put("operation", ChangeLanguage.getLabel(head[1],languageCode));
                                                        fileMap.put("newworknumber", head[2]);
                                                        fileMap.put("time", head[3]);
                                                        fileMap.put("result", head[4]);
                                                        fileMap.put("id", head[5].trim());
                                                        if(!head[5].equals("deleted")){
                                                        if(head[1].equals("edited"))
                                                                logData+=ChangeLanguage.getLabel("edited",languageCode)+" "+ChangeLanguage.getLabel("WorkNumber",languageCode)+" - "+head[11];
                                                        else if(head[1].equals("copiedandcreated"))
                                                                logData+=ChangeLanguage.getLabel("copiedfrom",languageCode)+" "+ChangeLanguage.getLabel("WorkNumber",languageCode)+" - "+head[11];
                                                        else if(head[1].equals("deleted"))
                                                                logData+=ChangeLanguage.getLabel("deleted",languageCode)+" "+ChangeLanguage.getLabel("WorkNumber",languageCode)+" - "+head[11];
                                                        else if(head[1].equals("created"))
                                                                logData+=ChangeLanguage.getLabel("created",languageCode)+" "+ChangeLanguage.getLabel("WorkNumber",languageCode)+" - "+head[2];
                                                        
                                                        logData += ", "+ChangeLanguage.getLabel("NameofWork",languageCode)+" - "
                                                        +head[6]+", "+ChangeLanguage.getLabel("FiscalYearofWork",languageCode)+" - "
                                                        +head[7]+", "+ChangeLanguage.getLabel("TypeofWork",languageCode)+" - "
                                                        +head[8]+", "+ChangeLanguage.getLabel("WorkStatus",languageCode)+" - "
                                                        +head[9]+", "+ChangeLanguage.getLabel("Division", languageCode)+" - "
                                                        +head[10]+", "+ChangeLanguage.getLabel(head[1],languageCode)+" "+ChangeLanguage.getLabel("by",languageCode)+" - "+head[0];
                                                        
                                                        }
                                                        fileMap.put("logData", logData);
                                                        fileList.add(fileMap);
                                                    
                                    }
                            }
                            
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                   finally{
                           in.close();
                   }
                  
                   }
                  
                }
                
                if (null != session.getAttribute("logList")) {
                        jsonObject = "{\"clearLbl\":\""+ChangeLanguage.getLabel("Clear", languageCode)+"\"," +
                                        "\"previous\":\""+ChangeLanguage.getLabel("previouslogs", languageCode)+"\"," +
                                                 "\"ids\":\""+"Id"+"\"," +
                                              "\"users\":\""+"User"+"\"," +
                                              "\"operations\":\""+ChangeLanguage.getLabel("operation", languageCode)+"\"," +
                                              "\"newworknumbers\":\""+ChangeLanguage.getLabel("WorkNumber", languageCode)+"\"," +
                                              "\"date\":\""+ChangeLanguage.getLabel("time", languageCode)+"\"," +
                                              "\"logDatas\":\""+ChangeLanguage.getLabel("log", languageCode)+"\"," +
                                              "\"filterStr\":\""+filterStr+"\"," +
                                              " \"values\": [" ;
                        for (int i = 0; i < fileList.size(); i++) {
                                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                                map = fileList.get(i);
                                if("".equals(jsonObjectCore)) {
                                        jsonObjectCore += " {\"id\": \""+map.get("id")+"\",\"user\": \""+map.get("user")+"\",\"operation\": \""+map.get("operation")+"\",\"newworknumber\": \""+map.get("newworknumber")+""+"\",\"time\": \""+map.get("time")+"\",\"result\": \""+map.get("result")+"\",\"logData\": \""+map.get("logData")+"\"} ";
                                } else {
                                        jsonObjectCore += ", {\"id\": \""+map.get("id")+"\",\"user\": \""+map.get("user")+"\",\"operation\": \""+map.get("operation")+"\",\"newworknumber\": \""+map.get("newworknumber")+""+"\",\"time\": \""+map.get("time")+"\",\"result\": \""+map.get("result")+"\",\"logData\": \""+map.get("logData")+"\"} ";
                                }
                                
                        }
                        jsonObject += jsonObjectCore+ "]}";
                }
        } catch (Exception e) {
                e.printStackTrace();
        }
        return jsonObject;                
}*/
	
	
	
	
}
