package com.manage.utils;

import java.util.Properties;

public class PropertiesUtils {
	/**
	 * 加载属性文件
	 * 
	 * @param filePath
	 *            文件路径
	 * @return
	 */
	public static Properties loadProps(String filePath) {
		Properties properties = new Properties();
		try {
			properties.load(PropertiesUtils.class.getResourceAsStream(filePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
	}

	/**
	 * 读取配置文件
	 * 
	 * @param props
	 *            配置文件
	 * @param key
	 * @return
	 */
	public static String getString(Properties properties, String key) {
		return properties.getProperty(key);
	}
}
