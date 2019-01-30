package de.florian_timm.gastroparser.entity;

import java.time.LocalDate;
import de.florian_timm.gastroparser.Database;

public class Garantiepreis implements Comparable <Garantiepreis>{
	private long id;
	private Produkt produkt;
	private float preis;
	private LocalDate von, bis;
	private Lieferant lieferant;
	
	
	/**
	 * @param produkt
	 * @param preis
	 * @param von
	 */
	public Garantiepreis(Produkt produkt, Lieferant lieferant, float preis, LocalDate von) {
		this.produkt = produkt;
		this.lieferant = lieferant;
		this.setPreis(preis);
		this.setVon(von);
		this.id = Database.get().insert(this);
		this.produkt.addGarantiePreis(this);
		this.lieferant.addGarantiePreis(this);
	}
	
	/**
	 * @param produkt
	 * @param preis
	 * @param von
	 * @param bis
	 * @param lieferant 
	 */
	public Garantiepreis(Produkt produkt, Lieferant lieferant, float preis, LocalDate von, LocalDate bis) {
		this.produkt = produkt;
		this.lieferant = lieferant;
		this.setPreis(preis);
		this.setVon(von);
		this.setBis(bis);
		this.id = Database.get().insert(this);
		this.produkt.addGarantiePreis(this);
		this.lieferant.addGarantiePreis(this);
	}

	

	public Garantiepreis(long id, Produkt produkt, Lieferant lieferant, float preis, LocalDate von,
			LocalDate bis) {
		this.id = id;
		this.produkt = produkt;
		this.lieferant = lieferant;
		this.setPreis(preis);
		this.setVon(von);
		this.setBis(bis);
		produkt.addGarantiePreis(this);
		lieferant.addGarantiePreis(this);
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

	/**
	 * @return the produkt
	 */
	public Produkt getProdukt() {
		return produkt;
	}

	/**
	 * @return the preis
	 */
	public float getPreis() {
		return preis;
	}

	/**
	 * @param preis the preis to set
	 */
	public void setPreis(float preis) {
		this.preis = preis;
	}

	/**
	 * @return the von
	 */
	public LocalDate getVon() {
		return von;
	}

	/**
	 * @param von the von to set
	 */
	public void setVon(LocalDate von) {
		this.von = von;
	}

	/**
	 * @return the bis
	 */
	public LocalDate getBis() {
		return bis;
	}

	/**
	 * @param bis the bis to set
	 */
	public void setBis(LocalDate bis) {
		this.bis = bis;
	}

	/**
	 * @return the lieferant
	 */
	public Lieferant getLieferant() {
		return lieferant;
	}

	@Override
	public int compareTo(Garantiepreis gp) {
		return this.getBis().compareTo(gp.getBis());
	}

}
