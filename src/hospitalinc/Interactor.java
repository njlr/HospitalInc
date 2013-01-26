package hospitalinc;

import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import nlib.components.BasicComponentRenderable;
import nlib.components.ComponentManager;

public strictfp final class Interactor extends BasicComponentRenderable implements MouseListener {
	
	private final ComponentManager componentManager;
	
	private Patient patientDragging;
	
	public Interactor(long id, ComponentManager componentManager) {
		
		super(id);
		
		this.componentManager = componentManager;
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		gameContainer.getInput().addMouseListener(this);
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
	}

	@Override
	public void inputEnded() {
		
	}

	@Override
	public void inputStarted() {
		
	}

	@Override
	public boolean isAcceptingInput() {
		
		return true;
	}

	@Override
	public void setInput(Input input) {
		
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		
		if (this.patientDragging == null) {
			
			if(button == Input.MOUSE_LEFT_BUTTON) {
				
				List<Selectable> selectables = this.componentManager.getComponents(Selectable.class);
				
				for (Selectable i : selectables) {
					
					if (i.isHover()) {
						
						i.activate();
						
						break;
					}
				}
			}
		}
	}

	@Override
	public void mouseDragged(int oldX, int oldY, int newX, int newY) {
		
		if (this.patientDragging != null) {
			
			this.patientDragging.setPosition(newX, newY);
		}
	}

	@Override
	public void mouseMoved(int oldX, int oldY, int newX, int newY) {
		
		if (this.patientDragging == null) {
			
			Vector2f t = new Vector2f(newX, newY);
			
			List<Selectable> selectables = this.componentManager.getComponents(Selectable.class);
			
			Selectable best = null;
			
			for (Selectable i : selectables) {
				
				if (i.isSelectable() && t.distanceSquared(i.getPositionCenter()) <= i.getRadius() * i.getRadius()) {
					
					if (best == null) {
						
						best = i;
					}
					else {
						
						if (t.distanceSquared(i.getPositionCenter()) < t.distanceSquared(best.getPositionCenter())) {
							
							best = i;
						}
					}
				}
			}
			
			for (Selectable i : selectables) {
				
				if (i.isHover() && i != best) {
					
					i.endHover();
				}
			}
			
			if (best != null) {
				
				best.startHover();
			}
		}
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		
		if (button == Input.MOUSE_LEFT_BUTTON) {
			
			Vector2f m = new Vector2f(x, y);
			
			List<Patient> patients = this.componentManager.getComponents(Patient.class);
			
			this.patientDragging = null;
			
			for (Patient i : patients) {
				
				if (m.distanceSquared(i.getPosition()) <= 16f * 16f) {
					
					this.patientDragging = i;
					
					break;
				}
			}
		}
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		
		if (button == Input.MOUSE_LEFT_BUTTON) {
			
			if (this.patientDragging != null) {
				
				List<Bed> beds = this.componentManager.getComponents(Bed.class);
				
				for (Bed i : beds) {
					
					if (i.getArea().contains(x, y)) {
						
						if (i.getPatient() == null) {
							
							if (this.patientDragging.getBed() != null) {
								
								this.patientDragging.getBed().setPatient(null);
							}
							
							i.setPatient(this.patientDragging);
							
							this.patientDragging.setBed(i);
							
							break;
						}
						else {
							
							if (this.patientDragging.getBed() != null) {
								
								this.patientDragging.getBed().setPatient(i.getPatient());
								
								i.getPatient().setBed(this.patientDragging.getBed());
								
								i.setPatient(this.patientDragging);
								
								this.patientDragging.setBed(i);
								
								break;
							}
						}
					}
				}
				
				if (this.patientDragging.getBed() != null) {
					
					this.patientDragging.setBed(this.patientDragging.getBed());
				}
				
				this.patientDragging = null;
			}
		}
	}

	@Override
	public void mouseWheelMoved(int arg0) {
		// TODO Auto-generated method stub
		
	}
}
