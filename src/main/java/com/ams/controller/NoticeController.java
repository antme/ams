package com.ams.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ams.bean.Notice;
import com.ams.bean.SiteMessageUser;
import com.ams.service.INoticeService;
import com.ams.util.PermissionConstants;
import com.eweblib.annotation.column.LoginRequired;
import com.eweblib.annotation.column.Permission;
import com.eweblib.controller.AbstractController;

@Controller
@RequestMapping("/ams/notice")
@Permission()
@LoginRequired()
public class NoticeController extends AbstractController {

	@Autowired
	private INoticeService siteMessageService;
	
	@RequestMapping("/add.do")
	@Permission(groupName = PermissionConstants.ADM_SITE_MSG_MANAGE, permissionID = PermissionConstants.ADM_SITE_MSG_MANAGE)
	public void addNotice(HttpServletRequest request, HttpServletResponse response) {
//		SiteMessage message = (SiteMessage) parserJsonParameters(request, false, SiteMessage.class);
		Notice notice = (Notice)  parserJsonParameters(request, false, Notice.class);
		siteMessageService.addNotice(notice);
		responseWithData(null, request, response);
	}
	
	@RequestMapping("/list.do")
	public void listNotices(HttpServletRequest request, HttpServletResponse response) {
		responseWithDataPagnation(siteMessageService.listNotices(), request, response);
	}
	
	@RequestMapping("/app/list.do")
	public void listNoticesForApp(HttpServletRequest request, HttpServletResponse response) {
		parserJsonParameters(request, true);
		responseWithDataPagnation(siteMessageService.listNoticesForApp(), request, response);
	}
//	
//	
//	
//	@RequestMapping("/detail.do")
//	public void loadSiteMessageDetail(HttpServletRequest request, HttpServletResponse response) {
//		SiteMessageUser smu = (SiteMessageUser) parserJsonParameters(request, true, SiteMessageUser.class);
//		responseWithEntity(siteMessageService.loadSiteMessageDetail(smu), request, response);
//	}
	


}
