/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package render;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import src.Renderable;


/**
 *
 * @author August's PC
 */
public class Model implements Renderable{
    
    private int draw_count;
    private int v_id;
    private int t_id;
    private int i_id;
    
    
    private float L; //Left most location
    private float R; //Right most location
    private float T; //Top most location
    private float B; //Bottom most location
    
    private Texture Tex;
//    private float Red;
//    private float Green;
//    private float Blue;
//    private float Alpha;
    public Model(float vertices[], float[] tex_coords, int[] indices){
        draw_count = indices.length;
        
        v_id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, v_id);
        glBufferData(GL_ARRAY_BUFFER,createBuffer(vertices), GL_STATIC_DRAW);
        
        t_id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, t_id);
        glBufferData(GL_ARRAY_BUFFER,createBuffer(tex_coords), GL_STATIC_DRAW);
        
        i_id = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,i_id);
        IntBuffer buffer = BufferUtils.createIntBuffer(indices.length);
        buffer.put(indices);
        buffer.flip();
        
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,buffer,GL_STATIC_DRAW);
       
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
    
    private FloatBuffer createBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
    
    public void setTexture(String filename){
        Tex = new Texture(filename);
    }
    
    public void setTexture(Texture tex){
        Tex = tex;
    }
    
    @Override
    public void render(){
         glEnableVertexAttribArray(0);
         glEnableVertexAttribArray(1);
        
        glBindBuffer(GL_ARRAY_BUFFER, v_id);
        
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        
        glBindBuffer(GL_ARRAY_BUFFER, t_id);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, i_id);
        
        glDrawElements(GL_TRIANGLES, draw_count, GL_UNSIGNED_INT, 0 );
        
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
        glBindBuffer(GL_ARRAY_BUFFER,0);
        
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

    }
}
