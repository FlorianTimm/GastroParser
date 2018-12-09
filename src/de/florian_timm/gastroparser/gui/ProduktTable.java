package de.florian_timm.gastroparser.gui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JDialog;
import javax.swing.JScrollPane;

import de.florian_timm.gastroparser.entity.Produkt;
import de.florian_timm.gastroparser.ordner.ProduktOrdner;

public class ProduktTable extends AbstractDataTable {
	private static final long serialVersionUID = 1L;
	private Produkt[] produkte;
	
	public ProduktTable() {
		this.dataChanged();
	}	
	
	@Override
	protected void doppelklickAuf(int row, int col) {
		System.out.println("Öffne Produkt " + produkte[row].getBezeichnung());
		JDialog jd = new JDialog();
		jd.setTitle(produkte[row].getBezeichnung());
		Container cp = jd.getContentPane();
		cp.setLayout(new BorderLayout());
		RechnungsPostenTable tab = new RechnungsPostenTable(produkte[row]);
		cp.add(new JScrollPane(tab), BorderLayout.CENTER);
		jd.pack();
		jd.setVisible(true);
	}

	@Override
	protected String[] getColumns() {
		String[] columns = { "Name", "Gesamtmenge", "Anzahl Posten", "Summe" };
		return columns;
	}

	protected String[][] getData() {
		produkte = ProduktOrdner.getInstanz().getProdukte();
		String[][] data = new String[produkte.length][getColumns().length];
		for (int i = 0; i < data.length; i++) {
			data[i][0] = produkte[i].getBezeichnung();
			data[i][1] = String.format("%.2f", produkte[i].getGesamtMenge()) 
					+ " " + produkte[i].getEinheit();
			data[i][2] = String.format("%.2f", produkte[i].getGesamtPreis());
			data[i][3] = String.format("%.2f", produkte[i].getDurchschnittsPreis());
		}
		return data;
	}

}
