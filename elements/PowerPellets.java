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
public class PowerPellets extends Element implements Serializable{
     private  int pointCount = 100;
        //"Conta quantos powerpallets ja foram comidos"
        //Contem a quantidade de pontos que o pacman ganharÃ¡ 
        //   ao comer um fantasma
    
    public PowerPellets(String imageName) {
    super(imageName);
    this.isTransposable = true;
    this.isMortal = false;
    this.flag = 0; //----|| zero -> o pacman nÃ£o estÃ¡ com a powerPellets
                   //----|| um -> (dentro dos 7 seg) pode comer fantasmas
    }

    public PowerPellets(String imageName, int i, int j) {
        super(imageName, i, j);
        this.isTransposable = true;
        this.isMortal = false;
    }
     
    
    //Se o pacman comer um powerpellet
    //chamar este mÃ©todo
    public void killerOfGhosts(){
        //dando + 50 pontos aos pacman
//        PacMan.setScore += 50; // [criar variavel Score (set e get) em pacMan]
        this.flag = 1;
        if(this.timer(7) == true) this.flag = 0;
    }
    
    //Se o pacman ocupar a mesma posiÃ§Ã£o de um fantasma, testar se flag == 1
    //chamar este metodo (retorna true se nÃ£o houver mais fantasmas)
    public boolean eatingTheGhost(){
        //dando pontos aos pacman
        pointCount = (pointCount * 2);
 //       pacman.setScore += pointCount;
        
        if(pointCount != 1600) return false;
        return true;
    }
    
    public boolean timer(int segundos){
        Thread threadControl = new Thread((Runnable) this);
        return true;
    }

    public  int getPointCount() {
        return pointCount;
    }

    
    
    //desenhar 4 powerPallets nas 4 pontas da fase
    //criar um metodo para calcular estas posicoes
    @Override
    public void autoDraw(Graphics g) {
        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
    }
    
    @Override
    public void alternativeImage(String imageName1, String imageName2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
