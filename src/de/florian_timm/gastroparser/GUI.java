package de.florian_timm.gastroparser;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.florian_timm.gastroparser.entity.Rechnungsposten;

public class GUI extends JFrame implements ParserListener {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new GUI();
	}

	private JButton jbOpen;
	private JProgressBar jpb;

	public GUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());

		jbOpen = new JButton("Dateien öffnen");
		jbOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				openFolder();
			}
		});
		cp.add(jbOpen, BorderLayout.NORTH);

		jpb = new JProgressBar();
		jpb.setMaximum(100);
		cp.add(jpb, BorderLayout.SOUTH);

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void openFolder() {

		JFileChooser jfc = new JFileChooser("/mnt/ssd_daten/GastroParser/Rechnungen/");
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc.setMultiSelectionEnabled(true);
		FileFilter filter = new FileNameExtensionFilter("PDF-Dateien (*.pdf)", "pdf");
		jfc.setFileFilter(filter);
		int returnVal = jfc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			jbOpen.setEnabled(false);
			jpb.setValue(0);
			MetroParser p = new MetroParser(this, jfc.getSelectedFiles());
			Thread t = new Thread(p);
			t.start();	
		}
	}

	public void setStatus(int status) {
		  this.jpb.setValue(status);
	}

	public void readyParser(ArrayList<Rechnungsposten> posten) {
		new TableDialog(this, posten);
		jbOpen.setEnabled(true);
		this.setCursor(Cursor.getDefaultCursor());
	}
}
