package com.daisy.verifyEmailExistance;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
	public static void main(String[] args) {
		// // List of domains to check
		ArrayList<String> domains = new ArrayList<>(
				Arrays.asList("gmail.com", "example.com", "nonexistentdomain.xyz", "wipro.com", "wwwipro.com",
						"google.com", "maantic.com", "maaantic.com", "sukhda.com", "codyskill.com", "appiantips.com",
						"abcd.com", "efgh.com", "gmail.com", "gmaiil.com", "gmaiil.com", "maantic.com", "maantic.com"

				));

		// Loop through each domain and check if it's valid
		for (String domain : domains) {
			boolean isValid = DomianExistanceChecker.isValidDomain(domain); // Call the static method from
																			// DomainValidator class
			if (isValid) {
				System.out.println(domain + " is a valid and running domain.");
			} else {
				System.out.println(domain + " is not a valid or running domain.");
			}
		}
	}
}
