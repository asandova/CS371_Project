/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package render;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

/**
 *
 * @author August's PC
 * NOTE: That the texture class cannot load a texture that has diametions that
 * are not a power of two or square
 */
public class Texture {
    private int id;
    private int width;
    private int height;
    
    public Texture(String filename){
        BufferedImage bi;
        try{
            bi = ImageIO.read(new File("./res/" +filename));
            width = bi.getWidth();
            height = bi.getHeight();
            int[] pixels_raw = new int[width * height * 4];
            pixels_raw = bi.getRGB(0,0,width,height, null, 0, width);
            
            ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);
            
            for(int i = 0; i < width; i++){
                for(int j = 0; j < height; j++){
                    int pixel = pixels_raw[i*width + j];
                    pixels.put( (byte) ((pixel >> 16) & 0xFF ) ); //Red
                    pixels.put( (byte) ((pixel >> 8) & 0xFF ) ); // Grean
                    pixels.put( (byte) (pixel & 0xFF ) ); // Blue
                    pixels.put( (byte) ((pixel >> 24) & 0xFF ) ); //Alpha
                }
            }
            
            pixels.flip();
            
            id = glGenTextures();
            
            glBindTexture(GL_TEXTURE_2D, id);
            
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
            
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }
    
    public void bind(int sampler){
        if(sampler >= 0 && sampler <= 31){
            glActiveTexture(GL_TEXTURE0 + sampler);
            glBindTexture(GL_TEXTURE_2D, id);
        }
    }
    
}
