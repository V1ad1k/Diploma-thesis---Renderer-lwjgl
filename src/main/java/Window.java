

//import com.jogamp.opengl.swt.GLCanvas;
//import com.jmex.awt.lwjgl.LWJGLCanvas;

import org.lwjgl.glfw.GLFWVidMode;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.Method;

import static org.lwjgl.glfw.GLFW.*;


public class Window extends JFrame implements ActionListener {
    private static float cameraPos;


    JMenu menu = new JMenu("Options");
    JMenu menu2 = new JMenu("Renderer");
    JMenu submenu = new JMenu("Save");
    JMenu submenu2 = new JMenu("Built-in");
    JMenuBar menuBar = new JMenuBar();
    JButton cameraParameters, useAlternativeRenderer;
    JMenuItem importButton, quitButton, saveButton, saveAsImage, saveAsVideo, MyRender, Alrernative, staticRenderer, staticAndDynamicRenderer;
    JFrame frame;
    JTextField testField;
    static boolean flagImage = false;
    private static String pathObj = null;
    private static String pathMtlFromObj = null;
    private static boolean flag = false;
    private static String pathImg;
    private LwjglApp lwjglApp;
    private LwjglApp2 lwjglApp2;
    private LwjglApp3 lwjglApp3;
    private Canvas canvas;


    public Window(String objPath, String mtlPath) {
        JPanel north = new JPanel(new GridLayout(2, 1));//  2 rows the same width and height.
        JPanel topRow = new JPanel(new GridLayout(1, 3));// 3 buttons the same size
        canvas = new Canvas();
        frame = new JFrame("3D world renderer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 900);
        frame.setLayout(new BorderLayout());

        quitButton = new JMenuItem("Quit");
        importButton = new JMenuItem("Import");
        MyRender = new JMenuItem("OwnRender");
        Alrernative = new JMenuItem("Alternative");
        cameraParameters = new JButton("Camera");
        useAlternativeRenderer = new JButton("Alternative Renderer");
        saveAsImage = new JMenuItem("as an image");
        saveAsVideo = new JMenuItem("as a video");
        saveButton = new JMenuItem("Save");
        staticRenderer = new JMenuItem("static");
        staticAndDynamicRenderer = new JMenuItem("static with dynamic");
        testField = new JTextField(10);

        north.add(topRow);
        menu.add(importButton);
        submenu.add(saveAsImage);
        submenu.add(saveAsVideo);
        submenu2.add(staticRenderer);
        submenu2.add(staticAndDynamicRenderer);
        menu.add(submenu);
        menu2.add(submenu2);
        menuBar.add(menu);
        menuBar.add(menu2);
        menu.add(quitButton);

        MyRender.addActionListener(this);
        saveAsVideo.addActionListener(this);
        saveAsImage.addActionListener(this);
        Alrernative.addActionListener(this);
        staticRenderer.addActionListener(this);
        staticAndDynamicRenderer.addActionListener(this);
        menu2.add(Alrernative);
        menuBar.add(menu2);
        frame.setJMenuBar(menuBar);

        importButton.addActionListener(this);
        saveButton.addActionListener(this);
        quitButton.addActionListener(this);
        cameraParameters.addActionListener(this);
        testField.addActionListener(this);
        useAlternativeRenderer.addActionListener(this);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
        frame.add(north, BorderLayout.NORTH);
        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {


        if (e.getSource() == staticRenderer) {
            System.out.println(flag);
            if (flag) {
                lwjglApp2 = new LwjglApp2(pathObj, 0, 8, 12, "img", "video");
                lwjglApp2.start();
            } else {
                JOptionPane.showMessageDialog(frame, "Please select the Obj file");
            }
        }

        if (e.getSource() == staticAndDynamicRenderer) {
            System.out.println(flag);
            if (flag) {
                lwjglApp3 = new LwjglApp3(pathObj, 0, 8, 12, "img", "video");
                lwjglApp3.start();
            } else {
                JOptionPane.showMessageDialog(frame, "Please select the Obj file");
            }
        }

        if (e.getSource() == MyRender) {
            System.out.println(flag);
            if (flag) {
                lwjglApp2 = new LwjglApp2(pathObj, 0, 8, 12, "img", "video");
                lwjglApp2.start();
            } else {
                JOptionPane.showMessageDialog(frame, "Please select the Obj file");
            }
        }

        if (e.getSource() == Alrernative) {

            System.out.println(flag);
            if (flag) {
                try {
                    invokeClass("alternative.Main", new String[]{pathObj});
                } catch (IOException | ReflectiveOperationException ioException) {
                    ioException.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select the Obj file");
            }
        }

        if (e.getSource() == importButton) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                System.out.println(selectedFile);
                pathObj = selectedFile.toString();
            }
            String extension = "";

            int i = pathObj.lastIndexOf('.');
            if (i > 0) {
                extension = pathObj.substring(i + 1);
            }
            System.out.println(extension + "extension");
            pathMtlFromObj = pathObj.substring(0, pathObj.length() - extension.length()) + "mtl";
            System.out.println(pathMtlFromObj + " MtlFromObj");
            if (pathObj != null && pathMtlFromObj != null) {
                flag = true;
            }
            System.out.println(flag);

        }
        if (e.getSource() == quitButton) {
            System.exit(0);
        }
        if (e.getSource() == cameraParameters) {
            cameraPos = 45.0f;
        }
        if (e.getSource() == testField) {
            String getValue = testField.getText();
            System.out.println(getValue);

        }

        if (e.getSource() == saveAsImage) {
            JFrame parentFrame = new JFrame();
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(".png", "png");
            fileChooser.setFileFilter(filter);

            fileChooser.setDialogTitle("Specify a file to save");

            int userSelection = fileChooser.showSaveDialog(parentFrame);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                System.out.println("Save as file: " + fileToSave.getAbsolutePath());
                pathImg = fileToSave.getAbsolutePath();
            }

            System.out.println("clicked save to Image");
            flagImage = true;
        }

        if (e.getSource() == saveAsVideo) {
            if (new File(("./storage/video.mp4")).exists())
                FileUtils.deleteImages(("./storage/video.mp4"));

            System.out.println("clicked save to video");
            String c = "ffmpeg -r 10 -f image2 -s 1920x1080 -i ./storage/img%01d.png -vcodec libx264 -crf 25  -pix_fmt yuv420p ./storage/video.mp4";
            try {
                Runtime.getRuntime().exec(c);
                System.out.println("executed");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }
    }

    private String invokeClass(String className, String[] path) throws IOException, ReflectiveOperationException {
        Class<?> clazz = Class.forName(className);
        Method main = clazz.getDeclaredMethod("main", String[].class);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             PrintStream ps = new PrintStream(out)) {
            System.setOut(ps);
            main.invoke(main, new Object[]{path});
            return out.toString();
        } finally {
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        }
    }


    public static void setFlagImage(boolean flag) {
        flagImage = flag;
    }

    public static boolean getFlagImage() {
        return flagImage;
    }

    public static String getPathAnd() {
        return pathImg;
    }


    public static void main(String[] args) {
        new Window(args[0], args[1]);
    }
}
