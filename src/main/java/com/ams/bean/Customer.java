package com.ams.bean;

import com.eweblib.bean.BaseEntity;

public class Customer extends BaseEntity {

	public String projects;

	public String name;

	public String address;

	public String remark;

	public String contactPerson;

	public String position;

	public String contactMobileNumber;

	public String getProjects() {
		return projects;
	}

	public void setProjects(String projectName) {
		this.projects = projectName;
	}

	public String getName() {
		return name;
	}

	public void setName(String customerName) {
		this.name = customerName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getContactMobileNumber() {
		return contactMobileNumber;
	}

	public void setContactMobileNumber(String mobileNumber) {
		this.contactMobileNumber = mobileNumber;
	}

	public static void main(String args[]) {

		Customer customer = new Customer();
		customer.setAddress("");
		customer.setContactPerson("");
		customer.setName("");
		customer.setId("");
		customer.setContactMobileNumber("");
		customer.setPosition("");
		customer.setProjects("");
		
		System.out.println(customer.toString());
		
	}
}
