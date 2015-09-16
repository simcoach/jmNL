package edu.usc.ict.nl.nlu.opennlp;

import java.io.File;

import opennlp.maxent.DataStream;

import org.apache.log4j.Logger;

import edu.usc.ict.nl.nlu.TrainingDataFormat;
import edu.usc.ict.nl.nlu.trainingFileReaders.MXNLUTrainingFile;
import edu.usc.ict.nl.util.StringUtils;
import edu.usc.ict.nl.utils.Sanitizer;

public class NLUTrainingFileReader implements DataStream {
	
    private static final Logger logger = Logger.getLogger(NLUTrainingFileReader.class);

	private MXNLUTrainingFile dataReader=null;
	private TrainingDataFormat next=null;

	public NLUTrainingFileReader(File input) {
		dataReader = new MXNLUTrainingFile();
		try {
			next = dataReader.getNextTrainingInstance(input);
		} catch (Exception e) {
			logger.error(Sanitizer.log(e.getMessage()), e);
		}
	}

	@Override
	public Object nextToken() {
		TrainingDataFormat current=next;
		try {
			next=dataReader.getNextTrainingInstance();
		} catch (Exception e) {
			next=null;
			logger.error(Sanitizer.log(e.getMessage()), e);
		}
		return convertToString(current);
	}

	@Override
	public boolean hasNext() {
		return next!=null;
	}
	
	public String convertToString(TrainingDataFormat td) {
		if (td!=null) {
			String fs=td.getFeatures();
			if (StringUtils.isEmptyString(fs)) fs=td.getUtterance();
			return fs+" "+td.getLabel();
		}
		return null;
	}

}
