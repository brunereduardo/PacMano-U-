/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bruner Eduardo
 */
public class MyTempoThread  implements Serializable{

    public int seg;
    public int min;
    public int hor;
    public int tot;
    
    public MyTempoThread() {
        this.hor = 0;
        this.min = 0;
        this.seg = 0;
        this.atualizaTimer();
    }

    public int getSeg() {
        return seg;
    }

    public void setSeg(int seg) {
        this.seg = seg;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getHor() {
        return hor;
    }

    public void setHor(int hor) {
        this.hor = hor;
    }

    public int getTot() {
        return tot;
    }

    public void setTot(int tot) {
        this.tot = tot;
    }
    
     public void atualizaTimer() {
        this.seg= this.getTot()%60;
        this.min = (int)(this.getTot()/60);
        this.hor = (int)(this.getMin()/60);
    }
     
    public MyTempoThread(int hora, int minuto, int segundo) {
        this.setTot((hora*60*60)+(minuto*60)+segundo);
        this.atualizaTimer();
    }
    
    public void incrementaSeg() {
        this.setTot(this.tot+1);
        
        this.atualizaTimer();
    }
    public void incrementaMin(){
        this.setTot(this.min+60);
        this.atualizaTimer();
    }
    public void incrementaHor(){
        this.setTot(this.hor+3600);
        this.atualizaTimer();
    }
    public boolean acabou(){
        return this.getTot()==0;
    }
    public void count() {
        if(this.getMin() == 60){
            this.setMin(0);
            this.incrementaHor();
        }if( this.getSeg() <60){
            this.incrementaSeg();
           
        }if((this.getSeg() == 60)){
            this.setSeg(0);
            this.incrementaMin();
        }
    }
    
    @Override
    public String toString() {
  
            this.count();
        
        String mAux, sAux, hAux;
        if (this.getSeg()<10) {
            sAux= "0"+this.getSeg();
        } else {
            sAux= ""+this.getSeg();
        }
        if (this.getMin()<10) {
            mAux= "0"+ this.getMin();
        } else {
            mAux=""+this.getMin();
        }
        if (this.getHor()< 10) {
            hAux="0"+this.getHor();
        } else {
            hAux=""+this.getHor();
        }
        return hAux+":"+mAux+":"+sAux;
    }

    
    
    }
    

