package org.wso2.carbon.mediator.datamapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.avro.io.Encoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.llom.OMTextImpl;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.axiom.soap.SOAP11Constants;
import org.apache.axiom.soap.SOAP12Constants;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.AxisFault;
import org.apache.synapse.MessageContext;
import org.apache.synapse.util.AXIOMUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.mozilla.javascript.JavaScriptException;

import java.io.ByteArrayOutputStream;

import org.codehaus.jettison.mapped.MappedXMLStreamReader;
import org.wso2.datamapper.engine.core.MappingHandler;
import org.wso2.datamapper.engine.core.MappingResourceLoader;
import org.wso2.datamapper.engine.core.writer.DummyEncoder;
import org.wso2.datamapper.engine.core.writer.WriterRegistry;

public class DataMapperHelper {

	//private static final String ERROR_MESSAGE = Messages.DataMapperHelper_errorMessage;

	public static boolean mediateDataMapper(MessageContext context, String configkey,
			String inSchemaKey, String outSchemaKey, String inputType, String outputType) {

		InputStream configFileInputStream = getInputStream(context, configkey);
		InputStream inputSchemaStream = getInputStream(context, inSchemaKey);
		InputStream outputSchemaStream = getInputStream(context, outSchemaKey);

		OMElement inputMessage = context.getEnvelope();
		
		try {

			MappingResourceLoader mappingResourceLoader = new MappingResourceLoader(inputSchemaStream,
					outputSchemaStream, configFileInputStream);
			GenericRecord result = MappingHandler.doMap(inputMessage,mappingResourceLoader);	
			
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			DatumWriter<GenericRecord> writer = null; 
			Encoder encoder;
			
			if(outputType.equals("text/csv")){
		        writer = WriterRegistry.getInstance().get("text/csv").newInstance(); 
			}
			if(outputType.equals("application/xml")){
			    writer = WriterRegistry.getInstance().get("application/xml").newInstance(); 
			}
		    
		    writer.setSchema(result.getSchema());
		    encoder =  new DummyEncoder(byteArrayOutputStream);
		    writer.write(result, encoder);
		    System.out.println(byteArrayOutputStream.toString());
		    encoder.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	         /*GenericRecord finalOutput = mapper.doMap(inputMessage, mappingResourceLoader);
	 		OutputJsonBuilder outJsonBuilder = new OutputJsonBuilder();
			JSONObject result =outJsonBuilder.getOutPut(finalOutput, mappingResourceLoader.getInputRootelement());
			OMElement outmessage = parseJsonToXml(result);

			if (outmessage != null) {
				OMElement firstChild = outmessage.getFirstElement();
				if (firstChild != null) {
					QName resultQName = firstChild.getQName();
					if (resultQName.getLocalPart().equals("envelope")
							&& (resultQName.getNamespaceURI().equals(
									SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI) || resultQName
									.getNamespaceURI().equals(
											SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI))) {
						SOAPEnvelope soapEnvelope = AXIOMUtils.getSOAPEnvFromOM(outmessage
								.getFirstElement());
						if (soapEnvelope != null) {
							try {
								context.setEnvelope(soapEnvelope);
							} catch (AxisFault axisFault) {
								axisFault.printStackTrace();
							}
						}
					} else {
						context.getEnvelope().getBody().setFirstChild(outmessage);
					}
				}
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	private static OMElement parseJsonToXml(JSONObject jsonObject) throws 
			XMLStreamException, IOException, JSONException {
		StringWriter sw = new StringWriter(5120);
		
		MappedXMLStreamReader reader = new MappedXMLStreamReader(jsonObject);

		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		XMLStreamWriter xmlStreamWriter = factory.createXMLStreamWriter(sw);

		xmlStreamWriter.writeStartDocument();
		while (reader.hasNext()) {
			int x = reader.next();
			switch (x) {
			case XMLStreamConstants.START_ELEMENT:
				xmlStreamWriter.writeStartElement(reader.getPrefix(), reader.getLocalName(),
						reader.getNamespaceURI());
				int namespaceCount = reader.getNamespaceCount();
				for (int i = namespaceCount - 1; i >= 0; i--) {
					xmlStreamWriter.writeNamespace(reader.getNamespacePrefix(i),
							reader.getNamespaceURI(i));
				}
				int attributeCount = reader.getAttributeCount();
				for (int i = 0; i < attributeCount; i++) {
					xmlStreamWriter.writeAttribute(reader.getAttributePrefix(i),
							reader.getAttributeNamespace(i), reader.getAttributeLocalName(i),
							reader.getAttributeValue(i));
				}
				break;
			case XMLStreamConstants.START_DOCUMENT:
				break;
			case XMLStreamConstants.CHARACTERS:
				xmlStreamWriter.writeCharacters(reader.getText());
				break;
			case XMLStreamConstants.CDATA:
				xmlStreamWriter.writeCData(reader.getText());
				break;
			case XMLStreamConstants.END_ELEMENT:
				xmlStreamWriter.writeEndElement();
				break;
			case XMLStreamConstants.END_DOCUMENT:
				xmlStreamWriter.writeEndDocument();
				break;
			case XMLStreamConstants.SPACE:
				break;
			case XMLStreamConstants.COMMENT:
				xmlStreamWriter.writeComment(reader.getText());
				break;
			case XMLStreamConstants.DTD:
				xmlStreamWriter.writeDTD(reader.getText());
				break;
			case XMLStreamConstants.PROCESSING_INSTRUCTION:
				xmlStreamWriter
						.writeProcessingInstruction(reader.getPITarget(), reader.getPIData());
				break;
			case XMLStreamConstants.ENTITY_REFERENCE:
				xmlStreamWriter.writeEntityRef(reader.getLocalName());
				break;
			default:
				throw new RuntimeException(ERROR_MESSAGE); //$NON-NLS-1$
			}
		}
		xmlStreamWriter.writeEndDocument();
		xmlStreamWriter.flush();
		xmlStreamWriter.close();

		OMElement element = AXIOMUtil.stringToOM(sw.toString());
		return element;
	}
*/
	private static InputStream getInputStream(MessageContext context, String key) {

		InputStream inputStream = null;
		Object entry = context.getEntry(key);
		if (entry instanceof OMTextImpl) {
			OMTextImpl text = (OMTextImpl) entry;
			String content = text.getText();
			inputStream = new ByteArrayInputStream(content.getBytes());
		}
		return inputStream;
	}

}
