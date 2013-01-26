package hospitalinc;

import hospitalinc.events.PatientDiedEvent;
import hospitalinc.events.PatientHealedEvent;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import com.google.common.eventbus.EventBus;

import nlib.components.BasicComponentRenderable;

public strictfp final class Patient extends BasicComponentRenderable {
	
	private final EventBus eventBus;
	
	private final PatientInfo patientInfo;
	
	private final Vector2f position;
	
	private Bed bed;
	
	private int health;
	
	private int timeTillHealed;
	private int healTime;
	
	public PatientInfo getPatientInfo() {
		
		return this.patientInfo;
	}
	
	public Vector2f getPosition() {
		
		return this.position.copy();
	}
	
	public void setPosition(float x, float y) {
		
		this.position.set(x, y);
	}
	
	public Bed getBed() {
		
		return bed;
	}
	
	public void setBed(Bed bed) {
		
		if (bed != null) {
			
			this.position.set(bed.getArea().getCenterX(), bed.getArea().getCenterY());
		}
		
		if (bed != this.bed) {
			
			this.bed = bed;
			
			if (this.bed != null) {
				
				this.bed.setPatient(this);
			}
		}
	}
	
	public Patient(long id, EventBus eventBus, PatientInfo patientInfo, Bed bed) {
		
		super(id);
		
		this.eventBus = eventBus;
		
		this.patientInfo = patientInfo;
		
		this.position = new Vector2f();
		
		this.setBed(bed);
		
		this.eventBus.register(this);
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.health = Constants.PATIENT_HEALTH;
		
		switch (this.patientInfo.getPatientType()) {
		
		case Green:
			
			this.healTime = Constants.PATIENT_HEALING_TIME_GREEN;
			
			break;
			
		case Yellow:
			
			this.healTime = Constants.PATIENT_HEALING_TIME_YELLOW;
			
			break;
			
		case Red:
			
			this.healTime = Constants.PATIENT_HEALING_TIME_RED;
			
			break;
			
		default:
			
			this.healTime = Constants.PATIENT_HEALING_TIME_GREEN;
			
			break;
		}
		
		this.timeTillHealed = this.healTime;
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		if (this.bed.isHealing()) {
			
			if (this.health < Constants.PATIENT_HEALTH) {
				
				this.health += delta;
				
				if (this.health > Constants.PATIENT_HEALTH) {
					
					this.health = Constants.PATIENT_HEALTH;
				}
			}
			
			this.timeTillHealed -= delta;
			
			if (this.timeTillHealed <= 0) {
				
				this.eventBus.post(new PatientHealedEvent(this));
				
				this.destroy(gameContainer);
			}
		}
		else {
			
			this.health -= delta;
			
			if (this.health <= 0) {
				
				this.eventBus.post(new PatientDiedEvent(this));
				
				this.destroy(gameContainer);
			}
		}
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		switch (this.patientInfo.getPatientType()) {
		
		case Green:
			
			graphics.setColor(Color.green);
			
			break;
			
		case Yellow:
			
			graphics.setColor(Color.yellow);
			
			break;
			
		case Red:
			
			graphics.setColor(Color.red);
			
			break;

		default:
			
			graphics.setColor(Color.green);
			
			break;
		}
		
		float w = 12f;
		float h = 24f;
		
		graphics.fillRect(this.position.getX() - w / 2f, this.position.getY() - h / 2f, w, h);
		
		if (this.health < Constants.PATIENT_HEALTH) {
			
			this.drawBar(graphics, this.position.getX() - 12f, this.position.getY() - 6f, 24f, 6f, this.health / (float) Constants.PATIENT_HEALTH, Color.red, Color.black);
		}
		else {
			
			this.drawBar(graphics, this.position.getX() - 12f, this.position.getY() - 6f, 24f, 6f, 1f - this.timeTillHealed / (float) this.healTime, Color.green, Color.black);
		}
	}
	
	@Override
	public float getDepth() {
		
		return Constants.DEPTH_PATIENT;
	}
	
	private void drawBar(Graphics graphics, float x, float y, float w, float h, float p, Color color, Color colorBackground) {
		
		graphics.setColor(colorBackground);
		
		graphics.fillRect(this.position.getX() - w / 2, this.position.getY() - h / 2, w, h);
		
		graphics.setColor(color);
		
		graphics.fillRect(this.position.getX() - w / 2 + 1, this.position.getY() - h / 2 + 1, (w - 2) * p, h - 2);
	}
	
	@Override
	public void destroy(GameContainer gameContainer) throws SlickException {
		
		super.destroy(gameContainer);
		
		this.eventBus.unregister(this);
	}
}
