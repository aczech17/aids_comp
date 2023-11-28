import Structures.AssociativeArray.AssociativeArray;
import Structures.BitVector;
import Structures.HuffmanTree;
import org.junit.jupiter.api.Test;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class HuffmanTreeTest
{
    @Test
    public void emptyTreeGivesNullCodeMap()
    {
        AssociativeArray<Byte, Integer> emptyMap = new AssociativeArray<>();
        HuffmanTree tree = new HuffmanTree(emptyMap);

        assertNull(tree.getBytesEncoding());
    }

    @Test
    public void onlyOneByteGivesCodeZero()
    {
        AssociativeArray<Byte, Integer> byteFrequencies = new AssociativeArray<>();
        byteFrequencies.put((byte)0, 5);

        HuffmanTree tree = new HuffmanTree(byteFrequencies);

        AssociativeArray<Byte, BitVector> codes = tree.getBytesEncoding();

        BitVector expectedCode = new BitVector();
        expectedCode.pushBit(0);

        AssociativeArray<Byte, BitVector> expectedCodes = new AssociativeArray<>();
        expectedCodes.put((byte)0, expectedCode);

        assertEquals(expectedCodes, codes);
    }

    @Test
    public void A1B2C3D4()
    {
        AssociativeArray<Byte, Integer> byteFrequencies = new AssociativeArray<>();
        byteFrequencies.put((byte)'A', 1);
        byteFrequencies.put((byte)'B', 2);
        byteFrequencies.put((byte)'C', 3);
        byteFrequencies.put((byte)'D', 4);

        HuffmanTree tree = new HuffmanTree(byteFrequencies);
        var codes = tree.getBytesEncoding();

        AssociativeArray<Byte, BitVector> expectedCodes = new AssociativeArray<>();

        BitVector ACode = new BitVector();
        ACode.pushBit(1);
        ACode.pushBit(1);
        ACode.pushBit(0);

        BitVector BCode = new BitVector();
        BCode.pushBit(1);
        BCode.pushBit(1);
        BCode.pushBit(1);

        BitVector CCode = new BitVector();
        CCode.pushBit(1);
        CCode.pushBit(0);

        BitVector DCode = new BitVector();
        DCode.pushBit(0);

        expectedCodes.put((byte)'A', ACode);
        expectedCodes.put((byte)'B', BCode);
        expectedCodes.put((byte)'C', CCode);
        expectedCodes.put((byte)'D', DCode);

        assertEquals(expectedCodes, codes);
    }
}
