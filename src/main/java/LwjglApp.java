
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL30.*;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LwjglApp extends JPanel implements Runnable {

    //-obj "./data/base_test_1.obj" -obs 10 15 0 -img result -video video3
    private long window;
    public static int width = 900, height = 900;
    public boolean running;
    private Scene scene;
    private int i = 1;
    private int times = 0;
    private final String pathObj;
    private float x, y, z;
    private static String nameImage, nameVideo;

    public LwjglApp(String arg, float x1, float y1, float z1, String nameStore, String videoName) {
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

    public void init() throws Exception {

        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit())
            throw new IllegalStateException("GLFW failed to init");

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        //Enable msaa anti aliasing
        glfwWindowHint(GLFW_SAMPLES, 4);
        window = glfwCreateWindow(width, height, "Render Engine", 0, 0);
        if (window == 0)
            throw new RuntimeException("Failed to create a window");

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        assert vidmode != null;
        glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);

        glfwMakeContextCurrent(window);
        GL.createCapabilities();

//        scene = new Scene(new Vector3f(x,y,z), new Vector3f(0, 0, 0), pathObj);
        scene = new Scene(new Vector3f(20,7,-7), new Vector3f(0, 0, 0), pathObj);
        glfwShowWindow(window);
    }

    public void render() throws InterruptedException {
        GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        GL30.glEnable(GL30.GL_DEPTH_TEST);
        glEnable(GL_MULTISAMPLE);

        glEnable(GL_CULL_FACE);
        glCullFace(GL_FRONT);
        glFrontFace(GL_CW);
        scene.staticRender(pathObj);
        scene.dynamicRender(i);
        glfwSwapBuffers(window);
        saveImages(times);
        saveImage();
        i++;
        boolean flagImg = Window.getFlagImage();
        if (flagImg) {
            saveImage3(Window.getPathAnd());
            saveImage();
        }
        Window.setFlagImage(false);

        times++;
        if (i > 5) i = 1;
        if (times > 50) times = 0;
        Thread.sleep(100);
    }

    public void saveImages(int times){

        glfwGetCurrentContext();
        GL.createCapabilities();
        GL.setCapabilities(GL.createCapabilities());
        GL.getCapabilities();
        glReadBuffer(GL_FRONT);
        int bpp = 4; // Assuming a 32-bit display with a byte each for red, green, blue, and alpha.
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
        glReadPixels(0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        //Save the screen image
        File outputfile = new File("./storage/img" + times + ".png");
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

    public static int get_video_card_memory() {
//        while (get_error() != null); // If previous errors have not been called yet!
        int nvidia_total_memory = GL11.glGetInteger(NVXGPUMemoryInfo.GL_GPU_MEMORY_INFO_TOTAL_AVAILABLE_MEMORY_NVX);
//        System.out.println(get_error());
        int ati_total_memory = GL11.glGetInteger(ATIMeminfo.GL_VBO_FREE_MEMORY_ATI);
//        System.out.println(get_error());
        System.out.println(Math.max(nvidia_total_memory, ati_total_memory) + "GPU");
        return Math.max(nvidia_total_memory, ati_total_memory);
    }



    public static void saveImage() {
//        public static void SaveImage() {
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
//        File outputfile = new File(String.format("./storage/%s%d.png", nameImage , times));
//        File outputfile = new File(String.format("./storage/%s%d.png", nameImage , times));
//        File outputfile = new File("./storage/saveImage" + times + ".png");
        File outputfile = new File("Testing2.png");
//        File outputfile = new File(fileOut);
//        File outputfile = new File(path + "Test.png");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        glfwSetKeyCallback(window, (windowHnd, key, scancode, action, mods) -> {
            if (action != GLFW_RELEASE) {
                return;
            }
            switch (key) {
                case GLFW_KEY_ESCAPE:
                    glfwSetWindowShouldClose(windowHnd, true);
                    break;
                default:
                    System.out.println("GLFW key: " + key);
            }
        });
        glfwSetWindowPos(window, 10, 90);

        float tps = 60.0f;
        double interval = 1e9 / tps;
        double delta = 0;
        long last = System.nanoTime();
        long now;

        int frames = 0;
        int updates = 0;
        long timer = System.currentTimeMillis();

        GL30.glPolygonMode(GL30.GL_FRONT_AND_BACK, GL30.GL_LINE);
        GL30.glClearColor(0.12f, 0.12f, 0.12f, 1.0f);
        while (!glfwWindowShouldClose(window) && running) {

            now = System.nanoTime();
            delta += (now - last) / interval;
            last = now;

            if (delta >= 1) {
                delta--;
                updates++;
                glfwPollEvents();

            }
            try {
                render();
                get_video_card_memory();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            frames++;

            if (System.currentTimeMillis() - timer >= 1000) {
                glfwSetWindowTitle(window, "FPS: " + frames + " | UPS: " + updates);
                timer = System.currentTimeMillis();
                frames = 0;
                updates = 0;
            }


        }
        int i = 0;
            while(new File(String.format("./storage/img%d.png", i)).exists()) {
                System.out.println("here");
                FileUtils.deleteImages(String.format("./storage/img%d.png", i));
                i++;
            }
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();

    }


    private static HashMap<String, String> convertToKeyValuePair(String[] args) {

        HashMap<String, String> params = new HashMap<>();

        for (String arg: args) {

            String[] splitFromEqual = arg.split("=");

            String key = splitFromEqual[0].substring(2);
            String value = splitFromEqual[1];

            params.put(key, value);

        }

        return params;
    }


    private class Option {
        String flag, opt;
        public Option(String flag, String opt) { this.flag = flag; this.opt = opt; }
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
        new LwjglApp(params.get("obj").get(0),  // obj file and automatically mtl
                Float.parseFloat(params.get("obs").get(0)), Float.parseFloat(params.get("obs").get(1)), Float.parseFloat(params.get("obs").get(2)),
                params.get("img").get(0),
                params.get("video").get(0)).start();
    }
}
