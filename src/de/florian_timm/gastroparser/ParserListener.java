package de.florian_timm.gastroparser;

import java.util.ArrayList;

import de.florian_timm.gastroparser.entity.Rechnungsposten;

public interface ParserListener {
	abstract public void readyParser (ArrayList<Rechnungsposten> rp);

	public abstract void setStatus(int i);
}
