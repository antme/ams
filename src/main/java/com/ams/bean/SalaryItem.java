package com.ams.bean;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = SalaryItem.TABLE_NAME)
public class SalaryItem extends BaseEntity {

	public static final String PERFORMANCE_SALARY_UNIT = "performanceSalaryUnit";
	public static final String COMMENT = "comment";
	public static final String REMAINING_SALARAY = "remainingSalaray";
	public static final String PERFORMANCE_SALARY = "performanceSalary";
	public static final String TOTOL_SALARY = "totolSalary";
	public static final String ATTENDANCE_DAYS = "attendanceDays";
	public static final String DISPAY_ORDER = "dispayOrder";
	public static final String SALARY_ID = "salaryId";
	public static final String TABLE_NAME = "SalaryItem";
	public static final String PROJECT_ID = "projectId";

	@Column(name = SALARY_ID)
	public String salaryId;

	@Column(name = DISPAY_ORDER)
	public Integer dispayOrder;

	@Column(name = PROJECT_ID)
	public String projectId;

	@Column(name = ATTENDANCE_DAYS)
	public Double attendanceDays;

	@Column(name = TOTOL_SALARY)
	public Double totolSalary;

	@Column(name = PERFORMANCE_SALARY)
	public Double performanceSalary;
	
	@Column(name = PERFORMANCE_SALARY_UNIT)
	public Double performanceSalaryUnit;

	@Column(name = COMMENT)
	public String comment;
	
	
	@Column(name = "year")
	public Integer year;
	
	@Column(name = "month")
	public Integer month;

	public String projectName;
	
	
	
	public String getSalaryId() {
		return salaryId;
	}



	public void setSalaryId(String salaryId) {
		this.salaryId = salaryId;
	}



	public Integer getDispayOrder() {
		return dispayOrder;
	}



	public void setDispayOrder(Integer dispayOrder) {
		this.dispayOrder = dispayOrder;
	}



	public String getProjectId() {
		return projectId;
	}



	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}



	public Double getAttendanceDays() {
		return attendanceDays;
	}



	public void setAttendanceDays(Double attendanceDays) {
		this.attendanceDays = attendanceDays;
	}



	public Double getTotolSalary() {
		return totolSalary;
	}



	public void setTotolSalary(Double totolSalary) {
		this.totolSalary = totolSalary;
	}



	public Double getPerformanceSalary() {
		return performanceSalary;
	}



	public void setPerformanceSalary(Double performanceSalary) {
		this.performanceSalary = performanceSalary;
	}



	public String getComment() {
		return comment;
	}



	public void setComment(String comment) {
		this.comment = comment;
	}



	public String getProjectName() {
		return projectName;
	}



	public void setProjectName(String projectName) {
		this.projectName = projectName;
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



	public Double getPerformanceSalaryUnit() {
		return performanceSalaryUnit;
	}



	public void setPerformanceSalaryUnit(Double performanceSalaryUnit) {
		this.performanceSalaryUnit = performanceSalaryUnit;
	}



	public static void main(String args[]){
		SalaryItem sd = new SalaryItem();
		
		sd.setId(UUID.randomUUID().toString());
		sd.setAttendanceDays(12.5);
		sd.setComment("上班12天半");
		sd.setTotolSalary(2050.50);
		sd.setPerformanceSalary(100d);
		sd.setProjectName("佘山改造一期");
		sd.setYear(2014);
		sd.setMonth(05);
		System.out.println(sd.toString());
	}

}
