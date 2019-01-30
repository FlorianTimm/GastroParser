package de.florian_timm.gastroparser.gui;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import de.florian_timm.gastroparser.ordner.Informer;
import de.florian_timm.gastroparser.ordner.Listener;

public abstract class AbstractDataTable extends JTable implements Listener, MouseListener {
	private static final long serialVersionUID = 1L;

	public AbstractDataTable() {
		super();
		this.setFont(this.getFont().deriveFont(this.getFont().getSize() + 4));
		this.setRowHeight(this.getRowHeight() + 8);
		Informer.get().addListener(this);
	}

	public void dataChanged() {

		String[] columns = getColumns();
		Object[][] data = getData();
		TableModel model = new DefaultTableModel(data, columns) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int x, int y) {
				return false;
			}

			@Override
			public java.lang.Class<?> getColumnClass(int c) {
				if (this.getRowCount() > 0 && getValueAt(0, c) != null)
					return getValueAt(0, c).getClass();
				return super.getColumnClass(c);
			}
		};
		this.setModel(model);
		this.setAutoCreateRowSorter(true);
		// TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		// this.setRowSorter(sorter);
		this.addMouseListener(this);
		this.resizeColumns();
		this.afterDataChanged();
	}

	protected abstract void afterDataChanged();

	public void resizeColumns() {
		this.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		int[] cWidth = new int[this.getColumnCount()];

		for (int column = 0; column < this.getColumnCount(); column++) {
			TableColumn tableColumn = this.getColumnModel().getColumn(column);
			cWidth[column] = tableColumn.getMinWidth();
			int maxWidth = tableColumn.getMaxWidth();

			for (int row = 0; row < this.getRowCount(); row++) {
				TableCellRenderer cellRenderer = this.getCellRenderer(row, column);
				Component c = this.prepareRenderer(cellRenderer, row, column);
				int width = c.getPreferredSize().width + this.getIntercellSpacing().width;
				cWidth[column] = Math.max(cWidth[column], width);

				// We've exceeded the maximum width, no need to check other rows

				if (cWidth[column] >= maxWidth) {
					cWidth[column] = maxWidth;
					break;
				}
			}

			tableColumn.setPreferredWidth(cWidth[column]);
		}
	}

	protected abstract Object[][] getData();

	protected abstract String[] getColumns();

	@Override
	public void mouseClicked(MouseEvent e) {

		int row = this.rowAtPoint(e.getPoint());
		this.setRowSelectionInterval(row, row);

		if (this.getSelectedRow() == -1)
			return;

		// Doppelklick mit Links
		if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			int mrow = this.convertRowIndexToModel(row);
			int col = this.columnAtPoint(e.getPoint());
			doppelklickAuf(mrow, col);
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
