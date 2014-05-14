
public class ThreadJoin implements Runnable {
	public void run() {
		for(int i=0;i<300000;i++) {
			System.out.println(i+" Thread Running");
		}
	}
	
	public static void main(String[] args) {
		Thread th1=new Thread(new ThreadJoin());
		th1.start();
		
		try  {
			th1.join();
		} catch(InterruptedException e) {}
		
		System.out.println("Thread Finsihed");
	}
}
