package net.ion.radon.core.let;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.ion.framework.util.Debug;
import net.ion.framework.util.chardet.HtmlCharsetDetector;
import net.ion.framework.util.chardet.nsDetector;
import net.ion.framework.util.chardet.nsICharsetDetectionObserver;
import net.ion.framework.util.chardet.nsPSMDetector;

public class EncodeDetector {

	private boolean _found = false;
	private String _charsetName;

	public String detectEncode(byte[] bytes, Locale locale) {
		return detectEncode(new ByteArrayInputStream(bytes), locale);
	}

	/**
	 * 
	 * @param stream
	 */
	public String detectEncode(InputStream stream, Locale locale) {
		try {
			if (stream == null) {
				return null;
			}
			// means detect Chinese
			nsDetector det = new nsDetector(getLang(locale));
			// The Notify() will be called when a matching charset is found.
			det.Init(new nsICharsetDetectionObserver() {

				public void Notify(String charset) {
					HtmlCharsetDetector.found = true;
					_found = true;
					_charsetName = charset;
				}
			});
			BufferedInputStream imp = new BufferedInputStream(stream);
			byte[] buf = new byte[1024];
			int len;
			boolean done = false;
			boolean isAscii = true;
			while ((len = imp.read(buf, 0, buf.length)) != -1) {
				// Check if the stream is only ascii.
				if (isAscii) {
					isAscii = det.isAscii(buf, len);
				}
				if (!isAscii && !done) {
					done = det.DoIt(buf, len, false);
				}
			}
			det.DataEnd();
			if (isAscii) {
				return "US-ASCII";
			}
			if (!_found) {
				String prob[] = det.getProbableCharsets();
				for (int i = 0; i < prob.length; i++) {
					if (prob[i].indexOf("KR") > 0) {
						return "EUC-KR";
					} else if (prob[i].indexOf("JP") > 0) {
						return "EUC-JP";
					} else if (prob[i].indexOf("UTF-8") > 0) {
						return "UTF-8";
					}
				}
				if (prob.length > 0 && prob[0].startsWith("UTF-8")) return prob[0];
			}

			return _charsetName;

		} catch (IOException ex) {
			Logger.getLogger(EncodeDetector.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	private static int getLang(Locale locale) {
		int lang = 0;
		String country = locale.toString();
		if ("ja".equals(country) || "ja_JP".equals(country)) {
			lang = nsPSMDetector.JAPANESE;
		} else if ("zh".equals(country)) {
			lang = nsPSMDetector.CHINESE;
		} else if ("zh_CN".equals(country)) {
			lang = nsPSMDetector.SIMPLIFIED_CHINESE;
		} else if ("zh_TW".equals(country)) {
			lang = nsPSMDetector.TRADITIONAL_CHINESE;
		} else if ("ko".equals(country) || "ko_KR".equals(country)) {
			lang = nsPSMDetector.KOREAN;
		}
		return lang;
	}
}
