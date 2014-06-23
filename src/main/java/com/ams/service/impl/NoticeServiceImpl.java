package com.ams.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.ams.bean.Notice;
import com.ams.bean.Reminder;
import com.ams.bean.User;
import com.ams.bean.vo.SearchVo;
import com.ams.service.INoticeService;
import com.eweblib.bean.BaseEntity;
import com.eweblib.bean.EntityResults;
import com.eweblib.dbhelper.DataBaseQueryBuilder;
import com.eweblib.dbhelper.DataBaseQueryOpertion;
import com.eweblib.exception.ResponseException;
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
	public EntityResults<Notice> listNotices(SearchVo notice) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Notice.TABLE_NAME);
		builder.join(Notice.TABLE_NAME, User.TABLE_NAME, Notice.CREATOR_ID, User.ID);
		builder.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME + "," + Notice.PUBLISHER });
		builder.limitColumns(new String[] { Notice.PUBLISH_END_DATE, Notice.CREATED_ON, Notice.TITLE, Notice.PRIORITY, Notice.CONTENT, Notice.PUBLISH_DATE, Notice.ATTACH_FILE_URL, Notice.ID });
		
		mergeKeywordQuery(builder, notice.getKeyword(), Notice.TABLE_NAME, new String[] { Notice.TITLE, Notice.CONTENT });
		builder.orderBy(Notice.PRIORITY, false);
		builder.orderBy(Notice.CREATED_ON, false);

		return this.dao.listByQueryWithPagnation(builder, Notice.class);
	}

	@Override
	public EntityResults<Notice> listNoticesForApp(SearchVo notice) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Notice.TABLE_NAME);
		builder.join(Notice.TABLE_NAME, User.TABLE_NAME, Notice.CREATOR_ID, User.ID);
		builder.joinColumns(User.TABLE_NAME, new String[] { User.USER_NAME + "," + Notice.PUBLISHER });
		builder.limitColumns(new String[] { Notice.TITLE, Notice.CONTENT, Notice.PUBLISH_DATE, Notice.ATTACH_FILE_URL, Notice.ID });

		mergeKeywordQuery(builder, notice.getKeyword(), Notice.TABLE_NAME, new String[] { Notice.TITLE, Notice.CONTENT });

		DataBaseQueryBuilder dateQuery = new DataBaseQueryBuilder(Notice.TABLE_NAME);
		dateQuery.or(DataBaseQueryOpertion.LARGER_THAN, Notice.PUBLISH_END_DATE, new Date());
		dateQuery.or(DataBaseQueryOpertion.NULL, Notice.PUBLISH_END_DATE);

		builder.and(dateQuery);

		builder.orderBy(Notice.PRIORITY, false);
		builder.orderBy(Notice.CREATED_ON, false);

		return this.dao.listByQueryWithPagnation(builder, Notice.class);
	}

	public Reminder addReminder(Reminder reminder) {
		if (reminder.getUserId() == null) {
			throw new ResponseException("请先登录");
		}

		if (EweblibUtil.isEmpty(reminder.getId())) {
			this.dao.insert(reminder);
		} else {
			this.dao.updateById(reminder);
		}
		// 返回ID给客户端
		Reminder tmp = new Reminder();
		tmp.setId(reminder.getId());
		return tmp;
	}

	public EntityResults<Reminder> listUserReminderForApp(Reminder reminder) {

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Reminder.TABLE_NAME);
		if (reminder.getUserId() == null) {
			throw new ResponseException("请先登录");
		}

		builder.and(Reminder.USER_ID, reminder.getUserId());

		if (reminder.getIsQueryToday() != null && reminder.getIsQueryToday()) {
			Calendar c = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);

			builder.and(DataBaseQueryOpertion.GREATER_THAN_EQUALS, Reminder.REMIND_DATE, c.getTime());

			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);

			builder.and(DataBaseQueryOpertion.LESS_THAN_EQUAILS, Reminder.REMIND_DATE, c.getTime());

		}

		mergeKeywordQuery(builder, reminder.getKeyword(), Reminder.TABLE_NAME, new String[] { Reminder.TITLE, Reminder.CONTENT });

		builder.limitColumns(new String[] { Reminder.TITLE, Reminder.CONTENT, Reminder.REMIND_DATE, Reminder.ID });

		return this.dao.listByQueryWithPagnation(builder, Reminder.class);

	}
	
	
	public EntityResults<Reminder> listAllUserReminder(Reminder reminder){

		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Reminder.TABLE_NAME);
		builder.join(Reminder.TABLE_NAME, User.TABLE_NAME, Reminder.USER_ID, User.ID);
		builder.joinColumns(User.TABLE_NAME, new String[]{User.USER_NAME});

		mergeKeywordQuery(builder, reminder.getKeyword(), Reminder.TABLE_NAME, new String[] { Reminder.TITLE, Reminder.CONTENT });
		
		if(EweblibUtil.isValid(reminder.getUserId())){
			builder.and(Reminder.USER_ID, reminder.getUserId());
		}
		
		if(reminder.getStartDate()!=null){
			builder.and(DataBaseQueryOpertion.GREATER_THAN_EQUALS, Reminder.REMIND_DATE, reminder.getStartDate());
		}
		
		if(reminder.getEndDate()!=null){
			builder.and(DataBaseQueryOpertion.LESS_THAN_EQUAILS, Reminder.REMIND_DATE, reminder.getEndDate());
		}

		builder.limitColumns(new String[] { Reminder.TITLE, Reminder.CONTENT, Reminder.REMIND_DATE, Reminder.ID });

		return this.dao.listByQueryWithPagnation(builder, Reminder.class);

	
	}

	

	public BaseEntity getNoticeInfo(Notice notice) {

		return this.dao.findById(notice.getId(), Notice.TABLE_NAME, Notice.class);
	}

	public void deleteNoticeInfo(Notice notice) {

		this.dao.deleteById(notice);
	}
}
