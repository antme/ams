package com.ams.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = SalaryDetail.TABLE_NAME)
public class SalaryDetail extends BaseEntity {

	public static final String COMMENT = "comment";
	public static final String REMAINING_SALARAY = "remainingSalaray";
	public static final String PERFORMANCE_SALARY = "performanceSalary";
	public static final String TOTOL_SALARY = "totolSalary";
	public static final String ATTENDANCE_DAYS = "attendanceDays";
	public static final String DISPAY_ORDER = "dispayOrder";
	public static final String SALARY_ID = "salaryId";
	public static final String TABLE_NAME = "SalaryDetail";
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

	@Column(name = COMMENT)
	public String comment;

	public String projectName;

}
