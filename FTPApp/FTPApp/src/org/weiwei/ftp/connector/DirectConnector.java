package org.weiwei.ftp.connector;

import java.io.IOException;
import java.net.Socket;

import org.weiwei.ftp.FTPConnector;

/**
 * The DirectConnector connects the remote host with a straight socket
 * connection, using no proxy.
 */
public class DirectConnector extends FTPConnector {

	@Override
	public Socket connectForCommunicationChannel(String host, int port)
			throws IOException {
		// TODO Auto-generated method stub
		return tcpConnectForCommunicationChannel(host, port);
	}

	@Override
	public Socket connectForDataTransferChannel(String host, int port)
			throws IOException {
		// TODO Auto-generated method stub
		return tcpConnectForDataTransferChannel(host, port);
	}

}
