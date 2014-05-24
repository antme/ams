package com.ams.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ams.bean.Attendance;
import com.ams.bean.Customer;
import com.ams.bean.Department;
import com.ams.bean.EmployeeTeam;
import com.ams.bean.Pic;
import com.ams.bean.Salary;
import com.ams.bean.Team;
import com.ams.bean.User;
import com.ams.bean.vo.SearchVo;
import com.ams.service.IProjectService;
import com.ams.service.IUserService;
import com.ams.util.PermissionConstants;
import com.eweblib.annotation.column.LoginRequired;
import com.eweblib.annotation.column.Permission;
import com.eweblib.exception.ResponseException;
import com.eweblib.util.EWeblibThreadLocal;
import com.eweblib.util.EweblibUtil;
import com.eweblib.util.ImgUtil;

@Controller
@RequestMapping("/ams/user")
@Permission()
@LoginRequired()
public class UserController extends AmsController {

	//_User used in AbstractController.removeSessionInfo
	public static final String IMG_CODE = "imgCode_User";
	public static final String REG_CODE = "regCode_User";

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IProjectService projectService;

	private static Logger logger = LogManager.getLogger(UserController.class);

	@RequestMapping("/login.do")
	@LoginRequired(required = false)
	public void login(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) parserJsonParameters(request, false, User.class);
		user = userService.login(user, false);
		responseWithEntity(user, request, response);

	}
	
	@RequestMapping("/web/login.do")
	@LoginRequired(required = false)
	public void loginForWeb(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) parserJsonParameters(request, false, User.class);
		
		String imgCode = getSessionValue(request, IMG_CODE);
		if (imgCode != null && user.getImgCode() != null && user.getImgCode().equalsIgnoreCase(imgCode)) {
			user = userService.login(user, false);
			setLoginSessionInfo(request, response, user);
			EWeblibThreadLocal.set(User.ID, user.getId());

			responseWithEntity(user, request, response);
		} else {
			throw new ResponseException("请输入正确验证码");
		}

	}
	
	@RequestMapping("/img.do")
	@LoginRequired(required = false)
	public void loadLoginImg(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("image/png");
		String word = ImgUtil.getRandomWord(4);
		setSessionValue(request, IMG_CODE, word);
		BufferedImage image = ImgUtil.getCaptchaImage(word, 93, 35);

		try {
			ImageIO.write(image, "png", response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping("/add.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void addUser(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) parserJsonParameters(request, false, User.class);
		userService.regUser(user);
		responseWithData(null, request, response);
	}
	
	@RequestMapping("/load.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void loadUser(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) parserJsonParameters(request, false, User.class);	
		responseWithEntity(userService.loadUser(user), request, response);
	}
	
	@RequestMapping("/department/add.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void addDepartment(HttpServletRequest request, HttpServletResponse response) {
		Department dep = (Department) parserJsonParameters(request, false, Department.class);
		userService.addDepartment(dep);
		responseWithData(null, request, response);
	}
	
	@RequestMapping("/department/load.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void loadDepartment(HttpServletRequest request, HttpServletResponse response) {
		Department dep = (Department) parserJsonParameters(request, false, Department.class);

		responseWithEntity(userService.loadDepartment(dep), request, response);
	}
	
	@RequestMapping("/department/list.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void listDepartments(HttpServletRequest request, HttpServletResponse response) {
		SearchVo vo = (SearchVo) parserJsonParameters(request, true, SearchVo.class);
		responseWithDataPagnation(userService.listDepartments(vo), request, response);
	}
	
	@RequestMapping("/department/app/list.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void listDepartmentsForApp(HttpServletRequest request, HttpServletResponse response) {
		SearchVo vo = (SearchVo) parserJsonParameters(request, false, SearchVo.class);
		responseWithDataPagnation(userService.listDepartmentsForApp(vo), request, response);
	}
	

	@RequestMapping("/team/add.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void addTeam(HttpServletRequest request, HttpServletResponse response) {
		Team dep = (Team) parserJsonParameters(request, false, Team.class);
		projectService.addTeam(dep);
		responseWithData(null, request, response);
	}
	
	

	@RequestMapping("/team/get.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void getTeam(HttpServletRequest request, HttpServletResponse response) {
		Team dep = (Team) parserJsonParameters(request, false, Team.class);
		
		responseWithEntity(projectService.getTeam(dep), request, response);
	}
	
	
	@RequestMapping("/team/list.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void listTeams(HttpServletRequest request, HttpServletResponse response) {
		SearchVo vo = (SearchVo) parserJsonParameters(request, false, SearchVo.class);
		responseWithDataPagnation(projectService.listTeams(vo), request, response);
	}
	
	
	@RequestMapping("/team/app/list.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void listTeamsForApp(HttpServletRequest request, HttpServletResponse response) {
		SearchVo vo = (SearchVo) parserJsonParameters(request, false, SearchVo.class);
		Team team  = (Team) parserJsonParameters(request, false, Team.class);
		
		if(EweblibUtil.isEmpty(team.getDepartmentId())){
			throw new ResponseException("请先选择部门");
		}
		responseWithListData(projectService.listTeamsForApp(team), request, response);
	}
	
	
	@RequestMapping("/team/memebers/app/list.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void listTeamMemebersForApp(HttpServletRequest request, HttpServletResponse response) {
		EmployeeTeam team  = (EmployeeTeam) parserJsonParameters(request, false, EmployeeTeam.class);
		
		if(EweblibUtil.isEmpty(team.getTeamId())){
			throw new ResponseException("请先选择团队");
		}
		responseWithListData(projectService.listTeamMemebersForApp(team), request, response);
	}
	
	
	@RequestMapping("/attendance/app/add.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void addAttendance(HttpServletRequest request, HttpServletResponse response) {
		List<Attendance> attendanceList =  parserListJsonParameters(request,  false, Attendance.class);
		userService.addAttendance(attendanceList);
		responseWithData(null, request, response);
	}
	
	
	@RequestMapping("/customer/add.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void addCustomer(HttpServletRequest request, HttpServletResponse response) {
		Customer customer = (Customer) parserJsonParameters(request, false, Customer.class);
		projectService.addCustomer(customer);
		responseWithData(null, request, response);
	}
	
	@RequestMapping("/customer/app/list.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void listCustomersForApp(HttpServletRequest request, HttpServletResponse response) {
		SearchVo vo = (SearchVo) parserJsonParameters(request, false, SearchVo.class);
		
		if(vo.getUserId() == null){
			throw new ResponseException("请先登录");
		}
		responseWithDataPagnation(projectService.listCustomersForApp(vo), request, response);
	}
	
	
	@RequestMapping("/app/list.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void listUserForApp(HttpServletRequest request, HttpServletResponse response) {
		SearchVo vo = (SearchVo) parserJsonParameters(request, false, SearchVo.class);

		if (EweblibUtil.isEmpty(vo.getUserId())) {
			throw new ResponseException("请先登录");
		}
		responseWithDataPagnation(userService.listUserForApp(vo), request, response);
	}
	
	
	
	@RequestMapping("/list.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void listAllUsers(HttpServletRequest request, HttpServletResponse response) {
		SearchVo vo = (SearchVo) parserJsonParameters(request, false, SearchVo.class);
		responseWithDataPagnation(userService.listAllUsers(vo), request, response);
	}
	
	@RequestMapping("/pic/app/add.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void addPic(HttpServletRequest request, HttpServletResponse response) {
		Pic pic = (Pic) parserJsonParameters(request, false, Pic.class);

		Integer images = pic.getImagesCount();
		if (images == null || images < 1) {
			throw new ResponseException("请上传图片");
		}
		String relativeFilePath = genRandomRelativePath(EWeblibThreadLocal.getCurrentUserId());

		if (EweblibUtil.isEmpty(pic.getUserId())) {
			pic.setUserId(EWeblibThreadLocal.getCurrentUserId());
		}
		
		if (EweblibUtil.isEmpty(pic.getUserId())) {			
			throw new ResponseException("请先登录");
		}
		
		for (int i = 0; i < images; i++) {
			pic.setPicUrl(uploadFile(request, relativeFilePath, "picData" + i, 512, new String[]{"gif","jpg","jpeg","png"}));
			userService.addPic(pic);
		}
		responseWithData(null, request, response);
	}
	
	@RequestMapping("/pic/list.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void listPics(HttpServletRequest request, HttpServletResponse response) {
		SearchVo vo = (SearchVo) parserJsonParameters(request, false, SearchVo.class);
		responseWithDataPagnation(userService.listPics(vo), request, response);
	}
	
	
	@RequestMapping("/salary/add.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void addSalary(HttpServletRequest request, HttpServletResponse response) {
		Salary salary = (Salary) parserJsonParameters(request, false, Salary.class);
		userService.addSalart(salary);
		responseWithData(null, request, response);
	}
	
	
	@RequestMapping("/salary/app/detail.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void getSalaryDetail(HttpServletRequest request, HttpServletResponse response) {
		Salary salary = (Salary) parserJsonParameters(request, false, Salary.class);
		
		responseWithEntity(userService.getSalaryDetail(salary), request, response);
	}
	
	
	
	@RequestMapping("/salary/app/list.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void listUserSalaries(HttpServletRequest request, HttpServletResponse response) {
		SearchVo vo = (SearchVo) parserJsonParameters(request, false, SearchVo.class);
		responseWithDataPagnation(userService.listUserSalaries(vo), request, response);
	}
	
	
	@RequestMapping("/salary/list.do")
	@Permission(groupName = PermissionConstants.ADM_USER_MANAGE, permissionID = PermissionConstants.ADM_USER_MANAGE)
	public void listAllUserSalaries(HttpServletRequest request, HttpServletResponse response) {
		SearchVo vo = (SearchVo) parserJsonParameters(request, false, SearchVo.class);
		responseWithDataPagnation(userService.listAllUserSalaries(vo), request, response);
	}
	
}
