import org.joml.Vector3f;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShaderProgramTest {

    @Test
    public void shouldReturnPath() throws Exception {

        ShaderProgram shader = new ShaderProgram("Phong.vert", "Phong.frag");
        String vPath = "Phong.vert";
        String fPath = "Phong.frag";
        int vert = shader.compile("/shaders/" + vPath, ShaderProgram.ShaderType.VERT);
        int frag = shader.compile("/shaders/" + fPath, ShaderProgram.ShaderType.FRAG);
//        Assertions.assertTrue(1 == vert);
//        Assertions.assertTrue(1 == frag);

//        Assertions.assertTrue(shader == );

    }
}