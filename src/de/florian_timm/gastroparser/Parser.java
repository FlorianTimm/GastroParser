package de.florian_timm.gastroparser;

import java.io.File;

abstract class Parser implements Runnable {
	protected StatusListener frame;
	protected File[] files;
	
	public void run() {
		for (int i = 0; i < files.length; i++) {
			parse(files[i]);
			this.frame.setStatus(100 * i/files.length);
		}
		this.frame.setStatus(100);
		this.frame.ready();
	}
	
	abstract protected void parse(File file);
	
	public Parser(StatusListener gui, File[] files) {
		this.files = files;
		this.frame = gui;		
	};
	
}
