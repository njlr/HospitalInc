package hospitalinc;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import hospitalinc.events.PatientDiedEvent;
import hospitalinc.events.PatientHealedEvent;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import nlib.components.BasicComponentRenderable;

public strictfp abstract class AbstractBed extends BasicComponentRenderable implements Bed {
	
	private final EventBus eventBus;
	
	private Patient patient;

	@Override
	public Patient getPatient() {
		
		return this.patient;
	}
	
	@Override
	public void setPatient(Patient patient) {
		
		if (this.patient != patient) {
			
			this.patient = patient;
			
			if (this.patient != null) {
				
				this.patient.setBed(this);
			}
		}
	}
	
	@Override
	public boolean isAvailable() {
		
		return this.getPatient() == null;
	}
	
	public AbstractBed(long id, EventBus eventBus) {
		
		super(id);
		
		this.eventBus = eventBus;
		
		this.patient = null;
		
		this.eventBus.register(this);
	}
	
	@Override
	public void destroy(GameContainer gameContainer) throws SlickException {
		
		super.destroy(gameContainer);
		
		this.eventBus.unregister(this);
	}
	
	@Subscribe
	public void handlePatientDiedEvent(PatientDiedEvent e) {
		
		if (this.patient != null) {
			
			if (e.getPatient() == this.patient) {
				
				this.patient = null;
			}
		}
	}
	
	@Subscribe
	public void handlePatientHealedEvent(PatientHealedEvent e) {
		
		if (this.patient != null) {
			
			if (e.getPatient() == this.patient) {
				
				this.patient = null;
			}
		}
	}
	
	@Override
	public float getDepth() {
		
		return Constants.DEPTH_BED;
	}
}
