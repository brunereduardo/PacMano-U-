package control;

import elements.Element;
import elements.Fruit;
import elements.Ghost;
import elements.PacDots;
import elements.PacMan;
import elements.PowerPellets;
import elements.Stage;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import utils.PacManException;

/**
 * Projeto de POO 2017
 * 
 * @author Luiz Eduardo
 * Baseado em material do Prof. Jose Fernando Junior
 */
public class GameController implements Serializable{
    
    public void drawAllElements(Stage fase, Graphics g)throws PacManException{
        Element help;
        for(int i=0; i<20;i++){
            for(int j=0;j<20;j++){
                    fase.getFase(i, j).autoDraw(g);
                }
            
        }
    }
    
    //PACMAN
    public void processAllElements(Stage s) throws PacManException{
        
        for(int i=0;i<20;i++){
            for(int j=0;j<20;j++){
                if(s.getFase(i, j) == null){
                    return;
                }
            }
        }
        //if(e.isEmpty())
          //  return; 
        
        //PacMan lLolo = (PacMan)e.get(0);
        PacMan lLolo = (PacMan) s.getLista().get(0);

        if(s.getTipo() == 0){
            if(lLolo.getPosition().getX() == 6 && lLolo.getPosition().getY() == 0){
                throw new PacManException("TELETRANSPORTE (6,0) -> (6,19)!");

                }
            if(lLolo.getPosition().getX() == 6 && lLolo.getPosition().getY() == 19){
                throw new PacManException("TELETRANSPORTE (6,19) -> (6,0)!");
            }
        } else if(s.getTipo() == 1) {
            if(lLolo.getPosition().getX() == 9 && lLolo.getPosition().getY() == 0){
                throw new PacManException("TELETRANSPORTE (9,0) -> (9,19)!");

                }
            if(lLolo.getPosition().getX() == 9 && lLolo.getPosition().getY() == 19){
                throw new PacManException("TELETRANSPORTE (9,19) -> (9,0)!");
            }
        } else {
            if(lLolo.getPosition().getX() == 17 && lLolo.getPosition().getY() == 6){
                throw new PacManException("TELETRANSPORTE (17,6) -> (17,13)!");

                }
            if(lLolo.getPosition().getX() == 17 && lLolo.getPosition().getY() == 13){
                throw new PacManException("TELETRANSPORTE (17,13) -> (17,6)!");
            }
        }
   
        if (!isValidPosition(s, lLolo)) {
             lLolo.backToLastPosition();
             lLolo.setMovDirection(PacMan.STOP, s);
        }
        else{
       
            Element eTemp;
        
            for(int i = 0; i < 20; i++){
                for(int j = 0; j < 20; j++){
                    if(!(s.getFase(i, j) instanceof PacMan)){
                        eTemp = s.getFase(i, j);
                        if(lLolo.overlap(eTemp)){
                            if(eTemp.isTransposable()){
                                if(eTemp instanceof PacDots || eTemp instanceof PowerPellets){
                                    if(s.getTipo() == 0){
                                            if(s.getqPP() != 1){
                                            s.setqPP(s.getqPP() - 1);
                                            s.removeElement(eTemp);
                                            s.countPlacar(eTemp);

                                            }else{
                                                s.setqPP(s.getqPP() - 1);
                                                s.removeElement(eTemp);
                                                throw new PacManException("TERMINOU A FASE");
                                            }
                                    }else if(s.getTipo() == 1){
                                            if(s.getqPP() != 1){
                                            s.setqPP(s.getqPP() - 1);
                                            s.removeElement(eTemp);
                                            s.countPlacar(eTemp);

                                            }else{
                                                s.setqPP(s.getqPP() - 1);
                                                s.removeElement(eTemp);
                                                throw new PacManException("TERMINOU A FASE");
                                            }
                                    }else{
                                        if(s.getqPP() != 1){
                                            s.setqPP(s.getqPP() - 1);
                                            s.removeElement(eTemp);
                                            s.countPlacar(eTemp);

                                        }else{
                                            s.setqPP(s.getqPP() - 1);
                                            s.removeElement(eTemp);
                                            throw new PacManException("TERMINOU A FASE");
                                        }
                                    }
                                }
                                if(eTemp instanceof Fruit){
                                    s.countPlacar(eTemp);
                                    s.removeElement(eTemp);
                                }if(eTemp instanceof Ghost){
                                    if(eTemp.isIsMortal()){
                                        throw new PacManException("Perdeu uma vida");
                                    }else{
                                        //s.getLista().remove(eTemp);
                                        s.removeElement(eTemp);
                                        s.countPlacar(eTemp);
                                        
                                        //eTemp = null;
                                        
                                        //eTemp.changeImage("background.png");
                                    }
                                    //ainda arrumar
                                }
                            }
                        }
                    }
                }
            }
            /*
            for(int i = 1; i < e.size(); i++){
                eTemp = e.get(i);
                if(lLolo.overlap(eTemp))
                    if(eTemp.isTransposable())
                        e.remove(eTemp);
            }*/
            if(lLolo.getDirectionFlag() == true){
                lLolo.setMovDirection(lLolo.getNextDirection(), s);
            }
        
            lLolo.move();
        }
        //Processando Fantasmas
        for(int i = 1; i < s.getLista().size(); i++){
            processGhost((Ghost)s.getLista().get(i), lLolo, s);
        }
    }
    
    //FANTASMAS
    public void processGhost(Ghost g, PacMan pac, Stage s){
        
        if (!isValidPosition(s, g)) {
            g.backToLastPosition();
            //ESQUERDA -> DIREITA
            if(g.getMovDirection() == g.MOVE_LEFT) g.setMovDirection(g.MOVE_RIGHT);
            //DIREITA -> ESQUERDA
            else if(g.getMovDirection() == g.MOVE_RIGHT) g.setMovDirection(g.MOVE_LEFT);
            //CIMA -> BAIXO
            else if(g.getMovDirection() == g.MOVE_UP) g.setMovDirection(g.MOVE_DOWN);
            //BAIXO -> CIMA
            else if(g.getMovDirection() == g.MOVE_DOWN) g.setMovDirection(g.MOVE_UP);
            
            return;
        }
        
        //SAINDO DA TOCA
        if(g.getIsOut() == false) g.getOut(s);
        
        //JA SAIU DA TOCA
        else{
            g.selectGhostMove(pac, s);
            if(g.getDirectionFlag() == true){
                g.setMovDirection(g.getNextDirection(), s);
            }
            g.moveGhost();
        }
    }
    
    public boolean isValidPosition(Stage s , Element elem){
        Element elemAux;
        for(int i = 0; i < 20; i++){
            for(int j = 0; j < 20; j++){
                if((!(s.getFase(i, j) instanceof PacMan))&&(!(s.getFase(i, j) instanceof Ghost))){
                    elemAux = s.getFase(i, j);
                    if(!elemAux.isTransposable()){
                        if(elemAux.overlap(elem)){
                            return false;
                        }
                    }
                    //fantasmas no portal
                    if(elem instanceof Ghost && s.getTipo() == 0){
                        if((elem.getPosition().getX() == 6 && elem.getPosition().getY() == 0) ||
                            (elem.getPosition().getX() == 6 && elem.getPosition().getY() == 19)) return false;
                    }
                    else if(elem instanceof Ghost && s.getTipo() == 1){
                        if((elem.getPosition().getX() == 9 && elem.getPosition().getY() == 0) ||
                            (elem.getPosition().getX() == 9 && elem.getPosition().getY() == 19)) return false;
                        
                    }
                    else if(elem instanceof Ghost && s.getTipo() == 2){
                        if((elem.getPosition().getX() == 17 && elem.getPosition().getY() == 6) ||
                                (elem.getPosition().getX() == 17 && elem.getPosition().getY() == 13)) return false;
                    }
                }
            }
        }
        return true;
        
        /*
        for(int i = 1; i < elemArray.size(); i++){
            elemAux = elemArray.get(i);            
            if(!elemAux.isTransposable())
                if(elemAux.overlap(elem))
                    return false;
        }        
        return true;*/
    }
}
