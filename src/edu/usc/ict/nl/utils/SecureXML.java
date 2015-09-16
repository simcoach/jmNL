package edu.usc.ict.nl.utils;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class SecureXML {

	/**
	 * Creates a {@link DocumentBuilderFactory} with settings to
	 * prevent XML Entity Expansion Injection attacks
	 * @return Factory that disallows entity declarations and expansions
	 * @throws ParserConfigurationException 
	 */
	public static DocumentBuilderFactory newSafeBuilderFactory() throws ParserConfigurationException {
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    	// disallow all doctype declarations in xerces2
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        // actually only needed if certain doctype declarations are still allowed
    	factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
    	factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
    	// enable secure processing
    	factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING , true);
    	// disable xinclude
    	factory.setXIncludeAware(false);
    	// disable entity reference expansions
    	factory.setExpandEntityReferences(false);
    	factory.setValidating(true);
    	return factory;
	}
}