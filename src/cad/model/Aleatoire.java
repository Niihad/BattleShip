package cad.model;

import java.util.Random;

public class Aleatoire implements Strategy {

	public String name() {
		return "Aleatoire";
	}

	public void play(Model mod) {
		Random r = new Random();
		int w = mod.getWidth();
		int h = mod.getHeight();
		int x = 0, y = 0;

		do{
			x = 1 + r.nextInt(w);
			y = 1 + r.nextInt(h);
		}while(mod.neverShoot(x,y));
		
		mod.setShoot(x,y);
	}

}
