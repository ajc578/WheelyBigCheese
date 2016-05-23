package account;

public class ThreadInterCom {
	private String threadOutput;
	private boolean inUse = true;

	public synchronized void finished() throws InterruptedException {
		threadOutput = Protocol.END;
		notify();
		wait();
	}
	
	public synchronized void finishAccepted() {
		notify();
	}
	
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
