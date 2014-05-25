package com.ams.service;

import com.ams.bean.Attendance;
import com.eweblib.bean.BaseEntity;
import com.eweblib.bean.EntityResults;

public interface IAttendanceService {

	EntityResults<Attendance> listAttendances(Attendance attendance);

}
