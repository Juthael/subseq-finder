package com.tregouet.subseq_finder.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tregouet.subseq_finder.ISubseqND;

public class SubseqNDTest {

	private String[][] values = new String[3][];
	//abcd in {abcbd, abcdeb, afbcdb}
	private ISubseqND subseq1 = new SubseqND(5, 3);
	//abc in {abcbd, abcdeb, afbcdb}
	private ISubseqND subseq2 = new SubseqND(5, 3);
	//abb in {abcbd, abcdeb, afbcdb}
	private ISubseqND subseq3 = new SubseqND(5, 3);
	
	@Before
	public void setUp() throws Exception {
		String[] abcbd = {"a", "b", "c", "b", "d"};
		String[] abcdeb = {"a", "b", "c", "d", "e", "b"};
		String[] afbcbd = {"a", "f", "b", "c", "b", "d"};
		values[0] = abcbd;
		values[1] = abcdeb;
		values[2] = afbcbd;
		int[] coordA = {0,0,0};
		int[] coordB1 = {1,1,2};
		int[] coordC = {2,2,3};
		int[] coordB2 = {3,5,5};
		int[] coordD = {4,3,4};
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
	public void whenSubsequenceRequestedThenExpectedReturned() {
		List<String> expected1 = new ArrayList<String>(Arrays.asList("a", "_", "b", "c", "_", "d", "_"));
		List<String> expected2 = new ArrayList<String>(Arrays.asList("a", "_", "b", "c", "_"));
		List<String> expected3 = new ArrayList<String>(Arrays.asList("a", "_", "b", "_", "b", "_"));
		List<String> returned1 = subseq1.getSubsequence(values);
		List<String> returned2 = subseq2.getSubsequence(values);
		List<String> returned3 = subseq3.getSubsequence(values);
		System.out.println("subseq1 : " + returned1.toString() + System.lineSeparator() 
			+ "subseq1 expected : " + expected1.toString());
		System.out.println("subseq2 : " + returned2.toString() + System.lineSeparator() 
		+ "subseq2 expected : " + expected2.toString());
		System.out.println("subseq3 : " + returned3.toString() + System.lineSeparator() 
		+ "subseq3 expected : " + expected3.toString());
		assertTrue(expected1.equals(returned1) && expected2.equals(returned2) && expected3.equals(returned3));
	}
	
	@Test
	public void whenSubsequenceOfSpecifiedThenReturnsTrue() {
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
