package com.tregouet.subseq_finder.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.subseq_finder.ISubseq;
import com.tregouet.subseq_finder.exceptions.SubseqException;

public class Subseq implements ISubseq, Cloneable {

	public static final String ARG_PLACEHOLDER = "_";
	public static final int NOT_COMPARABLE = -2;
	public static final int LESS_THAN = -1;
	public static final int EQUAL_TO = 0;
	public static final int MORE_THAN = 1;
	
	private final int subseqMaxSize;
	private final int nbOfSequences;
	private final int[][] coords;
	private int coordIndex;
	private final List<Set<Integer>> subseqPositionsInSequences;
	
	public Subseq(int subSeqMaxSize, int nbOfSequences) {
		this.subseqMaxSize = subSeqMaxSize;
		this.nbOfSequences = nbOfSequences;
		coords = new int[subSeqMaxSize][nbOfSequences];
		for (int[] coordinate : coords)
			Arrays.fill(coordinate, -1);
		coordIndex = 0;
		subseqPositionsInSequences = new ArrayList<Set<Integer>>();
		for (int i=0 ; i < nbOfSequences ; i++) {
			subseqPositionsInSequences.add(new HashSet<Integer>());
		}
	}
	
	private Subseq(int subSeqMaxSize, int nbOfSequences, int[][] coords, int coordIndex, List<Set<Integer>> subsetPositionsInSequences) {
		this.subseqMaxSize = subSeqMaxSize;
		this.nbOfSequences = nbOfSequences;
		this.coords = coords;
		this.coordIndex = coordIndex;
		this.subseqPositionsInSequences = subsetPositionsInSequences;
	}

	public void addNewCoord(int[] newCoord) {
		coords[coordIndex] = newCoord;
		coordIndex++;
		for (int i=0 ; i < nbOfSequences ; i++)
			subseqPositionsInSequences.get(i).add(newCoord[i]);
	}
	
	@Override
	public ISubseq clone() {
		ISubseq subseqClone;
		int[][] coordsClone = new int[coords.length][];
		for (int i=0 ; i<coords.length ; i++) {
			coordsClone[i] = Arrays.copyOf(coords[i], coords[i].length);
		}
		List<Set<Integer>> subseqPosInSeqClone = new ArrayList<Set<Integer>>();
		for (Set<Integer> pos : subseqPositionsInSequences) {
			subseqPosInSeqClone.add(new HashSet<Integer>(pos));
		}
		subseqClone = new Subseq(subseqMaxSize, nbOfSequences, coordsClone, coordIndex, subseqPosInSeqClone);
		return subseqClone;
	}

	public int compareTo(ISubseq other) {
		if (this.equals(other))
			return EQUAL_TO;
		if (this.isASubseqOf(other))
			return LESS_THAN;
		if (other.isASubseqOf(this))
			return MORE_THAN;
		return NOT_COMPARABLE;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subseq other = (Subseq) obj;
		if (coordIndex != other.coordIndex)
			return false;
		if (subseqPositionsInSequences == null) {
			if (other.subseqPositionsInSequences != null)
				return false;
		} else if (!subseqPositionsInSequences.equals(other.subseqPositionsInSequences))
			return false;
		return true;
	}
	
	public int[][] getCoordinates() {
		return coords;
	}	

	public List<Set<Integer>> getSubseqPositionsInSeq(){
		return subseqPositionsInSequences;
	}
	
	public List<String> getSubsequence(String[][] sequences) {
		List<String> subseq = new ArrayList<String>();
		if (coordIndex > 0) {
			boolean placeholderNeeded = false;
			//add common symbols with placeholder symbols inserted when needed
			int[] lastPos = new int[nbOfSequences]; 
			Arrays.fill(lastPos, -1);
			int seqIdx;
			int symbolIdx = 0;
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
			//check if last symbol is a placeholder symbol
			boolean terminateWithPlaceholder = false;
			seqIdx = 0;
			while (terminateWithPlaceholder == false && seqIdx < nbOfSequences) {
				terminateWithPlaceholder = (coords[coordIndex - 1][seqIdx] != (sequences[seqIdx].length - 1));
				seqIdx++;
			}
			if (terminateWithPlaceholder)
				subseq.add(ARG_PLACEHOLDER);	
		}
		return subseq;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + coordIndex;
		result = prime * result + 
				((subseqPositionsInSequences == null) ? 0 : subseqPositionsInSequences.hashCode());
		return result;
	}

	public boolean isASubseqOf(ISubseq other) {
		boolean isASubsetOf = true;
		if (other.length() > this.length()) {
			List<Set<Integer>> otherSubPosInSeq = other.getSubseqPositionsInSeq();
			int seqIndex = 0;
			while (isASubsetOf == true && seqIndex < nbOfSequences) {
				isASubsetOf = (otherSubPosInSeq.get(seqIndex).containsAll(subseqPositionsInSequences.get(seqIndex)));
				seqIndex++;
			}	
		}
		else isASubsetOf = false;
		return isASubsetOf;
	}

	public int length() {
		return coordIndex;
	}

}
