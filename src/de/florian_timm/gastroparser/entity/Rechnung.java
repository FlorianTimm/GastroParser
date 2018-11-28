package de.florian_timm.gastroparser.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Rechnung {
	private String rechnungsnummer;
	private Date datumUhrzeit;
	private List<Rechnungsposten> liste;
	private Lieferant lieferant;
	
	/**
	 * @param rechnungsnummer
	 * @param datumUhrzeit
	 */
	public Rechnung(Lieferant lieferant, String rechnungsnummer, Date datumUhrzeit) {
		this.setLieferant(lieferant);
		this.setRechnungsnummer(rechnungsnummer);
		this.setDatumUhrzeit(datumUhrzeit);
		liste = new ArrayList<Rechnungsposten>();
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
	public Date getDatumUhrzeit() {
		return datumUhrzeit;
	}


	/**
	 * @param datumUhrzeit the datumUhrzeit to set
	 */
	public void setDatumUhrzeit(Date datumUhrzeit) {
		this.datumUhrzeit = datumUhrzeit;
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
}
