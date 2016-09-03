package com.inapp.cms.utils;

public class MessageConstants {
	
	public static final String msg_1 = "Success";
	public static final String msg_9 = "Wrong Signature";
	public static final String msg_8 = "User not logged in";
	public static final String msg_0 = "Username or password is incorrect";
	public static final String msg_11 = "Password is incorrect";
	public static final String msg_2 = "Details not Found";
	public static final String msg_3 = "Save Failed";
	public static final String msg_10 = "Server Error";
	public static final String msg_12 = "Permission Denied";
	public static final String msg_13 = "No User is Registered with this Email";
	public static final String msg_5 = "User was Logged in with temp password";
	public static final String msg_14 = "Please Use this feature from your Desktop application";
	
	//role checking 
	public static final int admin = 1;
	public static final int owner = 2;
	public static final int vet = 3;
	public static final int alluser = 4;
	
	public static final String role_admin = "ADMIN";
	public static final String role_owner = "OWNER";
	public static final String role_vet = "VET";
	
	public static final String[] ADMIN_PERMISSION = {"ADMIN"};
	public static final String[] OWNER_PERMISSION = {"OWNER"};
	public static final String[] ADMN_OWNR_PERMISSION = {"ADMIN","OWNER"};
	public static final String[] ADMN_VET_PERMISSION = {"ADMIN","VET"};
	public static final String[] VET_PERMISSION = {"VET"};
	public static final String[] ALL_PERMISSION = {"VET","ADMIN","OWNER"};

}
