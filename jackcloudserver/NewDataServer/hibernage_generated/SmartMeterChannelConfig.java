// default package
// Generated Mar 26, 2015 3:44:56 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * SmartMeterChannelConfig generated by hbm2java
 */
public class SmartMeterChannelConfig implements java.io.Serializable {

	private Integer smChannelId;
	private Date timestamp;
	private Integer smId;
	private byte[] smConfigCode;
	private String channel0Name;
	private String channel1Name;
	private String channel2Name;
	private String channel3Name;
	private String channel4Name;
	private String channel5Name;
	private String channel6Name;
	private String channel7Name;

	public SmartMeterChannelConfig() {
	}

	public SmartMeterChannelConfig(Integer smId, byte[] smConfigCode,
			String channel0Name, String channel1Name, String channel2Name,
			String channel3Name, String channel4Name, String channel5Name,
			String channel6Name, String channel7Name) {
		this.smId = smId;
		this.smConfigCode = smConfigCode;
		this.channel0Name = channel0Name;
		this.channel1Name = channel1Name;
		this.channel2Name = channel2Name;
		this.channel3Name = channel3Name;
		this.channel4Name = channel4Name;
		this.channel5Name = channel5Name;
		this.channel6Name = channel6Name;
		this.channel7Name = channel7Name;
	}

	public Integer getSmChannelId() {
		return this.smChannelId;
	}

	public void setSmChannelId(Integer smChannelId) {
		this.smChannelId = smChannelId;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getSmId() {
		return this.smId;
	}

	public void setSmId(Integer smId) {
		this.smId = smId;
	}

	public byte[] getSmConfigCode() {
		return this.smConfigCode;
	}

	public void setSmConfigCode(byte[] smConfigCode) {
		this.smConfigCode = smConfigCode;
	}

	public String getChannel0Name() {
		return this.channel0Name;
	}

	public void setChannel0Name(String channel0Name) {
		this.channel0Name = channel0Name;
	}

	public String getChannel1Name() {
		return this.channel1Name;
	}

	public void setChannel1Name(String channel1Name) {
		this.channel1Name = channel1Name;
	}

	public String getChannel2Name() {
		return this.channel2Name;
	}

	public void setChannel2Name(String channel2Name) {
		this.channel2Name = channel2Name;
	}

	public String getChannel3Name() {
		return this.channel3Name;
	}

	public void setChannel3Name(String channel3Name) {
		this.channel3Name = channel3Name;
	}

	public String getChannel4Name() {
		return this.channel4Name;
	}

	public void setChannel4Name(String channel4Name) {
		this.channel4Name = channel4Name;
	}

	public String getChannel5Name() {
		return this.channel5Name;
	}

	public void setChannel5Name(String channel5Name) {
		this.channel5Name = channel5Name;
	}

	public String getChannel6Name() {
		return this.channel6Name;
	}

	public void setChannel6Name(String channel6Name) {
		this.channel6Name = channel6Name;
	}

	public String getChannel7Name() {
		return this.channel7Name;
	}

	public void setChannel7Name(String channel7Name) {
		this.channel7Name = channel7Name;
	}

}
