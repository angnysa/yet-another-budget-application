package org.angnysa.yaba.swing;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private static final String BUNDLE_NAME = "org.angnysa.yaba.l10n.messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages() {
	}

	public static String getString(String key, Object... args) {
		try {
			String str =  RESOURCE_BUNDLE.getString(key);
			return String.format(str, args);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
