package de.florian_timm.gastroparser.entity;

import java.util.ArrayList;

import de.florian_timm.gastroparser.ordner.LieferantOrdner;

public class Lieferant {
	private String name;
	private String ustId;
	private ArrayList<Rechnung> rechnungen;

	/**
	 * @param name
	 */
	private Lieferant(String name, String ustId) {
		this.name = name;
		this.setUstId(ustId);
		LieferantOrdner.getInstanz().addLieferant(this);
		this.rechnungen = new ArrayList<Rechnung>();
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

}
