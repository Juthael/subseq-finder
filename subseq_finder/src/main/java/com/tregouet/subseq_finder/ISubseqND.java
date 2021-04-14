package com.tregouet.subseq_finder;

import java.util.List;

import com.tregouet.subseq_finder.exceptions.SubseqException;

public interface ISubseqND {
	
	public void addNewCoord(int[] newCoord) throws SubseqException;
	
	public int length();
	
	public List<String> getSubsequence(String[][] values);
	
	public List<String> getTrimmedSequence(String[][] values);
	
	public int[][] getSubSqCoordinatesInSq();
	
	public boolean isASubseqOf(Subseq other);

}
