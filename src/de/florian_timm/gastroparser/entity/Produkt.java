package de.florian_timm.gastroparser.entity;

public class Produkt {
	private String bezeichnung;
	private double mwst;
	private String einheit;
	
	/**
	 * @param bezeichnung
	 * @param einheit
	 * @param mwst
	 */
	public Produkt(String bezeichnung, String einheit, double mwst) {
		this.setBezeichnung(bezeichnung);
		this.setEinheit(einheit);
		this.setMwst(mwst);
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
	
	
}
