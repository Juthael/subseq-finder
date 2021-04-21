package com.tregouet.subseq_finder.impl;

import java.util.Iterator;
import java.util.List;

import com.tregouet.subseq_finder.ISymbolSeq;

public class SymbolSeq implements ISymbolSeq {

	private List<String> sequence;
	private int length;
	
	public SymbolSeq(List<String> sequence) {
		this.sequence = sequence;
		length = sequence.size();
	}

	public Integer compareTo(ISymbolSeq other) {
		Integer comparison = ISymbolSeq.NOT_COMPARABLE;
		if (this.equals(other))
			comparison = ISymbolSeq.EQUAL_TO;
		else if (isSubsetOf(this, other))
			comparison = ISymbolSeq.SUBSEQ_OF;
		else if(isSubsetOf(other, this))
			comparison = ISymbolSeq.SUPERSEQ_OF;
		return comparison;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SymbolSeq other = (SymbolSeq) obj;
		if (sequence == null) {
			if (other.sequence != null)
				return false;
		} else if (!sequence.equals(other.sequence))
			return false;
		return true;
	}

	public List<String> getStringSequence() {
		return sequence;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sequence == null) ? 0 : sequence.hashCode());
		return result;
	}

	public boolean isAbstract() {		
		return sequence.contains(PLACEHOLDER);
	}
	
	public int length() {
		return length;
	}
	
	private boolean isSubsetOf(ISymbolSeq seq1, ISymbolSeq seq2) {
		boolean subsetOf = false;
		if (seq1.length() < seq2.length()) {
			Iterator<String> shortestIte = seq1.getStringSequence().iterator();
			Iterator<String> longestIte = seq2.getStringSequence().iterator();
			String shortestNextSymbol = shortestIte.next();
			String longestNextSymbol;
			while (shortestIte.hasNext() && longestIte.hasNext()) {
				longestNextSymbol = longestIte.next();
				if (longestNextSymbol.equals(shortestNextSymbol))
					shortestNextSymbol = shortestIte.next();
			}
			if (!shortestIte.hasNext())
				subsetOf = true;	
		}
		return subsetOf;
	}
	
	@Override
	public String toString() {
		return sequence.toString();
	}

}
