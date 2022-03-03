import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CameraTest {

    @Test
    public void shouldReturnMatrix() {
        Camera camera = new Camera();
        Matrix4f m = new Matrix4f();
        m = camera.getCamera();
//
//        Assertions.assertTrue(m.equals(new Matrix4f(
//                        1.000f, 0.000f, 0.000f, 0.000f,
//                        0.000f, 1.000f, 0.000f, 0.000f,
//                        0.000f, 0.000f, -1.001f, -1.000f,
//                        0.000f, 0.000f, -0.2001f, 0.000f)),
//                String.valueOf(0.0f));

//        Assertions.assertTrue(m.equals(new Matrix4f(
//                        1.000f, 0.000f, 0.000f, 0.000f,
//                        0.000f, 1.000f, 0.000f, 0.000f,
//                        0.000f, 0.000f, -1.001f, -0.2001f,
//                        0.000f, 0.000f, -1.000f, 0.000f)),
//                String.valueOf(0.0f));

//        Assertions.assertEquals(camera.getCamera(),
//                new Matrix4f(
//                        1.000f,  0.000f,  0.000f,  0.000f,
//                        0.000f,  1.000f,  0.000f,  0.000f,
//                        0.000f,  0.000f, -1.001f, -0.2001f,
//                        0.000f,  0.000f,-1.000f ,  0.000f),
//                String.valueOf(0.0f));



//        Assertions.assertEquals(camera.getCamera(),
//                new Matrix4f(
//                        1.000f,  0.000f,  0.000f,  0.000f,
//                        0.000f,  1.000f,  0.000f,  0.000f,
//                        0.000f,  0.000f, -1.001f, -1.000f ,
//                        0.000f,  0.000f,-0.2001f,  0.000f),
//                String.valueOf(0.0f));



//        Assert.assertEquals(String.valueOf(camera.getCamera()),
//                new Matrix4f(
//                        1.000E+0f,  0.000E+0f,  0.000E+0f,  0.000E+0f,
//        0.000E+0f,  1.000E+0f,  0.000E+0f,  0.000E+0f,
//        0.000E+0f,  0.000E+0f, -1.001E+0f, -2.001E-1f,
//        0.000E+0f, 0.000E+0f, -1.000E+0f,  0.000E+0f),
//                0.0f);


//        Assert.assertEquals(expected, actual, delta)

    }
}