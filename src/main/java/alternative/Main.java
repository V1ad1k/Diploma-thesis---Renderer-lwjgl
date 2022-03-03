package alternative;

import java.io.IOException;

/**
 * The sole purpose of this class is to hold the main method.
 * <p>
 * Any other use should be placed in a separate class
 */

public class Main implements Runnable {
    private boolean running = false;
    private String pathObj;


    public void start() {
        running = true;
        Thread t = new Thread(this);
        t.start();
    }
    public Main(String arg){
        pathObj = arg;
    }

    public void loop() throws InterruptedException, IOException {
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        System.out.println();
        //"./3D-Renderer-master/3DRenderer/res/base_test_1.obj"

        Thread.sleep(1000);

        Display display = new Display(1000, 876, "Software Rendering");
        RenderContext target = display.GetFrameBuffer();
        Bitmap texture = new Bitmap("./data/simpbricks.png");
//        Bitmap texture = new Bitmap("D:\\Diploma\\LastAlternative\\3D-Renderer-master\\3DRenderer\\res\\simpbricks.png");
//        Bitmap texture2 = new Bitmap("D:\\Diploma\\LastAlternative\\3D-Renderer-master\\3DRenderer\\res\\greens.jpg");
        Bitmap texture2 = new Bitmap("./data/greens.jpg");
//        Bitmap texture3 = new Bitmap("D:\\Diploma\\LastAlternative\\3D-Renderer-master\\3DRenderer\\res\\Red_color.jpg");
        Bitmap texture3 = new Bitmap("./data/Red_color.jpg");
//        Mesh monkeyMesh = new Mesh("D:\\Diploma\\LastAlternative\\3D-Renderer-master\\3DRenderer\\res\\Car1.obj");
        Mesh monkeyMesh = new Mesh("./data/Car1.obj");
//        Mesh tree1 = new Mesh("D:\\Diploma\\LastAlternative\\3D-Renderer-master\\3DRenderer\\res\\test_tree_unmod_rotate_ok.obj");
        Mesh tree1 = new Mesh("./data/test_tree_unmod_rotate_ok.obj");

//        Mesh tree2 = new Mesh("D:\\Diploma\\LastAlternative\\3D-Renderer-master\\3DRenderer\\res\\test_tree_unmod_rotate_ok.obj");
        Transform monkeyTransform = new Transform(new Vector4f(1, -0.8f, 0.0f));
        Transform tree1Transform = new Transform(new Vector4f(6, -0.8f, -9.0f));
//        Transform tree2Transform = new Transform(new Vector4f(3,-0.8f,-6.0f));
        Transform tree2Transform = new Transform(new Vector4f(3, -0.8f, -6.0f));
        Transform tree3Transform = new Transform(new Vector4f(4, -0.8f, -7.0f));
        Transform tree4Transform = new Transform(new Vector4f(5, -0.8f, -8.0f));
//        Transform tree3Transform = new Transform(new Vector4f(4,-0.8f,-4.0f));
//        Transform tree4Transform = new Transform(new Vector4f(5,-0.8f,-5.0f));
        Transform tree5Transform = new Transform(new Vector4f(3, -0.8f, -9.0f));
        Transform tree6Transform = new Transform(new Vector4f(2, -0.8f, -8.0f));
        Transform tree66Transform = new Transform(new Vector4f(4, -0.8f, -10.0f));
        Transform tree7Transform = new Transform(new Vector4f(3, -0.8f, -8.0f));
        Transform tree8Transform = new Transform(new Vector4f(2, -0.8f, -6.0f));
        Transform tree9Transform = new Transform(new Vector4f(3, -0.8f, -5.0f));
        Transform tree10Transform = new Transform(new Vector4f(3, -0.8f, -3.0f));
        Transform tree11Transform = new Transform(new Vector4f(6, -0.8f, -9.0f));
        Transform tree12Transform = new Transform(new Vector4f(6, -0.8f, -11.0f));
        Transform tree13Transform = new Transform(new Vector4f(8, -0.8f, -10.0f));
        Transform tree14Transform = new Transform(new Vector4f(7, -0.8f, -8.0f));
        Transform tree15Transform = new Transform(new Vector4f(5, -0.8f, -7.0f));
        Transform tree16Transform = new Transform(new Vector4f(5, -0.8f, -12.0f));
        Transform tree17Transform = new Transform(new Vector4f(9, -0.8f, -14.0f));

//        Mesh terrainMesh = new Mesh(args[0]);
        Mesh terrainMesh = new Mesh(pathObj);
        Transform terrainTransform = new Transform(new Vector4f(0, -1.0f, 6.0f));


        Camera camera = new Camera(new Matrix4f().InitPerspective((float) Math.toRadians(80.0f),
                (float) target.GetWidth() / (float) target.GetHeight(), 0.1f, 1000.0f));

        float rotCounter = 0.0f;
        int counter = 0;
        float x = 1;
        float z = 0.0f;
        long previousTime = System.nanoTime();
        while (true) {
            long currentTime = System.nanoTime();
            float delta = (float) ((currentTime - previousTime) / 1000000000.0);
            previousTime = currentTime;

            camera.Update(display.GetInput(), delta);
            Matrix4f vp = camera.GetViewProjection();
            if (counter < 25) {
                monkeyTransform = new Transform(new Vector4f(x += 1, -0.8f, z += 0.3f));
                monkeyTransform = monkeyTransform.Translate(new Quaternion(new Vector4f(0, 1, 0), delta));
                counter++;
            } else {
                monkeyTransform = new Transform(new Vector4f(x -= 1, -0.8f, z -= 0.3f));
                monkeyTransform = monkeyTransform.Translate(new Quaternion(new Vector4f(0, 1, 0), 0));
            }
            tree1Transform = tree1Transform.Translate(new Quaternion(new Vector4f(0, 1, 0), delta));
            tree2Transform = tree2Transform.Translate(new Quaternion(new Vector4f(0, 1, 0), delta));

            tree3Transform = tree3Transform.Scale3(new Quaternion(new Vector4f(0, 1, 0), delta));
            tree4Transform = tree4Transform.Scale3(new Quaternion(new Vector4f(0, 1, 0), delta));
            tree5Transform = tree5Transform.Scale3(new Quaternion(new Vector4f(0, 1, 0), delta));
            tree6Transform = tree6Transform.Scale3(new Quaternion(new Vector4f(0, 1, 0), delta));
            tree66Transform = tree66Transform.Scale3(new Quaternion(new Vector4f(0, 1, 0), delta));
            tree7Transform = tree7Transform.Scale3(new Quaternion(new Vector4f(0, 1, 0), delta));
            tree8Transform = tree8Transform.Scale3(new Quaternion(new Vector4f(0, 1, 0), delta));
            tree11Transform = tree11Transform.Scale3(new Quaternion(new Vector4f(0, 1, 0), delta));
            tree15Transform = tree15Transform.Scale3(new Quaternion(new Vector4f(0, 1, 0), delta));

            tree13Transform = tree13Transform.Scale(new Quaternion(new Vector4f(0, 1, 0), delta));

            target.Clear((byte) 0x00);
            target.ClearDepthBuffer();
            monkeyMesh.Draw(target, vp, monkeyTransform.GetTransformation(), texture3);
            terrainMesh.Draw(target, vp, terrainTransform.GetTransformation(), texture);
            tree1.Draw(target, vp, tree3Transform.GetTransformation(), texture2);
            tree1.Draw(target, vp, tree4Transform.GetTransformation(), texture2);
            tree1.Draw(target, vp, tree5Transform.GetTransformation(), texture2);
            tree1.Draw(target, vp, tree6Transform.GetTransformation(), texture2);
            tree1.Draw(target, vp, tree7Transform.GetTransformation(), texture2);
            tree1.Draw(target, vp, tree15Transform.GetTransformation(), texture2);
            tree1.Draw(target, vp, tree66Transform.GetTransformation(), texture2);
            tree1.Draw(target, vp, tree11Transform.GetTransformation(), texture2);


            Thread.sleep(100);
            display.SwapBuffers();
        }
    }
    // Lazy exception handling here. You can do something more interesting
    // depending on what you're doing
    public static void main(String[] args) throws IOException, InterruptedException {
        Main main = new Main(args[0]);
        main.start();
//        new Main(args[0]).run();

//        Main main = new Main();
//        main.start();

    }

    @Override
    public void run() {
        try {
            loop();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}

//import java.io.IOException;
//
///**
// * The sole purpose of this class is to hold the main method.
// *
// * Any other use should be placed in a separate class
// */
//public class Main
//{
//    // Lazy exception handling here. You can do something more interesting
//    // depending on what you're doing
//    public static void main(String[] args) throws IOException
//    {
//        Display display = new Display(1400, 900, "Software Rendering");
//        RenderContext target = display.GetFrameBuffer();
//
//        Bitmap texture = new Bitmap("D:\\Diploma\\Trials\\3D-Renderer-master\\3DRenderer\\res\\bricks.jpg");
////        Bitmap texture2 = new Bitmap("D:\\Diploma\\Trials\\3D-Renderer-master\\3DRenderer\\res\\greens.jpg");
//        Bitmap texture2 = new Bitmap("D:\\Diploma\\Trials\\3D-Renderer-master\\3DRenderer\\res\\blue.jpg");
////        Bitmap texture2 = new Bitmap("D:\\Diploma\\Trials\\3D-Renderer-master\\3DRenderer\\res\\Red_Color.jpg");
////        Mesh monkeyMesh = new Mesh("D:\\Diploma\\Trials\\3D-Renderer-master\\3DRenderer\\res\\cubes2.obj");
//        Mesh monkeyMesh = new Mesh("D:\\Diploma\\Trials\\3D-Renderer-master\\3DRenderer\\res\\base_test_1.obj");
//
//        Transform monkeyTransform = new Transform(new Vector4f(0,0.0f,3.0f));
//
////        Mesh terrainMesh = new Mesh("D:\\Diploma\\Trials\\3D-Renderer-master\\3DRenderer\\res\\terrain2.obj");
//        Transform terrainTransform = new Transform(new Vector4f(0,-1.0f,0.0f));
//
//        Camera camera = new Camera(new Matrix4f().InitPerspective((float)Math.toRadians(80.0f),
//                (float)target.GetWidth()/(float)target.GetHeight(), 0.1f, 1000.0f));
//
////        monkeyTransform = monkeyTransform.LookAt(new Vector4f(5,10,0), new Vector4f(0,1,0,0));
//        monkeyTransform = monkeyTransform.Rotate(new Quaternion(new Vector4f(0,-10,0), 0));
//
//
//        float rotCounter = 0.0f;
//        long previousTime = System.nanoTime();
//        while(true)
//        {
//            long currentTime = System.nanoTime();
//            float delta = (float)((currentTime - previousTime)/1000000000.0);
//            previousTime = currentTime;
//
//            camera.Update(display.GetInput(), delta);
//            Matrix4f vp = camera.GetViewProjection();
//
//
//            target.Clear((byte)0x00);
//            target.ClearDepthBuffer();
//            monkeyMesh.Draw(target, vp, monkeyTransform.GetTransformation(), texture2);
////            terrainMesh.Draw(target, vp, terrainTransform.GetTransformation(), texture);
//
//            display.SwapBuffers();
//        }
//    }
//}


//public class Main
//
//{
//    // Lazy exception handling here. You can do something more interesting
//    // depending on what you're doing
//    public static void main(String[] args) throws IOException, InterruptedException {
//        System.out.println("Working Directory = " +
//                System.getProperty("user.dir"));
//        System.out.println();
//
//        Thread.sleep(1000);
//
//        Display display = new Display(1000, 900, "Software Rendering");
//        RenderContext target = display.GetFrameBuffer();
//
////        Bitmap texture = new Bitmap("D:\\Diploma\\Trials\\3D-Renderer-master\\3DRenderer\\res\\blue.jpg");
//        Bitmap texture = new Bitmap("D:\\Diploma\\LastAlternative\\3D-Renderer-master\\3DRenderer\\res\\simpbricks.png");
////        Bitmap texture2 = new Bitmap("D:\\Diploma\\Trials\\3D-Renderer-master\\3DRenderer\\res\\bricks2.jpg");
//        Bitmap texture2 = new Bitmap("D:\\Diploma\\LastAlternative\\3D-Renderer-master\\3DRenderer\\res\\greens.jpg");
//
//        Bitmap texture3 = new Bitmap("D:\\Diploma\\LastAlternative\\3D-Renderer-master\\3DRenderer\\res\\Red_color.jpg");
////        Mesh monkeyMesh = new Mesh("D:\\Diploma\\Trials\\3D-Renderer-master\\3DRenderer\\res\\smoothMonkey0.obj");
//        Mesh monkeyMesh = new Mesh("D:\\Diploma\\LastAlternative\\3D-Renderer-master\\3DRenderer\\res\\Car1.obj");
////        Mesh monkeyMesh = new Mesh("./3D-Renderer-master/3DRenderer/res/Car1.obj");
////        Mesh monkeyMesh = new Mesh(args[0]);
////        Mesh monkeyMesh = new Mesh("D:\\Diploma\\LastAlternative\\3D-Renderer-master\\3DRenderer\\res\\test_tree_unmod_rotate_ok.obj");
//        Mesh tree1 = new Mesh("D:\\Diploma\\LastAlternative\\3D-Renderer-master\\3DRenderer\\res\\test_tree_unmod_rotate_ok.obj");
////        Mesh tree2 = new Mesh("D:\\Diploma\\LastAlternative\\3D-Renderer-master\\3DRenderer\\res\\test_tree_unmod_rotate_ok.obj");
//        Transform monkeyTransform = new Transform(new Vector4f(1,-0.8f,0.0f));
//        Transform tree1Transform = new Transform(new Vector4f(6,-0.8f,-9.0f));
////        Transform tree2Transform = new Transform(new Vector4f(3,-0.8f,-6.0f));
//        Transform tree2Transform = new Transform(new Vector4f(3,-0.8f,-6.0f));
//        Transform tree3Transform = new Transform(new Vector4f(4,-0.8f,-7.0f));
//        Transform tree4Transform = new Transform(new Vector4f(5,-0.8f,-8.0f));
////        Transform tree3Transform = new Transform(new Vector4f(4,-0.8f,-4.0f));
////        Transform tree4Transform = new Transform(new Vector4f(5,-0.8f,-5.0f));
//        Transform tree5Transform = new Transform(new Vector4f(3,-0.8f,-9.0f));
//        Transform tree6Transform = new Transform(new Vector4f(2,-0.8f,-8.0f));
//        Transform tree66Transform = new Transform(new Vector4f(4,-0.8f,-10.0f));
//        Transform tree7Transform = new Transform(new Vector4f(3,-0.8f,-8.0f));
//        Transform tree8Transform = new Transform(new Vector4f(2,-0.8f,-6.0f));
//        Transform tree9Transform = new Transform(new Vector4f(3,-0.8f,-5.0f));
//        Transform tree10Transform = new Transform(new Vector4f(3,-0.8f,-3.0f));
//        Transform tree11Transform = new Transform(new Vector4f(6,-0.8f,-9.0f));
//        Transform tree12Transform = new Transform(new Vector4f(6,-0.8f,-11.0f));
//        Transform tree13Transform = new Transform(new Vector4f(8,-0.8f,-10.0f));
//        Transform tree14Transform = new Transform(new Vector4f(7,-0.8f,-8.0f));
//        Transform tree15Transform = new Transform(new Vector4f(5,-0.8f,-7.0f));
//        Transform tree16Transform = new Transform(new Vector4f(5,-0.8f,-12.0f));
//        Transform tree17Transform = new Transform(new Vector4f(9,-0.8f,-14.0f));
//
//
//
////        Mesh terrainMesh = new Mesh("D:\\Diploma\\LastAlternative\\3D-Renderer-master\\3DRenderer\\res\\base_test_1.obj");
//        Mesh terrainMesh = new Mesh(args[0]);;
//        Transform terrainTransform = new Transform(new Vector4f(0,-1.0f,6.0f));
//
//
//        Camera camera = new Camera(new Matrix4f().InitPerspective((float)Math.toRadians(80.0f),
//                (float)target.GetWidth()/(float)target.GetHeight(), 0.1f, 1000.0f));
//
//        float rotCounter = 0.0f;
//        int counter = 0;
//        float x = 1;
//        float z = 0.0f;
//        long previousTime = System.nanoTime();
//        while(true)
//        {
//            long currentTime = System.nanoTime();
//            float delta = (float)((currentTime - previousTime)/1000000000.0);
//            previousTime = currentTime;
//
//            camera.Update(display.GetInput(), delta);
//            Matrix4f vp = camera.GetViewProjection();
//            if (counter < 25) {
//                monkeyTransform = new Transform(new Vector4f(x += 1, -0.8f, z += 0.3f));
////            monkeyTransform = monkeyTransform.Rotate(new Quaternion(new Vector4f(0,1,0), 0));
//                monkeyTransform = monkeyTransform.Translate(new Quaternion(new Vector4f(0, 1, 0), delta));
//                counter++;
//            }
//            else {
//                monkeyTransform = new Transform(new Vector4f(x -= 1, -0.8f, z -= 0.3f));
////            monkeyTransform = monkeyTransform.Rotate(new Quaternion(new Vector4f(0,1,0), 0));
//                monkeyTransform = monkeyTransform.Translate(new Quaternion(new Vector4f(0, 1, 0), 0));
//            }
//            tree1Transform = tree1Transform.Translate(new Quaternion(new Vector4f(0,1,0), delta));
//            tree2Transform = tree2Transform.Translate(new Quaternion(new Vector4f(0,1,0), delta));
//
//            tree3Transform = tree3Transform.Scale3(new Quaternion(new Vector4f(0,1,0), delta));
//            tree4Transform = tree4Transform.Scale3(new Quaternion(new Vector4f(0,1,0), delta));
//            tree5Transform = tree5Transform.Scale3(new Quaternion(new Vector4f(0,1,0), delta));
//            tree6Transform = tree6Transform.Scale3(new Quaternion(new Vector4f(0,1,0), delta));
//            tree66Transform = tree66Transform.Scale3(new Quaternion(new Vector4f(0,1,0), delta));
//            tree7Transform = tree7Transform.Scale3(new Quaternion(new Vector4f(0,1,0), delta));
//            tree8Transform = tree8Transform.Scale3(new Quaternion(new Vector4f(0,1,0), delta));
//            tree11Transform = tree11Transform.Scale3(new Quaternion(new Vector4f(0,1,0), delta));
//            tree15Transform = tree15Transform.Scale3(new Quaternion(new Vector4f(0,1,0), delta));
//
//            tree13Transform = tree13Transform.Scale(new Quaternion(new Vector4f(0,1,0), delta));
////            tree14Transform = tree14Transform.Scale2(new Quaternion(new Vector4f(0,1,0), delta));
////            monkeyTransform = monkeyTransform.Translation();
//
//            target.Clear((byte)0x00);
//            target.ClearDepthBuffer();
//            monkeyMesh.Draw(target, vp, monkeyTransform.GetTransformation(), texture3);
//            terrainMesh.Draw(target, vp, terrainTransform.GetTransformation(), texture);
////            tree1.Draw(target, vp, tree1Transform.GetTransformation(), texture2);
////            tree1.Draw(target, vp, tree2Transform.GetTransformation(), texture2);
//
//            tree1.Draw(target, vp, tree3Transform.GetTransformation(), texture2);
//            tree1.Draw(target, vp, tree4Transform.GetTransformation(), texture2);
//            tree1.Draw(target, vp, tree5Transform.GetTransformation(), texture2);
//            tree1.Draw(target, vp, tree6Transform.GetTransformation(), texture2);
//            tree1.Draw(target, vp, tree7Transform.GetTransformation(), texture2);
//            tree1.Draw(target, vp, tree15Transform.GetTransformation(), texture2);
//            tree1.Draw(target, vp, tree66Transform.GetTransformation(), texture2);
//
////            tree1.Draw(target, vp, tree8Transform.GetTransformation(), texture2);
////            tree1.Draw(target, vp, tree9Transform.GetTransformation(), texture2);
////            tree1.Draw(target, vp, tree10Transform.GetTransformation(), texture2);
//            tree1.Draw(target, vp, tree11Transform.GetTransformation(), texture2);
//
//            //            tree1.Draw(target, vp, tree14Transform.GetTransformation(), texture2);
//
//
////            tree1.Draw(target, vp, tree12Transform.GetTransformation(), texture2);
////            tree1.Draw(target, vp, tree13Transform.GetTransformation(), texture2);
//
//
////            tree1.Draw(target, vp, tree16Transform.GetTransformation(), texture2);
////            tree1.Draw(target, vp, tree17Transform.GetTransformation(), texture2);
//
//            Thread.sleep(100);
//            display.SwapBuffers();
//        }
//    }
//}
//
//
////  while(true)
////          {
////          long currentTime = System.nanoTime();
////          float delta = (float)((currentTime - previousTime)/1000000000.0);
////          previousTime = currentTime;
////
////          camera.Update(display.GetInput(), delta);
////          Matrix4f vp = camera.GetViewProjection();
////
////          if(counter < 10) {
////        terrainTransform = new Transform(new Vector4f(x +0.5f, -1.0f, z));
////        monkeyTransform = monkeyTransform.Translate(new Quaternion(new Vector4f(0,1,0), delta));
////        target.Clear((byte)0x00);
////        target.ClearDepthBuffer();
////        monkeyMesh.Draw(target, vp, monkeyTransform.GetTransformation(), texture2);
////        terrainMesh.Draw(target, vp, terrainTransform.GetTransformation(), texture);
////
////        display.SwapBuffers();
////        counter++;
////        }
////        else{
////        counter = 0;
////        }
//////            monkeyTransform = monkeyTransform.Rotate(new Quaternion(new Vector4f(0,1,0), delta));
//////            monkeyTransform = monkeyTransform.Translation();
////
////
////        }
//
////import java.io.IOException;
////
/////**
//// * The sole purpose of this class is to hold the main method.
//// *
//// * Any other use should be placed in a separate class
//// */
////public class Main
////{
////    // Lazy exception handling here. You can do something more interesting
////    // depending on what you're doing
////    public static void main(String[] args) throws IOException
////    {
////        Display display = new Display(1400, 900, "Software Rendering");
////        RenderContext target = display.GetFrameBuffer();
////
////        Bitmap texture = new Bitmap("D:\\Diploma\\Trials\\3D-Renderer-master\\3DRenderer\\res\\bricks.jpg");
//////        Bitmap texture2 = new Bitmap("D:\\Diploma\\Trials\\3D-Renderer-master\\3DRenderer\\res\\greens.jpg");
////        Bitmap texture2 = new Bitmap("D:\\Diploma\\Trials\\3D-Renderer-master\\3DRenderer\\res\\blue.jpg");
//////        Bitmap texture2 = new Bitmap("D:\\Diploma\\Trials\\3D-Renderer-master\\3DRenderer\\res\\Red_Color.jpg");
//////        Mesh monkeyMesh = new Mesh("D:\\Diploma\\Trials\\3D-Renderer-master\\3DRenderer\\res\\cubes2.obj");
////        Mesh monkeyMesh = new Mesh("D:\\Diploma\\Trials\\3D-Renderer-master\\3DRenderer\\res\\base_test_1.obj");
////
////        Transform monkeyTransform = new Transform(new Vector4f(0,0.0f,3.0f));
////
//////        Mesh terrainMesh = new Mesh("D:\\Diploma\\Trials\\3D-Renderer-master\\3DRenderer\\res\\terrain2.obj");
////        Transform terrainTransform = new Transform(new Vector4f(0,-1.0f,0.0f));
////
////        Camera camera = new Camera(new Matrix4f().InitPerspective((float)Math.toRadians(80.0f),
////                (float)target.GetWidth()/(float)target.GetHeight(), 0.1f, 1000.0f));
////
//////        monkeyTransform = monkeyTransform.LookAt(new Vector4f(5,10,0), new Vector4f(0,1,0,0));
////        monkeyTransform = monkeyTransform.Rotate(new Quaternion(new Vector4f(0,-10,0), 0));
////
////
////        float rotCounter = 0.0f;
////        long previousTime = System.nanoTime();
////        while(true)
////        {
////            long currentTime = System.nanoTime();
////            float delta = (float)((currentTime - previousTime)/1000000000.0);
////            previousTime = currentTime;
////
////            camera.Update(display.GetInput(), delta);
////            Matrix4f vp = camera.GetViewProjection();
////
////
////            target.Clear((byte)0x00);
////            target.ClearDepthBuffer();
////            monkeyMesh.Draw(target, vp, monkeyTransform.GetTransformation(), texture2);
//////            terrainMesh.Draw(target, vp, terrainTransform.GetTransformation(), texture);
////
////            display.SwapBuffers();
////        }
////    }
////}