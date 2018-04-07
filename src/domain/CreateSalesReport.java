package domain;

import java.awt.event.WindowEvent;
import  java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import  org.apache.poi.hssf.usermodel.HSSFSheet;
import  org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import  org.apache.poi.hssf.usermodel.HSSFRow;
import  org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

public class CreateSalesReport extends AbstractReport{
	String startETD;
	String endETD;
	ArrayList<DataReportSales> data = new ArrayList<DataReportSales>();
	String [] monthArray;
	int cntmonth;
	int cntData = 0;
	int cntColumn = 4;
	double columnSubTotal = 0;
	double columnTotal = 0;
	double [] rowSubTotal;
	double [] rowTotal;
	double Total = 0;
	HSSFRow row;

	JPanel successPanel;

	public CreateSalesReport(ArrayList<DataReportSales> data, String startETD, String endETD, String[] monthArray, int cntmonth){
		this.data = data;
		this.startETD = startETD;
		this.endETD = endETD;
		this.monthArray = monthArray;
		this.cntmonth = cntmonth;
		rowSubTotal = new double[cntmonth];
		rowTotal = new double[cntmonth];
		create();
	}

	private void create() {
		// parent component of the dialog
		JFrame parentFrame = new JFrame();
		int finalColumn = cntmonth + 2;

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");   
		
		FileSystemView fsv = FileSystemView.getFileSystemView();
		fileChooser.setCurrentDirectory(fsv.getHomeDirectory());  

		int userSelection = fileChooser.showSaveDialog(parentFrame);
		
		NumberFormat nf = new DecimalFormat("###,###,###,###,###");

		try {
			if (userSelection == JFileChooser.APPROVE_OPTION) {
				File fileToSave = fileChooser.getSelectedFile();
				System.out.println("Save as file: " + fileToSave.getAbsolutePath());

				//Build excel file
				String filename = fileToSave.getAbsolutePath() + ".xls" ;
				HSSFWorkbook workbook = new HSSFWorkbook();
				HSSFSheet sheet = workbook.createSheet("FirstSheet");  
				sheet.getPrintSetup().setPaperSize(PrintSetup.A4_PAPERSIZE);
				sheet.getPrintSetup().setLandscape(true);
				sheet.setAutobreaks(true);
				sheet.setFitToPage(true);
				sheet.getPrintSetup().setFitWidth((short) 1);
				sheet.getPrintSetup().setFitHeight((short) 0);
				//create the title of excel file
				HSSFRow rowhead = sheet.createRow((short)0);
				for(int i = 0; i < cntmonth+3; i++){
					rowhead.createCell(i);
				}
				rowhead.getCell(0).setCellValue("Prediction of Sales From " + startETD + " To " + endETD);
				headerCellStyle(workbook, rowhead, sheet, cntmonth);
				
				HSSFRow row1 = sheet.createRow((short)1);
				row1.createCell(0).setCellValue("VENDOR");
				row1.createCell(1).setCellValue("PRODUCT");
				for(int i = 0; i <= cntmonth; i++){
					row1.createCell(i+2).setCellValue(monthArray[i]);
				}
				row1.createCell(finalColumn).setCellValue("TOTAL");
				row1CellStyle(workbook, row1, sheet, cntmonth);
				
				row = sheet.createRow((short)2);
				row.createCell(0);
				rowWithBorderCellStyle(workbook, row, sheet, 0, false, false, true, true);
				row.createCell(finalColumn);
				rowWithBorderCellStyle(workbook, row, sheet, finalColumn, false, false, false, true);
				
				if(data.size() > 0){
					row = sheet.createRow((short)3);
					row.createCell(0).setCellValue(data.get(0).getVendor());
					rowWithBorderCellStyle(workbook, row, sheet, 0, false, false, true, true);
					row.createCell(finalColumn);
					rowWithBorderCellStyle(workbook, row, sheet, finalColumn, false, false, false, true);
					row = sheet.createRow((short)4);
					row.createCell(0);
					rowWithBorderCellStyle(workbook, row, sheet, 0, false, false, true, true);
					row.createCell(1).setCellValue(data.get(cntData).getPRODUCT());
					
					while(cntData < data.size()){
						for(int i = 0; i < cntmonth; i++){
							row = sheet.getRow((short)1);
							//System.out.println(row.getCell(i+1));
							if((data.get(cntData).getOrderMonth() + "/" + data.get(cntData).getOrderYear()).equals(row.getCell(i+2).toString())){
								row = sheet.getRow((short)cntColumn);
								row.createCell(i+2).setCellValue(nf.format(data.get(cntData).getMonthSales()));
								rowWithBorderCellStyle(workbook, row, sheet, i+2, false, false, false, false);
								columnSubTotal = columnSubTotal + data.get(cntData).getMonthSales();
								rowSubTotal[i] = rowSubTotal[i] + data.get(cntData).getMonthSales();
								Total = Total + data.get(cntData).getMonthSales();
							}else{
								row = sheet.getRow((short)cntColumn);
								if(row.getCell(i+2) == (null)){
									row.createCell(i+2).setCellValue("-");
									rowWithBorderCellStyle(workbook, row, sheet, i+2, false, false, false, false);
								}
							}
						}
						
						if(cntData+1 < data.size() && !data.get(cntData).getPRODUCT().equals((data.get(cntData+1).getPRODUCT()))){
							row = sheet.getRow((short)cntColumn);
							row.createCell(finalColumn).setCellValue(nf.format(columnSubTotal));
							rowWithBorderCellStyle(workbook, row, sheet, finalColumn, false, false, false, true);
							columnTotal = columnTotal + columnSubTotal;
							columnSubTotal = 0;
							
							//create another sub-vendor field if the vendor is different
							if(cntData+1 < data.size() && !data.get(cntData).getVendor().equals(data.get(cntData+1).getVendor())){
								//print out the sub-total quantity on each vendor
								cntColumn++;
								row = sheet.createRow((short)cntColumn);
								row.createCell(0);
								rowWithBorderCellStyle(workbook, row, sheet, 0, false, true, true, true);
								row.createCell(1).setCellValue("Sub-Total: ");
								rowWithBorderCellStyle(workbook, row, sheet, 1, true, true, true, false);
								
								//calculate the rowsubtotal
								for(int i = 0; i < cntmonth; i++){
									if(rowSubTotal[i] != 0){
										row.createCell(i+2).setCellValue(nf.format(rowSubTotal[i]));
										rowWithBorderCellStyle(workbook, row, sheet, i+2, true, true, false, false);
									}else{
										row.createCell(i+2).setCellValue("-");
										rowWithBorderCellStyle(workbook, row, sheet, i+2, true, true, false, false);
									}
									rowTotal[i] = rowTotal[i] + rowSubTotal[i];
									rowSubTotal[i] = 0;
								}
								
								row = sheet.getRow((short)cntColumn);
								row.createCell(finalColumn).setCellValue(nf.format(columnTotal));
								rowWithBorderCellStyle(workbook, row, sheet, finalColumn, true, true, false, true);
								columnTotal = 0;
								
								cntColumn++;
								row = sheet.createRow((short)cntColumn);
								row.createCell(0);
								rowWithBorderCellStyle(workbook, row, sheet, 0, false, false, true, true);
								row.createCell(finalColumn);
								rowWithBorderCellStyle(workbook, row, sheet, finalColumn, false, false, false, true);
								
								cntColumn++;
								row = sheet.createRow((short)cntColumn);
								row.createCell(0).setCellValue(data.get(cntData+1).getVendor());
								rowWithBorderCellStyle(workbook, row, sheet, 0, false, false, true, true);
								row.createCell(finalColumn);
								rowWithBorderCellStyle(workbook, row, sheet, finalColumn, false, false, false, true);
							}
							cntColumn++;
							row = sheet.createRow((short)cntColumn);
							row.createCell(0);
							rowWithBorderCellStyle(workbook, row, sheet, 0, false, false, true, true);
							row.createCell(1).setCellValue(data.get(cntData+1).getPRODUCT());
						}
						cntData++;
					}
					
					row = sheet.getRow((short)cntColumn);
					row.createCell(finalColumn).setCellValue(nf.format(columnSubTotal));
					rowWithBorderCellStyle(workbook, row, sheet, finalColumn, false, false, false, true);
					columnTotal = columnTotal + columnSubTotal;
					
					cntColumn++;
					row = sheet.createRow((short)cntColumn);
					row.createCell(0);
					rowWithBorderCellStyle(workbook, row, sheet, 0, false, true, true, true);
					row.createCell(1).setCellValue("Sub-Total: ");
					rowWithBorderCellStyle(workbook, row, sheet, 1, true, true, true, false);
					
					//calculate the rowsubtotal
					for(int i = 0; i < cntmonth; i++){
						if(rowSubTotal[i] != 0){
							row.createCell(i+2).setCellValue(nf.format(rowSubTotal[i]));
							rowWithBorderCellStyle(workbook, row, sheet, i+2, true, true, false, false);
						}else{
							row.createCell(i+2).setCellValue("-");
							rowWithBorderCellStyle(workbook, row, sheet, i+2, true, true, false, false);
						}
						rowTotal[i] = rowTotal[i] + rowSubTotal[i];
						rowSubTotal[i] = 0;
					}
					
					row = sheet.getRow((short)cntColumn);
					row.createCell(finalColumn).setCellValue(nf.format(columnTotal));
					rowWithBorderCellStyle(workbook, row, sheet, finalColumn, true, true, false, true);
					columnTotal = 0;
					
					cntColumn++;
					row = sheet.createRow((short)cntColumn);
					row.createCell(0);
					rowWithBorderCellStyle(workbook, row, sheet, 0, true, false, true, true);
					row.createCell(1);
					rowWithBorderCellStyle(workbook, row, sheet, 1, true, false, true, false);
					row.createCell(finalColumn);
					rowWithBorderCellStyle(workbook, row, sheet, finalColumn, true, false, false, true);
					
					cntColumn++;
					row = sheet.createRow((short)cntColumn);
					row.createCell(0);
					rowWithBorderCellStyle(workbook, row, sheet, 0, false, true, true, true);
					row.createCell(1).setCellValue("Total: ");
					rowWithBorderCellStyle(workbook, row, sheet, 1, false, true, true, false);
					
					//calculate the rowtotal
					for(int i = 0; i < cntmonth; i++){
						if(rowTotal[i] != 0){
							row.createCell(i+2).setCellValue(nf.format(rowTotal[i]));
							rowWithBorderCellStyle(workbook, row, sheet, i+2, false, true, false, false);
						}else{
							row.createCell(i+2).setCellValue("-");
							rowWithBorderCellStyle(workbook, row, sheet, i+2, false, true, false, false);
						}
						rowTotal[i] = 0;
					}
					
					row = sheet.getRow((short)cntColumn);
					row.createCell(finalColumn).setCellValue(nf.format(Total));
					rowWithBorderCellStyle(workbook, row, sheet, finalColumn, false, true, false, true);
					
					
					
					//output the excel and close it
					FileOutputStream fileOut = new FileOutputStream(filename);
					workbook.write(fileOut);
					fileOut.close();
					JOptionPane.showMessageDialog(
							successPanel,
							"Your excel file has been generated!",
							"Success", JOptionPane.INFORMATION_MESSAGE);
					//System.out.println("Your excel file has been generated!");
					cntData = 0;
					cntColumn = 0;
				}else{
					JOptionPane.showMessageDialog(
							successPanel,
							"There is no data in the excel",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		} catch ( Exception ex ) {
			JOptionPane.showMessageDialog(
					successPanel,
					ex,
					"Error", JOptionPane.ERROR_MESSAGE);
			System.out.println(ex);

		}		
	}
}
