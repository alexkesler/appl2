package org.kesler.appl2.gui.reestr.export;

import java.util.List;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
// import org.apache.poi.xssf.util.CellReference;
// import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.alee.laf.filechooser.WebFileChooser;

import org.kesler.appl2.logic.Reception;
import org.kesler.appl2.logic.reception.ReceptionsModel;
import org.kesler.appl2.gui.reestr.column.ReestrColumn;
import org.kesler.appl2.gui.reestr.column.ReestrColumns;

public class ReestrExporter {

	public static void exportReestr(List <Reception> receptions) {

		List<ReestrColumn> reestrColumns = ReestrColumns.getInstance().getActiveColumns();

//		List<Reception> receptions = ReceptionsModel.getInstance().getFilteredReceptions();


		SXSSFWorkbook wb = new SXSSFWorkbook(100);
		Sheet sh = wb.createSheet();
		
		// Впечатываем наименования

		Row titleRow = sh.createRow(0);
		for (int colnum = 0; colnum < reestrColumns.size(); colnum++) {
			int width = reestrColumns.get(colnum).getWidth();
			sh.setColumnWidth(colnum, 256*width);
			Cell cell = titleRow.createCell(colnum);
			String value = reestrColumns.get(colnum).getName();
			cell.setCellValue(value);
			CellStyle cellStyle = wb.createCellStyle();
			cellStyle.setWrapText(true);
			cellStyle.setBorderTop(CellStyle.BORDER_THIN);
			cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
			cellStyle.setBorderRight(CellStyle.BORDER_THIN);
			cellStyle.setBorderBottom(CellStyle.BORDER_THIN);

			cell.setCellStyle(cellStyle);	
		}

		// Впечатываем значения

		for (int rownum = 0; rownum < receptions.size(); rownum++) {
			Row row = sh.createRow(rownum+1);
			for (int colnum = 0; colnum < reestrColumns.size(); colnum++) {
				Cell cell = row.createCell(colnum);
				String value = reestrColumns.get(colnum).getValue(receptions.get(rownum)).toString();
				cell.setCellValue(value);

				CellStyle cellStyle = wb.createCellStyle();
				cellStyle.setWrapText(true);
				cellStyle.setBorderTop(CellStyle.BORDER_THIN);
				cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
				cellStyle.setBorderRight(CellStyle.BORDER_THIN);
				cellStyle.setBorderBottom(CellStyle.BORDER_THIN);

				cell.setCellStyle(cellStyle);	
				}
		}

		WebFileChooser fileChooser = new WebFileChooser();
		//fileChooser.setMultiSelectionEnabled(false);
		FileFilter fileFilter = new FileNameExtensionFilter("Excel file", "xlsx");
		fileChooser.setFileFilter(fileFilter);

		File file = null;

		file = fileChooser.showSaveDialog();

		if (file == null) {
			return;
		}

		String filePath = file.getPath();
		if(filePath.indexOf(".xlsx") == -1) {
			filePath += ".xlsx";
			file = new File(filePath);
		}

		if (file.exists()) {

		} 

		try {
			file.createNewFile();
			FileOutputStream out = new FileOutputStream(file);
			wb.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// wb.dispose();

	}

}