package com.tregouet.subseq_finder.impl.poset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.subseq_finder.ISubseq;
import com.tregouet.subseq_finder.ISubseqFinder;
import com.tregouet.subseq_finder.exceptions.SubseqException;
import com.tregouet.subseq_finder.impl.Subseq;
import com.tregouet.subseq_finder.impl.dot_plot.utils.CoordinatesDP;
import com.tregouet.subseq_finder.impl.poset.utils.CoordinatesPOS;
import com.tregouet.subseq_finder.impl.poset.utils.Similarity;

public class SubseqFinderPOS implements ISubseqFinder {

	private final String[][] sequences;
	private final int subseqMaxSize;
	private final List<Similarity> similarities = new ArrayList<Similarity>();
	private final Map<Similarity, Set<Similarity>> succRelation = new HashMap<Similarity, Set<Similarity>>();
	private final Set<Similarity> seqSeeds = new HashSet<Similarity>();
	private final Set<ISubseq> subsequences = new HashSet<ISubseq>();
	
	public SubseqFinderPOS(String[][]sequences) {
		this.sequences = sequences;
		subseqMaxSize = setSubseqMaxSize();
		setSimilarities();
		setSuccessorRelation();
		setSubsequenceSeeds();
		setSubsequences();
	}
	
	public Set<ISubseq> getSubseqs() {
		return subsequences;
	}

	public Set<List<String>> getStringSubseqs() {
		Set<List<String>> stringSubseqs = new HashSet<List<String>>();
		for (ISubseq subseq : subsequences)
			stringSubseqs.add(subseq.) //HERE
	}
	
	private void setSuccessorRelation() {
		//set as relation (not reflective)
		for (Similarity sim : similarities)
			succRelation.put(sim, new HashSet<Similarity>());
		for (int i=0 ; i < similarities.size() ; i++) {
			for (int j=i ; j < similarities.size() ; j++) {
				Similarity sim1 = similarities.get(i);
				Similarity sim2 = similarities.get(j);
				int comparison = sim1.compareTo(sim2);
				if (comparison == Similarity.LESS_THAN)
					succRelation.get(sim1).add(sim2);
				else if (comparison == Similarity.MORE_THAN) {
					succRelation.get(sim2).add(sim1);
				}
			}
		}
		//set as successor relation
		for (int i=0 ; i < similarities.size() ; i++) {
			for (int j=0 ; j < similarities.size() ; j++) {
				if ((i != j) && (succRelation.get(similarities.get(j)).contains(similarities.get(i)))) {
					succRelation.get(similarities.get(j)).removeAll(succRelation.get(similarities.get(i)));
				}
			}
		}
	}
	
	private void setSubsequenceSeeds() {
		seqSeeds.addAll(similarities);
		for (Similarity keySim : succRelation.keySet()) {
			seqSeeds.removeAll(succRelation.get(keySim)) ;
		}
	}
	
	private int setSubseqMaxSize() {
		int maxSize = sequences[0].length;
		for (int i=1 ; i < sequences.length ; i++) {
			if (sequences[i].length < maxSize)
				maxSize = sequences[i].length;
		}
		return maxSize;
	}
	
	private void setSubsequences() {
		for (Similarity seed : seqSeeds) {
			ISubseq newSubseq = new Subseq(subseqMaxSize, sequences.length);
			Set<Similarity> nextSimilarities = succRelation.get(seed);
			if (!nextSimilarities.isEmpty())
				subsequences.add(newSubseq);
			else {
				for (Similarity next : nextSimilarities)
					continueSubsequence(newSubseq, next);
			}
		}
	}
	
	//recursive
	private void continueSubsequence(ISubseq subseqParam, Similarity sim) {
		ISubseq subseq = subseqParam.clone();
		subseq.addNewCoord(sim.getCoordinates());
		Set<Similarity> relatedSim = succRelation.get(sim);
		if (relatedSim.isEmpty()) {
			//then similarity in parameter is a leaf
			subsequences.add(subseq);
		}
		else {
			for (Similarity nextSim : relatedSim) {
				//recursion
				continueSubsequence(subseq, nextSim);
			}
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
			similar = testSimilarity(coords, seqIdx);
			if (similar && (seqIdx == (sequences.length - 1)))
				similarities.add(new Similarity(coords));
		}
		while (seqIdx != -1);
	}
	
	private boolean testSimilarity(int[] coords, int seqIdx) {
		if (seqIdx != -1) {
			String refSymbol = sequences[0][coords[0]];
			return refSymbol.equals(sequences[seqIdx][coords[seqIdx]]);
		}
		else return false;
	}
	
	private boolean addSubSeqToSet(ISubseq subseq) {
		boolean maxSubset = true;
		Set<ISubseq> notMaxSubsetsAfterAll = new HashSet<ISubseq>();
		Iterator<ISubseq> iterator = subsequences.iterator();
		while (maxSubset && iterator.hasNext()) {
			ISubseq other = iterator.next();
			int comparison = subseq.compareTo(other);
			if (comparison == Subseq.LESS_THAN)
				maxSubset = false;
			else if (comparison == Subseq.MORE_THAN)
				notMaxSubsetsAfterAll.add(other);
		}
		subsequences.removeAll(notMaxSubsetsAfterAll);
		if (maxSubset)
			subsequences.add(subseq);
		return maxSubset;
	}

}
