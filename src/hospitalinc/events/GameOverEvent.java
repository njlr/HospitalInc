package hospitalinc.events;

import java.util.List;

import hospitalinc.PatientInfo;

import com.google.common.collect.ImmutableList;

public strictfp final class GameOverEvent extends HospitalIncEvent {
	
	private static final long serialVersionUID = -8751043873604106568L;
	
	private final List<PatientInfo> fatalities;
	
	public List<PatientInfo> getFatalities() {
		
		return ImmutableList.copyOf(this.fatalities);
	}
	
	public GameOverEvent(List<PatientInfo> fatalities) {
		
		super();
		
		this.fatalities = ImmutableList.copyOf(fatalities);
	}
}
