package com.tregouet.subseq_finder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.subseq_finder.exceptions.SubseqException;

public class Subseq {

	public static final String ARG_PLACEHOLDER = "_";
	public static final String SEQ_START = "START";
	public static final String SEQ_END = "END";
	
	private final int[][] coordinates;
	private int coordIndex;
	private final List<List<Integer>> value1ArgumentsCoords;
	private final List<List<Integer>> value2ArgumentsCoords;
	
	public Subseq(int seqMaxSize) {
		coordIndex = 0;
		coordinates = new int[seqMaxSize][2];
		for (int i=0 ; i<coordinates.length ; i++) {
			coordinates[i][1] = -1;
			coordinates[i][2] = -1;
		}
		value1ArgumentsCoords = new ArrayList<List<Integer>>();
		value2ArgumentsCoords = new ArrayList<List<Integer>>();
	}
	
	public Subseq(int[][] dotCoordinates, int dotCoordIndex, List<List<Integer>> value1ArgumentsCoords, 
			List<List<Integer>> value2ArgumentsCoords) {
		this.coordinates = Arrays.copyOf(dotCoordinates, dotCoordinates.length);
		this.coordIndex = dotCoordIndex;
		this.value1ArgumentsCoords = new ArrayList<List<Integer>>(value1ArgumentsCoords);
		this.value2ArgumentsCoords = new ArrayList<List<Integer>>(value2ArgumentsCoords);
	}
	
	public void addNewCoord(int val1NewCoord, int val2NewCoord) throws SubseqException {
		if (coordIndex < coordinates.length - 1) {
			if (coordIndex > 0) {
				int val1LastCoord = coordinates[coordIndex - 1][1];
				int val2LastCoord = coordinates[coordIndex - 1][2];
				if ((val1NewCoord > val1LastCoord + 1) || (val2NewCoord > val2LastCoord +1)) {
					List<Integer> val1ArgCoords = new ArrayList<Integer>();
					List<Integer> val2ArgCoords = new ArrayList<Integer>();
					for (int val1ArgCoord = val1LastCoord + 1 ; val1ArgCoord < val1NewCoord ; val1ArgCoord++)
						val1ArgCoords.add(val1ArgCoord);
					for (int val2ArgCoord = val2LastCoord + 1 ; val2ArgCoord < val2NewCoord ; val2ArgCoord++)
						val2ArgCoords.add(val2ArgCoord);
					value1ArgumentsCoords.add(val1ArgCoords);
					value2ArgumentsCoords.add(val2ArgCoords);
				}
			}
			coordinates[coordIndex][1] = val1NewCoord;
			coordinates[coordIndex][2] = val2NewCoord;
			coordIndex++;
		}
		else throw new SubseqException("SubSeq.setNewCoordinates() : coordinates array is already full");
	}
	
	public List<String> getSequence(String[] value, int valueNumber) throws SubseqException {
		List<String> sequence = new ArrayList<String>();
		if ((valueNumber > 0) && (valueNumber < 3)) {
			int lastVal1Pos = -1;
			int lastVal2Pos = -1;
			int idx = 0;
			while (coordinates[idx][1] != -1) {
				int currVal1Pos = coordinates[idx][1];
				int currVal2Pos = coordinates[idx][2];
				if ((currVal1Pos > lastVal1Pos + 1) || (currVal2Pos > lastVal2Pos + 1)) {
					sequence.add(ARG_PLACEHOLDER);
				}
				sequence.add(value[coordinates[idx][valueNumber]]);
				lastVal1Pos = currVal1Pos;
				lastVal2Pos = currVal2Pos;
				idx++;
			}
		}
		else throw new SubseqException("Subseq.getSequence() : invalid value number");
		return sequence;
	}
	
	public Set<Integer> getValueCoordinates(int valueNumber) throws SubseqException {
		Set<Integer> valCoordinates;
		if (valueNumber > 0 && valueNumber < 3) {
			valCoordinates = new HashSet<Integer>();
			int idx = 0;
			while (coordinates[idx][1] != -1) {
				valCoordinates.add(coordinates[idx][valueNumber]);
				idx++;
			}
		}
		else throw new SubseqException("Subseq.getValueCoordinates() : value number is invalid");
		return valCoordinates;		
	}
	
	public List<List<Integer>> getValueArgsCoordinates(int valueNumber) throws SubseqException{
		List<List<Integer>> valueArgsCoordinates;
		if (valueNumber > 0 && valueNumber < 3) {
			if (valueNumber == 1)
				valueArgsCoordinates = value1ArgumentsCoords;
			else valueArgsCoordinates = value2ArgumentsCoords;
		}
		else throw new SubseqException("Subseq.getValueArgsCoordinates() : invalid value number");
		return valueArgsCoordinates;
	}
	
	public List<String> getTrimmedSequence(String[] value, int valueNumber) throws SubseqException {
		List<String> sequence = getSequence(value, valueNumber);
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
	
	public boolean isASubseqOf(Subseq subseq) throws SubseqException {
		boolean isASubSeqOf;
		try {
			if ((subseq.getValueCoordinates(1).containsAll(this.getValueCoordinates(1))
					|| subseq.getValueCoordinates(2).containsAll(this.getValueCoordinates(2))))
				isASubSeqOf = true;
			else isASubSeqOf = false;	
		}
		catch (SubseqException e) {
			throw new SubseqException("Subseq.isASubseqOf() : " + System.lineSeparator() + e.getMessage());
		}
		return isASubSeqOf;
	}
	
	@Override
	public Subseq clone() {
		Subseq subseqClone = new Subseq(coordinates, coordIndex, value1ArgumentsCoords, value2ArgumentsCoords);
		return subseqClone;
	}

}