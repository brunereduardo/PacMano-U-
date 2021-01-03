/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import control.MyTempoThread;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Drawing;
import utils.PacManException;

/**
 *
 * @author Bruner Eduardo
 */
public class Stage implements Serializable {
    private Element[][] fase;
    private static int placar = 0;
    private int qPP; //quantidade de packdots e powerpallets
    private int tipo;
    private int help;
    private ArrayList<Element> lista;
    private ArrayList<Fruit> cesta;
    private MyTempoThread auxTimer;
    public Stage() {
    }

    public Stage(int tipo, PacMan p, Ghost g1, Ghost g2, Ghost g3, Ghost g4, MyTempoThread auxTimer) throws PacManException {
        this.auxTimer = auxTimer;
        this.fase = new Element[20][20];
        this.qPP = 0;       
        lista = new ArrayList<Element>();
        this.fase[1][1] = p;
            lista.add(p);
        this.fase[9][7] = g1;// vermelho
            lista.add(g1);
        this.fase[9][8] = g2;// rosa
            lista.add(g2);
        this.fase[9][11] = g3;//azul
            lista.add(g3);
        this.fase[9][12] = g4;//laranja
            lista.add(g4);
        this.tipo = tipo;
        this.constructNormal(tipo);  
        this.cesta = new ArrayList<>();
    }

    public int getqPP() {
        return qPP;
    }

    public void setqPP(int qPP) {
        this.qPP = qPP;
    }

    public Element getFase(int i, int j) {
        return fase[i][j];
    }

    public void setFaseElement(int i, int j, Element e){
        this.fase[i][j] = e;
    }
    public void removeElement(Element e){
        this.setFaseElement((int)e.getPosition().getX(),(int) e.getPosition().getY(),this.lista.get(0));
    }
    public void setFase(Element[][] fase) {
        this.fase = fase;
    }
    public ArrayList<Element> getLista() {
        return lista;
    }
    
    public void setLista(ArrayList<Element> lista) {
        this.lista = lista;
    }

    public ArrayList<Fruit> getCesta() {
        return cesta;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getHelp() {
        return help;
    }

    public int getPlacar() {
        return placar;
    }

    public void setPlacar(int placar) {
        this.placar = placar;
        
    }
    
    public void setFruit(Fruit f){
        Random r = new Random();
        while(f.flag == 1){
            int i = r.nextInt(16)+1;
            int j = r.nextInt(17)+1;
            if(j!=0 && j!=19){
                if( (this.fase[i][j].isTransposable) && (!(this.fase[i][j] instanceof PowerPellets)) && ((!(this.fase[i][j] instanceof Ghost))) ){
                    f.setPosition(i, j);
                    this.cesta.add(f);
                    this.fase[i][j] = f;
                    this.qPP--;
                    f.flag = 0;
                }
            }
        }

    }
    public void countPlacar(Element e) throws PacManException{
        if(this.getPlacar()== 10000  ){
            throw new PacManException("Ganha uma vida");
        }

        if( this.getPlacar()!= 10000){
            if(e instanceof PacDots){
                this.setPlacar(this.getPlacar() + ((PacDots) e).getValeu());
            }if(e instanceof PowerPellets){
                this.setPlacar(this.getPlacar() + ((PowerPellets) e).getPointCount());
                throw new PacManException("Comeu um PowerPellet");
            }if( e instanceof Fruit){
                this.setPlacar(this.getPlacar() + ((Fruit) e).getScore());
            }
            if(e instanceof Ghost){
                if(this.lista.size() == 5){
                    this.setPlacar(this.getPlacar() + 200);
                    this.removeElement(e);
                    throw new PacManException(((Ghost) e).getName());
                }if(this.lista.size() == 4){
                    this.setPlacar(this.getPlacar() + 400);
                    throw new PacManException(((Ghost) e).getName());
                }if(this.lista.size() == 3){
                    this.setPlacar(this.getPlacar() + 800);
                    throw new PacManException(((Ghost) e).getName());
                }if(this.lista.size() == 2){
                    this.setPlacar(this.getPlacar() + 1600);
                    throw new PacManException(((Ghost) e).getName());
                }
            
            }
        }
    }
    
   public void constructNormal(int tipo) throws PacManException{
        if(tipo == 0) {
            // Primeiro Mapa
            // Ghost's house{
            try{
                this.fase[8][6] = new BackGround("1CurvaSupEsq.png", 8, 6);
                this.fase[8][7] = new BackGround("1ContHorizontal.png", 8, 7);
                this.fase[8][8] = new BackGround("1FimDireita.png", 8, 8);
                this.fase[8][11] = new BackGround("1FimEsquerda.png", 8, 11);
                this.fase[8][12] = new BackGround("1ContHorizontal.png", 8, 12);
                this.fase[8][13] = new BackGround("1CurvaSupDir.png", 8, 13);

                this.fase[9][6] = new BackGround("1ContVertical.png", 9, 6);
                this.fase[9][13] = new BackGround("1ContVertical.png", 9, 13);

                // fazendo um teste para prender os fantasmas {
                    //this.fase[8][9] = new BackGround("1ContHorizontal.png", 8, 9);
                    //this.fase[8][10] = new BackGround("1ContHorizontal.png", 8, 10);
                
                //}
                
                this.fase[10][6] = new BackGround("1CurvaInfEsq.png", 10, 6);
                this.fase[10][7] = new BackGround("1ContHorizontal.png", 10, 7);
                this.fase[10][8] = new BackGround("1ContHorizontal.png", 10, 8);
                this.fase[10][9] = new BackGround("1ContHorizontal.png", 10, 9);
                this.fase[10][10] = new BackGround("1ContHorizontal.png", 10, 10);
                this.fase[10][11] = new BackGround("1ContHorizontal.png", 10, 11);
                this.fase[10][12] = new BackGround("1ContHorizontal.png", 10, 12);
                this.fase[10][13] = new BackGround("1CurvaInfDir.png", 10, 13);
            }catch(Exception e) {
                throw new PacManException("DEU MERDA NA GHOST HOUSE ");
            }
            try {
                //BORDAS
                for(int i = 0; i < 20; i++) {
                    if(i == 0) {
                        this.fase[0][i] = new BackGround("1CurvaSupEsq.png", 0, i);
                        this.fase[18][i] = new BackGround("1CurvaInfEsq.png", 18, i);
                    } else if(i == 19) {
                        this.fase[0][i] = new BackGround("1CurvaSupDir.png", 0, i);
                        this.fase[18][i] = new BackGround("1CurvaInfDir.png", 18, i);
                    } else {
                        this.fase[0][i] = new BackGround("1ContHorizontal.png", 0, i);
                        this.fase[18][i] = new BackGround("1ContHorizontal.png", 18, i);
                    }
                }
                //LATERAIS
                for(int i = 1; i < 18; i++) {
                    if(i != 6) {
                        this.fase[i][0] = new BackGround("1ContVertical.png", i, 0);
                        this.fase[i][19] = new BackGround("1ContVertical.png", i, 19);
                    } //FAZER O ELSE E COLOCAR UM PORTAL
                }

                this.fase[1][7] = new BackGround("1ContVertical.png", 1, 7);
                this.fase[1][12] = new BackGround("1ContVertical.png", 1, 12);

                this.fase[2][2] = new BackGround("1CurvaSupEsq.png", 2, 2);
                this.fase[2][3] = new BackGround("1ContHorizontal.png", 2, 3);
                this.fase[2][4] = new BackGround("1ContHorizontal.png", 2, 4);
                this.fase[2][5] = new BackGround("1FimDireita.png", 2, 5);
                this.fase[2][7] = new BackGround("1ContVertical.png", 2, 7);
                this.fase[2][9] = new BackGround("1CurvaSupEsq.png", 2, 9);
                this.fase[2][10] = new BackGround("1CurvaSupDir.png", 2, 10);
                this.fase[2][12] = new BackGround("1ContVertical.png", 2, 12);
                this.fase[2][14] = new BackGround("1FimEsquerda.png", 2, 14);
                this.fase[2][15] = new BackGround("1ContHorizontal.png", 2, 15);
                this.fase[2][16] = new BackGround("1ContHorizontal.png", 2, 16);
                this.fase[2][17] = new BackGround("1CurvaSupDir.png", 2, 17);

                this.fase[3][2] = new BackGround("1FimBaixo.png", 3, 2);
                this.fase[3][7] = new BackGround("1ContVertical.png", 3, 7);
                this.fase[3][9] = new BackGround("1CurvaInfEsq.png", 3, 9);
                this.fase[3][10] = new BackGround("1CurvaInfDir.png", 3, 10);
                this.fase[3][12] = new BackGround("1ContVertical.png", 3, 12);
                this.fase[3][17] = new BackGround("1FimBaixo.png", 3, 17);

                this.fase[4][4] = new BackGround("1FimEsquerda.png", 4, 4);
                this.fase[4][5] = new BackGround("1FimDireita.png", 4, 5);
                this.fase[4][7] = new BackGround("1FimBaixo.png", 4, 7);
                this.fase[4][12] = new BackGround("1FimBaixo.png", 4, 12);
                this.fase[4][14] = new BackGround("1FimEsquerda.png", 4, 14);
                this.fase[4][15] = new BackGround("1FimDireita.png", 4, 15);

                this.fase[5][0] = new BackGround("1CurvaInfEsq.png", 5, 0);
                this.fase[5][1] = new BackGround("1ContHorizontal.png", 5, 1);
                this.fase[5][2] = new BackGround("1FimDireita.png", 5, 2);
                this.fase[5][9] = new BackGround("1CurvaSupEsq.png", 5, 9);
                this.fase[5][10] = new BackGround("1CurvaSupDir.png", 5, 10);
                this.fase[5][17] = new BackGround("1FimEsquerda.png", 5, 17);
                this.fase[5][18] = new BackGround("1ContHorizontal.png", 5, 18);
                this.fase[5][19] = new BackGround("1CurvaInfDir.png", 5, 19);

                BackGround b = new BackGround("portalLaranja.png", 6, 0);
                b.setTransposable(true);
                this.fase[6][0] = b;
                this.fase[6][4] = new BackGround("1FimCima.png", 6, 4);
                this.fase[6][6] = new BackGround("1FimEsquerda.png", 6, 6);
                this.fase[6][7] = new BackGround("1ContHorizontal.png", 6, 7);
                this.fase[6][8] = new BackGround("1ContHorizontal.png", 6, 8);
                this.fase[6][9] = new BackGround("1ContHorizontal.png", 6, 9);
                this.fase[6][10] = new BackGround("1ContHorizontal.png", 6, 10);
                this.fase[6][11] = new BackGround("1ContHorizontal.png", 6, 11);
                this.fase[6][12] = new BackGround("1ContHorizontal.png", 6, 12);
                this.fase[6][13] = new BackGround("1FimDireita.png", 6, 13);
                this.fase[6][15] = new BackGround("1FimCima.png", 6, 15);
                this.fase[6][19] = new BackGround("portalAzul.png", 6, 19);
                b = new BackGround("portalAzul.png", 6, 19);
                b.setTransposable(true);
                this.fase[6][19] = b;

                this.fase[7][0] = new BackGround("1FimCima.png", 7, 0);
                this.fase[7][2] = new BackGround("1FimEsquerda.png", 7, 2);
                this.fase[7][3] = new BackGround("1ContHorizontal.png", 7, 3);
                this.fase[7][4] = new BackGround("1ContVertical.png", 7, 4);
                this.fase[7][15] = new BackGround("1ContVertical.png", 7, 15);
                this.fase[7][16] = new BackGround("1ContHorizontal.png", 7, 16);
                this.fase[7][17] = new BackGround("1FimDireita.png", 7, 17);
                this.fase[7][19] = new BackGround("1FimCima.png", 7, 19);

                this.fase[8][4] = new BackGround("1ContVertical.png", 8, 4);
                this.fase[8][15] = new BackGround("1ContVertical.png", 8, 15);

                this.fase[9][1] = new BackGround("1ContHorizontal.png", 9, 1);
                this.fase[9][2] = new BackGround("1FimDireita.png", 9, 2);
                this.fase[9][4] = new BackGround("1ContVertical.png", 9, 4);
                this.fase[9][15] = new BackGround("1ContVertical.png", 9, 15);
                this.fase[9][17] = new BackGround("1FimEsquerda.png", 9, 17);
                this.fase[9][18] = new BackGround("1ContHorizontal.png", 9, 18);

                this.fase[10][4] = new BackGround("1ContVertical.png", 10, 4);
                this.fase[10][15] = new BackGround("1ContVertical.png", 10, 15);

                this.fase[11][2] = new BackGround("1FimCima.png", 11, 2);
                this.fase[11][4] = new BackGround("1ContVertical.png", 11, 4);
                this.fase[11][15] = new BackGround("1ContVertical.png", 11, 15);
                this.fase[11][17] = new BackGround("1FimCima.png", 11, 17);

                this.fase[12][2] = new BackGround("1ContVertical.png", 12, 2);
                this.fase[12][4] = new BackGround("1FimBaixo.png", 12, 4);
                this.fase[12][6] = new BackGround("1FimEsquerda.png", 12, 6);
                this.fase[12][7] = new BackGround("1CurvaSupDir.png", 12, 7);
                this.fase[12][9] = new BackGround("1CurvaSupEsq.png", 12, 9);
                this.fase[12][10] = new BackGround("1CurvaSupDir.png", 12, 10);
                this.fase[12][12] = new BackGround("1CurvaSupEsq.png", 12, 12);
                this.fase[12][13] = new BackGround("1FimDireita.png", 12, 13);
                this.fase[12][15] = new BackGround("1FimBaixo.png", 12, 15);
                this.fase[12][17] = new BackGround("1ContVertical.png", 12, 17);

                this.fase[13][2] = new BackGround("1ContVertical.png", 13, 2);
                this.fase[13][7] = new BackGround("1ContVertical.png", 13, 7);
                this.fase[13][9] = new BackGround("1ContVertical.png", 13, 9);
                this.fase[13][10] = new BackGround("1ContVertical.png", 13, 10);
                this.fase[13][12] = new BackGround("1ContVertical.png", 13, 12);
                this.fase[13][17] = new BackGround("1ContVertical.png", 13, 17);

                this.fase[14][2] = new BackGround("1ContVertical.png", 14, 2);
                this.fase[14][4] = new BackGround("1CurvaSupEsq.png", 14, 4);
                this.fase[14][5] = new BackGround("1FimDireita.png", 14, 5);
                this.fase[14][7] = new BackGround("1FimBaixo.png", 14, 7);
                this.fase[14][9] = new BackGround("1CurvaInfEsq.png", 14, 9);
                this.fase[14][10] = new BackGround("1CurvaInfDir.png", 14, 10);
                this.fase[14][12] = new BackGround("1FimBaixo.png", 14, 12);
                this.fase[14][14] = new BackGround("1FimEsquerda.png", 14, 14);
                this.fase[14][15] = new BackGround("1CurvaSupDir.png", 14, 15);
                this.fase[14][17] = new BackGround("1ContVertical.png", 14, 17);

                this.fase[15][2] = new BackGround("1ContVertical.png", 15, 2);
                this.fase[15][4] = new BackGround("1ContVertical.png", 15, 4);
                this.fase[15][15] = new BackGround("1ContVertical.png", 15, 15);
                this.fase[15][17] = new BackGround("1ContVertical.png", 15, 17);

                this.fase[16][2] = new BackGround("1FimBaixo.png", 16, 2);
                this.fase[16][4] = new BackGround("1ContVertical.png", 16, 4);
                this.fase[16][6] = new BackGround("1FimEsquerda.png", 16, 6);
                this.fase[16][7] = new BackGround("1ContHorizontal.png", 16, 7);
                this.fase[16][8] = new BackGround("1ContHorizontal.png", 16, 8);
                this.fase[16][9] = new BackGround("1ContHorizontal.png", 16, 9);
                this.fase[16][10] = new BackGround("1ContHorizontal.png", 16, 10);
                this.fase[16][11] = new BackGround("1ContHorizontal.png", 16, 11);
                this.fase[16][12] = new BackGround("1ContHorizontal.png", 16, 12);
                this.fase[16][13] = new BackGround("1FimDireita.png", 16, 13);
                this.fase[16][15] = new BackGround("1ContVertical.png", 16, 15);
                this.fase[16][17] = new BackGround("1FimBaixo.png", 16, 17);

                this.fase[17][4] = new BackGround("1ContVertical.png", 17, 4);
                this.fase[17][15] = new BackGround("1ContVertical.png", 17, 15);  
            }catch(Exception e) {
                throw new PacManException("DEU MERDA NO STAGE 1");
            }
            
        } else if(tipo == 1) {

           // Segundo Mapa
            try{
                this.fase[8][6] = new BackGround("2CurvaSupEsq.png", 8, 6);
                this.fase[8][7] = new BackGround("2ContHorizontal.png", 8, 7);
                this.fase[8][8] = new BackGround("2FimDireita.png", 8, 8);
                this.fase[8][11] = new BackGround("2FimEsquerda.png", 8, 11);
                this.fase[8][12] = new BackGround("2ContHorizontal.png", 8, 12);
                this.fase[8][13] = new BackGround("2CurvaSupDir.png", 8, 13);

                this.fase[9][6] = new BackGround("2ContVertical.png", 9, 6);
                this.fase[9][13] = new BackGround("2ContVertical.png", 9, 13);

                this.fase[10][6] = new BackGround("2CurvaInfEsq.png", 10, 6);
                this.fase[10][7] = new BackGround("2ContHorizontal.png", 10, 7);
                this.fase[10][8] = new BackGround("2ContHorizontal.png", 10, 8);
                this.fase[10][9] = new BackGround("2ContHorizontal.png", 10, 9);
                this.fase[10][10] = new BackGround("2ContHorizontal.png", 10, 10);
                this.fase[10][11] = new BackGround("2ContHorizontal.png", 10, 11);
                this.fase[10][12] = new BackGround("2ContHorizontal.png", 10, 12);
                this.fase[10][13] = new BackGround("2CurvaInfDir.png", 10, 13);
            }catch(Exception e) {
                throw new PacManException("DEU MERDA Na GHOST HOUSE ");
            }
            try {
                //BORDAS
                for(int i = 0; i < 20; i++) {
                    if(i == 0) {
                        this.fase[0][i] = new BackGround("2CurvaSupEsq.png", 0, i);
                        this.fase[18][i] = new BackGround("2CurvaInfEsq.png", 18, i);
                    } else if(i == 19) {
                        this.fase[0][i] = new BackGround("2CurvaSupDir.png", 0, i);
                        this.fase[18][i] = new BackGround("2CurvaInfDir.png", 18, i);
                    } else {
                        this.fase[0][i] = new BackGround("2ContHorizontal.png", 0, i);
                        this.fase[18][i] = new BackGround("2ContHorizontal.png", 18, i);
                    }
                }
                //LATERAIS
                for(int i = 1; i < 18; i++) {
                    if(i != 9) {
                        this.fase[i][0] = new BackGround("2ContVertical.png", i, 0);
                        this.fase[i][19] = new BackGround("2ContVertical.png", i, 19);
                    } //FAZER O ELSE E COLOCAR UM PORTAL
                }

                this.fase[2][2] = new BackGround("2CurvaSupEsq.png", 2, 2);
                this.fase[2][3] = new BackGround("2ContHorizontal.png", 2, 3);
                this.fase[2][4] = new BackGround("2ContHorizontal.png", 2, 4);
                this.fase[2][5] = new BackGround("2FimDireita.png", 2, 5);
                this.fase[2][7] = new BackGround("2CurvaSupEsq.png", 2, 7);
                this.fase[2][8] = new BackGround("2ContHorizontal.png", 2, 8);
                this.fase[2][9] = new BackGround("2ContHorizontal.png", 2, 9);
                this.fase[2][10] = new BackGround("2ContHorizontal.png", 2, 10);
                this.fase[2][11] = new BackGround("2ContHorizontal.png", 2, 11);
                this.fase[2][12] = new BackGround("2CurvaSupDir.png", 2, 12);
                this.fase[2][14] = new BackGround("2FimEsquerda.png", 2, 14);
                this.fase[2][15] = new BackGround("2ContHorizontal.png", 2, 15);
                this.fase[2][16] = new BackGround("2ContHorizontal.png", 2, 16);
                this.fase[2][17] = new BackGround("2CurvaSupDir.png", 2, 17);

                this.fase[3][2] = new BackGround("2ContVertical.png", 3, 2);
                this.fase[3][7] = new BackGround("2ContVertical.png", 3, 7);
                this.fase[3][12] = new BackGround("2ContVertical.png", 3, 12);
                this.fase[3][17] = new BackGround("2ContVertical.png", 3, 17);

                this.fase[4][2] = new BackGround("2FimBaixo.png", 4, 2);
                this.fase[4][4] = new BackGround("2FimCima.png", 4, 4);
                this.fase[4][6] = new BackGround("2FimEsquerda.png", 4, 6);
                this.fase[4][7] = new BackGround("2CurvaInfDir.png", 4, 7);
                this.fase[4][9] = new BackGround("2CurvaSupEsq.png", 4, 9);
                this.fase[4][10] = new BackGround("2CurvaSupDir.png", 4, 10);
                this.fase[4][12] = new BackGround("2CurvaInfEsq.png", 4, 12);
                this.fase[4][13] = new BackGround("2FimDireita.png", 4, 13);
                this.fase[4][15] = new BackGround("2FimCima.png", 4, 15);
                this.fase[4][17] = new BackGround("2FimBaixo.png", 4, 17);

                this.fase[5][4] = new BackGround("2ContVertical.png", 5, 4);
                this.fase[5][9] = new BackGround("2ContVertical.png", 5, 9);
                this.fase[5][10] = new BackGround("2ContVertical.png", 5, 10);
                this.fase[5][15] = new BackGround("2ContVertical.png", 5, 15);

                this.fase[6][2] = new BackGround("2FimEsquerda.png", 6, 2);
                this.fase[6][3] = new BackGround("2ContHorizontal.png", 6, 3);
                this.fase[6][4] = new BackGround("2ContHorizontal.png", 6, 4);
                this.fase[6][5] = new BackGround("2ContHorizontal.png", 6, 5);
                this.fase[6][6] = new BackGround("2FimDireita.png", 6, 6);
                this.fase[6][8] = new BackGround("2FimEsquerda.png", 6, 8);
                this.fase[6][9] = new BackGround("2ContHorizontal.png", 6, 9);
                this.fase[6][10] = new BackGround("2ContHorizontal.png", 6, 10);
                this.fase[6][11] = new BackGround("2FimDireita.png", 6, 11);
                this.fase[6][13] = new BackGround("2FimEsquerda.png", 6, 13);
                this.fase[6][14] = new BackGround("2ContHorizontal.png", 6, 14);
                this.fase[6][15] = new BackGround("2ContHorizontal.png", 6, 15);
                this.fase[6][16] = new BackGround("2ContHorizontal.png", 6, 16);
                this.fase[6][17] = new BackGround("2FimDireita.png", 6, 17);

                this.fase[7][4] = new BackGround("2ContVertical.png", 7, 4);
                this.fase[7][15] = new BackGround("2ContVertical.png", 7, 15);

                this.fase[8][0] = new BackGround("2CurvaInfEsq.png", 8, 0);
                this.fase[8][1] = new BackGround("2ContHorizontal.png", 8, 1);
                this.fase[8][2] = new BackGround("2FimDireita.png", 8, 2);
                this.fase[8][4] = new BackGround("2FimBaixo.png", 8, 4);
                this.fase[8][15] = new BackGround("2FimBaixo.png", 8, 15);
                this.fase[8][17] = new BackGround("2FimEsquerda.png", 8, 17);
                this.fase[8][18] = new BackGround("2ContHorizontal.png", 8, 18);
                this.fase[8][19] = new BackGround("2CurvaInfDir.png", 8, 19);

                BackGround b = new BackGround("portalLaranja.png", 9, 0);
                b.setTransposable(true);
                this.fase[9][0] = b;
                b = new BackGround("portalAzul.png", 9, 19);
                b.setTransposable(true);
                this.fase[9][19] = b;

                this.fase[10][0] = new BackGround("2CurvaSupEsq.png", 10, 0);
                this.fase[10][1] = new BackGround("2ContHorizontal.png", 10, 1);
                this.fase[10][2] = new BackGround("2FimDireita.png", 10, 2);
                this.fase[10][4] = new BackGround("2FimCima.png", 10, 4);
                this.fase[10][15] = new BackGround("2FimCima.png", 10, 15);
                this.fase[10][17] = new BackGround("2FimEsquerda.png", 10, 17);
                this.fase[10][18] = new BackGround("2ContHorizontal.png", 10, 18);
                this.fase[10][19] = new BackGround("2CurvaSupDir.png", 10, 19);
                
                this.fase[11][4] = new BackGround("2ContVertical.png", 11, 4);
                this.fase[11][6] = new BackGround("2CurvaInfEsq.png", 11, 6);
                this.fase[11][7] = new BackGround("2CurvaInfDir.png", 11, 7);
                this.fase[11][12] = new BackGround("2CurvaInfEsq.png", 11, 12);
                this.fase[11][13] = new BackGround("2CurvaInfDir.png", 11, 13);
                this.fase[11][15] = new BackGround("2ContVertical.png", 11, 15);

                this.fase[12][2] = new BackGround("2FimCima.png", 12, 2);
                this.fase[12][4] = new BackGround("2ContVertical.png", 12, 4);
                this.fase[12][9] = new BackGround("2CurvaSupEsq.png", 12, 9);
                this.fase[12][10] = new BackGround("2CurvaSupDir.png", 12, 10);
                this.fase[12][15] = new BackGround("2ContVertical.png", 12, 15);
                this.fase[12][17] = new BackGround("2FimCima.png", 12, 17);
                
                this.fase[13][2] = new BackGround("2ContVertical.png", 13, 2);
                this.fase[13][4] = new BackGround("2ContVertical.png", 13, 4);
                this.fase[13][5] = new BackGround("2CurvaSupDir.png", 13, 5);
                this.fase[13][7] = new BackGround("2FimCima.png", 13, 7);
                this.fase[13][9] = new BackGround("2ContVertical.png", 13, 9);
                this.fase[13][10] = new BackGround("2ContVertical.png", 13, 10);
                this.fase[13][12] = new BackGround("2FimCima.png", 13, 12);
                this.fase[13][14] = new BackGround("2CurvaSupEsq.png", 13, 14);
                this.fase[13][15] = new BackGround("2ContVertical.png", 13, 15);
                this.fase[13][17] = new BackGround("2ContVertical.png", 13, 17);

                this.fase[14][2] = new BackGround("2ContVertical.png", 14, 2);
                this.fase[14][4] = new BackGround("2FimBaixo.png", 14, 4);
                this.fase[14][5] = new BackGround("2CurvaInfDir.png", 14, 5);
                this.fase[14][7] = new BackGround("2ContVertical.png", 14, 7);
                this.fase[14][9] = new BackGround("2CurvaInfEsq.png", 14, 9);
                this.fase[14][10] = new BackGround("2CurvaInfDir.png", 14, 10);
                this.fase[14][12] = new BackGround("2ContVertical.png", 14, 12);
                this.fase[14][14] = new BackGround("2CurvaInfEsq.png", 14, 14);
                this.fase[14][15] = new BackGround("2FimBaixo.png", 14, 15);
                this.fase[14][17] = new BackGround("2ContVertical.png", 14, 17);

                this.fase[15][2] = new BackGround("2ContVertical.png", 15, 2);
                this.fase[15][7] = new BackGround("2ContVertical.png", 15, 7);
                this.fase[15][12] = new BackGround("2ContVertical.png", 15, 12);
                this.fase[15][17] = new BackGround("2ContVertical.png", 15, 17);

                this.fase[16][2] = new BackGround("2CurvaInfEsq.png", 16, 2);
                this.fase[16][3] = new BackGround("2ContHorizontal.png", 16, 3);
                this.fase[16][4] = new BackGround("2ContHorizontal.png", 16, 4);
                this.fase[16][5] = new BackGround("2FimDireita.png", 16, 5);
                this.fase[16][7] = new BackGround("2CurvaInfEsq.png", 16, 7);
                this.fase[16][8] = new BackGround("2ContHorizontal.png", 16, 8);
                this.fase[16][9] = new BackGround("2ContHorizontal.png", 16, 9);
                this.fase[16][10] = new BackGround("2ContHorizontal.png", 16, 10);
                this.fase[16][11] = new BackGround("2ContHorizontal.png", 16, 11);
                this.fase[16][12] = new BackGround("2CurvaInfDir.png", 16, 12);
                this.fase[16][14] = new BackGround("2FimEsquerda.png", 16, 14);
                this.fase[16][15] = new BackGround("2ContHorizontal.png", 16, 15);
                this.fase[16][16] = new BackGround("2ContHorizontal.png", 16, 16);
                this.fase[16][17] = new BackGround("2CurvaInfDir.png", 16, 17);

            }catch(Exception e) {
                throw new PacManException("DEU MERDA NO STAGE 2");
            }
        } else {
            //GHOST HOUSE
            try{
                this.fase[8][6] = new BackGround("3CurvaSupEsq.png", 8, 6);
                this.fase[8][7] = new BackGround("3ContHorizontal.png", 8, 7);
                this.fase[8][8] = new BackGround("3FimDireita.png", 8, 8);
                this.fase[8][11] = new BackGround("3FimEsquerda.png", 8, 11);
                this.fase[8][12] = new BackGround("3ContHorizontal.png", 8, 12);
                this.fase[8][13] = new BackGround("3CurvaSupDir.png", 8, 13);
                
                this.fase[9][6] = new BackGround("3ContVertical.png", 9, 6);
                this.fase[9][13] = new BackGround("3ContVertical.png", 9, 13);

                this.fase[10][6] = new BackGround("3CurvaInfEsq.png", 10, 6);
                this.fase[10][7] = new BackGround("3ContHorizontal.png", 10, 7);
                this.fase[10][8] = new BackGround("3ContHorizontal.png", 10, 8);
                this.fase[10][9] = new BackGround("3ContHorizontal.png", 10, 9);
                this.fase[10][10] = new BackGround("3ContHorizontal.png", 10, 10);
                this.fase[10][11] = new BackGround("3ContHorizontal.png", 10, 11);
                this.fase[10][12] = new BackGround("3ContHorizontal.png", 10, 12);
                this.fase[10][13] = new BackGround("3CurvaInfDir.png", 10, 13);
            }catch(Exception e) {
                throw new PacManException("DEU MERDA Na GHOST HOUSE ");
            }
            try {
                for(int i = 0; i < 20; i++) {
                    if(i == 0) {
                        this.fase[0][i] = new BackGround("3CurvaSupEsq.png", 0, i);
                        this.fase[18][i] = new BackGround("3CurvaInfEsq.png", 18, i);
                    } else if(i == 19) {
                        this.fase[0][i] = new BackGround("3CurvaSupDir.png", 0, i);
                        this.fase[18][i] = new BackGround("3CurvaInfDir.png", 18, i);
                    } else {
                        this.fase[0][i] = new BackGround("3ContHorizontal.png", 0, i);
                        this.fase[18][i] = new BackGround("3ContHorizontal.png", 18, i);
                    }
                }
                //LATERAIS
                for(int i = 1; i < 18; i++) {
                    this.fase[i][0] = new BackGround("3ContVertical.png", i, 0);
                    this.fase[i][19] = new BackGround("3ContVertical.png", i, 19);
                }
                
                this.fase[1][5] = new BackGround("3ContVertical.png", 1, 5);
                this.fase[1][14] = new BackGround("3ContVertical.png", 1, 14);

                this.fase[2][2] = new BackGround("3FimEsquerda.png", 2, 2);
                this.fase[2][3] = new BackGround("3CurvaSupDir.png", 2, 3);
                this.fase[2][5] = new BackGround("3ContVertical.png", 2, 5);
                this.fase[2][7] = new BackGround("3CurvaSupEsq.png", 2, 7);
                this.fase[2][8] = new BackGround("3ContHorizontal.png", 2, 8);
                this.fase[2][9] = new BackGround("3ContHorizontal.png", 2, 9);
                this.fase[2][10] = new BackGround("3ContHorizontal.png", 2, 10);
                this.fase[2][11] = new BackGround("3ContHorizontal.png", 2, 11);
                this.fase[2][12] = new BackGround("3CurvaSupDir.png", 2, 12);
                this.fase[2][14] = new BackGround("3ContVertical.png", 2, 14);
                this.fase[2][16] = new BackGround("3CurvaSupEsq.png", 2, 16);
                this.fase[2][17] = new BackGround("3FimDireita.png", 2, 17);
                
                this.fase[3][3] = new BackGround("3ContVertical.png", 3, 3);
                this.fase[3][5] = new BackGround("3FimBaixo.png", 3, 5);
                this.fase[3][7] = new BackGround("3FimBaixo.png", 3, 7);
                this.fase[3][12] = new BackGround("3FimBaixo.png", 3, 12);
                this.fase[3][14] = new BackGround("3FimBaixo.png", 3, 14);
                this.fase[3][16] = new BackGround("3ContVertical.png", 3, 16);
                
                this.fase[4][1] = new BackGround("3CurvaSupDir.png", 4, 1);
                this.fase[4][3] = new BackGround("3ContVertical.png", 4, 3);
                this.fase[4][9] = new BackGround("3CurvaSupEsq.png", 4, 9);
                this.fase[4][10] = new BackGround("3CurvaSupDir.png", 4, 10);
                this.fase[4][16] = new BackGround("3ContVertical.png", 4, 16);
                this.fase[4][18] = new BackGround("3CurvaSupEsq.png", 4, 18);
                
                this.fase[5][1] = new BackGround("3CurvaInfDir.png", 5, 1);
                this.fase[5][3] = new BackGround("3CurvaInfEsq.png", 5, 3);
                this.fase[5][4] = new BackGround("3FimDireita.png", 5, 4);
                this.fase[5][6] = new BackGround("3FimCima.png", 5, 6);
                this.fase[5][8] = new BackGround("3CurvaSupEsq.png", 5, 8);
                this.fase[5][9] = new BackGround("3ContVertical.png", 5, 9);
                this.fase[5][10] = new BackGround("3ContVertical.png", 5, 10);
                this.fase[5][11] = new BackGround("3CurvaSupDir.png", 5, 11);
                this.fase[5][13] = new BackGround("3FimCima.png", 5, 13);
                this.fase[5][15] = new BackGround("3FimEsquerda.png", 5, 15);
                this.fase[5][16] = new BackGround("3CurvaInfDir.png", 5, 16);
                this.fase[5][18] = new BackGround("3CurvaInfEsq.png", 5, 18);
                
                this.fase[6][6] = new BackGround("3FimBaixo.png", 6, 6);
                this.fase[6][8] = new BackGround("3CurvaInfEsq.png", 6, 8);
                this.fase[6][9] = new BackGround("3ContHorizontal.png", 6, 9);
                this.fase[6][10] = new BackGround("3ContHorizontal.png", 6, 10);
                this.fase[6][11] = new BackGround("3CurvaInfDir.png", 6, 11);
                this.fase[6][13] = new BackGround("3FimBaixo.png", 6, 13);
                
                this.fase[7][2] = new BackGround("3CurvaSupEsq.png", 7, 2);
                this.fase[7][3] = new BackGround("3ContHorizontal.png", 7, 3);
                this.fase[7][4] = new BackGround("3CurvaSupDir.png", 7, 4);
                this.fase[7][15] = new BackGround("3CurvaSupEsq.png", 7, 15);
                this.fase[7][16] = new BackGround("3ContHorizontal.png", 7, 16);
                this.fase[7][17] = new BackGround("3CurvaSupDir.png", 7, 17);
                
                this.fase[8][2] = new BackGround("3CurvaInfEsq.png", 8, 2);
                this.fase[8][3] = new BackGround("3ContHorizontal.png", 8, 3);
                this.fase[8][4] = new BackGround("3CurvaInfDir.png", 8, 4);
                this.fase[8][15] = new BackGround("3CurvaInfEsq.png", 8, 15);
                this.fase[8][16] = new BackGround("3ContHorizontal.png", 8, 16);
                this.fase[8][17] = new BackGround("3CurvaInfDir.png", 8, 17);
                
                this.fase[10][1] = new BackGround("3ContHorizontal.png", 10, 1);
                this.fase[10][2] = new BackGround("3ContHorizontal.png", 10, 2);
                this.fase[10][3] = new BackGround("3ContHorizontal.png", 10, 3);
                this.fase[10][4] = new BackGround("3FimDireita.png", 10, 4);
                this.fase[10][15] = new BackGround("3FimEsquerda.png", 10, 15);
                this.fase[10][16] = new BackGround("3ContHorizontal.png", 10, 16);
                this.fase[10][17] = new BackGround("3ContHorizontal.png", 10, 17);
                this.fase[10][18] = new BackGround("3ContHorizontal.png", 10, 18);

                this.fase[12][2] = new BackGround("3FimCima.png", 12, 2);
                this.fase[12][4] = new BackGround("3FimCima.png", 12, 4);
                this.fase[12][6] = new BackGround("3FimEsquerda.png", 12, 6);
                this.fase[12][7] = new BackGround("3FimDireita.png", 12, 7);
                this.fase[12][9] = new BackGround("3CurvaSupEsq.png", 12, 9);
                this.fase[12][10] = new BackGround("3CurvaSupDir.png", 12, 10);
                this.fase[12][12] = new BackGround("3FimEsquerda.png", 12, 12);
                this.fase[12][13] = new BackGround("3FimDireita.png", 12, 13);
                this.fase[12][15] = new BackGround("3FimCima.png", 12, 15);
                this.fase[12][17] = new BackGround("3FimCima.png", 12, 17);
                
                this.fase[13][2] = new BackGround("3ContVertical.png", 13, 2);
                this.fase[13][4] = new BackGround("3ContVertical.png", 13, 4);
                this.fase[13][9] = new BackGround("3ContVertical.png", 13, 9);
                this.fase[13][10] = new BackGround("3ContVertical.png", 13, 10);
                this.fase[13][15] = new BackGround("3ContVertical.png", 13, 15);
                this.fase[13][17] = new BackGround("3ContVertical.png", 13, 17);
                
                this.fase[14][2] = new BackGround("3ContVertical.png", 14, 2);
                this.fase[14][4] = new BackGround("3CurvaInfEsq.png", 14, 4);
                this.fase[14][5] = new BackGround("3ContHorizontal.png", 14, 5);
                this.fase[14][6] = new BackGround("3ContHorizontal.png", 14, 6);
                this.fase[14][7] = new BackGround("3FimDireita.png", 14, 7);
                this.fase[14][9] = new BackGround("3ContVertical.png", 14, 9);
                this.fase[14][10] = new BackGround("3ContVertical.png", 14, 10);
                this.fase[14][12] = new BackGround("3FimEsquerda.png", 14, 12);
                this.fase[14][13] = new BackGround("3ContHorizontal.png", 14, 13);
                this.fase[14][14] = new BackGround("3ContHorizontal.png", 14, 14);
                this.fase[14][15] = new BackGround("3CurvaInfDir.png", 14, 15);
                this.fase[14][17] = new BackGround("3ContVertical.png", 14, 17);
                
                this.fase[15][2] = new BackGround("3ContVertical.png", 15, 2);
                this.fase[15][9] = new BackGround("3ContVertical.png", 15, 9);
                this.fase[15][10] = new BackGround("3ContVertical.png", 15, 10);
                this.fase[15][17] = new BackGround("3ContVertical.png", 15, 17);
                
                this.fase[16][2] = new BackGround("3CurvaInfEsq.png", 16, 2);
                this.fase[16][3] = new BackGround("3ContHorizontal.png", 16, 3);
                this.fase[16][4] = new BackGround("3ContHorizontal.png", 16, 4);
                this.fase[16][5] = new BackGround("3ContHorizontal.png", 16, 5);
                this.fase[16][6] = new BackGround("3ContHorizontal.png", 16, 6);
                this.fase[16][7] = new BackGround("3CurvaSupDir.png", 16, 7);
                this.fase[16][9] = new BackGround("3CurvaInfEsq.png", 16, 9);
                this.fase[16][10] = new BackGround("3CurvaInfDir.png", 16, 10);
                this.fase[16][12] = new BackGround("3CurvaSupEsq.png", 16, 12);
                this.fase[16][13] = new BackGround("3ContHorizontal.png", 16, 13);
                this.fase[16][14] = new BackGround("3ContHorizontal.png", 16, 14);
                this.fase[16][15] = new BackGround("3ContHorizontal.png", 16, 15);
                this.fase[16][16] = new BackGround("3ContHorizontal.png", 16, 16);
                this.fase[16][17] = new BackGround("3CurvaInfDir.png", 16, 17);
                
                BackGround b = new BackGround("portalLaranja.png", 17, 6);
                b.setTransposable(true);
                this.fase[17][6] = b;
                this.fase[17][7] = new BackGround("3ContVertical.png", 17, 7);
                this.fase[17][12] = new BackGround("3ContVertical.png", 17, 12);
                b = new BackGround("portalAzul.png", 17, 13);
                b.setTransposable(true);
                this.fase[17][13] = b;
            }catch(Exception e) {
                throw new PacManException ("DEU MERDA NO STAGE 3");
            }           
        }
        // coloca os PacDots onde não foi inicializado ainda 
        try{
            for(int i = 0; i < 20 ;i++){
                for(int j = 0; j < 20; j++){
                    if(this.fase[i][j] == null){//7,12 1,18 17,18
                        if(i == 19)this.fase[i][j] = new BackGround("background.png",i,j);
                        else if((i == 7 && j == 13) || (i == 1 && j == 18) || (i == 17 && j == 18)) {
                            this.qPP++;
                            PowerPellets b = new PowerPellets("powerPellet.png",i,j);
                            b.setTransposable(true);
                            this.fase[i][j] = b;
                        } else {
                            this.fase[i][j]= new PacDots("pacdot.png",i,j);
                            this.qPP++;
                        }
                    }
                }
            }
            /*if(i!=9  && (j!=9 || j!=10)){
                                    this.fase[i][j]= new PacDots("pacdot.png",i,j);
                                    this.qPP++;
                                }else{
                                    BackGround b = new BackGround("background.png",i,j);
                                    b.setTransposable(true);
                                    this.fase[i][j] = b;*/
        }catch(Exception e) {
            throw new PacManException("DEU MERDA NOS PACDOTS ");
        }
        
        //VIDAS DO PACMAN
        try {
            PacMan p = (PacMan)this.lista.get(0);
            switch(p.getLives()) {
                case 5:
                    this.fase[19][0] = new BackGround("pacmanDireita2.png", 19, 0);
                    this.fase[19][1] = new BackGround("pacmanDireita.png", 19, 1);
                    this.fase[19][2] = new BackGround("pacmanDireita2.png", 19, 2);
                    this.fase[19][3] = new BackGround("pacmanDireita.png", 19, 3);
                    this.fase[19][4] = new BackGround("pacmanDireita2.png", 19, 4);
                    break;
                case 4:
                    this.fase[19][0] = new BackGround("pacmanDireita2.png", 19, 0);
                    this.fase[19][1] = new BackGround("pacmanDireita.png", 19, 1);
                    this.fase[19][2] = new BackGround("pacmanDireita2.png", 19, 2);
                    this.fase[19][3] = new BackGround("pacmanDireita.png", 19, 3);
                    break;
                case 3:
                    this.fase[19][0] = new BackGround("pacmanDireita2.png", 19, 0);
                    this.fase[19][1] = new BackGround("pacmanDireita.png", 19, 1);
                    this.fase[19][2] = new BackGround("pacmanDireita2.png", 19, 2);
                    break;
                case 2:
                    this.fase[19][0] = new BackGround("pacmanDireita2.png", 19, 0);
                    this.fase[19][1] = new BackGround("pacmanDireita.png", 19, 1);
                    break;
                case 1:
                    this.fase[19][0] = new BackGround("pacmanDireita2.png", 19, 0);
                    break;
            }
        } catch(Exception e) {
            throw new PacManException("DEU MERDA NAS VIDA DELE");
        }
   }
}
