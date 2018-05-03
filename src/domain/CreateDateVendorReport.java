package domain;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

public class CreateDateVendorReport extends AbstractReport {
	String startOrderDate;
	String endOrderDate;
	String vendor;
	ArrayList<DataReportDateVendor> data = new ArrayList<DataReportDateVendor>();
	HSSFRow row;
	int cntData = 0;
	int cntColumn = 3;

	JPanel successPanel;
	JPanel errorPanel;

	public CreateDateVendorReport(ArrayList<DataReportDateVendor> data, String startOrderDate, String endOrderDate){
		this.data = data;
		this.startOrderDate = startOrderDate;
		this.endOrderDate = endOrderDate;
		create();
	}

	private void create() {
		// parent component of the dialog
		JFrame parentFrame = new JFrame();

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");   
		
		FileSystemView fsv = FileSystemView.getFileSystemView();
		fileChooser.setCurrentDirectory(fsv.getHomeDirectory());  

		int userSelection = fileChooser.showSaveDialog(parentFrame);
		
		NumberFormat nf = new DecimalFormat("###,###,###,###,##0.000");

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
				reportDateVendorHeaderCellStyle(workbook, rowhead, sheet);
				rowhead.getCell(0).setCellValue("Vendor & PO Report (for checking SA purpose) From " + startOrderDate + " to " + endOrderDate);

				if(data.size() > 0){
					//build the sub-vendor field
					row = sheet.createRow((short)2);
					row.createCell(0).setCellValue("PO#");
					reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 0, false, true, false, false, true, false, 3000, false, false);
					row.createCell(1).setCellValue("P/N");
					reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 1, false, true, false, false, true, false, 3000, false, false);
					row.createCell(2).setCellValue("DESCRIPTION");
					reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 2, false, true, false, false, true, false, 9000, false, false);
					row.createCell(3).setCellValue("PRICE");
					reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 3, false, true, false, false, false, true, 3000, false, false);
					row.createCell(4).setCellValue("FIXED COST");
					reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 4, false, true, false, false, false, true, 4000, false, false);
					row.createCell(5).setCellValue("DUTY CODE");
					reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 5, false, true, false, false, false, true, 4000, false, false);
					row.createCell(6).setCellValue("DEPOSIT");
					reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 6, false, true, false, false, false, true, 3000, false, false);
					row.createCell(7).setCellValue("VENDOR");
					reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 7, false, true, false, false, false, true, 3000, false, false);

					while(cntData < data.size()){
						row = sheet.createRow((short)cntColumn);
						if(!data.get(cntData).getsubCode().isEmpty()) {
							String PO = data.get(cntData).getpoNumber() + data.get(cntData).getsubCode();
							row.createCell(0).setCellValue(PO);
						}else {
							int PO = data.get(cntData).getpoNumber();
							row.createCell(0).setCellValue(PO);
						}
						reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 0, false, false, false, false, true, false, 3000, false, false);
						row.createCell(1).setCellValue(data.get(cntData).getPRODUCT());
						reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 1, false, false, false, false, true, false, 3000, false, false);
						row.createCell(2).setCellValue(data.get(cntData).getDESCRIPTION());
						reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 2, false, false, false, false, true, false, 9000, false, false);
						row.createCell(3).setCellValue(data.get(cntData).getprice());
						if(data.get(cntData).getprice() == 0) {
							reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 3, false, false, false, false, false, true, 3000, false, false);
						}else {
							reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 3, false, false, false, false, false, true, 3000, true, false);
						}
						row.createCell(4).setCellValue(Double.parseDouble(data.get(cntData).getfixedcost()));
						if(Double.parseDouble(data.get(cntData).getfixedcost()) == 0) {
							reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 4, false, false, false, false, false, true, 4000, false, false);
						}else {
							reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 4, false, false, false, false, false, true, 4000, true, false);
						}
						row.createCell(5).setCellValue(data.get(cntData).getDUTY_CODE());
						reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 5, false, false, false, false, false, true, 4000, false, false);
						row.createCell(6).setCellValue(Double.parseDouble(data.get(cntData).getdeposit()));
						if(Double.parseDouble(data.get(cntData).getdeposit()) == 0) {
							reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 6, false, false, false, false, false, true, 3000, false, false);
						}else {
							reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 6, false, false, false, false, false, true, 3000, true, false);
						}
						row.createCell(7).setCellValue(data.get(cntData).getVendor());
						reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 7, false, false, false, false, false, true, 3000, false, false);

						cntData++;
						cntColumn++;
					}

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
							errorPanel,
							"There is no data in the excel",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		} catch ( Exception ex ) {
			JOptionPane.showMessageDialog(
					errorPanel,
					ex,
					"Error", JOptionPane.ERROR_MESSAGE);
			System.out.println("EEE:" + ex);

		}
	}
}

