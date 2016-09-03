package com.inapp.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.utils.ServiceConstants;
import com.inapp.cms.dao.MilkProductionDAO;
import com.inapp.cms.entity.BreedKidsEntity;
import com.inapp.cms.entity.MilkProductionEntity;

@Service(ServiceConstants.MILKPRODUCTION_MANAGER)
public class MilkProductionManagerImpl implements MilkProductionManager{

	@Autowired
    private MilkProductionDAO milkproductionDAO;
	
	@Override
	public MilkProductionEntity saveMilkProduction(MilkProductionEntity objMilkProductionEntity) {
		return milkproductionDAO.saveMilkProduction(objMilkProductionEntity);
	}

	@Override
	public BreedKidsEntity saveBreedKids(BreedKidsEntity objBreedKidsEntity) {
		return milkproductionDAO.saveBreedKids(objBreedKidsEntity);
	}
	
	@Override
	public List<MilkProductionEntity> getMilkProductionDetails(MilkProductionEntity objMilkProductionEntity) {
		return milkproductionDAO.getMilkProductionDetails(objMilkProductionEntity);
	}

	@Override
	public int deleteMilkProduction(MilkProductionEntity objMilkProductionEntity) {
		return milkproductionDAO.deleteMilkProduction(objMilkProductionEntity);
	}

	@Override
	public List<MilkProductionEntity> getMilkProductionDetailsByOwner(int userId) {
		return milkproductionDAO.getMilkProductionDetailsByOwner(userId);
	}
	
	@Override
	public List<MilkProductionEntity> getMilkProductionById(MilkProductionEntity objMilkProductionEntity) {
		return milkproductionDAO.getMilkProductionById(objMilkProductionEntity);
	}
	
	@Override
	public List<MilkProductionEntity> getMilkProductionDetailsByCattleId(int cattle_id) {
		return milkproductionDAO.getMilkProductionDetailsByCattleId(cattle_id);
	}

}
