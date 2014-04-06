package org.wso2.carbon.mediator.datamapper.config.xml;

import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;
import org.apache.synapse.Mediator;
import org.apache.synapse.config.xml.*;
import org.apache.synapse.mediators.Value;
import org.wso2.carbon.mediator.datamapper.DataMapperMediator;

import javax.xml.namespace.QName;

import java.util.Properties;

/*Configuration syntax:
 * <datamapper config="gov:datamapper/mappingConfig.dmc"
 *   inputSchema="gov:datamapper/inputSchema.avsc"
 *   outputSchema="gov:datamapper/outputSchema.avsc"/> 
 */

public class DataMapperMediatorFactory extends AbstractMediatorFactory {

	private static final String CONFIG_ATTRIBUTE = Messages.DataMapperMediatorFactory_configAttribute;
	private static final String INPUTSCHEMA_ATTRIBUTE = Messages.DataMapperMediatorFactory_inputSchemaAttribute;
	private static final String OUTPUTSCHEMA_ATTRIBUTE = Messages.DataMapperMediatorFactory_outputSchemaAttribute;

	private static final QName TAG_QNAME = new QName(XMLConfigConstants.SYNAPSE_NAMESPACE,
			ConfigurationProperties.DATAMAPPER);

	@Override
	public QName getTagQName() {
		return TAG_QNAME;
	}

	@Override
	protected Mediator createSpecificMediator(OMElement element, Properties properties) {

		DataMapperMediator datamapperMediator = new DataMapperMediator();

		OMAttribute configKeyAttribute = element.getAttribute(new QName(
				ConfigurationProperties.CONFIG));
		OMAttribute inputSchemaKeyAttribute = element.getAttribute(new QName(
				ConfigurationProperties.INPUTSCHEMA));
		OMAttribute outputSchemaKeyAttribute = element.getAttribute(new QName(
				ConfigurationProperties.OUTPUTSCHEMA));

		/*
		 * ValueFactory for creating dynamic or static Value and provide methods
		 * to create value objects
		 */
		ValueFactory keyFac = new ValueFactory();

		if (configKeyAttribute != null) {
			// Create dynamic or static key based on OMElement
			Value configKeyValue = keyFac.createValue(configKeyAttribute.getLocalName(), element);
			// set key as the Value
			datamapperMediator.setConfigurationKey(configKeyValue);
		} else {
			handleException(CONFIG_ATTRIBUTE); //$NON-NLS-1$
		}

		if (inputSchemaKeyAttribute != null) {
			Value configKeyValue = keyFac.createValue(inputSchemaKeyAttribute.getLocalName(),
					element);
			datamapperMediator.setInputSchemaKey(configKeyValue);
		} else {
			handleException(INPUTSCHEMA_ATTRIBUTE); //$NON-NLS-1$
		}

		if (outputSchemaKeyAttribute != null) {
			Value configKeyValue = keyFac.createValue(outputSchemaKeyAttribute.getLocalName(),
					element);
			datamapperMediator.setOutputSchemaKey(configKeyValue);
		} else {
			handleException(OUTPUTSCHEMA_ATTRIBUTE); //$NON-NLS-1$
		}

		processAuditStatus(datamapperMediator, element);

		return datamapperMediator;
	}
}
