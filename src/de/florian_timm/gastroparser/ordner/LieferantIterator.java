package de.florian_timm.gastroparser.ordner;

import java.util.Iterator;

import de.florian_timm.gastroparser.entity.Lieferant;

public class LieferantIterator implements Iterator<Lieferant> {
	private LieferantOrdner ordner;
	private int index;

	public LieferantIterator (LieferantOrdner ordner) {
		this.ordner = ordner;
		this.index = 0;
	}
	
	public boolean hasNext() {
		return (this.index + 1) < ordner.size();
	}

	@Override
	public Lieferant next() {
		// TODO Auto-generated method stub
		Lieferant r = ordner.getLieferant(index);
		index++;
		return r;
	}

}
