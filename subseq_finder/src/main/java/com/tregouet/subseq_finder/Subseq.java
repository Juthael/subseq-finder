package com.tregouet.subseq_finder;

import java.util.ArrayList;
import java.util.List;

public class Subseq {

	private static final String VALUE_PLACEHOLDER = "_";
	private static final String SEQ_START = "START";
	private static final String SEQ_END = "END";
	
	int[][] dotCoordinates;
	int dotCoordIndex = 0;
	
	public Subseq(int seqMaxSize) {
		dotCoordinates = new int[seqMaxSize][2];
	}
	
	public void setNewCoordinates(int firstCoord, int secCoord) {
		dotCoordinates[dotCoordIndex][1] = firstCoord;
		dotCoordinates[dotCoordIndex][2] = secCoord;
		dotCoordIndex++;
	}
	
	public List<String> getSequence(String[] first, String[] second) {
		List<String> sequence = new ArrayList<String>();
		int lastFirstPos = 0;
		int lastSecondPos = 0;
		for (int i=0 ; i < dotCoordinates.length ; i++) {
			int currFirstPos = dotCoordinates[i][1];
			int currSecondPos = dotCoordinates[i][2];
			if ((currFirstPos > lastFirstPos + 1) || (currSecondPos > lastSecondPos + 1))
				sequence.add(VALUE_PLACEHOLDER);
			sequence.add(first[currFirstPos]);
			lastFirstPos = currFirstPos;
			lastSecondPos = currSecondPos;
		}
		return sequence;
	}
	
	public List<String> getTrimmedSequence(String[] first, String[] second) {
		List<String> sequence = getSequence(first, second);
		if (!sequence.isEmpty() && sequence.get(0).equals(SEQ_START))
			sequence.remove(0);
		if (!sequence.isEmpty() && sequence.get(sequence.size() - 1).equals(SEQ_END))
			sequence.remove(sequence.size() - 1);
	}

}