/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import java.awt.Graphics;
import java.io.Serializable;
import utils.Drawing;

/**
 *
 * @author Bruner Eduardo
 */
public class PacDots extends Element implements Serializable{
    public final int valeu=10;
    
    public PacDots(String imageName) {
        super(imageName);
        super.isMortal = false;
        super.isTransposable = true;
    }

    public PacDots(String imageName, int i, int j) {
        super(imageName, i, j);
         super.isMortal = false;
        super.isTransposable = true;
    }

    public int getValeu() {
        return valeu;
    }
    
    @Override
    public void autoDraw(Graphics g) {
        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
    }

    @Override
    public void alternativeImage(String imageName1, String imageName2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
