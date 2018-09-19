package org.bb.service;

import java.util.List;

import org.bb.bean.AdminInfoBean;

public interface ILoginService {

	/**
	 * 根据用户名和密码查询登录用户信息
	 * 
	 * @param adminB 参数实体
	 * @return 查询结果集
	 */
	public List<AdminInfoBean> queryLoginInfo(AdminInfoBean adminB);
	
	/**
	 * 保存和修改管理员信息
	 * 
	 * @param adminB 参数实体
	 * @return 操作结果
	*/
	public int saveOrUpdateAdminInf(AdminInfoBean adminB);
}

