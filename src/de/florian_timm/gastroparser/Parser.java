package de.florian_timm.gastroparser;

import java.io.File;
import java.util.ArrayList;

import de.florian_timm.gastroparser.entity.*;

abstract class Parser implements Runnable {
	protected ParserListener frame;
	protected File[] files;
	protected ArrayList<Rechnungsposten> posten;
	
	public void run() {
		for (int i = 0; i < files.length; i++) {
			parse(files[i]);
			this.frame.setStatus(100 * i/files.length);
		}
		this.frame.setStatus(100);
		this.frame.readyParser(posten);
	}
	
	abstract protected void parse(File file);
	
	public Parser(ParserListener gui, File[] files) {
		posten = new ArrayList<Rechnungsposten>();
		this.files = files;
		this.frame = gui;		
	};
	
}
