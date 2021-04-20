package com.tregouet.subseq_finder.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.subseq_finder.ISubseq;
import com.tregouet.subseq_finder.ISubseqFinder;
import com.tregouet.subseq_finder.exceptions.SubseqException;

public abstract class SubseqFinder implements ISubseqFinder {

	protected final String[][] sequences;
	protected final int subseqMaxSize;
	protected final Set<ISubseq> subsequences = new HashSet<ISubseq>();
	
	public SubseqFinder(String[][] sequences) throws SubseqException {
		try {
			testParameterValidity(sequences);
		} catch (Exception e) {
			throw new SubseqException("SubseqFinder(String[][]) : " + System.lineSeparator() + e.getMessage());
		}
		this.sequences = sequences;
		subseqMaxSize = setSubseqMaxSize();
	}

	public Set<List<String>> getStringSubseqs() {
		Set<List<String>> stringSubseqs = new HashSet<List<String>>();
		for (ISubseq subseq : subsequences) {
			stringSubseqs.add(subseq.getSubsequence(sequences));
		}
		return stringSubseqs;
	}

	public Set<ISubseq> getSubseqs() {
		return subsequences;
	}
	
	private int setSubseqMaxSize() {
		int maxSize = sequences[0].length;
		for (int i=1 ; i < sequences.length ; i++) {
			if (sequences[i].length < maxSize)
				maxSize = sequences[i].length;
		}
		return maxSize;
	}
	
	private void testParameterValidity(String[][] sequences) throws SubseqException {
		if ((sequences == null) || (sequences.length < 2))
			throw new SubseqException("SubseqFinderPOS.setParameterValidity(int[][]) : parameter cannot be null nor "
					+ "of length < 2");
		for (int i=0 ; i < sequences.length ; i++) {
			if (sequences[i].length == 0)
				throw new SubseqException("SubseqFinderPOS.setParameterValidity(int[][]) : "
						+ "compared sequences cannot be empty");
		}
	}	

}
