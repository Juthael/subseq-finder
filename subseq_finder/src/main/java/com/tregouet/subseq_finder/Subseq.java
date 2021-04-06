package com.tregouet.subseq_finder;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.subseq_finder.exceptions.SubseqException;

public class Subseq {

	private static final String VALUE_PLACEHOLDER = "_";
	private static final String SEQ_START = "START";
	private static final String SEQ_END = "END";
	
	int[][] dotCoordinates;
	int dotCoordIndex = 0;
	List<List<Integer>> Value1ArgumentsCoord = new ArrayList<List<Integer>>();
	List<List<Integer>> Value2ArgumentsCoord = new ArrayList<List<Integer>>();
	
	public Subseq(int seqMaxSize) {
		dotCoordinates = new int[seqMaxSize][2];
	}
	
	public void setNewCoordinates(int firstCoord, int secCoord) throws SubseqException {
		if (dotCoordIndex < dotCoordinates.length - 1) {
			dotCoordinates[dotCoordIndex][1] = firstCoord;
			dotCoordinates[dotCoordIndex][2] = secCoord;
			dotCoordIndex++;
		}
		else throw new SubseqException("SubSeq.setNewCoordinates() : coordinates array is already full");
	}
	
	public List<String> getSequence(String[] first, String[] second) {
		List<String> sequence = new ArrayList<String>();
		int lastVal1Pos = 0;
		int lastVal2Pos = 0;
		for (int i=0 ; i < dotCoordinates.length ; i++) {
			int currVal1Pos = dotCoordinates[i][1];
			int currVal2Pos = dotCoordinates[i][2];
			if ((currVal1Pos > lastVal1Pos + 1) || (currVal2Pos > lastVal2Pos + 1)) {
				sequence.add(VALUE_PLACEHOLDER);
				List<Integer> val1ArgCoord = new ArrayList<Integer>();
				List<Integer> val2ArgCoord = new ArrayList<Integer>();
				for (int val1ArgIdx = lastVal1Pos ; val1ArgIdx < currVal1Pos - 1 ; val1ArgIdx++) {
					val1ArgCoord.add(val1ArgIdx);
				}
				for (int val2ArgIdx = lastVal2Pos ; val2ArgIdx < currVal2Pos - 1 ; val2ArgIdx++) {
					val2ArgCoord.add(val2ArgIdx);
				}
				Value1ArgumentsCoord.add(val1ArgCoord);
				Value2ArgumentsCoord.add(val2ArgCoord);
			}
			sequence.add(first[currVal1Pos]);
			lastVal1Pos = currVal1Pos;
			lastVal2Pos = currVal2Pos;
		}
		return sequence;
	}
	
	public List<List<String>> getValueArguments(String[] value1, int valueNumber) throws SubseqException{
		List<List<String>> arguments;
		if (valueNumber > 0 && valueNumber < 3) {
			arguments = new ArrayList<List<String>>();
			List<String> currentArgument;
			int dotCoordinatesIndex = 0;
			int lastFirstValCoord = 0;
			int lastSecValCoord = 0; 
			
		}
		else throw new SubseqException("Subseq.getValueArguments() : value number invalid");
		return arguments;
	}
	
	public List<String> getTrimmedSequence(String[] first, String[] second) throws SubseqException {
		List<String> sequence = getSequence(first, second);
		if (sequence != null && !sequence.isEmpty()) {
			if (!sequence.get(0).equals(SEQ_START))
				throw new SubseqException("Subseq.getTrimmedSequence() : first value invalid");
			else {
				sequence.remove(0);
				if (sequence.get(sequence.size() - 1).equals(SEQ_END))
					sequence.remove(sequence.size() - 1);
			}
		}
		else throw new SubseqException("Subseq.getTrimmedSequence() : sequence is null or empty");
		return sequence;
	}

}