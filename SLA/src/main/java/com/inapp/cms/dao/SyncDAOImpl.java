package com.inapp.cms.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.inapp.cms.entity.SyncEntity;
import com.inapp.cms.utils.Common;
import com.inapp.cms.utils.RepositoryConstants;

@Repository(RepositoryConstants.SYNC_DAO)
public class SyncDAOImpl implements SyncDAO{
	private static final Logger logger = Logger.getLogger(SyncDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Integer> getFarmIdsAssignedByUserId(int userId,String role) {
		String sql = "";
		if(role.toUpperCase().equals("OWNER")){
			sql = "select linkEntity.farm_owner_link_farm_id from farm.farm_owner_link linkEntity inner join usermanagement.owner owner on(linkEntity.farm_owner_link_owner_id=owner_id) " +
					"inner join farm.farm farm on(farm_owner_link_farm_id=farm.farm_id) inner join usermanagement.user us on(owner.owner_user_id=us.user_id) where us.user_id= "+userId+" and farm.farm_status = true";
		}
		else if(role.toUpperCase().equals("VET")){
			sql = "select linkEntity.farm_vet_link_farm_id from farm.farm_vet_link linkEntity inner join usermanagement.vet vet on(linkEntity.farm_vet_link_vet_id=vet_id) " +
					"inner join farm.farm farm on(farm_vet_link_farm_id=farm.farm_id) inner join usermanagement.user us on(vet.vet_user_id=us.user_id) where us.user_id= "+userId+" and farm.farm_status = true";
		}
		
		List<Integer> resultList = null;
		try {
			Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql);
			resultList =  query.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getFarmsAssignedByUserId Method " + e.getMessage());
		}
		
	return resultList;
	
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@Transactional
	public List<HashMap<String, Object>> getSyncDetails(SyncEntity objSyncEntity, List<Integer> farmIdList,List<Integer> newFarmIdList) {
		List<HashMap<String, Object>> aliasToValueMapList = null;List<HashMap<String, Object>> singleDataList = null;
		String sql = "SELECT table_schema,table_name FROM information_schema.tables where table_schema not in ('information_schema','pg_catalog') and table_name != 'owner_desk'";
		if(objSyncEntity.getUsername().equals("defaultowner")&& farmIdList.size() == 0){
        	sql = "SELECT table_schema,table_name FROM information_schema.tables where table_schema not in ('information_schema','pg_catalog') and table_name in('user','role','owner')";
        }
		String tableSql = "";Query query = null; List<HashMap<String, Object>> finalList = new ArrayList<HashMap<String,Object>>();
		 HashMap<String, Object> obj = new HashMap<String, Object>();String priority = "";
		  try {
			  String farmId = "";int i=1;
			  for(int s : farmIdList){
				 if(i < farmIdList.size()){
				  farmId += s+",";}
				  else{
					  farmId += s; } 
				  i++;
			  }String newfarmId = "";
			  if(newFarmIdList!=null && newFarmIdList.size()>0){
				  int ii=1;
				  for(int s : newFarmIdList){
					 if(ii < newFarmIdList.size()){
						 newfarmId += s+",";}
					  else{
						  newfarmId += s; } 
					  ii++;
				  } 
			  }
			  logger.info("farm ids============="+farmId);
			  logger.info("new farm ids============="+newfarmId);
			   query = this.sessionFactory
	                    .getCurrentSession()
	                    .createSQLQuery(sql)
	                    .setResultTransformer(
	                            AliasToEntityMapResultTransformer.INSTANCE);
	           
	            aliasToValueMapList = query.list(); 
	            if(aliasToValueMapList != null && aliasToValueMapList.size()>0 && farmIdList.size()>0){
	            	String lastSyncdt = objSyncEntity.getLastSyncDt();
	            	if(newFarmIdList!=null && newFarmIdList.size()>0){
	            		objSyncEntity.setLastSyncDt("2011-01-01 00:00:00");
	            		obj = getDetailsList(aliasToValueMapList,objSyncEntity,tableSql,priority,newfarmId,singleDataList,obj); //get table entries after last sync date	
		            	finalList.add(obj);
	            	}
	            	objSyncEntity.setLastSyncDt(lastSyncdt);
	            	obj = getDetailsList(aliasToValueMapList,objSyncEntity,tableSql,priority,farmId,singleDataList,obj); //get table entries after last sync date	
	            	finalList.add(obj);
	            }else if(objSyncEntity.getUsername().equals("defaultowner")&& farmIdList.size() == 0){
	            	for(HashMap<String, Object> map : aliasToValueMapList)
	            	{
	            		tableSql = "select * from "+map.get("table_schema")+"."+map.get("table_name")+" where (created_dt > '"+objSyncEntity.getLastSyncDt()+"' or updated_dt > '"+objSyncEntity.getLastSyncDt()+"' or deleted_dt > '"+objSyncEntity.getLastSyncDt()+"')";
	            		if(map.get("table_name").equals("role")){
	            			priority = "1";
	            	}
	            		else if(map.get("table_name").equals("user")){
	            			priority = "2";
	            	}
	            		else if(map.get("table_name").equals("owner")){
	            			priority = "3";
	            	}
	            		query = this.sessionFactory
	    	                    .getCurrentSession()
	    	                    .createSQLQuery(tableSql)
	    	                    .setResultTransformer(
	    	                            AliasToEntityMapResultTransformer.INSTANCE);
	            		singleDataList =  query.list();
	            		if(singleDataList!=null && singleDataList.size()>0)
	            		{   HashMap<String, Object> singlemap = new HashMap<String, Object>();
	            		    singlemap.put(map.get("table_name")+"",singleDataList);
	            		    obj.put(priority.trim(), singlemap);
	            			
	            		}
	            	}finalList.add(obj);
	            }
		  }catch(Exception e){
			  e.printStackTrace();
			  logger.error("Error in getSyncDetails Method "+e.getMessage());
		  }
		return finalList;
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, Object> getDetailsList(
			List<HashMap<String, Object>> aliasToValueMapList,
			SyncEntity objSyncEntity, String tableSql, String priority, String farmId, List<HashMap<String, Object>> singleDataList, HashMap<String, Object> obj) {
		boolean alreadyAdded;
		for(HashMap<String, Object> map : aliasToValueMapList)
    	{  alreadyAdded = false;
    		tableSql = "select * from "+map.get("table_schema")+"."+map.get("table_name")+" where (created_dt > '"+objSyncEntity.getLastSyncDt()+"' or updated_dt > '"+objSyncEntity.getLastSyncDt()+"' or deleted_dt > '"+objSyncEntity.getLastSyncDt()+"')";
    		if(map.get("table_name").equals("role")){
    			priority = "1";alreadyAdded = true;
    	}
    		else if(map.get("table_name").equals("user")){
    			priority = "2";alreadyAdded = true;
    	}
    		else if(map.get("table_name").equals("surgery")){
    			priority = "3";alreadyAdded = true;
    	}
    		else if(map.get("table_name").equals("vaccination")){
    			priority = "4";alreadyAdded = true;
    	}
    		else if(map.get("table_name").equals("cattle_type")){
    			priority = "5";alreadyAdded = true;
        }
    		else if(map.get("table_name").equals("owner")){
    			priority = "6";alreadyAdded = true;
        }
    		else if(map.get("table_name").equals("vet")){
    			priority = "7";alreadyAdded = true;
    	}
    		else if(map.get("table_name").equals("farm")){
    			priority = "8";
    			//alreadyAdded = true;
    			tableSql = "select farm.* from farm.farm where farm_id in("+farmId+") and (farm.created_dt > '"+objSyncEntity.getLastSyncDt()+"' or farm.updated_dt > '"+objSyncEntity.getLastSyncDt()+"' or farm.deleted_dt > '"+objSyncEntity.getLastSyncDt()+"');";
    	}
    		
    		else if(map.get("table_name").equals("cattle")){
    			priority = "9";
    			tableSql = "select cattle1.*,farm.farm_code as cattle_farm_code, cattlebuck.cattle_ear_tag_id as buck_ear_tag_id,cattledoe.cattle_ear_tag_id as doe_ear_tag_id from farm.cattle cattle1 left join farm.cattle cattlebuck on(cattlebuck.cattle_id = cattle1.cattle_buck_id) left join farm.cattle cattledoe on (cattledoe.cattle_id = cattle1.cattle_doe_id) inner join farm.farm on(farm_id = cattle1.cattle_farm_id) where cattle1.cattle_farm_id in("+farmId+") and (cattle1.created_dt > '"+objSyncEntity.getLastSyncDt()+"' or cattle1.updated_dt > '"+objSyncEntity.getLastSyncDt()+"' or cattle1.deleted_dt > '"+objSyncEntity.getLastSyncDt()+"') order by cattle1.cattle_dob";
    		}
    		
    		else if(map.get("table_name").equals("cattle_image"))
    		{	priority = "10";
    			tableSql = "select cattle_image.* from farm.cattle_image inner join farm.cattle on(cattle_image_cattle_id = cattle_id) where cattle_farm_id in("+farmId+") and (cattle_image.created_dt > '"+objSyncEntity.getLastSyncDt()+"' or cattle_image.updated_dt > '"+objSyncEntity.getLastSyncDt()+"' or cattle_image.deleted_dt > '"+objSyncEntity.getLastSyncDt()+"')";	
    		}
    		else if(map.get("table_name").equals("farm_owner_link"))
    		{	priority = "11";
    			tableSql = "select farm_owner_link.* from farm.farm_owner_link  inner join " +
    					"usermanagement.owner on(owner_id =farm_owner_link_owner_id ) " +
    					"inner join usermanagement.user on(owner_user_id = user_id)" +
    					" where user_id = "+objSyncEntity.getUserId()+" ";	
    			alreadyAdded = true;
    		}
    		else if(map.get("table_name").equals("farm_vet_link"))
    		{	priority = "12";
    			tableSql = "select farm_vet_link.* from farm.farm_vet_link  " +
    					" where farm_vet_link_farm_id " +
    							"in("+farmId+") and (farm_vet_link.created_dt > '"+objSyncEntity.getLastSyncDt()+"' or farm_vet_link.updated_dt > '"+objSyncEntity.getLastSyncDt()+"' or farm_vet_link.deleted_dt > '"+objSyncEntity.getLastSyncDt()+"')";	
    		}
    		else if(map.get("table_name").equals("breeding"))
    		{	priority = "13";
    			tableSql = "select breeding.* from farm.breeding left join farm.cattle doecattle on(breeding_doe_id=doecattle.cattle_id) left join farm.cattle buckcattle on(breeding_buck_id=buckcattle.cattle_id) where (buckcattle.cattle_farm_id in("+farmId+") or doecattle.cattle_farm_id in("+farmId+")) and (breeding.created_dt > '"+objSyncEntity.getLastSyncDt()+"' or breeding.updated_dt > '"+objSyncEntity.getLastSyncDt()+"')";	
    		}
    		else if(map.get("table_name").equals("breed_kids"))
    		{	priority = "14";
    			tableSql = "select breed_kids.*,breeding.unique_sync_key as breeding_unique_sync_key from farm.breed_kids inner join farm.breeding on(breed_kids_breeding_id = breeding_id) inner join farm.cattle on(cattle_id = breed_kids_cattle_id) where cattle_farm_id in("+farmId+") and (breed_kids.created_dt > '"+objSyncEntity.getLastSyncDt()+"' or breed_kids.updated_dt > '"+objSyncEntity.getLastSyncDt()+"')";	
    		}
    		
    		//select breed_kids.*,breeding.unique_sync_key as breeding_unique_sync_key from farm.breed_kids inner join farm.cattle kidcattle on(kidcattle.cattle_id = breed_kids_cattle_id) inner join farm.breeding on(breeding_id = breed_kids_breeding_id) left join farm.cattle doecattle on(breeding_doe_id=doecattle.cattle_id) left join farm.cattle buckcattle on(breeding_buck_id=buckcattle.cattle_id) where buckcattle.cattle_farm_id in("+farmId+") or doecattle.cattle_farm_id in("+farmId+") and kidcattle.cattle_farm_id in("+farmId+") and (breed_kids.created_dt > '"+objSyncEntity.getLastSyncDt()+"' or breed_kids.updated_dt > '"+objSyncEntity.getLastSyncDt()+"');
    		
    		else if(map.get("table_name").equals("healthcare"))
    		{	priority = "15";
    			tableSql = "select healthcare.*,cattle.cattle_ear_tag_id as cattle_ear_tag_id,vet.unique_sync_key as vet_unique_sync_key from farm.healthcare inner join farm.cattle on (healthcare_cattle_id = cattle_id) inner join usermanagement.vet on(healthcare_vet_id = vet_id) where cattle_farm_id in("+farmId+") and (healthcare.created_dt > '"+objSyncEntity.getLastSyncDt()+"' or healthcare.updated_dt > '"+objSyncEntity.getLastSyncDt()+"')";	
    		}
    		else if(map.get("table_name").equals("vacc_hc_link"))
    		{	priority = "16";
    			tableSql = "select vacc_hc_link.*,vaccination.unique_sync_key as vaccination_unique_sync_key,healthcare.unique_sync_key as healthcare_unique_sync_key,vet.unique_sync_key as vet_unique_sync_key from farm.vacc_hc_link left join master.vaccination on(vaccination_id=vacc_vaccination_id) inner join farm.healthcare on (vacc_healthcare_id = healthcare_id) inner join farm.cattle on (healthcare_cattle_id = cattle_id) inner join usermanagement.vet on(vacc_hc_vet_id = vet_id) where cattle_farm_id in("+farmId+") and (vacc_hc_link.created_dt > '"+objSyncEntity.getLastSyncDt()+"' or vacc_hc_link.updated_dt > '"+objSyncEntity.getLastSyncDt()+"' or  vacc_hc_link.deleted_dt > '"+objSyncEntity.getLastSyncDt()+"')";	
    		}
    		else if(map.get("table_name").equals("surgery_illness"))
    		{	priority = "17";
    			tableSql = "select surgery_illness.*,cattle.cattle_ear_tag_id as cattle_ear_tag_id,vet.unique_sync_key as vet_unique_sync_key,surgery.unique_sync_key as surgery_unique_sync_key from farm.surgery_illness inner join usermanagement.vet on(surg_ill_vet_id = vet_id) inner join farm.cattle on (surg_ill_cattle_id = cattle_id) left join master.surgery on(surgery_id = surg_ill_surgery_id) where cattle_farm_id in("+farmId+") and (surgery_illness.created_dt > '"+objSyncEntity.getLastSyncDt()+"' or surgery_illness.updated_dt > '"+objSyncEntity.getLastSyncDt()+"')";	
    		}
    		else if(map.get("table_name").equals("surg_ill_det"))
    		{	priority = "18";
    			tableSql = "select surg_ill_det.*,surgery_illness.unique_sync_key as surgery_unique_sync_key from farm.surg_ill_det inner join farm.surgery_illness on (surg_ill_det_surg_ill_id = surg_ill_id) inner join farm.cattle on (surg_ill_cattle_id = cattle_id) where cattle_farm_id in("+farmId+") and (surg_ill_det.created_dt > '"+objSyncEntity.getLastSyncDt()+"' or surg_ill_det.updated_dt > '"+objSyncEntity.getLastSyncDt()+"' or surg_ill_det.deleted_dt > '"+objSyncEntity.getLastSyncDt()+"')";	
    		}
    		else if(map.get("table_name").equals("milk_production"))
    		{	priority = "19";
    			tableSql = "select milk_production.*,cattle.cattle_ear_tag_id from farm.milk_production inner join farm.cattle on (milk_prod_doe_id = cattle_id) where cattle_farm_id in ("+farmId+") and (milk_production.created_dt > '"+objSyncEntity.getLastSyncDt()+"' or milk_production.updated_dt > '"+objSyncEntity.getLastSyncDt()+"' or milk_production.deleted_dt > '"+objSyncEntity.getLastSyncDt()+"')";	
    		}
    		
    		Query query = this.sessionFactory
                    .getCurrentSession()
                    .createSQLQuery(tableSql)
                    .setResultTransformer(
                            AliasToEntityMapResultTransformer.INSTANCE);
    		singleDataList =  query.list();
    		if(singleDataList!=null && singleDataList.size()>0)
    		{   HashMap<String, Object> singlemap = new HashMap<String, Object>();
    		    if(obj.get(priority.trim())!=null && alreadyAdded == false)
    		    {
    		    	 HashMap<String, Object> singlemapTemp = new HashMap<String, Object>();	
    		    	 singlemapTemp = (HashMap<String, Object>)obj.get(priority.trim()) ;
    		    	 List<HashMap<String, Object>> singleDataListTemp = (List<HashMap<String, Object>>)singlemapTemp.get(map.get("table_name")+"");
    		    	 if(singleDataListTemp!=null && singleDataListTemp.size()>0){
    		    	 singleDataList.addAll(singleDataListTemp);
    		    	 }
    		    }
    		    singlemap.put(map.get("table_name")+"",singleDataList);
    		    obj.put(priority.trim(), singlemap);
    			
    		}
    	}
		return obj;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean saveClientDetails(JSONObject objJson) {
		String sql = "";
		boolean resultFlag = true;
		try {
			
			JSONArray objJsonArray = new JSONArray();
			List<HashMap<String, Object>> aliasToValueMapList = null;
			if(objJson.has("cattle_information")){
				objJsonArray = objJson.getJSONArray("cattle_information");
				for(int i=0;i<objJsonArray.length();i++){
					aliasToValueMapList = null;
					JSONObject columnObj = new JSONObject();
					columnObj = (JSONObject)objJsonArray.get(i);
					//System.out.println("columnObj==="+columnObj.getString("cattle_ear_tag_id")+","+columnObj.getString("created_dt")+","+columnObj.getString("updated_dt"));
					 sql = "SELECT count(*) FROM farm.fn_cattle_sync(:cattleeartagid,:cattlefarmcode,:cattledob,:cattlegender,:cattlelocation,:cattlecategory,:cattletype,:cattlestatus,:cattlebuckeartag,:cattledoeeartag,:cattlecreateddate,:cattlelatt,:cattlelong,:cattlelandmark,:cattlecode,:updateddt,:uniquesynckey,:createddt,:tmpgmttime)";
						  Query query = this.sessionFactory
				                    .getCurrentSession()
				                    .createSQLQuery(sql)
				                    .setResultTransformer(
				                            AliasToEntityMapResultTransformer.INSTANCE);
				           
						    query.setParameter("cattleeartagid", columnObj.getString("cattle_ear_tag_id"));
				            query.setParameter("cattlefarmcode", columnObj.getString("cattle_farm_code"));
				            query.setParameter("cattledob",columnObj.getString("cattle_dob"));
				            query.setParameter("cattlegender", columnObj.getString("cattle_gender"));
				            query.setParameter("cattlelocation",columnObj.getString("cattle_location"));
				            query.setParameter("cattlecategory",columnObj.getString("cattle_category"));
				            query.setParameter("cattletype",Integer.parseInt(columnObj.getString("cattle_type")));
				            query.setParameter("cattlestatus",columnObj.getBoolean("cattle_status"));
				            query.setParameter("cattlebuckeartag",columnObj.getString("buck_ear_tag_id"));
				            query.setParameter("cattledoeeartag",columnObj.getString("doe_ear_tag_id"));
				            query.setParameter("cattlecreateddate",columnObj.getString("cattle_created_dt"));
				            query.setParameter("cattlelatt", columnObj.getString("cattle_latt"));
				            query.setParameter("cattlelong",columnObj.getString("cattle_long"));
				            query.setParameter("cattlelandmark", columnObj.getString("cattle_land_mark"));
				            query.setParameter("cattlecode", columnObj.getString("cattle_code"));
				            query.setParameter("updateddt", (columnObj.getString("updated_dt").equals("null")|| columnObj.getString("updated_dt").trim().length() == 0 )?"0000-00-00 00:00:00":columnObj.getString("updated_dt"));
							query.setParameter("uniquesynckey", columnObj.getString("unique_sync_key"));
							query.setParameter("createddt", (columnObj.getString("created_dt").equals("null")|| columnObj.getString("created_dt").trim().length() == 0 )?"0000-00-00 00:00:00":columnObj.getString("created_dt"));
							query.setParameter("tmpgmttime", Common.getCurrentGMTTimestamp());
				           System.out.println("QUERY==========>>>"+"'"+columnObj.getString("cattle_ear_tag_id")+"','"+columnObj.getString("cattle_farm_code")+"','"+columnObj.getString("cattle_dob")+"','"+columnObj.getString("cattle_gender")
				            		+"','"+columnObj.getString("cattle_location")+"','"+columnObj.getString("cattle_category")+"',"+columnObj.getInt("cattle_type")+","+columnObj.getBoolean("cattle_status")+",'"+
				            		columnObj.getString("buck_ear_tag_id")+"','"+columnObj.getString("doe_ear_tag_id")+"','"+columnObj.getString("cattle_created_dt")+"','"+
				            		 columnObj.getString("cattle_latt")+"','"+ columnObj.getString("cattle_long")+"','"+columnObj.getString("cattle_land_mark")+"','"+ columnObj.getString("cattle_code")+"','"+
				            		 columnObj.getString("updated_dt")+"','"+columnObj.getString("unique_sync_key")+"','"+columnObj.getString("created_dt")+"',"+Common.getCurrentGMTTimestamp());
							aliasToValueMapList = query.list(); 
							if(aliasToValueMapList!=null && aliasToValueMapList.size()>0){
								resultFlag = true;
							}else{
								break;
							}
							}
			}
			if(objJson.has("breeding")){
			objJsonArray = objJson.getJSONArray("breeding");
			for(int i=0;i<objJsonArray.length();i++){
				aliasToValueMapList = null;
				JSONObject columnObj = new JSONObject();
				columnObj = (JSONObject)objJsonArray.get(i);
				sql = "select count(*) from farm.fn_breeding_sync(:tmp_breeding_date,:tmp_breeding_village," +
						" :tmp_breeding_aborted_dt ,:tmp_breeding_kids_no ,:tmp_breeding_aborted_no ," +
						" :tmp_breeding_lact_start_dt ,:tmp_breeding_lact_end_dt ,:tmp_updated_dt," +
						":tmp_unique_sync_key , :tmp_created_dt , :tmp_breeding_buck_ear_tag ," +
						" :tmp_breeding_doe_ear_tag, :tmp_gmt_time );";	
				
			Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql);
			query.setParameter("tmp_breeding_date", columnObj.getString("breeding_date"));
			query.setParameter("tmp_breeding_village", columnObj.getString("breeding_village"));
			query.setParameter("tmp_breeding_aborted_dt",(columnObj.getString("breeding_aborted_dt").equals("null")|| columnObj.getString("breeding_aborted_dt").trim().length()==0 )?"0000-00-00 00:00:00":columnObj.getString("breeding_aborted_dt"));
			query.setParameter("tmp_breeding_kids_no", columnObj.getInt("breeding_kids_no"));
			query.setParameter("tmp_breeding_aborted_no", columnObj.getInt("breeding_aborted_no"));
			query.setParameter("tmp_breeding_lact_start_dt",(columnObj.getString("breeding_lact_start_dt").equals("null") || columnObj.getString("breeding_lact_start_dt").trim().length()==0)?"0000-00-00 00:00:00":columnObj.getString("breeding_lact_start_dt"));
			query.setParameter("tmp_breeding_lact_end_dt",columnObj.getString("breeding_lact_end_dt").equals("null")?"0000-00-00 00:00:00":columnObj.getString("breeding_lact_end_dt"));
			query.setParameter("tmp_updated_dt",columnObj.getString("updated_dt"));
			query.setParameter("tmp_unique_sync_key", columnObj.getString("unique_sync_key"));
			query.setParameter("tmp_created_dt", (columnObj.getString("created_dt").equals("null") || columnObj.getString("created_dt").trim().length() == 0)?"0000-00-00 00:00:00":columnObj.getString("created_dt"));
			query.setParameter("tmp_breeding_buck_ear_tag", columnObj.getString("breeding_buck_ear_tag"));
			query.setParameter("tmp_breeding_doe_ear_tag",columnObj.getString("breeding_doe_ear_tag"));
			query.setParameter("tmp_gmt_time", Common.getCurrentGMTTimestamp());
			aliasToValueMapList = query.list(); 
			if(aliasToValueMapList!=null && aliasToValueMapList.size()>0){
				resultFlag = true;
			}else{
				break;
			}
			}
			}
			
			if(objJson.has("breed_kids")){
				objJsonArray = objJson.getJSONArray("breed_kids");
				for(int i=0;i<objJsonArray.length();i++){
					aliasToValueMapList = null;
					JSONObject columnObj = new JSONObject();
					columnObj = (JSONObject)objJsonArray.get(i);
					sql = "select count(*) from farm.fn_breed_kids_sync(:tmp_br_unique_sync_key,:tmp_br_kids_nur_lact_dt,:tmp_br_kids_weaning_dt,:tmp_br_kids_weight,:tmp_updated_dt,:tmp_unique_sync_key,:tmp_created_dt,:tmp_gmt_time)";	
				Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql);
				query.setParameter("tmp_br_unique_sync_key", columnObj.getString("breeding_unique_sync_key"));
				
				query.setParameter("tmp_br_kids_nur_lact_dt", (columnObj.getString("breed_kids_nur_lact_dt").equals("null") || columnObj.getString("breed_kids_nur_lact_dt").trim().length() == 0)?"0000-00-00 00:00:00":columnObj.getString("breed_kids_nur_lact_dt"));
				query.setParameter("tmp_br_kids_weaning_dt",(columnObj.getString("breed_kids_weaning_dt").equals("null") || columnObj.getString("breed_kids_weaning_dt").trim().length() == 0)?"0000-00-00 00:00:00":columnObj.getString("breed_kids_weaning_dt"));
				query.setParameter("tmp_br_kids_weight",columnObj.getDouble("breed_kids_weight"));
				query.setParameter("tmp_updated_dt", (columnObj.getString("updated_dt").equals("null") || columnObj.getString("updated_dt").trim().length() == 0 )?"0000-00-00 00:00:00":columnObj.getString("updated_dt"));
				query.setParameter("tmp_unique_sync_key", columnObj.getString("unique_sync_key"));
				query.setParameter("tmp_created_dt", (columnObj.getString("created_dt").equals("null") || columnObj.getString("created_dt").trim().length() == 0)?"0000-00-00 00:00:00":columnObj.getString("created_dt"));
				query.setParameter("tmp_gmt_time", Common.getCurrentGMTTimestamp());
				aliasToValueMapList = query.list(); 
				if(aliasToValueMapList!=null && aliasToValueMapList.size()>0){
					resultFlag = true;
				}else{
					break;
				}
				}
			}
		/*	aliasToValueMapList = null;*/
			if(objJson.has("milk_production")){
			objJsonArray = objJson.getJSONArray("milk_production");
			for(int i=0;i<objJsonArray.length();i++){
				aliasToValueMapList = null;
				JSONObject columnObj = new JSONObject();
				columnObj = (JSONObject)objJsonArray.get(i);
				sql = "select count(*) from farm.fn_milk_production_sync(:tmp_milk_prod_dt,:tmp_milk_prod_qty," +
						":tmp_milk_prod_comments,:tmp_updated_dt,:tmp_unique_sync_key,:tmp_created_dt," +
						":tmp_gmt_time,:tmp_doe_ear_tag)";	
			Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql);
			query.setParameter("tmp_milk_prod_dt", columnObj.getString("milk_prod_dt"));
			query.setParameter("tmp_milk_prod_qty", columnObj.getDouble("milk_prod_qty"));
			query.setParameter("tmp_milk_prod_comments",columnObj.getString("milk_prod_comments"));
			query.setParameter("tmp_updated_dt", (columnObj.getString("updated_dt").equals("null") || columnObj.getString("updated_dt").trim().length() == 0)?"0000-00-00 00:00:00":columnObj.getString("updated_dt"));
			query.setParameter("tmp_unique_sync_key", columnObj.getString("unique_sync_key"));
			query.setParameter("tmp_created_dt", (columnObj.getString("created_dt").equals("null") || columnObj.getString("created_dt").trim().length() == 0)?"0000-00-00 00:00:00":columnObj.getString("created_dt"));
			query.setParameter("tmp_gmt_time", Common.getCurrentGMTTimestamp());
			//logger.info("doe ear tag id "+columnObj.getString("MilkProductionDoeEarTagID"));
			query.setParameter("tmp_doe_ear_tag",columnObj.getString("MilkProductionDoeEarTagID"));
			aliasToValueMapList = query.list(); 
			if(aliasToValueMapList!=null && aliasToValueMapList.size()>0){
				resultFlag = true;
			}else{
				break;
			}
			}
			}
			if(objJson.has("user")){
				objJsonArray = objJson.getJSONArray("user");
				for(int i=0;i<objJsonArray.length();i++){
					aliasToValueMapList = null;
					JSONObject columnObj = new JSONObject();
					columnObj = (JSONObject)objJsonArray.get(i);
					//System.out.println("columnObj==="+columnObj);
					sql = "select count(*) from usermanagement.fn_user_sync(:tmp_role_name,:tmp_user_updated_dt,:tmp_user_fname,:tmp_user_lname,:tmp_user_location,:tmp_user_email,:tmp_user_address,:tmp_user_phone,:tmp_updated_dt,:tmp_unique_sync_key,:tmp_created_dt,:tmp_gmt_time )";	
				Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql);
				query.setParameter("tmp_role_name", columnObj.getString("user_role_name"));
				query.setParameter("tmp_user_updated_dt", (columnObj.getString("user_updated_dt").equals("null") || columnObj.getString("updated_dt").trim().length() == 0) ?"0000-00-00 00:00:00":columnObj.getString("user_updated_dt"));
				query.setParameter("tmp_user_fname",columnObj.getString("user_fname"));
				query.setParameter("tmp_user_lname",columnObj.getString("user_lname"));
				query.setParameter("tmp_user_location", columnObj.getString("user_location"));
				query.setParameter("tmp_user_email", columnObj.getString("user_email"));
				query.setParameter("tmp_user_address", columnObj.getString("user_address"));
				query.setParameter("tmp_user_phone", columnObj.getString("user_phone"));
				query.setParameter("tmp_updated_dt", (columnObj.getString("updated_dt").equals("null")|| columnObj.getString("updated_dt").trim().length() == 0 )?"0000-00-00 00:00:00":columnObj.getString("updated_dt"));
				query.setParameter("tmp_unique_sync_key", columnObj.getString("unique_sync_key"));
				query.setParameter("tmp_created_dt", (columnObj.getString("created_dt").equals("null")|| columnObj.getString("updated_dt").trim().length() == 0 )?"0000-00-00 00:00:00":columnObj.getString("created_dt"));
				query.setParameter("tmp_gmt_time", Common.getCurrentGMTTimestamp());
				aliasToValueMapList = query.list(); 
				if(aliasToValueMapList!=null && aliasToValueMapList.size()>0){
					resultFlag = true;
				}else{
					break;
				}
				}
			}
			
		} catch (Exception e) {
			resultFlag = false;
			e.printStackTrace();
			logger.error("Error in saveClientDetails Method " + e.getMessage());
		}
		
		return resultFlag;
	}
	/* Not in use
	
	@Transactional
	public CattleEntity getCattleDetails(String cattleEarTag) {
		String sql = "from CattleEntity where cattle_ear_tag_id = :earTag";
		List<CattleEntity> resultList = null;
		boolean resultFlag = false;CattleEntity objCattleEntity = null;
		try {
			Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
			query.setParameter("earTag",cattleEarTag);
			resultList = query.list();
			if(resultList!=null && resultList.size()>0){
				objCattleEntity = resultList.get(0);			
				}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getFarmsAssignedByUserId Method " + e.getMessage());
		}
		
		return objCattleEntity;
	}

*/

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public String generateDesktopId(String status, int u_id) {
		
		String sql ="select * from usermanagement.fn_generate_desktop_id (:u_id)";
		List<Integer> result = null;
		try{
			if(status.equals("0")){
			Query query = this.sessionFactory.getCurrentSession()
                    .createSQLQuery(sql);
			query.setParameter("u_id", u_id);
			result =  query.list();
			}
		}catch(Exception e){
			logger.error("Error in generateDesktopId Method " + e.getMessage());
		}
		if(null != result && result.size()>0){
			System.out.println( result.get(0).toString());
			return "D"+result.get(0).toString();
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "null" })
	@Override
	@Transactional
	public List<HashMap<String, Object>> getImageDetails(
			SyncEntity objSyncEntity, List<Integer> farmIdList, List<Integer> newFarmIdList) {
		List<HashMap<String, Object>> singleDataList = null;List<HashMap<String, Object>> finalSingledataList = new ArrayList<HashMap<String,Object>>();
		List<HashMap<String, Object>> singleDataList1 = null;
		try{
			  String farmId = "";int i=1;String newfarmId ="";
			  for(int s : farmIdList){
					 if(i < farmIdList.size()){
					  farmId += s+",";}
					  else{
						  farmId += s; } 
					  i++;
				  }
			 if(newFarmIdList!=null && newFarmIdList.size()>0)
			 {
				 int ii=1;
				  for(int s : newFarmIdList){
						 if(ii < newFarmIdList.size()){
							 newfarmId += s+",";}
						  else{
							  newfarmId += s; } 
						  ii++;
					  }
			 }
		if(null !=farmIdList &&  farmIdList.size() >0){
			String sql = "select cattle_image.* from farm.cattle_image inner join farm.cattle on(cattle_image_cattle_id = cattle_id) where cattle_farm_id in("+farmId+") and (cattle_image.created_dt > '"+objSyncEntity.getLastSyncDt()+"' or cattle_image.updated_dt > '"+objSyncEntity.getLastSyncDt()+"' or cattle_image.deleted_dt > '"+objSyncEntity.getLastSyncDt()+"')";
		
			Query query = this.sessionFactory.getCurrentSession()
                    .createSQLQuery(sql).setResultTransformer(
                            AliasToEntityMapResultTransformer.INSTANCE);
			singleDataList =  query.list();
			if(null !=newFarmIdList &&  newFarmIdList.size() >0){
			 sql = "select cattle_image.* from farm.cattle_image inner join farm.cattle on(cattle_image_cattle_id = cattle_id) where cattle_farm_id in("+newfarmId+") and (cattle_image.created_dt > '2011-01-01 00:00:00' or cattle_image.updated_dt > '2011-01-01 00:00:00' or cattle_image.deleted_dt > '2011-01-01 00:00:00'";
			
			query = this.sessionFactory.getCurrentSession()
                    .createSQLQuery(sql).setResultTransformer(
                            AliasToEntityMapResultTransformer.INSTANCE);
			singleDataList1 =  query.list();
			if(singleDataList1!=null){
			finalSingledataList.addAll(singleDataList1);
			}
			}
			if(singleDataList!=null){
			finalSingledataList.addAll(singleDataList);
			}
		}else{
			return null;
		}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error in getImageDetails Method " + e.getMessage());
		}
		return finalSingledataList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Integer> getfarmsByUserId(int userId,String role,int existOrNotExist, List<String> farms) {
		String sql = "";
		if(role.toUpperCase().equals("OWNER")){
			String farmCodes = "";int i=1;
			if(farms!=null && farms.size()>0){
			  for(String s : farms){
					 if(i < farms.size()){
					  farmCodes += "'"+s+"',";}
					  else{
						  farmCodes += "'"+s+"'"; } 
					  i++;
				  }
			}
			if(existOrNotExist==0 && farms!=null && farms.size()>0){
				System.out.println("==========11111===============");
			sql = "select linkEntity.farm_owner_link_farm_id from farm.farm_owner_link linkEntity inner join usermanagement.owner owner on(linkEntity.farm_owner_link_owner_id=owner_id) " +
					"inner join farm.farm farm on(farm_owner_link_farm_id=farm.farm_id) inner join usermanagement.user us on(owner.owner_user_id=us.user_id) where us.user_id= "+userId+"  and farm.farm_code not in("+farmCodes+")";// and farm.farm_status = true
			}
			else if(existOrNotExist==1 && farms!=null && farms.size()>0){
				System.out.println("==========00000===============");
				sql = "select linkEntity.farm_owner_link_farm_id from farm.farm_owner_link linkEntity inner join usermanagement.owner owner on(linkEntity.farm_owner_link_owner_id=owner_id) " +
						"inner join farm.farm farm on(farm_owner_link_farm_id=farm.farm_id) inner join usermanagement.user us on(owner.owner_user_id=us.user_id) where us.user_id= "+userId+" and farm.farm_code in("+farmCodes+") "; //and farm.farm_status = true
				}
			else {
				System.out.println("==========######===============");
				sql = "select linkEntity.farm_owner_link_farm_id from farm.farm_owner_link linkEntity inner join usermanagement.owner owner on(linkEntity.farm_owner_link_owner_id=owner_id) " +
						"inner join farm.farm farm on(farm_owner_link_farm_id=farm.farm_id) inner join usermanagement.user us on(owner.owner_user_id=us.user_id) where us.user_id= "+userId+" "; //and farm.farm_status = true
				}
		}
		
		List<Integer> resultList = null;
		try {
			Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql);
			resultList =  query.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getfarmsByUserId Method " + e.getMessage());
		}
		
	return resultList;
	
	}
	
	
}
