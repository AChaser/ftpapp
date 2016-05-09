package org.weiwei.ftp.extrecognizers;

public interface FTPTextualExtensionRecognizer {

	/**
	 * This method returns true if the given file extension is recognized to be
	 * a textual one.
	 * 
	 * @param ext
	 *            The file extension, always in lower-case.
	 * @return true if the given file extension is recognized to be a textual
	 *         one.
	 */
	public boolean isTextualExt(String ext);
}
