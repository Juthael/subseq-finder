package com.tregouet.subseq_finder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.subseq_finder.exceptions.SubseqException;

public class SubseqFinder {

	private final String[] value1;
	private final String[] value2;
	private final boolean[][] dotPlot;
	private final Set<Subseq> subseqs = new HashSet<Subseq>();
	
	public SubseqFinder(String[] first, String[] second) throws SubseqException {
		this.value1 = new String[first.length + 2];
		this.value2 = new String[second.length + 2];
		this.value1[0] = "START";
		this.value2[0] = "START";
		this.value1[this.value1.length - 1] = "END";
		this.value2[this.value2.length - 1] = "END";
		System.arraycopy(first, 0, this.value1, 1, first.length);
		System.arraycopy(second, 0, this.value2, 1, second.length);
		dotPlot = new boolean[first.length + 2][second.length + 2];
		for (int f=0 ; f < this.value1.length ; f++) {
			for (int s=0 ; s < this.value2.length ; s++) { 
				if (this.value1[f].equals(this.value2[s]))
					dotPlot[f][s] = true;
			}
		}
		setSubseqs();
	}
	
	private void setSubseqs() throws SubseqException {
		List<Subseq> allSubseqs = new ArrayList<Subseq>();
		int seqMaxSize = value1.length;
		if (value1.length > value2.length)
			seqMaxSize = value1.length;
		else seqMaxSize = value2.length;
		if (dotPlot != null && dotPlot[0][0] == true) {
			Subseq newSeq = new Subseq(seqMaxSize);
			try {
				allSubseqs.addAll(continueSubseq(newSeq, 0, 0));
			}
			catch (SubseqException e) {
				throw new SubseqException("SubseqFinder.setSubseqs() : " + System.lineSeparator() + e.getMessage());
			}
		}
		else throw new SubseqException("SubseqFinder.setSubseqs() : initial dotPlot values are invalid");
		Set<Integer> nonMaxIndexes = new HashSet<Integer>();
		for (int i = 0; i < subseqs.size() ; i++) {
			if (!nonMaxIndexes.contains(i)) {
				for (int j=i+1 ; j < subseqs.size() ; j++) {
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
	
	private Set<Subseq> continueSubseq(Subseq currentSubseq, int newElemVal1Coord, int newElemVal2Coord) throws SubseqException {
		Set<Subseq> subSequences = new HashSet<Subseq>();
		Subseq currSubseq = currentSubseq.clone();
		try {
			currSubseq.addNewCoord(newElemVal1Coord, newElemVal2Coord);
		}
		catch (SubseqException e) {
			throw new SubseqException("SubseqFinder.continueSubseq() : " + System.lineSeparator() + e.getMessage());
		}
		boolean nextElemFound = false;
		for (int nextElemVal1Coord = newElemVal1Coord + 1 ; nextElemVal1Coord < dotPlot.length ; nextElemVal1Coord++) {
			for (int nextElemVal2Coord = newElemVal2Coord + 1 ; nextElemVal2Coord < dotPlot[nextElemVal1Coord].length ; 
					nextElemVal2Coord++) {
				if (dotPlot[nextElemVal1Coord][nextElemVal2Coord] == true) {
					nextElemFound = true;
					try {
						subSequences.addAll(continueSubseq(currSubseq, nextElemVal1Coord, nextElemVal2Coord));
					}
					catch (SubseqException e) {
						throw new SubseqException("SubseqFinder.continueSubseq() : " + System.lineSeparator() 
						+ e.getMessage());
					}
				}
			}
		}
		if (!nextElemFound)
			subSequences.add(currSubseq);
		return subSequences;
	}
}