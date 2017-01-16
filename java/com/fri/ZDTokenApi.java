
package com.fri;

import android.util.Log;


public class ZDTokenApi {  
    

	final static boolean DBG = true;
	final static String TAG = "ZDTokenApi";
	
	static {
		try {
	        System.loadLibrary("ZDToken_jni");	
		} catch (UnsatisfiedLinkError e) {
			e.printStackTrace();
		}
    }
			    		
	public native String getVersion();
	
	public native int Initialize();
	
	public native int UnInitialize();
	
	public native String GetTFName();
	
	public native byte[] LoadCert();
	
	public native byte[] PrivateEncrypt(byte[] from, int nKeyLength, int padding);
	
	public void Test()
	{
		Initialize();
		
		log( "Initialize" );
		
		String name = GetTFName();

		log( "GetTFName " + name  );
	
		byte[] cert = LoadCert();

		log( "LoadCert " + cert.length  );
	
		byte[] hash = new byte[128];
		
		for( int i=0; i<128; i++ ){
			hash[i] = (byte)i;
		}
		
		byte[] sign = PrivateEncrypt( hash, 128, 0 );
		
		log( "PrivateEncrypt " + sign.length  );
		
		UnInitialize();
	}
	
	
    private void log(String s) {
    	Log.d( TAG, s);
    }

    private void loge(String s) {
    	Log.e( TAG, s);
    }
    
    private static void slog(String s) {
    	Log.d( TAG, s);
    }

    private void loge(String s, Throwable e) {
    	Log.e( TAG, s);
    	e.printStackTrace();
    }
	
};