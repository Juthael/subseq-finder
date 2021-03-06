package com.tregouet.subseq_finder.impl;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.tregouet.subseq_finder.ICoordSubseq;
import com.tregouet.subseq_finder.ISymbolSeq;

public class CoordSubseqTest {

	private String[][] values = new String[3][];
	//abcd in {abcbd, abcdeb, xafbcdb}
	private ICoordSubseq subseq1 = new CoordSubseq(5, 3);
	//abc in {abcbd, abcdeb, xafbcdb}
	private ICoordSubseq subseq2 = new CoordSubseq(5, 3);
	//abb in {abcbd, abcdeb, xafbcdb}
	private ICoordSubseq subseq3 = new CoordSubseq(5, 3);
	
	@Before
	public void setUp() throws Exception {
		String[] abcbd = {"a", "b", "c", "b", "d"};
		String[] abcdeb = {"a", "b", "c", "d", "e", "b"};
		String[] afbcbd = {"x", "a", "f", "b", "c", "b", "d"};
		values[0] = abcbd;
		values[1] = abcdeb;
		values[2] = afbcbd;
		int[] coordA = {0,0,1};
		int[] coordB1 = {1,1,3};
		int[] coordC = {2,2,4};
		int[] coordB2 = {3,5,5};
		int[] coordD = {4,3,6};
		subseq1.addNewCoord(coordA);
		subseq1.addNewCoord(coordB1);
		subseq1.addNewCoord(coordC);
		subseq1.addNewCoord(coordD);
		subseq2.addNewCoord(coordA);
		subseq2.addNewCoord(coordB1);
		subseq2.addNewCoord(coordC);
		subseq3.addNewCoord(coordA);
		subseq3.addNewCoord(coordB1);
		subseq3.addNewCoord(coordB2);
	}

	@Test
	public void whenSubsequenceRequestedThenExpectedReturnedWithPlaceholders() {
		ISymbolSeq expected1 = new SymbolSeq(Arrays.asList("_", "a", "_", "b", "c", "_", "d", "_"));
		ISymbolSeq expected2 = new SymbolSeq(Arrays.asList("_", "a", "_", "b", "c", "_"));
		ISymbolSeq expected3 = new SymbolSeq(Arrays.asList("_", "a", "_", "b", "_", "b", "_"));
		ISymbolSeq returned1 = subseq1.getSymbolSubseq(values);
		ISymbolSeq returned2 = subseq2.getSymbolSubseq(values);
		ISymbolSeq returned3 = subseq3.getSymbolSubseq(values);
		/*
		System.out.println("subseq1 : " + returned1.toString() + System.lineSeparator() 
			+ "subseq1 expected : " + expected1.toString());
		System.out.println("subseq2 : " + returned2.toString() + System.lineSeparator() 
		+ "subseq2 expected : " + expected2.toString());
		System.out.println("subseq3 : " + returned3.toString() + System.lineSeparator() 
		+ "subseq3 expected : " + expected3.toString());
		*/
		assertTrue(expected1.equals(returned1) && expected2.equals(returned2) && expected3.equals(returned3));
	}
	
	@Test
	public void whenIsSubsequenceOfSpecifiedThenReturnsTrue() {
		boolean asExpected = true;
		if (subseq1.isASubseqOf(subseq2))
			asExpected = false;
		if (subseq1.isASubseqOf(subseq3))
			asExpected = false;
		if (!subseq2.isASubseqOf(subseq1))
			asExpected = false;
		if (subseq2.isASubseqOf(subseq3))
			asExpected = false;
		if (subseq3.isASubseqOf(subseq1))
			asExpected = false;
		if (subseq3.isASubseqOf(subseq2))
			asExpected = false;
		assertTrue(asExpected);
	}

}
