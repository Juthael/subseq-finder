package com.tregouet.subseq_finder.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;
import com.tregouet.subseq_finder.ISubseqND;
import com.tregouet.subseq_finder.exceptions.SubseqException;

public class SubseqND implements ISubseqND {

	public static final String ARG_PLACEHOLDER = "_";
	public static final String SEQ_START = "START";
	public static final String SEQ_END = "END";
	
	private final int subseqMaxSize;
	private final int nbOfSequences;
	private final int[][] coords;
	private int coordIndex;
	
	public SubseqND(int subSeqMaxSize, int nbOfSequences) {
		this.subseqMaxSize = subSeqMaxSize;
		this.nbOfSequences = nbOfSequences;
		coords = new int[subSeqMaxSize][nbOfSequences];
		for (int[] coordinate : coords)
			Arrays.fill(coordinate, -1);
		coordIndex = 0;
	}

	public void addNewCoord(int[] newCoord) throws SubseqException {
		if (newCoord == null) {
			throw new SubseqException("SubseqND.addNewCoord(int[]) : parameter is null.");
		}
		else if (coordIndex > subseqMaxSize - 1) {
			throw new SubseqException("SubseqND.addNewCoord(int[]) : the subsequence can't be this long.");
		}
		else if (newCoord.length != nbOfSequences) {
			throw new SubseqException("SubseqND.addNewCoord(int[]) : " + System.lineSeparator() + "the new coordinate "
					+ "should contain "	+ Integer.toString(nbOfSequences) + " values instead of " 
					+ Integer.toString(newCoord.length) + ".");
		}
		else coords[coordIndex] = newCoord;
	}
	
	public int length() {
		return coordIndex;
	}

	public List<String> getSubsequence(String[][] values) {
		List<String> subseq = new ArrayList<String>();
		int[] lastPos = new int[nbOfSequences]; 
		Arrays.fill(lastPos, -1);
		int symbolIdx = 0;
		while (symbolIdx < coordIndex) {
			String nextSymbol = values[0][coords[symbolIdx][0]];
			boolean placeholderNeeded = false;
			int seqIdx = 0;
			while (placeholderNeeded == false && seqIdx < nbOfSequences) {
				if (coords[symbolIdx][seqIdx] > lastPos[seqIdx] + 1)
					placeholderNeeded = true;
				seqIdx++;
			}
			lastPos = coords[symbolIdx];
			if (placeholderNeeded)
				subseq.add(ARG_PLACEHOLDER);
			subseq.add(nextSymbol);
			symbolIdx++;
		}
		return subseq;
	}

	public List<String> getTrimmedSequence(String[][] values) {
		List<String> sequence = getSubsequence(values);
		if (sequence.get(0).equals(SEQ_START))
			sequence.remove(0);
		if (sequence.get(sequence.size() - 1).equals(SEQ_END))
			sequence.remove(sequence.size() -1);
		return sequence;
	}

	public int[][] getSubSqCoordinatesInSq() {
		return coords;
	}

	public boolean isASubseqOf(SubseqND other) {
		boolean isASubseq = true;
		if (coordIndex >= other.length()) {
			isASubseq = false;
		}
		else {
			int seqIndex = 0;
			while (isASubseq && seqIndex < nbOfSequences) {
				
			}	
		}
		return isASubseq;
	}
	
	private Set<Integer> convertArrayIntoSet(int[] array){
		Set<Integer> set = new HashSet<Integer>();
		for (int integer : array)
			set.add(integer);
		return set;
	}

}
