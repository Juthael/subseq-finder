package com.tregouet.subseq_finder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubseqFinder {

	private final String[] first;
	private final String[] second;
	private final boolean[][] dotPlot;
	private final List<Integer[][]> subseqs = new ArrayList<Integer[][]>();
	
	public SubseqFinder(String[] first, String[] second) {
		this.first = new String[first.length + 2];
		this.second = new String[second.length + 2];
		this.first[0] = "START";
		this.second[0] = "START";
		this.first[this.first.length - 1] = "END";
		this.second[this.second.length - 1] = "END";
		System.arraycopy(first, 0, this.first, 1, first.length);
		System.arraycopy(second, 0, this.second, 1, second.length);
		dotPlot = new boolean[first.length + 2][second.length + 2];
		for (int f=0 ; f < this.first.length ; f++) {
			for (int s=0 ; s < this.second.length ; s++) { 
				if (this.first[f].equals(this.second[s]))
					dotPlot[f][s] = true;
			}
		}
	}
	
	private int[][] returnPartialSubseqtest
}