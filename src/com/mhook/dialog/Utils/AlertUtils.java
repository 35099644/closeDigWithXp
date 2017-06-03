package com.mhook.dialog.Utils;

import android.content.*;
import android.app.*;
import android.view.*;
import android.view.View.*;
import android.util.*;
import android.graphics.drawable.*;

public class AlertUtils
{
	//普通提示对话框
	public static void Alert(Context con,String msg){

		ThreeBtnAlert(con,"提示",null,msg,null,"",null,"",null,"",null);

	}
	//自定义view对话框

	public static void DiyViewAlert(Context con,String title,View view){

		ThreeBtnAlert(con,title,null,"",view,"",null,"",null,"",null);

	}

	public static void DiyViewAlert(Context con,String title,Drawable icon,View view){

		ThreeBtnAlert(con,title,icon,"",view,"",null,"",null,"",null);

	}

	//带一个按钮的自定义view对话框

	public static void DiyViewAlertWithOneBtn(Context con,String title,View view,

											  String positivestr,DialogInterface.OnClickListener positiveclick){

		ThreeBtnAlert(con,title,null,"",view,positivestr,positiveclick,"",null,"",null);

	}

	public static void DiyViewAlertWithOneBtn(Context con,String title,Drawable icon,View view,

											  String positivestr,DialogInterface.OnClickListener positiveclick){

		ThreeBtnAlert(con,title,icon,"",view,positivestr,positiveclick,"",null,"",null);

	}

	//带两个按钮的自定义view对话框

	public static void DiyViewAlertWithTwoBtn(Context con,String title,View view,

											  String positivestr,DialogInterface.OnClickListener positiveclick,

											  String negativestr,DialogInterface.OnClickListener negativeclick){

		ThreeBtnAlert(con,title,null,"",view,positivestr,positiveclick,negativestr,negativeclick,"",null);

	}

	public static void DiyViewAlertWithTwoBtn(Context con,String title,Drawable icon,View view,

											  String positivestr,DialogInterface.OnClickListener positiveclick,

											  String negativestr,DialogInterface.OnClickListener negativeclick){

		ThreeBtnAlert(con,title,icon,"",view,positivestr,positiveclick,negativestr,negativeclick,"",null);

	}

	//一个按钮的对话框

	public static void OneBtnAlert(Context con,String title,String msg,String positivestr,DialogInterface.OnClickListener positiveclick){

		ThreeBtnAlert(con,title,null,msg,null,positivestr,positiveclick,"",null,"",null);
	}


	public static void OneBtnAlert(Context con,String title,Drawable icon,String msg,String positivestr,DialogInterface.OnClickListener positiveclick){

		ThreeBtnAlert(con,title,icon,msg,null,positivestr,positiveclick,"",null,"",null);
	}

	//两个按钮的对话框

	public static void TwoBtnAlert(Context con,String title,String msg,String positivestr,DialogInterface.OnClickListener positiveclick,

								   String negativestr,DialogInterface.OnClickListener negativeclick

								   ){

		ThreeBtnAlert(con,title,null,msg,null,positivestr,positiveclick,negativestr,negativeclick,"",null);

	}

	public static void TwoBtnAlert(Context con,String title,Drawable icon,String msg,String positivestr,DialogInterface.OnClickListener positiveclick,

								   String negativestr,DialogInterface.OnClickListener negativeclick

								   ){

		ThreeBtnAlert(con,title,icon,msg,null,positivestr,positiveclick,negativestr,negativeclick,"",null);

	}

	//详细对话框

	public static void ThreeBtnAlert(Context con,String title,Drawable icon,String msg,View view,

									 String positivestr,DialogInterface.OnClickListener positiveclick,

									 String negativestr,DialogInterface.OnClickListener negativeclick,

									 String neutralstr,DialogInterface.OnClickListener neutralclick

									 ){

		if(con==null)return;

		AlertDialog.Builder alert=new AlertDialog.Builder(con);

		if(!title.equals("")){

			alert.setTitle(title);

		}

		if(icon!=null){

			alert.setIcon(icon);

		}

		if(view==null){

			if(!msg.equals("")){

				alert.setMessage(msg);

			}

		}else{

			alert.setView(view);

		}

		if(!positivestr.equals("")&positiveclick!=null)

			alert.setPositiveButton(positivestr,positiveclick);

		if(!negativestr.equals("")&negativeclick!=null)

			alert.setNegativeButton(negativestr,negativeclick);

		if(!neutralstr.equals("")&neutralclick!=null)

			alert.setNeutralButton(neutralstr,neutralclick);

		alert.create().show();

	}



}

