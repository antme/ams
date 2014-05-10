package com.ams.schedule;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ams.service.IECommerceUserService;
import com.eweblib.dao.IQueryDao;

public class SystemSchedule {

	private static Logger logger = LogManager.getLogger(SystemSchedule.class);

	@Autowired
	public IQueryDao dao;

	@Autowired
	public IECommerceUserService us;

	public void run() {

	}
	

}
