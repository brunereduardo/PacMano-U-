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
public class BackGround extends Element implements Serializable{

    public BackGround(String imageName) {
        super(imageName);
        super.isMortal=false;
        super.isTransposable = false; // paredes
    }

    public BackGround(String imageName, int i, int j) {
        super(imageName, i, j);
        super.isMortal=false;
        super.isTransposable = false;
    }
   
    @Override
    public void autoDraw(Graphics g) {
        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
    }

    @Override
    public void alternativeImage(String imageName1, String imageName2) {
        // não faz nada 
    }
    
}
