package org.bb.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bb.bean.AdminInfoBean;
import org.bb.service.impl.LoginServiceImpl;
import org.bb.utils.MyUtils;

/**
 * 使用WebServlet注解配置servlet信息 默认参数是servlet的映射路径
 */

/**
 * @ClassName: loginAction
 * @Description: 控制器
 * @author bql
 * @date 2018年9月12日
 *
 */
@WebServlet("/LoginAction")
public class loginAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 对客户端请求进行响应处理
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 取出客户端发送的表单数据
		// 获取用户名
		AdminInfoBean adminB = MyUtils.convertFormData2Bean(request, AdminInfoBean.class);
		System.out.println("登录用户名："+adminB.getAdminname());
		//时间
		System.out.println("注册时间："+MyUtils.convertDate2String(adminB.getRegDate(), "yyyy-MM-dd HH:mm:ss"));
		
		// 调用model层进行业务逻辑处理：查询数据库进行用户信息合法性验证
		List<AdminInfoBean> lstAdmins = MyUtils.getInstance(LoginServiceImpl.class).queryLoginInfo(adminB);
		// 根据接收到的model的返回结果，进行用户视图的转发控制
		if (lstAdmins != null && lstAdmins.size() > 0) {
			// 登录成功，挑战到登录成功的页面
			// 缓存登录成功的用户信息
			request.getSession().setAttribute("loginedUser", lstAdmins.get(0));
			response.setCharacterEncoding("utf-8");
			// 使用重定向
			response.sendRedirect("logined.jsp");
		} else {
			// 登录失败，重新登录
			// 使用重定向
			response.sendRedirect("login.jsp");
		}
	}

}
