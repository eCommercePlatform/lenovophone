package org.bb.service.impl;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.bb.bean.AdminInfoBean;
import org.bb.db.DBProvider;
import org.bb.service.ILoginService;
import org.bb.utils.MyUtils;

public class LoginServiceImpl implements ILoginService {

	@Override
	public List<AdminInfoBean> queryLoginInfo(AdminInfoBean adminB) {
		// 调用db层进行数据库操作
		// 登录查询语句
		String sql = "select * from admininfo where adminName = ? and adminpsw= ? ";
		// 调用db层进行数据库查询操作
		List<AdminInfoBean> lstAdmins = MyUtils.getInstance(DBProvider.class).query(sql,
				new BeanListHandler<>(AdminInfoBean.class), adminB.getAdminname(), adminB.getAdminpsw());
		// 返回结果
		return lstAdmins;
	}


	@Override
	public int saveOrUpdateAdminInf(AdminInfoBean adminB) {
		// 默认返回值
		int i = 0;
		// 新增和修改的区别在于：参数实体类的主键字段是否为null，为null则是新增操作，不为null则是修改操作
		// 新增的sql语句
		if(adminB != null ) {
			// 新增操作
			if(adminB.getAdminid() == null) {
				// 新增sql语句
				String strSql = "insert into admininfo(adminname,adminpsw,adminaddress,admintel,regdate,adminimg) values(?,?,?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?)";
				// 调用db层进行数据库操作
				i = MyUtils.getInstance(DBProvider.class).update(strSql, adminB.getAdminname(),adminB.getAdminpsw(),adminB.getAdminaddress(),adminB.getAdmintel(),MyUtils.convertDate2String(adminB.getRegDate(), "yyyy-MM-dd HH:mm:ss"),adminB.getAdminimg());
			}else {
				// 修改操作
				if(adminB.getAdminid() != null) {
					
				}
			}
		}
		return i;
	}

}
