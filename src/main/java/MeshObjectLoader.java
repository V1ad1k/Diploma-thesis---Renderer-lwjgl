
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.*;


public class MeshObjectLoader {


    private static int BUFFER_READER_SIZE = 65536;
    private static String mtl_path;
    private static Material materials;
    private static ArrayList mattimings;
    private static  Map<String, Integer> mattimings2 = new HashMap<>();
    private static  Map<Integer, String> mattimings3 = new HashMap<>();
    private static int amountFaces;

    public static List<Mesh> loadModelMeshFromStream(String mtlPath, String objPath) {
        InputStream inputstream;
        Vector3f camera = new Vector3f();
        List<Mesh> meshList = new ArrayList<>();
        List<Mesh> instances = new ArrayList<>();
        mattimings = new ArrayList();
        BufferedReader bufferedReader = null;
        mtl_path = mtlPath;
        ArrayList<Vertex> vertexList = new ArrayList<>();

        // Lists to keep all data when reading file
        ArrayList<Float> vlist = new ArrayList<Float>();
        ArrayList<Float> tlist = new ArrayList<Float>();
        ArrayList<Float> nlist = new ArrayList<Float>();
        ArrayList<Face> fplist = new ArrayList<Face>();
        ArrayList<Float> mlist = new ArrayList<Float>();

        Map<String, Integer> mapIndex = new HashMap<String, Integer>();
        Map<String, Vector3f> mapPosition = new HashMap<String,Vector3f>();
        Map<String, Vector3f> mapRotation = new HashMap<String,Vector3f>();
        Map<String, Vector3f> mapScale = new HashMap<String, Vector3f>();
        Map<String, Integer> mapMaterial = new HashMap<String, Integer>();
        Map<String, String > mapMaterialString = new HashMap<String, String>();
        Vector3f vectorPosition = new Vector3f();
        Vector3f vectorRotation = new Vector3f();
        Vector3f vectorScale = new Vector3f();

        ArrayList<Vector4f> m_positions = new ArrayList<Vector4f>();
        // Result buffers
        FloatBuffer mVertexBuffer;
        FloatBuffer mTexBuffer;
        FloatBuffer mNormBuffer;
        FloatBuffer diffuseBf = null;
        FloatBuffer ambientBf = null;
        FloatBuffer specularBf = null;

        int numVerts = 0;
        int numTexCoords = 0;
        int numNormals = 0;
        int numFaces = 0;
        int numMaterials = 0;
        float illum = 0;
        float transperacy = 0;
        float shininess;

        String str;
        String[] tmp;
        int tempCounter = -1;


        try {
            inputstream = new FileInputStream(objPath);
            int counter = 0;

            bufferedReader = new BufferedReader(new InputStreamReader(inputstream), BUFFER_READER_SIZE);

            while ((str = bufferedReader.readLine()) != null) {
                // Replace double spaces. Some files may have it.
                str = str.replace("  ", " ");
                tmp = str.split(" ");
                if (tmp[0].equalsIgnoreCase("v")) {
                    for (int i = 1; i < 4; i++) {
                        vlist.add(Float.parseFloat(tmp[i]));
                    }
                    numVerts++;
                }

                if (tmp[0].equalsIgnoreCase("vn")) {
                    for (int i = 1; i < 4; i++) {
                        nlist.add(Float.parseFloat(tmp[i]));
                    }
                    numNormals++;
                }

                if (tmp[0].equalsIgnoreCase("vt")) {
                    for (int i = 1; i < 3; i++) {
                        tlist.add(Float.parseFloat(tmp[i]));
                    }
                    numTexCoords++;
                }

                if (tmp[0].equalsIgnoreCase("f")) {

                    String[] ftmp;
                    String[] ftmp2;
                    String[] ftmp3;
                    int facex;
                    int facey;
                    int facez;

                    if (tmp.length >= 5)  {
                        amountFaces = 5;
                        for (int i = 1; i < tmp.length - 2; i++) {
                            ftmp = tmp[1].split("/");
                            ftmp2 = tmp[i+1].split("/");
                            ftmp3 = tmp[i+2].split("/");

                            facex = faceStringToInt(ftmp, 0) - 1;
                            facey = faceStringToInt(ftmp, 1) - 1;
                            facez = faceStringToInt(ftmp, 2) - 1;
                            fplist.add(new Face(facex, facey, facez));

                            facex = faceStringToInt(ftmp2, 0) - 1;
                            facey = faceStringToInt(ftmp2, 1) - 1;
                            facez = faceStringToInt(ftmp2, 2) - 1;
                            fplist.add(new Face(facex, facey, facez));

                            facex = faceStringToInt(ftmp3, 0) - 1;
                            facey = faceStringToInt(ftmp3, 1) - 1;
                            facez = faceStringToInt(ftmp3, 2) - 1;
                            fplist.add(new Face(facex, facey, facez));

                        }

                        numFaces++;
                    }

                    else{
                        amountFaces = 3;
                        for (int i = 1; i < 4; i++) {
                            ftmp = tmp[i].split("/");
                            facex = faceStringToInt(ftmp, 0) - 1;
                            facey = faceStringToInt(ftmp, 1) - 1;
                            facez = faceStringToInt(ftmp, 2) - 1;
                            fplist.add(new Face(facex, facey, facez));

                        }
                        numFaces++;
                    }
                }

                if (tmp[0].equalsIgnoreCase("mtllib")) {
                    if (mtl_path != null)
                        loadmaterials();
                    numMaterials++;
                }
                if (tmp[0].equalsIgnoreCase("usemtl")) {
                    tempCounter++;
                    String[] coords = new String[2];
                    String[] coordstext = new String[3];
                        for (int i = 1; i < 2; i++) {
                            coords[0] = tmp[1];
                            coords[1] = fplist.size() + "";
                        }
                    mattimings2.put(coords[0], Integer.valueOf(coords[1]));
                    mattimings3.put(tempCounter,coords[0]);
                    mattimings.add(coords);
                }

                if (tmp[0].equalsIgnoreCase("instance")) {

                    String keyMaterial = "Material[" + counter + "]";
                    String keyIndex = "Index[" + counter + "]";
                    String keyPosition = "Position[" + counter + "]";
                    String keyRotation = "Rotation[" + counter + "]";
                    String keyScale = "Scale[" + counter + "]";

                    mapIndex.put(keyIndex, Integer.valueOf(tmp[1]));
                    vectorPosition.add(Float.parseFloat(tmp[2]), Float.parseFloat(tmp[3]), Float.parseFloat(tmp[4]));
                    vectorRotation.add(Float.parseFloat(tmp[5]), Float.parseFloat(tmp[6]), Float.parseFloat(tmp[7]));
                    mapPosition.put(keyPosition, vectorPosition);
                    mapRotation.put(keyRotation, vectorRotation);
                    vectorScale.add(Float.parseFloat(tmp[8]), Float.parseFloat(tmp[9]), Float.parseFloat(tmp[10]));
                    mapScale.put(keyScale, vectorScale);
                    System.out.println(tmp[10] + "value");
                    mapMaterial.put(keyMaterial, Integer.valueOf(tmp[11]));
                    mapMaterialString.put(keyMaterial,mattimings3.get(Integer.valueOf(tmp[11])));
                    vectorPosition = new Vector3f();
                    vectorRotation = new Vector3f();
                    vectorScale = new Vector3f();
                    counter++;
                }
                if (tmp[0].equalsIgnoreCase("camera")) {
                    for (int i = 1; i < 3; i++) {
                        tlist.add(Float.parseFloat(tmp[i]));
                    }
                    camera = new Vector3f(Float.parseFloat(tmp[1]),Float.parseFloat(tmp[2]),Float.parseFloat(tmp[3]));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();

                }
            } catch (IOException e) {
                e.printStackTrace();

            }

        }

        // Each float takes 4 bytes
        int fplistSize = fplist.size();
        int vlistSize = vlist.size();
        int nlistSize = nlist.size();
        int tlistSize = tlist.size();
        int mlistSIze = mlist.size();

        Face face = null;

        int nextmat = -1;
        int tempnextmat = -1;
        int matcount = 0;
        int tempmatcount = 0;
        int totalmats = mattimings.size();
        String[] nextmatnamearray = null;
        String[] tempnextmatnamearray = null;
        String nextmatname = null;
        String tempnextmatname = null;

        if (totalmats > 0 && materials != null) {
            nextmatnamearray = (String[]) (mattimings.get(matcount));
            nextmatname = nextmatnamearray[0];
            nextmat = Integer.parseInt(nextmatnamearray[1]);
        }


        int facessize;
        int temp = 0;
        for (int j = 0; j < fplistSize; j++) {
            if (j == nextmat) {
                facessize = 0;
                vertexList = new ArrayList<>();

                temp = nextmat;
                tempmatcount++;
                if (tempmatcount < totalmats) {
                    tempnextmatnamearray = (String[]) (mattimings.get(tempmatcount));
                    tempnextmatname = tempnextmatnamearray[0];
                    tempnextmat = Integer.parseInt(tempnextmatnamearray[1]);
                }
                if (tempmatcount == totalmats){
                    tempnextmat = fplistSize;
                }

                for (int k = temp; k < tempnextmat; k++) {
                    facessize++;
                }

                ByteBuffer vbb = ByteBuffer.allocateDirect(facessize * 4 * 3);
                vbb.order(ByteOrder.LITTLE_ENDIAN);
                mVertexBuffer = vbb.asFloatBuffer();

                ByteBuffer vtbb = ByteBuffer.allocateDirect(facessize * 4 * 3);
                vtbb.order(ByteOrder.LITTLE_ENDIAN);
                mTexBuffer = vtbb.asFloatBuffer();

                ByteBuffer nbb = ByteBuffer.allocateDirect(facessize * 4 * 3);
                nbb.order(ByteOrder.LITTLE_ENDIAN);
                mNormBuffer = nbb.asFloatBuffer();

                ByteBuffer mdbb = ByteBuffer.allocateDirect(4 * 3);
                mdbb.order(ByteOrder.LITTLE_ENDIAN);
                diffuseBf = mdbb.asFloatBuffer();

                ByteBuffer mabb = ByteBuffer.allocateDirect(4 * 3);
                mabb.order(ByteOrder.LITTLE_ENDIAN);
                ambientBf = mabb.asFloatBuffer();

                ByteBuffer msbb = ByteBuffer.allocateDirect(4 * 3);
                msbb.order(ByteOrder.LITTLE_ENDIAN);
                specularBf = msbb.asFloatBuffer();

                diffuseBf.put(materials.getKd(nextmatname));
                illum = materials.getIllum(nextmatname);
                transperacy = materials.getd(nextmatname);
                shininess = materials.getShininess(nextmatname);
                System.out.println(transperacy + "transperacy");
                ambientBf.put(materials.getKa(nextmatname));
                specularBf.put(materials.getKs(nextmatname));

                matcount++;
                if (matcount < totalmats) {
                    nextmatnamearray = (String[]) (mattimings.get(matcount));
                    nextmatname = nextmatnamearray[0];
                    nextmat = Integer.parseInt(nextmatnamearray[1]);
                }

                for (int k = temp; k < tempnextmat; k++) {

                    face = fplist.get(k);

                    mVertexBuffer.put(vlist.get(fixedIndex(vlistSize, (int) face.fx * 3, numVerts)));
                    mVertexBuffer.put(vlist.get(fixedIndex(vlistSize, (int) (face.fx * 3 + 1), numVerts)));
                    mVertexBuffer.put(vlist.get(fixedIndex(vlistSize, (int) (face.fx * 3 + 2), numVerts)));

                    mNormBuffer.put(nlist.get(fixedIndex(nlistSize, face.fz * 3, numNormals)));
                    mNormBuffer.put(nlist.get(fixedIndex(nlistSize, face.fz * 3 + 1, numNormals)));
                    mNormBuffer.put(nlist.get(fixedIndex(nlistSize, face.fz * 3 + 2, numNormals)));

                    vertexList.add( new Vertex(
                            new Vector3f(
                                    vlist.get(fixedIndex(vlistSize, (int) face.fx * 3, numVerts)),
                                    vlist.get(fixedIndex(vlistSize, (int) face.fx * 3 +1, numVerts)),
                                    vlist.get(fixedIndex(vlistSize, (int) face.fx * 3 +2, numVerts))),
                            new Vector3f(
                                    nlist.get(fixedIndex(nlistSize, face.fz * 3, numNormals)),
                                    nlist.get(fixedIndex(nlistSize, face.fz * 3 +1, numNormals)),
                                    nlist.get(fixedIndex(nlistSize, face.fz * 3+ 2, numNormals)))));

                }

                mVertexBuffer.rewind();
                mTexBuffer.rewind();
                mNormBuffer.rewind();
                System.out.println(camera.x + "x of camera");
                Mesh obj = (new Mesh(mVertexBuffer, mNormBuffer, vertexList.size(), diffuseBf, ambientBf, specularBf, transperacy, shininess, vertexList, amountFaces, camera));
                meshList.add(obj);

            }
        }
//        if (mapIndex.size() > 0) {
//            for (int i = 0; i < mapIndex.size(); i++) {
//                instances.add(addInstance(
//                        new Vector3f(mapPosition.get("Position[" + i + "]")),
//                        new Vector3f(mapRotation.get("Rotation[" + i + "]")),
//                        meshList.get(mapIndex.get("Index["+ i + "]")).vertices,
//                        meshList.get(mapIndex.get("Index["+ i + "]")).normals,
////                        meshList.get(mapIndex.get("Index["+ i + "]")).texCoords,
//                        meshList.get(mapIndex.get("Index["+ i + "]")).numVertices,
//                        meshList.get(mapMaterial.get("Material[" + i + "]")).diffuseBf,
//                        meshList.get(mapMaterial.get("Material[" + i + "]")).ambientBf,
//                        meshList.get(mapMaterial.get("Material[" + i + "]")).specularBf,
//                        meshList.get(mapMaterial.get("Material[" + i + "]")).transperacy,
//                        meshList.get(mapMaterial.get("Material[" + i + "]")).shininess,
//                        new Vector3f(mapScale.get("Scale[" + i + "]")),
//                        meshList.get(mapIndex.get("Index[" + i + "]")).vertexArrayList)
//                );
//
//            }
//        }
//
//        return instances;


        if (mapIndex.size() > 0) {
            for (int i = 0; i < mapIndex.size(); i++) {
                meshList.add(addInstance(
                        new Vector3f(mapPosition.get("Position[" + i + "]")),
                        new Vector3f(mapRotation.get("Rotation[" + i + "]")),
                        meshList.get(mapIndex.get("Index["+ i + "]")).vertices,
                        meshList.get(mapIndex.get("Index["+ i + "]")).normals,
                        meshList.get(mapIndex.get("Index["+ i + "]")).numVertices,
                        meshList.get(mapMaterial.get("Material[" + i + "]")).diffuseBf,
                        meshList.get(mapMaterial.get("Material[" + i + "]")).ambientBf,
                        meshList.get(mapMaterial.get("Material[" + i + "]")).specularBf,
                        meshList.get(mapMaterial.get("Material[" + i + "]")).transperacy,
                        meshList.get(mapMaterial.get("Material[" + i + "]")).shininess,
                        new Vector3f(mapScale.get("Scale[" + i + "]")),
                        meshList.get(mapIndex.get("Index[" + i + "]")).vertexArrayList)
                );

            }
        }

        if (meshList.size() > 500)
        meshList.remove(525);

        return meshList;

    }

    private static Mesh addInstance(Vector3f position, Vector3f rotation, FloatBuffer vertices, FloatBuffer normals,
                                    int numVertices, FloatBuffer diffuseBf, FloatBuffer ambientBf, FloatBuffer specularBf,
                                    float transperacy , float shininess , Vector3f scale, ArrayList<Vertex> vertexArrayList) {
        return new Mesh(position, rotation, vertices, normals, numVertices, diffuseBf, ambientBf, specularBf, transperacy, shininess , scale, vertexArrayList);
    }


    private static void loadmaterials() {
        FileReader frm;
        String refm = mtl_path;

        try {
            frm = new FileReader(refm);
            BufferedReader brm = new BufferedReader(frm);
            materials = new Material(brm);
            frm.close();
        } catch (IOException e) {
            System.out.println("Could not open file: " + refm);
            materials = null;
        }
    }

    private static int fixedIndex(int listSize, int index, int total) {
        if (index >= listSize || index < 0) {
            return total -1;
        } else {
            return index;
        }
    }

    private static int faceStringToInt(String[] number, int index) {
        int result;

        try {
            result = Integer.parseInt(number[index]);
        } catch (Exception e) {
            result = 0;
        }

        return result;
    }

}