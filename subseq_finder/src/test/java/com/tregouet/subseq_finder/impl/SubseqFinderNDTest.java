package com.tregouet.subseq_finder.impl;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.tregouet.subseq_finder.exceptions.SubseqException;

public class SubseqFinderNDTest {

	private String[][] sequences = new String[3][];
	//"true" coordinates in dotPlot;
	int[] coordA = {0,0,0};
	int[] coordB1 = {1,1,2};
	int[] coordC = {2,2,3};
	int[] coordB2 = {3,5,4};
	int[] coordD = {4,3,5};
	//"false" coordinates in dotPlot;
	int[] false1 = {0,0,1};
	int[] false2 = {1,2,2};
	int[] false3 = {1,3,5};
	private SubseqFinderND subseqFinder;
	
	@Before
	public void setUp() throws Exception {
		String[] abcbd = {"a", "b", "c", "b", "d"};
		String[] abcdeb = {"a", "b", "c", "d", "e", "b"};
		String[] afbcbd = {"a", "f", "b", "c", "b", "d"};
		sequences[0] = abcbd;
		sequences[1] = abcdeb;
		sequences[2] = afbcbd;
		//subseqFinder = new SubseqFinder3D(sequences);
	}

	@Test
	public void whenSymbolsAtSpecifiedPosAreIdenticalThenReturnsTrueOtherwiseFalse() 
			throws SubseqException {
		boolean asExpected = true;
		if (subseqFinder.isASameSymbolCoord(coordA) == false)
			asExpected = false;
		if (subseqFinder.isASameSymbolCoord(coordB1) == false)
			asExpected = false;
		if (subseqFinder.isASameSymbolCoord(coordB2) == false)
			asExpected = false;
		if (subseqFinder.isASameSymbolCoord(coordC) == false)
			asExpected = false;
		if (subseqFinder.isASameSymbolCoord(coordD) == false)
			asExpected = false;
		if (subseqFinder.isASameSymbolCoord(false1) == true)
			asExpected = false;
		if (subseqFinder.isASameSymbolCoord(false2) == true)
			asExpected = false;
		if (subseqFinder.isASameSymbolCoord(false3) == true)
			asExpected = false;
		assertTrue(asExpected);
	}
	
	@Test
	public void somethingToFindOut() {
		int[] array1 = {1,2,3};
		int[] array2 = Arrays.copyOf(array1, array1.length);
		System.out.println("array1 : " + Arrays.toString(array1));
		System.out.println("array2 : " + Arrays.toString(array2));
		array2[0] = 7;
		System.out.println("*******");
		System.out.println("array1 : " + Arrays.toString(array1));
		System.out.println("array2 : " + Arrays.toString(array2));
		int int0 = 0;
		int int1 = int0;
		int0++;
		System.out.println("int0 : " + int0 + " / int1 : " + int1);
		assertTrue(true);
	}

}
