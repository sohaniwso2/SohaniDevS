package org.wso2.carbon.mediator.datamapper.config.xml;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private static final String BUNDLE_NAME = "org.wso2.carbon.mediator.datamapper.config.xml.messages"; //$NON-NLS-1$
	public static String DataMapperMediatorFactory_configAttribute;
	public static String DataMapperMediatorFactory_inputSchemaAttribute;
	public static String DataMapperMediatorFactory_outputSchemaAttribute;
	public static String DataMapperMediatorSerializer_serializationMessage;
	public static String DataMapperMediatorSerializer_configRegistryKey;
	public static String DataMapperMediatorSerializer_inputSchemaRegistryKey;
	public static String DataMapperMediatorSerializer_outputSchemaRegistryKey;

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
