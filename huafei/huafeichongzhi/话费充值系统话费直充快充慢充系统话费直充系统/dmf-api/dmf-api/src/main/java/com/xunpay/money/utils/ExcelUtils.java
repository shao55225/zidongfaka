package com.xunpay.money.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.jfinal.kit.StrKit;

/**
 * Excel工具类
 * @author tangshu
 *
 */
public class ExcelUtils {

	/**
	 * 将excel文件装成List数组
	 * @param is
	 * @return
	 */
	public static List<List<String>> readExcel(InputStream is) {
		List<List<String>> datas = new ArrayList<List<String>>();
		Workbook workbook = null;
		try {
			workbook = WorkbookFactory.create(is);
		} catch (Exception e) {
			try {
				workbook = new HSSFWorkbook(is);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if (workbook == null) {
			return datas;
		}
		for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
			 Sheet sheet = workbook.getSheetAt(numSheet);  
             if (sheet == null) {  
                 continue;  
             }
             for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            	 Row row = sheet.getRow(rowNum);
            	 if (row == null) {
            		 continue;
            	 }
            	 List<String> arrCell = new ArrayList<String>();
            	 for (int cellNum = 0; cellNum <= row.getLastCellNum(); cellNum++) {
                     Cell cell = row.getCell(cellNum);
                     if (cell == null) {
                         continue;
                     }
                     arrCell.add(getValue(cell).trim());
                 }
            	 datas.add(arrCell);
             }
		}
		return datas;
	}
	
	public static void writeExcel(List<List<String>> datas, OutputStream out) {
		// 创建Excel的工作书册 Workbook,对应到一个excel文档  
	    HSSFWorkbook wb = new HSSFWorkbook();
	  
	    // 创建Excel的工作sheet,对应到一个excel文档的tab  
	    HSSFSheet sheet = wb.createSheet("sheet1");
	    sheet.setDefaultColumnWidth(20);
	    //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）  
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch(); 
        
	    // 创建字体样式  
	    HSSFFont font = wb.createFont();
	    font.setFontName("Verdana");
	    //font.setBoldweight((short) 100);
	    //font.setFontHeight((short) 300);
	    //font.setColor(HSSFColor.BLUE.index);
	  
	    // 创建单元格样式  
	    HSSFCellStyle style = wb.createCellStyle();
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
	    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	  
	    // 设置边框  
	    style.setBottomBorderColor(HSSFColor.RED.index);
	    style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    style.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    style.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    style.setFont(font);// 设置字体 
	    
	    int rowIndex = 0;
	    for (List<String> data : datas) {
	    	// 创建Excel的sheet的一行 
		    HSSFRow row = sheet.createRow(rowIndex);
		    int cellIndex = 0;
		    for (String v : data) {
				if (StrKit.notBlank(v) && v.startsWith("<img>") && v.endsWith("</img>")) { // 含图片
					row.setHeight((short) 950);// 设定行的高度 
					v = v.replace("<img>", "").replace("</img>", "");
					ByteArrayOutputStream byteArrayOut = null;
					BufferedImage bufferImg = null;
					try {
						byteArrayOut = new ByteArrayOutputStream();
						bufferImg = ImageIO.read(new URL(v));
						ImageIO.write(bufferImg, "jpg", byteArrayOut);
					} catch (IOException e) {
						e.printStackTrace();
					}
					// anchor主要用于设置图片的属性
					HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) cellIndex, rowIndex, (short) (cellIndex + 1), rowIndex + 1);
					anchor.setAnchorType(3);
					// 插入图片
					patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
				} else {
					 // 创建一个Excel的单元格  
				    HSSFCell cell = row.createCell(cellIndex);
				    
				    // 给Excel的单元格设置样式和赋值  
				    if (rowIndex == 0) {
				    	cell.setCellStyle(style);
				    }
				    cell.setCellValue(v);
				}
			    cellIndex++;
		    }
		    rowIndex++;
	    }
	    
	    try {
			wb.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public HSSFCellStyle getBoldStyle(HSSFWorkbook wb) {
		HSSFFont font = wb.createFont();
		font.setFontName("Verdana");
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFCellStyle style = wb.createCellStyle();
		style.setFont(font);// 设置字体
		return style;
	}
	
	private static String getValue(Cell cell) {
		String result = cell.toString();
//        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
//        	result = String.valueOf(cell.getBooleanCellValue());
//        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
//        	result = String.valueOf(cell.getNumericCellValue());
//        } else {
//        	result = String.valueOf(cell.getStringCellValue());
//        }
        return result == null ? "" : result;
    }
	
	
}
