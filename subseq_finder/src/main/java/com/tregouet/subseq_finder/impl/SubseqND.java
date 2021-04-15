package com.tregouet.subseq_finder.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private final List<Set<Integer>> subsetPositionsInSequences;
	
	public SubseqND(int subSeqMaxSize, int nbOfSequences) {
		this.subseqMaxSize = subSeqMaxSize;
		this.nbOfSequences = nbOfSequences;
		coords = new int[subSeqMaxSize][nbOfSequences];
		for (int[] coordinate : coords)
			Arrays.fill(coordinate, -1);
		coordIndex = 0;
		subsetPositionsInSequences = new ArrayList<Set<Integer>>();
		for (int i=0 ; i < nbOfSequences ; i++) {
			subsetPositionsInSequences.add(new HashSet<Integer>());
		}
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
		else {
			coords[coordIndex] = newCoord;
			coordIndex++;
			for (int i=0 ; i < nbOfSequences ; i++) {
				subsetPositionsInSequences.get(i).add(newCoord[i]);
			}
		}
	}
	
	public int[][] getCoordinates() {
		return coords;
	}

	public List<Set<Integer>> getSubseqPositionsInSeq(){
		return subsetPositionsInSequences;
	}

	public List<String> getSubsequence(String[][] sequences) {
		List<String> subseq = new ArrayList<String>();
		boolean placeholderNeeded = false;
		//check if first symbol is a placeholder
		String firstValue = sequences[0][0];
		int valueIdx = 1;
		while (!placeholderNeeded && valueIdx < sequences.length) {
			if (!sequences[valueIdx][0].equals(firstValue))
				placeholderNeeded = true;
			valueIdx++;
		}
		//add common symbols with placeholders when needed
		int[] lastPos = new int[nbOfSequences]; 
		Arrays.fill(lastPos, -1);
		int symbolIdx = 0;
		int seqIdx;
		while (symbolIdx < coordIndex) {
			String nextSymbol = sequences[0][coords[symbolIdx][0]];
			placeholderNeeded = false;
			seqIdx = 0;
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
		//check if last common symbol is a placeholder
		boolean terminateWithPlaceholder = false;
		seqIdx = 0;
		while (terminateWithPlaceholder == false && seqIdx < nbOfSequences) {
			terminateWithPlaceholder = (coords[coordIndex - 1][seqIdx] != (sequences[seqIdx].length - 1));
			seqIdx++;
		}
		if (terminateWithPlaceholder)
			subseq.add(ARG_PLACEHOLDER);
		return subseq;
	}

	public List<String> getTrimmedSequence(String[][] sequences) {
		List<String> sequence = getSubsequence(sequences);
		if (sequence.get(0).equals(SEQ_START))
			sequence.remove(0);
		if (sequence.get(sequence.size() - 1).equals(SEQ_END))
			sequence.remove(sequence.size() -1);
		return sequence;
	}
	
	public boolean isASubseqOf(ISubseqND other) {
		boolean isASubsetOf = true;
		List<Set<Integer>> otherSubPosInSeq = other.getSubseqPositionsInSeq();
		int seqIndex = 0;
		while (isASubsetOf == true && seqIndex < nbOfSequences) {
			isASubsetOf = (otherSubPosInSeq.get(seqIndex).containsAll(subsetPositionsInSequences.get(seqIndex)));
			seqIndex++;
		}
		return isASubsetOf;
	}

	public int length() {
		return coordIndex;
	}

}
