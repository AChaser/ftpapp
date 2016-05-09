package org.weiwei.ftp;


import java.net.Socket;

import org.weiwei.ftp.exception.FTPDataTransferException;

public interface FTPDataTransferConnectionProvider {

	/**
	 * Returns the connection.
	 * 
	 * @return The connection.
	 * @throws FTPException
	 *             If an unexpected error occurs.
	 */
	public Socket openDataTransferConnection() throws FTPDataTransferException;

	/**
	 * Terminates the provider.
	 */
	public void dispose();
}
