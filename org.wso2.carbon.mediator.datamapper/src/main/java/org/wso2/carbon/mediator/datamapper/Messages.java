package org.wso2.carbon.mediator.datamapper;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private static final String BUNDLE_NAME = "org.wso2.carbon.mediator.datamapper.messages"; //$NON-NLS-1$
	public static String DataMapperHelper_errorMessage;
	public static String DataMapperMediator_startMessage;
	public static String DataMapperMediator_message;
	public static String DataMapperMediator_completeMessage;
	public static String DataMapperMediator_destroyMessage;
	public static String DataMapperMediator_initializedMessage;

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
