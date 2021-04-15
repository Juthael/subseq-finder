package com.tregouet.subseq_finder.impl;

import java.util.Set;

import org.api.hyperdrive.NArrayBool;

import com.tregouet.subseq_finder.ISubseqFinderND;
import com.tregouet.subseq_finder.ISubseqND;

public class SubseqFinder3D extends SubseqFinderND implements ISubseqFinderND {

	public SubseqFinder3D(String[][] sequences) {
		super(sequences);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Set<ISubseqND> setSubsequences(NArrayBool dotPlot) {
		// TODO Auto-generated method stub
		return null;
	}

}
