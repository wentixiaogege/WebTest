package edu.itu.html;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JSONResult {
 
	public JSONResult(int httpcode, String result) {
		super();
		this.httpcode = httpcode;
		this.result = result;
	}

	public JSONResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	private int httpcode;
	private String result ;

	public String getResult() {
	
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getHttpcode() {
		return httpcode;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "debugging the httpcode=["+httpcode+"] result = "+result+"";
	}

	public void setHttpcode(int httpcode) {
		this.httpcode = httpcode;
	}
	
}
