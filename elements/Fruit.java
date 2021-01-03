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
public class Fruit extends Element implements Serializable{
    private int score;
    private String name;
    
    public Fruit(String imageName) {
        super(imageName);
        this.name = imageName;
        this.flag =1;
        super.isMortal = false;
        super.isTransposable = true;
        if ("cereja.png".equals(imageName)) {
            this.score = 100;
        } else {
            this.score = 300;
        }
    }

    public Fruit(String imageName, int i, int j) {
        super(imageName, i, j);
         super.isMortal = false;
         this.flag =1;
        super.isTransposable = true;
        if ("cereja.png".equals(imageName)) {
            this.score = 100;
        } else {
            this.score = 300;
        }
        
    }

    public String getName() {
        return name;
    }
    
    @Override
    public void autoDraw(Graphics g) {
        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
    }

    @Override
    public void alternativeImage(String imageName1, String imageName2) {
    }

    public int getScore() {
        return score;
    }
    
}
