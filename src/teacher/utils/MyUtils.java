package org.bb.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.log4j.Logger;

public class MyUtils {
	public static Logger logRootLogger = Logger.getLogger("rootLogger");

	/**
	 * 简单工厂模式：获取一个指定Class的实例对象
	 * 
	 * @param clazz Class
	 * @return 实例对象
	 */
	public static <T> T getInstance(Class<T> clazz) {
		// 返回值
		T t = null;
		try {
			// 反射获取该Class的一个实例对象
			t = clazz.newInstance();
		} catch (Exception e) {
			// 获取实例对象异常
			// e.printStackTrace();
			System.out.println("获取实例对象异常:" + e.getMessage());
		}
		return t;
	}

	/**
	 * 使用反射，将request中提交的form表单数据映射到实体类中 约定：表达的name属性值和数据库的字段名保持一致【大小写敏感】
	 * 
	 * @param request request请求对象
	 * @param clazz   实体类的Class
	 * @return 映射表达数据的实体类实例
	 */
	public static <T> T convertFormData2Bean(HttpServletRequest request, Class<T> clazz) {
		T t = getInstance(clazz);
		if (request != null) {

			/*******
			 * 注册定义了类型转换器：form表单中的String串向目标数据类型的自定义转换 定义BeanUtils的包装类类型的转换器【可以使用匿名内部类】
			 ********/
			ConvertUtils.register(new DateTimeConverter(), Date.class);

			try {
				// request.getParameterNames()方法是将发送请求页面中form表单里所有具有name属性的表单对象获取(包括button).返回一个Enumeration类型的枚举.
				Enumeration<String> paramName = request.getParameterNames();
				if (paramName != null) {
					// hasMoreElements() 当且仅当此枚举对象至少还包含一个可提供的元素时，才返回 true
					while (paramName.hasMoreElements()) {
						// 如果此枚举对象至少还有一个可提供的元素，则返回此枚举的下一个元素。此枚举的下一个元素。
						String strName = (String) paramName.nextElement();
						// 读取提交的表单中的值
						String strVal = request.getParameter(strName);
						// 将获取到的数据打包放入实体类中(把对象的属性数据封装到对象中)
						// bean是指你将要设置的对象，name指的是将要设置的属性(写成"属性名"),value(从配置文件中读取到到的字符串值)
						// BeanUtils.setProperty(t, strName, strVal);
						// setProperty和copyProperty用法一样
						BeanUtils.copyProperty(t, strName, strVal);
					}
				}
			} catch (Exception e) {
				// e.printStackTrace();
				logRootLogger.info("form表单属性值映射到实体类中异常：" + e.getMessage());
//				System.out.println("form表单属性值映射到实体类中异常：" + e.getMessage());
			}
		}
///////////////////// 文件的映射处理-begin////////////////////////////
// 从request中将文件流信息进行提取，将其保存到服务器指定目录，并将保存的目录的相对地址存入数据库中
//		System.out.println("request中的上下文内容：" + request.getContentType());
// 判断当前request请求中是否包含了流文件
		if (request.getContentType().split(";")[0].equals("multipart/form-data")) {
			try {
				// 获取request中的所有的控件的part集合【每个part中包含form表单中的一个控件对象】
				Collection<Part> parts = request.getParts();

				// 临时参数
				String uploadDerictory = "amdinImgs";
				String uploadFilePrefix = "amdinImg";

				// 遍历每一个part，处理含有文件的part部分
				if (parts != null && parts.size() > 0) {
					for (Part part : parts) {
						// 检查含有文件流的part部分进行处理
						// 使用part的ContentType来区分该part是包含文件流控件还是普通控件
						// 获取 MINE类型，普通控件的改属性是 null，文件上传控件的属性不是null
						String partContent = part.getContentType();
						if (partContent != null && !partContent.equals("") && part.getSize() > 0) {
							// 处理含有文件流空间的part，进行文件上传
							// 准备文件上传的服务器的路径地址【要求是绝对地址】
							String strUploadPath = request.getServletContext().getRealPath("/");
							// 规定：默认存储在 static/uploadfiles/
							// 文件上传时保存的相对路径地址
							String strRelativePath = "static/uploadfiles/" + uploadDerictory;

							// 检测文件上传目录是否已经存在，如果不存在，则创建
							File file = new File(strUploadPath + strRelativePath);
							if (!file.exists()) {
								file.mkdirs();
							}

							// 默认文件后缀为.dat
							String strFileType = ".dat";
							// 获取包含文件后缀信息的上下文的字符描述串
							String strContent = part.getHeader("Content-Disposition");
//							System.out.println("part:"+part);
//							System.out.println("strContent:"+strContent);
							// 正则表达式匹配文件后缀
							String strReg = "\\.\\w*";
							// 创建正则表达式类
							Pattern pattern = Pattern.compile(strReg);
							// 进行匹配操作
							Matcher matcher = pattern.matcher(strContent);
							if (matcher.find()) {
								strFileType = matcher.group(0);
							}
							// 组合一个具有随机码的文件名，防止文件重名
							String strNameFileName = uploadFilePrefix + "_" + System.currentTimeMillis() + strFileType;

							// 使用反射将文件存储的相对路径保存到实例类的属性中
							// 上传文件对象
							part.write(strUploadPath + strRelativePath + File.separatorChar + strNameFileName);

							// 将文件的相对路径保存到文件的实体类属性中
							// 获取文件上传控件的name属性
							String strFieldName = part.getName();
							// 使用反射将表单的value值设置到与之对应的实体类属性中【借助beanutils或者直接使用反射】
							BeanUtils.setProperty(t, strFieldName,
									strRelativePath + File.separatorChar + strNameFileName);

						} else {
							// 处理普通控件
							// 如果在此处进行处理，需要aciton中添加@MultipartConfig注解，所以不建议在此处进行普通控件的处理
						}
					}
				}

			} catch (Exception e) {
				// 文件上传处理异常
				// e.printStackTrace();
				logRootLogger.info("文件上传处理异常：" + e.getMessage());
				// System.out.println("文件上传处理异常：" + e.getMessage());
			}
		}
///////////////////// 文件的映射处理-end////////////////////////////

		return t;
	}

	/**
	 * 将时间类型专为制定格式的字符串【转换失败返回null】
	 * 
	 * @param date           被转化的时间对象
	 * @param strDatePattern 转换的时间格式串
	 * @return 转换之后的日期字符串
	 */
	public static String convertDate2String(Date date, String strDatePattern) {
		// 返回值
		String strDate = null;

		// 日期格式串检测
		if (strDatePattern == null || strDatePattern.equals("")) {
			// 给定默认的时间格式串
			strDatePattern = "yyyy-MM-dd HH:mm:ss";
		}

		// 创建日期转换类
		SimpleDateFormat sdf = new SimpleDateFormat(strDatePattern);

		if (date != null) {
			// 将日期类型专为指定格式的字符串
			strDate = sdf.format(date);
		} else {
			// 根据需求确定
			// strDate = sdf.format(new Date());
		}
		// 返回值
		return strDate;
	}

	/**
	 * 将字符串转换为日期对象 【注意：日期格式串和日期字符串要在格式上匹配】
	 * 
	 * @param strDate        日期字符串
	 * @param strDatePattern 日期格式串
	 * @return 转换之后的日期对象【默认为当前系统时间】
	 */
	public static Date convertString2Date(String strDate, String strDatePattern) {
		// 返回值，默认为当前时间【还是为null，根据业务需求定】
		Date date = new Date();
		// 日期格式串检测:注意：日期格式串和日期字符串要在格式上匹配
		if (strDatePattern == null || strDatePattern.equals("")) {
			// 给定默认的时间格式串
			strDatePattern = "yyyy-MM-dd HH:mm:ss";
		}

		// 创建日期转换类
		SimpleDateFormat sdf = new SimpleDateFormat(strDatePattern);

		try {
			// 进行日期转换
			date = sdf.parse(strDate);
		} catch (Exception e) {
			// 日期转换异常
//			e.printStackTrace();
			System.out.println("字符串转为日期对象异常：" + e.getMessage());
		}
		// 返回值
		return date;
	}

	public static void main(String[] args) {
		int i = 1;
		if (i == 1) {
//			#OFF FATAL ERROR WARN INFO DEBUG ALL  (heig-->low)
			logRootLogger.fatal("fatal");
			logRootLogger.error("error");
			logRootLogger.warn("warn");
			logRootLogger.info("info");
			logRootLogger.debug("debug");
		}
	}

}
