package com.inapp.cms.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.inapp.cms.entity.LoginEntity;
import com.inapp.cms.entity.UserEntity;
import com.inapp.cms.utils.RepositoryConstants;

/**
 * @author Jinesh George
 */
@Repository(RepositoryConstants.USER_DAO)
public class UserDaoImpl implements UserDAO {
	private static final Logger logger = Logger.getLogger(UserDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	public void addUser(UserEntity user) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(user);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<UserEntity> getUser(UserEntity user) {
		Query query = this.sessionFactory.getCurrentSession().createQuery(
				"from UserEntity where user_id = :id");
		query.setParameter("id", user.getId());
		List<UserEntity> list = query.list();
		return list;

	}

	@SuppressWarnings("unchecked")
	public List<UserEntity> getAllUsers() {
		return this.sessionFactory.getCurrentSession()
				.createQuery("from UserDetailsEntity").list();
	}

	public void deleteUser(Integer userId) {
		UserEntity user = (UserEntity) sessionFactory.getCurrentSession().load(
				UserEntity.class, userId);
		if (null != user) {
			this.sessionFactory.getCurrentSession().delete(user);
		}
	}

	@SuppressWarnings("unchecked")
	public List<UserEntity> userLogin(LoginEntity login) {
		Query query = this.sessionFactory
				.getCurrentSession()
				.createQuery(
						"from UserEntity where username = :username and password = :password ");
		query.setParameter("username", login.getUsername());
		query.setParameter("password", login.getPassword());
		List<UserEntity> list = query.list();
		return list;

	}

	public void updateUserProfile(int userID, String fileUrl) throws Exception {
		try {
			String hql = "update UserDetailsEntity set userdetail_prof_url = :fileUrl where userId = :userId  ";
			Query query = this.sessionFactory.getCurrentSession().createQuery(
					hql);
			query.setParameter("userId", userID);
			query.setParameter("fileUrl", fileUrl);
			query.executeUpdate();
		} catch (Exception e) {
			logger.error("Error in updating the user profile url . "
					+ e.getMessage());
			throw new Exception("Error in updating the user profile url . "
					+ e.getMessage(), e);
		}

	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getUserDetails(int userID) throws Exception {
		List<Map<String, Object>> aliasToValueMapList = null;
		try {
			String sql = "SELECT  userdetail_fname ||' ' ||  userdetail_lname as name , user_phone_type as phonetype, user_device_token as devicetoken"
					+ " FROM usermanagement.user_login"
					+ " join usermanagement.user_details on (userdetail_user_id = user_id) "
					+ " where user_id = " + userID;

			Query query = this.sessionFactory
					.getCurrentSession()
					.createSQLQuery(sql)
					.setResultTransformer(
							AliasToEntityMapResultTransformer.INSTANCE);
			aliasToValueMapList = query.list();
		} catch (Exception e) {
			logger.error("Error in getting the host details. " + e.getMessage());
			throw new Exception("Error in getting the host details. "
					+ e.getMessage(), e);
		}
		return aliasToValueMapList.isEmpty() ? null
				: (HashMap<String, Object>) aliasToValueMapList.get(0);
	}

	public void updateLoginDetails(int userId) {
		try {
			String hql = "update UserEntity set userDeviceToken = null , userPhoneType = 0 where id = :userId  ";
			Query query = this.sessionFactory.getCurrentSession().createQuery(
					hql);
			query.setParameter("userId", userId);
			query.executeUpdate();
		} catch (Exception e) {
			logger.error("Error in update login details fro log out . "
					+ e.getMessage());
		}
	}

	@Transactional
	public HashMap<String, Object> getUserDetails(UserEntity user) {

		List<HashMap<String, Object>> aliasToValueMapList = null;
		String sql = "SELECT user_role_id,user_role_name,user_password,user_temp_password,user_name,user_id,user_active FROM usermanagement.fn_user_sel(:username,:password) where user_active=true";
		try {
			Query query = this.sessionFactory
					.getCurrentSession()
					.createSQLQuery(sql)
					.setResultTransformer(
							AliasToEntityMapResultTransformer.INSTANCE);

			query.setParameter("username", user.getUsername());
			query.setParameter("password", user.getPassword());
			aliasToValueMapList = query.list();
			if (aliasToValueMapList != null && aliasToValueMapList.size() > 0) {
				logger.info("user Details " + aliasToValueMapList.get(0));
			} else {
				return null;
			}
			// List<UserEntity> list = query.list();
		} catch (Exception e) {
			logger.error("Error in getUserDetails Method " + e.getMessage());
		}
		return aliasToValueMapList.get(0);
	}

	@Override
	@Transactional
	public boolean isUserNameExist(String username) throws Exception {
		List<Integer> list = null;
		try {
			Query query = this.sessionFactory.getCurrentSession().createQuery(
					"from UserEntity where UPPER(username) = :username ");
			query.setParameter("username", username.toUpperCase());
			list = query.list();
		} catch (Exception e) {
			logger.error("Error while isUserNameExist : " + e.getMessage());
		}
		return !list.isEmpty();

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public UserEntity getUser(int userId) {
		String sql = "from UserEntity  where id =:id";
		UserEntity obj = null;
		try {
			Query query = this.sessionFactory.getCurrentSession().createQuery(
					sql);
			query.setParameter("id", userId);
			List<UserEntity> result = query.list();
			if (result != null && result.size() > 0) {
				obj = (UserEntity) result.get(0);
			}
		} catch (Exception e) {
			logger.error("Error in getUser Method " + e.getMessage());
		}
		return obj;
	}

	@Override
	@Transactional
	public void updatePassword(UserEntity objUserEntity) throws Exception {
		Integer userId1 = objUserEntity.getId();
		String newPassword1 = objUserEntity.getNewpassword();
		try {
			String hql = "update UserEntity set password = :newPassword , temppassword = null, updateddt = :updateddt  where id = :userId  ";
			Query query = this.sessionFactory.getCurrentSession().createQuery(
					hql);

			query.setParameter("newPassword", newPassword1);
			query.setParameter("updateddt", objUserEntity.getUpdateddt());
			query.setParameter("userId", userId1);
			query.executeUpdate();
		} catch (Exception e) {
			logger.error("Error in updating the Password " + e.getMessage());
			throw new Exception("Error in updating Password . "
					+ e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public UserEntity validateMail(UserEntity objUser) {
		String sql = "from UserEntity  where useremail =:email and isactive = true";
		String tmp_pswd = objUser.getTemppassword();
		Timestamp updatedDt = objUser.getUpdateddt();
		String platform = objUser.getPlatform();
		try {
			Query query = this.sessionFactory.getCurrentSession().createQuery(
					sql);
			query.setParameter("email", objUser.getUseremail());
			List<UserEntity> result = query.list();
			if (null != result && result.size() > 0) {
				objUser = (UserEntity) result.get(0);
				if (objUser.getObjRoleEntity().getRolename().toUpperCase()
						.equals("OWNER")
						&& platform.equals("web")) {
					return objUser;
				} 
				else if(objUser.getObjRoleEntity().getRolename().toUpperCase()
						.equals("OWNER")
						&& !platform.equals("web")){
					sql = "update UserEntity set temppassword = :tmpPswd,password = :password, updated_dt = :updateddt where id = :userId  ";
					query = this.sessionFactory.getCurrentSession()
							.createQuery(sql);
					query.setParameter("tmpPswd", tmp_pswd);
					query.setParameter("password", tmp_pswd);
					query.setParameter("updateddt", updatedDt);
					query.setParameter("userId", objUser.getId());
					query.executeUpdate();
				}
				else {
					sql = "update UserEntity set temppassword = :tmpPswd, updated_dt = :updateddt where id = :userId  ";
					query = this.sessionFactory.getCurrentSession()
							.createQuery(sql);
					query.setParameter("tmpPswd", tmp_pswd);
					query.setParameter("updateddt", updatedDt);
					query.setParameter("userId", objUser.getId());
					query.executeUpdate();
				}
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in validateMail  " + e.getMessage());
			return null;
		}
		return objUser;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean isemailExist(String email, String uId) {
		List<Integer> list = null;
		String sql = "from UserEntity where UPPER(useremail) = :email ";
		if (null != uId && uId.trim().length() > 0) {
			sql += " and id !=:id";
		}
		try {
			Query query = this.sessionFactory.getCurrentSession().createQuery(
					sql);
			if (null != uId && uId.trim().length() > 0) {
				query.setParameter("id", Integer.parseInt(uId));
			}
			query.setParameter("email", email.toUpperCase());
			list = query.list();
		} catch (Exception e) {
			logger.error("Error while isemailExist : " + e.getMessage());
		}
		return !list.isEmpty();

	}

}
