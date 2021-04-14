package com.tregouet.subseq_finder;

import java.util.List;
import java.util.Set;

public interface ISubseqFinderND {
	
	public Set<ISubseqND> getSubseqs();
	
	public Set<List<String>> getStringSubseqs();

}
