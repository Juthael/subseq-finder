package com.tregouet.subseq_finder.impl.dot_plot.utils;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tregouet.subseq_finder.impl.dot_plot.utils.CoordinatesDP;

public class CoordinatesDPTest {

	int[] coord = {1,1,1};
	int[] dotPlotLimits = {4,4,4};
	int[] lowerBound = {0,0,0};
	List<List<Integer>> upperBounds = new ArrayList<List<Integer>>();
	int[][] expected = new int[22][];
	
	@Before
	public void setUp() throws Exception {
		int[] upperBound1Array = {2,3,3};
		int[] upperBound2Array = {3,2,2};
		List<Integer> upperBound1 = new ArrayList<Integer>();
		List<Integer> upperBound2 = new ArrayList<Integer>();
		for (int coordVal : upperBound1Array)
			upperBound1.add(coordVal);
		for (int coordVal : upperBound2Array)
			upperBound2.add(coordVal);
		upperBounds.add(upperBound1);
		upperBounds.add(upperBound2);
		expect();
	}

	@Test
	public void whenCoordIncrementedThenAsExpected() {
		boolean asExpected = true;
		System.out.println(CoordinatesDP.toString(coord));
		boolean outOfDotPlot = false;
		int expectationIndex = 1;
		while (!outOfDotPlot && asExpected) {
			outOfDotPlot = !CoordinatesDP.advanceInSpecifiedArea(coord, dotPlotLimits, lowerBound, upperBounds);
			if (!outOfDotPlot) {
				System.out.println(CoordinatesDP.toString(coord));
				asExpected = (Arrays.equals(coord, expected[expectationIndex]));
			}
			expectationIndex++;
		}
		assertTrue(asExpected);
	}
	
	private void expect() {
		int[] expected0 = {1,1,1};
		int[] expected1 = {2,1,1};
		int[] expected2 = {3,1,1};
		int[] expected3 = {1,2,1};
		int[] expected4 = {2,2,1};
		int[] expected5 = {3,2,1};
		int[] expected6 = {1,3,1};
		int[] expected7 = {2,3,1};
		int[] expected8 = {3,3,1};
		int[] expected9 = {1,1,2};
		int[] expected10 = {2,1,2};
		int[] expected11 = {3,1,2};
		int[] expected12 = {1,2,2};
		int[] expected13 = {2,2,2};
		int[] expected14 = {1,3,2};
		int[] expected15 = {2,3,2};
		int[] expected16 = {1,1,3};
		int[] expected17 = {2,1,3};
		int[] expected18 = {3,1,3};
		int[] expected19 = {1,2,3};
		int[] expected20 = {2,2,3};
		int[] expected21 = {1,3,3};
		expected[0] = expected0;
		expected[1] = expected1;
		expected[2] = expected2;
		expected[3] = expected3;
		expected[4] = expected4;
		expected[5] = expected5;
		expected[6] = expected6;
		expected[7] = expected7;
		expected[8] = expected8;
		expected[9] = expected9;
		expected[10] = expected10;
		expected[11] = expected11;
		expected[12] = expected12;
		expected[13] = expected13;
		expected[14] = expected14;
		expected[15] = expected15;
		expected[16] = expected16;
		expected[17] = expected17;
		expected[18] = expected18;
		expected[19] = expected19;
		expected[20] = expected20;
		expected[21] = expected21;
	}

}
