package com.tregouet.subseq_finder.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.tregouet.subseq_finder.ISubseqFinderND;
import com.tregouet.subseq_finder.ISubseqND;
import com.tregouet.subseq_finder.exceptions.SubseqException;

public class SubseqFinderNDTest {

	private String[][] sequences = new String[3][];
	//"true" coordinates in dotPlot;
	int[] coordA = {0,0,0};
	int[] coordB1 = {1,1,2};
	int[] coordC = {2,2,3};
	int[] coordB2 = {3,5,5};
	int[] coordD = {4,3,4};
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
		subseqFinder = new SubseqFinder3D(sequences);
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

}
