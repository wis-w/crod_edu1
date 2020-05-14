package com.edu.crowd.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.edu.croed.constant.CrowdConstant;
import com.edu.croed.exception.LoginAcctAlreadyInUseForUpdateException;
import com.edu.croed.exception.LoginFailedException;
import com.edu.croed.util.CrowdUtil;
import com.edu.crowd.entity.Admin;
import com.edu.crowd.entity.AdminExample;
import com.edu.crowd.entity.AdminExample.Criteria;
import com.edu.crowd.mapper.AdminMapper;
import com.edu.crowd.service.api.AdminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author wyg_edu
 * @version v1.0
 */
@Service
public class AdminServiceImpl implements AdminService{
	
	Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);
	
	@Autowired
	private AdminMapper adminMapper;

	/**
	 *存储用户
	 */
	@Override
	public void saveAdmin(Admin admin){
		adminMapper.insert(admin);
//		throw new RuntimeException();
	}

	/**
	 * 查询所有用户
	 */
	@Override
	public List<Admin> getAll() {
		return adminMapper.selectByExample(new AdminExample());
	}

	@Override
	public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
		
		// 1、根据登录帐号查询Admin对象
		
		AdminExample adminExample = new AdminExample();
		Criteria criteria = adminExample.createCriteria(); 
		criteria.andLoginAcctEqualTo(loginAcct);
		List<Admin> list = adminMapper.selectByExample(adminExample);
		
		// 2、判断Admin用户是否为空
		if(list == null || list.size() == 0) {
			log.info("用户信息不存在");
			throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
		}
		
		if(list.size() > 1) {
			log.info("用户名不唯一");
			throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQU);
		}
		
		// 3、如果为空抛出异常
		Admin admin = list.get(0);
		
		if(admin == null) {
			log.info("用户信息不存在");
			throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
		}
		
		// 4、不为空取出密码
		String userPwdDB = admin.getUserPswd();
		
		// 5、将表单密码进行加密
		String userPwdFrom = CrowdUtil.md5(userPswd);
		
		// 6、比较结果不一致则抛出异常
		if(!Objects.equals(userPwdDB, userPwdFrom)) {
			log.info("用户密码错误");
			throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
		}
		
		// 7、如果一致则返回Admin对象
		return admin;
		
		// 我为什么不使用加密密码和用户直接进行查询数据库，这样就不用对比了
	}

	/**
	 * 根据关键字的查询
	 */
	@Override
	public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSise) {
		// 1、调用pageHelp静态方法开启分页功能
		PageHelper.startPage(pageNum,pageSise);
		
		// 2、执行查询
		List<Admin> list = adminMapper.selectAdminByKeyWord(keyword);
		
		// 3、封装到pageinfo对象中
		return new PageInfo<>(list);
	}

	/**
	 * 删除操作
	 */
	@Override
	public void remove(Integer adminId) {
		adminMapper.deleteByPrimaryKey(adminId);
	}

	/**
	 * 根据id查询用户
	 */
	@Override
	public Object getAdminById(int adminId) {
		return adminMapper.selectByPrimaryKey(adminId);
	}

	/**
	 * 执行更新(对空值不执行)
	 */
	@Override
	public void update(Admin admin) {
		try {
			adminMapper.updateByPrimaryKeySelective(admin);
		} catch (Exception e) {
			if(e instanceof DuplicateKeyException) {
				throw new LoginAcctAlreadyInUseForUpdateException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
			}
		}
	}

}
