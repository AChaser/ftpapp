package org.weiwei.ftp;
/**
 * 该接口作为FTP命令通道的监听器
 * @author weiwei
 *
 */
public interface FTPCommunicationListener {
	/**
	 * Called every time a telnet statement has been sent over the network to
	 * the remote FTP server.
	 * 
	 * @param statement
	 *            The statement that has been sent.
	 */
	public void sent(String statement);

	/**
	 * Called every time a telnet statement is received by the client.
	 * 
	 * @param statement
	 *            The received statement.
	 */
	public void received(String statement);


}
