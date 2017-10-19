/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import org.joml.*;
import render.Camera;
import render.Shader;

/**
 *
 * @author August's PC
 */
public class World {
    private byte[] tiles;
    private int width;
    private int height;
    private Matrix4f world;
    
    public World(){
        width = 16;
        height = 16;
        
        tiles = new byte[width * height];
        world = new Matrix4f().setTranslation(new Vector3f(0));
        world.scale(256);
    }
    
    public void render(TileRenderer render, Shader shader, Camera cam){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                render.renderTile(tiles[i + j * width], j,-i,shader, world,cam);
            }
        }
    }
}
