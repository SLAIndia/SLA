package com.inapp.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inapp.cms.dao.ReportDAO;
import com.inapp.cms.entity.CattleEntity;
import com.inapp.cms.entity.MilkProductionEntity;
import com.inapp.cms.entity.ReportEntity;
import com.inapp.cms.utils.ServiceConstants;

@Service(ServiceConstants.REPORT_MANAGER)
public class ReportManagerImpl implements ReportManager{

	@Autowired
	private ReportDAO reportDAO;
	
	@Override
	public List<MilkProductionEntity> getMilkProdData(ReportEntity reportObj) {
		return reportDAO.getMilkProdData(reportObj);
	}

	@Override
	public List<Object[]> getHealthCareData(ReportEntity reportObj) {
		return reportDAO.getHealthCareData(reportObj);
	}

	@Override
	public List<CattleEntity> getCattleDtls(ReportEntity reportObj) {
		return reportDAO.getCattleDtls(reportObj);
	}

}
