import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

public  class Mesh {

    public FloatBuffer vertices;
    public FloatBuffer normals;
    public FloatBuffer texCoords;
    public FloatBuffer diffuseBf;
    public FloatBuffer ambientBf;
    public FloatBuffer specularBf;
    public float illum;
    public float transperacy;
    public int amountFaces;
    public Vector3f position;
    public Vector3f scale;
    public  Vector3f rotation;
    public  Vector3f observer;
    public float shininess;
    ArrayList<Vertex> vertexArrayList;

    public int numVertices;

    public Mesh(FloatBuffer vertices, FloatBuffer normals, FloatBuffer texCoords, int numVertices, FloatBuffer diffuseBf, FloatBuffer ambientBf, FloatBuffer specularBf,
                float transperacy, float shininess, ArrayList<Vertex> vertexArrayList, int amountFaces, Vector3f observer) {
        this.vertices = vertices;
        this.normals = normals;
        this.texCoords = texCoords;
        this.numVertices = numVertices;
        this.diffuseBf = diffuseBf;
        this.ambientBf = ambientBf;
        this.specularBf = specularBf;
        this.transperacy = transperacy;
        this.shininess = shininess;
        this.position = new Vector3f(0, 0, 0);
        this.scale = new Vector3f(0, 0, 0);
        this.rotation = new Vector3f(0, 0, 0);
        this.vertexArrayList = vertexArrayList;
        this.amountFaces = amountFaces;
        this.observer = observer;
    }



    public Mesh(FloatBuffer vertices, FloatBuffer normals, int numVertices, FloatBuffer diffuseBf, FloatBuffer ambientBf, FloatBuffer specularBf,
                float transperacy, float shininess, ArrayList<Vertex> vertexArrayList, int amountFaces, Vector3f observer) {
        this.vertices = vertices;
        this.normals = normals;
        this.numVertices = numVertices;
        this.diffuseBf = diffuseBf;
        this.ambientBf = ambientBf;
        this.specularBf = specularBf;
        this.transperacy = transperacy;
        this.shininess = shininess;
        this.position = new Vector3f(0, 0, 0);
        this.scale = new Vector3f(0, 0, 0);
        this.rotation = new Vector3f(0, 0, 0);
        this.vertexArrayList = vertexArrayList;
        this.amountFaces = amountFaces;
        this.observer = observer;
    }



    //Constructor without textures
    public Mesh(FloatBuffer vertices, FloatBuffer normals, int numVertices, FloatBuffer diffuseBf,
                FloatBuffer ambientBf, FloatBuffer specularBf, float transperacy, float shininess) {
        this.vertices = vertices;
        this.normals = normals;
        this.numVertices = numVertices;
        this.diffuseBf = diffuseBf;
        this.ambientBf = ambientBf;
        this.specularBf = specularBf;
        this.transperacy = transperacy;
        this.position = new Vector3f(0, 0, 0);
        this.scale = new Vector3f(0,0,0);
        this.rotation = new Vector3f(0, 0, 0);
        this.shininess = shininess;
    }

    public Mesh(Vector3f position, Vector3f rotation, FloatBuffer vertices, FloatBuffer normals, FloatBuffer texCoords, int numVertices,
                FloatBuffer diffuseBf, FloatBuffer ambientBf, FloatBuffer specularBf,
                float transperacy, float shininess, Vector3f scale, ArrayList<Vertex> vertexArrayList) {
        this.vertices = vertices;
        this.normals = normals;
        this.texCoords = texCoords;
        this.position = new Vector3f(position.x,position.y,position.z);
        this.rotation = new Vector3f(rotation.x,rotation.y,rotation.z);
        this.numVertices = numVertices;
        this.diffuseBf = diffuseBf;
        this.ambientBf = ambientBf;
        this.specularBf = specularBf;
        this.scale = scale;
        this.transperacy = transperacy;
        this.shininess = shininess;
        this.vertexArrayList = vertexArrayList;
        this.amountFaces = amountFaces;
    }

    //Constructor without textures
    public Mesh(Vector3f position, Vector3f rotation, FloatBuffer vertices, FloatBuffer normals, int numVertices,
                FloatBuffer diffuseBf, FloatBuffer ambientBf, FloatBuffer specularBf, float transperacy, float shininess , Vector3f scale, ArrayList<Vertex> vertexArrayList) {
        this.position = new Vector3f(position);
        this.rotation = new Vector3f(rotation);
        this.vertices = vertices;
        this.normals = normals;
        this.numVertices = numVertices;
        this.diffuseBf = diffuseBf;
        this.ambientBf = ambientBf;
        this.specularBf = specularBf;
        this.scale = scale;
        this.transperacy = transperacy;
        this.shininess = shininess;
        this.vertexArrayList = vertexArrayList;
    }


    public FloatBuffer getVertices() {
        return vertices;
    }

    public FloatBuffer getDiffuseMaterial() {
        return diffuseBf;
    }

    public FloatBuffer getAmbientMaterial() {
        return ambientBf;
    }

    public float getIllum(){ return illum; }

    public float getTransperacy() { return  transperacy; }

    public FloatBuffer getSpecularMaterial() {
        return specularBf;
    }

    public FloatBuffer getNormals() { return normals; }

    public FloatBuffer getTexCoords() {
        return texCoords;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getScale() {
        return scale;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public float getShininess() { return shininess; }

    public int getPolygon(){
        if (amountFaces == 0) amountFaces = 3;
        return amountFaces;
    }

}