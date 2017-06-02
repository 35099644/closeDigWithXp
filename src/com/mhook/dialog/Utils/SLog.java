package com.mhook.dialog.Utils;
import android.util.*;

public class SLog
{
	
	private static boolean isDebug=true;
	
	
	public static void d(String tag,String msg){
		
		if(isDebug){
		
		Log.d(tag,msg);
		
		}
		
	}
}
