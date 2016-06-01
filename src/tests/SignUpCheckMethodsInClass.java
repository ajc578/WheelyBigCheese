package tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	static boolean invalidWeightCheck(String weight) {
		//String weight = weightField.getText();
		char[] wFormat = weight.toCharArray();
		boolean weightCheck = true;
		
		// 6. If length is less than 2 or greater than 3.
		if(wFormat.length < 2 || wFormat.length > 3){
			weightCheck = false;
			System.out.println("6");
			return weightCheck;
		}
		
		if(wFormat[0] < 49 || wFormat[0] > 57 || wFormat[0] ==51){
			weightCheck = false;
			System.out.println("1");
			return weightCheck;
		}
		// 2. If less than 45
		else if(wFormat[0] == 52 && wFormat[1] <53){
			weightCheck = false;
			System.out.println("2");
			System.out.println("Length = " + wFormat.length);
			return weightCheck;
		}
		// 3. If 1st ASCII = (1 or 2) & length is less than 3
		else if((wFormat[0] == 49 || wFormat[0] == 50) && wFormat.length < 3){
			weightCheck = false;
			System.out.println("3");
			return weightCheck;
		}
		
		// 4. If 2nd ASCII is less than 0 or greater than 99
		if(wFormat[1] < 48 || wFormat[1] > 57){
			weightCheck = false;
			System.out.println("4");
			return weightCheck;
		}
		// 5. If greater than 299
		if(wFormat.length > 2 && wFormat[0] > 50){
			weightCheck = false;
			System.out.println("5");
			return weightCheck;
		}
		

		
		// 7. If 3rd ASCII is less than 0 or greater than 9
		if(wFormat.length == 3){
		if(wFormat[2] < 48 || wFormat[2] > 57){
			weightCheck = false;
			System.out.println("7");
			return weightCheck;
		}
		}
		else weightCheck = true;
		return weightCheck;
	}
	
	boolean invalidDOBCheck(String dob) {
		boolean dobCheck = true;
		//dob = dOBPicker.getEditor().getText();
		Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            date = sdf.parse(dob);
            if (!dob.equals(sdf.format(date))) {
                dobCheck = false;
            }
        } catch (ParseException ex) {
        	dobCheck = false;
        }
        return dobCheck;
	}
	
	public static void main (String args[]) {
		invalidUserNameCheck(null);
	}
}
