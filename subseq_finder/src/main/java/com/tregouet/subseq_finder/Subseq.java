package com.tregouet.subseq_finder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.subseq_finder.exceptions.SubseqException;

public class Subseq implements Cloneable {

	public static final String ARG_PLACEHOLDER = "_";
	public static final String SEQ_START = "START";
	public static final String SEQ_END = "END";
	
	private final int[][] coordinates;
	private int coordIndex;
	private final List<List<Integer>> value0ArgumentsCoords;
	private final List<List<Integer>> value1ArgumentsCoords;
	
	public Subseq(int seqMaxSize) {
		coordIndex = 0;
		coordinates = new int[seqMaxSize][2];
		for (int i=0 ; i<coordinates.length ; i++) {
			coordinates[i][0] = -1;
			coordinates[i][1] = -1;
		}
		value0ArgumentsCoords = new ArrayList<List<Integer>>();
		value1ArgumentsCoords = new ArrayList<List<Integer>>();
	}
	
	public Subseq(int[][] dotCoordinates, int dotCoordIndex, List<List<Integer>> value1ArgumentsCoords, 
			List<List<Integer>> value2ArgumentsCoords) {
		this.coordinates = new int[dotCoordinates.length][];
		for (int i = 0 ; i < dotCoordinates.length ; i++) {
			this.coordinates[i] = new int[dotCoordinates[i].length];
			for (int j = 0 ; j < coordinates[i].length ; j++) {
				this.coordinates[i][j] = dotCoordinates[i][j];
			}
		}
		this.coordIndex = dotCoordIndex;
		this.value0ArgumentsCoords = new ArrayList<List<Integer>>(value1ArgumentsCoords);
		this.value1ArgumentsCoords = new ArrayList<List<Integer>>(value2ArgumentsCoords);
	}
	
	public void addNewCoord(int val0NewCoord, int val1NewCoord) throws SubseqException {
		if (coordIndex < coordinates.length - 1) {
			if (coordIndex > 0) {
				int val0LastCoord = coordinates[coordIndex - 1][0];
				int val1LastCoord = coordinates[coordIndex - 1][1];
				if ((val0NewCoord > val0LastCoord + 1) || (val1NewCoord > val1LastCoord +1)) {
					List<Integer> val0ArgCoords = new ArrayList<Integer>();
					List<Integer> val1ArgCoords = new ArrayList<Integer>();
					for (int val0ArgCoord = val0LastCoord + 1 ; val0ArgCoord < val0NewCoord ; val0ArgCoord++)
						val0ArgCoords.add(val0ArgCoord);
					for (int val1ArgCoord = val1LastCoord + 1 ; val1ArgCoord < val1NewCoord ; val1ArgCoord++)
						val1ArgCoords.add(val1ArgCoord);
					value0ArgumentsCoords.add(val0ArgCoords);
					value1ArgumentsCoords.add(val1ArgCoords);
				}
			}
			coordinates[coordIndex][0] = val0NewCoord;
			coordinates[coordIndex][1] = val1NewCoord;
			coordIndex++;
		}
		else throw new SubseqException("SubSeq.setNewCoordinates() : coordinates array is already full");
	}
	
	public List<String> getSequence(String[] value, int valueNumber) throws SubseqException {
		List<String> sequence = new ArrayList<String>();
		if ((valueNumber > -1) && (valueNumber < 2)) {
			int lastVal0Pos = -1;
			int lastVal1Pos = -1;
			int idx = 0;
			while (coordinates[idx][1] != -1 && idx < coordinates.length) {
				int currVal0Pos = coordinates[idx][0];
				int currVal1Pos = coordinates[idx][1];
				if ((currVal0Pos > lastVal0Pos + 1) || (currVal1Pos > lastVal1Pos + 1)) {
					sequence.add(ARG_PLACEHOLDER);
				}
				sequence.add(value[coordinates[idx][valueNumber]]);
				lastVal0Pos = currVal0Pos;
				lastVal1Pos = currVal1Pos;
				idx++;
			}
		}
		else throw new SubseqException("Subseq.getSequence() : invalid value number");
		return sequence;
	}
	
	public Set<Integer> getValueCoordinates(int valueNumber) throws SubseqException {
		Set<Integer> valCoordinates;
		if (valueNumber > -1 && valueNumber < 2) {
			valCoordinates = new HashSet<Integer>();
			int idx = 0;
			while (coordinates[idx][0] != -1) {
				valCoordinates.add(coordinates[idx][valueNumber]);
				idx++;
			}
		}
		else throw new SubseqException("Subseq.getValueCoordinates() : value number is invalid");
		return valCoordinates;		
	}
	
	public List<List<Integer>> getValueArgsCoordinates(int valueNumber) throws SubseqException{
		List<List<Integer>> valueArgsCoordinates;
		if (valueNumber > -1 && valueNumber < 2) {
			if (valueNumber == 0)
				valueArgsCoordinates = value0ArgumentsCoords;
			else valueArgsCoordinates = value1ArgumentsCoords;
		}
		else throw new SubseqException("Subseq.getValueArgsCoordinates() : invalid value number");
		return valueArgsCoordinates;
	}
	
	public List<String> getTrimmedSequence(String[] value, int valueNumber) throws SubseqException {
		List<String> sequence = getSequence(value, valueNumber);
		if (valueNumber > -1 && valueNumber < 2) {
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
		}
		else throw new SubseqException("Subseq.getTrimmedSequence() : invalid value number");
		return sequence;
	}
	
	public boolean isASubseqOf(Subseq subseq) throws SubseqException {
		boolean isASubSeqOf;
		try {
			if ((subseq.getValueCoordinates(0).containsAll(this.getValueCoordinates(0))
					|| subseq.getValueCoordinates(1).containsAll(this.getValueCoordinates(1))))
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
		Subseq subseqClone = new Subseq(coordinates, coordIndex, value0ArgumentsCoords, value1ArgumentsCoords);
		return subseqClone;
	}

}