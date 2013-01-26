package hospitalinc.events;

import hospitalinc.Generator;

public strictfp final class GeneratorDestroyedEvent extends HospitalIncEvent {
	
	private static final long serialVersionUID = -8057105825917264421L;
	
	private final Generator generator;
	
	public Generator getGenerator() {
		
		return this.generator;
	}
	
	public GeneratorDestroyedEvent(Generator generator) {
		
		super();
		
		this.generator = generator;
	}
}
