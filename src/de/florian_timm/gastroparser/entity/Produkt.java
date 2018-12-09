package de.florian_timm.gastroparser.entity;

import java.util.ArrayList;

import de.florian_timm.gastroparser.ordner.ProduktOrdner;

public class Produkt {
	private String bezeichnung;
	private double mwst;
	private String einheit;
	private final ArrayList<Artikel> artikel = new ArrayList<Artikel>();
	
	/**
	 * @param bezeichnung
	 * @param einheit
	 * @param mwst
	 */
	private Produkt(String bezeichnung, String einheit, double mwst) {
		this.setBezeichnung(bezeichnung);
		this.setEinheit(einheit);
		this.setMwst(mwst);
		ProduktOrdner.getInstanz().addProdukt(this);
	}
	
	/**
	 * @param bezeichnung
	 * @param einheit
	 * @param mwst
	 */
	public static Produkt create(String bezeichnung, String einheit, double mwst) {
		Produkt p = ProduktOrdner.getInstanz().getProdukt(bezeichnung);
		if (p != null) {
			return p;
		}
		return new Produkt(bezeichnung, einheit, mwst);
	}

	/**
	 * @return the bezeichnung
	 */
	public String getBezeichnung() {
		return bezeichnung;
	}

	/**
	 * @param bezeichnung the bezeichnung to set
	 */
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	/**
	 * @return the mwst
	 */
	public double getMwst() {
		return mwst;
	}

	/**
	 * @param mwst the mwst to set
	 */
	public void setMwst(double mwst) {
		this.mwst = mwst;
	}

	/**
	 * @return the einheit
	 */
	public String getEinheit() {
		return einheit;
	}

	/**
	 * @param einheit the einheit to set
	 */
	public void setEinheit(String einheit) {
		this.einheit = einheit;
	}

	public void add(Artikel artikel) {
		this.artikel.add(artikel);
	}
	
	public double getGesamtMenge() {
		double summe = 0.;
		for (Artikel a : this.artikel) {
			summe += a.getGesamtMenge();
		}
		return summe;
	}
	
	public double getGesamtPreis() {
		double summe = 0.;
		for (Artikel a : this.artikel) {
			summe += a.getGesamtPreis();
		}
		return summe;
	}
	
	public double getDurchschnittsPreis () {
		return getGesamtPreis() / getGesamtMenge();
	}
	
}
