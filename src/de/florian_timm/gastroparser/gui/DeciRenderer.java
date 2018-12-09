package de.florian_timm.gastroparser.gui;

import java.awt.Component;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class DeciRenderer extends DefaultTableCellRenderer {
	final static public DecimalFormat EURO = new DecimalFormat("#.00 €");
	final static public DecimalFormat MWST = new DecimalFormat("#.0 %");
	final static public DecimalFormat KG = new DecimalFormat("#.000");
	private DecimalFormat formatter;
	
	public DeciRenderer() {
		this.formatter = this.EURO;
	}
	
	public DeciRenderer(DecimalFormat formatter) {
		this.formatter = formatter;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		// set Alignment
		cell.setHorizontalAlignment(SwingConstants.RIGHT);

		return cell;
	}
    @Override public void setValue(Object value) {
        setText(value instanceof Double ? formatter.format(value) : "");
    }
}
