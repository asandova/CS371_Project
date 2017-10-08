/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;
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
//        float[] BGverts = new float[]{
//            -2f, 2f ,0,
//             2f, 2f ,0,
//             2f,-2f ,0,
//            -2f,-2f ,0
//        };
        int s = 256;
        float[] BGverts = new float[]{
            -(float)win.getWidth()/s, (float)win.getHeight()/s ,0,
             (float)win.getWidth()/s, (float)win.getHeight()/s ,0,
             (float)win.getWidth()/s,-(float)win.getHeight()/s ,0,
            -(float)win.getWidth()/s,-(float)win.getHeight()/s ,0
        };


        TileRenderer tiles = new TileRenderer();
        
        
//        Model BG = new Model(BGverts,texture,indices);
//        Texture BGtex = new Texture("./res/HalcyonPlanet.jpg");
//        BG.setTexture(BGtex);
//        
//        Model model = new Model(vertices, texture, indices);
        Texture tex = new Texture("./res/doge_1.jpg");
//        model.setTexture(tex);
        Shader shader = new Shader("shader");
        
        //Matrix4f projection = new Matrix4f().ortho2D(-640/2, 640/2, -480/2, 480/2);
        
        Matrix4f scale = new Matrix4f().translate(new Vector3f(0,0,0)).scale(32);
        Matrix4f target = new Matrix4f();
        camera.setPosition(new Vector3f(-100,0,0));
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
                target = scale;
                if(win.getInput().isKeyPressed(GLFW_KEY_ESCAPE)){             
                    glfwSetWindowShouldClose(win.getWindow(), true );
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
                
                //BGtex.bind(0);
               // BG.display();
                for(int i=0; i < 8; i++){
                    for(int j=0; j < 8; j++){
                        tiles.renderTile((byte)0, i, j, shader, scale, camera);
                    }
                }
                //tex.bind(0);
                //model.display();

                win.swapBuffers();
                frames++;
            }

        }
        
        glfwTerminate();
    }
}