package com.tregouet.subseq_finder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.subseq_finder.exceptions.SubseqException;

public class SubseqFinder {

	private final String[] value1;
	private final String[] value2;
	private final boolean[][] dotPlot;
	private final Set<Subseq> subseqs = new HashSet<Subseq>();
	
	public SubseqFinder(String[] first, String[] second) {
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
	
	private void setSubseqs() {
		Set<Subseq> subseqs = new HashSet<Subseq>();
		Set<Subseq> nonMaximalSubseqs = new HashSet<Subseq>();
		int seqMaxSize = value1.length;
		if (value2.length > seqMaxSize)
			seqMaxSize = value2.length;
		if (dotPlot != null 
				&& dotPlot[0][0] == true 
				&& value1[0] == Subseq.SEQ_START 
				&& value2[0] == Subseq.SEQ_START) {
			Subseq newSeq = new Subseq(seqMaxSize);
			try {
				subseqs.addAll(continueSubseq(newSeq, 0, 0));
				HERE
			}
			
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