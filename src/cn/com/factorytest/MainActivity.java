package cn.com.factorytest;

import android.app.Activity;
import android.app.StatusBarManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiConfiguration;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.os.StatFs;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;
import android.text.Editable;
import android.text.format.Formatter;
import android.Manifest;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.hardware.Camera;
import android.view.Gravity;
import android.os.SystemProperties;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Enumeration;
import java.net.*;
import java.math.*;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.WindowManager;
import android.util.DisplayMetrics;

import com.google.gson.Gson;
import cn.com.factorytest.encoding.EncodingUtils;
import android.graphics.Bitmap;
import android.text.TextUtils;
import java.lang.reflect.Method;

public class MainActivity extends Activity {

    public static final String TAG = Tools.TAG;

    public static String test_board = "VIM3";
    public static String udisk_backup = "";

    public static String check_mcu_ver = "";
    public static String check_fw_ver = "";

    public static boolean tfcard_test = false;
    private static boolean tfcard_test_ret = false;

    public static boolean usb20_test = false;
    private static boolean usb20_test_ret = false;

    public static boolean usb30_test = false;
    private static boolean usb30_test_ret = false;

    public static boolean spi_test = false;
    private static boolean spi_test_ret = false;

    public static boolean wifi_test = false;
    private static boolean wifi_test_ret = false;

    public static boolean bt_test = false;
    private static boolean bt_test_ret = false;

    public static boolean led_test = false;
    private static boolean led_test_ret = false;

    public static boolean mcu_test = false;
    private static boolean mcu_test_ret = false;

    public static boolean hdmi_test = false;
    private static boolean hdmi_test_ret = false;

    public static boolean fusb302_test = false;
    private static boolean fusb302_test_ret = false;

    public static boolean gsensor_test = false;
    private static boolean gsensor_test_ret = false;

    public static boolean rtc_test = false;
    private static boolean rtc_test_ret = false;

    public static boolean lan_test = false;
    private static boolean lan_test_ret = false;

    public static boolean gigabit_test = false;
    private static boolean gigabit_test_ret = false;

    public static boolean board_key_test = false;
    private static boolean board_key_test_ret = false;

    public static boolean ageing_test = false;
    private static boolean ageing_test_ret = false;

    private static boolean ageing_test_ok_flag = false;

    public static boolean key_test = false;
    private static boolean key_test_ret = false;

    public static boolean irkey_test = false;
    private static boolean irkey_test_ret = false;

    public static boolean mipi_camera_test = false;
    private static boolean mipi_camera_test_ret = false;

    public static boolean mipi_lcd_test = false;
    private static boolean mipi_lcd_test_ret = false;

    public static boolean tp_test = false;
    private static boolean tp_test_ret = false;

    public static boolean wirte_mac = false;
    private static boolean wirte_mac_ret = false;

    public static boolean burn_efuse_flag = false;
    private static boolean burn_efuse_flag_ret = false;

    public static boolean reset_mcu = false;
    private static boolean reset_mcu_ret = false;

    public static boolean power_led_test = false;
    private static boolean power_led_test_ret = false;

    public static boolean device_id_test = false;
    private static boolean device_id_test_ret = false;

    TextView m_mcu_version;
    TextView m_firmware_version;
    TextView m_ddr_size;
    TextView m_nand_size;
    TextView m_device_type;
    TextView m_macvalue;
    TextView m_snvalue;
    TextView m_TextView_MacLabel;
    TextView m_ip;
    TextView m_wifimac;
    TextView m_wifiip;
    TextView m_device_id;
    TextView m_TextView_CPU_THERMAL;
    TextView m_TextView_CPU_FREQ;

    TextView m_mactitle;
    EditText m_maccheck;
    TextView m_TextView_Time;
    TextView m_TextView_TF;
    TextView m_TextView_USB1;
    TextView m_TextView_USB2;
    TextView m_TextView_SPI;
    TextView m_TextView_KEY;
    TextView m_TextView_Wifi;
    TextView m_TextView_BT;

    TextView m_TextView_MCU;
    TextView m_TextView_HDMI;
    TextView m_TextView_FUSB302;
    TextView m_TextView_GSENSOR;
    TextView m_TextView_Rtc;
    TextView m_TextView_Lan;
    TextView m_TextView_Gigabit;
    TextView m_TextView_AGEING;

    TextView m_TextView_NetLed;
    Button m_Button_NetLed;
    Button m_Button_PowerLed;
    TextView m_TextView_Led;
    Button m_Button_Key;
    Button m_Button_IRKey;
    Button m_Button_Mipi_Camera;
    Button m_Button_Mipi_LCD;
    Button m_Button_TP;
    Button m_Button_EnableWol;
    Button m_Button_write_mac_usid;
    Button m_Button_Rst_MCU;
    ImageView m_ImageView_infoBarCode;

    Handler mHandler = new FactoryHandler();

    private final int MSG_WIFI_TEST_ERROR =  77;
    private final int MSG_WIFI_TEST_OK =  78;
    private final int MSG_LAN_TEST_ERROR =  79;
    private final int MSG_LAN_TEST_OK =  80;
    private final int MSG_TF_TEST_ERROR =  81;
    private final int MSG_TF_TEST_OK =  82;
    private final int MSG_USB1_TEST_ERROR = 83;
    private final int MSG_USB1_TEST_OK =  84;
    private final int MSG_USB2_TEST_ERROR =  85;
    private final int MSG_USB2_TEST_OK =  86;
    private final int MSG_NETLED_TEST_Start =  87;
    private final int MSG_NETLED_TEST_End =  88;
    private final int MSG_POWERLED_TEST_Start =  89;
    private final int MSG_POWERLED_TEST_End =  90;
    private final int MSG_WIFI_TOAST =  91;
    private final int MSG_PLAY_VIDEO =  92;
    private final int MSG_TF_TEST_XL_OK = 93;
    private final int MSG_TF_TEST_XL_ERROR = 94;
    private final int MSG_USB1_TEST_XL_OK = 95;
    private final int MSG_USB1_TEST_XL_ERROR = 96;
    private final int MSG_android_6_0_TEXT_LAYOUT = 97;
    private final int MSG_USB2_TEST_XL_OK = 98;
    private final int MSG_USB2_TEST_XL_ERROR = 99;
    private final int MSG_RTC_TEST_OK = 100;
    private final int MSG_RTC_TEST_ERROR = 101;
    private final int MSG_BT_TEST_ERROR =  102;
    private final int MSG_BT_TEST_OK =  103;
    private final int MSG_MCU_TEST_ERROR =  104;
    private final int MSG_MCU_TEST_OK =  105;
    private final int MSG_HDMI_TEST_ERROR =  106;
    private final int MSG_HDMI_TEST_OK =  107;
    private final int MSG_SPI_TEST_ERROR =  108;
    private final int MSG_SPI_TEST_OK =  109;
    private final int MSG_GIGABIT_TEST_ERROR =  110;
    private final int MSG_GIGABIT_TEST_OK =  111;
    private final int MSG_FUSB302_TEST_ERROR =  112;
    private final int MSG_FUSB302_TEST_OK =  113;
    private final int MSG_GSENSOR_TEST_ERROR =  114;
    private final int MSG_GSENSOR_TEST_OK =  115;
    private final int MSG_AGEING_TEST_ERROR =	116;
    private final int MSG_AGEING_TEST_OK =  117;
    private final int MSG_KEY_TEST_ERROR = 118;
    private final int MSG_KEY_TEST_OK = 119;
    private final int MSG_GET_CPU_STATUS = 120;
    private final int MSG_TIME = 777;

    private final int MSG_TEST_RET_UPDATE = 1000;

    private static final String nullip = "0.0.0.0";
    private static final String USB_PATH = (Tools.isAndroid5_1_1() ? "/storage/udisk" : "/storage/external_storage/sd");
    private static final String USB1_PATH = (Tools.isAndroid5_1_1() ? "/storage/udisk0" : "/storage/external_storage/sda");
    private static final String USB2_PATH = (Tools.isAndroid5_1_1() ? "/storage/udisk1" : "/storage/external_storage/sdb");
    private static final String TFCARD_PATH = (Tools.isAndroid5_1_1() ? "/storage/sdcard" : "/storage/external_storage/sdcard");
    private static final String SYS_ADC_TABLE = "sys/class/adc_keypad/table";
    private List<ScanResult> wifiList;

    String configSSID =  "";
    int configLevel = 60;

    int wifiLevel = 0;
    String usb_path = "";
    LinearLayout mLeftLayout, mBottomLayout, mBottomLayout2, mBottomLayout3, mBottomLayout4, mBottomLayout5, mBottomLayout6;
    String configFile = "";
    int tag_net = 0;
    int tag_power = 0;
    AudioManager mAudioManager = null;
    int maxVolume;
    int currentVolume;
    int currentkeyvalue;
    String lssue_value = "";
    String client_value = "";
    String readMac = "";
    String readSn = "";
    String readDeviceid = "";

    private boolean bIsKeyDown = false;
    //系统灯和网络灯测试时间 单位s
    int ledtime = 60;
    //videoview 全屏播放时间
    //private final long  MSG_PLAY_VIDEO_TIME= 30 * 60 * 1000;

    private Context mContext;
    private BTDeviceReceiver mBTDeviceReceiver;
    private int CONFIG_BT_RSSI = -100;
    private boolean BT_ERR = true;
    private int BT_try_count = 2;
    private int btLevel = 0;
    private final String BTSSID = "Khadas";
    public static int key_flag = 0;
    public static int ageing_flag = 0;
    public static int ageing_time = 0;
    public static int ageing_cpu_max = 0;
    private StatusBarManager mStatusBarManager;
    private AlertDialog verAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mStatusBarManager = (StatusBarManager) mContext.getSystemService(Context.STATUS_BAR_SERVICE);
        mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

        maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);

        m_TextView_TF = (TextView)findViewById(R.id.TextView_TF);
        m_TextView_USB1 = (TextView)findViewById(R.id.TextView_USB1);
        m_TextView_USB2 = (TextView)findViewById(R.id.TextView_USB2);
        m_TextView_SPI = (TextView)findViewById(R.id.TextView_SPI);
        m_TextView_Wifi = (TextView)findViewById(R.id.TextView_Wifi);
        m_TextView_BT = (TextView)findViewById(R.id.TextView_BT);
        m_TextView_Led = (TextView)findViewById(R.id.LedTest);

        m_TextView_MCU = (TextView)findViewById(R.id.TextView_MCU);
        m_TextView_HDMI = (TextView)findViewById(R.id.TextView_HDMI);
        m_TextView_FUSB302 = (TextView)findViewById(R.id.TextView_FUSB302);
        m_TextView_GSENSOR = (TextView)findViewById(R.id.TextView_GSENSOR);
        m_TextView_Rtc = (TextView)findViewById(R.id.TextView_Rtc);
        m_TextView_Lan = (TextView)findViewById(R.id.TextView_Lan);
        m_TextView_Gigabit = (TextView)findViewById(R.id.TextView_Gigabit);
        m_TextView_AGEING = (TextView)findViewById(R.id.TextView_AGEING);

        m_TextView_KEY = (TextView) findViewById(R.id.TextView_KEY);
        m_Button_EnableWol = (Button)findViewById(R.id.EnableWol);
        m_Button_Key = (Button) findViewById(R.id.KeyTest);
        m_Button_IRKey = (Button) findViewById(R.id.IRKeyTest);
        m_Button_Mipi_Camera = (Button) findViewById(R.id.Mipi_Camera);
        m_Button_Mipi_LCD = (Button) findViewById(R.id.Mipi_LCD);
        m_Button_TP = (Button) findViewById(R.id.TP_Test);
        m_Button_write_mac_usid = (Button)findViewById(R.id.Button_Writemac);
        m_Button_Rst_MCU = (Button)findViewById(R.id.Button_RstMcu);

        m_TextView_Time = (TextView)findViewById(R.id.TextView_Time);

        m_TextView_MacLabel = (TextView)findViewById(R.id.mac_label);
        m_macvalue = (TextView)findViewById(R.id.mac_value);
        m_ip = (TextView)findViewById(R.id.ip_value);
        m_wifimac = (TextView)findViewById(R.id.wifi_mac_value);
        m_wifiip = (TextView)findViewById(R.id.wifi_ip_value);
        m_device_id = (TextView)findViewById(R.id.device_id_value);
        m_device_type = (TextView)findViewById(R.id.device_type_value);
        //m_snvalue = (TextView)findViewById(R.id.sn_value);
        m_mcu_version = (TextView)findViewById(R.id.mcu_version_value);
        m_firmware_version = (TextView)findViewById(R.id.firmware_version_value);
        m_ddr_size = (TextView)findViewById(R.id.ddr_size_value);
        m_nand_size = (TextView)findViewById(R.id.nand_size_value);
        m_TextView_CPU_THERMAL = (TextView) findViewById(R.id.cpu_thermal_value);
        m_TextView_CPU_FREQ = (TextView) findViewById(R.id.cpu_freq_value);

        m_Button_PowerLed = (Button)findViewById(R.id.Button_PowerLed);
        m_TextView_NetLed = (TextView)findViewById(R.id.Button_NetLed);

        if (MainActivity.burn_efuse_flag) {
            m_Button_write_mac_usid.setText(R.string.WriteMac2);
        }

        if (!tfcard_test) {
            m_TextView_TF.setVisibility(View.GONE);
        }

        if (!usb20_test) {
            m_TextView_USB1.setVisibility(View.GONE);
        }

        if (!usb30_test) {
            m_TextView_USB2.setVisibility(View.GONE);
        }

        if (!spi_test) {
            m_TextView_SPI.setVisibility(View.GONE);
        }

        if (!bt_test) {
            m_TextView_BT.setVisibility(View.GONE);
        }

        if (!wifi_test) {
            m_TextView_Wifi.setVisibility(View.GONE);
        }

        if (!led_test) {
            m_TextView_Led.setVisibility(View.GONE);
        }

        if (!mcu_test) {
            m_TextView_MCU.setVisibility(View.GONE);
        }

        if (!hdmi_test) {
            m_TextView_HDMI.setVisibility(View.GONE);
        }

        if (!fusb302_test) {
            m_TextView_FUSB302.setVisibility(View.GONE);
        }

        if (!gsensor_test) {
            m_TextView_GSENSOR.setVisibility(View.GONE);
        }

        if (!rtc_test) {
            m_TextView_Rtc.setVisibility(View.GONE);
        }

        if (!lan_test) {
            m_TextView_Lan.setVisibility(View.GONE);
        }

        if (!gigabit_test) {
            m_TextView_Gigabit.setVisibility(View.GONE);
        }

        if (!board_key_test) {
            m_Button_Key.setVisibility(View.GONE);
        }

        if (!ageing_test) {
            m_TextView_AGEING.setVisibility(View.GONE);
        }

        if (!key_test) {
            m_TextView_KEY.setVisibility(View.GONE);
        }
        if (key_test) {
            Tools.exec("echo 1 > /sys/class/mcu/key_test");
            currentkeyvalue = SystemProperties.getInt("persist.sys.func.key.action", 3);
            setFunKey(114);
        }

        if (!irkey_test) {
            m_Button_IRKey.setVisibility(View.GONE);
        }

        if (!mipi_camera_test) {
            m_Button_Mipi_Camera.setVisibility(View.GONE);
        }

        if (!mipi_lcd_test) {
            m_Button_Mipi_LCD.setVisibility(View.GONE);
        }

        if (!tp_test) {
            m_Button_TP.setVisibility(View.GONE);
        }

        if (!wirte_mac) {
            m_Button_write_mac_usid.setVisibility(View.GONE);
        }

        if (!reset_mcu) {
            m_Button_Rst_MCU.setVisibility(View.GONE);
        }

        if (!power_led_test) {
            m_Button_PowerLed.setVisibility(View.GONE);
        }

        if (!device_id_test) {
            m_device_id.setVisibility(View.GONE);
        }

        m_maccheck = (EditText)findViewById(R.id.EditTextMac);
        m_maccheck.setInputType(InputType.TYPE_NULL);
        m_maccheck.addTextChangedListener(mTextWatcher);
        m_mactitle = (TextView)findViewById(R.id.MacTitle);

        mLeftLayout = (LinearLayout) findViewById(R.id.Layout_Left);
        mBottomLayout = (LinearLayout) findViewById(R.id.Layout_Bottom);
        mBottomLayout2 = (LinearLayout) findViewById(R.id.Layout_Bottom2);
        mBottomLayout3 = (LinearLayout) findViewById(R.id.Layout_Bottom3);
        mBottomLayout4 = (LinearLayout) findViewById(R.id.Layout_Bottom4);
        mBottomLayout5 = (LinearLayout) findViewById(R.id.Layout_Bottom5);
        //mBottomLayout6 = (LinearLayout) findViewById(R.id.Layout_Bottom6);

        mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        mWifiManager.setWifiEnabled(true);

        updateTime();
        new Thread() {
            public void run() {
                if (ageing_test) {
                    test_cpu_ageing();
                }
                while (true) {
                    try {
                        unregisterBTReceiver();
                        test_Thread();
                        Thread.sleep(10 * 1000);
                    } catch (Exception localException1) {
                    }
                }
            }
        } .start();

        //if (Build.MODEL.equals("VIM3") || Build.MODEL.equals("VIM3L") || Build.MODEL.equals("VIM1")) {
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        mHandler.sendEmptyMessage(MSG_GET_CPU_STATUS);
                        mHandler.sendEmptyMessage(MSG_TEST_RET_UPDATE);
                        Thread.sleep(1000);
                        if(0 == ageing_flag) {
                            Tools.writeFile(Tools.Red_Led, "2");
                            Tools.writeFile(Tools.White_Led, "default-on");
                            Thread.sleep(1000);
                            Tools.writeFile(Tools.White_Led, "off");
                            Tools.writeFile(Tools.Red_Led, "1");
                            Thread.sleep(1000);
                        }
                        //else
                        //test_cpu_ageing();
                        if(2 == VideoFragment.ageing_test_step && !ageing_test_ok_flag) {
                            mHandler.sendEmptyMessage(MSG_AGEING_TEST_OK);
                            ageing_test_ok_flag = true;
                        } else if(1 == VideoFragment.ageing_test_step && ageing_test_ok_flag) {
                            mHandler.sendEmptyMessage(MSG_AGEING_TEST_ERROR);
                            ageing_test_ok_flag = false;
                        }
                    } catch (Exception localException1) {
                    }
                }
            }
        } .start();

        m_ImageView_infoBarCode = (ImageView)findViewById(R.id.iv_infoBarCode);
        m_ImageView_infoBarCode.setVisibility(View.GONE);
    }

    private void checkVersionInfo() {
        if ( (!TextUtils.isEmpty(check_mcu_ver) && !check_mcu_ver.equals(getMCUVersion())) ||
                (!TextUtils.isEmpty(check_fw_ver) && !check_fw_ver.equals(Build.DISPLAY)) ) {
            if (verAlertDialog == null) {

                String verInfo = getResources().getString(R.string.mcu_version) + getMCUVersion()  + "\n" +
                                 getResources().getString(R.string.require_mcu_version)  + check_mcu_ver + "\n" +
                                 getResources().getString(R.string.firmware_version) + Build.DISPLAY  + "\n" +
                                 getResources().getString(R.string.require_fw_version)  + check_fw_ver + "\n";

                WindowManager windowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
                DisplayMetrics displayMetrics = new DisplayMetrics();
                windowManager.getDefaultDisplay().getMetrics(displayMetrics);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.version_error);
                builder.setMessage(verInfo);
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                verAlertDialog = builder.create();

                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(verAlertDialog.getWindow().getAttributes());
                layoutParams.width = displayMetrics.widthPixels / 3;
                layoutParams.height = displayMetrics.heightPixels / 3;
                verAlertDialog.getWindow().setAttributes(layoutParams);
            }
            verAlertDialog.show();

        }
    }

    private void checkTestRetUpate() {
        checkVersionInfo();

        Log.d("TESTINFO", "=================================checkTestRetUpate start===========================================");
        Log.d("TESTINFO", "tfcard_test " + tfcard_test +  " tfcard_test_ret " +  tfcard_test_ret + " RET " + (tfcard_test ? (tfcard_test & tfcard_test_ret) : true));
        Log.d("TESTINFO", "usb20_test " + usb20_test +  " usb20_test_ret " +  usb20_test_ret + " RET " + (usb20_test ? (usb20_test & usb20_test_ret) : true));
        Log.d("TESTINFO", "usb30_test " + usb30_test +  " usb30_test_ret " +  usb30_test_ret + " RET " + (usb30_test ? (usb30_test & usb30_test_ret) : true));
        //        Log.d("TESTINFO", "spi_test " + spi_test +  " spi_test_ret " +  spi_test_ret + " RET " + (spi_test ? (spi_test & spi_test_ret) : true));
        Log.d("TESTINFO", "gsensor_test " + gsensor_test +  " gsensor_test_ret " +  gsensor_test_ret + " RET " +  (gsensor_test ? (gsensor_test & gsensor_test_ret) : true));
        Log.d("TESTINFO", "mcu_test " + mcu_test +  " mcu_test_ret " +  mcu_test_ret + " RET " + (mcu_test ? (mcu_test & mcu_test_ret) : true));
        Log.d("TESTINFO", "hdmi_test " + hdmi_test +  " hdmi_test_ret " +  hdmi_test_ret + " RET " +  (hdmi_test ? (hdmi_test & hdmi_test_ret) : true));
        Log.d("TESTINFO", "fusb302_test " + fusb302_test +  " fusb302_test_ret " +  fusb302_test_ret + " RET " + (fusb302_test ? (fusb302_test & fusb302_test_ret) : true));
        Log.d("TESTINFO", "wifi_test " + wifi_test +  " wifi_test_ret " +  wifi_test_ret + " RET " + (wifi_test ? (wifi_test & wifi_test_ret) : true));
        Log.d("TESTINFO", "bt_test " + bt_test +  " bt_test_ret " +  bt_test_ret + " RET " + (bt_test ? (bt_test & bt_test_ret) : true));
        Log.d("TESTINFO", "rtc_test " + rtc_test +  " rtc_test_ret " +  rtc_test_ret + " RET " + (rtc_test ? (rtc_test & rtc_test_ret) : true));
        Log.d("TESTINFO", "ageing_test " + ageing_test +  " ageing_test_ret " +  ageing_test_ret + " RET " + (ageing_test ? (ageing_test & ageing_test_ret) : true));
        Log.d("TESTINFO", "power_led_test " + power_led_test +  " power_led_test_ret " +  power_led_test_ret + " RET " + (power_led_test ? (power_led_test & power_led_test_ret) : true));
        Log.d("TESTINFO", "irkey_test " + irkey_test +  " irkey_test_ret " +  irkey_test_ret + " RET " + (irkey_test ? (irkey_test & irkey_test_ret) : true));
        Log.d("TESTINFO", "mipi_camera_test " + mipi_camera_test +  " mipi_camera_test_ret " +  mipi_camera_test_ret + " RET " + (mipi_camera_test ? (mipi_camera_test & mipi_camera_test_ret) : true));
        Log.d("TESTINFO", "board_key_test " + board_key_test +  " board_key_test_ret " +  board_key_test_ret + " RET " + (board_key_test ? (board_key_test & board_key_test_ret) : true));
        Log.d("TESTINFO", "key_test " + key_test +  " key_test_ret " +  key_test_ret + " RET " + (key_test ? (key_test & key_test_ret) : true));
        Log.d("TESTINFO", "reset_mcu " + reset_mcu +  " reset_mcu_ret " +  reset_mcu_ret + " RET " + (reset_mcu ? (reset_mcu & reset_mcu_ret) : true));
        Log.d("TESTINFO", "mipi_lcd_test " + mipi_lcd_test +  " mipi_lcd_test_ret " +  mipi_lcd_test_ret + " RET " + (mipi_lcd_test ? (mipi_lcd_test & mipi_lcd_test_ret) : true));
        Log.d("TESTINFO", "tp_test " + tp_test +  " tp_test_ret " +  tp_test_ret + " RET " + (tp_test ? (tp_test & tp_test_ret) : true));
        Log.d("TESTINFO", "wirte_mac " + wirte_mac +  " wirte_mac_ret " +  wirte_mac_ret + " RET " + (wirte_mac ? (wirte_mac & wirte_mac_ret) : true));
        Log.d("TESTINFO", "ageing_test_ok_flag " + ageing_test_ok_flag);
        Log.d("TESTINFO", "=================================checkTestRetUpate end===========================================");

        if((tfcard_test ? (tfcard_test & tfcard_test_ret) : true) && (usb20_test ? (usb20_test & usb20_test_ret) : true) &&
                (usb30_test ? (usb30_test & usb30_test_ret) : true) &&
                //                (spi_test ? (spi_test & spi_test_ret) : true) &&
                (gsensor_test ? (gsensor_test & gsensor_test_ret) : true) && (mcu_test ? (mcu_test & mcu_test_ret) : true) &&
                (hdmi_test ? (hdmi_test & hdmi_test_ret) : true) &&
                (fusb302_test ? (fusb302_test & fusb302_test_ret) : true) &&
                (wifi_test ? (wifi_test & wifi_test_ret) : true) &&
                (bt_test ? (bt_test & bt_test_ret) : true) && (rtc_test ? (rtc_test & rtc_test_ret) : true) &&
                (ageing_test ? (ageing_test & ageing_test_ret) : true) && (power_led_test ? (power_led_test & power_led_test_ret) : true) &&
                (irkey_test ? (irkey_test & irkey_test_ret) : true) &&
                (mipi_camera_test ? (mipi_camera_test & mipi_camera_test_ret) : true) &&
                (board_key_test ? (board_key_test & board_key_test_ret) : true) && (key_test ? (key_test & key_test_ret) : true) &&
                (reset_mcu ? (reset_mcu & reset_mcu_ret) : true) &&
                (mipi_lcd_test ? (mipi_lcd_test & mipi_lcd_test_ret) : true) && (tp_test ? (tp_test & tp_test_ret) : true) &&
                (wirte_mac ? (wirte_mac & wirte_mac_ret) : true)) {
            DevBarcodeInfo devBarcodeInfo = new DevBarcodeInfo();
            devBarcodeInfo.setModel("" + MainActivity.test_board);
            devBarcodeInfo.setMac("" + getMac());
            devBarcodeInfo.setSn("" + getSn());
            devBarcodeInfo.setMcu("" + getMCUVersion());
            devBarcodeInfo.setFw("" + Build.DISPLAY);
            devBarcodeInfo.setDdr("" + Tools.getMemSize());
            devBarcodeInfo.setFlash("" + Tools.getRomSize(this));
            devBarcodeInfo.setRemark("");
            Gson json = new Gson();
            String showBardCode = json.toJson(devBarcodeInfo);
            Bitmap bitmap = EncodingUtils.createQRCode(showBardCode, 500, 500, null);
            m_ImageView_infoBarCode.setImageBitmap(bitmap);
            m_ImageView_infoBarCode.setVisibility(View.VISIBLE);
            Log.d(TAG, "showBardCode:" + showBardCode);
        }
    }

    private String execSuCmd(String cmd) {
        try {
            Process mProcess = Runtime.getRuntime().exec("cmdclient " + cmd);
            BufferedReader mInputReader = new BufferedReader(new InputStreamReader(mProcess.getInputStream()));
            BufferedReader mErrorReader = new BufferedReader(new InputStreamReader(mProcess.getErrorStream()));
            String msg = "";
            String line;
            int i = 0;
            while ((line = mInputReader.readLine()) != null) {
                if(0 != i)
                    msg += '\n';
                msg += line;
                i = 1;
            }
            mInputReader.close();

            i = 0;
            while ((line = mErrorReader.readLine()) != null) {
                if(0 != i)
                    msg += '\n';
                msg += line;
                i = 1;
            }
            mErrorReader.close();
            mProcess.destroy();
            return msg;
        } catch (IOException e) {
            e.printStackTrace();
            return "execSuCmd Error";
        }
    }

    public void test_Thread() {
        test_AGEING();
        test_volumes();
        test_ETH();
        test_BT();
        test_RTC();
        test_MCU();
        test_FUSB302();
        test_GSENSOR();
        test_HDMI();
        test_Gigabit();
        test_SPI();
        if(wifi_test) {
            test_Wifi();
        }
    }

    private void registerBTReceiver() {
        if(bt_test) {
            mBTDeviceReceiver = new BTDeviceReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(BluetoothDevice.ACTION_FOUND);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            mContext.registerReceiver(mBTDeviceReceiver, filter);
            Log.d(TAG, "registerBTReceiver");
        }
    }

    private class BTDeviceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice btd = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                short rssi = intent.getExtras().getShort(BluetoothDevice.EXTRA_RSSI);
                if(btd != null) {
                    String name = btd.getName();
                    if(name != null) {
                        //if (name.equals(BTSSID)) {
                        if(rssi > CONFIG_BT_RSSI) {
                            btLevel = -rssi;
                            BT_ERR = false;
                            mHandler.sendEmptyMessage(MSG_BT_TEST_OK);
                        } else {
                            BT_ERR = true;
                        }
                        //}
                        Log.d(TAG, "BT Found device name= " + btd.getName() + "rssi = " + rssi);
                    }
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {

                if(BT_ERR) {

                    BT_try_count--;
                    if(BT_try_count > 0) {
                        test_BT();
                    }
                    if(BT_try_count <= 0) {
                        mHandler.sendEmptyMessage(MSG_BT_TEST_ERROR);
                    }
                }

                Log.d(TAG, "BT Found End");
            }
        }
    }


    private void updateEthandWifi() {
        boolean isEthConnected = NetworkUtils.isEthConnected(this);

        if (isEthConnected) {
            m_ip.setText(NetworkUtils.getLocalIpAddress(this));
        } else {
            m_ip.setText(nullip);
        }

        WifiManager manager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcpInfo = manager.getDhcpInfo();
        WifiInfo wifiinfo = manager.getConnectionInfo();
        if (wifiinfo != null) {
            m_wifiip.setText(NetworkUtils.int2ip(wifiinfo.getIpAddress()));
            m_wifimac.setText(wifiinfo.getMacAddress());
        } else {
            m_wifiip.setText(nullip);
            m_wifimac.setText(" ");
        }
    }

    private String getMCUVersion() {
        String mcuversion = Tools.exec("i2cget -f -y 4 0x18 0x13");
        String formattedVersion = "ERR";
        if (mcuversion != null && mcuversion.startsWith("0x")) {
            formattedVersion = "" + mcuversion.substring(2).toUpperCase();
        }
        return formattedVersion.trim();
    }

    private String getSerialNumber() {
        String serial = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "ro.serialno");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serial;
    }

    private String getSn() {
        String serial = "";
        try {
            serial = Build.SERIAL;
            if ((TextUtils.isEmpty(serial) || Build.UNKNOWN.equalsIgnoreCase(serial)) && Build.VERSION.SDK_INT >= 26) {
                serial = Build.getSerial();
            }
        } catch (Exception e) {
        }
        if (TextUtils.isEmpty(serial) || Build.UNKNOWN.equalsIgnoreCase(serial)) {
            serial = getSerialNumber();
            if (TextUtils.isEmpty(serial)) {
                serial = Build.UNKNOWN;
            }
        }
        return serial;
    }

    private String getMac() {
        String strMac = Tools.getMac();
        if (strMac != null) {
            int length = strMac.length();
            if (length != 17) {
                strMac = "ERR";
            }
        } else {
            strMac = "ERR";
        }
        return strMac;
    }

    private boolean checkMacAndSnOk() {
        String mac = getMac();
        if (TextUtils.isEmpty(mac) || mac.equals("ERR") || mac.equals("00:00:00:00:00:00")) {
            return false;
        }

        //          String sn = getSn();
        //          if (TextUtils.isEmpty(sn) || "".equals(sn.replaceAll("0", ""))) {
        //              return false;
        //          }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //readVersion();
        mStatusBarManager.addUpperBar();
        mStatusBarManager.addBottomBar();

        m_ddr_size.setText(Tools.getMemSize());
        m_nand_size.setText(Tools.getRomSize(this));

        //        String mcuversion = Tools.exec("i2cget -f -y 4 0x18 0x13");
        //        if (mcuversion != null && mcuversion.startsWith("0x"))
        //        {
        //            String formattedVersion = "V" + mcuversion.substring(2).toUpperCase();
        //            m_mcu_version.setText(formattedVersion);
        //        }
        //        else
        //        {
        //            m_mcu_version.setText("Error");
        //        }
        m_mcu_version.setText(getMCUVersion());

        String display = Build.DISPLAY;
        m_firmware_version.setText(display);

        //        StringBuilder result = new StringBuilder();
        //        if (display != null) {
        //        String[] segments = display.split("-");
        //            for (String segment : segments) {
        //                if (!"Edge2".equals(segment)) {
        //                    result.append(segment).append("-");
        //                }
        //            }
        //            if (result.length() > 0) {
        //                result.setLength(result.length() - 1);
        //                String finalStr = result.toString();
        //                finalStr = Character.toUpperCase(finalStr.charAt(0)) + finalStr.substring(1);
        //                m_firmware_version.setText(finalStr);
        //            }
        //        }

        m_device_type.setText(Build.MODEL);

        updateEthandWifi();

        //mHandler.sendEmptyMessageDelayed(MSG_PLAY_VIDEO, MSG_PLAY_VIDEO_TIME);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(mFactoryReceiver, filter);

        IntentFilter mountfilter = new IntentFilter();
        mountfilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
        mountfilter.addDataScheme("file");
        registerReceiver(mountReceiver, mountfilter);

        String strMac = Tools.getMac();
        if (strMac != null) {
            int length = strMac.length();
            if (length != 17) {
                m_macvalue.setTextColor(Color.RED);
                m_macvalue.setText("ERR");
            } else {
                m_macvalue.setTextColor(Color.RED);
                m_macvalue.setText(strMac + " ");
                if (Tools.isMacFromEfuse()) {
                    m_TextView_MacLabel.setTextColor(Color.WHITE);
                }
            }
        } else {
            m_macvalue.setTextColor(Color.RED);
            m_macvalue.setText("ERR");
        }

        if (checkMacAndSnOk()) {
            wirte_mac_ret = true;
        }

        m_maccheck.requestFocus();
        int rec = 2;
        rec = Settings.System.getInt(mContext.getContentResolver(), "Khadas_irkey_test", 2);
        if (rec == 1) {
            m_Button_IRKey.setTextColor(Color.GREEN);
            irkey_test_ret = true;
        } else if (rec == 0) {
            m_Button_IRKey.setTextColor(Color.RED);
        }

        rec = Settings.System.getInt(mContext.getContentResolver(), "Khadas_mipi_camera_test", 2);
        if (rec == 1) {
            m_Button_Mipi_Camera.setTextColor(Color.GREEN);
            mipi_camera_test_ret = true;
        } else if (rec == 0) {
            m_Button_Mipi_Camera.setTextColor(Color.RED);
        }

        rec = Settings.System.getInt(mContext.getContentResolver(), "Khadas_mipi_lcd_test", 2);
        if (rec == 1) {
            m_Button_Mipi_LCD.setTextColor(Color.GREEN);
            mipi_lcd_test_ret = true;
        } else if (rec == 0) {
            m_Button_Mipi_LCD.setTextColor(Color.RED);
        }

        rec = Settings.System.getInt(mContext.getContentResolver(), "Khadas_tp_test", 2);
        if (rec == 1) {
            m_Button_TP.setTextColor(Color.GREEN);
            tp_test_ret = true;
        } else if (rec == 0) {
            m_Button_TP.setTextColor(Color.RED);
        }

        rec = Settings.System.getInt(mContext.getContentResolver(), "Khadas_write_mac_usid_test", 2);
        if (rec == 1) {
            m_Button_write_mac_usid.setTextColor(Color.GREEN);
        } else if (rec == 0) {
            m_Button_write_mac_usid.setTextColor(Color.RED);
        }
    }

    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            //bIsKeyDown = true;
            mHandler.sendEmptyMessageDelayed(MSG_TIME, 1 * 1000);
        }
    };

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        //退出产测apk恢复系统音量大小
        if(mAudioManager != null)
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
        setFunKey(currentkeyvalue);
        unregisterBTReceiver();
        Tools.exec("echo 0 > /sys/class/mcu/key_test");
        super.onDestroy();
    }

    private void unregisterBTReceiver() {
        if (bt_test) {
            Log.d(TAG, "unregisterBTReceiver");
            if (mBTDeviceReceiver != null) {
                unregisterReceiver(mBTDeviceReceiver);
                mBTDeviceReceiver = null;
            }
        }
    }

    @Override
    protected void onPause() {
        mHandler.removeMessages(MSG_NETLED_TEST_Start);
        mHandler.removeMessages(MSG_POWERLED_TEST_Start);
        mHandler.removeMessages(MSG_PLAY_VIDEO);
        unregisterReceiver(mFactoryReceiver);
        unregisterReceiver(mountReceiver);
        if(mAudioManager != null)
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
        setFunKey(currentkeyvalue);
        Tools.exec("echo 0 > /sys/class/mcu/key_test");
        super.onPause();
    }

    public void NetLed_Test(View view) {
        Log.e(TAG, "NetLed_Test()");
        m_Button_NetLed.setTag(0);
        mHandler.removeMessages(MSG_NETLED_TEST_Start);
        mHandler.sendEmptyMessage(MSG_NETLED_TEST_Start);
    }

    public void PowerLed_Test(View view) {
        Log.e(TAG, "PowerLed_Test()");
        m_Button_PowerLed.setTag(0);
        mHandler.removeMessages(MSG_POWERLED_TEST_Start);
        mHandler.sendEmptyMessage(MSG_POWERLED_TEST_Start);
    }

    public void IRKeyTest(View view) {
        Log.e(TAG, "IRKeyTest()");
        Intent intent = new Intent(this, IRKeyTestActivity.class);
        mStatusBarManager.removeUpperBar();
        mStatusBarManager.removeBottomBar();
        startActivity(intent);
    }

    public void Mipi_Camera(View view) {
        Log.d(TAG, "Mipi_Camera()");
        Intent intent = new Intent(this, MipiCameraTestActivity.class);
        startActivity(intent);
    }

    public void Mipi_LCD(View view) {
        Log.d(TAG, "Mipi_LCD()");
        Intent intent = new Intent(this, MipiLCDTestActivity.class);
        startActivity(intent);
    }

    public void TP_Test(View view) {
        Log.d(TAG, "TP_Test()");
        Intent intent = new Intent(this, TouchTestActivity.class);
        startActivity(intent);
    }

    public void Write_mac_usid(View view) {
        Log.e(TAG, "Write_mac_usid()");
        m_Button_write_mac_usid.setTag(0);
        Intent intent = new Intent(this, WriteMacActivity.class);
        startActivity(intent);
    }

    public synchronized void rst_mcu(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                String str_serial_number = "";
                Log.e(TAG, "rst_mcu(execCommand)");
                Tools.writeFile("/sys/class/mcu/rst", "0");
                str_serial_number =  Build.getSerial();
                Log.d(TAG, "str_serial_number : " + str_serial_number);

                String path = MainActivity.udisk_backup + "/";
                String cmd_val = "ls " + path;
                Log.d(TAG, "cmd_val : " + cmd_val);
                if(Tools.exec(cmd_val).contains("No such file or directory")) {
                    cmd_val = "mkdir -p " + path;
                    Tools.exec(cmd_val);
                }
                cmd_val = "screencap -p " + path + str_serial_number + ".png";
                Log.d(TAG, "screencap cmd_val : " + cmd_val);

                String rec = "";
                try {
                    Tools.execCommand(new String[] {"sh", "-c", cmd_val});
                    Tools.execCommand(new String[] {"sh", "-c", "sync"});
                    Log.d(TAG, "screencap sync");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(rec.contains("")) {
                            m_Button_Rst_MCU.setTextColor(Color.GREEN);
                            reset_mcu_ret = true;
                        }
                    }
                });

            }
        }).start();


    }

    public void EnableWol(View view) {
        Tools.writeFile("/sys/class/wol/test", "1");
        Tools.writeFile("/sys/class/wol/enable", "1");
    }

    public void KeyTest(View view) {
        Log.e(TAG, "KeyTest()");
    }

    private void test_BT() {
        if(bt_test) {
            try {
                Thread.sleep(1000);
            } catch(Exception localException1) {
            }

            BTAdmin localBTAdmin = new BTAdmin();
            registerBTReceiver();
            localBTAdmin.OpenBT();
            if(!localBTAdmin.ScanBT()) {
                mHandler.sendEmptyMessage(MSG_BT_TEST_ERROR);
            }
        }
    }

    private void test_SPI() {
        String val = Tools.readFile("/proc/cmdline");
        if (val.indexOf("spi_state=1") != -1)
            mHandler.sendEmptyMessage(MSG_SPI_TEST_OK);
        else
            mHandler.sendEmptyMessage(MSG_SPI_TEST_ERROR);
    }

    private void test_FUSB302() {
        String val = Tools.readFile("/proc/cmdline");
        if (val.indexOf("fusb302_state=1") != -1)
            mHandler.sendEmptyMessage(MSG_FUSB302_TEST_OK);
        else
            mHandler.sendEmptyMessage(MSG_FUSB302_TEST_ERROR);
    }

    private void test_MCU() {
        File file = new File("/sys/class/mcu/rst");
        if (file.exists())
            mHandler.sendEmptyMessage(MSG_MCU_TEST_OK);
        else
            mHandler.sendEmptyMessage(MSG_MCU_TEST_ERROR);
    }

    private void test_HDMI() {
        String node = "/sys/class/amhdmitx/amhdmitx0/edid";
        File file = new File(node);
        if (file.exists()) {
            String value = Tools.readFile(node);
            Log.d(TAG, "===hdmi i2c====" + value + "======");
            if (value.indexOf("EDID Version: 0.0") != -1)
                mHandler.sendEmptyMessage(MSG_HDMI_TEST_ERROR);
            else
                mHandler.sendEmptyMessage(MSG_HDMI_TEST_OK);
        } else
            mHandler.sendEmptyMessage(MSG_HDMI_TEST_ERROR);
    }

    private void test_GSENSOR() {
        int num = 10;
        String path = "/sys/class/input/";
        String node = "";
        for (int i = 0; i < num; i++) {
            node = path + "input" + i + "/name";
            File file = new File(node);
            if (!file.exists())
                continue;
            String value = Tools.readFile(node);
            if (value.equals("gsensor")) {
                mHandler.sendEmptyMessage(MSG_GSENSOR_TEST_OK);
                return;
            }
        }
        mHandler.sendEmptyMessage(MSG_GSENSOR_TEST_ERROR);
    }

    private void test_AGEING() {
        String pathname = "/sys/class/mcu/ageing_test";
        try (FileReader reader = new FileReader(pathname);
                    BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                int id = Integer.parseInt(line);
                Log.d(TAG, "hlm AGEING: " + id);
                if(1 == id) {
                    ageing_test_ok_flag = true;
                    if (!ageing_test) {
                        //m_TextView_AGEING.setVisibility(View.VISIBLE);
                    }
                    mHandler.sendEmptyMessage(MSG_AGEING_TEST_OK);
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ageing_test_ok_flag = false;
        mHandler.sendEmptyMessage(MSG_AGEING_TEST_ERROR);
    }

    private void test_Gigabit() {
        String node = "/sys/class/net/eth0/speed";
        File file = new File(node);
        if (file.exists()) {
            String rate = Tools.readFile(node);
            if (rate.equals("1000"))
                mHandler.sendEmptyMessage(MSG_GIGABIT_TEST_OK);
            else
                mHandler.sendEmptyMessage(MSG_GIGABIT_TEST_ERROR);
        } else
            mHandler.sendEmptyMessage(MSG_GIGABIT_TEST_ERROR);
    }

    private void test_RTC() {
        String content = "";
        String node = "/sys/class/rtc/rtc0/time";
        File file = new File(node);
        if (file.exists()) {
            String val = Tools.readFile(node);
            try {
                FileInputStream instream = new FileInputStream(node);
                if(instream != null) {
                    InputStreamReader inputreader = new InputStreamReader(instream);
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    Log.d(TAG, "buffreader = " + buffreader.toString());
                    String line;
                    while( (line = buffreader.readLine() )  !=  null) {
                        content = content + line;
                    }
                    instream.close();
                }
            } catch(FileNotFoundException e) {
                Log.e(TAG, "The File doesn\'t not exist.");
                mHandler.sendEmptyMessage(MSG_RTC_TEST_ERROR);
            } catch(IOException e) {
                Log.e(TAG, " readFile error!");
                Log.e(TAG, e.getMessage() );
                mHandler.sendEmptyMessage(MSG_RTC_TEST_ERROR);
                return;
            }
            mHandler.sendEmptyMessage(MSG_RTC_TEST_OK);
        } else
            mHandler.sendEmptyMessage(MSG_RTC_TEST_ERROR);
    }

    private void test_cpu_ageing() {
        BigDecimal a = new BigDecimal("1");
        BigDecimal b = new BigDecimal("7");
        System.out.println("1/7 =" + a.divide(b, 100, RoundingMode.HALF_UP));
    }

    private void test_Wifi() {
        boolean bWifiScaned = false;
        try {
            Thread.sleep(2000);
        } catch (Exception localException1) {
        }
        configSSID = getResources().getString(R.string.config_ap_ssid);
        WifiAdmin localWifiAdmin = new WifiAdmin(this);
        localWifiAdmin.openWifi();
        localWifiAdmin.startScan();
        wifiList = new ArrayList<ScanResult>();
        wifiList = localWifiAdmin.getWifiList();
        Log.d(TAG, "wifi size: " + wifiList.size());
        if (wifiList != null) {
            for (ScanResult result : wifiList) {
                //if (result.SSID.equals(configSSID)) {
                wifiLevel = WifiManager.calculateSignalLevel(result.level, 100);
                Log.d(TAG, "wifiLevel: " + wifiLevel);
                if (wifiLevel >= configLevel) {
                    bWifiScaned = true;
                }
                //}
            }
        }
        if (bWifiScaned) {
            mHandler.sendEmptyMessage(MSG_WIFI_TEST_OK);
        } else {
            mHandler.sendEmptyMessage(MSG_WIFI_TEST_ERROR);
        }
    }

    /**
     * 判断USB与TF
     */
    private void test_volumes() {
        if(Tools.isAndroid5_1_1()) {
            test_android5_1();
        } else {
            test_android6_0();
        }
    }

    /**
     * android6.0
     */
    private void test_android6_0() {
        Log.d(TAG, "----- kernel 6.0 -----");
        mHandler.sendEmptyMessage(MSG_android_6_0_TEXT_LAYOUT);
        Boolean[] usbOrSd = Tools.isUsbOrSd(MainActivity.this);
        Log.e("TEST", "usbOrSd:" + usbOrSd[0] + ", " + usbOrSd[1] + ", " + usbOrSd[2]);

        if(usbOrSd[0]) {
            mHandler.sendEmptyMessage(MSG_TF_TEST_XL_OK);
        } else {
            mHandler.sendEmptyMessage(MSG_TF_TEST_XL_ERROR);
        }

        {
            mHandler.sendEmptyMessage(MSG_USB1_TEST_XL_ERROR);
            mHandler.sendEmptyMessage(MSG_USB2_TEST_XL_ERROR);
        }
        String val = Tools.readFile("/sys/kernel/debug/usb/devices");
        if (val.indexOf("(O)") == -1) {
            Log.e(TAG, "=========USB2.0 and USB3.0 is bad");
            mHandler.sendEmptyMessage(MSG_USB1_TEST_XL_ERROR);
            mHandler.sendEmptyMessage(MSG_USB2_TEST_XL_ERROR);
            return;
        }

        int length;
        String[] list = val.split("T:|B:|D:|P:|S:|C:|I:|E:");
        int num = -1;
        int count = getSubCount(val, "Bus=");
        String[] tmp = new String[count];
        for (int z = 0; z < list.length; z++) {

            if (list[z].indexOf("Bus=") != -1) {
                num++;
            }
            if (num == count)
                break;
            if (num == -1)
                continue;
            tmp[num] = tmp[num] + list[z];
        }
        for (int i = 0; i < tmp.length; i++) {
            if (Build.MODEL.equals("VIM1") || Build.MODEL.equals("VIM2")) {
                if ((tmp[i].indexOf("(O)") != -1) && (tmp[i].indexOf("Bus=01") != -1) && (tmp[i].indexOf("Port=02") != -1)) {
                    Log.d("TAG", "USB2.0 port 1 is OK");
                    mHandler.sendEmptyMessage(MSG_USB1_TEST_XL_OK);
                }
                if ((tmp[i].indexOf("(O)") != -1) && (tmp[i].indexOf("Bus=01") != -1) && (tmp[i].indexOf("Port=03") != -1)) {
                    Log.d("TAG", "USB2.0 port 2 is OK");
                    mHandler.sendEmptyMessage(MSG_USB2_TEST_XL_OK);
                }
            } else {
                if ((tmp[i].indexOf("(O)") != -1) && (tmp[i].indexOf("Bus=02") != -1)) {
                    Log.d(TAG, "USB3.0 is OK");
                    mHandler.sendEmptyMessage(MSG_USB2_TEST_XL_OK);
                }
                if ((tmp[i].indexOf("(O)") != -1) && (tmp[i].indexOf("Bus=01") != -1)) {
                    Log.d("TAG", "USB2.0 is OK");
                    mHandler.sendEmptyMessage(MSG_USB1_TEST_XL_OK);
                }
            }
        }

    }


    private  int getSubCount(String str, String key) {
        int count = 0;
        int index = 0;
        while ((index = str.indexOf(key, index)) != -1) {
            index = index + key.length();
            count++;
        }
        return count;
    }

    private void test_android5_1() {
        Log.d(TAG, "----- kernel 5.1 -----");
        List<String> volumes = getVolumes();
        boolean bSdcard = false;
        boolean bSda = false;
        boolean bSdb = false;
        for(String volume : volumes) {
            if(volume.contains(TFCARD_PATH)) {
                bSdcard = true;
            } else if(volume.contains(USB1_PATH)) {
                Log.d(TAG, USB1_PATH + " usb1 " + volume.toString());
                usb_path = volume;
                bSda = true;
            } else if(volume.contains(USB2_PATH)) {
                Log.d(TAG, USB2_PATH + " usb2 " + volume.toString());
                bSdb = true;
            }
        }
        if(bSdcard) {
            mHandler.sendEmptyMessage(MSG_TF_TEST_OK);
        } else {
            mHandler.sendEmptyMessage(MSG_TF_TEST_ERROR);
        }

        {
            mHandler.sendEmptyMessage(MSG_USB1_TEST_ERROR);
            mHandler.sendEmptyMessage(MSG_USB2_TEST_ERROR);
        }
        if(bSda) {
            if(isUsb1()) {
                mHandler.sendEmptyMessage(MSG_USB1_TEST_OK);
            } else {
                mHandler.sendEmptyMessage(MSG_USB2_TEST_OK);
            }
        }

        if(bSdb) {
            //判断两个U盘是否都接入
            if(bSda) {
                mHandler.sendEmptyMessage(MSG_USB1_TEST_OK);
                mHandler.sendEmptyMessage(MSG_USB2_TEST_OK);
            }//只有一个U盘情况下 判断在哪个口
            else {
                if(isUsb1()) {
                    mHandler.sendEmptyMessage(MSG_USB1_TEST_OK);
                } else {
                    mHandler.sendEmptyMessage(MSG_USB2_TEST_OK);
                }
            }
        }
    }


    private List<String> getVolumes() {
        List<String> volumes = new ArrayList<String>();
        try {
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("df").getInputStream()));
            String readline;
            while ((readline = bufferReader.readLine()) != null) {
                Log.d(TAG, "df State:" + readline);
                if(readline.contains(USB_PATH) || readline.contains(TFCARD_PATH)) {
                    String[] result = readline.split(" ");
                    if(result.length > 0) {
                        volumes.add(result[0]);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            return volumes;
        } catch (IOException e) {
            return volumes;
        }
        return volumes;
    }
    //仅仅在一个U盘接入情况下判断接入那个USB口
    private boolean isUsb1() {
        try {
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("lsusb").getInputStream()));
            String readline = bufferReader.readLine();
            //Bus 001 Device 008: ID 05e3:0723
            String USBBus = readline.substring(readline.indexOf("00") + 2, readline.lastIndexOf("Device")).trim();
            Log.d(TAG, "lsusb :  " + USBBus);
            if(USBBus.equals("1")) {
                Log.d(TAG, "lsusb :  is USB1 mount");
                return true;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    private boolean hasEthIpAddress() {

        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                if (intf.getName().toLowerCase().equals("eth0")) {
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            String ipaddress = inetAddress.getHostAddress().toString();
                            if(!ipaddress.contains("::")) {
                                return true;
                            }
                        }
                    }

                } else {
                    continue;
                }
            }

        } catch (SocketException ex) {
            Log.e(TAG, ex.toString());
        }
        return false;

    }

    private void test_ETH() {
        if (hasEthIpAddress()) {
            mHandler.sendEmptyMessage(MSG_LAN_TEST_OK);
        } else {
            mHandler.sendEmptyMessage(MSG_LAN_TEST_ERROR);
        }
    }

    class FactoryHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case  MSG_TF_TEST_ERROR: {
                String strTxt = getResources().getString(R.string.TF_Test) + "    " + getResources().getString(R.string.Test_Fail);
                m_TextView_TF.setText(strTxt);
                m_TextView_TF.setTextColor(0xFFFF5555);
            }
            break;

            case  MSG_TF_TEST_OK: {
                String strTxt = getResources().getString(R.string.TF_Test) + "    " + getResources().getString(R.string.Test_Ok);
                m_TextView_TF.setText(strTxt);
                m_TextView_TF.setTextColor(0xFF55FF55);
                tfcard_test_ret = true;
            }
            break;

            case  MSG_USB1_TEST_ERROR: {
                String strTxt = getResources().getString(R.string.USB1_Test) + "    " + getResources().getString(R.string.Test_Fail);
                m_TextView_USB1.setText(strTxt);
                m_TextView_USB1.setTextColor(0xFFFF5555);
            }
            break;

            case  MSG_USB1_TEST_OK: {
                String strTxt = getResources().getString(R.string.USB1_Test) + "    " + getResources().getString(R.string.Test_Ok);
                m_TextView_USB1.setText(strTxt);
                m_TextView_USB1.setTextColor(0xFF55FF55);
            }
            break;

            case  MSG_USB2_TEST_ERROR: {
                String strTxt = getResources().getString(R.string.USB2_Test) + "    " + getResources().getString(R.string.Test_Fail);
                m_TextView_USB2.setText(strTxt);
                m_TextView_USB2.setTextColor(0xFFFF5555);
            }
            break;

            case  MSG_USB2_TEST_OK: {
                String strTxt = getResources().getString(R.string.USB2_Test) + "    " + getResources().getString(R.string.Test_Ok);
                m_TextView_USB2.setText(strTxt);
                m_TextView_USB2.setTextColor(0xFF55FF55);
            }
            break;

            case  MSG_LAN_TEST_OK: {
                String strTxt = getResources().getString(R.string.Lan_Test) + "    " + getResources().getString(R.string.Test_Ok);

                m_TextView_Lan.setText(strTxt);
                m_TextView_Lan.setTextColor(0xFF55FF55);
                lan_test_ret = true;
                Log.d(TAG, "MSG_LAN_TEST_OK");
            }
            break;

            case  MSG_LAN_TEST_ERROR: {
                String strTxt = getResources().getString(R.string.Lan_Test) + "    " + getResources().getString(R.string.Test_Fail);

                m_TextView_Lan.setText(strTxt);
                m_TextView_Lan.setTextColor(0xFFFF5555);
                Log.d(TAG, "MSG_LAN_TEST_ERROR");
            }
            break;

            case  MSG_GIGABIT_TEST_OK: {
                String strTxt = getResources().getString(R.string.Gigabit_Test) + "    " + getResources().getString(R.string.Test_Ok);

                m_TextView_Gigabit.setText(strTxt);
                m_TextView_Gigabit.setTextColor(0xFF55FF55);
                gigabit_test_ret = true;
                Log.d(TAG, "MSG_GIGABIT_TEST_OK");
            }
            break;

            case  MSG_GIGABIT_TEST_ERROR: {
                String strTxt = getResources().getString(R.string.Gigabit_Test) + "    " + getResources().getString(R.string.Test_Fail);

                m_TextView_Gigabit.setText(strTxt);
                m_TextView_Gigabit.setTextColor(0xFFFF5555);
                Log.d(TAG, "MSG_GIGABIT_TEST_ERROR");
            }
            break;
            case  MSG_HDMI_TEST_OK: {
                String strTxt = getResources().getString(R.string.HDMI_Test) + "    " + getResources().getString(R.string.Test_Ok);

                m_TextView_HDMI.setText(strTxt);
                m_TextView_HDMI.setTextColor(0xFF55FF55);
                hdmi_test_ret = true;
                Log.d(TAG, "MSG_HDMI_TEST_OK");
            }
            break;

            case  MSG_HDMI_TEST_ERROR: {
                String strTxt = getResources().getString(R.string.HDMI_Test) + "    " + getResources().getString(R.string.Test_Fail);

                m_TextView_HDMI.setText(strTxt);
                m_TextView_HDMI.setTextColor(0xFFFF5555);
                Log.d(TAG, "MSG_HDMI_TEST_ERROR");
            }
            break;

            case  MSG_GSENSOR_TEST_OK: {
                String strTxt = getResources().getString(R.string.GSENSOR_Test) + "    " + getResources().getString(R.string.Test_Ok);

                m_TextView_GSENSOR.setText(strTxt);
                m_TextView_GSENSOR.setTextColor(0xFF55FF55);
                gsensor_test_ret = true;
                Log.d(TAG, "MSG_GSENSOR_TEST_OK");
            }
            break;

            case  MSG_GSENSOR_TEST_ERROR: {
                String strTxt = getResources().getString(R.string.GSENSOR_Test) + "    " + getResources().getString(R.string.Test_Fail);

                m_TextView_GSENSOR.setText(strTxt);
                m_TextView_GSENSOR.setTextColor(0xFFFF5555);
                Log.d(TAG, "MSG_GSENSOR_TEST_ERROR");
            }
            break;

            case MSG_KEY_TEST_OK: {
                String strTxt = getResources().getString(R.string.KEY_Test);
                if(key_test) {
                    strTxt = strTxt + "    " + getResources().getString(R.string.KEY_Test_1_Ok) + "    " + getResources().getString(R.string.KEY_Test_2_Ok);
                } else {
                    strTxt = strTxt + "    " + getResources().getString(R.string.Test_Ok);
                }
                m_TextView_KEY.setText(strTxt);
                m_TextView_KEY.setTextColor(0xFF55FF55);
                key_test_ret = true;
                Log.d(TAG, "MSG_KEY_TEST_OK");
            }
            break;
            case MSG_KEY_TEST_ERROR: {
                String strTxt = getResources().getString(R.string.KEY_Test);
                if(key_test) {
                    if(key_flag == 0x1 || key_flag == 0x5)
                        strTxt = strTxt + "    " + getResources().getString(R.string.KEY_Test_1_Ok);
                    else
                        strTxt = strTxt + "    " + getResources().getString(R.string.KEY_Test_1_Fail);

                    if(key_flag == 0x6)
                        strTxt = strTxt + "    " + getResources().getString(R.string.KEY_Test_2_Ok);
                    else
                        strTxt = strTxt + "    " + getResources().getString(R.string.KEY_Test_2_Fail);
                } else {
                    strTxt = strTxt + "    " + getResources().getString(R.string.Test_Fail);
                }
                m_TextView_KEY.setText(strTxt);
                m_TextView_KEY.setTextColor(0xFFFF5555);
                Log.d(TAG, "MSG_KEY_TEST_ERROR");
            }
            break;

            case  MSG_AGEING_TEST_OK: {
                String strTxt = getResources().getString(R.string.AGEING_Test) + "    " + getResources().getString(R.string.Test_Ok);

                m_TextView_AGEING.setText(strTxt);
                m_TextView_AGEING.setTextColor(0xFF55FF55);
                ageing_test_ret = true;
                Log.d(TAG, "MSG_AGEING_TEST_OK");
            }
            break;

            case  MSG_AGEING_TEST_ERROR: {
                String strTxt = getResources().getString(R.string.AGEING_Test) + "    " + getResources().getString(R.string.Test_In);

                m_TextView_AGEING.setText(strTxt);
                m_TextView_AGEING.setTextColor(0xFFFF5555);
                Log.d(TAG, "MSG_AGEING_TEST_ERROR");
            }
            break;

            case  MSG_FUSB302_TEST_OK: {
                String strTxt = getResources().getString(R.string.FUSB302_Test) + "    " + getResources().getString(R.string.Test_Ok);

                m_TextView_FUSB302.setText(strTxt);
                m_TextView_FUSB302.setTextColor(0xFF55FF55);
                fusb302_test_ret  = true;
                Log.d(TAG, "MSG_FUSB302_TEST_OK");
            }
            break;

            case  MSG_FUSB302_TEST_ERROR: {
                String strTxt = getResources().getString(R.string.FUSB302_Test) + "    " + getResources().getString(R.string.Test_Fail);

                m_TextView_FUSB302.setText(strTxt);
                m_TextView_FUSB302.setTextColor(0xFFFF5555);
                Log.d(TAG, "MSG_FUSB302_TEST_ERROR");
            }
            break;

            case  MSG_SPI_TEST_OK: {
                String strTxt = getResources().getString(R.string.SPI_Test) + "    " + getResources().getString(R.string.Test_Ok);

                m_TextView_SPI.setText(strTxt);
                m_TextView_SPI.setTextColor(0xFF55FF55);
                spi_test_ret = true;
                Log.d(TAG, "MSG_SPI_TEST_OK");
            }
            break;

            case  MSG_SPI_TEST_ERROR: {
                String strTxt = getResources().getString(R.string.SPI_Test) + "    " + getResources().getString(R.string.Test_Fail);

                m_TextView_SPI.setText(strTxt);
                m_TextView_SPI.setTextColor(0xFFFF5555);
                Log.d(TAG, "MSG_SPI_TEST_ERROR");
            }
            break;
            case  MSG_MCU_TEST_OK: {
                String strTxt = getResources().getString(R.string.MCU_Test) + "    " + getResources().getString(R.string.Test_Ok);

                m_TextView_MCU.setText(strTxt);
                m_TextView_MCU.setTextColor(0xFF55FF55);
                mcu_test_ret = true;
                Log.d(TAG, "MSG_MCU_TEST_OK");
            }
            break;

            case  MSG_MCU_TEST_ERROR: {
                String strTxt = getResources().getString(R.string.MCU_Test) + "    " + getResources().getString(R.string.Test_Fail);

                m_TextView_MCU.setText(strTxt);
                m_TextView_MCU.setTextColor(0xFFFF5555);
                Log.d(TAG, "MSG_MCU_TEST_ERROR");
            }
            break;

            case MSG_WIFI_TEST_OK: {
                String strTxt = getResources().getString(R.string.Wifi_Test) + "    " + wifiLevel + "    " + getResources().getString(R.string.Test_Ok);

                m_TextView_Wifi.setText(strTxt);
                m_TextView_Wifi.setTextColor(0xFF55FF55);
                wifi_test_ret = true;
            }
            break;

            case MSG_WIFI_TEST_ERROR: {
                String  strTxt = getResources().getString(R.string.Wifi_Test) + "    " + getResources().getString(R.string.Test_Fail);
                m_TextView_Wifi.setText(strTxt);
                m_TextView_Wifi.setTextColor(0xFFFF5555);
            }
            break;
            case MSG_BT_TEST_OK: {
                String strTxt = getResources().getString(R.string.BT_Test) + "    " + btLevel + "    " + getResources().getString(R.string.Test_Ok);
                m_TextView_BT.setText(strTxt);
                m_TextView_BT.setTextColor(0xFF55FF55);
                bt_test_ret = true;
            }
            break;
            case MSG_BT_TEST_ERROR: {
                String strTxt = getResources().getString(R.string.BT_Test) + "    " + getResources().getString(R.string.Test_Fail);
                m_TextView_BT.setText(strTxt);
                m_TextView_BT.setTextColor(0xFFFF5555);
            }
            break;
            case MSG_RTC_TEST_OK: {
                String  strTxt = getResources().getString(R.string.Rtc_Test) + "    " + getResources().getString(R.string.Test_Ok);
                m_TextView_Rtc.setText(strTxt);
                m_TextView_Rtc.setTextColor(0xFF55FF55);
                rtc_test_ret = true;

            }
            break;
            case MSG_RTC_TEST_ERROR: {
                String  strTxt = getResources().getString(R.string.Rtc_Test) + "    " + getResources().getString(R.string.Test_Fail);
                m_TextView_Rtc.setText(strTxt);
                m_TextView_Rtc.setTextColor(0xFFFF5555);

            }
            break;

            case MSG_NETLED_TEST_Start:
                tag_net ++;
                if(tag_net > ledtime) {
                    mHandler.removeMessages(MSG_NETLED_TEST_Start);
                    mHandler.sendEmptyMessage(MSG_NETLED_TEST_End);
                    return ;
                }
                Log.d(TAG, "MSG_NETLED_TEST_Start: " + tag_net);
                if(tag_net % 2 == 1 ) {
                    m_Button_NetLed.setText(getResources().getString(R.string.Led_TestIng) + "!");
                    Tools.writeFile(Tools.Ethernet_Led, "off");
                    mHandler.removeMessages(MSG_NETLED_TEST_Start);
                    mHandler.sendEmptyMessageDelayed(MSG_NETLED_TEST_Start, 1000);
                } else if(tag_net % 2 == 0) {
                    m_Button_NetLed.setText(getResources().getString(R.string.Led_TestIng) + "!!");
                    Tools.writeFile(Tools.Ethernet_Led, "default-on");
                    mHandler.removeMessages(MSG_NETLED_TEST_Start);
                    mHandler.sendEmptyMessageDelayed(MSG_NETLED_TEST_Start, 1000);
                }
                break;
            case MSG_NETLED_TEST_End:
                tag_net = 0;
                m_Button_NetLed.setText(getResources().getString(R.string.Led_Test));
                if(Tools.isNetworkAvailable(MainActivity.this)) {
                    Tools.writeFile(Tools.Ethernet_Led, "on");
                } else {
                    Tools.writeFile(Tools.Ethernet_Led, "default-on");
                }
                break;
            case MSG_POWERLED_TEST_Start:
                tag_power ++;
                if(tag_power > ledtime) {
                    mHandler.removeMessages(MSG_POWERLED_TEST_Start);
                    mHandler.sendEmptyMessage(MSG_POWERLED_TEST_End);
                    return ;
                }
                Log.d(TAG, "MSG_POWERLED_TEST_Start: " + tag_power);
                if(tag_power % 2 == 1) {
                    m_Button_PowerLed.setText(getResources().getString(R.string.PowerKey_TestIng) + "!");
                    Tools.writeFile(Tools.Power_Led, "off");
                    mHandler.removeMessages(MSG_POWERLED_TEST_Start);
                    mHandler.sendEmptyMessageDelayed(MSG_POWERLED_TEST_Start, 1000);
                } else if(tag_power % 2 == 0) {
                    m_Button_PowerLed.setText(getResources().getString(R.string.PowerKey_TestIng) + "!!");
                    Tools.writeFile(Tools.Power_Led, "on");
                    mHandler.removeMessages(MSG_POWERLED_TEST_Start);
                    mHandler.sendEmptyMessageDelayed(MSG_POWERLED_TEST_Start, 1000);
                }
                break;
            case MSG_POWERLED_TEST_End:
                tag_power = 0;
                m_Button_PowerLed.setText(getResources().getString(R.string.PowerKey_Test));
                Tools.writeFile(Tools.Power_Led, "on");
                break;
            case MSG_PLAY_VIDEO:
                mLeftLayout.setVisibility(View.GONE);
                mBottomLayout.setVisibility(View.GONE);
                mBottomLayout2.setVisibility(View.GONE);
                mBottomLayout3.setVisibility(View.GONE);
                mBottomLayout4.setVisibility(View.GONE);
                mBottomLayout5.setVisibility(View.GONE);
                //mBottomLayout6.setVisibility(View.GONE);
                break;
            case MSG_TIME: {
                /*    if(bIsKeyDown)
                    {
                        bIsKeyDown = false;
                        mHandler.removeMessages(MSG_TIME);
                        mHandler.sendEmptyMessageDelayed(MSG_TIME, 1 * 1000);
                    }
                    else*/
                {
                    mHandler.removeMessages(MSG_TIME);
                    OnScanText();
                }
            }
            break;

            case MSG_TF_TEST_XL_ERROR: {
                String strTxt = getResources().getString(R.string.TF_Test)
                                + "    " + getResources().getString(R.string.Test_Fail);
                m_TextView_TF.setText(strTxt);
                m_TextView_TF.setTextColor(0xFFFF5555);
            }
            break;

            case MSG_TF_TEST_XL_OK: {
                String strTxt = getResources().getString(R.string.TF_Test)
                                + "    " + getResources().getString(R.string.Test_Ok);
                m_TextView_TF.setText(strTxt);
                m_TextView_TF.setTextColor(0xFF55FF55);
                tfcard_test_ret = true;
            }
            break;

            case MSG_USB1_TEST_XL_ERROR: {
                String strTxt = "";
                if (Build.MODEL.equals("VIM1") || Build.MODEL.equals("VIM2")) {
                    strTxt = getResources().getString(R.string.USB1_Test_Other)
                             + "    " + getResources().getString(R.string.Test_Fail);
                } else {
                    strTxt = getResources().getString(R.string.USB1_Test)
                             + "    " + getResources().getString(R.string.Test_Fail);
                }
                m_TextView_USB1.setText(strTxt);
                m_TextView_USB1.setTextColor(0xFFFF5555);
            }
            break;

            case MSG_USB1_TEST_XL_OK: {
                String strTxt = "";
                if (Build.MODEL.equals("VIM1") || Build.MODEL.equals("VIM2")) {
                    strTxt = getResources().getString(R.string.USB1_Test_Other)
                             + "    " + getResources().getString(R.string.Test_Ok);
                } else {
                    strTxt = getResources().getString(R.string.USB1_Test)
                             + "    " + getResources().getString(R.string.Test_Ok);
                }
                m_TextView_USB1.setText(strTxt);
                m_TextView_USB1.setTextColor(0xFF55FF55);
                usb20_test_ret = true;
            }
            break;

            case  MSG_USB2_TEST_XL_ERROR: {
                String strTxt = "";
                if (Build.MODEL.equals("VIM1") || Build.MODEL.equals("VIM2")) {
                    strTxt = getResources().getString(R.string.USB2_Test_Other) + "    " + getResources().getString(R.string.Test_Fail);
                } else {
                    strTxt = getResources().getString(R.string.USB2_Test) + "    " + getResources().getString(R.string.Test_Fail);
                }
                m_TextView_USB2.setText(strTxt);
                m_TextView_USB2.setTextColor(0xFFFF5555);
            }
            break;

            case  MSG_USB2_TEST_XL_OK: {
                String strTxt = "";
                if (Build.MODEL.equals("VIM1") || Build.MODEL.equals("VIM2")) {
                    strTxt = getResources().getString(R.string.USB2_Test_Other) + "    " + getResources().getString(R.string.Test_Ok);
                } else {
                    strTxt = getResources().getString(R.string.USB2_Test) + "    " + getResources().getString(R.string.Test_Ok);
                }
                m_TextView_USB2.setText(strTxt);
                m_TextView_USB2.setTextColor(0xFF55FF55);
                usb30_test_ret = true;
            }
            break;

            case MSG_GET_CPU_STATUS:
                m_TextView_CPU_THERMAL.setText(Tools.readFile(Tools.cpu_thermal));
                m_TextView_CPU_FREQ.setText("0-2:" + Tools.readFile(Tools.cpu0_cpufreq).trim().substring(0, 4) + " 3-5: " + Tools.readFile(Tools.cpu3_cpufreq).trim().substring(0, 4));
                break;
            case MSG_TEST_RET_UPDATE:
                checkTestRetUpate();
                break;
            }
        }
    }

    private void CheckSameMac(String Scanmac) {

        if(Scanmac.equalsIgnoreCase(readMac)) {

            Toast.makeText(getApplicationContext(), getResources().getString(R.string.testled), Toast.LENGTH_LONG).show();
            m_mactitle.setText(readMac + "   " + getResources().getString(R.string.the_same_mac));
            m_mactitle.setTextColor(Color.GREEN);

            Log.e(TAG, "PowerLed_Test()");
            m_Button_PowerLed.setTag(0);
            mHandler.removeMessages(MSG_POWERLED_TEST_Start);
            mHandler.sendEmptyMessage(MSG_POWERLED_TEST_Start);

        } else {
            m_mactitle.setText(Scanmac + "   " + getResources().getString(R.string.the_diff_mac));
            m_mactitle.setTextColor(Color.RED);
            m_maccheck.requestFocus();
        }
    }

    private void OnScanText() {

        String strMac = m_maccheck.getText().toString();
        int nLength = m_maccheck.getText().toString().length();

        if(strMac.isEmpty() ) {
            return;
        }
        m_maccheck.setText("");

        String strTmpMac = "";

        if(getResources().getInteger(R.integer.config_mac_length) == nLength) {
            for(int i = 0; i < nLength; i += 2) {
                strTmpMac += strMac.substring(i, (i + 2) < nLength ? (i + 2) :  nLength );

                if( (i + 2) < nLength) strTmpMac += ':';
            }
            strMac = strTmpMac;
            CheckSameMac(strMac);
        } else if(getResources().getInteger(R.integer.config_mac_length2) == nLength) {
            CheckSameMac(strMac);
        } else {
            strTmpMac = "";
            m_mactitle.setText(strMac + "   " + getResources().getString(R.string.the_diff_mac));
            m_mactitle.setTextColor(Color.RED);
            m_maccheck.requestFocus();
        }

    }
    private static final int BAIDU_READ_PHONE_STATE = 100;
    private static WifiManager mWifiManager;
    private BroadcastReceiver mFactoryReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                if (hasEthIpAddress()) {
                    mHandler.sendEmptyMessage(MSG_LAN_TEST_OK);
                }
                updateEthandWifi();
            } else if(action.equals(Intent.ACTION_TIME_TICK) || action.equals(Intent.ACTION_TIME_CHANGED)) {
                updateTime();
            }
        }
    };


    private BroadcastReceiver mountReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Uri uri = intent.getData();
            if(uri.getScheme().equals("file")) {
                if(action.equals(Intent.ACTION_MEDIA_MOUNTED)) {
                    String path = uri.getPath();
                    Log.d(TAG, "mFactoryReceiver mount patch is " + path);
                    if(path.contains(USB1_PATH)) {
                        if(isUsb1()) {
                            mHandler.sendEmptyMessage(MSG_USB1_TEST_OK);
                        } else {
                            mHandler.sendEmptyMessage(MSG_USB2_TEST_OK);
                        }
                    } else if(path.contains(USB2_PATH)) {
                        List<String> volumes = getVolumes();
                        boolean isUSB1MOUNT = false;
                        for(String volume : volumes) {
                            if(volume.contains(USB1_PATH)) {
                                isUSB1MOUNT = true;
                                mHandler.sendEmptyMessage(MSG_USB1_TEST_OK);
                                mHandler.sendEmptyMessage(MSG_USB2_TEST_OK);
                            }
                        }
                        if(!isUSB1MOUNT) {
                            if(isUsb1())
                                mHandler.sendEmptyMessage(MSG_USB1_TEST_OK);
                            else
                                mHandler.sendEmptyMessage(MSG_USB2_TEST_OK);
                        }
                    } else if(path.contains(TFCARD_PATH)) {
                        mHandler.sendEmptyMessage(MSG_TF_TEST_OK);
                    }
                }
            }

        }
    };

    private void updateTime() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd/  E ");
                SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
                m_TextView_Time.setText(sdf1.format(new Date()) + sdf2.format(new Date()));
            }
        });
    }

    private void readVersion() {

        String lssue = getResources().getString(R.string.lssue_ver);
        String client = getResources().getString(R.string.client_ver);
        String verfile = getResources().getString(R.string.versionfile);

        File OutputFile = new File(verfile);
        if (!OutputFile.exists()) {
            Toast.makeText(getApplicationContext(), verfile + getResources().getString(R.string.noexist), Toast.LENGTH_LONG).show();
            return;
        }

        try {
            FileInputStream instream = new FileInputStream(verfile);
            if (instream != null) {
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                Log.d(TAG, "buffreader = " + buffreader.toString());

                String line;
                while( (line = buffreader.readLine() )  !=  null) {
                    if (line.startsWith(lssue)) {
                        lssue_value = line.replace(lssue, "").replace("=", "").trim().toString();
                    }
                    if(line.startsWith(client)) {
                        client_value = line.replace(client, "").replace("=", "").trim().toString();
                    }
                }

                instream.close();
            }
        } catch(FileNotFoundException e) {
            Log.e(TAG, "The File doesn\'t not exist.");
        } catch(IOException e) {
            Log.e(TAG, " readFile error!");
            Log.e(TAG, e.getMessage() );
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        mHandler.removeMessages(MSG_PLAY_VIDEO);
        Log.d(TAG, "onKeyDown ----> " + keyCode);
        //mHandler.sendEmptyMessageDelayed(MSG_PLAY_VIDEO, MSG_PLAY_VIDEO_TIME);
        mLeftLayout.setVisibility(View.VISIBLE);
        mBottomLayout.setVisibility(View.VISIBLE);
        mBottomLayout2.setVisibility(View.VISIBLE);
        mBottomLayout3.setVisibility(View.VISIBLE);
        mBottomLayout4.setVisibility(View.VISIBLE);
        mBottomLayout5.setVisibility(View.VISIBLE);
        //mBottomLayout6.setVisibility(View.VISIBLE);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyUp ----> " + keyCode);
        if(KeyEvent.KEYCODE_VOLUME_DOWN == keyCode) {
            key_flag = key_flag | 0x1;
            if(7 == key_flag)
                mHandler.sendEmptyMessage(MSG_KEY_TEST_OK);
            else
                mHandler.sendEmptyMessage(MSG_KEY_TEST_ERROR);
        } else if(KeyEvent.KEYCODE_VOLUME_UP == keyCode) { //Two power keys need to be pressed   需要按下两次电源键
            if(4 == key_flag || 5 == key_flag)
                key_flag = key_flag | 0x2;
            else
                key_flag = key_flag | 0x4;

            if(7 == key_flag)
                mHandler.sendEmptyMessage(MSG_KEY_TEST_OK);
            else
                mHandler.sendEmptyMessage(MSG_KEY_TEST_ERROR);
        }
        return super.onKeyUp(keyCode, event);
    }

    public static int setFunKey(int value) {
        Log.d(TAG, "setFanKey: " + value);
        File file = new File(SYS_ADC_TABLE);
        if((file == null) || !file.exists()) {
            Log.e(TAG, "" + SYS_ADC_TABLE + " no exist");
            return -1;
        }
        try {
            FileOutputStream fout = new FileOutputStream(file);
            PrintWriter pWriter = new PrintWriter(fout);
            pWriter.println("home:" + String.valueOf(value) + ":2:108:40");
            pWriter.flush();
            pWriter.close();
            fout.close();
        } catch (IOException e) {
            Log.e(TAG, "setFanKey ERR: " + e);
            return -1;
        }
        SystemProperties.set("persist.sys.func.key.action", Integer.toString(value));
        return 0;
    }
}
