package com.xunpay.money.model.helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.jfinal.kit.PropKit;

public class ModelHelper {
	
	static JdbcCommon common;
	static final String database = "f0f5e5w6";
	
	public static void main(String[] args) throws Exception {
		PropKit.clear();
		PropKit.use("jdbc.properties");
		
		String url = PropKit.get("jdbc.url");
		String username = PropKit.get("jdbc.username");
		String password = PropKit.get("jdbc.password").trim();
		
		common = new JdbcCommon("com.mysql.jdbc.Driver", url, username, password);
		
		List<Map<String, Object>> items = common.queryBySql("SELECT TABLE_NAME,TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = ? AND TABLE_TYPE = 'BASE TABLE'", database);
		
		StringBuffer utilContent = new StringBuffer("package com.xunpay.money.model;\n\n");
		utilContent.append("import com.jfinal.plugin.activerecord.ActiveRecordPlugin;\n\n");
		utilContent.append("public class ModelUtils {\n\n");
		utilContent.append("	public static void loadModels(ActiveRecordPlugin arp) {\n");
		String path = "src/main/java/com/xunpay/money/model/";
		for (Map<String, Object> item : items) {
			String tableName = (String) item.get("TABLE_NAME");
			if (tableName.startsWith("sms_weixin_2")) {
				continue;
			}
			String tableComment = (String) item.get("TABLE_COMMENT");
			String filePath = path + firstUpper(getJavaName(tableName)) + ".java";
			String fileContent =getModelContent(tableName, tableComment);
			writetoFile(filePath, fileContent);
			utilContent.append("		arp.addMapping(\"" + tableName + "\", \"id\", " + firstUpper(getJavaName(tableName)) + ".class);\n");
		}
		utilContent.append("	}\n");
		utilContent.append("}");
		writetoFile(path + "ModelUtils.java", utilContent.toString());
		System.out.println("Model生成完成，请刷新model文件夹");
	}
	
	private static String getModelContent(String tableName, String tableComment) {
		String className = firstUpper(getJavaName(tableName));
		StringBuffer context = new StringBuffer();
		context.append("package com.xunpay.money.model;\n\n");
		context.append("import com.xunpay.money.model.annotation.Table;\n");
		context.append("import com.jfinal.plugin.activerecord.Model;\n\n");
		context.append("/**\n");
		context.append(" * \n");
		context.append(" * 对应表名：").append(tableName).append("\n");
		context.append(" * 表描述：").append(tableComment).append("\n");
		context.append(" * \n");
		context.append(" * @author ModelHelper \n");
		context.append(" * @version 1.0 \n");
		context.append(" */\n");
		context.append("@SuppressWarnings(\"serial\")\n");
		context.append("@Table(name = \"").append(tableName).append("\")\n");
		context.append("public class ").append(className).append(" extends Model<").append(className).append("> {\n");
		context.append("\n");
		context.append("\tpublic static final ").append(className).append(" dao = new ").append(className).append("();\n");
		context.append("\n");
		
		List<Map<String, Object>> colItems = common.queryBySql(
						"SELECT COLUMN_NAME,COLUMN_DEFAULT,IS_NULLABLE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH,COLUMN_KEY,COLUMN_COMMENT,EXTRA FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ? ORDER BY ORDINAL_POSITION",
						database, (String) tableName);
		context.append("\n");
		for (Map<String, Object> colItem : colItems) {
			String cname = (String) colItem.get("COLUMN_NAME");
			String type = (String) colItem.get("DATA_TYPE");
			
			String defaultValue = (String) colItem.get("COLUMN_DEFAULT");
			String canNull = (String) colItem.get("IS_NULLABLE");
			BigInteger colLen = (BigInteger) colItem.get("CHARACTER_MAXIMUM_LENGTH");
			String key = (String) colItem.get("COLUMN_KEY");
			String comment = (String) colItem.get("COLUMN_COMMENT");
			String auto = (String) colItem.get("EXTRA");
			
			context.append("\t/**\n");
			if (StringUtils.isNotEmpty(comment)) {
				context.append("\t * ").append(comment).append("<br />\n");
			}
			context.append("\t * <ul>\n");
			if (StringUtils.isNotEmpty(defaultValue)) {
				context.append("\t *   <li>默认值：").append(comment).append("</li>\n");
			}
			context.append("\t *   <li>非空：").append("NO".equalsIgnoreCase(canNull) ? "不能为空" : "可以为空").append("</li>\n");
			context.append("\t *   <li>数据类型：").append(type);
			if (colLen != null) {
				context.append("(").append(colLen).append(")");
			}
			context.append("</li>\n");
			if (StringUtils.isNotEmpty(key)) {
				context.append("\t *   <li>键约束：").append(key).append("</li>\n");
			}
			if ("auto_increment".equalsIgnoreCase(auto)) {
				context.append("\t *   <li>扩展：自动增长列").append("</li>\n");
			}
			context.append("\t * </ul>\n");
			context.append("\t */\n");
			context.append("\tpublic ");
			context.append(getJAVAType(type));
			context.append(" get").append(firstUpper(getJavaName(cname))).append("() {\n");
			context.append("\t\treturn (").append(getJAVAType(type)).append(") get(\"").append(cname).append("\");");
			context.append("\n\t}\n\n");
			context.append("\tpublic void set").append(firstUpper(getJavaName(cname))).append("(").append(getJAVAType(type)).append(" ").append(getJavaName(cname)).append(") {\n");
			context.append("\t\tset(\"").append(cname).append("\", ").append(getJavaName(cname)).append(");");
			context.append("\n\t}\n\n");
		}
		
		context.append("}");
		return context.toString();
	}
	
	public static String getJAVAType(String type) {
		return new MySqlDialect().getJAVAType(type);
	}
	
	private static String firstUpper(String name) {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}
	
	private static String firstLower(String name) {
		return name.substring(0, 1).toLowerCase() + name.substring(1);
	}
	
	private static String getJavaName(String name) {
		name = name.toLowerCase();
		String[] cs = name.split("_");
		String jname = "";
		for (String c : cs) {
			jname += firstUpper(c);
		}
		return firstLower(jname);
	}
	
	private static void writetoFile(String filePath, String content) {
		FileWriter writer = null;
		try {
			File file = new File(filePath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			writer = new FileWriter(file);
			writer.write(content);
			System.out.println("文件生成：" + filePath);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
