package hospitalinc.events;

import hospitalinc.Patient;

public strictfp final class PatientHealedEvent extends HospitalIncEvent {
	
	private static final long serialVersionUID = -3425210353345619922L;
	
	private final Patient patient;
	
	public Patient getPatient() {
		
		return this.patient;
	}
	
	public PatientHealedEvent(Patient patient) {
		
		super();
		
		this.patient = patient;
	}
}
