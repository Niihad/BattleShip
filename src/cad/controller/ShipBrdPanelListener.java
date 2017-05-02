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
import cad.view.PlacementScreen;

public class ShipBrdPanelListener extends MouseAdapter {
	
	private Model model;
	private PlacementScreen placement;
    private JPanel[][] board;
    private JPanel[] originalJPanel;
    private JLabel[] label;
    private int length, part, abs, absC, ord;
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
    	JPanel comp = (JPanel) src.getComponentAt(e.getPoint());
    	// ainsi que son index sur le gridLayout
    	int ord = placement.getIndexJPanel(comp).x;
    	int abs = placement.getIndexJPanel(comp).y;
    	int absC = (abs>Model.getWidth()) ? abs-Model.getWidth()-3 : abs;
  
        if (comp != null && ((JComponent) comp).getComponentCount() == 1) {
        	Cell cell = model.getBoardConvert(abs)[ord][absC];
        	/* les differentes cell que constitue un bateau on tous le meme ship en commun. 
    		 * il est donc necessaire de cloner la reference ship des anciens position cells du bateau
    		 * puisque ces dernieres devront etre mise a null.
    		 */
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
        		if (this.model.verificationRotationShip(cell,abs,length,!rotation,middle)){
	        		this.model.rotationShipPlacement(cell, abs-middle, part);
	        		for(int i=0; i<length; i++){
	        			if(!this.rotation){ // horizontal
	        				JPanel panelOrigin = (JPanel) board[ord][abs+i-part];
	        				JLabel label = (JLabel) panelOrigin.getComponent(0);
	        				JPanel panelDestination = (JPanel) board[ord+i-length/2][abs-middle];
	        				panelDestination.add(label);
	        			}else{ // vertical
	        				JPanel panelOrigin = (JPanel) board[ord+i-part][abs];
	        				JLabel label = (JLabel) panelOrigin.getComponent(0);
	        				JPanel panelDestination = (JPanel) board[ord-middle][abs+i-length/2];
	        				panelDestination.add(label);
	        			}
	        		}
	        		this.placement.revalidate();
	                this.placement.repaint();
        		}
	        }else{
	        	// stock chaque cells que constitue le bateau selectionné
	        	this.originalJPanel = new JPanel[length]; 
	        	// stock chaque label que constitue le bateau selectionné
	        	this.label = new JLabel[length];
	        	for(int i=0; i<length; i++){
	        		if(!this.rotation){ // horizontal
	        			Cell rmvCell = model.getBoardConvert(abs)[ord][absC+i-part];
	        			rmvCell.setShip(null);
	        			this.originalJPanel[i] = (JPanel) board[ord][abs+i-part];
	        			this.repaintClick(this.originalJPanel[i],i,e,true);
	        		}else{ // vertical
	        			Cell rmvCell = model.getBoardConvert(abs)[ord+i-part][absC];
	        			rmvCell.setShip(null);
	        			this.originalJPanel[i] = (JPanel) board[ord+i-part][abs];
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
    	for(int i=0; i<length; i++){
			glassPane = (JPanel) SwingUtilities.getRootPane(label[i]).getGlassPane();
	        this.buildShip(glassPane, i, e);
	        placement.repaint();
		}
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    	if (label == null) {
            return;
        }
        JPanel src = (JPanel) e.getSource();
        JPanel comp = (JPanel) src.getComponentAt(e.getPoint());
        ord = placement.getIndexJPanel(comp).x;
    	abs = placement.getIndexJPanel(comp).y;
    	absC = (abs>Model.getWidth()) ? abs-Model.getWidth()-3 : abs;
        if (comp != null) {
        	if((abs < 11 || abs > 13) && ord != 0 && abs != 0){
	        	Cell cell = model.getBoardConvert(abs)[ord][absC]; 
	        	if (this.model.verificationPlacementShip(cell, abs, this.length, this.rotation,part)){ 
	        		for(int i=0; i<length; i++){
		        		if(!this.rotation){ // horizontal
			        		this.model.movePlacementShipBoard(cell,abs,i,part);
			        		JPanel panelDestination = (JPanel) board[ord][abs+i-part];
			        		panelDestination.add(label[i]);
			        	}else{ // vertical
			        		this.model.movePlacementShipBoard(cell,abs,i,part);
			        		JPanel panelDestination = (JPanel) board[ord+i-part][abs];
			        		panelDestination.add(label[i]);
			        	}
		        	}
	        	}else{
	        		this.replaeShip();
				}
        	}else{
        		this.replaeShip();
			}
        }else{
			this.replaeShip();
		}
        this.placement.revalidate();
        this.placement.repaint();
        this.model.print(this.model.getBoardPlayer());
        this.model.print(this.model.getBoardAI());
        label = null;
    }
    
    
    /***********************************************************/
	/************************* FONCTION ************************/
	/***********************************************************/
    
    private void repaintClick(JPanel cell, int i, MouseEvent e, boolean first){
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
        int tmpX = 0, tmpY = 0;
        int x = e.getXOnScreen() - gpP.x - label[i].getWidth() / 2;
        int y = e.getYOnScreen() - gpP.y - label[i].getHeight() / 2;
        if(!this.rotation) // horizontal
        	tmpX = (((int)label[i].getSize().getWidth())*(i-this.part));
        else
        	tmpY = (((int)label[i].getSize().getHeight())*(i-this.part));
        label[i].setLocation(x+tmpX, y+tmpY);
    }
    
    private void replaeShip(){
    	for(int i=0; i<originalJPanel.length; i++){
			originalJPanel[i].add(label[i]);
			int ordOld = placement.getIndexJPanel(originalJPanel[i]).x;
	    	int absOld = placement.getIndexJPanel(originalJPanel[i]).y;
	    	int absCold = (absOld>Model.getWidth()) ? absOld-Model.getWidth()-3 : absOld;
			Cell OriginCell = model.getBoardConvert(absOld)[ordOld][absCold];
			this.model.replacePlacementShipBoard(OriginCell,absOld,i);
		}
    }
    
}
