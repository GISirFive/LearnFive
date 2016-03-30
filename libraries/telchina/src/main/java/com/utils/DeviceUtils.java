package com.utils;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;

public class DeviceUtils {
	private Context context;
	private static DeviceUtils deviceHelper;

	public static DeviceUtils getInstance(Context c) {
		if ((deviceHelper == null) && (c != null))
			deviceHelper = new DeviceUtils(c);
		return deviceHelper;
	}

	private DeviceUtils(Context context) {
		this.context = context.getApplicationContext();
	}

	public boolean isRooted() {
		return false;
	}

	public String getSSID() {
		WifiManager wifi = (WifiManager) getSystemService("wifi");
		if (wifi == null)
			return null;
		WifiInfo info = wifi.getConnectionInfo();
		if (info != null) {
			String ssid = info.getSSID().replace("\"", "");
			return ((ssid == null) ? null : ssid);
		}
		return null;
	}

	public String getBssid() {
		WifiManager wifi = (WifiManager) getSystemService("wifi");
		if (wifi == null)
			return null;
		WifiInfo info = wifi.getConnectionInfo();
		if (info != null) {
			String bssid = info.getBSSID();
			return ((bssid == null) ? null : bssid);
		}
		return null;
	}

	public String getMacAddress() {
		WifiManager wifi = (WifiManager) getSystemService("wifi");
		if (wifi == null)
			return null;
		WifiInfo info = wifi.getConnectionInfo();
		if (info != null) {
			String mac = info.getMacAddress();
			return ((mac == null) ? null : mac);
		}
		return null;
	}

	public String getModel() {
		return Build.MODEL;
	}

	public String getManufacturer() {
		return Build.MANUFACTURER;
	}

	public String getDeviceId() {
		String deviceId = getIMEI();
		if ((TextUtils.isEmpty(deviceId)) && (Build.VERSION.SDK_INT >= 9))
			return getSerialno();
		return deviceId;
	}

	/** @deprecated */
	public String getMime() {
		return getIMEI();
	}

	public String getIMEI() {
		TelephonyManager phone = (TelephonyManager) getSystemService("phone");
		if (phone == null)
			return null;
		String deviceId = null;
		try {
			if (checkPermission("android.permission.READ_PHONE_STATE"))
				deviceId = phone.getDeviceId();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		if (TextUtils.isEmpty(deviceId))
			return null;
		return deviceId;
	}

	public String getSerialno() {
		String serialno = null;
		if (Build.VERSION.SDK_INT >= 9)
			try {
				Class c = Class.forName("android.os.SystemProperties");
				Method get = c.getMethod("get", new Class[] { String.class,
						String.class });
				serialno = (String) (String) get.invoke(c, new Object[] {
						"ro.serialno", "unknown" });
			} catch (Throwable t) {
				t.printStackTrace();
				serialno = null;
			}
		return serialno;
	}

	public String getDeviceDataNotAES() {
		return new StringBuilder().append(getModel()).append("|")
				.append(getOSVersion()).append("|").append(getManufacturer())
				.append("|").append(getCarrier()).append("|")
				.append(getScreenSize()).toString();
	}

	/** @deprecated */
	public String getOSVersion() {
		return String.valueOf(getOSVersionInt());
	}

	public int getOSVersionInt() {
		return Build.VERSION.SDK_INT;
	}

	public String getOSVersionName() {
		return Build.VERSION.RELEASE;
	}

	public String getOSLanguage() {
		return Locale.getDefault().getLanguage();
	}

	public String getOSCountry() {
		return Locale.getDefault().getCountry();
	}

	public String getScreenSize() {
		int[] size = ScreenUtils.getScreenSize(this.context);
		if (this.context.getResources().getConfiguration().orientation == 1)
			return new StringBuilder().append(size[0]).append("x")
					.append(size[1]).toString();
		return new StringBuilder().append(size[1]).append("x").append(size[0])
				.toString();
	}

	public String getCarrier() {
		TelephonyManager tm = (TelephonyManager) getSystemService("phone");
		if (tm == null)
			return "-1";
		String operator = tm.getSimOperator();
		if (TextUtils.isEmpty(operator))
			operator = "-1";
		return operator;
	}

	public String getCarrierName() {
		TelephonyManager tm = (TelephonyManager) getSystemService("phone");
		if (tm == null)
			return null;
		try {
			if (checkPermission("android.permission.READ_PHONE_STATE")) {
				String operator = tm.getSimOperatorName();
				if (TextUtils.isEmpty(operator))
					operator = null;
				return operator;
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}

	public String getMCC() {
		TelephonyManager tm = (TelephonyManager) getSystemService("phone");
		if (tm == null)
			return null;
		String imsi = getIMSI();
		if ((imsi != null) && (imsi.length() >= 3))
			return imsi.substring(0, 3);
		return null;
	}

	public String getMNC() {
		TelephonyManager tm = (TelephonyManager) getSystemService("phone");
		if (tm == null)
			return null;
		String imsi = getIMSI();
		if ((imsi != null) && (imsi.length() >= 5))
			return imsi.substring(3, 5);
		return null;
	}

	public String getSimSerialNumber() {
		TelephonyManager tm = (TelephonyManager) getSystemService("phone");
		if (tm == null)
			return "-1";
		return tm.getSimSerialNumber();
	}

	public String getLine1Number() {
		TelephonyManager tm = (TelephonyManager) getSystemService("phone");
		if (tm == null)
			return "-1";
		return tm.getLine1Number();
	}

	public String getBluetoothName() {
		try {
			BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
			if ((myDevice != null)
					&& (checkPermission("android.permission.BLUETOOTH")))
				return myDevice.getName();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	private Object getSystemService(String name) {
		try {
			return this.context.getSystemService(name);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}

	public String getNetworkType() {
		ConnectivityManager conn = (ConnectivityManager) getSystemService("connectivity");
		if (conn == null)
			return "none";
		try {
			if (!(checkPermission("android.permission.ACCESS_NETWORK_STATE")))
				return "none";
		} catch (Throwable t) {
			t.printStackTrace();
			return "none";
		}
		NetworkInfo network = conn.getActiveNetworkInfo();
		if ((network == null) || (!(network.isAvailable())))
			return "none";
		int type = network.getType();
		switch (type) {
		case 1:
			return "wifi";
		case 0:
			if (is4GMobileNetwork())
				return "4G";
			return ((isFastMobileNetwork()) ? "3G" : "2G");
		case 7:
			return "bluetooth";
		case 8:
			return "dummy";
		case 9:
			return "ethernet";
		case 6:
			return "wimax";
		case 2:
		case 3:
		case 4:
		case 5:
		}
		return String.valueOf(type);
	}

	public String getNetworkTypeForStatic() {
		String networkType = getNetworkType().toLowerCase();
		if ((TextUtils.isEmpty(networkType)) || ("none".equals(networkType)))
			return "none";
		if ((networkType.startsWith("4g")) || (networkType.startsWith("3g"))
				|| (networkType.startsWith("2g")))
			return "cell";
		if (networkType.startsWith("wifi"))
			return "wifi";
		return "other";
	}

	public String getDetailNetworkTypeForStatic() {
		String networkType = getNetworkType().toLowerCase();
		if ((TextUtils.isEmpty(networkType)) || ("none".equals(networkType)))
			return "none";
		if (networkType.startsWith("wifi"))
			return "wifi";
		if (networkType.startsWith("4g"))
			return "4g";
		if (networkType.startsWith("3g"))
			return "3g";
		if (networkType.startsWith("2g"))
			return "2g";
		if (networkType.startsWith("bluetooth"))
			return "bluetooth";
		return networkType;
	}

	public int getPlatformCode() {
		return 1;
	}

	private boolean is4GMobileNetwork() {
		TelephonyManager phone = (TelephonyManager) getSystemService("phone");
		if (phone == null)
			return false;
		return (phone.getNetworkType() == 13);
	}

	private boolean isFastMobileNetwork() {
		TelephonyManager phone = (TelephonyManager) getSystemService("phone");
		if (phone == null)
			return false;
		switch (phone.getNetworkType()) {
		case 7:
			return false;
		case 4:
			return false;
		case 2:
			return false;
		case 5:
			return true;
		case 6:
			return true;
		case 1:
			return false;
		case 8:
			return true;
		case 10:
			return true;
		case 9:
			return true;
		case 3:
			return true;
		case 14:
			return true;
		case 12:
			return true;
		case 15:
			return true;
		case 11:
			return false;
		case 13:
			return true;
		case 0:
			return false;
		}
		return false;
	}

	public JSONArray getRunningApp() {
		JSONArray appNmes = new JSONArray();
		ActivityManager am = (ActivityManager) getSystemService("activity");
		if (am == null)
			return appNmes;
		List apps = am.getRunningAppProcesses();
		if (apps == null)
			return appNmes;
		Iterator i$ = apps.iterator();
		while (i$.hasNext()) {
			ActivityManager.RunningAppProcessInfo app = (ActivityManager.RunningAppProcessInfo) i$
					.next();
			appNmes.put(app.processName);
		}
		return appNmes;
	}

	public String getRunningAppStr() throws JSONException {
		JSONArray apps = getRunningApp();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < apps.length(); ++i) {
			if (i > 0)
				sb.append(',');
			sb.append(String.valueOf(apps.get(i)));
		}
		return sb.toString();
	}

	public String getCharAndNumr(int length) {
		long currentTime = System.currentTimeMillis();
		long elapseTime = SystemClock.elapsedRealtime();
		long realTime = currentTime ^ elapseTime;
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(realTime);
		Random random = new Random();
		for (int i = 0; i < length; ++i) {
			String charOrNum = (random.nextInt(2) % 2 == 0) ? "char" : "num";
			if ("char".equalsIgnoreCase(charOrNum)) {
				char charValue = (char) (97 + random.nextInt(26));
				stringBuffer.insert(i + 1, charValue);
			} else {
				stringBuffer.insert(stringBuffer.length(), random.nextInt(10));
			}
		}
		return stringBuffer.toString().substring(0, 40);
	}

	private String getLocalDeviceKey() throws Throwable {
		if (!(getSdcardState()))
			return null;
		String sdPath = getSdcardPath();
		File cacheRoot = new File(sdPath, "ShareSDK");
		if (!(cacheRoot.exists()))
			return null;
		File keyFile = new File(cacheRoot, ".dk");
		if (!(keyFile.exists()))
			return null;
		FileInputStream fis = new FileInputStream(keyFile);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object key = ois.readObject();
		String strKey = null;
		if ((key != null) && (key instanceof char[])) {
			char[] cKey = (char[]) (char[]) key;
			strKey = String.valueOf(cKey);
		}
		ois.close();
		return strKey;
	}

	private void saveLocalDeviceKey(String key) throws Throwable {
		if (!(getSdcardState()))
			return;
		String sdPath = getSdcardPath();
		File cacheRoot = new File(sdPath, "ShareSDK");
		if (!(cacheRoot.exists()))
			cacheRoot.mkdirs();
		File keyFile = new File(cacheRoot, ".dk");
		if (keyFile.exists())
			keyFile.delete();
		FileOutputStream fos = new FileOutputStream(keyFile);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		char[] cKey = key.toCharArray();
		oos.writeObject(cKey);
		oos.flush();
		oos.close();
	}

	public String getPackageName() {
		return this.context.getPackageName();
	}

	public String getAppName() {
		String appName = this.context.getApplicationInfo().name;
		if (appName != null)
			return appName;
		int appLbl = this.context.getApplicationInfo().labelRes;
		if (appLbl > 0)
			appName = this.context.getString(appLbl);
		else
			appName = String
					.valueOf(this.context.getApplicationInfo().nonLocalizedLabel);
		return appName;
	}

	public int getAppVersion() {
		try {
			PackageManager pm = this.context.getPackageManager();
			PackageInfo pi = pm
					.getPackageInfo(this.context.getPackageName(), 0);
			return pi.versionCode;
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return 0;
	}

	public String getAppVersionName() {
		try {
			PackageManager pm = this.context.getPackageManager();
			PackageInfo pi = pm
					.getPackageInfo(this.context.getPackageName(), 0);
			return pi.versionName;
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return "1.0";
	}

	public ArrayList<HashMap<String, String>> getInstalledApp(
			boolean includeSystemApp) {
		try {
			PackageManager pm = this.context.getPackageManager();
			List pis = pm.getInstalledPackages(0);
			ArrayList apps = new ArrayList();
			Iterator i$ = pis.iterator();
			while (i$.hasNext()) {
				PackageInfo pi = (PackageInfo) i$.next();
				if ((!(includeSystemApp)) && (isSystemApp(pi)))
					continue;
				HashMap app = new HashMap();
				app.put("pkg", pi.packageName);
				app.put("name", pi.applicationInfo.loadLabel(pm).toString());
				app.put("version", pi.versionName);
				apps.add(app);
			}
			return apps;
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return new ArrayList();
	}

	private boolean isSystemApp(PackageInfo pi) {
		boolean isSysApp = (pi.applicationInfo.flags & 0x1) == 1;
		boolean isSysUpd = (pi.applicationInfo.flags & 0x80) == 1;
		return ((isSysApp) || (isSysUpd));
	}

	public String getNetworkOperator() {
		TelephonyManager tm = (TelephonyManager) getSystemService("phone");
		if (tm == null)
			return null;
		String operator = tm.getNetworkOperator();
		return operator;
	}

	public boolean checkPermission(String permission) throws Throwable {
		int res;
		if (Build.VERSION.SDK_INT >= 23) {
			try {
				Integer ret = context.checkCallingOrSelfPermission(permission);
				res = (ret == null) ? -1 : ret.intValue();
			} catch (Throwable t) {
				res = -1;
			}
		} else {
			this.context.checkPermission(permission,
					android.os.Process.myPid(), android.os.Process.myUid());
			res = this.context.getPackageManager().checkPermission(permission,
					getPackageName());
		}
		return (res == 0);
	}

	public boolean getSdcardState() {
		try {
			return "mounted".equals(Environment.getExternalStorageState());
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return false;
	}

	public String getSdcardPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	public String getAndroidID() {
		String androidId = Settings.Secure.getString(
				this.context.getContentResolver(), "android_id");
		return androidId;
	}

	public void hideSoftInput(View view) {
		Object service = getSystemService("input_method");
		if (service == null)
			return;
		InputMethodManager imm = (InputMethodManager) service;
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	public void showSoftInput(View view) {
		Object service = getSystemService("input_method");
		if (service == null)
			return;
		InputMethodManager imm = (InputMethodManager) service;
		imm.toggleSoftInputFromWindow(view.getWindowToken(), 2, 0);
	}

	/** @deprecated */
	public boolean isMainProcess(Context context, int pid) {
		return isMainProcess(pid);
	}

	public boolean isMainProcess(int pid) {
		String application = null;
		ActivityManager mActivityManager = (ActivityManager) getSystemService("activity");
		if (mActivityManager.getRunningAppProcesses() == null)
			return (pid <= 0);
		int mPid = (pid <= 0) ? android.os.Process.myPid() : pid;
		Iterator i$ = mActivityManager.getRunningAppProcesses().iterator();
		while (i$.hasNext()) {
			ActivityManager.RunningAppProcessInfo appProcess = (ActivityManager.RunningAppProcessInfo) i$
					.next();
			if (appProcess.pid == mPid) {
				application = appProcess.processName;
				break;
			}
		}
		return getPackageName().equals(application);
	}

	public String getIMSI() {
		TelephonyManager phone = (TelephonyManager) getSystemService("phone");
		if (phone == null)
			return null;
		String imsi = null;
		try {
			if (checkPermission("android.permission.READ_PHONE_STATE"))
				imsi = phone.getSubscriberId();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		if (TextUtils.isEmpty(imsi))
			return null;
		return imsi;
	}

	public String getIPAddress() {
		try {
			if (checkPermission("android.permission.INTERNET")) {
				Enumeration en = NetworkInterface.getNetworkInterfaces();
				while (en.hasMoreElements()) {
					NetworkInterface intf = (NetworkInterface) en.nextElement();
					Enumeration enumIpAddr = intf.getInetAddresses();
					while (enumIpAddr.hasMoreElements()) {
						InetAddress inetAddress = (InetAddress) enumIpAddr
								.nextElement();
						if ((!(inetAddress.isLoopbackAddress()))
								&& (inetAddress instanceof Inet4Address))
							return inetAddress.getHostAddress();
					}
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "0.0.0.0";
	}

	public HashMap<String, String> ping(String address, int count,
			int packetsize) {
		ArrayList sucRes = new ArrayList();
		try {
			String cmd = new StringBuilder().append("ping -c ").append(count)
					.append(" -s ").append(packetsize).append(" ")
					.append(address).toString();
			int bytes = packetsize + 8;
			java.lang.Process p = Runtime.getRuntime().exec(cmd);
			InputStreamReader isr = new InputStreamReader(p.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			for (String line = br.readLine(); line != null; line = br
					.readLine()) {
				if (!(line.startsWith(new StringBuilder().append(bytes)
						.append(" bytes from").toString())))
					continue;
				if (line.endsWith("ms"))
					line = line.substring(0, line.length() - 2).trim();
				else if (line.endsWith("s"))
					line = new StringBuilder()
							.append(line.substring(0, line.length() - 1).trim())
							.append("000").toString();
				int i = line.indexOf("time=");
				if (i <= 0)
					continue;
				line = line.substring(i + 5).trim();
				try {
					sucRes.add(Float.valueOf(Float.parseFloat(line)));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			p.waitFor();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		int sucCount = sucRes.size();
		int fldCount = count - sucRes.size();
		float min = 0.0F;
		float max = 0.0F;
		float average = 0.0F;
		if (sucCount > 0) {
			min = 3.4028235E+38F;
			for (int i = 0; i < sucCount; ++i) {
				float item = ((Float) sucRes.get(i)).floatValue();
				if (item < min)
					min = item;
				if (item > max)
					max = item;
				average += item;
			}
			average /= sucCount;
		}
		HashMap map = new HashMap();
		map.put("address", address);
		map.put("transmitted", String.valueOf(count));
		map.put("received", String.valueOf(sucCount));
		map.put("loss", String.valueOf(fldCount));
		map.put("min", String.valueOf(min));
		map.put("max", String.valueOf(max));
		map.put("avg", String.valueOf(average));
		return map;
	}
}