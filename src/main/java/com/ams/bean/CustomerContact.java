package com.ams.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = CustomerContact.TABLE_NAME)
public class CustomerContact extends BaseEntity {

	public static final String REMARK = "remark";

	public static final String TABLE_NAME = "CustomerContact";

	public static final String CONTACT_MOBILE_NUMBER = "contactMobileNumber";

	public static final String POSITION = "position";

	public static final String CONTACT_PERSON = "contactPerson";

	public static final String CUSTOMER_ID = "customerId";

	@Column(name = CUSTOMER_ID)
	public String customerId;

	@Column(name = CONTACT_PERSON)
	public String contactPerson;

	@Column(name = POSITION)
	public String position;

	@Column(name = CONTACT_MOBILE_NUMBER)
	public String contactMobileNumber;

	@Column(name = REMARK)
	public String remark;
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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

	public void setContactMobileNumber(String contactMobileNumber) {
		this.contactMobileNumber = contactMobileNumber;
	}

}
