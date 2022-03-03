import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SceneTest {

    int i = 0;

    @DisplayName("Repeat for dynamic obj files")
    @RepeatedTest(value = 6)
    public void shouldReturnDynamicMeshes(){
        String objPath = String.format("./data/res%d.obj", ++i);
        System.out.println(i + " index ");
        String mtlPath = FileUtils.getMtlPathFromOBJ(objPath);
        List<Mesh> res2 = MeshObjectLoader.loadModelMeshFromStream(mtlPath, objPath);
        assertTrue(res2.size() != 0);



    }

}