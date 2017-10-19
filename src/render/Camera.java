/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package render;

import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 *
 * @author augus
 */
public class Camera {
    private Vector3f position;
    private Matrix4f projection;

    public Camera(int width, int height){
        position = new Vector3f(0,0,0);
        setProjection(width, height);
    }
    
    public void setProjection(int width, int height){
        projection = new Matrix4f().setOrtho2D(-width/2, width/2,-height/2, height/2);
    }
    
    public void setPosition(Vector3f pos){
        position = pos;
    }
    public void addPositon(Vector3f pos){
        position.add(pos);
    }
    public Vector3f getPosition(){return position;}
    
    public Matrix4f getProjection(){
        return projection.translate(position, new Matrix4f());
    }
}
