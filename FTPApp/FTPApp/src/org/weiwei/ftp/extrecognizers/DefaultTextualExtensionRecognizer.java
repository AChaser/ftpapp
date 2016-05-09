package org.weiwei.ftp.extrecognizers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class DefaultTextualExtensionRecognizer extends
		ParametricTextualExtensionRecognizer {

	/**
	 * Lock object.
	 */
	private static final Object lock = new Object();

	/**
	 * The singleton instance.
	 */
	private static DefaultTextualExtensionRecognizer instance = null;

	/**
	 * This one returns the default instance of the class.
	 * 
	 * @return An instance of the class.
	 */
	public static DefaultTextualExtensionRecognizer getInstance() {
		synchronized (lock) {
			if (instance == null) {
				instance = new DefaultTextualExtensionRecognizer();
			}
		}
		return instance;
	}

	/**
	 * It builds the instance.
	 */
	private DefaultTextualExtensionRecognizer() {
		BufferedReader r = null;
		try {
			r = new BufferedReader(new InputStreamReader(getClass()
					.getResourceAsStream("textualexts")));
			String line;
			while ((line = r.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line);
				while (st.hasMoreTokens()) {
					addExtension(st.nextToken());
				}
			}
		} catch (Exception e) {
			;
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (Throwable t) {
					;
				}
			}
		}
	}

}
