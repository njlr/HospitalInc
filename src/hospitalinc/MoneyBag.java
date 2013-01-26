package hospitalinc;

import hospitalinc.events.PatientHealedEvent;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import nlib.components.BasicComponentRenderable;

public strictfp final class MoneyBag extends BasicComponentRenderable {
	
	public static final int STARTING_MONEY = 200;
	
	private final EventBus eventBus;
	
	private int money;
	
	public int getMoney() {
		
		return this.money;
	}
	
	public MoneyBag(long id, EventBus eventBus) {
		
		super(id);
		
		this.eventBus = eventBus;
		
		this.money = STARTING_MONEY;
		
		this.eventBus.register(this);
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.money = STARTING_MONEY;
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		graphics.setColor(Color.yellow);
		
		graphics.drawString("$" + Integer.toString(this.money) + ".00", gameContainer.getWidth() - 128f, 4f);
	}
	
	@Override
	public void destroy(GameContainer gameContainer) throws SlickException {
		
		super.destroy(gameContainer);
		
		this.eventBus.unregister(this);
	}
	
	@Override
	public float getDepth() {
		
		return Constants.DEPTH_UI;
	}
	
	public void spend(int amount) {
		
		this.money -= amount;
	}
	
	public void add(int amount) {
		
		this.money += amount;
	}
	
	@Subscribe
	public void handlePatientHealedEvent(PatientHealedEvent e) {
		
		switch (e.getPatient().getPatientInfo().getPatientType()) {
		
		case Green:
			
			this.add(Constants.PATIENT_VALUE_GREEN);
			
			break;
			
		case Yellow: 
			
			this.add(Constants.PATIENT_VALUE_YELLOW);
			
			break;
			
		case Red: 
			
			this.add(Constants.PATIENT_VALUE_RED);
			
			break;
		}
	}
}
