// default package
// Generated Mar 26, 2015 3:44:56 PM by Hibernate Tools 3.4.0.CR1

/**
 * SmartMeterLoadConfig generated by hbm2java
 */
public class SmartMeterLoadConfig implements java.io.Serializable {

	private int smLoadId;
	private Integer smId;
	private String smLoadName;
	private String voltageName;
	private String currentName;
	private String thetaName;

	public SmartMeterLoadConfig() {
	}

	public SmartMeterLoadConfig(int smLoadId) {
		this.smLoadId = smLoadId;
	}

	public SmartMeterLoadConfig(int smLoadId, Integer smId, String smLoadName,
			String voltageName, String currentName, String thetaName) {
		this.smLoadId = smLoadId;
		this.smId = smId;
		this.smLoadName = smLoadName;
		this.voltageName = voltageName;
		this.currentName = currentName;
		this.thetaName = thetaName;
	}

	public int getSmLoadId() {
		return this.smLoadId;
	}

	public void setSmLoadId(int smLoadId) {
		this.smLoadId = smLoadId;
	}

	public Integer getSmId() {
		return this.smId;
	}

	public void setSmId(Integer smId) {
		this.smId = smId;
	}

	public String getSmLoadName() {
		return this.smLoadName;
	}

	public void setSmLoadName(String smLoadName) {
		this.smLoadName = smLoadName;
	}

	public String getVoltageName() {
		return this.voltageName;
	}

	public void setVoltageName(String voltageName) {
		this.voltageName = voltageName;
	}

	public String getCurrentName() {
		return this.currentName;
	}

	public void setCurrentName(String currentName) {
		this.currentName = currentName;
	}

	public String getThetaName() {
		return this.thetaName;
	}

	public void setThetaName(String thetaName) {
		this.thetaName = thetaName;
	}

}
