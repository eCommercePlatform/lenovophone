package org.bb.db;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

/**
 * 单例模式实现数据源
 * 
 * @author Administrator
 *
 */
public class DBCPDataSource {
	// 1、定义一个单例模式的静态变量
	private static DataSource ds = null;

	// 2、构造方法私有化
	private DBCPDataSource() {
	}

	// 3、提供一个静态的全局的访问入口【没有进行多线程同步处理】
	public static DataSource getDataSource() {
		// 是否第一次访问，并进行初始化
		if (ds == null) {
			try {
				// 使用Properties读取配置信息
				Properties prop = new Properties();
				// 加载配置信息
				prop.load(DBCPDataSource.class.getClassLoader().getResourceAsStream("db.properties"));
				// 使用工厂类初始化数据源
				ds = BasicDataSourceFactory.createDataSource(prop);
			} catch (Exception e) {
				// dbcp数据源初始化异常
				System.out.println("dbcp数据源初始化异常:" + e.getMessage());
				e.printStackTrace();
			}
		}
		return ds;
	}

//	public static void main(String[] args) {
//		try {
//			// 测试数据库连接是否正常
//			// 创建一个queryrunner类 -- 等价于jdbc的statement查询器
//			QueryRunner qr = new QueryRunner(getDataSource(), true); // 第二个参数是支持sql语句中使用占位符
//			// 查询sql语句
//			String strSql = "select * from ADMININFO";
//			// 进行查询操作
//			List<AdminInfoBean> lst = qr.query(strSql, new BeanListHandler<AdminInfoBean>(AdminInfoBean.class));
//			// 遍历查询结果
//			if (lst != null && lst.size() > 0) {
//				for (AdminInfoBean adminInfoBean : lst) {
//					System.out.println(adminInfoBean.toString());
//					System.out.println(adminInfoBean);
//				}
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
////			e.printStackTrace();
//			System.out.println(e.getMessage());
//		}
//		
		// 查询sql语句
//		String strSql = "select * from ADMININFO";
//		// 进行查询操作
//		List<AdminInfoBean> lst = MyUtils.getInstance(DBProvider.class).query(strSql, new BeanListHandler<AdminInfoBean>(AdminInfoBean.class));
//		// 遍历查询结果
//		if (lst != null && lst.size() > 0) {
//			for (AdminInfoBean adminInfoBean : lst) {
//				System.out.println(adminInfoBean.toString());
//			}
//		}
//	}
	
}
