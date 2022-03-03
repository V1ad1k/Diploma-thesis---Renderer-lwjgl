import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileUtils
{

    public static String readAsString(String path)throws Exception
    {
        String result;
        try (InputStream in = Class.forName(FileUtils.class.getName()).getResourceAsStream(path);
             Scanner scanner = new Scanner(in, String.valueOf(StandardCharsets.UTF_8))) {
            result = scanner.useDelimiter("\\A").next();
        }
        return result;
    }

    public static String getMtlPathFromOBJ(String objPath){
        String extension = "";

        int l = objPath.lastIndexOf('.');
        if (l > 0) { extension = objPath.substring(l + 1); }

        return objPath.substring(0, objPath.length() - extension.length()) + "mtl";
    }

    public static void deleteImages(String img){
        Path imagesPath = Paths.get(img);

        try {
            Files.delete(imagesPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
