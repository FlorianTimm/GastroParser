package de.florian_timm.gastroparser.entity;

import java.util.ArrayList;

public class Artikel {
	private Produkt produkt;
	private String artikelnr;
	private long ean;
	private double menge;
	private final ArrayList<Rechnungsposten> posten = new ArrayList<Rechnungsposten>();

	/**
	 * @param produkt
	 * @param menge
	 */
	public Artikel(Produkt produkt, double menge) {
		this.setProdukt(produkt);
		this.setMenge(menge);
		produkt.add(this);
	}

	/**
	 * @param produkt
	 * @param menge
	 * @param artikelnr
	 * @param ean
	 */
	public Artikel(Produkt produkt, double menge, String artikelnr, long ean) {
		this(produkt, menge);
		this.setArtikelnr(artikelnr);
		this.setEan(ean);
	}

	public void addPosten(Rechnungsposten posten) {
		this.posten.add(posten);
	}

	public Rechnungsposten[] getRechnungsposten() {
		return posten.toArray(new Rechnungsposten[posten.size()]);
	}

	/**
	 * @return the produkt
	 */
	public Produkt getProdukt() {
		return produkt;
	}

	/**
	 * @param produkt the produkt to set
	 */
	public void setProdukt(Produkt produkt) {
		this.produkt = produkt;
	}

	/**
	 * @return the artikelnr
	 */
	public String getArtikelnr() {
		return artikelnr;
	}

	/**
	 * @param artikelnr the artikelnr to set
	 */
	public void setArtikelnr(String artikelnr) {
		this.artikelnr = artikelnr;
	}

	/**
	 * @return the ean
	 */
	public long getEan() {
		return ean;
	}

	/**
	 * @param ean the ean to set
	 */
	public void setEan(long ean) {
		this.ean = ean;
	}

	/**
	 * @return the menge
	 */
	public double getMenge() {
		return menge;
	}

	/**
	 * @param menge the menge to set
	 */
	public void setMenge(double menge) {
		this.menge = menge;
	}

	public double getGesamtMenge() {
		double summe = 0.;
		for (Rechnungsposten rp : posten) {
			summe += rp.getMenge();
		}
		return summe;
	}

	public double getGesamtPreis() {
		double summe = 0.;
		for (Rechnungsposten rp : posten) {
			summe += rp.getPreis();
		}
		return summe;
	}

	public void add(Rechnungsposten rechnungsposten) {
		posten.add(rechnungsposten);
	}
}
