
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20C.glBindAttribLocation;

import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;

public class ShaderProgram
{
    private final int program;
    private final PointLight[] lights = new PointLight[6];
    private final Map<String, Integer> locations = new HashMap<>(); //collect locations to save computation time

    public ShaderProgram(String vPath, String fPath) throws Exception {
        int vert = compile("/shaders/" + vPath, ShaderType.VERT);
        int frag = compile("/shaders/" + fPath, ShaderType.FRAG);

        System.out.println(vert + " vert");
        System.out.println(frag + " frag");

        program = glCreateProgram();
        glAttachShader(program, vert);
        glAttachShader(program, frag);
        glValidateProgram(program);
        glLinkProgram(program);

        if(glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE)
        {
            System.err.println("ERROR LINKING PROGRAM");
            System.err.println(glGetProgramInfoLog(program));
        }

        //Free resources
        glDetachShader(program, vert);
        glDetachShader(program, frag);
        glDeleteShader(vert);
        glDeleteShader(frag);
    }

    protected int compile(String path, ShaderType type) throws Exception {
        int shader = 0;
        if(type == ShaderType.VERT)
            shader = glCreateShader(GL_VERTEX_SHADER);
        else if(type == ShaderType.FRAG)
            shader = glCreateShader(GL_FRAGMENT_SHADER);
        GL20.glShaderSource(shader, FileUtils.readAsString(path));
        glCompileShader(shader);
        if(glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE)
        {
            System.err.println("ERROR COMPILING " + type + " SHADER");
            System.err.println(glGetShaderInfoLog(shader));
        }

        return shader;
    }

    public void unbind()
    {
        glUseProgram(0);
    }

    public void bind()
    {
        glUseProgram(program);
    }

    public int getLocation(String name)
    {
        //If not in map yet.
        if(!locations.containsKey(name))
        {
            int loc = glGetUniformLocation(program, name);
            if(loc == -1) //means invalid location
            {
                System.err.println("Invalid uniform name: '" + name +"'");
                return -1;
            }
            locations.put(name, loc);
        }
        return locations.get(name);
    }

    public void getLight(){

        glUniform3f(getLocation("spotlight.ambient"),  0.7f, 0.7f, 0.7f);
        glUniform3f(getLocation("spotlight.diffuse"),  0.8f, 0.8f, 0.8f);
        glUniform3f(getLocation("spotlight.specular"),  1.0f, 1.0f, 1.0f);

        glUniform1f(getLocation("spotlight.constant"),  1.0f);
        glUniform1f(getLocation("spotlight.linear"),  0.09f);
        glUniform1f(getLocation("spotlight.quadratic"),  0.032f);

        glUniform4f(getLocation("spotlight.position"),  5,20,60,1.0f);
        glUniform3f(getLocation("spotlight.direction"),  0,0,-300);
        glUniform1f(getLocation("spotlight.cutOff"),  (float) Math.cos(Math.toRadians(22.5f)));


        glUniform4f(getLocation("lightt.position"),  1.5f, 1.0f, 5.5f, 1.0f);
        glUniform3f(getLocation("lightt.ambient"),  0.8f, 0.8f, 0.8f);
        glUniform3f(getLocation("lightt.diffuse"),  0.8f, 0.8f, 0.8f);
        glUniform3f(getLocation("lightt.specular"),  1.0f, 1.0f, 1.0f);

        glUniform1f(getLocation("lightt.constant"),  0f);
        glUniform1f(getLocation("lightt.linear"),  1f);
        glUniform1f(getLocation("lightt.quadratic"),  0f);
    }


    public void getMaterial(Mesh mesh, Vector3f camera) {

        String field_name = "material";

        glUniform3f(getLocation(field_name + ".mat_amb"),mesh.getAmbientMaterial().get(0), mesh.getAmbientMaterial().get(1), mesh.getAmbientMaterial().get(2));
        glUniform3f(getLocation(field_name + ".mat_diff"),mesh.getDiffuseMaterial().get(0), mesh.getDiffuseMaterial().get(1), mesh.getDiffuseMaterial().get(2));
        glUniform3f(getLocation(field_name + ".mat_spec"),mesh.getSpecularMaterial().get(0), mesh.getSpecularMaterial().get(1), mesh.getSpecularMaterial().get(2));
        glUniform1f(getLocation(field_name + ".mat_transp"),mesh.getTransperacy());
        System.out.println("mat_transp" + mesh.getTransperacy());
//        glUniform1f(getLocation(field_name + ".glossiness"), mesh.getShininess());
        glUniform1f(getLocation(field_name + ".glossiness"), 60f);

/*      For point lights in shader

        glUniform4f(getLocation("lightt[1].position"),  1.5f, 1.0f, 1.5f, 1.0f);
        glUniform3f(getLocation("lightt[1].ambient"),  0.7f, 0.7f, 0.7f);
        glUniform3f(getLocation("lightt[1].diffuse"),  0.8f, 0.8f, 0.8f);
        glUniform3f(getLocation("lightt[1].specular"),  1.0f, 1.0f, 1.0f);

        glUniform4f(getLocation("lightt[2].position"),  -1.5f, 1.0f, 1.5f, 1.0f);
        glUniform3f(getLocation("lightt[2].ambient"),  0.7f, 0.7f, 0.7f);
        glUniform3f(getLocation("lightt[2].diffuse"),  0.5f, 0.5f, 0.5f);
        glUniform3f(getLocation("lightt[2].specular"),  1.0f, 1.0f, 1.0f);

        glUniform4f(getLocation("lightt[3].position"),  -1.5f, 1.0f, -1.5f, 1.0f);
        glUniform3f(getLocation("lightt[3].ambient"),  0.7f, 0.7f, 0.7f);
        glUniform3f(getLocation("lightt[3].diffuse"),  0.8f, 0.8f, 0.8f);
        glUniform3f(getLocation("lightt[3].specular"),  1.0f, 1.0f, 1.0f);

        glUniform1f(getLocation("lightt[1].constant"),  1.0f);
        glUniform1f(getLocation("lightt[1].linear"),  0.39f);
        glUniform1f(getLocation("lightt[1].quadratic"),  0.032f);

        glUniform1f(getLocation("lightt[1].constant"),  0f);
        glUniform1f(getLocation("lightt[1].linear"),  1f);
        glUniform1f(getLocation("lightt[1].quadratic"),  0f);

        glUniform1f(getLocation("lightt[2].constant"),  0f);
        glUniform1f(getLocation("lightt[2].linear"),  1f);
        glUniform1f(getLocation("lightt[2].quadratic"),  0.032f);

        glUniform1f(getLocation("lightt[3].constant"),  0f);
        glUniform1f(getLocation("lightt[3].linear"),  1f);
        glUniform1f(getLocation("lightt[3].quadratic"),  0.032f);

 */

    }

    public void PassSingleLight(PointLight light, int index, Matrix4f matrix){
        int field_ref;
        String field_name;
        Vector4f position_eye_coords = light.position.mul(matrix);
        field_name = "lights[" + index + "].position";
        field_ref = (glGetUniformLocation(program, field_name));
        Vector4f position = new Vector4f(10.0f, 10.0f, 10.0f, 1.0f);
        glUniform4f(field_ref, position_eye_coords.x,position_eye_coords.y,position_eye_coords.y,position_eye_coords.w);

        field_name = "lights[" + index  + "].axis";
        field_ref = glGetUniformLocation(program, field_name);
        Vector4f axis = new Vector4f(1.0f, 0.0f, 0.0f, 1.0f);
        glUniform4f(field_ref, light.axis.x, light.axis.y,light.axis.z,light.axis.w);

        field_name = "lights[" + index + "].cone_angle";
        field_ref = glGetUniformLocation(program, field_name);
        float cone_angle = 90;
        glUniform1f(field_ref, cone_angle);

        field_name = "lights[" + index + "].color";
        field_ref = glGetUniformLocation(program, field_name);
        Vector3f color = new Vector3f(1, 1, 1);
        glUniform3f(field_ref, light.color.x,light.color.y,light.color.z);

        field_name = "lights[" + index + "].attenuation_polynomial";
        field_ref = glGetUniformLocation(program, field_name);
        glUniform1fv(field_ref,light.attenuation_polynomial);

    }

    public void PassAllLights(int light_count, Matrix4f matrix){
        for (int i = 0; i < light_count; i++) {
            PassSingleLight(lights[i], i, matrix);
        }
        int field_ref = glGetUniformLocation(program, "current_light_count");
        glUniform1i(field_ref, light_count);

    }

    public void setAllLights(Matrix4f world) {
        float[] arr = new float[]{0f, 0f, 0f};
        float[] arr1 = new float[]{1f, 1f, 1f};
        float[] attenuation = new float[]{0, 1f, 0};
        PointLight light = new PointLight(
                new Vector4f(1.5f, -1.0f, 0.5f, 1.0f),
                new Vector4f(1.0f, 0.0f, 0.0f, 1.0f),
                90,
                new Vector3f(1, 1, 1),
                arr1);
        PointLight light2 = new PointLight(
                new Vector4f(-1.5f, 1.0f, 1.5f, 1.0f),
                new Vector4f(1.0f, 0.0f, 0.0f, 1.0f),
                90,
                new Vector3f(1, 1, 1),
                arr);
/*
                PointLight light2 = new PointLight(
                new Vector4f(1.5f, -1.0f, 0.5f, 1.0f),
                new Vector4f(1.0f, 0.0f, 0.0f, 1.0f),
                90,
                new Vector3f(1, 1, 1),
                arr1);
 */

        PointLight light3 = new PointLight(
                new Vector4f(0.5f, 1.0f, 0.5f, 1.0f),
                new Vector4f(1.0f, 0.0f, 0.0f, 1.0f),
                90,
                new Vector3f(0.8f, 0.8f, 0.8f),
                arr);
        PointLight light5 = new PointLight(
                new Vector4f(-2.0f,  -1.5f, 1.5f, 1.0f),
                new Vector4f(1.0f, 0.0f, 0.0f, 1.0f),
                90,
                new Vector3f(1, 1, 1),
                attenuation);
        PointLight light6 = new PointLight(
                new Vector4f(1.5f, -2.0f, 0.5f, 1.0f),
                new Vector4f(1.0f, 0.0f, 0.0f, 1.0f),
                90,
                new Vector3f(1, 1, 1),
                attenuation);
        PointLight light7 = new PointLight(
                new Vector4f(0.5f, 1.0f, 2.5f, 1.0f),
                new Vector4f(1.0f, 0.0f, 0.0f, 1.0f),
                90,
                new Vector3f(1,1,1),
                attenuation);

        lights[0] = light;
        lights[1] = light2;
        lights[2] = light3;

//        lights[0] = light5;
//        lights[1] = light6;
//        lights[2] = light7;

        PassAllLights(3, world);
    }


    public void SetModelMatrix(String name, Matrix4f camera){
        try(MemoryStack stack = MemoryStack.stackPush()) {
            glUniformMatrix4fv(getLocation(name), false, camera.get(stack.mallocFloat(16)));
        }
    }


    public void setProjectionMatrix(String name, Matrix4f camera){
        try(MemoryStack stack = MemoryStack.stackPush()) {
            glUniformMatrix4fv(getLocation(name), false, camera.get(stack.mallocFloat(16)));
        }
    }

    public void setViewMatrix(String name, Matrix4f MV_mtx){
        try(MemoryStack stack = MemoryStack.stackPush()) {
            glUniformMatrix4fv(getLocation(name), false, MV_mtx.get(stack.mallocFloat(16)));
        }

    }

    protected enum ShaderType
    {
        VERT, FRAG
    }

}
