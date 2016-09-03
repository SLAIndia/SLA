package com.inapp.cms.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.inapp.cms.entity.CattleEntity;
import com.inapp.cms.entity.CattleImageEntity;
import com.inapp.cms.entity.FarmEntity;

public interface CattleDAO {

	List<CattleEntity> getCattleDetailsByFarm(int farm_id);
	
	List<HashMap<String, Object>> getCattleDetailsOfAssignedFarm( int u_id);

	HashMap<String, Object> saveCattleDetails(CattleEntity objCattleEntity);

	CattleEntity getCattleDetails(CattleEntity objCattleEntity);

	int saveImageUrl(ArrayList<CattleImageEntity> objCattleImgList);

	List<CattleImageEntity> getImageUrls(int cattleid);

	int deleteImageUrl(int cattleId);

	List<HashMap<String, Object>> getParentByFarmBithdt(CattleEntity objCattleEntity);

	List<HashMap<String, Object>> getCattleDetailsOfAssignedFarmAndBreedDt(
			int u_id, String breeddt);

	FarmEntity getFarm(String cattle_farm_code);

	CattleEntity getParentCattle(String ear_tag_id);

	String generateEarTag(String farmcode);

	void updateDeleteStatus(String[] deletedImages, int cattleId);

	long getImageCount(int cattleId);

}
