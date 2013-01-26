package hospitalinc;

import java.util.ArrayList;
import java.util.List;

import hospitalinc.events.GameOverEvent;
import hospitalinc.events.PatientDiedEvent;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import nlib.components.BasicComponent;

public strictfp final class FatalityTracker extends BasicComponent {
	
	private final EventBus eventBus;
	
	private List<PatientInfo> fatalities;
	
	public List<PatientInfo> getFatalities() {
		
		return ImmutableList.copyOf(this.fatalities);
	}
	
	public FatalityTracker(long id, EventBus eventBus) {
		
		super(id);
		
		this.eventBus = eventBus;
		
		this.fatalities = new ArrayList<PatientInfo>(Constants.MAX_FATALITY_COUNT);
		
		this.eventBus.register(this);
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.fatalities.clear();
	}
	
	@Override
	public void destroy(GameContainer gameContainer) throws SlickException {
		
		super.destroy(gameContainer);
		
		this.eventBus.unregister(this);
	}
	
	@Subscribe
	public void handlePatientDiedEvent(PatientDiedEvent e) {
		
		this.fatalities.add(e.getPatient().getPatientInfo());
		
		if (this.fatalities.size() == Constants.MAX_FATALITY_COUNT) {
			
			this.eventBus.post(new GameOverEvent(this.fatalities));
		}
	}
}
