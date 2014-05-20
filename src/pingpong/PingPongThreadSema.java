package pingpong;

import java.util.concurrent.Semaphore;

public class PingPongThreadSema extends PlayPingPong {
	Semaphore acquireSema, releaseSema;
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Let's play pong");
		final Semaphore pingSema=new Semaphore(1,true);
		final Semaphore pongSema=new Semaphore(1,true); pongSema.acquire();
		new PingPongThreadSema("ping", pingSema, pongSema);
		new PingPongThreadSema("pong", pongSema, pingSema);
	}
	
	public PingPongThreadSema(String playAction, Semaphore acquireSema, Semaphore releaseSema) {
		this.acquireSema=acquireSema;
		this.releaseSema=releaseSema;
		this.playAction=playAction;
		this.start();
	}
	
	public void acquire() {
		try {
			acquireSema.acquire();
		} catch (Exception e) {}
	}
	
	public void release() {
		releaseSema.release();
	}
}
