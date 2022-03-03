import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

class TransformationTest {

//    @Test
//    public void shouldReturnMinMax() {
//        List<Mesh> meshList = new ArrayList<>();
//        meshList = MeshObjectLoader.loadModelMeshFromStream("./data/cubes2.mtl", "./data/cubes2.obj");
//        Vector3f min = Transformation.getBounds(meshList.get(0).vertexArrayList,  Math::min);
//        Vector3f max = Transformation.getBounds(meshList.get(0).vertexArrayList,  Math::max);
////        assertSame(min, new Vector3f(-8.0f, -0.000002f, -8.0f));
////        assertSame(max, new Vector3f(8.0f, 0.000002f, 8f));
//        Assertions.assertEquals(max, new Vector3f(8.0f, 0.000002f, 8.0f), String.valueOf(0.0f));
//        Assertions.assertEquals(min, new Vector3f(-8.0f, -0.000002f, -8.0f), String.valueOf(0.0f));
////        Assertions.assertTrue(max.x,8);
//    }
//
    @org.junit.jupiter.api.Test
    public void shouldReturnModelMatrix(){
        List<Mesh> meshList = new ArrayList<>();
        Matrix4f modelMatrix = new Matrix4f();
        Matrix4f expectedMatrix = new Matrix4f();
        Transformation transformation =  new Transformation();;
        meshList = MeshObjectLoader.loadModelMeshFromStream("./data/base_test_1.mtl",
                "./data/base_test_1.obj");
        modelMatrix = transformation.getModelMatrix(meshList.get(537));
        expectedMatrix = new Matrix4f(
                0.7f,  0.000E+0f,  0.000E+0f,   0.000E+0f ,
                0.000E+0f,  7.000E-1f,  0.000E+0f,  0.000E+0f,
                0.000E+0f,  0.000E+0f,  7.000E-1f, 0.000E+0f ,
                8.009659f , 0.000E+0f,  -16.5182f ,  1.000E+0f);

        Assertions.assertEquals(expectedMatrix , modelMatrix);

    }


//            Assertions.assertEquals(excpectedMatrix.m00() , modelMatrix.m00());
//        Assertions.assertEquals(excpectedMatrix.m11() , modelMatrix.m11());
//        Assertions.assertEquals(excpectedMatrix.m22() , modelMatrix.m22());
//        Assertions.assertEquals(excpectedMatrix.m30() , modelMatrix.m30());
//        Assertions.assertEquals(excpectedMatrix.m32() , modelMatrix.m32());

//        Assertions.assertEquals(excpectedMatrix.m00() , modelMatrix.m00());
//        Assertions.assertEquals(excpectedMatrix.m00() , modelMatrix.m00());
//        Assertions.assertEquals(excpectedMatrix.m00() , modelMatrix.m00());
//        Assertions.assertEquals(excpectedMatrix.m00() , modelMatrix.m00());
//        Assertions.assertEquals(excpectedMatrix.m00() , modelMatrix.m00());
        //        Assertions.assertEquals(excpectedMatrix , modelMatrix);






    @org.junit.jupiter.api.Test
    public void shouldReturnAllMeshes() {
        /*verify that all instances are added*/
        List<Mesh> meshList = new ArrayList<>();
        meshList = MeshObjectLoader.loadModelMeshFromStream(
                "./data/base_test_1.mtl",
                "./data/base_test_1.obj");
        Assertions.assertEquals(542,meshList.size());
    }

}