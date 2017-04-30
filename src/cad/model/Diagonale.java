package cad.model;

import java.util.Random;

public class Diagonale implements Strategy {

	@Override
	public String name() {
		return "Diagonale";
	}

	@Override
	/**
	 * Mode de jeu Diagonale :
	 * On récupère les cellules
	 * Si un bateau adverse encore en vie est touché 
	 * 		alors si une seule case touchée 
	 * 				alors aléatoire autour
	 * 				sinon viser dans l'alignement 	=> 	c'est une amélioration
	 * 		 sinon viser en diagonale selon les cases déjà touchées
	 */
	public void play(Model mod) {

		Cell[][] board = mod.getBoardPlayer();

		Random r = new Random();
		int x = 0, y = 0;
		int w = mod.getWidth();
		int h = mod.getHeight();

		//Si une cellule d'un bateau est touchée alors on regarde autour d'une si on peut tirer sur une cellule qui n'est pas encore touchée
		for(int i = 1; i < w; i++)
			for(int j = 1; j < h; j++) {
				if(board[i][j].isShoot() && board[i][j].getShip() != null) {
					if(j != 1 && !board[i][j - 1].isShoot()) {
						// On tire en haut
						x = i;
						y = j - 1;
					}
					else if(i != 1 && !board[i - 1][j].isShoot()) {
						// On tire à gauche
						x = i - 1;
						y = j;
					}
					else if(j != h - 1 && !board[i][j + 1].isShoot()) {
						// On tire à en bas
						x = i;
						y = j + 1;
					}
					else if(i != w - 1 && !board[i + 1][j].isShoot()) {
						// On tire à droite
						x = i + 1;
						y = j;
					}
					if(x != 0) {
						mod.setShoot(x,y);
						return;
					}
				}
			}
		System.out.println("L'IA tire en : " + x + " - " + y);
		
		// On tire en diagonale de manière aléatoire
		do{
			if(r.nextInt(2) == 0) {
				x = 1 + 2 * r.nextInt((w - 1) / 2);
				y = 1 + 2 * r.nextInt((h - 1) / 2);
			}
			else {
				x = 2 + 2 * r.nextInt((w - 2) / 2);
				y = 2 + 2 * r.nextInt((h - 2) / 2);
			}
		} while(mod.neverShoot(x,y));
		
		mod.setShoot(x,y);
	}

}