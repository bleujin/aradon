package net.ion.radon;

public class InfinityThread extends Thread{

	public void run(){
		while(true){
			try {
				Thread.sleep(1000) ;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void startNJoin() {
		this.start() ;
		try {
			this.join() ;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
