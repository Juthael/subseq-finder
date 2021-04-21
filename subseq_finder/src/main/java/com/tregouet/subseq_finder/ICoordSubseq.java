package com.tregouet.subseq_finder;

import java.util.List;
import java.util.Set;

/**
 * This is a strictly increasing sequence of tuples of coordinates, each element in this sequence being a list of 
 * <i>n</i> coordinate values, issued by the comparison of <i>n</i> sequences of symbols by a {@link ISubseqFinder}
 * implementing class. <br>
 * 
 * (Given <i>t<sub>1</sub></i> and <i>t<sub>2</sub></i> two n-tuples of coordinates, <i>t<sub>1</sub></i> &lt; 
 * <i>t<sub>2</sub></i> iff for 1 ≤ <i> i </i> ≤ <i> n </i>, <i>t<sub>1</sub></i>[<i>i</i>] &lt; 
 * <i>t<sub>2</sub></i>[<i>i</i>]).
 * 
 * Each tuple in this sequence of tuples is meant to be associated with a single symbol, the <i>i</i><sup>th</sup> 
 * value of this tuple giving the position of this symbol in the <i>i</i><sup>th</sup> compared sequence. <br>
 * 
 * Note that <i>x</i> sequences of tuples of coordinates ({@link ICoordSubseq}) yield <i>y</i> maximal common 
 * subsequences of symbols, with 1 ≤ <i>y</i> ≤ <i>x</i>. For example, the successive addition of two sequences 
 * of tuples <i>c<sub>1</sub></i> and <i>c<sub>2</sub></i> can lead to the following results : <br> 
 * 
 * <ul>
 * 	<li> <i>c<sub>1</sub></i> and <i>c<sub>2</sub></i> yield the sequences of symbols <i>s<sub>1</sub></i> and 
 * 		<i>s<sub>2</sub></i>, neither of which is a subsequence of the other. Therefore, <i>s<sub>1</sub></i> 
 * 		and <i>s<sub>2</sub></i> are both accepted as maximal sequences of symbols.  
 * 	<li> <i>c<sub>1</sub></i> and <i>c<sub>2</sub></i>, although being distinct sequences of tuples of coordinates, 
 * 		yield the same sequence of symbols <i>s<sub>1</sub></i>. Obviously, <i>s<sub>1</sub></i> is accepted as 
 * 		a maximal sequence of symbols.
 * 	<li> <i>c<sub>1</sub></i> and <i>c<sub>2</sub></i> yield the sequences of symbols <i>s<sub>1</sub></i> and 
 * 		<i>s<sub>2</sub></i>, with <i>s<sub>2</sub></i> being a subsequence of <i>s<sub>1</sub></i>. Only 
 * 		<i>s<sub>1</sub></i> is then accepted as a maximal sequence of symbols. 
 * </ul>
 * 
 * @author Gael Tregouet
 * @see ISubseqFinder
 */
public interface ICoordSubseq extends Cloneable, Comparable<ICoordSubseq> {
	
	/**
	 * Each tuple of coordinates is meant to be associated with a symbol found in each of the <i>n</i> compared 
	 * sequences of symbols. The <i>i</i><sup>th</sup> value of this tuple gives the position of the common symbol 
	 * in the <i>i</i><sup>th</sup> compared sequence. <br>
	 * @param newCoord tuple of coordinates to be added to this increasing sequence 
	 * 
	 * The parameter must not be null, nor have a different length than previously added coordinates. <br>
	 */
	public void addNewCoord(int[] newCoord);
	
	/**
	 * Returns this sequence of tuples of coordinates as a two-dimensional array. <br>
	 * 
	 * The length <i>l</i> of the returned array is the length of the shortest compared sequence, i.e. the maximal 
	 * length of a common subsequence. The length of every array in the returned array is the number of compared 
	 * sequences. The value [x][y] is the position, in the (<i>y</i>+1)<sup>th</sup> compared sequence of symbols, 
	 * of the common symbol associated with the (<i>x</i>+1)<sup>th</sup> tuple of this sequence. <br>
	 *  
	 * If this sub-sequence length is <i>n</i> &lt; <i>l</i>, then all coordinates arrays at indexes <i>n</i> to 
	 * <i>l</i>-1 are filled with the default value -1. <br>
	 *     
	 * @return successive coordinates of this sequence
	 */
	public int[][] getCoordinates();
	
	/**
	 * Returns, for each compared sequence, the positions of the successive common symbols respectively associated 
	 * with each of the tuples of coordinates of this sequence. 
	 *  
	 * @return for each compared sequence, the positions of the successive common symbols
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
	public ISymbolSeq getSymbolSubseq(String[][] sequences);
	
	/**
	 * 
	 * @param other another sub-sequence 
	 * @return true if this sub-sequence is a sub-sequence of the specified sub-sequence
	 */
	public boolean isASubseqOf(ICoordSubseq other);
	
	public int length();
	
	public ICoordSubseq clone();

}
