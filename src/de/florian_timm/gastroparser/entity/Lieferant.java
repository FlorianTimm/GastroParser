package de.florian_timm.gastroparser.entity;

import java.util.ArrayList;

import de.florian_timm.gastroparser.Database;
import de.florian_timm.gastroparser.ordner.LieferantOrdner;

public class Lieferant {
	

	private long id;
	private String name;
	private String ustId;
	private ArrayList<Rechnung> rechnungen;
	
	private String regDate = "((0[1-9]|[12]\\d|3[01])\\.(0\\d|1[0-2])\\.[12]\\d{3})\\h(([01]\\d|2[0-4]|):[0-5]\\d)";
	private int regDatePos = 0;
	private String regDateOrder = "dd.MM.yyyy HH:mm";
	
	private String regRechPos = "";
	private int[] regRechPosOrder = new int[11];
	
	private String regRechNr = "";
	private int regRechNrPos = 0;

	/**
	 * @param name
	 */
	private Lieferant(String name, String ustId) {
		this.name = name;
		this.setUstId(ustId);
		this.rechnungen = new ArrayList<Rechnung>();
		this.id = Database.get().insert(this);
		LieferantOrdner.getInstanz().addLieferant(this);
	}
	
	/**
	 * @param id
	 * @param name
	 * @param ustId
	 * @param rechnungen
	 * @param regDate
	 * @param regDatePos
	 * @param regDateOrder
	 * @param regRechPos
	 * @param regRechPosOrder
	 * @param regRechNr
	 * @param regRechNrPos
	 */
	public Lieferant(long id, String name, String ustId, String regDate, int regDatePos,
			String regDateOrder, String regRechPos, int[] regRechPosOrder, String regRechNr, int regRechNrPos) {
		this.id = id;
		this.name = name;
		this.ustId = ustId;
		this.regDate = regDate;
		this.regDatePos = regDatePos;
		this.regDateOrder = regDateOrder;
		this.regRechPos = regRechPos;
		this.regRechPosOrder = regRechPosOrder;
		this.regRechNr = regRechNr;
		this.regRechNrPos = regRechNrPos;
		this.rechnungen = new ArrayList<Rechnung>();
		LieferantOrdner.getInstanz().addLieferant(this);
	}

	public String getName() {
		return name;
	}

	public Rechnung[] getRechnungen() {
		return rechnungen.toArray(new Rechnung[rechnungen.size()]);
	}

	public void addRechnung(Rechnung rechnung) {
		rechnungen.add(rechnung);
	}

	public static boolean pruefeUstID(String ustId) {
		// cj stehe für eine der Ziffern c1 bis c8,
		// pz ist die Prüfziffer,
		// j, produkt, summe bezeichnen Hilfsfelder.
		
		
		char[] c = ustId.replace("\\s", "").substring(2).toCharArray();
		if (c.length != 8)
			return false;
		int produkt = 10;
		for (int j = 1; j < 8; j++) {
			int summe = Integer.parseInt("" + c[j]) + produkt;
			summe = summe % 10;
			if (summe == 0) {
				summe = 10;
			}

			produkt = (2 * summe) % 11;
		}
		int pz = 11 - produkt;
		if (pz == 10) {
			pz = 0;
		}
		return Integer.parseInt("" + c[7]) == pz;
	}

	/**
	 * @return the ustId
	 */
	public String getUstId() {
		return ustId;
	}

	/**
	 * @param ustId the ustId to set
	 */
	public void setUstId(String ustId) {
		//if (Lieferant.pruefeUstID(ustId))
			this.ustId = ustId;
	}
	
	public double getGesamtSumme() {
		double summe = 0.;
		
		for (Rechnung r : rechnungen) {
			System.out.println(r.getGesamtSumme());
			summe += r.getGesamtSumme();
		}
		
		return summe;
	}
	
	public int getAnzahlRechnungen() {
		return rechnungen.size();
	}

	public static Lieferant create(String name, String ustId) {
		Lieferant l = LieferantOrdner.getInstanz().getLieferant(name, ustId);
		if (l != null) {
			return l;
		}
		return new Lieferant(name, ustId);
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getRegRechPos() {
		return regRechPos;
	}

	public void setRegRechPos(String regRechPos) {
		this.regRechPos = regRechPos;
	}

	public int[] getRegRechPosOrder() {
		return regRechPosOrder;
	}

	public void setRegRechPosOrder(int[] regRechPosOrder) {
		this.regRechPosOrder = regRechPosOrder;
	}

	public String getRegRechNr() {
		return regRechNr;
	}

	public void setRegRechNr(String regRechNr) {
		this.regRechNr = regRechNr;
	}

	/**
	 * @return the regDateOrder
	 */
	public String getRegDateOrder() {
		return regDateOrder;
	}

	/**
	 * @param regDateOrder the regDateOrder to set
	 */
	public void setRegDateOrder(String regDateOrder) {
		this.regDateOrder = regDateOrder;
	}

	/**
	 * @return the regDatePos
	 */
	public int getRegDatePos() {
		return regDatePos;
	}

	/**
	 * @param regDatePos the regDatePos to set
	 */
	public void setRegDatePos(int regDatePos) {
		this.regDatePos = regDatePos;
	}

	/**
	 * @return the regRechNrPos
	 */
	public int getRegRechNrPos() {
		return regRechNrPos;
	}

	/**
	 * @param regRechNrPos the regRechNrPos to set
	 */
	public void setRegRechNrPos(int regRechNrPos) {
		this.regRechNrPos = regRechNrPos;
	}

	/**
	 * @return the lid
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param lid the lid to set
	 */
	public void setId(long id) {
		this.id = id;
	}

}
