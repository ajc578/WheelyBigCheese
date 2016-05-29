package tests;

public class SignUpCheckMethodsInClass {
	static boolean invalidUserNameCheck(String userName) {
		char uNFormat[] = userName.toCharArray();
		boolean uNCheck = false;
		
		if(uNFormat.length < 1) {
			uNCheck = false;
			return uNCheck;
		}
		if (((uNFormat[0] > 64 && uNFormat[0] < 91) || (uNFormat[0] > 96 && uNFormat[0] < 123))
			&& (userName.matches(".*\\d.*") || userName.matches(".*[abcdefghijklmnopqrstuvwxyz].*+")
			|| userName.matches(".*[ABCDEFGHIJKLMNOPQRSTUVWXYZ].*+")
			|| userName.matches(".*[_]*."))){
			uNCheck = true;
		}
		for(int i = 0; i < uNFormat.length; i++) {
			if((uNFormat[i] > 31 && uNFormat[i] < 48) ||
				(uNFormat[i] > 57 && uNFormat[i] < 65) ||
				(uNFormat[i] > 90 && uNFormat[i] < 97) ||
				(uNFormat[i] > 122 && uNFormat[i] <= 255)) {
				
				uNCheck = false;
				System.out.println(uNCheck);
				return uNCheck;
			}
		}
	//	System.out.println("unFormat[0] = " + uNFormat[0]);
		System.out.println(uNCheck);
		return uNCheck;	
	}
	
	static boolean invalidFirstNameCheck(String firstName) {
		boolean fNCheck = true;
		char[] fNFormat = firstName.toCharArray();
		
		if(fNFormat.length < 1) {
			fNCheck = false;
			return fNCheck;
		}
		if(fNFormat[0] < 65 || fNFormat[0] > 90) {
			fNCheck = false;
			return fNCheck;
		}
		
		if (fNFormat.length < 2) {
			fNCheck = false;
			return fNCheck;
		}
		
		if (firstName.matches(".*\\d.*")){
			fNCheck = false;
			return fNCheck;
		}
		if ((firstName.matches(".*[abcdefghijklmnopqrstuvwxyz].*+"))) {
			fNCheck = true;
		} 
		
		for(int i = 0; i < fNFormat.length; i++) {
			if((fNFormat[i] > 31 && fNFormat[i] < 48) ||
				(fNFormat[i] > 57 && fNFormat[i] < 65) ||
				(fNFormat[i] > 90 && fNFormat[i] < 97) ||
				(fNFormat[i] > 122 && fNFormat[i] <= 255)) {
				
				fNCheck = false;
				return fNCheck;
			}
		}
		
		return fNCheck;
	}
	
	static boolean invalidHeightCheck(String height){
		boolean heightCheck = true;
		if (!height.contains(".")) {
			heightCheck = false;
		} else {
			if (height.matches(".*[0123456789.].*+")) {
				double heightTemp = Double.parseDouble(height);
				if (!(heightTemp > 0.50 && heightTemp < 3.00)) {
					heightCheck = false;
				}
			} else {
				heightCheck = false;
			}
			
		}
		return heightCheck;
	}
	
	public static boolean invalidPasswordCheck(String password) {
		char[] pFormat = password.toCharArray();
		boolean passwordCheck = false;
		
		if(password.matches(".*\\d.*+") && password.matches
			(".*[abcdefghijklmnopqrstuvwxyz].*") && password.matches
			(".*[ABCDEFGHIJKLMNOPQRSTUVWXYZ].*") && password.length() >= 6)
			passwordCheck = true;
			
		for(int i = 0; i < pFormat.length; i++) {
			if((pFormat[i] > 31 && pFormat[i] < 48) ||
				(pFormat[i] > 57 && pFormat[i] < 65) ||
				(pFormat[i] > 90 && pFormat[i] < 97) ||
				(pFormat[i] > 122 && pFormat[i] <= 255)) {
				
				passwordCheck = false;
				return passwordCheck;
			}
		}		
		return passwordCheck;
	}
	
	static boolean invalidEmailCheck(String email) {
		boolean emailCheck = false;
		char[] eFormat = new char[300];
		eFormat = email.toCharArray();
		
		if(eFormat.length < 1) {
			emailCheck = false;
			return emailCheck;
		}
		
		else emailCheck = false;
		
		for(int i = 1; i < eFormat.length - 1; i++) {
			if (eFormat[i] == 64){
				if(!(eFormat[0] == 64 || eFormat[eFormat.length - 1] == 64)){
					emailCheck = true;
					break;
				}
			}
			else emailCheck = false;
		}

		
		if(!(email.matches(".*[@].*+") || email.matches(".*\\..*"))){
			emailCheck = false;
			return emailCheck;
		}
		return emailCheck;	
	}	
	
	public static void main (String args[]) {
		invalidUserNameCheck(null);
	}
}
