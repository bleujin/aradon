package net.ion.framework.util;

public class TimeoutThread extends Thread {

	private int timeMili ;
	private TimeoutThread(int timeSec){
		this.timeMili = timeSec ;
	}

	public static TimeoutThread createWithMili(int timeMili){
		return new TimeoutThread(timeMili) ; 
	} 

	public static TimeoutThread createWithSec(int timeSec){
		return new TimeoutThread(timeSec * 1000) ; 
	} 
	
	public void run() {
		try {
			Thread.sleep(timeMili);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void startNJoin() {
		this.start();
		try {
			this.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
