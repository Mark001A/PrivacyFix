package cn.guo.privacyfix;

public class ContentBean1 {
	private String imei;
	private String imsi;
	private String number;
	private String simserial;
	private String wifimac;
	private String bluemac;
	private String androidid;
	private String serial;
	private String brand;
	private String model;
	private String simOperator;
	private String SSID;
	private String MacAddress;
	private String version_codes;
	private String release;
	private String resolution;

	public ContentBean1() {
		// TODO Auto-generated constructor stub
	}

	public ContentBean1(String imei, String imsi, String number,
			String simserial, String wifimac, String bluemac, String androidid,
			String serial, String brand, String model, String simOperator,
			String SSID, String MacAddress, String version_codes,
			String release) {
		// TODO Auto-generated constructor stub
		this.setImei(imei);
		this.setImsi(imsi);
		this.setNumber(number);
		this.setSimserial(simserial);
		this.setWifimac(wifimac);
		this.setBluemac(bluemac);
		this.setAndroidid(androidid);
		this.setSerial(serial);
		this.setBrand(brand);
		this.setModel(model);
		this.setSimOperator(simOperator);
		this.setSSID(SSID);
		this.setMacAddress(MacAddress);
		this.setVersion_codes(version_codes);
		this.setRelease(release);

	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getSimserial() {
		return simserial;
	}

	public void setSimserial(String simserial) {
		this.simserial = simserial;
	}

	public String getWifimac() {
		return wifimac;
	}

	public void setWifimac(String wifimac) {
		this.wifimac = wifimac;
	}

	public String getBluemac() {
		return bluemac;
	}

	public void setBluemac(String bluemac) {
		this.bluemac = bluemac;
	}

	public String getAndroidid() {
		return androidid;
	}

	public void setAndroidid(String androidid) {
		this.androidid = androidid;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSimOperator() {
		return simOperator;
	}

	public void setSimOperator(String simOperator) {
		this.simOperator = simOperator;
	}

	public String getSSID() {
		return SSID;
	}

	public void setSSID(String sSID) {
		SSID = sSID;
	}

	public String getMacAddress() {
		return MacAddress;
	}

	public void setMacAddress(String macAddress) {
		MacAddress = macAddress;
	}

	public String getVersion_codes() {
		return version_codes;
	}

	public void setVersion_codes(String version_codes) {
		this.version_codes = version_codes;
	}

	public String getRelease() {
		return release;
	}

	public void setRelease(String release) {
		this.release = release;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
}
