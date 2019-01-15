package de.florian_timm.gastroparser.ordner;

import java.util.Iterator;

import de.florian_timm.gastroparser.entity.Produkt;

public class ProduktIterator implements Iterator<Produkt> {
	private ProduktOrdner ordner;
	private int index;

	public ProduktIterator (ProduktOrdner ordner) {
		this.ordner = ordner;
		this.index = 0;
	}
	
	public boolean hasNext() {
		return (this.index + 1) < ordner.size();
	}

	@Override
	public Produkt next() {
		// TODO Auto-generated method stub
		Produkt r = ordner.getProdukt(index);
		index++;
		return r;
	}

}
