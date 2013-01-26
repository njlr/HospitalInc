package hospitalinc;

import nlib.utils.Utils;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public strictfp final class Launcher {
	
	public static void main(String[] args) throws SlickException {
		
		Utils.linkLwjgl();
		
		HospitalIncGame game = new HospitalIncGame();
		
		AppGameContainer appGameContainer = new AppGameContainer(game, 640, 480, false);
		
		appGameContainer.setVSync(true);
		appGameContainer.setTargetFrameRate(60);
		
		appGameContainer.start();
	}
}
