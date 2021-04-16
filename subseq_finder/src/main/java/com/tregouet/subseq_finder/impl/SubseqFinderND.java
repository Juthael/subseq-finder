package com.tregouet.subseq_finder.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.api.hyperdrive.Coord;

import com.tregouet.subseq_finder.ISubseqFinderND;
import com.tregouet.subseq_finder.ISubseqND;
import com.tregouet.subseq_finder.exceptions.SubseqException;
import com.tregouet.subseq_finder.impl.utils.Coordinate;
import com.tregouet.subseq_finder.impl.utils.NArrayBool;

public abstract class SubseqFinderND implements ISubseqFinderND {

	private final String[][] sequences;
	private final int subseqMaxSize;
	private final NArrayBool dotPlot;
	private final Set<ISubseqND> subsequences;
	
	
	public SubseqFinderND(String[][]sequences) throws SubseqException {
		if (sequences != null && sequences.length > 0) {
			this.sequences = sequences;
			dotPlot = setDotPlot();
			int subsequenceMaxSize = sequences[0].length;
			for (int i=1 ; i < sequences.length ; i++) {
				if (sequences[i].length < subsequenceMaxSize)
					subsequenceMaxSize = sequences[i].length;
			}
			subseqMaxSize = subsequenceMaxSize;
			try {
				subsequences = setSubsequences(dotPlot);
			} catch (Exception e) {
				throw new SubseqException("SubseqFinderND(String[][]) : " + System.lineSeparator() + e.getMessage());
			}
		}
		else throw new SubseqException("SubseqFinderND(String[][]) : parameter is null or empty.");
	}

	public Set<ISubseqND> getSubseqs() {
		return subsequences;
	}

	public Set<List<String>> getStringSubseqs() {
		Set<List<String>> stringSubseqs = new HashSet<List<String>>();
		for (ISubseqND subseq : subsequences) {
			stringSubseqs.add(subseq.getSubsequence(sequences));
		}
		return stringSubseqs;
	}
	
	// for test use only
	public boolean isASameSymbolCoord(int[] coordinates) throws SubseqException {
		boolean isASameSymbolCoord;
		if (coordinates.length != sequences.length)
			throw new SubseqException("SubseqFinderND.isASameSymbolCoord : coordinate size is invalid.");
		else {
			boolean validCoordinate = true;
			for (int i=0 ; i < coordinates.length ; i++) {
				if (coordinates[i] >= sequences[i].length)
					validCoordinate = false;
			}
			if (!validCoordinate)
				throw new SubseqException(
						"SubseqFinderND.isASameSymbolCoord : coordinate values are invalid.");
			else isASameSymbolCoord = dotPlot.getBoolean(coordinates);
		}
		return isASameSymbolCoord;
	}
	
	public Set<ISubseqND> setSubsequences(NArrayBool dotPlot) throws SubseqException {
		Set<ISubseqND> subsequences = new HashSet<ISubseqND>();
		ISubseqND subseqSeed = new SubseqND(subseqMaxSize, sequences.length);
		int[] dotPlotLimits = dotPlot.dimensions();
		int[] newCoord = new int[dotPlotLimits.length];
		boolean allFirstValuesFound = false;
		List<List<Integer>> upperBounds = new ArrayList<List<Integer>>();
		while (!allFirstValuesFound) {
			if (dotPlot.getBoolean(newCoord) == true) {
				try {
					subsequences.addAll(continueSubsequence(subseqSeed, newCoord, dotPlotLimits));
				} catch (Exception e) {
					throw new SubseqException("SubseqFinderND.setSubsequences : " + System.lineSeparator() 
						+ e.getMessage());
				}
				List<Integer> newUpperBound = new ArrayList<Integer>();
				for (int i=0 ; i < newCoord.length ; i++)
					newUpperBound.add(newCoord[i] +1);
				upperBounds.add(newUpperBound);
			}
			allFirstValuesFound = 
					!Coordinate.advanceInSpecifiedArea(
							newCoord, dotPlotLimits, new int[dotPlotLimits.length], upperBounds);
		}
		return subsequences;
	}
	
	private Set<ISubseqND> continueSubsequence(ISubseqND paramSubseq, int[] nextSymbolCoord, int[] dotPlotLimits) 
			throws SubseqException{
		Set<ISubseqND> subseqs = new HashSet<ISubseqND>();
		ISubseqND subseq = paramSubseq.clone();
		try {
			subseq.addNewCoord(nextSymbolCoord);
		} catch (Exception e) {
			throw new SubseqException("SubseqFinderND.continueSubsequence() : " + System.lineSeparator() 
				+ "current subseq : " + Arrays.deepToString(subseq.getCoordinates()) + System.lineSeparator() + 
				e.getMessage());
		}
		boolean outOfDotPlot = false;
		int[] newCoord = new int[nextSymbolCoord.length];
		for (int i=0 ; i < nextSymbolCoord.length ; i++) {
			newCoord[i] = nextSymbolCoord[i] + 1;
			if (newCoord[i] == dotPlotLimits[i])
				outOfDotPlot = true;
		}
		boolean nextSymbolFound = false;
		int[] lowerBound = nextSymbolCoord;
		List<List<Integer>> upperBounds = new ArrayList<List<Integer>>();
		while (!outOfDotPlot) {
			if (dotPlot.get(newCoord) == true) {
				nextSymbolFound = true;
				try {
					subseqs.addAll(continueSubsequence(subseq, newCoord, dotPlotLimits));
				}
				catch (Exception e) {
					throw new SubseqException("SubseqFinderND.continueSubsequence() : " + System.lineSeparator() 
					+ e.getMessage());
				}
				List<Integer> newUpperBound = new ArrayList<Integer>();
				for (int coordVal : newCoord)
					newUpperBound.add(coordVal + 1);
				upperBounds.add(newUpperBound);
			}
			outOfDotPlot = !Coordinate.advanceInSpecifiedArea(newCoord, dotPlotLimits, lowerBound, upperBounds);
		}
		if (!nextSymbolFound)
			subseqs.add(subseq);
		return subseqs;
	}
	
	private NArrayBool setDotPlot() {
		NArrayBool dotPlot;
		//set dimensions of the n-dimensional dotPlot array
		int[] dimensions = new int[sequences.length];
		for (int i=0 ; i<sequences.length ; i++) {
			dimensions[i] = sequences[i].length;
		}
		//instantiate dotPlot
		dotPlot = new NArrayBool(dimensions);
		/*
		 * set 'true' value in n-dimensional dotPlot[i][j]...[z] if strings in 
		 * sequence[0][i], sequence[1][j], ..., sequence[n-1][z] are equal
		 */
		int[] coordinates = new int[sequences.length];
		boolean dotPlotSet = false;
		while (!dotPlotSet) {
			String currSymbol = sequences[0][coordinates[0]];
			int sequenceIdx = 1;
			boolean sameSymbol = true;
			while ((sequenceIdx < sequences.length) && sameSymbol) {
				sameSymbol = 
						(currSymbol.equals(sequences[sequenceIdx][coordinates[sequenceIdx]]));
				sequenceIdx++;
			}
			if (sameSymbol)
				dotPlot.set(coordinates, true);
			dotPlotSet = !Coord.advance(coordinates, dimensions);
		}
		return dotPlot;
	}

}
