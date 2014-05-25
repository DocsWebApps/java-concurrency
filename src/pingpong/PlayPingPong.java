package pingpong;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

// This is the abstract class from which PingPongThreadSema and PingPongThreadCond inherit
// This is an implementation of the TEMPLATE pattern

public class PlayPingPong {
	
	public static void main(String[] args) {
		final boolean sema=false;
		System.out.println("Lets play pong!");
		if(sema) {
			final Semaphore pingSema=new Semaphore(1);
			final Semaphore pongSema=new Semaphore(0);	
			PingPongThreadSema ping=new PingPongThreadSema("ping", pingSema, pongSema);
			PingPongThreadSema pong=new PingPongThreadSema("pong", pongSema, pingSema);
			ping.start();
			pong.start();
			try {
				ping.join();
				pong.join();
			} catch (InterruptedException e) {}
			
		} else {
			final ReentrantLock lock=new ReentrantLock(true);
			final Condition condition=lock.newCondition();
			PingPongThreadCond ping=new PingPongThreadCond("ping", lock, condition, true);
			PingPongThreadCond pong=new PingPongThreadCond("pong", lock, condition, false);
			ping.setOtherThread(pong.getId());
			pong.setOtherThread(ping.getId());
			ping.start();
			pong.start();
			try {
				ping.join();
				pong.join();
			} catch (InterruptedException e) {}
		}
		System.out.println("That's all folks!");
	}
	
	static abstract class PingPongThread extends Thread {
		protected String playAction;
	
		public void run () {
			for(int loopsDone=1; loopsDone<21; loopsDone++) {
				try {
					acquire();
					System.out.println(playAction+"("+loopsDone+")");
					release();
				} catch (Exception e) {}
			}
		}
	
		abstract void acquire();
		abstract void release();
	}
	
	static class PingPongThreadSema extends PingPongThread {
		private Semaphore firstSema, secondSema;
		
		public PingPongThreadSema(String playAction, Semaphore firstSema, Semaphore secondSema) {
			this.playAction=playAction;
			this.firstSema=firstSema;
			this.secondSema=secondSema;
		}

		@Override
		void acquire() {
			try {
				firstSema.acquire();
			} catch (InterruptedException e) {}
		}

		@Override
		void release() {
			secondSema.release();
		}
	}
	
	static class PingPongThreadCond extends PingPongThread {
		private ReentrantLock mLock;
		private Condition condition;
		private boolean mIsOwner;
		private long mOtherThread;
		private static volatile long mTurnOwner;
		
		public void setOtherThread(long otherThread) {
			mOtherThread=otherThread;
		}
		
		public PingPongThreadCond(String playAction, ReentrantLock lock, Condition condition, boolean isOwner) {
			this.playAction=playAction;
			this.mLock=lock;
			this.condition=condition;
			this.mIsOwner=isOwner;
			
			if(mIsOwner) {
				mTurnOwner=this.getId();
			}
		}

		@Override
		void acquire() {
			mLock.lock();
			if(mTurnOwner!=this.getId()) {
				try {
					condition.await();
				} catch (InterruptedException e) {}
			}	
		}

		@Override
		void release() {
			mTurnOwner=mOtherThread;
			condition.signal();
			mLock.unlock();
		}
	}
}

