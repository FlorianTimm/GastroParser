package de.florian_timm.gastroparser;

public interface StatusListener {
	abstract public void ready ();

	public abstract void setStatus(int i);
}
