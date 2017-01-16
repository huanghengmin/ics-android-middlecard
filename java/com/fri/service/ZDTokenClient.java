package com.fri.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * @author
 *
 */
public class ZDTokenClient implements ZDTokenCmd{
	
	final String TAG = "ZDTokenClient";
	
	Context mContext;
	
	public ZDTokenClient( Context context ){
		mContext = context;
	}
	
	IZDTokenService mZDTokenService = null;	
	
	ServiceConnection conn = new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mZDTokenService = IZDTokenService.Stub.asInterface(service);  			
			if( mListener != null ){
				mListener.onBindConnect();
			}
			Log.i( TAG, "onServiceConnected " );
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.i( TAG, "onServiceDisconnected " );
			mZDTokenService = null;
			if( mListener != null ){
				mListener.onBindDisconnect();
			}
		}
				
	};
	
	//public final String packageName = "sonicom.sip";
	//public final String packageName = "com.fri.sonicom.idcardservice";
	public final String packageName = "com.fri.zdtoken";
	public final String serviceNmae =  "com.fri.service.ZDTokenService";
	
    public void startTokenService()
    {
		Intent inte=new Intent();
	    ComponentName com=new ComponentName(packageName,serviceNmae );
	    inte.setComponent(com);    
	    mContext.startService( inte );	    	
    }
	
    public boolean bindTokenService()
    {
    	if( mZDTokenService == null ){    		
    		boolean bOk = false;    		
   	    	Intent inte=new Intent();
       	    ComponentName com=new ComponentName(packageName,serviceNmae );
       	    inte.setComponent(com);        	    
       	    bOk = mContext.bindService( inte, conn, Service.BIND_AUTO_CREATE );
       	    if( bOk ){
    	    	Log.e(TAG, "bindTokenService ok" );    	    	
    	    }
    	    else{
    	    	Log.e(TAG, "bindTokenService error" );
    	    }
       	    return bOk;
    	}
    	return true;
    }
    
    public void unbindTokenService(){
    	if( mZDTokenService != null ){
    		mContext.unbindService(conn);
    		mZDTokenService = null;
    		if( mListener != null ){
    			mListener.onBindDisconnect();
    		}
    	}
    }
    
    public static class Listener {
        public void onBindConnect( ) {
        }
        public void onBindDisconnect( ) {
        }
    }
    
    private Listener mListener;
    public void setListener(Listener listener) {
        mListener = listener;
    }
        
    	
	int Times = 0;
	public String onTestDemo(){
		String cmd = "";
		cmd = command( ID_ZDTOKEN_DEMO, Times++, 0, cmd );
		return cmd;
	}
   
	public String command( int what, int arg1, int arg2, String cmd ){
		String result = "";
		if( mZDTokenService == null ){
			startTokenService();
			bindTokenService();			
		}
		if( mZDTokenService == null ){
			Log.i(TAG, "mZDTokenService == null" );
			return "mZDTokenService == null";		
		}
		try {
			result = mZDTokenService.command( what, arg1, arg2, cmd );
		} catch (RemoteException e) {
			e.printStackTrace();
			Log.i(TAG, "RemoteException" );
			return "RemoteException";
		}
		//Log.i(TAG, Times + " Service Test "  + cmd );
		return result;				
	}
	
	
	public String getSN(){
				
		String cmd = "", result;		
		JSONObject j_response, j_result;			
		try {
			result = command( ID_VTOKEN_HANDLE, ARG1_ID_VTOKEN_GET_SN, 0, cmd );			
			if( result != null && result.length() > 2 ){
				j_response = new JSONObject(result);				
				if( j_response.has(J_RESULT) == false ){
					return "";
				}				
				j_result = j_response.getJSONObject(J_RESULT);
				if( j_result.has("SN") == false){
					return "";
				}
				String sn = j_result.getString("SN");
				return sn;				
			}
			else{
				return "";
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		return "";					
	}
		
}
