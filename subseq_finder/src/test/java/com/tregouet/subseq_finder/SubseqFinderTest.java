package com.tregouet.subseq_finder;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.tregouet.subseq_finder.exceptions.SubseqException;

public class SubseqFinderTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void whenSubseqsRequestedThenSupplied() throws SubseqException {
		String[] array1 = {"a", "b", "c", "b", "d", "b"};
		String[] array2 = {"a", "c", "b", "c", "d", "b"};
		SubseqFinder finder = new SubseqFinder(array1, array2);
		Set<Subseq> returned = finder.getSubseqs(); 
		finder.printSubsetStrings();
		assertTrue(!returned.isEmpty());
	}

}
