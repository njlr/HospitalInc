package hospitalinc.events;

import hospitalinc.Patient;

public strictfp final class PatientDiedEvent extends HospitalIncEvent {
	
	private static final long serialVersionUID = -7890247453504487455L;
	
	private final Patient patient;
	
	public Patient getPatient() {
		
		return this.patient;
	}
	
	public PatientDiedEvent(Patient patient) {
		
		super();
		
		this.patient = patient;
	}
}
