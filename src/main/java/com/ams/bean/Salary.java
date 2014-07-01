package com.ams.bean;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = Salary.TABLE_NAME)
public class Salary extends BaseEntity {

	public static final String SALARY_PER_DAY = "salaryPerDay";

	public static final String SALARY_FILE_NAME = "salaryFileName";

	public static final String USER_ID = "userId";

	public static final String REMAINING_SALARAY = "remainingSalaray";

	public static final String DEDUCTED_SALARY = "deductedSalary";

	public static final String TOTAL_SALARY = "totalSalary";

	public static final String MONTH = "month";

	public static final String YEAR = "year";

	public static final String TABLE_NAME = "Salary";

	@Column(name = USER_ID)
	public String userId;

	@Column(name = TOTAL_SALARY)
	public Double totalSalary;

	@Column(name = DEDUCTED_SALARY)
	public Double deductedSalary;

	@Column(name = REMAINING_SALARAY)
	public Double remainingSalaray;

	@Column(name = YEAR)
	public Integer year;

	@Column(name = MONTH)
	public Integer month;
	
	
	
	@Column(name = SALARY_FILE_NAME)
	public String salaryFileName;
	
	@Column(name = SALARY_PER_DAY)
	public Double salaryPerDay;

	public String userName;

	

	public List<SalaryItem> salaryItems;

	public List<DeductedSalaryItem> deductedSalaryItems;
	
	public String overrideexists;
	
	

	public String getSalaryFileName() {
		return salaryFileName;
	}

	public void setSalaryFileName(String salaryFileName) {
		this.salaryFileName = salaryFileName;
	}

	public String getOverrideexists() {
		return overrideexists;
	}

	public void setOverrideexists(String overrideexists) {
		this.overrideexists = overrideexists;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
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

	public List<SalaryItem> getSalaryItems() {
		return salaryItems;
	}

	public void setSalaryItems(List<SalaryItem> salaryItems) {
		this.salaryItems = salaryItems;
	}

	public List<DeductedSalaryItem> getDeductedSalaryItems() {
		return deductedSalaryItems;
	}

	public void setDeductedSalaryItems(List<DeductedSalaryItem> deductedSalaryItems) {
		this.deductedSalaryItems = deductedSalaryItems;
	}

	public Double getSalaryPerDay() {
		return salaryPerDay;
	}

	public void setSalaryPerDay(Double salaryPerDay) {
		this.salaryPerDay = salaryPerDay;
	}

	public static void main(String args[]) {
		Salary salary = new Salary();
		salary.setDeductedSalary(1000.5);
		salary.setTotalSalary(5800d);
		salary.setRemainingSalaray(5800 - 1000.5);
		salary.setYear(2014);
		salary.setMonth(05);
		salary.setUserName("张三");
		salary.setId(UUID.randomUUID().toString());
		System.out.println(salary.toString());
	}

}
