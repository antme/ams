package com.ams.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = Department.TABLE_NAME)
public class Department extends BaseEntity {

	
	public static final String TABLE_NAME = "Department";
	
	@Column(name = "departmentName")
	public String departmentName;

	@Column(name = "departmentDescription")
	public String departmentDescription;

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
	
	

}
