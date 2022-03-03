import org.joml.Matrix4f;

import static java.lang.Math.PI;

public class Camera {

    private final float Z_NEAR = 0.1f;

    private final float Z_FAR = 300.f;

    private final float aspect = (float) LwjglApp.width /  (float) LwjglApp.height;

    private final float viewAngle = 100;

    public Camera() {
    }

    public  Matrix4f getCamera(){
        return new Matrix4f().identity().perspective((float) (2 * PI * viewAngle / 360.0), aspect, Z_NEAR, Z_FAR );
    }

}