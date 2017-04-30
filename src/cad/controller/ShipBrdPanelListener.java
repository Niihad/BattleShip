package cad.controller;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import cad.model.Cell;
import cad.model.Model;
import cad.view.CellView;

public class ShipBrdPanelListener extends MouseAdapter {
	
	private Model model;
	private JPanel panel;
    private JPanel[][] board;
    private CellView[] originalJPanel;
    private JLabel[] label;
    private int length, part;
    private boolean rotation;
    
    public ShipBrdPanelListener(Model model, JPanel panel, JPanel[][] board){
    	this.model = model;
    	this.panel = panel;
    	this.board = board;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() != MouseEvent.BUTTON1) {
            return;
        }
        JPanel src = (JPanel) e.getSource();
        CellView comp = (CellView) src.getComponentAt(e.getPoint());
        if (comp != null && ((JComponent) comp).getComponentCount() == 1) {
        	this.length = comp.getCell().getShip().getLengthShip();
        	this.part = comp.getCell().getPart();
        	this.originalJPanel = new CellView[length]; 
        	this.label = new JLabel[length];
    		this.rotation = comp.getCell().getShip().isRotation();
    		if(!comp.getCell().getShip().isRotation()){ // horizontal
    			for(int i=0; i<=part; i++){
    				this.originalJPanel[i] = (CellView) board[comp.getOrd()][comp.getAbs()-i];
    				this.repaintClick(this.originalJPanel[i],i,i,e,true);
    			}
    			for(int i=1; i<length-part; i++){
    				this.originalJPanel[i+part] = (CellView) board[comp.getOrd()][comp.getAbs()+i];
    				this.repaintClick(this.originalJPanel[i+part],i+part,i,e,false);
    			}
    		}
        }else{
            return;
        }
        e.consume();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    	if (label == null) {
            return;
        }
		if(!this.rotation){ // horizontal
			for(int i=0; i<=part; i++)
				this.repaintComp(i,i,e,true);
			for(int i=1; i<length-part; i++)
				this.repaintComp(i+part,i,e,false);
		}
        JPanel src = (JPanel) e.getSource();
        CellView comp = (CellView) src.getComponentAt(e.getPoint());
        if (comp != null) {
	        if(!this.rotation){ // horizontal
	        	if (isNewJPanelValid(comp)){ // test position
					for(int i=0; i<=part; i++){
						CellView panelDestination = (CellView) board[comp.getOrd()][comp.getAbs()-i];
						panelDestination.setCell((Cell) this.originalJPanel[i].getCell().clone());
						this.originalJPanel[i].getCell().setShip(null);
						panelDestination.add(label[i]);
						if(comp.getAbs() < 11)
							this.model.getBoardPlayer()[comp.getOrd()][comp.getAbs()-i] = panelDestination.getCell();
						else
							this.model.getBoardAI()[comp.getOrd()][comp.getAbs()-i] = panelDestination.getCell();
					}
					for(int i=1; i<length-part; i++){
						CellView panelDestination = (CellView) board[comp.getOrd()][comp.getAbs()+i];
						panelDestination.setCell((Cell) this.originalJPanel[i+part].getCell().clone());
						panelDestination.add(label[i+part]);
						this.originalJPanel[i+part].getCell().setShip(null);
						if(comp.getAbs() < 11)
							this.model.getBoardPlayer()[comp.getOrd()][comp.getAbs()+i] = panelDestination.getCell();
						else
							this.model.getBoardAI()[comp.getOrd()][comp.getAbs()+i] = panelDestination.getCell();
					}
				}else{
					for(int i=0; i<=originalJPanel.length; i++)
						originalJPanel[i].add(label[i]);
				}
        	}
        }
        this.panel.revalidate();
        this.panel.repaint();
        label = null;
        this.model.print(model.getBoardPlayer());
        this.model.print(model.getBoardAI());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    	if (label == null) {
            return;
        }
		if(!this.rotation){ // horizontal
			for(int i=0; i<=part; i++)
				repaintComp(i,i,e,true);
			for(int i=1; i<length-part; i++)
				repaintComp(i+part,i,e,false);
		}
    }
    
    private void repaintClick(CellView cell, int i, int j, MouseEvent e, boolean first){
    	label[i] = (JLabel) cell.getComponent(0);
    	cell.remove(label[i]);
    	cell.revalidate();
    	cell.repaint();
    	JPanel glassPane = (JPanel) SwingUtilities.getRootPane(cell).getGlassPane();
        this.buildShip(glassPane, i, j, e, first);
        label[i].setSize(label[i].getPreferredSize());
        glassPane.add(label[i]);
        glassPane.repaint();
    }
    
    private void repaintComp(int i, int j, MouseEvent e, boolean first){
    	 if (label[i] == null) {
             return;
         }
         JPanel glassPane = (JPanel) SwingUtilities.getRootPane(label[i]).getGlassPane();
         this.buildShip(glassPane, i, j, e, first);
         panel.repaint();
    }
    
    private void buildShip(JPanel glassPane, int i, int j, MouseEvent e, boolean first){
        glassPane.setVisible(true);
        Point gpP = glassPane.getLocationOnScreen();
        glassPane.setLayout(null);
        int tmp;
        if(first)
        	tmp = e.getXOnScreen()-(((int)label[i].getSize().getWidth())*i);
        else
        	tmp = e.getXOnScreen()+(((int)label[i].getSize().getWidth())*j);
        int x = tmp - gpP.x - label[i].getWidth() / 2;
        int y = e.getYOnScreen() - gpP.y - label[i].getHeight() / 2;
        label[i].setLocation(x, y);
    }
    
    private boolean isNewJPanelValid(CellView cell) {
        int x = cell.getAbs();
        int y = cell.getOrd();
        return !(x==0 || x==13 || y==0 || (y>10 && y<13));
    }
}
