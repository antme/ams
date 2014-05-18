package com.ams.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ams.bean.User;
import com.eweblib.bean.BaseEntity;
import com.eweblib.controller.AbstractController;

public class AmsController extends AbstractController {
	
	public void setLoginSessionInfo(HttpServletRequest request, HttpServletResponse response, BaseEntity userentity) {
		User user = (User) userentity;
		removeSessionInfo(request);

	    setSessionValue(request, User.USER_NAME, user.getUserName());
		setSessionValue(request, BaseEntity.ID, user.getId());
		
		
    }
}
