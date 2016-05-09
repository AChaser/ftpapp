package org.weiwei.ftp.exception;

public class FTPDataTransferException extends Exception{

	private static final long serialVersionUID = 1L;

	public FTPDataTransferException() {
		super();
	}

	public FTPDataTransferException(String message, Throwable cause) {
		super(message, cause);
	}

	public FTPDataTransferException(String message) {
		super(message);
	}

	public FTPDataTransferException(Throwable cause) {
		super(cause);
	}



}
