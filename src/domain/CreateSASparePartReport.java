package domain;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.PrintSetup;

public class CreateSASparePartReport extends AbstractReport {
	private ArrayList<DataReportSASparePart> data = new ArrayList<DataReportSASparePart>();
	private String startOrderDate;
	private String endOrderDate;
	HSSFRow row;
	int cntData = 0;
	int cntColumn = 3;

	JPanel successPanel;
	JPanel errorPanel;
	
	public CreateSASparePartReport(ArrayList<DataReportSASparePart> data, String startOrderDate, String endOrderDate) {
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
				reportDateVendorHeaderCellStyle(workbook, rowhead, sheet);
				rowhead.getCell(0).setCellValue("SA Spare Part Report From " + startOrderDate + " to " + endOrderDate);

				if(data.size() > 0){
					//build the sub-vendor field
					row = sheet.createRow((short)2);
					row.createCell(0).setCellValue("PART NO");
					reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 0, false, true, false, false, true, false, 3000, false);
					row.createCell(1).setCellValue("PO#");
					reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 1, false, true, false, false, true, false, 3000, false);
					row.createCell(2).setCellValue("Vendor");
					reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 2, false, true, false, false, true, false, 3000, false);
					row.createCell(3).setCellValue("Order Quantity");
					reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 3, false, true, false, false, false, true, 5000, false);
					row.createCell(4).setCellValue("SA Quantity");
					reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 4, false, true, false, false, false, true, 5000, false);
					row.createCell(5).setCellValue("SA Remain");
					reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 5, false, true, false, false, false, true, 5000, false);

					while(cntData < data.size()){
						row = sheet.createRow((short)cntColumn);
						row.createCell(0).setCellValue(data.get(cntData).getPRODUCT());
						reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 0, false, false, false, false, true, false, 3000, false);
						row.createCell(1).setCellValue(data.get(cntData).getPoNumber());
						reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 1, false, false, false, false, true, false, 3000, false);
						row.createCell(2).setCellValue(data.get(cntData).getVendor());
						reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 2, false, false, false, false, true, false, 3000, false);
						row.createCell(3).setCellValue(nf.format(data.get(cntData).getQuantity()));
						reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 3, false, false, false, false, false, true, 5000, false);
						row.createCell(4).setCellValue(nf.format(data.get(cntData).getSaUnit()));
						reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 4, false, false, false, false, false, true, 5000, false);
						row.createCell(5).setCellValue(nf.format(data.get(cntData).getSAremain()));
						reportDateVendorRowWithBorderCellStyle(workbook, row, sheet, 5, false, false, false, false, false, true, 5000, false);

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
