package pingpong;

public class MyPingPongWrong {
	
	public static void main(String[] args) {
		
		Thread pong=new Thread(new Runnable() {
			public void run() {
				for(int i=1;i<20;i++) {
					System.out.println("Pong ("+i+")");
				}
			}
		});
		
		Thread ping=new Thread(new Runnable() {
			public void run() {
				for(int i=1;i<20;i++) {
					System.out.println("Ping ("+i+")");
				}
			}
		});
		
		try {
			pong.start();
			ping.start();
		} catch (Exception e) {}
	}

}
