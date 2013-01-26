package hospitalinc;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import nlib.components.BasicComponentRenderable;
import nlib.components.ComponentManager;

public strictfp final class BedSite extends BasicComponentRenderable implements Selectable {
	
	private final ComponentManager componentManager;
	
	private final Vector2f position;
	private final Rectangle area;
	
	private final boolean isHorizontal;
	
	private boolean isHover;
	
	private boolean isSelectable;
	
	private Animation animationBed;
	
	public Vector2f getPosition() {
		
		return this.position;
	}
	
	@Override
	public Vector2f getPositionCenter() {
		
		return new Vector2f(this.area.getCenterX(), this.area.getCenterY());
	}
	
	public Rectangle getArea() {
		
		return this.area;
	}
	
	@Override
	public float getRadius() {
		
		return this.area.getBoundingCircleRadius();
	}
	
	@Override
	public boolean isHover() {
		
		return this.isHover;
	}
	
	public BedSite(long id, ComponentManager componentManager, Vector2f position, boolean isHorizontal) {
		
		super(id);
		
		this.componentManager = componentManager;
		
		this.isHorizontal = isHorizontal;
		
		this.position = new Vector2f(position);
		
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
		
		this.isHover = false;
		
		this.isSelectable = true;
		
		if (this.isHorizontal) {
			
			this.animationBed = new Animation(new SpriteSheet("assets/gfx/HealingBedHorizontal.png", 48, 32), 30);
		}
		else {
			
			this.animationBed = new Animation(new SpriteSheet("assets/gfx/HealingBed.png", 32, 48), 30);
		}
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		graphics.setColor(Color.gray);
		
		graphics.fill(this.area);
		
		if (this.isHover) {
			
			graphics.drawAnimation(this.animationBed, this.position.getX(), this.position.getY(), Constants.COLOR_GHOST);
		}
	}
	
	@Override
	public void startHover() {
		
		this.isHover = true;
	}

	@Override
	public void endHover() {
		
		this.isHover = false;
	}

	@Override
	public void activate() {
		
		this.isSelectable = false;
		
		HealingBed bed = new HealingBed(this.componentManager.takeId(), this.componentManager, this.position, this.isHorizontal);
		
		this.componentManager.addComponent(bed);
	}

	@Override
	public boolean isSelectable() {
		
		return this.isSelectable;
	}
}
