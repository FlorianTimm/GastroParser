package de.florian_timm.gastroparser;

import java.awt.Container;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class Parser implements Runnable {
	List<String[]> liste;
	String[] columns;
	private File[] files;
	private GUI frame;
	
	final static Pattern PT_ARTIKEL = 
			Pattern.compile("\\h{2}(\\d{6}\\.\\d)\\h(\\d{13})\\h+(.{0,45})([A-Z]{2})\\h+(\\d+,\\d{3})\\h+(\\d+,?\\d*)\\h*(\\d+,\\d{2})\\h*(\\d+)\\h+(\\d+,\\d{2})\\h+([AB])\\h+(\\d+,\\d{3})(\\h{5}\\w)?");
	final static Pattern PT_DATUM = 
			Pattern.compile("((0[1-9]|[12]\\d|3[01])\\.(0\\d|1[0-2])\\.[12]\\d{3})\\h(([01]\\d|2[0-4]|):[0-5]\\d)");
	
	public Parser(GUI frame, File[] files) {
		liste = new ArrayList<String[]>();
		columns = new String[]{"Datum", "Uhrzeit", "ArtNr", "EAN", "Bezeichnung", 
				"Pack", "Einzelpreis", "Inhalt", "Preis", "Menge", 
				"Gesamtpreis",  "MwSt", "Stückpreis","Werbung"};
		this.files = files;
		this.frame = frame;
	}
	
	public void run() {
		for (int i = 0; i < files.length; i++) {
			parse(files[i]);
			this.frame.setStatus(i);
		}
		
	}
	
	private void parse(File file) {
		try {
			RandomAccessFile raf = new RandomAccessFile (file, "r");
			PDFParser pdfp = new PDFParser(raf);
			pdfp.parse();
            COSDocument cosDoc = pdfp.getDocument();
            PDFTextStripper pdfStripper = new PDFTextStripper();
            PDDocument pdDoc = new PDDocument(cosDoc);
            String parsedText = pdfStripper.getText(pdDoc);
            //System.out.println(parsedText);
            Matcher d = PT_DATUM.matcher(parsedText);
        	String datum = "";
        	String zeit = "";
            if (d.find()) {
	            datum = d.group(1);
	            zeit = d.group(4);
            }
            
            Matcher m = PT_ARTIKEL.matcher(parsedText);
            
            
            while (m.find()) {
            	String[] zeile = new String[columns.length];
            	for (int i = 1; i < m.groupCount(); i++) {
            		zeile[0] = datum;
            		zeile[1] = zeit;
	                zeile[i+1] = m.group(i);
            	}
            	liste.add(zeile);
            }           
            cosDoc.close();
            
            
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void showTable (JFrame frame) {
		String[][] data = new String[liste.size()][columns.length];
        for (int i = 0; i < liste.size(); i++) {
        	data[i] = liste.get(i);
        }
		JDialog dialog = new JDialog(frame);
		Container cp = dialog.getContentPane();
		JTable jt = new JTable(data, columns);
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jt.getModel());
		jt.setRowSorter(sorter);
		cp.add(new JScrollPane(jt));
		dialog.pack();
		dialog.setVisible(true);
	}
}
