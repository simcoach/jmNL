package edu.usc.ict.nl.dm.fsm.scxml;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.commons.scxml.EventDispatcher;
import org.apache.log4j.Logger;

import edu.usc.ict.nl.bus.events.DMSpeakEvent;
import edu.usc.ict.nl.bus.events.Event;
import edu.usc.ict.nl.bus.events.changes.VarChange;
import edu.usc.ict.nl.bus.events.changes.DMVarChangeEvent;
import edu.usc.ict.nl.bus.modules.DMEventsListenerInterface;
import edu.usc.ict.nl.dm.reward.model.DialogueOperatorEffect;
import edu.usc.ict.nl.utils.Sanitizer;


public class SystemEventDispatcher implements EventDispatcher, Serializable {

	static final Logger logger = Logger.getLogger(SystemEventDispatcher.class);
	
	private static final long serialVersionUID = 1L;
	transient private DMEventsListenerInterface listener;
	transient private Long sessionID;

	public SystemEventDispatcher(Long sid,DMEventsListenerInterface l) {
		this.sessionID=sid;
		this.listener=l;
	}
	
	@Override
	public void cancel(String sendId) {
	}

	public void internalSend(String event,Map params) {
		System.out.println("received this internal event "+event+" with parameters: "+params);
		if (listener!=null) try {listener.handleDMResponseEvent(new DMSpeakEvent(null,event,sessionID,params,null));} catch (Exception e) {logger.error(Sanitizer.log(e.getMessage()), e);}
	}
	
	@Override
	public void send(String sendId, String target, String type, String event,
			Map params, Object hints, long delay, List externalNodes) {
		if (target.equals("dispatcher")) {
			if (type.equals("systemSpeechAct")) {
				System.out.println("received this system speech act "+event+" with parameters: "+params);
				if (listener!=null) try {listener.handleDMResponseEvent(new DMSpeakEvent(null,event,sessionID,params,null));} catch (Exception e) {logger.error(Sanitizer.log(e.getMessage()), e);}
			} else if (type.equals("changeEvent")) {
				System.out.println("received this change event "+event+" with parameters: "+params);
				DialogueOperatorEffect oldValueAndVar;
				try {
					oldValueAndVar = DialogueOperatorEffect.createAssignment(event, null);
					if (listener!=null) try {listener.handleDMResponseEvent(new DMVarChangeEvent(null,sessionID,new VarChange(oldValueAndVar, params)));} catch (Exception e) {logger.error(Sanitizer.log(e.getMessage()), e);}
				} catch (Exception e) {
					logger.error(Sanitizer.log(e.getMessage()), e);
				}
			}
		}
	}

	public boolean setHoldingOfEventsAs(boolean newSetup) {
		if (listener!=null) {
			boolean currentSetup=listener.getHoldProcessingOfResponseEvents();
			listener.setHoldProcessingOfResponseEvents(newSetup);
			return currentSetup;
		} else return false;
	}

	public void clearHeldEvents() {
		if (listener!=null) listener.clearHeldEvents(sessionID);
	}

	public Queue<Event> getHeldEvents() {
		if (listener!=null) return listener.getUnprocessedResponseEvents(sessionID);
		else return null;
	}

}
