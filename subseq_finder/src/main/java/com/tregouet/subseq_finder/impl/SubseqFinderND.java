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
import com.tregouet.subseq_finder.impl.utils.Coordinates;
import com.tregouet.subseq_finder.impl.utils.NArrayBool;

public class SubseqFinderND implements ISubseqFinderND {

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
	
	private Set<ISubseqND> setSubsequences(NArrayBool dotPlot) throws SubseqException {
		Set<ISubseqND> subsequences = new HashSet<ISubseqND>();
		ISubseqND subseqSeed = new SubseqND(subseqMaxSize, sequences.length);
		int[] dotPlotLimits = dotPlot.dimensions();
		int[] currCoord = new int[sequences.length];
		boolean allFirstValuesFound = false;
		//lists of minimal coordinates of areas in the dot plot that doesn't need to be looked up (empty at first)
		List<List<Integer>> upperBounds = new ArrayList<List<Integer>>();
		//looks every relevant place up in the dot plot. 
		while (!allFirstValuesFound) {
			if (dotPlot.getBoolean(currCoord) == true) {
				//a first common symbol has been found for the beginning of a new sub-sequence
				try {
					//returns all max common sub-sequences beginning with this symbol
					subsequences.addAll(continueSubsequence(subseqSeed, currCoord, dotPlotLimits));
				} catch (Exception e) {
					throw new SubseqException("SubseqFinderND.setSubsequences : " + System.lineSeparator() 
						+ e.getMessage());
				}
				/* removes the area that has just been explored (while building the last sub-sequences) 
				 * from the reachable dot plot space.  
				 */
				List<Integer> newUpperBound = new ArrayList<Integer>();
				for (int i=0 ; i < currCoord.length ; i++)
					newUpperBound.add(currCoord[i] +1);
				upperBounds.add(newUpperBound);
			}
			//if there remains a reachable position to be looked up in the dot plot, then continue search
			allFirstValuesFound = 
					!Coordinates.advanceInSpecifiedArea(
							currCoord, dotPlotLimits, new int[dotPlotLimits.length], upperBounds);
		}
		return subsequences;
	}
	
	//recursive
	private Set<ISubseqND> continueSubsequence(ISubseqND paramSubseq, int[] newSymbolCoord, int[] dotPlotLimits) 
			throws SubseqException{
		//HERE
		System.out.println(System.lineSeparator() + "***continueSubsequence()***" + System.lineSeparator() + "Current subseq : ");
		System.out.println(paramSubseq.getSubsequence(sequences));
		//HERE
		//current sub-sequence
		Set<ISubseqND> subseqs = new HashSet<ISubseqND>();
		ISubseqND subseq = paramSubseq.clone();
		//add specified new coordinate to current sub-sequence
		try {
			subseq.addNewCoord(newSymbolCoord);
		} catch (Exception e) {
			throw new SubseqException("SubseqFinderND.continueSubsequence() : " + System.lineSeparator() 
				+ "current subseq : " + Arrays.deepToString(subseq.getCoordinates()) + System.lineSeparator() + 
				e.getMessage());
		}
		//HERE
		System.out.println("New symbol : " + sequences[0][newSymbolCoord[0]]);
		System.out.println("New coordinates : " + Arrays.toString(newSymbolCoord));
		//HERE
		boolean outOfDotPlot = false;
		//*search for the continuation of current sub-sequence*
		int[] nextCoord = new int[newSymbolCoord.length];
		//set the minimal position for the next common symbol coordinates
		for (int i=0 ; i < newSymbolCoord.length ; i++) {
			nextCoord[i] = newSymbolCoord[i] + 1;
			if (nextCoord[i] == dotPlotLimits[i])
				outOfDotPlot = true;
		}
		boolean nextSymbolFound = false;
		//set the "minimal values - 1" for the next common symbol coordinates
		int[] lowerBound = newSymbolCoord;
		//lists of minimal coordinates of areas in the dot plot that doesn't need to be looked up (empty at first)
		List<List<Integer>> upperBounds = new ArrayList<List<Integer>>();
		//begin search
		while (!outOfDotPlot) {
			if (dotPlot.get(nextCoord) == true) {
				nextSymbolFound = true;
				//HERE
				System.out.println("Next symbol : " + sequences[0][nextCoord[0]]);
				System.out.println("New coordinates : " + Arrays.toString(nextCoord));
				//HERE
				try {
					/* recursion. Returns all max common sub-sequences beginning with current sub-sequence plus the 
					 * new symbol coordinates 
					 */
					subseqs.addAll(continueSubsequence(subseq, nextCoord, dotPlotLimits));
				}
				catch (Exception e) {
					throw new SubseqException("SubseqFinderND.continueSubsequence() : " + System.lineSeparator() 
					+ e.getMessage());
				}
				/* removes the area that has just been explored (while building the last sub-sequences) 
				 * from the reachable dot plot space
				 */
				List<Integer> newUpperBound = new ArrayList<Integer>();
				for (int coordVal : nextCoord)
					newUpperBound.add(coordVal + 1);
				upperBounds.add(newUpperBound);
			}
			//if there remains a reachable position to be looked up in the dot plot, then continue search
			outOfDotPlot = !Coordinates.advanceInSpecifiedArea(nextCoord, dotPlotLimits, lowerBound, upperBounds);
		}
		/* if no common symbol coordinates has been found to continue the current sub-sequence, then current one 
		 * is a max common sub-sequence
		 */
		if (!nextSymbolFound) {
			subseqs.add(subseq);
			//HERE
			System.out.println("No next common symbol found" + System.lineSeparator() + "Sequence returned : " 
					+ subseq.getSubsequence(sequences));
		}
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
