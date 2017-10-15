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
    public static byte NoT = 0;
    public static final Tile text_tile = new Tile("nebula-space");
    
    private byte ID;
    private String Texture;
    
    public Tile( String texture){
        ID = NoT;
        NoT++;
        Texture = texture;
        if(tiles[ID] != null )
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
