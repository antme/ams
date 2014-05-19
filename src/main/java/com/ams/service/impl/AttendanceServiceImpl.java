package com.ams.service.impl;

import org.springframework.stereotype.Service;

import com.ams.bean.Attendance;
import com.ams.service.IAttendanceService;
import com.eweblib.bean.EntityResults;
import com.eweblib.dbhelper.DataBaseQueryBuilder;
import com.eweblib.service.AbstractService;

@Service(value = "attendanceService")
public class AttendanceServiceImpl extends AbstractService implements IAttendanceService {

	public EntityResults<Attendance> listAttendances() {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Attendance.TABLE_NAME);

		return this.dao.listByQueryWithPagnation(builder, Attendance.class);

	}
}
