package com.edu.crowd.mvc.handler;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.edu.croed.constant.CrowdConstant;
import com.edu.croed.exception.LoginAcctAlreadyInUseException;
import com.edu.croed.util.CrowdUtil;
import com.edu.crowd.entity.Admin;
import com.edu.crowd.service.api.AdminService;
import com.github.pagehelper.PageInfo;

/**
 * @author wyg_edu
 * @date 2020年5月9日 下午9:13:43
 * @version v1.0
 */
@Controller
public class AdminHandler {

	Logger log = LoggerFactory.getLogger(AdminHandler.class);

	@Autowired
	private AdminService adminService;

	/**
	 * 用户信息修改
	 * @param admin
	 * @param pageNum
	 * @param keyWord
	 * @return
	 */
	@RequestMapping("/admin/update.html")
	public String update(Admin admin, @RequestParam("pageNum") Integer pageNum,
			@RequestParam("keyWord") String keyWord) {
		log.info("用户信息修改");
		adminService.update(admin);
		return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyWord=" + keyWord;
		
	}
	

	/**
	 * 修改页面跳转
	 * @param adminId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/admin/to/edit/page.html")
	public String edit(@RequestParam("adminId") int adminId,
			ModelMap modelMap) {
		// 查詢需要修改的记录
		modelMap.addAttribute("admin", adminService.getAdminById(adminId));
		return "admin-edit";

	}

	/**
	 * 新增用户
	 * 
	 * @param admin
	 * @return
	 */
	@RequestMapping("/admin/save.html")
	public String add(Admin admin) {
		// 1、密码加迷
		admin.setUserPswd(CrowdUtil.md5(admin.getUserPswd()));
		// 2、创建当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		admin.setCreateTime(sdf.format(new Date()));
		// 3、执行保存
		try {
			adminService.saveAdmin(admin);
		} catch (Exception e) {
			log.info("用户新增异常");
			if (e instanceof DuplicateKeyException) {
				throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
			}
		}

		return "redirect:/admin/get/page.html?pageNum=" + Integer.MAX_VALUE;// 转发到最后一页，使得用户新政后直接显示
	}

	/**
	 * 删除操作
	 * 
	 * @return
	 */
	@RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyWord}.html")
	public String remove(@PathVariable("adminId") Integer adminId, @PathVariable("pageNum") int pageNum,
			@PathVariable("keyWord") String keyWord, HttpSession httpSession) {
		// 判断是否为当前用户
		Admin admin = (Admin) httpSession.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);
		if (adminId == admin.getId()) {
			throw new RuntimeException("无法删除当前操作的用户");
		}
		// 执行删除
		adminService.remove(adminId);
		// 跳转回查询页面
//		return "admin-page"; // 会无法显示分页的数据
//		return "forward:/admin/get/page.html";// 刷新页面会重新执行删除，浪费性能
		return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyWord=" + keyWord;

	}

	/**
	 * 进行分页查询
	 * 
	 * @param keyWord
	 * @param pageNum
	 * @param pageSize
	 * @param madelMap
	 * @return
	 */
	@RequestMapping("/admin/get/page.html")
	public String getPageInfo(@RequestParam(value = "keyWord", defaultValue = "") String keyWord,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize, ModelMap modelMap) {
		log.info("分页显示");

		// 1、调用service方法，获取pageInfo对象
		PageInfo<Admin> info = adminService.getPageInfo(keyWord, pageNum, pageSize);

		// 2、将pageinfo放入model
		modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, info);

		return "admin-page";

	}

	/**
	 * 登录功能
	 * 
	 * @param loginAcct
	 * @param userPswd
	 * @param session
	 * @return
	 */
	@RequestMapping("/admin/do/login.html")
	public String doLogin(@RequestParam("loginAcct") String loginAcct, @RequestParam("userPswd") String userPswd,
			HttpSession session) {

		// 调用service方法进行登录检查
		// 如果成功返回admin则证明登录成功，反之抛出异常
		Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);
		log.info("登录成功" + admin.toString());

		// 将登录成功返回的Admin对象存入session域
		session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);

		// 使用重定向，转发刷新页面时会重新提交表单
		return "redirect:/admin/to/admin/page.html";
	}

	/**
	 * 退出功能
	 * 
	 * @param httpSession
	 * @return
	 */
	@RequestMapping("/admin/do/logout.html")
	public String doLogout(HttpSession httpSession) {
		httpSession.invalidate();// session失效
		return "redirect:/admin/do/login/page.html";
	}

}
