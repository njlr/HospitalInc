package hospitalinc;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import nlib.components.BasicComponentRenderable;
import nlib.components.ComponentManager;

public strictfp final class BedSite extends BasicComponentRenderable implements Selectable {
	
	private final ComponentManager componentManager;
	
	private final Vector2f position;
	private final Rectangle area;
	
	private boolean isHover;
	
	private boolean isSelectable;
	
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
	
	public BedSite(long id, ComponentManager componentManager, Vector2f position) {
		
		super(id);
		
		this.componentManager = componentManager;
		
		this.position = new Vector2f(position);
		this.area = new Rectangle(this.position.getX(), this.position.getY(), 32f, 64f);
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.isHover = false;
		
		this.isSelectable = true;
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		if (this.isHover) {
			
			graphics.setColor(Color.magenta);
		}
		else {
			
			graphics.setColor(Color.gray);
		}
		
		graphics.fill(this.area);
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
		
		HealingBed bed = new HealingBed(this.componentManager.takeId(), this.componentManager, this.position);
		
		this.componentManager.addComponent(bed);
	}

	@Override
	public boolean isSelectable() {
		
		return this.isSelectable;
	}
}
