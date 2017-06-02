package com.mhook.dialog;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;

import de.robv.android.xposed.XposedBridge;

import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import de.robv.android.xposed.*;
import android.widget.*;
import android.util.*;
import android.app.*;
import android.content.*;
import de.robv.android.xposed.XC_MethodHook.*;
import com.mhook.dialog.Utils.*;

public class Module implements IXposedHookLoadPackage {

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		
		XSharedPreferences XSharedPreferences1=new XSharedPreferences("com.mhook.dialog","share_data");
		
		if(!XSharedPreferences1.getBoolean("moduleactivation",false))return;
   
			XC_MethodHook callback1=new XC_MethodHook(){
			@Override
			protected void beforeHookedMethod(MethodHookParam param){
				
				SLog.d("log_2","对话框已被设置为能取消");
				
					param.args[0]=true;
				
			}

		};	
		
		XC_MethodHook XC_MethodHook1=new XC_MethodHook(){
			
			public void   beforeHookedMethod(XC_MethodHook.MethodHookParam param){
				
				SLog.d("log_2","获取取消函数完毕");
				
				if(param.args[0] instanceof DialogInterface.OnCancelListener){
					
					SLog.d("log_2","参数是匹配");
					
					DialogInterface.OnCancelListener OnCancelListener1=new DialogInterface.OnCancelListener(){

						@Override
						public void onCancel(DialogInterface p1)
						{
							
							SLog.d("log_2","对话框替换成功");
							
							// TODO: Implement this method
						}
					};
					
					param.args[0]=OnCancelListener1;
					
				}
				
			}
			
		};
		
		SLog.d("log_2","对话框取消已激活");
		
		Class clazz=null;
		try
		{
		 clazz=Class.forName("android.support.v7.app.AlertDialog.Builder");
		}
		catch (ClassNotFoundException e)
		{
			
			SLog.d("log_2","class未发现:"+e);
			
		}
		
		if(clazz!=null){
			
			SLog.d("log_2","clazz存在");
			
		XposedBridge.hookAllMethods(clazz, "setCancelable", callback1);
		
			XposedBridge.hookAllMethods(clazz,"setOnCancelListener",XC_MethodHook1);
		
		}else{
			
			SLog.d("log_2","clazz未知");
			
		}
			XposedBridge.hookAllMethods(Dialog.class, "setCancelable", callback1);
		XposedBridge.hookAllMethods(Dialog.class, "setCanceledOnTouchOutside",callback1);
		XposedBridge.hookAllMethods(AlertDialog.Builder.class, "setCancelable", callback1);
		XposedBridge.hookAllMethods(Activity.class, "setFinishOnTouchOutside", callback1);
		
		XposedBridge.hookAllMethods(AlertDialog.Builder.class,"setOnCancelListener",XC_MethodHook1);
		
		XposedBridge.hookAllMethods(Dialog.class,"setOnCancelListener",XC_MethodHook1);
		
		}

	}
	
