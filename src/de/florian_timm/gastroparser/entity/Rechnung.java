package de.florian_timm.gastroparser.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Rechnung {
	private String rechnungsnummer;
	private LocalDateTime datumUhrzeit;
	private List<Rechnungsposten> liste;
	private Lieferant lieferant;
	
	/**
	 * @param lieferant
	 * @param rechnungsnummer
	 * @param datum
	 */
	public Rechnung(Lieferant lieferant, String rechnungsnummer, LocalDateTime datum) {
		this.setLieferant(lieferant);
		this.setRechnungsnummer(rechnungsnummer);
		this.setDatumUhrzeit(datum);
		liste = new ArrayList<Rechnungsposten>();
		lieferant.addRechnung(this);
	}

	
	public void addPosten(Rechnungsposten posten) {
		liste.add(posten);
	}
	
	public Rechnungsposten[] getRechnungsposten() {
		return liste.toArray(new Rechnungsposten[liste.size()]);
	}

	/**
	 * @return the lieferant
	 */
	public Lieferant getLieferant() {
		return lieferant;
	}

	/**
	 * @param lieferant the lieferant to set
	 */
	private void setLieferant(Lieferant lieferant) {
		this.lieferant = lieferant;
	}


	/**
	 * @return the datumUhrzeit
	 */
	public LocalDateTime getDatumUhrzeit() {
		return datumUhrzeit;
	}


	/**
	 * @param datum the datumUhrzeit to set
	 */
	public void setDatumUhrzeit(LocalDateTime datum) {
		this.datumUhrzeit = datum;
	}


	/**
	 * @return the rechnungsnummer
	 */
	public String getRechnungsnummer() {
		return rechnungsnummer;
	}


	/**
	 * @param rechnungsnummer the rechnungsnummer to set
	 */
	public void setRechnungsnummer(String rechnungsnummer) {
		this.rechnungsnummer = rechnungsnummer;
	}


	public double getGesamtSumme() {
		double summe = 0.;
		
		for (Rechnungsposten r : liste) {
			summe += r.getPreis();
		}
		
		return summe;
	}


	public Object getAnzahlPosten() {
		return liste.size();
	}
}
