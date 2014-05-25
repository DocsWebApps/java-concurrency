package blockingqueue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @class BuggyBlockingQueue
 * 
 * @brief Defines a buggy version of the BlockingQueue interface that
 *        doesn't implement any synchronization mechanisms, so of
 *        course it will fail horribly, which is the intent!
 */
public class SynchBlockingQueue<E> implements BlockingQueue<E> {
	
	public static void main (String[] args) {
		final SynchBlockingQueue<String> buggyQueue=new SynchBlockingQueue<String>(100);
		
		Thread put=new Thread(new Runnable() {
			public void run() {
				for(int i=0; i<1000;i++) {
					try {
						buggyQueue.put("hello");
						System.out.println("put"+buggyQueue.size());
					} catch (InterruptedException e) {}
				}
			}
		});

		Thread take=new Thread(new Runnable() {
			public void run() {
				for(int i=0; i<1000;i++) {
					try {
						buggyQueue.take();
						System.out.println("take"+buggyQueue.size());
					} catch (InterruptedException e) {}
				}
			}
		});

		
		put.start();
		take.start();
		
		try {
			put.join();
			take.join();
		} catch (InterruptedException e) {}
		
		System.out.println("Finished");
	}
	
    /**
     * ArrayList doesn't provide any synchronization, so it will not
     * work correctly when called from multiple Java Threads.
     */
    private ArrayList<E> mList = null;
    private int queueLength=0;
    private int queueSize=0;

    /**
     * Constructor just creates an ArrayList of the appropriate size.
     */
    public SynchBlockingQueue(int initialSize) {
    	queueSize=initialSize;
        mList = new ArrayList<E>(initialSize);
    }

    /**
     * Insert msg at the tail of the queue, but doesn't block if the
     * queue is full.
     */
    public synchronized void put(E msg) throws InterruptedException {
    	if(queueLength==queueSize) {
    		wait();
    	}
        mList.add(msg);
        queueLength++;
        notify();
    }

    /**
     * Remove msg from the head of the queue, but doesn't block if the
     * queue is empty.
     */
    public synchronized E take() throws InterruptedException {
    	if(queueLength==0) {
    		wait();
    	}
    	queueLength--;
    	notify();
        return mList.remove(0);
    }

    /**
     * All these methods are inherited from the BlockingQueue
     * interface. They are defined as no-ops to ensure the "Buggyness"
     * of this class ;-)
     */
    public int drainTo(Collection<? super E> c) {
        return 0;
    }
    public int drainTo(Collection<? super E> c, int maxElements) {
        return 0;
    }
    public boolean contains(Object o) {
        return false;
    }
    public boolean remove(Object o) {
        return false;
    }
    public int remainingCapacity() {
        return 0;
    }
    public E poll() {
        return null;
    }
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        return take();
    }
    public E peek() {
        return null;
    }
    public boolean offer(E e) {
        return false;
    }
    public boolean offer(E e, long timeout, TimeUnit unit) {
        try {
            put(e);
        }
        catch (InterruptedException ex) {
            // Just swallow this exception for this simple (buggy) test.
        }
        return true;
    }
    public boolean add(E e) {
        return false;
    }
    public E element() {
        return null;
    }
    public E remove() {
        return null;
    }
    public void clear() {
    }
    public boolean retainAll(Collection<?> collection) {
        return false;
    }
    public boolean removeAll(Collection<?> collection) {
        return false;
    }
    public boolean addAll(Collection<? extends E> collection) {
        return false;
    }
    public boolean containsAll(Collection<?> collection) {
        return false;
    }
    public Object[] toArray() {
        return null;
    }
    public <T> T[] toArray(T[] array) {
        return null;
    }
    public Iterator<E> iterator() {
        return null;
    }
    public boolean isEmpty() {
        return false;
    }
    public int size() {
        return queueLength;
    }
}
