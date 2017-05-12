package model;

import java.io.Serializable;

public class Stupide implements Strategy, Serializable {

	@Override
	public String name() {
		return "Stupide";
	}

	@Override
	public void play(Model mod) {
		int xShoot = 0, yShoot = 0;
		boolean find = false;
		for (int y = 1; y <= Model.getHeight(); y++) {
			for (int x = 1; x <= Model.getWidth(); x++) {
				if (!mod.neverShoot(x, y) && !find) {
					xShoot = x;
					yShoot = y;
					find = true;
				}
			}
		}
		mod.setShoot(xShoot, xShoot);
	}

}
