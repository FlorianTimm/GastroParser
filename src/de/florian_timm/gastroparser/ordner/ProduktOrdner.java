package de.florian_timm.gastroparser.ordner;

import java.util.ArrayList;
import java.util.Iterator;
import de.florian_timm.gastroparser.entity.Produkt;

public class ProduktOrdner implements Iterable<Produkt> {
	private ArrayList<Produkt> produkte;
	private static ProduktOrdner einzigeInstanz;
	
	private ProduktOrdner() {
		produkte = new ArrayList<Produkt>();
	}
	
	public static ProduktOrdner getInstanz() {
		if (einzigeInstanz == null) {
			einzigeInstanz = new ProduktOrdner();
		}
		return einzigeInstanz;
	}
	
	public void addProdukt(Produkt produkt) {
		produkte.add(produkt);
	}

	public int size() {
		return produkte.size();
	}

	@Override
	public Iterator<Produkt> iterator() {
		// TODO Auto-generated method stub
		return new ProduktIterator(this);
	}

	public Produkt getProdukt(int index) {
		// TODO Auto-generated method stub
		if (index < size()) {
			return produkte.get(index);
		}
		return null;
	}
	
	
	
}