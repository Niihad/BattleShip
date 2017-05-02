package cad.model;

import java.io.Serializable;
import java.util.Random;

public class Aleatoire implements Strategy, Serializable {

	@Override
	public String name() {
		return "Aleatoire";
	}

	//strategie qui tire au hasard
	@Override
	public void play(Model mod) {
		Random r = new Random();
		int w = mod.getWidth();
		int h = mod.getHeight();
		int x = 0, y = 0;

		do{//on verifie qu il n a jamais jouer dans cette case
			x = 1 + r.nextInt(w);
			y = 1 + r.nextInt(h);
		}while(mod.neverShoot(x,y));
		
		mod.setShoot(x,y);
	}

}
