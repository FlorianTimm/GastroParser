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
		String[] columns = { "Name", "Gesamtmenge", "Einheit", "Anzahl Posten", "Summe" };
		return columns;
	}

	protected Object[][] getData() {
		produkte = ProduktOrdner.getInstanz().getProdukte();
		Object[][] data = new Object[produkte.length][getColumns().length];
		for (int i = 0; i < data.length; i++) {
			data[i][0] = produkte[i].getBezeichnung();
			data[i][1] = produkte[i].getGesamtMenge();
			data[i][2] = produkte[i].getEinheit();
			data[i][3] = produkte[i].getGesamtPreis();
			data[i][4] = produkte[i].getDurchschnittsPreis();
		}
		return data;
	}

	@Override
	protected void afterDataChanged() {
		this.getColumnModel().getColumn(4).setCellRenderer(new DeciRenderer(DeciRenderer.EURO));
	}

}
