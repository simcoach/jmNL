package edu.usc.ict.nl.dm.reward.trackers;

import edu.usc.ict.nl.bus.NLBusBase;
import edu.usc.ict.nl.dm.reward.RewardDM;
import edu.usc.ict.nl.kb.DialogueKB;
import edu.usc.ict.nl.kb.InformationStateInterface.ACCESSTYPE;
import edu.usc.ict.nl.utils.Sanitizer;

public class SpeechOverlapTracker extends ValueTracker {

	public SpeechOverlapTracker(RewardDM dm) {
		super(dm);
	}

	@Override
	public Boolean getter() {
		boolean systemSpeaking=dm.getSpeakingTracker().isSpeaking();
		
		DialogueKB is = dm.getInformationState();
		boolean userSpeaking=false;
		try {
			Boolean isUserSpeaking = (Boolean) is.evaluate(is.getValueOfVariable(NLBusBase.userSpeakingStateVarName,ACCESSTYPE.AUTO_OVERWRITEAUTO,null),null);
			userSpeaking=isUserSpeaking!=null && isUserSpeaking;
		} catch (Exception e) {
			dm.getLogger().error(Sanitizer.log(e.getMessage()), e);
		}
		boolean ret=systemSpeaking && userSpeaking;
		setter(ret);
		return ret;
	}
	
	@Override
	protected void setter(Object cv) {
		boolean currentValue=(Boolean)cv;
		Boolean oldValue=(Boolean)value;
		if (oldValue==null || currentValue!=oldValue) {
			value=currentValue;
			if (currentValue) touch();
		}
	}
}
