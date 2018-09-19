package org.bb.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * 设置所有的请求和响应的编码的过滤器
 */
@WebFilter("/*")
public class CharsetEncodingFilter implements Filter {

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 过滤器业务逻辑处理
		// 过滤器链正向穿过时执行的代码块
		// 设置request编码
		request.setCharacterEncoding("utf-8");
		// 设置response编码
		response.setCharacterEncoding("utf-8");
		chain.doFilter(request, response);
		
		// 过滤器链反向穿过时执行的代码块
		
		
//		System.out.println("执行了设置编码过滤器");
		// 继续在过滤器链上向下传递
	}

	/**
	 * 获取配置信息
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
