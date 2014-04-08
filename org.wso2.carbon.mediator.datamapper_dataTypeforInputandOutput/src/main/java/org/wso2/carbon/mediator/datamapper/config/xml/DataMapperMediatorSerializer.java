package org.wso2.carbon.mediator.datamapper.config.xml;

import org.apache.axiom.om.OMElement;
import org.apache.synapse.Mediator;
import org.apache.synapse.config.xml.*;
import org.wso2.carbon.mediator.datamapper.DataMapperMediator;

public class DataMapperMediatorSerializer extends AbstractMediatorSerializer {

	/*private static final String SERIALIZATION_MESSAGE = Messages.DataMapperMediatorSerializer_serializationMessage;
	private static final String CONFIG_REGISTRYKEY = Messages.DataMapperMediatorSerializer_configRegistryKey;
	private static final String INPUTSCHEMA_REGISTRYKEY = Messages.DataMapperMediatorSerializer_inputSchemaRegistryKey;
	private static final String OUTPUTSHEMA_REGISTRYKEY = Messages.DataMapperMediatorSerializer_outputSchemaRegistryKey;*/

	@Override
	public String getMediatorClassName() {
		return DataMapperMediator.class.getName();
	}

	@Override
	protected OMElement serializeSpecificMediator(Mediator mediator) {
		if (!(mediator instanceof DataMapperMediator)) {
			//handleException(SERIALIZATION_MESSAGE //$NON-NLS-1$
			//		+ mediator.getType());
		}

		DataMapperMediator dataMapperMediator = (DataMapperMediator) mediator;
		OMElement dataMapperElement = fac
				.createOMElement(ConfigurationProperties.DATAMAPPER, synNS);

		if (dataMapperMediator.getConfigurationKey() != null) {
			// Serialize Value using ValueSerializer
			ValueSerializer keySerializer = new ValueSerializer();
			keySerializer.serializeValue(dataMapperMediator.getConfigurationKey(),
					ConfigurationProperties.CONFIG, dataMapperElement);
		} else {
			//handleException(CONFIG_REGISTRYKEY); //$NON-NLS-1$
		}

		if (dataMapperMediator.getInputSchemaKey() != null) {
			ValueSerializer keySerializer = new ValueSerializer();
			keySerializer.serializeValue(dataMapperMediator.getInputSchemaKey(),
					ConfigurationProperties.INPUTSCHEMA, dataMapperElement);
		} else {
			//handleException(INPUTSCHEMA_REGISTRYKEY); //$NON-NLS-1$
		}

		if (dataMapperMediator.getOutputSchemaKey() != null) {
			ValueSerializer keySerializer = new ValueSerializer();
			keySerializer.serializeValue(dataMapperMediator.getOutputSchemaKey(),
					ConfigurationProperties.OUTPUTSCHEMA, dataMapperElement);
		} else {
			//handleException(OUTPUTSHEMA_REGISTRYKEY); //$NON-NLS-1$
		}

		if (dataMapperMediator.getInputType() != null) {
			dataMapperElement.addAttribute(fac.createOMAttribute("inputType", nullNS,dataMapperMediator.getInputType()));			
		} else {
			//handleException(OUTPUTSHEMA_REGISTRYKEY); //$NON-NLS-1$
		}
		if (dataMapperMediator.getOutputType() != null) {
			dataMapperElement.addAttribute(fac.createOMAttribute("outputType", nullNS,dataMapperMediator.getOutputType()));
		} else {
			//handleException(OUTPUTSHEMA_REGISTRYKEY); //$NON-NLS-1$
		}
		
		
		saveTracingState(dataMapperElement, dataMapperMediator);

		return dataMapperElement;
	}

}
