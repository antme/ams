package com.ams.bean;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = DeductedSalaryItem.TABLE_NAME)
public class DeductedSalaryItem extends BaseEntity {
	public static final String NAME = "name";
	public static final String COMMENT = "comment";
	public static final String TOTOL_SALARY = "totolSalary";
	public static final String DISPAY_ORDER = "dispayOrder";
	public static final String SALARY_ID = "salaryId";
	public static final String TABLE_NAME = "DeductedSalaryItem";

	@Column(name = SALARY_ID)
	public String salaryId;

	@Column(name = DISPAY_ORDER)
	public Integer dispayOrder;

	@Column(name = TOTOL_SALARY)
	public Double totolSalary;

	@Column(name = COMMENT)
	public String comment;

	@Column(name = NAME)
	public String name;

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

	public Double getTotolSalary() {
		return totolSalary;
	}

	public void setTotolSalary(Double totolSalary) {
		this.totolSalary = totolSalary;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public static void main(String args[]){
		DeductedSalaryItem ds = new DeductedSalaryItem();
		ds.setComment("迟到扣工资");
		ds.setName("迟到1天");
		ds.setTotolSalary(120.5);
		ds.setId(UUID.randomUUID().toString());
		System.out.println(ds.toString());
		
	}

}
