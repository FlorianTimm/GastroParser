package de.florian_timm.gastroparser.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;

import de.florian_timm.gastroparser.Database;

public class Rechnung {
	private String rechnungsnummer;
	private LocalDateTime datumUhrzeit;
	private ArrayList<Rechnungsposten> liste = new ArrayList<Rechnungsposten>();
	private Lieferant lieferant;
	private long id;

	private static ArrayList<Rechnung> rechnungsListe = new ArrayList<Rechnung>();

	/**
	 * @param lieferant
	 * @param rechnungsnummer
	 * @param datum
	 */
	private Rechnung(Lieferant lieferant, String rechnungsnummer, LocalDateTime datum) {
		this.setLieferant(lieferant);
		this.setRechnungsnummer(rechnungsnummer);
		this.setDatumUhrzeit(datum);
		this.setId(Database.get().insert(this));
		lieferant.addRechnung(this);
		rechnungsListe.add(this);
	}

	public Rechnung(long id, LocalDateTime datum, Lieferant lieferant, String rechnungsnummer) {
		this.setLieferant(lieferant);
		this.setRechnungsnummer(rechnungsnummer);
		this.setDatumUhrzeit(datum);
		this.setId(id);
		lieferant.addRechnung(this);
		rechnungsListe.add(this);
	}

	public static Rechnung create(Lieferant lieferant, String rechnungsnummer, LocalDateTime datum) {
		return new Rechnung(lieferant, rechnungsnummer, datum);
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

	public int getAnzahlPosten() {
		return liste.size();
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	public static Rechnung getRechnung(long rechnungId) {
		for (Rechnung r : rechnungsListe)
			if (r.getId() == rechnungId)
				return r;
		return null;
	}
}
