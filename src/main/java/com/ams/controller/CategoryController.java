package com.ams.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ams.bean.Category;
import com.ams.service.ICategoryService;
import com.ams.util.PermissionConstants;
import com.eweblib.annotation.column.LoginRequired;
import com.eweblib.annotation.column.Permission;
import com.eweblib.controller.AbstractController;

@Controller
@RequestMapping("/ecs/category")
public class CategoryController extends AbstractController {

	@Autowired
	private ICategoryService categoryService;
	

	
	private static Logger logger = LogManager.getLogger(CategoryController.class);

	@RequestMapping("/save.do")
	@Permission(groupName = PermissionConstants.ADM_CATEGORY_MANAGE, permissionID = PermissionConstants.ADM_CATEGORY_MANAGE)
	public void save(HttpServletRequest request, HttpServletResponse response) {
		

	}

	@RequestMapping("/delete.do")
	@Permission(groupName = PermissionConstants.ADM_CATEGORY_MANAGE, permissionID = PermissionConstants.ADM_CATEGORY_MANAGE)
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		Category category = (Category) parserJsonParameters(request, false, Category.class);
		logger.info("delete category. " + category.toString());
//		categoryService.delete(category);
		responseWithKeyValue("msg", "ok", request, response);
	}
	
	@RequestMapping("/list.do")
	@LoginRequired(required = false)
	public void list(HttpServletRequest request, HttpServletResponse response) {
//		responseWithListData(categoryService.list(), request, response);
	}
	
	
	
	public ICategoryService getCategoryService() {
    	return categoryService;
    }

	public void setCategoryService(ICategoryService categoryService) {
    	this.categoryService = categoryService;
    }
	
	
}