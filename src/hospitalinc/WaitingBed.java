package hospitalinc;

import nlib.components.ComponentManager;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public strictfp final class WaitingBed extends AbstractBed implements Bed {
	
	private final Vector2f position;
	private final Rectangle area;
	
	private final boolean isHorizontal;
	
	private Animation animation;
	
	public Vector2f getPosition() {
		
		return this.position.copy();
	}
	
	public Rectangle getArea() {
		
		return this.area;
	}
	
	public WaitingBed(long id, ComponentManager componentManager, Vector2f position, boolean isHorizontal) {
		
		super(id, componentManager.getEventBus());
		
		this.position = new Vector2f(position);
		
		this.isHorizontal = isHorizontal;
		
		if (this.isHorizontal) {
			
			this.area = new Rectangle(this.position.getX(), this.position.getY(), 48f, 32f);
		}
		else {
			
			this.area = new Rectangle(this.position.getX(), this.position.getY(), 32f, 48f);
		}
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		if (this.isHorizontal) {
			
			this.animation = new Animation(new SpriteSheet("assets/gfx/WaitingBedHorizontal.png", 32, 48), 30);
		}
		else {
			
			this.animation = new Animation(new SpriteSheet("assets/gfx/WaitingBed.png", 32, 48), 30);
		}
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		graphics.drawAnimation(this.animation, this.position.getX(), this.position.getY());
	}

	@Override
	public boolean isHealing() {
		
		return false;
	}

	@Override
	public Vector2f getPatientPosition() {
		
		return new Vector2f(this.area.getCenterX(), this.area.getCenterY());
	}
}
