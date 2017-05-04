package view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Model;

public class StatsScreen extends JPanel implements Observer{
	private Model mod;
	private JLabel life,life_IA,strategy;
	private int tmp;
	
	public StatsScreen(Model model){
		this.mod = model;
		draw();
		this.mod.addObserver(this);
	}

	private void draw() {
		this.tmp = this.mod.getLife();
		this.life = new JLabel("Life : " + tmp +" / " + tmp);
		this.life.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.life_IA = new JLabel("Life IA : " + tmp +" / " + tmp);
		this.life_IA.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.strategy = new JLabel("Strategie IA : " + mod.getContext().getNameStrategy());
		
		this.add(life);
		this.add(life_IA);	
		this.add(strategy);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		this.life.setText("Life : " + this.mod.getLife() +" / " + tmp);
		this.life_IA.setText("Life IA: " + this.mod.getLife_ia() +" / " + tmp);
		this.strategy.setText("Strategie IA : " + mod.getContext().getNameStrategy());
	}

}
