package com.app.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
/**
 * @author Jinesh George
 */
public class HtmlReader {
	
	private static final Logger logger = Logger.getLogger(HtmlReader.class);
    public static String htmlReader(String toName, String toEmail, String option, String value) {
        		String emailcontentpath = "";
        		String msgText = "";
        		BufferedReader in = null;
        		if(toName.trim().equals(""))
        		{
        			toName = toEmail.split("@")[0];
        		}
               try {
                    StringBuilder contentBuilder = new StringBuilder();
                    try {
                    	emailcontentpath = HtmlReader.class.getClassLoader().getResource("email-template.html").getPath();
                         in = new BufferedReader(new FileReader(emailcontentpath.toString()));
                        String str;
                        while ((str = in.readLine()) != null) {
                            contentBuilder.append(str);
                        }
                       if(option.equals("password"))
                        {
                    	   msgText = "Please reset your password using this code : <b>"+value+"</b>";
                    	   msgText = contentBuilder.toString()
            						.replace("subject", "")
            						.replace("_user", toName)
            						.replace("_message", msgText)
            						.replace("test@gms.com", toEmail);
                        }
                        
                    } catch (IOException e) {
                    	e.printStackTrace();
                    }
                    finally{
                    	if(in!=null){
                    	in.close();
                    	}
                    }
                    
                } catch (Exception ex) {
                	logger.info("Error  while geting email password " + ex.getMessage());
                }

        return msgText;
    }
}
