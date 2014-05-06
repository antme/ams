package com.ams.bean;

import java.util.Date;

import com.eweblib.bean.BaseEntity;

public class Task extends BaseEntity {

	public String projectName;

	public Date projectStartDate;

	public Date projectEndDate;

	public String teamName;

	public String memebers;

	public String unit;

	public Double amount;

	public Double price;

	public String priceDescription;

	public String amountDescription;

	public String description;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getProjectStartDate() {
		return projectStartDate;
	}

	public void setProjectStartDate(Date projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	public Date getProjectEndDate() {
		return projectEndDate;
	}

	public void setProjectEndDate(Date projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getMemebers() {
		return memebers;
	}

	public void setMemebers(String memebers) {
		this.memebers = memebers;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getAmountDescription() {
		return amountDescription;
	}

	public void setAmountDescription(String amountDescription) {
		this.amountDescription = amountDescription;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPriceDescription() {
		return priceDescription;
	}

	public void setPriceDescription(String priceDescription) {
		this.priceDescription = priceDescription;
	}

	public static void main(String args[]) {
		Task task = new Task();
		task.setAmountDescription("4000㎡");
		task.setPriceDescription("1.2元");
		task.setProjectName("");
		task.setProjectStartDate(new Date());
		task.setProjectEndDate(new Date());
		task.setDescription("");
		task.setTeamName("");
		task.setMemebers("");
		
		System.out.println(task);

	}

}
