/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import java.util.HashMap;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import render.Camera;
import render.Model;
import render.Shader;
import render.Texture;

/**
 *
 * @author August's PC
 */
public class TileRenderer {
    private HashMap<String, Texture> tile_textures;
    private Model model;
    
    public TileRenderer(){
        tile_textures = new HashMap<String, Texture>();
        float[] vertices = new float[]{
            -2f, 2f,0,
             2f, 2f,0,
             2f,-2f,0,
            -2f,-2f,0
            };
        
        float[] texture = new float[]{
            0,0,
            2,0,
            2,2,
            0,2
        };
        
        int[] indices = new int[]{
            0,1,2,
            2,3,0
        };
        model = new Model(vertices, texture, indices);
        
        for(int i = 0; i < Tile.tiles.length; i++){
            if(Tile.tiles[i]!= null){
                if(!tile_textures.containsKey(Tile.tiles[i].getTexture() ) ){
                    String tex = Tile.tiles[i].getTexture();
                    tile_textures.put(tex, new Texture(tex + ".png")  );
                }
            }
        }
    }
    
    public void renderTile(Tile tile, int x, int y ,Shader shader, Matrix4f world, Camera cam){
        shader.bind();
        if(tile_textures.containsKey(tile.getTexture() )  ){
            tile_textures.get(tile.getTexture()).bind(0);
        }
        Matrix4f tile_pos = new Matrix4f().translate(new Vector3f(x*2,y*2,0) );
        Matrix4f target = new Matrix4f();
        
        cam.getProjection().mul(world,target);
        target.mul(tile_pos);
        
        shader.setUniform("sampler", 0);
        shader.setUniform("projection", target);
        
        model.render();
    }
    
}
