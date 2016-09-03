package com.inapp.cms.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.inapp.cms.entity.CattleImageEntity;

public class FileUpload {
	
	private static final Logger logger = Logger.getLogger(FileUpload.class);
	
	public static ArrayList<CattleImageEntity> uploadFile(MultipartFile[] files, String path,
			String name,int cattleId,String cattlePrimaryImg,String[] updateImages,long count) {
		Properties prop = new Properties();
		
		ArrayList<CattleImageEntity> objCattleImgList = new ArrayList<CattleImageEntity>();
		CattleImageEntity objCattleImg = null;
		try {
			prop.load(FileUpload.class.getClassLoader().getResourceAsStream(
					"config.properties"));
			String file_path = prop.getProperty("image_filePath");
		
			/*int n = 0;
			if (null != updateImages && updateImages.length > 0) {
				logger.info("updateImages" + updateImages.length + "---");
				for (int j = 0; j < updateImages.length; j++) {
					File serverFile = new File(file_path + File.separator
							+ path + File.separator + updateImages[j]);
					if (serverFile.exists() && j == (updateImages.length - 1)) {
						logger.info("serverfile in exist checking "
								+ serverFile);
						String[] etNames = serverFile.getName().split("\\.")[0]
								.split("_");
						String etName = etNames[etNames.length - 1];
						logger.info("before n is ?"
								+ Integer.parseInt(etName));
						n = Integer.parseInt(etName) + 1;
						logger.info("n is : " + n);
					}

					objCattleImg = new CattleImageEntity();
					objCattleImg.setCattleimagecattleid(cattleId);
					objCattleImg.setCattleimageurl(updateImages[j]);
					objCattleImg.setCattle_ear_tag(name);
					objCattleImgList.add(objCattleImg);
				}
			}*/
			if (null != files && files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					MultipartFile file = files[i];
					logger.info("file name original "
							+ file.getOriginalFilename());
					objCattleImg = new CattleImageEntity();
					byte[] bytes = file.getBytes();
					String fname = file.getOriginalFilename();
					String[] fnLen = fname.split("\\.");
					logger.info("fn length " + fnLen.length);
					String ext = fnLen[fnLen.length - 1];
					logger.info("ext " + ext);
					// Creating the directory to store file

					File dir = new File(file_path + File.separator + path
							+ File.separator + name.split("/")[0]);
					if (!dir.exists())
						dir.mkdirs();

					// Create the file on server
					String serverFilename = name + "_" + (count + i) + "." + ext;
					File serverFile = new File(file_path + File.separator
							+ path + File.separator + serverFilename);

					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
					if (cattlePrimaryImg.equals(fname)) {
						objCattleImg.setCattleimageisprimary(true);
					}
					objCattleImg.setCattleimagecattleid(cattleId);
					objCattleImg.setCattle_ear_tag(name);
					objCattleImg.setCattleimageurl(serverFilename);
					objCattleImg.setImgstatus(1);
					objCattleImgList.add(objCattleImg);

					logger.info("Server File Location="
							+ serverFile.getAbsolutePath());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in file upload " + e.getMessage());
		}

		return objCattleImgList;
	}

	public static ArrayList<CattleImageEntity> uploadFile(String name,
			int cattleId, MultipartFile[] files) {
		ArrayList<CattleImageEntity> objCattleImgList = new ArrayList<CattleImageEntity>();
		CattleImageEntity objCattleImg = null;
		Properties prop = new Properties();
		String path = "images";
		try {
			prop.load(FileUpload.class.getClassLoader().getResourceAsStream(
					"config.properties"));
			String file_path = prop.getProperty("image_filePath");

			if (null != files && files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					MultipartFile file = files[i];
					logger.info("file name original "
							+ file.getOriginalFilename());
					objCattleImg = new CattleImageEntity();
					byte[] bytes = file.getBytes();
					String fname = file.getOriginalFilename();
					
					
					// Creating the directory to store file

					File dir = new File(file_path + File.separator + path
							+ File.separator + name.split("/")[0]);
					if (!dir.exists())
						dir.mkdirs();

					// Create the file on server
					File serverFile = new File(dir +File.separator+ fname);
					
					//if already file exist in db ---> insertion in db blocks now but
					// file will change in serverpath.
					if(!serverFile.exists()){  
						objCattleImg.setCattle_ear_tag(name);
						objCattleImg.setCattleimagecattleid(cattleId);
						objCattleImg.setCattleimageurl(name.split("/")[0]+File.separator+fname);
						objCattleImgList.add(objCattleImg);
					}
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
					

					logger.info("Server File Location="
							+ serverFile.getAbsolutePath());
				}
			}
		} catch (Exception e) {
			

		}

		return objCattleImgList;
	}
	

}
