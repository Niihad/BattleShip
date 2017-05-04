package main;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import model.Model;
import view.ConfigScreen;
import view.EndScreen;
import view.GameScreen;
import view.LoadScreen;
import view.MenuScreen;
import view.MenuView;
import view.PlacementScreen;
import view.StatsView;

public class BattleShip extends JFrame {

	private static final long serialVersionUID = 1L;
	private MenuScreen menu;
	private GameScreen game;
	private PlacementScreen placement;
	private StatsView stats;
	private ConfigScreen config;
	private EndScreen end;
	private JFrame frame;
	private Model model;
	private LoadScreen load;

	public BattleShip() {
		this.model = new Model();
		frame = new JFrame("Bataille navale");
		this.menu = new MenuScreen(this);
		frame.add(menu, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(350, 300);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	//passer a l ecran de menu
	public void setMenuScreen(){
		this.menu = new MenuScreen(this);
		frame.add(menu, BorderLayout.CENTER);
		frame.setSize(350, 300);
	}
	
	//passer a l ecran de placement des bateaux
	public void setPlacementScreen() {
		this.placement = new PlacementScreen(this);
		frame.add(placement, BorderLayout.CENTER);
		frame.setSize(1250, 800);
	}
	
	//passer a l ecran de jeux
	public void setGameScreen() {
		frame.setJMenuBar(new MenuView(this));
		this.stats = new StatsView(model);
		this.game = new GameScreen(this);
		frame.add(stats, BorderLayout.NORTH);
		frame.add(game, BorderLayout.CENTER);
		frame.setSize(1300, 800);
	}
	
	//passer a l ecran de configuration
	public void setConfigScreen() {
		this.config = new ConfigScreen(this);
		frame.add(config, BorderLayout.CENTER);
	}
	
	//passer a l ecran de fin
	public void setEndScreen() {
		this.end = new EndScreen(this);
		this.stats.setVisible(false);
		frame.add(end, BorderLayout.CENTER);
		frame.setSize(350, 300);
	}
	
	//passer a l ecran de chargement
	public void setLoadScreen(){
		this.load = new LoadScreen(this);
		frame.add(load, BorderLayout.CENTER);
		frame.setSize(350, 300);
	}
	
	//pour rejouer
	public void replay() {
		this.model = new Model();
		setMenuScreen();
	}
	
	/***********************************************************/
	/********************* GETTER / SETTER *********************/
	/***********************************************************/
	
	public Model getModel() {
		return model;
	}
	
	public void setModel(Model model) {
		this.model = model;
	}
	
	public GameScreen getGame() {
		return game;
	}
	
	public static void main(String[] args) {
		new BattleShip();
	}
}