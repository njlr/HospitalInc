package hospitalinc;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

import com.google.common.base.CharMatcher;

import nlib.components.BasicComponentRenderable;
import nlib.components.ComponentManager;

public strictfp final class Map extends BasicComponentRenderable {
	
	private final ComponentManager componentManager;
	private final MoneyBag moneyBag;
	
	private final String reference;
	
	private TiledMap tiledMap;
	
	public Map(long id, ComponentManager componentManager, MoneyBag moneyBag, String reference) {
		
		super(id);
		
		this.componentManager = componentManager;
		this.moneyBag = moneyBag;
		
		this.reference = reference;
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.tiledMap = new TiledMap(this.reference);
		
		for (int i = 0; i < this.tiledMap.getObjectGroupCount(); i++) {
			
			for (int j = 0; j < this.tiledMap.getObjectCount(i); j++) {
				
				String type = CharMatcher.WHITESPACE.trimFrom(this.tiledMap.getObjectType(i, j));
				
				int x = this.tiledMap.getObjectX(i, j);
				int y = this.tiledMap.getObjectY(i, j);
				
				if (type.equalsIgnoreCase("GeneratorSite")) {
					
					GeneratorSite generatorSite = new GeneratorSite(this.componentManager.takeId(), this.componentManager, this.moneyBag, new Vector2f(x, y));
					
					this.componentManager.addComponent(generatorSite);
				}
				else if (type.equalsIgnoreCase("Bed")) {
					
					HealingBed bed = new HealingBed(
							this.componentManager.takeId(), 
							this.componentManager, 
							new Vector2f(x, y), 
							Boolean.parseBoolean(this.tiledMap.getObjectProperty(i, j, "Horizontal", "False")));
					
					this.componentManager.addComponent(bed);
				}
				else if (type.equalsIgnoreCase("WaitingBed")) {
					
					WaitingBed bed = new WaitingBed(
							this.componentManager.takeId(), 
							this.componentManager, 
							new Vector2f(x, y), 
							Boolean.parseBoolean(this.tiledMap.getObjectProperty(i, j, "Horizontal", "False")));
					
					this.componentManager.addComponent(bed);
				}
				else if (type.equalsIgnoreCase("BedSite")) {
					
					BedSite bedSite = new BedSite(
							this.componentManager.takeId(), 
							this.componentManager, 
							new Vector2f(x, y), 
							Boolean.parseBoolean(this.tiledMap.getObjectProperty(i, j, "Horizontal", "False")));
					
					this.componentManager.addComponent(bedSite);
				}
			}
		}
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		this.tiledMap.render(0, 0);
	}
	
	@Override
	public float getDepth() {
		
		return Constants.DEPTH_MAP;
	}
}
