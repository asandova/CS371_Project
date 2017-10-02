/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWVidMode;

/**
 *
 * @author August's PC
 */
public class Window {
    private long Window;
    private int Width, Height;
    
    public Window(){
        setSize(640,480);
    }
    
    public void createWindow(String title){
        Window = glfwCreateWindow(Width,Height, title, 0,0);
        
        if(Window == 0){
            throw new IllegalStateException("Failed to create window!");
        }
        
        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(Window, 
                        (videoMode.width()-Width)/2,
                        (videoMode.height() - Height )/2 );
        
        glfwShowWindow(Window);
        
        glfwMakeContextCurrent(Window);
    }
    
    public boolean shouldClose(){
        
        return glfwWindowShouldClose(Window) ;
    }
    
    public void swapBuffers(){
        glfwSwapBuffers(Window);
    }
    
    public void setSize(int width, int height){
        Width = width;
        Height = height;
    }
    
    
    public long getWindow(){ return Window; }
    
    public int getWidth(){return Width;}
    public int getHeight(){return Height;}
    
}
