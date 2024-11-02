/**
 * 
 */
package com.daisy.verifyEmailExistance;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 */
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

// Not Working Code
// Its always giving all mails id's as valid as per Domian

public class EmailValidator {

	// Method to validate email address
	public static boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false; // If AddressException occurs, the email is not valid
		}
		return result;
	}

	public static void main(String[] args) {
		ArrayList<String> mails = new ArrayList<>(Arrays.asList("daisy.singh@maantic.com", "singhdmgangian@gmail.com",
				"daisysingh@maantic.com", "singhdmgangiann@gmail.com"

		));

		for (String emailToTest : mails) {
			boolean isValid = isValidEmailAddress(emailToTest);

			if (isValid) {
				System.out.println(emailToTest + " is a valid email address.");
			} else {
				System.out.println(emailToTest + " is not a valid email address.");
			}
		}
	}

}
