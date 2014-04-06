package org.wso2.carbon.mediator.datamapper;

import org.apache.synapse.ManagedLifecycle;
import org.apache.synapse.MessageContext;
import org.apache.synapse.SynapseLog;
import org.apache.synapse.core.SynapseEnvironment;
import org.apache.synapse.mediators.AbstractMediator;
import org.apache.synapse.mediators.Value;

/*
 * This interface defines all the managed parts of Synapse including the configuration itself
 */

public class DataMapperMediator extends AbstractMediator implements ManagedLifecycle {

	private static final String START_MESSAGE = Messages.DataMapperMediator_startMessage;
	private static final String MESSAGE = Messages.DataMapperMediator_message;
	private static final String COMPLETE_MESSAGE = Messages.DataMapperMediator_completeMessage;
	private static final String DESTROY_MESSAGE = Messages.DataMapperMediator_destroyMessage;
	private static final String INITIALIZED_MESSAGE = Messages.DataMapperMediator_initializedMessage;

	private Value configurationKey = null;
	private Value inputSchemaKey = null;
	private Value outputSchemaKey = null;

	public Value getConfigurationKey() {
		return configurationKey;
	}

	public void setConfigurationKey(Value xsltKey) {
		this.configurationKey = xsltKey;
	}

	public Value getInputSchemaKey() {
		return inputSchemaKey;
	}

	public void setInputSchemaKey(Value xsltKey) {
		this.inputSchemaKey = xsltKey;
	}

	public Value getOutputSchemaKey() {
		return outputSchemaKey;
	}

	public void setOutputSchemaKey(Value xsltKey) {
		this.outputSchemaKey = xsltKey;
	}

	@Override
	public boolean mediate(MessageContext messageContext) {

		SynapseLog synLog = getLog(messageContext);
		if (synLog.isTraceOrDebugEnabled()) {
			synLog.traceOrDebug(START_MESSAGE); //$NON-NLS-1$

			if (synLog.isTraceTraceEnabled()) {
				synLog.traceTrace(MESSAGE + messageContext.getEnvelope()); //$NON-NLS-1$
			}
		}

		String configkey = configurationKey.evaluateValue(messageContext);
		String inSchemaKey = inputSchemaKey.evaluateValue(messageContext);
		String outSchemaKey = outputSchemaKey.evaluateValue(messageContext);

		DataMapperHelper.mediateDataMapper(messageContext, configkey, inSchemaKey, outSchemaKey);

		if (synLog.isTraceOrDebugEnabled()) {
			synLog.traceOrDebug(COMPLETE_MESSAGE); //$NON-NLS-1$

			if (synLog.isTraceTraceEnabled()) {
				synLog.traceTrace(MESSAGE + messageContext.getEnvelope()); //$NON-NLS-1$
			}
		}
		return true;
	}

	/*
	 * true if the mediator is intended to interact with the MessageContext
	 */
	public boolean isContentAware() {
		return false;
	}

	@Override
	public void destroy() {
		log.info(DESTROY_MESSAGE + DataMapperMediator.class.getName()); //$NON-NLS-1$
	}

	@Override
	public void init(SynapseEnvironment arg0) {

		if (log.isDebugEnabled()) {
			log.debug(INITIALIZED_MESSAGE //$NON-NLS-1$
					+ DataMapperMediator.class.getName());
		}
	}
}