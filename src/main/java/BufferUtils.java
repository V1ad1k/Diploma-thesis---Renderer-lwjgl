import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class BufferUtils {

    public static ByteBuffer createByteBuffer(int numElements){

        return nativeOrder(ByteBuffer.allocateDirect(numElements));

    }

    public static ByteBuffer nativeOrder(ByteBuffer buf) {

        return buf.order(ByteOrder.nativeOrder());

    }

    public static FloatBuffer createFloatBuffer(float[] arr)
    {
        FloatBuffer result = ByteBuffer.allocateDirect(arr.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        result.put(arr);
        result.flip();
        return result;
    }
}
