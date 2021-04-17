package com.tregouet.subseq_finder;

import java.util.List;
import java.util.Set;

import com.tregouet.subseq_finder.exceptions.SubseqException;
import com.tregouet.subseq_finder.impl.utils.NArrayBool;

/**
 * Sequence of coordinates in a n-dimensional dot plot. 
 * 
 * A dot plot allows the comparison of n sequences of symbols ; 
 * they are implemented as n-dimensional arrays of booleans ({@link NArrayBool}). This comparison, performed by a 
 * "sub-sequence finder" class ({@link ISubseqFinderND}, yields the successive coordinates of common subsequences, 
 * encapsulated in instances of a {@link ISubseqND} class.  
 * 
 * @author Gael Tregouet
 * @see NArrayBool
 * @see ISubseqFinderND
 */
public interface ISubseqND extends Cloneable {
	
	/**
	 * 
	 * @param newCoord new coordinates to be added to this sequence 
	 * @throws SubseqException
	 */
	public void addNewCoord(int[] newCoord) throws SubseqException;
	
	/**
	 * Returns the coordinates of this sub-sequence in the dot plot. <br>
	 * 
	 * The length <i>s</i> of the returned array is the length of the shortest compared sequence, i.e. the maximal 
	 * length of a subsequence. The length of every array in the returned array is the number of compared sequences, 
	 * and therefore the number of dimensions in the dot plot. <br>
	 *  
	 * If this sub-sequence length is <i>n</i> < <i>s</i>, then all coordinates arrays at indexes <i>n</i> to <i>s</i>-1 are filled 
	 * with the default value -1. <br>
	 *     
	 * @return successive coordinates of this sequence
	 */
	public int[][] getCoordinates();
	
	/**
	 * 
	 * @return for each compared sequence, the positions of the common symbols
	 */
	public List<Set<Integer>> getSubseqPositionsInSeq();
	
	/**
	 * The subsequence returned contains placeholder symbols. <br>
	 * 
	 * A placeholder is inserted : <br> 
	 * -1/at the beginning of the subsequence if the first symbol in the subsequence is not the first symbol in 
	 * every compared sequence. <br>
	 * -2/at the end of the subsequence if the last symbol in the subsequence is not the last symbol in every compared 
	 * sequence. <br>
	 * -3/between two symbols of the subsequence if there is at least one sequence in the set of compared sequences for 
	 * which those two symbols are not consecutive. <br> <br> 
	 * 
	 * Placeholders are used to indicate that for this subsequence to be transformable into any of the compared sequences, 
	 * actual strings of symbols must replace placeholder symbols (be it empty strings or strings of any size).
	 *  
	 * @param sequences the compared sequences of symbols
	 * @return the current common sub-sequence, with placeholder symbols where needed.
	 */
	public List<String> getSubsequence(String[][] sequences);
	
	/**
	 * 
	 * @param other another sub-sequence 
	 * @return true if this sub-sequence is a sub-sequence of the specified sub-sequence
	 */
	public boolean isASubseqOf(ISubseqND other);
	
	public int length();
	
	public ISubseqND clone();

}
