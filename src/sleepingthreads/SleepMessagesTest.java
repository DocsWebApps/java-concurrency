package sleepingthreads;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class SleepMessagesTest {
	String myNull, myNotNull;
	String statusCheck=null;
	
    @Before
    public void setUp() throws Exception {
    	myNull=null;
    	myNotNull="NotNull";
    }
    
    @Test
    public void checkSleepMessages() {
    	SleepMessages mySleepMsg=new SleepMessages();
    	try {
			statusCheck=mySleepMsg.runThreads();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}  
    	assertNotNull(statusCheck);
    }
    
	@Test
	public void myTest1() {
		assertNull(myNull);
	}
	
	@Test
	public void myTest2() {
		assertNotNull(myNotNull);
	}
}
