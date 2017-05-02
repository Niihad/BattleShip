package cad.model;

import java.io.Serializable;

public class Context implements Serializable {
	protected Strategy strategy;
	
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
