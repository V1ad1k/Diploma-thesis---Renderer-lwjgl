import org.joml.Vector3f;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class MeshObjectLoaderTest {

    @Test
    public void shouldReturnAllMeshes() {
        /*verify that all instances are added*/
        List<Mesh> meshList = new ArrayList<>();
        meshList = MeshObjectLoader.loadModelMeshFromStream(
                "./data/base_test_1.mtl",
                "./data/base_test_1.obj");
        Assertions.assertEquals(542,meshList.size());
    }

    @Test
    public void shouldThrowException(){
        Assertions.assertThrows(NullPointerException.class, () -> {MeshObjectLoader.loadModelMeshFromStream("./data/base_test.mtl", null); } );
//        Assertions.assertThrows(NoSuchFieldException.class, () -> {MeshObjectLoader.loadModelMeshFromStream("./data/cubes2.mtl", "./data/cubess2.obj"); } );
    }




}