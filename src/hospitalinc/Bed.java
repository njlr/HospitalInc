package hospitalinc;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public strictfp interface Bed {
	
	Rectangle getArea();
	
	Patient getPatient();
	
	Vector2f getPatientPosition();
	
	void setPatient(Patient patient);
	
	boolean isHealing();
	
	boolean isAvailable();
}
