package de.florian_timm.gastroparser.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import de.florian_timm.gastroparser.ordner.Informer;
import de.florian_timm.gastroparser.ordner.Listener;

public abstract class AbstractDataTable extends JTable implements Listener, MouseListener {
	private static final long serialVersionUID = 1L;

	public AbstractDataTable() {
		super();
		Informer.get().addListener(this);
	}

	public void dataChanged() {

		String[] columns = getColumns();
		String[][] data = getData();
		TableModel model = new DefaultTableModel(data, columns) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int x, int y) {
				return false;
			}
		};
		this.setModel(model);
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		this.setRowSorter(sorter);
		this.addMouseListener(this);
	}

	protected abstract String[][] getData();

	protected abstract String[] getColumns();

	@Override
	public void mouseClicked(MouseEvent e) {

		int trow = this.rowAtPoint(e.getPoint());
		int row = this.convertRowIndexToModel(trow);
		this.setRowSelectionInterval(row, row);

		if (this.getSelectedRow() == -1)
			return;

		// Doppelklick mit Links
		if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			int col = this.columnAtPoint(e.getPoint());
			doppelklickAuf(row, col);
		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent event) {

	}

	protected abstract void doppelklickAuf(int row, int col);

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

}
