package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import main.BattleShip;
import model.Model;
import model.Model.Etat;
import view.CellView;
import view.GameScreen;

public class GameController implements ActionListener {
	private int x,y;
	private CellView cellView;
	private Model model;
	private BattleShip bs;
	private GameScreen gs;
	
	public GameController(BattleShip bs, GameScreen gs, Model model, CellView view, int i, int j) {
		this.bs = bs;
		this.gs = gs;
		this.cellView = view; 
		this.model = model;
		this.x = i;
		this.y = j;
		model.IA_play();
	}

	//traitement des action du joueur
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(model.getEtat() == Etat.PLAYER && model.isEnd_game() == false){
			model.setShoot(x, y);
			if(model.getBoardPlayer()[x][y].getShip() != null){
				try {
                    AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("./assets/son/explosion.wav"));
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioIn);
                    clip.start();
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }
			
			}else{
				try {
                    AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("./assets/son/plouf.wav"));
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioIn);
                    clip.start();
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }
			}
			//tour du joueur
			this.model.IA_play();

	        //this.model.print(this.model.getBoardPlayer());
	        //this.model.print(this.model.getBoardAI());
		}
		
		//test fin de partie pour passer a l ecran de fin
		if(model.isEnd_game()){
			gs.setVisible(false);
			bs.setEndScreen();
		}
	}

}