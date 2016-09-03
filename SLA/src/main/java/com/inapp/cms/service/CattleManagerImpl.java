package com.inapp.cms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inapp.cms.dao.CattleDAO;
import com.inapp.cms.entity.CattleEntity;
import com.inapp.cms.entity.CattleImageEntity;
import com.inapp.cms.entity.FarmEntity;
import com.inapp.cms.utils.ServiceConstants;

@Service(ServiceConstants.CATTLE_MANAGER)
public class CattleManagerImpl implements CattleManager {
	@Autowired
	CattleDAO cattleDAO ;

	@Override
	public List<CattleEntity> getCattleDetailsByFarm(int farm_id) {
		return cattleDAO.getCattleDetailsByFarm(farm_id);
	}
	
	@Override
	public List<HashMap<String, Object>> getCattleDetailsOfAssignedFarm(int u_id) {
		return cattleDAO.getCattleDetailsOfAssignedFarm(u_id);
	}

	@Override
	public List<HashMap<String, Object>> getCattleDetailsOfAssignedFarmAndBreedDt(int u_id, String breeddt) {
		return cattleDAO.getCattleDetailsOfAssignedFarmAndBreedDt(u_id, breeddt);
	}

	
	@Override
	public HashMap<String, Object> saveCattleDetails(CattleEntity objCattleEntity) {
		return cattleDAO.saveCattleDetails(objCattleEntity);
	}

	@Override
	public CattleEntity getCattleDetails(CattleEntity objCattleEntity) {
		return cattleDAO.getCattleDetails(objCattleEntity);
	}

	@Override
	public int saveImageUrl(ArrayList<CattleImageEntity> objCattleImgList) {

		return cattleDAO.saveImageUrl(objCattleImgList);
	}

	@Override
	public List<CattleImageEntity> getImageUrls(int cattleid) {
		return cattleDAO.getImageUrls(cattleid);
	}

	@Override
	public int deleteImageUrl(int cattleId) {
		return cattleDAO.deleteImageUrl(cattleId);
	}

	@Override
	public List<HashMap<String, Object>> getParentByFarmBithdt(CattleEntity objCattleEntity) {
		return cattleDAO.getParentByFarmBithdt(objCattleEntity);
	}

	@Override
	public FarmEntity getFarm(String cattle_farm_code) {
		return cattleDAO.getFarm(cattle_farm_code);
	}

	@Override
	public CattleEntity getParentCattle(String ear_tag_id) {
		return cattleDAO.getParentCattle(ear_tag_id);
	}
	
	@Override
	public String generateEarTag(String farmcode) {
		return cattleDAO.generateEarTag(farmcode);
	}

	@Override
	public void updateDeleteStatus(String[] deletedImages, int cattleId) {
		 cattleDAO.updateDeleteStatus(deletedImages,cattleId);
	}

	@Override
	public long getImageCount(int cattleId) {
		return cattleDAO.getImageCount(cattleId);
	}
	
}
