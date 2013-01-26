package hospitalinc;

public strictfp final class PatientInfo {
	
	private final String name;
	private final String bio;
	
	private final PatientType patientType;
	
	public String getName() {
		
		return this.name;
	}
	
	public String getBio() {
		
		return this.bio;
	}
	
	public PatientType getPatientType() {
		
		return this.patientType;
	}
	
	public PatientInfo(String name, String bio, PatientType patientType) {
		
		super();
		
		this.name = name;
		this.bio = bio;
		
		this.patientType = patientType;
	}
	
	public static final PatientInfo PATIENT_INFO_TEST = new PatientInfo("Bernardo De La Paz", "Baller", PatientType.Green);
}
