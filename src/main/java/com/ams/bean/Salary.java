package com.ams.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = Salary.TABLE_NAME)
public class Salary extends BaseEntity {

	public static final String REMAINING_SALARAY = "remainingSalaray";

	public static final String DEDUCTED_SALARY = "deductedSalary";

	public static final String TOTAL_SALARY = "totalSalary";

	public static final String MONTH = "month";

	public static final String YEAR = "year";

	public static final String EMPLOYEE_NAME = "employeeName";

	public static final String EMPLOYEE_ID = "employeeId";

	public static final String TABLE_NAME = "Salary";

	@Column(name = EMPLOYEE_ID)
	public String employeeId;

	@Column(name = EMPLOYEE_NAME)
	public String employeeName;

	@Column(name = TOTAL_SALARY)
	public Double totalSalary;

	@Column(name = DEDUCTED_SALARY)
	public Double deductedSalary;

	@Column(name = REMAINING_SALARAY)
	public Double remainingSalaray;

	@Column(name = YEAR)
	public Date year;

	@Column(name = MONTH)
	public Integer month;

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}


	public Date getYear() {
		return year;
	}

	public void setYear(Date year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Double getTotalSalary() {
		return totalSalary;
	}

	public void setTotalSalary(Double totalSalary) {
		this.totalSalary = totalSalary;
	}

	public Double getDeductedSalary() {
		return deductedSalary;
	}

	public void setDeductedSalary(Double deductedSalary) {
		this.deductedSalary = deductedSalary;
	}

	public Double getRemainingSalaray() {
		return remainingSalaray;
	}

	public void setRemainingSalaray(Double remainingSalaray) {
		this.remainingSalaray = remainingSalaray;
	}
	
	

}
