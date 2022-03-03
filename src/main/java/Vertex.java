import org.joml.Vector3f;

public class Vertex {

    public Vector3f position;
    public Vector3f textureCoordinate;
    public Vector3f normal;

    public Vertex(Vector3f position, Vector3f textureCoordinate, Vector3f normal) {
        this.position = position;
        this.textureCoordinate = textureCoordinate;
        this.normal = normal;
    }

    public Vertex(Vector3f position, Vector3f normal) {
        this.position = position;
        this.normal = normal;
    }


    public Vertex(Vector3f position) {
    }

    public String toString() {
        return String.format("<%s, %s, %s>", this.position, this.textureCoordinate, this.normal);
    }

}