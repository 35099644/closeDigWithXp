package com.mhook.dialog;
import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.*;
import com.mhook.dialog.Utils.*;
import android.widget.CompoundButton.*;
import android.widget.TableRow.*;
import android.content.*;
import android.content.pm.*;

public class MainActivity extends Activity
{
	
	private Activity mActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		
		mActivity=this;
		
		setTheme(android.R.style.Theme_Holo_Light);
		
		if((boolean)SPUtils.get(mActivity,"hideicon",false)){

			hia();

		}else{

			sia();

		}
		
		LinearLayout LinearLayout1=new LinearLayout(mActivity);
		
		LinearLayout1.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		
		LinearLayout1.setOrientation(LinearLayout.VERTICAL);
		
		final Switch Switch1=new Switch(mActivity);
		
		Switch1.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		
		Switch1.setText("开启模块");
		
		LinearLayout1.addView(Switch1);
		
		final Switch Switch2=new Switch(mActivity);

		Switch2.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));

		Switch2.setText("隐藏图标");
		
		LinearLayout1.addView(Switch2);
		
		TextView TextView1=new TextView(mActivity);
		
		TextView1.setText("\n\n关于:\n\n作者: S先生.\n\nQQ: 2664487933.\n\n微信号: Larson-ainixiang.\n\n以上信息可以直接长按复制！");
		
		TextView1.setTextIsSelectable(true);
		
		LinearLayout1.addView(TextView1);
		
		Switch1.setChecked(SPUtils.get(mActivity,"moduleactivation",true));
		
		Switch2.setChecked(SPUtils.get(mActivity,"hideicon",true));
		
		setContentView(LinearLayout1);
		
		OnCheckedChangeListener OnCheckedChangeListener1=new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton p1, boolean p2)
			{
				
				if(p1==Switch1){
					
				T.ShowToast(mActivity,"模块已"+(p2?"激活":"关闭")+",重启应用后生效(非重启本应用)");
				
				SPUtils.put(mActivity,"moduleactivation",p2);
					
				}
				
				if(p1==Switch2){
					
					T.ShowToast(mActivity,"应用图标已"+(p2?"隐藏":"显示"));
					
					if(p2){
						
						hia();
						
					}else{
						
						sia();
						
					}
					
					SPUtils.put(mActivity,"hideicon",p2);
					
				}
				// TODO: Implement this method
			}


		};
		
		Switch1.setOnCheckedChangeListener(OnCheckedChangeListener1);
		
		Switch2.setOnCheckedChangeListener(OnCheckedChangeListener1);
		
	}
	
	//隐藏应用图标
	public void hia(){
		getPackageManager().setComponentEnabledSetting(new ComponentName(this,getClass().getName()+"-alias"),PackageManager.COMPONENT_ENABLED_STATE_DISABLED,1);
	}
//显示图标
	public void sia(){
		getPackageManager().setComponentEnabledSetting(new ComponentName(this,getClass().getName()+"-alias"),PackageManager.COMPONENT_ENABLED_STATE_ENABLED,1);
	}
}
