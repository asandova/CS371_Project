/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

/**
 *
 * @author August's PC
 */
public class Window {
    private long Window;
    private int Width, Height;
    private boolean FullScreen;
    private Input input;
    private boolean hasResized;
    private GLFWWindowSizeCallback windowSizeCallback;
    
    public Window(){
        //640,480
        setSize(640,640);
        setFullScreen(false);
        hasResized = false;
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
            setLocalCallbacks();
        }
    }
    
    public void cleanUp(){
        windowSizeCallback.close();
    }
    
    public static void setCallbacks(){
        glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
    }
    
    private void setLocalCallbacks(){
        windowSizeCallback = new GLFWWindowSizeCallback(){
            @Override
            public void invoke(long argWindow, int argWidth, int argHeight){
                Width = argWidth;
                Height = argHeight;
                hasResized = true;
            }
        };
        glfwSetWindowSizeCallback(Window, windowSizeCallback);
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
        hasResized = false;
        input.update();
        glfwPollEvents();
    }
    
    public boolean isFullScreen(){return FullScreen;}
    public long getWindow(){ return Window; }
    public int getWidth(){return Width;}
    public int getHeight(){return Height;}
    public Input getInput(){return input;}
    public boolean hasResized(){return hasResized;}
}
