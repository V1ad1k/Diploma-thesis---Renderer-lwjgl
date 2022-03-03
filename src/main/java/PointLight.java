import org.joml.Vector3f;
import org.joml.Vector4f;

public class PointLight {

    public Vector4f position;
    public Vector4f axis;
    public float cone_angle;
    public Vector3f color;
    public float[] attenuation_polynomial;


    public PointLight(Vector4f position, Vector4f axis, float cone_angle, Vector3f color, float[] attenuation_polynomial){
        this.position = position;
        this.axis = axis;
        this.cone_angle = cone_angle;
        this.color = color;
        this.attenuation_polynomial = attenuation_polynomial;
    }
}
