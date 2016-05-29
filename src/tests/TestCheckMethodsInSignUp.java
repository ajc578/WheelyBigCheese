package tests;

/**
 * This class consists of the tests that check for correct operation of the 
 * InvalidUserNameCheck() method
 * @author Kamil Sledziewski
 *
 */

import static org.junit.Assert.*;

import org.junit.Test;

public class TestCheckMethodsInSignUp {
	double screenWidth, screenHeight;
	SignUpCheckMethodsInClass signUpMenu = new SignUpCheckMethodsInClass();
	boolean actual = true;
	boolean expected;

	/**
	 * Checks whether invalidUserNameCheck returns false if username provided by the user
	 * consists of numbers only
	 * <p>
	 * This testing method can also be applied to invalidLastNameCheck
	 */
	@Test
	public void testInvalidUserNameCheck1() {
		expected = SignUpCheckMethodsInClass.invalidUserNameCheck("23498");
		assertEquals(expected, actual);
	}
	
	/**
	 * Checks whether invalidUserNameCheck will return false if username provided by the user
	 * contains "special" characters
	 * <p>
	 * This testing method can also be applied to invalidLastNameCheck
	 */
	@Test
	public void testInvalidUserNameCheck2() {
		expected = signUpMenu.invalidUserNameCheck("#username1@");
		assertEquals(expected, actual);
	}
	
	/**
	 * Checks whether invalidUserNameCheck will return true if username provided by the user 
	 * consists of letters only
	 * <p>
	 * This testing method can also be applied to invalidLastNameCheck
	 */
	@Test
	public void testIndividualUserNameCheck3() {
		expected = signUpMenu.invalidUserNameCheck("username");
		assertEquals(expected, actual);
	}
	
	/**
	 * Checks whether invalidUserNameCheck will return true if username provided by the user
	 * consists of letters and numbers
	 * <p>
	 * This testing method can also be applied to invalidLastNameCheck
	 */
	@Test
	public void testIndividualUserNameCheck4() {
		expected = signUpMenu.invalidUserNameCheck("username12");
		assertEquals(expected, actual);
	}
	
	/**
	 * Checks whether invalidFirstNameCheck will return false if first name provided by the
	 * user consists of letters only
	 * <p>
	 * This testing method can also be applied to invalidLastNameCheck
	 */
	@Test
	public void testInvalidFirstNameCheck1() {
		expected = signUpMenu.invalidFirstNameCheck("123");
		assertEquals(expected, actual);
	}
	
	/**
	 * Checks whether invalidFirstNameCheck will return false if first name provided by the
	 * user contains a digit
	 * <p>
	 * This testing method can also be applied to invalidLastNameCheck
	 */
	@Test 
	public void testInvalidFirstNameCheck2() {
		expected = signUpMenu.invalidFirstNameCheck("John1");
		assertEquals(expected, actual);
	}
	
	/**
	 * Checks whether invalidFirstNameCheck will return false if first name provided by the
	 * user contains a special character
	 * <p>
	 * This testing method can also be applied to invalidLastNameCheck
	 */
	@Test
	public void testInvalidFirstNameCheck3() {
		expected = signUpMenu.invalidFirstNameCheck("John@^");
		assertEquals(expected, actual);
	}
	
	/**
	 * Checks whether invalidFirstNameCheck will return false if first name provided by the
	 * user is only one letter long
	 */
	@Test
	public void testInvalidFirstNameCheck4() {
		expected = signUpMenu.invalidFirstNameCheck("J");
		assertEquals(expected, actual);
	}
	
	/**
	 * Checks whether invalidFirstNameCheck will return false if first name provided by the
	 * user starts with a lower-case letter
	 * <p>
	 * This testing method can also be applied to invalidLastNameCheck
	 */
	@Test
	public void testInvalidFirstNameCheck5() {
		expected = signUpMenu.invalidFirstNameCheck("joe");
		assertEquals(expected, actual);
	}
	
	/**
	 * Checks whether invalidFirstNameCheck will return true if first name provided by the
	 * user contains at least two letters and no characters of other type
	 * <p>
	 * This testing method can also be applied to invalidLastNameCheck
	 */
	@Test
	public void testInvalidFirstNameCheck6() {
		expected = signUpMenu.invalidFirstNameCheck("Jo");
		assertEquals(expected, actual);
	}
	
	/**
	 * Checks whether invalidHeightCheck will return false if height provided by the user 
	 * contains a letter
	 */
	@Test
	public void testInvalidHeightCheck1() {
		expected = signUpMenu.invalidHeightCheck("1k81");
		assertEquals(expected, actual);
	}
	
	/**
	 * Checks whether invalidHeightCheck will return false if height provided by the user 
	 * contains a special character
	 */
	@Test
	public void testInvalidHeightCheck2() {
		expected = signUpMenu.invalidHeightCheck("2@21");
		assertEquals(expected, actual);
	}
	
	/**
	 * Checks whether invalidHeightCheck will return false if height provided by the user 
	 * is too big
	 */
	@Test
	public void testInvalidHeightCheck3() {
		expected = signUpMenu.invalidHeightCheck("3.14");
		assertEquals(expected, actual);
	}
	
	/**
	 * Checks whether invalidHeightCheck will return false if height provided by the user 
	 * is too small
	 */
	@Test
	public void testInvalidHeightCheck4() {
		expected = signUpMenu.invalidHeightCheck("0.46");
		assertEquals(expected, actual);
	}
	
	/**
	 * Checks whether invalidHeightCheck will return true if height provided by the user 
	 * is within the defined limits
	 */
	@Test
	public void testInvalidHeightCheck5() {
		expected = signUpMenu.invalidHeightCheck("1.80");
		assertEquals(expected, actual);
	}
	
	/**
	 * Checks whether invalidPasswordCheck will return false if password provided by the 
	 * user contains a special character
	 */
	@Test
	public void testInvalidPasswordCheck1() {
		expected = signUpMenu.invalidPasswordCheck("paS$word1");
		assertEquals(expected, actual);
	}
	
	/**
	 * Checks whether invalidPasswordCheck will return false if password provided by the 
	 * user does not contain a digit
	 */
	@Test
	public void testInvalidPasswordCheck2() {
		expected = signUpMenu.invalidPasswordCheck("pasSword");
		assertEquals(expected, actual);
	}
	
	/**
	 * Checks whether invalidPasswordCheck will return false if password provided by the 
	 * user does not contain an upper-case letter
	 */
	@Test
	public void testInvalidPasswordCheck3() {
		expected = signUpMenu.invalidPasswordCheck("password1");
		assertEquals(expected, actual);
	}
	
	/**
	 * Checks whether invalidPasswordCheck will return false if password provided by the 
	 * user does not contain a lower-case letter
	 */
	@Test
	public void testInvalidPasswordCheck4() {
		expected = signUpMenu.invalidPasswordCheck("PASSWORD1");
		assertEquals(expected, actual);
	}
	
	/**
	 * Checks whether invalidPasswordCheck will return false if password provided by the 
	 * user contains less than 6 characters
	 */
	@Test
	public void testInvalidPasswordCheck5() {
		expected = signUpMenu.invalidPasswordCheck("pass1");
		assertEquals(expected, actual);
	}
	
	/**
	 * Checks whether invalidPasswordCheck will return true if password provided by the 
	 * user is long enough, contains at least one lower-case letter, one upper-case letter,
	 * one digit, and no other characters
	 */
	@Test
	public void testInvalidPasswordCheck6() {
		expected = signUpMenu.invalidPasswordCheck("passWord1");
		assertEquals(expected, actual);
	}
	
	/**
	 * Checks whether invalidEmailCheck will return false if email provided by the user
	 * does not contain an @ sign
	 */
	@Test
	public void testInvalidEmailCheck1() {
		expected = signUpMenu.invalidEmailCheck("John.Smith");
		assertEquals(expected, actual);
	}
	
	/**
	 * Checks whether invalidEmailCheck will return false if email provided by the user
	 * starts with an @ sign
	 */
	@Test
	public void testInvalidEmailCheck2() {
		expected = signUpMenu.invalidEmailCheck("@john.smith@gmail.com");
		assertEquals(expected, actual);
	}
	
	/**
	 * Checks whether invalidEmailCheck will return false if email provided by the user
	 * ends with an @ sign
	 */
	@Test
	public void testInvalidEmailCheck3() {
		expected = signUpMenu.invalidEmailCheck("John.Smith@");
		assertEquals(expected, actual);
	}
	
	/**
	 * Checks whether invalidEmailCheck will return true if email provided by the user
	 * is of acceptable type
	 */
	@Test
	public void testInvalidEmailCheck4() {
		expected = signUpMenu.invalidEmailCheck("John.Smith@gmail.com");
		assertEquals(expected, actual);
	}

}


