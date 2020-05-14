package com.edu.crowd.entity;
/**
 * @author wyg_edu
 * @date 2020年4月29日 下午9:23:31
 * @version v1.0
 */
public class Address {
	
	private String province;
	private String city;
	private String street;
	@Override
	public String toString() {
		return "Address [province=" + province + ", city=" + city + ", street=" + street + "]";
	}
	public Address() {
		super();
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public Address(String province, String city, String street) {
		super();
		this.province = province;
		this.city = city;
		this.street = street;
	}

}
