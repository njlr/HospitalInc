package hospitalinc;

import org.newdawn.slick.Color;

public strictfp final class Constants {
	
	private Constants() {
		
		super();
	}
	
	public static final int PATIENT_HEALTH = 10 * 1000;
	
	public static final int PATIENT_HEALING_TIME_GREEN = 45 * 1000;
	public static final int PATIENT_HEALING_TIME_YELLOW = 20 * 1000;
	public static final int PATIENT_HEALING_TIME_RED = 10 * 1000;
	
	public static final int PATIENT_VALUE_GREEN = 100;
	public static final int PATIENT_VALUE_YELLOW = 50;
	public static final int PATIENT_VALUE_RED = 20;
	
	public static final int TIME_BETWEEN_PATIENTS = 5 * 1000;
	
	public static final float GENERATOR_POWER_RADIUS = 48f;
	public static final int GENERATOR_LIFE_TIME = 17 * 1000;
	public static final int GENERATOR_LIFE_TIME_VARIANCE = 5 * 1000;
	public static final int GENERATOR_COST = 30;
	
	public static final int DEPTH_MAP = 0;
	public static final int DEPTH_GENERATOR_SITE = -1;
	public static final int DEPTH_GENERATOR = -2;
	public static final int DEPTH_BED = -3;
	public static final int DEPTH_PATIENT = -4;
	public static final int DEPTH_UI = -5;
	
	public static final int MAX_FATALITY_COUNT = 2;
	
	public static final float SCROLL_SPEED = 0.01f;

	public static final Color COLOR_GHOST = new Color(255, 255, 255, 160);
}
