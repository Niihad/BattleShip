package cad.controller;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import cad.view.CellView;

public class ShipBrdPanelListener extends MouseAdapter {
	
	private JPanel panel;
    private JPanel[][] board;
    private JLabel[] label;
    private int length, part;
    private boolean rotation;
    
    public ShipBrdPanelListener(JPanel panel, JPanel[][] board){
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
        	this.label = new JLabel[length];
        	if(length > 1){
        		this.rotation = comp.getCell().getShip().isRotation();
        		if(!comp.getCell().getShip().isRotation()){ // horizontal
        			for(int i=0; i<=part; i++){
        				CellView cell = (CellView) board[comp.getOrd()][comp.getAbs()-i];
        				repaintClick(cell,i,i,e,true);
        			}
        			for(int i=1; i<length-part; i++){
        				CellView cell = (CellView) board[comp.getOrd()][comp.getAbs()+i];
        				repaintClick(cell,i+part,i,e,false);
        			}
        		}
        	}
        } else {
            return;
        }
        e.consume();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        /*if (label == null) {
            return;
        }
        int x = e.getXOnScreen() - gpP.x - label.getWidth() / 2;
        int y = e.getYOnScreen() - gpP.y - label.getHeight() / 2;
        label.setLocation(x, y);
        
        panel.repaint();
        JPanel src = (JPanel) e.getSource();
        CellView comp = (CellView) src.getComponentAt(e.getPoint());
        if (comp != null){
            if (isNewJPanelValid(comp))
            	comp.add(label);
            else 
                originalJPanel.add(label);
        }else{
            originalJPanel.add(label);
        }
        
        label = null;
        glassPane.setVisible(false);
        panel.revalidate();
        panel.repaint();*/
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    	if(length > 1){
    		if(!this.rotation){ // horizontal
    			for(int i=0; i<=part; i++)
    				repaintDragged(i,i,e,true);
    			for(int i=1; i<length-part; i++)
    				repaintDragged(i+part,i,e,false);
    		}
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
    
    private void repaintDragged(int i, int j, MouseEvent e, boolean first){
    	if (label[i] == null) {
            return;
        }
    	JPanel glassPane = (JPanel) SwingUtilities.getRootPane(label[i]).getGlassPane();
    	this.buildShip(glassPane, i, j, e, first);
        panel.repaint();
    }
    
    private boolean isNewJPanelValid(CellView cell) {
        int x = cell.getAbs();
        int y = cell.getOrd();
        return !(x==0 || x==13 || y==0 || (y>10 && y<13));
    }
}
