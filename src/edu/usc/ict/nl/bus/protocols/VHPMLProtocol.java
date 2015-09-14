package edu.usc.ict.nl.bus.protocols;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import edu.usc.ict.nl.bus.NLBus;
import edu.usc.ict.nl.pml.PMLStateKeeper;
import edu.usc.ict.nl.vhmsg.VHBridge;
import edu.usc.ict.nl.vhmsg.VHBridge.VRPerception;
import edu.usc.ict.perception.pml.Pml;
import edu.usc.ict.vhmsg.MessageEvent;
import edu.usc.ict.vhmsg.MessageListener;

public class VHPMLProtocol extends Protocol {
	// PML processing
	private Unmarshaller pmlParser;
	private XMLReader xmlReader;
	private PMLStateKeeper pmlStateKeeper=null;
	private VHBridge vhBridge=null;

	public VHPMLProtocol(NLBus bus) throws Exception {
		super(bus);
		if (config.hasVHConfig() &&  config.getPmlListening()) {
			vhBridge=new VHBridge(config.getVhServer(), config.getVhTopic());
			JAXBContext jc = JAXBContext.newInstance(Pml.class);
			pmlParser = jc.createUnmarshaller();
			pmlStateKeeper=new PMLStateKeeper(bus);
			vhBridge.addMessageListenerFor("vrPerception", createVrPerceptionMessageListener());
			
	        SAXParserFactory spf = SAXParserFactory.newInstance();
	        spf.setValidating(true);
	        spf.setXIncludeAware(false);
	        spf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
	        xmlReader = spf.newSAXParser().getXMLReader();
	        xmlReader.setEntityResolver(new EntityResolver() {
				
				@Override
				public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
					return new InputSource(); // do not allow external entities
				}
			});
		} else {
			logger.error(this.getClass()+" requested to start but no VH/PML configuration.");
		}
	}
	
	protected MessageListener createVrPerceptionMessageListener() {
		return new MessageListener() {

			public void messageAction(MessageEvent e)
			{
				VHBridge.VRPerception msg=null;
				try {
					// this will fire an exception if the input message is not a vrExpress message. 
					msg=vhBridge.processVrPerceptionEvent(e);
				} catch (Exception ex){
					//logger.warn("MessageListener.messageAction received non vrPerception message.");
				}
				if (msg!=null) {
					try {
						handlePerceptionEvent(msg);
					} catch (Exception e1) {
						logger.error("Error processing PML message:",e1);
					}
				}
			}
		};
	}
	/**
	 * Handles PML event from MultiSense
	 * @param msg
	 * @throws Exception
	 */
	public void handlePerceptionEvent(VRPerception msg) throws Exception {
		ByteArrayInputStream byteStream = new ByteArrayInputStream (msg.getPerceptionEvent().getBytes());
        InputSource inputSource = new InputSource(byteStream);
        SAXSource source = new SAXSource(xmlReader, inputSource);
		Pml pml = (Pml)pmlParser.unmarshal(source);
		pmlStateKeeper.add(pml);
	}
	@Override
	public void kill() {
		if (vhBridge!=null) vhBridge.sendComponentKilled(config.getVhComponentId());
	}
}
