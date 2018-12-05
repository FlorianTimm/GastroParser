package de.florian_timm.gastroparser.gui;

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

	protected String[][] getData() {
		Rechnungsposten[] rp = rechnung.getRechnungsposten();

		String[][] data = new String[rp.length][getColumns().length];
		for (int i = 0; i < data.length; i++) {
			Rechnungsposten p = rp[i];
			int j = 0;
			data[i][j++] = p.getRechnung().getLieferant().getName();
			data[i][j++] = p.getArtikel().getArtikelnr();
			data[i][j++] = String.format("%13d", p.getArtikel().getEan());
			data[i][j++] = p.getArtikel().getProdukt().getBezeichnung();
			data[i][j++] = String.format("%.3f", p.getPreisProEinheit());
			data[i][j++] = p.getArtikel().getProdukt().getEinheit();
			data[i][j++] = String.format("%.3f", p.getArtikel().getMenge());
			data[i][j++] = String.format("%.3f", p.getMenge());
			data[i][j++] = String.format("%.2f", p.getPreis());
			data[i][j++] = String.format("%.1f", p.getArtikel().getProdukt().getMwst());
			;
		}
		return data;
	}

}
