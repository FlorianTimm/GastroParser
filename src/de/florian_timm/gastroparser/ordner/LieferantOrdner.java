package de.florian_timm.gastroparser.ordner;

import java.util.ArrayList;
import java.util.Iterator;
import de.florian_timm.gastroparser.entity.Lieferant;

public class LieferantOrdner implements Iterable<Lieferant> {
	private ArrayList<Lieferant> lieferanten;
	private static LieferantOrdner einzigeInstanz = null;

	private LieferantOrdner() {
		lieferanten = new ArrayList<Lieferant>();
	}

	public static LieferantOrdner getInstanz() {
		if (einzigeInstanz == null) {
			einzigeInstanz = new LieferantOrdner();
		}
		return einzigeInstanz;
	}

	public Lieferant getLieferant(String name, String ustId) {
		for (Lieferant lieferant : lieferanten) {
			if ((lieferant.getUstId() != null && lieferant.getUstId().toUpperCase().equals(ustId.toUpperCase()))
					|| (lieferant.getName() != null && lieferant.getName().toUpperCase().equals(name.toUpperCase()))) {
				return lieferant;
			}
		}
		return null;
	}

	public void addLieferant(Lieferant lieferant) {
		this.lieferanten.add(lieferant);
	}

	public int size() {
		return lieferanten.size();
	}

	@Override
	public Iterator<Lieferant> iterator() {
		// TODO Auto-generated method stub
		return new LieferantIterator(this);
	}

	public Lieferant getLieferant(int index) {
		// TODO Auto-generated method stub
		if (index < size()) {
			return lieferanten.get(index);
		}
		return null;
	}

}
