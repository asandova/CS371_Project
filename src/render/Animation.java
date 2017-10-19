/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package render;

import io.Timer;

/**
 *
 * @author August's PC
 */
public class Animation {
    private Texture[] frames;
    private int pointer;
    
    private double elapsedTime;
    private double currentTime;
    private double lastTime;
    private double FPS;

    public Animation(int amount, int fps,String filename){
        pointer = 0;
        elapsedTime = 0;
        currentTime = 0;
        lastTime = Timer.getTime();
        FPS = 1.0/(double)fps;
        frames = new Texture[amount];
        for(int i = 0; i < amount; i++){
            frames[i] = new Texture("anim/" + filename + "_" + i+ ".png");
        }
    }
    
    public void bind(){ bind(0);}
    
    public void bind(int sampler){
        currentTime = Timer.getTime();
        elapsedTime += currentTime - lastTime;    
        
        if(elapsedTime >= FPS){
            elapsedTime -=FPS;
            pointer++;
        }
        
        if(pointer >= frames.length){
            pointer = 0;
        }
        
        lastTime = currentTime;
        
        frames[pointer].bind(sampler);
    }
}
