package com.ams.service;

import com.ams.bean.Notice;
import com.eweblib.bean.BaseEntity;
import com.eweblib.bean.EntityResults;

public interface INoticeService {
	

	public void addNotice(Notice notice);

	public EntityResults<Notice> listNotices();

	public EntityResults<Notice> listNoticesForApp();
	
}
