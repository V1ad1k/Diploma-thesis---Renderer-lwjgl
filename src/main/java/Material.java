
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class Material {

    public ArrayList<mtl> Materials = new ArrayList<>();

    public static class mtl {
        public String name;
        public int mtlnum;
        public float d = 1f;
        public float[] Ka = new float[3];
        public float[] Kd = new float[3];
        public int[] Kd2 = new int[3];
        public float[] Ks = new float[3];
        public float shininess = 10f;
        public float illum;
    }

    public Material(BufferedReader ref) {
        loadobject(ref);
        cleanup();
    }

    private void cleanup() {
    }

    public int getSize() {
        return Materials.size();
    }

    public float getd(String namepass) {
        float returnfloat = 1f;
        for (int i = 0; i < Materials.size(); i++) {
            mtl tempmtl = Materials.get(i);
            if (tempmtl.name.matches(namepass)) {
                returnfloat = tempmtl.d;
            }
        }
        return returnfloat;
    }

    public float[] getKa(String namepass) {
        float[] returnfloat = new float[3];
        for (int i = 0; i < Materials.size(); i++) {
            mtl tempmtl = Materials.get(i);
            if (tempmtl.name.matches(namepass)) {
                returnfloat = tempmtl.Ka;
            }
        }
        return returnfloat;
    }

    public float[] getKd(String namepass) {
        float[] returnfloat = new float[3];
        for (int i = 0; i < Materials.size(); i++) {
            mtl tempmtl = Materials.get(i);
            if (tempmtl.name.matches(namepass)) {
                returnfloat = tempmtl.Kd;
            }
        }
        return returnfloat;
    }

    public float getShininess(String namepass) {
        float returnfloat = 1f;
        for (int i = 0; i < Materials.size(); i++) {
            mtl tempmtl = Materials.get(i);
            if (tempmtl.name.matches(namepass)) {
                returnfloat = tempmtl.shininess;
            }
        }
        return returnfloat;
    }

    public int[] getKd2(String namepass) {
        int[] returnInt = new int[3];
        for (int i = 0; i < Materials.size(); i++) {
            mtl tempmtl = Materials.get(i);
            if (tempmtl.name.matches(namepass)) {
                returnInt = tempmtl.Kd2;
            }
        }
        return returnInt;
    }

    public float[] getKs(String namepass) {
        float[] returnfloat = new float[3];
        for (int i = 0; i < Materials.size(); i++) {
            mtl tempmtl = Materials.get(i);
            if (tempmtl.name.matches(namepass)) {
                returnfloat = tempmtl.Ks;
            }
        }
        return returnfloat;
    }


    public float getIllum(String namepass) {
        float returnfloat = 1f;
        for (int i = 0; i < Materials.size(); i++) {
            mtl tempmtl = Materials.get(i);
            if (tempmtl.name.matches(namepass)) {
                returnfloat = tempmtl.illum;
            }
        }
        return returnfloat;
    }


    private void loadobject(BufferedReader br) {
        int linecounter = 0;
        float[] coords;
        try {

            String newline;
            boolean firstpass = true;
            mtl matset = new mtl();
            int mtlcounter = 0;

            while (((newline = br.readLine()) != null)) {
                linecounter++;
                //newline = newline.trim();
                if (newline.length() > 0) {
                    String[] data = newline.split(" ");
                    switch (data[0]) {
                        case "newmtl" -> {
                            if (firstpass) {
                                firstpass = false;
                            } else {
                                Materials.add(matset);
                                matset = new mtl();
                            }
                            String[] coordstext = new String[2];
                            coordstext = newline.split("\\s+");
                            matset.name = coordstext[1];
                            matset.mtlnum = mtlcounter;
                            mtlcounter++;
                        }
                        case "Ka" -> {
                            coords = new float[3];
                            String[] coordstext = new String[4];
                            coordstext = newline.split("\\s+");
                            for (int i = 1; i < coordstext.length; i++) {
                                coords[i - 1] = Float.parseFloat(coordstext[i]);
                            }
                            matset.Ka = coords;
                        }
                        case "Kd" -> {
                            coords = new float[3];
                            String[] coordstext = new String[4];
                            coordstext = newline.split("\\s+");
                            for (int i = 1; i < coordstext.length; i++) {
                                coords[i - 1] = Float.parseFloat(coordstext[i]);
                            }
                            matset.Kd = coords;
                        }
                        case "Ks" -> {
                            coords = new float[3];
                            String[] coordstext = new String[4];
                            coordstext = newline.split("\\s+");
                            for (int i = 1; i < coordstext.length; i++) {
                                coords[i - 1] = Float.parseFloat(coordstext[i]);
                            }
                            matset.Ks = coords;
                        }
                        case "d" -> {
                            String[] coordstext = newline.split("\\s+");
                            matset.d = Float.parseFloat(coordstext[1]);
                        }
                        case "illum" -> {
                            String[] coordstext = newline.split("\\s+");
                            matset.illum = Float.parseFloat(coordstext[1]);
                        }
                        case "Ns" -> {
                            String[] coordstext = newline.split("\\s+");
                            matset.shininess = Float.parseFloat(coordstext[1]);
                        }
                    }
                }
            }
            Materials.add(matset);

        }
        catch(IOException e){
            System.out.println("Failed to read file: " + br.toString());
            e.printStackTrace();
        }
        catch(NumberFormatException | StringIndexOutOfBoundsException e){
            System.out.println("Malformed MTL (on line " + linecounter + "): " + br.toString() + "\r \r" + e.getMessage());
        }
    }
}
