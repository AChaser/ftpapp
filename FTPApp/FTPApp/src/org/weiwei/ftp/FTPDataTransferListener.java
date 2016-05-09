package org.weiwei.ftp;

/**
 * FTP数据传输过程中的监听器
 * @author weiwei
 *
 */
public interface FTPDataTransferListener {
	/**
	 * Called to notify the listener that the transfer operation has been
	 * initialized.
	 */
	public void started();

	/**
	 * Called to notify the listener that some bytes have been transmitted.
	 * 
	 * @param length
	 *            The number of the bytes transmitted since the last time the
	 *            method was called (or since the begin of the operation, at the
	 *            first call received).
	 */
	public void transferred(int length);

	/**
	 * Called to notify the listener that the transfer operation has been
	 * successfully complete.
	 */
	public void completed();

	/**
	 * Called to notify the listener that the transfer operation has been
	 * aborted.
	 */
	public void aborted();

	/**
	 * Called to notify the listener that the transfer operation has failed due
	 * to an error.
	 */
	public void failed();


}
