package com.inapp.cms.service;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.utils.ServiceConstants;
import com.inapp.cms.dao.VetDAO;
import com.inapp.cms.entity.UserEntity;
import com.inapp.cms.entity.VetEntity;

@Service(ServiceConstants.VET_MANAGER)
public class VetManagerImpl implements VetManager {

	private static final Logger logger = Logger.getLogger(VetManagerImpl.class);
	
	@Autowired
	private VetDAO vetDavo;
	
	@Override
	public UserEntity save(UserEntity vetUserEntity) throws Exception {
		return vetDavo.save(vetUserEntity);
	}

	@Override
	public VetEntity saveVetDetails(VetEntity vetEntity) throws Exception {		
		return vetDavo.saveVetDetails(vetEntity);
	}

	@Override
	public VetEntity getVetDetails(int userId) {		
		return vetDavo.getVetDetails(userId);
	}

	
	@Override
	public List<HashMap<String, Object>> getAllVetByFnameOrLname(String code) {
		return vetDavo.getAllVetByFnameOrLname(code);
	}

	@Override
	public int saveVetFarm(VetEntity objVetEntity) {
		return vetDavo.saveVetFarm(objVetEntity);
	}
	@Override
	public int approveVet(VetEntity objVetEntity) {
		return vetDavo.approveVet(objVetEntity);
	}

}
