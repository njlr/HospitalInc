package hospitalinc;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.google.common.collect.ImmutableList;

public strictfp final class GameStateGameOver extends BasicGameState {
	
	public static final int GAME_STATE_OVER = 1;
	
	private final List<PatientInfo> fatalities;
	
	private float scrollY;
	
	public GameStateGameOver(List<PatientInfo> fatalities) {
		
		super();
		
		this.fatalities = ImmutableList.copyOf(fatalities);
	}
	
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		
	}
	
	@Override
	public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		
		super.enter(gameContainer, stateBasedGame);
		
		this.scrollY = gameContainer.getHeight();
	}
	
	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
		
		this.scrollY -= Constants.SCROLL_SPEED * delta;
	}
	
	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		
		graphics.setColor(Color.white);
		
		float x = 32f;
		float y = this.scrollY;
		
		graphics.drawString("Fatalities", x, y);
		
		y += 48f;
		
		for (PatientInfo i : this.fatalities) {
			
			graphics.drawString(i.getName() + ". " + i.getBio(), x, y);
			
			y += 32f;
		}
	}

	@Override
	public int getID() {
		
		return GAME_STATE_OVER;
	}
}
