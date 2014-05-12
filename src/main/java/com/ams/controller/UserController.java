package com.ams.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ams.bean.Customer;
import com.ams.bean.Department;
import com.ams.bean.Pic;
import com.ams.bean.Team;
import com.ams.bean.vo.SearchVo;
import com.ams.service.IUserService;
import com.ams.util.PermissionConstants;
import com.eweblib.annotation.column.LoginRequired;
import com.eweblib.annotation.column.Permission;
import com.eweblib.bean.User;
import com.eweblib.controller.AbstractController;
import com.eweblib.util.EWeblibThreadLocal;

@Controller
@RequestMapping("/ams/user")
@Permission()
@LoginRequired()
public class UserController extends AbstractController {

	//_User used in AbstractController.removeSessionInfo
	public static final String IMG_CODE = "imgCode_User";
	public static final String REG_CODE = "regCode_User";
	private static final String FORGET_PWD_IMG_CODE = "pwdImgCode_User";
	private static final String FORGET_PWD_SMS_CODE = "pwdSmsCode_User";
	private static final String FORGET_PWD_MOBILE_PHONE = "pwdMobilePhone_User";

	@Autowired
	private IUserService userService;

	private static Logger logger = LogManager.getLogger(UserController.class);

	@RequestMapping("/login.do")
	@LoginRequired(required = false)
	public void login(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) parserJsonParameters(request, false, User.class);
		String imgCode = getSessionValue(request, IMG_CODE);
//		if (imgCode != null && user.getImgCode() != null && user.getImgCode().equalsIgnoreCase(imgCode)) {
			user = userService.login(user, false);
			setLoginSessionInfo(request, response, user);
			EWeblibThreadLocal.set(User.ID, user.getId());
//			try {
//	            response.sendRedirect("/index.jsp");
//            } catch (IOException e) {
//            	
//            }
			responseWithEntity(user, request, response);
//		} else {
//			throw new ResponseException("请输入正确验证码");
//		}

	}
	
	
	@RequestMapping("/department/add.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void addDepartment(HttpServletRequest request, HttpServletResponse response) {
		Department dep = (Department) parserJsonParameters(request, false, Department.class);
		userService.addDepartment(dep);
		responseWithData(null, request, response);
	}
	
	@RequestMapping("/department/list.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void listDepartments(HttpServletRequest request, HttpServletResponse response) {
		SearchVo vo = (SearchVo) parserJsonParameters(request, false, SearchVo.class);
		responseWithDataPagnation(userService.listDepartments(vo), request, response);
	}
	

	@RequestMapping("/team/add.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void addTeam(HttpServletRequest request, HttpServletResponse response) {
		Team dep = (Team) parserJsonParameters(request, false, Team.class);
		userService.addTeam(dep);
		responseWithData(null, request, response);
	}
	
	@RequestMapping("/team/list.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void listTeams(HttpServletRequest request, HttpServletResponse response) {
		SearchVo vo = (SearchVo) parserJsonParameters(request, false, SearchVo.class);
		responseWithDataPagnation(userService.listTeams(vo), request, response);
	}
	
	
	@RequestMapping("/customer/add.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void addCustomer(HttpServletRequest request, HttpServletResponse response) {
		Customer customer = (Customer) parserJsonParameters(request, false, Customer.class);
		userService.addCustomer(customer);
		responseWithData(null, request, response);
	}
	
	@RequestMapping("/customer/app/list.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void listCustomersForApp(HttpServletRequest request, HttpServletResponse response) {
		SearchVo vo = (SearchVo) parserJsonParameters(request, false, SearchVo.class);
		responseWithDataPagnation(userService.listCustomersForApp(vo), request, response);
	}
	
	
	@RequestMapping("/app/list.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void listUserForApp(HttpServletRequest request, HttpServletResponse response) {
		SearchVo vo = (SearchVo) parserJsonParameters(request, false, SearchVo.class);
		responseWithDataPagnation(userService.listUserForApp(vo), request, response);
	}
	
	
	@RequestMapping("/pic/app/add.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void addPic(HttpServletRequest request, HttpServletResponse response) {
		Pic pic = (Pic) parserJsonParameters(request, false, Pic.class);
		String relativeFilePath = genRandomRelativePath(EWeblibThreadLocal.getCurrentUserId());
		System.out.println(uploadFile(request, relativeFilePath, "picData0"));
//		userService.addCustomer(customer);
		responseWithData(null, request, response);
	}
	
	
	
//
//	@RequestMapping("/logout.do")
//	@LoginRequired(required = false)
//	public void logout(HttpServletRequest request, HttpServletResponse response) {
//		clearLoginSession(request, response);
//		responseWithData(null, request, response);
//	}
//
//	@RequestMapping("/reg/getCode.do")
//	@LoginRequired(required = false)
//	public void getMobileValidCode(HttpServletRequest request, HttpServletResponse response) {
//		User user = (User) parserJsonParameters(request, false, User.class);
//
//		String imgCode = getSessionValue(request, IMG_CODE);
//		if (imgCode != null && user.getImgCode() != null && user.getImgCode().equalsIgnoreCase(imgCode)) {
//			String regCode = ImgUtil.getRandomWord(4);
//			setSessionValue(request, REG_CODE, regCode);
//			smsService.sendRegCode(user.getMobileNumber(), regCode);
//			responseWithData(null, request, response);
//		} else {
//			throw new ResponseException("请输入正确验证码");
//		}
//	}
//
//	@RequestMapping("/reg.do")
//	@LoginRequired(required = false)
//	public void registerUser(HttpServletRequest request, HttpServletResponse response) {
//		User user = (User) parserJsonParameters(request, false, User.class);
//		
//		String imgCode = getSessionValue(request, IMG_CODE);
//		if (imgCode != null && user.getImgCode() != null && user.getImgCode().equalsIgnoreCase(imgCode)) {
//
//			userService.checkUserName(user.getUserName());
//			userService.checkUserMobile(user.getMobileNumber());
//			user = userService.regUser(user);
//
//			setLoginSessionInfo(request, response, user);
//			responseWithData(null, request, response);
//		} else {
//			throw new ResponseException("请输入正确验证码");
//		}
//
//
//	}
//
//	@RequestMapping("/img.do")
//	@LoginRequired(required = false)
//	public void loadLoginImg(HttpServletRequest request, HttpServletResponse response) {
//		response.setContentType("image/png");
//		String word = ImgUtil.getRandomWord(4);
//		setSessionValue(request, IMG_CODE, word);
//		BufferedImage image = ImgUtil.getCaptchaImage(word, 93, 35);
//
//		try {
//			ImageIO.write(image, "png", response.getOutputStream());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	@RequestMapping("/forgot/pwd/img.do")
//	@LoginRequired(required = false)
//	public void loadForgetPwdImg(HttpServletRequest request, HttpServletResponse response) {
//		response.setContentType("image/png");
//		String word = ImgUtil.getRandomWord(4);
//		setSessionValue(request, FORGET_PWD_IMG_CODE, word);
//		BufferedImage image = ImgUtil.getCaptchaImage(word, 93, 35);
//		try {
//			ImageIO.write(image, "png", response.getOutputStream());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	@RequestMapping("/forgot/pwd/getCode.do")
//	@LoginRequired(required = false)
//	public void getPwdCode(HttpServletRequest request, HttpServletResponse response) {
//		User user = (User) parserJsonParameters(request, false, User.class);
//		if (EcUtil.isEmpty(user.getImgCode())) {
//			throw new ResponseException("请输图片验证码");
//		}
//		if (getSessionValue(request, FORGET_PWD_IMG_CODE) == null || !(getSessionValue(request, FORGET_PWD_IMG_CODE).equalsIgnoreCase(user.getImgCode()))) {
//			throw new ResponseException("验证码不正确");
//		}
//
//		String word = ImgUtil.getRandomWord(4);
//		setSessionValue(request, FORGET_PWD_SMS_CODE, word);
//		setSessionValue(request, FORGET_PWD_MOBILE_PHONE, user.getMobileNumber());
//
//		userService.getForgotPwdSmsCode(user, word);
//
//		responseWithDataPagnation(null, request, response);
//	}
//
//	@RequestMapping("/forgot/pwd/reset.do")
//	@LoginRequired(required = false)
//	public void resetPwdByMobile(HttpServletRequest request, HttpServletResponse response) {
//		User user = (User) parserJsonParameters(request, false, User.class);
//
//		String pwdCode = getSessionValue(request, FORGET_PWD_SMS_CODE);
//
//		if (EcUtil.isEmpty(pwdCode)) {
//			throw new ResponseException("请输入手机验证码");
//		}
//
//		if (EcUtil.isEmpty(user.getPwdCode())) {
//			throw new ResponseException("请输入手机验证码");
//		}
//
//		if (!pwdCode.equalsIgnoreCase(user.getPwdCode())) {
//			throw new ResponseException("验证码不对");
//		}
//		user.setMobileNumber(getSessionValue(request, FORGET_PWD_MOBILE_PHONE));
//		userService.resetPwdByMobile(user);
//
//		removeSessionInfo(request);
//		responseWithDataPagnation(null, request, response);
//	}
//
//	@RequestMapping("/pwd/reset.do")
//	@LoginRequired(required = false)
//	public void resetPwd(HttpServletRequest request, HttpServletResponse response) {
//		User user = (User) parserJsonParameters(request, false, User.class);
//		user.setId(EcThreadLocal.getCurrentUserId());
//		userService.resetPwd(user);
//
//		responseWithDataPagnation(null, request, response);
//	}
//
//	@RequestMapping("/manage.do")
//	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
//	public void listForAdmin(HttpServletRequest request, HttpServletResponse response) {
//		SearchVo vo = (SearchVo) parserJsonParameters(request, false, SearchVo.class);
//		responseWithDataPagnation(userService.listForAdmin(vo), request, response);
//	}
//
//	@RequestMapping("/info.do")
//	public void loadUserInfo(HttpServletRequest request, HttpServletResponse response) {
//		User user = (User) parserJsonParameters(request, true, User.class);
//
//		if (EcUtil.isEmpty(user.getId())) {
//			user.setId(EcThreadLocal.getCurrentUserId());
//		}
//		responseWithEntity(userService.loadUserInfo(user), request, response);
//	}
//
//	@RequestMapping("/update.do")
//	public void updateUserInfo(HttpServletRequest request, HttpServletResponse response) {
//		User user = (User) parserJsonParameters(request, true, User.class);
//
//		userService.updateUser(user);
//		responseWithEntity(null, request, response);
//	}
//
//	@RequestMapping("/adminadd.do")
//	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
//	public void adminAddUserForCustomer(HttpServletRequest request, HttpServletResponse response) {
//		User user = (User) parserJsonParameters(request, true, User.class);
//
//		userService.adminAddUserAsUserRole(user);
//		responseWithEntity(null, request, response);
//	}
//
//	@RequestMapping("/lock.do")
//	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
//	public void lockUser(HttpServletRequest request, HttpServletResponse response) {
//		BaseEntity be = (BaseEntity) parserJsonParameters(request, false, BaseEntity.class);
//		userService.lockUserById(be);
//		responseWithData(null, request, response);
//	}
//
//	@RequestMapping("/unlock.do")
//	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
//	public void unlockUser(HttpServletRequest request, HttpServletResponse response) {
//		BaseEntity be = (BaseEntity) parserJsonParameters(request, false, BaseEntity.class);
//		userService.unlockUserById(be);
//		responseWithData(null, request, response);
//	}
//	
//
//	@RequestMapping("/access.do")
//	public void listUserAccessMenuIds(HttpServletRequest request, HttpServletResponse response) {		
//		Map<String, Object> result = new HashMap<String, Object>();
//		result.put("data", userService.listUserAccessMenuIds());
//		responseWithData(result, request, response);
//	}
//	
//
//	//FIXME 临时解决方案
//	@RequestMapping("/todolist.do")
//	public void logincheck(HttpServletRequest request, HttpServletResponse response) {
//		responseWithData(userService.getTodoListInfo(), request, response);
//	}
//	

}
