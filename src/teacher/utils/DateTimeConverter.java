package org.bb.utils;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.commons.beanutils.Converter;

public class DateTimeConverter implements Converter {
	// 要转换的目标日期类型的格式
	private static final String DATE = "yyyy-MM-dd";
	private static final String DATETIME = "yyyy-MM-dd HH:mm";
	private static final String DATETIME1 = "yyyy-MM-dd HH:mm:ss";
	private static final String TIMESTAMP = "yyyy-MM-dd HH:mm:ss.SSS";

	/*
	 * 将第二个参数【来自于表单数据】转换为目标类型【来自于实体类】
	 */
	@Override
	public <T> T convert(Class<T> desDate, Object srcObj) {
		//
		return toDate(desDate, srcObj);
	}

	/**
	 * 将字符串转换为对应格式的Date对象
	 * 
	 * @param type
	 *            类型
	 * @param value
	 *            转换的值
	 * @return Date对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toDate(Class<T> type, Object value) {
		if (value == null || "".equals(value))
			return null;
		// 判断被转换的对象值是不是String类型
		if (value instanceof String) {
			// 获取转换的对象的字符串
			String dateValue = value.toString().trim();
			// 被转换字符串的长度
			int length = dateValue.length();
			// 判断被转换的类型是不是时间类型
			if (type.equals(java.util.Date.class)) {
				try {
					DateFormat formatter = null;
					// 转换为“yyyy-MM-dd”格式的Date对象
					if (length <= 10) {
						formatter = new SimpleDateFormat(DATE, new DateFormatSymbols(Locale.CHINA));
					} else
					// 转换为“yyyy-MM-dd HH:mm:ss”格式的Date对象
					if (length <= 16) {
						formatter = new SimpleDateFormat(DATETIME, new DateFormatSymbols(Locale.CHINA));
					} else
					// 转换为“yyyy-MM-dd HH:mm:ss”格式的Date对象
					if (length <= 19) {
						formatter = new SimpleDateFormat(DATETIME1, new DateFormatSymbols(Locale.CHINA));
					} else
					// 转换为“yyyy-MM-dd HH:mm:ss.SSS”格式的Date对象
					if (length <= 23) {
						formatter = new SimpleDateFormat(TIMESTAMP, new DateFormatSymbols(Locale.CHINA));
					}

					return (T) formatter.parse(dateValue);
				} catch (Exception e) {
					// e.printStackTrace();
					System.out.println("时间转换错误" + e.getMessage());
				}
			}
		}
		return (T) value;
	}

}
