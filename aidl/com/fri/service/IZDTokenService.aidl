package com.fri.service;

interface IZDTokenService {     
	String jsonrpc( String cmd );
	String serviceCmd( int cmd, String info, String service );
	String command( int what, int arg1, int arg2, String cmd );
}
