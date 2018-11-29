package de.florian_timm.gastroparser;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import de.florian_timm.gastroparser.entity.Artikel;
import de.florian_timm.gastroparser.entity.Lieferant;
import de.florian_timm.gastroparser.entity.Produkt;
import de.florian_timm.gastroparser.entity.Rechnung;
import de.florian_timm.gastroparser.entity.Rechnungsposten;
import de.florian_timm.gastroparser.ordner.*;

public class MetroParser extends Parser {
	final private Pattern PT_ARTIKEL = Pattern.compile(
			"\\h{2}(\\d{6}\\.\\d)\\h(\\d{13})\\h+(.{0,45})([A-Z]{2})\\h+(\\d+,\\d{3})\\h+(\\d+,?\\d*)\\h*(\\d+,\\d{2})\\h*(\\d+)\\h+(\\d+,\\d{2})\\h+([AB])\\h+(\\d+,\\d{3})(\\h{5}\\w)?");
	final private Pattern PT_DATUM = Pattern
			.compile("((0[1-9]|[12]\\d|3[01])\\.(0\\d|1[0-2])\\.[12]\\d{3})\\h(([01]\\d|2[0-4]|):[0-5]\\d)");

	final private NumberFormat number = NumberFormat.getInstance(Locale.GERMAN);

	public MetroParser(ParserListener gui, File[] selectedFiles) {
		super(gui, selectedFiles);
	}

	protected void parse(File file) {
		try {
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			PDFParser pdfp = new PDFParser(raf);
			pdfp.parse();
			COSDocument cosDoc = pdfp.getDocument();
			PDFTextStripper pdfStripper = new PDFTextStripper();
			PDDocument pdDoc = new PDDocument(cosDoc);
			String parsedText = pdfStripper.getText(pdDoc);
			// System.out.println(parsedText);
			Matcher d = PT_DATUM.matcher(parsedText);
			LocalDateTime datum = null;
			if (d.find()) {
				DateTimeFormatter f = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
				datum = LocalDateTime.from(f.parse(d.group(0)));
			}
			Lieferant metro = new Lieferant("Metro");
			LieferantOrdner.getInstanz().addLieferant(metro);
			Rechnung rechnung = new Rechnung(metro, file.getName(), datum);
			metro.addRechnung(rechnung);

			Matcher m = PT_ARTIKEL.matcher(parsedText);

			/*
			 * "ArtNr", "EAN", "Bezeichnung", 4"Pack", 5"Einzelpreis", 6"Inhalt", 7"Preis",
			 * 8"Menge", 9"Gesamtpreis", 10"MwSt", 11"Stückpreis","Werbung"
			 */

			while (m.find()) {
				if (m.groupCount() >= 11) {
					try {
						String artnr = m.group(1);
						long ean = Long.parseLong(m.group(2));
						String bez = m.group(3);
						String einheit = m.group(4);
						double inhalt = number.parse(m.group(6)).doubleValue();
						double menge = number.parse(m.group(8)).doubleValue();
						double preis = number.parse(m.group(11)).doubleValue() * menge * inhalt;
						double mwst = m.group(11).equals("B") ? 7.0 : 19.0;

						Produkt produkt = new Produkt(bez, einheit, mwst);
						Artikel artikel = new Artikel(produkt, menge * inhalt, artnr, ean);
						Rechnungsposten rp = new Rechnungsposten(rechnung, artikel, menge, preis);
						posten.add(rp);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			cosDoc.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
