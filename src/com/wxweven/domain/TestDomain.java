package com.wxweven.domain;

import java.io.Serializable;

public class TestDomain implements Serializable {

	private static final long serialVersionUID = -11730301468728093L;
	
	private Integer id;
	private String userName;
	private int age;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
