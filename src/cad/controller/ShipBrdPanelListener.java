package cad.controller;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import cad.view.CellView;

public class ShipBrdPanelListener extends MouseAdapter {
    private JLabel label;
    private JPanel originalJPanel;
    private JPanel panel, glassPane;
    private Point gpP;
    
    public ShipBrdPanelListener(JPanel panel){
    	this.panel = panel;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() != MouseEvent.BUTTON1) {
            return;
        }
        JPanel src = (JPanel) e.getSource();
        Component comp = src.getComponentAt(e.getPoint());
        if (comp != null && ((JComponent) comp).getComponentCount() == 1) {
            originalJPanel = (JPanel) comp;
            label = (JLabel) originalJPanel.getComponent(0);
        } else {
            return;
        }
        originalJPanel.remove(label);
        originalJPanel.revalidate();
        originalJPanel.repaint();
        
        glassPane = (JPanel) SwingUtilities.getRootPane(originalJPanel).getGlassPane();
        glassPane.setVisible(true);
        gpP = glassPane.getLocationOnScreen();
        glassPane.setLayout(null);
        int x = e.getXOnScreen() - gpP.x - label.getWidth() / 2;
        int y = e.getYOnScreen() - gpP.y - label.getHeight() / 2;
        label.setLocation(x, y);
        label.setSize(label.getPreferredSize());
        glassPane.add(label);
        
        glassPane.repaint();
        e.consume();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (label == null) {
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
        panel.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (label == null) {
            return;
        }
        int x = e.getXOnScreen() - gpP.x - label.getWidth() / 2;
        int y = e.getYOnScreen() - gpP.y - label.getHeight() / 2;
        label.setLocation(x, y);
        
        panel.repaint();
    }
    
    private boolean isNewJPanelValid(CellView cell) {
        int x = cell.getAbs();
        int y = cell.getOrd();
        return !(x==0 || x==13 || y==0 || (y>10 && y<13));
    }
}
