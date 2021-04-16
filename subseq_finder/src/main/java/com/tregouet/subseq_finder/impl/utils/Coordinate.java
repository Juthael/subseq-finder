package com.tregouet.subseq_finder.impl.utils;

import java.util.List;

import org.api.hyperdrive.Coord;

import com.tregouet.subseq_finder.exceptions.SubseqException;

public class Coordinate extends Coord {

	private Coordinate() {
	}
	
	/**
	 * Increments the specified coordinate according to the specified constraints. 
	 * @param coord coordinate to be incremented
	 * @param arrayLimits dotPlot dimensions
	 * @param lowerBound lowerBound[x]+1 is the minimal value of the incremented coordinate in the x^th dimension of the dotPlot
	 * @param upperBounds minimal coordinates of areas in the dotPlot that can't be reached.  
	 * @return false if no other coordinate can be reached
	 */
	public static final boolean advanceInSpecifiedArea(int[] coord, int[] arrayLimits,  int[] lowerBound, 
			List<List<Integer>> upperBounds) {
		for (int i=0 ; i<coord.length ; i++) {
			if (++coord[i] < arrayLimits[i] && !upperBoundReached(coord, upperBounds)) {
				return true;
			}
			else coord[i] = lowerBound[i] + 1;
		}
		return false;
	}
  
	/**
	 * An upper bound is reached by a coordinate if every value in this coordinate is greater than the value at the same 
	 * index in the upper bound. It means that in the n-dimensional array, the specified coordinate belongs to the restricted 
	 * area defined by the bound coordinate.  
	 * @param coords
	 * @param upperBounds
	 * @return
	 * @throws SubseqException
	 */
	private static boolean upperBoundReached(int[] coords, List<List<Integer>> upperBounds) {
		boolean upperBoundsReached = false;
		if (!upperBounds.isEmpty()) {
			int upperBoundIdx = 0;
			while (!upperBoundsReached && upperBoundIdx < upperBounds.size()) {
				List<Integer> upperBound = upperBounds.get(upperBoundIdx);
				boolean thisUpperBoundNotReached = true;
				int coordIdx = 0;
				while (thisUpperBoundNotReached && (coordIdx < coords.length)) {
					thisUpperBoundNotReached = (upperBound.get(coordIdx) > coords[coordIdx]);
					coordIdx++;
				}
				upperBoundsReached = !thisUpperBoundNotReached;
				upperBoundIdx++;
			}
		}
		return upperBoundsReached;
	}

}
