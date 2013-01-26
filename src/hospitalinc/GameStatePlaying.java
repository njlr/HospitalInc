package hospitalinc;

import hospitalinc.events.GameOverEvent;
import hospitalinc.events.HospitalIncEvent;
import nlib.components.ComponentManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.google.common.eventbus.Subscribe;

public strictfp final class GameStatePlaying extends BasicGameState {
	
	public static final int GAME_STATE_PLAYING = 0;
	
	private ComponentManager componentManager;
	
	private boolean isGameOver;
	
	private FatalityTracker fatalityTracker;
	
	public GameStatePlaying() {
		
		super();
		
		this.componentManager = new ComponentManager();
		
		this.isGameOver = false;
		
		this.componentManager.getEventBus().register(this);
		
		// Patient dragger
		this.componentManager.addComponent(new Interactor(this.componentManager.takeId(), this.componentManager));
		
		// Money bag
		MoneyBag moneyBag = new MoneyBag(this.componentManager.takeId(), this.componentManager.getEventBus());
		
		this.componentManager.addComponent(moneyBag);
		
		// Fatality tracker
		this.fatalityTracker = new FatalityTracker(this.componentManager.takeId(), this.componentManager.getEventBus());
		
		this.componentManager.addComponent(this.fatalityTracker);
		
		// Map
		this.componentManager.addComponent(new Map(this.componentManager.takeId(), this.componentManager, moneyBag, "assets/Map.tmx"));
		
		// Ambulance
		this.componentManager.addComponent(new Ambulance(this.componentManager.takeId(), this.componentManager));
	}
	
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		
		this.componentManager.init(gameContainer);
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
		
		this.componentManager.update(gameContainer, delta);
		
		if (this.isGameOver) {
			
			stateBasedGame.addState(new GameStateGameOver(this.fatalityTracker.getFatalities()));
			
			stateBasedGame.enterState(GameStateGameOver.GAME_STATE_OVER);
		}
	}
	
	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		
		this.componentManager.render(gameContainer, graphics);
	}

	@Override
	public int getID() {
		
		return GAME_STATE_PLAYING;
	}
	
	@Subscribe
	public void handleHospitalIncEvent(HospitalIncEvent e) {
		
		System.out.println(e.toString());
	}
	
	@Subscribe
	public void handleGameOverEvent(GameOverEvent e) {
		
		this.isGameOver = true;
	}
}
