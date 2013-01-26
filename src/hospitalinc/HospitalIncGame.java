package hospitalinc;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public strictfp final class HospitalIncGame extends StateBasedGame {

	public HospitalIncGame() {
		
		super("Hospital Inc.");
	}

	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException {
		
		this.addState(new GameStatePlaying());
	}
}
