package com.xunpay.money.model.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * jdbc操作类
 * 
 * @author tangshu
 * 
 */
public class JdbcCommon {

	private String dirver;
	private String url;
	private String username;
	private String password;
	
	public JdbcCommon(String dirver, String url, String username,
			String password) {
		super();
		this.dirver = dirver;
		this.url = url;
		this.username = username;
		this.password = password;
	}

	private Connection connection;
	private PreparedStatement statement;
	private ResultSet resultSet;
	
	/**
	 * 初始化和打开数据库连接
	 */
	private void openConnection() throws Exception {
		if (connection == null) {
			Class.forName(dirver);
			connection = DriverManager.getConnection(url, username, password);
		}
	}

	/**
	 * 关闭连接 释放资源
	 * @throws Exception
	 */
	private void closeConnection() {
		try {
			if (resultSet != null) resultSet.close(); resultSet = null;
			if (statement != null) statement.close(); statement = null;
			if (connection != null) connection.close(); connection = null;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 绑定sql参数
	 * @param objs
	 * @throws Exception
	 */
	private void bind(Object... objs) throws Exception {
		if (statement != null) {
			int i = 1;
			for (Object obj : objs) {
				statement.setObject(i, obj);
				i++;
			}
		}
	}
	
	/**
	 * 执行SQL(insert, update, delete)语句
	 * @param sql
	 * @param objs
	 * @return
	 */
	public int executeSQL(String sql, Object... objs) {
		try {
			if (connection == null) openConnection();
			statement = connection.prepareStatement(sql);
			bind(objs);
			return statement.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return 0;
	}

	/**
	 * 查询SQL语句
	 * @param sql
	 * @param objs
	 * @return
	 */
	public List<Map<String, Object>> queryBySql(String sql, Object... objs) {
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		try {
			if (connection == null) openConnection();
			statement = connection.prepareStatement(sql);
			bind(objs);
			resultSet = statement.executeQuery();
			int columnCount = 0;
			while (resultSet.next()) {
				Map<String, Object> item = new HashMap<String, Object>();
				ResultSetMetaData metaData = resultSet.getMetaData();
				if (columnCount <= 0) {
					columnCount = metaData.getColumnCount();
				}
				for (int i = 1; i <= columnCount; i++) {
					item.put(metaData.getColumnName(i), resultSet.getObject(i));
				}
				result.add(item);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return result;
	}
	
}
