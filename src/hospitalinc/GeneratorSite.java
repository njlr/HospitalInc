package hospitalinc;

import hospitalinc.events.GeneratorDestroyedEvent;
import nlib.components.BasicComponentRenderable;
import nlib.components.ComponentManager;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.google.common.eventbus.Subscribe;

public strictfp final class GeneratorSite extends BasicComponentRenderable implements Selectable {
	
	private final ComponentManager componentManager;
	private final MoneyBag moneyBag;
	
	private final Vector2f position;
	private final Rectangle area;
	
	private Generator generator;
	
	private boolean isHover;
	
	public Vector2f getPosition() {
		
		return this.position.copy();
	}
	
	@Override
	public Vector2f getPositionCenter() {
		
		return new Vector2f(this.area.getCenterX(), this.area.getCenterY());
	}
	
	public Rectangle getArea() {
		
		return this.area;
	}
	
	public boolean hasGenerator() {
		
		return this.generator != null;
	}
	
	@Override
	public boolean isHover() {
		
		return this.isHover;
	}
	
	public GeneratorSite(long id, ComponentManager componentManager, MoneyBag moneyBag, Vector2f position) {
		
		super(id);
		
		this.componentManager = componentManager;
		this.moneyBag = moneyBag;
		
		this.position = new Vector2f(position);
		this.area = new Rectangle(this.position.getX(), this.position.getY(), 8f, 8f);
		
		this.componentManager.getEventBus().register(this);
		
		this.generator = null;
		
		this.isHover = false;
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.generator = null;
		
		this.isHover = false;
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
	public void destroy(GameContainer gameContainer) throws SlickException {
		
		super.destroy(gameContainer);
		
		this.componentManager.getEventBus().unregister(this);
	}
	
	@Override
	public float getDepth() {
		
		return Constants.DEPTH_GENERATOR_SITE;
	}
	
	@Subscribe
	public void handleGeneratorDestroyedEvent(GeneratorDestroyedEvent e) {
		
		if (this.generator != null) {
			
			if (this.generator == e.getGenerator()) {
				
				this.generator = null;
			}
		}
	}
	
	private void createGenerator() {
		
		Generator generator = new Generator(this.componentManager.takeId(), this.componentManager.getEventBus(), this.position);
		
		this.generator = generator;
		
		this.componentManager.addComponent(generator);
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
		
		if (this.generator == null) {
			
			if (this.moneyBag.getMoney() >= Constants.GENERATOR_COST) {
				
				this.moneyBag.spend(Constants.GENERATOR_COST);
				
				this.createGenerator();
			}
		}
	}

	@Override
	public boolean isSelectable() {
		
		return !this.hasGenerator();
	}

	@Override
	public float getRadius() {
		
		return this.area.getBoundingCircleRadius() + 4f;
	}
}
