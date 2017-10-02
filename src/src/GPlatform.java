/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;
import org.joml.Matrix4f;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.*;
/**
 * 
 * @author August B. Sandoval
 */
public class GPlatform {
    public static void main(String[] args){
        
        if( !glfwInit() ){
            throw new IllegalStateException("glfw failed to initalize!");
        }
        
        //glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        
        Window win = new Window();
        win.createWindow("CS372 Group Project");
        
        
        GL.createCapabilities();
        glEnable(GL_TEXTURE_2D);
        
        //Rect test = new Rect(0.5f,-0.5f,-0.5f,0.5f,1f,1f,1f,1f);
        float[] vertices = new float[]{
            -0.5f, 0.5f,0,
             0.5f, 0.5f,0,
             0.5f,-0.5f,0,
            -0.5f,-0.5f,0
            };
        
        float[] texture = new float[]{
            0,0,
            1,0,
            1,1,
            0,1
        };
        
        int[] indices = new int[]{
            0,1,2,
            2,3,0
        };
        
        Model model = new Model(vertices, texture, indices);
        Texture tex = new Texture("./res/doge_1.jpg");
        Shader shader = new Shader("shader");
        
        Matrix4f projection = new Matrix4f().ortho2D(-640/2, 640/2, -480/2, 480/2);
        
        Matrix4f scale = new Matrix4f().scale(128);
        Matrix4f target = new Matrix4f();
        projection.mul(scale, target);
        
        while(!win.shouldClose()){
            if(glfwGetKey(win.getWindow(),GLFW_KEY_ESCAPE ) == GL_TRUE ){
                break;
            }
            glfwPollEvents();
            
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            

            shader.bind();
            shader.setUniform("sampler", 0);
            shader.setUniform("projection", target);
            tex.bind(0);
            model.render();
            
            win.swapBuffers();
        }
        
        glfwTerminate();
    }
}