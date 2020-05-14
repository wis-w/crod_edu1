package com.edu.crowd.service.api;

import java.util.List;

import com.edu.crowd.entity.Admin;
import com.github.pagehelper.PageInfo;

/**
 * @author wyg_edu
 * @date 2020年4月27日
 * @version v1.0
 */
public interface AdminService {
	
	public void saveAdmin(Admin admin);

	public List<Admin> getAll();

	public Admin getAdminByLoginAcct(String loginAcct, String userPswd);
	
	PageInfo<Admin> getPageInfo(String keyWord,Integer pageNum,Integer pageSise);

	public void remove(Integer adminId);

	public Object getAdminById(int adminId);

	public void update(Admin admin);

}
