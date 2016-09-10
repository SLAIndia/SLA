package com.app.usermanagement.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.usermanagement.dao.UserDao;
import com.app.usermanagement.entity.UserDetailsEntity;
import com.app.usermanagement.entity.UserEntity;
import com.app.utils.AppException;
import com.app.utils.AppMessage;
import com.app.utils.AppUtil;
import com.app.utils.Common;
import com.app.utils.RandomString;
import com.app.utils.ServiceConstants;

@Service(ServiceConstants.USER_SERVICE)
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	public List<UserEntity> getUsers() throws Exception {
		return userDao.getUsers();
	}

	@Override
	public UserEntity getUser(int userTypeId) throws Exception {
		return userDao.getUser(userTypeId);
	}

	@Override
	public void registerUser(UserDetailsEntity userDetails) throws Exception {
		try {
			UserEntity userEntity = userDetails.getUser();
			String password = new RandomString(6).nextString().toUpperCase();
			Timestamp currentTime = Common.getCurrentTimestamp();
			userEntity.setPassword(AppUtil.getMD5(password));
			userEntity.setUpdatedDt(currentTime);
			userEntity.setCreatedDt(currentTime);
			userEntity.setTempPasswordDt(currentTime);
			userEntity.setUserStatus(1);
			userDao.saveUser(userEntity);

			// setting userName as primary Email Id
			userDetails.setpEmail(userEntity.getUsername());

			userDetails.setFname(Optional.ofNullable(userDetails.getFname()).orElse(""));
			userDetails.setLname(Optional.ofNullable(userDetails.getLname()).orElse(""));
			userDetails.setPhone1(Optional.ofNullable(userDetails.getPhone1()).orElse(""));

			userDetails.setCreatedDt(currentTime);
			userDetails.setUpdatedDt(currentTime);

			userDao.saveUserDetails(userDetails);
			
			//sendRegisterMessageToUser(userDetails);
		} catch (ConstraintViolationException | PSQLException e) {
			throw new AppException(AppMessage.USERNAME_EXISTS);
		}
	}

	@Override
	public void updateUser(UserDetailsEntity userDetails) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isUsernameAlreadyInUse(String username, int userId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(int userTypeId) throws Exception {
		return userDao.deleteUser(userTypeId);
	}

	private void sendRegisterMessageToUser(UserDetailsEntity userDetails) {

	}

}
