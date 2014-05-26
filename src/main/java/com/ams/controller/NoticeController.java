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
	private INoticeService noticeService;

	@RequestMapping("/add.do")
	@Permission(groupName = PermissionConstants.ADM_SITE_MSG_MANAGE, permissionID = PermissionConstants.ADM_SITE_MSG_MANAGE)
	public void addNotice(HttpServletRequest request, HttpServletResponse response) {
		Notice notice = (Notice) parserJsonParameters(request, false, Notice.class);

		
		System.out.println(notice);
		String relativeFilePath = genRandomRelativePath(EWeblibThreadLocal.getCurrentUserId());

		if (notice.getDeleteAttachFile() != null) {
			//TODO: delete file 
			notice.setAttachFileUrl("");
		}

		String upFile = uploadFile(request, relativeFilePath, "attachFileUpload", 0, null);

		if (upFile != null) {
			notice.setAttachFileUrl(uploadFile(request, relativeFilePath, "attachFileUpload", 0, null));
		}

		notice.setPublishDate(new Date());

		noticeService.addNotice(notice);
		responseWithData(null, request, response);
	}

	@RequestMapping("/list.do")
	public void listNotices(HttpServletRequest request, HttpServletResponse response) {
		Notice notice = (Notice) parserJsonParameters(request, true, Notice.class);
		responseWithDataPagnation(noticeService.listNotices(notice), request, response);
	}

	@RequestMapping("/app/list.do")
	public void listNoticesForApp(HttpServletRequest request, HttpServletResponse response) {
		Notice notice = (Notice) parserJsonParameters(request, true, Notice.class);

		if (EweblibUtil.isEmpty(notice.getUserId())) {
			throw new ResponseException("请先登录");
		}

		responseWithDataPagnation(noticeService.listNoticesForApp(notice), request, response);
	}

	@RequestMapping("/get.do")
	public void getNoticeInfo(HttpServletRequest request, HttpServletResponse response) {
		Notice notice = (Notice) parserJsonParameters(request, true, Notice.class);
		responseWithEntity(noticeService.getNoticeInfo(notice), request, response);
	}
	
	@RequestMapping("/delete.do")
	public void deleteNoticeInfo(HttpServletRequest request, HttpServletResponse response) {
		Notice notice = (Notice) parserJsonParameters(request, true, Notice.class);
		noticeService.deleteNoticeInfo(notice);
		responseWithEntity(null, request, response);
	}

	@RequestMapping("/remind/add.do")
	public void addReminder(HttpServletRequest request, HttpServletResponse response) {
		Reminder reminder = (Reminder) parserJsonParameters(request, false, Reminder.class);

		if (EweblibUtil.isEmpty(reminder.getUserId())) {
			throw new ResponseException("请先登录");
		}

		responseWithEntity(noticeService.addReminder(reminder), request, response);
	}

	@RequestMapping("/remind/list.do")
	public void listUserReminder(HttpServletRequest request, HttpServletResponse response) {
		Reminder reminder = (Reminder) parserJsonParameters(request, false, Reminder.class);

		if (EweblibUtil.isEmpty(reminder.getUserId())) {
			throw new ResponseException("请先登录");
		}
		responseWithDataPagnation(noticeService.listUserReminderForApp(reminder), request, response);
	}

	@RequestMapping("/remind/all/list.do")
	public void listAllUserReminders(HttpServletRequest request, HttpServletResponse response) {

		responseWithDataPagnation(noticeService.listAllUserReminders(), request, response);
	}

}
