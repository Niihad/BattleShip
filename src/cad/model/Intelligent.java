package cad.model;

import java.io.Serializable;
import java.util.Random;

public class Intelligent implements Strategy, Serializable {

	@Override
	public String name() {
		return "Intelligent";
	}

	@Override
	/**
	 * Mode de jeu Diagonale :
	 * On r�cup�re les cellules
	 * Si un bateau adverse encore en vie est touch� 
	 * 		alors si une seule case touch�e 
	 * 				alors al�atoire autour
	 * 				sinon viser dans l'alignement
	 * 		 sinon viser en diagonale selon les cases d�j� touch�es
	 */
	public void play(Model mod) {

		Cell[][] board = mod.getBoardPlayer();

		Random r = new Random();
		int x = 0, y = 0;
		int w = mod.getWidth();
		int h = mod.getHeight();

		System.out.println("Test : ");
		//Si une cellule d'un bateau est touch�e alors on regarde autour d'une si on peut tirer sur une cellule qui n'est pas encore touch�e
		for(int i = 1; i <= w; i++)
			for(int j = 1; j <= h; j++) {
				if(board[i][j].isShoot() && board[i][j].getShip() != null) {
					int rand = r.nextInt(4);
					for(int k = 0; k < 4; k++) {
						if(j != 1 && (rand + k) % 4 == 1 && !board[i][j - 1].isShoot()) {
							// On tire en haut
							x = i;
							y = j - 1;
						}
						else if(i != 1 && (rand + k) % 4 == 2 && !board[i - 1][j].isShoot()) {
							// On tire � gauche
							x = i - 1;
							y = j;
						}
						else if(j != h && (rand + k) % 4 == 3 && !board[i][j + 1].isShoot()) {
							// On tire � en bas
							x = i;
							y = j + 1;
						}
						else if(i != w && (rand + k) % 4 == 0 && !board[i + 1][j].isShoot()) {
							// On tire � droite
							x = i + 1;
							y = j;
						}
						if(x != 0) {
							mod.setShoot(x,y);
							return;
						}
					}
				}
			}
		System.out.println("L'IA tire en : " + x + " - " + y);
		
		// On tire en diagonale de mani�re al�atoire
		do{
			if(r.nextInt(2) == 0) {
				x = 1 + 2 * r.nextInt((w) / 2);
				y = 1 + 2 * r.nextInt((h) / 2);
			}
			else {
				x = 2 + 2 * r.nextInt((w) / 2);
				y = 2 + 2 * r.nextInt((h) / 2);
			}
		} while(mod.neverShoot(x,y));
		
		mod.setShoot(x,y);
	}

}