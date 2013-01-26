package hospitalinc.events;

import hospitalinc.Generator;

public strictfp final class GeneratorCreatedEvent extends HospitalIncEvent {
	
	private static final long serialVersionUID = -4680053755760424298L;
	
	private final Generator generator;
	
	public Generator getGenerator() {
		
		return this.generator;
	}
	
	public GeneratorCreatedEvent(Generator generator) {
		
		super();
		
		this.generator = generator;
	}
}
