package de.florian_timm.gastroparser;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.florian_timm.gastroparser.entity.Rechnungsposten;
import de.florian_timm.gastroparser.gui.LieferantenTable;
import de.florian_timm.gastroparser.gui.ProduktTable;
import de.florian_timm.gastroparser.ordner.Informer;

public class GUI extends JFrame implements ParserListener {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new GUI();
	}

	private JProgressBar jpb;

	public GUI() {
		super("GastroParser");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menu = new JMenuBar();
		JMenu mDatei = new JMenu("Datei");
		JMenuItem mImport = new JMenuItem("Rechnung importieren...");
		mImport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				openFolder();
			}
		});
		JMenuItem mExport = new JMenuItem("Daten exportieren");
		JMenuItem mExit = new JMenuItem("Beenden");
		mDatei.add(mImport);
		mDatei.add(mExport);
		mDatei.addSeparator();
		mDatei.add(mExit);
		menu.add(mDatei);
		JMenu mProdukte = new JMenu("Produkte");
		JMenuItem mPListe = new JMenuItem("Liste...");
		mPListe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JDialog jd = new JDialog();
				jd.setTitle("Produkte");
				Container cp2 = jd.getContentPane();
				cp2.setLayout(new BorderLayout());
				ProduktTable tab = new ProduktTable();
				cp2.add(new JScrollPane(tab), BorderLayout.CENTER);
				jd.pack();
				jd.setVisible(true);
			}
		});
		mProdukte.add(mPListe);
		menu.add(mProdukte);
		this.setJMenuBar(menu);
		
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());
		
		LieferantenTable tabLieferant = new LieferantenTable();
		cp.add(new JScrollPane(tabLieferant), BorderLayout.CENTER);

		jpb = new JProgressBar();
		jpb.setMaximum(100);
		cp.add(jpb, BorderLayout.SOUTH);

		this.setPreferredSize(new Dimension(300,200));
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
		this.setCursor(Cursor.getDefaultCursor());
		Informer.get().informListener();
	}
}
