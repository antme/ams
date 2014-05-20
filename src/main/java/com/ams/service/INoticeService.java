package com.ams.service;

import com.ams.bean.Notice;
import com.ams.bean.Reminder;
import com.eweblib.bean.BaseEntity;
import com.eweblib.bean.EntityResults;

public interface INoticeService {
	

	public void addNotice(Notice notice);

	public EntityResults<Notice> listNotices();

	public EntityResults<Notice> listNoticesForApp(Notice notice);

	public Reminder addReminder(Reminder reminder);

	public EntityResults<Reminder> listUserReminderForApp(Reminder reminder);

	public EntityResults<Reminder> listAllUserReminders();

	public BaseEntity getNoticeInfo(Notice notice);
	
}
