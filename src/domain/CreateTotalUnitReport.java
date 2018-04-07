package domain;

import java.awt.event.WindowEvent;
import  java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import  org.apache.poi.hssf.usermodel.HSSFSheet;
import  org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.PrintSetup;
import  org.apache.poi.hssf.usermodel.HSSFRow;
import  org.apache.poi.hssf.usermodel.HSSFCell;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

public class CreateTotalUnitReport extends AbstractReport{
	String startETD;
	String endETD;
	ArrayList<DataReportTotalUnit> data = new ArrayList<DataReportTotalUnit>();
	HSSFRow row;
	int cntData = 0;
	int cntColumn = 4;
	int subtotalPRODUCT;
	int totalunit;

	JPanel successPanel;
	JPanel errorPanel;

	public CreateTotalUnitReport(ArrayList<DataReportTotalUnit> data, String startETD, String endETD){
		this.data = data;
		this.startETD = startETD;
		this.endETD = endETD;
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
				reportTotalUnitHeaderCellStyle(workbook, rowhead, sheet);
				rowhead.getCell(0).setCellValue("PREDICTION OF TOTAL UNITS REPORT FROM " + startETD + " TO " + endETD);

				if(data.size() > 0){
					//build the sub-vendor field
					row = sheet.createRow((short)2);
					row.createCell(0).setCellValue("Vendor: " + data.get(0).getVendor());

					row = sheet.createRow((short)3);
					row.createCell(0).setCellValue("PRODUCT");
					reportTotalUnitRowWithBorderCellStyle(workbook, row, sheet, 0, false, false, false, false, true, false);
					row.createCell(1).setCellValue("QUANTITY");
					reportTotalUnitRowWithBorderCellStyle(workbook, row, sheet, 1, false, false, false, false, false, true);
					row.createCell(2).setCellValue("TOTAL UNITS");
					reportTotalUnitRowWithBorderCellStyle(workbook, row, sheet, 2, false, false, false, false, false, true);

					while(cntData < data.size()){
						row = sheet.createRow((short)cntColumn);
						row.createCell(0).setCellValue(data.get(cntData).getPRODUCT());
						row.createCell(1).setCellValue(nf.format(Integer.parseInt(data.get(cntData).getQuantity())));
						reportTotalUnitRowWithBorderCellStyle(workbook, row, sheet, 1, false, false, false, false, false, true);
						subtotalPRODUCT = subtotalPRODUCT + Integer.parseInt(data.get(cntData).getQuantity());

						//create another sub-vendor field if the vendor is different
						if(cntData+1 < data.size() && !data.get(cntData).getVendor().equals(data.get(cntData+1).getVendor())){
							//print out the sub-total quantity on each vendor 
							row.createCell(2).setCellValue(nf.format(subtotalPRODUCT));
							reportTotalUnitRowWithBorderCellStyle(workbook, row, sheet, 2, false, false, false, false, false, true);
							totalunit = totalunit + subtotalPRODUCT;
							subtotalPRODUCT = 0;

							cntColumn += 2;
							row = sheet.createRow((short)cntColumn);
							row.createCell(0).setCellValue("Vendor: " + data.get(cntData+1).getVendor());
							cntColumn++;
							row = sheet.createRow((short)cntColumn);
							row.createCell(0).setCellValue("PRODUCT");
							reportTotalUnitRowWithBorderCellStyle(workbook, row, sheet, 0, false, false, false, false, true, false);
							row.createCell(1).setCellValue("QUANTITY");
							reportTotalUnitRowWithBorderCellStyle(workbook, row, sheet, 1, false, false, false, false, false, true);
							row.createCell(2).setCellValue("TOTAL UNITS");
							reportTotalUnitRowWithBorderCellStyle(workbook, row, sheet, 2, false, false, false, false, false, true);
						}
						cntData++;
						cntColumn++;
					}
					row.createCell(2).setCellValue(nf.format(subtotalPRODUCT));
					reportTotalUnitRowWithBorderCellStyle(workbook, row, sheet, 2, false, false, false, false, false, true);
					totalunit = totalunit + subtotalPRODUCT;
					subtotalPRODUCT = 0;

					//print all the total unit in the excel
					cntColumn++;
					row = sheet.createRow((short)cntColumn);
					row.createCell(1).setCellValue("TOTAL UNITS:");
					reportTotalUnitRowWithBorderCellStyle(workbook, row, sheet, 1, false, false, false, false, false, true);
					row.createCell(2).setCellValue(nf.format(totalunit));
					reportTotalUnitRowWithBorderCellStyle(workbook, row, sheet, 2, false, false, false, false, false, true);
					totalunit = 0;

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
			System.out.println(ex);

		}
	}
}

