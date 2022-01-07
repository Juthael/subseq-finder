package com.tregouet.subseq_finder.impl.utils;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class CoordinatesTest {

	String[][] sequences = new String[3][];
	
	@Before
	public void setUp() throws Exception {
		String[] abc = {"a", "b", "c"};
		String[] adbec = {"a", "b", "d", "e", "c"};
		String[] jabc = {"j", "a", "b", "c"};
		sequences[0] = abc;
		sequences[1] = adbec;
		sequences[2] = jabc;
	} //

	@Test
	public void whenNewtCoordinateRequestedThenAsExpected() {
		boolean asExpected = true;
		int[] coords = {0,0,0};
		int[][] expectedVisitedCoords = expect();
		int[] limits = {3,5,4};
		int seqIdx = 0;
		boolean similar = true;
		int expectationIndex = 1;
		/*
		System.out.println("**********");
		System.out.println(Arrays.deepToString(sequences));
		System.out.println("**********" + System.lineSeparator());
		System.out.println("sequence index : " + seqIdx + System.lineSeparator());
		*/
		do {
			seqIdx = Coordinates.tryNext(coords, limits, seqIdx, similar);
			similar = test(coords, seqIdx);
			if (seqIdx != -1) {
				int[] posTested = Arrays.copyOfRange(coords, 0, seqIdx +1);
				/*
				System.out.println("Pos tested : " + Arrays.toString(posTested));
				System.out.println("Pos expected : " + Arrays.toString(expectedVisitedCoords[expectationIndex]));
				System.out.println(System.lineSeparator());
				*/
				if (!Arrays.equals(posTested, expectedVisitedCoords[expectationIndex]))
					asExpected = false;
				expectationIndex++;
				/*
				System.out.println("sequence index : " + seqIdx);
				StringBuffer sBPos = new StringBuffer();
				StringBuffer sBSymbol = new StringBuffer();
				for (int i=0 ; i<=seqIdx ; i++) {
					sBPos.append("[" + i + "][" + coords[i] + "] ; ");
					sBSymbol.append(sequences[i][coords[i]] + ";");
				}				
				System.out.println("Positions tested : " + sBPos.toString());
				System.out.println("Symbols tested : " + sBSymbol.toString());
				System.out.println("Result : " + similar);
				*/
			}
		} 
		while (seqIdx != -1);
		assertTrue(asExpected);
	}
	
	private boolean test(int[] coords, int seqIdx) {
		if (seqIdx != -1) {
			String refSymbol = sequences[0][coords[0]];
			return refSymbol.equals(sequences[seqIdx][coords[seqIdx]]);
		}
		else return false;
	}
	
	private int[][] expect() {
		int[][] expectations = new int[30][];
		expectations[1] = new int[]{0,0};
		expectations[2] = new int[]{0,0,0};
		expectations[3] = new int[]{0,0,1};
		expectations[4] = new int[]{0,0,2};
		expectations[5] = new int[]{0,0,3};
		expectations[6] = new int[]{0,1};
		expectations[7] = new int[]{0,2};
		expectations[8] = new int[]{0,3};
		expectations[9] = new int[]{0,4};
		expectations[10] = new int[]{1};
		expectations[11] = new int[]{1,0};
		expectations[12] = new int[]{1,1};
		expectations[13] = new int[]{1,1,0};
		expectations[14] = new int[]{1,1,1};
		expectations[15] = new int[]{1,1,2};
		expectations[16] = new int[]{1,1,3};
		expectations[17] = new int[]{1,2};
		expectations[18] = new int[]{1,3};
		expectations[19] = new int[]{1,4};
		expectations[20] = new int[]{2};
		expectations[21] = new int[]{2,0};
		expectations[22] = new int[]{2,1};
		expectations[23] = new int[]{2,2};
		expectations[24] = new int[]{2,3};
		expectations[25] = new int[]{2,4};
		expectations[26] = new int[]{2,4,0};
		expectations[27] = new int[]{2,4,1};
		expectations[28] = new int[]{2,4,2};
		expectations[29] = new int[]{2,4,3};
		return expectations;
	}

}
