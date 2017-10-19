/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import io.Window;
import org.joml.Vector3f;
import static org.lwjgl.glfw.GLFW.*;
import render.Camera;
import render.Model;
import render.Shader;
import render.Texture;
import world.World;

/**
 *
 * @author August's PC
 */
public class Player {
 
    private Model model;
    private Texture tex;
    //private Animation animation
    private Transform transform;
    
    public Player(){
        float[] vertices = new float[]{
            -1f, 1f,0,
             1f, 1f,0,
             1f,-1f,0,
            -1f,-1f,0
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
        
        model = new Model(vertices,texture,indices);
        tex = new Texture("doge_1.jpg");
        //text = new Animation(1,15, "doge");
        transform = new Transform();
        transform.scale = new Vector3f(64,64,1);
    }
    public void setTexture(Texture nTex){
        tex = nTex;
    }
    public void setModel(float[] verts, float[] texture, int[] indices){
        model = new Model(verts, texture, indices);
    }
    
    public void update(float delta, Window window, Camera camera, World world){
                 if(window.getInput().isKeyDown(GLFW_KEY_A)){
                    transform.pos.add(new Vector3f(-10 * delta,0,0));
                }
                if(window.getInput().isKeyDown(GLFW_KEY_D)){
                    transform.pos.add(new Vector3f(10 * delta,0,0));
                }
                if(window.getInput().isKeyDown(GLFW_KEY_W)){
                    transform.pos.add(new Vector3f(0,10 * delta,0));
                }
                if(window.getInput().isKeyDown(GLFW_KEY_S)){
                    transform.pos.add(new Vector3f(0,-10 * delta,0));
                }
                
                camera.setPosition(transform.pos.mul(-world.getScale(), new Vector3f() ));
    }
    
    public void render(Shader shader, Camera camera){
        shader.bind();
        shader.setUniform("samper", 0);
        shader.setUniform("projection",transform.getProjection( camera.getProjection() ) );
        tex.bind(0);
        model.render();
    }
}
