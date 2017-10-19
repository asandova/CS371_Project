/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;
import Entity.Player;
import render.Texture;
import render.Camera;
import render.Shader;
import render.Model;
import io.Timer;
import io.Window;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.*;
import world.TileRenderer;
import world.World;
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
        System.out.println(win.getWidth() + " " + win.getHeight() );
        
        int s = 256;
        float[] BGverts = new float[]{
            -(float)win.getWidth()/s, (float)win.getHeight()/s ,0,
             (float)win.getWidth()/s, (float)win.getHeight()/s ,0,
             (float)win.getWidth()/s,-(float)win.getHeight()/s ,0,
            -(float)win.getWidth()/s,-(float)win.getHeight()/s ,0
        };


        TileRenderer tiles = new TileRenderer();
        
        Shader shader = new Shader("shader");
       /* 
        float[] vertices1 = new float[]{
            -1f, 1f,0, 
             1f, 1f,0,
             1f,-1f,0,
            -1f,-1f,0
            };
        
        float[] texture1 = new float[]{
            0,0,
            1,0,
            1,1,
            0,1
        };
        
        int[] indices = new int[]{
            0,1,2,
            2,3,0
        };
        */
        
        
        
        World world = new World();
        
        Player player = new Player();
        //Player title = new Player();
        //title.setModel(vertices1, texture1, indices);
        //title.setTexture(new Texture("TestTitle.jpg") );

        
        int xp=-720;
        int yp=260;
        camera.setPosition(new Vector3f(xp,yp,0));
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
                if(win.hasResized()){
                    camera.setProjection(win.getWidth(), win.getHeight());
                    glViewport(0,0,win.getWidth(), win.getHeight());
                }
                
                unprocessed -= frame_cap;
                can_render = true;
                //target = scale;
                if(win.getInput().isKeyPressed(GLFW_KEY_ESCAPE)){             
                    glfwSetWindowShouldClose(win.getWindow(), true );
                }
                if(win.getInput().isKeyDown(GLFW_KEY_LEFT)){
                    camera.getPosition().add(new Vector3f(1,0,0));
                }
                if(win.getInput().isKeyDown(GLFW_KEY_RIGHT)){
                    camera.getPosition().add(new Vector3f(-1,0,0));
                }
                if(win.getInput().isKeyDown(GLFW_KEY_UP)){
                    camera.getPosition().add(new Vector3f(0,-1,0));
                }
                if(win.getInput().isKeyDown(GLFW_KEY_DOWN)){
                    camera.getPosition().add(new Vector3f(0,1,0));
                }
                //System.out.println("x: "+xp+" yp: "+yp);
                
                player.update((float)frame_cap, win, camera, world);
                
                //title.update((float)frame_cap, win, camera, world);
                
                world.correctCamera(camera, win);
                
                win.update();
                if(frames_time >= 1.0){
                    frames_time =0;
                        System.out.println("FPS: " + frames);
                        frames =0;
                }
            }
            if(can_render){
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//                shader.bind();
//                shader.setUniform("sampler", 0);
//                shader.setUniform("projection", camera.getProjection().mul(target));
                
                //BGtex.bind(0);
               // BG.display();
                world.render(tiles, shader,camera, win);
                //tex.bind(0);
                //model.display();
                //title.render(shader, camera);
                player.render(shader, camera);
                win.swapBuffers();
                frames++;
            }

        }
        
        glfwTerminate();
    }
}