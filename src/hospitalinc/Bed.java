package hospitalinc;

import java.util.HashSet;
import java.util.Set;

import hospitalinc.events.GeneratorCreatedEvent;
import hospitalinc.events.GeneratorDestroyedEvent;
import hospitalinc.events.PatientDiedEvent;
import hospitalinc.events.PatientHealedEvent;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.google.common.eventbus.Subscribe;

import nlib.components.BasicComponentRenderable;
import nlib.components.ComponentManager;

public strictfp final class Bed extends BasicComponentRenderable {
	
	private ComponentManager componentManager;
	
	private Set<Generator> generators;
	
	private final Vector2f position;
	private final Rectangle area;
	
	private Patient patient;
	
	private boolean waiting;
	
	private Animation animation;
	
	public Vector2f getPosition() {
		
		return this.position.copy();
	}
	
	public Rectangle getArea() {
		
		return this.area;
	}
	
	public boolean isReceivingPower() {
		
		return !this.generators.isEmpty();
	}
	
	public boolean isAvailable() {
		
		return this.patient == null;
	}
	
	public Patient getPatient() {
		
		return this.patient;
	}
	
	public void setPatient(Patient patient) {
		
		this.patient = patient;
	}
	
	public boolean isWaiting() {
		
		return this.waiting;
	}
	
	public Bed(long id, ComponentManager componentManager, Vector2f position, boolean waiting) {
		
		super(id);
		
		this.componentManager = componentManager;
		
		this.generators = new HashSet<Generator>();
		
		this.position = new Vector2f(position);
		this.area = new Rectangle(this.position.getX(), this.position.getY(), 32f, 64f);
		
		this.waiting = waiting;
		
		this.componentManager.getEventBus().register(this);
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.patient = null;
		
		this.animation = new Animation(new SpriteSheet("assets/gfx/Bed.png", 32, 48), 30);
		
		this.generators.clear();
		
		for (Generator i : this.componentManager.getComponents(Generator.class)) {
			
			if (this.position.distanceSquared(i.getPosition()) <= Math.pow(i.getPowerRadius(), 2)) {
				
				this.generators.add(i);
			}
		}
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		if (this.isReceivingPower()) {
			
			graphics.setColor(Color.yellow);
			
			graphics.draw(this.area);
		}
		
		graphics.drawAnimation(this.animation, this.position.getX(), this.position.getY());
	}
	
	@Override
	public void destroy(GameContainer gameContainer) throws SlickException {
		
		super.destroy(gameContainer);
		
		this.componentManager.getEventBus().unregister(this);
	}
	
	@Override
	public float getDepth() {
		
		return Constants.DEPTH_BED;
	}
	
	@Subscribe
	public void handleGeneratorCreatedEvent(GeneratorCreatedEvent e) {
		
		if (this.position.distanceSquared(e.getGenerator().getPosition()) <= 
				Math.pow(e.getGenerator().getPowerRadius(), 2)) {
			
			this.generators.add(e.getGenerator());
		}
	}
	
	@Subscribe
	public void handleGeneratorDestroyedEvent(GeneratorDestroyedEvent e) {
		
		this.generators.remove(e.getGenerator());
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
}
