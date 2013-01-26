package hospitalinc;

import java.util.Random;

import hospitalinc.events.GeneratorCreatedEvent;
import hospitalinc.events.GeneratorDestroyedEvent;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.google.common.eventbus.EventBus;

import nlib.components.BasicComponentRenderable;

public strictfp final class Generator extends BasicComponentRenderable {
	
	private final EventBus eventBus;
	
	private final Vector2f position;
	private final Rectangle area;
	
	private int life;
	private int lifeTime;
	
	private final Random random;
	
	private Animation animation;
	
	public Vector2f getPosition() {
		
		return this.position.copy();
	}
	
	public Rectangle getArea() {
		
		return this.area;
	}
	
	public float getPowerRadius() {
		
		return Constants.GENERATOR_POWER_RADIUS;
	}
	
	public Generator(long id, EventBus eventBus, Vector2f position) {
		
		super(id);
		
		this.eventBus = eventBus;
		
		this.position = new Vector2f(position);
		
		this.area = new Rectangle(this.position.getX(), this.position.getY(), 8f, 8f);
		
		this.random = new Random();
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.life = 0;
		this.lifeTime = Constants.GENERATOR_LIFE_TIME - Constants.GENERATOR_LIFE_TIME_VARIANCE + this.random.nextInt(Constants.GENERATOR_LIFE_TIME_VARIANCE * 2);
		
		this.animation = new Animation(new SpriteSheet("assets/Generator.png", 18, 18), 300);
		
		this.eventBus.post(new GeneratorCreatedEvent(this));
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		this.life += delta;
		
		if (this.life >= this.lifeTime) {
			
			this.destroy(gameContainer);
		}
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		graphics.drawAnimation(this.animation, this.position.getX() - 1f, this.position.getY() - 1f);
	}
	
	@Override
	public float getDepth() {
		
		return Constants.DEPTH_GENERATOR;
	}
	
	@Override
	public void destroy(GameContainer gameContainer) throws SlickException {
		
		super.destroy(gameContainer);
		
		this.eventBus.post(new GeneratorDestroyedEvent(this));
	}
}
