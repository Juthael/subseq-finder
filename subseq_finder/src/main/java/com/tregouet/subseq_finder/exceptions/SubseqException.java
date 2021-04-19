package com.tregouet.subseq_finder.exceptions;

public class SubseqException extends Exception {

	private static final long serialVersionUID = 5823318307906820323L;

	public SubseqException() {
	}

	public SubseqException(String arg0) {
		super(arg0);
	}

	public SubseqException(Throwable arg0) {
		super(arg0);
	}

	public SubseqException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public SubseqException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
