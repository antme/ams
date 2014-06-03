package com.ams.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = Customer.TABLE_NAME)
public class Customer extends BaseEntity {
	public static final String CONTACT_MOBILE_NUMBER = "contactMobileNumber";

	public static final String POSITION = "position";

	public static final String CONTACT_PERSON = "contactPerson";

	public static final String REMARK = "remark";

	public static final String ADDRESS = "address";

	public static final String NAME = "name";

	public static final String TABLE_NAME = "Customer";

	@Column(name = NAME)
	public String name;

	@Column(name = ADDRESS)
	public String address;

	@Column(name = REMARK)
	public String remark;

	@Column(name = CONTACT_PERSON)
	public String contactPerson;

	@Column(name = POSITION)
	public String position;

	@Column(name = CONTACT_MOBILE_NUMBER)
	public String contactMobileNumber;

	public List<CustomerContact> contacts;

	public List<CustomerContact> getContacts() {
		return contacts;
	}

	public void setContacts(List<CustomerContact> contacts) {
		this.contacts = contacts;
	}

	public String projects;

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
