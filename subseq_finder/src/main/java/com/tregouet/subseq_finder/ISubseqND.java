package com.tregouet.subseq_finder;

import java.util.List;
import java.util.Set;

import com.tregouet.subseq_finder.exceptions.SubseqException;

public interface ISubseqND {
	
	public void addNewCoord(int[] newCoord) throws SubseqException;
	
	public int[][] getCoordinates();
	
	public List<Set<Integer>> getSubseqPositionsInSeq();
	
	public List<String> getSubsequence(String[][] sequences);
	
	public List<String> getTrimmedSequence(String[][] sequences);
	
	public boolean isASubseqOf(ISubseqND other);
	
	public int length();

}
