package practicePackage;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SearchApp {
	//global scanner	
	static Scanner scanner = new Scanner(System.in);
	
	public static int[] badMatchTable(String p) {
		int maxASCII = 256;
		int[] table = new int[maxASCII];
		int patternLength = p.length();
		Arrays.fill(table, -1);
		
		//this navigates to each index in table using the pattern char, and sets it to i (last occurrence of that char) 
		for (int i = 0; i < patternLength; i++) {
			table[p.charAt(i)] = i;
		}
		return table;
	}
	
	public static int badMatchProtocol(String t, String p, int[] bmp) {
		int tLength = t.length();
	    int pLength = p.length();
	    
	    // Start at the beginning of the text
	    int tIndex = 0; 
	    int pIndex;
	    // Makes sure pattern fits in the remaining text
	    while (tIndex <= tLength - pLength) {
	    	// Compare pattern with text from right to left
	    	for (pIndex = pLength - 1; pIndex >= 0; pIndex--) {
	    		if (t.charAt(tIndex + pIndex) != p.charAt(pIndex)) {
	    			// Mismatch found, calculate shift
	    			int badCharShift = bmp[t.charAt(tIndex + pIndex)];
	    			// Makes sure shift is at least 1
	    			tIndex += Math.max(1, pIndex - badCharShift); 
	    			break; // Exit the inner loop
	    		}
	    	}

	    	if (pIndex < 0) {
	    		// If pIndex < 0, a match is found
	    		return tIndex; 
	    	}
	    }
	    //if while loop doesn't yield a result, return -1
	    return -1;
	}
	
private static void boyerMoore (String[] t, String p) {
	int[] badMatchTable = badMatchTable(p);
	ArrayList<Integer> finalIndex = new ArrayList<>();
	boolean matchFound = false;
	
	int pLength = p.length();
	int tLength = t.length;
	for (int i = 0; i < tLength; i++) {
		//if pattern is longer than text, no match.
		if (pLength <= t[i].length()) {
			//initiate bad match protocol
			int index = badMatchProtocol(t[i], p, badMatchTable);
			if (index != -1) {
				matchFound = true;
                System.out.println("Match found!\nState: " + t[i] + ", at index " + i + "\n");
			}		
		}
	}
	if (!matchFound) {
		System.out.println("No match found. Remember to check your spelling and capitalization!");
	}
}

	public static void main(String[] args) {		
	    String[] States = {
	    	"Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware",
	    	"Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky",
	    	"Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri",
	    	"Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York",
	    	"North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island",
	    	"South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia",
	    	"Washington", "West Virginia", "Wisconsin", "Wyoming"
	    };
		
		int choice;
		do {
			System.out.println("Menu:");
			System.out.println("1. Display the text");
			System.out.println("2. Search");
			System.out.println("3. Exit program");
			System.out.print("\nEnter your choice: ");
			
			choice = scanner.nextInt();
			scanner.nextLine(); 
		
		switch (choice) {
			case 1: 
				int lengthOfArray = States.length;
				for (int i = 0; i < lengthOfArray; i++) {
					System.out.print(States[i] + " ");
				}
				System.out.println("\n");
				break;
			case 2: 
				//implement boyer-moore algo
				System.out.println("Please enter the state you would like to search: \n");
				
				String userWord = scanner.nextLine();
				//if input is empty, break
				if (userWord.isEmpty()) {
					System.out.println("No content entered, returning to main menu.\n");
					break;
				}
				
				boyerMoore(States, userWord);
				break;
			case 3: 
				System.out.println("Terminating program.");
				break;
			default: 
				System.out.println("Invalid choice, please try again.");
			} 
		}
		while (choice != 3);
		scanner.close();
	}	
}
