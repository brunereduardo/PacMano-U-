package control;

import elements.BackGround;
import elements.PacMan;
import elements.Element;
import elements.Fruit;
import elements.Ghost;
import elements.PacDots;
import elements.Stage;
import utils.Consts;
import utils.Drawing;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.PacManException;

/**
 * Projeto de POO 2017
 * 
 * @author Luiz Eduardo
 * Baseado em material do Prof. Jose Fernando Junior
 */
public class GameScreen extends javax.swing.JFrame implements KeyListener, Serializable {
    
    private  PacMan lolo;
    private Ghost azul, laranja, rosa, vermelho;
    private Stage level;
    //private  ArrayList<Element> elemArray;
    private final GameController controller = new GameController();
    private int u , auxC, auxM;
    private int helpC, helpM, v;
    
    private MyTempoThread mine;

    public GameScreen() {
        Drawing.setGameScreen(this);
        initComponents();
        this.u = 0;
        this.mine = new MyTempoThread();
        this.auxC=this.auxM=0;
        this.addKeyListener(this);   /*teclado*/
        
        /*Cria a janela do tamanho do tabuleiro + insets (bordas) da janela*/
        this.setSize(Consts.NUM_CELLS * Consts.CELL_SIZE + getInsets().left + getInsets().right,
                     Consts.NUM_CELLS * Consts.CELL_SIZE + getInsets().top + getInsets().bottom);

        this.lolo = new PacMan("PacmanDireita2.png",1,1);
        this.azul = new Ghost("Azul.png",9,7);
        this.laranja = new Ghost("Laranja.png",9,8);
        this.rosa = new Ghost("Rosa.png",9,11);
        this.vermelho = new Ghost("Vermelho.png",9,12);
        
        /*Cria e adiciona elementos*/        
        try{
            this.level= new Stage(u , this.lolo, this.vermelho, this.rosa, this.azul, this.laranja, this.mine);
        }catch(Exception e){
            System.out.println(e.getStackTrace());
        }
        
    }
    
  
    @Override
    public void paint(Graphics gOld) {
        Graphics g = getBufferStrategy().getDrawGraphics();
        
        /*Criamos um contexto grafico*/
        Graphics g2 = g.create(getInsets().right, getInsets().top, getWidth() - getInsets().left, getHeight() - getInsets().bottom);
        
        /* DESENHA CENARIO
           Trocar essa parte por uma estrutura mais bem organizada
           Utilizando a classe Stage
        */
        for (int i = 0; i < Consts.NUM_CELLS; i++) {
            for (int j = 0; j < Consts.NUM_CELLS; j++) {
                try {
                    Image newImage = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Consts.PATH + "background.png");
                    g2.drawImage(newImage,
                            j * Consts.CELL_SIZE, i * Consts.CELL_SIZE, Consts.CELL_SIZE, Consts.CELL_SIZE, null);
                    
                } catch (IOException ex) {
                    Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        try {
            this.controller.drawAllElements(this.level, g2);
            this.controller.processAllElements(this.level);
            this.changeThings();
        } catch (PacManException ex) {
            if(ex.tag == 1){
                this.lolo.setPosition(9, 18);
                this.level.getLista().get(0).setPosition(9, 18);
            }if(ex.tag == 2){
                this.lolo.setPosition(9, 1);
                this.level.getLista().get(0).setPosition(9, 1);
            }if(ex.tag == 3){
                // terminou uma fase
                if (this.u == 0) {
                    System.out.println(u);
                    try {
                        this.u++;
                        this.lolo = new PacMan("PacmanDireita2.png",1,1);
                        this.azul = new Ghost("Azul.png",9,7);
                        this.laranja = new Ghost("Laranja.png",9,8);
                        this.rosa = new Ghost("Rosa.png",9,11);
                        this.vermelho = new Ghost("Vermelho.png",9,12);
                        this.level = null;
                        this.level = new Stage(1, this.lolo, this.azul, this.laranja, this.rosa, this.vermelho,this.mine);
                    } catch (PacManException ex1) {
                        System.out.println(ex1.getMessage());
                    } catch(Exception e) {
                        System.out.println(e.getMessage());
                    }
                }else if (this.u == 1) {
                    try {
                        this.u++;
                        this.lolo = new PacMan("PacmanDireita2.png",1,1);
                        this.azul = new Ghost("Azul.png",9,7);
                        this.laranja = new Ghost("Laranja.png",9,8);
                        this.rosa = new Ghost("Rosa.png",9,11);
                        this.vermelho = new Ghost("Vermelho.png",9,12);
                        this.level = null;
                        this.level = new Stage(2, this.lolo, this.azul, this.laranja, this.rosa, this.vermelho,this.mine);
                    } catch (PacManException ex1) {
                        System.out.println(ex1.getMessage());
                    }catch(Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.exit(1);
                }
            }if(ex.tag == 4){
                this.azul.setState(true);
                this.laranja.setState(true);
                this.rosa.setState(true);
                this.vermelho.setState(true);
                v = this.mine.getSeg()+7;
                    
            }if(ex.tag == 5){
                this.lolo.setLives(this.lolo.getLives() +1);
                changingLives(this.level, this.lolo);
            }if(ex.tag == 6){
                if(this.lolo.getLives()!=1){
                    this.lolo.setLives(this.lolo.getLives() -1);
                    this.lolo.setPosition(1, 1);
                    changingLives(this.level, this.lolo);
                }else{
                    this.lolo.setLives(this.lolo.getLives() -1);
                    System.out.println("VOCE PERDEU O JOGO");
                    System.exit(0);
                }
            }if(ex.tag == 7){
                this.lolo.setPosition(6, 18.9);
                this.level.getLista().get(0).setPosition(6, 18.9);
            }if(ex.tag == 8){
                this.lolo.setPosition(6, 0.1);
                this.level.getLista().get(0).setPosition(6, 0.1);
            }if(ex.tag == 9){
                this.lolo.setPosition(17, 13.1);
                this.level.getLista().get(0).setPosition(17, 13.1);
            }if(ex.tag == 10){
                this.lolo.setPosition(17, 5.9);
                this.level.getLista().get(0).setPosition(17, 5.9);
            }if(ex.tag == 11){
                this.azul.setPosition(9, 11);
                this.azul.setIsOut(false);
                this.azul.setOutCounter(-1);
            }if(ex.tag == 12){
                this.laranja.setPosition(9, 12);
                this.laranja.setIsOut(false);
                this.laranja.setOutCounter(-1);
            }if(ex.tag == 13){
              this.rosa.setPosition(9, 8);
              this.rosa.setIsOut(false);
              this.rosa.setOutCounter(-1);
            }if(ex.tag == 14){
               this.vermelho.setPosition(9, 7);
               this.vermelho.setIsOut(false);
               this.vermelho.setOutCounter(-1);
            }if(ex.tag == 15){
               this.auxC =1;
                this.helpC = this.mine.getSeg()+15;
                Fruit fit = new Fruit(ex.getMessage());
                this.level.setFruit(fit);
            }if(ex.tag == 16){
                this.auxM=1;
                this.helpM = this.mine.getSeg()+15;
                Fruit fit = new Fruit(ex.getMessage());
                this.level.setFruit(fit);
            }if(ex.tag == 17){
               this.auxC =0;
               this.helpC = 0;
               if(!(this.level.getCesta().isEmpty())){
                    BackGround b = new BackGround("background.png",(int) this.level.getCesta().get(0).getPosition().getX() , (int)this.level.getCesta().get(0).getPosition().getY());
                    b.setTransposable(true);
                    this.level.getCesta().remove(0);
                    this.level.setFaseElement((int)b.getPosition().getX(), (int) b.getPosition().getY(), b);
               }
            }if(ex.tag == 18){
               this.auxM=0;
               this.helpM=0;
               if(!(this.level.getCesta().isEmpty())){
                    BackGround b = new BackGround("background.png",(int) this.level.getCesta().get(0).getPosition().getX() , (int)this.level.getCesta().get(0).getPosition().getY());
                    b.setTransposable(true);
                    this.level.getCesta().remove(0);
                    this.level.setFaseElement((int)b.getPosition().getX(), (int) b.getPosition().getY(), b);
               }
            }if(ex.tag == 19){
                this.azul.setState(false);
                this.laranja.setState(false);
                this.rosa.setState(false);
                this.vermelho.setState(false);
                v = 0;
            }
            
        }
        this.setTitle("-> Cell: " + lolo.getStringPosition() + "    SCORE: " + this.level.getPlacar() + "    FASE: " + (this.level.getTipo() + 1));
        
        g.dispose();
        g2.dispose();
        if (!getBufferStrategy().contentsLost()) {
            getBufferStrategy().show();
        }
    }
    
    public void go() {
        TimerTask task = new TimerTask() {
            
            public void run() {
                repaint();
                // colocar o timer aqui 
            }
        };
        TimerTask taskTwo = new TimerTask() {
            @Override
            public void run() {
                reRun();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, Consts.DELAY);
        timer.schedule(taskTwo, 0, 1000);
    }   
    
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            lolo.setMovDirection(PacMan.MOVE_UP, level);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            lolo.setMovDirection(PacMan.MOVE_DOWN, level);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            lolo.setMovDirection(PacMan.MOVE_LEFT, level);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            lolo.setMovDirection(PacMan.MOVE_RIGHT, level);
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            lolo.setMovDirection(PacMan.STOP, level);
        }else if (e.getKeyCode() == (KeyEvent.VK_E)) {//save
            ObjectOutputStream out = null;
            try {
                out = new ObjectOutputStream(new FileOutputStream("save.txt"));
                out.writeObject(this.level);
            }catch(IOException f){
                System.err.println(Arrays.toString(f.getStackTrace()));
            }finally {
                try {
                    out.close();
                } catch (IOException ex) {
                    System.err.println(Arrays.toString(ex.getStackTrace()));
                }
            }
        }
        else if (e.getKeyCode() == (KeyEvent.VK_L)) {//load
            ObjectInputStream in = null;
            if(this.u == 0) {
                try {
                    in = new ObjectInputStream(new FileInputStream("save.txt"));
                    this.level = (Stage) in.readObject();
                    this.lolo= (PacMan)this.level.getLista().get(0);
                    this.azul= (Ghost)this.level.getLista().get(1);
                    this.laranja= (Ghost)this.level.getLista().get(2);
                    this.rosa= (Ghost)this.level.getLista().get(3);
                    this.vermelho= (Ghost)this.level.getLista().get(4);
                    this.u = this.level.getTipo();
                } catch (IOException ex) {
                    Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
        }
        
        //repaint(); /*invoca o paint imediatamente, sem aguardar o refresh*/
    }
    public void reRun(){
        System.out.println(this.mine.toString());
    }
    public void changeThings() throws PacManException{
        //fantasmas primeiro - 7segundos
        if(this.mine.getSeg() == this.v){
            throw new PacManException("Fantasma.png");
        }
        // tempo da cereja, pois aparece antes do morango 
        if(this.mine.getSeg() == this.helpC){
            throw new PacManException("Back cherry");
        }
        // tempo do morango, pois aparece depois da cereja 
        if(this.mine.getSeg() == this.helpM){
            throw new PacManException("Back strawberry");
        }
        // tempo da cereja
        if(this.mine.getSeg() == 50 && this.auxC !=1){
            throw new PacManException("cereja.png");
        }// tempo do morango
        if((this.mine.getSeg() == 15)&&(this.mine.getMin() ==1)&& this.auxM !=1){
            throw new PacManException("morango.png");
        }
    }
    
    public void changingLives(Stage s, PacMan p) {
        switch(p.getLives()) {
            case 5:
                s.setFaseElement(19, 0, new BackGround("pacmanDireita.png", 19, 0));
                s.setFaseElement(19, 1, new BackGround("pacmanDireita2.png", 19, 1));
                s.setFaseElement(19, 2, new BackGround("pacmanDireita.png", 19, 2));
                s.setFaseElement(19, 3, new BackGround("pacmanDireita2.png", 19, 3));
                s.setFaseElement(19, 4, new BackGround("pacmanDireita.png", 19, 4));
                break;
            case 4:
                s.setFaseElement(19, 0, new BackGround("pacmanDireita.png", 19, 0));
                s.setFaseElement(19, 1, new BackGround("pacmanDireita2.png", 19, 1));
                s.setFaseElement(19, 2, new BackGround("pacmanDireita.png", 19, 2));
                s.setFaseElement(19, 3, new BackGround("pacmanDireita2.png", 19, 3));
                s.setFaseElement(19, 4, new BackGround("background.png", 19, 4));
                break;
            case 3:
                s.setFaseElement(19, 0, new BackGround("pacmanDireita.png", 19, 0));
                s.setFaseElement(19, 1, new BackGround("pacmanDireita2.png", 19, 1));
                s.setFaseElement(19, 2, new BackGround("pacmanDireita.png", 19, 2));
                s.setFaseElement(19, 3, new BackGround("background.png", 19, 3));
                s.setFaseElement(19, 4, new BackGround("background.png", 19, 4));
                break;
            case 2:
                s.setFaseElement(19, 0, new BackGround("pacmanDireita.png", 19, 0));
                s.setFaseElement(19, 1, new BackGround("pacmanDireita2.png", 19, 1));
                s.setFaseElement(19, 2, new BackGround("background.png", 19, 2));
                s.setFaseElement(19, 3, new BackGround("background.png", 19, 3));
                s.setFaseElement(19, 4, new BackGround("background.png", 19, 4));;
                break;
            case 1:
                s.setFaseElement(19, 0, new BackGround("pacmanDireita.png", 19, 0));
                s.setFaseElement(19, 1, new BackGround("background.png", 19, 1));
                s.setFaseElement(19, 2, new BackGround("background.png", 19, 2));
                s.setFaseElement(19, 3, new BackGround("background.png", 19, 3));
                s.setFaseElement(19, 4, new BackGround("background.png", 19, 4));
                break;
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SCC0604 - Pacman");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocation(new java.awt.Point(20, 20));
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
