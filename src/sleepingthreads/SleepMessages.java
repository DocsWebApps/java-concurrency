package sleepingthreads;

public class SleepMessages {
	
	public static void main(String[] args) {
		SleepMessages mySleepMsg=new SleepMessages();
		try {
			String myReturn=mySleepMsg.runThreads();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Finished no errors");
	}
	
	public String runThreads() throws InterruptedException {
		final String RESULT_OK="OK";
		String msg[] ={"Message 1", "Message 2", "Message 3"};
		
		for(int i=0;i<msg.length;i++) {
			try {
				Thread.sleep(4000);
			} catch(InterruptedException e) {
				// We have been interrupted so return or do something else
				return null;
			}
			System.out.println(msg[i]);
		}
		return RESULT_OK;
		
	}

}
