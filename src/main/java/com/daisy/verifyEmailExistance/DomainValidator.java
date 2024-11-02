package com.daisy.verifyEmailExistance;

import org.xbill.DNS.Lookup;
import org.xbill.DNS.MXRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.Type;
import org.xbill.DNS.TextParseException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

// Not Giving Proper result on email Address
// Validating Most of the domians Properly
public class DomainValidator {

	public static boolean isValidDomain(String email) {
		if (email == null || !email.contains("@")) {
			System.err.println("Invalid email format: " + email);
			return false; // Early return for invalid format
		}

		String domain = email.substring(email.indexOf('@') + 1);
		try {
			// Lookup MX records
			Lookup lookup = new Lookup(domain, Type.MX);
			Record[] records = lookup.run();

			// Check if the lookup was successful and we have records
			if (lookup.getResult() == Lookup.SUCCESSFUL && records != null && records.length > 0) {
				for (Record record : records) {
					if (record instanceof MXRecord) { // Check if the record is an instance of MXRecord
						MXRecord mxRecord = (MXRecord) record;
						String mxHost = mxRecord.getTarget().toString().replaceFirst("\\.$", ""); // Remove trailing dot

						// Log MX record found
						System.out.println("Found MX record: " + mxHost);

						// Attempt to connect to the SMTP server
						if (checkSMTP(mxHost)) {
							return true; // Valid domain
						} else {
							System.out.println("SMTP check failed for: " + mxHost);
						}
					}
				}
			} else {
				System.err.println("No MX records found for domain: " + domain);
			}
		} catch (TextParseException e) {
			System.err.println("Invalid domain format: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("An error occurred during domain validation: " + e.getMessage());
		}
		return false; // Invalid domain
	}

	private static boolean checkSMTP(String mxHost) {
		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress(mxHost, 25), 2000); // 2 seconds timeout
			System.out.println("Successfully connected to SMTP server: " + mxHost);
			return true; // Connection successful
		} catch (SocketTimeoutException e) {
			System.err.println("Connection timed out for SMTP server: " + mxHost);
		} catch (IOException e) {
			System.err.println("Unable to connect to SMTP server: " + mxHost + " - " + e.getMessage());
		}
		return false; // Connection failed
	}

	public static void main(String[] args) {

		String[] s = { "abcd.efgh@abcd.com", "abcd.efgh@efgh.com", "singhdmgangian@gmail.com",
				"singhdmgangian@gmaiil.com", "singhdmgangiian@gmaiil.com", "daisy.singh@maantic.com",
				"daisysingh@maantic.com" };

		ArrayList<String> mails = new ArrayList<>();
		for (int i = 0; i < s.length; i++) {
			mails.add(s[i]);
		}

//				Arrays.asList("singhdmgangian@gmail.com", "test@gmail.com", // Valid Gmail
//						"user@nonexistentdomain.xyz", // Invalid
//						"invalid-email" // Invalid
//				));

		for (String email : mails) {
			boolean isValid = isValidDomain(email);
			if (isValid) {
				System.out.println(email + " has a valid domain for email.");
			} else {
				System.out.println(email + " does not have a valid domain for email.");
			}
		}
	}
}
