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
        Window.setCallbacks();
        
        if( !glfwInit() ){
            throw new IllegalStateException("glfw failed to initalize!");
        }
        
        //glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        
        Window win = new Window();
        win.createWindow("Much game. So show off. wow!");
        
        
        GL.createCapabilities();
        glEnable(GL_TEXTURE_2D);
        
        Camera camera = new Camera(win.getWidth(), win.getHeight());
        

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
        
        //Matrix4f projection = new Matrix4f().ortho2D(-640/2, 640/2, -480/2, 480/2);
        
        Matrix4f scale = new Matrix4f().scale(128);
        Matrix4f target = new Matrix4f();
        //projection.mul(scale, target);
        
        double frame_cap = 1.0/60.0; //limits to 60 FPS
        double time = Timer.getTime();
        double unprocessed = 0;
        double frames_time = 0;
        int frames = 0;
        while(!win.shouldClose()){
            boolean can_render = false;
            double time_2 = Timer.getTime();
            double passed = time_2 - time;
            unprocessed += passed;
            frames_time += passed;
            time = time_2;
            
            while(unprocessed >= frame_cap){
                unprocessed -= frame_cap;
                can_render = true;
                target = scale;
                if(win.getInput().isKeyPressed(GLFW_KEY_ESCAPE)){             
                    break;
                }
                win.update();
                if(frames_time >= 1.0){
                    frames_time =0;
                        System.out.println("FPS: " + frames);
                        frames =0;
                }
            }
            if(can_render){
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                shader.bind();
                shader.setUniform("sampler", 0);
                shader.setUniform("projection", camera.getProjection().mul(target));
                tex.bind(0);
                model.render();

                win.swapBuffers();
                frames++;
            }

        }
        
        glfwTerminate();
    }
}