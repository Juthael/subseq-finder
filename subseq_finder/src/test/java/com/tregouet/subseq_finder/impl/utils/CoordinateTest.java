package com.tregouet.subseq_finder.impl.utils;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CoordinateTest {

	@Before
	public void setUp() throws Exception {
		int[] coord = {1,1,1};
		int[] lowerbound = coord;
		List<List<Integer>> upperBounds = new ArrayList<List<Integer>>();
		int[] upperBound1Array = {2,3,3};
		int[] upperBound2Array = {3,2,2};
		List<Integer> upperBound1 = new ArrayList(upperBound1Array);
	}

	@Test
	public void whenCoordIncrementedThenAsExpected() {
		
	}

}
