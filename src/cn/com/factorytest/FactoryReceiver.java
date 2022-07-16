package cn.com.factorytest;

import java.io.File;
import java.io.IOException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import java.io.IOException;

public class FactoryReceiver extends BroadcastReceiver{
	private static final String TAG = Tools.TAG;
	//检测U盘 udiskfile 启动产测apk
	private static final String udiskfile = "khadas_test.xml";
	private static final String ageing_udiskfile4 = "khadas_test_4.xml";
	private static final String ageing_udiskfile8 = "khadas_test_8.xml";
	private static final String ageing_udiskfile12 = "khadas_test_12.xml";
	private static final String ageing_udiskfile24 = "khadas_test_24.xml";
	private static final String rebootfile = "khadas_reboot.xml";
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
           Bundle bundle = new Bundle();
           if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
                String mac = Tools.getMac();
                if (mac.equals("00:00:00:00:00:00")) {
                   mac = Tools.getSharedPreference(context);
                   if (!mac.equals("00:00:00:00:00:00")) {
                       String cmd = String.format("setbootenv ubootenv.var.factory_mac %s", mac);
                       try {
                           Process exeCmd = Runtime.getRuntime().exec(cmd);
                       } catch (IOException e) {
                           Log.e(TAG, "Excute exception: " + e.getMessage());
                       }
                   }
                }
                Log.d(TAG, "Factory action="+action);
		return;
            }
            Log.d(TAG, "Factory action="+action);
		Uri uri = intent.getData();

		if (uri.getScheme().equals("file")) {
			String path = uri.getPath();
            String externalStoragePath = Environment.getExternalStorageDirectory().getPath();
            String legacyPath = Environment.getLegacyExternalStorageDirectory().getPath();

            try {
                path = new File(path).getCanonicalPath();
            } catch (IOException e) {
                Log.e(TAG, "couldn't canonicalize " + path);
                return;
            }
            if (path.startsWith(legacyPath)) {
                path = externalStoragePath + path.substring(legacyPath.length());                                                          
            }

			if (Intent.ACTION_MEDIA_MOUNTED.equals(action)) {
				String rebootfullpath = path+"/"+rebootfile;
				File rebootfile = new File(rebootfullpath);
				if(rebootfile.exists() && rebootfile.isFile()){
					try {
					Thread.sleep(10000);
					Process proc = Runtime.getRuntime().exec(new String[]{"reboot"});
					proc.waitFor();
					} catch (Exception e){
						e.printStackTrace();
					}
					return;
				}
				String fullpath = path+"/"+udiskfile;
				File file = new File(fullpath);
				 if(file.exists() && file.isFile()){
					 try {
						Thread.sleep(2000);
					 } catch (InterruptedException e) {
						 e.printStackTrace();
					 }
					 Intent i = new Intent();
					 bundle.putInt("ageing_flag", 0);
					 i.putExtras(bundle);
					 i.setClassName("cn.com.factorytest", "cn.com.factorytest.MainActivity");
					 i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					 context.startActivity(i);
				 }
				 else{
					 String ageing_fullpath4 = path+"/"+ageing_udiskfile4;
					 File ageing_file4 = new File(ageing_fullpath4);
					  if(ageing_file4.exists() && ageing_file4.isFile()){
						  try {
							 Thread.sleep(2000);
						  } catch (InterruptedException e) {
							  e.printStackTrace();
						  }
						  Intent i = new Intent();
						  bundle.putInt("ageing_flag", 1);
						  bundle.putInt("ageing_time", 4);
						  i.putExtras(bundle);  
						  i.setClassName("cn.com.factorytest", "cn.com.factorytest.MainActivity");
						  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						  context.startActivity(i);
					  }
					  else{
						 String ageing_fullpath12 = path+"/"+ageing_udiskfile12;
						 File ageing_file12 = new File(ageing_fullpath12);
						  if(ageing_file12.exists() && ageing_file12.isFile()){
							  try {
								 Thread.sleep(2000);
							  } catch (InterruptedException e) {
								  e.printStackTrace();
							  }
							  Intent i = new Intent();
							  bundle.putInt("ageing_flag", 1);
							  bundle.putInt("ageing_time", 12);
							  i.putExtras(bundle);
							  i.setClassName("cn.com.factorytest", "cn.com.factorytest.MainActivity");
							  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							  context.startActivity(i);
						  }
						  else{
							 String ageing_fullpath24 = path+"/"+ageing_udiskfile24;
							 File ageing_file24 = new File(ageing_fullpath24);
							  if(ageing_file24.exists() && ageing_file24.isFile()){
								  try {
									 Thread.sleep(2000);
								  } catch (InterruptedException e) {
									  e.printStackTrace();
								  }
								  Intent i = new Intent();
								  bundle.putInt("ageing_flag", 1);
								  bundle.putInt("ageing_time", 24);
								  i.setClassName("cn.com.factorytest", "cn.com.factorytest.MainActivity");
								  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								  context.startActivity(i);
							  }
							   else{
								  String ageing_fullpath8 = path+"/"+ageing_udiskfile8;
								  File ageing_file8 = new File(ageing_fullpath8);
								   if(ageing_file8.exists() && ageing_file8.isFile()){
									   try {
										  Thread.sleep(2000);
									   } catch (InterruptedException e) {
										   e.printStackTrace();
									   }
									   Intent i = new Intent();
									   bundle.putInt("ageing_flag", 1);
									   bundle.putInt("ageing_time", 8);
									   i.putExtras(bundle);
									   i.setClassName("cn.com.factorytest", "cn.com.factorytest.MainActivity");
									   i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									   context.startActivity(i);
								   }
							  }
						 }
					 }
				 }
			}
		}
		
	}
	
}
