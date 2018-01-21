package com.ramit.domain;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement(name = "coffee")
public class Coffee {
 
	private String name;
	private String type;
	private int quanlity;
 	
	public String getType() {
		return type;
	}

	@XmlElement
	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}
 
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}
 
	public int getQuanlity() {
		return quanlity;
	}
 
	@XmlElement
	public void setQuanlity(int quanlity) {
		this.quanlity = quanlity;
	}
 
	public Coffee(String name, String type, int quanlity) {
		this.name = name;
		this.type = type;
		this.quanlity = quanlity;
	}
 
	public Coffee() {
	}
 
}