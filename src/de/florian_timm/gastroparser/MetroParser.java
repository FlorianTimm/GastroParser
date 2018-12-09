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

public class MetroParser extends Parser {
	final private Pattern PT_USTID = Pattern.compile("(DE\\d{9})");

	final private Pattern PT_RECHNR = Pattern.compile("\\/(\\d{3}\\/\\d{1}\\/\\d{1}\\/\\d{4}\\/\\d{6})");

	final private Pattern PT_ARTIKEL = Pattern.compile(
			"\\h{2}(\\d{6}\\.\\d)\\h(\\d{13}|\\h{13})\\h+(.{0,45})([A-Z]{2})\\h+(\\d+,\\d{3})\\h+(\\d+,?\\d*)\\h*(\\d+,\\d{2})\\h*(\\d+)\\h+(\\d+,\\d{2})\\h+([AB])\\h+(\\d+,\\d{3}|\\h+)(\\h{5}\\w)?");
	final private Pattern PT_DATUM = Pattern
			.compile("((0[1-9]|[12]\\d|3[01])\\.(0\\d|1[0-2])\\.[12]\\d{3})\\h(([01]\\d|2[0-4]|):[0-5]\\d)");

	final private Pattern PT_ART_FRISCHEPARADIES = Pattern.compile(
			"\\h+\\d{1,2}\\h(\\d{5})\\h(.{32})\\h+(\\d{1,2},\\d{3})\\hKG\\h+(\\d+,\\d{2,3})\\hEUR\\/\\h+(\\d{1,2})\\h+(KG)\\h+(\\d{1,2},\\d{2})\\h+\\%\\h+(\\d{1,3},\\d{2})");

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
			// System.out.print(parsedText);
			cosDoc.close();

			Matcher u = PT_USTID.matcher(parsedText);
			if (u.find()) {
				System.out.println(u.group(0));
				switch (u.group(0)) {
				case "DE184048859":
					parseMetro(parsedText);
					break;
				case "DE275810857":
					parseFrischeParadies(parsedText);
					break;
				case "DE117979516":
					parseGoettsche(parsedText);
					break;
				case "DE117979330":
					parseBruck(parsedText);
				default:
					System.out.println("Händler nicht unterstützt");
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void parseBruck(String parsedText) {
		// TODO Auto-generated method stub
	}

	private void parseGoettsche(String parsedText) {
		// TODO Auto-generated method stub

	}

	private void parseFrischeParadies(String parsedText) {
		System.out.println(parsedText);
	}

	private void parseMetro(String parsedText) {
		// System.out.println(parsedText);
		Matcher d = PT_DATUM.matcher(parsedText);
		LocalDateTime datum = null;
		if (d.find()) {
			DateTimeFormatter f = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
			datum = LocalDateTime.from(f.parse(d.group(0)));
		}
		Matcher rnr = PT_RECHNR.matcher(parsedText);
		String rechnr = "";
		if (rnr.find()) {
			rechnr = rnr.group(1);
		}
		Lieferant metro = Lieferant.create("Metro", "DE184048859");
		Rechnung rechnung = Rechnung.create(metro, rechnr, datum);

		if (rechnung.getAnzahlPosten() <= 0) {
			Matcher m = PT_ARTIKEL.matcher(parsedText);

			/*
			 * "ArtNr", "EAN", "Bezeichnung", 4"Pack", 5"Einzelpreis", 6"Inhalt", 7"Preis",
			 * 8"Menge", 9"Gesamtpreis", 10"MwSt", 11"Stückpreis","Werbung"
			 */

			while (m.find()) {
				if (m.groupCount() >= 11) {
					try {
						String artnr = m.group(1);
						long ean = 0;
						try {
							ean = Long.parseLong(m.group(2));
						} catch (NumberFormatException e) {
						}
						String bez = m.group(3);
						String einheit = m.group(4);
						double inhalt = number.parse(m.group(6)).doubleValue();
						double menge = number.parse(m.group(8)).doubleValue();
						double preis = 0;
						try {
							preis = number.parse(m.group(11)).doubleValue() * menge * inhalt;
						} catch (ParseException e) {
							preis = number.parse(m.group(9)).doubleValue();
						}
						double mwst = m.group(10).equals("B") ? 7.0 : 19.0;

						Produkt produkt = Produkt.create(bez, einheit, mwst);
						Artikel artikel = new Artikel(produkt, inhalt, artnr, ean);
						Rechnungsposten rp = new Rechnungsposten(rechnung, artikel, menge, preis);
						posten.add(rp);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

}
