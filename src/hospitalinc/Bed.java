package hospitalinc;

import org.newdawn.slick.geom.Rectangle;

public strictfp interface Bed {
	
	Rectangle getArea();
	
	Patient getPatient();
	
	void setPatient(Patient patient);
	
	boolean isHealing();
}
