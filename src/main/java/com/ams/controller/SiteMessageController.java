package com.ams.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ams.bean.SiteMessageUser;
import com.ams.service.ISiteMessageService;
import com.ams.util.PermissionConstants;
import com.eweblib.annotation.column.LoginRequired;
import com.eweblib.annotation.column.Permission;
import com.eweblib.controller.AbstractController;

@Controller
@RequestMapping("/ecs/sitemessage")
@Permission()
@LoginRequired()
public class SiteMessageController extends AbstractController {

	@Autowired
	private ISiteMessageService siteMessageService;
	
//	@RequestMapping("/add.do")
//	@Permission(groupName = PermissionConstants.ADM_SITE_MSG_MANAGE, permissionID = PermissionConstants.ADM_SITE_MSG_MANAGE)
//	public void addSiteMessage(HttpServletRequest request, HttpServletResponse response) {
////		SiteMessage message = (SiteMessage) parserJsonParameters(request, false, SiteMessage.class);
//		AddSiteMessageVO vo = (AddSiteMessageVO)  parserJsonParameters(request, false, AddSiteMessageVO.class);
//		siteMessageService.addSiteMessage(vo);
//		responseWithData(null, request, response);
//	}
//	
//	@RequestMapping("/list.do")
//	public void listSiteMessages(HttpServletRequest request, HttpServletResponse response) {
//		responseWithDataPagnation(siteMessageService.listSiteMessage(), request, response);
//	}
//	
//	
//	
//	@RequestMapping("/detail.do")
//	public void loadSiteMessageDetail(HttpServletRequest request, HttpServletResponse response) {
//		SiteMessageUser smu = (SiteMessageUser) parserJsonParameters(request, true, SiteMessageUser.class);
//		responseWithEntity(siteMessageService.loadSiteMessageDetail(smu), request, response);
//	}
	


}
