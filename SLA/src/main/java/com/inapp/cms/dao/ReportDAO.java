package com.inapp.cms.dao;

import java.util.List;

import com.inapp.cms.entity.CattleEntity;
import com.inapp.cms.entity.MilkProductionEntity;
import com.inapp.cms.entity.ReportEntity;

public interface ReportDAO {

	List<MilkProductionEntity> getMilkProdData(ReportEntity reportObj);

	List<Object[]> getHealthCareData(ReportEntity reportObj);

	List<CattleEntity> getCattleDtls(ReportEntity reportObj);

}
