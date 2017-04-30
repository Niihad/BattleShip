package cad.model;

import java.util.Random;

public class Aleatoire implements Strategy{

	@Override
	public String name() {
		return "Aleatoire";
	}

	@Override
	public void play(Model mod) {
		Random r = new Random();
		int w = mod.getWidth();
		int h = mod.getHeight();
		int x, y = 0;

		do{
			x = 1 + r.nextInt(w - 1);
			y = 1 + r.nextInt(h - 1);
		}while(mod.neverShoot(x,y));
		
		mod.setShoot(x,y);
	}

}
