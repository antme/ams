package com.ams.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ams.bean.Notice;
import com.ams.bean.Reminder;
import com.ams.service.INoticeService;
import com.ams.util.PermissionConstants;
import com.eweblib.annotation.column.LoginRequired;
import com.eweblib.annotation.column.Permission;
import com.eweblib.exception.ResponseException;
import com.eweblib.util.EWeblibThreadLocal;
import com.eweblib.util.EweblibUtil;

@Controller
@RequestMapping("/ams/notice")
@Permission()
@LoginRequired()
public class NoticeController extends AmsController {

	@Autowired
	private INoticeService siteMessageService;
	
	@RequestMapping("/add.do")
	@Permission(groupName = PermissionConstants.ADM_SITE_MSG_MANAGE, permissionID = PermissionConstants.ADM_SITE_MSG_MANAGE)
	public void addNotice(HttpServletRequest request, HttpServletResponse response) {
//		SiteMessage message = (SiteMessage) parserJsonParameters(request, false, SiteMessage.class);
		Notice notice = (Notice)  parserJsonParameters(request, false, Notice.class);
		
		String relativeFilePath = genRandomRelativePath(EWeblibThreadLocal.getCurrentUserId());
		notice.setAttachFileUrl(uploadFile(request, relativeFilePath, "attachFileUrl", 0 , null));
		
		notice.setPublishDate(new Date());
		
		siteMessageService.addNotice(notice);
		responseWithData(null, request, response);
	}
	
	@RequestMapping("/list.do")
	public void listNotices(HttpServletRequest request, HttpServletResponse response) {
		responseWithDataPagnation(siteMessageService.listNotices(), request, response);
	}
	
	@RequestMapping("/app/list.do")
	public void listNoticesForApp(HttpServletRequest request, HttpServletResponse response) {
		Notice notice = (Notice)  parserJsonParameters(request, true, Notice.class);
		responseWithDataPagnation(siteMessageService.listNoticesForApp(notice), request, response);
	}
	
	
	@RequestMapping("/remind/add.do")
	public void addReminder(HttpServletRequest request, HttpServletResponse response) {
		Reminder reminder = (Reminder) parserJsonParameters(request, false, Reminder.class);

		if (EweblibUtil.isEmpty(reminder.getUserId())) {
			throw new ResponseException("请先登录");
		}

		responseWithEntity(siteMessageService.addReminder(reminder), request, response);
	}
	
	
	@RequestMapping("/remind/list.do")
	public void listUserReminder(HttpServletRequest request, HttpServletResponse response) {
		Reminder reminder = (Reminder)  parserJsonParameters(request, false, Reminder.class);
		
		if (EweblibUtil.isEmpty(reminder.getUserId())) {
			throw new ResponseException("请先登录");
		}
		responseWithDataPagnation(siteMessageService.listUserReminderForApp(reminder), request, response);
	}
	
	
	
	@RequestMapping("/remind/all/list.do")
	public void listAllUserReminders(HttpServletRequest request, HttpServletResponse response) {
	
		responseWithDataPagnation(siteMessageService.listAllUserReminders(), request, response);
	}


}
