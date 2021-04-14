package com.tregouet.subseq_finder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.subseq_finder.exceptions.SubseqException;

public class SubseqFinderDeprec {

	// first string of symbols. First symbol is "START", last one is "END".
	private final String[] value0;
	
	// second string of symbols. First symbol is "START", last one is "END".
	private final String[] value1;
	
	
	// dotPlot[x][y] == true if value[0].equals(value[1]);
	private final boolean[][] dotPlot;
	
	// all subsequences found
	private final Set<Subseq> subseqs = new HashSet<Subseq>();
	
	public SubseqFinderDeprec(String[] value0, String[] value1) throws SubseqException {
		this.value0 = new String[value0.length + 2];
		this.value1 = new String[value1.length + 2];
		this.value0[0] = "START";
		this.value1[0] = "START";
		this.value0[this.value0.length - 1] = "END";
		this.value1[this.value1.length - 1] = "END";
		System.arraycopy(value0, 0, this.value0, 1, value0.length);
		System.arraycopy(value1, 0, this.value1, 1, value1.length);
		dotPlot = new boolean[value0.length + 2][value1.length + 2];
		for (int f=0 ; f < this.value0.length ; f++) {
			for (int s=0 ; s < this.value1.length ; s++) { 
				if (this.value0[f].equals(this.value1[s]))
					dotPlot[f][s] = true;
			}
		}
		setSubseqs();
	}
	
	public Set<Subseq> getSubseqs(){
		return subseqs;
	}
	
	public void printSubsetStrings() throws SubseqException {
		for (Subseq subseq : subseqs) {
			System.out.println(subseq.getSequence(value0, 0));
		}
	}
	
	private void setSubseqs() throws SubseqException {
		List<Subseq> allSubseqs = new ArrayList<Subseq>();
		//a subsequence can't be longer than the shortest sequence
		int seqMaxSize;
		if (value0.length < value1.length)
			seqMaxSize = value0.length;
		else seqMaxSize = value1.length;
		//initial value of the array must be START
		if (dotPlot != null && dotPlot[0][0] == true) {
			Subseq newSeq = new Subseq(seqMaxSize);
			try {
				allSubseqs.addAll(continueSubseq(newSeq, 0, 0));
			}
			catch (SubseqException e) {
				throw new SubseqException("SubseqFinderDeprec.setSubseqs() : " + System.lineSeparator() + e.getMessage());
			}
		}
		else throw new SubseqException("SubseqFinderDeprec.setSubseqs() : initial dotPlot values are invalid");
		Set<Integer> nonMaxIndexes = new HashSet<Integer>();
		for (int i = 0; i < allSubseqs.size() ; i++) {
			if (!nonMaxIndexes.contains(i)) {
				for (int j=i+1 ; j < allSubseqs.size() ; j++) {
					if (!nonMaxIndexes.contains(j)) {
						if (allSubseqs.get(i).isASubseqOf(allSubseqs.get(j))) {
							nonMaxIndexes.add(i);
						}
						else if (allSubseqs.get(j).isASubseqOf(allSubseqs.get(i))) {
							nonMaxIndexes.add(j);
						}
					}
				}
			}
		}
		for (int k = 0 ; k < allSubseqs.size() ; k++) {
			if (!nonMaxIndexes.contains(k))
				subseqs.add(allSubseqs.get(k));
		}
	}
	
	private Set<Subseq> continueSubseq(Subseq currentSubseq, int newElemVal0Coord, int newElemVal1Coord) throws SubseqException {
		Set<Subseq> subSequences = new HashSet<Subseq>();
		Subseq currSubseq = currentSubseq.clone();
		try {
			currSubseq.addNewCoord(newElemVal0Coord, newElemVal1Coord);
		}
		catch (SubseqException e) {
			throw new SubseqException("SubseqFinderDeprec.continueSubseq() : " + System.lineSeparator() + e.getMessage());
		}
		if ((dotPlot.length < newElemVal0Coord + 1) 
				&& (dotPlot[newElemVal0Coord].length < newElemVal1Coord + 1)
				&& (dotPlot[newElemVal0Coord +1][newElemVal1Coord + 1] == true)) {
			subSequences.addAll(continueSubseq(currSubseq, newElemVal0Coord +1, newElemVal1Coord +1));
		}
		else {
			boolean nextElemFound = false;
			for (int nextElemVal0Coord = newElemVal0Coord + 1 ; nextElemVal0Coord < dotPlot.length ; nextElemVal0Coord++) {
				for (int nextElemVal1Coord = newElemVal1Coord + 1 ; nextElemVal1Coord < dotPlot[nextElemVal0Coord].length ; 
						nextElemVal1Coord++) {
					if (dotPlot[nextElemVal0Coord][nextElemVal1Coord] == true) {
						nextElemFound = true;
						try {
							subSequences.addAll(continueSubseq(currSubseq, nextElemVal0Coord, nextElemVal1Coord));
						}
						catch (SubseqException e) {
							throw new SubseqException("SubseqFinderDeprec.continueSubseq() : " + System.lineSeparator() 
							+ e.getMessage());
						}
					}
				}
			}
			if (!nextElemFound) {
				subSequences.add(currSubseq);
			}
		}
		return subSequences;
	}
}