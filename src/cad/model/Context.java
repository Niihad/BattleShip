package cad.model;

public class Context {
	private Strategy strategy;
	
	public Context(Strategy strategy){
		this.strategy = strategy;
	}
	
	public void executeStrategy(Model mod){
		strategy.play(mod);
	}
	
	public String getNameStrategy(){
		return strategy.name();	
	}

}
