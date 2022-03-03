import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

class LwjglAppTest {

    @Test
    public void setEnvironmentVariable() throws Exception {
       // -obj "./data/cubes2.obj" -obs 8 5 2 -img result -video video3
        String value = withEnvironmentVariable("-obs", "./data/cubes2.obj")
                .execute(() -> System.getenv("-obs"));
        System.out.println(value);
        assertEquals("./data/cubes2.obj", value);
    }


    @Rule
    public final EnvironmentVariables environmentVariables
            = new EnvironmentVariables();

    @Test
    public void setEnvironmentVariable2() {
        environmentVariables.set("-obs", "./data/cubes2.obj");
        assertEquals("./data/cubes2.obj", System.getenv("-obs"));
    }
}


