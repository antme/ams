package com.ams.service;

import com.ams.bean.Notice;
import com.ams.bean.Reminder;
import com.ams.bean.vo.SearchVo;
import com.eweblib.bean.BaseEntity;
import com.eweblib.bean.EntityResults;

public interface INoticeService {
	

	public void addNotice(Notice notice);

	public EntityResults<Notice> listNotices(SearchVo notice);

	public EntityResults<Notice> listNoticesForApp(SearchVo notice);

	public Reminder addReminder(Reminder reminder);

	public EntityResults<Reminder> listUserReminderForApp(Reminder reminder);


	public BaseEntity getNoticeInfo(Notice notice);

	public void deleteNoticeInfo(Notice notice);

	public EntityResults<Reminder> listAllUserReminder(Reminder reminder);
	
}
