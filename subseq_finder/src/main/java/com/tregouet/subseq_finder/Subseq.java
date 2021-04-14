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
	
	/**
	 * If coordinates[x][n] == y and y != -1, then the x^th symbol (starting from 0) in the subsequence is the y^th symbol 
	 * in the n^th sequence. If y == -1, then there is no x^th symbol and the n^th sequence length is < x. 
	 */
	private final int[][] coordinates;
	
	/**
	 * If coordIndex == x, then the next symbol added to the subsequence will be the x^th. 
	 */
	private int coordIndex;
	
	public Subseq(int seqMaxSize) {
		coordIndex = 0;
		coordinates = new int[seqMaxSize][2];
		for (int i=0 ; i<coordinates.length ; i++) {
			coordinates[i][0] = -1;
			coordinates[i][1] = -1;
		}
	}
	
	public Subseq(int[][] dotCoordinates, int dotCoordIndex) {
		this.coordinates = new int[dotCoordinates.length][];
		for (int i = 0 ; i < dotCoordinates.length ; i++) {
			this.coordinates[i] = new int[dotCoordinates[i].length];
			for (int j = 0 ; j < coordinates[i].length ; j++) {
				this.coordinates[i][j] = dotCoordinates[i][j];
			}
		}
		this.coordIndex = dotCoordIndex;
	}
	
	public void addNewCoord(int val0NewCoord, int val1NewCoord) throws SubseqException {
		if (coordIndex < coordinates.length - 1) {
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
					//if the common subsequences pass some letters of the value, then a placeholder is added
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
	
	/**
	 * 
	 * @param valueNumber
	 * @return the position of the subsequence symbols in the value
	 * @throws SubseqException
	 */
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
					//sinon erreur, non ?
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
		Subseq subseqClone = new Subseq(coordinates, coordIndex);
		return subseqClone;
	}

}