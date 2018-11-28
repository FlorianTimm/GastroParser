package de.florian_timm.gastroparser.entity;

import java.util.ArrayList;

public class Lieferant {
	private String name;
	private ArrayList<Rechnung> rechnungen;

	/**
	 * @param name
	 */
	public Lieferant(String name) {
		this.name = name;
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
	
	
	private boolean pruefeUstID(String ustId) {
	/*
	cj stehe für eine der Ziffern c1 bis c8,
     pz ist die Prüfziffer,
     j, produkt, summe bezeichnen Hilfsfelder.
  begin
     produkt := 10
     for j = 1 to 8 step 1
       summe := cj + produkt
       summe := summe mod 10
       if summe = 0
          then summe = 10
       end-if
       produkt := (2 * summe) mod 11
     end-for
     pz := 11 - produkt
     if pz - 10
       then pz = 0
     end-if
  end.	
	 */
		
	return true;
	}
	
}
