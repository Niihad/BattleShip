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
import cad.view.PlacementScreen;

public class ShipBrdPanelListener extends MouseAdapter {
	
	private Model model;
	private PlacementScreen placement;
    private JPanel[][] board;
    private CellView[] originalJPanel;
    private JLabel[] label;
    private int length, part;
    private boolean rotation;
    
    public ShipBrdPanelListener(Model model, PlacementScreen placement){
    	this.model = model;
    	this.placement = placement;
    	this.board = placement.getBoard();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    	/* recupere et stock le jPanel present a l'endroit du curseur 
    	 * remarque un bloc jPanel represente un seul bloc du bateau plusieur bloc
    	 * de jPanel sont donc utilisé pour constituer l'integralité d'un bateau.
    	 */
    	JPanel src = (JPanel) e.getSource();
    	// recuperaton du composant present dans le jPanel
        CellView comp = (CellView) src.getComponentAt(e.getPoint());
        if (comp != null && ((JComponent) comp).getComponentCount() == 1) {
        	Cell cell = model.getBoardConvert(comp.getAbs())[comp.getOrd()][comp.getAbsConvert()];
        	this.model.affectCloneShip(cell);
        	/* longueur du bateau. Cette variable sera utilisé pour dessiner bloc par bloc 
        	 * les composants que constitue un bateau 
        	 */
        	this.length = cell.getShip().getLengthShip();
        	// correspond au numero du bloc du bateau selectionné
        	this.part = cell.getPart();
        	// renseigne sur la rotation du bateau
    		this.rotation = cell.getShip().isRotation();
        	// rotation du bateau selectionné
        	if (e.getButton() != MouseEvent.BUTTON1){
        		int middle = part - length/2;
        		this.model.rotationShipPlacement(cell, comp.getAbs()-middle, part);
        		for(int i=0; i<length; i++){
        			if(!this.rotation){ // horizontal
        				CellView panelOrigin = (CellView) board[comp.getOrd()][comp.getAbs()+i-part];
        				JLabel label = (JLabel) panelOrigin.getComponent(0);
        				CellView panelDestination = (CellView) board[comp.getOrd()+i-length/2][comp.getAbs()-middle];
        				panelDestination.add(label);
        			}else{ // vertical
        				CellView panelOrigin = (CellView) board[comp.getOrd()+i-part][comp.getAbs()];
        				JLabel label = (JLabel) panelOrigin.getComponent(0);
        				CellView panelDestination = (CellView) board[comp.getOrd()-middle][comp.getAbs()+i-length/2];
        				panelDestination.add(label);
        			}
        		}
        		this.placement.revalidate();
                this.placement.repaint();
                this.model.print(this.model.getBoardPlayer());
                this.model.print(this.model.getBoardAI());
	        }else{
	        	// stock chaque cells que constitue le bateau selectionné
	        	this.originalJPanel = new CellView[length]; 
	        	// stock chaque label que constitue le bateau selectionné
	        	this.label = new JLabel[length];
	        	
	        	for(int i=0; i<length; i++){
	        		if(!this.rotation){ // horizontal
	        			this.originalJPanel[i] = (CellView) board[comp.getOrd()][comp.getAbs()+i-part];
	        			this.repaintClick(this.originalJPanel[i],i,e,true);
	        		}else{ // vertical
	        			this.originalJPanel[i] = (CellView) board[comp.getOrd()+i-part][comp.getAbs()];
						this.repaintClick(this.originalJPanel[i],i,e,true);
	        		}
	        	}

	        }
        }else{
        	return;
        }
        e.consume();
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
    	if (label == null) {
            return;
        }
    	JPanel glassPane;
		if(!this.rotation){ // horizontal
			for(int i=0; i<length; i++){
				glassPane = (JPanel) SwingUtilities.getRootPane(label[i]).getGlassPane();
		        this.buildShip(glassPane, i, e);
		        placement.repaint();
			}
		}
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    	if (label == null) {
            return;
        }
        JPanel src = (JPanel) e.getSource();
        CellView comp = (CellView) src.getComponentAt(e.getPoint());
        if (comp != null) {
        	boolean player = (comp.getAbs()<10) ? true : false;
        	Cell cell = model.getBoardConvert(comp.getAbs())[comp.getOrd()][comp.getAbsConvert()];
        	if (this.model.placementShipValid(cell)){ // test position
        		Cell cloneShip = model.getBoardConvert(originalJPanel[0].getAbs())[originalJPanel[0].getOrd()][originalJPanel[0].getAbsConvert()];
        		this.model.affectCloneShip(cloneShip);
        		for(int i=0; i<length; i++){
        			Cell OriginCell = model.getBoardConvert(originalJPanel[i].getAbs())[originalJPanel[i].getOrd()][originalJPanel[i].getAbsConvert()];
	        		if(!this.rotation){ // horizontal
		        		this.model.movePlacementShipBoard(OriginCell,cell,part,player);
		        		CellView panelDestination = (CellView) board[comp.getOrd()][comp.getAbs()+i-part];
		        		panelDestination.add(label[i]);
		        	}else{ // vertical
		        		this.model.movePlacementShipBoard(OriginCell,cell,i,player);
		        		CellView panelDestination = (CellView) board[comp.getOrd()+i-part][comp.getAbs()];
		        		panelDestination.add(label[i]);
		        	}
	        	}
        	}else{
				for(int i=0; i<=originalJPanel.length; i++)
					originalJPanel[i].add(label[i]);
			}
        }
        this.placement.revalidate();
        this.placement.repaint();
        label = null;
    }
    
    
    /***********************************************************/
	/************************* FONCTION ************************/
	/***********************************************************/
    
    private void repaintClick(CellView cell, int i, MouseEvent e, boolean first){
    	label[i] = (JLabel) cell.getComponent(0);
    	cell.remove(label[i]);
    	cell.revalidate();
    	cell.repaint();
    	JPanel glassPane = (JPanel) SwingUtilities.getRootPane(cell).getGlassPane();
        this.buildShip(glassPane, i, e);
        label[i].setSize(label[i].getPreferredSize());
        glassPane.add(label[i]);
        glassPane.repaint();
    }
    
    private void buildShip(JPanel glassPane, int i, MouseEvent e){
        glassPane.setVisible(true);
        Point gpP = glassPane.getLocationOnScreen();
        glassPane.setLayout(null);
        int tmpX = 0;
        int x = e.getXOnScreen() - gpP.x - label[i].getWidth() / 2;
        int y = e.getYOnScreen() - gpP.y - label[i].getHeight() / 2;
        tmpX = (((int)label[i].getSize().getWidth())*(i-this.part));
        label[i].setLocation(x+tmpX, y);
    }
    
}
