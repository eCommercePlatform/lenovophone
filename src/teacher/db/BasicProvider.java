package org.bb.db;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

public class BasicProvider {

	/**
	 * 查询操作
	 * 
	 * @param sql    sql语句
	 * @param rsh    结果集接口
	 * @param params 参数列表
	 * @return 查询结果集
	 */
	public <T> T query(String sql, ResultSetHandler<T> rsh, Object... params) {
		// 查询结果集
		T t = null;
		try {
			// 创建一个queryrunner类 -- 等价于jdbc的statement查询器
			QueryRunner qr = new QueryRunner(DBCPDataSource.getDataSource(), true); // 第二个参数是支持sql语句中使用占位符
			// 进行查询操作
			t = qr.query(sql, rsh, params);
		} catch (Exception e) {
			// 查询操作异常
			System.out.println("查询操作异常：" + e.getMessage());
			e.printStackTrace();
		}
		// 返回值
		return t;
	}

	/**
	 * 修改、新增、删除操作
	 * 
	 * @param sql    sql语句
	 * @param params 参数列表
	 * @return 操作结果
	 */
	public int update(String sql, Object... params) {
		// 返回结果
		int i = 0;
		try {
			// 创建一个queryrunner类 -- 等价于jdbc的statement查询器
			QueryRunner qr = new QueryRunner(DBCPDataSource.getDataSource(), true); // 第二个参数是支持sql语句中使用占位符
			// 进行修改、新增、删除操作
			i = qr.update(sql, params);
		} catch (Exception e) {
			// 查询操作异常
			System.out.println("查询操作异常：" + e.getMessage());
			e.printStackTrace();
		}
		// 返回值
		return i;
	}

}
