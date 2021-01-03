/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Bruner Eduardo
 */
public class PacManException extends Exception{
    public int tag;
    public PacManException() {
    }

    public PacManException(String string) {
        super(string);
        if("TELETRANSPORTE (9,0) -> (9,19)!".equals(string)){
            this.tag = 1;
        }
        if("TELETRANSPORTE (9,19) -> (9,0)!".equals(string)){
            this.tag = 2;
        }
        if("TERMINOU A FASE".equals(string)){
            this.tag = 3;
        }
        if("Comeu um PowerPellet".equals(string)){
            this.tag = 4;
        }
        if("Ganha uma vida".equals(string)){
            this.tag = 5;
        }
        if("Perdeu uma vida".equals(string)){
            this.tag = 6;
        }
        if("TELETRANSPORTE (6,0) -> (6,19)!".equals(string)){
            this.tag = 7;
        }
        if("TELETRANSPORTE (6,19) -> (6,0)!".equals(string)){
            this.tag = 8;
        }
        if("TELETRANSPORTE (17,6) -> (17,13)!".equals(string)){
            this.tag = 9;
        }
        if("TELETRANSPORTE (17,13) -> (17,6)!".equals(string)){
            this.tag = 10;
        }if("Azul.png".equals(string)){
            this.tag = 11;
        }if("Laranja.png".equals(string)){
            this.tag = 12;
        }if("Rosa.png".equals(string)){
            this.tag = 13;
        }if("Vermelho.png".equals(string)){
            this.tag = 14;
        }if("cereja.png".equals(string)){
            this.tag = 15;
        }if("morango.png".equals(string)){
            this.tag = 16;
        }if("Back cherry".equals(string)){
            this.tag = 17;
        }if("Back strawberry".equals(string)){
            this.tag = 18;
        }if("Fantasma.png".equals(string)){
            this.tag = 19;
        }
    }

    @Override
    public String getMessage() {
        return super.getMessage(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
