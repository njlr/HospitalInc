package hospitalinc;

import nlib.utils.Utils;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;

public strictfp final class Launcher {
	
	public static void main(String[] args) throws SlickException {
		
		Utils.linkLwjgl();
		
		Game game = new HospitalIncGame();
		
		ScalableGame scalableGame = new ScalableGame(game, 640, 480, true);
		
		AppGameContainer appGameContainer = new AppGameContainer(scalableGame, 640, 480, false);
		
		appGameContainer.setVSync(true);
		appGameContainer.setTargetFrameRate(60);
		
		appGameContainer.start();
	}
}
