package account;

public class Communicate {

	//private static final String 
	private static final boolean IN = true, OUT = false;

	private String toLogin = null;
	private String toLogout = null;

	public synchronized void setAccounts(String accountNumber, boolean logType) {
		if (logType == IN) {
			toLogin = accountNumber;
		} else {
			toLogout = accountNumber;
		}
		toLogin = accountNumber;
		notify();
	}

	public synchronized void removeAccount(String accountNumber) {
		toLogout = accountNumber;
		notify();
	}

	public synchronized String getAccount() {
		String accountNum = null;
		while (toLogin == null && toLogout == null) {
			try {
				wait();
			} catch (InterruptedException ie) {
				return null;
			}
		}
		return accountNum;

	}

	public synchronized String getToLogout() {
		String accountNum = null;

		return accountNum;
	}
	
	/*public void refreshToLogin(String accountLoggedIn) {
		for (int i = 0; i < toLogin.size(); i++) {
			if (toLogin.get(i).equals(accountLoggedIn))
				toLogin.remove(i);
		}
	}
	
	public void refreshToLogout(String accountLoggedOut) {
		for (int i = 0; i < toLogout.size(); i++) {
			if (toLogout.get(i).equals(accountLoggedOut))
				toLogout.remove(i);
		}
	}*/
}
