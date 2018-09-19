package org.bb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.bb.bean.AdminInfoBean;
import org.bb.db.DBProvider;
import org.bb.service.IAjaxDemoService;
import org.bb.utils.MyUtils;

public class AjaxDemoServiceImpl implements IAjaxDemoService {

	@Override
	public List<AdminInfoBean> queryLoginInfoByParams(AdminInfoBean adminB) {
		// 组织查询语句
		StringBuilder sbQuerySql = new StringBuilder(" select * from admininfo where 1=1 ");

		// 查询条件的参数集合
		List<Object> lstParams = new ArrayList<>();
		if (adminB != null) {
			// 根据查询条件动态拼写查询条件
			// 姓名模糊查询
			String strName = adminB.getAdminname();
			if (strName != null && !strName.equals("")) {
				// 添加姓名的模糊查询条件
				sbQuerySql.append(" and adminName like ? ");
				// 保存查询参数
				lstParams.add("%" + strName + "%");
			}

			// 地址模糊查询
			String strAddress = adminB.getAdminaddress();
			if (strAddress != null && !strAddress.equals("")) {
				// 添加姓名的模糊查询条件
				sbQuerySql.append(" and adminaddress like ? ");
				// 保存查询参数
				lstParams.add("%" + strAddress + "%");
			}

		}

		// 调用db层进行数据库查询操作
		List<AdminInfoBean> lstAdmins = MyUtils.getInstance(DBProvider.class).query(sbQuerySql.toString(),
				new BeanListHandler<>(AdminInfoBean.class), lstParams.toArray());
		// 返回结果
		return lstAdmins;
	}

}
