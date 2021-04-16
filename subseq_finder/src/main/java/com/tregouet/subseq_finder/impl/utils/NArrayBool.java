package com.tregouet.subseq_finder.impl.utils;

import org.api.hyperdrive.NArray;

public class NArrayBool extends NArray<Boolean> {

	private final boolean[] data;
	
	public NArrayBool(int[] dimensions) {
		super(dimensions);
		this.data = new boolean[super.size()];
	}

	@Override
	public Boolean get(int idx) {
		return data[idx];
	}

	@Override
	public void set(int idx, Boolean value) {
		data[idx] = value;
	}
	
	public final boolean getBoolean(int idx) {
		return data[idx];
	}
	
	public final void setBoolean(int idx, boolean value) {
		data[idx] = value;
	}
	
	public final boolean getBoolean(int[] coords) {
		return data[super.indexOf(coords)];
	}
	
	public final void setBoolean(int[] coords, boolean value) {
		data[super.indexOf(coords)] = value;
	}	

}
