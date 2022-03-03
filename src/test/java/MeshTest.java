import org.joml.Vector3f;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MeshTest {

    List<Mesh> meshList = MeshObjectLoader.loadModelMeshFromStream("./data/cubes2.mtl", "./data/cubes2.obj");;
    Mesh testMesh = meshList.get(4);


    @Test
    public void shouldReturnAmbient(){
//        Assertions.assertTrue(testMesh.getAmbientMaterial().equals(new Vector3f(1.000000f, 1.000000f, 1.000000f)));

        Assertions.assertEquals(new Vector3f(
                        testMesh.getAmbientMaterial().get(0),
                        testMesh.getAmbientMaterial().get(1),
                        testMesh.getAmbientMaterial().get(2)),
                new Vector3f(1.000000f,1.000000f,1.000000f),
                String.valueOf(0.0f));

        Assertions.assertEquals(new Vector3f(
                        testMesh.getDiffuseMaterial().get(0),
                        testMesh.getDiffuseMaterial().get(1),
                        testMesh.getDiffuseMaterial().get(2)),
                new Vector3f(0.635096f, 0.000000f, 0.009776f),
                String.valueOf(0.0f));

        Assertions.assertEquals(new Vector3f(
                        testMesh.getSpecularMaterial().get(0),
                        testMesh.getSpecularMaterial().get(1),
                        testMesh.getSpecularMaterial().get(2)),
                new Vector3f(0.800000f, 0.800000f, 0.800000f),
                String.valueOf(0.0f));

        Assertions.assertEquals(
                        testMesh.getTransperacy(),
               1f,
                String.valueOf(0.0f));

        Assertions.assertEquals(testMesh.getShininess(),500.000001f, 0.0f);


    }
    @Test
    public void shouldReturnTransformationInfo(){

    }

    @Test
    @EnabledOnOs(value = OS.WINDOWS)
//    @DisabledOnOs(value = OS.WINDOWS, disabledReason = "Disabled on Windows OS")
    public void shouldBeWindowsOs(){
        meshList = MeshObjectLoader.loadModelMeshFromStream("./data/cubes2.mtl", "./data/cubes2.obj");
        Mesh testMesh = meshList.get(2);
        Assertions.assertEquals(testMesh.getShininess(),500.000001f, 0.0f);
    }

    @Test
    @EnabledOnOs(value = OS.MAC, disabledReason = "Enabled only on Mac OS")
//    @DisabledOnOs(value = OS.WINDOWS, disabledReason = "Disabled on Windows OS")
    public void shouldMacOs(){
        meshList = MeshObjectLoader.loadModelMeshFromStream("./data/cubes2.mtl", "./data/cubes2.obj");
        Mesh testMesh = meshList.get(2);
        Assertions.assertEquals(testMesh.getShininess(),500.000001f, 0.0f);
    }

    @Test
    public void shouldCheckInstanceProperties(){
        Assertions.assertEquals(testMesh.vertexArrayList.size(), 36);
        Assertions.assertEquals(testMesh.getPosition(), new Vector3f(-2.3f, 1.0f, 0.0f) , String.valueOf(0.0f));
        Assertions.assertEquals(testMesh.getRotation(), new Vector3f(0, 0, 0) , String.valueOf(0.0f));
        Assertions.assertEquals(testMesh.getScale(), new Vector3f(1, 1, 1) , String.valueOf(0.0f));
        Assertions.assertEquals(
                new Vector3f(testMesh.getDiffuseMaterial().get(0),testMesh.getDiffuseMaterial().get(1),testMesh.getDiffuseMaterial().get(2)),
                new Vector3f(0.635096f, 0.000000f, 0.009776f) , String.valueOf(0.0f));
    }

}