package pingpong;

// This is the abstract class from which PingPongThreadSema and PingPongThreadCond inherit
// This is an implementation of the TEMPLATE pattern

public abstract class PlayPingPong extends Thread {
	
	protected String playAction;
	
	public void run () {
		for(int loopsDone=1; loopsDone<20; loopsDone++) {
			try {
				acquire();
				System.out.println(playAction+"("+loopsDone+")");
			} catch (Exception e) {}
			finally {
				release();
			}
		}
	}
	
	abstract void acquire();
	abstract void release();
}
