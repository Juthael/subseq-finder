package com.tregouet.subseq_finder;

import java.util.List;
import java.util.Set;

/**
 * A tool for the determination of all maximal common subsequences of a list of sequences of symbols (which has to be specified 
 * in the constructor of the implementing class). <br> 
 * 
 * Maximal common subsequences are here defined as the subset of all common subsequences such as no element in this subset is a 
 * subsequence of another element (note that this is not the usual definition). <br>
 * 
 * Common subsequences are not required to occupy consecutive positions within the original sequences. <br>
 * 
 * @author Gael Tregouet
 *
 */
public interface ISubseqFinder {
	
	/**
	 * 
	 * @return all maximal common subsequences as positions in the compared sequences, in a {@link ISubseq} format 
	 */
	public Set<ISubseq> getSubseqs();
	
	/**
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
	 * @return all maximal common subsequences, as lists of symbols with placeholders symbols inserted where needed 
	 */
	public Set<List<String>> getStringSubseqs();

}
