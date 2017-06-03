package com.mhook.dialog.Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.IInterface;

public class SPUtils
{
	public SPUtils(Context con)
	{
		
		context=con;

		FILE_NAME ="digXposed";

		MODE=Context.MODE_WORLD_READABLE|Context.MODE_WORLD_WRITEABLE;

	}

	/**
	 * 保存在手机里面的文件名
	 */
	 
	private static Context context;
	 
	public static  String FILE_NAME ="digXposed";

	public static  int MODE=Context.MODE_WORLD_READABLE|Context.MODE_WORLD_WRITEABLE;

	public  SPUtils( Context con, String sharename,int sharemode){

		context=con;
		
		FILE_NAME=sharename;

		MODE=sharemode;

	}

	public  SPUtils( Context con, String sharename){

		context=con;
		
		FILE_NAME=sharename;

		MODE=Context.MODE_WORLD_READABLE|Context.MODE_WORLD_WRITEABLE;

	}
	
	public  SPUtils( Context con,int sharemode){

		context=con;

		FILE_NAME="ShareData";

		MODE=sharemode;

	}
	
	public float GetFloat( String key, float value){

		return (Float) get(key,value);

	}

	public boolean GetBoolean( String key, boolean value){

		return (Boolean) get(key,value);

	}

	public long GetLong( String key, long value){

		return (Long) get(key,value);

	}

	public int GetInt( String key, int value){

		 return (Integer) get(key,value);

	}

	public String GetString(String key, String value){

		 return (String) get(key,value);

	}
	
	public void PutFloat( String key, float value){

		put(key,value);

	}
	
	public void PutBoolean( String key, boolean value){

		put(key,value);

	}
	
	public void PutLong(String key, long value){

		put(key,value);

	}
	
	public void PutInt( String key, int value){

		put(key,value);

	}
	
	public void PutString( String key, String value){
		
		put(key,value);
		
	}

	/**
	 * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
	 * 
	 * @param context
	 * @param key
	 * @param object
	 */
	public static void put( String key, Object object)
	{
		
		if(null==context)throw new NullPointerException("未知上下文！");

		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
															MODE);
		SharedPreferences.Editor editor = sp.edit();

		if (object instanceof String)
		{
			editor.putString(key, (String) object);
		} else if (object instanceof Integer)
		{
			editor.putInt(key, (Integer) object);
		} else if (object instanceof Boolean)
		{
			editor.putBoolean(key, (Boolean) object);
		} else if (object instanceof Float)
		{
			editor.putFloat(key, (Float) object);
		} else if (object instanceof Long)
		{
			editor.putLong(key, (Long) object);
		} else
		{
			editor.putString(key, object.toString());
		}

		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
	 * 
	 * @param context
	 * @param key
	 * @param defaultObject
	 * @return
	 */
	public static Object get(String key, Object defaultObject)
	{
		
		if(null==context)throw new NullPointerException("未知上下文！");

		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
														    MODE);

		if (defaultObject instanceof String)
		{
			return sp.getString(key, (String) defaultObject);
		} else if (defaultObject instanceof Integer)
		{
			return sp.getInt(key, (Integer) defaultObject);
		} else if (defaultObject instanceof Boolean)
		{
			return sp.getBoolean(key, (Boolean) defaultObject);
		} else if (defaultObject instanceof Float)
		{
			return sp.getFloat(key, (Float) defaultObject);
		} else if (defaultObject instanceof Long)
		{
			return sp.getLong(key, (Long) defaultObject);
		}

		return null;
	}

	/**
	 * 移除某个key值已经对应的值
	 * 
	 * @param context
	 * @param key
	 */
	public static void remove(String key)
	{
		
		if(null==context)throw new NullPointerException("未知上下文！");

		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
															MODE);
		SharedPreferences.Editor editor = sp.edit();
		editor.remove(key);
		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 清除所有数据
	 * 
	 * @param context
	 */
	public static void clear()
	{
		
		if(null==context)throw new NullPointerException("未知上下文！");

		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
															MODE);
		SharedPreferences.Editor editor = sp.edit();
		editor.clear();
		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 查询某个key是否已经存在
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean contains( String key)
	{
		
		if(null==context)throw new NullPointerException("未知上下文！");

		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
															MODE);
		return sp.contains(key);
	}

	/**
	 * 返回所有的键值对
	 * 
	 * @param context
	 * @return
	 */
	public static Map<String, ?> getAll()
	{
		
		if(null==context)throw new NullPointerException("未知上下文！");

		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
															MODE);
		return sp.getAll();
	}

	/**
	 * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
	 * 
	 * @author zhy
	 * 
	 */
	private static class SharedPreferencesCompat
	{
		private static final Method sApplyMethod = findApplyMethod();

		/**
		 * 反射查找apply的方法
		 * 
		 * @return
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private static Method findApplyMethod()
		{
			try
			{
				Class clz = SharedPreferences.Editor.class;
				return clz.getMethod("apply");
			} catch (NoSuchMethodException e)
			{
			}

			return null;
		}

		/**
		 * 如果找到则使用apply执行，否则使用commit
		 * 
		 * @param editor
		 */
		public static void apply(SharedPreferences.Editor editor)
		{
			try
			{
				if (sApplyMethod != null)
				{
					sApplyMethod.invoke(editor);
					return;
				}
			} catch (IllegalArgumentException e)
			{
			} catch (IllegalAccessException e)
			{
			} catch (InvocationTargetException e)
			{
			}
			editor.commit();
		}
	}

}


