import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.ArrayList;
import java.util.List;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Scene
{
    private int program;
    //Create matrixes
    private Matrix4f world2eye;
    private Transformation transformation;
    private Vector3f obs;
    private Vector3f tar;
    private Camera camera;
    protected  int vaoId;
    private  int vertexCount;
    protected  List<Integer> vboIdList;

    protected ShaderProgram shader;
    protected List<Mesh> res;
    protected List<Mesh> res2;

    public Scene(Vector3f observer_pos, Vector3f target_pos, String objPath) throws Exception {

        String mtlPath = FileUtils.getMtlPathFromOBJ(objPath);
        if (res == null) { res = MeshObjectLoader.loadModelMeshFromStream(mtlPath, objPath); }

        shader = new ShaderProgram("spot.vert", "spot.frag");
        shader.bind();
        obs = observer_pos;
        tar = target_pos;
        transformation = new Transformation();
        camera = new Camera();
        shader.setProjectionMatrix("projectionMatrix", camera.getCamera());
        System.out.println(res.get(0).observer.length() + "lennn");

        if (res.get(0).observer.x != 0 || res.get(0).observer.y != 0 || res.get(0).observer.z != 0){
            world2eye = transformation.getViewMatrix(res.get(0).observer,tar, new Vector3f(0,1,0));

        }
        else {
            world2eye = transformation.getViewMatrix(obs,tar, new Vector3f(0,1,0));
        }

        shader.getLight();
        Matrix4f viewMatrix = new Matrix4f(world2eye);
        shader.setViewMatrix("viewMatrix", viewMatrix);
    }

    public Scene(Vector3f observer_pos, Vector3f target_pos, String objPath, int i) throws Exception {

        String objPath2 = String.format("./data/frame%d.obj", i);
        String mtlPath2 = FileUtils.getMtlPathFromOBJ(objPath2);
        res2 = MeshObjectLoader.loadModelMeshFromStream(mtlPath2, objPath2);
        String mtlPath = FileUtils.getMtlPathFromOBJ(objPath);
        if (res == null) { res = MeshObjectLoader.loadModelMeshFromStream(mtlPath, objPath); }

        shader = new ShaderProgram("spot.vert", "spot.frag");
        shader.bind();
        obs = observer_pos;
        tar = target_pos;
        transformation = new Transformation();
        camera = new Camera();
        shader.setProjectionMatrix("projectionMatrix", camera.getCamera());
        System.out.println(res2.get(0).observer.length() + "lennn");

        if (res2.get(0).observer.x != 0 || res2.get(0).observer.y != 0 || res2.get(0).observer.z != 0){
            world2eye = transformation.getViewMatrix(res2.get(0).observer,tar, new Vector3f(0,1,0));

        }
        else {
            world2eye = transformation.getViewMatrix(obs,tar, new Vector3f(0,1,0));
        }

        shader.getLight();
        Matrix4f viewMatrix = new Matrix4f(world2eye);
        shader.setViewMatrix("viewMatrix", viewMatrix);
        System.out.println("Scene2 starting");
    }

    public void createMesh(Mesh mesh){

        vboIdList = new ArrayList<>();
        vertexCount = mesh.numVertices;
        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        int vboId = glGenBuffers();
        vboIdList.add(vboId);
        glBindBuffer(GL_ARRAY_BUFFER,vboId);
        glBufferData(GL_ARRAY_BUFFER, mesh.getVertices(), GL_STATIC_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        vboId = glGenBuffers();
        vboIdList.add(vboId);
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, mesh.getNormals(),GL_STATIC_DRAW);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);

        vboId = glGenBuffers();
        vboIdList.add(vboId);
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER,  mesh.getVertices() ,GL_STATIC_DRAW);
        glEnableVertexAttribArray(3);
        glVertexAttribPointer(3, 3, GL_FLOAT, false, 0, 0);

    }

    private int getVaoId() {
        return vaoId;
    }

    protected void initRender() { glBindVertexArray(getVaoId()); }

    private int getVertexCount() {
        return vertexCount;
    }

    protected void endRender() {
        glBindVertexArray(0);
    }

    protected void disableAttribs(){
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
    }

    private void deleteBuffers() {
        // Delete the VBOs
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        for (int vboId : vboIdList) {
            glDeleteBuffers(vboId);
        }

    }

    public void staticRender(String objPath) {
        for (Mesh mesh : res) {
            createMesh(mesh);
            initRender();
            Matrix4f modelMatrix = transformation.getModelMatrix(mesh);
            shader.getMaterial(mesh, obs);
            shader.SetModelMatrix("model_matrix", modelMatrix);
            glDrawArrays(GL_TRIANGLES, 0, getVertexCount());
            endRender();
        }

    }

    public void dynamicRender(int i){
        String objPath = String.format("./data/Car1.obj");
        String mtlPath = FileUtils.getMtlPathFromOBJ(objPath);
        List<Mesh> res2 = MeshObjectLoader.loadModelMeshFromStream(mtlPath, objPath);
        res2.remove(0);
        createMesh(res2.get(i-1));
        initRender();
        Matrix4f modelMatrix = transformation.getModelMatrix(res2.get(i-1));
        shader.getMaterial(res2.get(i-1), obs);
        shader.SetModelMatrix("model_matrix", modelMatrix);
        glDrawArrays(GL_TRIANGLES, 0, getVertexCount());
        endRender();
    }


    public void dynamicRender2(int i) {
        res2.remove(0);
        for (Mesh mesh : res2) {
            createMesh(mesh);
            initRender();
            Matrix4f modelMatrix = transformation.getModelMatrix(mesh);
            shader.getMaterial(mesh, obs);
            shader.SetModelMatrix("model_matrix", modelMatrix);
            glDrawArrays(GL_TRIANGLES, 0, getVertexCount());
            endRender();
        }
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }




}

