package de.florian_timm.gastroparser;

import java.awt.Container;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import de.florian_timm.gastroparser.entity.Rechnungsposten;

public class TableDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	public TableDialog(JFrame gui, ArrayList<Rechnungsposten> posten) {
		super(gui);
		String[] columns = {"Lieferant","ArtNr", "EAN", "Bezeichnung", "Preis pro",
				 "Einheit", "Inhalt", "Menge", "Gesamtpreis","MwSt"};
		String[][] data = new String[posten.size()][columns.length];
        for (int i = 0; i < data.length; i++) {
        	Rechnungsposten p = posten.get(i);
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
        	data[i][j++] = String.format("%.1f", p.getArtikel().getProdukt().getMwst());;
        }
		makeTable(columns, data);
	}

	private void makeTable(String[] columns, String[][] data) {
		Container cp = this.getContentPane();
		JTable jt = new JTable(data, columns);
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jt.getModel());
		jt.setRowSorter(sorter);
		cp.add(new JScrollPane(jt));
		this.pack();
		this.setVisible(true);
	}
}
