
public class ThreadInterrupt implements Runnable {
	public void run() {
		while(true) {
			if(Thread.interrupted()) {
				System.out.println("Thread Interrupted");
				return;
			}
			System.out.println("Thread Running");
		}
	}
	
	public static void main(String[] args) {
		Thread th1=new Thread(new ThreadInterrupt());
		th1.start();
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {}
		
		th1.interrupt();
	}
	

}
