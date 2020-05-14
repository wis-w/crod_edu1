package com.edu.crowd.entity;

import java.util.List;
import java.util.Map;

/**
 * @author wyg_edu
 * @date 2020年4月29日 下午9:22:52
 * @version v1.0
 */
public class Student {

	private Integer stuId;
	private String stuName;
	private Address address;
	private List<Subject> subject;
	private Map<String, String> map;
	public Student(Integer stuId, String stuName, Address address, List<Subject> subject, Map<String, String> map) {
		super();
		this.stuId = stuId;
		this.stuName = stuName;
		this.address = address;
		this.subject = subject;
		this.map = map;
	}
	@Override
	public String toString() {
		return "Student [stuId=" + stuId + ", stuName=" + stuName + ", address=" + address + ", subject=" + subject
				+ ", map=" + map + "]";
	}
	public Student() {
		super();
	}
	public Integer getStuId() {
		return stuId;
	}
	public void setStuId(Integer stuId) {
		this.stuId = stuId;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public List<Subject> getSubject() {
		return subject;
	}
	public void setSubject(List<Subject> subject) {
		this.subject = subject;
	}
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	
	

}
