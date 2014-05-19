package pingpong;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class MyPingPongRLock {
	
	public static void main(String[] args) throws InterruptedException {
		final ReentrantLock lock=new ReentrantLock(true);
		final Condition condition=lock.newCondition();
		
		System.out.println("Pong anyone?");
		
		class PlayAction {
			private volatile String nextAction="pong";
			
			public void play(String action, int seqNum) 
			throws InterruptedException {
				lock.lock();
				while (nextAction==action) {
					condition.await();
				}
				System.out.println(action+"("+seqNum+")");
				nextAction=action;
				condition.signal();
				lock.unlock();
			}
		}
		
		final PlayAction mPlayAction=new PlayAction();
		
		Thread pong=new Thread(new Runnable() {
			public void run() {
				for(int i=1;i<20;i++) {
					try {
						mPlayAction.play("ping", i);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		Thread ping=new Thread(new Runnable() {
			public void run() {
				for(int i=1;i<20;i++) {
					try {
						mPlayAction.play("pong", i);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});

		pong.start();
		ping.start();
		
		pong.join();
		ping.join();
		
		System.out.println("That's all folks!!");

	}

}