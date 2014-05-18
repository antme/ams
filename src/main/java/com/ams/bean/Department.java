package com.ams.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = Department.TABLE_NAME)
public class Department extends BaseEntity {

	
	public static final String DEPARTMENT_MANAGER_ID = "departmentManagerId";

	public static final String DEPARTMENT_NAME = "departmentName";

	public static final String TABLE_NAME = "Department";
	
	@Column(name = DEPARTMENT_NAME)
	public String departmentName;
	
	@Column(name = DEPARTMENT_MANAGER_ID)
	public String departmentManagerId;
	

	@Column(name = "departmentDescription")
	public String departmentDescription;
	


	
	public String userName;
	
	public String getDepartmentManagerId() {
		return departmentManagerId;
	}

	public void setDepartmentManagerId(String departmentManagerId) {
		this.departmentManagerId = departmentManagerId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentDescription() {
		return departmentDescription;
	}

	public void setDepartmentDescription(String departmentDescription) {
		this.departmentDescription = departmentDescription;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	

}
