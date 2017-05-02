package cad.model;

import java.io.Serializable;

public class Context implements Serializable {
	protected Strategy strategy;
	
	public Context(Strategy strategy){
		this.strategy = strategy;
	}
	
	//execution de la strategie
	public void executeStrategy(Model mod){
		strategy.play(mod);
	}
	
	//recuperer le nom de la strategie
	public String getNameStrategy(){
		return strategy.name();	
	}

}
