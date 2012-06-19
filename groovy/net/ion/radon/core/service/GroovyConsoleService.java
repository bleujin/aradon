package net.ion.radon.core.service;

import groovy.ui.Console;

/**
 * 
 * @author Bruce Fancher
 * 
 */
public class GroovyConsoleService extends GroovyService {

	private Thread thread;

	public GroovyConsoleService() {
		super();
	}

	public void launch() {
		thread = new Thread() {
			@Override
			public void run() {
				try {
					final Console console = new Console(createBinding());
					console.run();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		thread.setDaemon(true);
		thread.start();
	}
}
