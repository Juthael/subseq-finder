package com.tregouet.subseq_finder.impl.poset.utils;

import java.util.Arrays;

public class Similarity implements Comparable<Similarity> {

	private final int[] coordinates;
	public static final int NOT_COMPARABLE = -2;
	public static final int LESS_THAN = -1;
	public static final int EQUAL_TO = 0;
	public static final int MORE_THAN = 1;
	
	public Similarity(int[] coordinates) {
		this.coordinates = coordinates;
	}
	
	public int[] getCoordinates() {
		return coordinates;
	}
	
	public int length() {
		return coordinates.length;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(coordinates);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Similarity other = (Similarity) obj;
		if (!Arrays.equals(coordinates, other.coordinates))
			return false;
		return true;
	}

	public int compareTo(Similarity other) {
		if (this.length() == other.length()) {
			if (this.equals(other))
				return EQUAL_TO;
			boolean lessThanOther = true;
			boolean moreThanOther = true;
			int coordIdx = 0;
			while ((lessThanOther || moreThanOther) && (coordIdx < this.length())) {
				if (lessThanOther)
					lessThanOther = (this.getCoordinates()[coordIdx] < other.getCoordinates()[coordIdx]);
				if (moreThanOther)
					moreThanOther = (this.getCoordinates()[coordIdx] > other.getCoordinates()[coordIdx]);
				coordIdx++;	
			}
			if (lessThanOther)
				return LESS_THAN;
			if (moreThanOther)
				return MORE_THAN;
		}
		return NOT_COMPARABLE;
	}

}
