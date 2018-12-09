package de.florian_timm.gastroparser.gui;

import de.florian_timm.gastroparser.entity.Produkt;
import de.florian_timm.gastroparser.entity.Rechnung;
import de.florian_timm.gastroparser.entity.Rechnungsposten;

public class RechnungsPostenTable extends AbstractDataTable {
	private static final long serialVersionUID = 1L;
	private Rechnung rechnung;

	/**
	 * @param lieferant
	 */
	public RechnungsPostenTable(Rechnung rechnung) {
		this.rechnung = rechnung;
		this.dataChanged();
	}

	public RechnungsPostenTable(Produkt produkt) {
		//TODO Implementieren
	}

	@Override
	protected void doppelklickAuf(int row, int col) {
		// TODO Auto-generated method stub

	}

	@Override
	protected String[] getColumns() {
		String[] columns = { "Lieferant", "ArtNr", "EAN", "Bezeichnung", "Preis pro", "Einheit", "Inhalt", "Menge",
				"Gesamtpreis", "MwSt" };
		return columns;
	}

	protected Object[][] getData() {
		Rechnungsposten[] rp = rechnung.getRechnungsposten();

		Object[][] data = new Object[rp.length][getColumns().length];
		for (int i = 0; i < data.length; i++) {
			Rechnungsposten p = rp[i];
			int j = 0;
			data[i][0] = p.getRechnung().getLieferant().getName();
			data[i][1] = p.getArtikel().getArtikelnr();
			data[i][2] = String.format("%13d", p.getArtikel().getEan());
			data[i][3] = p.getArtikel().getProdukt().getBezeichnung();
			data[i][4] = p.getPreisProEinheit();
			data[i][5] = p.getArtikel().getProdukt().getEinheit();
			data[i][6] = p.getArtikel().getMenge();
			data[i][7] = p.getMenge();
			data[i][8] = p.getPreis();
			data[i][9] = p.getArtikel().getProdukt().getMwst();
			;
		}
		return data;
	}

	@Override
	protected void afterDataChanged() {
		this.getColumnModel().getColumn(6).setCellRenderer(new DeciRenderer(DeciRenderer.KG));
		this.getColumnModel().getColumn(7).setCellRenderer(new DeciRenderer(DeciRenderer.KG));
		this.getColumnModel().getColumn(8).setCellRenderer(new DeciRenderer(DeciRenderer.EURO));
		this.getColumnModel().getColumn(9).setCellRenderer(new DeciRenderer(DeciRenderer.MWST));
	}

}
