package com.mhook.dialog.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast统一管理类
 * 
 */

public class T
{

	private T()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	static Toast toast=null;

	public static void ShowToast(Context con,CharSequence message){

		ShowToast(con,message,0,Toast.LENGTH_SHORT);

	}

	public static void ShowToast(Context con,CharSequence message,int msg ,int showtime){

		if(toast==null){

			if(message.equals("")|message==null){

				toast=Toast.makeText(con,msg,Toast.LENGTH_LONG);

			}else{

				toast=Toast.makeText(con,message,Toast.LENGTH_LONG);

			}

		}else{

			toast.setText(message);

		}

		toast.show();


	} 

	public static boolean isShow = false;

	/**
	 * 短时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showShort(Context context, CharSequence message)
	{
		if (isShow)
			ShowToast(context,message,0,Toast.LENGTH_SHORT);
	}

	/**
	 * 短时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showShort(Context context, int message)
	{
		if (isShow)
			ShowToast(context,"",message,Toast.LENGTH_SHORT);
	}

	/**
	 * 长时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showLong(Context context, CharSequence message)
	{
		if (isShow)
			ShowToast(context,message,0,Toast.LENGTH_LONG);
	}

	/**
	 * 长时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showLong(Context context, int message)
	{
		if (isShow)
			ShowToast(context,"",message,Toast.LENGTH_LONG);

	}

}

