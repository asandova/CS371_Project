/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

/**
 *
 * @author August's PC
 */
public class Tile {
    public static Tile tiles[] = new Tile[16];
    
    public static final Tile text_tile = new Tile((byte)0,"nebula-space");
    
    private byte ID;
    private String Texture;
    
    public Tile(byte id, String texture){
        ID = id;
        Texture = texture;
        if(tiles[id] != null )
            throw new IllegalStateException("Tiles at [" + ID + "] is already being used!");
        tiles[ID] = this;
    }
    
    public byte getID(){
        return ID;
    }
    public String getTexture(){
        return Texture;
    }
    
}
