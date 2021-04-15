package com.tregouet.subseq_finder.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.api.hyperdrive.Coord;
import org.api.hyperdrive.NArrayBool;

import com.tregouet.subseq_finder.ISubseqFinderND;
import com.tregouet.subseq_finder.ISubseqND;
import com.tregouet.subseq_finder.exceptions.SubseqException;

public abstract class SubseqFinderND implements ISubseqFinderND {

	private final String[][] sequences;
	private final NArrayBool dotPlot;
	protected final Set<ISubseqND> subsequences;
	
	public SubseqFinderND(String[][]sequences) {
		this.sequences = sequences;
		dotPlot = setDotPlot();
		subsequences = setSubsequences(dotPlot);
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
	
	abstract protected Set<ISubseqND> setSubsequences(NArrayBool dotPlot);
	
	private NArrayBool setDotPlot() {
		NArrayBool dotPlot;
		int[] dimensions = new int[sequences.length];
		for (int i=0 ; i<sequences.length ; i++) {
			dimensions[i] = sequences[i].length;
		}
		dotPlot = new NArrayBool(dimensions);
		int[] coordinates = new int[sequences.length];
		boolean dotPlotSet = false;
		while (!dotPlotSet) {
			String currSymbol = sequences[0][coordinates[0]];
			int sequenceIdx = 1;
			boolean sameSymbol = true;
			while ((sequenceIdx < sequences.length) && sameSymbol) {
				sameSymbol = 
						(currSymbol.equals(sequences[sequenceIdx][coordinates[sequenceIdx]]));
			}
			if (sameSymbol)
				dotPlot.set(coordinates, true);
			dotPlotSet = Coord.advance(coordinates, dimensions);
		}
		return dotPlot;
	}

}
