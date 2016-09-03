package com.app.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

public class AppZip{
	
    private static List<String> fileList;
    private static  String OUTPUT_ZIP_FILE ;
    private static  String SOURCE_FOLDER ;
    private static final Logger logger = Logger.getLogger(AppZip.class);
   
   
 
    public static void zipAllFiles(String zipFolder,String srcFolder){
    	fileList = new ArrayList<String>();
    	OUTPUT_ZIP_FILE = zipFolder;
    	SOURCE_FOLDER = srcFolder;
    	File f = new File(OUTPUT_ZIP_FILE);
    	if(f.exists()){
    		f.delete();
    	}
    	logger.info("source path"+SOURCE_FOLDER+"---OUTPUT_ZIP_FILE"+OUTPUT_ZIP_FILE);
    	generateFileList(new File(SOURCE_FOLDER));
    	zipIt(OUTPUT_ZIP_FILE);
    }
    
    /**
     * Zip it
     * @param zipFile output ZIP file location
     */
    public static void zipIt(String zipFile){
 
     byte[] buffer = new byte[1024];
 
     try{
 
    	FileOutputStream fos = new FileOutputStream(zipFile);
    	ZipOutputStream zos = new ZipOutputStream(fos);
 
    	logger.info("Output to Zip : " + zipFile);
    	if(fileList.size() == 0){
    		File folder = new File(SOURCE_FOLDER + File.separator + "noImg");
    		folder.mkdir();
    		File file = new File(folder,"noImg.jpg");
    		file.createNewFile();
    		fileList.add(generateZipEntry(file.getAbsoluteFile().toString()));
    	}
 
    	for(String file : fileList){
 
    		logger.info("File Added : " + file);
    		ZipEntry ze= new ZipEntry(file);
        	zos.putNextEntry(ze);
 
        	FileInputStream in = 
                       new FileInputStream(SOURCE_FOLDER + File.separator + file);
 
        	int len;
        	while ((len = in.read(buffer)) > 0) {
        		zos.write(buffer, 0, len);
        	}
 
        	in.close();
    	}
 
    	zos.closeEntry();
    	//remember close it
    	zos.close();
 
    	logger.info("Done");
    }catch(IOException ex){
       logger.error("Error in zip method"+ex.getMessage());
    }
   }
 
    /**
     * Traverse a directory and get all files,
     * and add the file into fileList  
     * @param node file or directory
     */
    public static void generateFileList(File node){
 
    	//add file only
	if(node.isFile()){
		fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
	}
 
	if(node.isDirectory()){
		String[] subNote = node.list();
		for(String filename : subNote){
			generateFileList(new File(node, filename));
		}
	}
 
    }
 
    /**
     * Format the file path for zip
     * @param file file path
     * @return Formatted file path
     */
    private static String generateZipEntry(String file){
    	logger.info("file substring "+file.substring(SOURCE_FOLDER.length()+1, file.length()));
    	return file.substring(SOURCE_FOLDER.length()+1, file.length());
    }
}