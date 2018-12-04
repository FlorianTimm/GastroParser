package de.florian_timm.gastroparser.entity;

public class Rechnungsposten {
	private Artikel artikel;
	private double menge;
	private double preis;
	private Rechnung rechnung;
	/**
	 * @param rechnung
	 * @param artikel
	 * @param menge
	 * @param preis
	 */
	public Rechnungsposten(Rechnung rechnung, Artikel artikel, double menge, double preis) {
		this.setRechnung(rechnung);
		this.setArtikel(artikel);
		this.setMenge(menge);
		this.setPreis(preis);
		rechnung.addPosten(this);
	}
	/**
	 * @return the artikel
	 */
	public Artikel getArtikel() {
		return artikel;
	}
	/**
	 * @param artikel the artikel to set
	 */
	public void setArtikel(Artikel artikel) {
		this.artikel = artikel;
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
	/**
	 * @return the preis
	 */
	public double getPreis() {
		return preis;
	}
	/**
	 * @param preis the preis to set
	 */
	public void setPreis(double preis) {
		this.preis = preis;
	}
	/**
	 * @return the rechnung
	 */
	public Rechnung getRechnung() {
		return rechnung;
	}
	/**
	 * @param rechnung the rechnung to set
	 */
	public void setRechnung(Rechnung rechnung) {
		this.rechnung = rechnung;
	}
	public double getPreisProEinheit() {
		return this.getPreis() / (this.getMenge() * getArtikel().getMenge());
	}
	

}
