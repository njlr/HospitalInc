package hospitalinc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nlib.components.BasicComponent;
import nlib.components.ComponentManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public strictfp final class Ambulance extends BasicComponent {
	
	private final ComponentManager componentManager;
	
	private List<PatientInfo> patientInfos;
	
	private int timer;
	
	private final Random random;
	
	public Ambulance(long id,ComponentManager componentManager) {
		
		super(id);
		
		this.componentManager = componentManager;
		
		this.random = new Random();
		
		this.patientInfos = new ArrayList<PatientInfo>();
		
		this.patientInfos.add(new PatientInfo("John Jackson", "Loving father of three. ", PatientType.Green));
		
		this.patientInfos.add(new PatientInfo("Jack Johnson", "Champion marathon runner. ", PatientType.Yellow));
		this.patientInfos.add(new PatientInfo("Mark Tucker", "A gentleman and a scholar. ", PatientType.Yellow));
		
		this.patientInfos.add(new PatientInfo("Janice Smith", "Cat lover. Home owner. ", PatientType.Red));
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		// this.timer = 0;
		this.timer = Constants.TIME_BETWEEN_PATIENTS;
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		this.timer += delta;
		
		while (this.timer >= Constants.TIME_BETWEEN_PATIENTS) {
			
			this.timer -= Constants.TIME_BETWEEN_PATIENTS;
			
			this.sendNextPatient();
		}
	}
	
	private void sendNextPatient() {
		
		WaitingBed bed = this.getAvailableBed();
		
		if (bed == null) {
			
			// TODO: GG!
		}
		else {
			
			PatientInfo patientInfo = this.patientInfos.get(this.random.nextInt(this.patientInfos.size()));
			
			Patient patient = new Patient(this.componentManager.takeId(), this.componentManager.getEventBus(), patientInfo, bed);
			
			this.componentManager.addComponent(patient);
			
			bed.setPatient(patient);
		}
	}
	
	private WaitingBed getAvailableBed() {
		
		List<WaitingBed> beds = this.componentManager.getComponents(WaitingBed.class);
		
		WaitingBed availableBed = null;
		
		for (WaitingBed bed : beds) {
			
			if (bed.isAvailable()) {
				
				if (availableBed == null) {
					
					availableBed = bed;
				}
				else {
					
					if ((bed.getPosition().getX() < availableBed.getPosition().getX()) && 
							(bed.getPosition().getY() <= availableBed.getPosition().getY())) {
						
						availableBed = bed;
					}
				}
			}
		}
		
		return availableBed;
	}
}
