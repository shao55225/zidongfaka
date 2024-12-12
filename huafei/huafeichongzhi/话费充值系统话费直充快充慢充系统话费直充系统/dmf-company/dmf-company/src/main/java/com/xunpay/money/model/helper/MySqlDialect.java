package com.xunpay.money.model.helper;

public class MySqlDialect implements IDialect {

	enum MySqlColumnType {
		BIGINT("bigint", "Long", "", "BIGINT"),
		BIT("bit", "Boolean", "", "BIT"),
		CHAR("char", "String", "", "CHAR"),
		DATE("date", "Date", "java.util.Date", "DATE"),
		DATETIME("datetime", "java.util.Date", "java.util.Date", "TIMESTAMP"),
		DECIMAL("decimal", "java.math.BigDecimal", "", "DECIMAL"),
		DOUBLE("double", "Double", "", "DOUBLE"),
		FLOAT("float", "Float", "", "FLOAT"),
		INT("int", "Integer", "", "INTEGER"),
		SMALLINT("smallint", "Integer", "", "INTEGER"),
		MEDIUMINT("mediumint", "Integer", "", "INTEGER"),
		TEXT("text", "String", "", "TEXT"),
		TINYTEXT("tinytext", "String", "", "TINYTEXT"),
		LONGTEXT("longtext", "String", "", "LONGTEXT"),
		TIME("time", "Timestamp", "java.sql.Timestamp", "TIME"),
		TINYINT("tinyint", "Integer", "", "TINYINT"),
		VARCHAR("varchar", "String", "", "VARCHAR")
		;
		private String type;
		private String javaType;
		private String javaPackage;
		private String sqlType;
		
		private MySqlColumnType(String type, String javaType,
				String javaPackage, String sqlType) {
			this.type = type;
			this.javaType = javaType;
			this.javaPackage = javaPackage;
			this.sqlType = sqlType;
		}

		public String getType() {
			return type;
		}

		public String getJavaType() {
			return javaType;
		}

		public String getJavaPackage() {
			return javaPackage;
		}

		public String getSQLType() {
			return sqlType;
		}
	}
	
	public String getJAVAType(String type) {
		for (MySqlColumnType columnType : MySqlColumnType.values()) {
			if (columnType.getType().equals(type)) {
				return columnType.getJavaType();
			}
		}
		return null;
	}
	
	public String getSQLType(String type) {
		for (MySqlColumnType columnType : MySqlColumnType.values()) {
			if (columnType.getType().equals(type)) {
				return columnType.getSQLType();
			}
		}
		return null;
	}


}
