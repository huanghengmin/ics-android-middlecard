package com.fri.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.util.Log;
import android.util.Base64;

/**
 * @author  Sonicom Beijing
 *
 */
public interface ZDTokenCmd {
		
	
	final static String J_ERROR = "error"; 
	final static String J_RESULT = "result"; 
	public final static String ACTION_IDCARD_READ_DATA = "com.fri.service.IDCARD_READ_DATA";	
	
	public final static int SERVICE_IDCARD_READ 		= 0x20000;	//
	
	public final static int ID_ZDTOKEN_DEMO 			= 0; //
	public final static int ID_ZDTOKEN_GET_INFO 		= 1; // 
	public final static int ID_ZDTOKEN_SET_INFO 		= 2; // 
	public final static int ID_DEVLOPMENT_TEST      	= 3;  // 
	
	public final static int ID_VTOKEN_HANDLE			= 10020; // 
			
		
	
	// ID_ZDTOKEN_GET_INFO
	public final static int ARG1_ID_INFO_VERSION 		= 10;  	//
	public final static int ARG1_ID_INFO_SERVER			= 20;	//
	
			
	// CMD_VTOKEN_HANDLE
	public final static int ARG1_ID_VTOKEN_OPEN 		= 1000; //
	public final static int ARG1_ID_VTOKEN_CLOSE 		= 1020;	//
	public final static int ARG1_ID_VTOKEN_GET_SN 		= 1030;	//
	public final static int ARG1_ID_VTOKEN_GET_CERT 	= 1040;	//
	public final static int ARG1_ID_VTOKEN_SIGN_DATA 	= 1050;	//
	
	
	//ID_DEVLOPMENT_TEST
	public final static int ARG1_ID_DEV_TEST_YATE 		= 10;  
	public final static int ARG1_ID_DEV_TEST_ZMQ 		= 20;
	public final static int ARG1_ID_DEV_TEST_JNI 		= 30;
	
	
	public final static int ARG1_ID_DEV_TEST_VTOKEN		= 1000001;
	
	
}
