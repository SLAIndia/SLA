package com.inapp.cms.dao;
/**
 * @author Jinesh George
 */
import java.util.HashMap;
import java.util.List;

import com.inapp.cms.entity.LoginEntity;
import com.inapp.cms.entity.UserEntity;

public interface UserDAO 
{
    public void addUser(UserEntity user);
   
  
    public List<UserEntity> getAllUsers();
    public void deleteUser(Integer userId);
    public List<UserEntity> getUser(UserEntity user);

	public HashMap<String, Object> getUserDetails(UserEntity user);


	public List<UserEntity> userLogin(LoginEntity login);


	public boolean isUserNameExist(String username) throws Exception;

	public UserEntity getUser(int userId);


	public void updatePassword(UserEntity objUserEntity) throws Exception;


	public UserEntity validateMail(UserEntity objUser);


	public boolean isemailExist(String email,String uId);

}
