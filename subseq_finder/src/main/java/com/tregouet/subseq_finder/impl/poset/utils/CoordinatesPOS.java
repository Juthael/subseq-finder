package com.tregouet.subseq_finder.impl.poset.utils;

public class CoordinatesPOS {

	private  CoordinatesPOS() {
	}
	
	/**
	 * Returns next explorable coordinates, given the result of the last test. <br>
	 * 
	 * @param coords array to be incremented
	 * @param limits max values +1 at each index of the coords array
	 * @param seqIdx index of the current incrementable value in the coords array
	 * @param similar result of the last test of similarity (or 'true' before first test)
	 * @return the index of the next incrementable value in the coords array
	 */
	public static int tryNext(int[] coords, int[] limits, int seqIdx, boolean similar) {
		if (similar) {
			if (seqIdx < coords.length - 1)
				seqIdx++;
			else seqIdx = tryNextSymbolInCurrentSequence(coords, limits, seqIdx);
		}
		else seqIdx = tryNextSymbolInCurrentSequence(coords, limits, seqIdx);
		return seqIdx;
	}		
	
	private static int tryNextSymbolInPreviousSequence(int[] coords, int[] limits, int seqIdx) {
		coords[seqIdx] = 0;
		seqIdx--;
		seqIdx = tryNextSymbolInCurrentSequence(coords, limits, seqIdx);
		return seqIdx;
	}
	
	private static int tryNextSymbolInCurrentSequence(int[] coords, int[] limits, int seqIdx) {
		if (seqIdx != -1) {
			if (coords[seqIdx] < limits[seqIdx] -1) {
				coords[seqIdx]++;
			}
			else seqIdx = tryNextSymbolInPreviousSequence(coords, limits, seqIdx);
		}
		return seqIdx;
	}

}
