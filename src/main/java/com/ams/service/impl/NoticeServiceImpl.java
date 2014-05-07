package com.ams.service.impl;

import org.springframework.stereotype.Service;

import com.ams.bean.Notice;
import com.ams.service.INoticeService;
import com.eweblib.bean.BaseEntity;
import com.eweblib.bean.EntityResults;
import com.eweblib.dbhelper.DataBaseQueryBuilder;
import com.eweblib.service.AbstractService;
import com.eweblib.util.EweblibUtil;

@Service(value = "noticeService")
public class NoticeServiceImpl extends AbstractService implements INoticeService {

	@Override
	public void addNotice(Notice notice) {

		if (EweblibUtil.isEmpty(notice.getId())) {
			this.dao.insert(notice);
		} else {
			this.dao.updateById(notice);
		}

	}

	@Override
	public EntityResults<Notice> listNotices() {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Notice.TABLE_NAME);
		return this.dao.listByQueryWithPagnation(builder, Notice.class);
	}

	@Override
	public EntityResults<Notice> listNoticesForApp() {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Notice.TABLE_NAME);
		builder.limitColumns(new String[] { Notice.TITLE, Notice.CONTENT, Notice.PUBLISHER, Notice.PUBLISH_DATE });
		return this.dao.listByQueryWithPagnation(builder, Notice.class);
	}

}
