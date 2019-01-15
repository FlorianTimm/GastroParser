package de.florian_timm.gastroparser.gui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JDialog;
import javax.swing.JScrollPane;

import de.florian_timm.gastroparser.entity.Lieferant;
import de.florian_timm.gastroparser.entity.Rechnung;

public class RechnungenTable extends AbstractDataTable {
	private static final long serialVersionUID = 1L;
	private Lieferant lieferant;
	private Rechnung[] rechnungen;
	
	/**
	 * @param lieferant
	 */
	public RechnungenTable(Lieferant lieferant) {
		this.lieferant = lieferant;
		this.dataChanged();
	}	
	
	@Override
	protected void doppelklickAuf(int row, int col) {
		System.out.println("Öffne Rechnung " + rechnungen[row].getRechnungsnummer());
		JDialog jd = new JDialog();
		jd.setTitle(rechnungen[row].getRechnungsnummer());
		Container cp = jd.getContentPane();
		cp.setLayout(new BorderLayout());
		RechnungsPostenTable tab = new RechnungsPostenTable(rechnungen[row]);
		cp.add(new JScrollPane(tab), BorderLayout.CENTER);
		jd.pack();
		jd.setVisible(true);
	}

	@Override
	protected String[] getColumns() {
		String[] columns = { "Name", "Rechnungsnummer", "Anzahl Posten", "Summe" };
		return columns;
	}

	protected Object[][] getData() {
		rechnungen = lieferant.getRechnungen();
		Object[][] data = new Object[rechnungen.length][getColumns().length];
		for (int i = 0; i < data.length; i++) {
			data[i][0] = rechnungen[i].getDatumUhrzeit().toString();
			data[i][1] = rechnungen[i].getRechnungsnummer();
			data[i][2] = rechnungen[i].getAnzahlPosten();
			data[i][3] = rechnungen[i].getGesamtSumme();
		}
		return data;
	}

	@Override
	protected void afterDataChanged() {
		this.getColumnModel().getColumn(3).setCellRenderer(new DeciRenderer(DeciRenderer.EURO));
	}

}
