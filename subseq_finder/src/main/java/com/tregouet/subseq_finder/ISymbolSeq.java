package com.tregouet.subseq_finder;

import java.util.List;

/**
 * Sequence of symbols. <br>
 * 
 * A sequence of symbols may be abstract, i.e. partially undefined, if it contains the placeholder symbol "<b>_</b>". 
 * 
 * @author Gael Tregouet
 *
 */
public interface ISymbolSeq {
	
	public static final String PLACEHOLDER = "_";
	public static final Integer SUBSEQ_OF = -1;
	public static final Integer EQUAL_TO = 0;
	public static final Integer SUPERSEQ_OF = 1;
	public static final Integer NOT_COMPARABLE = -2;
	
	/**
	 * Note : ordering on ISymbolSeq instances is partial, so this is not an implementation of the {@link Comparable} 
	 * interface.  
	 * @param other
	 * @return '0' if other equals this, '1' if other is a subset of this, '-1' if this is a subset of other, 'null' if 
	 * this and other aren't comparable.  
	 */
	public Integer compareTo(ISymbolSeq other);
	
	/**
	 * Returns maximal subsequences of symbols yielded by the set of previously added coordinates subsequences. <br> 
	 * 
	 * Maximal common subsequences are here defined as the subset of all common subsequences such as no element in this 
	 * subset is a subsequence of another element (note that this is not the usual definition). <br>
	 * 
	 * A placeholder is inserted in a returned subsequence : <br> 
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
	 * @return
	 */
	List<String> getStringSequence();
	
	String[] getStringArray();
	
	/**
	 * 
	 * @return 'true' if this sequence contains a placeholder symbol. 
	 */
	boolean isAbstract();
	
	/**
	 * 
	 * @return the sequence length.
	 */
	int length();
	
	/**
	 * 
	 * @return the sequence length without placeholders
	 */
	int lengthWithoutPlaceholders();

}
