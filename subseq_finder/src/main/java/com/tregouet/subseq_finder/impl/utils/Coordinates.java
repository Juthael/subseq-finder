package com.tregouet.subseq_finder.impl.utils;

import java.util.List;

import org.api.hyperdrive.Coord;

import com.tregouet.subseq_finder.exceptions.SubseqException;

/**
 * 
 * @author Gael Tregouet
 *
 */
public class Coordinates extends Coord {

	private Coordinates() {
	}
	
	/**
	 * Increments the specified coordinates according to the specified constraints. <br>
	 * 
	 * @param coord coordinates to be incremented
	 * @param arrayLimits dotPlot dimensions
	 * @param lowerBound lowerBound[x]+1 is the minimal value of the incremented coordinates in the x^th dimension of the dotPlot
	 * @param upperBounds list of minimal coordinates of areas in the dotPlot that can't be reached.  
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
	 * Checks whether the specified constraints are respected or not. <br>
	 * 
	 * An upper bound is reached by a list of coordinates if every coordinate is greater than the coordinate at the same 
	 * index in the upper bound. It means that in the n-dimensional array, the specified coordinates belongs to the restricted 
	 * area defined by the bound coordinates.  
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
				boolean thisUpperBoundReached = true;
				int coordIdx = 0;
				while (thisUpperBoundReached && (coordIdx < coords.length)) {
					thisUpperBoundReached = !(upperBound.get(coordIdx) > coords[coordIdx]);
					coordIdx++;
				}
				upperBoundsReached = thisUpperBoundReached;
				upperBoundIdx++;
			}
		}
		return upperBoundsReached;
	}

}
