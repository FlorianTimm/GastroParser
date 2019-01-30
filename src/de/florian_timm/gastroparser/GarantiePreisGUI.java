package de.florian_timm.gastroparser;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import de.florian_timm.gastroparser.entity.Garantiepreis;
import de.florian_timm.gastroparser.entity.Lieferant;
import de.florian_timm.gastroparser.entity.Produkt;
import de.florian_timm.gastroparser.ordner.LieferantOrdner;

public class GarantiePreisGUI extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	private Produkt produkt;
	private JTextField preisField;
	private JTextField vonField;
	private JTextField bisField;
	private JComboBox<Lieferant> lieferantField;

	public GarantiePreisGUI(Frame owner, Produkt p) {
		super(owner);
		this.produkt = p;

		this.setTitle(produkt.getBezeichnung());
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());

		JPanel gpPanel = new JPanel();

		JLabel produktLabel = new JLabel(p.getBezeichnung());
		cp.add(produktLabel, BorderLayout.NORTH);
		
		JLabel lieferantLabel = new JLabel("Lieferant");
		lieferantField = new JComboBox<Lieferant>(LieferantOrdner.getInstanz().getLieferanten());

		JLabel preisLabel = new JLabel("Preis");
		preisField = new JTextField();

		JLabel vonLabel = new JLabel("gültig vom");
		vonField = new JTextField(LocalDate.now().toString());

		JLabel bisLabel = new JLabel("gültig bis");
		bisField = new JTextField(LocalDate.now().plusMonths(1).toString());

		JButton okButton = new JButton("Speichern");
		okButton.addActionListener(this);

		GroupLayout layout = new GroupLayout(gpPanel);
		gpPanel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(lieferantLabel).addComponent(preisLabel)
						.addComponent(vonLabel).addComponent(bisLabel))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(lieferantField).addComponent(preisField)
						.addComponent(vonField).addComponent(bisField).addComponent(okButton)));

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lieferantLabel)
						.addComponent(lieferantField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(preisLabel)
						.addComponent(preisField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(vonLabel)
						.addComponent(vonField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(bisLabel)
						.addComponent(bisField))
				.addComponent(okButton));

		cp.add(gpPanel, BorderLayout.CENTER);

		/*RechnungsPostenTable tab = new RechnungsPostenTable(this.produkt);
		cp.add(new JScrollPane(tab), BorderLayout.SOUTH);*/
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		new Garantiepreis(this.produkt,(Lieferant) lieferantField.getSelectedItem(), 
				Float.parseFloat(preisField.getText().replace(",", ".")), LocalDate.parse(vonField.getText()), 
				LocalDate.parse(bisField.getText()));
		this.setVisible(false);

	}

}
