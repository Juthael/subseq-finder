package com.tregouet.subseq_finder.impl.poset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.subseq_finder.ISubseq;
import com.tregouet.subseq_finder.ISubseqFinder;
import com.tregouet.subseq_finder.impl.dot_plot.utils.CoordinatesDP;
import com.tregouet.subseq_finder.impl.poset.utils.CoordinatesPOS;
import com.tregouet.subseq_finder.impl.poset.utils.Similarity;

public class SubseqFinderPOS implements ISubseqFinder {

	private final String[][] sequences;
	private final List<Similarity> similarities = new ArrayList<Similarity>();
	private final Map<Similarity, Set<Similarity>> relation = new HashMap<Similarity, Set<Similarity>>();
	private final Set<Similarity> seqSeeds = new HashSet<Similarity>();
	private final Set<ISubseq> subsequences = new HashSet<ISubseq>();
	
	public SubseqFinderPOS(String[][]sequences) {
		this.sequences = sequences;
		setSimilarities();
		setRelation();
		setSubsequenceSeeds();
	}
	
	public Set<ISubseq> getSubseqs() {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<List<String>> getStringSubseqs() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void setRelation() {
		for (int i=0 ; i < similarities.size() ; i++) {
			for (int j=i ; j < similarities.size() ; j++) {
				Similarity sim1 = similarities.get(i);
				Similarity sim2 = similarities.get(j);
				int comparison = sim1.compareTo(sim2);
				if ((comparison == Similarity.LESS_THAN) || (comparison == Similarity.EQUAL_TO)) {
					if (!relation.containsKey(sim1)) {
						Set<Similarity> relatedToSim1 = new HashSet<Similarity>();
						relatedToSim1.add(sim2);
						relation.put(sim1, relatedToSim1);
					}
					else relation.get(sim1).add(sim2);
				}
				else if (comparison == Similarity.MORE_THAN) {
					if (!relation.containsKey(sim2)) {
						Set<Similarity> relatedToSim2 = new HashSet<Similarity>();
						relatedToSim2.add(sim1);
						relation.put(sim2, relatedToSim2);
					}
					else relation.get(sim2).add(sim1);
				}
			}
		}
	}
	
	private void setSubsequenceSeeds() {
		for (Similarity sim : similarities) {
			if (relation.get(sim).size() == 1)
				seqSeeds.add(sim); //then this similarity is a minimal element only related to itself. 
		}
	}
	
	private void setSimilarities() {
		int[] limits = new int[sequences.length];
		for (int i=0 ; i < sequences.length ; i++)
			limits[i] = sequences[i].length;
		int[] coords = new int[sequences.length];
		int seqIdx = 0;
		boolean similar = true;
		do {
			seqIdx = CoordinatesPOS.tryNext(coords, limits, seqIdx, similar);
			similar = test(coords, seqIdx);
			if (similar && (seqIdx == (sequences.length - 1)))
				similarities.add(new Similarity(coords));
		}
		while (seqIdx != -1);
	}
	
	private boolean test(int[] coords, int seqIdx) {
		if (seqIdx != -1) {
			String refSymbol = sequences[0][coords[0]];
			return refSymbol.equals(sequences[seqIdx][coords[seqIdx]]);
		}
		else return false;
	}	

}
