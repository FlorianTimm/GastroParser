package de.florian_timm.gastroparser.ordner;

import java.util.ArrayList;

public class Informer {
	private final ArrayList<Listener> listener;
	private static Informer instanz = null;
	
	private Informer () {
		this.listener = new ArrayList<Listener>();
	}
	
	public static Informer get() {
		if (instanz == null) {
			instanz = new Informer();
		}
		return instanz;
	}
	
	public void addListener(Listener listener) {
		this.listener.add(listener);
	}
	
	public void informListener() {
		for (Listener l : listener) {
			l.dataChanged();
		}
	}
}
