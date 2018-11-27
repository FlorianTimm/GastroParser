package de.florian_timm.gastroparser;

import java.awt.Container;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class TableDialog extends JDialog {
	private ArrayList<String[]> liste;
	private String[] columns;
	
	public TableDialog(String[] columns, ArrayList<String[]> liste) {
		this.liste = liste;
		this.columns = columns;
	}
	
	public void showTable (JFrame frame) {
		String[][] data = new String[liste.size()][columns.length];
        for (int i = 0; i < liste.size(); i++) {
        	data[i] = liste.get(i);
        }
		JDialog dialog = new JDialog(frame);
		Container cp = dialog.getContentPane();
		JTable jt = new JTable(data, columns);
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jt.getModel());
		jt.setRowSorter(sorter);
		cp.add(new JScrollPane(jt));
		dialog.pack();
		dialog.setVisible(true);
	}
}
