package elements;

import java.awt.Graphics;
import java.io.Serializable;
import static utils.Consts.WALK_STEP;
import utils.Drawing;

public class Ghost extends Element implements Serializable{
    
    private final String name;
    private static final float D = 8;
    public static final int STOP = 0;
    public static final int MOVE_LEFT = 1;
    public static final int MOVE_RIGHT = 2;
    public static final int MOVE_UP = 3;
    public static final int MOVE_DOWN = 4;
    
    private int movDirection = 0; //------------||direcao de movimento
    private int nextDirection = 0; //-----------||prox direcao de movimento
    private int outCounter = 0; //--------------||cont para sair da toca
    private boolean directionFlag = false; //---||indica se ha uma prox direcao
    private boolean isOut = false; //-----------||indica se ja sairam da toca
    private int movAnt = STOP; //---------------||indica qual foi o movimento anterior
    public static int imagesFlag = 5; //-------||mudanÃ§a de imagens
    
    //desenhar o fantasma
    public Ghost(String imageName) {
        super(imageName);
        super.isMortal = true;
        this.name = imageName;
        this.flag = 0;
    }
    public Ghost(String imageName, int i, int j) {
        super(imageName, i, j);
        super.isMortal = true;
        this.name = imageName;
        this.flag = 0;
    }
    @Override
    public void autoDraw(Graphics g) {
        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
    }

    @Override
    public void alternativeImage(String imageName1, String imageName2) {
        this.imagesFlag++;
        if (imagesFlag == 6){
            this.changeImage(imageName1);
            imagesFlag = 0;
        } else if (imagesFlag == 3) this.changeImage(imageName2);
    }

    @Override
    public void changeImage(String imageName) {
        super.changeImage(imageName); //To change body of generated methods, choose Tools | Templates.
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setState(boolean state) {
        if(state){
            this.isMortal= false;
            this.changeImage("FantasmaNaBad.png");
            this.flag = 1;
            
        }else {
            this.isMortal = true;
            this.changeImage(this.getName());
            this.flag = 0;
        }
    }
    
    public int getMovDirection(){
        return this.movDirection;
    }
    public int getNextDirection(){
        return this.nextDirection;
    }
    public boolean getDirectionFlag(){
        return this.directionFlag;
    }
    
    public void setMovAnd(int mov){
        this.movAnt = mov;;
    }
    
    public void setOutCounter(int V){
        this.outCounter = V;
    }
    
    public boolean getIsOut(){
        return this.isOut;
    }
    public void setIsOut(boolean o){
        this.isOut = o;
    }
    
    //retorna um valor aleatorio de
    //  zero ateh um valor limite
    public int random(int limite){
        return ((int)(Math.random() * limite));
    }
    
    public boolean setMovDirection(int direction, Stage s) {
        if(posIsInt(this) && nextIsValid(s, this, direction)){
                movDirection = direction;
                directionFlag = false;
                return true;
        }
        else{
            nextDirection = direction;
            directionFlag = true;
            return false;
        }
    }
    
    public void setMovDirection(int direction) {
        movDirection = direction;
    }
    
    public void moveGhost(){
        if(this.flag == 0){
            if(movDirection == MOVE_LEFT){
                    this.moveLeft();
                    this.movAnt = MOVE_LEFT;
                    this.alternativeImage("1Esquerda" + this.getName(), "2Esquerda" + this.getName());
            } else if(movDirection == MOVE_RIGHT){
                    this.moveRight();
                    this.movAnt = MOVE_RIGHT;
                    this.alternativeImage("1Direita" + this.getName(), "2Direita" + this.getName());
            } else if(movDirection == MOVE_UP){
                    this.moveUp();
                    this.movAnt = MOVE_UP;
                    this.alternativeImage("1Direita" + this.getName(), "2Direita" + this.getName());
            } else if(movDirection == MOVE_DOWN){
                    this.moveDown();
                    this.movAnt = MOVE_DOWN;
                    this.alternativeImage("1Baixo" + this.getName(), "2Baixo" + this.getName());
            }
        } else{
            if(movDirection == MOVE_LEFT){
                    this.moveRight();
                    this.movAnt = MOVE_LEFT;
                    this.alternativeImage("1naBad.png", "2naBad.png");
            } else if(movDirection == MOVE_RIGHT){
                    this.moveLeft();
                    this.movAnt = MOVE_RIGHT;
                    this.alternativeImage("1naBad.png", "2naBad.png");
            } else if(movDirection == MOVE_UP){
                    this.moveDown();
                    this.movAnt = MOVE_UP;
                    this.alternativeImage("1naBad.png", "2naBad.png");
            } else if(movDirection == MOVE_DOWN){
                    this.moveUp();
                    this.movAnt = MOVE_DOWN;
                    this.alternativeImage("1naBad.png", "2naBad.png");
            }
        }
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
        
        //movimento padrao
        if(this.flag == 0){
            //esquerda
            if(next == MOVE_LEFT && s.getFase((int)X, (int)(Y -1)).isTransposable()) return true;
            //direita
            else if(next == MOVE_RIGHT && s.getFase((int)X, (int)(Y +1)).isTransposable()) return true;
            //cima
            else if(next == MOVE_UP && s.getFase((int)(X -1), (int)Y).isTransposable()) return true;
            //baixo
            else if(next == MOVE_DOWN && s.getFase((int)(X +1), (int)Y).isTransposable()) return true;
            //parado
            else if(next == STOP) return true;
        }
        //movimento invertido
        else{
            //esquerda
            if(next == MOVE_LEFT && s.getFase((int)X, (int)(Y +1)).isTransposable()) return true;
            //direita
            else if(next == MOVE_RIGHT && s.getFase((int)X, (int)(Y -1)).isTransposable()) return true;
            //cima
            else if(next == MOVE_UP && s.getFase((int)(X +1), (int)Y).isTransposable()) return true;
            //baixo
            else if(next == MOVE_DOWN && s.getFase((int)(X -1), (int)Y).isTransposable()) return true;
            //parado
            else if(next == STOP) return true;            
        }
        
        return false;
    }  
    
    //pitagoras
    public double calculaDistancia(double X, double Y){
        return Math.sqrt((double)(Math.pow(Y, 2) + Math.pow(X, 2)));
    }
    
    public void selectGhostMove(PacMan pac, Stage s){
        //Blinky 182
        if("Vermelho.png".equals(this.getName())){
            //distancia em mÃ³sudlo do Vermelho pro pacMan
            double Ya = Math.abs(this.getPosition().getY() - pac.getPosition().getY());
            double Xa = Math.abs(this.getPosition().getX() - pac.getPosition().getX());
            //distancia real do Vermelho pro pacMan
            double Yb = this.getPosition().getY() - pac.getPosition().getY();
            double Xb = this.getPosition().getX() - pac.getPosition().getX();
            
            //Mover Horizontal
            if(calculaDistancia(Xa, Ya - WALK_STEP) < calculaDistancia(Xa - WALK_STEP, Ya)){
                //PacMan esta a esquerda
                if(Yb > 0){
                    if(nextIsValid(s, this, MOVE_LEFT) && this.movAnt != MOVE_RIGHT) this.setMovDirection(MOVE_LEFT, s);
                    //PacMan esta acima
                    else if(Xb > 0){
                        if(nextIsValid(s, this, MOVE_UP) && this.movAnt != MOVE_DOWN) this.setMovDirection(MOVE_UP, s);
                        else if(nextIsValid(s, this, MOVE_DOWN) && this.movAnt != MOVE_UP) this.setMovDirection(MOVE_DOWN, s);
                        else this.setMovDirection(MOVE_RIGHT, s);
                    }
                    //PacMan esta abaixo
                    else{
                        if(nextIsValid(s, this, MOVE_DOWN) && this.movAnt != MOVE_UP) this.setMovDirection(MOVE_DOWN, s);
                        else if(nextIsValid(s, this, MOVE_UP) && this.movAnt != MOVE_DOWN) this.setMovDirection(MOVE_UP, s);
                        else this.setMovDirection(MOVE_RIGHT, s);
                    }
                }
                //PacMan esta a direita
                else{
                    if(nextIsValid(s, this, MOVE_RIGHT) && this.movAnt != MOVE_LEFT) this.setMovDirection(MOVE_RIGHT, s);
                    //PacMan esta acima
                    else if(Xb > 0){
                        if(nextIsValid(s, this, MOVE_UP) && this.movAnt != MOVE_DOWN) this.setMovDirection(MOVE_UP, s);
                        else if(nextIsValid(s, this, MOVE_DOWN) && this.movAnt != MOVE_UP) this.setMovDirection(MOVE_DOWN, s);
                        else this.setMovDirection(MOVE_LEFT, s);
                    }
                    //PacMan esta abaixo
                    else{
                        if(nextIsValid(s, this, MOVE_DOWN) && this.movAnt != MOVE_UP) this.setMovDirection(MOVE_DOWN, s);
                        else if(nextIsValid(s, this, MOVE_UP) && this.movAnt != MOVE_DOWN) this.setMovDirection(MOVE_UP, s);
                        else this.setMovDirection(MOVE_LEFT, s);
                    }
                }
            }
            //Mover Vertical
            else{
                //PacMan esta acima
                if(Xb > 0){
                    if(nextIsValid(s, this, MOVE_UP) && this.movAnt != MOVE_DOWN) this.setMovDirection(MOVE_UP, s);
                    //PacMan esta a esquerda
                    else if(Yb > 0){
                        if(nextIsValid(s, this, MOVE_LEFT) && this.movAnt != MOVE_RIGHT) this.setMovDirection(MOVE_LEFT, s);
                        else if(nextIsValid(s, this, MOVE_RIGHT) && this.movAnt != MOVE_LEFT) this.setMovDirection(MOVE_RIGHT, s);
                        else this.setMovDirection(MOVE_DOWN, s);
                    }
                    //PacMan esta a direita
                    else{
                        if(nextIsValid(s, this, MOVE_RIGHT) && this.movAnt != MOVE_LEFT) this.setMovDirection(MOVE_RIGHT, s);
                        else if(nextIsValid(s, this, MOVE_LEFT) && this.movAnt != MOVE_RIGHT) this.setMovDirection(MOVE_LEFT, s);
                        else this.setMovDirection(MOVE_DOWN, s);
                    }
                }
                //PacMan esta abaixo
                else{
                    if(nextIsValid(s, this, MOVE_DOWN) && this.movAnt != MOVE_UP) this.setMovDirection(MOVE_DOWN, s);
                    //PacMan esta a esquerda
                    else if(Yb > 0){
                        if(nextIsValid(s, this, MOVE_LEFT) && this.movAnt != MOVE_RIGHT) this.setMovDirection(MOVE_LEFT, s);
                        else if(nextIsValid(s, this, MOVE_RIGHT) && this.movAnt != MOVE_LEFT) this.setMovDirection(MOVE_RIGHT, s);
                        else this.setMovDirection(MOVE_UP, s);
                    }
                    //PacMan esta a direita
                    else{
                        if(nextIsValid(s, this, MOVE_RIGHT) && this.movAnt != MOVE_LEFT) this.setMovDirection(MOVE_RIGHT, s);
                        else if(nextIsValid(s, this, MOVE_LEFT) && this.movAnt != MOVE_RIGHT) this.setMovDirection(MOVE_LEFT, s);
                        else this.setMovDirection(MOVE_UP, s);
                    }
                }
            }
        }

        //Pinky Floid
        else if("Rosa.png".equals(this.getName())){
            double Xa = this.getPosition().getX() - pac.getPosition().getX();
            int R = random(9);
            
            //pacMan esta acima
            if(Xa > 0){
                if(R > 1 && nextIsValid(s, this, MOVE_UP)) this.setMovDirection(MOVE_UP, s);
                //aleatÃ³rio
                else{
                    //direita
                    if(R == 0 && this.movAnt != MOVE_LEFT) this.setMovDirection(MOVE_RIGHT, s);
                    //esquerda
                    else if (R == 1 && this.movAnt != MOVE_RIGHT) this.setMovDirection(MOVE_LEFT, s);
                }
            }
            //pacMan esta abaixo
            else if(Xa < 0){
                if(R > 1 && nextIsValid(s, this, MOVE_DOWN)) this.setMovDirection(MOVE_DOWN, s);
                //aleatorio
                else{
                    //direita
                    if(R == 0 && this.movAnt != MOVE_LEFT) this.setMovDirection(MOVE_RIGHT, s);
                    //esquerda
                    else if (R == 1 && this.movAnt != MOVE_RIGHT) this.setMovDirection(MOVE_LEFT, s);
                }
            }
        }
        
        //Inkysition
        else if("Azul.png".equals(this.getName())){
            Ghost red = null;
            
            for(int i = 1; i < s.getLista().size(); i++){
                if(isTheRed((Ghost)s.getLista().get(i))){
                    red = (Ghost)s.getLista().get(i);
                }
            }
            
            //vermelho ta morto
            if(red == null) movRandom(s);
            
            //vermelho ta vivo
            else{    
                //distancia do Azul para o Vermelho
                double Xa = Math.abs(this.getPosition().getX() - red.getPosition().getX());
                double Ya = Math.abs(this.getPosition().getY() - red.getPosition().getY());
            
                //distancia do Azul para o Pacman
                double Xb = this.getPosition().getX() - pac.getPosition().getX();
                double Yb = this.getPosition().getY() - pac.getPosition().getY();
            
                //movimento aleatÃ³rio
                if(Xa > D || Ya > D) movRandom(s);
                
                //imita o Vermelho
                else{
                    //Mover Horizontal
                    if(calculaDistancia(Xa, Ya - WALK_STEP) < calculaDistancia(Xa - WALK_STEP, Ya)){
                        //PacMan esta a esquerda
                        if(Yb > 0){
                            if(nextIsValid(s, this, MOVE_LEFT) && this.movAnt != MOVE_RIGHT) this.setMovDirection(MOVE_LEFT, s);
                            //PacMan esta acima
                            else if(Xb > 0){
                                if(nextIsValid(s, this, MOVE_UP) && this.movAnt != MOVE_DOWN) this.setMovDirection(MOVE_UP, s);
                                else if(nextIsValid(s, this, MOVE_DOWN) && this.movAnt != MOVE_UP) this.setMovDirection(MOVE_DOWN, s);
                                else this.setMovDirection(MOVE_RIGHT, s);
                            }
                            //PacMan esta abaixo
                            else{
                                if(nextIsValid(s, this, MOVE_DOWN) && this.movAnt != MOVE_UP) this.setMovDirection(MOVE_DOWN, s);
                                else if(nextIsValid(s, this, MOVE_UP) && this.movAnt != MOVE_DOWN) this.setMovDirection(MOVE_UP, s);
                                else this.setMovDirection(MOVE_RIGHT, s);
                            }
                        }
                        //PacMan esta a direita
                        else{
                            if(nextIsValid(s, this, MOVE_RIGHT) && this.movAnt != MOVE_LEFT) this.setMovDirection(MOVE_RIGHT, s);
                            //PacMan esta acima
                            else if(Xb > 0){
                                if(nextIsValid(s, this, MOVE_UP) && this.movAnt != MOVE_DOWN) this.setMovDirection(MOVE_UP, s);
                                else if(nextIsValid(s, this, MOVE_DOWN) && this.movAnt != MOVE_UP) this.setMovDirection(MOVE_DOWN, s);
                                else this.setMovDirection(MOVE_LEFT, s);
                            }
                            //PacMan esta abaixo
                            else{
                                if(nextIsValid(s, this, MOVE_DOWN) && this.movAnt != MOVE_UP) this.setMovDirection(MOVE_DOWN, s);
                                else if(nextIsValid(s, this, MOVE_UP) && this.movAnt != MOVE_DOWN) this.setMovDirection(MOVE_UP, s);
                                else this.setMovDirection(MOVE_LEFT, s);
                            }
                        }
                    }
                    //Mover Vertical
                    else{
                        //PacMan esta acima
                        if(Xb > 0){
                            if(nextIsValid(s, this, MOVE_UP) && this.movAnt != MOVE_DOWN) this.setMovDirection(MOVE_UP, s);
                            //PacMan esta a esquerda
                            else if(Yb > 0){
                                if(nextIsValid(s, this, MOVE_LEFT) && this.movAnt != MOVE_RIGHT) this.setMovDirection(MOVE_LEFT, s);
                                else if(nextIsValid(s, this, MOVE_RIGHT) && this.movAnt != MOVE_LEFT) this.setMovDirection(MOVE_RIGHT, s);
                                else this.setMovDirection(MOVE_DOWN, s);
                            }
                            //PacMan esta a direita
                            else{
                                if(nextIsValid(s, this, MOVE_RIGHT) && this.movAnt != MOVE_LEFT) this.setMovDirection(MOVE_RIGHT, s);
                                else if(nextIsValid(s, this, MOVE_LEFT) && this.movAnt != MOVE_RIGHT) this.setMovDirection(MOVE_LEFT, s);
                                else this.setMovDirection(MOVE_DOWN, s);
                            }
                        }
                        //PacMan esta abaixo
                        else{
                            if(nextIsValid(s, this, MOVE_DOWN) && this.movAnt != MOVE_UP) this.setMovDirection(MOVE_DOWN, s);
                            //PacMan esta a esquerda
                            else if(Yb > 0){
                                if(nextIsValid(s, this, MOVE_LEFT) && this.movAnt != MOVE_RIGHT) this.setMovDirection(MOVE_LEFT, s);
                                else if(nextIsValid(s, this, MOVE_RIGHT) && this.movAnt != MOVE_LEFT) this.setMovDirection(MOVE_RIGHT, s);
                                else this.setMovDirection(MOVE_UP, s);
                            }
                            //PacMan esta a direita
                            else{
                                if(nextIsValid(s, this, MOVE_RIGHT) && this.movAnt != MOVE_LEFT) this.setMovDirection(MOVE_RIGHT, s);
                                else if(nextIsValid(s, this, MOVE_LEFT) && this.movAnt != MOVE_RIGHT) this.setMovDirection(MOVE_LEFT, s);
                                else this.setMovDirection(MOVE_UP, s);
                            }
                        }
                    }
                }
            }
        }
        //Clyderson -> Laranja
        else{
            //distancia em modulo do Laranja para o Pacman
            double Xa = Math.abs(this.getPosition().getX() - pac.getPosition().getX());
            double Ya = Math.abs(this.getPosition().getY() - pac.getPosition().getX());
            
            //distancia real do Laranja para o Pacman
            double Xb = this.getPosition().getX() - pac.getPosition().getX();
            double Yb = this.getPosition().getY() - pac.getPosition().getX();
            
            //imita o Vermelho
            if(Xa > D && Ya > D){
                //Mover Horizontal
                if(calculaDistancia(Xa, Ya - WALK_STEP) < calculaDistancia(Xa - WALK_STEP, Ya)){
                    //PacMan esta a esquerda
                    if(Yb > 0){
                        if(nextIsValid(s, this, MOVE_LEFT) && this.movAnt != MOVE_RIGHT) this.setMovDirection(MOVE_LEFT, s);
                        //PacMan esta acima
                        else if(Xb > 0){
                            if(nextIsValid(s, this, MOVE_UP) && this.movAnt != MOVE_DOWN) this.setMovDirection(MOVE_UP, s);
                            else if(nextIsValid(s, this, MOVE_DOWN) && this.movAnt != MOVE_UP) this.setMovDirection(MOVE_DOWN, s);
                            else this.setMovDirection(MOVE_RIGHT, s);
                        }
                        //PacMan esta abaixo
                        else{
                            if(nextIsValid(s, this, MOVE_DOWN) && this.movAnt != MOVE_UP) this.setMovDirection(MOVE_DOWN, s);
                            else if(nextIsValid(s, this, MOVE_UP) && this.movAnt != MOVE_DOWN) this.setMovDirection(MOVE_UP, s);
                            else this.setMovDirection(MOVE_RIGHT, s);
                        }
                    }
                    //PacMan esta a direita
                    else{
                        if(nextIsValid(s, this, MOVE_RIGHT) && this.movAnt != MOVE_LEFT) this.setMovDirection(MOVE_RIGHT, s);
                        //PacMan esta acima
                        else if(Xb > 0){
                            if(nextIsValid(s, this, MOVE_UP) && this.movAnt != MOVE_DOWN) this.setMovDirection(MOVE_UP, s);
                            else if(nextIsValid(s, this, MOVE_DOWN) && this.movAnt != MOVE_UP) this.setMovDirection(MOVE_DOWN, s);
                            else this.setMovDirection(MOVE_LEFT, s);
                        }
                        //PacMan esta abaixo
                        else{
                            if(nextIsValid(s, this, MOVE_DOWN) && this.movAnt != MOVE_UP) this.setMovDirection(MOVE_DOWN, s);
                            else if(nextIsValid(s, this, MOVE_UP) && this.movAnt != MOVE_DOWN) this.setMovDirection(MOVE_UP, s);
                            else this.setMovDirection(MOVE_LEFT, s);
                            }
                    }
                }
                //Mover Vertical
                else{
                    //PacMan esta acima
                    if(Xb > 0){
                        if(nextIsValid(s, this, MOVE_UP) && this.movAnt != MOVE_DOWN) this.setMovDirection(MOVE_UP, s);
                        //PacMan esta a esquerda
                        else if(Yb > 0){
                            if(nextIsValid(s, this, MOVE_LEFT) && this.movAnt != MOVE_RIGHT) this.setMovDirection(MOVE_LEFT, s);
                            else if(nextIsValid(s, this, MOVE_RIGHT) && this.movAnt != MOVE_LEFT) this.setMovDirection(MOVE_RIGHT, s);
                            else this.setMovDirection(MOVE_DOWN, s);
                        }
                        //PacMan esta a direita
                        else{
                            if(nextIsValid(s, this, MOVE_RIGHT) && this.movAnt != MOVE_LEFT) this.setMovDirection(MOVE_RIGHT, s);
                            else if(nextIsValid(s, this, MOVE_LEFT) && this.movAnt != MOVE_RIGHT) this.setMovDirection(MOVE_LEFT, s);
                            else this.setMovDirection(MOVE_DOWN, s);
                        }
                    }
                    //PacMan esta abaixo
                    else{
                        if(nextIsValid(s, this, MOVE_DOWN) && this.movAnt != MOVE_UP) this.setMovDirection(MOVE_DOWN, s);
                        //PacMan esta a esquerda
                        else if(Yb > 0){
                            if(nextIsValid(s, this, MOVE_LEFT) && this.movAnt != MOVE_RIGHT) this.setMovDirection(MOVE_LEFT, s);
                            else if(nextIsValid(s, this, MOVE_RIGHT) && this.movAnt != MOVE_LEFT) this.setMovDirection(MOVE_RIGHT, s);
                            else this.setMovDirection(MOVE_UP, s);
                        }
                        //PacMan esta a direita
                        else{
                            if(nextIsValid(s, this, MOVE_RIGHT) && this.movAnt != MOVE_LEFT) this.setMovDirection(MOVE_RIGHT, s);
                            else if(nextIsValid(s, this, MOVE_LEFT) && this.movAnt != MOVE_RIGHT) this.setMovDirection(MOVE_LEFT, s);
                            else this.setMovDirection(MOVE_UP, s);
                        }
                    }
                }
            }
            //Movimento AleatÃ³rio
            else movRandom(s); 
        }
    }

    public void backToLastPosition() {
        this.pos.comeBack();
    }
    
    public void movRandom(Stage s){
        boolean F = false;
            
        while(F == false){
            int R = random(4);
                    
            //CIMA
            if(R == 0){
                if(this.movAnt != MOVE_DOWN){
                    this.setMovDirection(MOVE_UP, s);
                    F = true;
                }
            }
            //DOWN
            else if(R == 1){
                if(this.movAnt != MOVE_UP){
                    this.setMovDirection(MOVE_DOWN, s);
                    F = true;
                }
            }
            //LEFT
            else if(R == 2){
                if(this.movAnt != MOVE_RIGHT){
                    this.setMovDirection(MOVE_LEFT, s);
                    F = true;
                }
            }
            //RIGHT
            else{
                if(this.movAnt != MOVE_LEFT){
                    this.setMovDirection(MOVE_RIGHT, s);
                    F = true;
                }
            }
        }  
    }
    
    public boolean isTheRed(Ghost g){
        if("Vermelho.png".equals(g.getName())) return true;
        return false;
    }
    
    public void getOut(Stage s){
        //PASSO ZERO
        if(this.outCounter == -1){
            if("Vermelho.png".equals(this.getName())){
                if(this.getPosition().getY() != 12) this.moveRight();
                else this.outCounter++;
            }
            else if("Rosa.png".equals(this.getName())){
                if(this.getPosition().getY() != 12) this.moveRight();
                else this.outCounter++;
            }
            else if("Azul.png".equals(this.getName())){
                if(this.getPosition().getY() != 7) this.moveLeft();
                else this.outCounter++;           
            }
            else if("Laranja.png".equals(this.getName())){
                if(this.getPosition().getY() != 7) this.moveLeft();
                else this.outCounter++;            
            }
        }
        //PASSO1
        if(this.outCounter == 0){
            if("Vermelho.png".equals(this.getName())){
                if(this.getPosition().getY() != 11) this.moveLeft();
                else this.outCounter++;
            }
            else if("Rosa.png".equals(this.getName())){
                if(this.getPosition().getY() != 10) this.moveLeft();
                else this.outCounter++;
            }
            else if("Azul.png".equals(this.getName())){
                if(this.getPosition().getY() != 8) this.moveRight();
                else this.outCounter++;           
            }
            else if("Laranja.png".equals(this.getName())){
                if(this.getPosition().getY() != 9) this.moveRight();
                else this.outCounter++;            
            }
        }
        //PASSO 2
        else if(this.outCounter == 1){
            if("Vermelho.png".equals(this.getName())){
                if(this.getPosition().getY() != 10) this.moveLeft();
                else this.outCounter++;
            }
            else if ("Rosa.png".equals(this.getName())) {
                if (this.getPosition().getX() != 8) this.moveUp();
                else this.outCounter++;    
            }
            else if ("Azul.png".equals(this.getName())) {
                if (this.getPosition().getY() != 9) this.moveRight();
                else this.outCounter++;    
            }
            else if ("Laranja.png".equals(this.getName())) {
                if (this.getPosition().getX() != 8) this.moveUp();
                else this.outCounter++;
            }
        }
        //PASSO 3
        else if(this.outCounter == 2){
            if("Vermelho.png".equals(this.getName())){
                if(this.getPosition().getX() != 8) this.moveUp();
                else this.outCounter++;
            }
            else if("Rosa.png".equals(this.getName())){
                if(this.getPosition().getX() != 7) this.moveUp();
                else this.outCounter++;
            }
            else if("Azul.png".equals(this.getName())){
                if(this.getPosition().getX() != 8) this.moveUp();
                else this.outCounter++;            
            }
            else if("Laranja.png".equals(this.getName())){
                if(this.getPosition().getX() != 7) this.moveUp();
                else this.outCounter++;            
            }
        }
        //PASSO 4
        else if(this.outCounter == 3){
            if("Vermelho.png".equals(this.getName())){
                if(this.getPosition().getX() != 7) this.moveUp();
                else this.outCounter++;
            }
            else if("Rosa.png".equals(this.getName())){
                if(this.getPosition().getY() != 11) this.moveRight();
                else this.isOut = true;
            }
            else if("Azul.png".equals(this.getName())){
                if(this.getPosition().getX() != 7) this.moveUp();
                else this.outCounter++;           
            }
            else if("Laranja.png".equals(this.getName())){
                if(this.getPosition().getY() != 8) this.moveLeft();
                else this.isOut = true;            
            }    
        }
        //PASSO 5
        else if(this.outCounter == 4){
            if("Vermelho.png".equals(this.getName())){
                if(this.getPosition().getY() != 5) this.moveLeft();
                else this.isOut = true;
            }
            else if("Azul.png".equals(this.getName())){
                if(this.getPosition().getY() != 14) this.moveRight();
                else this.isOut = true;
            }
        }
    }
}