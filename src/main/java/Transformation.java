import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.List;
import java.util.function.BinaryOperator;

public class Transformation {

    private final Matrix4f projectionMatrix;

    private final Matrix4f modelMatrix;

    private final Matrix4f viewMatrix;

    private final Matrix4f modelViewMatrix;

    public Transformation() {
        modelMatrix = new Matrix4f();
        modelViewMatrix = new Matrix4f();
        projectionMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
    }


    public static Vector3f getBounds(final List<Vertex> vertices,
                                     final BinaryOperator<Float> op) {
        final float x = vertices.stream().map(v -> v.position.x).reduce(op).orElse(0f);
        final float y = vertices.stream().map(v -> v.position.y).reduce(op).orElse(0f);
        final float z = vertices.stream().map(v -> v.position.z).reduce(op).orElse(0f);
        return new Vector3f(x, y, z);
    }

    public Matrix4f getModelMatrix(Mesh mesh) {

        Vector3f min = getBounds(mesh.vertexArrayList, Math::min);
        Vector3f max = getBounds(mesh.vertexArrayList, Math::max);

        float shiftx = (min.x + max.x)/2;
        float shifty =  (min.y + max.y)/2;
        float shiftz = (min.z + max.z)/2;

        Vector3f rotation = mesh.getRotation();
        Vector3f pos = mesh.getPosition();
        Vector3f scale = mesh.getScale();

        if (pos.x != 0 || pos.y != 0 || pos.z != 0){
            System.out.println("pos x" + pos.x);
            modelMatrix.identity().
                    translate(-shiftx,0,-shiftz).
                    rotateLocalX((float) Math.toRadians(rotation.x)).
                    rotateLocalY((float) Math.toRadians(rotation.y)).
                    rotateLocalZ((float) Math.toRadians(rotation.z)).
                    scale(scale).
                    translateLocal(pos);
        }
        else{
            modelMatrix.identity();
        }

        /* TO Move objects into center of origin
        System.out.println(-shifty + "shift y");
        modelMatrix.identity().translate(-shiftx,-shifty,-shiftz).
                rotateLocalX((float) Math.toRadians(rotation.x)).
                rotateLocalY((float) Math.toRadians(rotation.y)).
                rotateLocalZ((float) Math.toRadians(rotation.z)).
                scale(scale).
        translateLocal(pos);
        */


        return modelMatrix;
    }

    public Matrix4f getViewMatrix(Vector3f eye, Vector3f center, Vector3f up){
        return viewMatrix.lookAt(eye, center, up);
    }

    public final Matrix4f getProjectionMatrix(float fov, float width, float height, float zNear, float zFar) {
        float aspectRatio = width / height;
        projectionMatrix.identity();
        projectionMatrix.perspective(fov, aspectRatio, zNear, zFar);
        return projectionMatrix;
    }

    public Matrix4f updateProjectionMatrix(float fov, float width, float height, float zNear, float zFar) {
        projectionMatrix.identity();
        return projectionMatrix.setPerspective(fov, width / height, zNear, zFar);
    }


}