package com.mhook.dialog;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import com.mhook.dialog.R.id;
import com.mhook.dialog.Utils.AlertUtils;
import com.mhook.dialog.Utils.AppUtils;
import com.mhook.dialog.Utils.SPUtils;
import com.mhook.dialog.Utils.T;

import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.text.*;


@SuppressLint("WorldReadableFiles")
public class AppList extends Activity {
	
	private Activity mActivity;
	private ArrayList<ApplicationInfo> appList = new ArrayList<ApplicationInfo>();
	private ArrayList<ApplicationInfo> filteredAppList = new ArrayList<ApplicationInfo>();
	private String nameFilter;
	private SharedPreferences prefs;
	
	public static final String strDisabledAlert="switchDisabledAlert";
	
	public static final String strDisabledExit="switchDisabledExit";
	
	public static final String strModule="switchModule";
	
	public static final String strModEx="switchModEx";
	
	private static final String strIcon="switchIcon";
	
	public static final String strPkgName="com.mhook.dialog";
	
	private static File prefsdir = new File(Environment.getDataDirectory()+"/data/" + "com.mhook.dialog" + "/shared_prefs" );
	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		mActivity=this;
		
		setTitle("对话框取消");
		
		super.onCreate(savedInstanceState);
		
		if((Boolean)new SPUtils(mActivity).GetBoolean(strIcon,false)){

			AppUtils.HideApkIcon(mActivity);

		}else{

			AppUtils.ShowApkIcon(mActivity);

		}

		prefs = getSharedPreferences("digXposed", Context.MODE_WORLD_READABLE|Context.MODE_WORLD_WRITEABLE);
		
		setContentView(R.layout.main);
		
		ListView list = (ListView) findViewById(R.id.lstApps);
		
		final Switch switchModule=(Switch)findViewById(R.id.switch_module);
		
		final Switch switchIcon=(Switch)findViewById(R.id.switch_icon);
		
		final Switch switchModEx=(Switch)findViewById(R.id.switch_mode_ex);
		
		switchModule.setChecked(new SPUtils(mActivity).GetBoolean(strModule,true));
		
		switchIcon.setChecked(new SPUtils(mActivity).GetBoolean(strIcon,false));
		
		switchModEx.setChecked(new SPUtils(mActivity).GetBoolean(strModEx,false));
		
		findViewById(R.id.layout_setting).setVisibility(switchModEx.isChecked()?View.VISIBLE:View.GONE);
		
		findViewById(R.id.textv_main_about).setVisibility(switchModEx.isChecked()?View.GONE:View.VISIBLE);	
		
		OnCheckedChangeListener OnCheckedChangeListener1=new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton p1, boolean p2)
			{
				
				if(p1==switchModule){
					
				T.ShowToast(mActivity,"模块已"+(p2?"激活":"关闭")+",重启应用后生效(非重启本应用)");
				
				new SPUtils(mActivity).PutBoolean(strModule,p2);
					
				}
				
				if(p1==switchIcon){
					
					T.ShowToast(mActivity,"应用图标已"+(p2?"隐藏":"显示"));
					
					if(p2){
						
						AppUtils.HideApkIcon(mActivity);
						
					}else{
						
						AppUtils.ShowApkIcon(mActivity);
						
					}
					
					new SPUtils(mActivity).PutBoolean(strIcon,p2);
					
				}
				
                    if(p1==switchModEx){
					
					T.ShowToast(mActivity,"增强模式已"+(p2?"开启":"关闭"));
					
					new SPUtils(mActivity).PutBoolean(strModEx,p2);
					
					findViewById(R.id.layout_setting).setVisibility(p2?View.VISIBLE:View.GONE);	
					
					findViewById(R.id.textv_main_about).setVisibility(p2?View.GONE:View.VISIBLE);	
					
				}
				// TODO: Implement this method
			}

		};
		
		switchIcon.setOnCheckedChangeListener(OnCheckedChangeListener1);
		
		switchModule.setOnCheckedChangeListener(OnCheckedChangeListener1);
		
		switchModEx.setOnCheckedChangeListener(OnCheckedChangeListener1);
		
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// Open settings activity when clicking on an application
				String appname = ((TextView) view.findViewById(R.id.app_name)).getText().toString();
				final String pkgName = ((TextView) view.findViewById(R.id.app_package)).getText().toString();
				
				View View1=LayoutInflater.from(mActivity).inflate(R.layout.app_settings_alert,null);

				final Switch switchDisabledAlert=(Switch)View1.findViewById(R.id.switch_disabled_alert),

						 switchDisabledExit=(Switch)View1.findViewById(R.id.switch_disabled_exit);

				switchDisabledAlert.setChecked(new SPUtils(mActivity,pkgName).GetBoolean(strDisabledAlert,false));
				
				switchDisabledExit.setChecked(new SPUtils(mActivity,pkgName).GetBoolean(strDisabledExit,false));
				
				OnClickListener OnClickListener1=new OnClickListener(){

					@Override
					public void onClick(DialogInterface p1, int p2)
					{

						new SPUtils(mActivity,pkgName).PutBoolean(strDisabledAlert,switchDisabledAlert.isChecked());
						
						new SPUtils(mActivity,pkgName).PutBoolean(strDisabledExit,switchDisabledExit.isChecked());

						T.ShowToast(mActivity,"配置已更改!正在重启应用...");
						
						AppUtils.ReStartApp(mActivity,pkgName);
						
						// TODO: Implement this method
					}
				};
				
				OnClickListener OnClickListener2=new OnClickListener(){

					@Override
					public void onClick(DialogInterface p1, int p2)
					{

                        new SPUtils(mActivity,pkgName).PutBoolean(strDisabledAlert,switchDisabledAlert.isChecked());
						
						new SPUtils(mActivity,pkgName).PutBoolean(strDisabledExit,switchDisabledExit.isChecked());

						T.ShowToast(mActivity,"配置已更改!重启应用后生效...");
						
						// TODO: Implement this method
					}
				};

				AlertUtils.DiyViewAlertWithTwoBtn(mActivity,appname,getResources().getDrawable(R.drawable.ic_launcher),View1,"保存并重启(root)",OnClickListener1,"仅保存",OnClickListener2);
				
			}
		});
		refreshApps();
	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		case R.id.menu_refresh:
			refreshApps();
			return true;
		case R.id.menu_about:
			startActivity(new Intent(getApplicationContext(), About.class));
			return true;
		case R.id.menu_exit:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void refreshApps() {
		appList.clear();
		// (re)load the list of apps in the background
		new PrepareAppsAdapter().execute();
	}


	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_SEARCH && (event.getFlags() & KeyEvent.FLAG_CANCELED) == 0) {
			SearchView searchV = (SearchView) findViewById(R.id.searchApp);
			if (searchV.isShown()) {
				searchV.setIconified(false);
				return true;
			}
		}
		return super.onKeyUp(keyCode, event);
	}

	@SuppressLint("DefaultLocale")
	private void loadApps(ProgressDialog dialog) {
		appList.clear();
		PackageManager pm = getPackageManager();
		List<PackageInfo> pkgs = getPackageManager().getInstalledPackages(PackageManager.GET_PERMISSIONS);
		dialog.setMax(pkgs.size());
		int i = 1;
		for (PackageInfo pkgInfo : pkgs) {
			dialog.setProgress(i++);
			ApplicationInfo appInfo = pkgInfo.applicationInfo;
			if (appInfo == null)
				continue;
			appInfo.name = appInfo.loadLabel(pm).toString();
			appList.add(appInfo);
		}

		Collections.sort(appList, new Comparator<ApplicationInfo>() {
			@Override
			public int compare(ApplicationInfo lhs, ApplicationInfo rhs) {
				if (lhs.name == null) {
					return -1;
				} else if (rhs.name == null) {
					return 1;
				} else {
					return lhs.name.toUpperCase().compareTo(rhs.name.toUpperCase());
				}
			}
		});
	}

	private void prepareAppList() {
		final AppListAdapter appListAdapter = new AppListAdapter(AppList.this, appList);

		((ListView) findViewById(R.id.lstApps)).setAdapter(appListAdapter);
		appListAdapter.getFilter().filter(nameFilter);
		((SearchView) findViewById(R.id.searchApp)).setOnQueryTextListener(new SearchView.OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				nameFilter = query;
				appListAdapter.getFilter().filter(nameFilter);
				((SearchView) findViewById(R.id.searchApp)).clearFocus();
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				nameFilter = newText;
				appListAdapter.getFilter().filter(nameFilter);
				return false;
			}

		});

	}


	// Handle background loading of apps
	private class PrepareAppsAdapter extends AsyncTask<Void,Void,AppListAdapter> {
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(((ListView) findViewById(R.id.lstApps)).getContext());
			dialog.setMessage(getString(R.string.app_loading));
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialog.setCancelable(false);
			if(((Switch)findViewById(R.id.switch_mode_ex)).isChecked())dialog.show();
			
		}

		@Override
		protected AppListAdapter doInBackground(Void... params) {
			if (appList.size() == 0) {
				loadApps(dialog);
			}
			return null;
		}

		@Override
		protected void onPostExecute(final AppListAdapter result) {
			prepareAppList();

			try {
				dialog.dismiss();
			} catch (Exception e) {

			}
		}
	}



	private class AppListFilter extends Filter {

		private AppListAdapter adapter;

		AppListFilter(AppListAdapter adapter) {
			super();
			this.adapter = adapter;
		}

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			// NOTE: this function is *always* called from a background thread, and
			// not the UI thread.

			ArrayList<ApplicationInfo> items = new ArrayList<ApplicationInfo>();
			synchronized (this) {
				items.addAll(appList);
			}

			FilterResults result = new FilterResults();
			if (constraint != null && constraint.length() > 0) {
				Pattern regexp = Pattern.compile(constraint.toString(), Pattern.LITERAL | Pattern.CASE_INSENSITIVE);
				for (Iterator<ApplicationInfo> i = items.iterator(); i.hasNext(); ) {
					ApplicationInfo app = i.next();
					if (!regexp.matcher(app.name == null ? "" : app.name).find()
							&& !regexp.matcher(app.packageName).find()) {
						i.remove();
					}
				}
			}
			result.values = items;
			result.count = items.size();
			return result;
		}



		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			// NOTE: this function is *always* called from the UI thread.
			filteredAppList = (ArrayList<ApplicationInfo>) results.values;
			adapter.notifyDataSetChanged();
			adapter.clear();
			for (int i = 0, l = filteredAppList.size(); i < l; i++) {
				adapter.add(filteredAppList.get(i));
			}
			adapter.notifyDataSetInvalidated();
		}
	}

	static class AppListViewHolder {
		TextView app_name;
		TextView app_package;


		AsyncTask<AppListViewHolder, Void, Drawable> imageLoader;
	}

	class AppListAdapter extends ArrayAdapter<ApplicationInfo> implements SectionIndexer {

		private Map<String, Integer> alphaIndexer;
		private String[] sections;
		private Filter filter;
		private LayoutInflater inflater;

		
		@SuppressLint("DefaultLocale")
		public AppListAdapter(Context context, List<ApplicationInfo> items) {
			super(context, R.layout.app_list_item, new ArrayList<ApplicationInfo>(items));

			filteredAppList.addAll(items);

			filter = new AppListFilter(this);
			inflater = getLayoutInflater();	
			alphaIndexer = new HashMap<String, Integer>();
			for (int i = filteredAppList.size() - 1; i >= 0; i--) {
				ApplicationInfo app = filteredAppList.get(i);
				String appName = app.name;
				String firstChar;
				if (appName == null || appName.length() < 1) {
					firstChar = "@";
				} else {
					firstChar = appName.substring(0, 1).toUpperCase();
					if (firstChar.charAt(0) > 'Z' || firstChar.charAt(0) < 'A')
						firstChar = "@";
				}

				alphaIndexer.put(firstChar, i);
			}

			Set<String> sectionLetters = alphaIndexer.keySet();

			// create a list from the set to sort
			List<String> sectionList = new ArrayList<String>(sectionLetters);
			Collections.sort(sectionList);
			sections = new String[sectionList.size()];
			sectionList.toArray(sections);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// Load or reuse the view for this row
			View row = convertView;
			AppListViewHolder holder;
			if (row == null) {
				row = inflater.inflate(R.layout.app_list_item, parent, false);
				holder = new AppListViewHolder();
				holder.app_name = (TextView) row.findViewById(R.id.app_name);
				holder.app_package = (TextView) row.findViewById(R.id.app_package);
				row.setTag(holder);
			} else {
				holder = (AppListViewHolder) row.getTag();
				holder.imageLoader.cancel(true);
			}

			final ApplicationInfo app = filteredAppList.get(position);

			holder.app_name.setText(app.name == null ? "" : app.name);
			holder.app_package.setTextColor(prefs.getBoolean(app.packageName, false)
					? Color.RED : Color.parseColor("#0099CC"));
			holder.app_package.setText(app.packageName);


			if (app.enabled) {
				holder.app_name.setPaintFlags(holder.app_name.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
				holder.app_package.setPaintFlags(holder.app_package.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
			} else {
				holder.app_name.setPaintFlags(holder.app_name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
				holder.app_package.setPaintFlags(holder.app_package.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			}

			holder.imageLoader = new AsyncTask<AppListViewHolder, Void, Drawable>() {

				@Override
				protected Drawable doInBackground(AppListViewHolder... params) {
					return app.loadIcon(getPackageManager());
				}

			}.execute(holder);

			return row;
		}

		@SuppressLint("DefaultLocale")
		@Override
		public void notifyDataSetInvalidated() {
			alphaIndexer.clear();
			for (int i = filteredAppList.size() - 1; i >= 0; i--) {
				ApplicationInfo app = filteredAppList.get(i);
				String appName = app.name;
				String firstChar;
				if (appName == null || appName.length() < 1) {
					firstChar = "@";
				} else {
					firstChar = appName.substring(0, 1).toUpperCase();
					if (firstChar.charAt(0) > 'Z' || firstChar.charAt(0) < 'A')
						firstChar = "@";
				}
				alphaIndexer.put(firstChar, i);
			}

			Set<String> keys = alphaIndexer.keySet();
			Iterator<String> it = keys.iterator();
			ArrayList<String> keyList = new ArrayList<String>();
			while (it.hasNext()) {
				keyList.add(it.next());
			}

			Collections.sort(keyList);
			sections = new String[keyList.size()];
			keyList.toArray(sections);

			super.notifyDataSetInvalidated();
		}

		@Override
		public int getPositionForSection(int section) {
			if (section >= sections.length)
				return filteredAppList.size() - 1;

			return alphaIndexer.get(sections[section]);
		}

		@Override
		public int getSectionForPosition(int position) {

			// Iterate over the sections to find the closest index
			// that is not greater than the position
			int closestIndex = 0;
			int latestDelta = Integer.MAX_VALUE;

			for (int i = 0; i < sections.length; i++) {
				int current = alphaIndexer.get(sections[i]);
				if (current == position) {
					// If position matches an index, return it immediately
					return i;
				} else if (current < position) {
					// Check if this is closer than the last index we inspected
					int delta = position - current;
					if (delta < latestDelta) {
						closestIndex = i;
						latestDelta = delta;
					}
				}
			}
			return closestIndex;
		}

		@Override
		public Object[] getSections() {
			return sections;
		}

		@Override
		public Filter getFilter() {
			return filter;
		}
	}
}
