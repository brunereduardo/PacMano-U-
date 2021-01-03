package elements;

import utils.Drawing;
import java.awt.Graphics;
import java.io.Serializable;

/**
 * Projeto de POO 2017
 * 
 * @author Luiz Eduardo
 * Baseado em material do Prof. Jose Fernando Junior
 */
public class PacMan extends Element  implements Serializable{
    
    public static final int STOP = 0;
    public static final int MOVE_LEFT = 1;
    public static final int MOVE_RIGHT = 2;
    public static final int MOVE_UP = 3;
    public static final int MOVE_DOWN = 4;
    private static int lives = 3;
    
    private int movDirection = STOP;
    private int nextDirection = STOP;
    private boolean directionFlag = false;
    
    public PacMan(String imageName) {
        super(imageName);
        super.flag = 11;
    }

    public PacMan(String imageName, int i, int j) {
        super(imageName, i, j);
        super.flag = 11;
    }
    
    public boolean getDirectionFlag(){
        return this.directionFlag;
    }
    
    public int getNextDirection(){
        return this.nextDirection;
    }
    
    @Override
    public void autoDraw(Graphics g){
        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
    }

    public void backToLastPosition(){
        this.pos.comeBack();
    }
    
    public void setMovDirection(int direction, Stage s) {
        if(posIsInt(this) && nextIsValid(s, this, direction)){
                movDirection = direction;
                directionFlag = false;
        }
        else{
            nextDirection = direction;
            directionFlag = true;
        }
    }
    
    public void move() {
        switch (movDirection) {
            case MOVE_LEFT:
                this.alternativeImage("PacmanEsquerda.png","PacmanEsquerda2.png");
                this.moveLeft();
                break;
            case MOVE_RIGHT:
                this.alternativeImage("PacmanDireita.png","PacmanDireita2.png");
                this.moveRight();
                break;
            case MOVE_UP:
                alternativeImage("PacmanCima.png","PacmanCima2.png");
                this.moveUp();
                break;
            case MOVE_DOWN:
                this.alternativeImage("PacmanBaixo.png","PacmanBaixo2.png");
                this.moveDown();
                break;
            default:
                break;
        }
    }

    @Override
    public void alternativeImage(String imageName1, String imageName2) {
        this.flag++;
        if (flag == 12){
            this.changeImage(imageName1);
            flag = 0;
        } else if (flag == 6) this.changeImage(imageName2);
    
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
    
    public boolean posIsInt(Element e){
        if((e.getPosition().getX() - (int)e.getPosition().getX() == 0) &&
            (e.getPosition().getY() - (int)e.getPosition().getY() == 0))
                return true;
        
        return false;
    }
    
    public boolean nextIsValid(Stage s, Element elem, int next){
        double X = elem.getPosition().getX();
        double Y = elem.getPosition().getY();
        
        //esquerda
        if(next == 1 && s.getFase((int)X, (int)(Y -1)).isTransposable()) return true;
        
        //direita
        else if(next == 2 && s.getFase((int)X, (int)(Y +1)).isTransposable()) return true;
            
        //cima
        else if(next == 3 && s.getFase((int)(X -1), (int)Y).isTransposable()) return true;
            
        //baixo
        else if(next == 4 && s.getFase((int)(X +1), (int)Y).isTransposable()) return true;
        
        //parado
        else if(next == 0) return true;
        
        return false;
    }
}