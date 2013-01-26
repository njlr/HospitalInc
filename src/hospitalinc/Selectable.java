package hospitalinc;

import org.newdawn.slick.geom.Vector2f;

public interface Selectable {
	
	boolean isSelectable();
	
	float getRadius();
	
	Vector2f getPositionCenter();
	
	boolean isHover();
	
	void startHover();
	
	void endHover();
	
	void activate();
}
