package de.florian_timm.gastroparser.entity;

import de.florian_timm.gastroparser.Database;

public class Rechnungsposten {
	private Artikel artikel;
	private double menge;
	private double preis;
	private Rechnung rechnung;
	private long id;
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
		artikel.add(this);
		rechnung.addPosten(this);
		this.setId(Database.get().insert(this));
	}
	public Rechnungsposten(long rpid, Artikel artikel, double menge, double preis, Rechnung rechnung) {
		this.setRechnung(rechnung);
		this.setArtikel(artikel);
		this.setMenge(menge);
		this.setPreis(preis);
		this.setId(rpid);
		artikel.add(this);
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
	

}
