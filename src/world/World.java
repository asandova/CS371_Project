/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import io.Window;
import org.joml.*;
import render.Camera;
import render.Shader;

/**
 *
 * @author August's PC
 */
public class World {
    
    private final int view = 16;
    private byte[] tiles;
    private int width;
    private int height;
    private Matrix4f world;
    private int scale;
    
    public World(){
        width = 8;
        height = 8;
        scale = 128;
        
        tiles = new byte[width * height];
        world = new Matrix4f().setTranslation(new Vector3f(0));
        world.scale(scale);
    }
    
    public void render(TileRenderer render, Shader shader, Camera cam, Window window){
        int posX = ((int)cam.getPosition().x + (window.getWidth()/2)) / (scale *2);
        int posY = ((int)cam.getPosition().y - (window.getHeight()/2)) / (scale *2);
        
        for(int i = 0; i < view; i++){
            for(int j = 0; j < view; j++){
                Tile t = getTile(i - posX, j+posY);
                if(t != null)
                    render.renderTile(t, i-posX, -j-posY, shader, world, cam);
            }
        }
    }
    
    public void correctCamera(Camera camera, Window window){
        Vector3f pos = camera.getPosition();
        
        int w = - width * scale * 2;
        int h = height * scale * 2;
    
        if(pos.x > -(window.getWidth()/2)+scale)
            pos.x = -(window.getWidth()/2)+scale;
        if(pos.x < w + (window.getWidth()/2)+scale)
            pos.x = w + (window.getWidth()/2)+scale;
        if(pos.y < (window.getHeight()/2)-scale)
            pos.y = (window.getHeight()/2)-scale;
        if(pos.y > h - (window.getHeight()/2)-scale)
            pos.y = h - (window.getHeight()/2)-scale;
    }
    
    public void setTile(Tile tile, int x, int y){
        tiles[x *y *width] = tile.getID();
    
    }
    public Tile getTile(int x, int y){
        try{
            return Tile.tiles[tiles[x+y * width]];
        }catch(ArrayIndexOutOfBoundsException e){
            return null;
        }
    }

    public int getScale() { return scale; }
}
