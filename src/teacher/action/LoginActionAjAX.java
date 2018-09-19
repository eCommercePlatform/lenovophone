package org.bb.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bb.bean.AdminInfoBean;
import org.bb.utils.MyUtils;

/**
 * Servlet implementation class LoginActionAjAX
 */
@WebServlet("/LoginActionAjAX")
public class LoginActionAjAX extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 打包
		AdminInfoBean adminB = MyUtils.convertFormData2Bean(request, AdminInfoBean.class);
		// 获取名字
		System.out.println("从客户端请求获取的用户名为:" + adminB.getAdminname());
		// 响应客户单的请求
		// 返回的是PrintWriter，这是一个打印输出流。response.getWriter().writer（）,只能打印输出文本格式的（包括html标签），不可以打印对象。
		PrintWriter pw = response.getWriter();
		pw.write("用户已存在");
		// 每个任务执行完之后都会直接输出信息到页面。
		pw.flush();
	}

}
