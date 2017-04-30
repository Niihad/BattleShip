package cad.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import cad.model.Model;

public class StatsScreen extends JPanel implements Observer{
	private Model mod;
	private JLabel life,life_IA;
	private int tmp;
	
	public StatsScreen(Model model){
		this.mod = model;
		this.tmp = this.mod.getLife();
		this.life = new JLabel("Life : " + tmp +" / " + tmp);
		this.life.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.life_IA = new JLabel("Life IA : " + tmp +" / " + tmp);
		this.add(life);
		this.add(life_IA);
		this.mod.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		this.life.setText("Life : " + this.mod.getLife() +" / " + tmp);
		this.life_IA.setText("Life : " + this.mod.getLife_ia() +" / " + tmp);
	}

}
