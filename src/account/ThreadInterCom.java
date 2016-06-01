package account;

/**
 * This class contains the synchronous <code>send</code> and <code>receive</code>
 * methods for two threads to communicate and transfer (String) data safely. The
 * methods act as synchronous blocks to wait and notify the two threads 
 * in the communication.
 * 
 * <p> <STRONG> Developed by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Tested by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Oliver Rushton
 * 
 */
public class ThreadInterCom {
	private String threadOutput;
	private boolean inUse = true;
	/**
	 * Sets the <code>threadOutput</code> field to the input String parameter and notifies
	 * the receiving thread that the field has been set. The sending thread cannot set
	 * another message until the receiving thread has retrieved the previous value.
	 * <p>
	 * If the receiving thread hasn't entered the synchronous <code>receive</code> method,
	 * the <code>threadOutput</code> is set and the method finishes executing.
	 * 
	 * @param output the output that the sending thread wants the receiving thread to
	 * 				 retrieve.
	 */
	public synchronized void send(String output)  {
		while (!inUse) {
			try {
				wait();
			} catch (InterruptedException ie) {}
		}
		inUse = false;
		this.threadOutput = output;
		notifyAll();
	}
	/**
	 * Retrieves the value of the <code>threadOutput</code> set by the sending thread and
	 * notifies any thread waiting in this class object.
	 * <p>
	 * If the sending thread hasn't set the value of the <code>threadOutput</code> field yet, 
	 * then the receiving thread waits until it is notified.
	 * 
	 * @return The value of the <code>threadOutput</code> field.
	 */
	public synchronized String receive() {
		while (inUse) {
			try {
				wait();
			} catch (InterruptedException ie) {}
		}
		inUse = true;
		notifyAll();
		return threadOutput;
	}
}
