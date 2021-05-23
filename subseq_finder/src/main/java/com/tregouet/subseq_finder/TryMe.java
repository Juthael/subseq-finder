package com.tregouet.subseq_finder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tregouet.subseq_finder.exceptions.SubseqException;
import com.tregouet.subseq_finder.impl.SubseqFinder;

public class TryMe {

	public TryMe() {
	}

	public static void main(String[] args) throws SubseqException {
		System.out.println("**********SubseqFinder**********" + System.lineSeparator());
		System.out.println("Enter a chain of symbols separated by spaces, or press ENTER");
		String input = readString();
		List<List<String>> inputs = new ArrayList<List<String>>();
		while (!input.isEmpty()) {
			while (input.charAt(input.length() - 1) == ' ')
				input = input.substring(0, input.length() - 1);
			while (input.charAt(0) == ' ')
				input = input.substring(1);
			inputs.add(Arrays.asList(input.split(" ")));
			System.out.println(System.lineSeparator() + "Enter another chain of symbols separated by spaces, or press ENTER");
			input = readString();
		}
		ISubseqFinder sFinder = new SubseqFinder(inputs);
		System.out.println("Maximal common subsequences :");
		for (ISymbolSeq seq : sFinder.getMaxCommonSubseqs())
			System.out.println(seq.toString());

	}
	
	private static String readString() {
		String stringInput = null;
		try {
			InputStreamReader reader = new InputStreamReader(System.in);
			BufferedReader input = new BufferedReader(reader);
			stringInput = input.readLine();
		}
		catch (IOException err) {
			System.exit(-1);
		}
		return stringInput;
	}	

}
