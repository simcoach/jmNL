package edu.usc.ict.nl.bus.modules;

import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import edu.usc.ict.nl.bus.NLBusInterface;
import edu.usc.ict.nl.bus.events.DMInterruptionRequest;
import edu.usc.ict.nl.bus.events.DMSpeakEvent;
import edu.usc.ict.nl.bus.events.NLGEvent;
import edu.usc.ict.nl.bus.events.SystemUtteranceInterruptedEvent;
import edu.usc.ict.nl.config.NLBusConfig;
import edu.usc.ict.nl.kb.DialogueKBInterface;
import edu.usc.ict.nl.utils.LogConfig;

public abstract class NLG implements NLGInterface {
	private NLBusConfig configuration;
	private NLBusInterface nlModule;

	protected static final Logger logger = Logger.getLogger(NLG.class.getName());
	static {
		URL log4Jresource=LogConfig.findLogConfig("src","log4j.properties", false);
		if (log4Jresource != null)
			PropertyConfigurator.configure( log4Jresource );
	}

	public NLG(NLBusConfig c) {
		this.configuration=c;
	}

	public NLBusConfig getConfiguration() {return configuration;}
	public NLBusInterface getNLModule() {return nlModule;}
	public void setNLModule(NLBusInterface nl) {this.nlModule=nl;}
	
	@Override
	public NLGEvent doNLG(Long sessionID, DMSpeakEvent ev,boolean simulate) throws Exception {
		throw new Exception("un-implemented");
	}

	@Override
	public DialogueKBInterface getKBForEvent(DMSpeakEvent ev) throws Exception {
		// first attemp to get the local context
		DialogueKBInterface context=ev.getLocalInformationState();
		// if not there get the one associated with the DM
		if (context==null) {
			DM dm=getNLModule().getPolicyDMForSession(ev.getSessionID());
			context=dm.getInformationState();
		}
		return context;
	}
	
	@Override
	public String getAudioFileName4SA(String arg0) throws Exception {
		throw new Exception("un-implemented");
	}
	
	@Override
	public void interrupt(DMInterruptionRequest ev) throws Exception {
		Long sessionID=ev.getSessionID();
		DM dm=nlModule.getPolicyDMForSession(sessionID);
		dm.handleEvent(new SystemUtteranceInterruptedEvent(ev.getPayload().getDMEventName(), sessionID,ev.getSourceEvent()));
	}
}
