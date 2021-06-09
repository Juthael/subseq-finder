package com.tregouet.subseq_finder.impl;

import java.util.List;

import com.tregouet.subseq_finder.ISymbolSeq;

public class SymbolSeq implements ISymbolSeq {

	private List<String> sequence;
	private int length;
	private int lengthWithoutPlaceHolders = 0;
	
	public SymbolSeq(List<String> sequence) {
		this.sequence = sequence;
		length = sequence.size();
		for (String symbol : sequence) {
			if (!symbol.equals(ISymbolSeq.PLACEHOLDER))
				lengthWithoutPlaceHolders++;
		}
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
	
	public String[] getStringArray() {
		return sequence.toArray(new String[sequence.size()]);
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
	
	public int lengthWithoutPlaceholders() {
		return lengthWithoutPlaceHolders;
	}
	
	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		for (int i = 0 ; i < length ; i++){
			sB.append(sequence.get(i));
			if (i < length - 1)
				sB.append(" ");
		}
		return sB.toString();
	}
	
	private boolean isSubsetOf(ISymbolSeq seq1, ISymbolSeq seq2) {
		if (seq1.lengthWithoutPlaceholders() < seq2.lengthWithoutPlaceholders()) {
			List<String> sequence1 = seq1.getStringSequence();
			List<String> sequence2 = seq2.getStringSequence();
			int seq1Idx = 0;
			for (int seq2Idx = 0 ; (seq2Idx < sequence2.size()) && (seq1Idx < sequence1.size()) ; seq2Idx++) {
				while (seq1Idx < sequence1.size() && sequence1.get(seq1Idx).equals(ISymbolSeq.PLACEHOLDER))
					seq1Idx++;
				if (seq1Idx < sequence1.size() && sequence2.get(seq2Idx).equals(sequence1.get(seq1Idx)))
					seq1Idx++;
			}
			if (seq1Idx == sequence1.size())
				return true;
		}
		return false;
	}

}
