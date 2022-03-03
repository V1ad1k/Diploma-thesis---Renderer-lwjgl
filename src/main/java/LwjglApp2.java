import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.opengl.GL11.glReadBuffer;
import static org.lwjgl.opengl.GL11.glReadPixels;
import static org.lwjgl.opengl.GL30C.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.*;
import org.joml.Vector3f;

import javax.imageio.ImageIO;

public class LwjglApp2 implements Runnable {

    GLCapabilities caps;
    public boolean running;
    GLFWErrorCallback errCallback;
    GLFWKeyCallback keyCallback;
    GLFWFramebufferSizeCallback fbCallback;
    Callback debugProc;
    long window;
    static int width = 990, height = 850;
    Scene scene;
    String pathObj;
    float x, y, z;
    String nameImage, nameVideo;
    int i = 1;
    boolean toSave = true;
    float x1 = 25, z1 = 15, x2 = 25, z2 = 1;

    public LwjglApp2(String arg, float x1, float y1, float z1, String nameStore, String videoName) {
        pathObj = arg;
        x = x1;
        y = y1;
        z = z1;
        nameImage = nameStore;
        nameVideo = videoName;
    }

    public void start() {
        running = true;
        Thread t = new Thread(this);
        t.start();
    }

    void init() throws Exception {

        glfwSetErrorCallback(errCallback = new GLFWErrorCallback() {
            GLFWErrorCallback delegate = GLFWErrorCallback.createPrint(System.err);

            public void invoke(int error, long description) {
                if (error == GLFW_VERSION_UNAVAILABLE)
                    System.err.println("This demo requires OpenGL 3.0 or higher.");
                delegate.invoke(error, description);
            }

            @Override
            public void free() {
                delegate.free();
            }
        });

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(width, height, "Render Engine", NULL, NULL);
        if (window == NULL) {
            throw new AssertionError("Failed to create the GLFW window");
        }

        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (action != GLFW_RELEASE)
                    return;

                if (key == GLFW_KEY_ESCAPE) {
                    glfwSetWindowShouldClose(window, true);
                }
            }
        });

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - width) / 2);
        int y = (int) ((dimension.getHeight() - height) / 2);
        glfwSetWindowPos(window, x, y + 60);
//        glfwSetWindowPos(window, (width) / 2 + 10, (vidmode.height() - height) / 2 + 60);
        glfwMakeContextCurrent(window);
        glfwSwapInterval(0);
        glfwShowWindow(window);
        caps = GL.createCapabilities();
        debugProc = GLUtil.setupDebugMessageCallback();

        /* Set some GL states
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);
        glClearColor(0.2f, 0.3f, 0.4f, 1.0f);
         */
    }

    void loop() throws Exception {
        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents();
            render();
        }
    }

    public void render() throws Exception {
//        GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
//        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
//        GL30.glEnable(GL30.GL_DEPTH_TEST);
//        GL30.glShadeModel(GL30.GL_SMOOTH);
        glEnable(GL_BLEND);
        glBlendEquation(GL_FUNC_ADD);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        glEnable(GL_DEPTH_TEST);
        /* Support for transparencies
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        */
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

//        scene = new Scene(new Vector3f(15, 8, -4), new Vector3f(0, 0, 0), pathObj, i);
        scene = new Scene(new Vector3f(12, 3, -10), new Vector3f(0, 0, 0), pathObj);

        scene.staticRender(pathObj);
//        scene.dynamicRender(i);
//        scene.dynamicRender2(i);
        boolean flagImg = Window.getFlagImage();

        Window.setFlagImage(false);
        if (flagImg) {
            saveImage3(Window.getPathAnd());
        }
        Window.setFlagImage(false);
        glfwSwapBuffers(window);
    }

    public static void saveImage3(String fileOut) {
        //First off, we need to access the pixel data of the Display. Without this, we don't know what to save!
        glfwGetCurrentContext();
        GL.createCapabilities();
        GL.setCapabilities(GL.createCapabilities());
        GL.getCapabilities();
        glReadBuffer(GL_FRONT);
        int bpp = 4; // Assuming a 32-bit display with a byte each for red, green, blue, and alpha.
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
        glReadPixels(0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        //Save the screen image
        File outputfile = new File(fileOut + ".png");
        String format = "png"; // Example: "PNG" or "JPG"
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int i = (x + (width * y)) * bpp;
                int r = buffer.get(i) & 0xFF;
                int g = buffer.get(i + 1) & 0xFF;
                int b = buffer.get(i + 2) & 0xFF;
                image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
            }
        }
        try {
            ImageIO.write(image, format, outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            init();
            loop();

            if (debugProc != null)
                debugProc.free();

            errCallback.free();
            keyCallback.free();
            glfwDestroyWindow(window);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            glfwTerminate();
        }
    }

    public static void main(String[] args) {
        final Map<String, List<String>> params = new HashMap<>();

        List<String> options = null;
        for (int i = 0; i < args.length; i++) {
            final String a = args[i];

            if (a.charAt(0) == '-') {
                if (a.length() < 2) {
                    System.err.println("Error at argument " + a);
                    return;
                }

                options = new ArrayList<>();
                params.put(a.substring(1), options);
            }
            else if (options != null) {
                options.add(a);
            }
            else {
                System.err.println("Illegal parameter usage");
                return;
            }
        }
        System.out.println(params.get("obj").get(0));
        System.out.println(params.get("obs").get(0)); // 1
        System.out.println(params.get("obs").get(1)); // 2
        System.out.println(params.get("obs").get(2)); // 1
        new LwjglApp2(params.get("obj").get(0),  // obj file and automatically mtl
                Float.parseFloat(params.get("obs").get(0)), Float.parseFloat(params.get("obs").get(1)), Float.parseFloat(params.get("obs").get(2)),
                params.get("img").get(0),
                params.get("video").get(0)).start(); // observer
    }
}


