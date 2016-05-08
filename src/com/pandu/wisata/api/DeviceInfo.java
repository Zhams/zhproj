package com.pandu.wisata.api;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import java.util.Locale;

public class DeviceInfo {

	private String manufacture = "";
	private String handset_version = "";
	private String os = "";
	private String os_version = "";
	private String lang = "";
	private String imei1 = "";
	private String imei2 = "";
	private String imei3 = "";
	private String app_version = "";
	private String cid = "";
	private String lac = "";

	// Construct
	@SuppressLint("SimpleDateFormat")
	public DeviceInfo(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		CellLocation location = telephonyManager.getCellLocation();

		String tLang = Locale.getDefault().getLanguage();
		if (tLang != null) this.lang = tLang;
		String tImei1 = telephonyManager.getDeviceId();
		//int phoneCount = telephonyManager.getPhoneCount();
		if (tImei1 != null) this.imei1 = tImei1;

		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			this.app_version = info.versionName;
		} catch (NameNotFoundException e) {
			this.app_version = "unknown";
		}

		this.os = "ANDROID";
		this.os_version = android.os.Build.VERSION.RELEASE;
		this.handset_version = android.os.Build.MODEL;
		this.manufacture = android.os.Build.MANUFACTURER;

		if (location instanceof GsmCellLocation) {
			try {
				this.cid = (String) Integer.toString(((GsmCellLocation) location).getCid());
			} catch (Exception e) {
				this.cid = "";
			}

			try {
				this.lac = Integer.toString(((GsmCellLocation) location).getLac());
			} catch (Exception e) {
				this.lac = "";
			}
		} else {
			try {
				this.cid = (String) Integer.toString(((CdmaCellLocation) location).getBaseStationId());
			} catch (Exception e) {
				this.cid = "";
			}

			try {
				this.lac = Integer.toString(((CdmaCellLocation) location).getNetworkId());
			} catch (Exception e) {
				this.lac = "";
			}
		}

	}
	
	/**
	 * Returns the value of the field called 'lang'.
	 * @return Returns the lang.
	 */
	public String getLang() {
		return this.lang;
	}

	/**
	 * Sets the field called 'lang' to the given value.
	 * @param lang The lang to set.
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * Returns the value of the field called 'imei1'.
	 * @return Returns the imei1.
	 */
	public String getImei1() {
		return this.imei1;
	}

	/**
	 * Sets the field called 'imei1' to the given value.
	 * @param imei1 The imei1 to set.
	 */
	public void setImei1(String imei1) {
		this.imei1 = imei1;
	}

	/**
	 * Returns the value of the field called 'imei2'.
	 * @return Returns the imei2.
	 */
	public String getImei2() {
		return this.imei2;
	}

	/**
	 * Sets the field called 'imei2' to the given value.
	 * @param imei2 The imei2 to set.
	 */
	public void setImei2(String imei2) {
		this.imei2 = imei2;
	}

	/**
	 * Returns the value of the field called 'imei3'.
	 * @return Returns the imei3.
	 */
	public String getImei3() {
		return this.imei3;
	}

	/**
	 * Sets the field called 'imei3' to the given value.
	 * @param imei3 The imei3 to set.
	 */
	public void setImei3(String imei3) {
		this.imei3 = imei3;
	}

	/**
	 * Returns the value of the field called 'app_version'.
	 * @return Returns the app_version.
	 */
	public String getApp_version() {
		return this.app_version;
	}

	/**
	 * Sets the field called 'app_version' to the given value.
	 * @param app_version The app_version to set.
	 */
	public void setApp_version(String app_version) {
		this.app_version = app_version;
	}

	/**
	 * Returns the value of the field called 'os'.
	 * @return Returns the os.
	 */
	public String getOs() {
		return this.os;
	}

	/**
	 * Sets the field called 'os' to the given value.
	 * @param os The os to set.
	 */
	public void setOs(String os) {
		this.os = os;
	}

	/**
	 * Returns the value of the field called 'os_version'.
	 * @return Returns the os_version.
	 */
	public String getOs_version() {
		return this.os_version;
	}

	/**
	 * Sets the field called 'os_version' to the given value.
	 * @param os_version The os_version to set.
	 */
	public void setOs_version(String os_version) {
		this.os_version = os_version;
	}

	/**
	 * Returns the value of the field called 'handset_version'.
	 * @return Returns the handset_version.
	 */
	public String getHandset_version() {
		return this.handset_version;
	}

	/**
	 * Sets the field called 'handset_version' to the given value.
	 * @param handset_version The handset_version to set.
	 */
	public void setHandset_version(String handset_version) {
		this.handset_version = handset_version;
	}

	/**
	 * Returns the value of the field called 'manufacture'.
	 * @return Returns the manufacture.
	 */
	public String getManufacture() {
		return this.manufacture;
	}

	/**
	 * Sets the field called 'manufacture' to the given value.
	 * @param manufacture The manufacture to set.
	 */
	public void setManufacture(String manufacture) {
		this.manufacture = manufacture;
	}

	/**
	 * Returns the value of the field called 'cid'.
	 * @return Returns the cid.
	 */
	public String getCid() {
		return this.cid;
	}

	/**
	 * Sets the field called 'cid' to the given value.
	 * @param cid The cid to set.
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}

	/**
	 * Returns the value of the field called 'lac'.
	 * @return Returns the lac.
	 */
	public String getLac() {
		return this.lac;
	}

	/**
	 * Sets the field called 'lac' to the given value.
	 * @param lac The lac to set.
	 */
	public void setLac(String lac) {
		this.lac = lac;
	}

}
