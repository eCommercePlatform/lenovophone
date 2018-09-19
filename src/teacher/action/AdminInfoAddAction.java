package org.bb.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bb.bean.AdminInfoBean;
import org.bb.service.impl.LoginServiceImpl;
import org.bb.utils.MyUtils;

/**
 * Servlet 文件上传
 */
@WebServlet("/AdminInfoAddAction")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // 设置文件大小
public class AdminInfoAddAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 业务逻辑处理
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1、接收表单数据
		AdminInfoBean adminB = MyUtils.convertFormData2Bean(request, AdminInfoBean.class);
		MyUtils.logRootLogger.info("用户登陆名:" + adminB.getAdminname());
		MyUtils.logRootLogger.info("用户登陆时间:" + adminB.getRegDate());
		// 2、调用service层进行数据保存
		int i = MyUtils.getInstance(LoginServiceImpl.class).saveOrUpdateAdminInf(adminB);
		// 3、根据保存处理结果进行页面转发
		if (i > 0) {
			// 保存成功
			response.sendRedirect("adminInfoAddDetail.jsp");
		} else {
			// 保存失败
			response.sendRedirect("AjaxQueryAdd.jsp");
		}
	}

}
