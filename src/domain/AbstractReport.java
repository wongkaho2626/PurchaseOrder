package domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

public abstract class AbstractReport {
	HashMap<String, HSSFCellStyle> styleMap = new HashMap<String, HSSFCellStyle>();
	
	protected void headerCellStyle(HSSFWorkbook workbook, HSSFRow rowhead, HSSFSheet sheet, int cntmonth){
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,cntmonth+2));
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setWrapText(true);
		for(int i = 0; i < cntmonth+3; i++){
			rowhead.getCell(i).setCellStyle(cellStyle);
		}
	}
	
	//set sales report cell style on 2 row
	protected void row1CellStyle(HSSFWorkbook workbook, HSSFRow row1, HSSFSheet sheet, int cntmonth){
		HSSFCellStyle cellStyle0 = workbook.createCellStyle();
		cellStyle0.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle0.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle0.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle0.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle0.setAlignment(CellStyle.ALIGN_LEFT);
		cellStyle0.setWrapText(true);
		row1.getCell(0).setCellStyle(cellStyle0);
		HSSFCellStyle cellStyle1 = workbook.createCellStyle();
		cellStyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle1.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle1.setAlignment(CellStyle.ALIGN_LEFT);
		cellStyle1.setWrapText(true);
		row1.getCell(1).setCellStyle(cellStyle1);
		HSSFCellStyle cellStyle2 = workbook.createCellStyle();
		cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle2.setAlignment(CellStyle.ALIGN_LEFT);
		cellStyle2.setWrapText(true);
		for(int i = 2; i < cntmonth+2; i++){
			row1.getCell(i).setCellStyle(cellStyle2);
		}
		HSSFCellStyle cellStyleLast = workbook.createCellStyle();
		cellStyleLast.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyleLast.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyleLast.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyleLast.setAlignment(CellStyle.ALIGN_LEFT);
		cellStyleLast.setWrapText(true);
		row1.getCell(cntmonth+2).setCellStyle(cellStyleLast);
	}
	
	
	protected void rowWithBorderCellStyle(HSSFWorkbook workbook, HSSFRow row, HSSFSheet sheet, int column, boolean top, boolean bottom, boolean left, boolean right){
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		if(top){
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		}
		if(bottom){
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		}
		if(left){
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		}
		if(right){
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		}
		cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		cellStyle.setWrapText(true);
		row.getCell(column).setCellStyle(cellStyle);
//		sheet.autoSizeColumn(column);
		sheet.setColumnWidth(column, 3000);
	}
	
	protected void reportTotalUnitHeaderCellStyle(HSSFWorkbook workbook, HSSFRow rowhead, HSSFSheet sheet){
		for(int i = 0; i < 3; i++){
			rowhead.createCell(i);
		}
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,2));
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setWrapText(true);
		for(int i = 0; i < 3; i++){
			rowhead.getCell(i).setCellStyle(cellStyle);
		}
	}
	
	protected void reportTotalUnitRowWithBorderCellStyle(HSSFWorkbook workbook, HSSFRow row, HSSFSheet sheet, int column, boolean top, boolean bottom, boolean left, boolean right, boolean ALIGN_LEFT, boolean ALIGN_RIGHT){
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		if(top){
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		}
		if(bottom){
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		}
		if(left){
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		}
		if(right){
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		}
		if(ALIGN_LEFT){
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		}
		if(ALIGN_RIGHT){
			cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		}
		cellStyle.setWrapText(true);
		row.getCell(column).setCellStyle(cellStyle);
		sheet.setColumnWidth(column, 8000);
	}
	
	protected void reportDateVendorHeaderCellStyle(HSSFWorkbook workbook, HSSFRow rowhead, HSSFSheet sheet){
		for(int i = 0; i < 8; i++){
			rowhead.createCell(i);
		}
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,7));
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setWrapText(true);
		for(int i = 0; i < 8; i++){
			rowhead.getCell(i).setCellStyle(cellStyle);
		}
	}
		
	protected void reportDateVendorRowWithBorderCellStyle(HSSFWorkbook workbook, HSSFRow row, HSSFSheet sheet, int column, boolean top, boolean bottom, boolean left, boolean right, boolean ALIGN_LEFT, boolean ALIGN_RIGHT, int columnWidth){
		HSSFCellStyle cellStyle;
		CellStyleObject cellStyleObject = new CellStyleObject(top, bottom, left, right, ALIGN_LEFT, ALIGN_RIGHT);
		if(styleMap.containsKey(cellStyleObject.getResult())) {
			cellStyle = styleMap.get(cellStyleObject.getResult());
		}else {
			cellStyle = workbook.createCellStyle();
			if(top){
				cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			}
			if(bottom){
				cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			}
			if(left){
				cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			}
			if(right){
				cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			}
			if(ALIGN_LEFT){
				cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
			}
			if(ALIGN_RIGHT){
				cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
			}
			cellStyle.setWrapText(true);
			styleMap.put(cellStyleObject.getResult(), cellStyle);
		}
		row.getCell(column).setCellStyle(cellStyle);
		sheet.setColumnWidth(column, columnWidth);
	}
	
	protected void reportProductDatabaseHeaderCellStyle(HSSFWorkbook workbook, HSSFRow rowhead, HSSFSheet sheet){
		for(int i = 0; i < 4; i++){
			rowhead.createCell(i);
		}
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setWrapText(true);
		for(int i = 0; i < 4; i++){
			rowhead.getCell(i).setCellStyle(cellStyle);
		}
	}
}
