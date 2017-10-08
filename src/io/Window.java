/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

/**
 *
 * @author August's PC
 */
public class Window {
    private long Window;
    private int Width, Height;
    private boolean FullScreen;
    private Input input;
    
    public Window(){
        setSize(640,480);
        setFullScreen(false);
    }
    
    public void createWindow(String title){
        Window = glfwCreateWindow(Width,Height, title, FullScreen ? glfwGetPrimaryMonitor(): 0 ,0);
        
        if(Window == 0){
            throw new IllegalStateException("Failed to create window!");
        }
        if(!FullScreen){
            GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(Window, 
                            (videoMode.width()-Width)/2,
                            (videoMode.height() - Height )/2 );

            glfwShowWindow(Window);

            glfwMakeContextCurrent(Window);
            input = new Input(Window);
        }
    }
    
    public static void setCallbacks(){
        glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
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
    public void setFullScreen(boolean full){
        FullScreen = full;
    }
    
    public void update(){
        input.update();
        glfwPollEvents();
    }
    
    public boolean isFullScreen(){return FullScreen;}
    public long getWindow(){ return Window; }
    public int getWidth(){return Width;}
    public int getHeight(){return Height;}
    public Input getInput(){return input;}
}
