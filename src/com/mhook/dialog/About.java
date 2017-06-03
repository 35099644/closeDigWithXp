package com.mhook.dialog;

import java.util.List;

import com.mhook.dialog.Utils.AppUtils;
import com.mhook.dialog.Utils.T;

import android.R.anim;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.view.MenuItem;

public class About extends PreferenceActivity 
{
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
        getPreferenceManager().setSharedPreferencesMode(Context.MODE_WORLD_READABLE);
        addPreferencesFromResource(R.xml.about);
		
	}
	
	/* (非 Javadoc)
	 * Description:
	 * @see android.preference.PreferenceActivity#onPreferenceTreeClick(android.preference.PreferenceScreen, android.preference.Preference)
	 */
	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		// TODO 自动生成的方法存根
		
		if(preference.getKey()==null)return false;
		
		try{
			
			if(preference.getKey().equals("author")){
	 			
	 			Intent intent =new Intent(Intent.ACTION_VIEW );
	 			
	 			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	 			
	 			intent.setData(Uri.parse("mqqapi://card/show_pslcard?src_type=internal&version=1&uin=2664487933&card_type=person&source=external"));
	 			
	 			startActivity(intent);
	 			
	 			T.ShowToast(this,"启动QQ中...");
	 			
	 		}
			
		if(preference.getKey().equals("alipay")){
			
			Intent intent =new Intent(Intent.ACTION_VIEW );
			
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			
			intent.setData(Uri.parse("alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=https://qr.alipay.com/aphe0jx6etfz5te890"));
			
			startActivity(intent);
			
			T.ShowToast(this,"启动支付宝中...");
			
		}
		
         if(preference.getKey().equals("kuan")){
			
			Intent intent =new Intent(Intent.ACTION_VIEW );
			
			intent.setData(Uri.parse("market://details?id=com.mhook.dialog"));
			
			startActivity(intent);
			
		}
         
         if(preference.getKey().equals("weixin")){
  			
        	 Intent intent =new Intent();
        	 
        	 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	 
        	 intent.putExtra("Larson", true);
  			
  			intent.setComponent(new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI"));
  			
  			startActivity(intent);
  			
  			T.ShowToast(this,"启动微信中...");
  			
  		}
         
         if(preference.getKey().equals("github")){
 			
 			Intent intent =new Intent(Intent.ACTION_VIEW );
 			
 			intent.setData(Uri.parse("https://github.com/liubaoyua/CustomText"));
 			
 			startActivity(intent);
 			
 		}
         
		}catch(Exception e){
			
			T.ShowToast(this, "不存在可供跳转的应用！\nERROR:"+e.toString());
			
		}
		
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}


	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}
}	
