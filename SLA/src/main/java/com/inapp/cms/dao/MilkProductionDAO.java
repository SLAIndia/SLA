package com.inapp.cms.dao;

import java.util.List;

import com.inapp.cms.entity.BreedKidsEntity;
import com.inapp.cms.entity.MilkProductionEntity;

public interface MilkProductionDAO {

	public MilkProductionEntity saveMilkProduction(MilkProductionEntity objMilkProductionEntity);
	public BreedKidsEntity saveBreedKids(BreedKidsEntity objBreedKidsEntity);
	public List<MilkProductionEntity> getMilkProductionDetails(MilkProductionEntity objMilkProductionEntity);
	public int deleteMilkProduction(MilkProductionEntity objMilkProductionEntity);
	public List<MilkProductionEntity> getMilkProductionDetailsByOwner(int userId);
	public List<MilkProductionEntity> getMilkProductionById(
			MilkProductionEntity objMilkProductionEntity);
	public List<MilkProductionEntity> getMilkProductionDetailsByCattleId(
			int cattle_id);
}
