package com.ams.bean;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = DeductedSalary.TABLE_NAME)
public class DeductedSalary extends BaseEntity {
	public static final String COMMENT = "comment";
	public static final String TOTOL_SALARY = "totolSalary";
	public static final String DISPAY_ORDER = "dispayOrder";
	public static final String SALARY_ID = "salaryId";
	public static final String TABLE_NAME = "DeductedSalary";

	@Column(name = SALARY_ID)
	public String salaryId;

	@Column(name = DISPAY_ORDER)
	public Integer dispayOrder;

	@Column(name = TOTOL_SALARY)
	public Double totolSalary;

	@Column(name = COMMENT)
	public String comment;

}
