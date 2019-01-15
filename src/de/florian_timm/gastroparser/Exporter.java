package de.florian_timm.gastroparser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Exporter implements Runnable {

	private File file;
	private StatusListener gui;
	private Workbook workbook;

	public Exporter(StatusListener gui, File file) {
		this.file = file;
		this.gui = gui;

		workbook = new XSSFWorkbook();
	}

	public void run() {
		String[][] tab = { 
				{ "Zusammenfassung", "SELECT l.lid, l.name, l.ustId,r.*,rp.*,a.*,p.* from rechnungsposten rp " + 
						"left join rechnungen r on rp.rechnung = r.rid " + 
						"left join lieferanten l on r.lieferant = l.lid " + 
						"left join artikel a on rp.artikel = a.aid " + 
						"left join produkte p on a.produkt = p.pid;" }, 
				{ "Lieferanten", "SELECT * FROM lieferanten;" }, 
				{ "Produkte", "SELECT * FROM produkte;" },
				{ "Rechnung", "SELECT * FROM rechnungen;" },
				{ "Artikel", "SELECT * FROM artikel;" },
				{ "Rechnungsposten", "SELECT * FROM rechnungsposten;" }};
		for (String[] t : tab) {
			ResultSet rs = Database.get().execute(t[1]);
			try {
				generateSheet(t[0], rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		safeWorkbook();
	}

	public void generateSheet(String name, ResultSet result) throws SQLException {
		Sheet sheet = workbook.createSheet(name);
		//sheet.setColumnWidth(0, 6000);
		//sheet.setColumnWidth(1, 4000);

		Row header = sheet.createRow(0);

		CellStyle headerStyle = workbook.createCellStyle();
		
		//headerStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
		//headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		// XSSFFont font = ((XSSFWorkbook) workbook).createFont();
		// font.setFontName("Arial");
		// font.setFontHeightInPoints((short) 16);
		// font.setBold(true);
		//headerStyle.setFont(font);
		ResultSetMetaData meta = result.getMetaData();
		int columns = meta.getColumnCount();
		for (int i = 0; i < columns; i++) {
			Cell headerCell = header.createCell(i);
			headerCell.setCellValue(meta.getColumnName(i+1));
			headerCell.setCellStyle(headerStyle);
		}

		CellStyle style = workbook.createCellStyle();
		//style.setWrapText(true);

		int rowN = 1;
		while (result.next()) {
			Row row = sheet.createRow(rowN++);
			for (int c = 0; c < columns; c++) {
				Cell cell = row.createCell(c);
				cell.setCellValue(result.getString(c+1));
				cell.setCellStyle(style);
			}

		}
	}

	private void safeWorkbook() {
		try {
			FileOutputStream outputStream = new FileOutputStream(file);
			workbook.write(outputStream);
			workbook.close();
			gui.setStatus(100);
			gui.ready();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
