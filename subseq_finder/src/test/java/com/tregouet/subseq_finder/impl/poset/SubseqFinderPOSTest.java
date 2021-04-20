package com.tregouet.subseq_finder.impl.poset;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.tregouet.subseq_finder.ISubseq;

public class SubseqFinderPOSTest {

	private String[][] sequences = new String[3][];
	//"true" coordinates in dotPlot;
	int[] coordA = {0,0,0};
	int[] coordB1 = {1,1,2};
	int[] coordC = {2,2,3};
	int[] coordB2 = {3,5,4};
	int[] coordD = {4,3,5};
	//"false" coordinates in dotPlot ;
	int[] false1 = {0,0,1};
	int[] false2 = {1,2,2};
	int[] false3 = {1,3,5};
	private SubseqFinderPOS subseqFinder;
	
	@Before
	public void setUp() throws Exception {
		String[] abcbd = {"a", "b", "c", "b", "d"};
		String[] abcdeb = {"a", "b", "c", "d", "e", "b"};
		String[] afbcbd = {"a", "f", "b", "c", "b", "d"};
		sequences[0] = abcbd;
		sequences[1] = abcdeb;
		sequences[2] = afbcbd;
		subseqFinder = new SubseqFinderPOS(sequences);
	}

	@Test
	public void whenRequestedThenReturnsAllCommonMaxSubsequences() {
		Set<ISubseq> subseqs = subseqFinder.getSubseqs();
		for (ISubseq subseq : subseqs) {
			System.out.println("*****New Subseq*****");
			System.out.println(Arrays.deepToString(subseq.getCoordinates()));
			System.out.println(subseq.getSubsequence(sequences).toString());
			System.out.println(System.lineSeparator());
		}
		assertTrue(false);
	}		

}
