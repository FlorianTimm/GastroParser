package de.florian_timm.gastroparser.gui;

import javax.swing.JDialog;
import de.florian_timm.gastroparser.GarantiePreisGUI;
import de.florian_timm.gastroparser.entity.Garantiepreis;
import de.florian_timm.gastroparser.entity.Produkt;
import de.florian_timm.gastroparser.ordner.ProduktOrdner;

public class GarantiePreisTable extends AbstractDataTable {
	private static final long serialVersionUID = 1L;
	private Produkt[] produkte;

	public GarantiePreisTable() {
		this.dataChanged();
	}

	@Override
	protected void doppelklickAuf(int row, int col) {
		JDialog jd = new GarantiePreisGUI(null, produkte[row]);
		jd.setVisible(true);
	}

	@Override
	protected String[] getColumns() {
		String[] columns = { "Name", "Durchschnitt", "Garantiepreis", "gültig bis" };
		return columns;
	}

	protected Object[][] getData() {
		produkte = ProduktOrdner.getInstanz().getProdukte();
		Object[][] data = new Object[produkte.length][getColumns().length];
		for (int i = 0; i < data.length; i++) {
			data[i][0] = produkte[i].getBezeichnung();
			data[i][1] = produkte[i].getDurchschnittsPreis();
			Garantiepreis garantiePreis = produkte[i].getGarantiePreis();
			if (garantiePreis == null) continue;
			data[i][2] = garantiePreis.getPreis();
			data[i][2] = garantiePreis.getBis();
		}
		return data;
	}

	@Override
	protected void afterDataChanged() {
		this.getColumnModel().getColumn(1).setCellRenderer(new DeciRenderer(DeciRenderer.EURO));
		this.getColumnModel().getColumn(2).setCellRenderer(new DeciRenderer(DeciRenderer.EURO));
	}

}
