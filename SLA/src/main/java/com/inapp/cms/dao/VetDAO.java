package com.inapp.cms.dao;

import java.util.HashMap;
import java.util.List;

import com.inapp.cms.entity.UserEntity;
import com.inapp.cms.entity.VetEntity;

public interface VetDAO {

	public UserEntity save(UserEntity vetUserEntity) throws Exception;

	public VetEntity saveVetDetails(VetEntity vetEntity)throws Exception;

	public List<HashMap<String, Object>> getAllVetByFnameOrLname(String code);

	public int saveVetFarm(VetEntity objVetEntity); 
	public int approveVet(VetEntity objVetEntity);
	public VetEntity getVetDetails(int userId); 

}
