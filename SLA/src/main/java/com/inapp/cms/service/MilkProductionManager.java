package com.inapp.cms.service;

import java.util.List;

import com.inapp.cms.entity.BreedKidsEntity;
import com.inapp.cms.entity.MilkProductionEntity;

public interface MilkProductionManager {

	MilkProductionEntity saveMilkProduction(MilkProductionEntity objMilkProductionEntity);
	BreedKidsEntity saveBreedKids(BreedKidsEntity objBreedKidsEntity);
	List<MilkProductionEntity> getMilkProductionDetails(MilkProductionEntity objMilkProductionEntity);
	int deleteMilkProduction(MilkProductionEntity objMilkProductionEntity);
	List<MilkProductionEntity> getMilkProductionDetailsByOwner(int userId);
	List<MilkProductionEntity> getMilkProductionById(
			MilkProductionEntity objMilkProductionEntity);
	List<MilkProductionEntity> getMilkProductionDetailsByCattleId(int cattle_id);
}
