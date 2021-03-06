package de.florian_timm.gastroparser.gui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JDialog;
import javax.swing.JScrollPane;

import de.florian_timm.gastroparser.entity.Lieferant;
import de.florian_timm.gastroparser.ordner.LieferantOrdner;

public class LieferantenTable extends AbstractDataTable {
	private static final long serialVersionUID = 1L;
	private Lieferant[] lieferanten;
	

	public LieferantenTable () {
		super();
		this.dataChanged();
	}
	
	@Override
	protected void doppelklickAuf(int row, int col) {
		System.out.println("Öffne Lieferant " + lieferanten[row].getName());
		JDialog jd = new JDialog();
		jd.setTitle(lieferanten[row].getName());
		Container cp = jd.getContentPane();
		cp.setLayout(new BorderLayout());
		RechnungenTable tab = new RechnungenTable(lieferanten[row]);
		cp.add(new JScrollPane(tab), BorderLayout.CENTER);
		jd.pack();
		jd.setVisible(true);
	}

	@Override
	protected String[] getColumns() {
		String[] columns = { "Name", "UstID", "Rechnungen", "Summe" };
		return columns;
	}

	protected Object[][] getData() {
		lieferanten = LieferantOrdner.getInstanz().getLieferanten();
		Object[][] data = new Object[lieferanten.length][getColumns().length];
		for (int i = 0; i < data.length; i++) {
			data[i][0] = lieferanten[i].getName();
			data[i][1] = lieferanten[i].getUstId();
			data[i][2] = lieferanten[i].getAnzahlRechnungen();
			data[i][3] = lieferanten[i].getGesamtSumme();
		}
		return data;
	}

	@Override
	protected void afterDataChanged() {
		this.getColumnModel().getColumn(3).setCellRenderer(new DeciRenderer(DeciRenderer.EURO));
	}

}
