package com.edu.crowd.entity;
/**
 * @author wyg_edu
 * @date 2020年4月29日 下午9:25:13
 * @version v1.0
 */
public class Subject {
	
	private String subjectName;
	private Integer subjectScore;
	public Subject(String subjectName, Integer subjectScore) {
		super();
		this.subjectName = subjectName;
		this.subjectScore = subjectScore;
	}
	public Subject() {
		super();
	}
	@Override
	public String toString() {
		return "Subject [subjectName=" + subjectName + ", subjectScore=" + subjectScore + "]";
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public Integer getSubjectScore() {
		return subjectScore;
	}
	public void setSubjectScore(Integer subjectScore) {
		this.subjectScore = subjectScore;
	}
	
	

}
