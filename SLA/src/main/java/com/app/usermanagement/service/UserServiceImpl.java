package com.app.usermanagement.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.usermanagement.dao.UserDao;
import com.app.usermanagement.entity.UserAddressEntity;
import com.app.usermanagement.entity.UserDetailsEntity;
import com.app.usermanagement.entity.UserEntity;
import com.app.utils.AppException;
import com.app.utils.AppMessage;
import com.app.utils.AppUtil;
import com.app.utils.Common;
import com.app.utils.EmailTemplate;
import com.app.utils.HtmlReader;
import com.app.utils.RandomString;
import com.app.utils.ServiceConstants;

@Service(ServiceConstants.USER_SERVICE)
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Autowired
	private UserMessageService userMessageService;

	@Override
	public List<UserDetailsEntity> getUsers() throws Exception {
		return userDao.getUsers();
	}

	@Override
	public UserDetailsEntity getUser(int userTypeId) throws Exception {
		return userDao.getUser(userTypeId);
	}

	@Override
	public void registerUser(UserDetailsEntity userDetails) throws Exception {

		UserEntity user = userDetails.getUser();
		validateUser(user, userDetails);

		String password = new RandomString(6).nextString().toUpperCase();
		Timestamp currentTime = Common.getCurrentTimestamp();
 
		user.setPassword(AppUtil.getMD5(password));
		user.setUpdatedDt(currentTime);
		user.setCreatedDt(currentTime);
		user.setTempPasswordDt(currentTime);
		user.setUserStatus(1);
		userDao.saveUser(user);

		// setting userName as primary Email Id
		userDetails.setpEmail(user.getUsername());
		userDetails.setFname(Optional.ofNullable(userDetails.getFname()).orElse(""));
		userDetails.setLname(Optional.ofNullable(userDetails.getLname()).orElse(""));
		userDetails.setCreatedDt(currentTime);
		userDetails.setUpdatedDt(currentTime);

		userDao.saveUserDetails(userDetails);
		// saving user addresses
		if (null != userDetails.getAddresses()) {
			userDao.saveUserAddresses(userDetails.getAddresses(), user);
		}

		//userMessageService.sendRegistrationMail(userDetails,password);
	}

	@Override
	public void updateUser(UserDetailsEntity userDetails) throws Exception {
		UserEntity user = userDetails.getUser();
		validateUser(user, userDetails);

		Timestamp currentTime = Common.getCurrentTimestamp();

		UserDetailsEntity userDetailsDB = userDao.getUser(user.getId());
		UserEntity userEntityDB = userDetailsDB.getUser();

		// updating user
		if (null != user.getRole()) {
			userEntityDB.setRole(user.getRole());
		}
		if (null != user.getUserType()) {
			userEntityDB.setUserType(user.getUserType());
		}
		if (null != user.getUserEntity()) {
			userEntityDB.setUserEntity(user.getUserEntity());
		}
		userEntityDB.setUsername(user.getUsername());
		userEntityDB.setUserStatus(user.getUserStatus());
		userEntityDB.setUpdatedDt(currentTime);

		userDao.updateUser(userEntityDB);

		// updating user details
		userDetailsDB.setFname(userDetails.getFname());
		userDetailsDB.setLname(userDetails.getLname());
		userDetailsDB.setPhone1(userDetails.getPhone1());
		userDetailsDB.setPhone2(userDetails.getPhone2());
		userDetailsDB.setPhone3(userDetails.getPhone3());

		if (userDetails.getDeviceToken() != null) {
			userDetailsDB.setDeviceToken(userDetails.getDeviceToken());
		}
		if (userDetails.getDeviceType() != null) {
			userDetailsDB.setDeviceType(userDetails.getDeviceType());
		}
		userDetailsDB.setpEmail(user.getUsername());
		userDetailsDB.setsEmail(userDetails.getsEmail());
		userDetailsDB.setCountry(userDetails.getCountry());
		userDetailsDB.setUpdatedDt(currentTime);

		userDao.updateUserDetails(userDetailsDB);
		// updating user addresses
		if (null != userDetails.getAddresses()) {
			userDao.updateUserAddresses(userDetails.getAddresses(), user);
		}
	}

	@Override
	public boolean isUsernameAlreadyInUse(String username, Integer userId) throws Exception {
		if (username == null) {
			throw new AppException(AppMessage.INVALID_REQUEST);
		}
		return userDao.isUsernameAlreadyInUse(username, userId);
	}

	@Override
	public boolean isPhoneAlreadyInUse(String phone1, Integer userId) throws Exception {
		if (phone1 == null) {
			throw new AppException(AppMessage.INVALID_REQUEST);
		}
		return userDao.isPhoneAlreadyInUse(phone1, userId);
	}

	@Override
	public boolean deleteUser(int userTypeId) throws Exception {
		return userDao.deleteUser(userTypeId);
	}

	private void validateUser(UserEntity user, UserDetailsEntity userDetails) throws Exception {

		if (isUsernameAlreadyInUse(user.getUsername(), user.getId())) {
			throw new AppException(AppMessage.USERNAME_EXISTS);
		}
		if (isPhoneAlreadyInUse(userDetails.getPhone1(), user.getId())) {
			throw new AppException(AppMessage.PHONE_EXISTS);
		}
		// Admin role is not allowed for register/update user through this apis
		if (user.getRole().getId() == 1) {
			throw new AppException(AppMessage.INVALID_REQUEST);
		}

		Optional.ofNullable(userDetails.getUser()).orElseThrow(() -> new AppException(AppMessage.USERNAME_EXISTS));
		Optional.ofNullable(userDetails.getUser().getRole())
				.orElseThrow(() -> new AppException(AppMessage.INVALID_REQUEST));
		Optional.ofNullable(userDetails.getUser().getUserType())
				.orElseThrow(() -> new AppException(AppMessage.INVALID_REQUEST));

	}

	@Override
	public boolean approveUser(int status, Integer[] userIds) throws Exception {
		return userDao.approveUser(status, userIds);
	}

}
